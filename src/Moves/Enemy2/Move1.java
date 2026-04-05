package Moves.Enemy2;

import Entities.Enemies.Zenzilla;
import Entities.Entity;
import Moves.Move;

public class Move1 extends Move {
    public Move1(){
        super("Zen Claw", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Zenzilla && Move.currentTarget != null){
            Zenzilla zenzilla = (Zenzilla) Entity;
            Entity target = Move.currentTarget;

            //Add total atk from enemy and this move
            double totalATK = zenzilla.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 1.10;

            target.takeDamage(damage, target.getDefense(), target.getDmgResistance());
        }
    }
}
