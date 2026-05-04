package Entities.Enemies;

import Main.GamePanel;

public class MapBoss_Thorncrusher extends EnemyEntity {

    public MapBoss_Thorncrusher(GamePanel gp) {
        super(gp);
        npcName        = "Thorncrusher";
        detectionRange = 160;
        battleRange    = 55;
        chaseSpeed     = 1;
        loadFrames();
    }

    @Override
    public void loadFrames() {
        // 5 frames, all idle_left (boss uses same frames both directions)
        for (int i = 0; i < 5; i++) {
            walkLeft[i]  = loadFrame(
                    "/boss/thorncrusher_idle/idle_left" + (i + 1) + ".png");
            walkRight[i] = loadFrame(
                    "/boss/thorncrusher_idle/idle_left" + (i + 1) + ".png");
        }
        image = walkLeft[0] != null ? walkLeft[0] : null;
    }

    @Override
    public Enemy createBattleEnemy() {
        return new Thorncrusher(); // your existing Thorncrusher boss class
    }
}