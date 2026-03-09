package Combat.StatusEffects;

public class Stun extends StatusEffect{
    public Stun(){
        super("Stun");
    }

    @Override
    public <T> void executeEffect(T Entity){
        //TODO reduces accuracy by 20% for 3 turns
    }
}
