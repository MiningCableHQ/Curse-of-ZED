package Entities.Characters;

import Main.*;
import Moves.Ranger.*;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Ranger extends Player {
    private int windstepStacks = 0;
    private double originalSpeed;

    public Ranger(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);
        name = "Ranger";
        hp = 680;
        maxHp = hp;
        attack = 240;
        maxAttack = attack;
        defense = 20;
        maxDefense = defense;
        speed = 50;
        dmgResistance = 0.10;
        loadMoves();

        originalSpeed = speed;
    }

    @Override
    public void getPlayerImage(){
        try{
            left1 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_left3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_left4.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_right4.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadMoves(){
        moveset.add(new PreciseShot());
        moveset.add(new Scattershot());
        moveset.add(new Windstep());
        moveset.add(new SnipersMark());
        moveset.add(new FlurryShot());
        moveset.add(new ShadowStep());
        moveset.add(new SnipersGamble());

        moves.add(new PreciseShot());
        moves.add(new Scattershot());
        moves.add(new Windstep());
        moves.add(new SnipersMark());
    }

    // --- For move: Windstep ------------------------------------------------------------------------------------------
    public int getWindstepStacks() {
        return windstepStacks;
    }
    public boolean canUseWindstep() {
        return windstepStacks < 3;
    }
    public void addWindstepStack() {
        if (windstepStacks < 3) {
            windstepStacks++;
            // Increase SPD by 4%
            speed = originalSpeed + (originalSpeed * 0.04 * windstepStacks);
        }
    }
    public void resetBattleBuffs() {
        windstepStacks = 0;
        speed = originalSpeed;
    }
    public void setInBattle(boolean inBattle) {
        if (!inBattle) {
            resetBattleBuffs();
        }
    }
    @Override
    public double getSpeed() {
        return speed;
    }
}
