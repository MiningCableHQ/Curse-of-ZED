package Entities.Characters;

import Main.*;
import Moves.Mage.*;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Mage extends Player {
    public Mage(GamePanel gp, KeyHandler keyH){
        super(gp, keyH);
        hp = 650;
        maxHp = hp;
        attack = 335;
        maxAttack = attack;
        defense = 10;
        maxDefense = defense;
        speed = 35;
        dmgResistance = 0.13;
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
}
