

package Objects;

import Entities.Characters.NPC_Frankenstein;
import Main.GamePanel;
import Main.GameStateManager;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        // 1. Clear the array
        for (int i = 0; i < gp.obj.length; i++) {
            gp.obj[i] = null;
        }

        // 2. Delegate to map-specific setters
        if (gp.currentMap == 0) {
            new Map1Setter(gp).setObjects();

            // ── Notice Board (Map 1 only) ──────────────────────────
            OBJ_NoticeBoard board = new OBJ_NoticeBoard(gp);
            board.worldX = gp.tileSize * 21;  // adjust to suit your map
            board.worldY = gp.tileSize * 24;
            board.setOnCodeUnlocked(() -> {
                // Triggers the Frankenstein easter egg spawn
                gp.triggerEasterEggFromCode();
            });
            gp.obj[50] = board;

        } else if (gp.currentMap == 1) {
            Map2Setter map2 = new Map2Setter(gp);
            map2.setObjectsPart1();
            map2.setObjectsPart2();



        } else if (gp.currentMap == 2) {
            Map3Setter map3 = new Map3Setter(gp);
            map3.setObjectsPart1();
            map3.setObjectsPart2();
            map3.setObjectsPart3();
        }
    }
}