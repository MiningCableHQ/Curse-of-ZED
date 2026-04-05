package Moves.Boss1;

import Entities.Enemies.Thorncrusher;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("Root Recovery", 0);
    }

    @Override
    public <T> void execute(T Entity){
        if (Entity instanceof Thorncrusher) {
            Thorncrusher thorncrusher = (Thorncrusher) Entity;

            // Heal by 100 HP
            thorncrusher.heal(100); //TODO Display in UI
        }
    }
}
