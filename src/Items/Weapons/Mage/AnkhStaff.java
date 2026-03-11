package Items.Weapons.Mage;

import Items.Weapons.Weapon;

public class AnkhStaff extends Weapon {
    public AnkhStaff() {
        attack = 10;
    }

    @Override
    public <T> void activatePassive(T Entity){
        //TODO 30% Chance to heal after using a move by 10% | 20% | 30% | 40% | 50%
    }
}

