package Moves.Swordsman;

import Entities.Characters.Swordsman;
import Moves.Move;

public class IronStance extends Move {
    public IronStance(){
        super("Iron Stance", 0, TargetType.SELF);
        hasUnlocked = true;
        description = "Increases DEF by 10% (max 3 stacks)";
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof Swordsman) {
            Swordsman swordsman = (Swordsman) Entity;

            if (swordsman.canUseIronStance()) {
                // Store defense before buff
                double beforeDefense = swordsman.getDefense();

                swordsman.addIronStanceStack();

                // Calculate actual defense increase
                double afterDefense = swordsman.getDefense();
                double defenseIncreased = afterDefense - beforeDefense;
                int stacks = swordsman.getIronStanceStacks();

                setBuffAmount(defenseIncreased, "DEF");
                setMessage(swordsman.getName() + " used " + this.name + "! Defense increased to " +
                        String.format("%d", (int)afterDefense) + " (Stack " + stacks + "/3)");
            } else {
                setMessage(swordsman.getName() + " used " + this.name + " but is already at max stacks (3)!");
            }
        }
    }
}