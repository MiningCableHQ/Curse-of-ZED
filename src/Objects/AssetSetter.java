package Objects;

import Main.GamePanel;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        // 1. Clear the array (Keep this here)
        for (int i = 0; i < gp.obj.length; i++) {
            gp.obj[i] = null;
        }

        // 2. Delegate to the new classes
        if (gp.currentMap == 0) {
            new Map1Setter(gp).setObjects();
        } else if (gp.currentMap == 1) {
            Map2Setter map2  = new Map2Setter(gp);
            map2.setObjectsPart1();
            map2.setObjectsPart2();

        } else if (gp.currentMap == 2) {
            // This is where you call the new Map 3 class
            Map3Setter map3 = new Map3Setter(gp);
            map3.setObjectsPart1();
            map3.setObjectsPart2();
        }
    }
}