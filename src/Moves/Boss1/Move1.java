package Moves.Boss1;

import Entities.Enemies.Thorncrusher;
import Entities.Entity;
import Moves.Move;
import java.util.Random;

public class Move1 extends Move {
    Random rand = new Random();

    public Move1(){
        super("Poison Stomp", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Thorncrusher && Move.currentTarget != null){
            Thorncrusher thorncrusher = (Thorncrusher) Entity;
            Entity target = Move.currentTarget;

            if(rand.nextDouble() <= thorncrusher.getAccuracy()){
                double totalATK = thorncrusher.getAttack();
                totalATK += this.attack;

                // Multiply sum to multiplier
                double damage = totalATK * 1.10;
                double actualDamage = target.takeDamage(damage, target.getDefense(), target.getDmgResistance());

                setDamageDealt(actualDamage);
                setMessage(thorncrusher.getName() + " used " + this.name + " and dealt " + (int)actualDamage + " damage!");

            } else {
                setDamageDealt(0);
                setMessage(thorncrusher.getName() + " used " + this.name + " but it missed!");
            }
        }
    }
}
//TODO FRANK 20% chance to inflict poison
