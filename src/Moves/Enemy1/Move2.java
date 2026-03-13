package Moves.Enemy1;

import Entities.Enemies.Enemy1;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("Move 2", 0);
    }

    @Override
    public <T> void execute(T Entity){
        //TODO heals 70 hp
    }
}
