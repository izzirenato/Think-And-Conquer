package trivia;


import gameSetup.UIStyleUtils;

import javax.swing.*;
import java.awt.*;


/*
 * custom button component specifically designed for quiz answers
 * with abilities to lock colors and handle different states
 */


public class CustomAnswerButton extends JButton 
{
    private Color currentBackground = new Color(245, 222, 179);
    private Color currentForeground = new Color(139, 69, 19);
    private boolean colorLocked = false;
    
    public CustomAnswerButton(String text) 
    {
        super(text);
        setupButton();
    }
    
    private void setupButton() 
    {
        setFont(UIStyleUtils.PROMPT_FONT.deriveFont(20f));
        setForeground(new Color(139, 69, 19));
        setBackground(new Color(245, 222, 179));
        setBorder(BorderFactory.createCompoundBorder
        (
            BorderFactory.createLineBorder(UIStyleUtils.BUTTON_BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        
        addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt)
             {
                if (!colorLocked) 
                {
                    setBackground(new Color(180, 200, 220));
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) 
            {
                if (!colorLocked) 
                {
                    setBackground(new Color(245, 222, 179));
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
        setPreferredSize(new Dimension(280, 80));
    }
    
    
    // locks the button color to prevent hover effects
    public void lockColor(Color backgroundColor, Color foregroundColor) 
    {
        colorLocked = true;
        currentBackground = backgroundColor;
        currentForeground = foregroundColor;
        super.setBackground(backgroundColor);
        super.setForeground(foregroundColor);
        
        repaint();
    }


    // unlocks the button color to allow hover effects again
    public void unlockColor() 
    {
        colorLocked = false;
        super.setBackground(UIStyleUtils.BUTTON_COLOR);
        super.setForeground(new Color(139, 69, 19));
    }

    // setters for background and foreground colors
    @Override
    public void setBackground(Color bg) 
    {
        if (!colorLocked) 
        {
            currentBackground = bg;
            super.setBackground(bg);
        }
    }
    @Override
    public void setForeground(Color fg) 
    {
        if (!colorLocked) 
        {
            currentForeground = fg;
            super.setForeground(fg);
        }
    }
    

    // custom paint component to draw the button with rounded corners and custom styles
    @Override
    protected void paintComponent(Graphics g) 
    {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // background
        g2d.setColor(currentBackground);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        
        // border
        g2d.setColor(UIStyleUtils.BUTTON_BORDER_COLOR);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 10, 10);
        
        // text
        FontMetrics fm = g2d.getFontMetrics(getFont());
        String text = getText();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() - textHeight) / 2 + fm.getAscent();
        
        g2d.setColor(currentForeground);
        g2d.setFont(getFont());
        g2d.drawString(text, x, y);
        
        g2d.dispose();
    }


    // disables hover effects without locking the color
    public void disableHover() 
    {
        for (java.awt.event.MouseListener listener : getMouseListeners()) 
        {
            removeMouseListener(listener);
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}