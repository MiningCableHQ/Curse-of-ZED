package Moves.Ranger;

import Moves.Move;

public class FlurryShot extends Move {
    public FlurryShot() {
        super("Flurry Shot", 0); //TODO Implement attack stat
        hasUnlocked = false;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Deals 45% of ATK as dmg 2-5x to a single target
    }
}
