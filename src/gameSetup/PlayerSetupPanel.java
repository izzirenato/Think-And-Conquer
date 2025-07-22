package gameSetup;


import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.List;

import map.MapPanel;

import troops.TroopManager;


/*
 * PlayerSetupPanel displays the player setup screen where players can enter their names and select colors
 */


public class PlayerSetupPanel extends JPanel implements GameLauncher.Scalable
{
    private JFrame _parentFrame;
    private BufferedImage _backgroundImage;
    private BufferedImage _loadingImage;
    private int _numPlayers = 2;
    private JTextField[] _playerNames;
    private JPanel _configPanel;
    private JSlider _playersSlider;
    private Font _titleFont = UIStyleUtils.TITLE_FONT;
    private Font _buttonFont = UIStyleUtils.PROMPT_FONT;
    private JButton[] _colorButtons;
    private Color[] _selectedColors;
    private final Color[] _availableColors =
    {
        Color.RED,
        Color.BLUE,
        Color.YELLOW,
        Color.GREEN,
        new Color(128, 0, 128), // purple
        Color.CYAN
    };
    private final String[] _colorNames =
    {
        "Red",
        "Blue",
        "Yellow",
        "Green",
        "Purple",
        "Cyan"
    };


    // ctor
    public PlayerSetupPanel(JFrame parentFrame)
    {
        _parentFrame = parentFrame;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        loadImages();
        setupUI();

        // Load troop icons at initialization
        try {TroopManager.loadTroopIcons();} 
        catch (Exception e) {System.err.println("Error during the icon load in the PSP: " + e.getMessage());}
    }


    // loads background images
    public void loadImages()
    {
        try
        {
            _backgroundImage = ImageIO.read(new java.io.File("resources/images/playerSetup.png"));
            _loadingImage = ImageIO.read(new java.io.File("resources/images/loading.png"));
        }
        catch (Exception e){System.err.println("Cannot load background images: " + e.getMessage());}
    }


    // setup the UI components
    private void setupUI()
    {
        _configPanel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.setColor(new Color(30,30,60,180));
                g.fillRoundRect(0,0, getWidth(), getHeight(), 20, 20);
            }
        };

        _configPanel.setOpaque(false);
        _configPanel.setLayout(new BoxLayout(_configPanel, BoxLayout.Y_AXIS));
        _configPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // title
        JLabel titleLabel = new JLabel("Setup players");
        titleLabel.setFont(_titleFont);
        titleLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        _configPanel.add(titleLabel);
        _configPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // slider for the number of players
        JLabel playersLabel = new JLabel("Number of players: ");
        playersLabel.setFont(_buttonFont);
        playersLabel.setForeground(Color.WHITE);
        playersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        _configPanel.add(playersLabel);
        _configPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        _playersSlider = new JSlider(2, 4, 2);
        _playersSlider.setMajorTickSpacing(1);
        _playersSlider.setPaintTicks(true);
        _playersSlider.setPaintLabels(true);
        _playersSlider.setSnapToTicks(true);
        _playersSlider.setOpaque(false);
        _playersSlider.setForeground(Color.WHITE);
        _playersSlider.setAlignmentX(Component.CENTER_ALIGNMENT);

        _playersSlider.setUI(new javax.swing.plaf.basic.BasicSliderUI(_playersSlider)
        {
            @Override
            public void paintThumb(Graphics g)
            {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(UIStyleUtils.GOLDEN_COLOR);
                g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
                g2d.setColor(UIStyleUtils.BUTTON_COLOR);
                g2d.drawOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
            }

            @Override
            public void paintTrack(Graphics g)
            {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(100, 100, 150));
                g2d.fillRoundRect(trackRect.x, trackRect.y + 5, trackRect.width, trackRect.height - 1, 5, 5);
            }
        });
        _configPanel.add(_playersSlider);
        _configPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // player names
        JPanel playerNamesPanel = new JPanel();
        playerNamesPanel.setLayout(new GridLayout(4,1,10,10));
        playerNamesPanel.setOpaque(false);
        _playerNames = new JTextField[4];
        _colorButtons = new JButton[4];
        _selectedColors = new Color[4];

        // preassigned colors
        System.arraycopy(_availableColors, 0, _selectedColors, 0, 4);

        for(int i = 0; i < 4; i++)
        {
            JPanel playerRow = new JPanel();
            playerRow.setLayout(new FlowLayout(FlowLayout.CENTER));
            playerRow.setOpaque(false);

            JLabel playerLabel = new JLabel("Player " + (i + 1) + ": ");
            playerLabel.setFont(_buttonFont);
            playerLabel.setForeground(Color.WHITE);
            playerLabel.setPreferredSize(new Dimension(120, 50));

            _playerNames[i] = new JTextField("Player " + (i + 1), 12);
            _playerNames[i].setFont(_buttonFont);
            _playerNames[i].setPreferredSize(new Dimension(150, 50));

            // this part of the code limits the player name length to 15 characters
            ((AbstractDocument) _playerNames[i].getDocument()).setDocumentFilter(new DocumentFilter() 
            {
                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException 
                {
                    String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                    String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                    
                    if (newText.length() <= 15) 
                    {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }

                @Override
                public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                    if (fb.getDocument().getLength() + text.length() <= 15) 
                    {
                        super.insertString(fb, offset, text, attr);
                    }
                }
            });

            _playerNames[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UIStyleUtils.BUTTON_COLOR, 2, true),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            final int playerIndex = i;
            _colorButtons[i] = createColorButton(_selectedColors[i], playerIndex);

            playerRow.add(playerLabel);
            playerRow.add(_playerNames[i]);
            playerRow.add(_colorButtons[i]);

            playerNamesPanel.add(playerRow);
            playerRow.setVisible(i < _numPlayers);
        }

        _configPanel.add(playerNamesPanel);
        _configPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // whenever the slider changes, update the number of players
        _playersSlider.addChangeListener(_ ->
        {
            int oldNumPlayers = _numPlayers;
            _numPlayers = _playersSlider.getValue();
            
            if (_numPlayers > oldNumPlayers) 
            {
                for (int i = oldNumPlayers; i < _numPlayers; i++) 
                {
                    // Trova il primo colore disponibile non utilizzato dagli altri giocatori
                    Color newColor = findAvailableColor(i);
                    _selectedColors[i] = newColor;
                    _colorButtons[i].setBackground(newColor);
                }
            }
            
            for(int i = 0; i < 4; i++) 
            {
                _playerNames[i].getParent().setVisible(i < _numPlayers);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        JButton startButton = UIStyleUtils.createStyledButton("Start Game");
        startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (validatePlayerNames()) {startGame();}
            }
        });

        JButton backButton = UIStyleUtils.createStyledButton("Back");
        backButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _parentFrame.getContentPane().removeAll();
                SplashScreen splashScreen = new SplashScreen(_parentFrame);
                _parentFrame.getContentPane().add(splashScreen);
                _parentFrame.revalidate();
                _parentFrame.repaint();
                splashScreen.requestFocusInWindow();
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(backButton);

        _configPanel.add(buttonPanel);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(_configPanel, gbc);
    }


    // creates the button for color selection
    private JButton createColorButton(Color initialColor, int playerIndex) 
    {
        JButton colorButton = new JButton()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(getBackground());
                g2d.fillOval(2, 2, getWidth() - 4, getHeight() - 4);

                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke((2)));
                g2d.drawOval(2, 2, getWidth() - 4, getHeight() - 4);
            }
        };

        colorButton.setPreferredSize(new Dimension(30, 30));
        colorButton.setBackground(initialColor);
        colorButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        colorButton.setContentAreaFilled(false);

        colorButton.addActionListener(_ -> showColorPopup(colorButton, playerIndex));
        colorButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return colorButton;
    }


    // shows the color selection popup
    private void showColorPopup(JButton sourceButton, int playerIndex)
    {
        JPopupMenu colorMenu = new JPopupMenu();

        JPanel colorPanel = new JPanel(new GridLayout(1, 0, 3, 3));
        colorPanel.setBackground(new Color(40,40,70));
        colorPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        for (int i = 0; i < _availableColors.length; i++)
        {
            final Color color = _availableColors[i];
            boolean used = false;

            for (int j = 0; j < _numPlayers; j++)
            {
                if (j != playerIndex && _selectedColors[j].equals(color))
                {
                    used = true;
                    break;
                }
            }

            if (used){continue;}


            // subclass with a hover flag
            class ColorCircle extends JPanel
            {
                private boolean hovered = false;

                @Override
                protected void paintComponent(Graphics g)
                {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    g2d.setColor(color);
                    g2d.fillOval(2,2, getWidth() - 4, getHeight() - 4);

                    g2d.setColor(hovered ? Color.WHITE : Color.BLACK);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawOval(2,2, getWidth() - 4, getHeight() - 4);
                }

                public void setHovered(boolean hovered)
                {
                    this.hovered = hovered;
                    repaint();
                }
            }

            ColorCircle colorChoice = new ColorCircle();
            colorChoice.setPreferredSize(new Dimension(25, 25));
            colorChoice.setOpaque(false);
            colorChoice.setToolTipText(_colorNames[i]);
            colorChoice.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            colorChoice.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    sourceButton.setBackground(color);
                    _selectedColors[playerIndex] = color;
                    colorMenu.setVisible(false);
                }

                @Override
                public void mouseEntered(MouseEvent e) {colorChoice.setHovered(true);}

                @Override
                public void mouseExited(MouseEvent e) {colorChoice.setHovered(false);}
            });
            colorPanel.add(colorChoice);
        }

        colorMenu.add(colorPanel);
        colorMenu.pack();

        int yOffset = (sourceButton.getHeight() - colorMenu.getPreferredSize().height) / 2;
        colorMenu.show(sourceButton, sourceButton.getWidth() + 5, yOffset);
    }


    // checks if player names are valid
    private boolean validatePlayerNames()
    {
        for (int i = 0; i < _numPlayers; i++)
        {
            if (_playerNames[i].getText().trim().isEmpty()) {return false;}
        }

        for (int i = 0; i < _numPlayers - 1; i++)
        {
            for (int j = i + 1; j < _numPlayers; j++)
            {
                if (_playerNames[i].getText().trim().equalsIgnoreCase(_playerNames[j].getText().trim())) {return false;}
            }
        }
        return true;
    }


    // public because it could be started from the GameOverPanel
    public void startGame()
    {
        // create custom loading screen
        JPanel loadingPanel = new JPanel(new GridBagLayout())
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(_loadingImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JPanel container = new JPanel(new GridBagLayout())
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.setColor(new Color(30, 30, 60, 180));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        container.setOpaque(false);
        container.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel loadingLabel = new JLabel("Loading game...");
        loadingLabel.setFont(_buttonFont);
        loadingLabel.setForeground(UIStyleUtils.GOLDEN_COLOR);
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(300, 30));
        progressBar.setBorder(BorderFactory.createLineBorder(UIStyleUtils.BUTTON_COLOR, 2));
        progressBar.setBackground(new Color(100, 100, 150));
        progressBar.setForeground(UIStyleUtils.GOLDEN_COLOR);
        progressBar.setFont(_buttonFont.deriveFont(20f));

        progressBar.setUI(new BasicProgressBarUI()
        {
            protected Color getSelectionForeground(){return UIStyleUtils.BUTTON_COLOR;}
            protected Color getSelectionBackground(){return UIStyleUtils.BUTTON_COLOR;}
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets (0, 0, 20, 0);
        container.add(loadingLabel, gbc);

        gbc.gridy = 1;
        container.add(progressBar, gbc);

        GridBagConstraints loadingGbc = new GridBagConstraints();
        loadingGbc.gridx = 0;
        loadingGbc.gridy = 1;
        loadingGbc.weighty = 0.33;
        loadingGbc.anchor = GridBagConstraints.CENTER;

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        GridBagConstraints spacerGbc = new GridBagConstraints();
        spacerGbc.gridx = 0;
        spacerGbc.gridy = 0;
        spacerGbc.weighty = 0.67;
        
        loadingPanel.add(spacer, spacerGbc);
        loadingPanel.add(container, loadingGbc);

        _parentFrame.getContentPane().removeAll();
        _parentFrame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints frameGbc = new GridBagConstraints();
        frameGbc.gridx = 0;
        frameGbc.gridy = 0;
        frameGbc.weighty = 1;
        frameGbc.weightx = 1.0;
        frameGbc.fill = GridBagConstraints.BOTH;
        _parentFrame.getContentPane().add(loadingPanel, frameGbc);
        _parentFrame.revalidate();
        _parentFrame.repaint();

        // GUI interaction tasks in background
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>()
        {
            private MapPanel _mapPanel;
            private GameActionPanel _gameActionPanel;
            private GameManager _gameManager;
            private StatsPanel _statsPanel;

            @Override
            protected Void doInBackground() throws Exception
            {
                Thread.sleep(100);

                // setup players
                publish(5);
                List<Player> players = new ArrayList<Player>();
                for (int i = 0; i < _numPlayers; i++)
                {
                    String name = _playerNames[i].getText();
                    Color color = _selectedColors[i];
                    players.add(new Player(color, name));
                }
                Thread.sleep(200);

                // create a new game manager
                publish(10);
                _gameManager = new GameManager(players);
                _statsPanel = new StatsPanel(_gameManager);
                _gameManager.setStatsPanel(_statsPanel);
                Thread.sleep(200);

                // loading map
                publish(20);
                _mapPanel = new MapPanel(_selectedColors, _gameManager);
                Thread.sleep(300);
                publish(40);

                // links game manager and map panel
                _gameManager.setMapPanel(_mapPanel);
                _gameManager.initializeInteractionHandlers(_mapPanel);

                // some debug info cause I've got some issues before
                System.out.println("Setting up map interaction handler: OK");
                System.out.println("Is map panel aware of handler: " + (_mapPanel.getInteractionHandler() != null));

                Thread.sleep(300);
                publish(60);

                // initialize game
                _gameManager.initializeGame();
                Thread.sleep(300);
                publish(80);

                // UI components
                publish(95);
                SwingUtilities.invokeAndWait(() -> 
                {
                    _gameActionPanel = new GameActionPanel(_gameManager, _parentFrame, _mapPanel);
                    _gameManager.setGameActionPanel(_gameActionPanel);
                });
                
                Thread.sleep(200);
                publish(100);
                Thread.sleep(100);
                
                return null;
            }

            @Override
            protected void process(List<Integer> chunks)
            {
                int latestProgress = chunks.get(chunks.size() - 1);
                progressBar.setValue(latestProgress);
            }

            @Override
            protected void done()
            {
                try 
                {
                    get();      
                    SwingUtilities.invokeLater(() -> 
                    {
                        try 
                        {
                            _parentFrame.getContentPane().removeAll();
                            _parentFrame.getContentPane().setLayout(new BorderLayout());
                            
                            if (_mapPanel != null && _gameActionPanel != null && _statsPanel != null) 
                            {
                                // clear any existing components first
                                _parentFrame.getContentPane().removeAll();
                                
                                // add components in correct order
                                _parentFrame.getContentPane().add(_mapPanel, BorderLayout.CENTER);
                                _parentFrame.getContentPane().add(_gameActionPanel, BorderLayout.SOUTH);
                                _parentFrame.getContentPane().add(_statsPanel, BorderLayout.EAST);
                                
                                // set initial stats panel visibility
                                _statsPanel.setVisible(false);
                                
                                GameLauncher.showGameMenu();
                                
                                // orce complete refresh
                                _parentFrame.getContentPane().revalidate();
                                _parentFrame.getContentPane().repaint();
                                
                                System.out.println("Game layout setup completed successfully");
                            } 
                            else 
                            {
                                System.err.println("ERROR: One or more panels are null!");
                            }
                        } 
                        catch (Exception e) {e.printStackTrace();}
                    });
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();

                    // gets back to the splash screen
                    _parentFrame.getContentPane().removeAll();
                    SplashScreen splashScreen = new SplashScreen(_parentFrame);
                    _parentFrame.getContentPane().add(splashScreen);
                    _parentFrame.revalidate();
                    _parentFrame.repaint();
                }
            }
        };

        // starts the worker
        worker.execute();
    }


    // draws the background image
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if(_backgroundImage != null) {g.drawImage(_backgroundImage, 0, 0, getWidth(), getHeight(), this);}
        else
        {
            // fallback
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }


    // sets the size of the panel
    public void scale(int width, int height)
    {
        setSize(width, height);
        repaint();
    }


    // public setters if the game is set from the GameOverPanel
    public void setNumPlayers(int numPlayers) 
    {
        _numPlayers = numPlayers;
        _playersSlider.setValue(numPlayers);
        for(int i = 0; i < 4; i++) 
        {
            _playerNames[i].getParent().setVisible(i < _numPlayers);
        }
    }

    public void setPlayerName(int index, String name) 
    {
        if (index >= 0 && index < _playerNames.length) 
        {
            _playerNames[index].setText(name);
        }
    }

    public void setPlayerColor(int index, Color color) 
    {
        if (index >= 0 && index < _selectedColors.length) 
        {
            _selectedColors[index] = color;
            _colorButtons[index].setBackground(color);
        }
    }

    // trova un colore disponibile che non sia già utilizzato
    private Color findAvailableColor(int playerIndex)
    {
        for (Color availableColor : _availableColors)
        {
            boolean isUsed = false;
            for (int i = 0; i < _numPlayers; i++)
            {
                if (i != playerIndex && _selectedColors[i].equals(availableColor))
                {
                    isUsed = true;
                    break;
                }
            }
            
            if (!isUsed)
            {
                return availableColor;
            }
        }
        return _availableColors[0];
    }
}