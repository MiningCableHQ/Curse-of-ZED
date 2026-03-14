package Moves.Enemy4;

import Entities.Enemies.Enemy4;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("Move 2", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Enemy4){
            Enemy4 enemy4 = (Enemy4) Entity;

            //Add total atk from enemy and this move
            double totalATK = enemy4.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 2.00;
            //TODO cannot be used for 1 turn
        }
    }
}
