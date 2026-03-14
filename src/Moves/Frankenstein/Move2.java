package Moves.Frankenstein;

import Entities.Enemies.Frankenstein;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("Move 2", 0);
    }

    @Override
    public <T> void execute(T Entity){
        //TODO increase SPD by 30 for 2 turns, cannot be used for 5 turns
    }
}
