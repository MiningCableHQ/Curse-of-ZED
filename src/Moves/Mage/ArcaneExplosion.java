package Moves.Mage;

import Moves.Move;

public class ArcaneExplosion extends Move {
    public ArcaneExplosion(){
        super("Arcane Bolt", 0); //TODO Implement attack stat
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Deal 48% of ATK to all enemies
    }
}
