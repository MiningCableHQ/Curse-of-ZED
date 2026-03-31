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
    protected double hp;
    protected double maxHp;
    protected double attack;
    protected double maxAttack;
    protected double defense;
    protected double maxDefense;
    protected double dmgResistance;
    protected double speed;
    protected double accuracy;

    Random rand = new Random();

    //Methods
    public double takeDamage(double damage, int defense, double dmgResistance) {
        double actualDamage = damage * (1 - dmgResistance) - defense;
        if (actualDamage < 1) actualDamage = rand.nextDouble(1,11);

        hp -= actualDamage;

        return actualDamage;
    }

    //Getters and Setters
    public String getName(){
        return name;
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
}
