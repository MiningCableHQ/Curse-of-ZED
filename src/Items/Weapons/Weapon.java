package Items.Weapons;

import Items.Item;

public abstract class Weapon extends Item {
    protected int attack;

    public Weapon(){
        tier = 1.0;
    }

    public abstract <T> void activatePassive(T Entity);
}
