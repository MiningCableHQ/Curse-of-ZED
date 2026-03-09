package Moves.Ranger;

import Moves.Move;

public class SnipersMark extends Move {
    public SnipersMark() {
        super("Sniper's Mark", 0); //TODO Implement attack stat
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity){
        //TODO Increases the multiplier of the next move by 2.2x of its original value
    }
}
