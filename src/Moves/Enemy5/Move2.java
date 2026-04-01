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

            //Add total atk from enemy and this move
            double totalATK = reyven.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier and multiply by 2-5x
            double damage = totalATK * 0.50 * rand.nextDouble(2, 6);

            double actualDamage = target.takeDamage(damage, target.getDefense(), target.getDmgResistance());
        }
    }
}
