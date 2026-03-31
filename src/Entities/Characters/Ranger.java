package Entities.Characters;

import Main.*;
import Moves.Ranger.*;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Ranger extends Player {
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
}
