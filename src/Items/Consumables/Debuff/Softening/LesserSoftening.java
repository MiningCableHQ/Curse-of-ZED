package Items.Consumables.Debuff.Softening;

import Entities.Entity;
import Entities.Enemies.Enemy;
import Items.Consumables.Consumable;

public class LesserSoftening extends Consumable {
    protected double defenseDebuffMultiplier;

    public LesserSoftening() {
        super("Lesser Softening Potion", "Reduces enemy DEF by 6%", 40);
        defenseDebuffMultiplier = 0.94;
        useMessage = "Used Lesser Softening Potion!";

        loadImage("/items/debuff_potions/lesser_softening_potion.png");
    }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Enemy) {
            Enemy target = (Enemy) Entity;
            double beforeDefense = target.getDefense();

            target.debuffDefense(defenseDebuffMultiplier);
            double afterDefense = target.getDefense();
            double reductionPercent = (1 - defenseDebuffMultiplier) * 100;

            useMessage = "Used Lesser Softening Potion on " + target.getName() + "! DEF reduced by " +
                    String.format("%.0f", reductionPercent) + "% (" +
                    String.format("%.1f", beforeDefense - afterDefense) + " points).";
        }
    }
}