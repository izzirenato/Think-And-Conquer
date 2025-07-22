package map;


import gameSetup.GameLauncher;
import gameSetup.SplashScreen;
import gameSetup.GameManager;

import troops.*;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * MapPanel is responsible for rendering the game map
 * delegates all interaction state to MapInteractionHandler.
 * If i have to be honest, this class gave me a lot of issues
 * and I had to search for a lot of solutions to make it work.
 * The methods for the click detection are not
 * entirely written by myself but I've still understood them.
 */


public class MapPanel extends JPanel implements GameLauncher.Scalable 
{
    // core data
    private BufferedImage _backgroundMap;
    private BufferedImage _clickDetectionMap;
    private Map<Color, Territory> _colorToTerritory;
    private List<Territory> _territories = new ArrayList<>();
    private WorldMapData _worldMapData;

    // Brightness calculation constants
    private static final float RED_WEIGHT = 0.3f;
    private static final float GREEN_WEIGHT = 0.6f;
    private static final float BLUE_WEIGHT = 0.1f;
    private static final float BRIGHTNESS_THRESHOLD = 0.5f;
    private static final float COLOR_SCALE = 255.0f;
    
    // image management
    private Map<String, BufferedImage> _territoryImages = new HashMap<>();
    private Map<Territory, Point> _territoryCenters = new HashMap<>();
    
    // UI properties
    private final Font _TERRITORY_FONT;
    private final GameManager _gameManager;
    private MapInteractionHandler _interactionHandler;

    // color management
    private static final ColorManager COLOR_MANAGER = new ColorManager();
    
    
    // ctor
    public MapPanel(Color[] playerColors, GameManager gameManager) 
    {
        _gameManager = gameManager;
        _TERRITORY_FONT = SplashScreen.getPromptFont();
        
        try 
        {
            // initialize world map data
            _worldMapData = new WorldMapData();
            _territories = _worldMapData.getTerritories();
            _colorToTerritory = _worldMapData.getColorToTerritoryMap();
        
            COLOR_MANAGER.initializePlayerColors(playerColors);
            loadMapImages();
            loadInitialTerritoryImages();
            calculateTerritoryCenters();
            
            setSize(new Dimension(800, 600));
            
            SwingUtilities.invokeLater(this::ensureTroopIconsLoaded);
            
        } 
        catch (Exception e) 
        {
            System.err.println("Error initializing MapPanel: " + e.getMessage());
            e.printStackTrace();
            createFallbackSetup();
        }
    }


    // create fallback setup in case of errors
    private void createFallbackSetup() 
    {
        _territories = new ArrayList<>();
        _colorToTerritory = new HashMap<>();
        createFallbackMapImages();
        setSize(new Dimension(800, 600));
    }


    // set the interaction handler  
    public void setInteractionHandler(MapInteractionHandler handler) 
    {
        for (java.awt.event.MouseListener listener : getMouseListeners()) 
        {
            removeMouseListener(listener);
        }
        
        _interactionHandler = handler;
        
        if (handler != null) 
        {
            addMouseListener(new java.awt.event.MouseAdapter() 
            {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) 
                {
                    if (_interactionHandler != null) 
                    {
                        _interactionHandler.handleClick(e);
                    }
                }
            });
        }
    }
    
    
    // search the territory clicked
    public Territory getTerritoryAtPoint(Point point) 
    {
        if (_clickDetectionMap == null || point == null) return null;
        
        Point scaledPoint = scalePointToDetectionMap(point);
        if (!isValidPoint(scaledPoint)) return null;
        
        Color exactColor = sampleColorAtPoint(scaledPoint);
        Territory exactMatch = _colorToTerritory.get(exactColor);
        if (exactMatch != null) return exactMatch;
        
        // fallback to proximity search
        return findTerritoryByColorProximity(exactColor);
    }
    

    // helper methods for point scaling and validation
    private Point scalePointToDetectionMap(Point point) 
    {
        float scaleX = (float) _clickDetectionMap.getWidth() / getWidth();
        float scaleY = (float) _clickDetectionMap.getHeight() / getHeight();
        return new Point((int)(point.x * scaleX), (int)(point.y * scaleY));
    }

    private boolean isValidPoint(Point point) 
    {
        return point.x >= 0 && point.y >= 0 && 
               point.x < _clickDetectionMap.getWidth() && 
               point.y < _clickDetectionMap.getHeight();
    }
    
    private Color sampleColorAtPoint(Point point) 
    {
        // sample around the point to find the most common significant color
        Map<Integer, Integer> colorCounts = new HashMap<>();
        int radius = 2;
        
        for (int y = point.y - radius; y <= point.y + radius; y++) 
        {
            for (int x = point.x - radius; x <= point.x + radius; x++) 
            {
                if (x >= 0 && x < _clickDetectionMap.getWidth() && 
                    y >= 0 && y < _clickDetectionMap.getHeight()) 
                    {
                    
                    int rgb = _clickDetectionMap.getRGB(x, y);
                    if (isSignificantColor(rgb)) 
                    {
                        colorCounts.put(rgb, colorCounts.getOrDefault(rgb, 0) + 1);
                    }
                }
            }
        }
        
        // returns the most common color found
        return colorCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> new Color(entry.getKey()))
                .orElse(new Color(_clickDetectionMap.getRGB(point.x, point.y)));
    }
    
    // check if the color is not too transparent and not white/black
    private boolean isSignificantColor(int rgb) 
    {
        int alpha = (rgb >> 24) & 0xff;
        if (alpha < 50) return false;
        
        Color color = new Color(rgb, true);
        int r = color.getRed();
        int g = color.getGreen(); 
        int b = color.getBlue();
        
        // filter out white/black backgrounds
        return !(r > 240 && g > 240 && b > 240) && !(r < 15 && g < 15 && b < 15);
    }
    
    // find the territory closest to the clicked color
    private Territory findTerritoryByColorProximity(Color targetColor) 
    {
        return _colorToTerritory.entrySet().stream()
                .min((e1, e2) -> 
                {
                    float dist1 = calculateColorDistance(targetColor, e1.getKey());
                    float dist2 = calculateColorDistance(targetColor, e2.getKey());
                    return Float.compare(dist1, dist2);
                })
                .filter(entry -> calculateColorDistance(targetColor, entry.getKey()) <= 50)
                .map(Map.Entry::getValue)
                .orElse(null);
    }
    
    private float calculateColorDistance(Color c1, Color c2) 
    {
        // perceptual color distance
        float rMean = (c1.getRed() + c2.getRed()) / 2.0f;
        float deltaR = c1.getRed() - c2.getRed();
        float deltaG = c1.getGreen() - c2.getGreen();
        float deltaB = c1.getBlue() - c2.getBlue();
        
        float weightR = 2 + rMean / 256;
        float weightG = 4;
        float weightB = 2 + (255 - rMean) / 256;
        
        return (float) Math.sqrt(weightR * deltaR * deltaR + 
                        weightG * deltaG * deltaG + 
                        weightB * deltaB * deltaB);
    }
    

    // loads the map images from files
    private void loadMapImages() 
    {
        try 
        {
            _backgroundMap = loadImageFile("resources/images/worldMap.png");
            _clickDetectionMap = loadImageFile("resources/images/worldMapClickDetection.png");
            
            if (_backgroundMap == null || _clickDetectionMap == null)
            {
                createFallbackMapImages();
            }
        } 
        catch (Exception e) 
        {
            System.err.println("Error loading map images: " + e.getMessage());
            createFallbackMapImages();
        }
    }
    

    // loads an image file and returns it as BufferedImage
    private BufferedImage loadImageFile(String path) throws IOException 
    {
        File file = new File(path);
        if (!file.exists()) 
        {
            System.err.println("Image file not found: " + path);
            return null;
        }
        return ImageIO.read(file);
    }
    

    // creates fallback map images in case the original ones cannot be loaded
    private void createFallbackMapImages() 
    {
        System.err.println("Creating fallback map images");
        
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
    

    // loads the initial territory images
    private void loadInitialTerritoryImages() 
    {
        if (_territories == null || _territories.isEmpty()) 
        {
            System.err.println("Warning: No territories to load images for");
            return;
        }
        
        System.out.println("Loading initial territory images...");
        
        // load default grey images for all territories
        for (Territory territory : _territories) 
        {
            if (territory == null) {continue;}
            
            String territoryId = COLOR_MANAGER.formatTerritoryName(territory.getName());
            loadTerritoryImageVariant("grey" + territoryId);
        }
        
        System.out.println("Initial territory images loaded successfully");
    }
    

    // loads a specific territory image color variant by key
    private void loadTerritoryImageVariant(String key) 
    {
        if (_territoryImages.containsKey(key)) return;
        
        String imagePath = "resources/images/territories/" + key + ".png";
        try 
        {
            BufferedImage image = loadImageFile(imagePath);
            if (image != null) {_territoryImages.put(key, image);} 
            else {createFallbackTerritoryImage(key);}
        } 
        catch (IOException e) 
        {
            System.err.println("Cannot load territory image: " + imagePath);
            createFallbackTerritoryImage(key);
        }
    }
    

    // creates a fallback territory image with basic information (in reality is not very useful, it's just to notice that the load didn't work)
    private void createFallbackTerritoryImage(String key) 
    {
        String[] parts = key.split("(?=[A-Z])");
        String colorName = parts[0];
        String territoryId = parts.length > 1 ? parts[1] : "Unknown";
        
        BufferedImage fallbackImage = new BufferedImage(200, 150, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = fallbackImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Color fillColor = COLOR_MANAGER.getColorFromName(colorName);
        g2d.setColor(new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), 128));
        g2d.fillRoundRect(10, 10, 180, 130, 20, 20);
        
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(10, 10, 180, 130, 20, 20);
        
        g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Territory: " + territoryId, 20, 75);
        
        g2d.dispose();
        _territoryImages.put(key, fallbackImage);
    }
    
    
    // calculates the center of each territory based on the click detection map
    private void calculateTerritoryCenters() 
    {
        if (_clickDetectionMap == null) return;
        
        for (Map.Entry<Color, Territory> entry : _colorToTerritory.entrySet()) 
        {
            Point center = calculateTerritoryCenter(entry.getKey());
            if (center != null) 
            {
                _territoryCenters.put(entry.getValue(), center);
            }
        }
    }
    

    // calculates the center of mass for a territory based on its color in the click detection map
    private Point calculateTerritoryCenter(Color territoryColor) 
    {
        long sumX = 0, sumY = 0;
        int count = 0;
        int samplingFactor = 4;
        
        // calculate center of mass with sampling
        for (int y = 0; y < _clickDetectionMap.getHeight(); y += samplingFactor) 
        {
            for (int x = 0; x < _clickDetectionMap.getWidth(); x += samplingFactor) 
            {
                Color pixelColor = new Color(_clickDetectionMap.getRGB(x, y));
                if (pixelColor.equals(territoryColor)) 
                {
                    sumX += x;
                    sumY += y;
                    count++;
                }
            }
        }
        
        if (count == 0) return null;
        Point center = new Point((int)(sumX / count), (int)(sumY / count));
        
        // validate center is actually on territory
        if (center.x >= 0 && center.x < _clickDetectionMap.getWidth() &&
            center.y >= 0 && center.y < _clickDetectionMap.getHeight()) 
        {
            Color centerColor = new Color(_clickDetectionMap.getRGB(center.x, center.y));
            if (!centerColor.equals(territoryColor)) 
            {
                center = findNearestValidCenter(center, territoryColor);
            }
        }
        
        return center;
    }
    

    // finds the nearest valid center for a territory if the calculated one is not valid
    private Point findNearestValidCenter(Point approximateCenter, Color targetColor) 
    {
        int maxRadius = 50;
        
        for (int radius = 1; radius <= maxRadius; radius++) 
        {
            for (int angle = 0; angle < 360; angle += 45)
             {
                float radians = (float) Math.toRadians(angle);
                int x = approximateCenter.x + (int)(radius * Math.cos(radians));
                int y = approximateCenter.y + (int)(radius * Math.sin(radians));
                
                if (x >= 0 && x < _clickDetectionMap.getWidth() &&
                    y >= 0 && y < _clickDetectionMap.getHeight()) 
                {
                    
                    Color testColor = new Color(_clickDetectionMap.getRGB(x, y));
                    if (testColor.equals(targetColor)) 
                    {
                        return new Point(x, y);
                    }
                }
            }
        }
        // fallback
        return approximateCenter;
    }
    
    
    // custom paint component    
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // draw background
        if (_backgroundMap != null) 
        {
            g2d.drawImage(_backgroundMap, 0, 0, getWidth(), getHeight(), this);
        }
        
        drawTerritories(g2d);
        drawInteractionHighlights(g2d);
        drawAllTroopCounts(g2d);
    }
    

    // draws all territories on the map
    private void drawTerritories(Graphics2D g2d) 
    {
        if (_territories == null) {return;}
        
        for (Territory territory : _territories) 
        {
            if (territory == null) {continue;}
            
            String imageKey = COLOR_MANAGER.getTerritoryImageKey(territory);
            BufferedImage territoryImage = _territoryImages.get(imageKey);
            
            if (territoryImage != null) 
            {
                g2d.drawImage(territoryImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
    

    // draws interaction highlights based on the current interaction state
    private void drawInteractionHighlights(Graphics2D g2d) 
    {
        if (_interactionHandler == null) {return;}

        Territory selectedTerritory = _interactionHandler.getSelectedTerritory();
        Territory sourceTerritory = _interactionHandler.getSourceTerritory();
        List<Territory> highlightedTerritories = _interactionHandler.getHighlightedTerritories();
        Territory justConquered = _interactionHandler.getJustConquered();
        GameManager.ActionType currentAction = _interactionHandler.getCurrentAction();
        
        // draw source territory
        if (sourceTerritory != null && currentAction != GameManager.ActionType.NONE) 
        {
            highlightTerritory(g2d, sourceTerritory, GameManager.ActionType.NONE);
        }
        
        // draw highlighted territories - CORREZIONE: usa solo getBlinkState()
        if (highlightedTerritories != null && !highlightedTerritories.isEmpty()) 
        {
            for (Territory territory : highlightedTerritories) 
            {
                if (_interactionHandler.getBlinkState())
                {
                    highlightTerritory(g2d, territory, currentAction);
                }
            }
        }
        
        // draw selected territory
        if (currentAction == GameManager.ActionType.NONE && selectedTerritory != null) 
        {
            if (_interactionHandler.getBlinkState()) 
            {
                highlightTerritory(g2d, selectedTerritory, GameManager.ActionType.NONE);
            }
        }
        
        // draw just conquered
        if (justConquered != null) 
        {
            drawConqueredTerritory(g2d, justConquered);
        }
    }
    

    // highlights a territory based on the action type
    private void highlightTerritory(Graphics2D g2d, Territory territory, GameManager.ActionType actionType) 
    {
        String imageKey = COLOR_MANAGER.getTerritoryImageKey(territory);
        BufferedImage mask = _territoryImages.get(imageKey);
        if (mask == null) {return;}

        Color highlightColor = getHighlightColor(actionType);
        
        Composite originalComposite = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        
        BufferedImage coloredMask = createColoredMask(mask, highlightColor);
        g2d.drawImage(coloredMask, 0, 0, getWidth(), getHeight(), null);
        
        g2d.setComposite(originalComposite);
    }
    

    // returns the highlight color based on the action type
    private Color getHighlightColor(GameManager.ActionType actionType) 
    {
        return switch (actionType) 
        {
            case MOVE -> new Color(0, 255, 0, 180);
            case ATTACK -> new Color(255, 0, 0, 180);
            default -> new Color(255, 255, 255, 180);
        };
    }
    

    // creates a colored mask for the territory image
    private BufferedImage createColoredMask(BufferedImage mask, Color color) 
    {
        BufferedImage coloredMask = new BufferedImage(mask.getWidth(), mask.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = coloredMask.createGraphics();
        g2d.drawImage(mask, 0, 0, null);
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.setColor(color);
        g2d.fillRect(0, 0, mask.getWidth(), mask.getHeight());
        g2d.dispose();
        return coloredMask;
    }
    

    // draws the territory that has just been conquered
    private void drawConqueredTerritory(Graphics2D g2d, Territory territory) 
    {
        String imageKey = COLOR_MANAGER.getTerritoryImageKey(territory);
        BufferedImage territoryImage = _territoryImages.get(imageKey);
        
        if (territoryImage != null) 
        {
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2d.drawImage(territoryImage, 0, 0, getWidth(), getHeight(), this);
            g2d.setComposite(originalComposite);
        }
    }
    

    // draws troop counts for all occupied territories
    private void drawAllTroopCounts(Graphics2D g2d) 
    {
        for (Territory territory : _territories) 
        {
            if (territory.isOccupied()) 
            {
                drawTroopCount(g2d, territory);
            }
        }
    }
    

    // draws the troop count circles for a specific territory
    private void drawTroopCount(Graphics2D g2d, Territory territory) 
    {
        if (!territory.isOccupied() || territory.getOwner() == null) {return;}

        Point scaledCenter = getScaledTerritoryCenter(territory);
        if (scaledCenter == null) {return;}
        
        // get troops present
        List<String> troopTypesPresent = getTroopTypesPresent(territory);
        if (troopTypesPresent.isEmpty()) {return;}

        // draw troop circles
        drawTroopCirclesRow(g2d, territory, troopTypesPresent, scaledCenter);
    }


    // helper method to get scaled territory center
    private Point getScaledTerritoryCenter(Territory territory) 
    {
        Point originalCenter = _territoryCenters.get(territory);
        if (originalCenter == null) return null;

        float scaleX = (float) getWidth() / _clickDetectionMap.getWidth();
        float scaleY = (float) getHeight() / _clickDetectionMap.getHeight();
        return new Point((int) (originalCenter.x * scaleX), (int) (originalCenter.y * scaleY));
    }


    // helper method to draw a row of troop circles
    private void drawTroopCirclesRow(Graphics2D g2d, Territory territory, List<String> troopTypes, Point center) 
    {
        int circleSize = 30;
        int padding = 20;
        int totalWidth = troopTypes.size() * (circleSize + padding) - padding;
        int startX = center.x - (totalWidth / 2);
        
        for (int i = 0; i < troopTypes.size(); i++) 
        {
            String troopType = troopTypes.get(i);
            int count = territory.getTroopCount(troopType);
            int x = startX + (i * (circleSize + padding)) + (circleSize / 2);
            
            drawTroopCircle(g2d, territory, troopType, count, x, center.y, circleSize);
        }
    }
    

    // gets the list of troop types present in a territory
    private List<String> getTroopTypesPresent(Territory territory) 
    {
        List<String> troopTypesPresent = new ArrayList<>();
        Troop[] allTroops = TroopFactory.getAllTroops();
        
        for (Troop troop : allTroops) 
        {
            String troopType = troop.getName();
            if (territory.getTroopCount(troopType) > 0) 
            {
                troopTypesPresent.add(troopType);
            }
        }
        
        return troopTypesPresent;
    }
    

    // draws a troop circle with the troop icon and count
    private void drawTroopCircle(Graphics2D g2d, Territory territory, String troopType, int count, int x, int y, int circleSize) 
    {
        Color ownerColor = territory.getOwner().getColor();
        Color textColor = getContrastColor(ownerColor);
        
        // draw outer circle
        g2d.setColor(ownerColor);
        g2d.fillOval(x - circleSize/2, y - circleSize/2, circleSize, circleSize);
        
        // draw inner circle
        g2d.setColor(textColor);
        int innerSize = circleSize - 6;
        g2d.fillOval(x - innerSize/2, y - innerSize/2, innerSize, innerSize);
        
        // draw border
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawOval(x - circleSize/2, y - circleSize/2, circleSize, circleSize);
        
        // draw troop icon
        BufferedImage troopIcon = TroopFactory.getTroopIcon(troopType);
        if (troopIcon != null) 
        {
            int iconSize = 20;
            g2d.drawImage(troopIcon, x - iconSize/2, y - iconSize/2, iconSize, iconSize, null);
        } 
        else 
        {
            // fallback: draw letter
            g2d.setColor(ownerColor.darker());
            g2d.setFont(_TERRITORY_FONT.deriveFont(14f));
            String letter = troopType.substring(0, 1);
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(letter, x - fm.stringWidth(letter)/2, y + fm.getHeight()/4);
        }

        // draw count
        g2d.setColor(textColor);
        g2d.setFont(_TERRITORY_FONT.deriveFont(12f));
        String countText = String.valueOf(count);
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(countText, x - fm.stringWidth(countText)/2, y + circleSize/2 + 12);
    }


    // returns a contrasting color for the given background color
    private Color getContrastColor(Color backgroundColor) 
    {
        float brightness = (backgroundColor.getRed() * RED_WEIGHT + 
                           backgroundColor.getGreen() * GREEN_WEIGHT + 
                           backgroundColor.getBlue() * BLUE_WEIGHT) / COLOR_SCALE;
        
        return brightness > BRIGHTNESS_THRESHOLD ? Color.BLACK : Color.WHITE;
    }
    
    
    // ensures that troop icons are loaded at startup
    private void ensureTroopIconsLoaded() 
    {
        Troop[] troops = TroopFactory.getAllTroops();
        for (Troop troop : troops) 
        {
            BufferedImage icon = TroopFactory.getTroopIcon(troop.getName());
            if (icon == null) 
            {
                System.err.println("Warning: Could not load icon for " + troop.getName());
            }
        }
    }
    
    
    // scales the map panel to the specified width and height
    @Override
    public void scale(int width, int height) 
    {
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        
        SwingUtilities.invokeLater(() -> {
            repaint();
        });
    }
    
    
    // getters and setters
    public List<Territory> getTerritories() {return new ArrayList<>(_territories);}
    public MapInteractionHandler getInteractionHandler() {return _interactionHandler;}
    public GameManager getGameManager() {return _gameManager;}
    public WorldMapData getWorldMapData() {return _worldMapData;}

    public void setCurrentAction(GameManager.ActionType actionType) 
    {
        if (_interactionHandler != null) {
            _interactionHandler.setCurrentAction(actionType);
        }
    }
    
    public Territory getSelectedTerritory() 
    {
        return _interactionHandler != null ? _interactionHandler.getSelectedTerritory() : null;
    }
    
     
    // logging method to update the territory
    public void updateTerritory(Territory territory) 
    {
        if (territory == null) return;
        
        String imageKey = COLOR_MANAGER.getTerritoryImageKey(territory);
        loadTerritoryImageVariant(imageKey);
        
        Point center = _territoryCenters.get(territory);
        if (center != null) 
        {
            float scaleX = getWidth() / _clickDetectionMap.getWidth();
            float scaleY = getHeight() / _clickDetectionMap.getHeight();
            int scaledX = (int) (center.x * scaleX);
            int scaledY = (int) (center.y * scaleY);
            
            int repaintSize = 150;
            repaint(scaledX - repaintSize/2, scaledY - repaintSize/2, repaintSize, repaintSize);
        } 
        else 
        {
            repaint();
        }
    }
    

    // inner class for managing colors and territory images
    private static class ColorManager 
    {
        private final Map<Color, String> colorToName = new HashMap<>();
        private final Map<String, Color> nameToColor = new HashMap<>();
        
        public ColorManager() 
        {
            addColorMapping(Color.RED, "red");
            addColorMapping(Color.BLUE, "blue");
            addColorMapping(Color.GREEN, "green");
            addColorMapping(Color.YELLOW, "yellow");
            addColorMapping(Color.CYAN, "cyan");
            addColorMapping(new Color(128, 0, 128), "purple");
            addColorMapping(Color.GRAY, "grey");
        }
        
        private void addColorMapping(Color color, String name) 
        {
            colorToName.put(color, name.toLowerCase());
            nameToColor.put(name.toLowerCase(), color);
        }
        
        public void initializePlayerColors(Color[] playerColors) 
        {
            if (playerColors == null) return;
            
            // Ensure all player colors are mapped
            for (int i = 0; i < playerColors.length; i++) 
            {
                Color color = playerColors[i];
                if (color != null && !colorToName.containsKey(color)) 
                {
                    String genericName = "player" + (i + 1);
                    addColorMapping(color, genericName);
                }
            }
        }
        
        public String getColorName(Color color) 
        {
            if (color == null) return "grey";
            return colorToName.getOrDefault(color, "grey");
        }
        
        public Color getColorFromName(String name) 
        {
            if (name == null) return Color.GRAY;
            return nameToColor.getOrDefault(name.toLowerCase(), Color.GRAY);
        }
        
        public String getTerritoryImageKey(Territory territory) 
        {
            if (territory == null) return "greyUnknown";
            
            String territoryId = formatTerritoryName(territory.getName());
            if (territory.isOccupied() && territory.getOwner() != null) 
            {
                String colorName = getColorName(territory.getOwner().getColor());
                return colorName.toLowerCase() + territoryId;
            } 
            else 
            {
                return "grey" + territoryId;
            }
        }
        
        public String formatTerritoryName(String territoryName) 
        {
            if (territoryName == null) return "Unknown";
            return territoryName.replaceAll("\\s+", "");
        }
    }
}