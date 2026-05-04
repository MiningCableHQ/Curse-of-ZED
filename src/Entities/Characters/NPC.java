package Entities.Characters;

import Main.GamePanel;
import Objects.SuperObject;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import Dialogue.*;

public abstract class NPC extends SuperObject {

    protected GamePanel gp;
    public String npcName;
    public boolean interacted = false; // set true after first full dialogue
    public boolean available  = true;  // can be toggled off after story events
    public boolean showOnMinimap = true;
    // Portrait image shown in dialogue box (250x250)
    protected BufferedImage portrait;

    // Interaction range (pixels from player center)
    public int interactRange = 80;

    // Idle animation
    protected BufferedImage[] idleFrames = new BufferedImage[2];
    protected int idleSpriteNum = 0;
    protected int idleSpriteCounter = 0;
    protected static final int IDLE_ANIMATION_SPEED = 15; // frames per sprite change

    public NPC(GamePanel gp) {
        this.gp = gp;
        collision = true; // NPCs block walking via solidArea but don't block map collision
        solidArea = new Rectangle(0, 0, 48, 48);
        solidAreaDefaultX = 8;
        solidAreaDefaultY = 8;
    }

    protected BufferedImage loadPortrait(String path) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (Exception e) {
            System.err.println("Portrait not found: " + path);
            return null;
        }
    }
    public void updateAnimation() {
        idleSpriteCounter++;
        if (idleSpriteCounter >= IDLE_ANIMATION_SPEED) {
            idleSpriteNum = (idleSpriteNum + 1) % 2; // toggles between 0 and 1
            idleSpriteCounter = 0;
            image = idleFrames[idleSpriteNum]; // swap the current image
        }
    }

    /**
     * Returns the dialogue tree for this NPC.
     * Each DialoguePage holds lines + optional choices.
     */
    public abstract DialogueTree getDialogue(String playerClassName);

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        boolean onScreen =
                worldX + gp.tileSize * 3 > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.tileSize * 3 < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.tileSize * 3 > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.tileSize * 3 < gp.player.worldY + gp.player.screenY;

        if (!onScreen || !available) return;

        if (image != null) {
            g2.drawImage(image, screenX, screenY,
                    image.getWidth() * gp.scale, image.getHeight() * gp.scale, null);
        }
    }

    /** Returns true if the player is within interaction range */
    public boolean isPlayerNearby() {
        if (gp.player == null) return false;
        int px = gp.player.worldX + gp.tileSize / 2;
        int py = gp.player.worldY + gp.tileSize / 2;
        int nx = worldX + gp.tileSize / 2;
        int ny = worldY + gp.tileSize / 2;
        double dist = Math.sqrt(Math.pow(px - nx, 2) + Math.pow(py - ny, 2));
        return dist < interactRange;
    }
}