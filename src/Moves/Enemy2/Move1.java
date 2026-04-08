package Moves.Enemy2;

import Entities.Enemies.Zenzilla;
import Entities.Entity;
import Moves.Move;
import java.util.Random;

public class Move1 extends Move {
    Random rand = new Random();

    public Move1(){
        super("Zen Claw", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Zenzilla && Move.currentTarget != null){
            Zenzilla zenzilla = (Zenzilla) Entity;
            Entity target = Move.currentTarget;

            if(rand.nextDouble() <= zenzilla.getAccuracy()){
                // Add total atk from enemy and this move
                double totalATK = zenzilla.getAttack();
                totalATK += this.attack;

                // Multiply sum to multiplier
                double damage = totalATK * 1.10;
                double actualDamage = target.takeDamage(damage, target.getDefense(), target.getDmgResistance());

                setDamageDealt(actualDamage);
                setMessage(zenzilla.getName() + " used " + this.name + " and dealt " + (int)actualDamage + " damage!");
            } else {
                setDamageDealt(0);
                setMessage(zenzilla.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}