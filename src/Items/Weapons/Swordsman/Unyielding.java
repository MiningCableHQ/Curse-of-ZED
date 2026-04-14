package Items.Weapons.Swordsman;

import Items.Weapons.Weapon;

public class Unyielding extends Weapon {
    public Unyielding(){
        super("Unyielding", "Increase resistance", 10, 225);
    }

    @Override
    public <T> void activatePassive(T Entity) {
        //TODO Increase resistance by 3% | 6% | 9% | 12% | 15%
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}