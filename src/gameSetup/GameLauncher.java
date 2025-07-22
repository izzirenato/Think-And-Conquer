package gameSetup;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import trivia.DuelPanel;
import trivia.QuizPanel;

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


    // initializes the main JFrame, including size, listeners, key bindings, and menu bar
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

        // set initial size
        _previousBounds = new Rectangle(800, 600);
        _frame.setSize(_previousBounds.getSize());
        _frame.setMinimumSize(new Dimension(800, 600));
        _frame.addComponentListener(new ComponentAdapter() 
        {
            @Override
            public void componentResized(ComponentEvent e) 
            {
                if (!_isResizingFromToggle) {resizeGamePanels();}
            }
        });
        
        setupKeyBindings();
        setupMenuBar();
        _frame.setLocationRelativeTo(null);
        _frame.setVisible(true);
     }


    // displays the splash screen
    private static void showSplashScreen() 
    {
        SplashScreen splash = new SplashScreen(_frame);
        _frame.getContentPane().add(splash, BorderLayout.CENTER);
        _frame.pack();
    }


       // helper to check if focus is on a text component (e.g. JTextField, JTextArea)
    private static boolean isTypingInTextField() 
    {
        Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        return focusOwner instanceof javax.swing.text.JTextComponent;
    }

    // registers key bindings: F11 toggles full-screen, G toggles stats panel, I toggles info, SPACE for end turn
    private static void setupKeyBindings() 
    {
        JRootPane root = _frame.getRootPane();
        InputMap inMap = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actMap = root.getActionMap();

        // full screen (F11)
        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "TOGGLE_FULLSCREEN");
        actMap.put("TOGGLE_FULLSCREEN", new AbstractAction() 
        {
            @Override public void actionPerformed(ActionEvent e) 
            {
                if (isTypingInTextField()) return;
                toggleFullScreen();
            }
        });

        // stats panel (G)
        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0), "TOGGLE_STATS");
        actMap.put("TOGGLE_STATS", new AbstractAction() 
        {
            @Override public void actionPerformed(ActionEvent e) 
            {
                if (isTypingInTextField()) return;
                if (!isGameOverPanelActive()) 
                {
                    toggleStatsPanel();
                }
            }
        });

        // info panel (I)
        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "TOGGLE_INFO");
        actMap.put("TOGGLE_INFO", new AbstractAction() 
        {
            @Override public void actionPerformed(ActionEvent e) 
            {
                if (isTypingInTextField()) return;
                if (!isGameOverPanelActive()) 
                {
                    toggleInfoPanel();
                }
            }
        });

        // end turn (SPACE)
        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "END_TURN");
        actMap.put("END_TURN", new AbstractAction() 
        {
            @Override public void actionPerformed(ActionEvent e) 
            {
                if (isTypingInTextField()) return;
                if (!isGameOverPanelActive()) 
                {
                    triggerEndTurn();
                }
            }
        });

        // exit game (ESC)
        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "EXIT_GAME");
        actMap.put("EXIT_GAME", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) 
            {
                if (isTypingInTextField()) return;
                if (UIStyleUtils.showConfirmDialog(_frame)) 
                {
                    System.exit(0);
                }
            }
        });
    }


    // constructs a custom-styled menu bar with "Visualize", "Info" and (hidden) "Game" menus
    private static void setupMenuBar() 
    {
        JMenuBar menuBar = new GradientMenuBar();
        menuBar.setBorder(BorderFactory.createCompoundBorder
        (
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(82, 41, 0)),
            new EmptyBorder(2, 2, 2, 2)
        ));

        JMenu visualizeMenu = createMenu("Visualize");
        visualizeMenu.add(createMenuItem("Full Screen (F11)", _ -> toggleFullScreen()));
        visualizeMenu.add(createMenuItem("Exit Game (ESC)", _ -> 
        {
            if (UIStyleUtils.showConfirmDialog(_frame)) {System.exit(0);}
        }));
        menuBar.add(visualizeMenu);

        // info menu
        JMenu infoMenu = createMenu("Info");
        infoMenu.add(createMenuItem("Game Info (I)", _ -> toggleInfoPanel()));
        menuBar.add(infoMenu);

        // game menu (initially hidden)
        JMenu gameMenu = createMenu("Game");
        gameMenu.add(createMenuItem("Stats Panel (G)", _ -> toggleStatsPanel()));
        gameMenu.setVisible(false);
        menuBar.add(gameMenu);

        _frame.setJMenuBar(menuBar);
    }


    // helper to create a styled JMenu with a fixed width
    private static JMenu createMenu(String title) 
    {
        JMenu menu = new JMenu(title);
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("Serif", Font.BOLD, 20));
        menu.setPreferredSize(new Dimension(160, 30));
        return menu;
    }


    // helper to create a styled JMenuItem with fixed size and listener
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


    // checks if the GameOverPanel is currently active
    private static boolean isGameOverPanelActive() 
    {
        GameOverPanel gameOverPanel = findComponentRecursive(GameOverPanel.class, _frame.getContentPane());
        return gameOverPanel != null;
    }


    // end turn trigger
    private static void triggerEndTurn() 
    {
        // CHECK: Block if quiz or duel is active
        QuizPanel quizPanel = findComponentRecursive(QuizPanel.class, _frame.getContentPane());
        DuelPanel duelPanel = findComponentRecursive(DuelPanel.class, _frame.getContentPane());
        
        if (quizPanel != null || duelPanel != null) 
        {
            System.out.println("End turn blocked: Quiz/Duel in progress");
            return;
        }
        
        InfoPanel infoPanel = findComponentRecursive(InfoPanel.class, _frame.getLayeredPane());
        if (infoPanel != null && infoPanel.isInfoVisible()) 
        {
            System.out.println("End turn blocked: Info panel is visible");
            return;
        }
        
        GameActionPanel actionPanel = findComponentRecursive(GameActionPanel.class, _frame.getContentPane());
        if (actionPanel != null) 
        {
            Component[] components = actionPanel.getComponents();
            JButton endTurnButton = null;
            
            for (Component comp : components) 
            {
                if (comp instanceof JButton && "End Turn".equals(((JButton) comp).getText())) 
                {
                    endTurnButton = (JButton) comp;
                    break;
                }
            }
            
            if (endTurnButton != null) 
            {
                if (!endTurnButton.isVisible() || !endTurnButton.isEnabled()) 
                {
                    System.out.println("End turn blocked: Button is hidden or disabled");
                    return;
                }
                endTurnButton.doClick();
            }
        }
    }


    // toggles between full-screen and windowed states, restoring previous size
    public static void toggleFullScreen() 
    {        
        _isResizingFromToggle = true;
        if (!_isFullScreen) 
        {
            if (_frame.getExtendedState() == _WINDOWED_STATE) {_previousBounds = _frame.getBounds();}
            _frame.setExtendedState(_MAXIMIZED_STATE);
        } 
        else 
        {
            _frame.setExtendedState(_WINDOWED_STATE);
            if (_previousBounds != null) {_frame.setBounds(_previousBounds);}
        }
        _isFullScreen = !_isFullScreen;
        new Timer(100, _ -> _isResizingFromToggle = false).start();
        resizeGamePanels();
    }


    // toggles visibility of the StatsPanel component
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
                    SwingUtilities.invokeLater(() -> {actionPanel.repositionOverlay();});
                }
                
                if (actionPanel.isTurnAnimationRunning()) 
                {
                    SwingUtilities.invokeLater(() -> {actionPanel.repositionEllipse();});
                }
            }
        }
    }


    // toggles visibility of the InfoPanel component
    private static void toggleInfoPanel()
    {
        InfoPanel infoPanel = findComponentRecursive(InfoPanel.class, _frame.getLayeredPane());

        if (infoPanel != null) 
        {
            infoPanel.toggleVisibility();
            if (infoPanel.isInfoVisible()) {infoPanel.positionAndScale(_frame);}
        } 
        else 
        {
            InfoPanel newInfoPanel = new InfoPanel(_frame);
            _frame.getLayeredPane().add(newInfoPanel, JLayeredPane.POPUP_LAYER);
            newInfoPanel.toggleVisibility();
            if (newInfoPanel.isInfoVisible()) {newInfoPanel.positionAndScale(_frame);}
        }
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
            if (actionPanel != null) {actionPanel.scale(w, 75);}
            
            InfoPanel infoPanel = findComponentRecursive(InfoPanel.class, _frame.getLayeredPane());
            if (infoPanel != null && infoPanel.isInfoVisible()) 
            {
                infoPanel.positionAndScale(_frame);
            }
            
            content.revalidate(); 
            content.repaint();
        });
    }


    // hides the "Game" and "Info" menus in the menu bar
    public static void setGameMenus(boolean visible) 
    {
        JMenuBar bar = _frame.getJMenuBar();
        if (bar != null) 
        {
            for (int i = 0; i < bar.getMenuCount(); i++) 
            {
                JMenu menu = bar.getMenu(i);
                String menuText = menu.getText();
                if ("Game".equals(menuText) || "Info".equals(menuText)) {menu.setVisible(visible);}
            }
        }
    }


    // makes the "Game" menu visible in the menu bar
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



    // recursively searches for a component of the specified type within the container
    public static <T> T findComponentRecursive(Class<T> type, Container container) 
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
        
        return null;
    }


    // interface for panels that can dynamically scale their contents
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