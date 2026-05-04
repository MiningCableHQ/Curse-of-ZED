package Entities.Enemies;

import Main.GamePanel;

public class MapEnemy_Razormaw extends EnemyEntity {

    public MapEnemy_Razormaw(GamePanel gp) {
        super(gp);
        npcName        = "Razormaw";
        detectionRange = 130;
        battleRange    = 50;
        chaseSpeed     = 2;
        loadFrames();
    }

    @Override
    public void loadFrames() {
        for (int i = 0; i < 4; i++) {
            walkLeft[i]  = loadFrame("/enemies/razormaw/walking_left"  + (i + 1) + ".png");
            walkRight[i] = loadFrame("/enemies/razormaw/walking_right" + (i + 1) + ".png");
        }
        image = walkRight[0] != null ? walkRight[0] : null;
    }

    @Override
    public Enemy createBattleEnemy() {
        return new Razormaw();
    }

}