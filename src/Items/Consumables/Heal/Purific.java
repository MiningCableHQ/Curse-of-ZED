package Items.Consumables.Heal;

import Entities.Entity;
import Items.Consumables.Consumable;

public class Purific extends Consumable {

    public Purific(){
        super("Purific Potion", "Cures all status effects");
        useMessage = "Used Purific Potion!";

        loadImage("/items/healing_potions/purifying_potion.png");
    }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Entity) {
            Entity target = (Entity) Entity;

            //Store the number of status effects before clearing
            int effectCount = target.getStatusEffects().size();
            target.removeAllStatusEffects();

            if (effectCount > 0) {
                useMessage = "Used Purific Potion on " + target.getName() +
                        "! Cured " + effectCount + " status effect" +
                        (effectCount != 1 ? "s" : "") + ".";
            } else {
                useMessage = "Used Purific Potion on " + target.getName() +
                        "! No status effects to cure.";
            }
        }
    }
}