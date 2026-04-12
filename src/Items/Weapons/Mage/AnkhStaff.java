package Items.Weapons.Mage;

import Items.Weapons.Weapon;

public class AnkhStaff extends Weapon {
    public AnkhStaff() {
        super("Ankh Staff", "30% Chance to heal after using a move", 10, "/items/mage_weapon/ankh_staff.png");
        loadImage("/items/mage_weapon/ankh_staff.png");
    }

    @Override
    public <T> void activatePassive(T Entity){
        //TODO 30% Chance to heal after using a move by 4% | 8% | 12% | 16% | 20%
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}