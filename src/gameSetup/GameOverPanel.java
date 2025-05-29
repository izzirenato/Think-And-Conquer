package gameSetup;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import trivia.Question;

public class GameOverPanel extends JPanel implements GameLauncher.Scalable
{
    private Player _winner;
    private List<Player> _players;

    public GameOverPanel(Player winner, List<Player> players)
    {
        _winner = winner;
        _players = players;

        setupPanel();
    }

    private void setupPanel()
    {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setBackground(UIStyleUtils.BUTTON_COLOR); // Sfondo marroncino come al solito

        // Winner announcement - big and prominent
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main content with rankings and stats
        JPanel contentPanel = createContentPanel();
        add(contentPanel, BorderLayout.CENTER);

        // Styled buttons
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // Winner announcement with player's color
        JLabel winnerLabel = UIStyleUtils.createStyledLabel(
            _winner.getName() + " WON!", 
            UIStyleUtils.TITLE_FONT.deriveFont(48f)
        );
        winnerLabel.setForeground(_winner.getColor());
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(winnerLabel);

        panel.add(Box.createVerticalStrut(10));

        // Crown and celebration
        JLabel crownLabel = new JLabel("Game Summary");
        crownLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 28f)); // Increased from 24f
        crownLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
        crownLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(crownLabel);

        return panel;
    }

    private JPanel createContentPanel()
    {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setOpaque(false);

        // Left side: Rankings
        JPanel rankingsPanel = createRankingsPanel();
        panel.add(rankingsPanel);

        // Right side: Winner stats
        JPanel statsPanel = createWinnerStatsPanel();
        panel.add(statsPanel);

        return panel;
    }

    private JPanel createRankingsPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 250, 205)); // Stesso sfondo giallino delle stats
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Title
        JLabel titleLabel = new JLabel("Final Rankings");
        titleLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 24f));
        titleLabel.setForeground(UIStyleUtils.BUTTON_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createVerticalStrut(15));

        // Sort players by score
        _players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));

        // Add player rankings
        for (int i = 0; i < _players.size(); i++) {
            Player player = _players.get(i);
            
            String position = switch (i) {
                case 0 -> "1°";
                case 1 -> "2°";
                case 2 -> "3°";
                default -> String.valueOf(i + 1) + "°";
            };
            
            // Use BorderLayout per separare meglio i componenti
            JPanel playerPanel = new JPanel(new BorderLayout(10, 0));
            playerPanel.setOpaque(false);
            playerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
            
            // Left side: position + name
            JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            leftPanel.setOpaque(false);
            
            JLabel positionLabel = new JLabel(position);
            positionLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 20f));
            positionLabel.setPreferredSize(new Dimension(40, 30));
            
            JLabel nameLabel = new JLabel(player.getName());
            nameLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(20f));
            nameLabel.setForeground(player.getColor());
            
            leftPanel.add(positionLabel);
            leftPanel.add(nameLabel);
            
            // Right side: score
            JLabel scoreLabel = new JLabel(player.getScore() + " pts");
            scoreLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(18f));
            scoreLabel.setForeground(UIStyleUtils.BUTTON_COLOR);
            scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            
            playerPanel.add(leftPanel, BorderLayout.WEST);
            playerPanel.add(scoreLabel, BorderLayout.EAST);
            
            panel.add(playerPanel);
            panel.add(Box.createVerticalStrut(5)); // Small gap between rows
        }

        return panel;
    }

    // Nuovo metodo che usa BorderLayout per separare label e valore
    private void addStatRowWithBorder(JPanel parent, String label, String value)
    {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
        rowPanel.setOpaque(false);
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 18f));
        labelComponent.setForeground(Color.DARK_GRAY);
        
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(18f));
        valueComponent.setForeground(UIStyleUtils.BUTTON_COLOR);
        valueComponent.setHorizontalAlignment(SwingConstants.RIGHT);
        
        rowPanel.add(labelComponent, BorderLayout.WEST);
        rowPanel.add(valueComponent, BorderLayout.EAST);
        
        parent.add(rowPanel);
        parent.add(Box.createVerticalStrut(5)); // Small gap between rows
    }

    private JPanel createWinnerStatsPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 250, 205)); // Light yellow
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Title
        JLabel titleLabel = new JLabel("Winner Statistics");
        titleLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 24f));
        titleLabel.setForeground(UIStyleUtils.BUTTON_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createVerticalStrut(15));

        // General stats usando BorderLayout
        addStatRowWithBorder(panel, "Correct Answers:", String.valueOf(_winner.getCorrectAnswers()));
        addStatRowWithBorder(panel, "Wrong Answers:", String.valueOf(_winner.getWrongAnswers()));
        
        int totalAnswers = _winner.getCorrectAnswers() + _winner.getWrongAnswers();
        float accuracy = totalAnswers > 0 ? 
            (float) _winner.getCorrectAnswers() / totalAnswers * 100 : 0;
        addStatRowWithBorder(panel, "Accuracy:", String.format("%.1f%%", accuracy));

        panel.add(Box.createVerticalStrut(15));
        
        // Category breakdown
        JLabel categoryLabel = new JLabel("Performance by Category:");
        categoryLabel.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 20f));
        categoryLabel.setForeground(UIStyleUtils.BUTTON_COLOR);
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(categoryLabel);
        
        panel.add(Box.createVerticalStrut(10));
        
        Map<Question.Category, Integer> correctByCategory = _winner.getCorrectAnswersByCategory();
        Map<Question.Category, Integer> wrongByCategory = _winner.getWrongAnswersByCategory();
        
        for (Question.Category category : Question.Category.values()) {
            int correct = correctByCategory.getOrDefault(category, 0);
            int wrong = wrongByCategory.getOrDefault(category, 0);
            int total = correct + wrong;
            
            String displayValue = total > 0 ? correct + "/" + total : "0/0";
            addStatRowWithBorder(panel, category.getDisplayName() + ":", displayValue);
        }

        return panel;
    }

    private JPanel createButtonPanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panel.setOpaque(false);

        // Return to Setup button
        JButton setupButton = UIStyleUtils.createStyledButton("New Setup");
        setupButton.addActionListener(_ -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (parentFrame != null) {
                parentFrame.getContentPane().removeAll();
                PlayerSetupPanel setupPanel = new PlayerSetupPanel(parentFrame);
                parentFrame.getContentPane().add(setupPanel);
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        // Restart with same players button
        JButton restartButton = UIStyleUtils.createStyledButton("Play Again");
        restartButton.addActionListener(_ -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (parentFrame != null) {
                // Create a new PlayerSetupPanel with the same players
                PlayerSetupPanel setupPanel = new PlayerSetupPanel(parentFrame);
                
                // Pre-populate with current player data
                try {
                    java.lang.reflect.Field numPlayersField = PlayerSetupPanel.class.getDeclaredField("_numPlayers");
                    numPlayersField.setAccessible(true);
                    numPlayersField.setInt(setupPanel, _players.size());
                    
                    java.lang.reflect.Field playerNamesField = PlayerSetupPanel.class.getDeclaredField("_playerNames");
                    playerNamesField.setAccessible(true);
                    JTextField[] playerNames = (JTextField[]) playerNamesField.get(setupPanel);
                    
                    java.lang.reflect.Field selectedColorsField = PlayerSetupPanel.class.getDeclaredField("_selectedColors");
                    selectedColorsField.setAccessible(true);
                    Color[] selectedColors = (Color[]) selectedColorsField.get(setupPanel);
                    
                    // Set player names and colors
                    for (int i = 0; i < _players.size(); i++) {
                        playerNames[i].setText(_players.get(i).getName());
                        selectedColors[i] = _players.get(i).getColor();
                    }
                    
                    // Start the game directly
                    java.lang.reflect.Method startGameMethod = PlayerSetupPanel.class.getDeclaredMethod("startGame");
                    startGameMethod.setAccessible(true);
                    
                    parentFrame.getContentPane().removeAll();
                    parentFrame.getContentPane().add(setupPanel);
                    parentFrame.revalidate();
                    parentFrame.repaint();
                    
                    // Start game after a int delay to ensure UI is ready
                    SwingUtilities.invokeLater(() -> {
                        try {
                            startGameMethod.invoke(setupPanel);
                        } catch (Exception ex) {
                            System.err.println("Error restarting game: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    });
                    
                } catch (Exception ex) {
                    System.err.println("Error setting up restart: " + ex.getMessage());
                    // Fallback to normal setup
                    parentFrame.getContentPane().removeAll();
                    parentFrame.getContentPane().add(setupPanel);
                    parentFrame.revalidate();
                    parentFrame.repaint();
                }
            }
        });

        // Exit button
        JButton exitButton = UIStyleUtils.createStyledButton("Exit Game");
        exitButton.addActionListener(_ -> {
            if (UIStyleUtils.showConfirmDialog((JFrame) SwingUtilities.getWindowAncestor(this))) {
                System.exit(0);
            }
        });

        panel.add(setupButton);
        panel.add(restartButton);
        panel.add(exitButton);

        return panel;
    }

    @Override
    public void scale(int width, int height)
    {
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        repaint();
    }
}