package gameSetup;

import java.awt.*;

import javax.swing.*;

public class AnimatedEllipse extends JPanel
{
    public String _text;
    public Color _ellipseColor;
    public Color _textColor;
    private float _opacity = 0.0f;
    private Timer _animationTimer;
    private boolean _isShowing = false;

    // creates a thread
    private Runnable _onAnimationComplete;

    // animation constants
    private static final int ANIMATION_DURATION = 2000;
    private static final int FADE_IN_DURATION = 500;
    private static final int DISPLAY_DURATION = 1000;
    private static final int FADE_OUT_DURATION = 500;
    private static final int ANIMATION_FPS = 60;
    private static final int FRAME_DELAY = 1000 / ANIMATION_FPS;


    // ctor
    public AnimatedEllipse(String text, Color ellipseColor, Color textColor) 
    {
        _text = text;
        _ellipseColor = ellipseColor;
        _textColor = textColor;

        setOpaque(false);
        setVisible(false);
    }


    // starts the animation
    public void startAnimation(Runnable onComplete)
    {
        // prevents multiple animation
        if (_isShowing) {return;}

        _onAnimationComplete = onComplete;
        _isShowing = true;
        _opacity = 0.0f;

        setVisible(true);
        
        final long startTimer = System.currentTimeMillis();

        _animationTimer = new Timer(FRAME_DELAY, _ -> 
        {
            long elapsedTime = System.currentTimeMillis() - startTimer;

            if (elapsedTime <= FADE_IN_DURATION) 
            {
                _opacity = (float) elapsedTime / FADE_IN_DURATION;
            } 
            else if (elapsedTime <= FADE_IN_DURATION + DISPLAY_DURATION) 
            {
                _opacity = 1.0f;
            } 
            else if (elapsedTime <= ANIMATION_DURATION) 
            {
                float fadeOutProgress = (float) (elapsedTime - (FADE_IN_DURATION + DISPLAY_DURATION)) / FADE_OUT_DURATION;
                _opacity = 1.0f - fadeOutProgress;
            } 
            else 
            {
                stopAnimation();
                return;
            }
            repaint();
        });
        _animationTimer.start();
    }


    // stops the animation
    private void stopAnimation() 
    {
        if (_animationTimer != null)
        {
            _animationTimer.stop();
            _animationTimer = null;
        }

        _isShowing = false;
        setVisible(false);

        if (_onAnimationComplete != null) 
        {
            _onAnimationComplete.run();
            _onAnimationComplete = null;
        }
    }


    // custom paint component
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (!_isShowing || _text == null) {return;}

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // semi-transparent background overlay
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, _opacity * 0.7f));
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        int maxEllipseWidth = Math.min(getWidth() * 3 / 4, 600);
        int maxEllipseHeight = Math.min(getHeight() / 6, 150);
        int ellipseWidth = Math.max(maxEllipseWidth, 300);
        int ellipseHeight = Math.max(maxEllipseHeight, 80); 
        
        int ellipseX = (getWidth() - ellipseWidth) / 2;
        int ellipseY = (getHeight() - ellipseHeight) / 2;
        
        // draw ellipse shadow
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, _opacity * 0.3f));
        g2d.setColor(Color.BLACK);
        g2d.fillOval(ellipseX + 5, ellipseY + 5, ellipseWidth, ellipseHeight);
        
        // draw main ellipse
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, _opacity));
        g2d.setColor(_ellipseColor);
        g2d.fillOval(ellipseX, ellipseY, ellipseWidth, ellipseHeight);
        
        // draw ellipse border
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.setColor(UIStyleUtils.GOLDEN_COLOR);
        g2d.drawOval(ellipseX, ellipseY, ellipseWidth, ellipseHeight);
        
        // draw text with scalable font
        float fontSize = Math.max(20f, Math.min(36f, ellipseWidth / 15f)); // Font scalabile
        g2d.setFont(UIStyleUtils.PROMPT_FONT.deriveFont(Font.BOLD, fontSize));
        FontMetrics fm = g2d.getFontMetrics();
        
        int textWidth = fm.stringWidth(_text);
        int textX = (getWidth() - textWidth) / 2;
        int textY = getHeight() / 2 + fm.getAscent() / 2;
        
        // text shadow
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, _opacity * 0.5f));
        g2d.setColor(Color.BLACK);
        g2d.drawString(_text, textX + 2, textY + 2);
        
        // main text
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, _opacity));
        g2d.setColor(_textColor);
        g2d.drawString(_text, textX, textY);
        
        g2d.dispose();
    }

    
    // public method to check if the animation is currently running
    public boolean isAnimating()
    {
        return _isShowing;
    }   
}
