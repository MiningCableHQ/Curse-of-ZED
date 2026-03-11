package Items.Weapons.Mage;

import Items.Weapons.Weapon;

public class Arcanum extends Weapon {
    public Arcanum(){
        attack = 15;
    }

    @Override
    public <T> void activatePassive(T Entity){
        //TODO 30% Chance to deal 120% | 140% | 160% | 180% | 200%  the dmg
    }
}
