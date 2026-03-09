package Combat.StatusEffects;

public abstract class StatusEffect {
    protected String name;
    protected String description;

    public StatusEffect(String name){
        this.name = name;
    }

    public abstract <T> void executeEffect(T Entity);
}
