package Objects;

import Main.GamePanel;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

//To put layers here
    public void setObject() {

        // Clear the array so old map objects don't haunt the new map
        for (int i = 0; i < gp.obj.length; i++) {
            gp.obj[i] = null;
        }

        //FOR MAP 2
        if (gp.currentMap == 0) {
            //For Trees
            gp.obj[0] = new OBJ_BlueFlower();
            gp.obj[0].worldX = 1 * gp.tileSize; // Column 10
            gp.obj[0].worldY = 1 * gp.tileSize; // Row 10

            gp.obj[1] = new OBJ_RedFlower();
            gp.obj[1].worldX = 2 * gp.tileSize; // Column 20
            gp.obj[1].worldY = 2 * gp.tileSize; // Row 15

            //For Rocks
            gp.obj[2] = new OBJ_Flower();
            gp.obj[2].worldX = 3 * gp.tileSize; // Right next to the tree
            gp.obj[2].worldY = 3 * gp.tileSize;

            gp.obj[3] = new OBJ_BlueHouse();
            gp.obj[3].worldX = 7 * gp.tileSize; // Column 20
            gp.obj[3].worldY = 7 * gp.tileSize; // Row 15

            gp.obj[4] = new OBJ_RedHouse();
            gp.obj[4].worldX = 10 * gp.tileSize; // Column 20
            gp.obj[4].worldY = 10 * gp.tileSize; // Row 15

            gp.obj[5] = new OBJ_GreenTree();
            gp.obj[5].worldX = 13 * gp.tileSize; // Column 20
            gp.obj[5].worldY = 13 * gp.tileSize; // Row 15

            gp.obj[6] = new OBJ_TreeOrange();
            gp.obj[6].worldX = 15 * gp.tileSize; // Column 20
            gp.obj[6].worldY = 15 * gp.tileSize; // Row 15

            gp.obj[7] = new OBJ_TreeYellow();
            gp.obj[7].worldX = 18 * gp.tileSize; // Column 20
            gp.obj[7].worldY = 18 * gp.tileSize; // Row 15

            gp.obj[8] = new OBJ_Rocks();
            gp.obj[8].worldX = 21 * gp.tileSize; // Column 20
            gp.obj[8].worldY = 21 * gp.tileSize; // Row 15

            gp.obj[9] = new OBJ_ShopBlue();
            gp.obj[9].worldX = 23 * gp.tileSize; // Column 20
            gp.obj[9].worldY = 23 * gp.tileSize; // Row 15


            gp.obj[10] = new OBJ_ShopRed();
            gp.obj[10].worldX = 26 * gp.tileSize; // Column 20
            gp.obj[10].worldY = 26 * gp.tileSize; // Row 15

            gp.obj[11] = new OBJ_SmallShop();
            gp.obj[11].worldX = 29 * gp.tileSize; // Column 20
            gp.obj[11].worldY = 29 * gp.tileSize; // Row 15

            gp.obj[12] = new OBJ_Gate();
            gp.obj[12].worldX = 31 * gp.tileSize; // Column 20
            gp.obj[12].worldY = 31 * gp.tileSize; // Row 15

        }
    }
}