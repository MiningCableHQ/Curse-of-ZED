package Moves.Ranger;

import Entities.Characters.Ranger;
import Entities.Entity;
import Moves.Move;
import java.util.*;

public class FlurryShot extends Move {
    Random rand  = new Random();

    public FlurryShot() {
        super("Flurry Shot", 40);
        hasUnlocked = false;
        description = "Deals 45% of ATK as dmg 2-5x to a single target";
    }

    @Override
    public <T> void execute(T Entity) {
        if(Entity instanceof Ranger && Move.currentTarget != null) {
            Ranger ranger = (Ranger) Entity;
            Entity enemy = Move.currentTarget;

            //Add total atk from enemy and this move
            double totalATK = ranger.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier and multiply by 2-5x
            double damage = totalATK * 0.45 * rand.nextDouble(2, 6);

            double actualDamage = enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());
        }
    }
}
