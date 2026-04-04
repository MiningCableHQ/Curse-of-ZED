package Moves.Swordsman;

import Moves.Move;

public class CounterStance extends Move {
    public CounterStance() {
        super("Counter Stance", 20);
        hasUnlocked = true;
        description = "Reduces incoming dmg by 40% and counters by 80% of ATK to a single target";
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Reduces incoming dmg by 40% and counters by 80% of ATK to a single target
    }
}
