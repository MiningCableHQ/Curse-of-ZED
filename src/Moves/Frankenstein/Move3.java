package Moves.Frankenstein;

import Entities.Enemies.Frankenstein;
import Entities.Entity;
import Moves.Move;

public class Move3 extends Move {
    public Move3(){
        super("LAMI", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Frankenstein && Move.currentTarget != null){
            Frankenstein frankenstein = (Frankenstein) Entity;
            Entity target = Move.currentTarget;

            //Add total atk from enemy and this move
            double totalATK = frankenstein.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 2;

            target.takeDamage(damage, target.getDefense(), target.getDmgResistance());
            //TODO FRANK 30% chance to inflict stun, not stackable
        }
    }
}
