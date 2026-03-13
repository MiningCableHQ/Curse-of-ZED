package Moves.Enemy2;

import Entities.Enemies.Enemy2;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("Move 2", 0);
    }

    @Override
    public <T> void execute(T Entity){
        //TODO increase atk by 10, cannot be used for 2 turns
    }
}
