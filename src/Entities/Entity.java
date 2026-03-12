package Entities;

import java.awt.image.BufferedImage;

public abstract class Entity {
    public int worldX, worldY;
    public int entitySpeed;
    public int normalSpeed;
    public int sprintSpeed;
    public double remainderX = 0;
    public double remainderY = 0;

    public BufferedImage left1,  left2, left3, left4, right1, right2, right3, right4;
    public String direction;
    public boolean isMoving;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    //Entity Stats
    protected double hp;
    protected double maxHp;
    protected int attack;
    protected int maxAttack;
    protected int defense;
    protected int maxDefense;
    protected double dmgResistance;
    protected int speed;
    protected double accuracy;

}
