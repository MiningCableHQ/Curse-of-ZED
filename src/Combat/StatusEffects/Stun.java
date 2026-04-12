package Combat.StatusEffects;

public class Stun extends StatusEffect{
    public Stun(){
        super("Stun");
    }

    @Override
    public <T> void executeEffect(T Entity){
        //TODO reduces entity accuracy by 10% for 1 cycle
    }
}
