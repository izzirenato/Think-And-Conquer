package trivia;

import javax.swing.*;

import gameSetup.UIStyleUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/*
 * Shows a circular timer that counts down the time left in seconds
 * The timer is animated, changing its appearance as the time progresses
 */

 
public class CircularTimer extends JPanel
{
    private int _timeLeft;
    private Color _normalColor = UIStyleUtils.GOLDEN_COLOR;
    private Color _warningColor = new Color(255, 0, 0);
    private int _warningThreshold = 5;
    private Font _timerFont;
    private float _opacity = 1.0f;

    // variables for animation
    private float _animationProgress = 0.0f;
    private boolean _isAnimating = false;
    private javax.swing.Timer _animationTimer;
    private static final int ANIMATION_FPS = 60;
    private static final int ANIMATION_DELAY = 1000 / ANIMATION_FPS;
    private long _startTime;


    // ctor
    public CircularTimer(int maxTime) 
    {
        _timeLeft = maxTime;
        _timerFont = new Font("Sans Serif", Font.BOLD, 36);
        setPreferredSize(new Dimension(90, 90));
        setOpaque(false);

        // Configures the animation timer
        _animationTimer = new javax.swing.Timer(ANIMATION_DELAY, new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                updateAnimation();
            }
        });
        _animationTimer.setRepeats(true);
        _animationTimer.start();
    }


    // set the time left in seconds
    public void setTime(int seconds) 
    {
        // if the time changes, start a new animation
        if (_timeLeft != seconds) 
        {
            _timeLeft = seconds;
            resetAnimation();
        }
        repaint();
    }


    // set opacity of the timer
    public void setOpacity(float opacity) 
    {
        _opacity = opacity;
        repaint();
    }
    

    // resets the animation progress and starts a new animation
    private void resetAnimation() 
    {
        _animationProgress = 0.0f;
        _isAnimating = true;
        _startTime = System.currentTimeMillis();
    }
    

    // controls the animation update
    private void updateAnimation() 
    {
        if (_isAnimating) 
        {
            long elapsed = System.currentTimeMillis() - _startTime;
            _animationProgress = (elapsed % 1000) / 1000.0f;
            
            repaint();
        }
    }


    // custom paint method to draw the circular timer
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // apply opacity
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, _opacity));

        // antialiasing for smoother edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height) - 16;
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        
        // if the time is less than warning threshold, change the color to red
        Color timerColor = _timeLeft <= _warningThreshold ? _warningColor : _normalColor;

        int arcAngle = (int)(360 * (1.0f - _animationProgress));
        
        g2d.setColor(timerColor);
        g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        if (arcAngle > 0) 
        {
            g2d.drawArc(x, y, size, size, 90, arcAngle);
        }

        // draw the number in the center
        g2d.setFont(_timerFont);
        
        String timeText = String.valueOf(_timeLeft);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(timeText);
        int textHeight = fm.getHeight();
        
        // shadow for the text
        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.drawString(timeText, width/2 - textWidth/2 + 1, height/2 + textHeight/3 + 1);

        // main text
        g2d.setColor(timerColor);
        g2d.drawString(timeText, width/2 - textWidth/2, height/2 + textHeight/3);
        
        g2d.dispose();
    }
}