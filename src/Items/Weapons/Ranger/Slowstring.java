package Items.Weapons.Ranger;

import Items.Weapons.Weapon;

public class Slowstring extends Weapon {
    public Slowstring() {
        attack = 10;
    }

    @Override
    public <T> void activatePassive(T Entity){
        //TODO 5% | 10% | 15% | 20% | 25% chance to apply a slow debuff on an enemy
    }
}
