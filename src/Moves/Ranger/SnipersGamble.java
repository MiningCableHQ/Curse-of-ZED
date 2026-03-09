package Moves.Ranger;

import Moves.Move;

public class SnipersGamble extends Move {
    public SnipersGamble() {
        super("Sniper's Gamble", 0); //TODO Implement attack stat
        hasUnlocked = false;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Deals 250% of ATK as dmg to a single target, but accuracy is lowered by 25% for this move
    }
}
