package gameSetup;


import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;

import java.util.Map;

import trivia.Question;


/*
 * StatsPanel displays statistics for all players in the game
 * really useful to plan strategies
 */


public class StatsPanel extends JPanel 
{
    private final GameManager _gameManager;
    private final JPanel _allPlayersPanel;
    private boolean _isVisible;


    // ctor
    public StatsPanel(GameManager gameManager) 
    {
        _gameManager = gameManager;
        _isVisible = false;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        setPreferredSize(new Dimension(285, 650));
        setMinimumSize(new Dimension(310, 450));

        _allPlayersPanel = createAllPlayersPanel();
        
        JScrollPane scrollPane = UIStyleUtils.createStyledScrollPane(_allPlayersPanel);
        add(scrollPane, BorderLayout.CENTER);
        
        setVisible(false);
    }


    // custom paint component
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        Color darkBrown = UIStyleUtils.BUTTON_BORDER_COLOR;
        Color lightBrown = new Color(153, 102, 51);
        GradientPaint gradient = new GradientPaint
        (
            0, 0, darkBrown,
            0, getHeight(), lightBrown
        );

        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }


    // creates the panel with all players stats
    private JPanel createAllPlayersPanel() 
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        updateAllPlayersStats(panel);
        return panel;
    }

    
    // creates a styled border with a title in the player's color
    private Border createStyledBorder(String title, Color playerColor) 
    {
        Border line = BorderFactory.createLineBorder(UIStyleUtils.GOLDEN_COLOR, 2);
        Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        TitledBorder titledBorder = BorderFactory.createTitledBorder
        (
            BorderFactory.createCompoundBorder(line, empty), 
            title,
            TitledBorder.CENTER, 
            TitledBorder.TOP
        );
        titledBorder.setTitleColor(playerColor);
        titledBorder.setTitleFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 16f));
        return titledBorder;
    }


    // updates the stats panels
    public void update() 
    {
        updateAllPlayersStats(_allPlayersPanel);
        revalidate();
        repaint();
    }


    // updates the stats of all players
    private void updateAllPlayersStats(JPanel panel) 
    {
        panel.removeAll();
        
        Player currentPlayer = _gameManager.getCurrentPlayer();
        
        for (Player player : _gameManager.getPlayers()) 
        {
            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
            
            playerPanel.setBackground(new Color(40, 40, 70, 180));
            
            String playerTitle = player.getName();
            
            if (player == currentPlayer) 
            {
                playerPanel.setBorder(createStyledBorderWithWhiteBackground(playerTitle, player.getColor()));
            } 
            else 
            {
                playerPanel.setBorder(createStyledBorder(playerTitle, player.getColor()));
            }

            addStatsLabel(playerPanel, String.format("Points: %d/%d", player.getPoints(), _gameManager.getMaxPoints()), Color.WHITE);
            addStatsLabel(playerPanel, String.format("Correct: %d | Wrong: %d", player.getCorrectAnswers(), player.getWrongAnswers()), Color.WHITE);
            
            // category performance ratios
            addStatsLabel(playerPanel, "Category Performance:", Color.YELLOW);
            for (Question.Category category : Question.Category.values()) 
            {
                int correct = player.getCorrectAnswersByCategory().get(category);
                int wrong = player.getWrongAnswersByCategory().get(category);
                int total = correct + wrong;
                
                if (total > 0) 
                {
                    float ratio = (float) correct / total * 100;
                    String ratioText = String.format("  %s: %.1f%% (%d/%d)", category.getDisplayName(), ratio, correct, total);
                    Color ratioColor = ratio >= 75 ? Color.GREEN : ratio >= 50 ? Color.ORANGE : Color.RED;
                    addStatsLabel(playerPanel, ratioText, ratioColor);
                } 
                else 
                {
                    addStatsLabel(playerPanel, String.format("  %s: No attempts", category.getDisplayName()), Color.GRAY);
                }
            }
            
            playerPanel.add(Box.createVerticalStrut(5));
            
            addStatsLabel(playerPanel, "Available Troops:", Color.CYAN);
            Map<String, Integer> playerTroops = player.getAvailableTroopsMap();
            if (playerTroops.isEmpty()) 
            {
                addStatsLabel(playerPanel, "  No troops available", Color.GRAY);
            } 
            else 
            {
                for (Map.Entry<String, Integer> entry : playerTroops.entrySet()) 
                {
                    addStatsLabel(playerPanel, String.format("  %s: %d", entry.getKey(), entry.getValue()), Color.WHITE);
                }
            }

            panel.add(playerPanel);
            panel.add(Box.createVerticalStrut(8));
        }
    }


    // adds a label with stats to the given panel with specified color
    private void addStatsLabel(JPanel panel, String text, Color color) 
    {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(12f));
        label.setBorder(BorderFactory.createEmptyBorder(3, 6, 3, 6));
        panel.add(label);
    }


    // toggles the visibility of the stats panel
    public void toggleVisibility() 
    {
        _isVisible = !_isVisible;
        setVisible(_isVisible);
    }

    // creates a styled border with a title in the player's color and white background for current player
    private Border createStyledBorderWithWhiteBackground(String title, Color playerColor) 
    {
        Border line = BorderFactory.createLineBorder(UIStyleUtils.GOLDEN_COLOR, 2);
        Border empty = BorderFactory.createEmptyBorder(8, 8, 8, 8);
        
        boolean useWhiteBackground = playerColor.equals(Color.RED) || 
                                   playerColor.equals(Color.BLUE) || 
                                   playerColor.equals(new Color(128, 0, 128));

        TitledBorder titledBorder = new TitledBorder
        (
            BorderFactory.createCompoundBorder(line, empty), 
            title,
            TitledBorder.CENTER, 
            TitledBorder.TOP
        ) 
        {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                if (getTitle() != null && getTitleFont() != null) {
                    g2d.setFont(getTitleFont());
                    FontMetrics fm = g2d.getFontMetrics();
                    
                    int titleWidth = fm.stringWidth(getTitle());
                    int titleHeight = fm.getHeight();
                    
                    int titleX = x + (width - titleWidth) / 2;
                    int titleY = y;
                    
                    int padding = 10;
                    int rectX = titleX - padding;
                    int rectY = titleY - titleHeight / 2 - padding / 2;
                    int rectWidth = titleWidth + (padding * 2);
                    int rectHeight = titleHeight + padding + 6;
                    
                    g2d.setColor(useWhiteBackground ? Color.WHITE : Color.BLACK);
                    g2d.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 8, 8);
                    
                    g2d.setColor(UIStyleUtils.GOLDEN_COLOR);
                    g2d.setStroke(new BasicStroke(1.0f));
                    g2d.drawRoundRect(rectX, rectY, rectWidth, rectHeight, 8, 8);
                }
                
                g2d.dispose();
                
                super.paintBorder(c, g, x, y, width, height);
            }
        };
        
        titledBorder.setTitleColor(playerColor);
        titledBorder.setTitleFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 18f));
        return titledBorder;
    }
}