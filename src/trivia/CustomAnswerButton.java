package trivia;

import gameSetup.UIStyleUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Custom button component specifically designed for quiz answers
 * with abilities to lock colors and handle different states
 */
public class CustomAnswerButton extends JButton {
    private Color currentBackground = new Color(245, 222, 179); // Light tan/beige background
    private Color currentForeground = new Color(139, 69, 19); // Brown text color
    private boolean colorLocked = false;
    
    public CustomAnswerButton(String text) {
        super(text);
        setupButton();
    }
    
    private void setupButton() {
        // Apply base styling
        setFont(UIStyleUtils.PROMPT_FONT.deriveFont(20f));
        setForeground(new Color(139, 69, 19)); // Brown text color
        setBackground(new Color(245, 222, 179)); // Light tan/beige background
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyleUtils.BUTTON_BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true); // Assicura che il background sia visibile
        
        // Add hover effect - solo sfondo con colore azzurrino chiaro
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!colorLocked) {
                    // Usa un colore azzurrino chiaro anziché tan scuro
                    setBackground(new Color(180, 200, 220)); // Colore azzurrino chiaro
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    
                    // Mantieni lo stesso colore del testo (non lo cambiare)
                    // Il colore del testo rimane quindi quello impostato sopra
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!colorLocked) {
                    setBackground(new Color(245, 222, 179)); // Torna al colore originale
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
        
        // Set fixed size for answer buttons
        setPreferredSize(new Dimension(280, 80));
    }
    
    
    /**
     * Locks the button color to prevent hover effects from changing it
     */
    public void lockColor(Color backgroundColor, Color foregroundColor) {
        this.colorLocked = true;
        this.currentBackground = backgroundColor;
        this.currentForeground = foregroundColor;
        super.setBackground(backgroundColor);
        super.setForeground(foregroundColor);
        
        // Update UI to reflect changes immediately
        repaint();
    }
    
    /**
     * Unlocks the button color to allow hover effects again
     */
    public void unlockColor() {
        this.colorLocked = false;
        super.setBackground(UIStyleUtils.BUTTON_COLOR);
        super.setForeground(new Color(139, 69, 19)); // Reset to brown text
    }
    
    @Override
    public void setBackground(Color bg) {
        if (!colorLocked) {
            currentBackground = bg;
            super.setBackground(bg);
        }
    }
    
    @Override
    public void setForeground(Color fg) {
        if (!colorLocked) {
            currentForeground = fg;
            super.setForeground(fg);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Background
        g2d.setColor(currentBackground);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        
        // Border
        g2d.setColor(UIStyleUtils.BUTTON_BORDER_COLOR);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 10, 10);
        
        // Text
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
    
    /**
     * Disables hover effects without locking the color
     */
    public void disableHover() {
        // Remove all mouse listeners to disable hover effects
        for (java.awt.event.MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }
        
        // Reset to default cursor
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}