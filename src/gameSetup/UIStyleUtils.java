package gameSetup;


import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/*
 * UIStyleUtils provides utility methods for creating styled UI components
 * even though sometimes I created UI components directly in the class that uses them
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

                // Set anti-aliasing and rendering hints to have a smooth outline
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

                // Draw main text
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
}