package Items.Consumables.Buff;

import Items.Consumables.Consumable;

public class LesserPower extends Consumable {
    protected double attackBuffAmount;

    public LesserPower() {
        name = "Lesser Power";
        attackBuffAmount = 0.16;
    }

    @Override
    public <T> void useItem(T Entity){
        //TODO buffs attack to entity
    }
}
