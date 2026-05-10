package Entities.Enemies;

import Moves.Boss1.*;
import Moves.Move;

import java.util.Random;

// --- BOSS 1 ----------------------------------------------------------------------------------------------------------
public class Thorncrusher extends Boss{
    public Thorncrusher(){
        name = "Thorncrusher";
        level = 10;
        hp = 2000;
        maxHp = hp;
        attack = 120;
        maxAttack = attack;
        defense = 5;
        maxDefense = defense;
        speed = 28;
        dmgResistance = 0.10;
        expYield = 300;
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
