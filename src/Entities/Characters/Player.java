package Entities.Characters;

import Entities.Entity;
import Items.Consumables.Buff.*;
import Items.Inventory;
import Items.Weapons.Mage.ElementalCodex;
import Items.Weapons.Ranger.Mistwood;
import Items.Weapons.Ranger.Slowstring;
import Items.Weapons.Swordsman.RazorEdge;
import Items.Weapons.Swordsman.Stunblade;
import Main.*;
import Moves.Move;
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

    // Money for shop system
    protected int money;

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

        money = 0; // Initialize money

        level = 1;
        experience = 0;
        expNeeded = 100;
        accuracy = 0.90;
        inventory = new Inventory();
        moves = new ArrayList<>();
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

        boolean moving = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

        if (moving) {
            // Reset idle animation when starting to move
            if (!isMoving) {
                idleSpriteNum = 0;
                idleSpriteCounter = 0;
            }

            // Set facing direction — horizontal takes priority for animation
            if (keyH.leftPressed)       direction = "left";
            else if (keyH.rightPressed) direction = "right";
            else if (keyH.upPressed)    direction = "up";
            else                        direction = "down";

            // Check and apply horizontal movement independently
            if (keyH.leftPressed || keyH.rightPressed) {
                direction = keyH.leftPressed ? "left" : "right";
                collisionOn = false;
                gp.cChecker.checkObject(this, true);
                if (!collisionOn) {
                    worldX += keyH.leftPressed ? -entitySpeed : entitySpeed;
                }
            }

            // Check and apply vertical movement independently
            if (keyH.upPressed || keyH.downPressed) {
                direction = keyH.upPressed ? "up" : "down";
                collisionOn = false;
                gp.cChecker.checkObject(this, true);
                if (!collisionOn) {
                    worldY += keyH.upPressed ? -entitySpeed : entitySpeed;
                }
            }

            // Restore facing direction for animation after axis checks
            if (keyH.leftPressed)       direction = "left";
            else if (keyH.rightPressed) direction = "right";
            else if (keyH.upPressed)    direction = "up";
            else                        direction = "down";

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
        //inventory.addItem(new LesserSoftening(), 2);

        inventory.addItem(new AnkhStaff(5));
        inventory.addItem(new Swiftwind());
        inventory.addItem(new Unyielding(5));

        //I delete lang if tiwason ang duwa
//        inventory.addItem(new Arcanum());
//        inventory.addItem(new Slowstring(5));
//        inventory.addItem(new Mistwood(5));
//        inventory.addItem(new Stunblade(5));
//        inventory.addItem(new RazorEdge());
//        inventory.addItem(new ElementalCodex(5));
    }

    // KEEP YOUR LEVEL UP SYSTEM (with stat increases)
    public void levelUp(){
        level++;
        experience -= expNeeded;

        // Base stat increases (flat amounts)
        double hpIncrease = 220;
        double attackIncrease = 15;
        double defenseIncrease = 3;
        double speedIncrease = 3;

        // For unique class level up
        if (this instanceof Swordsman){
            hpIncrease = 400;
        } else if (this instanceof Ranger) {
            speedIncrease = 10;
        } else if (this instanceof Mage){
            attackIncrease = 20;
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

        updateMoveSet();
    }

    public void updateMoveSet(){
        for(Move move : moveset){
            move.canUnlock(this);
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
        if (canGainExp(chapter)) {
            this.experience += experience;

            // Calculate max level for this chapter
            int maxLevelForChapter;
            switch (chapter) {
                case 1: maxLevelForChapter = 4; break;
                case 2: maxLevelForChapter = 7; break;
                case 3: maxLevelForChapter = 10; break;
                default: maxLevelForChapter = 10; break;
            }

            // Only level up if we haven't reached the chapter cap
            while (this.experience >= this.expNeeded && level < maxLevelForChapter) {
                levelUp();
            }

            // If we're at the chapter cap, prevent overflow EXP
            if (level >= maxLevelForChapter) {
                int expNeededForNextLevel = this.expNeeded;
                if (this.experience > expNeededForNextLevel) {
                    // Cap the excess EXP to prevent overflow when chapter cap increases
                    this.experience = expNeededForNextLevel;
                }
            }
        } else {
            if (gp != null && gp.screenMessage != null) {
                gp.screenMessage.show("Cannot Gain EXP",
                        "You have reached the maximum level for this chapter!", 80, false);
            }
            System.out.println("Cannot gain EXP - Level " + level + " is max for chapter " + chapter);
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
        // Call onUnequip for the current weapon before removing it
        if (this.weapon != null) {
            this.weapon.onUnequip(this);
        }

        this.weapon = weapon;

        // Recalculate attack (original attack + weapon bonus)
        if (weapon != null) {
            this.attack = this.maxAttack + weapon.getAttack();
            // Call onEquip for the new weapon
            weapon.onEquip(this);
        } else {
            this.attack = this.maxAttack;
        }
    }

    public double getWeaponAttackBonus() {
        return (weapon != null) ? weapon.getAttack() : 0;
    }

    // Money methods for shop system
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    // Getters and Setters
    public int getExperience(){
        return experience;
    }

    public int getExpNeeded(){
        return expNeeded;
    }

    public ArrayList<Move> getMoves(){
        return moves;
    }

    public ArrayList<Move> getMoveset(){
        return moveset;
    }

    public Weapon getWeapon(){
        return weapon;
    }

    public Inventory getInventory(){
        return inventory;
    }
}