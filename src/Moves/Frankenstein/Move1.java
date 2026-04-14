package Moves.Frankenstein;

import Entities.Enemies.Frankenstein;
import Entities.Entity;
import Moves.Move;
import java.util.Random;

public class Move1 extends Move {
    Random rand = new Random();

    public Move1(){
        super("PALOA", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Frankenstein && Move.currentTarget != null){
            Frankenstein frankenstein = (Frankenstein) Entity;
            Entity target = Move.currentTarget;

            if(rand.nextDouble() <= frankenstein.getAccuracy()){
                //Add total atk from enemy and this move
                double totalATK = frankenstein.getAttack();
                totalATK += this.attack;

                //multiply sum to multiplier
                double damage = totalATK * 0.70;

                double actualDamage = target.takeDamage(damage, target.getDefense(), target.getDmgResistance());

                setDamageDealt(actualDamage);
                setMessage(frankenstein.getName() + " used " + this.name + " and dealt " + (int)actualDamage + " damage!");
            } else {
                setDamageDealt(0);
                setMessage(frankenstein.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}
