package Moves.Enemy3;

import Entities.Enemies.Enemy3;
import Moves.Move;

public class Move1 extends Move {
    public Move1(){
        super("Move 1", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Enemy3){
            Enemy3 enemy3 = (Enemy3) Entity;

            //Add total atk from enemy and this move
            double totalATK = enemy3.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 1.30;
        }
    }
}
