package Moves.Ranger;

import Entities.Characters.Ranger;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;
import java.util.Random;

public class SnipersGamble extends Move {
    private static final Random rand = new Random();

    public SnipersGamble() {
        super("Sniper's Gamble", 50, TargetType.ENEMY);
        hasUnlocked = false;
        description = "Deals 350% of ATK as dmg to a single target, but accuracy is lowered by 25% for this move";
    }

    @Override
    public <T> void execute(T Entity) {
        if(Entity instanceof Ranger && Move.currentTarget != null){
            Ranger ranger = (Ranger) Entity;
            Entity enemy = Move.currentTarget;

            // Calculate base accuracy 95%(base) - 25%(this move's drawback)
            double baseAccuracy = 0.7;
            double randomValue = rand.nextDouble();

            // Check if the attack hits
            if (randomValue <= baseAccuracy) {
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
                double damage = totalATK * 3.50;
                double actualDamage = enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());

                setDamageDealt(actualDamage);
                setMessage(ranger.getName() + " used " + this.name + " on " + enemy.getName() +
                        " and dealt " + (int)actualDamage + " damage!");
            } else {
                // Attack missed
                setDamageDealt(0);
                setMessage(ranger.getName() + " used " + this.name + " on " + enemy.getName() +
                        " but it missed!");
            }
        }
    }
}
//TODO accuracy is lowered by 25% for this move (I implemented it, but needs double checking)