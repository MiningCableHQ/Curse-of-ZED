package Items.Consumables.Debuff.Clumsiness;

import Entities.Entity;
import Entities.Enemies.Enemy;
import Items.Consumables.Consumable;
import Items.Item;

public class LesserClumsiness extends Consumable {
    protected double speedDebuffMultiplier;

    public LesserClumsiness() {
        super("Lesser Clumsiness Potion", "Reduces enemy SPD by 5%");
        speedDebuffMultiplier = 0.95;
        useMessage = "Used Lesser Clumsiness Potion!";

        loadImage("/items/debuff_potions/lesser_clumsiness_potion.png");
    }

    @Override
    public Item.TargetType getTargetType() { return Item.TargetType.ENEMY; }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Enemy) {
            Enemy target = (Enemy) Entity;
            double beforeSpeed = target.getSpeed();

            target.debuffSpeed(speedDebuffMultiplier);
            double afterSpeed = target.getSpeed();
            double reductionPercent = (1 - speedDebuffMultiplier) * 100;

            useMessage = "Used Lesser Clumsiness Potion on " + target.getName() + "! SPD reduced by " +
                    String.format("%.0f", reductionPercent) + "% (" +
                    String.format("%.1f", beforeSpeed - afterSpeed) + " points).";
        }
    }
}

// ---------------------------------------------------------------------------------------------------------------------
// --- TODO: To be removed
// ---------------------------------------------------------------------------------------------------------------------