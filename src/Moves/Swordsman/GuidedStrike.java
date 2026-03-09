package Moves.Swordsman;

import Moves.Move;

public class GuidedStrike extends Move {
    public GuidedStrike() {
        super("Guided Strike", 0); //TODO Implement attack stat
        hasUnlocked = false;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Next move is guaranteed to hit and increases the damage multiplier by an additional 50%
    }
}
