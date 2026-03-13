package Items.Weapons;

import Items.Item;

public abstract class Weapon extends Item {
    protected double attack;

    public Weapon(String name, String description, double attack){
        super(name, description);
        this.attack = attack;
        tier = 1.0;
    }

    public abstract <T> void activatePassive(T Entity);

    //getters
    public double getAttack(){
        return attack;
    }
}
