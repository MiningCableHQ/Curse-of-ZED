package Moves.Ranger;

import Moves.Move;

public class Scattershot extends Move {
    public Scattershot() {
        super("Scattershot", 20);
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity){
        //TODO Deal 55% of ATK to all enemies
    }
}
