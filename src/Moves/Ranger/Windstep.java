package Moves.Ranger;

import Entities.Characters.Ranger;
import Moves.Move;

public class Windstep extends Move {
    public Windstep() {
        super("Windstep", 0, TargetType.SELF);
        hasUnlocked = true;
        description = "Increase SPD by 8% (max of 3 stacks)";
    }

    @Override
    public <T> void execute(T Entity){
        if (Entity instanceof Ranger) {
            Ranger ranger = (Ranger) Entity;

            if (ranger.canUseWindstep()) {
                ranger.addWindstepStack();
                //Increase SPD by 8% per stack
            } else {
                //TODO inform player "Windstep cannot be used! Already at max stacks (3)!"
            }
        }
    }
}
