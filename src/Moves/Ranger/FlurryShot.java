package Moves.Ranger;

import Entities.Characters.Ranger;
import Entities.Entity;
import Moves.Move;
import java.util.*;

public class FlurryShot extends Move {
    Random rand = new Random();

    public FlurryShot() {
        super("Flurry Shot", 40, TargetType.ENEMY);
        hasUnlocked = false;
        description = "Deals 45% of ATK as dmg 2-5x to a single target";
    }

    @Override
    public <T> void execute(T Entity) {
        if(Entity instanceof Ranger && Move.currentTarget != null) {
            Ranger ranger = (Ranger) Entity;
            Entity enemy = Move.currentTarget;

            // Add total atk from ranger and this move
            double totalATK = ranger.getAttack();
            totalATK += this.attack;

            // Random number of hits between 2 and 5
            int hits = rand.nextInt(4) + 2; // 2 to 5 inclusive
            double damagePerHit = totalATK * 0.45;
            double totalDamage = damagePerHit * hits;
            double actualDamage = enemy.takeDamage(totalDamage, enemy.getDefense(), enemy.getDmgResistance());

            // Set message for battle display
            setDamageDealt(actualDamage);
            setMessage(ranger.getName() + " used " + this.name + " on " + enemy.getName() +
                    " and dealt " + (int)actualDamage + " damage (" + hits + " hits)!");
        }
    }
}