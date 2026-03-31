package Entities.Characters;
import Entities.Entity;
import Items.Inventory;
import Main.*;
import Moves.Move;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import Items.Weapons.Weapon;

public abstract class Player extends Entity {
    public final int screenX;
    public final int screenY;

    GamePanel gp;
    KeyHandler keyH;

    protected int level;
    protected int experience;
    protected int expNeeded;
    protected Inventory inventory;
    protected ArrayList<Move> moves;
    protected ArrayList<Move> moveset;
    protected Weapon weapon;

    public Player(GamePanel gp, KeyHandler keyH){
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();

        level = 1;
        experience = 0;
        expNeeded = 100;
        accuracy = 0.95;
        inventory = new Inventory();
        moves =  new ArrayList<>();
        moveset = new ArrayList<>();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 27;
        worldY = gp.tileSize * 27;
        entitySpeed = 4;
        normalSpeed = entitySpeed;
        sprintSpeed = 6;
        direction = "right";
        isMoving = false;
    }
    public abstract void getPlayerImage();

    public void update() {
        // Sprint Logic
        entitySpeed = keyH.shiftPressed ? sprintSpeed : normalSpeed;

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            else if (keyH.rightPressed) direction = "right";

            // CHECK COLLISION
            collisionOn = false;
            gp.cChecker.checkObject(this, true);

            // MOVE ONLY IF NOT COLLIDING
            if (!collisionOn) {
                switch (direction) {
                    case "up":    worldY -= entitySpeed; break;
                    case "down":  worldY += entitySpeed; break;
                    case "left":  worldX -= entitySpeed; break;
                    case "right": worldX += entitySpeed; break;
                }
            }

            // Animation
            spriteCounter++;
            if (spriteCounter > 5) {
                spriteNum++;
                if (spriteNum > 4) spriteNum = 1;
                spriteCounter = 0;
            }
            isMoving = true;
        } else {
            isMoving = false;
            spriteNum = 1;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                image = getWalkingImage("left");
                break;
            case "down":
                image = getWalkingImage("left");
                break;
            case "left":
                image = getWalkingImage("left");
                break;
            case "right":
                image = getWalkingImage("right");
                break;
        }

        if (image != null) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    // Helper method to get the correct walking frame
    private BufferedImage getWalkingImage(String dir) {
        if (!isMoving) {
            // Return idle frame when not moving
            return dir.equals("right") ? right1 : left1;
        }

        switch (spriteNum) {
            case 1:
                return dir.equals("right") ? right1 : left1;
            case 2:
                return dir.equals("right") ? right2 : left2;
            case 3:
                return dir.equals("right") ? right3 : left3;
            case 4:
                return dir.equals("right") ? right4 : left4;
            default:
                return dir.equals("right") ? right1 : left1;
        }
    }

    public abstract void loadMoves();

    public void levelUp(){
        level++;
        experience -= expNeeded;

        if(level <= 4){
            expNeeded = 100;
        }else if(level <= 7){
            expNeeded = 125;
        }else{
            expNeeded = 150;
        }
    }

    public boolean canGainExp(int chapter){
        return switch (chapter) {
            case 1 -> level < 4;
            case 2 -> level < 7;
            case 3 -> level < 10;
            default -> true;
        };
    }

    public void gainExp(int experience, int chapter) {
        if(canGainExp(chapter)){
            this.experience += experience;
            while (this.experience >= this.expNeeded && level < 10) {//level cap
                levelUp();
            }
        }else{
            //TODO Inform the player that he cannot gain exp
        }
    }

    //Getters and Setters
    public int getLevel(){
        return level;
    }
    public int getExperience(){
        return experience;
    }
    public int getExpNeeded(){
        return expNeeded;
    }
    public ArrayList<Move> getMoves(){
        return  moves;
    }
    public Weapon getWeapon(){
        return weapon;
    }
}
