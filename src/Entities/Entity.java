package Entities;

import java.awt.Rectangle;
import Moves.Move;
import java.util.*;

import java.awt.image.BufferedImage;

public abstract class Entity {
    public int worldX, worldY;
    public int entitySpeed;
    public int normalSpeed;
    public int sprintSpeed;

    public BufferedImage left1,  left2, left3, left4, right1, right2, right3, right4;
    public String direction;
    public boolean isMoving;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public Move weapon;

    //Entity Stats
    protected String name;
    protected int level;
    protected double hp;
    protected double maxHp;
    protected double attack;
    protected double maxAttack;
    protected double defense;
    protected double maxDefense;
    protected double dmgResistance;
    protected double speed;
    protected double originalSpeed;  // Store original speed for reset
    protected double accuracy;

    Random rand = new Random();

    public Entity(){
        level = 1;
        name = "Entity_Name";
    }

    //Methods
    public double takeDamage(double damage, double defense, double dmgResistance) {
        double actualDamage = damage * (1 - dmgResistance) - defense;
        if (actualDamage < 1) actualDamage = rand.nextDouble(1,11);

        hp -= actualDamage;

        return actualDamage;
    }

    // Speed management methods
    public void resetSpeed() {
        this.speed = originalSpeed;
    }

    public void setOriginalSpeed(double newSpeed) {
        this.originalSpeed = newSpeed;
    }

    //Getters
    public String getName(){
        return name;
    }
    public int getLevel(){
        return level;
    }
    public double getHp(){
        return hp;
    }
    public double getMaxHp(){
        return maxHp;
    }
    public double getAttack(){
        return attack;
    }
    public double getMaxAttack(){
        return maxAttack;
    }
    public double getDefense(){
        return defense;
    }
    public double getMaxDefense(){
        return maxDefense;
    }
    public double getDmgResistance(){
        return dmgResistance;
    }
    public double getSpeed(){
        return speed;
    }
    public double getAccuracy(){
        return accuracy;
    }

    //Setters
    public void setHp(double hp) {
        this.hp = Math.max(0, Math.min(hp, maxHp));
    }
    public void setAttack(double attack) {
        this.attack = Math.min(attack, maxAttack * 2);
    }
    public void setSpeed(double speed) {
        this.speed = Math.max(0, speed);
    }

    //Unique entity methods
    public void heal(double amount) {
        double newHp = this.hp + amount;
        this.hp = Math.min(newHp, maxHp);
    }
    public void buffAttack(double amount) {
        double newAttack = this.attack + amount;
        this.attack = Math.min(newAttack, maxAttack * 2);
    }
    public void sacrifice(double amount) { //For entities who consume their hp
        double newHp = this.hp - amount;
        this.hp = Math.max(0, newHp);
    }
}