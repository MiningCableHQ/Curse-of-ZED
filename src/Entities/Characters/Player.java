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
            right1 = ImageIO.read(getClass().getResourceAsStream("/swordsman/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/swordsman/right1.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if(keyH.shiftPressed){
            entitySpeed = sprintSpeed;
        }
        if(!keyH.shiftPressed){
            entitySpeed = normalSpeed;
        }

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            if(keyH.upPressed){
                y -= entitySpeed;
            }
            if(keyH.downPressed){
                y += entitySpeed;
            }
            if(keyH.leftPressed){
                direction = "left";
                x -= entitySpeed;
            }
            if(keyH.rightPressed){
                direction = "right";
                x += entitySpeed;
            }

            spriteCounter++;
            if(spriteCounter > 10){
                if(spriteNum == 1){
                    spriteNum = 2;
                }else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2){
        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image  = null;

        switch(direction){
            case "up":
                if(direction.equals("left")){
                    if(spriteNum == 1){
                        image = left1;
                    }
                    if(spriteNum == 2){
                        image = left2;
                    }
                }
                if(direction.equals("right")){
                    if(spriteNum == 1){
                        image = right1;
                    }
                    if(spriteNum == 2){
                        image = right2;
                    }
                }
                break;
            case "down":
                if(direction.equals("left")){
                    image = left1;
                }
                if(direction.equals("right")){
                    image = right1;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }

        g2.drawImage(image,x,y, gp.tileSize, gp.tileSize, null);
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