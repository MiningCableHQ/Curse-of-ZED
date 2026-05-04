package Moves.Ranger;

import Entities.Characters.Ranger;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

public class PreciseShot extends Move {
    public PreciseShot() {
        super("Precise Shot", 20, TargetType.ENEMY);
        hasUnlocked = true;
        description = "Deal 150% of ATK to a single target";
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Ranger && Move.currentTarget != null){
            Ranger ranger = (Ranger) Entity;
            Entity enemy = Move.currentTarget;

            // All 3 needed ATK stats
            double totalATK = ranger.getAttack(); // ranger atk
            if (ranger.getWeapon() != null) {
                if (ranger.getWeapon() instanceof Items.Weapons.Weapon) {
                    Weapon equippedWeapon = ranger.getWeapon();
                    totalATK += equippedWeapon.getAttack();
                }
            }
            totalATK += this.attack; // this move's atk

            // Multiply sum to damage multiplier
            double damage = totalATK * 100; /** Ako gi op**/
            double actualDamage = enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());

            setDamageDealt(actualDamage);
            setMessage(ranger.getName() + " used " + this.name + " on " + enemy.getName() +
                    " and dealt " + (int)actualDamage + " damage!");
        }
    }
}