package Moves.Swordsman;

import Entities.Characters.Swordsman;
import Items.Weapons.Weapon;
import Moves.Move;

public class SweepingStrike extends Move {
    public SweepingStrike(){
        super("Sweeping Strike", 20);
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity) {
        if(Entity instanceof Swordsman){
            Swordsman swordsman = (Swordsman) Entity;

            //all 3 needed ATK stats
            double totalATK = swordsman.getAttack(); //swordsman atk
            if (swordsman.getWeapon() != null) {
                if (swordsman.getWeapon() instanceof Items.Weapons.Weapon) {
                    Weapon equippedWeapon = swordsman.getWeapon();
                    totalATK += equippedWeapon.getAttack();
                }
            }
            totalATK += this.attack; //this move's atk

            //multiply sum to dmg multiplier
            double dmgMultiplier = 0.60;
            double damage = totalATK * dmgMultiplier;
            /*
            TODO Enemy class needs take damage method to make use of the damage variable above
            TODO Add AOE dmg type
            */
        }
    }
}
