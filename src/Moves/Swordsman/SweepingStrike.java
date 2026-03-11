package Moves.Swordsman;

import Moves.Move;

public class SweepingStrike extends Move {
    public SweepingStrike(){
        super("Sweeping Strike", 20);
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Deal 60% of ATK to all enemies
    }
}
