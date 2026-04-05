package Moves.Frankenstein;

import Entities.Enemies.Frankenstein;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("NAOLNAOLNAOL", 0);
    }

    @Override
    public <T> void execute(T Entity){
        if (Entity instanceof Frankenstein) {
            Frankenstein frankenstein = (Frankenstein) Entity;

            // Heal by 150 HP
            frankenstein.heal(150); //TODO Display in UI
        }
    }
}
