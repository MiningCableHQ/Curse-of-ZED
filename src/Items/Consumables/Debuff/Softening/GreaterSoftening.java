package Items.Consumables.Debuff.Softening;

import Entities.Entity;
import Entities.Enemies.Enemy;
import Items.Consumables.Consumable;
import Items.Item;

public class GreaterSoftening extends Consumable {
    protected double defenseDebuffMultiplier;

    public GreaterSoftening() {
        super("Greater Softening Potion", "Reduces enemy DEF by 18%", 120);
        defenseDebuffMultiplier = 0.82;
        useMessage = "Used Greater Softening Potion!";

        loadImage("/items/debuff_potions/greater_softening_potion.png");
    }

    @Override
    public Item.TargetType getTargetType() { return Item.TargetType.ENEMY; }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Enemy) {
            Enemy target = (Enemy) Entity;
            double beforeDefense = target.getDefense();

            target.debuffDefense(defenseDebuffMultiplier);
            double afterDefense = target.getDefense();
            double reductionPercent = (1 - defenseDebuffMultiplier) * 100;

            useMessage = "Used Greater Softening Potion on " + target.getName() + "! DEF reduced by " +
                    String.format("%.0f", reductionPercent) + "% (" +
                    String.format("%.1f", beforeDefense - afterDefense) + " points).";
        }
    }
}