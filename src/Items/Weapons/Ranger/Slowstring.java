package Items.Weapons.Ranger;

import Items.Weapons.Weapon;

public class Slowstring extends Weapon {
    public Slowstring() {
        super("Slowstring", "Chance to apply a slow debuff on an enemy", 10, "/items/archer_weapon/slowstring.png", 225);
        loadImage("/items/archer_weapon/slowstring.png");
    }

    @Override
    public <T> void activatePassive(T Entity){
        //TODO 5% | 10% | 15% | 20% | 25% chance to apply a slow debuff on an enemy
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}