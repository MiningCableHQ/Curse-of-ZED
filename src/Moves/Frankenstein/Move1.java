package Moves.Frankenstein;

import Entities.Enemies.Frankenstein;
import Moves.Move;

public class Move1 extends Move {
    public Move1(){
        super("Move 1", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Frankenstein){
            Frankenstein frankenstein = (Frankenstein) Entity;

            //Add total atk from enemy and this move
            double totalATK = frankenstein.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 0.70;
        }
    }
}
