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
            // Trees
            gp.obj[0] = new OBJ_Tree();
            gp.obj[0].worldX = 4 * gp.tileSize; // Col 4
            gp.obj[0].worldY = 1 * gp.tileSize; // Row 1

            gp.obj[1] = new OBJ_Tree();
            gp.obj[1].worldX = 33 * gp.tileSize; // Col 33
            gp.obj[1].worldY = 1 * gp.tileSize; // Row 1

            gp.obj[2] = new OBJ_Tree();
            gp.obj[2].worldX = 39 * gp.tileSize; // Col 39
            gp.obj[2].worldY = 1 * gp.tileSize; // Row 1

            gp.obj[3] = new OBJ_Tree();
            gp.obj[3].worldX = 12 * gp.tileSize; // Col 12
            gp.obj[3].worldY = 3 * gp.tileSize; // Row 3

            gp.obj[4] = new OBJ_Tree();
            gp.obj[4].worldX = 28 * gp.tileSize; // Col 28
            gp.obj[4].worldY = 3 * gp.tileSize; // Row 3

            gp.obj[5] = new OBJ_Tree();
            gp.obj[5].worldX = 3 * gp.tileSize; // Col 3
            gp.obj[5].worldY = 4 * gp.tileSize; // Row 4

            gp.obj[6] = new OBJ_Tree();
            gp.obj[6].worldX = 7 * gp.tileSize; // Col 7
            gp.obj[6].worldY = 4 * gp.tileSize; // Row 4

            gp.obj[7] = new OBJ_Tree();
            gp.obj[7].worldX = 43 * gp.tileSize; // Col 43
            gp.obj[7].worldY = 4 * gp.tileSize; // Row 4

            gp.obj[8] = new OBJ_Tree();
            gp.obj[8].worldX = 48 * gp.tileSize; // Col 48
            gp.obj[8].worldY = 4 * gp.tileSize; // Row 4

            gp.obj[9] = new OBJ_Tree();
            gp.obj[9].worldX = 33 * gp.tileSize; // Col 33
            gp.obj[9].worldY = 5 * gp.tileSize; // Row 5

            gp.obj[10] = new OBJ_Tree();
            gp.obj[10].worldX = 37 * gp.tileSize; // Col 37
            gp.obj[10].worldY = 5 * gp.tileSize; // Row 5

            gp.obj[11] = new OBJ_Tree();
            gp.obj[11].worldX = 12 * gp.tileSize; // Col 12
            gp.obj[11].worldY = 6 * gp.tileSize; // Row 6

            gp.obj[12] = new OBJ_Tree();
            gp.obj[12].worldX = 28 * gp.tileSize; // Col 28
            gp.obj[12].worldY = 6 * gp.tileSize; // Row 6

            gp.obj[13] = new OBJ_Tree();
            gp.obj[13].worldX = 1 * gp.tileSize; // Col 1
            gp.obj[13].worldY = 8 * gp.tileSize; // Row 8

            gp.obj[14] = new OBJ_Tree();
            gp.obj[14].worldX = 5 * gp.tileSize; // Col 5
            gp.obj[14].worldY = 8 * gp.tileSize; // Row 8

            gp.obj[15] = new OBJ_Tree();
            gp.obj[15].worldX = 8 * gp.tileSize; // Col 8
            gp.obj[15].worldY = 8 * gp.tileSize; // Row 8

            gp.obj[16] = new OBJ_Tree();
            gp.obj[16].worldX = 12 * gp.tileSize; // Col 12
            gp.obj[16].worldY = 9 * gp.tileSize; // Row 9

            gp.obj[17] = new OBJ_Tree();
            gp.obj[17].worldX = 28 * gp.tileSize; // Col 28
            gp.obj[17].worldY = 9 * gp.tileSize; // Row 9

            gp.obj[18] = new OBJ_Tree();
            gp.obj[18].worldX = 33 * gp.tileSize; // Col 33
            gp.obj[18].worldY = 9 * gp.tileSize; // Row 9

            gp.obj[19] = new OBJ_Tree();
            gp.obj[19].worldX = 36 * gp.tileSize; // Col 36
            gp.obj[19].worldY = 9 * gp.tileSize; // Row 9

            gp.obj[20] = new OBJ_Tree();
            gp.obj[20].worldX = 40 * gp.tileSize; // Col 40
            gp.obj[20].worldY = 9 * gp.tileSize; // Row 9

            gp.obj[21] = new OBJ_Tree();
            gp.obj[21].worldX = 46 * gp.tileSize; // Col 46
            gp.obj[21].worldY = 9 * gp.tileSize; // Row 9

            gp.obj[22] = new OBJ_Tree();
            gp.obj[22].worldX = 1 * gp.tileSize; // Col 1
            gp.obj[22].worldY = 16 * gp.tileSize; // Row 16

            gp.obj[23] = new OBJ_Tree();
            gp.obj[23].worldX = 5 * gp.tileSize; // Col 5
            gp.obj[23].worldY = 16 * gp.tileSize; // Row 16

            gp.obj[24] = new OBJ_Tree();
            gp.obj[24].worldX = 8 * gp.tileSize; // Col 8
            gp.obj[24].worldY = 16 * gp.tileSize; // Row 16

            gp.obj[25] = new OBJ_Tree();
            gp.obj[25].worldX = 16 * gp.tileSize; // Col 16
            gp.obj[25].worldY = 16 * gp.tileSize; // Row 16

            gp.obj[26] = new OBJ_Tree();
            gp.obj[26].worldX = 20 * gp.tileSize; // Col 20
            gp.obj[26].worldY = 16 * gp.tileSize; // Row 16

            gp.obj[27] = new OBJ_Tree();
            gp.obj[27].worldX = 24 * gp.tileSize; // Col 24
            gp.obj[27].worldY = 16 * gp.tileSize; // Row 16

            gp.obj[28] = new OBJ_Tree();
            gp.obj[28].worldX = 32 * gp.tileSize; // Col 32
            gp.obj[28].worldY = 16 * gp.tileSize; // Row 16

            gp.obj[29] = new OBJ_Tree();
            gp.obj[29].worldX = 37 * gp.tileSize; // Col 37
            gp.obj[29].worldY = 16 * gp.tileSize; // Row 16

            gp.obj[30] = new OBJ_Tree();
            gp.obj[30].worldX = 42 * gp.tileSize; // Col 42
            gp.obj[30].worldY = 16 * gp.tileSize; // Row 16

            gp.obj[31] = new OBJ_Tree();
            gp.obj[31].worldX = 47 * gp.tileSize; // Col 47
            gp.obj[31].worldY = 16 * gp.tileSize; // Row 16

            gp.obj[32] = new OBJ_Tree();
            gp.obj[32].worldX = 1 * gp.tileSize; // Col 1
            gp.obj[32].worldY = 30 * gp.tileSize; // Row 30

            gp.obj[33] = new OBJ_Tree();
            gp.obj[33].worldX = 5 * gp.tileSize; // Col 5
            gp.obj[33].worldY = 30 * gp.tileSize; // Row 30

            gp.obj[34] = new OBJ_Tree();
            gp.obj[34].worldX = 8 * gp.tileSize; // Col 8
            gp.obj[34].worldY = 30 * gp.tileSize; // Row 30

            gp.obj[35] = new OBJ_Tree();
            gp.obj[35].worldX = 17 * gp.tileSize; // Col 17
            gp.obj[35].worldY = 30 * gp.tileSize; // Row 30

            gp.obj[36] = new OBJ_Tree();
            gp.obj[36].worldX = 22 * gp.tileSize; // Col 22
            gp.obj[36].worldY = 30 * gp.tileSize; // Row 30

            gp.obj[37] = new OBJ_Tree();
            gp.obj[37].worldX = 34 * gp.tileSize; // Col 34
            gp.obj[37].worldY = 30 * gp.tileSize; // Row 30

            gp.obj[38] = new OBJ_Tree();
            gp.obj[38].worldX = 40 * gp.tileSize; // Col 40
            gp.obj[38].worldY = 30 * gp.tileSize; // Row 30

            gp.obj[39] = new OBJ_Tree();
            gp.obj[39].worldX = 44 * gp.tileSize; // Col 44
            gp.obj[39].worldY = 30 * gp.tileSize; // Row 30

            gp.obj[40] = new OBJ_Tree();
            gp.obj[40].worldX = 47 * gp.tileSize; // Col 47
            gp.obj[40].worldY = 32 * gp.tileSize; // Row 32

            gp.obj[41] = new OBJ_Tree();
            gp.obj[41].worldX = 7 * gp.tileSize; // Col 7
            gp.obj[41].worldY = 34 * gp.tileSize; // Row 34

            gp.obj[42] = new OBJ_Tree();
            gp.obj[42].worldX = 16 * gp.tileSize; // Col 16
            gp.obj[42].worldY = 34 * gp.tileSize; // Row 34

            gp.obj[43] = new OBJ_Tree();
            gp.obj[43].worldX = 21 * gp.tileSize; // Col 21
            gp.obj[43].worldY = 34 * gp.tileSize; // Row 34

            gp.obj[44] = new OBJ_Tree();
            gp.obj[44].worldX = 32 * gp.tileSize; // Col 32
            gp.obj[44].worldY = 34 * gp.tileSize; // Row 34

            gp.obj[45] = new OBJ_Tree();
            gp.obj[45].worldX = 23 * gp.tileSize; // Col 23
            gp.obj[45].worldY = 35 * gp.tileSize; // Row 35

            gp.obj[46] = new OBJ_Tree();
            gp.obj[46].worldX = 37 * gp.tileSize; // Col 37
            gp.obj[46].worldY = 36 * gp.tileSize; // Row 36

            gp.obj[47] = new OBJ_Tree();
            gp.obj[47].worldX = 5 * gp.tileSize; // Col 5
            gp.obj[47].worldY = 37 * gp.tileSize; // Row 37

            gp.obj[48] = new OBJ_Tree();
            gp.obj[48].worldX = 44 * gp.tileSize; // Col 44
            gp.obj[48].worldY = 37 * gp.tileSize; // Row 37

            gp.obj[49] = new OBJ_Tree();
            gp.obj[49].worldX = 47 * gp.tileSize; // Col 47
            gp.obj[49].worldY = 39 * gp.tileSize; // Row 39

            gp.obj[50] = new OBJ_Tree();
            gp.obj[50].worldX = 23 * gp.tileSize; // Col 23
            gp.obj[50].worldY = 40 * gp.tileSize; // Row 40

            gp.obj[51] = new OBJ_Tree();
            gp.obj[51].worldX = 32 * gp.tileSize; // Col 32
            gp.obj[51].worldY = 40 * gp.tileSize; // Row 40

            gp.obj[52] = new OBJ_Tree();
            gp.obj[52].worldX = 7 * gp.tileSize; // Col 7
            gp.obj[52].worldY = 41 * gp.tileSize; // Row 41

            gp.obj[53] = new OBJ_Tree();
            gp.obj[53].worldX = 16 * gp.tileSize; // Col 16
            gp.obj[53].worldY = 41 * gp.tileSize; // Row 41

            gp.obj[54] = new OBJ_Tree();
            gp.obj[54].worldX = 21 * gp.tileSize; // Col 21
            gp.obj[54].worldY = 41 * gp.tileSize; // Row 41

            gp.obj[55] = new OBJ_Tree();
            gp.obj[55].worldX = 44 * gp.tileSize; // Col 44
            gp.obj[55].worldY = 42 * gp.tileSize; // Row 42

            gp.obj[56] = new OBJ_Tree();
            gp.obj[56].worldX = 35 * gp.tileSize; // Col 35
            gp.obj[56].worldY = 43 * gp.tileSize; // Row 43

            gp.obj[57] = new OBJ_Tree();
            gp.obj[57].worldX = 48 * gp.tileSize; // Col 48
            gp.obj[57].worldY = 45 * gp.tileSize; // Row 45

            gp.obj[58] = new OBJ_Tree();
            gp.obj[58].worldX = 32 * gp.tileSize; // Col 32
            gp.obj[58].worldY = 46 * gp.tileSize; // Row 46

        }

            //For Rocks
            gp.obj[1] = new OBJ_Rock();
            gp.obj[1].worldX = 24 * gp.tileSize; // Right next to the tree
            gp.obj[1].worldY = 22 * gp.tileSize;
        }
    }