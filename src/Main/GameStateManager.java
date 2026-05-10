


package Main;

public class GameStateManager {

    public enum Map1Phase {
        TALK_TO_CHIEF,
        TALK_TO_RANGER,
        RECEIVE_WEAPON,
        FIGHT_ENEMIES,
        FIGHT_BOSS,
        COMPLETE,
        COLLECT_ESSENCE
    }

    public Map1Phase map1Phase = Map1Phase.TALK_TO_CHIEF;

    // ── Map 1 ──
    public boolean rangerDialogueDone            = false;
    public boolean weaponReceived                = false;
    public int     map1EnemiesDefeated           = 0;
    public boolean map1EnemiesDefeated_masklet   = false;
    public boolean map1EnemiesDefeated_zenzilla  = false;
    public boolean map1BossDefeated              = false;
    public boolean map1BossSpawned               = false;

    // ── Map 2 ──
    public boolean map2IntroShown                = false;
    public boolean map2EnemiesDefeated_run1      = false;
    public int     map2EnemiesDefeated           = 0;
    public boolean map2EnemiesDefeated_sanjveil  = false;
    public boolean map2EnemiesDefeated_razormaw  = false;
    public boolean easterEggFound                = false;
    public boolean easterEggBossDropped          = false;
    public boolean map2BossDefeated              = false;
    public boolean map2BossSpawned               = false;
    public boolean throneRoomCutsceneDone        = false;
    public boolean[] essenceCollected            = new boolean[5];
    public int     essenceCount                  = 0;
    public boolean easterEggUnlockedByCode = false;

    // Map 2 revisit (after PostDefeatCutscene returns player to Map 1)
    public boolean isMap2Revisit                 = false;
    public int     map2RevisitEnemiesDefeated    = 0;
    public boolean map2RevisitCleared            = false;

    // ── Map 3 ──
    public boolean map3EnemyDefeated             = false;  // Reyven
    public boolean map3BossDefeated              = false;  // Zed final
    public boolean map3BossSpawned               = false;
    public int     map3EnemiesDefeated           = 0;

    // ── Global ──
    public boolean gameWon                       = false;
    public boolean gameLost                      = false;

    // ── Essence helpers ──
    public void collectEssence(int npcIndex) {
        if (npcIndex >= 0 && npcIndex < essenceCollected.length
                && !essenceCollected[npcIndex]) {
            essenceCollected[npcIndex] = true;
            essenceCount++;
        }
    }

    public boolean allEssenceCollected() {
        return essenceCount >= 5;
    }

    public int getTotalEssence() { return essenceCount; }

    // ── Singleton ──
    private static GameStateManager instance;

    public static GameStateManager get() {
        if (instance == null) instance = new GameStateManager();
        return instance;
    }

    public static void reset() {
        instance = new GameStateManager();
    }
}