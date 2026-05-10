package Entities.Enemies;

import Moves.Enemy4.*;
import Moves.Move;

import java.util.Random;

// --- ENEMY 4 ---------------------------------------------------------------------------------------------------------
public class Razormaw extends Enemy{
    public Razormaw(){
        name = "Razormaw";
        level = 7;
        hp = 1400;
        maxHp = hp;
        attack = 180;
        maxAttack = attack;
        defense = 15;
        maxDefense = defense;
        speed = 32;
        dmgResistance = 0.12;
        expYield = 150;
        loadMoves();
        loadSprite();
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
