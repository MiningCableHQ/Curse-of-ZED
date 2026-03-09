package Moves.Swordsman;

import Moves.Move;

public class SacrificialBlade extends Move {
    public SacrificialBlade() {
        super("Sacrificial Blade", 0); //TODO Implement attack stat
        hasUnlocked = false;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Deal 500% of ATK as dmg to a single target, uses up 50% of maxHP
    }
}
