package Moves.Ranger;

import Entities.Characters.Ranger;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;
import java.util.*;

public class BounceShot extends Move {
    Random rand = new Random();

    public BounceShot() {
        super("Bounce Shot", 30, TargetType.ALL_ENEMIES);
        hasUnlocked = true;
        description = "Deal 20% of ATK to all enemies 1-5x";
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Ranger && Move.currentTarget != null){
            Ranger ranger = (Ranger) Entity;
            Entity enemy = Move.currentTarget;

            if(rand.nextDouble() <= ranger.getAccuracy()){
                // All 3 needed ATK stats
                double totalATK = ranger.getAttack(); // ranger atk
                if (ranger.getWeapon() != null) {
                    if (ranger.getWeapon() instanceof Items.Weapons.Weapon) {
                        Weapon equippedWeapon = ranger.getWeapon();
                        totalATK += equippedWeapon.getAttack();
                    }
                }
                totalATK += this.attack; // this move's atk

                // Random number of hits between 1 and 5
                int hits = rand.nextInt(5) + 1; // 1 to 5 inclusive
                double damagePerHit = totalATK * 0.20;
                double totalDamage = damagePerHit * hits;
                double actualDamage = enemy.takeDamage(totalDamage, enemy.getDefense(), enemy.getDmgResistance());

                // Set message for battle display
                setDamageDealt(actualDamage);
                setMessage(ranger.getName() + " used " + this.name + " and dealt damage to all enemies (" + hits + " hits)!");
            } else {
                setDamageDealt(0);
                setMessage(ranger.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}