package Combat.StatusEffects;

public class Slow extends StatusEffect{
    public Slow(){
        super("Slow");
    }

    @Override
    public <T> void executeEffect(T Entity){
        //TODO reduces speed for 3 turns (subject to change / TBA)
    }
}
