package Moves.Boss1;

import Entities.Enemies.Boss1;
import Moves.Move;

public class Move1 extends Move {
    public Move1(){
        super("Move 1", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Boss1){
            Boss1 boss1 = (Boss1) Entity;

            //Add total atk from enemy and this move
            double totalATK = boss1.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 1.10;
            //TODO 20% chance to inflict poison
        }
    }
}
