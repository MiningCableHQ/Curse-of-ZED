package Moves.Enemy5;

import Entities.Enemies.Reyven;
import Entities.Entity;
import Moves.Move;

public class Move1 extends Move {
    public Move1(){
        super("Reyven Strike", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Reyven && Move.currentTarget != null){
            Reyven reyven = (Reyven) Entity;
            Entity target = Move.currentTarget;

            //Add total atk from enemy and this move
            double totalATK = reyven.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 1.40;

            double actualDamage = target.takeDamage(damage, target.getDefense(), target.getDmgResistance());

            setDamageDealt(actualDamage);
            setMessage(reyven.getName() + " used " + this.name + " and dealt " + (int)actualDamage + " damage!");
        }
    }
}
