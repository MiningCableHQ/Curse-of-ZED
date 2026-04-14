package Items.Consumables.Debuff.Clumsiness;

import Entities.Entity;
import Entities.Enemies.Enemy;
import Items.Consumables.Consumable;

public class GreaterClumsiness extends Consumable {
    protected double speedDebuffMultiplier;

    public GreaterClumsiness() {
        super("Greater Clumsiness Potion", "Reduces enemy SPD by 15%");
        speedDebuffMultiplier = 0.85;
        useMessage = "Used Greater Clumsiness Potion!";

        loadImage("/items/debuff_potions/greater_clumsiness_potion.png");
    }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Enemy) {
            Enemy target = (Enemy) Entity;
            double beforeSpeed = target.getSpeed();

            target.debuffSpeed(speedDebuffMultiplier);
            double afterSpeed = target.getSpeed();
            double reductionPercent = (1 - speedDebuffMultiplier) * 100;

            useMessage = "Used Greater Clumsiness Potion on " + target.getName() + "! SPD reduced by " +
                    String.format("%.0f", reductionPercent) + "% (" +
                    String.format("%.1f", beforeSpeed - afterSpeed) + " points).";
        }
    }
}

// ---------------------------------------------------------------------------------------------------------------------
// --- TODO: To be removed
// ---------------------------------------------------------------------------------------------------------------------