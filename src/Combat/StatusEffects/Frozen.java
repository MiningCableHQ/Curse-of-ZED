package Combat.StatusEffects;

public class Frozen extends StatusEffect{
    public Frozen(){
        super("Frozen");
    }

    @Override
    public <T> void executeEffect(T Entity){
        //TODO Cannot move until next cycle, then removes this status
    }
}
