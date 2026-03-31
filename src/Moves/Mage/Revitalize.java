package Moves.Mage;

import Moves.Move;

public class Revitalize extends Move {
    public Revitalize() {
        super("Revitalize", 0, TargetType.SELF);
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Heals the character by 30-50%
    }
}