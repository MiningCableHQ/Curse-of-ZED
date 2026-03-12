package Entities;

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

    //Entity Stats
    protected String name;
    protected double hp;
    protected double maxHp;
    protected int attack;
    protected int maxAttack;
    protected int defense;
    protected int maxDefense;
    protected double dmgResistance;
    protected int speed;
    protected double accuracy;

    //Methods
    public void takeDamage(int damage){
        hp -= damage;
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
    public int getAttack(){
        return attack;
    }
    public int getMaxAttack(){
        return maxAttack;
    }
    public int getDefense(){
        return defense;
    }
    public int getMaxDefense(){
        return maxDefense;
    }
    public double getDmgResistance(){
        return dmgResistance;
    }
    public int getSpeed(){
        return speed;
    }
    public double getAccuracy(){
        return accuracy;
    }
}
