package Entities.Enemies;

import Moves.Frankenstein.*;
import Moves.Move;

import java.util.Random;

public class Frankenstein extends Boss{
    public Frankenstein(){
        name = "Frankenstein";
        level = 67;
        hp = 2000;
        maxHp = hp;
        attack = 100;
        maxAttack = attack;
        defense = 30;
        maxDefense = defense;
        speed = 60;
        dmgResistance = 0.10;
        expYield = 250;
        loadMoves();
        loadSprite();
    }

    @Override
    public void loadMoves(){
        moveset.add(new Move1());
        moveset.add(new Move2());
        moveset.add(new Move3());
    }

    @Override
    public Move selectMove() {
        Random random = new Random();
        double randomValue = random.nextDouble() * 100; // 0-100

        // 60% chance for Move1, 30% chance for Move2, 10% for ult
        if (randomValue < 60) {
            return moveset.get(0); // Move1
        } else if(randomValue < 90) {
            return moveset.get(1); // Move2
        } else {
            return moveset.get(2); // Move3 (Ult)
        }
    }
}
