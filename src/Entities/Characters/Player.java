package Entities.Characters;

import Entities.Entity;
import Items.Inventory;
import Items.Weapons.Mage.ElementalCodex;
import Main.*;
import Moves.Move;
import Items.Consumables.Buff.LesserHardening;
import Items.Consumables.Buff.LesserPower;
import Items.Consumables.Buff.Power;
import Items.Consumables.Heal.GreaterHealing;
import Items.Consumables.Heal.LesserHealing;
import Items.Weapons.Mage.AnkhStaff;
import Items.Weapons.Mage.Arcanum;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import Items.Weapons.Weapon;

import Items.Consumables.Debuff.Blinding.LesserBlinding;
import Items.Consumables.Debuff.Clumsiness.LesserClumsiness;
import Items.Consumables.Debuff.Dulling.LesserDulling;
import Items.Consumables.Debuff.Softening.LesserSoftening;
import Items.Weapons.Ranger.Swiftwind;
import Items.Weapons.Swordsman.Unyielding;

public abstract class Player extends Entity {
    public final int screenX;
    public final int screenY;

    GamePanel gp;
    KeyHandler keyH;

    protected int experience;
    protected int expNeeded;
    protected Inventory inventory;
    protected ArrayList<Move> moves;
    protected ArrayList<Move> moveset;
    protected Weapon weapon;

    // Idle animation arrays
    protected BufferedImage[] idleLeft = new BufferedImage[5];
    protected BufferedImage[] idleRight = new BufferedImage[5];
    protected int idleSpriteNum = 0;
    protected int idleSpriteCounter = 0;
    protected static final int IDLE_ANIMATION_SPEED = 8; // Frames per sprite change

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

        loadInventory();
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
            // Reset idle animation when starting to move
            if (!isMoving) {
                idleSpriteNum = 0;
                idleSpriteCounter = 0;
            }

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

            // Walking animation
            spriteCounter++;
            if (spriteCounter > 5) {
                spriteNum++;
                if (spriteNum > 4) spriteNum = 1;
                spriteCounter = 0;
            }
            isMoving = true;
        } else {
            isMoving = false;

            // Idle animation when not moving
            idleSpriteCounter++;
            if (idleSpriteCounter > IDLE_ANIMATION_SPEED) {
                idleSpriteNum++;
                if (idleSpriteNum >= 5) idleSpriteNum = 0;
                idleSpriteCounter = 0;
            }

            // Reset walking sprite to first frame
            spriteNum = 1;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (isMoving) {
            // Walking animation
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
        } else {
            // Idle animation
            switch (direction) {
                case "up":
                case "down":
                case "left":
                    image = getIdleImage("left");
                    break;
                case "right":
                    image = getIdleImage("right");
                    break;
            }
        }

        if (image != null) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    // Helper method to get the correct walking frame
    private BufferedImage getWalkingImage(String dir) {
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

    // Helper method to get the correct idle frame
    private BufferedImage getIdleImage(String dir) {
        if (dir.equals("right")) {
            if (idleRight[idleSpriteNum] != null) {
                return idleRight[idleSpriteNum];
            }
        } else {
            if (idleLeft[idleSpriteNum] != null) {
                return idleLeft[idleSpriteNum];
            }
        }
        // Fallback to first walking frame if idle frames not available
        return dir.equals("right") ? right1 : left1;
    }

    public abstract void loadMoves();

    public void loadInventory(){
        inventory.addItem(new LesserHealing(), 5);
        inventory.addItem(new LesserHardening(), 3);
        inventory.addItem(new LesserPower(), 3);
        inventory.addItem(new LesserSoftening(), 2);

        inventory.addItem(new ElementalCodex());
        inventory.addItem(new Swiftwind());
        inventory.addItem(new Unyielding());
    }

    public void levelUp(){
        level++;
        experience -= expNeeded;

        // Base stat increases (flat amounts)
        double hpIncrease = 110;
        double attackIncrease = 5;
        double defenseIncrease = 3;
        double speedIncrease = 3;

        //For unique class level up
        if (this instanceof Swordsman){
            hpIncrease = 300;
        } else if (this instanceof Ranger) {
            speedIncrease = 6;
        } else if (this instanceof Mage){
            attackIncrease = 10;
        }

        // Apply stat increases
        maxHp += hpIncrease;
        hp = maxHp; // Heal to full on level up
        maxAttack += attackIncrease;
        attack = maxAttack;
        maxDefense += defenseIncrease;
        defense = maxDefense;
        speed += speedIncrease;

        if(level < 4){
            expNeeded = 100;
        }else if(level < 7){
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

    public boolean canEquipWeapon(Weapon weapon) {
        String weaponPath = weapon.getImagePath();
        if (weaponPath == null) return false;

        if (this instanceof Swordsman) {
            return weaponPath.contains("warrior_weapon");
        } else if (this instanceof Ranger) {
            return weaponPath.contains("archer_weapon");
        } else if (this instanceof Mage) {
            return weaponPath.contains("mage_weapon");
        }
        return false;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        // Recalculate attack (original attack + weapon bonus)
        if (weapon != null) {
            this.attack = this.maxAttack + weapon.getAttack();
        } else {
            this.attack = this.maxAttack;
        }
    }

    //Getters and Setters
    public double getWeaponAttackBonus() {
        return (weapon != null) ? weapon.getAttack() : 0;
    }
    public int getExperience(){
        return experience;
    }
    public int getExpNeeded(){
        return expNeeded;
    }
    public ArrayList<Move> getMoves(){
        return moves;
    }
    public Weapon getWeapon(){
        return weapon;
    }
    public Inventory getInventory(){
        return inventory;
    }
}