package Items.Consumables.Debuff.Dulling;

import Items.Consumables.Consumable;

public class LesserDulling extends Consumable {
    protected double attackDebuffAmount;

    public LesserDulling() {
        super("Lesser Dulling Potion", "");
        //name = "Lesser Dulling Potion";
        attackDebuffAmount = 0.08;
    }

    @Override
    public <T> void useItem(T Entity){
        //TODO debuffs attack to entity
    }
}
