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
        x = 100;
        y = 100;
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
            right1 = ImageIO.read(getClass().getResourceAsStream("/swordsman/walking/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/swordsman/walking/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/swordsman/walking/right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/swordsman/walking/right4.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        // Sprint Logic
        entitySpeed = keyH.shiftPressed ? sprintSpeed : normalSpeed;

        // Movement Logic
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) y -= entitySpeed;
            if (keyH.downPressed) y += entitySpeed;
            if (keyH.leftPressed) { direction = "left"; x -= entitySpeed; }
            if (keyH.rightPressed) { direction = "right"; x += entitySpeed; }

            spriteCounter++;
            if (spriteCounter > 10) {
                if(spriteNum == 1){
                    spriteNum = 2;
                }  
                if(spriteNum == 2){
                    spriteNum = 3;
                }  
                if(spriteNum == 3){
                    spriteNum = 4;
                }  
                if(spriteNum == 4){
                    spriteNum = 1;
                }  
                spriteCounter = 0;
            }
        }

        // --- SEQUENTIAL MAP TRANSITION ---

        // 1. Move from Map 1 to Map 2 (Exiting Right)
        if (x > gp.screenWidth) {
            gp.tileM.loadMap("/maps/map02.txt");
            x = 0;
        }
        else if (x < -gp.tileSize && "/maps/map02.txt".equals(gp.tileM.currentMapName)) {
            gp.tileM.loadMap("/maps/map01.txt");
            x = gp.screenWidth - gp.tileSize;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Using left/right sprites even for up/down movement
        switch (direction) {
            case "up":
            case "down":
            case "left":
                image = (spriteNum == 1) ? left1 : left2;
                break;
            case "right":
                switch(spriteNum){
                    case 1:
                        image = right1;
                        break;
                    case 2:
                        image = right2;
                        break;
                    case 3:
                        image = right3;
                        break;
                    case 4:
                        image = right4;
                        break;
                }  
                break;
        }

        if (image != null) {
            g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
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
