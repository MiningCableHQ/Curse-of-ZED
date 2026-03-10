package Entities.Characters;

import Main.*;
import Moves.Ranger.*;

public class Ranger extends Player {
    public Ranger(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);
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
