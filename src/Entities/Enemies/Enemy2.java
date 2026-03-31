package Entities.Enemies;

import Moves.Enemy2.*;

public class Enemy2 extends Enemy{
    public Enemy2(){
        hp = 200;
        maxHp = hp;
        attack = 150;
        maxAttack = attack;
        defense = 20;
        maxDefense = defense;
        speed = 25;
        dmgResistance = 0.07;
        loadMoves();
    }

    @Override
    public void loadMoves(){
        moveset.add(new Move1());
        moveset.add(new Move2());
    }
}
