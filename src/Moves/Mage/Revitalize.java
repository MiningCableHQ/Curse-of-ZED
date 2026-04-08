package Moves.Mage;

import Entities.Characters.Mage;
import Moves.Move;

public class Revitalize extends Move {
    public Revitalize() {
        super("Revitalize", 0, TargetType.SELF);
        hasUnlocked = true;
        description = "Heals the character by 30-50% of MaxHP";
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof Mage) {
            Mage mage = (Mage) Entity;

            double beforeHp = mage.getHp();
            double maxHp = mage.getMaxHp();

            // Random between 30% and 50%
            double healPercentage = 0.30 + (Math.random() * 0.20);
            double healAmount = maxHp * healPercentage;

            mage.heal(healAmount);

            // Calculate actual healed amount (capped by max HP)
            double afterHp = mage.getHp();
            double actualHeal = afterHp - beforeHp;

            setHealAmount(actualHeal);
            if (actualHeal > 1) {
                setMessage(mage.getName() + " used " + this.name + " and healed " +
                        (int)actualHeal + " HP (" + (int)(healPercentage * 100) + "% of max HP)!");
            } else {
                setMessage(mage.getName() + " used " + this.name + " but was already at full health!");
            }
        }
    }
}