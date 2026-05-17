package Entities.Enemies;

import Main.GamePanel;

public class MapEnemy_Reyven extends EnemyEntity {

    public MapEnemy_Reyven(GamePanel gp) {
        super(gp);
        npcName        = "Reyven";
        detectionRange = 135;
        battleRange    = 50;
        chaseSpeed     = 2;
        loadFrames();
    }

    @Override
    public void loadFrames() {
        for (int i = 0; i < 4; i++) {
            walkLeft[i]  = loadFrame("/enemies/reyven/reyven_walking/walking_left"  + (i + 1) + ".png");
            walkRight[i] = loadFrame("/enemies/reyven/reyven_walking/walking_right" + (i + 1) + ".png");
        }
        image = walkRight[0] != null ? walkRight[0] : null;
    }

    @Override
    public Enemy createBattleEnemy() {
        return new Reyven();
    }
}