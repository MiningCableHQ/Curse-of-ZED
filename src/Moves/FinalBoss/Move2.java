package Moves.FinalBoss;

import Entities.Enemies.ZED;
import Entities.Entity;
import Moves.Move;
import java.util.Random;

public class Move2 extends Move {
    Random rand = new Random();

    public Move2(){
        super("So be it.", 0);
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof ZED && Move.currentTarget != null) {
            ZED zed = (ZED) Entity;
            Entity target = Move.currentTarget;

            if(rand.nextDouble() <= zed.getAccuracy()){
                // Store values for message
                double beforeDefense = zed.getDefense();

                // --- Damage Part -------------------------------------------------------------------------------------
                double totalATK = zed.getAttack();
                totalATK += this.attack;

                // Multiply sum to multiplier (20% damage)
                double damage = totalATK * 0.20;

                // Deal the damage
                double actualDamage = target.takeDamage(damage, target.getDefense(), target.getDmgResistance());

                // --- DEF Buff Part -----------------------------------------------------------------------------------
                zed.addDefBuff();

                double afterDefense = zed.getDefense();
                double defenseIncreased = afterDefense - beforeDefense;

                setDamageDealt(actualDamage);
                setBuffAmount(defenseIncreased, "DEF");

                String message = zed.getName() + " used \"" + this.name + "\"";

                // Add damage part if any damage was dealt
                if (actualDamage > 0) {
                    message += " and dealt " + (int)actualDamage + " damage";
                }

                // Add defense buff part
                if (defenseIncreased > 0) {
                    message += ", and increased defense by " + (int)defenseIncreased;
                }

                message += "!";
                setMessage(message);
            } else {
                setDamageDealt(0);
                setMessage(zed.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}