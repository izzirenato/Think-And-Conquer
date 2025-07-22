package map;


import gameSetup.GameActionHandler;
import gameSetup.GameManager;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;


/*
 * MapInteractionHandler manages user interactions with the map and passes actions to the GameActionHandler
 */


public class MapInteractionHandler 
{
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
    
    private GameManager.ActionType _currentAction = GameManager.ActionType.NONE;
    
    private Map<String, Integer> _selectedTroops = new HashMap<>();
    private boolean _processingClick = false;
    
    
    // ctor
    public MapInteractionHandler(MapPanel mapPanel) 
    {
        if (mapPanel == null) 
        {
            throw new IllegalArgumentException("MapPanel cannot be null");
        }
        
        _mapPanel = mapPanel;
        _mapPanel.setInteractionHandler(this);

        System.out.println("MapInteractionHandler initialized and bound to MapPanel");
    }
    

    // getters and setters
    public Territory getSelectedTerritory() {return _selectedTerritory;}
    public Territory getSourceTerritory() {return _sourceTerritory;}
    public Territory getJustConquered() {return _justConquered;}
    public boolean getBlinkState() {return _blinkState;}
    public List<Territory> getHighlightedTerritories() {return new ArrayList<>(_highlightedTerritories);}
    public GameManager.ActionType getCurrentAction() {return _currentAction;}

    public void setActionHandler(GameActionHandler handler) {_actionHandler = handler;}
    public GameActionHandler getActionHandler() {return _actionHandler;}

    public void setSourceTerritory(Territory territory) 
    {
        _sourceTerritory = territory;
        startBlinking(territory);
    }

    public void setJustConquered(Territory territory) 
    {
        _justConquered = territory;
        _mapPanel.repaint();
        
        Timer clearTimer = new Timer(2000, _ -> 
        {
            _justConquered = null;
            _mapPanel.repaint();
        });
        clearTimer.setRepeats(false);
        clearTimer.start();
    }
    

    // sets the current action
    public void setCurrentAction(GameManager.ActionType actionType) 
    {
        if (_currentAction == actionType) {return;}
        
        _currentAction = actionType;
        updateCursorForAction(actionType);
        
        System.out.println("DEBUG: setCurrentAction called with: " + actionType);
        System.out.println("DEBUG: _sourceTerritory is " + (_sourceTerritory != null ? _sourceTerritory.getName() : "null"));
        
        if (_sourceTerritory != null) 
        {
            if (_blinkTimer != null) 
            {
                _blinkTimer.stop();
                _blinkTimer = null;
            }
            
            GameActionHandler actionHandler = getActionHandler();
            if (actionHandler == null) 
            {
                System.err.println("ERROR: ActionHandler is null in setCurrentAction");
                return;
            }
            
            if (actionType == GameManager.ActionType.ATTACK) 
            {
                List<Territory> targets = actionHandler.getValidAttackTargets(_sourceTerritory);
                startBlinkingForTargets(targets, _sourceTerritory);
            } 
            else if (actionType == GameManager.ActionType.MOVE) 
            {
                List<Territory> targets = actionHandler.getValidMoveTargets(_sourceTerritory);
                if (targets == null) {targets = new ArrayList<>();}
                startBlinkingForTargets(targets, _sourceTerritory);
            } 
            else {startBlinking(_sourceTerritory);}
        }
    }


    // updates the cursor based on the current action
    private void updateCursorForAction(GameManager.ActionType actionType) 
    {
        if (actionType == GameManager.ActionType.NONE) 
        {
            _mapPanel.setCursor(Cursor.getDefaultCursor());
        } 
        else if (actionType == GameManager.ActionType.ATTACK) 
        {
            _mapPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } 
        else if (actionType == GameManager.ActionType.MOVE) 
        {
            _mapPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }


    // sets the selected troops for the current action
    public void setSelectedTroops(Map<String, Integer> troops) 
    {
        _selectedTroops = new HashMap<>(troops);
        System.out.println("MapInteractionHandler: Selected troops: " + _selectedTroops);
    }


    // handle mouse clicks on the map based on the current action
    public void handleClick(MouseEvent e) 
    {
        if (_processingClick) 
        {
            System.out.println("DEBUG: Ignoring click while processing previous click");
            return;
        }
        
        _processingClick = true;
        try 
        {
            GameActionHandler actionHandler = getActionHandler();
            
            if (actionHandler == null) 
            {
                System.err.println("ERROR: ActionHandler is null in handleClick - cannot process click");
                return;
            }
            
            Point point = e.getPoint();
            Territory clickedTerritory = _mapPanel.getTerritoryAtPoint(point);
            
            if (clickedTerritory == null) 
            {
                clearSelection();
                return;
            }
            
            if (_currentAction != GameManager.ActionType.NONE && _sourceTerritory != null) 
            {
                if (_highlightedTerritories.contains(clickedTerritory)) {completeAction(clickedTerritory);} 
                else {selectTerritory(clickedTerritory);}
            } 
            else {selectTerritory(clickedTerritory);}
            _mapPanel.repaint();
        } 
        finally 
        {
            _processingClick = false;
        }
    }


    // selects a territory and starts blinking
    public void selectTerritory(Territory territory) 
    {
        _selectedTerritory = territory;
        
        if (territory != null) 
        {
            startBlinking(territory);
        }
        
        GameActionHandler actionHandler = getActionHandler();
        if (actionHandler == null) 
        {
            System.err.println("ERROR: ActionHandler is null in selectTerritory");
            return;
        }
        
        actionHandler.handleTerritorySelection(territory);
    }


    // completes the current action on the target territory
    public void completeAction(Territory targetTerritory) 
    {
        if (_currentAction == GameManager.ActionType.NONE) 
        {
            System.err.println("ERROR: Attempting to complete action when _currentAction is NONE");
            return;
        }
        
        System.out.println("DEBUG: MapInteractionHandler.completeAction triggered for " + 
                          targetTerritory.getName() + " with action type: " + _currentAction);

        GameActionHandler actionHandler = getActionHandler();
        if (actionHandler == null) 
        {
            System.err.println("ERROR: ActionHandler is null in completeAction");
            return;
        }
        
        boolean success = false;
        
        try 
        {
            if (_currentAction == GameManager.ActionType.ATTACK) {
                System.out.println("Completing attack on " + targetTerritory.getName() + 
                                  " with troops: " + _selectedTroops);

                if (_mapPanel.getGameManager().canAttackTerritory(_sourceTerritory, targetTerritory)) {
                    _mapPanel.getGameManager().completeAttack(targetTerritory);
                    success = true;
                } 
                else
                {
                    System.out.println("Invalid attack target");
                    _mapPanel.getGameManager().resetGameState();
                }
            } 
            else if (_currentAction == GameManager.ActionType.MOVE) 
            {
                System.out.println("Completing move to " + targetTerritory.getName() + 
                                  " with troops: " + _selectedTroops);
                if (_mapPanel.getGameManager().validateMoveTarget(targetTerritory)) 
                {
                    _mapPanel.getGameManager().completeMove(targetTerritory);
                    success = true;
                } 
                else 
                {
                    System.out.println("Invalid move target");
                    _mapPanel.getGameManager().resetGameState();
                }
            }  
        } 
        catch (Exception e) 
        {
            System.err.println("ERROR in completeAction: " + e.getMessage());
            e.printStackTrace();
        }
        
        clearBlinking();
        
        if (!success) 
        {
            selectTerritory(_sourceTerritory);
        } 
        else {
            _sourceTerritory = null;
            _selectedTerritory = null;
            _currentAction = GameManager.ActionType.NONE;
            _selectedTroops.clear();
        }
        
        _mapPanel.repaint();
    }


    // highlights territories for a specific action
    public void highlightTerritories(List<Territory> territories, Territory sourceTerritory) 
    {
        clearBlinking();
        
        _highlightedTerritories = territories != null ? territories : new ArrayList<>();
        _sourceTerritory = sourceTerritory;
        
        if (!_highlightedTerritories.isEmpty()) 
        {
            startBlinkTimer();
        }
        
        System.out.println("Highlighting " + _highlightedTerritories.size() + " territories");
    }


    // starts blinking effect for the source territory
    public void startBlinking(Territory territory) 
    {
        clearBlinking();
        _sourceTerritory = territory;
        startBlinkTimer();
    }

    
    // starts blinking effect for a list of target territories
    private void startBlinkingForTargets(List<Territory> targets, Territory sourceTerritory) 
    {
        _highlightedTerritories = targets != null ? targets : new ArrayList<>();
        _sourceTerritory = sourceTerritory;
        
        System.out.println("Starting blinking for " + _highlightedTerritories.size() + " targets");
        
        if (!_highlightedTerritories.isEmpty()) 
        {
            startBlinkTimer();
        }
    }


    // checks if the territory should blink based on the current action
    public boolean getBlinkStateForTerritory(Territory territory) 
    {
        if (_currentAction != GameManager.ActionType.NONE && territory == _sourceTerritory) 
        {
            return true;
        }
        return _blinkState;
    }


    // clears the blinking effect
    public void clearBlinking() 
    {
        if (_blinkTimer != null) 
        {
            _blinkTimer.stop();
            _blinkTimer = null;
        }
        _sourceTerritory = null;
        _highlightedTerritories.clear();
        _blinkState = false;
        _mapPanel.repaint();
    }


    // clears the current selection and resets the state
    public void clearSelection() 
    {
        _selectedTerritory = null;
        _currentAction = GameManager.ActionType.NONE;
        _selectedTroops.clear();
        
        clearBlinking();
        
        GameActionHandler actionHandler = getActionHandler();
        if (actionHandler != null) {
            actionHandler.handleTerritorySelection(null);
        }
        
        _mapPanel.setCursor(Cursor.getDefaultCursor());
        
        // AGGIUNTO: Riabilita end turn quando si cancella la selezione
        if (_mapPanel.getGameManager() != null && _mapPanel.getGameManager().getGameActionPanel() != null) 
        {
            _mapPanel.getGameManager().getGameActionPanel().setEndTurnButtonEnabled(true);
            System.out.println("End turn re-enabled after clearing selection");
        }
    }

    private void startBlinkTimer() 
    {
        if (_blinkTimer != null) 
        {
            _blinkTimer.stop();
        }
        
        _blinkTimer = new Timer(BLINK_INTERVAL, _ -> 
        {
            _blinkState = !_blinkState;
            _mapPanel.repaint();
        });
        _blinkTimer.start();
    }
}