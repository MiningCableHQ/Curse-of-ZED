package Entities.Enemies;

import Moves.FinalBoss.*;

public class FinalBoss extends Boss{
    public FinalBoss(){
        hp = 3000;
        maxHp = hp;
        attack = 320;
        maxAttack = attack;
        defense = 30;
        maxDefense = defense;
        speed = 45;
        dmgResistance = 0.25;
        loadMoves();
    }

    @Override
    public void loadMoves(){
        moveset.add(new Move1());
        moveset.add(new Move2());
        moveset.add(new Move3());
    }
}
