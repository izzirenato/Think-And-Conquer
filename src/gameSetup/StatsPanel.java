package gameSetup;


import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;

import java.util.Map;


/*
 * StatsPanel displays the statistics of the current player and all players in the game
 * it's not working cause the update methods are not called yet. I'll first implement the game logic
 * and then I'll fix the update methods
 */


public class StatsPanel extends JPanel 
{
    private final GameManager _gameManager;
    private final JPanel _playerStatsPanel;
    private final JPanel _allPlayersPanel;
    private boolean _isVisible;


    // ctor
    public StatsPanel(GameManager gameManager) 
    {
        _gameManager = gameManager;
        _isVisible = false;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        _playerStatsPanel = createPlayerStatsPanel();
        _allPlayersPanel = createAllPlayersPanel();

        add(_playerStatsPanel);
        add(Box.createVerticalStrut(10));
        add(_allPlayersPanel);
        
        setVisible(false);
    }


    // custom paint component
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Same gradient as menu bar and control panel
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


    // create panels for player stats 
    private JPanel createPlayerStatsPanel() 
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(40, 40, 70, 180));
        panel.setBorder(createStyledBorder("Your Stats"));
        updatePlayerStats(panel);
        return panel;
    }

    // creates the panel with the most important stats of the other players
    // the current player needs to see this to plan his strategy
    private JPanel createAllPlayersPanel() 
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(40, 40, 70, 180));
        panel.setBorder(createStyledBorder("All Players Stats"));
        updateAllPlayersStats(panel);
        return panel;
    }

    // creates a styled border with a title
    private Border createStyledBorder(String title) 
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
        titledBorder.setTitleColor(Color.WHITE);
        return titledBorder;
    }


    // updates the stats panels but it's not called yet
    // maybe i could decide to devide this method in little methods to update
    // only the parts that need to be updated
    public void update() 
    {
        updatePlayerStats(_playerStatsPanel);
        updateAllPlayersStats(_allPlayersPanel);
        revalidate();
        repaint();
    }


    // updates the stats of the current player
    private void updatePlayerStats(JPanel panel) 
    {
        panel.removeAll();
        Player currentPlayer = _gameManager.getCurrentPlayer();
        
        // Points info
        addStatsLabel(panel, String.format("Points: %d/%d", 
            currentPlayer.getPoints(), 
            _gameManager.getMaxPoints()));
        
        // Questions stats
        addStatsLabel(panel, String.format("Correct Answers: %d", currentPlayer.getCorrectAnswers()));
        addStatsLabel(panel, String.format("Wrong Answers: %d", currentPlayer.getWrongAnswers()));
        addStatsLabel(panel, String.format("Score: %d", currentPlayer.getScore()));
        
        panel.add(Box.createVerticalStrut(10));
        
        // Available troops (only for the current player)
        addStatsLabel(panel, "Your Troops:");
        Map<String, Integer> playerTroops = currentPlayer.getAvailableTroopsMap();
        for (Map.Entry<String, Integer> entry : playerTroops.entrySet()) 
        {
            addStatsLabel(panel, String.format("%s: %d", entry.getKey(), entry.getValue()));
        }
    }

    // updates the stats of all players except the current player
    private void updateAllPlayersStats(JPanel panel) 
    {
        panel.removeAll();
        
        for (Player player : _gameManager.getPlayers()) 
        {
            if (player != _gameManager.getCurrentPlayer()) 
            {
                JPanel playerPanel = new JPanel();
                playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
                playerPanel.setBackground(new Color(50, 50, 80, 180));
                playerPanel.setBorder(createStyledBorder(player.getName()));
                
                addStatsLabel(playerPanel, String.format("Points: %d/%d", 
                    player.getPoints(), 
                    _gameManager.getMaxPoints()));
                addStatsLabel(playerPanel, String.format("Available Troops: %d", 
                    player.getAvailableTroops()));
                
                panel.add(playerPanel);
                panel.add(Box.createVerticalStrut(5));
            }
        }
    }

    // adds a label with stats to the given panel
    private void addStatsLabel(JPanel panel, String text) 
    {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
    }

    // toggles the visibility of the stats panel
    public void toggleVisibility() 
    {
        _isVisible = !_isVisible;
        setVisible(_isVisible);
    }
}
