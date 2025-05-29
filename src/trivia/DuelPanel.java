package trivia;

import gameSetup.*;
import map.*;
import troops.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Manages player vs player trivia duels where questions are based on troops
 */
public class DuelPanel extends JPanel {
    
    // Core game data
    private final Player _attacker;
    private final Player _defender;
    private final QuestionDatabase _questionDB;
    private final DuelResponseListener _responseListener;
    
    // Game state
    private int _attackerScore = 0;
    private int _defenderScore = 0;
    private boolean _isAttackerTurn = true;
    private int _selectedDifficulty = 3;
    private boolean _gameOver = false;
    
    // Question management by player troops
    private List<TroopQuestion> _attackerQuestions = new ArrayList<>();
    private List<TroopQuestion> _defenderQuestions = new ArrayList<>();
    private int _attackerQuestionIndex = 0;
    private int _defenderQuestionIndex = 0;
    
    // Timer components
    private javax.swing.Timer _countdownTimer;
    private int _timeRemaining = 10;
    private CircularTimer _timerDisplay;
    
    // Current question info
    private String _currentTroopType;
    private Question.Category _currentCategory;
    
    // Target territory for the duel
    private Territory _targetTerritory;
    
    // Represents a question tied to a specific troop type
    private static class TroopQuestion {
        final String troopType;
        final Question question;
        final Question.Category category;
        
        TroopQuestion(String troopType, Question question, Question.Category category) {
            this.troopType = troopType;
            this.question = question;
            this.category = category;
        }
    }
    
    // Update the DuelResponseListener interface to match GameManager's callback
    public interface DuelResponseListener {
        // Manteniamo solo questo metodo, rimuoviamo l'altro
        void onDuelCompleted(boolean attackerWon, int attackerScore, int defenderScore);
    }
    
    /**
     * Constructor that accepts troops maps directly for more reliable question generation
     */
    public DuelPanel(Player attacker, Player defender,
                     QuestionDatabase questionDB, DuelResponseListener listener,
                     Map<String, Integer> attackerTroops, Map<String, Integer> defenderTroops) {
        // Validate inputs
        if (attacker == null || defender == null || questionDB == null) {
            throw new IllegalArgumentException("Invalid duel parameters");
        }
        
        _attacker = attacker;
        _defender = defender;
        _questionDB = questionDB;
        _responseListener = listener;
        
        setupPanel();
        
        // Use the provided troop maps directly
        prepareTroopBasedQuestions(attackerTroops, defenderTroops);
        showDifficultySelection();
    }
    
    /**
     * Costruttore completo che accetta territorio target e truppe per entrambi i giocatori
     */
    public DuelPanel(Territory targetTerritory, 
                     Player attacker, 
                     Player defender,
                     QuestionDatabase questionDB, 
                     DuelResponseListener listener,
                     Map<String, Integer> attackerTroops, 
                     Map<String, Integer> defenderTroops) 
    {
        // Validazione input
        if (attacker == null || defender == null || questionDB == null) {
            throw new IllegalArgumentException("Invalid duel parameters");
        }
        
        // Assegnazione campi
        _targetTerritory = targetTerritory;  // Aggiungi questa nuova variabile di classe
        _attacker = attacker;
        _defender = defender;
        _questionDB = questionDB;
        _responseListener = listener;
        
        // Configura l'interfaccia utente
        setupPanel();
        
        // Prepara le domande in base alle truppe
        prepareTroopBasedQuestions(attackerTroops, defenderTroops);
        showDifficultySelection();
    }
    
    private void setupPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyleUtils.BUTTON_COLOR);
        
        // Get screen size for responsive design
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int preferredWidth = Math.min(700, (int)(screenSize.width * 0.7));
        int preferredHeight = Math.min(500, (int)(screenSize.height * 0.7));
        
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    }
    
    /**
     * Prepare questions based on specified troop maps
     */
    private void prepareTroopBasedQuestions(Map<String, Integer> attackerTroops, Map<String, Integer> defenderTroops) {
        _attackerQuestions.clear();
        _defenderQuestions.clear();
        
        // Ensure maps are not null
        if (attackerTroops == null) attackerTroops = new HashMap<>();
        if (defenderTroops == null) defenderTroops = new HashMap<>();
        
        // If maps are empty, use fallback troops
        if (attackerTroops.isEmpty()) {
            System.out.println("Empty attacker troops map, using fallback troops");
            attackerTroops = new HashMap<>();
            attackerTroops.put("Archer", 3);
            attackerTroops.put("Barbarian", 2);
        }
        
        if (defenderTroops.isEmpty()) {
            System.out.println("Empty defender troops map, using fallback troops");
            defenderTroops = new HashMap<>();
            defenderTroops.put("Dragon", 1);
            defenderTroops.put("Horse", 1);
        }
        
        System.out.println("Using attacker troops: " + attackerTroops);
        System.out.println("Using defender troops: " + defenderTroops);
        
        // Create questions for attacker based on their troops
        for (Map.Entry<String, Integer> entry : attackerTroops.entrySet()) {
            createQuestionsForTroop(_attackerQuestions, entry.getKey(), entry.getValue());
        }
        
        // Create questions for defender based on their troops
        for (Map.Entry<String, Integer> entry : defenderTroops.entrySet()) {
            createQuestionsForTroop(_defenderQuestions, entry.getKey(), entry.getValue());
        }
        
        // Ensure there's at least one question per player
        if (_attackerQuestions.isEmpty()) {
            addDefaultQuestion(_attackerQuestions, "Barbarian");
        }
        if (_defenderQuestions.isEmpty()) {
            addDefaultQuestion(_defenderQuestions, "Barbarian");
        }
        
        // Initialize with the first question's troop type and category
        if (!_attackerQuestions.isEmpty()) {
            TroopQuestion firstQuestion = _attackerQuestions.get(0);
            _currentTroopType = firstQuestion.troopType;
            _currentCategory = firstQuestion.category;
        }
        
        System.out.println("Prepared " + _attackerQuestions.size() + " questions for attacker");
        System.out.println("Prepared " + _defenderQuestions.size() + " questions for defender");
    }

    
    
    private void createQuestionsForTroop(List<TroopQuestion> questionsList, String troopType, int count) {
        try {
            Troop troop = TroopFactory.getTroop(troopType);
            Question.Category category = troop.getCategory();
            
            // Generate the specified number of questions for this troop type
            for (int i = 0; i < count; i++) {
                Question question = _questionDB.getRandomQuestion(category);
                questionsList.add(new TroopQuestion(troopType, question, category));
            }
        } catch (Exception e) {
            System.err.println("Error creating questions for troop " + troopType + ": " + e);
        }
    }
    
    private void addDefaultQuestion(List<TroopQuestion> questionsList, String defaultTroopType) {
        try {
            Troop troop = TroopFactory.getTroop(defaultTroopType);
            Question.Category category = troop.getCategory();
            
            Question question = _questionDB.getRandomQuestion(category);
            questionsList.add(new TroopQuestion(defaultTroopType, question, category));
        } catch (Exception e) {
            System.err.println("Error creating default question: " + e);
        }
    }
    
    /**
     * Phase 1: Show difficulty selection for current player
     */
    private void showDifficultySelection() {
        if (_gameOver) return;
        
        // Check if current player has more questions
        if (_isAttackerTurn && _attackerQuestionIndex >= _attackerQuestions.size()) {
            // Attacker has no more questions, switch to defender
            _isAttackerTurn = false;
        } else if (!_isAttackerTurn && _defenderQuestionIndex >= _defenderQuestions.size()) {
            // Defender has no more questions, switch to attacker
            _isAttackerTurn = true;
        }
        
        // Check if both players are out of questions
        if (_attackerQuestionIndex >= _attackerQuestions.size() && 
            _defenderQuestionIndex >= _defenderQuestions.size()) {
            // End the game if no more questions for either player
            endGame();
            return;
        }
        
        // Update current question info
        updateCurrentQuestionInfo();
        
        // Display the difficulty selection screen
        removeAll();
        Player currentPlayer = _isAttackerTurn ? _attacker : _defender;
        JPanel difficultyPanel = createDifficultyPanel(currentPlayer);
        add(difficultyPanel, BorderLayout.CENTER);
        
        // Reset and start the timer
        _timeRemaining = 10;
        startCountdownTimer();
        
        revalidate();
        repaint();
    }
    
    private void updateCurrentQuestionInfo() {
        List<TroopQuestion> questions = _isAttackerTurn ? _attackerQuestions : _defenderQuestions;
        int index = _isAttackerTurn ? _attackerQuestionIndex : _defenderQuestionIndex;
        
        if (index < questions.size()) {
            TroopQuestion troopQuestion = questions.get(index);
            _currentTroopType = troopQuestion.troopType;
            _currentCategory = troopQuestion.category;
        }
    }
    
    private void startCountdownTimer() {
        // Cancel any existing timer
        if (_countdownTimer != null) {
            _countdownTimer.stop();
        }
        
        // Create new timer with 1-second intervals
        _countdownTimer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _timeRemaining--;
                
                if (_timerDisplay != null) {
                    _timerDisplay.setTime(_timeRemaining);
                }
                
                if (_timeRemaining <= 0) {
                    _countdownTimer.stop();
                    _selectedDifficulty = 3; // Default to medium difficulty
                    proceedToQuestion();
                }
            }
        });
        _countdownTimer.start();
    }
    
    /**
     * Create difficulty panel with dynamic sizing based on screen size
     */
    private JPanel createDifficultyPanel(Player currentPlayer) {
        // Get screen size for responsive design
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        boolean isSmallScreen = screenSize.height < 768;
        
        JPanel panel = new JPanel(new BorderLayout(isSmallScreen ? 5 : 10, isSmallScreen ? 5 : 10));
        panel.setBackground(new Color(40, 40, 70));
        int padding = isSmallScreen ? 8 : 15;
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyleUtils.GOLDEN_COLOR, 2),
            BorderFactory.createEmptyBorder(padding, padding, padding, padding)));
    
        // Header with player name and scores
        JPanel headerPanel = createHeaderPanel(currentPlayer, isSmallScreen);
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Main content panel with timer and buttons
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        
        // Timer sizing based on screen size
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        timerPanel.setOpaque(false);
        
        _timerDisplay = new CircularTimer(_timeRemaining);
        int timerSize = isSmallScreen ? 60 : 80;
        _timerDisplay.setPreferredSize(new Dimension(timerSize, timerSize));
        timerPanel.add(_timerDisplay);
        
        contentPanel.add(timerPanel);
        
        // Spacing between timer and buttons
        int spacing = isSmallScreen ? 5 : 15;
        contentPanel.add(Box.createRigidArea(new Dimension(0, spacing)));
        
        // Buttons
        JPanel buttonsPanel = createDifficultyButtons(isSmallScreen);
        
        // Wrap buttons in a flow layout panel for centering
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(buttonsPanel);
        
        contentPanel.add(buttonWrapper);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        // Category info at bottom
        JPanel categoryPanel = createCategoryInfo(isSmallScreen);
        panel.add(categoryPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createHeaderPanel(Player currentPlayer, boolean isSmallScreen) {
        JPanel headerPanel = new JPanel(new BorderLayout(0, isSmallScreen ? 3 : 8));
        headerPanel.setOpaque(false);
        
        // AGGIUNGI QUESTO - Territorio contestato
        JPanel territoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        territoryPanel.setOpaque(false);
        
        JLabel battleForLabel = new JLabel("Battle for ");
        battleForLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(isSmallScreen ? 16f : 20f));
        battleForLabel.setForeground(Color.WHITE);
        
        JLabel territoryNameLabel = new JLabel(_targetTerritory.getName());
        territoryNameLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, isSmallScreen ? 18f : 22f));
        territoryNameLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
        
        territoryPanel.add(battleForLabel);
        territoryPanel.add(territoryNameLabel);
        
        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        
        JLabel chooseLabel = new JLabel("Choose difficulty, ");
        chooseLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(isSmallScreen ? 18f : 24f));
        chooseLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
        
        JLabel nameLabel = new JLabel(currentPlayer.getName());
        nameLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, isSmallScreen ? 20f : 26f));
        nameLabel.setForeground(currentPlayer.getColor());
        
        titlePanel.add(chooseLabel);
        titlePanel.add(nameLabel);
        
        // Scores
        JPanel scoresPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scoresPanel.setOpaque(false);
        
        float scoreFontSize = isSmallScreen ? 16f : 20f;
        
        JLabel attackerLabel = new JLabel(_attacker.getName());
        attackerLabel.setForeground(_attacker.getColor());
        attackerLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(scoreFontSize));
        
        JLabel defenderLabel = new JLabel(_defender.getName());
        defenderLabel.setForeground(_defender.getColor());
        defenderLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(scoreFontSize));
        
        JLabel scoreLabel = new JLabel(": " + _attackerScore + " | ");
        scoreLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(scoreFontSize));
        scoreLabel.setForeground(Color.WHITE);
        
        JLabel score2Label = new JLabel(": " + _defenderScore);
        score2Label.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(scoreFontSize));
        score2Label.setForeground(Color.WHITE);
        
        scoresPanel.add(attackerLabel);
        scoresPanel.add(scoreLabel);
        scoresPanel.add(defenderLabel);
        scoresPanel.add(score2Label);
        
        headerPanel.add(territoryPanel, BorderLayout.NORTH);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(scoresPanel, BorderLayout.SOUTH);
        
        return headerPanel;
    }
    
    private JPanel createDifficultyButtons(boolean isSmallScreen) {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);
        
        // Button size based on screen
        int buttonWidth = isSmallScreen ? 240 : 300;
        int buttonHeight = isSmallScreen ? 36 : 45;
        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);
        
        for (int level = 1; level <= 5; level++) {
            String difficultyText = Question.getDifficultyText(level, true);
            JButton button = UIStyleUtils.createStyledButton(difficultyText);
            button.setPreferredSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setFont(button.getFont().deriveFont(Font.BOLD, isSmallScreen ? 16f : 20f));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            final int selectedLevel = level;
            button.addActionListener(_ -> {
                _selectedDifficulty = selectedLevel;
                if (_countdownTimer != null) {
                    _countdownTimer.stop();
                }
                proceedToQuestion();
            });
            
            buttonsPanel.add(button);
            
            // Spacing between buttons
            if (level < 5) {
                buttonsPanel.add(Box.createRigidArea(new Dimension(0, isSmallScreen ? 3 : 8)));
            }
        }
        
        return buttonsPanel;
    }
    
    private JPanel createCategoryInfo(boolean isSmallScreen) {
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        categoryPanel.setOpaque(false);
        
        // Display current troop and category
        String troopText = _currentTroopType != null ? _currentTroopType : "Unknown";
        String categoryName = _currentCategory != null ? _currentCategory.getDisplayName() : "Unknown";
        
        float infoFontSize = isSmallScreen ? 14f : 18f;
        
        JLabel troopLabel = new JLabel("Troop: " + troopText);
        troopLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, infoFontSize));
        troopLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
        
        JLabel separator = new JLabel(" | ");
        separator.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, infoFontSize));
        separator.setForeground(UIStyleUtils.GOLDEN_COLOR);
        
        JLabel categoryLabel = new JLabel("Category: " + categoryName);
        categoryLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, infoFontSize));
        categoryLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
        
        categoryPanel.add(troopLabel);
        categoryPanel.add(separator);
        categoryPanel.add(categoryLabel);
        
        return categoryPanel;
    }
    
    /**
     * Phase 2: Show the actual question
     */
    private void proceedToQuestion() {
        if (_gameOver) return;
        
        // Get current question based on player turn
        Question question = getCurrentQuestion();
        if (question == null) {
            endGame();
            return;
        }
        
        // Apply difficulty filtering to get appropriate question
        Question difficultyQuestion = getQuestionWithDifficulty(question);
        
        removeAll();
        
        Player currentPlayer = _isAttackerTurn ? _attacker : _defender;
        QuizPanel quizPanel = new QuizPanel(
            difficultyQuestion, 
            currentPlayer,
            _attacker,
            _defender,
            _attackerScore,
            _defenderScore,
            this::handleQuestionAnswered
        );
        
        add(quizPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private Question getCurrentQuestion() {
        // Get question for current player based on index
        if (_isAttackerTurn) {
            if (_attackerQuestionIndex < _attackerQuestions.size()) {
                return _attackerQuestions.get(_attackerQuestionIndex).question;
            }
        } else {
            if (_defenderQuestionIndex < _defenderQuestions.size()) {
                return _defenderQuestions.get(_defenderQuestionIndex).question;
            }
        }
        return null;
    }
    
    private Question getQuestionWithDifficulty(Question baseQuestion) {

        Question exactQuestion = _questionDB.getRandomQuestionWithExactDifficulty(
        baseQuestion.getCategory(), _selectedDifficulty);       
        return exactQuestion;
    }
    
    /**
     * Phase 3: Handle question response and show feedback
     */
    private void handleQuestionAnswered(boolean correct) {
        if (_gameOver) return;
        
        // Update score
        if (correct) {
            int points = _selectedDifficulty * 100;
            if (_isAttackerTurn) {
                _attackerScore += points;
            } else {
                _defenderScore += points;
            }
        }
        
        // Increment question index for current player
        if (_isAttackerTurn) {
            _attackerQuestionIndex++;
        } else {
            _defenderQuestionIndex++;
        }
        
        // Show feedback briefly
        showFeedback(correct);
        
        // Switch turns
        _isAttackerTurn = !_isAttackerTurn;
        
        // Continue or end game
        javax.swing.Timer nextTurnTimer = new javax.swing.Timer(2000, _ -> {
            // Check if there are more questions for either player
            if (_attackerQuestionIndex < _attackerQuestions.size() || 
                _defenderQuestionIndex < _defenderQuestions.size()) {
                showDifficultySelection();
            } else {
                endGame();
            }
        });
        nextTurnTimer.setRepeats(false);
        nextTurnTimer.start();
    }
    
    private void showFeedback(boolean correct) {
        removeAll();
        
        JPanel feedbackPanel = new JPanel(new BorderLayout());
        feedbackPanel.setBackground(correct ? new Color(0, 120, 0) : new Color(120, 0, 0));
        feedbackPanel.setBorder(BorderFactory.createLineBorder(UIStyleUtils.GOLDEN_COLOR, 3));
        
        String message = correct ? 
            "Correct! +" + (_selectedDifficulty * 100) + " points!" : 
            "Wrong answer!";
        
        JLabel feedbackLabel = new JLabel(message, JLabel.CENTER);
        feedbackLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(32f));
        feedbackLabel.setForeground(Color.WHITE);
        
        // Use colored player names in score display
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scorePanel.setOpaque(false);
        
        JLabel attackerName = new JLabel(_attacker.getName());
        attackerName.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(28f));
        attackerName.setForeground(_attacker.getColor());
        
        JLabel attackerScore = new JLabel(": " + _attackerScore + "  |  ");
        attackerScore.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(28f));
        attackerScore.setForeground(Color.WHITE);
        
        JLabel defenderName = new JLabel(_defender.getName());
        defenderName.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(28f));
        defenderName.setForeground(_defender.getColor());
        
        JLabel defenderScore = new JLabel(": " + _defenderScore);
        defenderScore.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(28f));
        defenderScore.setForeground(Color.WHITE);
        
        scorePanel.add(attackerName);
        scorePanel.add(attackerScore);
        scorePanel.add(defenderName);
        scorePanel.add(defenderScore);
        
        feedbackPanel.add(feedbackLabel, BorderLayout.CENTER);
        feedbackPanel.add(scorePanel, BorderLayout.SOUTH);
        
        add(feedbackPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    /**
     * Phase 4: End game and show results
     */
    private void endGame() {
        _gameOver = true;
        boolean attackerWon = determineWinner();
        showFinalResults(attackerWon);
    }
    
    private boolean determineWinner() {
        if (_attackerScore == _defenderScore) {
            // This is a tie - return false for backward compatibility
            // but the tie will be handled properly in calculateResultingTroops
            return false;
        }
        return _attackerScore > _defenderScore;
    }
    
    private void showFinalResults(boolean attackerWon) {
        _gameOver = true;
        
        removeAll();
        
        JPanel resultsPanel = new JPanel(new BorderLayout(20, 20));
        resultsPanel.setBackground(new Color(40, 40, 70));
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyleUtils.GOLDEN_COLOR, 2),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)));
        
        // Territorio contestato
        JLabel territoryLabel = new JLabel("Territory: " + _targetTerritory.getName(), JLabel.CENTER);
        territoryLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(28f));
        territoryLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
        
        // Winner announcement
        Player winner = attackerWon ? _attacker : _defender;
        JLabel winnerLabel = new JLabel(winner.getName() + " wins the duel!", JLabel.CENTER);
        winnerLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(36f));
        winnerLabel.setForeground(winner.getColor());
        
        // Aggiungi territorio e vincitore al pannello principale
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        
        territoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(territoryLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        headerPanel.add(winnerLabel);
        
        // Final scores with colored player names
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scorePanel.setOpaque(false);
        
        JLabel finalScoreLabel = new JLabel("Final Score: ", JLabel.CENTER);
        finalScoreLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(30f));
        finalScoreLabel.setForeground(Color.WHITE);
        
        JLabel attackerName = new JLabel(_attacker.getName());
        attackerName.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(30f));
        attackerName.setForeground(_attacker.getColor());
        
        JLabel scoreMiddle = new JLabel(" " + _attackerScore + " - " + _defenderScore + " ");
        scoreMiddle.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(30f));
        scoreMiddle.setForeground(Color.WHITE);
        
        JLabel defenderName = new JLabel(_defender.getName());
        defenderName.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(30f));
        defenderName.setForeground(_defender.getColor());
        
        scorePanel.add(finalScoreLabel);
        scorePanel.add(attackerName);
        scorePanel.add(scoreMiddle);
        scorePanel.add(defenderName);
        
        // Continue button
        JButton continueButton = UIStyleUtils.createStyledButton("Continue");
        continueButton.setFont(continueButton.getFont().deriveFont(24f));
        continueButton.addActionListener(_ -> {
            if (_responseListener != null) {
                // Call the new method with scores
                _responseListener.onDuelCompleted(attackerWon, _attackerScore, _defenderScore);
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(continueButton);
        
        JPanel contentPanel = new JPanel(new GridLayout(2, 1, 0, 30));
        contentPanel.setOpaque(false);
        contentPanel.add(headerPanel);
        contentPanel.add(scorePanel);
        
        resultsPanel.add(contentPanel, BorderLayout.CENTER);
        resultsPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(resultsPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}