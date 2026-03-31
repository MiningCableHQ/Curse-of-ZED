package Moves.FinalBoss;

import Entities.Enemies.FinalBoss;
import Moves.Move;

public class Move1 extends Move {
    public Move1(){
        super("Move 1", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof FinalBoss){
            FinalBoss finalBoss = (FinalBoss) Entity;

            //Add total atk from enemy and this move
            double totalATK = finalBoss.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 0.50;
            //TODO 15% chance to inflict burn
        }
    }
}
