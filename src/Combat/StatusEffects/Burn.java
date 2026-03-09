package Combat.StatusEffects;

public class Burn extends StatusEffect{
    public Burn(){
        super("Burn");
    }

    @Override
    public <T> void executeEffect(T Entity){
        //TODO reduces HP by 10% - 20% once
    }
}
