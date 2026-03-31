package Moves.Enemy5;

import Entities.Enemies.Enemy5;
import Moves.Move;
import java.util.*;

public class Move2 extends Move {
    Random rand = new Random();

    public Move2(){
        super("Move 2", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Enemy5){
            Enemy5 enemy5 = (Enemy5) Entity;

            //Add total atk from enemy and this move
            double totalATK = enemy5.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier and multiply by 2-5x
            double damage = totalATK * 0.60 * rand.nextDouble(2, 6);
        }
    }
}
