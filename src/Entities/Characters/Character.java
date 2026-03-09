package Entities.Characters;

import Entities.Entity;
import Items.Inventory;
import Moves.Move;

import java.util.ArrayList;

public abstract class Character extends Entity {
    protected int level;
    protected int experience;
    protected int expNeeded;
    protected Inventory inventory;
    protected ArrayList<Move> moves;
    protected ArrayList<Move> moveset;
    //TODO declare moveset

    public Character(){
        level = 1;
        experience = 0;
        expNeeded = 100;
        accuracy = 0.95;
        inventory = new Inventory();
    }

    public abstract void loadMoves();

    public void gainExp(int experience){
        this.experience += experience;
        if(this.experience >= this.expNeeded){
            levelUp();
        }
    }
    public void levelUp(){
        //TODO implement max level threshold per chapter/map

        level++;

        experience -= expNeeded;

        if(level <= 4){
            expNeeded = 100;
        }else if(level <= 7){
            expNeeded = 120;
        }else{
            expNeeded = 150;
        }
    }
}