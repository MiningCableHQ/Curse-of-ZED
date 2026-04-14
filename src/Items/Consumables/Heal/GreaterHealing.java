package Items.Consumables.Heal;

import Entities.Entity;
import Items.Consumables.Consumable;

public class GreaterHealing extends Consumable {
    protected double healingAmount;

    public GreaterHealing(){
        super("Greater Healing Potion", "Restores (30% MaxHP + 750) HP", 150);
        healingAmount = 0.30;
        useMessage = "Used Greater Healing Potion!";

        loadImage("/items/healing_potions/greater_healing_potion.png");
    }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Entity) {
            Entity target = (Entity) Entity;
            double maxHp = target.getMaxHp();
            double healValue = (maxHp * healingAmount) + 750;
            double beforeHp = target.getHp();

            target.heal(healValue);
            double afterHp = target.getHp();
            double actualHeal = afterHp - beforeHp;

            useMessage = "Used Greater Healing Potion on " + target.getName() + "! Restored " +
                    String.format("%.0f", actualHeal) + " HP.";
        }
    }
}