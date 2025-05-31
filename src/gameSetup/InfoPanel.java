package gameSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * InfoPanel displays game information in multiple languages
 * with a styled interface matching the game's theme.
 */
public class InfoPanel extends JPanel implements GameLauncher.Scalable
{
    public boolean _isVisible;
    private boolean _isEnglish; // true = English, false = Italian
    
    // UI Components
    private JTextArea _textArea;
    private JScrollPane _scrollPane;
    private JButton _closeButton;
    private JButton _englishButton;
    private JButton _italianButton;
    
    // Text content
    private String _englishText;
    private String _italianText;
    
    // Constants
    private static final Color PANEL_BACKGROUND = new Color(25, 25, 60);
    private static final Color BORDER_COLOR = new Color(101, 67, 33);
    private static final int CORNER_RADIUS = 15;
    
    // Constructor
    public InfoPanel(JFrame parentFrame) 
    {
        _isVisible = false;
        _isEnglish = true; // Default to English
        
        loadTextFiles();
        initializeComponents();
        setupLayout();
        setupKeyBlocker();
        
        // AGGIUNTO: Applica proporzioni corrette immediatamente
        SwingUtilities.invokeLater(() -> {
            // Usa le dimensioni del frame correnti per calcoli iniziali
            int frameWidth = parentFrame.getWidth();
            int frameHeight = parentFrame.getHeight();
            scale(frameWidth - 40, frameHeight - 80); // Stesse proporzioni usate nel launcher
        });
        
        setVisible(false);
    }
    
    // NUOVO: Blocca SPACE ed ENTER quando il pannello è visibile
    private void setupKeyBlocker() 
    {
        // Override dei key bindings per bloccare SPACE ed ENTER
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        
        // Blocca SPACE
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "BLOCK_SPACE");
        actionMap.put("BLOCK_SPACE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When the panel is visible, doing nothing here
                // effectively consumes the event
            }
        });
        
        // Blocca ENTER
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "BLOCK_ENTER");
        actionMap.put("BLOCK_ENTER", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When the panel is visible, doing nothing here
                // effectively consumes the event
            }
        });
        
        // Assicurati che il pannello possa ricevere il focus per intercettare i tasti
        setFocusable(true);
    }
    
    // Load text content from files
    private void loadTextFiles() 
    {
        _englishText = loadTextFromFile("resources/fonts/info_en.txt");
        _italianText = loadTextFromFile("resources/fonts/info_it.txt");
        
        // Fallback text if files don't exist
        if (_englishText.isEmpty()) {
            _englishText = "Welcome to Think And Conquer!\n\n" +
                          "This is a strategic board game where knowledge meets conquest.\n" +
                          "Answer trivia questions to earn troops and conquer territories.\n\n" +
                          "Game Features:\n" +
                          "- Strategic gameplay combining trivia and conquest\n" +
                          "- Multiple troop types with different strengths\n" +
                          "- Dynamic point system based on performance\n" +
                          "- Real-time statistics tracking\n\n" +
                          "Controls:\n" +
                          "- F11: Toggle fullscreen\n" +
                          "- G: Toggle statistics panel\n" +
                          "- I: Toggle this info panel\n" +
                          "- ESC: Exit game\n\n" +
                          "Good luck, commander!";
        }
        
        if (_italianText.isEmpty()) {
            _italianText = "Benvenuto in Think And Conquer!\n\n" +
                          "Questo è un gioco di strategia dove la conoscenza incontra la conquista.\n" +
                          "Rispondi alle domande per guadagnare truppe e conquistare territori.\n\n" +
                          "Caratteristiche del gioco:\n" +
                          "- Gameplay strategico che combina trivia e conquista\n" +
                          "- Tipi di truppe multiple con diverse forze\n" +
                          "- Sistema di punti dinamico basato sulle prestazioni\n" +
                          "- Tracciamento statistiche in tempo reale\n\n" +
                          "Controlli:\n" +
                          "- F11: Attiva/disattiva schermo intero\n" +
                          "- G: Attiva/disattiva pannello statistiche\n" +
                          "- I: Attiva/disattiva questo pannello info\n" +
                          "- ESC: Esci dal gioco\n\n" +
                          "Buona fortuna, comandante!";
        }
    }
    
    // Load text from a specific file
    private String loadTextFromFile(String filePath) 
    {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                content.append(line).append("\n");
            }
        } 
        catch (IOException e) 
        {
            System.err.println("Could not load text file: " + filePath + " - " + e.getMessage());
        }
        return content.toString().trim();
    }
    
    // Initialize all UI components
    private void initializeComponents() 
    {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Create close button (arrow)
        _closeButton = createCloseButton();
        
        // Create language buttons - RIMOSSO il parametro isDefault
        _englishButton = createLanguageButton("EN");
        _italianButton = createLanguageButton("IT");
        
        // Create text area
        _textArea = createTextArea();
        
        // Create scroll pane
        _scrollPane = createStyledScrollPane(_textArea);
        
        // Set initial text
        updateText();
    }

    // Create language selection button - RIMOSSO il parametro isDefault
    private JButton createLanguageButton(String text) 
    {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Determine if this button is selected
                boolean isSelected = (text.equals("EN") && _isEnglish) || (text.equals("IT") && !_isEnglish);
                
                // Background
                Color bgColor;
                if (isSelected) {
                    bgColor = UIStyleUtils.GOLDEN_COLOR;
                } else if (getModel().isRollover()) {
                    bgColor = UIStyleUtils.BUTTON_HOVER_COLOR;
                } else {
                    bgColor = UIStyleUtils.BUTTON_COLOR;
                }
                
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Border
                g2d.setColor(UIStyleUtils.BUTTON_BORDER_COLOR);
                g2d.setStroke(new BasicStroke(2.0f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                
                // Text
                g2d.setColor(isSelected ? Color.BLACK : Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                String buttonText = getText();
                int textX = (getWidth() - fm.stringWidth(buttonText)) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(buttonText, textX, textY);
                
                g2d.dispose();
            }
        };
        
        button.setPreferredSize(new Dimension(50, 30));
        button.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, 14f));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Add click listeners
        if (text.equals("EN")) {
            button.addActionListener(_ -> switchToEnglish());
        } else {
            button.addActionListener(_ -> switchToItalian());
        }
        
        return button;
    }
    
    // Create the main text area
    private JTextArea createTextArea() 
    {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(PANEL_BACKGROUND);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(14f));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        textArea.setCaretColor(Color.WHITE);
        
        return textArea;
    }
    
    // Create styled scroll pane (reusing StatsPanel logic)
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
                
                GradientPaint gradient = new GradientPaint(
                    thumbBounds.x, thumbBounds.y, UIStyleUtils.BUTTON_HOVER_COLOR,
                    thumbBounds.x + thumbBounds.width, thumbBounds.y, UIStyleUtils.BUTTON_COLOR
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, 
                                thumbBounds.width - 4, thumbBounds.height - 4, 8, 8);
                
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
    
    // Create styled scroll buttons
    private JButton createStyledScrollButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, getModel().isPressed() ? UIStyleUtils.BUTTON_COLOR : UIStyleUtils.BUTTON_HOVER_COLOR,
                    0, getHeight(), UIStyleUtils.BUTTON_BORDER_COLOR
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                
                g2d.setColor(UIStyleUtils.GOLDEN_COLOR);
                g2d.setStroke(new BasicStroke(1.0f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);
                
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
    
    private JButton createCloseButton() 
    {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Background circle
                Color bgColor;
                if (getModel().isPressed()) {
                    bgColor = UIStyleUtils.BUTTON_COLOR;
                } else if (getModel().isRollover()) {
                    bgColor = UIStyleUtils.BUTTON_HOVER_COLOR;
                } else {
                    bgColor = UIStyleUtils.BUTTON_BORDER_COLOR;
                }
                
                g2d.setColor(bgColor);
                g2d.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
                
                // Border
                g2d.setColor(UIStyleUtils.GOLDEN_COLOR);
                g2d.setStroke(new BasicStroke(2.0f));
                g2d.drawOval(2, 2, getWidth() - 4, getHeight() - 4);
                
                // FIXED: Draw arrow manually instead of using character
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                
                // Calculate arrow dimensions
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                int arrowSize = Math.min(getWidth(), getHeight()) / 4;
                
                // Draw arrow pointing left: < shape
                int[] xPoints = {centerX + arrowSize/2, centerX - arrowSize/2, centerX + arrowSize/2};
                int[] yPoints = {centerY - arrowSize, centerY, centerY + arrowSize};
                
                g2d.drawPolyline(xPoints, yPoints, 3);
                
                g2d.dispose();
            }
        };
        
        button.setPreferredSize(new Dimension(40, 40));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        button.addActionListener(_ -> toggleVisibility());
        
        return button;
}
    
    // Setup the panel layout
    private void setupLayout() 
    {
        setLayout(new BorderLayout());
        
        // Top panel with close button and language buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        
        // Close button on the left
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(_closeButton);
        topPanel.add(leftPanel, BorderLayout.WEST);
        
        // Language buttons on the right
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(_englishButton);
        rightPanel.add(_italianButton);
        topPanel.add(rightPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        add(_scrollPane, BorderLayout.CENTER);
    }
    
    // Custom paint component for rounded background
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Background
        g2d.setColor(PANEL_BACKGROUND);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
        
        // Border
        g2d.setColor(BORDER_COLOR);
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, CORNER_RADIUS, CORNER_RADIUS);
        
        g2d.dispose();
    }
    
    // Switch to English
    private void switchToEnglish() 
    {
        if (!_isEnglish) {
            _isEnglish = true;
            updateText();
            repaintLanguageButtons();
        }
    }
    
    // Switch to Italian
    private void switchToItalian() 
    {
        if (_isEnglish) {
            _isEnglish = false;
            updateText();
            repaintLanguageButtons();
        }
    }
    
    // Update text based on current language
    private void updateText() 
    {
        _textArea.setText(_isEnglish ? _englishText : _italianText);
        _textArea.setCaretPosition(0); // Scroll to top
    }
    
    // Repaint language buttons to show selection
    private void repaintLanguageButtons() 
    {
        _englishButton.repaint();
        _italianButton.repaint();
    }
    
    // Toggle visibility
    public void toggleVisibility() 
    {
        _isVisible = !_isVisible;
        setVisible(_isVisible);
        
        if (_isVisible) {
            // Bring to front
            Container parent = getParent();
            if (parent instanceof JLayeredPane) {
                ((JLayeredPane) parent).moveToFront(this);
            }
        }
    }
    
    // Check if panel is visible
    public boolean isInfoVisible() 
    {
        return _isVisible;
    }
    
    // Also improve the scale method in InfoPanel.java with this version:
    @Override
    public void scale(int width, int height) 
    {
        // Calculate font sizes based on panel dimensions with better ratios
        float baseFontSize = Math.max(12f, Math.min(20f, width / 50f));
        float buttonFontSize = Math.max(12f, Math.min(18f, width / 60f));
        
        // Update text area font
        if (_textArea != null) {
            _textArea.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(baseFontSize));
        }
        
        // FISSO: Tutti i bottoni hanno la stessa altezza e scalano proporzionalmente
        int buttonWidth = Math.max(50, width / 15);
        int buttonHeight = Math.max(30, height / 20);
        
        if (_englishButton != null) {
            _englishButton.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, buttonFontSize));
            _englishButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        }
        
        if (_italianButton != null) {
            _italianButton.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, buttonFontSize));
            _italianButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        }
        
        if (_closeButton != null) {
            // FISSO: Close button usa la stessa altezza degli altri bottoni
            int closeSize = buttonHeight; // STESSA ALTEZZA di EN/IT
            _closeButton.setPreferredSize(new Dimension(closeSize, closeSize));
        }
        
        // Scale borders and padding proportionally
        int padding = Math.max(8, width / 80);
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        
        if (_textArea != null) {
            int textPadding = Math.max(10, width / 60);
            _textArea.setBorder(BorderFactory.createEmptyBorder(textPadding, textPadding, textPadding, textPadding));
        }
        
        // CRITICAL: Force immediate layout updates
        SwingUtilities.invokeLater(() -> {
            invalidate();
            doLayout();
            revalidate();
            repaint();
            
            if (_scrollPane != null) {
                _scrollPane.revalidate();
                _scrollPane.repaint();
            }
        });
    }
    
    // NUOVO: Metodo per posizionamento e scaling (spostato da GameLauncher)
    public void positionAndScale(JFrame frame) {
        // USA LE DIMENSIONI DEL CONTENT PANE per la larghezza, del frame per l'altezza
        Container contentPane = frame.getContentPane();
        int frameWidth = contentPane.getWidth();  // Content pane per larghezza
        int frameHeight = frame.getHeight();      // Frame per altezza

        JMenuBar menuBar = frame.getJMenuBar();
        int menuHeight = menuBar != null ? menuBar.getHeight() : 0;
        

        int rawPanelWidth = frameWidth - 10;
        int panelHeight = frameHeight - menuHeight - 40;

        // CORRETTO: Calcola X con la larghezza RAW del content pane
        int panelX = (frameWidth - rawPanelWidth) / 2;
        
        // ORA applica i minimi
        int panelWidth = Math.max(400, rawPanelWidth);
        panelHeight = Math.max(300, panelHeight);
        
        // Se panelWidth è stato aumentato a 400, ricalcola X per centrare
        if (panelWidth != rawPanelWidth) {
            panelX = (frameWidth - panelWidth) / 2;
        }
        
        // CENTRATO: Calcola Y - RIMESSO come prima
        int availableHeight = frameHeight - menuHeight;
        int panelY = menuHeight + (availableHeight - panelHeight) / 2 - 20;

        // Set bounds e scale
        setBounds(panelX, panelY, panelWidth, panelHeight);
        scale(panelWidth, panelHeight);
        revalidate();
        repaint();
        
        // Porta in primo piano e richiedi focus
        Container parent = getParent();
        if (parent instanceof JLayeredPane) {
            ((JLayeredPane) parent).moveToFront(this);
        }
        requestFocusInWindow();
    }
}