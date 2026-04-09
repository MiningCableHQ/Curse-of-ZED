package Entities.Enemies;

import Moves.Enemy1.*;
import Moves.Move;
import java.util.*;

// --- ENEMY 1 ---------------------------------------------------------------------------------------------------------
public class Masklet extends Enemy{
    public Masklet(){
        name = "Masklet";
        level = 4;
        hp = 1200;
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
