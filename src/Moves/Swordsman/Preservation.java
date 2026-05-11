package Moves.Swordsman;

import Entities.Characters.Swordsman;
import Moves.Move;
import java.util.*;

public class Preservation extends Move {
    Random rand = new Random();

    public Preservation() {
        super("Preservation", 0, TargetType.SELF, 7);
        hasUnlocked = false;
        description = "Heals the character by 300-800 HP";
    }

    public Preservation(boolean hasUnlocked) {
        super("Preservation", 0, TargetType.SELF, 7);
        hasUnlocked = this.hasUnlocked;
        description = "Heals the character by 300-800 HP";
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof Swordsman) {
            Swordsman swordsman = (Swordsman) Entity;

            double beforeHp = swordsman.getHp();

            // Random heal amount between 300 and 800
            double healAmount = 300 + rand.nextInt(501);
            swordsman.heal(healAmount);

            // Calculate actual healed amount (capped by max HP)
            double afterHp = swordsman.getHp();
            double actualHeal = afterHp - beforeHp;

            setHealAmount(actualHeal);
            if (actualHeal > 1) {
                setMessage(swordsman.getName() + " used " + this.name + " and healed " +
                        String.format("%d", (int)actualHeal) + " HP!");
            } else {
                setMessage(swordsman.getName() + " used " + this.name + " but was already at full health!");
            }
        }
    }
}