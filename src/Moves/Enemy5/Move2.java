package Moves.Enemy5;

import Entities.Enemies.Reyven;
import Entities.Entity;
import Moves.Move;
import java.util.*;

public class Move2 extends Move {
    Random rand = new Random();

    public Move2(){
        super("Feather Flurry", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Reyven && Move.currentTarget != null){
            Reyven reyven = (Reyven) Entity;
            Entity target = Move.currentTarget;

            // Add total atk from enemy and this move
            double totalATK = reyven.getAttack();
            totalATK += this.attack;

            // Random number of hits between 2 and 5
            int hits = rand.nextInt(4) + 2; // 2 to 5 inclusive
            double damagePerHit = totalATK * 0.50;
            double totalDamage = damagePerHit * hits;
            double actualDamage = target.takeDamage(totalDamage, target.getDefense(), target.getDmgResistance());

            // Set message for battle display
            setDamageDealt(actualDamage);
            setMessage(reyven.getName() + " used " + this.name + " and dealt " +
                    (int)actualDamage + " damage (" + hits + " hits)!");
        }
    }
}