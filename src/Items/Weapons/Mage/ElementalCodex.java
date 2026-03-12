package Items.Weapons.Mage;

import Items.Weapons.Weapon;

public class ElementalCodex extends Weapon {
    public ElementalCodex(){
        attack = 15;
    }

    @Override
    public <T> void activatePassive(T Entity){
        //TODO 5% | 10% | 15% | 20% | 25% chance to apply poison/burn/frozen debuff on an enemy
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}
