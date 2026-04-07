package Items.Consumables.Debuff.Blinding;

import Entities.Entity;
import Entities.Enemies.Enemy;
import Items.Consumables.Consumable;

public class LesserBlinding extends Consumable {
    protected double accuracyDebuffMultiplier;

    public LesserBlinding() {
        super("Lesser Blinding Potion", "Reduces enemy ACC by 5%");
        accuracyDebuffMultiplier = 0.95;
        useMessage = "Used Lesser Blinding Potion!";

        loadImage("/items/debuff_potions/lesser_blinding_potion.png");
    }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Enemy) {
            Enemy target = (Enemy) Entity;
            double beforeAccuracy = target.getAccuracy();

            target.debuffAccuracy(accuracyDebuffMultiplier);
            double afterAccuracy = target.getAccuracy();
            double reductionPercent = (1 - accuracyDebuffMultiplier) * 100;

            useMessage = "Used Lesser Blinding Potion on " + target.getName() + "! ACC reduced by " +
                    String.format("%.0f", reductionPercent) + "% (" +
                    String.format("%.2f", beforeAccuracy - afterAccuracy) + " points).";
        } else if (Entity instanceof Entity) {
            Entity target = (Entity) Entity;
            double beforeAccuracy = target.getAccuracy();

            target.debuffAccuracy(accuracyDebuffMultiplier);
            double afterAccuracy = target.getAccuracy();
            double reductionPercent = (1 - accuracyDebuffMultiplier) * 100;

            useMessage = "Used Lesser Blinding Potion on " + target.getName() + "! ACC reduced by " +
                    String.format("%.0f", reductionPercent) + "% (" +
                    String.format("%.2f", beforeAccuracy - afterAccuracy) + " points).";
        }
    }
}