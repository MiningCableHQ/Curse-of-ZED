package Moves.Frankenstein;

import Entities.Enemies.Frankenstein;
import Moves.Move;

public class Move3 extends Move {
    public Move3(){
        super("Move 3", 10);
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Frankenstein){
            Frankenstein frankenstein = (Frankenstein) Entity;

            //Add total atk from enemy and this move
            double totalATK = frankenstein.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 2;
            //TODO 30% chance to inflict stun, not stackable, no cooldown
        }
    }
}
