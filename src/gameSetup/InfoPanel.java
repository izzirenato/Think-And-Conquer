package gameSetup;


import javax.swing.*;

import map.MapPanel;
import map.Territory;

import java.awt.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * InfoPanel displays game information in english or italian
 */


public class InfoPanel extends JPanel implements GameLauncher.Scalable
{
    public boolean _isVisible;
    private boolean _isEnglish;
    
    // UI Components
    private JTextArea _textArea;
    private JButton _closeButton;
    private JButton _englishButton;
    private JButton _italianButton;
    
    // text content
    private String _englishText;
    private String _italianText;
    
    // constants
    private static final Color PANEL_BACKGROUND = new Color(25, 25, 60);
    private static final Color BORDER_COLOR = new Color(101, 67, 33);
    private static final int CORNER_RADIUS = 15;
    

    // ctor
    public InfoPanel(JFrame parentFrame) 
    {
        _isVisible = false;
        _isEnglish = true;
        
        loadTextFiles();
        initializeComponents();
        setupLayout();
        
        setVisible(false);
    }
    

    // check if panel is visible
    public boolean isInfoVisible() {return _isVisible;}
    

    // toggle visibility
    public void toggleVisibility() 
    {
        _isVisible = !_isVisible;
        setVisible(_isVisible);
        
        if (_isVisible) 
        {
            disableGameInteractions();
            Container parent = getParent();
            if (parent instanceof JLayeredPane) {((JLayeredPane) parent).moveToFront(this);}
        } 
        else {enableGameInteractions();}
    }


    // inner scaling
    @Override
    public void scale(int width, int height) 
    {
        // calculate font sizes
        float baseFontSize = Math.max(12f, Math.min(20f, width / 50f));
        float buttonFontSize = Math.max(12f, Math.min(18f, width / 60f));
        
        // update components
        if (_textArea != null) {_textArea.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(baseFontSize));}
        
        int buttonWidth = Math.max(50, width / 15);
        int buttonHeight = Math.max(30, height / 20);
        
        if (_englishButton != null) 
        {
            _englishButton.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, buttonFontSize));
            _englishButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        }
        
        if (_italianButton != null)
        {
            _italianButton.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, buttonFontSize));
            _italianButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        }
        
        if (_closeButton != null) {_closeButton.setPreferredSize(new Dimension(buttonHeight, buttonHeight));}
        
        // scale borders
        int padding = Math.max(8, width / 80);
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        
        if (_textArea != null) 
        {
            int textPadding = Math.max(10, width / 60);
            _textArea.setBorder(BorderFactory.createEmptyBorder(textPadding, textPadding, textPadding, textPadding));
        }
        
        SwingUtilities.invokeLater(() -> 
        {
            revalidate();
            repaint();
        });
    }
    

    // scales and positions the panel within the parent frame
    public void positionAndScale(JFrame frame) 
    {
        Container contentPane = frame.getContentPane();
        int frameWidth = contentPane.getWidth();
        int frameHeight = contentPane.getHeight();

        JMenuBar menuBar = frame.getJMenuBar();
        int menuHeight = menuBar != null ? menuBar.getHeight() : 0;
        
        int titleBarHeight = frame.getInsets().top;
        int availableHeight = frameHeight - menuHeight - titleBarHeight + 50;
        
        int rawPanelWidth = frameWidth - 6;
        int panelHeight = availableHeight;

        int panelX = (frameWidth - rawPanelWidth) / 2;
        int panelWidth = rawPanelWidth;

        int panelY = menuHeight + titleBarHeight - 20;

        setBounds(panelX, panelY, panelWidth, panelHeight);
        scale(panelWidth, panelHeight);
    }


    // custom paint component for rounded background
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // background
        g2d.setColor(PANEL_BACKGROUND);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
        
        // border
        g2d.setColor(BORDER_COLOR);
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, CORNER_RADIUS, CORNER_RADIUS);
        
        g2d.dispose();
    }
    

    // load text content from files
    private void loadTextFiles() 
    {
        _englishText = loadTextFromFile("resources/fonts/info_en.txt");
        _italianText = loadTextFromFile("resources/fonts/info_it.txt");
        
        // Fallback text if files don't exist
        if (_englishText.isEmpty() || _englishText == null) 
        {
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
                          "- SPACE: End turn\n" +
                          "- ESC: Exit game\n\n" +
                          "Good luck, commander!";
        }
        
        if (_italianText.isEmpty() || _italianText == null)
        {
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
                          "- SPACE: Termina il turno\n" +
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
        
        _closeButton = createCloseButton();
        _englishButton = createLanguageButton("EN");
        _italianButton = createLanguageButton("IT");
        
        _textArea = createTextArea();
        
        updateText();
    }


    // setup the panel layout
    private void setupLayout() 
    {
        setLayout(new BorderLayout());
        
        // top panel with close button and language buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        
        // close button on the left
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(_closeButton);
        topPanel.add(leftPanel, BorderLayout.WEST);
        
        // language buttons on the right
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(_englishButton);
        rightPanel.add(_italianButton);
        topPanel.add(rightPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Create and add the scroll pane here
        JScrollPane scrollPane = UIStyleUtils.createStyledScrollPane(_textArea);
        add(scrollPane, BorderLayout.CENTER);
    }
    

    // create the close button with custom arrow
    private JButton createCloseButton() 
    {
        JButton button = new JButton() 
        {
            @Override
            protected void paintComponent(Graphics g) 
            {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // background circle
                Color bgColor;
                if (getModel().isPressed()) {bgColor = UIStyleUtils.BUTTON_COLOR;}
                else if (getModel().isRollover()) {bgColor = UIStyleUtils.BUTTON_HOVER_COLOR;} 
                else {bgColor = UIStyleUtils.BUTTON_BORDER_COLOR;}
                
                g2d.setColor(bgColor);
                g2d.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
                
                // border
                g2d.setColor(UIStyleUtils.GOLDEN_COLOR);
                g2d.setStroke(new BasicStroke(2.0f));
                g2d.drawOval(2, 2, getWidth() - 4, getHeight() - 4);
                
                // arrow
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                
                // calculate arrow dimensions
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                int arrowSize = Math.min(getWidth(), getHeight()) / 4;
                
                // draw arrow pointing left: < shape
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
    

    // creates the language selection buttons
    private JButton createLanguageButton(String text) 
    {
        JButton button = new JButton(text) 
        {
            @Override
            protected void paintComponent(Graphics g) 
            {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // determine if this button is selected
                boolean isSelected = (text.equals("EN") && _isEnglish) || (text.equals("IT") && !_isEnglish);
                
                // background
                Color bgColor;
                if (isSelected) {bgColor = UIStyleUtils.GOLDEN_COLOR;} 
                else if (getModel().isRollover()) {bgColor = UIStyleUtils.BUTTON_HOVER_COLOR;} 
                else {bgColor = UIStyleUtils.BUTTON_COLOR;}

                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                // border
                g2d.setColor(UIStyleUtils.BUTTON_BORDER_COLOR);
                g2d.setStroke(new BasicStroke(2.0f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                
                // text
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
        
        // add click listeners
        if (text.equals("EN")) {button.addActionListener(_ -> switchLanguage(true));} 
        else {button.addActionListener(_ -> switchLanguage(false));}
        
        return button;
    }

    
    // create the main text area
    private JTextArea createTextArea() 
    {
        _textArea = new JTextArea();
        _textArea.setEditable(false);
        _textArea.setLineWrap(true);
        _textArea.setWrapStyleWord(true);
        _textArea.setOpaque(false);
        _textArea.setForeground(Color.WHITE);
        _textArea.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(16f));
        
        return _textArea;
    }
    

    // switch language
    private void switchLanguage(boolean toEnglish) 
    {
        if (_isEnglish != toEnglish) 
        {
            _isEnglish = toEnglish;
            updateText();
            repaintLanguageButtons();
        }
    }
    

    // update text based on current language
    private void updateText() 
    {
        _textArea.setText(_isEnglish ? _englishText : _italianText);
        _textArea.setCaretPosition(0); // Scroll to top
    }
    

    // repaint language buttons to show selection
    private void repaintLanguageButtons() 
    {
        _englishButton.repaint();
        _italianButton.repaint();
    }
    

    // disable game interactions when the panel is visible
    private void disableGameInteractions() 
    {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) 
        {
            GameActionPanel actionPanel = GameLauncher.findComponentRecursive(GameActionPanel.class, frame.getContentPane());
            if (actionPanel != null) {actionPanel.disableAllInteractions();}
        }
    }


    // enable game interactions when the panel is closed
    private void enableGameInteractions() 
    {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) 
        {
            GameActionPanel actionPanel = GameLauncher.findComponentRecursive(GameActionPanel.class, frame.getContentPane());
            if (actionPanel != null) 
            {
                actionPanel.enableAllInteractions();
                
                MapPanel mapPanel = GameLauncher.findComponentRecursive(MapPanel.class, frame.getContentPane());
                if (mapPanel != null) 
                {
                    Territory selectedTerritory = mapPanel.getSelectedTerritory();
                    if (selectedTerritory != null) {actionPanel.updateButtonsForSelectedTerritory(selectedTerritory);}
                }
            }
        }
    }
}