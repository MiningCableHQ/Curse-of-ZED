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
    GamePanel gp;
    KeyHandler keyH;

    protected int level;
    protected int experience;
    protected int expNeeded;
    protected Inventory inventory;
    protected ArrayList<Move> moves;
    protected ArrayList<Move> moveset;

    public Player(GamePanel gp, KeyHandler keyH){
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
        worldX = 100;
        worldY = 100;
        entitySpeed = 4;
        normalSpeed = entitySpeed;
        sprintSpeed = 8;
        direction = "right";
        isMoving = false;
    }
    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/swordsman/right1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/swordsman/right1.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/swordsman/left1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/swordsman/left1.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/swordsman/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/swordsman/left1.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/swordsman/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/swordsman/right1.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        // Sprint Logic
        entitySpeed = keyH.shiftPressed ? sprintSpeed : normalSpeed;

        // Movement Logic
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) worldY -= entitySpeed;
            if (keyH.downPressed) worldY += entitySpeed;
            if (keyH.leftPressed) { direction = "left"; worldX -= entitySpeed; }
            if (keyH.rightPressed) { direction = "right"; worldX += entitySpeed; }

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }

        // --- SEQUENTIAL MAP TRANSITION ---

        // 1. Move from Map 1 to Map 2 (Exiting Right)
        if (worldX > gp.worldWidth) {
            gp.tileM.loadMap("/maps/map02.txt");
            worldX = 0;
        } else if (worldX < 0 && "/maps/map02.txt".equals(gp.tileM.currentMapName)) {
            gp.tileM.loadMap("/maps/map01.txt");
            worldX = gp.worldWidth - gp.tileSize;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up": case "down": case "left":
                image = (spriteNum == 1) ? left1 : left2; break;
            case "right":
                image = (spriteNum == 1) ? right1 : right2; break;
        }
        if (image != null) {
            g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
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
}