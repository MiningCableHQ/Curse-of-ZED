package Objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import Main.GamePanel;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Default size
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public boolean collisionOn = false;          // ← ADD THIS
    public String direction = "right";           // ← ADD THIS
    public int entitySpeed  = 0;                 // ← ADD THIS


    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Increase the boundary check (e.g., tileSize * 3) so large objects
        // don't pop out of existence at the screen edges.
        if (worldX + gp.tileSize * 3 > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize * 3 < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize * 3 > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize * 3 < gp.player.worldY + gp.player.screenY) {

            if (image != null) {
                g2.drawImage(image, screenX, screenY, image.getWidth() * gp.scale, image.getHeight() * gp.scale, null);
            }
        }
    }
}