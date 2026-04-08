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
    protected double accuracy;

    // Original stats for buff/debuff reset
    protected double originalAttack;
    protected double originalDefense;
    protected double originalSpeed;
    protected double originalAccuracy;

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

    // Stat management methods
    public void resetSpeed() {
        this.speed = originalSpeed;
    }

    public void resetAttack() {
        this.attack = originalAttack;
    }

    public void resetDefense() {
        this.defense = originalDefense;
    }

    public void resetAccuracy() {
        this.accuracy = originalAccuracy;
    }

    public void resetAllStats() {
        resetAttack();
        resetDefense();
        resetSpeed();
        resetAccuracy();
    }

    // Stat buff/debuff methods
    public void buffAttack(double amount) {
        double newAttack = this.attack + amount;
        this.attack = Math.min(newAttack, maxAttack * 2);
    }

    public void debuffAttack(double multiplier) {
        double newAttack = this.attack * multiplier;
        this.attack = Math.max(newAttack, maxAttack * 0.5); // Can't go below 50% of max
    }

    public void buffDefense(double amount) {
        double newDefense = this.defense + amount;
        this.defense = Math.min(newDefense, 500);
    }

    public void debuffDefense(double multiplier) {
        double newDefense = this.defense * multiplier;
        this.defense = Math.max(newDefense, 0); // Can't go below 30% of max
    }

    public void buffSpeed(double amount) {
        double newSpeed = this.speed + amount;
        this.speed = Math.min(newSpeed, 200); // Cap at 200 speed
    }

    public void debuffSpeed(double multiplier) {
        double newSpeed = this.speed * multiplier;
        this.speed = Math.max(newSpeed, 5); // Minimum speed of 5
    }

    public void debuffAccuracy(double multiplier) {
        double newAccuracy = this.accuracy * multiplier;
        this.accuracy = Math.max(newAccuracy, 0.5); // Can't go below 50% accuracy
    }

    // Setters
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
    public double getOriginalAttack() {
        return originalAttack;
    }
    public double getOriginalDefense() {
        return originalDefense;
    }
    public double getOriginalSpeed() {
        return originalSpeed;
    }
    public double getOriginalAccuracy() {
        return originalAccuracy;
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
    public void setDefense(double defense) {
        this.defense = Math.min(defense, 500);
    }
    public void setAccuracy(double accuracy) {
        this.accuracy = Math.max(0.5, Math.min(accuracy, 1.0));
    }

    //Unique entity methods
    public void heal(double amount) {
        double newHp = this.hp + amount;
        this.hp = Math.min(newHp, maxHp);
    }

    public void sacrifice(double amount) { //For entities who consume their hp
        double newHp = this.hp - amount;
        this.hp = Math.max(0, newHp);
    }
}