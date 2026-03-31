package Entities.Enemies;

import Moves.Enemy5.*;

public class Enemy5 extends Enemy{
    public Enemy5(){
        hp = 900;
        maxHp = hp;
        attack = 250;
        maxAttack = attack;
        defense = 25;
        maxDefense = defense;
        speed = 40;
        dmgResistance = 0.05;
        loadMoves();
    }

    @Override
    public void loadMoves(){
        moveset.add(new Move1());
        moveset.add(new Move2());
    }
}
