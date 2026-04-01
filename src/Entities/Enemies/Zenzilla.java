package Entities.Enemies;

import Moves.Enemy2.*;
import Moves.Move;
import java.util.*;

// --- ENEMY 2 ---------------------------------------------------------------------------------------------------------
public class Zenzilla extends Enemy{
    public Zenzilla(){
        name = "Zenzilla";
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

    //Move selection logic
    @Override
    public Move selectMove() {
        Random random = new Random();
        double randomValue = random.nextDouble() * 100; // 0-100

        // 80% chance for Move1, 20% chance for Move2
        if (randomValue < 80) {
            return moveset.get(0); // Move1
        } else {
            return moveset.get(1); // Move2
        }
    }
}
