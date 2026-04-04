package Moves.Ranger;

import Moves.Move;

public class ShadowStep extends Move {
    public ShadowStep(){
        super("Shadow Step", 0, TargetType.SELF);
        hasUnlocked = false;
        description = "Guaranteed to evade the enemy’s next attack";
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Guaranteed to evade the enemy’s next attack
    }
}
