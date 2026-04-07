package Items.Consumables.Debuff.Dulling;

import Entities.Entity;
import Entities.Enemies.Enemy;
import Items.Consumables.Consumable;

public class GreaterDulling extends Consumable {
    protected double attackDebuffMultiplier;

    public GreaterDulling() {
        super("Greater Dulling Potion", "Reduces enemy ATK by 24%");
        attackDebuffMultiplier = 0.76;
        useMessage = "Used Greater Dulling Potion!";

        loadImage("/items/debuff_potions/greater_dulling_potion.png");
    }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Enemy) {
            Enemy target = (Enemy) Entity;
            double beforeAttack = target.getAttack();

            target.debuffAttack(attackDebuffMultiplier);
            double afterAttack = target.getAttack();
            double reductionPercent = (1 - attackDebuffMultiplier) * 100;

            useMessage = "Used Greater Dulling Potion on " + target.getName() + "! ATK reduced by " +
                    String.format("%.0f", reductionPercent) + "% (" +
                    String.format("%.1f", beforeAttack - afterAttack) + " points).";
        } else if (Entity instanceof Entity) {
            Entity target = (Entity) Entity;
            double beforeAttack = target.getAttack();

            target.debuffAttack(attackDebuffMultiplier);
            double afterAttack = target.getAttack();
            double reductionPercent = (1 - attackDebuffMultiplier) * 100;

            useMessage = "Used Greater Dulling Potion on " + target.getName() + "! ATK reduced by " +
                    String.format("%.0f", reductionPercent) + "% (" +
                    String.format("%.1f", beforeAttack - afterAttack) + " points).";
        }
    }
}