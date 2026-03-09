package Moves.Mage;

import Moves.Move;

public class LifeLeech extends Move {
    public LifeLeech() {
        super("Life Leech", 0); //TODO Implement attack stat
        hasUnlocked = false;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Deals 110% of ATK as damage to a single target, and heals up to 10% of damage dealt
    }
}
