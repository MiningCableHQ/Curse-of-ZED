package Moves.Ranger;

import Entities.Characters.Ranger;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

public class PreciseShot extends Move {
    public PreciseShot() {
        super("Precise Shot", 20);
        hasUnlocked = true;
        description = "Deal 150% of ATK to a single target";
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Ranger && Move.currentTarget != null){
            Ranger ranger = (Ranger) Entity;
            Entity enemy = Move.currentTarget;

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
            double damage = totalATK * 1.50;
            enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());
        }
    }
}
