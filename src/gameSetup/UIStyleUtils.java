package gameSetup;


import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/*
 * UIStyleUtils provides utility methods for creating the most common used UI components
 */


public class UIStyleUtils 
{
    public static final Color BUTTON_HOVER_COLOR = new Color(160, 82, 45);
    public static final Color BUTTON_COLOR = new Color(139, 69, 19);
    public static final Color BUTTON_BORDER_COLOR = new Color(101, 67, 33);
    public static final Color GOLDEN_COLOR = new Color(255, 215, 0);
    public static final Color SHADOW_COLOR = new Color(0, 0, 0, 128);

    public static Font TITLE_FONT;
    public static Font PROMPT_FONT;

    public static BufferedImage ICON;
    public static BufferedImage DIALOG_ICON;

    static
    {
        try 
        {
            TITLE_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/Title.otf")).deriveFont(Font.BOLD, 64f);
            PROMPT_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/Regular.otf")).deriveFont(Font.BOLD, 30f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(TITLE_FONT);
            ge.registerFont(PROMPT_FONT);
        }
        catch (IOException | FontFormatException e) 
        {
            System.err.println("Cannot load fonts: " + e.getMessage());
            TITLE_FONT= new Font("Serif", Font.BOLD, 64);
            PROMPT_FONT = new Font("SansSerif", Font.PLAIN, 30);
        }

        try 
        {
            ICON = ImageIO.read(new File("resources/images/icon.png"));
            DIALOG_ICON = ImageIO.read(new File("resources/images/dialogIcon.png"));        
        } 
        catch (Exception e) {System.err.println("Cannot load icons: " + e.getMessage());}
    }


    // creates almost every button in the game
    public static JButton createStyledButton(String text) 
    {
        JButton button = new JButton(text);
        button.setFont(PROMPT_FONT.deriveFont(24f));
        button.setForeground(BUTTON_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorder(BorderFactory.createCompoundBorder
        (
            BorderFactory.createLineBorder(BUTTON_BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setFocusPainted(false);
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {button.setBackground(BUTTON_HOVER_COLOR);}

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {button.setBackground(BUTTON_COLOR);}
        });

        return button;
    }


    // creates the current player label used in GameActionPanel
    public static JLabel createStyledLabel(String text, Font font) 
    {
        JLabel label = new JLabel(text) 
        {
            @Override
            protected void paintComponent(Graphics g) 
            {
                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                FontMetrics fm = g2d.getFontMetrics(getFont());
                String text = getText();

                // more smooth outline with iterations
                g2d.setStroke(new BasicStroke(2.0f));
                for(int i = 1; i <= 3; i++) 
                {
                    float alpha = 0.3f - (i * 0.1f);
                    g2d.setColor(new Color(1f, 1f, 1f, alpha));
                    g2d.drawString(text, i, fm.getAscent() + i);
                    g2d.drawString(text, i, fm.getAscent() - i);
                    g2d.drawString(text, -i, fm.getAscent() + i);
                    g2d.drawString(text, -i, fm.getAscent() - i);
                }

                g2d.setColor(getForeground());
                g2d.drawString(text, 0, fm.getAscent());

                g2d.dispose();
            }
        };

        label.setFont(font);
        return label;
    }


    // creates the dialog used when the user wants to exit the game
    public static boolean showConfirmDialog(JFrame parent)
    {
        JDialog dialog = new JDialog(parent, "Confirm Exit", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BUTTON_COLOR);
        panel.setBorder(BorderFactory.createLineBorder(GOLDEN_COLOR, 3));
        
        Image scaledImage = DIALOG_ICON.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon dialogIcon = new ImageIcon(scaledImage);
        JLabel iconLabel = new JLabel(dialogIcon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel message = new JLabel("Are you sure you want to exit?");
        message.setFont(PROMPT_FONT.deriveFont(Font.BOLD, 22f));
        message.setForeground(GOLDEN_COLOR);
        message.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(iconLabel, BorderLayout.WEST);
        center.add(message, BorderLayout.CENTER);

        panel.add(center, BorderLayout.CENTER);

        JButton yes = createStyledButton("Yes");
        yes.setFont(PROMPT_FONT.deriveFont(18f));
        yes.setPreferredSize(new Dimension( 100, 40));

        JButton no = createStyledButton("No");
        no.setFont(PROMPT_FONT.deriveFont(18f));
        no.setPreferredSize(new Dimension( 100, 40));

        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttons.add(yes);
        buttons.add(Box.createHorizontalStrut(10));
        buttons.add(no);

        panel.add(buttons, BorderLayout.SOUTH);
        dialog.add(panel);

        // action listeners can only access final variables but I need to store the result
        // so I created an array of boolean
        
        final boolean[] result = {false};

        yes.addActionListener(_ ->
        {
            result[0] = true;
            dialog.dispose();
        });
        
        no.addActionListener(_ ->
        {
            dialog.dispose();
        });

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);

        return result[0];  
    }


    // creates a styled scroll pane with a custom scrollbar and styled track/thumb
    public static JScrollPane createStyledScrollPane(JComponent component) 
    {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Increase scroll speed
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setBlockIncrement(60);

        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() 
        {
            @Override
            protected void configureScrollBarColors() 
            {
                thumbColor = UIStyleUtils.BUTTON_HOVER_COLOR;
                trackColor = new Color(60, 60, 90);
            }
            
            @Override
            public void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) 
            {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Paint rounded thumb with golden border
                g2.setColor(thumbColor);
                g2.fillRoundRect(thumbBounds.x + 1, thumbBounds.y, thumbBounds.width - 2, thumbBounds.height, 8, 8);
                
                g2.setColor(UIStyleUtils.GOLDEN_COLOR);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(thumbBounds.x + 1, thumbBounds.y, thumbBounds.width - 2, thumbBounds.height, 8, 8);
                
                g2.dispose();
            }
            
            @Override
            public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) 
            {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Paint rounded track
                g2.setColor(trackColor);
                g2.fillRoundRect(trackBounds.x + 2, trackBounds.y, trackBounds.width - 4, trackBounds.height, 6, 6);
                
                g2.dispose();
            }
            
            @Override
            protected JButton createDecreaseButton(int orientation) 
            {
                JButton button = createStyledScrollButton(orientation, true);
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                return button;
            }
            
            @Override
            protected JButton createIncreaseButton(int orientation) 
            {
                JButton button = createStyledScrollButton(orientation, false);
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                return button;
            }
            
            private JButton createStyledScrollButton(int orientation, boolean isUp) 
            {
                JButton button = new JButton() 
                {
                    @Override
                    protected void paintComponent(Graphics g) 
                    {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        
                        // Paint rounded background
                        g2.setColor(getBackground());
                        g2.fillRoundRect(1, 1, getWidth()-2, getHeight()-2, 4, 4);
                        
                        // Paint border
                        g2.setColor(UIStyleUtils.BUTTON_BORDER_COLOR);
                        g2.setStroke(new BasicStroke(1));
                        g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 4, 4);
                        
                        // Draw arrow - perfectly centered
                        g2.setColor(UIStyleUtils.GOLDEN_COLOR);
                        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        
                        int centerX = getWidth() / 2;
                        int centerY = getHeight() / 2;
                        int size = 4;
                        
                        if (isUp) {
                            // Up arrow
                            g2.drawLine(centerX - size, centerY + 2, centerX, centerY - 2);
                            g2.drawLine(centerX, centerY - 2, centerX + size, centerY + 2);
                        } else {
                            // Down arrow
                            g2.drawLine(centerX - size, centerY - 2, centerX, centerY + 2);
                            g2.drawLine(centerX, centerY + 2, centerX + size, centerY - 2);
                        }
                        
                        g2.dispose();
                    }
                };
                
                // Make sure the button fits the scrollbar width exactly
                button.setPreferredSize(new Dimension(16, 16));
                button.setMinimumSize(new Dimension(16, 16));
                button.setMaximumSize(new Dimension(16, 16));
                button.setBackground(UIStyleUtils.BUTTON_COLOR);
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setFocusPainted(false);
                button.setOpaque(false);
                
                // Add hover effect
                button.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) 
                    {
                        button.setBackground(UIStyleUtils.BUTTON_HOVER_COLOR);
                        button.repaint();
                    }
                    
                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) 
                    {
                        button.setBackground(UIStyleUtils.BUTTON_COLOR);
                        button.repaint();
                    }
                });
                
                return button;
            }
        });
        
        return scrollPane;
    }
}