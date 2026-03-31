package Entities.Enemies;

import Moves.Enemy4.*;

public class Enemy4 extends Enemy{
    public Enemy4(){
        hp = 600;
        maxHp = hp;
        attack = 180;
        maxAttack = attack;
        defense = 15;
        maxDefense = defense;
        speed = 32;
        dmgResistance = 0.12;
        loadMoves();
    }

    @Override
    public void loadMoves(){
        moveset.add(new Move1());
        moveset.add(new Move2());
    }
}
