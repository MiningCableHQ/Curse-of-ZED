package Entities;

import java.awt.image.BufferedImage;

public abstract class Entity {
    public int x, y;
    public int entitySpeed;
    public int normalSpeed;
    public int sprintSpeed;

    public BufferedImage up1, up2, down1, down2, left1,  left2, right1, right2, right3, right4;
    public String direction;
    public boolean isMoving;

    public int spriteCounter = 0;
    public int spriteNum = 1;

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
