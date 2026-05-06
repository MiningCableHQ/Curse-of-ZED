package Objects;

import Entities.Characters.*;
import Entities.Enemies.*;
import Main.GamePanel;
import Main.GameStateManager;
public class Map1Setter {
    GamePanel gp;

    public Map1Setter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjects() {
        GameStateManager.Map1Phase phase = GameStateManager.get().map1Phase;

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


        gp.obj[66] = new OBJ_RedHouse();
        gp.obj[66].worldX = 32 * gp.tileSize; // Column 10
        gp.obj[66].worldY = 6 * gp.tileSize; // Row 10


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


        gp.obj[75] = new OBJ_BlueHouse();
        gp.obj[75].worldX = 32 * gp.tileSize; // Column 10
        gp.obj[75].worldY = 11 * gp.tileSize; // Row 10


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


        gp.obj[84] = new OBJ_RedHouse();
        gp.obj[84].worldX = 32 * gp.tileSize; // Column 10
        gp.obj[84].worldY = 16 * gp.tileSize; // Row 10

        gp.obj[85] = new OBJ_Bush_green();
        gp.obj[85].worldX = 36 * gp.tileSize; // Column 10
        gp.obj[85].worldY = 16 * gp.tileSize; // Row 10

        gp.obj[86] = new OBJ_Bush_green();
        gp.obj[86].worldX = 36 * gp.tileSize; // Column 10
        gp.obj[86].worldY = 17 * gp.tileSize; // Row 10

        gp.obj[87] = new OBJ_Bush_green();
        gp.obj[87].worldX = 36 * gp.tileSize; // Column 10
        gp.obj[87].worldY = 18 * gp.tileSize; // Row 10

        gp.obj[88] = new OBJ_Bush_green();
        gp.obj[88].worldX = 36 * gp.tileSize; // Column 10
        gp.obj[88].worldY = 19 * gp.tileSize; // Row 10

        gp.obj[89] = new OBJ_BlueHouse();
        gp.obj[89].worldX = 38 * gp.tileSize; // Column 10
        gp.obj[89].worldY = 16 * gp.tileSize; // Row 10

        gp.obj[90] = new OBJ_RedHouse();
        gp.obj[90].worldX = 42 * gp.tileSize; // Column 10
        gp.obj[90].worldY = 16 * gp.tileSize; // Row 10

        gp.obj[91] = new OBJ_Bush_green();
        gp.obj[91].worldX = 46 * gp.tileSize; // Column 10
        gp.obj[91].worldY = 16 * gp.tileSize; // Row 10

        gp.obj[92] = new OBJ_Bush_green();
        gp.obj[92].worldX = 46 * gp.tileSize; // Column 10
        gp.obj[92].worldY = 17 * gp.tileSize; // Row 10

        gp.obj[93] = new OBJ_Bush_green();
        gp.obj[93].worldX = 46 * gp.tileSize; // Column 10
        gp.obj[93].worldY = 18 * gp.tileSize; // Row 10

        gp.obj[94] = new OBJ_Bush_green();
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



        gp.obj[186] = new OBJ_Map1Fence();
        gp.obj[186].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[186].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[187] = new OBJ_FenceLeft();
        gp.obj[187].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[187].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[188] = new OBJ_FenceLeft();
        gp.obj[188].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[188].worldY = 35 * gp.tileSize; // Row 36

        gp.obj[189] = new OBJ_FenceLeft();
        gp.obj[189].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[189].worldY = 34 * gp.tileSize; // Row 36

        gp.obj[190] = new OBJ_FenceLeft();
        gp.obj[190].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[190].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[191] = new OBJ_FenceLeft();
        gp.obj[191].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[191].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[192] = new OBJ_FenceLeft();
        gp.obj[192].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[192].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[193] = new OBJ_FenceLeft();
        gp.obj[193].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[193].worldY = 30 * gp.tileSize; // Row 36

        gp.obj[194] = new OBJ_FenceLeft();
        gp.obj[194].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[194].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[195] = new OBJ_FenceLeft();
        gp.obj[195].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[195].worldY = 28 * gp.tileSize; // Row 36

        gp.obj[196] = new OBJ_FenceLeft();
        gp.obj[196].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[196].worldY = 27 * gp.tileSize; // Row 36

        gp.obj[197] = new OBJ_FenceLeft();
        gp.obj[197].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[197].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[198] = new OBJ_Top_Lfence();
        gp.obj[198].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[198].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[199] = new OBJ_Map1Fence();
        gp.obj[199].worldX = 1 * gp.tileSize; // Col 13
        gp.obj[199].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[200] = new OBJ_Map1Fence();
        gp.obj[200].worldX = 2 * gp.tileSize; // Col 13
        gp.obj[200].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[201] = new OBJ_Map1Fence();
        gp.obj[201].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[201].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[202] = new OBJ_Map1Fence();
        gp.obj[202].worldX = 4 * gp.tileSize; // Col 13
        gp.obj[202].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[203] = new OBJ_Map1Fence();
        gp.obj[203].worldX = 5 * gp.tileSize; // Col 13
        gp.obj[203].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[204] = new OBJ_Map1Fence();
        gp.obj[204].worldX = 6 * gp.tileSize; // Col 13
        gp.obj[204].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[205] = new OBJ_Map1Fence();
        gp.obj[205].worldX = 7 * gp.tileSize; // Col 13
        gp.obj[205].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[206] = new OBJ_Map1Fence();
        gp.obj[206].worldX = 8 * gp.tileSize; // Col 13
        gp.obj[206].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[207] = new OBJ_Map1Fence();
        gp.obj[207].worldX = 9 * gp.tileSize; // Col 13
        gp.obj[207].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[208] = new OBJ_Map1Fence();
        gp.obj[208].worldX = 10 * gp.tileSize; // Col 13
        gp.obj[208].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[209] = new OBJ_Map1Fence();
        gp.obj[209].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[209].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[210] = new OBJ_Map1Fence();
        gp.obj[210].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[210].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[211] = new OBJ_Map1Fence();
        gp.obj[211].worldX = 15 * gp.tileSize; // Col 13
        gp.obj[211].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[212] = new OBJ_Map1Fence();
        gp.obj[212].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[212].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[213] = new OBJ_Map1Fence();
        gp.obj[213].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[213].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[214] = new OBJ_Map1Fence();
        gp.obj[214].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[214].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[215] = new OBJ_Map1Fence();
        gp.obj[215].worldX = 19 * gp.tileSize; // Col 13
        gp.obj[215].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[216] = new OBJ_Top_Rfence();
        gp.obj[216].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[216].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[217] = new OBJ_FenceRight();
        gp.obj[217].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[217].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[218] = new OBJ_FenceRight();
        gp.obj[218].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[218].worldY = 27 * gp.tileSize; // Row 36

        gp.obj[219] = new OBJ_FenceRight();
        gp.obj[219].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[219].worldY = 28 * gp.tileSize; // Row 36

        gp.obj[220] = new OBJ_FenceRight();
        gp.obj[220].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[220].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[221] = new OBJ_FenceRight();
        gp.obj[221].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[221].worldY = 30 * gp.tileSize; // Row 36

        gp.obj[222] = new OBJ_FenceRight();
        gp.obj[222].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[222].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[223] = new OBJ_FenceRight();
        gp.obj[223].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[223].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[224] = new OBJ_FenceRight();
        gp.obj[224].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[224].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[225] = new OBJ_FenceRight();
        gp.obj[225].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[225].worldY = 34 * gp.tileSize; // Row 36

        gp.obj[226] = new OBJ_FenceRight();
        gp.obj[226].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[226].worldY = 35 * gp.tileSize; // Row 36

        gp.obj[227] = new OBJ_FenceRight();
        gp.obj[227].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[227].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[228] = new OBJ_Map1Fence();
        gp.obj[228].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[228].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[229] = new OBJ_Map1Fence();
        gp.obj[229].worldX = 1 * gp.tileSize; // Col 13
        gp.obj[229].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[230] = new OBJ_Map1Fence();
        gp.obj[230].worldX = 2 * gp.tileSize; // Col 13
        gp.obj[230].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[231] = new OBJ_Map1Fence();
        gp.obj[231].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[231].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[232] = new OBJ_Map1Fence();
        gp.obj[232].worldX = 4 * gp.tileSize; // Col 13
        gp.obj[232].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[233] = new OBJ_Map1Fence();
        gp.obj[233].worldX = 5 * gp.tileSize; // Col 13
        gp.obj[233].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[234] = new OBJ_Map1Fence();
        gp.obj[234].worldX = 6 * gp.tileSize; // Col 13
        gp.obj[234].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[235] = new OBJ_Map1Fence();
        gp.obj[235].worldX = 7 * gp.tileSize; // Col 13
        gp.obj[235].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[236] = new OBJ_Map1Fence();
        gp.obj[236].worldX = 8 * gp.tileSize; // Col 13
        gp.obj[236].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[237] = new OBJ_Map1Fence();
        gp.obj[237].worldX = 9 * gp.tileSize; // Col 13
        gp.obj[237].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[238] = new OBJ_Map1Fence();
        gp.obj[238].worldX = 10 * gp.tileSize; // Col 13
        gp.obj[238].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[239] = new OBJ_Map1Fence();
        gp.obj[239].worldX = 11 * gp.tileSize; // Col 13
        gp.obj[239].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[240] = new OBJ_Map1Fence();
        gp.obj[240].worldX = 12 * gp.tileSize; // Col 13
        gp.obj[240].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[241] = new OBJ_Map1Fence();
        gp.obj[241].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[241].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[242] = new OBJ_Map1Fence();
        gp.obj[242].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[242].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[243] = new OBJ_Map1Fence();
        gp.obj[243].worldX = 15 * gp.tileSize; // Col 13
        gp.obj[243].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[244] = new OBJ_Map1Fence();
        gp.obj[244].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[244].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[245] = new OBJ_Map1Fence();
        gp.obj[245].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[245].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[246] = new OBJ_Map1Fence();
        gp.obj[246].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[246].worldY = 37 * gp.tileSize; // Row 36



        gp.obj[248] = new OBJ_GrassPurpleEdge();
        gp.obj[248].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[248].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[249] = new OBJ_GrassPurpleEdge();
        gp.obj[249].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[249].worldY = 35 * gp.tileSize; // Row 36

        gp.obj[250] = new OBJ_GrassPurpleEdge();
        gp.obj[250].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[250].worldY = 34 * gp.tileSize; // Row 36

        gp.obj[251] = new OBJ_GrassPurpleEdge();
        gp.obj[251].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[251].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[252] = new OBJ_GrassPurpleEdge();
        gp.obj[252].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[252].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[252] = new OBJ_GrassPurpleEdge();
        gp.obj[252].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[252].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[253] = new OBJ_GrassPurpleEdge();
        gp.obj[253].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[253].worldY = 30* gp.tileSize; // Row 36

        gp.obj[254] = new OBJ_GrassPurpleEdge();
        gp.obj[254].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[254].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[255] = new OBJ_GrassPurpleEdge();
        gp.obj[255].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[255].worldY = 28 * gp.tileSize; // Row 36

        gp.obj[256] = new OBJ_GrassPurpleEdge();
        gp.obj[256].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[256].worldY = 27 * gp.tileSize; // Row 36

        gp.obj[257] = new OBJ_GrassPurpleEdge();
        gp.obj[257].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[257].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[258] = new OBJ_GrassPurpleEdge();
        gp.obj[258].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[258].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[259] = new OBJ_GrassPurpleEdge();
        gp.obj[259].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[259].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[260] = new OBJ_GrassPurpleEdge();
        gp.obj[260].worldX = 49 * gp.tileSize; // Col 13
        gp.obj[260].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[261] = new OBJ_GrassPurpleEdge();
        gp.obj[261].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[261].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[262] = new OBJ_GrassPurpleEdge();
        gp.obj[262].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[262].worldY = 20 * gp.tileSize; // Row 36

        gp.obj[263] = new OBJ_GrassPurpleEdge();
        gp.obj[263].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[263].worldY = 19 * gp.tileSize; // Row 36

        gp.obj[264] = new OBJ_GrassPurpleEdge();
        gp.obj[264].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[264].worldY = 18 * gp.tileSize; // Row 36

        gp.obj[265] = new OBJ_GrassPurpleEdge();
        gp.obj[265].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[265].worldY = 17 * gp.tileSize; // Row 36

        gp.obj[266] = new OBJ_GrassPurpleEdge();
        gp.obj[266].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[266].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[263] = new OBJ_GrassPurpleEdge();
        gp.obj[263].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[263].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[264] = new OBJ_FenceRight();
        gp.obj[264].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[264].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[265] = new OBJ_FenceRight();
        gp.obj[265].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[265].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[266] = new OBJ_FenceRight();
        gp.obj[266].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[266].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[267] = new OBJ_FenceRight();
        gp.obj[267].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[267].worldY = 11 * gp.tileSize; // Row 36



        gp.obj[269] = new OBJ_FenceRight();
        gp.obj[269].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[269].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[270] = new OBJ_FenceRight();
        gp.obj[270].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[270].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[271] = new OBJ_FenceRight();
        gp.obj[271].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[271].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[272] = new OBJ_FenceRight();
        gp.obj[272].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[272].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[273] = new OBJ_GrassPurpleEdge();
        gp.obj[273].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[273].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[274] = new OBJ_GrassPurpleEdge();
        gp.obj[274].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[274].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[275] = new OBJ_GrassPurpleEdge();
        gp.obj[275].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[275].worldY = 3 * gp.tileSize; // Row 36

        gp.obj[276] = new OBJ_GrassPurpleEdge();
        gp.obj[276].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[276].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[277] = new OBJ_GrassPurpleEdge();
        gp.obj[277].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[277].worldY = 1 * gp.tileSize; // Row 36

        gp.obj[278] = new OBJ_Map1Fence();
        gp.obj[278].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[278].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[279] = new OBJ_FenceRight();
        gp.obj[279].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[279].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[280] = new OBJ_FenceRight();
        gp.obj[280].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[280].worldY = 20 * gp.tileSize; // Row 36

        gp.obj[281] = new OBJ_FenceRight();
        gp.obj[281].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[281].worldY = 19 * gp.tileSize; // Row 36

        gp.obj[282] = new OBJ_FenceRight();
        gp.obj[282].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[282].worldY = 18 * gp.tileSize; // Row 36

        gp.obj[283] = new OBJ_FenceRight();
        gp.obj[283].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[283].worldY = 17 * gp.tileSize; // Row 36

        gp.obj[284] = new OBJ_FenceRight();
        gp.obj[284].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[284].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[285] = new OBJ_Map1Fence();
        gp.obj[285].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[285].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[286] = new OBJ_Top_Rfence();
        gp.obj[286].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[286].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[287] = new OBJ_FenceRight();
        gp.obj[287].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[287].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[288] = new OBJ_Map1Fence();
        gp.obj[288].worldX = 46 * gp.tileSize; // Col 13
        gp.obj[288].worldY = 4 * gp.tileSize; // Row 36





        gp.obj[292] = new OBJ_Map1Fence();
        gp.obj[292].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[292].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[293] = new OBJ_Map1Fence();
        gp.obj[293].worldX = 43 * gp.tileSize; // Col 13
        gp.obj[293].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[294] = new OBJ_Map1Fence();
        gp.obj[294].worldX = 42 * gp.tileSize; // Col 13
        gp.obj[294].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[295] = new OBJ_Map1Fence();
        gp.obj[295].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[295].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[296] = new OBJ_Map1Fence();
        gp.obj[296].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[296].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[297] = new OBJ_Map1Fence();
        gp.obj[297].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[297].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[298] = new OBJ_Map1Fence();
        gp.obj[298].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[298].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[299] = new OBJ_Map1Fence();
        gp.obj[299].worldX = 37 * gp.tileSize; // Col 13
        gp.obj[299].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[300] = new OBJ_Map1Fence();
        gp.obj[300].worldX = 36 * gp.tileSize; // Col 13
        gp.obj[300].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[301] = new OBJ_Map1Fence();
        gp.obj[301].worldX = 35 * gp.tileSize; // Col 13
        gp.obj[301].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[302] = new OBJ_Map1Fence();
        gp.obj[302].worldX = 34 * gp.tileSize; // Col 13
        gp.obj[302].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[303] = new OBJ_Map1Fence();
        gp.obj[303].worldX = 33 * gp.tileSize; // Col 13
        gp.obj[303].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[304] = new OBJ_Map1Fence();
        gp.obj[304].worldX = 32 * gp.tileSize; // Col 13
        gp.obj[304].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[305] = new OBJ_Map1Fence();
        gp.obj[305].worldX = 31 * gp.tileSize; // Col 13
        gp.obj[305].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[306] = new OBJ_Map1Fence();
        gp.obj[306].worldX = 30 * gp.tileSize; // Col 13
        gp.obj[306].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[307] = new OBJ_Map1Fence();
        gp.obj[307].worldX = 46 * gp.tileSize; // Col 13
        gp.obj[307].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[308] = new OBJ_Map1Fence();
        gp.obj[308].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[308].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[309] = new OBJ_Map1Fence();
        gp.obj[309].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[309].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[310] = new OBJ_Map1Fence();
        gp.obj[310].worldX = 43 * gp.tileSize; // Col 13
        gp.obj[310].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[311] = new OBJ_Map1Fence();
        gp.obj[311].worldX = 42 * gp.tileSize; // Col 13
        gp.obj[311].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[312] = new OBJ_Map1Fence();
        gp.obj[312].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[312].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[313] = new OBJ_Map1Fence();
        gp.obj[313].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[313].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[314] = new OBJ_Map1Fence();
        gp.obj[314].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[314].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[315] = new OBJ_Map1Fence();
        gp.obj[315].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[315].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[316] = new OBJ_FenceLeft();
        gp.obj[316].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[316].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[317] = new OBJ_FenceLeft();
        gp.obj[317].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[317].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[318] = new OBJ_FenceLeft();
        gp.obj[318].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[318].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[319] = new OBJ_FenceLeft();
        gp.obj[319].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[319].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[320] = new OBJ_FenceLeft();
        gp.obj[320].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[320].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[321] = new OBJ_FenceLeft();
        gp.obj[321].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[321].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[322] = new OBJ_FenceLeft();
        gp.obj[322].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[322].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[323] = new OBJ_FenceLeft();
        gp.obj[323].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[323].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[324] = new OBJ_FenceLeft();
        gp.obj[324].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[324].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[325] = new OBJ_FenceLeft();
        gp.obj[325].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[325].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[326] = new OBJ_Top_Lfence();
        gp.obj[326].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[326].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[327] = new OBJ_Map1Fence();
        gp.obj[327].worldX = 37 * gp.tileSize; // Col 13
        gp.obj[327].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[328] = new OBJ_Map1Fence();
        gp.obj[328].worldX = 36 * gp.tileSize; // Col 13
        gp.obj[328].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[329] = new OBJ_Map1Fence();
        gp.obj[329].worldX = 35 * gp.tileSize; // Col 13
        gp.obj[329].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[330] = new OBJ_Map1Fence();
        gp.obj[330].worldX = 34 * gp.tileSize; // Col 13
        gp.obj[330].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[331] = new OBJ_Map1Fence();
        gp.obj[331].worldX = 33 * gp.tileSize; // Col 13
        gp.obj[331].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[332] = new OBJ_Map1Fence();
        gp.obj[332].worldX = 32 * gp.tileSize; // Col 13
        gp.obj[332].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[333] = new OBJ_Map1Fence();
        gp.obj[333].worldX = 31 * gp.tileSize; // Col 13
        gp.obj[333].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[334] = new OBJ_Map1Fence();
        gp.obj[334].worldX = 30 * gp.tileSize; // Col 13
        gp.obj[334].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[335] = new OBJ_Map1Fence();
        gp.obj[335].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[335].worldY = 4 * gp.tileSize; // Row 36


        gp.obj[337] = new OBJ_Map1Fence();
        gp.obj[337].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[337].worldY = 4 * gp.tileSize; // Row 36


        gp.obj[339] = new OBJ_Map1Fence();
        gp.obj[339].worldX = 45 * gp.tileSize; // Col 13
        gp.obj[339].worldY = 4 * gp.tileSize; // Row 36


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

        gp.obj[392] = new OBJ_GrassPurpleEdge();
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




        gp.obj[538] = new OBJ_GrassPurpleEdge();
        gp.obj[538].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[538].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[539] = new OBJ_Map1Fence();
        gp.obj[539].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[539].worldY = 4 * gp.tileSize; // Row 36



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

        gp.obj[572] = new OBJ_Bush_green();
        gp.obj[572].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[572].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[573] = new OBJ_Bush_green();
        gp.obj[573].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[573].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[573] = new OBJ_Bush_green();
        gp.obj[573].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[573].worldY = 20 * gp.tileSize; // Row 36

        gp.obj[574] = new OBJ_Bush_green();
        gp.obj[574].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[574].worldY = 19 * gp.tileSize; // Row 36

        gp.obj[575] = new OBJ_Bush_green();
        gp.obj[575].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[575].worldY = 18 * gp.tileSize; // Row 36

        gp.obj[576] = new OBJ_Bush_green();
        gp.obj[576].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[576].worldY = 17 * gp.tileSize; // Row 36

        gp.obj[577] = new OBJ_Bush_green();
        gp.obj[577].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[577].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[578] = new OBJ_Bush_green();
        gp.obj[578].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[578].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[579] = new OBJ_Bush_green();
        gp.obj[579].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[579].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[580] = new OBJ_Bush_green();
        gp.obj[580].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[580].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[581] = new OBJ_Bush_green();
        gp.obj[581].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[581].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[582] = new OBJ_Bush_green();
        gp.obj[582].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[582].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[583] = new OBJ_Bush_green();
        gp.obj[583].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[583].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[584] = new OBJ_Bush_green();
        gp.obj[584].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[584].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[585] = new OBJ_Bush_green();
        gp.obj[585].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[585].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[586] = new OBJ_Bush_green();
        gp.obj[586].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[586].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[587] = new OBJ_Bush_green();
        gp.obj[587].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[587].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[588] = new OBJ_Bush_green();
        gp.obj[588].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[588].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[589] = new OBJ_Bush_green();
        gp.obj[589].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[589].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[590] = new OBJ_Bush_green();
        gp.obj[590].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[590].worldY = 3 * gp.tileSize; // Row 36

        gp.obj[591] = new OBJ_Bush_green();
        gp.obj[591].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[591].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[592] = new OBJ_Bush_green();
        gp.obj[592].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[592].worldY = 1 * gp.tileSize; // Row 36

        gp.obj[593] = new OBJ_Bush_green();
        gp.obj[593].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[593].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[594] = new OBJ_Bush_green();
        gp.obj[594].worldX = 1 * gp.tileSize; // Col 13
        gp.obj[594].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[595] = new OBJ_Bush_green();
        gp.obj[595].worldX = 2 * gp.tileSize; // Col 13
        gp.obj[595].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[596] = new OBJ_Bush_green();
        gp.obj[596].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[596].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[597] = new OBJ_Bush_green();
        gp.obj[597].worldX = 4 * gp.tileSize; // Col 13
        gp.obj[597].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[598] = new OBJ_Bush_green();
        gp.obj[598].worldX = 5 * gp.tileSize; // Col 13
        gp.obj[598].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[599] = new OBJ_Bush_green();
        gp.obj[599].worldX = 6 * gp.tileSize; // Col 13
        gp.obj[599].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[600] = new OBJ_Bush_green();
        gp.obj[600].worldX = 7 * gp.tileSize; // Col 13
        gp.obj[600].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[601] = new OBJ_Bush_green();
        gp.obj[601].worldX = 8 * gp.tileSize; // Col 13
        gp.obj[601].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[602] = new OBJ_Bush_green();
        gp.obj[602].worldX = 9 * gp.tileSize; // Col 13
        gp.obj[602].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[603] = new OBJ_Bush_green();
        gp.obj[603].worldX = 10 * gp.tileSize; // Col 13
        gp.obj[603].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[604] = new OBJ_Bush_green();
        gp.obj[604].worldX = 11 * gp.tileSize; // Col 13
        gp.obj[604].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[605] = new OBJ_Bush_green();
        gp.obj[605].worldX = 12 * gp.tileSize; // Col 13
        gp.obj[605].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[606] = new OBJ_Bush_green();
        gp.obj[606].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[606].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[607] = new OBJ_Bush_green();
        gp.obj[607].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[607].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[608] = new OBJ_Bush_green();
        gp.obj[608].worldX = 15 * gp.tileSize; // Col 13
        gp.obj[608].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[609] = new OBJ_Bush_green();
        gp.obj[609].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[609].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[610] = new OBJ_Bush_green();
        gp.obj[610].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[610].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[611] = new OBJ_Bush_green();
        gp.obj[611].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[611].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[612] = new OBJ_Bush_green();
        gp.obj[612].worldX = 19 * gp.tileSize; // Col 13
        gp.obj[612].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[613] = new OBJ_Bush_green();
        gp.obj[613].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[613].worldY = 0 * gp.tileSize; // Row 36


        gp.obj[614] = new OBJ_Grass();
        gp.obj[614].worldX = 22 * gp.tileSize; // Col 13
        gp.obj[614].worldY = 0 * gp.tileSize; // Row 36


        gp.obj[615] = new OBJ_Bush_green();
        gp.obj[615].worldX = 21 * gp.tileSize; // Col 13
        gp.obj[615].worldY = 0 * gp.tileSize; // Row 36

        gp.obj[616] = new OBJ_Bush_green();
        gp.obj[616].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[616].worldY = 21 * gp.tileSize; // Row 36


        gp.obj[617] = new OBJ_PurpleGrass();
        gp.obj[617].worldX = 49 * gp.tileSize; // Col 13
        gp.obj[617].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[618] = new OBJ_PurpleGrass();
        gp.obj[618].worldX = 49 * gp.tileSize; // Col 13
        gp.obj[618].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[619] = new OBJ_Bush();
        gp.obj[619].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[619].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[620] = new OBJ_Bush();
        gp.obj[620].worldX = 4 * gp.tileSize; // Col 13
        gp.obj[620].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[621] = new OBJ_Bush();
        gp.obj[621].worldX = 5 * gp.tileSize; // Col 13
        gp.obj[621].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[622] = new OBJ_Bush();
        gp.obj[622].worldX = 6 * gp.tileSize; // Col 13
        gp.obj[622].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[623] = new OBJ_Bush();
        gp.obj[623].worldX = 7 * gp.tileSize; // Col 13
        gp.obj[623].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[624] = new OBJ_Bush();
        gp.obj[624].worldX = 9 * gp.tileSize; // Col 13
        gp.obj[624].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[625] = new OBJ_Bush();
        gp.obj[625].worldX = 10 * gp.tileSize; // Col 13
        gp.obj[625].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[626] = new OBJ_Bush();
        gp.obj[626].worldX = 11 * gp.tileSize; // Col 13
        gp.obj[626].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[627] = new OBJ_Bush();
        gp.obj[627].worldX = 12 * gp.tileSize; // Col 13
        gp.obj[627].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[628] = new OBJ_Bush();
        gp.obj[628].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[628].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[629] = new OBJ_Bush();
        gp.obj[629].worldX = 15 * gp.tileSize; // Col 13
        gp.obj[629].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[630] = new OBJ_Bush();
        gp.obj[630].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[630].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[631] = new OBJ_Bush();
        gp.obj[631].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[631].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[632] = new OBJ_Bush();
        gp.obj[632].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[632].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[633] = new OBJ_Bush();
        gp.obj[633].worldX = 19 * gp.tileSize; // Col 13
        gp.obj[633].worldY = 2 * gp.tileSize; // Row 36

        gp.obj[634] = new OBJ_Bush();
        gp.obj[634].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[634].worldY = 4 * gp.tileSize; // Row 36

        gp.obj[635] = new OBJ_Bush();
        gp.obj[635].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[635].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[636] = new OBJ_Bush();
        gp.obj[636].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[636].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[637] = new OBJ_Bush();
        gp.obj[637].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[637].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[638] = new OBJ_Bush();
        gp.obj[638].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[638].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[639] = new OBJ_Bush();
        gp.obj[639].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[639].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[640] = new OBJ_Bush();
        gp.obj[640].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[640].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[641] = new OBJ_Bush();
        gp.obj[641].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[641].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[642] = new OBJ_Bush();
        gp.obj[642].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[642].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[643] = new OBJ_Bush();
        gp.obj[643].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[643].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[644] = new OBJ_Bush();
        gp.obj[644].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[644].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[645] = new OBJ_Bush();
        gp.obj[645].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[645].worldY = 17 * gp.tileSize; // Row 36

        gp.obj[646] = new OBJ_Bush();
        gp.obj[646].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[646].worldY = 18 * gp.tileSize; // Row 36

        gp.obj[647] = new OBJ_Bush();
        gp.obj[647].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[647].worldY = 19 * gp.tileSize; // Row 36

        gp.obj[648] = new OBJ_Bush();
        gp.obj[648].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[648].worldY = 20 * gp.tileSize; // Row 36

        gp.obj[649] = new OBJ_Bush();
        gp.obj[649].worldX = 6 * gp.tileSize; // Col 13
        gp.obj[649].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[650] = new OBJ_Bush();
        gp.obj[650].worldX = 7 * gp.tileSize; // Col 13
        gp.obj[650].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[651] = new OBJ_Bush();
        gp.obj[651].worldX = 8 * gp.tileSize; // Col 13
        gp.obj[651].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[652] = new OBJ_Bush();
        gp.obj[652].worldX = 8 * gp.tileSize; // Col 13
        gp.obj[652].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[653] = new OBJ_Bush();
        gp.obj[653].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[653].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[654] = new OBJ_Bush();
        gp.obj[654].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[654].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[655] = new OBJ_Bush();
        gp.obj[655].worldX = 15 * gp.tileSize; // Col 13
        gp.obj[655].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[656] = new OBJ_Bush();
        gp.obj[656].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[656].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[657] = new OBJ_Bush();
        gp.obj[657].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[657].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[658] = new OBJ_Bush();
        gp.obj[658].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[658].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[659] = new OBJ_Bush();
        gp.obj[659].worldX = 19 * gp.tileSize; // Col 13
        gp.obj[659].worldY = 5 * gp.tileSize; // Row 36

        gp.obj[660] = new OBJ_Bush();
        gp.obj[660].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[660].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[661] = new OBJ_Bush();
        gp.obj[661].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[661].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[662] = new OBJ_Bush();
        gp.obj[662].worldX = 15 * gp.tileSize; // Col 13
        gp.obj[662].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[663] = new OBJ_Bush();
        gp.obj[663].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[663].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[664] = new OBJ_Bush();
        gp.obj[664].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[664].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[665] = new OBJ_Bush();
        gp.obj[665].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[665].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[666] = new OBJ_Bush();
        gp.obj[666].worldX = 15 * gp.tileSize; // Col 13
        gp.obj[666].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[667] = new OBJ_Bush();
        gp.obj[667].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[667].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[668] = new OBJ_Bush();
        gp.obj[668].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[668].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[669] = new OBJ_Bush();
        gp.obj[669].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[669].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[670] = new OBJ_Bush();
        gp.obj[670].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[670].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[671] = new OBJ_Bush();
        gp.obj[671].worldX = 19 * gp.tileSize; // Col 13
        gp.obj[671].worldY = 15 * gp.tileSize; // Row 36

        gp.obj[672] = new OBJ_Bush();
        gp.obj[672].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[672].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[673] = new OBJ_Bush();
        gp.obj[673].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[673].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[674] = new OBJ_Bush();
        gp.obj[674].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[674].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[675] = new OBJ_Bush();
        gp.obj[675].worldX = 19 * gp.tileSize; // Col 13
        gp.obj[675].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[676] = new OBJ_Bush();
        gp.obj[676].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[676].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[677] = new OBJ_Bush();
        gp.obj[677].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[677].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[678] = new OBJ_Bush();
        gp.obj[678].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[678].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[679] = new OBJ_Bush();
        gp.obj[679].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[679].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[680] = new OBJ_Bush();
        gp.obj[680].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[680].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[681] = new OBJ_Bush();
        gp.obj[681].worldX = 19 * gp.tileSize; // Col 13
        gp.obj[681].worldY = 21 * gp.tileSize; // Row 36

        gp.obj[682] = new OBJ_Bush();
        gp.obj[682].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[682].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[683] = new OBJ_Bush();
        gp.obj[683].worldX = 19 * gp.tileSize; // Col 13
        gp.obj[683].worldY = 22 * gp.tileSize; // Row 36


        gp.obj[687] = new OBJ_GrassPurpleEdge();
        gp.obj[687].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[687].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[688] = new OBJ_GrassPurpleEdge();
        gp.obj[688].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[688].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[689] = new OBJ_GrassPurpleEdge();
        gp.obj[689].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[689].worldY = 19 * gp.tileSize; // Row 36

        gp.obj[690] = new OBJ_GrassPurpleEdge();
        gp.obj[690].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[690].worldY = 18 * gp.tileSize; // Row 36

        gp.obj[691] = new OBJ_GrassPurpleEdge();
        gp.obj[691].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[691].worldY = 17 * gp.tileSize; // Row 36

        gp.obj[692] = new OBJ_GrassPurpleEdge();
        gp.obj[692].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[692].worldY = 16 * gp.tileSize; // Row 36

        gp.obj[693] = new OBJ_GrassPurpleEdge();
        gp.obj[693].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[693].worldY = 14 * gp.tileSize; // Row 36

        gp.obj[694] = new OBJ_GrassPurpleEdge();
        gp.obj[694].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[694].worldY = 13 * gp.tileSize; // Row 36

        gp.obj[695] = new OBJ_GrassPurpleEdge();
        gp.obj[695].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[695].worldY = 12 * gp.tileSize; // Row 36

        gp.obj[696] = new OBJ_GrassPurpleEdge();
        gp.obj[696].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[696].worldY = 11 * gp.tileSize; // Row 36

        gp.obj[697] = new OBJ_GrassPurpleEdge();
        gp.obj[697].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[697].worldY = 9 * gp.tileSize; // Row 36

        gp.obj[698] = new OBJ_GrassPurpleEdge();
        gp.obj[698].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[698].worldY = 8 * gp.tileSize; // Row 36

        gp.obj[699] = new OBJ_GrassPurpleEdge();
        gp.obj[699].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[699].worldY = 7 * gp.tileSize; // Row 36

        gp.obj[700] = new OBJ_GrassPurpleEdge();
        gp.obj[700].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[700].worldY = 10 * gp.tileSize; // Row 36

        gp.obj[701] = new OBJ_GrassPurpleEdge();
        gp.obj[701].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[701].worldY = 6 * gp.tileSize; // Row 36


        gp.obj[702] = new OBJ_Map1Fence();
        gp.obj[702].worldX = 19 * gp.tileSize; // Col 13
        gp.obj[702].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[703] = new OBJ_GrassPurpleEdge();
        gp.obj[703].worldX = 48 * gp.tileSize; // Col 13
        gp.obj[703].worldY = 6 * gp.tileSize; // Row 36

        gp.obj[704] = new OBJ_Top_Rfence();
        gp.obj[704].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[704].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[705] = new OBJ_FenceRight();
        gp.obj[705].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[705].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[706] = new OBJ_FenceRight();
        gp.obj[706].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[706].worldY = 27 * gp.tileSize; // Row 36

        gp.obj[707] = new OBJ_FenceRight();
        gp.obj[707].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[707].worldY = 28 * gp.tileSize; // Row 36

        gp.obj[708] = new OBJ_FenceRight();
        gp.obj[708].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[708].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[709] = new OBJ_FenceRight();
        gp.obj[709].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[709].worldY = 30 * gp.tileSize; // Row 36

        gp.obj[710] = new OBJ_FenceRight();
        gp.obj[710].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[710].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[711] = new OBJ_FenceRight();
        gp.obj[711].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[711].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[712] = new OBJ_FenceRight();
        gp.obj[712].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[712].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[713] = new OBJ_FenceRight();
        gp.obj[713].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[713].worldY = 34 * gp.tileSize; // Row 36

        gp.obj[714] = new OBJ_FenceRight();
        gp.obj[714].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[714].worldY = 35 * gp.tileSize; // Row 36

        gp.obj[715] = new OBJ_FenceRight();
        gp.obj[715].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[715].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[716] = new OBJ_FenceRight();
        gp.obj[716].worldX = 47 * gp.tileSize; // Col 13
        gp.obj[716].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[717] = new OBJ_Grey();
        gp.obj[717].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[717].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[718] = new OBJ_Grey();
        gp.obj[718].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[718].worldY = 41 * gp.tileSize; // Row 36

        gp.obj[719] = new OBJ_Grey();
        gp.obj[719].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[719].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[720] = new OBJ_Grey();
        gp.obj[720].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[720].worldY = 43 * gp.tileSize; // Row 36

        gp.obj[721] = new OBJ_Grey();
        gp.obj[721].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[721].worldY = 39 * gp.tileSize; // Row 36

        gp.obj[722] = new OBJ_Map1Fence();
        gp.obj[722].worldX = 30 * gp.tileSize; // Col 13
        gp.obj[722].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[723] = new OBJ_Map1Fence();
        gp.obj[723].worldX = 31 * gp.tileSize; // Col 13
        gp.obj[723].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[724] = new OBJ_Map1Fence();
        gp.obj[724].worldX = 32 * gp.tileSize; // Col 13
        gp.obj[724].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[725] = new OBJ_Map1Fence();
        gp.obj[725].worldX = 33 * gp.tileSize; // Col 13
        gp.obj[725].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[726] = new OBJ_Map1Fence();
        gp.obj[726].worldX = 34 * gp.tileSize; // Col 13
        gp.obj[726].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[727] = new OBJ_Map1Fence();
        gp.obj[727].worldX = 35 * gp.tileSize; // Col 13
        gp.obj[727].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[728] = new OBJ_Map1Fence();
        gp.obj[728].worldX = 36 * gp.tileSize; // Col 13
        gp.obj[728].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[729] = new OBJ_Map1Fence();
        gp.obj[729].worldX = 37 * gp.tileSize; // Col 13
        gp.obj[729].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[730] = new OBJ_Map1Fence();
        gp.obj[730].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[730].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[731] = new OBJ_Map1Fence();
        gp.obj[731].worldX = 39 * gp.tileSize; // Col 13
        gp.obj[731].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[732] = new OBJ_Map1Fence();
        gp.obj[732].worldX = 40 * gp.tileSize; // Col 13
        gp.obj[732].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[733] = new OBJ_Map1Fence();
        gp.obj[733].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[733].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[734] = new OBJ_Map1Fence();
        gp.obj[734].worldX = 42 * gp.tileSize; // Col 13
        gp.obj[734].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[735] = new OBJ_Map1Fence();
        gp.obj[735].worldX = 43 * gp.tileSize; // Col 13
        gp.obj[735].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[736] = new OBJ_Map1Fence();
        gp.obj[736].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[736].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[737] = new OBJ_BlueHouse();
        gp.obj[737].worldX = 30 * gp.tileSize; // Column 10
        gp.obj[737].worldY = 25 * gp.tileSize; // Row 10

        gp.obj[738] = new OBJ_RedHouse();
        gp.obj[738].worldX = 35 * gp.tileSize; // Column 10
        gp.obj[738].worldY = 25 * gp.tileSize; // Row 10

        gp.obj[739] = new OBJ_BlueHouse();
        gp.obj[739].worldX = 40 * gp.tileSize; // Column 10
        gp.obj[739].worldY = 25 * gp.tileSize; // Row 10

        gp.obj[740] = new OBJ_Gate();
        gp.obj[740].worldX = 48 * gp.tileSize; // Column 10
        gp.obj[740].worldY = 37 * gp.tileSize; // Row 1

        gp.obj[741] = new OBJ_Gate();
        gp.obj[741].worldX = 38 * gp.tileSize; // Column 10
        gp.obj[741].worldY = 37 * gp.tileSize; // Row 1

        gp.obj[742] = new OBJ_Gate();
        gp.obj[742].worldX = 40 * gp.tileSize; // Column 10
        gp.obj[742].worldY = 37 * gp.tileSize; // Row 1

        gp.obj[743] = new OBJ_Gate();
        gp.obj[743].worldX = 42 * gp.tileSize; // Column 10
        gp.obj[743].worldY = 37 * gp.tileSize; // Row 1

        gp.obj[744] = new OBJ_Gate();
        gp.obj[744].worldX = 44 * gp.tileSize; // Column 10
        gp.obj[744].worldY = 37 * gp.tileSize; // Row 1

        gp.obj[745] = new OBJ_Gate();
        gp.obj[745].worldX = 46 * gp.tileSize; // Column 10
        gp.obj[745].worldY = 37 * gp.tileSize; // Row 1

        gp.obj[746] = new OBJ_Portal();
        gp.obj[746].worldX = 38 * gp.tileSize; // Col 13
        gp.obj[746].worldY = 35 * gp.tileSize; // Row 36

        gp.obj[747] = new OBJ_Portal();
        gp.obj[747].worldX = 41 * gp.tileSize; // Col 13
        gp.obj[747].worldY = 35 * gp.tileSize; // Row 36

        gp.obj[748] = new OBJ_Portal();
        gp.obj[748].worldX = 44 * gp.tileSize; // Col 13
        gp.obj[748].worldY = 35 * gp.tileSize; // Row 36

        gp.obj[749] = new OBJ_Top_Rfence();
        gp.obj[749].worldX = 37 * gp.tileSize; // Col 13
        gp.obj[749].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[750] = new OBJ_FenceRight();
        gp.obj[750].worldX = 37 * gp.tileSize; // Col 13
        gp.obj[750].worldY = 39 * gp.tileSize; // Row 36

        gp.obj[751] = new OBJ_FenceRight();
        gp.obj[751].worldX = 37 * gp.tileSize; // Col 13
        gp.obj[751].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[752] = new OBJ_FenceRight();
        gp.obj[752].worldX = 37 * gp.tileSize; // Col 13
        gp.obj[752].worldY = 41 * gp.tileSize; // Row 36

        gp.obj[753] = new OBJ_FenceRight();
        gp.obj[753].worldX = 37 * gp.tileSize; // Col 13
        gp.obj[753].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[754] = new OBJ_Map1Fence();
        gp.obj[754].worldX = 36 * gp.tileSize; // Col 13
        gp.obj[754].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[755] = new OBJ_Map1Fence();
        gp.obj[755].worldX = 35 * gp.tileSize; // Col 13
        gp.obj[755].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[756] = new OBJ_Map1Fence();
        gp.obj[756].worldX = 34 * gp.tileSize; // Col 13
        gp.obj[756].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[757] = new OBJ_Map1Fence();
        gp.obj[757].worldX = 33 * gp.tileSize; // Col 13
        gp.obj[757].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[758] = new OBJ_FenceRight();
        gp.obj[758].worldX = 30 * gp.tileSize; // Col 13
        gp.obj[758].worldY = 38 * gp.tileSize; // Row 36


        gp.obj[759] = new OBJ_FenceRight();
        gp.obj[759].worldX = 30 * gp.tileSize; // Col 13
        gp.obj[759].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[760] = new OBJ_FenceRight();
        gp.obj[760].worldX = 30 * gp.tileSize; // Col 13
        gp.obj[760].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[761] = new OBJ_Bush_green();
        gp.obj[761].worldX = 29 * gp.tileSize; // Col 13
        gp.obj[761].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[762] = new OBJ_Bush_green();
        gp.obj[762].worldX = 28 * gp.tileSize; // Col 13
        gp.obj[762].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[763] = new OBJ_Bush_green();
        gp.obj[763].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[763].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[764] = new OBJ_Bush_green();
        gp.obj[764].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[764].worldY = 37 * gp.tileSize; // Row 36


        gp.obj[766] = new OBJ_Bush_green();
        gp.obj[766].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[766].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[767] = new OBJ_Bush_green();
        gp.obj[767].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[767].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[768] = new OBJ_Bush_green();
        gp.obj[768].worldX = 22 * gp.tileSize; // Col 13
        gp.obj[768].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[769] = new OBJ_Bush_green();
        gp.obj[769].worldX = 21 * gp.tileSize; // Col 13
        gp.obj[769].worldY = 37 * gp.tileSize; // Row 36


        gp.obj[770] = new OBJ_Bush_green();
        gp.obj[770].worldX = 29 * gp.tileSize; // Col 13
        gp.obj[770].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[771] = new OBJ_Bush_green();
        gp.obj[771].worldX = 28 * gp.tileSize; // Col 13
        gp.obj[771].worldY = 36 * gp.tileSize; // Row 36


        gp.obj[773] = new OBJ_Bush_green();
        gp.obj[773].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[773].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[774] = new OBJ_Bush_green();
        gp.obj[774].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[774].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[775] = new OBJ_Bush_green();
        gp.obj[775].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[775].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[777] = new OBJ_Bush_green();
        gp.obj[777].worldX = 22 * gp.tileSize; // Col 13
        gp.obj[777].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[778] = new OBJ_Bush_green();
        gp.obj[778].worldX = 21 * gp.tileSize; // Col 13
        gp.obj[778].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[779] = new OBJ_Bush_green();
        gp.obj[779].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[779].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[780] = new OBJ_Bush_green();
        gp.obj[780].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[780].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[781] = new OBJ_Bush_green();
        gp.obj[781].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[781].worldY = 36 * gp.tileSize; // Row 36

        gp.obj[782] = new OBJ_Rock1_Beach();
        gp.obj[782].worldX = 1 * gp.tileSize; // Col 13
        gp.obj[782].worldY = 41 * gp.tileSize; // Row 36

        gp.obj[783] = new OBJ_Rock1_Beach();
        gp.obj[783].worldX = 1 * gp.tileSize; // Col 13
        gp.obj[783].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[784] = new OBJ_Rock1_Beach();
        gp.obj[784].worldX = 1 * gp.tileSize; // Col 13
        gp.obj[784].worldY = 39 * gp.tileSize; // Row 36

        gp.obj[785] = new OBJ_Rock1_Beach();
        gp.obj[785].worldX = 1 * gp.tileSize; // Col 13
        gp.obj[785].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[786] = new OBJ_Rock1_Beach();
        gp.obj[786].worldX = 2 * gp.tileSize; // Col 13
        gp.obj[786].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[787] = new OBJ_Rock1_Beach();
        gp.obj[787].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[787].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[788] = new OBJ_Rock1_Beach();
        gp.obj[788].worldX = 4 * gp.tileSize; // Col 13
        gp.obj[788].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[789] = new OBJ_Rock1_Beach();
        gp.obj[789].worldX = 7 * gp.tileSize; // Col 13
        gp.obj[789].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[790] = new OBJ_Rock1_Beach();
        gp.obj[790].worldX = 8 * gp.tileSize; // Col 13
        gp.obj[790].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[791] = new OBJ_Rock1_Beach();
        gp.obj[791].worldX = 9 * gp.tileSize; // Col 13
        gp.obj[791].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[792] = new OBJ_Rock1_Beach();
        gp.obj[792].worldX = 10 * gp.tileSize; // Col 13
        gp.obj[792].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[793] = new OBJ_Rock1_Beach();
        gp.obj[793].worldX = 11 * gp.tileSize; // Col 13
        gp.obj[793].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[794] = new OBJ_Water_Sand();
        gp.obj[794].worldX = 1 * gp.tileSize; // Col 13
        gp.obj[794].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[795] = new OBJ_Water_Sand();
        gp.obj[795].worldX = 2 * gp.tileSize; // Col 13
        gp.obj[795].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[796] = new OBJ_Water_Sand();
        gp.obj[796].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[796].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[797] = new OBJ_Water_Sand();
        gp.obj[797].worldX = 4 * gp.tileSize; // Col 13
        gp.obj[797].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[798] = new OBJ_Water_Sand();
        gp.obj[798].worldX = 5 * gp.tileSize; // Col 13
        gp.obj[798].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[799] = new OBJ_Water_Sand();
        gp.obj[799].worldX = 6 * gp.tileSize; // Col 13
        gp.obj[799].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[800] = new OBJ_Water_Sand();
        gp.obj[800].worldX = 7 * gp.tileSize; // Col 13
        gp.obj[800].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[801] = new OBJ_Water_Sand();
        gp.obj[801].worldX = 8 * gp.tileSize; // Col 13
        gp.obj[801].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[802] = new OBJ_Water_Sand();
        gp.obj[802].worldX = 9 * gp.tileSize; // Col 13
        gp.obj[802].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[803] = new OBJ_Water_Sand();
        gp.obj[803].worldX = 10 * gp.tileSize; // Col 13
        gp.obj[803].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[804] = new OBJ_Water_Sand();
        gp.obj[804].worldX = 11 * gp.tileSize; // Col 13
        gp.obj[804].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[805] = new OBJ_Water_Sand();
        gp.obj[805].worldX = 12 * gp.tileSize; // Col 13
        gp.obj[805].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[806] = new OBJ_Water_Sand();
        gp.obj[806].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[806].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[807] = new OBJ_Water_Sand();
        gp.obj[807].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[807].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[808] = new OBJ_Water_Sand();
        gp.obj[808].worldX = 15 * gp.tileSize; // Col 13
        gp.obj[808].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[809] = new OBJ_Water_Sand();
        gp.obj[809].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[809].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[810] = new OBJ_Water_Sand();
        gp.obj[810].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[810].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[811] = new OBJ_Water_Sand();
        gp.obj[811].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[811].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[812] = new OBJ_Water_Sand();
        gp.obj[812].worldX = 19 * gp.tileSize; // Col 13
        gp.obj[812].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[813] = new OBJ_Water_Sand();
        gp.obj[813].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[813].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[814] = new OBJ_Water_Sand();
        gp.obj[814].worldX = 21 * gp.tileSize; // Col 13
        gp.obj[814].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[815] = new OBJ_Water_Sand();
        gp.obj[815].worldX = 22 * gp.tileSize; // Col 13
        gp.obj[815].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[816] = new OBJ_Water_Sand();
        gp.obj[816].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[816].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[817] = new OBJ_Water_Sand();
        gp.obj[817].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[817].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[818] = new OBJ_Water_Sand();
        gp.obj[818].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[818].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[819] = new OBJ_Water_Sand();
        gp.obj[819].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[819].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[820] = new OBJ_Water_Sand();
        gp.obj[820].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[820].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[821] = new OBJ_Water_Sand();
        gp.obj[821].worldX = 28 * gp.tileSize; // Col 13
        gp.obj[821].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[822] = new OBJ_Water_Sand();
        gp.obj[822].worldX = 29 * gp.tileSize; // Col 13
        gp.obj[822].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[823] = new OBJ_Water_Sand();
        gp.obj[823].worldX = 30 * gp.tileSize; // Col 13
        gp.obj[823].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[824] = new OBJ_Water_Sand();
        gp.obj[824].worldX = 31 * gp.tileSize; // Col 13
        gp.obj[824].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[825] = new OBJ_Water_Sand();
        gp.obj[825].worldX = 32 * gp.tileSize; // Col 13
        gp.obj[825].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[826] = new OBJ_Water_Sand();
        gp.obj[826].worldX = 33 * gp.tileSize; // Col 13
        gp.obj[826].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[827] = new OBJ_Water_Sand();
        gp.obj[827].worldX = 34 * gp.tileSize; // Col 13
        gp.obj[827].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[828] = new OBJ_Water_Sand();
        gp.obj[828].worldX = 35 * gp.tileSize; // Col 13
        gp.obj[828].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[829] = new OBJ_Water_Sand();
        gp.obj[829].worldX = 36 * gp.tileSize; // Col 13
        gp.obj[829].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[830] = new OBJ_Water_Sand();
        gp.obj[830].worldX = 37 * gp.tileSize; // Col 13
        gp.obj[830].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[831] = new OBJ_FenceLeft();
        gp.obj[831].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[831].worldY = 41 * gp.tileSize; // Row 36

        gp.obj[832] = new OBJ_FenceLeft();
        gp.obj[832].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[832].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[833] = new OBJ_FenceLeft();
        gp.obj[833].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[833].worldY = 39 * gp.tileSize; // Row 36

        gp.obj[834] = new OBJ_FenceLeft();
        gp.obj[834].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[834].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[835] = new OBJ_FenceLeft();
        gp.obj[835].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[835].worldY = 37 * gp.tileSize; // Row 36

        gp.obj[836] = new OBJ_FenceLeft();
        gp.obj[836].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[836].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[837] = new OBJ_Water_Sand();
        gp.obj[837].worldX = 0 * gp.tileSize; // Col 13
        gp.obj[837].worldY = 42 * gp.tileSize; // Row 36

        gp.obj[838] = new OBJ_Rock1_Beach();
        gp.obj[838].worldX = 12 * gp.tileSize; // Col 13
        gp.obj[838].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[839] = new OBJ_Rock1_Beach();
        gp.obj[839].worldX = 13 * gp.tileSize; // Col 13
        gp.obj[839].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[840] = new OBJ_Rock1_Beach();
        gp.obj[840].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[840].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[841] = new OBJ_Rock1_Beach();
        gp.obj[841].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[841].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[842] = new OBJ_Rock1_Beach();
        gp.obj[842].worldX = 18 * gp.tileSize; // Col 13
        gp.obj[842].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[843] = new OBJ_Rock1_Beach();
        gp.obj[843].worldX = 19 * gp.tileSize; // Col 13
        gp.obj[843].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[844] = new OBJ_Rock1_Beach();
        gp.obj[844].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[844].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[845] = new OBJ_Rock1_Beach();
        gp.obj[845].worldX = 21 * gp.tileSize; // Col 13
        gp.obj[845].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[846] = new OBJ_Rock1_Beach();
        gp.obj[846].worldX = 22 * gp.tileSize; // Col 13
        gp.obj[846].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[847] = new OBJ_Rock1_Beach();
        gp.obj[847].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[847].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[848] = new OBJ_Rock1_Beach();
        gp.obj[848].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[848].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[849] = new OBJ_Rock1_Beach();
        gp.obj[849].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[849].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[850] = new OBJ_Rock1_Beach();
        gp.obj[850].worldX = 28 * gp.tileSize; // Col 13
        gp.obj[850].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[851] = new OBJ_Rock1_Beach();
        gp.obj[851].worldX = 29 * gp.tileSize; // Col 13
        gp.obj[851].worldY = 38 * gp.tileSize; // Row 36

        gp.obj[852] = new OBJ_Rock_Beach();
        gp.obj[852].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[852].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[853] = new OBJ_Rock_Beach();
        gp.obj[853].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[853].worldY = 41 * gp.tileSize; // Row 36

        gp.obj[854] = new OBJ_Rock_Beach();
        gp.obj[854].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[854].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[855] = new OBJ_Rock_Beach();
        gp.obj[855].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[855].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[856] = new OBJ_Rock_Beach();
        gp.obj[856].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[856].worldY = 40 * gp.tileSize; // Row 36


        gp.obj[858] = new OBJ_Rock_Beach();
        gp.obj[858].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[858].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[859] = new OBJ_Rock_Beach();
        gp.obj[859].worldX = 22 * gp.tileSize; // Col 13
        gp.obj[859].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[860] = new OBJ_Rock_Beach();
        gp.obj[860].worldX = 21 * gp.tileSize; // Col 13
        gp.obj[860].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[861] = new OBJ_Rock_Beach();
        gp.obj[861].worldX = 20 * gp.tileSize; // Col 13
        gp.obj[861].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[863] = new OBJ_Rock_Beach();
        gp.obj[863].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[863].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[865] = new OBJ_Rock_Beach();
        gp.obj[865].worldX = 17 * gp.tileSize; // Col 13
        gp.obj[865].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[866] = new OBJ_Rock_Beach();
        gp.obj[866].worldX = 16 * gp.tileSize; // Col 13
        gp.obj[866].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[867] = new OBJ_Rock_Beach();
        gp.obj[867].worldX = 15 * gp.tileSize; // Col 13
        gp.obj[867].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[868] = new OBJ_Rock_Beach();
        gp.obj[868].worldX = 14 * gp.tileSize; // Col 13
        gp.obj[868].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[869] = new OBJ_Rock_Beach();
        gp.obj[869].worldX = 12 * gp.tileSize; // Col 13
        gp.obj[869].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[870] = new OBJ_Rock_Beach();
        gp.obj[870].worldX = 11 * gp.tileSize; // Col 13
        gp.obj[870].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[871] = new OBJ_Rock_Beach();
        gp.obj[871].worldX = 10 * gp.tileSize; // Col 13
        gp.obj[871].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[872] = new OBJ_Rock_Beach();
        gp.obj[872].worldX = 9 * gp.tileSize; // Col 13
        gp.obj[872].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[873] = new OBJ_Rock_Beach();
        gp.obj[873].worldX = 7 * gp.tileSize; // Col 13
        gp.obj[873].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[874] = new OBJ_Rock_Beach();
        gp.obj[874].worldX = 6 * gp.tileSize; // Col 13
        gp.obj[874].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[875] = new OBJ_Rock_Beach();
        gp.obj[875].worldX = 5 * gp.tileSize; // Col 13
        gp.obj[875].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[876] = new OBJ_Rock_Beach();
        gp.obj[876].worldX = 4 * gp.tileSize; // Col 13
        gp.obj[876].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[877] = new OBJ_Rock_Beach();
        gp.obj[877].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[877].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[878] = new OBJ_Rock_Beach();
        gp.obj[878].worldX = 3 * gp.tileSize; // Col 13
        gp.obj[878].worldY = 41 * gp.tileSize; // Row 36

        gp.obj[879] = new OBJ_Rock1_Beach();
        gp.obj[879].worldX = 35 * gp.tileSize; // Col 13
        gp.obj[879].worldY = 39 * gp.tileSize; // Row 36

        gp.obj[880] = new OBJ_Rock1_Beach();
        gp.obj[880].worldX = 34 * gp.tileSize; // Col 13
        gp.obj[880].worldY = 39 * gp.tileSize; // Row 36

        gp.obj[881] = new OBJ_Rock1_Beach();
        gp.obj[881].worldX = 36 * gp.tileSize; // Col 13
        gp.obj[881].worldY = 39 * gp.tileSize; // Row 36

        gp.obj[882] = new OBJ_Rock1_Beach();
        gp.obj[882].worldX = 36 * gp.tileSize; // Col 13
        gp.obj[882].worldY = 40 * gp.tileSize; // Row 36

        gp.obj[883] = new OBJ_Rock1_Beach();
        gp.obj[883].worldX = 36 * gp.tileSize; // Col 13
        gp.obj[883].worldY = 41 * gp.tileSize; // Row 36


        gp.obj[888] = new OBJ_Leftsplash();
        gp.obj[888].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[888].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[889] = new OBJ_Leftsplash();
        gp.obj[889].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[889].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[890] = new OBJ_Leftsplash();
        gp.obj[890].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[890].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[891] = new OBJ_Rightsplash();
        gp.obj[891].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[891].worldY = 31 * gp.tileSize; // Row 36

        gp.obj[892] = new OBJ_Rightsplash();
        gp.obj[892].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[892].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[893] = new OBJ_Rightsplash();
        gp.obj[893].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[893].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[894] = new OBJ_Rightsplash();
        gp.obj[894].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[894].worldY = 32 * gp.tileSize; // Row 36

        gp.obj[895] = new OBJ_Rightsplash();
        gp.obj[895].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[895].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[896] = new OBJ_Finalsplash();
        gp.obj[896].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[896].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[897] = new OBJ_Finalsplash();
        gp.obj[897].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[897].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[898] = new OBJ_Finalsplash();
        gp.obj[898].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[898].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[899] = new OBJ_Finalsplash();
        gp.obj[899].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[899].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[900] = new OBJ_Finalsplash();
        gp.obj[900].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[900].worldY = 33 * gp.tileSize; // Row 36

        gp.obj[901] = new OBJ_Map1Fence();
        gp.obj[901].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[901].worldY = 34 * gp.tileSize; // Row 36

        gp.obj[902] = new OBJ_Map1Fence();
        gp.obj[902].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[902].worldY = 34 * gp.tileSize; // Row 36

        gp.obj[903] = new OBJ_Map1Fence();
        gp.obj[903].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[903].worldY = 34 * gp.tileSize; // Row 36

        gp.obj[904] = new OBJ_Map1Fence();
        gp.obj[904].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[904].worldY = 34 * gp.tileSize; // Row 36

        gp.obj[905] = new OBJ_Map1Fence();
        gp.obj[905].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[905].worldY = 34 * gp.tileSize; // Row 36

        gp.obj[906] = new OBJ_Topbridge();
        gp.obj[906].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[906].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[907] = new OBJ_Topbridge();
        gp.obj[907].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[907].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[908] = new OBJ_Topbridge();
        gp.obj[908].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[908].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[909] = new OBJ_Topbridge();
        gp.obj[909].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[909].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[910] = new OBJ_Topbridge();
        gp.obj[910].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[910].worldY = 22 * gp.tileSize; // Row 36

        gp.obj[911] = new OBJ_Btmbridge();
        gp.obj[911].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[911].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[912] = new OBJ_Btmbridge();
        gp.obj[912].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[912].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[913] = new OBJ_Btmbridge();
        gp.obj[913].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[913].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[914] = new OBJ_Btmbridge();
        gp.obj[914].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[914].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[915] = new OBJ_Btmbridge();
        gp.obj[915].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[915].worldY = 25 * gp.tileSize; // Row 36

        gp.obj[916] = new OBJ_Btmbridge();
        gp.obj[916].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[916].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[917] = new OBJ_Btmbridge();
        gp.obj[917].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[917].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[918] = new OBJ_Btmbridge();
        gp.obj[918].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[918].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[919] = new OBJ_Btmbridge();
        gp.obj[919].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[919].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[920] = new OBJ_Btmbridge();
        gp.obj[920].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[920].worldY = 29 * gp.tileSize; // Row 36

        gp.obj[921] = new OBJ_Topbridge();
        gp.obj[921].worldX = 27 * gp.tileSize; // Col 13
        gp.obj[921].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[922] = new OBJ_Topbridge();
        gp.obj[922].worldX = 26 * gp.tileSize; // Col 13
        gp.obj[922].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[923] = new OBJ_Topbridge();
        gp.obj[923].worldX = 25 * gp.tileSize; // Col 13
        gp.obj[923].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[924] = new OBJ_Topbridge();
        gp.obj[924].worldX = 24 * gp.tileSize; // Col 13
        gp.obj[924].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[925] = new OBJ_Topbridge();
        gp.obj[925].worldX = 23 * gp.tileSize; // Col 13
        gp.obj[925].worldY = 26 * gp.tileSize; // Row 36

        gp.obj[926] = new OBJ_Grass1();
        gp.obj[926].worldX = 31 * gp.tileSize; // Column 10
        gp.obj[926].worldY = 15 * gp.tileSize; // Row 10

        gp.obj[927] = new OBJ_Grass1();
        gp.obj[927].worldX = 32 * gp.tileSize; // Column 10
        gp.obj[927].worldY = 15 * gp.tileSize; // Row 10

        gp.obj[928] = new OBJ_Grass1();
        gp.obj[928].worldX = 33 * gp.tileSize; // Column 10
        gp.obj[928].worldY = 15 * gp.tileSize; // Row 10

        gp.obj[929] = new OBJ_Grass1();
        gp.obj[929].worldX = 34 * gp.tileSize; // Column 10
        gp.obj[929].worldY = 15 * gp.tileSize; // Row 10

        gp.obj[930] = new OBJ_Grass1();
        gp.obj[930].worldX = 35 * gp.tileSize; // Column 10
        gp.obj[930].worldY = 15 * gp.tileSize; // Row 10

        gp.obj[931] = new OBJ_Grass1();
        gp.obj[931].worldX = 36 * gp.tileSize; // Column 10
        gp.obj[931].worldY = 15 * gp.tileSize; // Row 10

        gp.obj[932] = new OBJ_Grass1();
        gp.obj[932].worldX = 31 * gp.tileSize; // Column 10
        gp.obj[932].worldY = 10 * gp.tileSize; // Row 10

        gp.obj[933] = new OBJ_Grass1();
        gp.obj[933].worldX = 32 * gp.tileSize; // Column 10
        gp.obj[933].worldY = 10 * gp.tileSize; // Row 10

        gp.obj[934] = new OBJ_Grass1();
        gp.obj[934].worldX = 33 * gp.tileSize; // Column 10
        gp.obj[934].worldY = 10 * gp.tileSize; // Row 10

        gp.obj[935] = new OBJ_Grass1();
        gp.obj[935].worldX = 34 * gp.tileSize; // Column 10
        gp.obj[935].worldY = 10 * gp.tileSize; // Row 10

        gp.obj[936] = new OBJ_Grass1();
        gp.obj[936].worldX = 35 * gp.tileSize; // Column 10
        gp.obj[936].worldY = 10 * gp.tileSize; // Row 10

        gp.obj[937] = new OBJ_Grass1();
        gp.obj[937].worldX = 36 * gp.tileSize; // Column 10
        gp.obj[937].worldY = 10 * gp.tileSize; // Row 10

        gp.obj[938] = new OBJ_Grass1();
        gp.obj[938].worldX = 31 * gp.tileSize; // Column 10
        gp.obj[938].worldY = 5 * gp.tileSize; // Row 10

        gp.obj[939] = new OBJ_Grass1();
        gp.obj[939].worldX = 32 * gp.tileSize; // Column 10
        gp.obj[939].worldY = 5 * gp.tileSize; // Row 10

        gp.obj[940] = new OBJ_Grass1();
        gp.obj[940].worldX = 33 * gp.tileSize; // Column 10
        gp.obj[940].worldY = 5 * gp.tileSize; // Row 10

        gp.obj[941] = new OBJ_Grass1();
        gp.obj[941].worldX = 34 * gp.tileSize; // Column 10
        gp.obj[941].worldY = 5 * gp.tileSize; // Row 10

        gp.obj[942] = new OBJ_Grass1();
        gp.obj[942].worldX = 35 * gp.tileSize; // Column 10
        gp.obj[942].worldY = 5 * gp.tileSize; // Row 10

        gp.obj[943] = new OBJ_Grass1();
        gp.obj[943].worldX = 36 * gp.tileSize; // Column 10
        gp.obj[943].worldY = 5 * gp.tileSize; // Row 10

        NPC_Chief chief = new NPC_Chief(gp);
        chief.worldX = 34 * gp.tileSize;  // <-- set to wherever Chief stands
        chief.worldY = 20 * gp.tileSize;
        gp.obj[10] = chief;

        NPC_Ranger ranger = new NPC_Ranger(gp);
        ranger.worldX = 8 * gp.tileSize; // <-- adjust as needed
        ranger.worldY = 29 * gp.tileSize;
        gp.obj[11] = ranger;

        NPC_Frank frank = new NPC_Frank(gp);
        frank.worldX = 14 * gp.tileSize;  // <-- adjust as needed
        frank.worldY = 36 * gp.tileSize;
        gp.obj[12] = frank;


        // ── Lock chief/ranger after weapon received ───────────
        if (phase == GameStateManager.Map1Phase.FIGHT_ENEMIES
                || phase == GameStateManager.Map1Phase.FIGHT_BOSS
                || phase == GameStateManager.Map1Phase.COMPLETE) {
            chief.interacted  = true;
            ranger.interacted = true;
        }

        // ── Second visit NPCs (COLLECT_ESSENCE phase only) ────
        if (phase == GameStateManager.Map1Phase.COLLECT_ESSENCE) {
            // Healer
            NPC_Healer healer = new NPC_Healer(gp);
            healer.worldX = 31 * gp.tileSize;
            healer.worldY = 8 * gp.tileSize;
            gp.obj[14] = healer;

            // Farmer
            NPC_Farmer farmer = new NPC_Farmer(gp);
            farmer.worldX = 47 * gp.tileSize;
            farmer.worldY = 10 * gp.tileSize;
            gp.obj[15] = farmer;

            // Woman Villager
            NPC_WomanVillager woman = new NPC_WomanVillager(gp);
            woman.worldX = 31* gp.tileSize;
            woman.worldY = 13 * gp.tileSize;
            gp.obj[16] = woman;

            // Chief and Ranger get new second-visit dialogue
            chief.interacted = false;  // re-enable for new dialogue
            ranger.interacted = false;
        }

        // ── Spawn enemies only after weapon phase ─────────────
        if (phase == GameStateManager.Map1Phase.FIGHT_ENEMIES
                || phase == GameStateManager.Map1Phase.FIGHT_BOSS
                || phase == GameStateManager.Map1Phase.COMPLETE) {
            gp.spawnMap1Enemies();
        }




















































    }
}