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
            ZED zed = (ZED) Entity;
            Entity target = Move.currentTarget;

            // Add total atk from enemy and this move
            double totalATK = zed.getAttack();
            totalATK += this.attack;

            // Multiply sum to multiplier (150% damage)
            double damage = totalATK * 1.50;
            double actualDamage = target.takeDamage(damage, target.getDefense(), target.getDmgResistance());

            setDamageDealt(actualDamage);
            setMessage(zed.getName() + " used " + this.name + " and dealt " + (int)actualDamage + " damage!");
        }
    }
}
//TODO FRANK 30% chance to inflict frozen