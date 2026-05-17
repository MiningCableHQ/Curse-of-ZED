package Items.Consumables.Heal;

import Entities.Entity;
import Items.Consumables.Consumable;

public class LesserHealing extends Consumable {
    protected double healingAmount;

    public LesserHealing(){
        super("Lesser Healing Potion", "Restores (15% MaxHP + 300) HP", 50);
        healingAmount = 0.15;
        useMessage = "Used Lesser Healing Potion!";

        loadImage("/items/healing_potions/lesser_healing_potion.png");
    }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Entity) {
            Entity target = (Entity) Entity;
            double maxHp = target.getMaxHp();
            double healValue = (maxHp * healingAmount) + 300;
            double beforeHp = target.getHp();

            target.heal(healValue);
            double afterHp = target.getHp();
            double actualHeal = afterHp - beforeHp;

            useMessage = "Used Lesser Healing Potion on " + target.getName() + "! Restored " +
                    String.format("%.0f", actualHeal) + " HP.";
        }
    }
}