package Moves.FinalBoss;

import Entities.Enemies.ZED;
import Entities.Entity;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("So be it.", 0);
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof ZED && Move.currentTarget != null) {
            ZED zed = (ZED) Entity;
            Entity target = Move.currentTarget;

            // --- Damage Part -----------------------------------------------------------------------------------------
            double totalATK = zed.getAttack();
            totalATK += this.attack;

            //multiply sum to multiplier
            double damage = totalATK * 0.20;

            // Deal the damage
            target.takeDamage(damage, target.getDefense(), target.getDmgResistance());

            // --- DEF Buff Part ---------------------------------------------------------------------------------------
            // Store current defense before buffing
            double beforeDefense = zed.getDefense();

            // Add a DEF buff stack
            zed.addDefBuff();

            // Get the new defense after buff
            double afterDefense = zed.getDefense();
            double buffAmount = afterDefense - beforeDefense; //TODO FRANK display this in UI during enemy turn
        }
    }
}