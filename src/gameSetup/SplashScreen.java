package gameSetup;


import javax.imageio.ImageIO;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;


/*
 * SplashScreen displays the initial splash screen with the game title and start
 */


public class SplashScreen extends JPanel implements GameLauncher.Scalable
{
    private BufferedImage _backgroundImage;
    private final String _gameTitle = "Think And Conquer";
    private final String _startPrompt = "Press ENTER to start";
    private boolean _shouldBlink = true;
    private boolean _showPrompt = true;
    private Timer _blinkTimer;
    private JFrame _parentFrame;
    private static Font _titleFont = UIStyleUtils.TITLE_FONT.deriveFont(64f);
    private static Font _promptFont = UIStyleUtils.PROMPT_FONT.deriveFont(30f);


    // ctor
    public SplashScreen(JFrame parentFrame)
    {
        _parentFrame = parentFrame;
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        loadImage();
        setupKeyListener();
        setupBlinkEffect();
    }


    // load background image
    private void loadImage()
    {
        try
        {
            _backgroundImage = ImageIO.read(new File("resources/images/splash.png"));
        }
        catch (IOException e) {System.err.println("Cannot load the background image: " + e.getMessage());}
    }


    // setup key listener for ENTER key
    private void setupKeyListener()
    {
        addKeyListener(new KeyAdapter()
        {
            @Override public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {transitionToPlayerSetup();}
            }
        });
    }


    // start blinking effect for the prompt
    private void setupBlinkEffect()
    {
        _blinkTimer = new Timer(800, new ActionListener()
        {
            @Override public void actionPerformed(ActionEvent e)
            {
                _showPrompt = !_showPrompt;
                repaint();
            }
        });
        _blinkTimer.start();
    }


    // transition to player setup panel
    private void transitionToPlayerSetup()
    {
        _blinkTimer.stop();
        _shouldBlink = false;
        _parentFrame.getContentPane().removeAll();
        PlayerSetupPanel playerSetupPanel = new PlayerSetupPanel(_parentFrame);
        _parentFrame.getContentPane().add(playerSetupPanel);
        _parentFrame.revalidate();
        _parentFrame.repaint();
        playerSetupPanel.requestFocusInWindow();
    }


    // paint splash screen graphics
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (_backgroundImage != null)
        {
            g.drawImage(_backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        else
        {
            // fallback
            g2d.setColor(new Color(30, 30, 60));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        float titlePositionRatio = 0.35f;
        float promptPositionRatio = 0.45f;

        int titleY = (int) (getHeight() * titlePositionRatio);
        int promptY = (int) (getHeight() * promptPositionRatio);

        g2d.setFont(_titleFont);
        FontMetrics titleMetrics = g2d.getFontMetrics();
        int titleX = (getWidth() - titleMetrics.stringWidth(_gameTitle)) / 2;

        // draw title shadow and text
        g2d.setColor(UIStyleUtils.SHADOW_COLOR);
        g2d.drawString(_gameTitle, titleX + 3, titleY + 3);
        g2d.setColor(UIStyleUtils.GOLDEN_COLOR);
        g2d.drawString(_gameTitle, titleX, titleY);

        if (_showPrompt && _shouldBlink)
        {
            g2d.setFont(_promptFont);
            FontMetrics promptMetrics = g2d.getFontMetrics();
            int promptX = (getWidth() - promptMetrics.stringWidth(_startPrompt)) / 2;

            // draw prompt shadow and text
            g2d.setColor(new Color(0, 0, 0, 128));
            g2d.drawString(_startPrompt, promptX + 2, promptY + 2);
            g2d.setColor(new Color(200, 180, 0));
            g2d.drawString(_startPrompt, promptX, promptY);
        }
    }


    // scale panel size and repaint
    @Override
    public void scale(int width, int height)
    {
        setSize(width, height);
        repaint();
    }


    // get title font
    public static Font getTitleFont() { return _titleFont; }


    // get prompt font
    public static Font getPromptFont() { return _promptFont; }
}
