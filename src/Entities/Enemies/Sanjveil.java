package Entities.Enemies;

import Moves.Enemy3.*;
import Moves.Move;

import java.util.Random;

// --- ENEMY 3 ---------------------------------------------------------------------------------------------------------
public class Sanjveil extends Enemy{
    public Sanjveil(){
        name = "Sanjveil";
        level = 7;
        hp = 1600;
        maxHp = hp;
        attack = 180;
        maxAttack = attack;
        defense = 22;
        maxDefense = defense;
        speed = 26;
        dmgResistance = 0.10;
        loadMoves();
        loadSprite();
    }

    @Override
    public void loadMoves(){
        moveset.add(new Move1());
        moveset.add(new Move2());
    }

    //Move selection logic
    @Override
    public Move selectMove() {
        Random random = new Random();
        double randomValue = random.nextDouble() * 100; // 0-100

        // 60% chance for Move1, 40% chance for Move2
        if (randomValue < 60) {
            return moveset.get(0); // Move1
        } else {
            return moveset.get(1); // Move2
        }
    }
}
