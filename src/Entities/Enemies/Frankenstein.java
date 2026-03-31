package Entities.Enemies;

import Moves.Frankenstein.*;

public class Frankenstein extends Boss{
    public Frankenstein(){
        hp = 1500;
        maxHp = hp;
        attack = 100;
        maxAttack = attack;
        defense = 30;
        maxDefense = defense;
        speed = 60;
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
