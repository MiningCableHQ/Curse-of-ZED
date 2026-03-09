package Moves.Swordsman;

import Moves.Move;

public class PurifyingStance extends Move {
    public PurifyingStance() {
        super("Purifying Stance", 0); //TODO Implement attack stat
        hasUnlocked = false;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Cures a status effect and gains status immunity for 2 turns. Cannot be used for 5 turns
    }
}
