package Entities.Enemies;

import Moves.Enemy5.*;
import Moves.Move;

import java.util.Random;

// --- ENEMY 5 ---------------------------------------------------------------------------------------------------------
public class Reyven extends Enemy{
    public Reyven(){
        name = "Reyven";
        level = 10;
        hp = 2000;
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

    @Override
    public Move selectMove() {
        Random random = new Random();
        double randomValue = random.nextDouble() * 100; // 0-100

        // 70% chance for Move1, 30% chance for Move2
        if (randomValue < 70) {
            return moveset.get(0); // Move1
        } else {
            return moveset.get(1); // Move2
        }
    }
}
