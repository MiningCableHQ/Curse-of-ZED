package Moves.Ranger;

import Entities.Characters.Ranger;
import Moves.Move;

public class Harmony extends Move {
    public Harmony() {
        super("Harmony", 0, TargetType.SELF);
        hasUnlocked = true;
        description = "Increase DEF and ATK by 15 (max 3 stacks)";
    }

    @Override
    public <T> void execute(T Entity){
        if (Entity instanceof Ranger) {
            Ranger ranger = (Ranger) Entity;

            if (ranger.canUseHarmony()) {
                double beforeAttack = ranger.getAttack();
                double beforeDefense = ranger.getDefense();

                ranger.addHarmonyStack();

                // Calculate actual stat increases
                double afterAttack = ranger.getAttack();
                double afterDefense = ranger.getDefense();
                double attackIncreased = afterAttack - beforeAttack;
                double defenseIncreased = afterDefense - beforeDefense;
                int stacks = ranger.getHarmonyStacks();

                // Set message for battle display
                setBuffAmount(attackIncreased, "ATK/DEF");
                setMessage(ranger.getName() + " used " + this.name + "! " +
                        "ATK and DEF increased by " + (int)attackIncreased +
                        " (Stack " + stacks + "/3)");
            } else {
                setMessage(ranger.getName() + " used " + this.name + " but is already at max stacks (3)!");
            }
        }
    }
}