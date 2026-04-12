package Combat.StatusEffects;

public class Slow extends StatusEffect{
    public Slow(){
        super("Slow");
    }

    @Override
    public <T> void executeEffect(T Entity){
        //TODO skips a turn and removes this status
    }
}
