package Entities.Enemies;

import Moves.Enemy3.*;

public class Enemy3 extends Enemy{
    public Enemy3(){
        hp = 800;
        maxHp = hp;
        attack = 180;
        maxAttack = attack;
        defense = 22;
        maxDefense = defense;
        speed = 26;
        dmgResistance = 0.10;
        loadMoves();
    }

    @Override
    public void loadMoves(){
        moveset.add(new Move1());
        moveset.add(new Move2());
    }
}
