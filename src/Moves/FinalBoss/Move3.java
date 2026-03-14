package Moves.FinalBoss;

import Entities.Enemies.FinalBoss;
import Moves.Move;

public class Move3 extends Move {
    public Move3(){
        super("Move 3", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof FinalBoss){
            FinalBoss finalBoss = (FinalBoss) Entity;

            //Add total atk from enemy and this move
            double totalATK = finalBoss.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 1.50;
            //TODO 30% chance to inflict frozen, cannot be used for 2 turns
        }
    }
}
