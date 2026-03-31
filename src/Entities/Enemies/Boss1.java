package Entities.Enemies;

import Moves.Boss1.*;

public class Boss1 extends Boss{
    public Boss1(){
        hp = 800;
        maxHp = hp;
        attack = 120;
        maxAttack = attack;
        defense = 5;
        maxDefense = defense;
        speed = 28;
        dmgResistance = 0.10;
        loadMoves();
    }

    @Override
    public void loadMoves(){
        moveset.add(new Move1());
        moveset.add(new Move2());
        moveset.add(new Move3());
    }
}
