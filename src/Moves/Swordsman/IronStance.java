package Moves.Swordsman;

import Moves.Move;

public class IronStance extends Move {
    public IronStance(){
        super("Iron Stance", 0); //TODO implement attack stat
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Increases DEF by 8% (max of 3 stacks)
    }
}
