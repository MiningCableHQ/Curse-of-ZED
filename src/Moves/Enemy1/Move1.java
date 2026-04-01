package Moves.Enemy1;

import Entities.Enemies.Masklet;
import Moves.Move;
import Entities.Entity;

public class Move1 extends Move {
    public Move1(){
        super("Spectral Slash", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Masklet && Move.currentTarget != null){
            Masklet masklet = (Masklet) Entity;
            Entity target = Move.currentTarget;

            //Add total atk from enemy and this move
            double totalATK = masklet.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 1.20;

            //Apply the damage
            double actualDamage = target.takeDamage(damage, target.getDefense(), target.getDmgResistance());
        }
    }
}
