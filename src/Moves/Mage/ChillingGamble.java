package Moves.Mage;

import Moves.Move;

public class ChillingGamble extends Move {
    public ChillingGamble() {
        super("Chilling Gamble", 0); //TODO Implement attack stat
        hasUnlocked = false;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Deals 300% of ATK as dmg to a single target, but gains Frozen status
    }
}
