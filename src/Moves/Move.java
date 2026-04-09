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

    // Store execution results for message display
    protected String lastMessage = "";
    protected double lastDamageDealt = 0;
    protected double lastHealAmount = 0;
    protected double lastBuffAmount = 0;
    protected String lastStatBuffed = "";

    public Move(String name, int attack){
        this.name = name;
        this.attack = attack;
        isDisabled = false;
        disabledCounter = 0;
        this.targetType = TargetType.ENEMY; // Default target type
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

    // Method to get the battle message after execution
    public String getMessage() {
        return lastMessage;
    }

    // Method to get the damage dealt (for enemy moves)
    public double getLastDamageDealt() {
        return lastDamageDealt;
    }

    // Method to get the heal amount (for healing moves)
    public double getLastHealAmount() {
        return lastHealAmount;
    }

    // Protected methods for subclasses to set message
    protected void setMessage(String message) {
        this.lastMessage = message;
    }

    protected void setDamageDealt(double damage) {
        this.lastDamageDealt = damage;
    }

    protected void setHealAmount(double heal) {
        this.lastHealAmount = heal;
    }

    protected void setBuffAmount(double amount, String stat) {
        this.lastBuffAmount = amount;
        this.lastStatBuffed = stat;
    }

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