package Items.Weapons.Mage;

import Items.Weapons.Weapon;

public class AnkhStaff extends Weapon {
    public AnkhStaff() {
        super("Ankh Staff", "Description pls", 10, "/items/mage_weapon/ankh_staff.png");
        loadImage("/items/mage_weapon/ankh_staff.png");
    }

    @Override
    public <T> void activatePassive(T Entity){
        //TODO 30% Chance to heal after using a move by 10% | 20% | 30% | 40% | 50%
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}