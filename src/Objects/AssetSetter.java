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
            gp.obj[0].worldX = 7 * gp.tileSize; // Column 10
            gp.obj[0].worldY = 6 * gp.tileSize; // Row 10

            gp.obj[1] = new OBJ_BlueFlower();
            gp.obj[1].worldX = 8 * gp.tileSize; // Column 10
            gp.obj[1].worldY = 6 * gp.tileSize; // Row 10

            gp.obj[2] = new OBJ_BlueFlower();
            gp.obj[2].worldX = 9 * gp.tileSize; // Column 10
            gp.obj[2].worldY = 6 * gp.tileSize; // Row 10

            gp.obj[3] = new OBJ_Flower();
            gp.obj[3].worldX = 7 * gp.tileSize; // Column 10
            gp.obj[3].worldY = 8 * gp.tileSize; // Row 10

            gp.obj[4] = new OBJ_Flower();
            gp.obj[4].worldX = 8 * gp.tileSize; // Column 10
            gp.obj[4].worldY = 8 * gp.tileSize; // Row 10

            gp.obj[5] = new OBJ_Flower();
            gp.obj[5].worldX = 9 * gp.tileSize; // Column 10
            gp.obj[5].worldY = 8 * gp.tileSize; // Row 10

            gp.obj[6] = new OBJ_RedFlower();
            gp.obj[6].worldX = 7 * gp.tileSize; // Column 10
            gp.obj[6].worldY = 10 * gp.tileSize; // Row 10

            gp.obj[7] = new OBJ_RedFlower();
            gp.obj[7].worldX = 8 * gp.tileSize; // Column 10
            gp.obj[7].worldY = 10 * gp.tileSize; // Row 10

            gp.obj[8] = new OBJ_RedFlower();
            gp.obj[8].worldX = 9 * gp.tileSize; // Column 10
            gp.obj[8].worldY = 10 * gp.tileSize; // Row 10

            gp.obj[9] = new OBJ_BlueFlower();
            gp.obj[9].worldX = 7* gp.tileSize; // Column 10
            gp.obj[9].worldY = 12 * gp.tileSize; // Row 10

            gp.obj[10] = new OBJ_BlueFlower();
            gp.obj[10].worldX = 8 * gp.tileSize; // Column 10
            gp.obj[10].worldY = 12 * gp.tileSize; // Row 10

            gp.obj[11] = new OBJ_BlueFlower();
            gp.obj[11].worldX = 9 * gp.tileSize; // Column 10
            gp.obj[11].worldY = 12 * gp.tileSize; // Row 10

            gp.obj[12] = new OBJ_Flower();
            gp.obj[12].worldX = 7 * gp.tileSize; // Column 10
            gp.obj[12].worldY = 14 * gp.tileSize; // Row 10

            gp.obj[13] = new OBJ_Flower();
            gp.obj[13].worldX = 8 * gp.tileSize; // Column 10
            gp.obj[13].worldY = 14 * gp.tileSize; // Row 10

            gp.obj[14] = new OBJ_Flower();
            gp.obj[14].worldX = 9 * gp.tileSize; // Column 10
            gp.obj[14].worldY = 14 * gp.tileSize; // Row 10

            gp.obj[15] = new OBJ_RedFlower();
            gp.obj[15].worldX = 7 * gp.tileSize; // Column 10
            gp.obj[15].worldY = 16 * gp.tileSize; // Row 10

            gp.obj[16] = new OBJ_RedFlower();
            gp.obj[16].worldX = 8 * gp.tileSize; // Column 10
            gp.obj[16].worldY = 16 * gp.tileSize; // Row 10

            gp.obj[17] = new OBJ_RedFlower();
            gp.obj[17].worldX = 9 * gp.tileSize; // Column 10
            gp.obj[17].worldY = 16 * gp.tileSize; // Row 10

            gp.obj[18] = new OBJ_BlueFlower();
            gp.obj[18].worldX = 7 * gp.tileSize; // Column 10
            gp.obj[18].worldY = 18 * gp.tileSize; // Row 10

            gp.obj[19] = new OBJ_BlueFlower();
            gp.obj[19].worldX = 8 * gp.tileSize; // Column 10
            gp.obj[19].worldY = 18 * gp.tileSize; // Row 10

            gp.obj[20] = new OBJ_BlueFlower();
            gp.obj[20].worldX = 9 * gp.tileSize; // Column 10
            gp.obj[20].worldY = 18 * gp.tileSize; // Row 10

            gp.obj[21] = new OBJ_Flower();
            gp.obj[21].worldX = 7 * gp.tileSize; // Column 10
            gp.obj[21].worldY = 20 * gp.tileSize; // Row 10

            gp.obj[22] = new OBJ_Flower();
            gp.obj[22].worldX = 8 * gp.tileSize; // Column 10
            gp.obj[22].worldY = 20 * gp.tileSize; // Row 10

            gp.obj[23] = new OBJ_Flower();
            gp.obj[23].worldX = 9 * gp.tileSize; // Column 10
            gp.obj[23].worldY = 20 * gp.tileSize; // Row 10

            gp.obj[24] = new OBJ_RedFlower();
            gp.obj[24].worldX = 7 * gp.tileSize; // Column 10
            gp.obj[24].worldY = 22 * gp.tileSize; // Row 10

            gp.obj[25] = new OBJ_RedFlower();
            gp.obj[25].worldX = 8 * gp.tileSize; // Column 10
            gp.obj[25].worldY = 22 * gp.tileSize; // Row 10

            gp.obj[26] = new OBJ_RedFlower();
            gp.obj[26].worldX = 9 * gp.tileSize; // Column 10
            gp.obj[26].worldY = 22 * gp.tileSize; // Row 10

            gp.obj[27] = new OBJ_BlueFlower();
            gp.obj[27].worldX = 14 * gp.tileSize; // Column 10
            gp.obj[27].worldY = 6 * gp.tileSize; // Row 10

            gp.obj[28] = new OBJ_Flower();
            gp.obj[28].worldX = 15 * gp.tileSize; // Column 10
            gp.obj[28].worldY = 6 * gp.tileSize; // Row 10

            gp.obj[29] = new OBJ_RedFlower();
            gp.obj[29].worldX = 16 * gp.tileSize; // Column 10
            gp.obj[29].worldY = 6 * gp.tileSize; // Row 10

            gp.obj[30] = new OBJ_BlueFlower();
            gp.obj[30].worldX = 17 * gp.tileSize; // Column 10
            gp.obj[30].worldY = 6 * gp.tileSize; // Row 10

            gp.obj[31] = new OBJ_Flower();
            gp.obj[31].worldX = 18 * gp.tileSize; // Column 10
            gp.obj[31].worldY = 6 * gp.tileSize; // Row 10

            gp.obj[32] = new OBJ_RedFlower();
            gp.obj[32].worldX = 19 * gp.tileSize; // Column 10
            gp.obj[32].worldY = 6 * gp.tileSize; // Row 10

            gp.obj[33] = new OBJ_BlueFlower();
            gp.obj[33].worldX = 14 * gp.tileSize; // Column 10
            gp.obj[33].worldY = 7 * gp.tileSize; // Row 10

            gp.obj[34] = new OBJ_Flower();
            gp.obj[34].worldX = 15 * gp.tileSize; // Column 10
            gp.obj[34].worldY = 7 * gp.tileSize; // Row 10

            gp.obj[35] = new OBJ_RedFlower();
            gp.obj[35].worldX = 16 * gp.tileSize; // Column 10
            gp.obj[35].worldY = 7 * gp.tileSize; // Row 10

            gp.obj[36] = new OBJ_BlueFlower();
            gp.obj[36].worldX = 17 * gp.tileSize; // Column 10
            gp.obj[36].worldY = 7 * gp.tileSize; // Row 10

            gp.obj[37] = new OBJ_Flower();
            gp.obj[37].worldX = 18 * gp.tileSize; // Column 10
            gp.obj[37].worldY = 7 * gp.tileSize; // Row 10

            gp.obj[38] = new OBJ_RedFlower();
            gp.obj[38].worldX = 19 * gp.tileSize; // Column 10
            gp.obj[38].worldY = 7 * gp.tileSize; // Row 10

            gp.obj[39] = new OBJ_BlueFlower();
            gp.obj[39].worldX = 14 * gp.tileSize; // Column 10
            gp.obj[39].worldY = 8 * gp.tileSize; // Row 10

            gp.obj[40] = new OBJ_Flower();
            gp.obj[40].worldX = 15 * gp.tileSize; // Column 10
            gp.obj[40].worldY = 8 * gp.tileSize; // Row 10

            gp.obj[41] = new OBJ_RedFlower();
            gp.obj[41].worldX = 16 * gp.tileSize; // Column 10
            gp.obj[41].worldY = 8 * gp.tileSize; // Row 10

            gp.obj[42] = new OBJ_BlueFlower();
            gp.obj[42].worldX = 17 * gp.tileSize; // Column 10
            gp.obj[42].worldY = 8 * gp.tileSize; // Row 10

            gp.obj[43] = new OBJ_Flower();
            gp.obj[43].worldX = 18 * gp.tileSize; // Column 10
            gp.obj[43].worldY = 8 * gp.tileSize; // Row 10

            gp.obj[44] = new OBJ_RedFlower();
            gp.obj[44].worldX = 19 * gp.tileSize; // Column 10
            gp.obj[44].worldY = 8 * gp.tileSize; // Row 10

            gp.obj[45] = new OBJ_BlueFlower();
            gp.obj[45].worldX = 14 * gp.tileSize; // Column 10
            gp.obj[45].worldY = 9 * gp.tileSize; // Row 10

            gp.obj[46] = new OBJ_Flower();
            gp.obj[46].worldX = 15 * gp.tileSize; // Column 10
            gp.obj[46].worldY = 9 * gp.tileSize; // Row 10

            gp.obj[47] = new OBJ_RedFlower();
            gp.obj[47].worldX = 16 * gp.tileSize; // Column 10
            gp.obj[47].worldY = 9 * gp.tileSize; // Row 10

            gp.obj[48] = new OBJ_BlueFlower();
            gp.obj[48].worldX = 17 * gp.tileSize; // Column 10
            gp.obj[48].worldY = 9 * gp.tileSize; // Row 10

            gp.obj[49] = new OBJ_Flower();
            gp.obj[49].worldX = 18 * gp.tileSize; // Column 10
            gp.obj[49].worldY = 9 * gp.tileSize; // Row 10

            gp.obj[50] = new OBJ_RedFlower();
            gp.obj[50].worldX = 19 * gp.tileSize; // Column 10
            gp.obj[50].worldY = 9 * gp.tileSize; // Row 10

            gp.obj[51] = new OBJ_ShopBlue();
            gp.obj[51].worldX = 4 * gp.tileSize; // Column 10
            gp.obj[51].worldY = 26 * gp.tileSize; // Row 10

            gp.obj[52] = new OBJ_SmallShop();
            gp.obj[52].worldX = 14 * gp.tileSize; // Column 10
            gp.obj[52].worldY = 27 * gp.tileSize; // Row 10

            gp.obj[53] = new OBJ_SmallShop();
            gp.obj[53].worldX = 17 * gp.tileSize; // Column 10
            gp.obj[53].worldY = 27 * gp.tileSize; // Row 10

            gp.obj[54] = new OBJ_ShopRed();
            gp.obj[54].worldX = 15 * gp.tileSize; // Column 10
            gp.obj[54].worldY = 33 * gp.tileSize; // Row 10

            gp.obj[55] = new OBJ_SmallShop();
            gp.obj[55].worldX = 2 * gp.tileSize; // Column 10
            gp.obj[55].worldY = 33 * gp.tileSize; // Row 10

            gp.obj[56] = new OBJ_SmallShop();
            gp.obj[56].worldX = 5 * gp.tileSize; // Column 10
            gp.obj[56].worldY = 33 * gp.tileSize; // Row 10

            gp.obj[57] = new OBJ_SmallShop();
            gp.obj[57].worldX = 8 * gp.tileSize; // Column 10
            gp.obj[57].worldY = 33 * gp.tileSize; // Row 10

            gp.obj[58] = new OBJ_BlueFlower();
            gp.obj[58].worldX = 30 * gp.tileSize; // Column 10
            gp.obj[58].worldY = 5 * gp.tileSize; // Row 10

            gp.obj[59] = new OBJ_Flower();
            gp.obj[59].worldX = 31 * gp.tileSize; // Column 10
            gp.obj[59].worldY = 5 * gp.tileSize; // Row 10

            gp.obj[60] = new OBJ_RedFlower();
            gp.obj[60].worldX = 32 * gp.tileSize; // Column 10
            gp.obj[60].worldY = 5 * gp.tileSize; // Row 10

            gp.obj[61] = new OBJ_BlueFlower();
            gp.obj[61].worldX = 33 * gp.tileSize; // Column 10
            gp.obj[61].worldY = 5 * gp.tileSize; // Row 10

            gp.obj[62] = new OBJ_Flower();
            gp.obj[62].worldX = 34 * gp.tileSize; // Column 10
            gp.obj[62].worldY = 5 * gp.tileSize; // Row 10

            gp.obj[63] = new OBJ_RedFlower();
            gp.obj[63].worldX = 35 * gp.tileSize; // Column 10
            gp.obj[63].worldY = 5 * gp.tileSize; // Row 10

            gp.obj[64] = new OBJ_BlueFlower();
            gp.obj[64].worldX = 36 * gp.tileSize; // Column 10
            gp.obj[64].worldY = 5 * gp.tileSize; // Row 10

            gp.obj[65] = new OBJ_Flower();
            gp.obj[65].worldX = 37 * gp.tileSize; // Column 10
            gp.obj[65].worldY = 5 * gp.tileSize; // Row 10

            gp.obj[66] = new OBJ_RedHouse();
            gp.obj[66].worldX = 32 * gp.tileSize; // Column 10
            gp.obj[66].worldY = 6 * gp.tileSize; // Row 10

            gp.obj[67] = new OBJ_BlueFlower();
            gp.obj[67].worldX = 30 * gp.tileSize; // Column 10
            gp.obj[67].worldY = 10 * gp.tileSize; // Row 10

            gp.obj[68] = new OBJ_Flower();
            gp.obj[68].worldX = 31 * gp.tileSize; // Column 10
            gp.obj[68].worldY = 10 * gp.tileSize; // Row 10

            gp.obj[69] = new OBJ_RedFlower();
            gp.obj[69].worldX = 32 * gp.tileSize; // Column 10
            gp.obj[69].worldY = 10 * gp.tileSize; // Row 10

            gp.obj[70] = new OBJ_BlueFlower();
            gp.obj[70].worldX = 33 * gp.tileSize; // Column 10
            gp.obj[70].worldY = 10 * gp.tileSize; // Row 10

            gp.obj[71] = new OBJ_Flower();
            gp.obj[71].worldX = 34 * gp.tileSize; // Column 10
            gp.obj[71].worldY = 10 * gp.tileSize; // Row 10

            gp.obj[72] = new OBJ_RedFlower();
            gp.obj[72].worldX = 35 * gp.tileSize; // Column 10
            gp.obj[72].worldY = 10 * gp.tileSize; // Row 10

            gp.obj[73] = new OBJ_BlueFlower();
            gp.obj[73].worldX = 36 * gp.tileSize; // Column 10
            gp.obj[73].worldY = 10 * gp.tileSize; // Row 10

            gp.obj[74] = new OBJ_Flower();
            gp.obj[74].worldX = 37 * gp.tileSize; // Column 10
            gp.obj[74].worldY = 10 * gp.tileSize; // Row 10

            gp.obj[75] = new OBJ_BlueHouse();
            gp.obj[75].worldX = 32 * gp.tileSize; // Column 10
            gp.obj[75].worldY = 11 * gp.tileSize; // Row 10

            gp.obj[76] = new OBJ_BlueFlower();
            gp.obj[76].worldX = 30 * gp.tileSize; // Column 10
            gp.obj[76].worldY = 15 * gp.tileSize; // Row 10

            gp.obj[77] = new OBJ_Flower();
            gp.obj[77].worldX = 31 * gp.tileSize; // Column 10
            gp.obj[77].worldY = 15 * gp.tileSize; // Row 10

            gp.obj[78] = new OBJ_RedFlower();
            gp.obj[78].worldX = 32 * gp.tileSize; // Column 10
            gp.obj[78].worldY = 15 * gp.tileSize; // Row 10

            gp.obj[79] = new OBJ_BlueFlower();
            gp.obj[79].worldX = 33 * gp.tileSize; // Column 10
            gp.obj[79].worldY = 15 * gp.tileSize; // Row 10

            gp.obj[80] = new OBJ_Flower();
            gp.obj[80].worldX = 34 * gp.tileSize; // Column 10
            gp.obj[80].worldY = 15 * gp.tileSize; // Row 10

            gp.obj[81] = new OBJ_RedFlower();
            gp.obj[81].worldX = 35 * gp.tileSize; // Column 10
            gp.obj[81].worldY = 15 * gp.tileSize; // Row 10

            gp.obj[82] = new OBJ_BlueFlower();
            gp.obj[82].worldX = 36 * gp.tileSize; // Column 10
            gp.obj[82].worldY = 15 * gp.tileSize; // Row 10

            gp.obj[83] = new OBJ_Flower();
            gp.obj[83].worldX = 37 * gp.tileSize; // Column 10
            gp.obj[83].worldY = 15 * gp.tileSize; // Row 10

            gp.obj[84] = new OBJ_RedHouse();
            gp.obj[84].worldX = 32 * gp.tileSize; // Column 10
            gp.obj[84].worldY = 16 * gp.tileSize; // Row 10

            gp.obj[85] = new OBJ_BlueFlower();
            gp.obj[85].worldX = 37 * gp.tileSize; // Column 10
            gp.obj[85].worldY = 16 * gp.tileSize; // Row 10

            gp.obj[86] = new OBJ_RedFlower();
            gp.obj[86].worldX = 37 * gp.tileSize; // Column 10
            gp.obj[86].worldY = 17 * gp.tileSize; // Row 10

            gp.obj[87] = new OBJ_Flower();
            gp.obj[87].worldX = 37 * gp.tileSize; // Column 10
            gp.obj[87].worldY = 18 * gp.tileSize; // Row 10

            gp.obj[88] = new OBJ_BlueFlower();
            gp.obj[88].worldX = 37 * gp.tileSize; // Column 10
            gp.obj[88].worldY = 19 * gp.tileSize; // Row 10

            gp.obj[89] = new OBJ_BlueHouse();
            gp.obj[89].worldX = 38 * gp.tileSize; // Column 10
            gp.obj[89].worldY = 16 * gp.tileSize; // Row 10

            gp.obj[90] = new OBJ_RedHouse();
            gp.obj[90].worldX = 42 * gp.tileSize; // Column 10
            gp.obj[90].worldY = 16 * gp.tileSize; // Row 10

            gp.obj[91] = new OBJ_BlueFlower();
            gp.obj[91].worldX = 46 * gp.tileSize; // Column 10
            gp.obj[91].worldY = 16 * gp.tileSize; // Row 10

            gp.obj[92] = new OBJ_Flower();
            gp.obj[92].worldX = 46 * gp.tileSize; // Column 10
            gp.obj[92].worldY = 17 * gp.tileSize; // Row 10

            gp.obj[93] = new OBJ_RedFlower();
            gp.obj[93].worldX = 46 * gp.tileSize; // Column 10
            gp.obj[93].worldY = 18 * gp.tileSize; // Row 10

            gp.obj[94] = new OBJ_BlueFlower();
            gp.obj[94].worldX = 46 * gp.tileSize; // Column 10
            gp.obj[94].worldY = 19 * gp.tileSize; // Row 10

            gp.obj[95] = new OBJ_BlueHouse();
            gp.obj[95].worldX = 30 * gp.tileSize; // Column 10
            gp.obj[95].worldY = 25 * gp.tileSize; // Row 10

            gp.obj[96] = new OBJ_RedHouse();
            gp.obj[96].worldX = 35 * gp.tileSize; // Column 10
            gp.obj[96].worldY = 25 * gp.tileSize; // Row 10

            gp.obj[97] = new OBJ_BlueHouse();
            gp.obj[97].worldX = 40 * gp.tileSize; // Column 10
            gp.obj[97].worldY = 25 * gp.tileSize; // Row 10

            gp.obj[98] = new OBJ_TreeOrange();
            gp.obj[98].worldX = 29 * gp.tileSize; // Column 10
            gp.obj[98].worldY = 29 * gp.tileSize; // Row 10

            gp.obj[99] = new OBJ_GreenTree();
            gp.obj[99].worldX = 31 * gp.tileSize; // Column 10
            gp.obj[99].worldY = 29 * gp.tileSize; // Row 10

            gp.obj[100] = new OBJ_TreeYellow();
            gp.obj[100].worldX = 33 * gp.tileSize; // Column 10
            gp.obj[100].worldY = 29 * gp.tileSize; // Row 10

            gp.obj[101] = new OBJ_TreeOrange();
            gp.obj[101].worldX = 35 * gp.tileSize; // Column 10
            gp.obj[101].worldY = 29 * gp.tileSize; // Row 10

            gp.obj[102] = new OBJ_GreenTree();
            gp.obj[102].worldX = 37 * gp.tileSize; // Column 10
            gp.obj[102].worldY = 29 * gp.tileSize; // Row 10

            gp.obj[103] = new OBJ_TreeYellow();
            gp.obj[103].worldX = 39 * gp.tileSize; // Column 10
            gp.obj[103].worldY = 29 * gp.tileSize; // Row 10

            gp.obj[104] = new OBJ_TreeOrange();
            gp.obj[104].worldX = 41 * gp.tileSize; // Column 10
            gp.obj[104].worldY = 29 * gp.tileSize; // Row 10

            gp.obj[105] = new OBJ_GreenTree();
            gp.obj[105].worldX = 43 * gp.tileSize; // Column 10
            gp.obj[105].worldY = 29 * gp.tileSize; // Row 10

            gp.obj[106] = new OBJ_TreeOrange();
            gp.obj[106].worldX = 29 * gp.tileSize; // Column 10
            gp.obj[106].worldY = 30 * gp.tileSize; // Row 10

            gp.obj[107] = new OBJ_GreenTree();
            gp.obj[107].worldX = 31 * gp.tileSize; // Column 10
            gp.obj[107].worldY = 30 * gp.tileSize; // Row 10

            gp.obj[108] = new OBJ_TreeYellow();
            gp.obj[108].worldX = 33 * gp.tileSize; // Column 10
            gp.obj[108].worldY = 30 * gp.tileSize; // Row 10

            gp.obj[109] = new OBJ_TreeOrange();
            gp.obj[109].worldX = 35 * gp.tileSize; // Column 10
            gp.obj[109].worldY = 30 * gp.tileSize; // Row 10

            gp.obj[110] = new OBJ_GreenTree();
            gp.obj[110].worldX = 37 * gp.tileSize; // Column 10
            gp.obj[110].worldY = 30 * gp.tileSize; // Row 10

            gp.obj[111] = new OBJ_TreeYellow();
            gp.obj[111].worldX = 39 * gp.tileSize; // Column 10
            gp.obj[111].worldY = 30 * gp.tileSize; // Row 10

            gp.obj[112] = new OBJ_TreeOrange();
            gp.obj[112].worldX = 41 * gp.tileSize; // Column 10
            gp.obj[112].worldY = 30 * gp.tileSize; // Row 10

            gp.obj[113] = new OBJ_GreenTree();
            gp.obj[113].worldX = 43 * gp.tileSize; // Column 10
            gp.obj[113].worldY = 30 * gp.tileSize; // Row 10

            gp.obj[114] = new OBJ_TreeYellow();
            gp.obj[114].worldX = 30 * gp.tileSize; // Column 10
            gp.obj[114].worldY = 31 * gp.tileSize; // Row 10

            gp.obj[115] = new OBJ_TreeOrange();
            gp.obj[115].worldX = 32 * gp.tileSize; // Column 10
            gp.obj[115].worldY = 31 * gp.tileSize; // Row 10

            gp.obj[116] = new OBJ_GreenTree();
            gp.obj[116].worldX = 34 * gp.tileSize; // Column 10
            gp.obj[116].worldY = 31 * gp.tileSize; // Row 10

            gp.obj[117] = new OBJ_TreeYellow();
            gp.obj[117].worldX = 36 * gp.tileSize; // Column 10
            gp.obj[117].worldY = 31 * gp.tileSize; // Row 10

            gp.obj[118] = new OBJ_TreeOrange();
            gp.obj[118].worldX = 38 * gp.tileSize; // Column 10
            gp.obj[118].worldY = 31 * gp.tileSize; // Row 10

            gp.obj[119] = new OBJ_GreenTree();
            gp.obj[119].worldX = 40 * gp.tileSize; // Column 10
            gp.obj[119].worldY = 31 * gp.tileSize; // Row 1

            gp.obj[120] = new OBJ_TreeYellow();
            gp.obj[120].worldX = 42 * gp.tileSize; // Column 10
            gp.obj[120].worldY = 31 * gp.tileSize; // Row 1

            gp.obj[121] = new OBJ_TreeYellow();
            gp.obj[121].worldX = 30 * gp.tileSize; // Column 10
            gp.obj[121].worldY = 32 * gp.tileSize; // Row 10

            gp.obj[122] = new OBJ_TreeOrange();
            gp.obj[122].worldX = 32 * gp.tileSize; // Column 10
            gp.obj[122].worldY = 32 * gp.tileSize; // Row 10

            gp.obj[123] = new OBJ_GreenTree();
            gp.obj[123].worldX = 34 * gp.tileSize; // Column 10
            gp.obj[123].worldY = 32 * gp.tileSize; // Row 10

            gp.obj[124] = new OBJ_TreeYellow();
            gp.obj[124].worldX = 36 * gp.tileSize; // Column 10
            gp.obj[124].worldY = 32 * gp.tileSize; // Row 10

            gp.obj[125] = new OBJ_TreeOrange();
            gp.obj[125].worldX = 38 * gp.tileSize; // Column 10
            gp.obj[125].worldY = 32 * gp.tileSize; // Row 10

            gp.obj[126] = new OBJ_GreenTree();
            gp.obj[126].worldX = 40 * gp.tileSize; // Column 10
            gp.obj[126].worldY = 32 * gp.tileSize; // Row 1

            gp.obj[127] = new OBJ_TreeYellow();
            gp.obj[127].worldX = 42 * gp.tileSize; // Column 10
            gp.obj[127].worldY = 32 * gp.tileSize; // Row 1

            gp.obj[128] = new OBJ_Gate();
            gp.obj[128].worldX = 38 * gp.tileSize; // Column 10
            gp.obj[128].worldY = 37 * gp.tileSize; // Row 1

            gp.obj[129] = new OBJ_Gate();
            gp.obj[129].worldX = 40 * gp.tileSize; // Column 10
            gp.obj[129].worldY = 37 * gp.tileSize; // Row 1

            gp.obj[130] = new OBJ_Gate();
            gp.obj[130].worldX = 42 * gp.tileSize; // Column 10
            gp.obj[130].worldY = 37 * gp.tileSize; // Row 1

            gp.obj[131] = new OBJ_Gate();
            gp.obj[131].worldX = 44 * gp.tileSize; // Column 10
            gp.obj[131].worldY = 37 * gp.tileSize; // Row 1

            gp.obj[132] = new OBJ_Gate();
            gp.obj[132].worldX = 46 * gp.tileSize; // Column 10
            gp.obj[132].worldY = 37 * gp.tileSize; // Row 1

            gp.obj[133] = new OBJ_Gate();
            gp.obj[133].worldX = 48 * gp.tileSize; // Column 10
            gp.obj[133].worldY = 37 * gp.tileSize; // Row 1

            gp.obj[134] = new OBJ_GreenTree();
            gp.obj[134].worldX = 30 * gp.tileSize; // Column 10
            gp.obj[134].worldY = 0 * gp.tileSize; // Row 10

            gp.obj[135] = new OBJ_TreeOrange();
            gp.obj[135].worldX = 32 * gp.tileSize; // Column 10
            gp.obj[135].worldY = 0 * gp.tileSize; // Row 10

            gp.obj[136] = new OBJ_GreenTree();
            gp.obj[136].worldX = 34 * gp.tileSize; // Column 10
            gp.obj[136].worldY = 0 * gp.tileSize; // Row 10

            gp.obj[137] = new OBJ_TreeYellow();
            gp.obj[137].worldX = 36 * gp.tileSize; // Column 10
            gp.obj[137].worldY = 0 * gp.tileSize; // Row 10

            gp.obj[138] = new OBJ_TreeOrange();
            gp.obj[138].worldX = 38 * gp.tileSize; // Column 10
            gp.obj[138].worldY = 0 * gp.tileSize; // Row 10

            gp.obj[139] = new OBJ_GreenTree();
            gp.obj[139].worldX = 40 * gp.tileSize; // Column 10
            gp.obj[139].worldY = 0 * gp.tileSize; // Row 10

            gp.obj[140] = new OBJ_TreeYellow();
            gp.obj[140].worldX = 42 * gp.tileSize; // Column 10
            gp.obj[140].worldY = 0 * gp.tileSize; // Row 10

            gp.obj[141] = new OBJ_TreeOrange();
            gp.obj[141].worldX = 44 * gp.tileSize; // Column 10
            gp.obj[141].worldY = 0 * gp.tileSize; // Row 10

            gp.obj[142] = new OBJ_Trunk();
            gp.obj[142].worldX = 18 * gp.tileSize; // Column 10
            gp.obj[142].worldY = 19 * gp.tileSize; // Row 10

            gp.obj[143] = new OBJ_Trunk();
            gp.obj[143].worldX = 17 * gp.tileSize; // Column 10
            gp.obj[143].worldY = 18 * gp.tileSize; // Row 10

            gp.obj[144] = new OBJ_Trunk();
            gp.obj[144].worldX = 16 * gp.tileSize; // Column 10
            gp.obj[144].worldY = 17 * gp.tileSize; // Row 10

            gp.obj[145] = new OBJ_Trunk();
            gp.obj[145].worldX = 19 * gp.tileSize; // Column 10
            gp.obj[145].worldY = 20 * gp.tileSize; // Row 10

            gp.obj[146] = new OBJ_Trunk();
            gp.obj[146].worldX = 14 * gp.tileSize; // Column 10
            gp.obj[146].worldY = 13 * gp.tileSize; // Row 10

            gp.obj[147] = new OBJ_Trunk();
            gp.obj[147].worldX = 16 * gp.tileSize; // Column 10
            gp.obj[147].worldY = 13 * gp.tileSize; // Row 10
























        }
    }
}