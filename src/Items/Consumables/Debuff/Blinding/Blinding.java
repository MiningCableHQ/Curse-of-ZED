package Items.Consumables.Debuff.Blinding;

import Entities.Entity;
import Entities.Enemies.Enemy;
import Items.Consumables.Consumable;

public class Blinding extends Consumable {
    protected double accuracyDebuffMultiplier;

    public Blinding() {
        super("Blinding Potion", "Reduces enemy ACC by 10%", 60);
        accuracyDebuffMultiplier = 0.90;
        useMessage = "Used Blinding Potion!";

        loadImage("/items/debuff_potions/blinding_potion.png");
    }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Enemy) {
            Enemy target = (Enemy) Entity;
            double beforeAccuracy = target.getAccuracy();

            target.debuffAccuracy(accuracyDebuffMultiplier);
            double afterAccuracy = target.getAccuracy();
            double reductionPercent = (1 - accuracyDebuffMultiplier) * 100;

            useMessage = "Used Blinding Potion on " + target.getName() + "! ACC reduced by " +
                    String.format("%.0f", reductionPercent) + "% (" +
                    String.format("%.2f", beforeAccuracy - afterAccuracy) + " points).";
        }
    }
}