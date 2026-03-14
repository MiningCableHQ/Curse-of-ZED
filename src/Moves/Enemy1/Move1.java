package Moves.Enemy1;

import Entities.Enemies.Enemy1;
import Moves.Move;

public class Move1 extends Move {
    public Move1(){
        super("Move 1", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Enemy1){
            Enemy1 enemy1 = (Enemy1) Entity;

            //Add total atk from enemy and this move
            double totalATK = enemy1.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 1.20;
        }
    }
}
