package Moves.Ranger;

import Moves.Move;

public class Windstep extends Move {
    public Windstep() {
        super("Windstep", 0); //TODO Implement attack stat
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity){
        //TODO Increase SPD by 4% (max of 3 stacks)
    }
}
