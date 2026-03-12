package Items.Consumables;

import Items.Item;

public class Consumable extends Item {

    @Override
    public <T> void useItem(T Entity){
        System.out.println("Item used (but nothing happened)");
    }
}
