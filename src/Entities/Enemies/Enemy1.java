package Entities.Enemies;

import Moves.Enemy1.*;

public class Enemy1 extends Enemy{
    public Enemy1(){
        hp = 400;
        maxHp = hp;
        attack = 100;
        maxAttack = attack;
        defense = 10;
        maxDefense = defense;
        speed = 20;
        dmgResistance = 0.05;
        loadMoves();
    }

    @Override
    public void loadMoves(){
        moveset.add(new Move1());
        moveset.add(new Move2());
    }
}
