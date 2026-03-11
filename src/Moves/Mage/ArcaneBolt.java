package Moves.Mage;

import Moves.Move;

public class ArcaneBolt extends Move {
    public ArcaneBolt() {
        super("Arcane Bolt", 20);
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Deal 145% of ATK to a single target
    }
}
