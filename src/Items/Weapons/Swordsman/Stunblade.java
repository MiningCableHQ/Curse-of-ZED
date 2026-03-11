package Items.Weapons.Swordsman;

import Items.Weapons.Weapon;

public class Stunblade extends Weapon {
    public Stunblade(){
        attack = 15;
    }

    @Override
    public <T> void activatePassive(T Entity) {
        //TODO 5% | 10% | 15% | 20% | 25% chance to stun enemy
    }
}
