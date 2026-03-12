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
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
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

        // Movement Logic
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                worldY -= entitySpeed;
            }
            if (keyH.downPressed) {
                worldY += entitySpeed;
            }
            if (keyH.leftPressed) {
                direction = "left";
                worldX -= entitySpeed;
            }
            if (keyH.rightPressed) {
                direction = "right";
                worldX += entitySpeed;
            }

            // Animation counter
            spriteCounter++;
            if (spriteCounter > 5) {
                spriteNum++;
                if (spriteNum > 4) {
                    spriteNum = 1;
                }
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

    public void gainExp(int experience){
        this.experience += experience;
        if(this.experience >= this.expNeeded){
            levelUp();
        }
    }
    public void levelUp(){
        //TODO implement max level threshold per chapter/map

        level++;

        experience -= expNeeded;

        if(level <= 4){
            expNeeded = 100;
        }else if(level <= 7){
            expNeeded = 120;
        }else{
            expNeeded = 150;
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
}
