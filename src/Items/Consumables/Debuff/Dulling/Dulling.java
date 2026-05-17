package Items.Consumables.Debuff.Dulling;

import Entities.Entity;
import Entities.Enemies.Enemy;
import Items.Consumables.Consumable;
import Items.Item;

public class Dulling extends Consumable {
    protected double attackDebuffMultiplier;

    public Dulling() {
        super("Dulling Potion", "Reduces enemy ATK by 16%", 70);
        attackDebuffMultiplier = 0.84;
        useMessage = "Used Dulling Potion!";

        loadImage("/items/debuff_potions/dulling_potion.png");
    }

    @Override
    public Item.TargetType getTargetType() { return Item.TargetType.ENEMY; }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Enemy) {
            Enemy target = (Enemy) Entity;
            double beforeAttack = target.getAttack();

            target.debuffAttack(attackDebuffMultiplier);
            double afterAttack = target.getAttack();
            double reductionPercent = (1 - attackDebuffMultiplier) * 100;

            useMessage = "Used Dulling Potion on " + target.getName() + "! ATK reduced by " +
                    String.format("%.0f", reductionPercent) + "% (" +
                    String.format("%.1f", beforeAttack - afterAttack) + " points).";
        }
    }
}