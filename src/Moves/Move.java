package Moves;

import Entities.Entity;

public abstract class Move {
    protected String name;
    protected String description;
    protected double attack;
    protected boolean isDisabled;
    protected int disabledCounter;
    protected boolean hasUnlocked;

    //Current target type shi
    public static Entity currentTarget;

    public Move(String name, int attack){
        this.name = name;
        this.attack = attack;
        isDisabled = false;
        disabledCounter = 0;
    }

    public abstract <T> void execute(T Entity);

    //getters
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public double getAttack() {
        return attack;
    }
}