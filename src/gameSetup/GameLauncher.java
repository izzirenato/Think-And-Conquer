package gameSetup;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;


/*
 * GameLauncher serves as the entry point
 * It initializes the main window with system look-and-feel, configures keybindings
 * for full-screen and stats toggles, sets up the menu bar, and displays the splash screen
 */

 
public class GameLauncher 
{
    // main application frame
    private static JFrame _frame;
    private static boolean _isFullScreen = false;
    private static Rectangle _previousBounds;
    private static boolean _isResizingFromToggle = false;
    private static JPanel _glassPane; // AGGIUNTO: Glass pane per bloccare interazioni

    // window state constants
    private static final int _WINDOWED_STATE = JFrame.NORMAL;
    private static final int _MAXIMIZED_STATE = JFrame.MAXIMIZED_BOTH;


    // entry point
    public static void main(String[] args) 
    {
        // wait for Swing to be ready
        SwingUtilities.invokeLater(() -> 
        {
            setSystemLookAndFeel();
            initializeFrame();
            _frame.setIconImage(UIStyleUtils.ICON);
            showSplashScreen();
        });
    }


    // it tries to match the java application look with the system look
    private static void setSystemLookAndFeel() 
    {
        try 
        {
            // gets the system look and feel and sets the java application to use it
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (Exception e) 
        {
            // if it fails, it will explain the error
            e.printStackTrace();
        }
    }


    // Initializes the main JFrame, including size, listeners, key bindings, and menu bar
    private static void initializeFrame() 
    {
        _frame = new JFrame("Think And Conquer");
        _frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        _frame.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                if (UIStyleUtils.showConfirmDialog(_frame)) {System.exit(0);}
            }
        });

        // Set initial size
        _previousBounds = new Rectangle(800, 600);
        _frame.setSize(_previousBounds.getSize());
        _frame.setMinimumSize(new Dimension(800, 600));
        _frame.addComponentListener(new ComponentAdapter() 
        {
            @Override
            public void componentResized(ComponentEvent e) 
            {
                if (!_isResizingFromToggle) 
                {
                    resizeGamePanels();
                }
            }
        });

        // AGGIUNTO: Inizializza glass pane
        setupGlassPane();
        
        setupKeyBindings();
        setupMenuBar();
        _frame.setLocationRelativeTo(null);
        _frame.setVisible(true);
    }

    // NUOVO: Configura il glass pane per bloccare interazioni
    private static void setupGlassPane() 
    {
        _glassPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Glass pane trasparente
                super.paintComponent(g);
            }
        };
        
        _glassPane.setOpaque(false);
        _glassPane.setVisible(false);
        
        // Blocca tutti i mouse events
        _glassPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { e.consume(); }
            @Override
            public void mousePressed(MouseEvent e) { e.consume(); }
            @Override
            public void mouseReleased(MouseEvent e) { e.consume(); }
        });
        
        _glassPane.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) { e.consume(); }
            @Override
            public void mouseDragged(MouseEvent e) { e.consume(); }
        });
        
        // Configura key bindings ristretti per il glass pane
        setupRestrictedKeyBindings();
        
        _frame.setGlassPane(_glassPane);
    }

    // NUOVO: Key bindings ristretti quando glass pane è attivo
    private static void setupRestrictedKeyBindings() 
    {
        InputMap inputMap = _glassPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = _glassPane.getActionMap();
        
        // Solo F11 e ESC sono permessi
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "TOGGLE_FULLSCREEN");
        actionMap.put("TOGGLE_FULLSCREEN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleFullScreen();
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "EXIT_GAME");
        actionMap.put("EXIT_GAME", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (UIStyleUtils.showConfirmDialog(_frame)) {
                    System.exit(0);
                }
            }
        });
        
        // Blocca esplicitamente tutti gli altri tasti
        KeyStroke[] blockedKeys = {
            KeyStroke.getKeyStroke(KeyEvent.VK_G, 0),
            KeyStroke.getKeyStroke(KeyEvent.VK_I, 0),
            KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0),
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0)
            // Aggiungi altri tasti se necessario
        };
        
        for (KeyStroke key : blockedKeys) {
            inputMap.put(key, "BLOCKED");
            actionMap.put("BLOCKED", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Non fare nulla - blocca l'azione
                }
            });
        }
    }

    // Displays the splash screen
    private static void showSplashScreen() 
    {
        SplashScreen splash = new SplashScreen(_frame);
        _frame.getContentPane().add(splash, BorderLayout.CENTER);
        _frame.pack();
    }


    // Registers key bindings: F11 toggles full-screen, G toggles stats panel, I toggles info, SPACE for end turn
    private static void setupKeyBindings() 
    {
        // gets the main bucket of the JFrame with the key bindings
        JRootPane root = _frame.getRootPane();
        // it links the key bindings to the JFrame
        InputMap inMap = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        // it links the key bindings to the JFrame actions
        ActionMap actMap = root.getActionMap();

        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "TOGGLE_FULLSCREEN");
        actMap.put("TOGGLE_FULLSCREEN", new AbstractAction() 
        {
            @Override public void actionPerformed(ActionEvent e) {toggleFullScreen();}
        });

        // MODIFICATO: Usa G invece di CTRL+SPACE per evitare conflitti
        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0), "TOGGLE_STATS");
        actMap.put("TOGGLE_STATS", new AbstractAction() 
        {
            @Override public void actionPerformed(ActionEvent e) {
                // NUOVO: Solo se non siamo in GameOverPanel
                if (!isGameOverPanelActive()) {
                    toggleStatsPanel();
                }
            }
        });

        // NUOVO: Shortcut I per info panel
        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "TOGGLE_INFO");
        actMap.put("TOGGLE_INFO", new AbstractAction() 
        {
            @Override public void actionPerformed(ActionEvent e) {
                // NUOVO: Solo se non siamo in GameOverPanel
                if (!isGameOverPanelActive()) {
                    toggleInfoPanel();
                }
            }
        });

        // NUOVO: SPACE per fine turno
        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "END_TURN");
        actMap.put("END_TURN", new AbstractAction() 
        {
            @Override public void actionPerformed(ActionEvent e) {
                // NUOVO: Solo se non siamo in GameOverPanel
                if (!isGameOverPanelActive()) {
                    triggerEndTurn();
                }
            }
        });

        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "EXIT_GAME");
        actMap.put("EXIT_GAME", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (UIStyleUtils.showConfirmDialog(_frame)) {
                    System.exit(0);
                }
            }
        });
    }

    // NUOVO: Controlla se GameOverPanel è attivo
    private static boolean isGameOverPanelActive() {
        GameOverPanel gameOverPanel = findComponentRecursive(GameOverPanel.class, _frame.getContentPane());
        return gameOverPanel != null;
    }

    // NUOVO: Trigger del fine turno tramite shortcut
    private static void triggerEndTurn() {
        GameActionPanel actionPanel = findComponentRecursive(GameActionPanel.class, _frame.getContentPane());
        if (actionPanel != null) {
            // Simula il click del bottone End Turn se è abilitato e visibile
            Component[] components = actionPanel.getComponents();
            for (Component comp : components) {
                if (comp instanceof JButton && "End Turn".equals(((JButton) comp).getText())) {
                    JButton endTurnButton = (JButton) comp;
                    if (endTurnButton.isVisible() && endTurnButton.isEnabled()) {
                        endTurnButton.doClick();
                    }
                    break;
                }
            }
        }
    }

    // NUOVO: Nasconde i menu Game e Info
    public static void hideGameMenus() {
        JMenuBar bar = _frame.getJMenuBar();
        if (bar != null) {
            for (int i = 0; i < bar.getMenuCount(); i++) {
                JMenu menu = bar.getMenu(i);
                String menuText = menu.getText();
                if ("Game".equals(menuText) || "Info".equals(menuText)) {
                    menu.setVisible(false);
                }
            }
        }
    }

    // NUOVO: Mostra i menu Game e Info
    public static void showGameMenus() {
        JMenuBar bar = _frame.getJMenuBar();
        if (bar != null) {
            for (int i = 0; i < bar.getMenuCount(); i++) {
                JMenu menu = bar.getMenu(i);
                String menuText = menu.getText();
                if ("Info".equals(menuText)) {
                    menu.setVisible(true);
                }
                // Game menu rimane nascosto fino a quando non viene esplicitamente mostrato
            }
        }
    }


    // Constructs a custom-styled menu bar with "Visualize", "Info" and (hidden) "Game" menus
    private static void setupMenuBar() 
    {
        JMenuBar menuBar = new GradientMenuBar();
        // combines the menu bar with a border
        menuBar.setBorder(BorderFactory.createCompoundBorder
        (
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(82, 41, 0)),
            new EmptyBorder(2, 2, 2, 2)
        ));

        JMenu visualizeMenu = createMenu("Visualize");
        visualizeMenu.add(createMenuItem("Full Screen (F11)", _ -> toggleFullScreen()));
        visualizeMenu.add(createMenuItem("Exit Game (ESC)", _ -> {
            if (UIStyleUtils.showConfirmDialog(_frame)) {
                System.exit(0);
            }
        }));
        menuBar.add(visualizeMenu);

        // NUOVO: Menu Info
        JMenu infoMenu = createMenu("Info");
        infoMenu.add(createMenuItem("Game Info (I)", _ -> toggleInfoPanel()));
        menuBar.add(infoMenu);

        JMenu gameMenu = createMenu("Game");
        // AGGIORNATO il testo per riflettere il nuovo tasto
        gameMenu.add(createMenuItem("Stats Panel (G)", _ -> toggleStatsPanel()));
        gameMenu.setVisible(false);
        menuBar.add(gameMenu);

        _frame.setJMenuBar(menuBar);
    }


    // Helper to create a styled JMenu with a fixed width
    private static JMenu createMenu(String title) 
    {
        JMenu menu = new JMenu(title);
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("Serif", Font.BOLD, 20));
        menu.setPreferredSize(new Dimension(160, 30));
        return menu;
    }


    // Helper to create a styled JMenuItem with fixed size and listener
    private static JMenuItem createMenuItem(String text, ActionListener listener) 
    {
        JMenuItem item = new JMenuItem(text);
        item.setOpaque(true);
        item.setBackground(UIStyleUtils.BUTTON_COLOR);
        item.setForeground(Color.WHITE);
        item.setFont(new Font("SansSerif", Font.PLAIN, 12));
        item.setBorder(new EmptyBorder(5, 10, 5, 10));
        item.addActionListener(listener);
        item.setPreferredSize(new Dimension(160, 25));
        return item;
    }


    // Toggles between full-screen and windowed states, restoring previous size
    public static void toggleFullScreen() 
    {
        _isResizingFromToggle = true;
        if (!_isFullScreen) 
        {
            if (_frame.getExtendedState() == _WINDOWED_STATE) 
            {
                _previousBounds = _frame.getBounds();
            }
            _frame.setExtendedState(_MAXIMIZED_STATE);
        } 
        else 
        {
            _frame.setExtendedState(_WINDOWED_STATE);
            if (_previousBounds != null) _frame.setBounds(_previousBounds);
        }
        _isFullScreen = !_isFullScreen;
        new Timer(100, _ -> _isResizingFromToggle = false).start();
        resizeGamePanels();
    }


    // NUOVO: Metodo helper per posizionare e scalare l'InfoPanel
    private static void positionAndScaleInfoPanel(InfoPanel infoPanel) {
        infoPanel.positionAndScale(_frame);
    }

    private static void resizeGamePanels() 
    {
        SwingUtilities.invokeLater(() -> 
        {
            Container content = _frame.getContentPane();
            int w = content.getWidth(), h = content.getHeight();
            if (w <= 0 || h <= 0) 
            {
                SwingUtilities.invokeLater(GameLauncher::resizeGamePanels);
                return;
            }
            
            GameActionPanel actionPanel = findComponentRecursive(GameActionPanel.class, content);
            if (actionPanel != null) 
            {
                actionPanel.scale(w, 75);
            }
            
            // SEMPLIFICATO: Usa il metodo helper
            InfoPanel infoPanel = findComponentRecursive(InfoPanel.class, _frame.getLayeredPane());
            if (infoPanel != null && infoPanel.isInfoVisible()) 
            {
                positionAndScaleInfoPanel(infoPanel);
            }
            
            content.revalidate(); 
            content.repaint();
        });
    }



    // Makes the "Game" menu visible in the menu bar
    public static void showGameMenu() 
    {
        JMenuBar bar = _frame.getJMenuBar();
        for (int i = 0; i < bar.getMenuCount(); i++) 
        {
            JMenu menu = bar.getMenu(i);
            if ("Game".equals(menu.getText())) 
            {
                menu.setVisible(true);
                break;
            }
        }
    }

    
    // Toggles visibility of the StatsPanel component
    private static void toggleStatsPanel() 
    {
        StatsPanel stats = findComponentRecursive(StatsPanel.class, _frame.getContentPane());
        if (stats != null) 
        {
            stats.toggleVisibility();
            
            GameActionPanel actionPanel = findComponentRecursive(GameActionPanel.class, _frame.getContentPane());
            if (actionPanel != null) 
            {
                if (actionPanel.isOverlayVisible()) 
                {
                    SwingUtilities.invokeLater(() -> 
                    {
                        actionPanel.repositionOverlay();
                    });
                }
                
                if (actionPanel.isTurnAnimationRunning()) 
                {
                    SwingUtilities.invokeLater(() -> 
                    {
                        actionPanel.repositionTurnNotification();
                    });
                }
            }
        }
    }

    
    // CORRETTO: Toggles visibility of the InfoPanel component
    private static void toggleInfoPanel() 
    {
        // CORRETTO: Cerca nel layered pane dove effettivamente viene aggiunto l'InfoPanel
        InfoPanel infoPanel = findComponentRecursive(InfoPanel.class, _frame.getLayeredPane());
        GameActionPanel actionPanel = findComponentRecursive(GameActionPanel.class, _frame.getContentPane());
        
        if (infoPanel != null) 
        {
            infoPanel.toggleVisibility();
            
            // NUOVO: Disattiva/attiva le interazioni
            if (actionPanel != null) {
                if (infoPanel.isInfoVisible()) {
                    actionPanel.setEndTurnButtonEnabled(false);
                } else {
                    actionPanel.setEndTurnButtonEnabled(true);
                }
            }
            
            // SEMPLIFICATO: Usa il metodo helper se il pannello è visibile
            if (infoPanel.isInfoVisible()) {
                positionAndScaleInfoPanel(infoPanel);
            }
        } 
        else 
        {            
            // CORRETTO: Usa il layered pane invece del content pane diretto
            JLayeredPane layeredPane = _frame.getLayeredPane();
            if (layeredPane != null) 
            {
                InfoPanel newInfoPanel = new InfoPanel(_frame);
                
                // IMPORTANTE: Prima aggiungi al parent
                layeredPane.add(newInfoPanel, JLayeredPane.POPUP_LAYER);
                
                // CRUCIALE: PRIMA imposta lo stato visibile interno
                newInfoPanel.setVisible(true);
                newInfoPanel._isVisible = true; // AGGIUNTO: Forza lo stato interno
                
                // NUOVO: Disattiva il bottone End Turn
                if (actionPanel != null) {
                    actionPanel.setEndTurnButtonEnabled(false);
                }
                
                // SEMPLIFICATO: Usa il metodo helper per posizionamento e scaling
                positionAndScaleInfoPanel(newInfoPanel);
                
                // Force immediate layout
                SwingUtilities.invokeLater(() -> {
                    layeredPane.revalidate();
                    layeredPane.repaint();
                });
            }
        }
        
        // Gestisci anche il riposizionamento di altre animazioni se necessario
        if (actionPanel != null) 
        {
            if (actionPanel.isOverlayVisible()) 
            {
                SwingUtilities.invokeLater(() -> actionPanel.repositionOverlay());
            }
            
            if (actionPanel.isTurnAnimationRunning()) 
            {
                SwingUtilities.invokeLater(() -> actionPanel.repositionTurnNotification());
            }
        }
    }
    
    // AGGIORNATO: Recursively finds a component of the given type
    private static <T> T findComponentRecursive(Class<T> type, Container container) 
    {
        for (Component c : container.getComponents()) 
        {
            if (type.isInstance(c)) {return type.cast(c);}
            if (c instanceof Container) 
            {
                T found = findComponentRecursive(type, (Container) c);
                if (found != null) return found;
            }
        }
        
        // AGGIUNTO: Cerca anche nel layered pane se il container è un JFrame
        if (container instanceof JFrame) {
            JFrame frame = (JFrame) container;
            JLayeredPane layeredPane = frame.getLayeredPane();
            if (layeredPane != null) {
                T found = findComponentRecursive(type, layeredPane);
                if (found != null) return found;
            }
        }
        
        return null;
    }


    // Interface for panels that can dynamically scale their contents
    public interface Scalable 
    {
        void scale(int width, int height);
    }


    // custom JMenuBar with vertical gradient background
    private static class GradientMenuBar extends JMenuBar 
    {
        @Override
        protected void paintComponent(Graphics g)
        {
            Graphics2D g2d = (Graphics2D) g.create();
            Color dark = UIStyleUtils.BUTTON_BORDER_COLOR;
            Color light = new Color(153, 102, 51);
            g2d.setPaint(new GradientPaint(0, 0, dark, 0, getHeight(), light));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }
}