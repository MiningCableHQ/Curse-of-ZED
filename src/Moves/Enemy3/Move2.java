package Moves.Enemy3;

import Entities.Enemies.Enemy3;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("Move 2", 10);
    }

    @Override
    public <T> void execute(T Entity){
        //TODO deals 20% of atk as dmg, inflicts burn
    }
}
