package Moves.Mage;

import Entities.Characters.Mage;
import Items.Weapons.Weapon;
import Moves.Move;

public class ArcaneBolt extends Move {
    public ArcaneBolt() {
        super("Arcane Bolt", 20);
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity) {
        if(Entity instanceof Mage){
            Mage mage = (Mage) Entity;

            //all 3 needed ATK stats
            double totalATK = mage.getAttack(); //mage atk
            if (mage.getWeapon() != null) {
                if (mage.getWeapon() instanceof Items.Weapons.Weapon) {
                    Weapon equippedWeapon = mage.getWeapon();
                    totalATK += equippedWeapon.getAttack();
                }
            }
            totalATK += this.attack; //this move's atk

            //multiply sum to dmg multiplier
            double dmgMultiplier = 1.45;
            double damage = totalATK * dmgMultiplier;
            /*
            TODO Enemy class needs take damage method to make use of the damage variable above
            TODO Add single target dmg type
            */
        }
    }
}
