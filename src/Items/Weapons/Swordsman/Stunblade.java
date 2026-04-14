package Items.Weapons.Swordsman;

import Items.Weapons.Weapon;

public class Stunblade extends Weapon {
    public Stunblade(){
        super("Stunblade", "Chance to apply a stun debuff on an enemy", 15, "/items/warrior_weapon/stunblade_sword.png", 265);
        loadImage("/items/warrior_weapon/stunblade_sword.png");
    }

    @Override
    public <T> void activatePassive(T Entity) {
        //TODO 5% | 10% | 15% | 20% | 25% chance to stun enemy
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}