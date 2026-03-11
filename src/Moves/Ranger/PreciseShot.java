package Moves.Ranger;

import Moves.Move;

public class PreciseShot extends Move {
    public PreciseShot() {
        super("Precise Shot", 20);
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity){
        //TODO Deal 150% of ATK to a single target
    }
}
