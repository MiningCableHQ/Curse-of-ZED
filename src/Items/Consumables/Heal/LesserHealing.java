package Items.Consumables.Heal;

import Items.Consumables.Consumable;

public class LesserHealing extends Consumable {
    protected double healingAmount;

    public LesserHealing(){
        name = "Lesser Healing Potion";
        healingAmount = 0.30;
    }

    @Override
    public <T> void useItem(T Entity){
        //TODO heals hp to entity
    }
}
