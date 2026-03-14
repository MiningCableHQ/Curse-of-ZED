package Objects;

import Main.GamePanel;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }


    public void setObject() {

        // Clear the array so old map objects don't haunt the new map
        for (int i = 0; i < gp.obj.length; i++) {
            gp.obj[i] = null;
        }

        //FOR MAP 2
        if (gp.currentMap == 0) {
            //For Trees
            gp.obj[0] = new OBJ_Tree();
            gp.obj[0].worldX = 10 * gp.tileSize; // Column 10
            gp.obj[0].worldY = 10 * gp.tileSize; // Row 10

            gp.obj[1] = new OBJ_Tree();
            gp.obj[1].worldX = 20 * gp.tileSize; // Column 20
            gp.obj[1].worldY = 15 * gp.tileSize; // Row 15

            //For Rocks
            gp.obj[2] = new OBJ_Rock();
            gp.obj[2].worldX = 24 * gp.tileSize; // Right next to the tree
            gp.obj[2].worldY = 22 * gp.tileSize;
        }
    }
}