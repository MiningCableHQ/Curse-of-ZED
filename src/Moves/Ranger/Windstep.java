package Moves.Ranger;

import Entities.Characters.Ranger;
import Moves.Move;

public class Windstep extends Move {
    public Windstep() {
        super("Windstep", 0, TargetType.SELF);
        hasUnlocked = true;
        description = "";
    }

    @Override
    public <T> void execute(T Entity){
        if (Entity instanceof Ranger) {
            Ranger ranger = (Ranger) Entity;

            //TODO Revamp and delete ts
        }
    }
}
