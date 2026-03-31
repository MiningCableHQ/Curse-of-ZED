package Entities.Characters;

import Main.*;
import Moves.Swordsman.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Swordsman extends Player {
    private int ironStanceStacks = 0;
    private double originalDefense;

    public Swordsman(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);
        name = "Swordsman";
        hp = 800;
        maxHp = hp;
        attack = 205;
        maxAttack = attack;
        defense = 65;
        maxDefense = defense;
        speed = 28;
        dmgResistance = 0.15;
        loadMoves();

        originalDefense = defense;
    }

    @Override
    public void getPlayerImage(){
        try{
            left1 = ImageIO.read(getClass().getResourceAsStream("/swordsman/walking/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/swordsman/walking/left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/swordsman/walking/left3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/swordsman/walking/left4.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/swordsman/walking/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/swordsman/walking/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/swordsman/walking/right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/swordsman/walking/right4.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadMoves(){
        moveset.add(new CounterStance());
        moveset.add(new GuidedStrike());
        moveset.add(new HeroicSlash());
        moveset.add(new IronStance());
        moveset.add(new PurifyingStance());
        moveset.add(new SacrificialBlade());
        moveset.add(new SweepingStrike());

        moves.add(new HeroicSlash());
        moves.add(new SweepingStrike());
        moves.add(new IronStance());
        moves.add(new CounterStance());
    }

    // --- For move: Iron stance ---------------------------------------------------------------------------------------
    public int getIronStanceStacks() {
        return ironStanceStacks;
    }
    public boolean canUseIronStance() {
        return ironStanceStacks < 3;
    }
    public void addIronStanceStack() {
        if (ironStanceStacks < 3) {
            ironStanceStacks++;
            //Increase DEF by 8%
            defense = originalDefense + (originalDefense * 0.08 * ironStanceStacks);
        }
    }
    public void resetBattleBuffs() {
        ironStanceStacks = 0;
        defense = originalDefense;
    }
    public void setInBattle(boolean inBattle) {
        if (!inBattle) {
            resetBattleBuffs();
        }
    }
    @Override
    public double getDefense() {
        return defense;
    }
}
