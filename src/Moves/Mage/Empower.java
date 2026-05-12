package Moves.Mage;

import Entities.Characters.Mage;
import Moves.Move;

public class Empower extends Move {
    public Empower() {
        super("Empower", 0, TargetType.SELF);
        hasUnlocked = true;
        description = "Increase ATK by 30 (max 3 stacks)";
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof Mage) {
            Mage mage = (Mage) Entity;

            if (mage.canUseEmpower()) {
                double beforeAttack = mage.getAttack();
                mage.addEmpowerStack();

                // Calculate actual attack increase
                double afterAttack = mage.getAttack();
                double attackIncreased = afterAttack - beforeAttack;
                int stacks = mage.getEmpowerStacks();

                // Set message for battle display
                setBuffAmount(attackIncreased, "ATK");
                setMessage(mage.getName() + " used " + this.name + "! Attack increased to " +
                        (int)afterAttack + " (Stack " + stacks + "/3)");
            } else {
                setMessage(mage.getName() + " used " + this.name + " but is already at max stacks (3)!");
            }
        }
    }
}