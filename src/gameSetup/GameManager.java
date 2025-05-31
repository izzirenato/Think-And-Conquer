package gameSetup;


import map.MapInteractionHandler;
import map.MapPanel;
import map.Territory;

import trivia.*;
import troops.*;

import javax.swing.*;

import java.awt.Cursor;
import java.awt.Container;
import java.awt.CardLayout;
import java.awt.BorderLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;


/*
 * manages the game mechanics, it's the most important class of the game
 */


public class GameManager
{
    // Constants for point system
    private static final int TERRITORY_POINTS = 100;
    private static final int CONTINENT_BONUS = 200;
    private static final int INITIAL_TROOPS_PER_PLAYER = 2;
    private static final int INITIAL_TERRITORIES_PER_PLAYER = 3;
    private static final int TROOPS_PER_TURN_DIVISOR = 200;

    // Core game state
    private final List<Player> _players;
    private final int _maxPoints;
    private int _currentPlayerIndex;
    private boolean _gameOver;

    // Game components
    private MapPanel _mapPanel;
    private final QuestionDatabase _questionDatabase;
    private GameActionPanel _gameActionPanel;
    private GameActionHandler _actionHandler;
    private MapInteractionHandler _mapInteractionHandler;

    // Action state management
    public enum ActionType {NONE, ATTACK, MOVE}
    private ActionType _currentAction = ActionType.NONE;
    private Territory _sourceTerritory;
    private String _selectedTroopType;
    private int _selectedQuantity;
    private Map<String, Integer> _attackingTroops;
    private Map<String, Integer> _movingTroops;

    // UI state management
    private Container _originalParent;
    private JPanel _originalContainer;
    private int _originalIndex = -1;

    // Game progression tracking
    private Map<Player, Set<String>> _playerControlledContinents = new HashMap<>();

    // Stats panel
    private StatsPanel _statsPanel;


    // ctor
    public GameManager(List<Player> players)
    {
        _players = players;
        _gameOver = false;
        _maxPoints = calculateMaxPoints(_players.size());
        _questionDatabase = initializeQuestionDatabase();
        
        // creates the ActionHandler and links it with the GameManager
        _actionHandler = new GameActionHandler(this);
    }


    // with more players you need less points to win
    private int calculateMaxPoints(int playerCount)
    {
        return switch (playerCount)
        {
            case 2 -> 3600;
            case 3 -> 3200;
            default -> 2800;
        };
    }


    // initialize the questions
    private QuestionDatabase initializeQuestionDatabase()
    {
        return new QuestionDatabase();
    }


    // initialize the interaction handlers after MapPanel is created
    public void initializeInteractionHandlers(MapPanel mapPanel) 
    {
        System.out.println("DEBUG: Initializing interaction handlers");
        setMapPanel(mapPanel);
        
        // Use the existing method to create and initialize the handler
        _actionHandler.initializeMapInteraction(mapPanel);
        
        // Get the newly created handler and set it in the map panel
        MapInteractionHandler mapHandler = _actionHandler.getMapInteractionHandler();
        mapPanel.setInteractionHandler(mapHandler);

        System.out.println("DEBUG: Handler connection complete - references secured");
    }


    // initializes the first round
    public void initializeGame()
    {
        assignInitialTerritories();

        // three troops in territories + two troops deployable
        for (Player player : _players) 
        {
            List<Territory> territories = getPlayerTerritories(player);
            for (Territory territory : territories) 
            {
                Map<String, Integer> randomTroop = generateRandomTroops(1);
                String troopType = randomTroop.keySet().iterator().next();
                territory.addTroops(troopType, 1);
            }

            Map<String, Integer> deployableTroops = generateRandomTroops(INITIAL_TROOPS_PER_PLAYER);
            for (Map.Entry<String, Integer> entry : deployableTroops.entrySet()) 
            {
                player.addTroops(entry.getKey(), entry.getValue());
            }
        }
        _currentPlayerIndex = 0;
    }


    // at the start of the game, each player gets 3 territories from a random continent
    private void assignInitialTerritories() 
    {
        Map<String, List<Territory>> continents = _mapPanel.getWorldMapData().getContinentTerritories();
        List<String> continentNames = new ArrayList<>(continents.keySet());
        Collections.shuffle(continentNames);
    
        // each player has one continent to spawn
        Map<Player, String> playerSpawnContinents = new HashMap<>();
        for (int i = 0; i < _players.size(); i++) 
        {
            playerSpawnContinents.put(_players.get(i), continentNames.get(i));
        }

        // Assign 3 territories from the spawn continent to each player
        for (Player player : _players) 
        {
            String spawnContinent = playerSpawnContinents.get(player);
            List<Territory> continentTerritories = new ArrayList<>(continents.get(spawnContinent));
            Collections.shuffle(continentTerritories); // Randomizza l'ordine dei territori

            for (int i = 0; i < INITIAL_TERRITORIES_PER_PLAYER && i < continentTerritories.size(); i++) 
            {
                Territory territory = continentTerritories.get(i);
                territory.setOwner(player);
                player.modifyPoints(TERRITORY_POINTS);
            }
        }

        _mapPanel.loadTerritoryImages();
        _mapPanel.repaint();
    }


    // prepares the next turn
    public void nextTurn()
    {
        if (_gameOver) return;
        
        System.out.println("DEBUG: Resetting troop actions for player: " + getCurrentPlayer().getName());
        for (Territory t : getPlayerTerritories(getCurrentPlayer())) 
        {
            t.resetTroopActions();
        }
        
        advanceToNextPlayer();
        assignReinforcements();
        
        _statsPanel.update();
    }


    // goes to the next player
    private void advanceToNextPlayer()
    {
        int originalIndex = _currentPlayerIndex;
        do 
        {
            _currentPlayerIndex = (_currentPlayerIndex + 1) % _players.size();
        } 
        while (_players.get(_currentPlayerIndex).isEliminated() 
                && _currentPlayerIndex != originalIndex);
    }


    // assigns reinforcements based on the player's points
    private void assignReinforcements() 
    {
        Player currentPlayer = getCurrentPlayer();
        int points = currentPlayer.getPoints();
        int reinforcements = Math.max(1, points / TROOPS_PER_TURN_DIVISOR);
        addRandomTroopsToPlayer(currentPlayer, reinforcements);
    }


    // deploys the troops to a territory
    public void deployTroops(Territory territory, Map<String, Integer> troops) 
    {
        for (Map.Entry<String, Integer> entry : troops.entrySet()) 
        {
            territory.addTroops(entry.getKey(), entry.getValue());
            System.out.println("Deployed " + entry.getValue() + " " + entry.getKey() + 
                                " to " + territory.getName());
        }
        _players.get(_currentPlayerIndex).removeTroops(troops);
        
        _gameActionPanel.updateButtonsForSelectedTerritory(territory);
        _mapPanel.updateTerritory(territory);
        
        _statsPanel.update();
    }


    // prepares a move action with troops
    public void prepareMoveWithTroops(Territory source, Map<String, Integer> troops) 
    {
        _sourceTerritory = source;
        _currentAction = ActionType.MOVE;
        _movingTroops = new HashMap<>(troops);
        
        _mapPanel.setCurrentAction(ActionType.MOVE);
        highlightValidMoveTargets();
        
        _mapInteractionHandler.setCurrentAction(ActionType.MOVE);
        _mapInteractionHandler.setMoveParameters(_movingTroops);
        
        System.out.println("Move prepared with troops: " + _movingTroops);
    }


    // starts an attack with a map of selected troops
    public void prepareAttackWithTroops(Territory source, Map<String, Integer> troops) 
    {        
        _sourceTerritory = source;
        _currentAction = ActionType.ATTACK;
        _attackingTroops = new HashMap<>(troops);
        
        _mapPanel.setCurrentAction(ActionType.ATTACK);
        
        highlightValidAttackTargets();

        _mapInteractionHandler.setCurrentAction(ActionType.ATTACK);
        _mapInteractionHandler.setAttackParameters(_attackingTroops);
        
        System.out.println("Attack prepared with troops: " + _attackingTroops);
    }


    //  public method that sorts out the action completion
    public void completeAction(Territory targetTerritory) 
    {
        System.out.println("Attempting to complete action: " + _currentAction + 
                          " on target: " + targetTerritory.getName());

        if (_currentAction == ActionType.ATTACK) 
        {
            if (canAttackTerritory(_sourceTerritory, targetTerritory)) 
            {
                completeAttack(targetTerritory);
            } 
            else 
            {
                System.out.println("Invalid attack target: " + targetTerritory.getName());
                resetGameState(); // Reset game state if target is invalid
            }
        } 
        else if (_currentAction == ActionType.MOVE) 
        {
            if (validateMoveTarget(targetTerritory)) 
            {
                completeMoveAction(targetTerritory);
            } 
            else 
            {
                System.out.println("Invalid move target: " + targetTerritory.getName());
                resetGameState(); // Reset game state if target is invalid
            }
        }
    }


    // moves the troops
    private void completeMoveAction(Territory targetTerritory) 
    {
        System.out.println("Moving troops from " + _sourceTerritory.getName() + 
                         " to " + targetTerritory.getName() + ": " + _movingTroops);
        
        _sourceTerritory.removeTroops(_movingTroops);
        targetTerritory.addTroops(_movingTroops);
        
        for (String troopType : _movingTroops.keySet()) 
        {
            _sourceTerritory.markTroopAsActed(troopType);
            targetTerritory.markTroopAsActed(troopType);
        }

        System.out.println("DEBUG: Troops " + _movingTroops + " marked as used in territories ");

        _mapPanel.updateTerritory(_sourceTerritory);
        _mapPanel.updateTerritory(targetTerritory);

        resetGameState();
    }


    // manages the attack action, storing the mapPanel
    private void completeAttack(Territory targetTerritory) 
    {
        System.out.println("Starting attack on " + targetTerritory.getName() + 
                            " from " + (_sourceTerritory != null ? _sourceTerritory.getName() : "null") +
                            " with " + _selectedQuantity + " " + _selectedTroopType);
        
        Map<String, Integer> attackingTroops = _attackingTroops;
        
        Player attacker = getCurrentPlayer();
        Player defender = targetTerritory.getOwner();

        _gameActionPanel.hideActionButtons();

        _originalParent = _mapPanel.getParent();
        Container parentContainer = _originalParent;
        _originalContainer = new JPanel(new BorderLayout());
        _originalContainer.add(_mapPanel, BorderLayout.CENTER);
        
        for (int i = 0; i < parentContainer.getComponentCount(); i++) 
        {
            if (parentContainer.getComponent(i) == _mapPanel) 
            {
                _originalIndex = i;
                break;
            }
        }
        
        CardLayout cardLayout = new CardLayout();
        JPanel mainContainer = new JPanel(cardLayout);
        
        JPanel mapContainer = new JPanel(new BorderLayout());
        mapContainer.add(_mapPanel, BorderLayout.CENTER);
        mainContainer.add(mapContainer, "MAP");
         
        boolean isUndefended = (defender == null || targetTerritory.getTroopCount() == 0);

        // if the territory is undefended, it will be asked just one question
        if (isUndefended) 
        {
            int totalAttackingTroops = 0;
            for (Integer count : attackingTroops.values()) 
            {
                totalAttackingTroops += count;
            }
            
            int questionDifficulty;
            if (totalAttackingTroops <= 2) {questionDifficulty = 3;} 
            else if (totalAttackingTroops == 3 || totalAttackingTroops == 4) {questionDifficulty = 2;}
            else {questionDifficulty = 1;}

            System.out.println("Attacking with " + totalAttackingTroops + " total troops, difficulty set to: " + 
                                Question.getDifficultyText(questionDifficulty, false));
            
            // Find the most common troop type for category selection
            String mostCommonTroopType = null;
            int highestCount = 0;
            List<String> troopTypesWithHighestCount = new ArrayList<>();
            
            for (Map.Entry<String, Integer> entry : attackingTroops.entrySet()) 
            {
                if (entry.getValue() > highestCount) 
                {
                    highestCount = entry.getValue();
                    troopTypesWithHighestCount.clear();
                    troopTypesWithHighestCount.add(entry.getKey());
                }
                else if (entry.getValue() == highestCount)
                {
                    troopTypesWithHighestCount.add(entry.getKey());
                }
            }
            
            if (!troopTypesWithHighestCount.isEmpty())
            {
                Random random = new Random();
                mostCommonTroopType = troopTypesWithHighestCount.get(
                    random.nextInt(troopTypesWithHighestCount.size()));
            }
            
            // Get appropriate category based on most common attacking troop type
            Question.Category category = null;
            Troop attackingTroop = TroopFactory.getTroop(mostCommonTroopType);
            category = attackingTroop.getCategory();
            System.out.println("Using category from most common troop type: " + 
                                mostCommonTroopType + " (" + category.getDisplayName() + ")");
            
            final Question question = _questionDatabase.getRandomQuestionWithExactDifficulty(category, questionDifficulty);
            System.out.println("Undefended territory question difficulty: " + 
                                Question.getDifficultyText(questionDifficulty, false));
            


            QuizPanel quizPanel = new QuizPanel
            (
                question,
                correct -> 
                {
                    try 
                    {
                        // Restore UI
                        parentContainer.remove(mainContainer);
                        parentContainer.add(_mapPanel, _originalIndex);
                        parentContainer.revalidate();
                        parentContainer.repaint();
                        _gameActionPanel.showEndTurnButton();
                        
                        if (correct) 
                        {
                            // Use resolveBattleResults for consistency - treat as perfect attacker score vs 0 defender score
                            resolveBattleResults(targetTerritory, attacker, defender, attackingTroops, new HashMap<>(), 100, 0);
                            attacker.incrementCorrectAnswers(question.getCategory());
                        } 
                        else 
                        {
                            // Mark attacking troops as used after failed attack
                            for (String troopType : _attackingTroops.keySet()) 
                            {
                                _sourceTerritory.markTroopAsActed(troopType);
                            }
                            attacker.incrementWrongAnswers(question.getCategory());
                        }
                        resetGameState();
                    } 
                    catch (Exception e) 
                    {
                        System.err.println("ERROR in attack callback: " + e.getMessage());
                        e.printStackTrace();
                    }
                },
                getCurrentPlayer()
            );

            // Add the question panel and show it
            mainContainer.add(quizPanel, "QUIZ");
            cardLayout.show(mainContainer, "QUIZ"); 
        } 
        else 
        {
            Map<String, Integer> defenderTroops = targetTerritory.getTroops();

            DuelPanel duelPanel = new DuelPanel
            (
                targetTerritory, 
                attacker, 
                defender, 
                _questionDatabase,
                (_, attackerScore, defenderScore) -> 
                {
                    // Restore the UI
                    parentContainer.remove(mainContainer);
                    parentContainer.add(_mapPanel, _originalIndex);
                    parentContainer.revalidate();
                    parentContainer.repaint();
                    _gameActionPanel.showEndTurnButton();

                    // Call the comprehensive battle resolution method
                    resolveBattleResults
                    (
                        targetTerritory,
                        attacker, 
                        defender, 
                        attackingTroops, 
                        defenderTroops, 
                        attackerScore, 
                        defenderScore
                    );
                    
                    // Reset game state after battle is resolved
                    resetGameState();
                },
                attackingTroops,
                defenderTroops
            );
            
            mainContainer.add(duelPanel, "DUEL");
            cardLayout.show(mainContainer, "DUEL");
        }
        _statsPanel.update();

        parentContainer.remove(_mapPanel);
        parentContainer.add(mainContainer);
        parentContainer.revalidate();
        parentContainer.repaint();
    }


    // decides the battle results based on scores
    public void resolveBattleResults(Territory targetTerritory, Player attacker, Player defender,
                                    Map<String, Integer> attackingTroops, Map<String, Integer> defendingTroops,
                                    int attackerScore, int defenderScore) 
    {
        
        System.out.println("Resolving battle: Attacker=" + attackerScore + " vs Defender=" + defenderScore);
        
        Map<String, Integer> survivingAttackerTroops;
        Map<String, Integer> survivingDefenderTroops;
        boolean attackerWins;

        // 0-0 tie - both sides lose all troops, defender keeps territory (I mean they both deserve to lose XD)
        if (attackerScore == 0 && defenderScore == 0) 
        {
            System.out.println("0-0 tie: Both players lose all troops");
            survivingAttackerTroops = new HashMap<>();
            survivingDefenderTroops = new HashMap<>();
            attackerWins = false;
        }

        // regular tie - equalize to lower count
        else if (attackerScore == defenderScore && attackerScore > 0) 
        {
            System.out.println("Regular tie: Equalizing troops to lower count");
            
            int attackerTroopCount = countTroops(attackingTroops);
            int defenderTroopCount = countTroops(defendingTroops);

            int lowerCount = Math.min(attackerTroopCount, defenderTroopCount);
            
            System.out.println("Troop equalization: Attacker=" + attackerTroopCount + 
                            ", Defender=" + defenderTroopCount + ", Target=" + lowerCount);
            
            // both players get reduced to the lower troop count
            survivingAttackerTroops = randomlySelectTroops(attackingTroops, lowerCount);
            survivingDefenderTroops = randomlySelectTroops(defendingTroops, lowerCount);
            attackerWins = false;
        }

        // attacker wins
        else if (attackerScore > defenderScore) 
        {
            System.out.println("Attacker wins!");
            
            // defender loses all troops
            survivingDefenderTroops = new HashMap<>();
            
            // attacker loses troops based on how close the battle was
            survivingAttackerTroops = calculateWinnerTroopLosses(attackingTroops, attackerScore, defenderScore);
            attackerWins = true;
        }

        // defender wins
        else 
        {
            System.out.println("Defender wins!");
            
            // attacker loses all troops
            survivingAttackerTroops = new HashMap<>();
            
            // defender loses troops based on how close the battle was
            survivingDefenderTroops = calculateWinnerTroopLosses(defendingTroops, defenderScore, attackerScore);
            attackerWins = false;
        }
        
        // apply the battle results
        applyBattleResults(targetTerritory, attacker, defender, survivingAttackerTroops, survivingDefenderTroops, attackerWins);
        
        // Add post-battle checks that were previously duplicated
        if (attackerWins) {
            attacker.modifyPoints(TERRITORY_POINTS);
            checkVictory(attacker);
        }
    }


    // applies the calculated battle results to the game state.
    private void applyBattleResults(Territory targetTerritory, Player attacker, Player defender,
                                Map<String, Integer> survivingAttackerTroops, 
                                Map<String, Integer> survivingDefenderTroops, 
                                boolean attackerWins) 
    {
        _sourceTerritory.removeTroops(_attackingTroops);
        
        if (attackerWins) 
        {
            targetTerritory.clearTroops();
            targetTerritory.setOwner(attacker);
            
            for (Map.Entry<String, Integer> entry : survivingAttackerTroops.entrySet()) 
            {
                if (entry.getValue() > 0) 
                {
                    targetTerritory.addTroops(entry.getKey(), entry.getValue());
                    targetTerritory.markTroopAsActed(entry.getKey());
                }
            }
            
            // check for continent bonuses and losses
            checkContinentBonus(attacker, targetTerritory);
            checkContinentLoss(defender, targetTerritory);

            checkPlayerElimination(defender);
            
            System.out.println("Territory conquered: " + survivingAttackerTroops + " troops moved to " + 
                            targetTerritory.getName());
        } 
        else 
        {
            targetTerritory.clearTroops();
            
            for (Map.Entry<String, Integer> entry : survivingDefenderTroops.entrySet())
            {
                if (entry.getValue() > 0) 
                {
                    targetTerritory.addTroops(entry.getKey(), entry.getValue());
                    // No markTroopAsActed call here (good)
                }
            }
            
            for (Map.Entry<String, Integer> entry : survivingAttackerTroops.entrySet()) 
            {
                if (entry.getValue() > 0) 
                {
                    _sourceTerritory.addTroops(entry.getKey(), entry.getValue());
                    _sourceTerritory.markTroopAsActed(entry.getKey());
                }
            }
            
            System.out.println("Territory defended: " + survivingDefenderTroops + " troops remain, " +
                            survivingAttackerTroops + " attacker troops return to source");
        }
        
        _mapPanel.updateTerritory(targetTerritory);
        _mapPanel.updateTerritory(_sourceTerritory);
    }


    // calculates the surviving troops for the winner based on the score difference (ratio)
    private Map<String, Integer> calculateWinnerTroopLosses(Map<String, Integer> originalTroops, 
                                                        int winnerScore, int loserScore) 
    {
        // Special case: opponent scored 0, winner keeps most troops
        if (loserScore == 0) {return originalTroops;}

        // calculate dominance ratio
        float totalPoints = winnerScore + loserScore;
        float dominanceRatio = (winnerScore - loserScore) / totalPoints;
        
        // converts dominance ratio to a survival rate
        float baseSurvival = 0.4f;                          // Minimum survival rate
        float bonusSurvival = dominanceRatio * 0.4f;        // Up to 40% bonus
        float survivalRate = Math.max(0.3f, Math.min(0.8f, baseSurvival + bonusSurvival));
        
        System.out.println("Winner survival: Dominance=" + String.format("%.2f", dominanceRatio) + 
                        ", Survival=" + String.format("%.1f%%", survivalRate * 100));
        
        return applyRandomSurvival(originalTroops, survivalRate);
    }


    // applies the random survival logic based on the calculated survival rate
    private Map<String, Integer> applyRandomSurvival(Map<String, Integer> originalTroops, float survivalRate) 
    {
        int totalTroops = countTroops(originalTroops);
        int troopsToRetain = Math.max(1, (int) Math.round(totalTroops * survivalRate));
        
        return randomlySelectTroops(originalTroops, troopsToRetain);
    }


    // randomly selects a specified number of troops from the original troop map
    private Map<String, Integer> randomlySelectTroops(Map<String, Integer> originalTroops, int count) 
    {
        Map<String, Integer> selectedTroops = new HashMap<>();

        // Create list of all individual troops
        List<String> allTroops = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : originalTroops.entrySet())
        {
            String troopType = entry.getKey();
            int quantity = entry.getValue();
            
            for (int i = 0; i < quantity; i++) 
            {
                allTroops.add(troopType);
            }
        }
        
        // Shuffle and select the requested count
        Collections.shuffle(allTroops);
        int troopsToSelect = Math.min(count, allTroops.size());
        
        for (int i = 0; i < troopsToSelect; i++)
        {
            String troopType = allTroops.get(i);
            selectedTroops.put(troopType, selectedTroops.getOrDefault(troopType, 0) + 1);
        }
        
        System.out.println("Troop survival: " + originalTroops + " -> " + selectedTroops + 
                        " (selected " + troopsToSelect + "/" + allTroops.size() + ")");
        
        return selectedTroops;
    }


    // checks the win
    private void checkVictory(Player player)
    {
        // Controlla se il giocatore ha raggiunto i punti massimi
        boolean wonByPoints = player.getPoints() >= _maxPoints;
        
        // Controlla se è l'ultimo giocatore rimasto
        long remainingPlayers = _players.stream().filter(p -> !p.isEliminated()).count();
        boolean lastPlayerStanding = remainingPlayers <= 1;
        
        if (wonByPoints || lastPlayerStanding)
        {
            _gameOver = true;
            
            System.out.println(player.getName() + " wins! " + 
                (wonByPoints ? "Points victory: " + player.getPoints() + "/" + _maxPoints : 
                 "Last player standing!"));
            
            // Get the main game frame
            JFrame gameFrame = (JFrame) SwingUtilities.getWindowAncestor(_mapPanel);
            
            if (gameFrame != null) {
                // Create the game over panel
                GameOverPanel gameOverPanel = new GameOverPanel(player, _players);
                
                // Replace the current content with the game over panel
                gameFrame.getContentPane().removeAll();
                gameFrame.getContentPane().setLayout(new BorderLayout());
                gameFrame.getContentPane().add(gameOverPanel, BorderLayout.CENTER);
                
                // Scale the panel to fit the frame
                gameOverPanel.scale(gameFrame.getWidth(), gameFrame.getHeight());
                
                gameFrame.revalidate();
                gameFrame.repaint();
            }
        }
    }


    // if a player conquers a territory, checks if he completed a continent
    private void checkContinentBonus(Player conqueror, Territory conqueredTerritory)
    {
        String targetContinent = conqueredTerritory.getContinent();
        
        // Get all territories in this continent
        Map<String, List<Territory>> continents = _mapPanel.getWorldMapData().getContinentTerritories();
        List<Territory> continentTerritories = continents.get(targetContinent);
        
        if (ownsAllTerritories(conqueror, continentTerritories)) 
        {
            // Award bonus points for continent control
            conqueror.modifyPoints(CONTINENT_BONUS);
            System.out.println(conqueror.getName() + " completed continent '" + 
                              targetContinent + "' and receives " + CONTINENT_BONUS + " bonus points!");
            
            // Update UI if needed
            if (_gameActionPanel != null) 
            {
                _gameActionPanel.updatePlayerInfo();
            }
        }
    }


    // checks if a player has lost control of a continent after losing a territory
    private void checkContinentLoss(Player defender, Territory lostTerritory) 
    {
        if (defender == null) return;
        
        // Initialize if needed
        if (!_playerControlledContinents.containsKey(defender)) 
        {
            _playerControlledContinents.put(defender, new HashSet<>());
            return;
        }
        
        String continent = lostTerritory.getContinent();
        Set<String> controlledContinents = _playerControlledContinents.get(defender);
        
        // If player doesn't control this continent, nothing to check
        if (!controlledContinents.contains(continent)) {return;}
        
        Map<String, List<Territory>> continents = _mapPanel.getWorldMapData().getContinentTerritories();
        List<Territory> continentTerritories = continents.get(continent);
        
        if (!ownsAllTerritories(defender, continentTerritories))
        {
            // Player has lost control of the continent
            controlledContinents.remove(continent);
            defender.modifyPoints(-CONTINENT_BONUS);
            
            System.out.println(defender.getName() + " lost control of continent '" + 
                              continent + "' and loses " + CONTINENT_BONUS + " points!");
            
            // Update UI
            if (_gameActionPanel != null) 
            {
                _gameActionPanel.updatePlayerInfo();
            }
        }
    }


    // finds the valid targets and sends to the MapInteractionHandler to let them highlight
    private void highlightValidAttackTargets() 
    {
        List<Territory> validTargets = new ArrayList<>();
        for (Territory neighbor : _sourceTerritory.getNeighbors()) 
        {
            if (neighbor.getOwner() != getCurrentPlayer()) 
            {
                validTargets.add(neighbor);
            }
        }
        _mapInteractionHandler = _mapPanel.getInteractionHandler();
        
        
        _mapInteractionHandler.setCurrentAction(ActionType.ATTACK);
        _mapInteractionHandler.setSourceTerritory(_sourceTerritory);
        _mapInteractionHandler.highlightTargets(_sourceTerritory, validTargets);
            
        System.out.println("Highlighting " + validTargets.size() + 
                            " attack targets from " + _sourceTerritory.getName());
    }


    // finds the valid targets and sends to the MapInteractionHandler to let them highlight
    private void highlightValidMoveTargets() 
    {
        List<Territory> validTargets = new ArrayList<>();
        for (Territory neighbor : _sourceTerritory.getNeighbors()) 
        {
            if (neighbor.getOwner() == getCurrentPlayer()) 
            {
                validTargets.add(neighbor);
            }
        }
        _mapInteractionHandler = _mapPanel.getInteractionHandler();

        // Set the current action to ensure proper coloring
        _mapInteractionHandler.setCurrentAction(ActionType.MOVE);
        _mapInteractionHandler.setSourceTerritory(_sourceTerritory);
        _mapInteractionHandler.highlightTargets(_sourceTerritory, validTargets);

        System.out.println("Highlighting " + validTargets.size() +
                            " move targets from " + _sourceTerritory.getName());
    }


    // resets the game state after an action is completed
    private void resetGameState() 
    {
        resetActionState();
        _attackingTroops = null;
        _movingTroops = null;

        _mapInteractionHandler.clearSelection(); 
    }


    // resets the action state
    private void resetActionState() 
    {
        _currentAction = ActionType.NONE;
        _sourceTerritory = null;
        _selectedTroopType = null;
        _selectedQuantity = 0;
        
        _mapPanel.setCursor(Cursor.getDefaultCursor());  
        _mapPanel.setCurrentAction(_currentAction);
        _gameActionPanel.showEndTurnButton();
    }


    // checks if a territory can be attacked from a source territory
    public boolean canAttackTerritory(Territory source, Territory target) 
    {
        return target != null && 
            source != null &&
            source.getNeighbors().contains(target) && 
            target.getOwner() != getCurrentPlayer();
    }


    // Validates if a territory is a valid move target, helper
    private boolean validateMoveTarget(Territory target)
    {
        return target != null && 
               _sourceTerritory != null &&
               _sourceTerritory.getNeighbors().contains(target) && 
               target.getOwner() == getCurrentPlayer();
    }


    // get valid attack targets from a source territory
    public List<Territory> getValidAttackTargets(Territory sourceTerritory) 
    {
        if (sourceTerritory == null) {return Collections.emptyList();}
        
        List<Territory> validTargets = new ArrayList<>();
        for (Territory neighbor : sourceTerritory.getNeighbors()) 
        {
            if (canAttackTerritory(sourceTerritory, neighbor)) 
            {
                validTargets.add(neighbor);
            }
        }
        return validTargets;
    }


    // return territories where the player could move
    public List<Territory> getValidMoveTargets(Territory sourceTerritory) 
    {
        List<Territory> validTargets = new ArrayList<>();
        List<Territory> neighbors = sourceTerritory.getNeighbors();
        
        for (Territory neighbor : neighbors) 
        {
            if (neighbor.getOwner() == getCurrentPlayer()) 
            {
                validTargets.add(neighbor);
            }
        }
        return validTargets;
    }


    // links the GameActionHandler with the GameActionPanel
    public void updateSelectedTerritoryState(Territory territory) 
    {
        _gameActionPanel.updateButtonsForSelectedTerritory(territory);
    }


    // return the territories owned by a player
    private List<Territory> getPlayerTerritories(Player player) 
    {
        List<Territory> playerTerritories = new ArrayList<>();
        for (Territory territory : _mapPanel.getTerritories()) 
        {
            if (territory.getOwner() == player) 
            {
                playerTerritories.add(territory);
            }
        }
        return playerTerritories;
    }


    // generate deployable random troops for a player
    private Map<String, Integer> generateRandomTroops(int troopCount)
    {
        Map<String, Integer> troopDistribution = new HashMap<>();
        if (troopCount <= 0) {return troopDistribution;}

        Troop[] allTroops = TroopFactory.getAllTroops();
        Random random = new Random();

        for (int i = 0; i < troopCount; i++)
        {
            int randomIndex = random.nextInt(allTroops.length);
            String troopType = allTroops[randomIndex].getName();
            troopDistribution.put(troopType, troopDistribution.getOrDefault(troopType, 0) + 1);
        }

        return troopDistribution;
    }


    // adds random troops to a player
    private void addRandomTroopsToPlayer(Player player, int troopCount) 
    {
        Map<String, Integer> newTroops = generateRandomTroops(troopCount);
        for (Map.Entry<String, Integer> entry : newTroops.entrySet()) 
        {
            player.addTroops(entry.getKey(), entry.getValue());
        }
    }


    // helper method
    private boolean ownsAllTerritories(Player player, List<Territory> territories)
    {
        for (Territory territory : territories)
        {
            if (territory.getOwner() != player) {return false;}
        }
        return true;
    }


    // counts the total number of troops in a map, helper
    private int countTroops(Map<String, Integer> troops) 
    {
        int total = 0;
        for (Integer count : troops.values()) {total += count;}
        return total;
    }

        // NUOVO METODO: Controlla se un giocatore è stato eliminato
    private void checkPlayerElimination(Player player) {
        if (player == null || player.isEliminated()) return;
        
        List<Territory> playerTerritories = getPlayerTerritories(player);
        if (playerTerritories.isEmpty()) {
            // Il giocatore non ha più territori, eliminalo
            player.eliminate();
            System.out.println(player.getName() + " has been eliminated from the game!");
            
            String eliminationText = player.getName() + " is dead";
            _gameActionPanel.showEliminationNotification(eliminationText, player.getColor(), null);
        }
    }



    // public getters and setters
    public int getMaxPoints() {return _maxPoints;}
    public List<Player> getPlayers() {return new ArrayList<>(_players);}
    public GameActionPanel getGameActionPanel() {return _gameActionPanel;}
    public GameActionHandler getActionHandler() {return _actionHandler;}
    public MapPanel getMapPanel() {return _mapPanel;}
    public Player getCurrentPlayer() {return _players.get(_currentPlayerIndex);}
    public void setMapPanel(MapPanel mapPanel) {_mapPanel = Objects.requireNonNull(mapPanel);}
    public void setGameActionPanel(GameActionPanel panel) {_gameActionPanel = panel;}
    public void setStatsPanel(StatsPanel statsPanel) {
        _statsPanel = statsPanel;
    }


}