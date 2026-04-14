package Moves.Enemy3;

import Entities.Enemies.Sanjveil;
import Entities.Entity;
import Moves.Move;
import java.util.Random;

public class Move1 extends Move {
    Random rand = new Random();

    public Move1(){
        super("Veil Pierce", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Sanjveil && Move.currentTarget != null){
            Sanjveil sanjveil = (Sanjveil) Entity;
            Entity target = Move.currentTarget;

            if(rand.nextDouble() <= sanjveil.getAccuracy()){
                // Add total atk from enemy and this move
                double totalATK = sanjveil.getAttack();
                totalATK += this.attack;

                // Multiply sum to multiplier
                double damage = totalATK * 1.30;
                double actualDamage = target.takeDamage(damage, target.getDefense(), target.getDmgResistance());

                setDamageDealt(actualDamage);
                setMessage(sanjveil.getName() + " used " + this.name + " and dealt " + (int)actualDamage + " damage!");
            } else {
                setDamageDealt(0);
                setMessage(sanjveil.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}