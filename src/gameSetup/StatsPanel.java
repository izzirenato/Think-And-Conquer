package gameSetup;


import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;

import java.util.Map;

import trivia.Question;


/*
 * StatsPanel displays comprehensive statistics for all players in the game
 * Updates in real-time during fights and other game events
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
        
        // Aumenta lievemente l'altezza
        setPreferredSize(new Dimension(285, 650)); // Era 275x600
        setMinimumSize(new Dimension(310, 450));   // Era 300x400

        _allPlayersPanel = createAllPlayersPanel();
        
        // Crea uno JScrollPane con stile personalizzato
        JScrollPane scrollPane = createStyledScrollPane(_allPlayersPanel);
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
            
            // Stesso sfondo per tutti i giocatori
            playerPanel.setBackground(new Color(40, 40, 70, 180));
            
            String playerTitle = player.getName();
            
            // Crea un border personalizzato con sfondo bianco per il current player
            if (player == currentPlayer) {
                playerPanel.setBorder(createStyledBorderWithWhiteBackground(playerTitle, player.getColor()));
            } else {
                playerPanel.setBorder(createStyledBorder(playerTitle, player.getColor()));
            }
            
            addStatsLabel(playerPanel, String.format("Points: %d/%d", player.getPoints(), _gameManager.getMaxPoints()), Color.WHITE);
            addStatsLabel(playerPanel, String.format("Correct: %d | Wrong: %d", player.getCorrectAnswers(), player.getWrongAnswers()), Color.WHITE);
            
            // Category performance ratios
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
    
    // Crea uno scroll pane con stile coerente al gioco
    private JScrollPane createStyledScrollPane(JComponent component) 
    {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Styling per la scrollbar verticale
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                // Colori coerenti con il tema del gioco
                trackColor = UIStyleUtils.BUTTON_BORDER_COLOR;
                thumbColor = UIStyleUtils.BUTTON_COLOR;
                thumbHighlightColor = UIStyleUtils.BUTTON_HOVER_COLOR;
                thumbLightShadowColor = UIStyleUtils.GOLDEN_COLOR;
                thumbDarkShadowColor = new Color(60, 30, 15);
            }
            
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createStyledScrollButton("▲");
            }
            
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createStyledScrollButton("▼");
            }
            
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Disegna il thumb con gradiente
                GradientPaint gradient = new GradientPaint(
                    thumbBounds.x, thumbBounds.y, UIStyleUtils.BUTTON_HOVER_COLOR,
                    thumbBounds.x + thumbBounds.width, thumbBounds.y, UIStyleUtils.BUTTON_COLOR
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, 
                                thumbBounds.width - 4, thumbBounds.height - 4, 8, 8);
                
                // Bordo dorato
                g2d.setColor(UIStyleUtils.GOLDEN_COLOR);
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, 
                                thumbBounds.width - 4, thumbBounds.height - 4, 8, 8);
                
                g2d.dispose();
            }
            
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(UIStyleUtils.BUTTON_BORDER_COLOR);
                g2d.fillRoundRect(trackBounds.x + 4, trackBounds.y, 
                                trackBounds.width - 8, trackBounds.height, 6, 6);
                g2d.dispose();
            }
        });
        
        verticalBar.setPreferredSize(new Dimension(16, 0));
        verticalBar.setOpaque(false);
        
        return scrollPane;
    }
    
    // Crea bottoni stilizzati per la scrollbar
    private JButton createStyledScrollButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Sfondo con gradiente
                GradientPaint gradient = new GradientPaint(
                    0, 0, getModel().isPressed() ? UIStyleUtils.BUTTON_COLOR : UIStyleUtils.BUTTON_HOVER_COLOR,
                    0, getHeight(), UIStyleUtils.BUTTON_BORDER_COLOR
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                
                // Bordo
                g2d.setColor(UIStyleUtils.GOLDEN_COLOR);
                g2d.setStroke(new BasicStroke(1.0f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);
                
                // Testo
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), textX, textY);
                
                g2d.dispose();
            }
        };
        
        button.setPreferredSize(new Dimension(16, 16));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 8));
        
        return button;
    }

    // creates a styled border with a title in the player's color and white background for current player
    private Border createStyledBorderWithWhiteBackground(String title, Color playerColor) 
    {
        Border line = BorderFactory.createLineBorder(UIStyleUtils.GOLDEN_COLOR, 2);
        Border empty = BorderFactory.createEmptyBorder(8, 8, 8, 8);
        
        // Determina se usare sfondo bianco o nero basandosi sul colore del player
        boolean useWhiteBackground = playerColor.equals(Color.RED) || 
                                   playerColor.equals(Color.BLUE) || 
                                   playerColor.equals(new Color(128, 0, 128)); // purple
        
        TitledBorder titledBorder = new TitledBorder(
            BorderFactory.createCompoundBorder(line, empty), 
            title,
            TitledBorder.CENTER, 
            TitledBorder.TOP
        ) {
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
                    
                    // Calcola il centro del titolo
                    int titleX = x + (width - titleWidth) / 2;
                    int titleY = y;
                    
                    // Disegna il rettangolo dietro al testo - ALLUNGATO verso il basso
                    int padding = 10;
                    int rectX = titleX - padding;
                    int rectY = titleY - titleHeight / 2 - padding / 2;
                    int rectWidth = titleWidth + (padding * 2);
                    int rectHeight = titleHeight + padding + 6; // AGGIUNTO +6 per allungare verso il basso
                    
                    // Usa bianco o nero a seconda del colore del player
                    g2d.setColor(useWhiteBackground ? Color.WHITE : Color.BLACK);
                    g2d.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 8, 8);
                    
                    // Bordo sottile attorno al rettangolo
                    g2d.setColor(UIStyleUtils.GOLDEN_COLOR);
                    g2d.setStroke(new BasicStroke(1.0f));
                    g2d.drawRoundRect(rectX, rectY, rectWidth, rectHeight, 8, 8);
                }
                
                g2d.dispose();
                
                // Disegna il border normale sopra
                super.paintBorder(c, g, x, y, width, height);
            }
        };
        
        titledBorder.setTitleColor(playerColor);
        titledBorder.setTitleFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 18f));
        return titledBorder;
    }
}
