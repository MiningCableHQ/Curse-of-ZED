package Moves.Ranger;

import Entities.Characters.Ranger;
import Moves.Move;

public class Harmony extends Move {
    public Harmony() {
        super("Harmony", 0, TargetType.SELF);
        hasUnlocked = true;
        description = "Increase DEF and ATK by 12 (max 3 stacks)";
    }

    @Override
    public <T> void execute(T Entity){
        if (Entity instanceof Ranger) {
            Ranger ranger = (Ranger) Entity;

            if (ranger.canUseHarmony()) {
                //Add Harmony stack
                ranger.addHarmonyStack();
            } else {
                //TODO inform player "Harmony cannot be used! Already at max stacks (3)!"
            }
        }
    }
}
