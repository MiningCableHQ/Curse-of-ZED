package Items.Consumables.Buff;

import Items.Consumables.Consumable;

public class LesserHardening extends Consumable {
    protected double defenseBuffAmount;

    public LesserHardening(){
        super("Lesser Dulling Potion", "");
        //name = "Lesser Dulling Potion";
        defenseBuffAmount = 0.12;
    }

    @Override
    public <T> void useItem(T Entity){
        //TODO Buffs defense to entity
    }
}
