package Moves.Enemy4;

import Entities.Enemies.Razormaw;
import Entities.Entity;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("Skull Tear", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Razormaw && Move.currentTarget != null){
            Razormaw razormaw = (Razormaw) Entity;
            Entity target = Move.currentTarget;

            //Add total atk from enemy and this move
            double totalATK = razormaw.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 2;

            double actualDamage = target.takeDamage(damage, target.getDefense(), target.getDmgResistance());
        }
    }
}
