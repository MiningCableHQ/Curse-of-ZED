package Combat.StatusEffects;

public class Poison extends StatusEffect{
    public Poison(){
        super("Poison");
    }

    @Override
    public <T> void executeEffect(T Entity){
        //TODO 60 hp is reduced per start of turn, lasts 5 entity turns
    }
}
