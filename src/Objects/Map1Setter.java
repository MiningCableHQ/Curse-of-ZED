package Objects;

import Main.GamePanel;
import Entities.Characters.*;
import Objects.*;
public class Map1Setter {
    GamePanel gp;

    public Map1Setter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjects() {

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

        gp.obj[148] = new OBJ_Water();
        gp.obj[148].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[148].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[149] = new OBJ_Water();
        gp.obj[149].worldX = 1 * gp.tileSize; // Col 13
        gp.obj[149].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[150] = new OBJ_Water();
        gp.obj[150].worldX = 2 * gp.tileSize; // Col 13
        gp.obj[150].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[151] = new OBJ_Water();
        gp.obj[151].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[151].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[152] = new OBJ_Water();
        gp.obj[152].worldX = 4 * gp.tileSize; // Col 13
        gp.obj[152].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[153] = new OBJ_Water();
        gp.obj[153].worldX = 5 * gp.tileSize; // Col 13
        gp.obj[153].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[154] = new OBJ_Water();
        gp.obj[154].worldX = 6 * gp.tileSize; // Col 13
        gp.obj[154].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[155] = new OBJ_Water();
        gp.obj[155].worldX = 7 * gp.tileSize; // Col 13
        gp.obj[155].worldY = 39 * gp.tileSize; // Row 36

        gp.obj[156] = new OBJ_Water();
        gp.obj[156].worldX = 8 * gp.tileSize; // Col 13
        gp.obj[156].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[157] = new OBJ_Water();
        gp.obj[157].worldX = 9 * gp.tileSize; // Col 13
        gp.obj[157].worldY = 41 * gp.tileSize; // Row 36

        gp.obj[158] = new OBJ_Water();
        gp.obj[158].worldX = 10 * gp.tileSize; // Col 13
        gp.obj[158].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[159] = new OBJ_Water();
        gp.obj[159].worldX = 11 * gp.tileSize; // Col 13
        gp.obj[159].worldY = 43 * gp.tileSize; // Row 36

        gp.obj[160] = new OBJ_Water();
        gp.obj[160].worldX = 12 * gp.tileSize; // Col 13
        gp.obj[160].worldY = 44 * gp.tileSize; // Row 36

        gp.obj[161] = new OBJ_Water();
        gp.obj[161].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[161].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[162] = new OBJ_Water();
        gp.obj[162].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[162].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[163] = new OBJ_Water();
        gp.obj[163].worldX = 15 * gp.tileSize; // Col 13
        gp.obj[163].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[164] = new OBJ_Water();
        gp.obj[164].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[164].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[165] = new OBJ_Water();
        gp.obj[165].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[165].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[166] = new OBJ_Water();
        gp.obj[166].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[166].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[167] = new OBJ_Water();
        gp.obj[167].worldX = 19 * gp.tileSize; // Col 13
        gp.obj[167].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[168] = new OBJ_Water();
        gp.obj[168].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[168].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[169] = new OBJ_Water();
        gp.obj[169].worldX = 21 * gp.tileSize; // Col 13
        gp.obj[169].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[170] = new OBJ_Water();
        gp.obj[170].worldX = 22 * gp.tileSize; // Col 13
        gp.obj[170].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[171] = new OBJ_Water();
        gp.obj[171].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[171].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[172] = new OBJ_Water();
        gp.obj[172].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[172].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[173] = new OBJ_Water();
        gp.obj[173].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[173].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[174] = new OBJ_Water();
        gp.obj[174].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[174].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[175] = new OBJ_Water();
        gp.obj[175].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[175].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[176] = new OBJ_Water();
        gp.obj[176].worldX = 28 * gp.tileSize; // Col 13
        gp.obj[176].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[177] = new OBJ_Water();
        gp.obj[177].worldX = 29 * gp.tileSize; // Col 13
        gp.obj[177].worldY = 45* gp.tileSize; // Row 36

        gp.obj[178] = new OBJ_Water();
        gp.obj[178].worldX = 30 * gp.tileSize; // Col 13
        gp.obj[178].worldY = 44* gp.tileSize; // Row 36

        gp.obj[179] = new OBJ_Water();
        gp.obj[179].worldX = 31 * gp.tileSize; // Col 13
        gp.obj[179].worldY = 43* gp.tileSize; // Row 36

        gp.obj[180] = new OBJ_Water();
        gp.obj[180].worldX = 31 * gp.tileSize; // Col 13
        gp.obj[180].worldY = 43* gp.tileSize; // Row 36

        gp.obj[181] = new OBJ_Water();
        gp.obj[181].worldX = 32 * gp.tileSize; // Col 13
        gp.obj[181].worldY = 42* gp.tileSize; // Row 36

        gp.obj[182] = new OBJ_Water();
        gp.obj[182].worldX = 33 * gp.tileSize; // Col 13
        gp.obj[182].worldY = 41* gp.tileSize; // Row 36

        gp.obj[183] = new OBJ_Water();
        gp.obj[183].worldX = 34 * gp.tileSize; // Col 13
        gp.obj[183].worldY = 40* gp.tileSize; // Row 36

        gp.obj[184] = new OBJ_Water();
        gp.obj[184].worldX = 35 * gp.tileSize; // Col 13
        gp.obj[184].worldY = 39* gp.tileSize; // Row 36

        gp.obj[185] = new OBJ_Water();
        gp.obj[185].worldX = 36 * gp.tileSize; // Col 13
        gp.obj[185].worldY = 39* gp.tileSize; // Row 36

        gp.obj[186] = new OBJ_Fences();
        gp.obj[186].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[186].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[187] = new OBJ_Fences();
        gp.obj[187].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[187].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[188] = new OBJ_Fences();
        gp.obj[188].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[188].worldY = 35 * gp.tileSize; // Row 36

        gp.obj[189] = new OBJ_Fences();
        gp.obj[189].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[189].worldY = 34 * gp.tileSize; // Row 36

        gp.obj[190] = new OBJ_Fences();
        gp.obj[190].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[190].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[191] = new OBJ_Fences();
        gp.obj[191].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[191].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[192] = new OBJ_Fences();
        gp.obj[192].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[192].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[193] = new OBJ_Fences();
        gp.obj[193].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[193].worldY = 30 * gp.tileSize; // Row 36

        gp.obj[194] = new OBJ_Fences();
        gp.obj[194].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[194].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[195] = new OBJ_Fences();
        gp.obj[195].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[195].worldY = 28 * gp.tileSize; // Row 36

        gp.obj[196] = new OBJ_Fences();
        gp.obj[196].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[196].worldY = 27 * gp.tileSize; // Row 36

        gp.obj[197] = new OBJ_Fences();
        gp.obj[197].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[197].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[198] = new OBJ_Fences();
        gp.obj[198].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[198].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[199] = new OBJ_Fences();
        gp.obj[199].worldX = 1 * gp.tileSize; // Col 13
        gp.obj[199].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[200] = new OBJ_Fences();
        gp.obj[200].worldX = 2 * gp.tileSize; // Col 13
        gp.obj[200].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[201] = new OBJ_Fences();
        gp.obj[201].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[201].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[202] = new OBJ_Fences();
        gp.obj[202].worldX = 4 * gp.tileSize; // Col 13
        gp.obj[202].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[203] = new OBJ_Fences();
        gp.obj[203].worldX = 5 * gp.tileSize; // Col 13
        gp.obj[203].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[204] = new OBJ_Fences();
        gp.obj[204].worldX = 6 * gp.tileSize; // Col 13
        gp.obj[204].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[205] = new OBJ_Fences();
        gp.obj[205].worldX = 7 * gp.tileSize; // Col 13
        gp.obj[205].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[206] = new OBJ_Fences();
        gp.obj[206].worldX = 8 * gp.tileSize; // Col 13
        gp.obj[206].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[207] = new OBJ_Fences();
        gp.obj[207].worldX = 9 * gp.tileSize; // Col 13
        gp.obj[207].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[208] = new OBJ_Fences();
        gp.obj[208].worldX = 10 * gp.tileSize; // Col 13
        gp.obj[208].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[209] = new OBJ_Fences();
        gp.obj[209].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[209].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[210] = new OBJ_Fences();
        gp.obj[210].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[210].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[211] = new OBJ_Fences();
        gp.obj[211].worldX = 15 * gp.tileSize; // Col 13
        gp.obj[211].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[212] = new OBJ_Fences();
        gp.obj[212].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[212].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[213] = new OBJ_Fences();
        gp.obj[213].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[213].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[214] = new OBJ_Fences();
        gp.obj[214].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[214].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[215] = new OBJ_Fences();
        gp.obj[215].worldX = 19 * gp.tileSize; // Col 13
        gp.obj[215].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[216] = new OBJ_Fences();
        gp.obj[216].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[216].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[217] = new OBJ_Fences();
        gp.obj[217].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[217].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[218] = new OBJ_Fences();
        gp.obj[218].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[218].worldY = 27 * gp.tileSize; // Row 36

        gp.obj[219] = new OBJ_Fences();
        gp.obj[219].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[219].worldY = 28 * gp.tileSize; // Row 36

        gp.obj[220] = new OBJ_Fences();
        gp.obj[220].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[220].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[221] = new OBJ_Fences();
        gp.obj[221].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[221].worldY = 30 * gp.tileSize; // Row 36

        gp.obj[222] = new OBJ_Fences();
        gp.obj[222].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[222].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[223] = new OBJ_Fences();
        gp.obj[223].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[223].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[224] = new OBJ_Fences();
        gp.obj[224].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[224].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[225] = new OBJ_Fences();
        gp.obj[225].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[225].worldY = 34 * gp.tileSize; // Row 36

        gp.obj[226] = new OBJ_Fences();
        gp.obj[226].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[226].worldY = 35 * gp.tileSize; // Row 36

        gp.obj[227] = new OBJ_Fences();
        gp.obj[227].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[227].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[228] = new OBJ_Fences();
        gp.obj[228].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[228].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[229] = new OBJ_Fences();
        gp.obj[229].worldX = 1 * gp.tileSize; // Col 13
        gp.obj[229].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[230] = new OBJ_Fences();
        gp.obj[230].worldX = 2 * gp.tileSize; // Col 13
        gp.obj[230].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[231] = new OBJ_Fences();
        gp.obj[231].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[231].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[232] = new OBJ_Fences();
        gp.obj[232].worldX = 4 * gp.tileSize; // Col 13
        gp.obj[232].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[233] = new OBJ_Fences();
        gp.obj[233].worldX = 5 * gp.tileSize; // Col 13
        gp.obj[233].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[234] = new OBJ_Fences();
        gp.obj[234].worldX = 6 * gp.tileSize; // Col 13
        gp.obj[234].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[235] = new OBJ_Fences();
        gp.obj[235].worldX = 7 * gp.tileSize; // Col 13
        gp.obj[235].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[236] = new OBJ_Fences();
        gp.obj[236].worldX = 8 * gp.tileSize; // Col 13
        gp.obj[236].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[237] = new OBJ_Fences();
        gp.obj[237].worldX = 9 * gp.tileSize; // Col 13
        gp.obj[237].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[238] = new OBJ_Fences();
        gp.obj[238].worldX = 10 * gp.tileSize; // Col 13
        gp.obj[238].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[239] = new OBJ_Fences();
        gp.obj[239].worldX = 11 * gp.tileSize; // Col 13
        gp.obj[239].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[240] = new OBJ_Fences();
        gp.obj[240].worldX = 12 * gp.tileSize; // Col 13
        gp.obj[240].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[241] = new OBJ_Fences();
        gp.obj[241].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[241].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[242] = new OBJ_Fences();
        gp.obj[242].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[242].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[243] = new OBJ_Fences();
        gp.obj[243].worldX = 15 * gp.tileSize; // Col 13
        gp.obj[243].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[244] = new OBJ_Fences();
        gp.obj[244].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[244].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[245] = new OBJ_Fences();
        gp.obj[245].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[245].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[246] = new OBJ_Fences();
        gp.obj[246].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[246].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[247] = new OBJ_Fences();
        gp.obj[247].worldX = 37 * gp.tileSize; // Col 13
        gp.obj[247].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[248] = new OBJ_Water();
        gp.obj[248].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[248].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[249] = new OBJ_Water();
        gp.obj[249].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[249].worldY = 35 * gp.tileSize; // Row 36

        gp.obj[250] = new OBJ_Water();
        gp.obj[250].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[250].worldY = 34 * gp.tileSize; // Row 36

        gp.obj[251] = new OBJ_Water();
        gp.obj[251].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[251].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[252] = new OBJ_Water();
        gp.obj[252].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[252].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[252] = new OBJ_Water();
        gp.obj[252].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[252].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[253] = new OBJ_Water();
        gp.obj[253].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[253].worldY = 30* gp.tileSize; // Row 36

        gp.obj[254] = new OBJ_Water();
        gp.obj[254].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[254].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[255] = new OBJ_Water();
        gp.obj[255].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[255].worldY = 28 * gp.tileSize; // Row 36

        gp.obj[256] = new OBJ_Water();
        gp.obj[256].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[256].worldY = 27 * gp.tileSize; // Row 36

        gp.obj[257] = new OBJ_Water();
        gp.obj[257].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[257].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[258] = new OBJ_Water();
        gp.obj[258].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[258].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[259] = new OBJ_Water();
        gp.obj[259].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[259].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[260] = new OBJ_Water();
        gp.obj[260].worldX = 49 * gp.tileSize; // Col 13
        gp.obj[260].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[261] = new OBJ_Water();
        gp.obj[261].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[261].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[262] = new OBJ_Water();
        gp.obj[262].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[262].worldY = 20 * gp.tileSize; // Row 36

        gp.obj[263] = new OBJ_Water();
        gp.obj[263].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[263].worldY = 19 * gp.tileSize; // Row 36

        gp.obj[264] = new OBJ_Water();
        gp.obj[264].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[264].worldY = 18 * gp.tileSize; // Row 36

        gp.obj[265] = new OBJ_Water();
        gp.obj[265].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[265].worldY = 17 * gp.tileSize; // Row 36

        gp.obj[266] = new OBJ_Water();
        gp.obj[266].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[266].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[263] = new OBJ_Fences();
        gp.obj[263].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[263].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[264] = new OBJ_Fences();
        gp.obj[264].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[264].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[265] = new OBJ_Fences();
        gp.obj[265].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[265].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[266] = new OBJ_Fences();
        gp.obj[266].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[266].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[267] = new OBJ_Fences();
        gp.obj[267].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[267].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[268] = new OBJ_Fences();
        gp.obj[268].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[268].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[269] = new OBJ_Fences();
        gp.obj[269].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[269].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[270] = new OBJ_Fences();
        gp.obj[270].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[270].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[271] = new OBJ_Fences();
        gp.obj[271].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[271].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[272] = new OBJ_Fences();
        gp.obj[272].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[272].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[273] = new OBJ_Fences();
        gp.obj[273].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[273].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[274] = new OBJ_Fences();
        gp.obj[274].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[274].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[275] = new OBJ_Water();
        gp.obj[275].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[275].worldY = 3 * gp.tileSize; // Row 36

        gp.obj[276] = new OBJ_Water();
        gp.obj[276].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[276].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[277] = new OBJ_Water();
        gp.obj[277].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[277].worldY = 1 * gp.tileSize; // Row 36

        gp.obj[278] = new OBJ_Fences();
        gp.obj[278].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[278].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[279] = new OBJ_Fences();
        gp.obj[279].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[279].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[280] = new OBJ_Fences();
        gp.obj[280].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[280].worldY = 20 * gp.tileSize; // Row 36

        gp.obj[281] = new OBJ_Fences();
        gp.obj[281].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[281].worldY = 19 * gp.tileSize; // Row 36

        gp.obj[282] = new OBJ_Fences();
        gp.obj[282].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[282].worldY = 18 * gp.tileSize; // Row 36

        gp.obj[283] = new OBJ_Fences();
        gp.obj[283].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[283].worldY = 17 * gp.tileSize; // Row 36

        gp.obj[284] = new OBJ_Fences();
        gp.obj[284].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[284].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[285] = new OBJ_Fences();
        gp.obj[285].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[285].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[286] = new OBJ_Fences();
        gp.obj[286].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[286].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[287] = new OBJ_Fences();
        gp.obj[287].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[287].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[288] = new OBJ_Fences();
        gp.obj[288].worldX = 46 * gp.tileSize; // Col 13
        gp.obj[288].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[289] = new OBJ_Fences();
        gp.obj[289].worldX = 46 * gp.tileSize; // Col 13
        gp.obj[289].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[290] = new OBJ_Fences();
        gp.obj[290].worldX = 46 * gp.tileSize; // Col 13
        gp.obj[290].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[291] = new OBJ_Fences();
        gp.obj[291].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[291].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[292] = new OBJ_Fences();
        gp.obj[292].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[292].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[293] = new OBJ_Fences();
        gp.obj[293].worldX = 43 * gp.tileSize; // Col 13
        gp.obj[293].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[294] = new OBJ_Fences();
        gp.obj[294].worldX = 42 * gp.tileSize; // Col 13
        gp.obj[294].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[295] = new OBJ_Fences();
        gp.obj[295].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[295].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[296] = new OBJ_Fences();
        gp.obj[296].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[296].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[297] = new OBJ_Fences();
        gp.obj[297].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[297].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[298] = new OBJ_Fences();
        gp.obj[298].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[298].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[299] = new OBJ_Fences();
        gp.obj[299].worldX = 37 * gp.tileSize; // Col 13
        gp.obj[299].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[300] = new OBJ_Fences();
        gp.obj[300].worldX = 36 * gp.tileSize; // Col 13
        gp.obj[300].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[301] = new OBJ_Fences();
        gp.obj[301].worldX = 35 * gp.tileSize; // Col 13
        gp.obj[301].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[302] = new OBJ_Fences();
        gp.obj[302].worldX = 34 * gp.tileSize; // Col 13
        gp.obj[302].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[303] = new OBJ_Fences();
        gp.obj[303].worldX = 33 * gp.tileSize; // Col 13
        gp.obj[303].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[304] = new OBJ_Fences();
        gp.obj[304].worldX = 32 * gp.tileSize; // Col 13
        gp.obj[304].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[305] = new OBJ_Fences();
        gp.obj[305].worldX = 31 * gp.tileSize; // Col 13
        gp.obj[305].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[306] = new OBJ_Fences();
        gp.obj[306].worldX = 30 * gp.tileSize; // Col 13
        gp.obj[306].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[307] = new OBJ_Fences();
        gp.obj[307].worldX = 46 * gp.tileSize; // Col 13
        gp.obj[307].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[308] = new OBJ_Fences();
        gp.obj[308].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[308].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[309] = new OBJ_Fences();
        gp.obj[309].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[309].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[310] = new OBJ_Fences();
        gp.obj[310].worldX = 43 * gp.tileSize; // Col 13
        gp.obj[310].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[311] = new OBJ_Fences();
        gp.obj[311].worldX = 42 * gp.tileSize; // Col 13
        gp.obj[311].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[312] = new OBJ_Fences();
        gp.obj[312].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[312].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[313] = new OBJ_Fences();
        gp.obj[313].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[313].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[314] = new OBJ_Fences();
        gp.obj[314].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[314].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[315] = new OBJ_Fences();
        gp.obj[315].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[315].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[316] = new OBJ_Fences();
        gp.obj[316].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[316].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[317] = new OBJ_Fences();
        gp.obj[317].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[317].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[318] = new OBJ_Fences();
        gp.obj[318].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[318].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[319] = new OBJ_Fences();
        gp.obj[319].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[319].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[320] = new OBJ_Fences();
        gp.obj[320].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[320].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[321] = new OBJ_Fences();
        gp.obj[321].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[321].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[322] = new OBJ_Fences();
        gp.obj[322].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[322].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[323] = new OBJ_Fences();
        gp.obj[323].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[323].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[324] = new OBJ_Fences();
        gp.obj[324].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[324].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[325] = new OBJ_Fences();
        gp.obj[325].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[325].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[326] = new OBJ_Fences();
        gp.obj[326].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[326].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[327] = new OBJ_Fences();
        gp.obj[327].worldX = 37 * gp.tileSize; // Col 13
        gp.obj[327].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[328] = new OBJ_Fences();
        gp.obj[328].worldX = 36 * gp.tileSize; // Col 13
        gp.obj[328].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[329] = new OBJ_Fences();
        gp.obj[329].worldX = 35 * gp.tileSize; // Col 13
        gp.obj[329].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[330] = new OBJ_Fences();
        gp.obj[330].worldX = 34 * gp.tileSize; // Col 13
        gp.obj[330].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[331] = new OBJ_Fences();
        gp.obj[331].worldX = 33 * gp.tileSize; // Col 13
        gp.obj[331].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[332] = new OBJ_Fences();
        gp.obj[332].worldX = 32 * gp.tileSize; // Col 13
        gp.obj[332].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[333] = new OBJ_Fences();
        gp.obj[333].worldX = 31 * gp.tileSize; // Col 13
        gp.obj[333].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[334] = new OBJ_Fences();
        gp.obj[334].worldX = 30 * gp.tileSize; // Col 13
        gp.obj[334].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[335] = new OBJ_Fences();
        gp.obj[335].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[335].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[336] = new OBJ_Fences();
        gp.obj[336].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[336].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[337] = new OBJ_Fences();
        gp.obj[337].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[337].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[338] = new OBJ_Fences();
        gp.obj[338].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[338].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[339] = new OBJ_Fences();
        gp.obj[339].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[339].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[340] = new OBJ_Fences();
        gp.obj[340].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[340].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[341] = new OBJ_Crops();
        gp.obj[341].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[341].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[342] = new OBJ_Crops();
        gp.obj[342].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[342].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[343] = new OBJ_Crops();
        gp.obj[343].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[343].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[344] = new OBJ_Crops();
        gp.obj[344].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[344].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[345] = new OBJ_Crops();
        gp.obj[345].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[345].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[346] = new OBJ_Crops();
        gp.obj[346].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[346].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[347] = new OBJ_Crops();
        gp.obj[347].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[347].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[348] = new OBJ_Crops();
        gp.obj[348].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[348].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[349] = new OBJ_Crops();
        gp.obj[349].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[349].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[350] = new OBJ_Crops();
        gp.obj[350].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[350].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[351] = new OBJ_Crops();
        gp.obj[351].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[351].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[352] = new OBJ_Crops();
        gp.obj[352].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[352].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[353] = new OBJ_Crops();
        gp.obj[353].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[353].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[354] = new OBJ_Crops();
        gp.obj[354].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[354].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[355] = new OBJ_Crops();
        gp.obj[355].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[355].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[356] = new OBJ_Crops();
        gp.obj[356].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[356].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[357] = new OBJ_Crops();
        gp.obj[357].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[357].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[358] = new OBJ_Crops();
        gp.obj[358].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[358].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[359] = new OBJ_Crops();
        gp.obj[359].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[359].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[360] = new OBJ_Crops();
        gp.obj[360].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[360].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[361] = new OBJ_Crops();
        gp.obj[361].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[361].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[362] = new OBJ_Crops();
        gp.obj[362].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[362].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[363] = new OBJ_Crops();
        gp.obj[363].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[363].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[364] = new OBJ_Crops();
        gp.obj[364].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[364].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[365] = new OBJ_Crops();
        gp.obj[365].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[365].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[366] = new OBJ_Crops();
        gp.obj[366].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[366].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[367] = new OBJ_Crops();
        gp.obj[367].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[367].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[368] = new OBJ_Crops();
        gp.obj[368].worldX = 43 * gp.tileSize; // Col 13
        gp.obj[368].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[369] = new OBJ_Crops();
        gp.obj[369].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[369].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[370] = new OBJ_Crops();
        gp.obj[370].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[370].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[371] = new OBJ_Crops();
        gp.obj[371].worldX = 43 * gp.tileSize; // Col 13
        gp.obj[371].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[372] = new OBJ_Crops();
        gp.obj[372].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[372].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[373] = new OBJ_Crops();
        gp.obj[373].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[373].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[374] = new OBJ_Crops();
        gp.obj[374].worldX = 43 * gp.tileSize; // Col 13
        gp.obj[374].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[375] = new OBJ_Crops();
        gp.obj[375].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[375].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[376] = new OBJ_Crops();
        gp.obj[376].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[376].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[377] = new OBJ_Crops();
        gp.obj[377].worldX = 43 * gp.tileSize; // Col 13
        gp.obj[377].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[378] = new OBJ_Crops();
        gp.obj[378].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[378].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[379] = new OBJ_Crops();
        gp.obj[379].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[379].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[380] = new OBJ_Crops();
        gp.obj[380].worldX = 43 * gp.tileSize; // Col 13
        gp.obj[380].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[381] = new OBJ_Crops();
        gp.obj[381].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[381].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[382] = new OBJ_Crops();
        gp.obj[382].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[382].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[383] = new OBJ_Crops();
        gp.obj[383].worldX = 43 * gp.tileSize; // Col 13
        gp.obj[383].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[384] = new OBJ_Crops();
        gp.obj[384].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[384].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[385] = new OBJ_Crops();
        gp.obj[385].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[385].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[386] = new OBJ_Crops();
        gp.obj[386].worldX = 43 * gp.tileSize; // Col 13
        gp.obj[386].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[387] = new OBJ_Crops();
        gp.obj[387].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[387].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[388] = new OBJ_Crops();
        gp.obj[388].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[388].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[389] = new OBJ_Crops();
        gp.obj[389].worldX = 43 * gp.tileSize; // Col 13
        gp.obj[389].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[390] = new OBJ_Crops();
        gp.obj[390].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[390].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[391] = new OBJ_Crops();
        gp.obj[391].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[391].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[392] = new OBJ_Water();
        gp.obj[392].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[392].worldY = 1 * gp.tileSize; // Row 36

        gp.obj[393] = new OBJ_Tlfall();
        gp.obj[393].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[393].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[394] = new OBJ_Tlfall();
        gp.obj[394].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[394].worldY = 1 * gp.tileSize; // Row 36

        gp.obj[395] = new OBJ_Tlfall();
        gp.obj[395].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[395].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[396] = new OBJ_Waterfall();
        gp.obj[396].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[396].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[397] = new OBJ_Waterfall();
        gp.obj[397].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[397].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[398] = new OBJ_Waterfall();
        gp.obj[398].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[398].worldY = 0 * gp.tileSize; // Row 36


        gp.obj[399] = new OBJ_Waterfall();
        gp.obj[399].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[399].worldY = 1 * gp.tileSize; // Row 36

        gp.obj[400] = new OBJ_Waterfall();
        gp.obj[400].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[400].worldY = 1 * gp.tileSize; // Row 36

        gp.obj[401] = new OBJ_Waterfall();
        gp.obj[401].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[401].worldY = 1 * gp.tileSize; // Row 36

        gp.obj[402] = new OBJ_Waterfall();
        gp.obj[402].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[402].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[403] = new OBJ_Waterfall();
        gp.obj[403].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[403].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[404] = new OBJ_Waterfall();
        gp.obj[404].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[404].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[405] = new OBJ_Waterfall();
        gp.obj[405].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[405].worldY = 3 * gp.tileSize; // Row 36

        gp.obj[406] = new OBJ_Waterfall();
        gp.obj[406].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[406].worldY = 3 * gp.tileSize; // Row 36

        gp.obj[407] = new OBJ_Waterfall();
        gp.obj[407].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[407].worldY = 3 * gp.tileSize; // Row 36

        gp.obj[408] = new OBJ_Waterfall();
        gp.obj[408].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[408].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[409] = new OBJ_Waterfall();
        gp.obj[409].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[409].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[410] = new OBJ_Waterfall();
        gp.obj[410].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[410].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[411] = new OBJ_Waterfall();
        gp.obj[411].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[411].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[412] = new OBJ_Waterfall();
        gp.obj[412].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[412].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[413] = new OBJ_Waterfall();
        gp.obj[413].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[413].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[414] = new OBJ_Waterfall();
        gp.obj[414].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[414].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[415] = new OBJ_Waterfall();
        gp.obj[415].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[415].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[416] = new OBJ_Waterfall();
        gp.obj[416].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[416].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[417] = new OBJ_Waterfall();
        gp.obj[417].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[417].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[418] = new OBJ_Waterfall();
        gp.obj[418].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[418].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[419] = new OBJ_Waterfall();
        gp.obj[419].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[419].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[420] = new OBJ_Waterfall();
        gp.obj[420].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[420].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[421] = new OBJ_Waterfall();
        gp.obj[421].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[421].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[422] = new OBJ_Waterfall();
        gp.obj[422].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[422].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[423] = new OBJ_Waterfall();
        gp.obj[423].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[423].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[424] = new OBJ_Waterfall();
        gp.obj[424].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[424].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[425] = new OBJ_Waterfall();
        gp.obj[425].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[425].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[426] = new OBJ_Waterfall();
        gp.obj[426].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[426].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[427] = new OBJ_Waterfall();
        gp.obj[427].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[427].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[428] = new OBJ_Waterfall();
        gp.obj[428].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[428].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[429] = new OBJ_Waterfall();
        gp.obj[429].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[429].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[430] = new OBJ_Waterfall();
        gp.obj[430].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[430].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[431] = new OBJ_Waterfall();
        gp.obj[431].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[431].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[432] = new OBJ_Waterfall();
        gp.obj[432].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[432].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[433] = new OBJ_Waterfall();
        gp.obj[433].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[433].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[434] = new OBJ_Waterfall();
        gp.obj[434].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[434].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[435] = new OBJ_Waterfall();
        gp.obj[435].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[435].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[436] = new OBJ_Waterfall();
        gp.obj[436].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[436].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[437] = new OBJ_Waterfall();
        gp.obj[437].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[437].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[438] = new OBJ_Waterfall();
        gp.obj[438].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[438].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[439] = new OBJ_Waterfall();
        gp.obj[439].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[439].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[440] = new OBJ_Waterfall();
        gp.obj[440].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[440].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[441] = new OBJ_Waterfall();
        gp.obj[441].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[441].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[442] = new OBJ_Waterfall();
        gp.obj[442].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[442].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[443] = new OBJ_Waterfall();
        gp.obj[443].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[443].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[444] = new OBJ_Waterfall();
        gp.obj[444].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[444].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[445] = new OBJ_Waterfall();
        gp.obj[445].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[445].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[446] = new OBJ_Waterfall();
        gp.obj[446].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[446].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[447] = new OBJ_Waterfall();
        gp.obj[447].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[447].worldY = 17 * gp.tileSize; // Row 36

        gp.obj[448] = new OBJ_Waterfall();
        gp.obj[448].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[448].worldY = 17 * gp.tileSize; // Row 36

        gp.obj[449] = new OBJ_Waterfall();
        gp.obj[449].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[449].worldY = 17 * gp.tileSize; // Row 36

        gp.obj[450] = new OBJ_Waterfall();
        gp.obj[450].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[450].worldY = 18 * gp.tileSize; // Row 36

        gp.obj[451] = new OBJ_Waterfall();
        gp.obj[451].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[451].worldY = 18 * gp.tileSize; // Row 36

        gp.obj[452] = new OBJ_Waterfall();
        gp.obj[452].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[452].worldY = 18 * gp.tileSize; // Row 36

        gp.obj[453] = new OBJ_Waterfall();
        gp.obj[453].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[453].worldY = 19 * gp.tileSize; // Row 36

        gp.obj[454] = new OBJ_Waterfall();
        gp.obj[454].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[454].worldY = 19 * gp.tileSize; // Row 36

        gp.obj[455] = new OBJ_Waterfall();
        gp.obj[455].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[455].worldY = 19 * gp.tileSize; // Row 36

        gp.obj[446] = new OBJ_Waterfall();
        gp.obj[446].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[446].worldY = 20 * gp.tileSize; // Row 36

        gp.obj[447] = new OBJ_Waterfall();
        gp.obj[447].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[447].worldY = 20 * gp.tileSize; // Row 36

        gp.obj[448] = new OBJ_Waterfall();
        gp.obj[448].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[448].worldY = 20 * gp.tileSize; // Row 368

        gp.obj[449] = new OBJ_Waterfall();
        gp.obj[449].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[449].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[450] = new OBJ_Waterfall();
        gp.obj[450].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[450].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[451] = new OBJ_Waterfall();
        gp.obj[451].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[451].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[452] = new OBJ_Waterfall();
        gp.obj[452].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[452].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[453] = new OBJ_Waterfall();
        gp.obj[453].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[453].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[454] = new OBJ_Waterfall();
        gp.obj[454].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[454].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[455] = new OBJ_Waterfall();
        gp.obj[455].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[455].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[456] = new OBJ_Waterfall();
        gp.obj[456].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[456].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[457] = new OBJ_Waterfall();
        gp.obj[457].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[457].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[458] = new OBJ_Waterfall();
        gp.obj[458].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[458].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[459] = new OBJ_Waterfall();
        gp.obj[459].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[459].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[460] = new OBJ_Waterfall();
        gp.obj[460].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[460].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[461] = new OBJ_Splash();
        gp.obj[461].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[461].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[462] = new OBJ_Splash();
        gp.obj[462].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[462].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[463] = new OBJ_Splash();
        gp.obj[463].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[463].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[464] = new OBJ_Splash();
        gp.obj[464].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[464].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[465] = new OBJ_Splash();
        gp.obj[465].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[465].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[466] = new OBJ_Splash();
        gp.obj[466].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[466].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[467] = new OBJ_Waterfall();
        gp.obj[467].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[467].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[468] = new OBJ_Waterfall();
        gp.obj[468].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[468].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[469] = new OBJ_Waterfall();
        gp.obj[469].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[469].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[470] = new OBJ_Leftwaterfall();
        gp.obj[470].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[470].worldY = 3 * gp.tileSize; // Row 36

        gp.obj[471] = new OBJ_Leftwaterfall();
        gp.obj[471].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[471].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[472] = new OBJ_Leftwaterfall();
        gp.obj[472].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[472].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[473] = new OBJ_Leftwaterfall();
        gp.obj[473].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[473].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[474] = new OBJ_Leftwaterfall();
        gp.obj[474].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[474].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[475] = new OBJ_Leftwaterfall();
        gp.obj[475].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[475].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[476] = new OBJ_Leftwaterfall();
        gp.obj[476].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[476].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[477] = new OBJ_Leftwaterfall();
        gp.obj[477].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[477].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[478] = new OBJ_Leftwaterfall();
        gp.obj[478].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[478].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[479] = new OBJ_Leftwaterfall();
        gp.obj[479].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[479].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[480] = new OBJ_Leftwaterfall();
        gp.obj[480].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[480].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[481] = new OBJ_Leftwaterfall();
        gp.obj[481].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[481].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[482] = new OBJ_Leftwaterfall();
        gp.obj[482].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[482].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[483] = new OBJ_Leftwaterfall();
        gp.obj[483].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[483].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[484] = new OBJ_Leftwaterfall();
        gp.obj[484].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[484].worldY = 17 * gp.tileSize; // Row 36

        gp.obj[485] = new OBJ_Leftwaterfall();
        gp.obj[485].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[485].worldY = 18 * gp.tileSize; // Row 36

        gp.obj[486] = new OBJ_Leftwaterfall();
        gp.obj[486].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[486].worldY = 19 * gp.tileSize; // Row 36

        gp.obj[487] = new OBJ_Leftwaterfall();
        gp.obj[487].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[487].worldY = 20 * gp.tileSize; // Row 36

        gp.obj[488] = new OBJ_Leftwaterfall();
        gp.obj[488].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[488].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[489] = new OBJ_Leftwaterfall();
        gp.obj[489].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[489].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[490] = new OBJ_Trfall();
        gp.obj[490].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[490].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[491] = new OBJ_Trfall();
        gp.obj[491].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[491].worldY = 1 * gp.tileSize; // Row 36

        gp.obj[492] = new OBJ_Trfall();
        gp.obj[492].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[492].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[493] = new OBJ_Rightwaterfall();
        gp.obj[493].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[493].worldY = 3 * gp.tileSize; // Row 36

        gp.obj[494] = new OBJ_Rightwaterfall();
        gp.obj[494].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[494].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[495] = new OBJ_Rightwaterfall();
        gp.obj[495].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[495].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[496] = new OBJ_Rightwaterfall();
        gp.obj[496].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[496].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[497] = new OBJ_Rightwaterfall();
        gp.obj[497].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[497].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[498] = new OBJ_Rightwaterfall();
        gp.obj[498].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[498].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[499] = new OBJ_Rightwaterfall();
        gp.obj[499].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[499].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[500] = new OBJ_Rightwaterfall();
        gp.obj[500].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[500].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[501] = new OBJ_Rightwaterfall();
        gp.obj[501].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[501].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[502] = new OBJ_Rightwaterfall();
        gp.obj[502].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[502].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[503] = new OBJ_Rightwaterfall();
        gp.obj[503].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[503].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[504] = new OBJ_Rightwaterfall();
        gp.obj[504].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[504].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[505] = new OBJ_Rightwaterfall();
        gp.obj[505].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[505].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[506] = new OBJ_Rightwaterfall();
        gp.obj[506].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[506].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[507] = new OBJ_Rightwaterfall();
        gp.obj[507].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[507].worldY = 17 * gp.tileSize; // Row 36

        gp.obj[508] = new OBJ_Rightwaterfall();
        gp.obj[508].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[508].worldY = 18 * gp.tileSize; // Row 36

        gp.obj[509] = new OBJ_Rightwaterfall();
        gp.obj[509].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[509].worldY = 19 * gp.tileSize; // Row 36

        gp.obj[510] = new OBJ_Rightwaterfall();
        gp.obj[510].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[510].worldY = 20 * gp.tileSize; // Row 36

        gp.obj[511] = new OBJ_Rightwaterfall();
        gp.obj[511].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[511].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[512] = new OBJ_Rightwaterfall();
        gp.obj[512].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[512].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[513] = new OBJ_Leftwaterfall();
        gp.obj[513].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[513].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[514] = new OBJ_Leftwaterfall();
        gp.obj[514].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[514].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[515] = new OBJ_Leftwaterfall();
        gp.obj[515].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[515].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[516] = new OBJ_Leftwaterfall();
        gp.obj[516].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[516].worldY = 30 * gp.tileSize; // Row 36

        gp.obj[517] = new OBJ_Splash();
        gp.obj[517].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[517].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[518] = new OBJ_Splash();
        gp.obj[518].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[518].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[519] = new OBJ_Splash();
        gp.obj[519].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[519].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[520] = new OBJ_Splash();
        gp.obj[520].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[520].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[521] = new OBJ_Splash();
        gp.obj[521].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[521].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[522] = new OBJ_Splash();
        gp.obj[522].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[522].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[523] = new OBJ_Splash();
        gp.obj[523].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[523].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[518] = new OBJ_Splash();
        gp.obj[518].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[518].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[519] = new OBJ_Splash();
        gp.obj[519].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[519].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[520] = new OBJ_Rightwaterfall();
        gp.obj[520].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[520].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[521] = new OBJ_Rightwaterfall();
        gp.obj[521].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[521].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[522] = new OBJ_Rightwaterfall();
        gp.obj[522].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[522].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[523] = new OBJ_Rightwaterfall();
        gp.obj[523].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[523].worldY = 30 * gp.tileSize; // Row 36

        gp.obj[524] = new OBJ_Waterfall();
        gp.obj[524].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[524].worldY = 30 * gp.tileSize; // Row 36

        gp.obj[525] = new OBJ_Waterfall();
        gp.obj[525].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[525].worldY = 30 * gp.tileSize; // Row 36

        gp.obj[526] = new OBJ_Waterfall();
        gp.obj[526].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[526].worldY = 30 * gp.tileSize; // Row 36

        gp.obj[526] = new OBJ_Splash();
        gp.obj[526].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[526].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[527] = new OBJ_Splash();
        gp.obj[527].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[527].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[528] = new OBJ_Splash();
        gp.obj[528].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[528].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[529] = new OBJ_Fences();
        gp.obj[529].worldX = 34 * gp.tileSize; // Col 13
        gp.obj[529].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[530] = new OBJ_Fences();
        gp.obj[530].worldX = 34 * gp.tileSize; // Col 13
        gp.obj[530].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[531] = new OBJ_Fences();
        gp.obj[531].worldX = 37 * gp.tileSize; // Col 13
        gp.obj[531].worldY = 39 * gp.tileSize; // Row 36

        gp.obj[532] = new OBJ_Water();
        gp.obj[532].worldX = 35 * gp.tileSize; // Col 13
        gp.obj[532].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[533] = new OBJ_Water();
        gp.obj[533].worldX = 34 * gp.tileSize; // Col 13
        gp.obj[533].worldY = 41 * gp.tileSize; // Row 36

        gp.obj[534] = new OBJ_Water();
        gp.obj[534].worldX = 33 * gp.tileSize; // Col 13
        gp.obj[534].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[535] = new OBJ_Water();
        gp.obj[535].worldX = 32 * gp.tileSize; // Col 13
        gp.obj[535].worldY = 43 * gp.tileSize; // Row 36

        gp.obj[536] = new OBJ_Water();
        gp.obj[536].worldX = 31 * gp.tileSize; // Col 13
        gp.obj[536].worldY = 44 * gp.tileSize; // Row 36

        gp.obj[537] = new OBJ_Water();
        gp.obj[537].worldX = 30 * gp.tileSize; // Col 13
        gp.obj[537].worldY = 45 * gp.tileSize; // Row 36

        gp.obj[538] = new OBJ_Water();
        gp.obj[538].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[538].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[539] = new OBJ_Fences();
        gp.obj[539].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[539].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[540] = new OBJ_Fences();
        gp.obj[540].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[540].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[541] = new OBJ_Sand();
        gp.obj[541].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[541].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[542] = new OBJ_Grass();
        gp.obj[542].worldX = 46 * gp.tileSize; // Col 13
        gp.obj[542].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[543] = new OBJ_Grass();
        gp.obj[543].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[543].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[544] = new OBJ_Grass();
        gp.obj[544].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[544].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[545] = new OBJ_Grass();
        gp.obj[545].worldX = 43 * gp.tileSize; // Col 13
        gp.obj[545].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[546] = new OBJ_Grass();
        gp.obj[546].worldX = 42 * gp.tileSize; // Col 13
        gp.obj[546].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[547] = new OBJ_Grass();
        gp.obj[547].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[547].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[548] = new OBJ_Grass();
        gp.obj[548].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[548].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[549] = new OBJ_Grass();
        gp.obj[549].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[549].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[550] = new OBJ_Grass();
        gp.obj[550].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[550].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[551] = new OBJ_Grass();
        gp.obj[551].worldX = 37 * gp.tileSize; // Col 13
        gp.obj[551].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[552] = new OBJ_Grass();
        gp.obj[552].worldX = 36 * gp.tileSize; // Col 13
        gp.obj[552].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[553] = new OBJ_Grass();
        gp.obj[553].worldX = 35 * gp.tileSize; // Col 13
        gp.obj[553].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[554] = new OBJ_Grass();
        gp.obj[554].worldX = 34 * gp.tileSize; // Col 13
        gp.obj[554].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[555] = new OBJ_Grass();
        gp.obj[555].worldX = 33 * gp.tileSize; // Col 13
        gp.obj[555].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[556] = new OBJ_Grass();
        gp.obj[556].worldX = 32 * gp.tileSize; // Col 13
        gp.obj[556].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[557] = new OBJ_Grass();
        gp.obj[557].worldX = 31 * gp.tileSize; // Col 13
        gp.obj[557].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[558] = new OBJ_Grass();
        gp.obj[558].worldX = 30 * gp.tileSize; // Col 13
        gp.obj[558].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[559] = new OBJ_Grass();
        gp.obj[559].worldX = 29 * gp.tileSize; // Col 13
        gp.obj[559].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[560] = new OBJ_Grass();
        gp.obj[560].worldX = 28 * gp.tileSize; // Col 13
        gp.obj[560].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[561] = new OBJ_TreeYellow();
        gp.obj[561].worldX = 28 * gp.tileSize; // Col 13
        gp.obj[561].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[562] = new OBJ_GreenTree();
        gp.obj[562].worldX = 30 * gp.tileSize; // Col 13
        gp.obj[562].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[563] = new OBJ_TreeOrange();
        gp.obj[563].worldX = 32 * gp.tileSize; // Col 13
        gp.obj[563].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[564] = new OBJ_TreeYellow();
        gp.obj[564].worldX = 34 * gp.tileSize; // Col 13
        gp.obj[564].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[565] = new OBJ_GreenTree();
        gp.obj[565].worldX = 36 * gp.tileSize; // Col 13
        gp.obj[565].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[566] = new OBJ_TreeOrange();
        gp.obj[566].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[566].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[567] = new OBJ_TreeYellow();
        gp.obj[567].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[567].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[568] = new OBJ_GreenTree();
        gp.obj[568].worldX = 42 * gp.tileSize; // Col 13
        gp.obj[568].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[569] = new OBJ_TreeOrange();
        gp.obj[569].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[569].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[570] = new OBJ_Sand();
        gp.obj[570].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[570].worldY = 24 * gp.tileSize; // Row 36

        gp.obj[571] = new OBJ_Sand();
        gp.obj[571].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[571].worldY = 23 * gp.tileSize; // Row 36

        gp.obj[572] = new OBJ_Grass();
        gp.obj[572].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[572].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[573] = new OBJ_Grass();
        gp.obj[573].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[573].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[573] = new OBJ_Grass();
        gp.obj[573].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[573].worldY = 20 * gp.tileSize; // Row 36

        gp.obj[574] = new OBJ_Grass();
        gp.obj[574].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[574].worldY = 19 * gp.tileSize; // Row 36

        gp.obj[575] = new OBJ_Grass();
        gp.obj[575].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[575].worldY = 18 * gp.tileSize; // Row 36

        gp.obj[576] = new OBJ_Grass();
        gp.obj[576].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[576].worldY = 17 * gp.tileSize; // Row 36

        gp.obj[577] = new OBJ_Grass();
        gp.obj[577].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[577].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[578] = new OBJ_Grass();
        gp.obj[578].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[578].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[579] = new OBJ_Grass();
        gp.obj[579].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[579].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[580] = new OBJ_Grass();
        gp.obj[580].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[580].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[581] = new OBJ_Grass();
        gp.obj[581].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[581].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[582] = new OBJ_Grass();
        gp.obj[582].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[582].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[583] = new OBJ_Grass();
        gp.obj[583].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[583].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[584] = new OBJ_Grass();
        gp.obj[584].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[584].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[585] = new OBJ_Grass();
        gp.obj[585].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[585].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[586] = new OBJ_Grass();
        gp.obj[586].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[586].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[587] = new OBJ_Grass();
        gp.obj[587].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[587].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[588] = new OBJ_Grass();
        gp.obj[588].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[588].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[589] = new OBJ_Grass();
        gp.obj[589].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[589].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[590] = new OBJ_Grass();
        gp.obj[590].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[590].worldY = 3 * gp.tileSize; // Row 36

        gp.obj[591] = new OBJ_Grass();
        gp.obj[591].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[591].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[592] = new OBJ_Grass();
        gp.obj[592].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[592].worldY = 1 * gp.tileSize; // Row 36

        gp.obj[593] = new OBJ_Grass();
        gp.obj[593].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[593].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[594] = new OBJ_Grass();
        gp.obj[594].worldX = 1 * gp.tileSize; // Col 13
        gp.obj[594].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[595] = new OBJ_Grass();
        gp.obj[595].worldX = 2 * gp.tileSize; // Col 13
        gp.obj[595].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[596] = new OBJ_Grass();
        gp.obj[596].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[596].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[597] = new OBJ_Grass();
        gp.obj[597].worldX = 4 * gp.tileSize; // Col 13
        gp.obj[597].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[598] = new OBJ_Grass();
        gp.obj[598].worldX = 5 * gp.tileSize; // Col 13
        gp.obj[598].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[599] = new OBJ_Grass();
        gp.obj[599].worldX = 6 * gp.tileSize; // Col 13
        gp.obj[599].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[600] = new OBJ_Grass();
        gp.obj[600].worldX = 7 * gp.tileSize; // Col 13
        gp.obj[600].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[601] = new OBJ_Grass();
        gp.obj[601].worldX = 8 * gp.tileSize; // Col 13
        gp.obj[601].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[602] = new OBJ_Grass();
        gp.obj[602].worldX = 9 * gp.tileSize; // Col 13
        gp.obj[602].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[603] = new OBJ_Grass();
        gp.obj[603].worldX = 10 * gp.tileSize; // Col 13
        gp.obj[603].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[604] = new OBJ_Grass();
        gp.obj[604].worldX = 11 * gp.tileSize; // Col 13
        gp.obj[604].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[605] = new OBJ_Grass();
        gp.obj[605].worldX = 12 * gp.tileSize; // Col 13
        gp.obj[605].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[606] = new OBJ_Grass();
        gp.obj[606].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[606].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[607] = new OBJ_Grass();
        gp.obj[607].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[607].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[608] = new OBJ_Grass();
        gp.obj[608].worldX = 15 * gp.tileSize; // Col 13
        gp.obj[608].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[609] = new OBJ_Grass();
        gp.obj[609].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[609].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[610] = new OBJ_Grass();
        gp.obj[610].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[610].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[611] = new OBJ_Grass();
        gp.obj[611].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[611].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[612] = new OBJ_Grass();
        gp.obj[612].worldX = 19 * gp.tileSize; // Col 13
        gp.obj[612].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[613] = new OBJ_Grass();
        gp.obj[613].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[613].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[614] = new OBJ_Grass();
        gp.obj[614].worldX = 21 * gp.tileSize; // Col 13
        gp.obj[614].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[615] = new OBJ_Grass();
        gp.obj[615].worldX = 22 * gp.tileSize; // Col 13
        gp.obj[615].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[616] = new OBJ_Grass();
        gp.obj[616].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[616].worldY = 21 * gp.tileSize; // Row 36

        NPC_Chief chief = new NPC_Chief(gp);
        chief.worldX = 15 * gp.tileSize;  // <-- set to wherever Chief stands
        chief.worldY = 10 * gp.tileSize;
        gp.obj[10] = chief;

        NPC_Ranger ranger = new NPC_Ranger(gp);
        ranger.worldX = 20 * gp.tileSize; // <-- adjust as needed
        ranger.worldY = 10 * gp.tileSize;
        gp.obj[11] = ranger;

        NPC_Frank frank = new NPC_Frank(gp);
        frank.worldX = 25 * gp.tileSize;  // <-- adjust as needed
        frank.worldY = 10 * gp.tileSize;
        gp.obj[12] = frank;





}
}