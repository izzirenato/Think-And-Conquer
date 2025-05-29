package gameSetup;


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import map.*;
import troops.TroopFactory;


/*
 * GameActionPanel serves as the control panel for the game interface.
 * It displays action buttons, and manages action overlays
 * for deploying, moving, and attacking with troops.
 */


public class GameActionPanel extends JPanel implements GameLauncher.Scalable 
{
    // constants for panel dimensions
    private static final int CONTROL_PANEL_HEIGHT = 75;
    private static final int OVERLAY_WIDTH = 600;
    private static final int OVERLAY_HEIGHT = 525;
    
    // core game references
    private final GameManager _gameManager;
    private final JFrame _parentFrame;
    private final MapPanel _mapPanel;
    
    // action state
    private Territory _selectedTerritory;
    private String _currentAction;
    
    // UI Components
    private JLabel _currentPlayerLabel;
    private JButton _endTurnButton;
    private JButton _deployButton;
    private JButton _moveButton;
    private JButton _attackButton;
    private JPanel _actionOverlay;
    private JPanel _blockingPanel;
    
    // fonts
    private final Font _controlFont;
    private final Font _titleFont;
    private final Font _buttonFont;


    // ctor
    public GameActionPanel(GameManager gameManager, JFrame frame, MapPanel mapPanel) 
    {
        _gameManager = gameManager;
        _parentFrame = frame;
        _mapPanel = mapPanel;
        _controlFont = UIStyleUtils.PROMPT_FONT.deriveFont(32f);
        _titleFont = UIStyleUtils.TITLE_FONT;
        _buttonFont = UIStyleUtils.PROMPT_FONT;
        
        initializePanelProperties();
        initializeUIComponents();
        initializeActionListeners();
        initializeOverlay();
        scale(frame.getWidth(), CONTROL_PANEL_HEIGHT);
        hideEverything();
        updatePlayerInfo();


        SwingUtilities.invokeLater(() -> 
        {
            scale(frame.getWidth(), CONTROL_PANEL_HEIGHT);
            this.requestFocusInWindow();
            this.revalidate();
            this.repaint();
        });
        
        addResizeListener();
    }


    // initializes panel properties like background color and border
    private void initializePanelProperties() 
    {
        setBackground(UIStyleUtils.BUTTON_BORDER_COLOR);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
    }


    // creates and initializes all UI components
    private void initializeUIComponents() 
    {
        setLayout(null);
        
        _currentPlayerLabel = UIStyleUtils.createStyledLabel("", _controlFont.deriveFont(42f));
        _moveButton = UIStyleUtils.createStyledButton("Move");
        _attackButton = UIStyleUtils.createStyledButton("Attack");
        _deployButton = UIStyleUtils.createStyledButton("Deploy");
        _endTurnButton = UIStyleUtils.createStyledButton("End Turn");
        
        // Initially hide action buttons, they will be shown only whenever the player clicks on his territory
        _moveButton.setVisible(false);
        _attackButton.setVisible(false);
        _deployButton.setVisible(false);

        // Add components to panel, the positions will be set in scale() method
        add(_currentPlayerLabel);
        add(_moveButton);
        add(_attackButton);
        add(_deployButton);
        add(_endTurnButton);
    }


    // adds a listener to handle frame resize events
    private void addResizeListener() 
    {
        _parentFrame.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e) 
            {
                scale(_parentFrame.getWidth(), CONTROL_PANEL_HEIGHT);
                repositionOverlay();
            }
        });
    }


    // sets up action listeners for all buttons
    private void initializeActionListeners() 
    {
        _endTurnButton.addActionListener(_ -> handleEndTurn());
        
        _deployButton.addActionListener(_ -> openOverlay("Deploy Troops", false));
        
        _moveButton.addActionListener(_ -> 
        {
            Territory selected = _mapPanel.getSelectedTerritory();
            if (selected != null && selected.hasMovableTroops()) 
            {
                openOverlay("Move Troops", true);
            }
        });
        
        _attackButton.addActionListener(_ -> 
        {
            Territory selected = _mapPanel.getSelectedTerritory();
            if (selected != null && selected.hasAttackableTroops()) 
            {
                openOverlay("Attack With", false);
            }
        });
    }


    // handles the end turn button action
    private void handleEndTurn() 
    {
        if (_gameManager != null) 
        {
            _gameManager.nextTurn();
            updatePlayerInfo();
        }
    }


    // Initializes the modal overlay used for troop actions
    private void initializeOverlay() 
    {
        // create overlay panel with semi-transparent background
        _actionOverlay = new JPanel() 
        {
            @Override
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                g.setColor(new Color(30, 30, 60, 180));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g.setColor(new Color(139, 69, 19, 120));
                ((Graphics2D)g).setStroke(new BasicStroke(2));
                g.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 20, 20);
            }
        };
        _actionOverlay.setOpaque(false);
        _actionOverlay.setLayout(new BorderLayout());
        _actionOverlay.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        _actionOverlay.setVisible(false);
        
        // create a blocking panel that will prevent clicks from passing through
        // didn't use GlassPane because I was having issues with mouse events
        _blockingPanel = new JPanel();
        _blockingPanel.setOpaque(false);
        _blockingPanel.setVisible(false);
        
        // add mouse listener to block all mouse events
        _blockingPanel.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) {e.consume();}
            
            @Override
            public void mousePressed(MouseEvent e) 
            {
                if (!_actionOverlay.getBounds().contains(e.getPoint())) 
                {
                    hideOverlay();
                }
                e.consume();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) 
            {
                e.consume();
            }
        });
        
        _parentFrame.getLayeredPane().add(_blockingPanel, JLayeredPane.MODAL_LAYER - 1);
        _parentFrame.getLayeredPane().add(_actionOverlay, JLayeredPane.MODAL_LAYER);
    }


    // repositions the overlay to be centered on the map panel (really useful if the StatsPanel is open)
    private void repositionOverlay() 
    {
        // Get MapPanel's position and dimensions relative to the frame
        Point mapLocation = SwingUtilities.convertPoint(_mapPanel, 0, 0, _parentFrame.getContentPane());
        int mapWidth = _mapPanel.getWidth();
        int mapHeight = _mapPanel.getHeight();
        
        // Calculate center of the map panel
        int mapCenterX = mapLocation.x + mapWidth / 2;
        int mapCenterY = mapLocation.y + mapHeight / 2;
        
        // Shift the vertical position down by a small percentage of the map height
        float verticalShiftPercentage = 0.07f;
        int verticalShift = (int)(mapHeight * verticalShiftPercentage);
        
        // Position overlay with its center at the map's center, but shifted down
        int x = mapCenterX - OVERLAY_WIDTH / 2;
        int y = mapCenterY - OVERLAY_HEIGHT / 2 + verticalShift;
        
        _actionOverlay.setBounds(x, y, OVERLAY_WIDTH, OVERLAY_HEIGHT);
        
        // Make blocking panel cover the entire layered pane area
        _blockingPanel.setBounds(0, 0, _parentFrame.getWidth(), _parentFrame.getHeight());
    }


    // creates a UI row for selecting troop quantities
    private JPanel createTroopRow(String troopType, int available, Map<String, JSlider> sliders) 
    {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 5));
        rowPanel.setOpaque(false);

        // troop type label
        JLabel typeLabel = new JLabel(troopType + ":");
        typeLabel.setFont(_buttonFont.deriveFont(26f));
        typeLabel.setForeground(Color.WHITE);
        typeLabel.setPreferredSize(new Dimension(120, 36)); 
        rowPanel.add(typeLabel);

        // troop icon
        JPanel iconPanel = new JPanel() 
        {
            @Override protected void paintComponent(Graphics g) 
            {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // White circular background
                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, getWidth(), getHeight());
                // Brown border
                g2.setColor(UIStyleUtils.BUTTON_COLOR);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(0, 0, getWidth()-1, getHeight()-1);
                g2.dispose();
            }
        };
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(32, 32));
        ImageIcon orig = new ImageIcon(TroopFactory.getTroopIcon(troopType));
        Image img = orig.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
        iconPanel.setLayout(new GridBagLayout());
        iconPanel.add(new JLabel(new ImageIcon(img)));
        rowPanel.add(iconPanel);

        // slider for troop quantity
        JSlider slider = new JSlider(0, available, 0);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(Math.max(1, available/5));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setOpaque(false);
        slider.setForeground(Color.WHITE);
        slider.setFont(_buttonFont.deriveFont(16f));
        slider.setPreferredSize(new Dimension(200, 40));
        
        // apply custom UI for the slider (maybe this should be moved to UIStyleUtils))
        slider.setUI(new javax.swing.plaf.basic.BasicSliderUI(slider) 
        {
            @Override
            public void paintThumb(Graphics g) 
            {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(UIStyleUtils.GOLDEN_COLOR);
                g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
                g2d.setColor(UIStyleUtils.BUTTON_COLOR);
                g2d.drawOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
            }

            @Override
            public void paintTrack(Graphics g) 
            {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(100, 100, 150));
                g2d.fillRoundRect(trackRect.x, trackRect.y + 5, trackRect.width, trackRect.height - 1, 5, 5);
            }
        });
        
        rowPanel.add(slider);
        sliders.put(troopType, slider);
        
        return rowPanel;
    }

    
    // opens the action overlay
    private void openOverlay(String title, boolean isMove) 
    {
        _selectedTerritory = _mapPanel.getSelectedTerritory();
        if (_selectedTerritory == null) return;
        
        // Set the current action based on the overlay title
        if (title.contains("Deploy")) {_currentAction = "deploy";} 
        else if (title.contains("Move")) {_currentAction = "move";} 
        else {_currentAction = "attack";}
        
        // Hide action buttons to prevent unintended interactions
        _moveButton.setVisible(false);
        _attackButton.setVisible(false);
        _deployButton.setVisible(false);
        _endTurnButton.setVisible(false);
        
        buildOverlayContents(title, isMove);
        repositionOverlay();
        
        _blockingPanel.setVisible(true);
        _actionOverlay.setVisible(true);
    }

    
    // hides the action overlay and restores action buttons
    private void hideOverlay() 
    {
        _actionOverlay.setVisible(false);
        _blockingPanel.setVisible(false);

        showEndTurnButton();
        Territory t = _mapPanel.getSelectedTerritory();
        if (t != null && t.getOwner() == _gameManager.getCurrentPlayer()) 
        {
            Player currentPlayer = _gameManager.getCurrentPlayer();
            boolean hasTroopsToDeploy = !currentPlayer.getAvailableTroopsMap().isEmpty();
            _deployButton.setVisible(hasTroopsToDeploy);
            
            _moveButton.setVisible(t.isOccupied());
            _attackButton.setVisible(t.isOccupied());
        }
    }


    // builds the overlay contents based on the action type
    private void buildOverlayContents(String title, boolean isMove) 
    {
        _actionOverlay.removeAll();
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(_titleFont);
        titleLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPanel.setLayout(new BorderLayout(0, 10));
        
        JPanel troopSelectionPanel = new JPanel();
        troopSelectionPanel.setOpaque(false);
        troopSelectionPanel.setLayout(new BoxLayout(troopSelectionPanel, BoxLayout.Y_AXIS));
        
        Map<String, JSlider> troopSliders = new HashMap<>();
        
        Player currentPlayer = _gameManager.getCurrentPlayer();
        Territory selectedTerritory = _mapPanel.getSelectedTerritory();
        if (selectedTerritory != null) 
        {
            Map<String, Integer> availableTroops;

            if (title.contains("Deploy")) 
            {
                availableTroops = currentPlayer.getAvailableTroopsMap();
            } 
            else 
            {
                availableTroops = selectedTerritory.getAvailableTroopsForAction();
            }
            
            // Create sliders for each troop type
            for (Map.Entry<String, Integer> entry : availableTroops.entrySet()) 
            {
                if (entry.getValue() > 0) 
                {
                    JPanel troopRow = createTroopRow(entry.getKey(), entry.getValue(), troopSliders);
                    troopSelectionPanel.add(troopRow);
                }
            }
        }
        
        contentPanel.add(troopSelectionPanel, BorderLayout.CENTER);
        _actionOverlay.add(titleLabel, BorderLayout.NORTH);
        _actionOverlay.add(contentPanel, BorderLayout.CENTER);

        // Button section
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 8));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        JButton confirm = UIStyleUtils.createStyledButton("Confirm");
        confirm.addActionListener(_ -> handleConfirm(troopSliders));
        JButton cancel = UIStyleUtils.createStyledButton("Cancel");
        cancel.addActionListener(_ -> hideOverlay());
        buttonPanel.add(confirm);
        buttonPanel.add(cancel);
        _actionOverlay.add(buttonPanel, BorderLayout.SOUTH);
    }


    // handles the confirm button passing the responsaibility to GameActionHandler
    private void handleConfirm(Map<String, JSlider> sliders) 
    {
        GameActionHandler actionHandler = _gameManager.getActionHandler();
        
        // Create troops HashMap from sliders
        Map<String, Integer> selectedTroops = new HashMap<>();
        sliders.forEach((troopType, slider) -> 
        {
            int quantity = slider.getValue();
            if (quantity > 0) 
            {
                selectedTroops.put(troopType, quantity);
            }
        });
        
        // equivalent to cancel
        if (selectedTroops.isEmpty()) 
        {
            hideOverlay();
        }


        if (_currentAction == "deploy") 
        {
            actionHandler.deployTroops(_selectedTerritory, selectedTroops);
        }
        else
        {
            GameManager.ActionType actionType = _currentAction.equals("move") ? 
                GameManager.ActionType.MOVE : GameManager.ActionType.ATTACK;
            actionHandler.prepareActions(_selectedTerritory, selectedTroops, actionType);
        }
        hideOverlay();
    }
    

    // hides all action buttons and overlays, used in ctor
    public void hideEverything() 
    {
        _moveButton.setVisible(false);
        _attackButton.setVisible(false);
        _deployButton.setVisible(false);
        _actionOverlay.setVisible(false);
        _parentFrame.getGlassPane().setVisible(false);
        _endTurnButton.setVisible(true);
    }


    // updates the label showing the current player's name and color
    public void updatePlayerInfo() 
    {
        Player p = _gameManager.getCurrentPlayer();
        if (p != null) 
        {
            _currentPlayerLabel.setText(p.getName() + "'s turn");
            _currentPlayerLabel.setForeground(p.getColor());
        }

        SwingUtilities.invokeLater(() -> 
        {
            revalidate();
            repaint();
            scale(_parentFrame.getWidth(), CONTROL_PANEL_HEIGHT);
        });
    }
    

    // update action buttons based on territory selection and available actions
    public void updateButtonsForSelectedTerritory(Territory territory) 
    {
        // First hide all buttons
        hideActionButtons();
        
        // Exit early if territory is null or not owned by current player
        if (territory == null || territory.getOwner() != _gameManager.getCurrentPlayer()) 
        {
            return;
        }
        
        Player currentPlayer = _gameManager.getCurrentPlayer();
        
        // DEPLOY: Show only if current player has troops to deploy
        boolean hasTroopsToDeploy = !currentPlayer.getAvailableTroopsMap().isEmpty();
        _deployButton.setVisible(hasTroopsToDeploy);
        
        // Only continue checking other buttons if territory has troops
        if (!territory.isOccupied()) {return;}

        // MOVE: Show only if territory has troops that could move and a friendly territory neighbor
        boolean hasMovableTroops = territory.hasMovableTroops();
        List<Territory> moveTargets = _gameManager.getValidMoveTargets(territory);
        boolean canMove = hasMovableTroops && !moveTargets.isEmpty();
        _moveButton.setVisible(canMove);
        
        // ATTACK: Show only if territory has troops that could move and a foe territory neighbor
        boolean hasAttackableTroops = territory.hasAttackableTroops();
        List<Territory> attackTargets = _gameManager.getValidAttackTargets(territory);
        boolean canAttack = hasAttackableTroops && !attackTargets.isEmpty();
        _attackButton.setVisible(canAttack);
    }
   
    
    // helper methods for button visibility, public 
    public void hideActionButtons() 
    {
        _moveButton.setVisible(false);
        _attackButton.setVisible(false);
        _deployButton.setVisible(false);
    }
    

    // public method to show the End Turn button
    public void showEndTurnButton() {_endTurnButton.setVisible(true);}


    // scales the panel and repositions components based on size
    @Override
    public void scale(int w, int h) 
    {
        setPreferredSize(new Dimension(w, CONTROL_PANEL_HEIGHT));

        int margin = 20;
        int centerY = CONTROL_PANEL_HEIGHT / 2;
        int buttonSpacing = 10;
        
        float fontSize = Math.min(38f, w / 30f);
        _currentPlayerLabel.setFont(_controlFont.deriveFont(fontSize));
        int playerLabelWidth = Math.max(200, w / 4);
        _currentPlayerLabel.setBounds
        (
            margin,
            centerY - _currentPlayerLabel.getPreferredSize().height / 2,
            playerLabelWidth,
            _currentPlayerLabel.getPreferredSize().height
        );
        
        Dimension moveButtonSize = _moveButton.getPreferredSize();
        Dimension attackButtonSize = _attackButton.getPreferredSize();
        Dimension deployButtonSize = _deployButton.getPreferredSize();
        Dimension endTurnButtonSize = _endTurnButton.getPreferredSize();
        
        int panelCenterX = w / 2;
        int deployButtonX = panelCenterX - (deployButtonSize.width / 2);
        
        int moveButtonX = deployButtonX - buttonSpacing - moveButtonSize.width;
        int attackButtonX = deployButtonX + deployButtonSize.width + buttonSpacing;
        

        _moveButton.setBounds
        (
            moveButtonX,
            centerY - moveButtonSize.height/2,
            moveButtonSize.width,
            moveButtonSize.height
        );
        
        _deployButton.setBounds
        (
            deployButtonX,
            centerY - deployButtonSize.height/2,
            deployButtonSize.width,
            deployButtonSize.height
        );

        _attackButton.setBounds
        (
            attackButtonX,
            centerY - attackButtonSize.height/2,
            attackButtonSize.width,
            attackButtonSize.height
        );
        
        int endTurnX = w - margin - endTurnButtonSize.width;
        _endTurnButton.setBounds
        (
            endTurnX,
            centerY - endTurnButtonSize.height/2,
            endTurnButtonSize.width,
            endTurnButtonSize.height
        );
        
        repositionOverlay();
    }


    // custom painting for the panel background
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        
        // Create gradient background
        GradientPaint gp = new GradientPaint
        (
            0, 0, UIStyleUtils.BUTTON_BORDER_COLOR,
            0, getHeight(), new Color(153, 102, 51)
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}