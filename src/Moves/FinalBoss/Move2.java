package Moves.FinalBoss;

import Entities.Enemies.ZED;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("So be it.", 0);
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof ZED) {
            ZED zed = (ZED) Entity;

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