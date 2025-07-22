package gameSetup;


import map.Territory;
import map.MapInteractionHandler;
import map.MapPanel;

import java.util.List;
import java.util.Map;


/*
 * GameActionHandler mediates the UI and GameLogic, translating the map interactions
 * into actions for the GameManager
 */


public class GameActionHandler 
{
    private final GameManager _gameManager;
    private MapInteractionHandler _mapInteractionHandler;
    

    // ctor
    public GameActionHandler(GameManager gameManager) 
    {
        _gameManager = gameManager;
        System.out.println("GameActionHandler initialized with GameManager: " + (gameManager != null ? "valid" : "null"));
    }
    

    // creates the MapInteractionHandler for the given MapPanel,
    // I had several bugs during the creation of the map and it seemed that the synchronized helped
    public synchronized void initializeMapInteraction(MapPanel mapPanel) 
    {
        if (_mapInteractionHandler != null) 
        {
            System.out.println("MapInteractionHandler already initialized - using existing instance");
            return;
        }
        
        MapInteractionHandler interactionHandler = new MapInteractionHandler(mapPanel);
        interactionHandler.setActionHandler(this);
        _mapInteractionHandler = interactionHandler;
        
        System.out.println("MapInteractionHandler created and connected to GameActionHandler");
    }


    // gets the MapInteractionHandler with availability (I had some issues with this communication)
    public MapInteractionHandler getMapInteractionHandler() 
    {
        if (_mapInteractionHandler == null && _gameManager != null && _gameManager.getMapPanel() != null) 
        {
            _mapInteractionHandler = _gameManager.getMapPanel().getInteractionHandler();
            System.out.println("DEBUG: Retrieved MapInteractionHandler from MapPanel");
        }
        
        if (_mapInteractionHandler == null) 
{
            throw new IllegalStateException("Critical error: MapInteractionHandler unavailable");
        }
        
        return _mapInteractionHandler;
    }


    // Sets the MapInteractionHandler reference
    public void setMapInteractionHandler(MapInteractionHandler handler) 
    {
        if (handler == null) 
        {
            throw new IllegalArgumentException("Cannot set null MapInteractionHandler");
        }
        _mapInteractionHandler = handler;
        System.out.println("Setting MapInteractionHandler in GameActionHandler: valid");
    }

    
    // handle territory selection
    public void handleTerritorySelection(Territory territory) 
    {
        GameManager.ActionType currentAction = _mapInteractionHandler.getCurrentAction();

        if (territory == null) 
        {
            if (_gameManager.getGameActionPanel() != null) 
            {
                _gameManager.getGameActionPanel().hideActionButtons();
            }
            return;
        }
        
        if (currentAction == GameManager.ActionType.NONE) 
        {
            _gameManager.updateSelectedTerritoryState(territory);
        } 
        else 
        {
            // Use specific methods instead of completeAction
            if (currentAction == GameManager.ActionType.ATTACK) 
            {
                if (_gameManager.canAttackTerritory(_gameManager.getSourceTerritory(), territory)) 
                {
                    _gameManager.completeAttack(territory);
                } 
                else 
                {
                    System.out.println("Invalid attack target: " + territory.getName());
                    _gameManager.resetGameState();
                    
                    Territory sourceTerritory = _gameManager.getSourceTerritory();
                    if (sourceTerritory != null && sourceTerritory.getOwner() == _gameManager.getCurrentPlayer()) 
                    {
                        _gameManager.updateSelectedTerritoryState(sourceTerritory);
                    } 
                    else 
                    {
                        _gameManager.updateSelectedTerritoryState(territory);
                    }
                }
            } 
            else if (currentAction == GameManager.ActionType.MOVE) 
            {
                if (_gameManager.validateMoveTarget(territory)) 
                {
                    _gameManager.completeMove(territory);
                } 
                else 
                {
                    System.out.println("Invalid move target: " + territory.getName());
                    _gameManager.resetGameState();
                    
                    Territory sourceTerritory = _gameManager.getSourceTerritory();
                    if (sourceTerritory != null && sourceTerritory.getOwner() == _gameManager.getCurrentPlayer()) 
                    {
                        _gameManager.updateSelectedTerritoryState(sourceTerritory);
                    } 
                    else 
                    {
                        _gameManager.updateSelectedTerritoryState(territory);
                    }
                }
            }
        }
    }
    

    // prepares the Action and goes to the GameManager
    public void prepareActions(Territory source, Map<String, Integer> troops, GameManager.ActionType actionType) 
    {
        if (source == null || troops == null || troops.isEmpty()) 
        {
            System.err.println("Invalid " + actionType + " parameters.");
            return;
        }
        
        MapInteractionHandler handler = getMapInteractionHandler();
        
        // Set parameters in GameManager and MapInteractionHandler
        if (actionType == GameManager.ActionType.ATTACK) 
        {
            _gameManager.prepareAttackWithTroops(source, troops);
            handler.setSelectedTroops(troops);
        } 
        else 
        {
            _gameManager.prepareMoveWithTroops(source, troops);
            handler.setSelectedTroops(troops);
        }
        
        System.out.println("Action prepared: " + actionType + " with " + troops.size() + " troop types");
    }


    // links to GameManager
    public void deployTroops(Territory territory, Map<String, Integer> troops) {_gameManager.deployTroops(territory, troops);}

    // public methods to get valid attack and move targets from a source territory
    public List<Territory> getValidAttackTargets(Territory sourceTerritory) {return _gameManager.getValidAttackTargets(sourceTerritory);}
    public List<Territory> getValidMoveTargets(Territory sourceTerritory) {return _gameManager.getValidMoveTargets(sourceTerritory);}
}