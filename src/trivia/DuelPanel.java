package trivia;

import gameSetup.*;

import map.*;

import troops.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.util.List;


/*
 * manages player vs player trivia duels where questions are based on troops
 */


public class DuelPanel extends JPanel 
{
    // represents a question tied to a specific troop type
    private static class TroopQuestion 
    {
        final String troopType;
        final Question.Category category;
        
        TroopQuestion(String troopType, Question.Category category) 
        {
            this.troopType = troopType;
            this.category = category;
        }
    }
    
    // interface to match GameManager's callback
    public interface DuelResponseListener 
    {
        void onDuelCompleted(int attackerScore, int defenderScore);
    }

    // core game data
    private final Player _attacker;
    private final Player _defender;
    private final QuestionDatabase _questionDB;
    private final DuelResponseListener _responseListener;
    
    // game state
    private int _attackerScore = 0;
    private int _defenderScore = 0;
    private boolean _isAttackerTurn = true;
    private int _selectedDifficulty = 3;
    private boolean _gameOver = false;
    
    // question management by player troops
    private List<TroopQuestion> _attackerQuestions = new ArrayList<>();
    private List<TroopQuestion> _defenderQuestions = new ArrayList<>();
    private int _attackerQuestionIndex = 0;
    private int _defenderQuestionIndex = 0;
    
    // timer components
    private javax.swing.Timer _countdownTimer;
    private int _timeRemaining = 10;
    private CircularTimer _timerDisplay;
    
    // current question info
    private String _currentTroopType;
    private Question.Category _currentCategory;
    
    // target territory for the duel
    private Territory _targetTerritory;

    
    // ctor
    public DuelPanel(Territory targetTerritory, 
                     Player attacker, 
                     Player defender,
                     QuestionDatabase questionDB, 
                     DuelResponseListener listener,
                     Map<String, Integer> attackerTroops, 
                     Map<String, Integer> defenderTroops) 
    {
        _targetTerritory = targetTerritory;
        _attacker = attacker;
        _defender = defender;
        _questionDB = questionDB;
        _responseListener = listener;
        
        setupPanel();
        
        prepareTroopBasedQuestions(attackerTroops, defenderTroops);
        showDifficultySelection();
    }


    // sets up the panel with a responsive layout and background color
    private void setupPanel() 
    {
        setLayout(new BorderLayout());
        setBackground(UIStyleUtils.BUTTON_COLOR);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int preferredWidth = Math.min(700, (int)(screenSize.width * 0.7));
        int preferredHeight = Math.min(500, (int)(screenSize.height * 0.7));
        
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        
        addKeyListener(new KeyAdapter() 
        {
            @Override
            public void keyPressed(KeyEvent e) 
            {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_G || 
                    keyCode == KeyEvent.VK_I || keyCode == KeyEvent.VK_F11 || 
                    keyCode == KeyEvent.VK_ESCAPE) 
                {
                    e.consume();
                }
            }
        });
        setFocusable(true);
    }
    
    // prepares questions based on the troops available for each player
    private void prepareTroopBasedQuestions(Map<String, Integer> attackerTroops, Map<String, Integer> defenderTroops) {
        _attackerQuestions.clear();
        _defenderQuestions.clear();
        
        System.out.println("Using attacker troops: " + attackerTroops);
        System.out.println("Using defender troops: " + defenderTroops);
        
        // create questions for attacker based on their troops
        for (Map.Entry<String, Integer> entry : attackerTroops.entrySet()) 
        {
            createQuestionsForTroop(_attackerQuestions, entry.getKey(), entry.getValue());
        }
        
        // create questions for defender based on their troops
        for (Map.Entry<String, Integer> entry : defenderTroops.entrySet()) 
        {
            createQuestionsForTroop(_defenderQuestions, entry.getKey(), entry.getValue());
        }
        
        // initialize with the first question's troop type and category
        if (!_attackerQuestions.isEmpty()) 
        {
            TroopQuestion firstQuestion = _attackerQuestions.get(0);
            _currentTroopType = firstQuestion.troopType;
            _currentCategory = firstQuestion.category;
        }
        
        System.out.println("Prepared " + _attackerQuestions.size() + " questions for attacker");
        System.out.println("Prepared " + _defenderQuestions.size() + " questions for defender");
    }


    // creates questions for a specific troop type and adds them to the provided list
    private void createQuestionsForTroop(List<TroopQuestion> questionsList, String troopType, int count) 
    {
        try 
        {
            Troop troop = TroopFactory.getTroop(troopType);
            Question.Category category = troop.getCategory();
            
            // generate the specified number of questions for this troop type
            for (int i = 0; i < count; i++) 
            {
                questionsList.add(new TroopQuestion(troopType, category));
            }
        } 
        catch (Exception e) 
        {
            System.err.println("Error creating questions for troop " + troopType + ": " + e);
        }
    }


    // phase 1: show difficulty selection screen
    private void showDifficultySelection() 
    {
        if (_gameOver) {return;}

        // check if current player has more questions
        if (_isAttackerTurn && _attackerQuestionIndex >= _attackerQuestions.size()) 
        {
            _isAttackerTurn = false;
        } 
        else if (!_isAttackerTurn && _defenderQuestionIndex >= _defenderQuestions.size()) 
        {
            _isAttackerTurn = true;
        }

        // check if both players have no more questions
        if (_attackerQuestionIndex >= _attackerQuestions.size() && 
            _defenderQuestionIndex >= _defenderQuestions.size()) 
        {
            endGame();
            return;
        }
        
        updateCurrentQuestionInfo();

        removeAll();
        Player currentPlayer = _isAttackerTurn ? _attacker : _defender;
        JPanel difficultyPanel = createDifficultyPanel(currentPlayer);
        add(difficultyPanel, BorderLayout.CENTER);
        
        _timeRemaining = 10;
        startCountdownTimer();
        
        revalidate();
        repaint();
    }
    

    // phase 2: proceed to question based on selected difficulty
    private void proceedToQuestion() 
    {
        // get current question based on player turn
        TroopQuestion troopQuestion;
        if (_isAttackerTurn) 
        {
            if (_attackerQuestionIndex >= _attackerQuestions.size()) {
                endGame();
                return;
            }
            troopQuestion = _attackerQuestions.get(_attackerQuestionIndex);
        } 
        else 
        {
            if (_defenderQuestionIndex >= _defenderQuestions.size()) {
                endGame();
                return;
            }
            troopQuestion = _defenderQuestions.get(_defenderQuestionIndex);
        }
        
        // get question with selected difficulty
        Question question = _questionDB.getRandomQuestion(troopQuestion.category, _selectedDifficulty);
        
        removeAll();
        
        Player currentPlayer = _isAttackerTurn ? _attacker : _defender;
        QuizPanel quizPanel = new QuizPanel(
            question, 
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


    // phase 3: handle question answered
    private void handleQuestionAnswered(boolean correct) 
    {
        // update scores
        if (correct) 
        {
            int points = _selectedDifficulty * 100;
            if (_isAttackerTurn) {_attackerScore += points;} 
            else {_defenderScore += points;}
        }

        // update player statistics
        Player currentPlayer = _isAttackerTurn ? _attacker : _defender;
        TroopQuestion troopQuestion = _isAttackerTurn ? 
            _attackerQuestions.get(_attackerQuestionIndex) : 
            _defenderQuestions.get(_defenderQuestionIndex);
        
        currentPlayer.updateStatistics(correct, troopQuestion.category);

        // increment question index
        if (_isAttackerTurn) {_attackerQuestionIndex++;} 
        else {_defenderQuestionIndex++;}
        
        showFeedback(correct);
        _isAttackerTurn = !_isAttackerTurn;

        javax.swing.Timer nextTurnTimer = new javax.swing.Timer(2000, _ -> 
        {
            if (_attackerQuestionIndex < _attackerQuestions.size() || 
                _defenderQuestionIndex < _defenderQuestions.size()) 
            {
                showDifficultySelection();
            } 
            else 
            {
                endGame();
            }
        });
        nextTurnTimer.setRepeats(false);
        nextTurnTimer.start();
    }


    // phase 4: end the game and show final results
    private void endGame() 
    {
        _gameOver = true;
        boolean attackerWon = _attackerScore > _defenderScore;
        showFinalResults(attackerWon);
    }


    // creates the difficulty selection panel
    private JPanel createDifficultyPanel(Player currentPlayer) 
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        boolean isSmallScreen = screenSize.height < 1000;
        
        JPanel panel = new JPanel(new BorderLayout(isSmallScreen ? 3 : 10, isSmallScreen ? 3 : 10));
        panel.setBackground(new Color(40, 40, 70));
        int padding = isSmallScreen ? 0 : 15;
        panel.setBorder(BorderFactory.createCompoundBorder
        (
            BorderFactory.createLineBorder(UIStyleUtils.GOLDEN_COLOR, 2),
            BorderFactory.createEmptyBorder(padding, padding, padding, padding)
        ));
    
        // header with player name and scores
        JPanel headerPanel = createHeaderPanel(currentPlayer, isSmallScreen);
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // main content panel with timer and buttons
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        
        // timer sizing based on screen size
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        timerPanel.setOpaque(false);
        timerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        _timerDisplay = new CircularTimer(_timeRemaining);
        int timerSize = isSmallScreen ? 60 : 120;
        _timerDisplay.setPreferredSize(new Dimension(timerSize, timerSize));
        timerPanel.add(_timerDisplay);
        
        contentPanel.add(timerPanel);
        
        // buttons
        JPanel buttonsPanel = createDifficultyButtons(isSmallScreen);
        
        // wrap buttons in a flow layout panel for centering
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(buttonsPanel);
        
        contentPanel.add(buttonWrapper);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        // category info at bottom
        JPanel categoryPanel = createCategoryInfo(isSmallScreen);
        panel.add(categoryPanel, BorderLayout.SOUTH);
        
        return panel;
    }


    // creates the header panel with player information
    private JPanel createHeaderPanel(Player currentPlayer, boolean isSmallScreen) 
    {
        JPanel headerPanel = new JPanel(new BorderLayout(0, isSmallScreen ? 3 : 8));
        headerPanel.setOpaque(false);

        JPanel territoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        territoryPanel.setOpaque(false);
        
        JLabel battleForLabel = new JLabel("Battle for ");
        battleForLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(isSmallScreen ? 12f : 20f));
        battleForLabel.setForeground(Color.WHITE);
        
        JLabel territoryNameLabel = new JLabel(_targetTerritory.getName());
        territoryNameLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, isSmallScreen ? 14f : 22f));
        territoryNameLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
        
        territoryPanel.add(battleForLabel);
        territoryPanel.add(territoryNameLabel);
        
        // title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        
        JLabel chooseLabel = new JLabel("Choose difficulty, ");
        chooseLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(isSmallScreen ? 14f : 24f));
        chooseLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
        
        JLabel nameLabel = new JLabel(currentPlayer.getName());
        nameLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, isSmallScreen ? 16f : 26f));
        nameLabel.setForeground(currentPlayer.getColor());
        
        titlePanel.add(chooseLabel);
        titlePanel.add(nameLabel);
        
        // scores
        JPanel scoresPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scoresPanel.setOpaque(false);
        
        float scoreFontSize = isSmallScreen ? 12f : 20f;
        
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


    // creates the difficulty buttons panel
    private JPanel createDifficultyButtons(boolean isSmallScreen) 
    {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);
        
        // button size based on screen
        int buttonWidth = isSmallScreen ? 240 : 300;
        int buttonHeight = isSmallScreen ? 24 : 45;
        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);
        
        for (int level = 1; level <= 5; level++) 
        {
            String difficultyText = Question.getDifficultyText(level, true);
            JButton button = UIStyleUtils.createStyledButton(difficultyText);
            button.setPreferredSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setFont(button.getFont().deriveFont(Font.BOLD, isSmallScreen ? 16f : 20f));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            final int selectedLevel = level;
            button.addActionListener(_ -> 
            {
                _selectedDifficulty = selectedLevel;
                if (_countdownTimer != null) 
                {
                    _countdownTimer.stop();
                }
                proceedToQuestion();
            });
            
            buttonsPanel.add(button);
            
            if (level < 5) 
            {
                buttonsPanel.add(Box.createRigidArea(new Dimension(0, isSmallScreen ? 3 : 8)));
            }
        }
        return buttonsPanel;
    }


    // creates the category info panel
    private JPanel createCategoryInfo(boolean isSmallScreen) 
    {
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        categoryPanel.setOpaque(false);
        
        // displays current troop type and category
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


    // feedback display after answering a question
    private void showFeedback(boolean correct) 
    {
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


    // displays the final results after the duel
    private void showFinalResults(boolean attackerWon) 
    {
        _gameOver = true;

        removeAll();

        JPanel resultsPanel = new JPanel(new BorderLayout(20, 20));
        resultsPanel.setBackground(new Color(40, 40, 70));
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyleUtils.GOLDEN_COLOR, 2),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)));
        
        JLabel territoryLabel = new JLabel("Territory: " + _targetTerritory.getName(), JLabel.CENTER);
        territoryLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(28f));
        territoryLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
        
        // winner announcement
        Player winner = attackerWon ? _attacker : _defender;
        JLabel winnerLabel = new JLabel(winner.getName() + " wins the duel!", JLabel.CENTER);
        winnerLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(36f));
        winnerLabel.setForeground(winner.getColor());

        // add territory and winner to the header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        
        territoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(territoryLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        headerPanel.add(winnerLabel);
        
        // final scores with colored player names
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
        
        // continue button
        JButton continueButton = UIStyleUtils.createStyledButton("Continue");
        continueButton.setFont(continueButton.getFont().deriveFont(24f));
        continueButton.addActionListener(_ -> 
        {
            if (_responseListener != null) 
            {
                _responseListener.onDuelCompleted(_attackerScore, _defenderScore);
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


    // updates the current question info based on the player's turn
    private void updateCurrentQuestionInfo() 
    {
        List<TroopQuestion> questions = _isAttackerTurn ? _attackerQuestions : _defenderQuestions;
        int index = _isAttackerTurn ? _attackerQuestionIndex : _defenderQuestionIndex;
        
        if (index < questions.size()) 
        {
            TroopQuestion troopQuestion = questions.get(index);
            _currentTroopType = troopQuestion.troopType;
            _currentCategory = troopQuestion.category;
        }
    }

    // starts the countdown timer
    private void startCountdownTimer() 
    {
        if (_countdownTimer != null)
         {
            _countdownTimer.stop();
        }
        
        // create new timer with 1-second intervals
        _countdownTimer = new javax.swing.Timer(1000, new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _timeRemaining--;
                
                if (_timerDisplay != null) 
                {
                    _timerDisplay.setTime(_timeRemaining);
                }
                
                if (_timeRemaining <= 0) 
                {
                    _countdownTimer.stop();
                    _selectedDifficulty = 3;
                    proceedToQuestion();
                }
            }
        });
        _countdownTimer.start();
    }
}