package Moves.FinalBoss;

import Entities.Enemies.FinalBoss;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("Move 2", 0);
    }

    @Override
    public <T> void execute(T Entity){
        //TODO increase DEF by 100 for 2 turns, cannot be used for 4 turns
    }
}
