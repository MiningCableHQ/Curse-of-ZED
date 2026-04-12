package Items.Consumables.Debuff.Softening;

import Entities.Entity;
import Entities.Enemies.Enemy;
import Items.Consumables.Consumable;

public class Softening extends Consumable {
    protected double defenseDebuffMultiplier;

    public Softening() {
        super("Softening Potion", "Reduces enemy DEF by 12%");
        defenseDebuffMultiplier = 0.88;
        useMessage = "Used Softening Potion!";

        loadImage("/items/debuff_potions/softening_potion.png");
    }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Enemy) {
            Enemy target = (Enemy) Entity;
            double beforeDefense = target.getDefense();

            target.debuffDefense(defenseDebuffMultiplier);
            double afterDefense = target.getDefense();
            double reductionPercent = (1 - defenseDebuffMultiplier) * 100;

            useMessage = "Used Softening Potion on " + target.getName() + "! DEF reduced by " +
                    String.format("%.0f", reductionPercent) + "% (" +
                    String.format("%.1f", beforeDefense - afterDefense) + " points).";
        }
    }
}