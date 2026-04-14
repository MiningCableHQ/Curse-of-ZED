package Items.Weapons.Mage;

import Items.Weapons.Weapon;

public class Arcanum extends Weapon {
    public Arcanum(){
        super("Arcanum", "30% Chance to deal multiplied damage", 20, "/items/mage_weapon/arcanum_staff.png", 300);
        loadImage("/items/mage_weapon/arcanum_staff.png");
    }

    @Override
    public <T> void activatePassive(T Entity){
        //TODO 30% Chance to deal 120% | 140% | 160% | 180% | 200%  the dmg
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}