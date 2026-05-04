package Moves.Enemy3;

import Combat.StatusEffects.Burn;
import Entities.Enemies.Sanjveil;
import Entities.Entity;
import Moves.Move;
import java.util.Random;

public class Move2 extends Move {
    Random rand = new Random();

    public Move2(){
        super("Cinder Veil", 10);
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

                double damage = totalATK * 0.50;
                double actualDamage = target.takeDamage(damage, target.getDefense(), target.getDmgResistance());

                setDamageDealt(actualDamage);
                setMessage(sanjveil.getName() + " used " + this.name + " and dealt " + (int)actualDamage + " damage!");

                target.addStatusEffect(new Burn(1));
            } else {
                setDamageDealt(0);
                setMessage(sanjveil.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}