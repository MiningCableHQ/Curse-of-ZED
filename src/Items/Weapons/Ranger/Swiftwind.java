package Items.Weapons.Ranger;

import Items.Weapons.Weapon;

public class Swiftwind extends Weapon {
    public Swiftwind() {
        attack = 20;
    }

    @Override
    public <T> void activatePassive(T Entity){
        //TODO 30% Chance to deal 120% | 140% | 160% | 180% | 200%  the dmg
    }
}
