package Combat.StatusEffects;

public class Frozen extends StatusEffect{
    public Frozen(){
        super("Frozen");
    }

    @Override
    public <T> void executeEffect(T Entity){
        //TODO Skips a turn and removes the status
    }
}
