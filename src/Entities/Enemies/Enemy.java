package Entities.Enemies;

import Entities.Entity;
import Moves.Move;
import java.util.*;

import java.util.ArrayList;

public abstract class Enemy extends Entity {
    protected int expYield;
    protected ArrayList<Move> moveset;
    protected ArrayList<Move> moves;

    public Enemy(){
        accuracy = 0.90;
        moves =  new ArrayList<>();
        moveset = new ArrayList<>();
    }

    public Enemy(String name, double attack){
    }

    public abstract void loadMoves();

    //Move selection logic
    public abstract Move selectMove();

    //Getters and Setters
    public int getExpYield(){
        return  expYield;
    }

    public ArrayList<Move> getMoves(){
        return moves;
    }
}
