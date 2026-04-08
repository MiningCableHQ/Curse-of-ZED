package Items.Weapons.Ranger;

import Items.Weapons.Weapon;

public class Mistwood extends Weapon {
    public Mistwood() {
        super("Mistwood", "Description pls", 15, "/items/archer_weapon/mistwood.png");
        loadImage("/items/archer_weapon/mistwood.png");
    }

    @Override
    public <T> void activatePassive(T Entity){
        //TODO 10% | 20% | 30% | 40% | 50% chance to evade incoming damage
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}