package Moves.Swordsman;

import Entities.Characters.Swordsman;
import Moves.Move;

public class IronStance extends Move {
    public IronStance(){
        super("Iron Stance", 0, TargetType.SELF);
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof Swordsman) {
            Swordsman swordsman = (Swordsman) Entity;

            if (swordsman.canUseIronStance()) {
                swordsman.addIronStanceStack();
                //Increases DEF by 8% per stack
            } else {
                //TODO inform player "Iron Stance cannot be used! Already at max stacks (3)!"
            }
        }
    }
}