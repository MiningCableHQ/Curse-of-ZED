package Moves.Enemy5;

import Entities.Enemies.Enemy5;
import Moves.Move;

public class Move1 extends Move {
    public Move1(){
        super("Move 1", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Enemy5){
            Enemy5 enemy5 = (Enemy5) Entity;

            //Add total atk from enemy and this move
            double totalATK = enemy5.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 1.40;
        }
    }
}
