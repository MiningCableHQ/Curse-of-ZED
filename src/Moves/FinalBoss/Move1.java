package Moves.FinalBoss;

import Entities.Enemies.ZED;
import Entities.Entity;
import Moves.Move;

public class Move1 extends Move {
    public Move1(){
        super("WHY!?", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof ZED && Move.currentTarget != null){
            ZED zed = (ZED) Entity;
            Entity target = Move.currentTarget;

            //Add total atk from enemy and this move
            double totalATK = zed.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 0.50;

            target.takeDamage(damage, target.getDefense(), target.getDmgResistance());
            //TODO FRANK 15% chance to inflict burn
        }
    }
}
