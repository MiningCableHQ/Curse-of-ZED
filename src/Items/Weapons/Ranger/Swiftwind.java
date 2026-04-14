package Items.Weapons.Ranger;

import Items.Weapons.Weapon;

public class Swiftwind extends Weapon {
    public Swiftwind() {
        super("Swiftwind", "30% Chance to deal multiplied damage", 20, "/items/archer_weapon/swiftwind.png", 300);
        loadImage("/items/archer_weapon/swiftwind.png");
    }

    @Override
    public <T> void activatePassive(T Entity){
        //TODO 30% Chance to deal 120% | 140% | 160% | 180% | 200%  the dmg
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}