package Moves.Boss1;

import Entities.Enemies.Thorncrusher;
import Entities.Entity;
import Moves.Move;

public class Move3 extends Move {
    public Move3(){
        super("Crushing Blow", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Thorncrusher && Move.currentTarget != null){
            Thorncrusher thorncrusher = (Thorncrusher) Entity;
            Entity target = Move.currentTarget;

            //Add total atk from enemy and this move
            double totalATK = thorncrusher.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 3;

            target.takeDamage(damage, target.getDefense(), target.getDmgResistance());
        }
    }
}
