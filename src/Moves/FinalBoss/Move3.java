package Moves.FinalBoss;

import Entities.Enemies.ZED;
import Entities.Entity;
import Moves.Move;
import java.util.Random;

public class Move3 extends Move {
    Random rand = new Random();

    public Move3(){
        super("Farewell", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof ZED && Move.currentTarget != null){
            ZED zed = (ZED) Entity;
            Entity target = Move.currentTarget;

            if(rand.nextDouble() <= zed.getAccuracy()){
                // Add total atk from enemy and this move
                double totalATK = zed.getAttack();
                totalATK += this.attack;

                // Multiply sum to multiplier (150% damage)
                double damage = totalATK * 1.50;
                double actualDamage = target.takeDamage(damage, target.getDefense(), target.getDmgResistance());

                setDamageDealt(actualDamage);
                setMessage(zed.getName() + " used " + this.name + " and dealt " + (int)actualDamage + " damage!");

                if(rand.nextDouble() <= 0.50){
                    Move.currentBattle.applyFrozen(target);
                }
            } else {
                setDamageDealt(0);
                setMessage(zed.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}