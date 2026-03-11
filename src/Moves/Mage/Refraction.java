package Moves.Mage;

import Moves.Move;

public class Refraction extends Move {
    public Refraction() {
        super("Refraction", 0);
        hasUnlocked = false;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO The next single-target move will deal damage to all enemies. Cannot be used for 6 turns
    }
}
