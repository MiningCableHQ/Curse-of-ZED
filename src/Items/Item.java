package Items;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Item {
    protected String name;
    protected double tier;
    protected String description;
    protected int quantity;
    protected BufferedImage image;
    protected String useMessage;

    public enum TargetType {
        SELF,
        ENEMY,
        ALL_ENEMIES
    }

    // Constructor
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        this.quantity = 0;
    }

    public TargetType getTargetType() {
        return TargetType.SELF;
    }

    public abstract <T> void useItem(T Entity);

    public void loadImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                this.image = javax.imageio.ImageIO.read(getClass().getResourceAsStream(imagePath));
            } catch (Exception e) {
                this.image = null;
                System.err.println("Failed to load image for " + name + ": " + imagePath);
            }
        } else {
            createPlaceholderImage();
        }
    }

    private void createPlaceholderImage() {
        int size = 32;
        image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        g2.setColor(new Color(150, 150, 200));
        g2.fillRect(0, 0, size, size);
        g2.setColor(new Color(80, 80, 140));
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(2, 2, size - 5, size - 5);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.BOLD, 20));
        FontMetrics fm = g2.getFontMetrics();
        String initial = getItemInitial();
        int x = (size - fm.stringWidth(initial)) / 2;
        int y = (size + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(initial, x, y);

        g2.setColor(new Color(150, 255, 150));
        g2.fillOval(size - 12, size - 12, 8, 8);
        g2.setColor(Color.WHITE);
        g2.drawString("↑", size - 12, size - 6);

        g2.dispose();
    }

    protected String getItemInitial() {
        if (name != null && !name.isEmpty()) {
            return String.valueOf(name.charAt(0));
        }
        return "I";
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getUseMessage() {
        return useMessage;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setter for quantity (if needed)
    public void setQuantity(int quantity) {
        this.quantity = Math.max(0, quantity);
    }

    public void setUseMessage(String useMessage) {
        this.useMessage = useMessage;
    }

    public void setTier(double tier) {
        this.tier = tier;
    }

    public double getTier() {
        return tier;
    }
}