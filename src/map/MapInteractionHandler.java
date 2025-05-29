package map;

import gameSetup.GameActionHandler;
import gameSetup.GameManager;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

/**
 * Handles user interactions with the map
 * Acts as the single source of truth for map interaction state
 */
public class MapInteractionHandler {
    private final MapPanel _mapPanel;
    private GameActionHandler _actionHandler;
    
    // UI state variables
    private Territory _selectedTerritory;
    private Territory _sourceTerritory;
    private Territory _justConquered;
    private List<Territory> _highlightedTerritories = new ArrayList<>();
    
    private Timer _blinkTimer;
    private boolean _blinkState = false;
    private static final int BLINK_INTERVAL = 600;
    
    // Action state
    private GameManager.ActionType _currentAction = GameManager.ActionType.NONE;
    private GameManager.ActionType _currentActionCache = null;
    
    // OPTIMIZATION: Replace single troop variables with HashMap
    private Map<String, Integer> _selectedTroops = new HashMap<>();

    // Aggiungi questa variabile come campo della classe
    private boolean _processingClick = false;

    /**
     * Constructor with action handler
     */
    public MapInteractionHandler(MapPanel mapPanel, GameActionHandler actionHandler) {
        _mapPanel = mapPanel;
        _actionHandler = actionHandler;
        
        _mapPanel.setInteractionHandler(this);
        _mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e);
            }
        });

        System.out.println("MapInteractionHandler initialized with ActionHandler" + 
                           (getActionHandler() != null ? " valid" : " null"));
    }
    
    /**
     * Constructor with clean initialization
     */
    public MapInteractionHandler(MapPanel mapPanel) {
        if (mapPanel == null) {
            throw new IllegalArgumentException("MapPanel cannot be null");
        }
        
        _mapPanel = mapPanel;
        _mapPanel.setInteractionHandler(this);
        
        _mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e);
            }
        });

        System.out.println("MapInteractionHandler initialized and bound to MapPanel");
    }
    
    /**
     * Handle mouse click on map
     */
    public void handleClick(MouseEvent e) {
        if (_processingClick) {
            System.out.println("DEBUG: Ignoring click while processing previous click");
            return;
        }
        
        _processingClick = true;
        try {
            GameActionHandler actionHandler = getActionHandler();
            
            if (actionHandler == null) {
                System.err.println("ERROR: ActionHandler is null in handleClick - cannot process click");
                return;
            }
            
            Point point = e.getPoint();
            Territory clickedTerritory = _mapPanel.getTerritoryAtPoint(point);
            
            if (clickedTerritory == null) {
                clearSelection();
                return;
            }
            
            if (_currentAction != GameManager.ActionType.NONE && _sourceTerritory != null) {
                if (_highlightedTerritories.contains(clickedTerritory)) {
                    completeAction(clickedTerritory);
                } else {
                    selectTerritory(clickedTerritory);
                }
            } else {
                selectTerritory(clickedTerritory);
            }
            _mapPanel.repaint();
        } finally {
            _processingClick = false;
        }
    }
    
    /**
     * Select a territory
     */
    public void selectTerritory(Territory territory) {
        _selectedTerritory = territory;
        
        if (territory != null) {
            startBlinking(territory);
        }
        
        GameActionHandler actionHandler = getActionHandler();
        if (actionHandler == null) {
            System.err.println("ERROR: ActionHandler is null in selectTerritory");
            return;
        }
        
        actionHandler.handleTerritorySelection(territory);
    }
    
    /**
     * Set current action type
     */
    public void setCurrentAction(GameManager.ActionType actionType) {
        // Skip if action hasn't changed
        if (_currentAction == actionType && _currentActionCache == actionType) {
            return;
        }
        
        _currentActionCache = actionType;
        _currentAction = actionType;
        updateCursorForAction(actionType);
        
        System.out.println("DEBUG: setCurrentAction called with: " + actionType);
        System.out.println("DEBUG: _sourceTerritory is " + (_sourceTerritory != null ? _sourceTerritory.getName() : "null"));
        
        if (_sourceTerritory != null) {
            if (_blinkTimer != null) {
                _blinkTimer.stop();
                _blinkTimer = null;
            }
            
            GameActionHandler actionHandler = getActionHandler();
            if (actionHandler == null) {
                System.err.println("ERROR: ActionHandler is null in setCurrentAction");
                return;
            }
            
            if (actionType == GameManager.ActionType.ATTACK) {
                List<Territory> targets = actionHandler.getValidAttackTargets(_sourceTerritory);
                startBlinkingForTargets(targets, _sourceTerritory);
            } else if (actionType == GameManager.ActionType.MOVE) {
                List<Territory> targets = actionHandler.getValidMoveTargets(_sourceTerritory);
                if (targets == null) {
                    targets = new ArrayList<>();
                }
                startBlinkingForTargets(targets, _sourceTerritory);
            } else {
                startBlinking(_sourceTerritory);
            }
        }
    }
    
    /**
     * Update cursor based on current action
     */
    private void updateCursorForAction(GameManager.ActionType actionType) {
        if (actionType == GameManager.ActionType.NONE) {
            _mapPanel.setCursor(Cursor.getDefaultCursor());
        } else if (actionType == GameManager.ActionType.ATTACK) {
            _mapPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else if (actionType == GameManager.ActionType.MOVE) {
            _mapPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }
    
    /**
     * Get current action type
     */
    public GameManager.ActionType getCurrentAction() {
        return _currentAction;
    }
    
    /**
     * Set source territory for current action
     */
    public void setSourceTerritory(Territory territory) {
        _sourceTerritory = territory;
        startBlinking(territory);
    }
    
    /**
     * Start blinking effect for a territory
     */
    public void startBlinking(Territory territory) {
        clearBlinking();
        
        _sourceTerritory = territory;
        
        _blinkTimer = new Timer(BLINK_INTERVAL, _ -> {
            _blinkState = !_blinkState;
            _mapPanel.repaint();
        });
        _blinkTimer.start();
    }
    
    /**
     * Set conquered territory for animation
     */
    public void setJustConquered(Territory territory) {
        _justConquered = territory;
        _mapPanel.repaint();
        
        Timer clearTimer = new Timer(2000, _ -> {
            _justConquered = null;
            _mapPanel.repaint();
        });
        clearTimer.setRepeats(false);
        clearTimer.start();
    }
    
    /**
     * Clear all blinking effects
     */
    public void clearBlinking() {
        if (_blinkTimer != null) {
            _blinkTimer.stop();
            _blinkTimer = null;
        }
        _sourceTerritory = null;
        _highlightedTerritories.clear();
        _blinkState = false; // Reset blink state to prevent flickering
        _mapPanel.repaint();
    }
    
    /**
     * Highlight territories for move or attack
     */
    public void highlightTerritories(List<Territory> territories, Territory sourceTerritory) {
        clearBlinking();
        
        _highlightedTerritories = territories != null ? territories : new ArrayList<>();
        _sourceTerritory = sourceTerritory;
        
        if (!_highlightedTerritories.isEmpty()) {
            _blinkTimer = new Timer(BLINK_INTERVAL, _ -> {
                _blinkState = !_blinkState;
                _mapPanel.repaint();
            });
            _blinkTimer.start();
        }
        
        _mapPanel.repaint();
    }
    
    /**
     * Complete action on target territory
     */
    public void completeAction(Territory targetTerritory) {
        if (_currentAction == GameManager.ActionType.NONE) {
            System.err.println("ERROR: Attempting to complete action when _currentAction is NONE");
            return;
        }
        
        System.out.println("DEBUG: MapInteractionHandler.completeAction triggered for " + 
                          targetTerritory.getName() + " with action type: " + _currentAction);

        GameActionHandler actionHandler = getActionHandler();
        if (actionHandler == null) {
            System.err.println("ERROR: ActionHandler is null in completeAction");
            return;
        }
        
        boolean success = false;
        
        try {
            // OPTIMIZATION: Use the stored HashMap instead of creating a new one
            if (_currentAction == GameManager.ActionType.ATTACK) {
                System.out.println("Completing attack on " + targetTerritory.getName() + 
                                  " with troops: " + _selectedTroops);
                actionHandler.executeAttack(_sourceTerritory, targetTerritory, _selectedTroops);
            } else if (_currentAction == GameManager.ActionType.MOVE) {
                System.out.println("Completing move to " + targetTerritory.getName() + 
                                  " with troops: " + _selectedTroops);
                actionHandler.executeMove(_sourceTerritory, targetTerritory, _selectedTroops);
            }
        } catch (Exception e) {
            System.err.println("ERROR in completeAction: " + e.getMessage());
            e.printStackTrace();
        }
        
        clearBlinking();
        
        if (!success) {
            selectTerritory(_sourceTerritory);
        } else {
            _sourceTerritory = null;
            _selectedTerritory = null;
            _currentAction = GameManager.ActionType.NONE;
            _selectedTroops.clear(); // Clear selected troops
        }
        
        _mapPanel.repaint();
    }
    
    /**
     * Update MapPanel's visible state after an action is completed
     */
    public void updateMapState() {
        clearBlinking();
        _mapPanel.repaint();
    }
    
    /**
     * Get methods for MapPanel to use in rendering
     */
    public Territory getSelectedTerritory() {
        return _selectedTerritory;
    }
    
    public Territory getSourceTerritory() {
        return _sourceTerritory;
    }
    
    public Territory getJustConquered() {
        return _justConquered;
    }
    
    /**
     * Get blink state
     */
    public boolean getBlinkState() {
        return _blinkState;
    }

    /**
     * Get highlighted territories
     */
    public List<Territory> getHighlightedTerritories() {
        return new ArrayList<>(_highlightedTerritories);
    }
    
    /**
     * Start blinking for target territories
     */
    private void startBlinkingForTargets(List<Territory> targets, Territory sourceTerritory) {
        _highlightedTerritories = targets != null ? targets : new ArrayList<>();
        _sourceTerritory = sourceTerritory;
        
        System.out.println("Starting blinking for " + _highlightedTerritories.size() + " targets");
        
        if (!_highlightedTerritories.isEmpty()) {
            _blinkTimer = new Timer(BLINK_INTERVAL, _ -> {
                _blinkState = !_blinkState;
                _mapPanel.repaint();
            });
            _blinkTimer.start();
        }
        
        _mapPanel.repaint();
    }
    
    /**
     * Get blink state for territory
     */
    public boolean getBlinkStateForTerritory(Territory territory) {
        if (_currentAction != GameManager.ActionType.NONE && territory == _sourceTerritory) {
            return true;
        }
        return _blinkState;
    }
    
    /**
     * Clear selection and notify GameActionHandler
     */
    public void clearSelection() {
        _selectedTerritory = null;
        _sourceTerritory = null;
        _currentAction = GameManager.ActionType.NONE;
        _selectedTroops.clear(); // OPTIMIZATION: Clear troops HashMap
        
        if (_blinkTimer != null) {
            _blinkTimer.stop();
            _blinkTimer = null;
        }
        
        _highlightedTerritories.clear();
        
        GameActionHandler actionHandler = getActionHandler();
        if (actionHandler != null) {
            actionHandler.handleTerritorySelection(null);
        }
        
        _mapPanel.setCursor(Cursor.getDefaultCursor());
        _mapPanel.repaint();
    }
    
    /**
     * OPTIMIZATION: Set selected troops using HashMap
     */
    public void setSelectedTroops(Map<String, Integer> troops) {
        _selectedTroops = new HashMap<>(troops);
        System.out.println("MapInteractionHandler: Selected troops: " + _selectedTroops);
    }
    
    /**
     * Add a single troop type to the selected troops
     */
    public void addSelectedTroop(String troopType, int quantity) {
        if (quantity > 0) {
            int current = _selectedTroops.getOrDefault(troopType, 0);
            _selectedTroops.put(troopType, current + quantity);
            System.out.println("MapInteractionHandler: Added " + quantity + " " + 
                             troopType + " to selected troops");
        }
    }

    /**
     * Highlight source territory and valid target territories
     */
    public void highlightTargets(Territory sourceTerritory, List<Territory> targets) {
        highlightTerritories(targets, sourceTerritory);
        System.out.println("MapInteractionHandler: Highlighting " + 
                         (targets != null ? targets.size() : 0) + " targets");
    }

    /**
     * OPTIMIZATION: Set move parameters using HashMap
     */
    public void setMoveParameters(Map<String, Integer> troops) {
        _selectedTroops = new HashMap<>(troops);
        System.out.println("MapInteractionHandler: Move parameters set - " + _selectedTroops);
    }

    /**
     * OPTIMIZATION: Set attack parameters using HashMap
     */
    public void setAttackParameters(Map<String, Integer> troops) {
        _selectedTroops = new HashMap<>(troops);
        System.out.println("MapInteractionHandler: Attack parameters set - " + _selectedTroops);
    }

    /**
     * Sets the GameActionHandler reference
     */
    public void setActionHandler(GameActionHandler actionHandler) {
        if (actionHandler == null) {
            System.out.println("WARNING: Attempt to set null ActionHandler ignored");
            return;
        }
        
        this._actionHandler = actionHandler;
        System.out.println("MapInteractionHandler: ActionHandler connection established");
    }
    
    /**
     * Gets the action handler with error handling
     */
    public GameActionHandler getActionHandler() {
        if (_actionHandler == null) {
            if (_mapPanel != null && _mapPanel.getGameManager() != null) {
                GameManager gameManager = _mapPanel.getGameManager();
                if (gameManager.getActionHandler() != null) {
                    _actionHandler = gameManager.getActionHandler();
                    System.out.println("Recovered ActionHandler from GameManager via MapPanel");
                }
            }
        }
        return _actionHandler;
    }
    
    /**
     * OPTIMIZATION: Get the selected troops HashMap
     */
    public Map<String, Integer> getSelectedTroops() {
        return new HashMap<>(_selectedTroops);
    }

    /**
     * Also clear timers when releasing resources
     */
    public void dispose() {
        clearBlinking();
        // Clear any other resources
    }
}