package trivia;

import gameSetup.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;


/*
 * QuizPanel displays trivia questions in different game scenarios
 * handles territory-based questions, duel questions, and standalone questions
 */


public class QuizPanel extends JPanel 
{
    // interface to match callback pattern
    public interface QuestionResponseListener 
    {
        void onQuestionAnswered(boolean correct);
    }
    
    // constants and configuration
    private static final int DEFAULT_TIME_SECONDS = 20;
    private static final Color SELECTED_COLOR = new Color(255, 215, 0);  // gold when selected
    private static final Color CORRECT_COLOR = new Color(50, 205, 50);   // green when correct
    private static final Color INCORRECT_COLOR = new Color(220, 20, 60); // red when incorrect
    
    // core components
    private final Question _question;
    private final QuestionResponseListener _responseListener;
    
    // ui components
    private CircularTimer _circularTimer;
    private JPanel _timerPanel;
    private CustomAnswerButton[] _answerButtons;
    private javax.swing.Timer _countdownTimer;
    
    // quiz state
    private int _secondsRemaining = DEFAULT_TIME_SECONDS;
    private boolean _answered = false;
    private boolean _answerCorrect = false;
    private int _selectedIndex = -1;
    
    // player information
    private Player _currentPlayer;
    private Player _attacker;
    private Player _defender;
    private int _attackerScore;
    private int _defenderScore;
    
    // prevents duplicate callback firing
    private boolean _callbackFired = false;
    
    
    // ctor for duel question
    public QuizPanel(Question question, Player currentPlayer, Player attacker, Player defender, 
                     int attackerScore, int defenderScore, QuestionResponseListener listener) 
    {
        _question = question;
        _currentPlayer = currentPlayer;
        _attacker = attacker;
        _defender = defender;
        _attackerScore = attackerScore;
        _defenderScore = defenderScore;
        _responseListener = listener;

        initializeAndSetup();
    }
    
    
    // ctor for standalone question
    public QuizPanel(Question question, QuestionResponseListener listener, Player currentPlayer) 
    {
        _question = question;
        _responseListener = listener;
        _currentPlayer = currentPlayer;
        
        initializeAndSetup();
    }
    
    
    // initializes components and sets up the ui
    private void initializeAndSetup() 
    {
        setupMainLayout();
        createTopSection();
        createQuestionSection();
        createAnswerSection();
        startQuizTimer();
        
        // ensure this panel doesn't process game control keys
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
    
    
    // sets up the main panel layout
    private void setupMainLayout() 
    {
        setLayout(new BorderLayout(15, 15));
        setBackground(UIStyleUtils.BUTTON_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }
    
    
    // creates the top section with player info, timer, and category
    private void createTopSection() 
    {
        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.setPreferredSize(new Dimension(getWidth(), 80));
        topPanel.setOpaque(false);
        
        // left: player information
        topPanel.add(createPlayerInfoPanel());
        
        // center: timer
        topPanel.add(createTimerPanel());
        
        // right: category information
        topPanel.add(createCategoryPanel());
        
        // bottom section for scores (if in duel mode)
        JPanel scoreSection = createScoreSection();
        
        if (scoreSection != null) 
        {
            JPanel combinedTop = new JPanel(new BorderLayout());
            combinedTop.setOpaque(false);
            combinedTop.add(topPanel, BorderLayout.NORTH);
            combinedTop.add(scoreSection, BorderLayout.SOUTH);
            add(combinedTop, BorderLayout.NORTH);
        } 
        else 
        {
            add(topPanel, BorderLayout.NORTH);
        }
    }
    
    
    // creates the player information panel
    private JPanel createPlayerInfoPanel() 
    {
        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 25));
        playerPanel.setOpaque(false);
        
        if (_currentPlayer != null) 
        {
            JLabel prefixLabel = new JLabel("Question for ");
            prefixLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 24f));
            prefixLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
            
            JLabel nameLabel = new JLabel(_currentPlayer.getName());
            nameLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 24f));
            nameLabel.setForeground(_currentPlayer.getColor());
            
            playerPanel.add(prefixLabel);
            playerPanel.add(nameLabel);
        }
        
        return playerPanel;
    }
    
    
    // creates the timer panel
    private JPanel createTimerPanel() 
    {
        _timerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        _timerPanel.setOpaque(false);
        _timerPanel.setPreferredSize(new Dimension(120, 80));
        
        _circularTimer = new CircularTimer(_secondsRemaining);
        _circularTimer.setPreferredSize(new Dimension(70, 70));
        _timerPanel.add(_circularTimer);
        
        return _timerPanel;
    }
    
    
    // creates the category panel
    private JPanel createCategoryPanel() 
    {
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 25));
        categoryPanel.setOpaque(false);
        
        JLabel categoryLabel = new JLabel(_question.getCategory().getDisplayName());
        categoryLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 24f));
        categoryLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
        categoryPanel.add(categoryLabel);
        
        return categoryPanel;
    }
    
    
    // creates the score section for duel mode or difficulty panel for standalone
    private JPanel createScoreSection() 
    {
        if (_attacker == null || _defender == null) 
        {
            // create difficulty panel for non-duel scenarios
            JPanel difficultyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            difficultyPanel.setOpaque(false);
            
            String difficultyText = "Difficulty: " + Question.getDifficultyText(_question.getDifficulty(), false);
            JLabel difficultyLabel = new JLabel(difficultyText);
            difficultyLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 22f));
            difficultyLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
            difficultyPanel.add(difficultyLabel);
            
            return difficultyPanel;
        }
        
        // create duel score panel
        JPanel scorePanel = new JPanel(new BorderLayout(10, 0));
        scorePanel.setOpaque(false);
        
        // attacker score (left)
        JPanel attackerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        attackerPanel.setOpaque(false);
        JLabel attackerLabel = new JLabel(_attacker.getName() + ": " + _attackerScore);
        attackerLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 20f));
        attackerLabel.setForeground(_attacker.getColor());
        attackerPanel.add(attackerLabel);
        
        // difficulty (center)
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        String difficultyText = "Difficulty: " + Question.getDifficultyText(_question.getDifficulty(), false);
        JLabel difficultyLabel = new JLabel(difficultyText);
        difficultyLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 22f));
        difficultyLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
        centerPanel.add(difficultyLabel);
        
        // defender score (right)
        JPanel defenderPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        defenderPanel.setOpaque(false);
        JLabel defenderLabel = new JLabel(_defender.getName() + ": " + _defenderScore);
        defenderLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 20f));
        defenderLabel.setForeground(_defender.getColor());
        defenderPanel.add(defenderLabel);
        
        scorePanel.add(attackerPanel, BorderLayout.WEST);
        scorePanel.add(centerPanel, BorderLayout.CENTER);
        scorePanel.add(defenderPanel, BorderLayout.EAST);
        
        return scorePanel;
    }
    
    
    // creates the question section with text display
    private void createQuestionSection() 
    {
        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createCompoundBorder
        (
            BorderFactory.createLineBorder(UIStyleUtils.GOLDEN_COLOR, 4),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        questionPanel.setBackground(new Color(40, 40, 70));
        questionPanel.setPreferredSize(new Dimension(700, 250));
        
        // create responsive question text display
        JTextPane questionText = new JTextPane();
        questionText.setContentType("text/plain");
        questionText.setText(_question.getText());
        questionText.setFont(calculateQuestionFont());
        questionText.setForeground(Color.WHITE);
        questionText.setBackground(new Color(40, 40, 70));
        questionText.setEditable(false);
        questionText.setFocusable(false);
        questionText.setMargin(new Insets(5, 5, 5, 5));
        
        JScrollPane scrollPane = new JScrollPane(questionText);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        questionPanel.add(scrollPane, BorderLayout.CENTER);
        add(questionPanel, BorderLayout.CENTER);
    }
    
    
    // calculates responsive font size for question text
    private Font calculateQuestionFont() 
    {
        // responsive font sizing based on screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        if (screenSize.width > 1600) 
        {
            return UIStyleUtils.PROMPT_FONT.deriveFont(48f);
        } 
        else if (screenSize.width > 1200) 
        {
            return UIStyleUtils.PROMPT_FONT.deriveFont(42f);
        } 
        else 
        {
            return UIStyleUtils.PROMPT_FONT.deriveFont(32f);
        }
    }
    
    
    // creates the answer buttons section
    private void createAnswerSection() 
    {
        JPanel answersPanel = new JPanel();
        answersPanel.setLayout(new GridLayout(0, 1, 10, 10));
        answersPanel.setOpaque(false);
        
        List<String> options = _question.getOptions();
        _answerButtons = new CustomAnswerButton[options.size()];
        
        for (int i = 0; i < options.size(); i++) 
        {
            final int index = i;
            _answerButtons[i] = new CustomAnswerButton(options.get(i));
            _answerButtons[i].addActionListener(_ -> handleAnswerSelection(index));
            answersPanel.add(_answerButtons[i]);
        }
        
        add(answersPanel, BorderLayout.EAST);
    }
    
    
    // starts the quiz timer
    private void startQuizTimer() 
    {
        if (_circularTimer != null) 
        {
            _circularTimer.setTime(_secondsRemaining);
        }
        
        _countdownTimer = new javax.swing.Timer(1000, _ -> 
        {
            _secondsRemaining--;
            
            if (_circularTimer != null) 
            {
                _circularTimer.setTime(_secondsRemaining);
            }
            
            if (_secondsRemaining <= 0) 
            {
                handleTimeExpired();
            }
        });
        
        _countdownTimer.start();
    }
    
    
    // handles answer selection by user
    private void handleAnswerSelection(int index) 
    {
        if (_answered) {return;}
        
        _answered = true;
        _selectedIndex = index;
        stopTimer();
        
        // disable hover effects on all buttons
        for (CustomAnswerButton button : _answerButtons) 
        {
            button.disableHover();
        }
        
        // show selection in gold (quiz show style)
        _answerButtons[_selectedIndex].lockColor(SELECTED_COLOR, Color.BLACK);
        
        // wait 2 seconds before revealing if answer is correct
        javax.swing.Timer selectionDelay = new javax.swing.Timer(2000, _ -> 
        {
            processAnswer(_question.getOptions().get(_selectedIndex));
        });
        selectionDelay.setRepeats(false);
        selectionDelay.start();
    }
    
    
    // handles when time expires
    private void handleTimeExpired() 
    {
        stopTimer();
        _answered = true;
        
        // disable hover on all buttons
        for (CustomAnswerButton button : _answerButtons) 
        {
            button.disableHover();
        }
        
        // replace timer with "time's up!" message
        _timerPanel.removeAll();
        JLabel timeUpLabel = new JLabel("Time's up!", JLabel.CENTER);
        timeUpLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 24f));
        timeUpLabel.setForeground(Color.RED);
        timeUpLabel.setPreferredSize(new Dimension(120, 50));
        _timerPanel.add(timeUpLabel);
        _timerPanel.revalidate();
        _timerPanel.repaint();
        
        // show correct answer after brief delay
        javax.swing.Timer resultDelay = new javax.swing.Timer(1000, _ -> 
        {
            highlightCorrectAnswer();
            finishQuizWithDelay(false, 2000);
        });
        resultDelay.setRepeats(false);
        resultDelay.start();
    }
    
    
    // processes the selected answer and shows result
    private void processAnswer(String selectedAnswer) 
    {
        boolean correct = _question.checkAnswer(selectedAnswer);
        _answerCorrect = correct;
        
        if (correct) 
        {
            _answerButtons[_selectedIndex].lockColor(CORRECT_COLOR, Color.WHITE);
        } 
        else 
        {
            _answerButtons[_selectedIndex].lockColor(INCORRECT_COLOR, Color.WHITE);
            highlightCorrectAnswer();
        }
        
        finishQuizWithDelay(_answerCorrect, 5000);
    }
    
    
    // highlights the correct answer button
    private void highlightCorrectAnswer() 
    {
        String correctAnswer = _question.getCorrectAnswer();
        for (int i = 0; i < _answerButtons.length; i++) 
        {
            if (_question.getOptions().get(i).equals(correctAnswer) && i != _selectedIndex) 
            {
                _answerButtons[i].lockColor(CORRECT_COLOR, Color.WHITE);
                break;
            }
        }
    }
    
    
    // finishes the quiz after a delay and fires callback
    private void finishQuizWithDelay(boolean wasCorrect, int delayMs) 
    {
        javax.swing.Timer finishTimer = new javax.swing.Timer(delayMs, _ -> 
        {
            if (_responseListener != null) 
            {
                // prevent duplicate callback firing
                if (_callbackFired) 
                {
                    System.out.println("WARNING: Avoiding duplicate callback in QuizPanel");
                    return;
                }
                _callbackFired = true;
                _responseListener.onQuestionAnswered(wasCorrect);
            }
        });
        finishTimer.setRepeats(false);
        finishTimer.start();
    }
    
    
    // stops the countdown timer
    private void stopTimer() 
    {
        if (_countdownTimer != null && _countdownTimer.isRunning()) 
        {
            _countdownTimer.stop();
        }
        
        if (_circularTimer != null) 
        {
            _circularTimer.setOpacity(0.0f); // make transparent but maintain space
        }
    }
    
    
    // returns whether the answer was correct
    public boolean isAnswerCorrect() 
    {
        return _answerCorrect;
    }
}