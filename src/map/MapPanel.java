package map;

import gameSetup.GameLauncher;
import gameSetup.SplashScreen;
import gameSetup.GameManager;
import gameSetup.Player;
import troops.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

/**
 * Panel responsible for rendering the game map
 * Delegates all interaction state to MapInteractionHandler
 */
public class MapPanel extends JPanel implements GameLauncher.Scalable {
    // Risorse e gestione della mappa
    private BufferedImage _backgroundMap;        // visible map
    private BufferedImage _clickDetectionMap;    // invisible map with color codes
    private static Map<Color, Territory> _colorToTerritory;
    private static Map<String, BufferedImage> _territoryImages;
    private static Map<Territory, Point> _territoryCenters;
    private List<Territory> _territories = new ArrayList<>();
    private WorldMapData _worldMapData;
    
    // Costanti e riferimenti esterni
    private final String[] _TERRITORY_COLORS;
    private final Font _TERRITORY_FONT;
    private final GameManager _gameManager;
    
    // Handler per le interazioni - Single Source of Truth
    private MapInteractionHandler _interactionHandler;

    /**
     * Constructor
     */
    public MapPanel(Color[] playerColors, GameManager gameManager) {
        _gameManager = gameManager;
        setSize(new Dimension(800, 600));
        _territoryImages = new HashMap<>();
        _TERRITORY_FONT = SplashScreen.getPromptFont();
        _TERRITORY_COLORS = new String[playerColors.length + 1];

        _worldMapData = new WorldMapData();
        _territories = _worldMapData.getTerritories();
        _colorToTerritory = _worldMapData.getColorToTerritoryMap();

        // Inizializzazione in sequenza
        loadImages();
        initializeColorNames(playerColors);
        loadTerritoryImages();
        initializeTerritoryCenters();

        // Carica le icone delle truppe
        SwingUtilities.invokeLater(() -> {
            Troop[] troops = TroopFactory.getAllTroops();
            for (Troop troop : troops) {
                BufferedImage icon = TroopFactory.getTroopIcon(troop.getName());
                if (icon == null) {
                    System.err.println("Warning: Could not load icon for " + troop.getName());
                }
            }
        });
    }

    /**
     * Set the interaction handler
     */
    public void setInteractionHandler(MapInteractionHandler handler) {
        // Rimuove i vecchi listener per evitare duplicati
        for (MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }
        
        _interactionHandler = handler;
        
        // Aggiunge il nuovo listener che delega all'handler
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                _interactionHandler.handleClick(e);
            }
        });
    }

    /**
     * Find territory at clicked point - accessibile dall'handler
     */
    public Territory getTerritoryAtPoint(Point point) {
        if (_clickDetectionMap == null || point == null) {
            return null;
        }
        
        // Scale the point to match detection map dimensions
        float scaleX = (float)_clickDetectionMap.getWidth() / getWidth();
        float scaleY = (float)_clickDetectionMap.getHeight() / getHeight();
        Point scaledPoint = new Point((int)(point.x * scaleX), (int)(point.y * scaleY));
        
        // Assicurati che il punto sia dentro i limiti dell'immagine
        if (scaledPoint.x < 0 || scaledPoint.y < 0 || 
            scaledPoint.x >= _clickDetectionMap.getWidth() || 
            scaledPoint.y >= _clickDetectionMap.getHeight()) {
            return null;
        }
        
        // Usa il sampling di area per trovare il colore più comune
        int areaColor = sampleAreaColor(scaledPoint.x, scaledPoint.y, 3);
        Color clickColor = new Color(areaColor);
        
        // Cerca il territorio basato sul colore
        Territory exactMatch = _colorToTerritory.get(clickColor);
        if (exactMatch != null) {
            return exactMatch;
        }
        
        // Se non troviamo una corrispondenza esatta, prova con la distanza di colore
        return findTerritoryByColorDistance(clickColor, 20);
    }
    
    /**
     * Check if a color is significant (not transparent/background)
     */
    private boolean isSignificantColor(int rgb) {
        int alpha = (rgb >> 24) & 0xff;
        // If nearly transparent, it's likely not a territory
        if (alpha < 50) return false;
        
        Color color = new Color(rgb, true);
        // Check if it's close to white or black (often background colors)
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        
        // If close to white
        if (r > 240 && g > 240 && b > 240) return false;
        // If close to black
        if (r < 15 && g < 15 && b < 15) return false;
        
        return true;
    }

    /**
     * Sample an area around the click point to find most common color
     * Helps with anti-aliasing issues and edge detection
     */
    private int sampleAreaColor(int centerX, int centerY, int radius) {
        Map<Integer, Integer> colorCounts = new HashMap<>();
        
        for (int y = centerY - radius; y <= centerY + radius; y++) {
            for (int x = centerX - radius; x <= centerX + radius; x++) {
                if (x >= 0 && x < _clickDetectionMap.getWidth() && 
                    y >= 0 && y < _clickDetectionMap.getHeight()) {
                    
                    int rgb = _clickDetectionMap.getRGB(x, y);
                    if (isSignificantColor(rgb)) {
                        colorCounts.put(rgb, colorCounts.getOrDefault(rgb, 0) + 1);
                    }
                }
            }
        }
        
        // Find most common color
        int mostCommonColor = 0;
        int highestCount = 0;
        
        for (Map.Entry<Integer, Integer> entry : colorCounts.entrySet()) {
            if (entry.getValue() > highestCount) {
                highestCount = entry.getValue();
                mostCommonColor = entry.getKey();
            }
        }
        
        return mostCommonColor;
    }

    /**
     * Find territory by color distance with adaptive threshold
     */
    private Territory findTerritoryByColorDistance(Color targetColor, int initialMaxDistance) {
        Territory closestTerritory = null;
        int closestDistance = Integer.MAX_VALUE;
        
        // Estratti i componenti target
        int targetR = targetColor.getRed();
        int targetG = targetColor.getGreen();
        int targetB = targetColor.getBlue();
        
        // Usa un threshold adattivo - inizia basso e aumenta se necessario
        int maxDistance = initialMaxDistance;
        int maxAttempts = 3;
        
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            for (Map.Entry<Color, Territory> entry : _colorToTerritory.entrySet()) {
                Color mapColor = entry.getKey();
                
                // Usa un algoritmo di distanza del colore più accurato (CIE76)
                // che rispetta meglio la percezione umana
                float rMean = (float) (targetR + mapColor.getRed()) / 2.0f;
                float weightR = 2.0f + rMean / 256.0f;
                float weightG = 4.0f; // Verde ha più peso nella percezione umana
                float weightB = 2.0f + (255.0f - rMean) / 256.0f;

                float deltaR = (float) (weightR * Math.pow(targetR - mapColor.getRed(), 2));
                float deltaG = (float) (weightG * Math.pow(targetG - mapColor.getGreen(), 2));
                float deltaB = (float) (weightB * Math.pow(targetB - mapColor.getBlue(), 2));

                int distance = (int)Math.sqrt(deltaR + deltaG + deltaB);
                
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestTerritory = entry.getValue();
                }
            }
            
            if (closestDistance <= maxDistance) {
                return closestTerritory;
            }
            
            // Aumenta la distanza massima e riprova
            maxDistance *= 2;
        }
        
        // Se ancora non abbiamo trovato corrispondenza, ritorna null
        return null;
    }

    /**
     * Load base map images
     */
    private void loadImages() {
        try {
            File backgroundFile = new File("resources/images/worldMap.png");
            File clickDetectionFile = new File("resources/images/worldMapClickDetection.png");
            
            if (!backgroundFile.exists() || !clickDetectionFile.exists()) {
                System.err.println("One or more map files do not exist!");
                System.err.println("Background exists: " + backgroundFile.exists());
                System.err.println("Click detection exists: " + clickDetectionFile.exists());
                createFallbackImages();
                return;
            }
            
            _backgroundMap = ImageIO.read(backgroundFile);
            _clickDetectionMap = ImageIO.read(clickDetectionFile);
    
            if (_backgroundMap == null || _clickDetectionMap == null) {
                System.err.println("Failed to load one or more images!");
                createFallbackImages();
            }
        } catch (IOException e) {
            System.err.println("Error loading images: " + e.getMessage());
            e.printStackTrace();
            createFallbackImages();
        }
    }

    /**
     * Create fallback images if loading fails
     */
    private void createFallbackImages() {
        _backgroundMap = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = _backgroundMap.createGraphics();
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, 800, 600);
        g2d.dispose();

        _clickDetectionMap = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        g2d = _clickDetectionMap.createGraphics();
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, 800, 600);
        g2d.dispose();
    }

    /**
     * Initialize color names for territories
     */
    private void initializeColorNames(Color[] playerColors) {
        _TERRITORY_COLORS[0] = "grey";
        for (int i = 0; i < playerColors.length; i++) {
            if (playerColors[i] != null) {
                if (playerColors[i].equals(Color.RED)) {_TERRITORY_COLORS[i + 1] = "red";}
                if (playerColors[i].equals(Color.BLUE)) {_TERRITORY_COLORS[i + 1] = "blue";}
                if (playerColors[i].equals(Color.GREEN)) {_TERRITORY_COLORS[i + 1] = "green";}
                if (playerColors[i].equals(Color.YELLOW)) {_TERRITORY_COLORS[i + 1] = "yellow";}
                if (playerColors[i].equals(Color.CYAN)) {_TERRITORY_COLORS[i + 1] = "cyan";}
                if (playerColors[i].equals(new Color(128, 0, 128))) {_TERRITORY_COLORS[i + 1] = "purple";}
            }
        }
    }

    /**
     * Get the name of a color
     */
    private String getColorName(Color color) {
        if (color == null) {
            return "grey";  // Default color for unowned territories
        }
        
        if (color.equals(Color.RED)) return "red";
        if (color.equals(Color.BLUE)) return "blue";
        if (color.equals(Color.GREEN)) return "green";
        if (color.equals(Color.YELLOW)) return "yellow";
        if (color.equals(Color.CYAN)) return "cyan";
        if (color.equals(new Color(128, 0, 128))) return "purple";
        return "grey";
    }

    /**
     * Load all territory images
     */
    public void loadTerritoryImages() {
        for (Territory territory : _territories) {
            String territoryId = territory.getName().replace(" ", "");
        
            // all grey versions
            String greyKey = "grey" + territoryId;
            if (!_territoryImages.containsKey(greyKey)) {
                loadTerritoryImage(greyKey);
            }
            
            if (territory.isOccupied()) {
                String colorName = getColorName(territory.getOwner().getColor());
                String colorKey = colorName.toLowerCase() + territoryId;
                if (!_territoryImages.containsKey(colorKey)) {
                    loadTerritoryImage(colorKey);
                }
            }
        }
    }


    /**
     * Main rendering method
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw base map
        if (_backgroundMap != null) {
            g2d.drawImage(_backgroundMap, 0, 0, getWidth(), getHeight(), this);
        }

        // Draw territories
        if (_territories != null) {
            for (Territory territory : _territories) {
                // Sostituiamo la chiamata drawTerritory con codice appropriato per mostrare l'immagine del territorio
                String territoryId = territory.getName().replace(" ", "");
                String colorKey;
                
                if (territory.isOccupied() && territory.getOwner() != null) {
                    String colorName = getColorName(territory.getOwner().getColor());
                    colorKey = colorName.toLowerCase() + territoryId;
                } else {
                    colorKey = "grey" + territoryId;
                }
                
                BufferedImage territoryImage = _territoryImages.get(colorKey);
                if (territoryImage != null) {
                    g2d.drawImage(territoryImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        }

        // Usa MapInteractionHandler per il rendering interattivo
        if (_interactionHandler != null) {
            // Ottiene lo stato dall'handler
            Territory selectedTerritory = _interactionHandler.getSelectedTerritory();
            Territory sourceTerritory = _interactionHandler.getSourceTerritory();
            List<Territory> highlightedTerritories = _interactionHandler.getHighlightedTerritories();
            Territory justConquered = _interactionHandler.getJustConquered();
            GameManager.ActionType currentAction = _interactionHandler.getCurrentAction();
            
            // Disegna il territorio sorgente (sempre visibile durante un'azione)
            if (sourceTerritory != null && currentAction != GameManager.ActionType.NONE) {
                // Usa il colore appropriato per il territorio sorgente
                highlightTerritory(g2d, sourceTerritory, GameManager.ActionType.NONE);
            }
            
            // Disegna i territori evidenziati (lampeggianti)
            if (highlightedTerritories != null && !highlightedTerritories.isEmpty()) {
                for (Territory t : highlightedTerritories) {
                    // Usa getBlinkStateForTerritory per determinare se visualizzare l'highlight
                    if (_interactionHandler.getBlinkStateForTerritory(t)) {
                        highlightTerritory(g2d, t, currentAction);
                    }
                }
            }
            
            // Disegna il territorio selezionato se non è il territorio sorgente
            if (currentAction == GameManager.ActionType.NONE && selectedTerritory != null) {
                if (_interactionHandler.getBlinkState()) {
                    highlightTerritory(g2d, selectedTerritory, GameManager.ActionType.NONE);
                }
            }
            
            // Disegna territorio appena conquistato
            if (justConquered != null) {
                drawConqueredTerritory(g2d, justConquered);
            }
        }
        
        // Draw troops count
        if (_territories != null) {
            for (Territory territory : _territories) {
                if (territory.isOccupied()) {
                    drawTroopCount(g2d, territory);
                }
            }
        }
    }

    /**
     * Scale the panel for different resolutions
     */
    @Override
    public void scale(int width, int height) {
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        
        SwingUtilities.invokeLater(() -> {
            initializeTerritoryCenters();
            repaint();
        });
    }

    /**
     * Highlight a territory with appropriate color based on action type
     */
    public void highlightTerritory(Graphics2D g2d, Territory territory, GameManager.ActionType actionType) {
        String territoryId = territory.getName().replace(" ", "");
        String maskKey = territory.isOccupied() ? 
            getColorName(territory.getOwner().getColor()).toLowerCase() + territoryId : 
            "grey" + territoryId;
        
        BufferedImage mask = _territoryImages.get(maskKey);
        
        if (mask == null) return;
        
        Composite originalComposite = g2d.getComposite();
        
        // Usa colore diverso in base all'azione corrente
        if (actionType == GameManager.ActionType.MOVE) {
            // Verde per movimento - meno trasparente (alpha 180 invece di 128)
            g2d.setColor(new Color(0, 255, 0, 180));
        } else if (actionType == GameManager.ActionType.ATTACK) {
            // Rosso per attacco - meno trasparente
            g2d.setColor(new Color(255, 0, 0, 180));
        } else {
            // Bianco per selezione normale - meno trasparente
            g2d.setColor(new Color(255, 255, 255, 180));
        }

        // Disegna un overlay colorato usando l'immagine del territorio come maschera
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        
        // Crea un'immagine temporanea con il colore dell'highlight
        BufferedImage coloredMask = new BufferedImage(mask.getWidth(), mask.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dMask = coloredMask.createGraphics();
        g2dMask.drawImage(mask, 0, 0, null);
        g2dMask.setComposite(AlphaComposite.SrcAtop);
        g2dMask.setColor(g2d.getColor());
        g2dMask.fillRect(0, 0, mask.getWidth(), mask.getHeight());
        g2dMask.dispose();
        
        // Disegna l'immagine colorata
        g2d.drawImage(coloredMask, 0, 0, getWidth(), getHeight(), null);
        g2d.setComposite(originalComposite);
    }

    /**
     * Calculate centers of territories for troop icon placement
     */
    private void initializeTerritoryCenters() {
        _territoryCenters = new HashMap<>();
        if (_clickDetectionMap == null) return;

        // Usa un fattore di campionamento per migliorare la performance
        // Analizza 1/N pixel invece di ogni pixel 
        final int SAMPLING_FACTOR = 4;
        
        for (Map.Entry<Color, Territory> entry : _colorToTerritory.entrySet()) {
            Color territoryColor = entry.getKey();
            Territory territory = entry.getValue();

            long sumX = 0;
            long sumY = 0;
            int count = 0;

            // Usa campionamento per calcolare il centro di massa invece di ogni pixel
            for (int y = 0; y < _clickDetectionMap.getHeight(); y += SAMPLING_FACTOR) {
                for (int x = 0; x < _clickDetectionMap.getWidth(); x += SAMPLING_FACTOR) {
                    Color pixelColor = new Color(_clickDetectionMap.getRGB(x, y));
                    if (pixelColor.equals(territoryColor)) {
                        sumX += x;
                        sumY += y;
                        count++;
                    }
                }
            }

            if (count > 0) {
                // Centro di massa
                int centerX = (int)(sumX / count);
                int centerY = (int)(sumY / count);
                
                // Verifica che il punto sia nel territorio
                Color centerColor = new Color(_clickDetectionMap.getRGB(centerX, centerY));
                if (!centerColor.equals(territoryColor)) {
                    // Usa un algoritmo di spirale ottimizzato
                    Point validPoint = findNearestValidPoint(centerX, centerY, territoryColor);
                    if (validPoint != null) {
                        centerX = validPoint.x;
                        centerY = validPoint.y;
                    }
                }
                
                _territoryCenters.put(territory, new Point(centerX, centerY));
            }
        }
    }

    /**
     * Trova il punto valido più vicino usando algoritmo a spirale efficiente
     */
    private Point findNearestValidPoint(int centerX, int centerY, Color targetColor) {
        int maxRadius = Math.min(_clickDetectionMap.getWidth(), _clickDetectionMap.getHeight()) / 8;
        int x = 0;
        int y = 0;
        int dx = 0;
        int dy = -1;
        int radius = 1;
        
        // Ottimizzazione: salva i punti già testati
        Set<Point> testedPoints = new HashSet<>();
        
        while (radius <= maxRadius) {
            if ((-radius <= x && x <= radius) && (-radius <= y && y <= radius)) {
                int testX = centerX + x;
                int testY = centerY + y;
                Point testPoint = new Point(testX, testY);
                
                if (!testedPoints.contains(testPoint) && 
                    testX >= 0 && testX < _clickDetectionMap.getWidth() &&
                    testY >= 0 && testY < _clickDetectionMap.getHeight()) {
                    
                    testedPoints.add(testPoint);
                    
                    Color testColor = new Color(_clickDetectionMap.getRGB(testX, testY));
                    if (testColor.equals(targetColor)) {
                        return testPoint;
                    }
                }
            }
            
            // Algoritmo a spirale ottimizzato
            if (x == y || (x < 0 && x == -y) || (x > 0 && x == 1-y)) {
                int temp = dx;
                dx = -dy;
                dy = temp;
            }
            x += dx;
            y += dy;
            radius = Math.max(Math.abs(x), Math.abs(y));
        }
        
        return null;
    }


    /**
     * Show animation for conquered territory
     */
    private void drawConqueredTerritory(Graphics2D g2d, Territory territory) {
        if (territory == null || territory.getOwner() == null) {
            return;  // Skip drawing if territory or owner is null
        }
        
        String territoryId = territory.getName().replace(" ", "");
        String key = getColorName(territory.getOwner().getColor()).toLowerCase() + territoryId;
        BufferedImage territoryImage = _territoryImages.get(key);
        
        if (territoryImage != null) {
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2d.drawImage(territoryImage, 0, 0, getWidth(), getHeight(), this);
            g2d.setComposite(originalComposite);
        }
    }

    /**
     * Draw troop counts on territories
     */
    private void drawTroopCount(Graphics2D g2d, Territory territory) {
        if (!territory.isOccupied() || territory.getOwner() == null) {
            return;  // Skip drawing troops for unowned territories
        }
        
        Point originalCenter = _territoryCenters.get(territory);
        if (originalCenter == null) return;

        // Calculate scaled coordinates correctly
        float scaleX = (float) getWidth() / _clickDetectionMap.getWidth();
        float scaleY = (float) getHeight() / _clickDetectionMap.getHeight();
        int scaledX = (int) (originalCenter.x * scaleX);
        int scaledY = (int) (originalCenter.y * scaleY);

        int circleSize = 30;
        int padding = 20;  // Reduced padding between icons
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get all troop types that exist in this territory
        List<String> troopTypesPresent = new ArrayList<>();
        Troop[] allTroops = TroopFactory.getAllTroops();
        for (Troop troop : allTroops) {
            String troopType = troop.getName();
            if (territory.getTroopCount(troopType) > 0) {
                troopTypesPresent.add(troopType);
            }
        }
        
        // Calculate total width of all icons to center the entire group
        int totalWidth = troopTypesPresent.size() * (circleSize + padding) - padding;
        int startX = scaledX - (totalWidth / 2);
        
        // Draw each troop icon
        for (int i = 0; i < troopTypesPresent.size(); i++) {
            String troopType = troopTypesPresent.get(i);
            int count = territory.getTroopCount(troopType);
            
            int x = startX + (i * (circleSize + padding)) + (circleSize / 2);
            
            // Draw the colored circle of the player
            g2d.setColor(territory.getOwner().getColor());
            g2d.fillOval(x - circleSize/2, scaledY - circleSize/2, circleSize, circleSize);
            Color crownColor;
            
            // Draw the white center
            if (territory.getOwner().getColor().equals(Color.CYAN) || 
                territory.getOwner().getColor().equals(Color.YELLOW) ||
                territory.getOwner().getColor().equals(Color.GREEN))
            {
                crownColor = Color.BLACK;
            } 
            else 
            {
                crownColor = Color.WHITE;
            }
            g2d.setColor(crownColor);

            int innerSize = circleSize - 6;
            g2d.fillOval(x - innerSize/2, scaledY - innerSize/2, innerSize, innerSize);
            
            // Draw the white border
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawOval(x - circleSize/2, scaledY - circleSize/2, circleSize, circleSize);

            // Draw the troop icon
            BufferedImage troopIcon = TroopFactory.getTroopIcon(troopType);
            if (troopIcon != null) {
                int iconSize = 20;
                g2d.drawImage(troopIcon, 
                    x - iconSize/2, 
                    scaledY - iconSize/2, 
                    iconSize, 
                    iconSize, 
                    null);
            } else {
                System.err.println("Missing icon for troop type: " + troopType);
                // Draw fallback icon
                g2d.setColor(territory.getOwner().getColor().darker());
                g2d.setFont(_TERRITORY_FONT.deriveFont(14f));
                String letter = troopType.substring(0, 1);
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(letter, 
                    x - fm.stringWidth(letter)/2, 
                    scaledY + fm.getHeight()/4);
            }

            // Draw the count number
            g2d.setColor(crownColor);
            g2d.setFont(_TERRITORY_FONT.deriveFont(12f));
            String countText = String.valueOf(count);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(countText);
            g2d.drawString(countText, 
                x - textWidth/2, 
                scaledY + circleSize/2 + 12);
        }
    }

    // Metodi pubblici per interfacciamento con GameManager e altri componenti

    /**
     * Get the list of all territories
     */
    public List<Territory> getTerritories() {
        return new ArrayList<>(_territories);
    }

    /**
     * Get the current player from game manager
     */
    public Player getCurrentPlayer() {
        return _gameManager != null ? _gameManager.getCurrentPlayer() : null;
    }

    /**
     * Set the current action type - delegato all'handler
     */
    public void setCurrentAction(GameManager.ActionType actionType) {
        if (_interactionHandler != null) {
            _interactionHandler.setCurrentAction(actionType);
        }
    }

    /**
     * Get the currently selected territory from interaction handler
     */
    public Territory getSelectedTerritory() {
        if (_interactionHandler != null) {
            return _interactionHandler.getSelectedTerritory();
        }
        return null;
    }
    private void loadTerritoryImage(String key) {
    String imagePath = String.format("resources/images/territories/%s.png", key);
    try {
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            createFallbackTerritoryImage(key);
            return;
        }
        
        BufferedImage img = ImageIO.read(imageFile);
        if (img != null) {
            _territoryImages.put(key, img);
        } else {
            System.err.println("Failed to load territory image: " + imagePath);
            createFallbackTerritoryImage(key);
        }
    } catch (IOException e) {
        System.err.println("Cannot load territory image " + imagePath + ": " + e.getMessage());
        createFallbackTerritoryImage(key);
    }
}

    /**
     * Crea un'immagine di fallback per territori mancanti
     */
    private void createFallbackTerritoryImage(String key) {
        // Estrai il colore e l'ID territorio dalla chiave
        String colorName = "grey";
        String territoryId = key;
        
        if (key.length() > 4) {
            for (String color : new String[]{"red", "blue", "green", "yellow", "cyan", "purple", "grey"}) {
                if (key.startsWith(color)) {
                    colorName = color;
                    territoryId = key.substring(color.length());
                    break;
                }
            }
        }
        
        // Crea un'immagine di fallback semplice
        BufferedImage fallbackImage = new BufferedImage(200, 150, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = fallbackImage.createGraphics();
        
        // Imposta il colore in base al nome
        Color fillColor;
        switch (colorName) {
            case "red": fillColor = Color.RED; break;
            case "blue": fillColor = Color.BLUE; break;
            case "green": fillColor = Color.GREEN; break;
            case "yellow": fillColor = Color.YELLOW; break;
            case "cyan": fillColor = Color.CYAN; break;
            case "purple": fillColor = new Color(128, 0, 128); break;
            default: fillColor = Color.GRAY;
        }
        
        // Disegna un poligono colorato con il nome del territorio
        g2d.setColor(new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), 128));
        g2d.fillRoundRect(10, 10, 180, 130, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(10, 10, 180, 130, 20, 20);
        
        // Aggiungi il nome del territorio
        g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2d.drawString("Territory: " + territoryId, 20, 75);
        g2d.dispose();
        
        _territoryImages.put(key, fallbackImage);
    }

    /**
     * Accesso all'handler per i componenti esterni
     */
    public MapInteractionHandler getInteractionHandler() {
        return _interactionHandler;
    }

    public GameManager getGameManager() {
        return _gameManager;
    }

    /**
     * Update a specific territory's image and repaint its region
     */
    public void updateTerritory(Territory territory) {
        String territoryId = territory.getName().replace(" ", "");
        String colorKey = territory.isOccupied() ? 
            getColorName(territory.getOwner().getColor()).toLowerCase() + territoryId : 
            "grey" + territoryId;
        
        // Aggiorna solo l'immagine di questo territorio
        loadTerritoryImage(colorKey);
        
        // Calcola i bounds del territorio per ridisegnare solo quella regione
        Point center = _territoryCenters.get(territory);
        repaint(center.x - 100, center.y - 100, 200, 200);
    }

    /**
     * Get the world map data
     * @return The WorldMapData instance used by this panel
     */
    public WorldMapData getWorldMapData() {
        return _worldMapData;
    }
}