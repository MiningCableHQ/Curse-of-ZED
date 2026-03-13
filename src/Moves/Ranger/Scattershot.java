package Moves.Ranger;

import Entities.Characters.Ranger;
import Items.Weapons.Weapon;
import Moves.Move;

public class Scattershot extends Move {
    public Scattershot() {
        super("Scattershot", 20);
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Ranger){
            Ranger ranger = (Ranger) Entity;

            //all 3 needed ATK stats
            double totalATK = ranger.getAttack(); //ranger atk
            if (ranger.getWeapon() != null) {
                if (ranger.getWeapon() instanceof Items.Weapons.Weapon) {
                    Weapon equippedWeapon = ranger.getWeapon();
                    totalATK += equippedWeapon.getAttack();
                }
            }
            totalATK += this.attack; //this move's atk

            //multiply sum to dmg multiplier
            double dmgMultiplier = 0.55;
            double damage = totalATK * dmgMultiplier;
            /*
            TODO Enemy class needs take damage method to make use of the damage variable above
            TODO Add AOE dmg type
            */
        }
    }
}
