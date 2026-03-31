package Moves;

import Entities.Entity;

public abstract class Move {
    protected String name;
    protected String description;
    protected double attack;
    protected boolean isDisabled;
    protected int disabledCounter;
    protected boolean hasUnlocked;

    // Current target type shi
    public static Entity currentTarget;

    // Target type for this move
    protected TargetType targetType;

    public Move(String name, int attack){
        this.name = name;
        this.attack = attack;
        isDisabled = false;
        disabledCounter = 0;
        this.targetType = targetType; // Default target type
    }

    // Overloaded constructor with target type
    public Move(String name, int attack, TargetType targetType){
        this.name = name;
        this.attack = attack;
        isDisabled = false;
        disabledCounter = 0;
        this.targetType = targetType;
    }

    public abstract <T> void execute(T Entity);

    // getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getAttack() {
        return attack;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    // Helper method to check if move targets a single enemy
    public boolean targetsSingleEnemy() {
        return targetType == TargetType.ENEMY;
    }

    // Helper method to check if move targets all enemies
    public boolean targetsAllEnemies() {
        return targetType == TargetType.ALL_ENEMIES;
    }

    // Helper method to check if move targets self
    public boolean targetsSelf() {
        return targetType == TargetType.SELF;
    }


    // ─────────────────────────────────────────────────────────────
    //  Target Type Enum
    // ─────────────────────────────────────────────────────────────
    public enum TargetType {
        ENEMY,        // Targets a single enemy
        ALL_ENEMIES,  // Targets all enemies (AoE attack)
        SELF,         // Targets self (healing/buff)
    }
}