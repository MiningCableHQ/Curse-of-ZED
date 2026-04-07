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

            // TODO: Clear all status effects from the target
            // This will be implemented when the status effect system is complete

            useMessage = "Used Purific Potion on " + target.getName() + "! All status effects have been cured.";
        }
    }
}