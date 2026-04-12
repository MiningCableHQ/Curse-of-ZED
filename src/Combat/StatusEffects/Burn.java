package Combat.StatusEffects;

public class Burn extends StatusEffect{
    public Burn(){
        super("Burn");
    }

    @Override
    public <T> void executeEffect(T Entity){
        //TODO reduces HP by 100 - 300 once, lasts one turn
    }
}
