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

            double maxHp = mage.getMaxHp();

            double healPercentage = 0.30 + (Math.random() * 0.20); //Random between 0.30 and 0.50
            double healAmount = maxHp * healPercentage;

            mage.heal(healAmount);
        }
    }
}