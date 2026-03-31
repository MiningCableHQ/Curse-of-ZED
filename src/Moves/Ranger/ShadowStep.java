package Moves.Ranger;

import Moves.Move;

public class ShadowStep extends Move {
    public ShadowStep(){
        super("Shadow Step", 0);
        hasUnlocked = false;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Guaranteed to evade the enemy’s next attack. Cannot be used for 3 turns
    }
}
