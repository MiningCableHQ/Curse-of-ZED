package Moves.FinalBoss;

import Entities.Enemies.ZED;
import Entities.Entity;
import Moves.Move;

public class Move3 extends Move {
    public Move3(){
        super("Farewell", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof ZED && Move.currentTarget != null){
            ZED ZED = (ZED) Entity;
            Entity target = Move.currentTarget;

            //Add total atk from enemy and this move
            double totalATK = ZED.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 1.50;

            double actualDamage = target.takeDamage(damage, target.getDefense(), target.getDmgResistance());
            //TODO FRANK 30% chance to inflict frozen
        }
    }
}
