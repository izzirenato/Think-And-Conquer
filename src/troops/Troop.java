package troops;

import trivia.Question;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Troop {
    protected String _name;
    protected Question.Category _category;
    protected String _iconPath;
    private BufferedImage _iconImage;

    public Troop(String name, Question.Category category, String iconPath) {
        _name = name;
        _category = category;
        _iconPath = iconPath;
        loadIcon();
    }

    private void loadIcon() {
        try {
            File iconFile = new File("resources/images/" + _iconPath);
            if (!iconFile.exists()) {
                System.err.println("Icon file does not exist: " + iconFile.getAbsolutePath());
                return;
            }
            _iconImage = ImageIO.read(iconFile);
            if (_iconImage == null) {
                System.err.println("Failed to load icon for " + _name);
            }
        } catch (IOException e) {
            System.err.println("Cannot load icon for " + _name + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getName() { 
        return _name; 
    }

    public Question.Category getCategory() { 
        return _category; 
    }

    public BufferedImage getIcon() { 
        return _iconImage; 
    }

    public void verifyIcon() {
        File iconFile = new File("resources/images/" + _iconPath);
        System.out.println("Checking icon for " + _name);
        System.out.println("Path: " + iconFile.getAbsolutePath());
        System.out.println("File exists: " + iconFile.exists());
        System.out.println("File can read: " + iconFile.canRead());
        System.out.println("Icon loaded: " + (_iconImage != null));
        if (_iconImage != null) {
            System.out.println("Icon dimensions: " + _iconImage.getWidth() + "x" + _iconImage.getHeight());
        }
    }
}