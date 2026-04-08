package Moves.Enemy3;

import Entities.Enemies.Sanjveil;
import Entities.Entity;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("Cinder Veil", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Sanjveil && Move.currentTarget != null){
            Sanjveil sanjveil = (Sanjveil) Entity;
            Entity target = Move.currentTarget;

            // Add total atk from enemy and this move
            double totalATK = sanjveil.getAttack();
            totalATK += this.attack;

            double damage = totalATK * 0.50;
            double actualDamage = target.takeDamage(damage, target.getDefense(), target.getDmgResistance());

            setDamageDealt(actualDamage);
            setMessage(sanjveil.getName() + " used " + this.name + " and dealt " + (int)actualDamage + " damage!");
            //TODO FRANK guaranteed inflict burn
        }
    }
}