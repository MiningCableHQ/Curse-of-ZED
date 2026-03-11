package Moves.Mage;

import Moves.Move;

public class Empower extends Move {
    public Empower() {
        super("Empower", 0);
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Increase ATK by 6% (max of 3 stacks)
    }
}
