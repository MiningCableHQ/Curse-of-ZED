package Items.Weapons.Swordsman;

import Items.Weapons.Weapon;

public class Unyielding extends Weapon {
    public Unyielding(){
        attack = 10;
    }

    @Override
    public <T> void activatePassive(T Entity) {
        //TODO Reduce incoming damage by 10% | 20% | 30% | 40% | 50%
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}
