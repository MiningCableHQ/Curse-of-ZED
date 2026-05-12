package Entities.Enemies;

import Main.GamePanel;

public class MapBoss_Zed extends EnemyEntity {

    public MapBoss_Zed(GamePanel gp) {
        super(gp);
        npcName        = "Zed the Sorcerer";
        detectionRange = 160;
        battleRange    = 55;
        chaseSpeed     = 1; // bosses move slower
        loadFrames();
    }

    @Override
    public void loadFrames() {
        for (int i = 0; i < 5; i++) {
            walkLeft[i]  = loadFrame(
                    "/boss/zed_idle/idle_left" + (i + 1) + ".png");
            walkRight[i] = loadFrame(
                    "/boss/zed_idle/idle_left" + (i + 1) + ".png");
        }
        image = walkLeft[0] != null ? walkLeft[0] : null;
    }

    @Override
    public Enemy createBattleEnemy() {
        return new ZED();
    }
}