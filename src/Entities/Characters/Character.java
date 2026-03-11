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

    public void levelUp(){
        level++;
        experience -= expNeeded;

        if(level <= 4){
            expNeeded = 100;
        }else if(level <= 7){
            expNeeded = 125;
        }else{
            expNeeded = 150;
        }
    }

    public boolean canGainExp(int chapter){
        return switch (chapter) {
            case 1 -> level < 4;
            case 2 -> level < 7;
            case 3 -> level < 10;
            default -> true;
        };
    }

    public void gainExp(int experience, int chapter) {
        if(canGainExp(chapter)){
            this.experience += experience;
            while (this.experience >= this.expNeeded && level < 10) {//level cap
                levelUp();
            }
        }else{
            //TODO Inform the player that he cannot gain exp
        }
    }
}
