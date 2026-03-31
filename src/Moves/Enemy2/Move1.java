package Moves.Enemy2;

import Entities.Enemies.Enemy2;
import Moves.Move;

public class Move1 extends Move {
    public Move1(){
        super("Move 1", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Enemy2){
            Enemy2 enemy2 = (Enemy2) Entity;

            //Add total atk from enemy and this move
            double totalATK = enemy2.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 1.10;
        }
    }
}
