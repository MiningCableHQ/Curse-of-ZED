package Items.Weapons.Mage;

import Items.Weapons.Weapon;

public class ElementalCodex extends Weapon {
    public ElementalCodex(){
        super("ElementalCodex", "Chance to apply poison/burn/frozen debuff on an enemy", 15, "/items/mage_weapon/elemental_codex.png", 265);
        loadImage("/items/mage_weapon/elemental_codex.png");
    }

    @Override
    public <T> void activatePassive(T Entity){
        //TODO 5% | 10% | 15% | 20% | 25% chance to apply poison/burn/frozen debuff on an enemy
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}