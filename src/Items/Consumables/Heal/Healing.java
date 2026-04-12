package Items.Consumables.Heal;

import Entities.Entity;
import Items.Consumables.Consumable;

public class Healing extends Consumable {
    protected double healingAmount;

    public Healing(){
        super("Healing Potion", "Restores (20% MaxHP + 500) HP");
        healingAmount = 0.20;
        useMessage = "Used Healing Potion!";

        loadImage("/items/healing_potions/healing_potion.png");
    }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Entity) {
            Entity target = (Entity) Entity;
            double maxHp = target.getMaxHp();
            double healValue = (maxHp * healingAmount) + 500;
            double beforeHp = target.getHp();

            target.heal(healValue);
            double afterHp = target.getHp();
            double actualHeal = afterHp - beforeHp;

            useMessage = "Used Healing Potion on " + target.getName() + "! Restored " +
                    String.format("%.0f", actualHeal) + " HP.";
        }
    }
}