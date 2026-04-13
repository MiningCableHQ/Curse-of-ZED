package Combat.StatusEffects;

public abstract class StatusEffect {
    protected String name;
    protected int duration;
    protected boolean isCycleBased; // true = cycle-based, false = turn-based

    public StatusEffect(String name) {
        this.name = name;
        this.isCycleBased = false;
    }

    public StatusEffect(String name, boolean isCycleBased) {
        this.name = name;
        this.isCycleBased = isCycleBased;
    }

    public abstract <T> void executeEffect(T Entity);

    // Getters and Setters
    public String getName() {
        return name;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void reduceDuration() {
        this.duration--;
    }
    public boolean isExpired() {
        return duration <= 0;
    }
    public boolean isCycleBased() {
        return isCycleBased;
    }

    @Override
    public String toString() {
        if(isCycleBased){
            return name;
        } else {
            return name + " (" + duration + " turns left)";
        }

    }
}