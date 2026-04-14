package Items.Consumables.Debuff.Dulling;

import Entities.Entity;
import Entities.Enemies.Enemy;
import Items.Consumables.Consumable;

public class LesserDulling extends Consumable {
    protected double attackDebuffMultiplier;

    public LesserDulling() {
        super("Lesser Dulling Potion", "Reduces enemy ATK by 8%", 35);
        attackDebuffMultiplier = 0.92;
        useMessage = "Used Lesser Dulling Potion!";

        loadImage("/items/debuff_potions/lesser_dulling_potion.png");
    }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Enemy) {
            Enemy target = (Enemy) Entity;
            double beforeAttack = target.getAttack();

            target.debuffAttack(attackDebuffMultiplier);
            double afterAttack = target.getAttack();
            double reductionPercent = (1 - attackDebuffMultiplier) * 100;

            useMessage = "Used Lesser Dulling Potion on " + target.getName() + "! ATK reduced by " +
                    String.format("%.0f", reductionPercent) + "% (" +
                    String.format("%.1f", beforeAttack - afterAttack) + " points).";
        }
    }
}