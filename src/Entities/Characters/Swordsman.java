package Entities.Characters;

import Entities.Entity;
import Moves.Swordsman.*;

public class Swordsman extends Character {
    public Swordsman(){
        hp = 800;
        maxHp = hp;
        attack = 205;
        maxAttack = attack;
        defense = 65;
        maxDefense = defense;
        speed = 28;
        dmgResistance = 0.15;
        loadMoves();
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
}
