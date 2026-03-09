package Entities.Characters;

import Entities.Entity;
import Moves.Ranger.*;
import Moves.Swordsman.*;

public class Ranger extends Character {
    public Ranger() {
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
