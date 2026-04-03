package Entities.Characters;

import Main.*;
import Moves.Mage.*;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Mage extends Player {
    private int empowerStacks = 0;
    private double originalAttack;

    public Mage(GamePanel gp, KeyHandler keyH){
        super(gp, keyH);
        name = "Mage";
        hp = 650;
        maxHp = hp;
        attack = 335;
        maxAttack = attack;
        defense = 10;
        maxDefense = defense;
        speed = 35;
        dmgResistance = 0.13;
        loadMoves();

        originalAttack = attack;
    }

    @Override
    public void getPlayerImage(){
        try{
            left1 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_leftt1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_leftt2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_leftt3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_leftt4.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_right4.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadMoves(){
        moveset.add(new ArcaneBolt());
        moveset.add(new ArcaneExplosion());
        moveset.add(new Empower());
        moveset.add(new Revitalize());
        moveset.add(new LifeLeech());
        moveset.add(new Refraction());
        moveset.add(new ChillingGamble());

        moves.add(new ArcaneBolt());
        moves.add(new ArcaneExplosion());
        moves.add(new Empower());
        moves.add(new Revitalize());
    }

    // --- For move: Empower -------------------------------------------------------------------------------------------
    public int getEmpowerStacks() {
        return empowerStacks;
    }
    public boolean canUseEmpower() {
        return empowerStacks < 3;
    }
    public void addEmpowerStack() {
        if (empowerStacks < 3) {
            empowerStacks++;
            // Increase attack by 6% per stack
            attack = originalAttack + (originalAttack * 0.06 * empowerStacks);
        }
    }
    public void resetBattleBuffs() {
        empowerStacks = 0;
        attack = originalAttack;
    }
    public void setInBattle(boolean inBattle) {
        if (!inBattle) {
            resetBattleBuffs();
        }
    }
    @Override
    public double getAttack() {
        return attack;
    }
}
