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

        setupKeyBindings();
        setupMenuBar();
        _frame.setLocationRelativeTo(null);
        _frame.setVisible(true);
    }


    // Displays the splash screen
    private static void showSplashScreen() 
    {
        SplashScreen splash = new SplashScreen(_frame);
        _frame.getContentPane().add(splash, BorderLayout.CENTER);
        _frame.pack();
    }


    // Registers key bindings: F11 toggles full-screen, Space toggles stats panel
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

        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "TOGGLE_STATS");
        actMap.put("TOGGLE_STATS", new AbstractAction() 
        {
            @Override public void actionPerformed(ActionEvent e) {toggleStatsPanel();}
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

    
    // Constructs a custom-styled menu bar with "Visualize" and (hidden) "Game" menus
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

        JMenu gameMenu = createMenu("Game");
        gameMenu.add(createMenuItem("Stats Panel (Space)", _ -> toggleStatsPanel()));
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


    // Invokes scaling on game panels after layout updates
    private static void resizeGamePanels() 
    {
        SwingUtilities.invokeLater(() -> 
        {
            Container content = _frame.getContentPane();
            int w = content.getWidth(), h = content.getHeight();
            if (w <= 0 || h <= 0) 
            {
                // it's a sort of wait and try again later
                SwingUtilities.invokeLater(GameLauncher::resizeGamePanels);
                return;
            }
            Scalable control = findScalableComponent(content, "MapPanel", false);
            if (control != null) {control.scale(w, 50);}
            Scalable map = findScalableComponent(content, "MapPanel", true);
            if (map != null) {map.scale(w, h - 50);}
            content.revalidate(); content.repaint();
        });
    }


    // Recursively searches for a Scalable component by class name match
    private static Scalable findScalableComponent(Container container, String className, boolean matchName) 
    {
        for (Component c : container.getComponents()) 
        {
            if (c instanceof Scalable) 
            {
                boolean nameMatch = c.getClass().getSimpleName().equalsIgnoreCase(className);
                if ((matchName && nameMatch) || (!matchName && !nameMatch)) {return (Scalable) c;}
            }
            if (c instanceof Container) 
            {
                Scalable result = findScalableComponent((Container) c, className, matchName);
                if (result != null) return result;
            }
        }
        return null;
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
        if (stats != null) stats.toggleVisibility();
    }

    
    // Recursively finds a component of the given type (if I have to be honest I didn't really understand how does it work)
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