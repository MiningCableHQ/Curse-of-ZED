package Moves;

public abstract class Move {
    protected String name;
    protected String description;
    protected int attack;
    protected boolean isDisabled;
    protected int disabledCounter;
    protected boolean hasUnlocked;

    public Move(String name, int attack){
        isDisabled = false;
        disabledCounter = 0;
    }

    public abstract <T> void execute(T Entity);
}
