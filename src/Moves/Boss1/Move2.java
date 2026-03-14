package Moves.Boss1;

import Entities.Enemies.Boss1;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("Move 2", 0);
    }

    @Override
    public <T> void execute(T Entity){
        //TODO heals 100 hp
    }
}
