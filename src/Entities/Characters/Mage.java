package Entities.Characters;

import Main.*;
import Moves.Mage.*;

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
