package Moves.Swordsman;

import Moves.Move;

public class HeroicSlash extends Move {
    public HeroicSlash(){
        super("Heroic Slash", 20);
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity) {
        //TODO Deal 160% of ATK to a single target
    }
}
