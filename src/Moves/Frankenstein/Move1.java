package Moves.Frankenstein;

import Entities.Enemies.Frankenstein;
import Entities.Entity;
import Moves.Move;

public class Move1 extends Move {
    public Move1(){
        super("PALOA", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Frankenstein && Move.currentTarget != null){
            Frankenstein frankenstein = (Frankenstein) Entity;
            Entity target = Move.currentTarget;

            //Add total atk from enemy and this move
            double totalATK = frankenstein.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 0.70;

            target.takeDamage(damage, target.getDefense(), target.getDmgResistance());
        }
    }
}
