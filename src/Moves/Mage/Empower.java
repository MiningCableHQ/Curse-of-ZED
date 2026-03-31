package Moves.Mage;

import Entities.Characters.Mage;
import Moves.Move;

public class Empower extends Move {
    public Empower() {
        super("Empower", 0, TargetType.SELF);
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof Mage) {
            Mage mage = (Mage) Entity;

            if (mage.canUseEmpower()) {
                mage.addEmpowerStack();
                //Increase ATK by 6% per stack
            } else {
                //TODO inform player "Empower cannot be used! Already at max stacks (3)!"
            }
        }
    }
}
