package Entities.Enemies;

import Moves.FinalBoss.*;
import Moves.Move;

import java.util.Random;

// --- FINAL BOSS ------------------------------------------------------------------------------------------------------
public class ZED extends Boss{
    private int defBuffStacks = 0;
    private double originalDefense;
    private boolean isStrongVersion;

    // Constructor for weak version (first encounter)
    public ZED(){
        this(false);
    }

    public ZED(boolean isStrongVersion){
        this.isStrongVersion = isStrongVersion;

        name = "Zed";
        level = 999;

        if (isStrongVersion) {
            hp = 10000;
            maxHp = hp;
            attack = 300;
            maxAttack = attack;
            defense = 0;
            maxDefense = defense;
            speed = 45;
            dmgResistance = 0;
            expYield = 300;
            goldYield = 1000;
        } else {
            hp = 4000;
            maxHp = hp;
            attack = 320;
            maxAttack = attack;
            defense = 30;
            maxDefense = defense;
            speed = 45;
            dmgResistance = 0.25;
            expYield = 300;
            goldYield = 1000;
        }

        originalDefense = defense;

        loadMoves();
        loadSprite();
    }

    @Override
    public void loadMoves(){
        moveset.add(new Move1());
        moveset.add(new Move2());
        moveset.add(new Move3());
    }

    @Override
    public Move selectMove() {
        Random random = new Random();
        double randomValue = random.nextDouble() * 100; // 0-100

        // 40% chance for Move1, 35% chance for Move2, 25% for ult
        if (randomValue < 40) {
            return moveset.get(0); // Move1
        } else if(randomValue < 75) {
            return moveset.get(1); // Move2
        } else {
            return moveset.get(2); // Move3 (Ult)
        }
    }

    // Getters
    public int getDefBuffStacks() {
        return defBuffStacks;
    }

    public boolean isStrongVersion() {
        return isStrongVersion;
    }

    // Unique methods for final boss
    public void addDefBuff() {
        defBuffStacks++;
        // Increase defense by 8 per stack
        defense = originalDefense + (defBuffStacks * 8);
    }

    public void resetBattleBuffs() {
        defBuffStacks = 0;
        defense = originalDefense;
    }
}