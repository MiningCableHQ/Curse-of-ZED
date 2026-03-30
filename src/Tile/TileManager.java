package Tile;

import Main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    public String currentMapName; // Tracks which map is currently loaded
    int spriteCounter = 0;
    int spriteNum = 1;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {
        try {
            // Mapping your specific IDs
            System.out.println("Image loaded started");
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/map2tiles/grass_top_right_32x32.png"));
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/map2tiles/cement_32x32.png"));
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/map2tiles/gloomy_grass_32x32.png"));
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/map2tiles/gloomy_grass_top_32x32.png"));
            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/map2tiles/gloomy_grass_right_32x32.png"));
            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/map2tiles/grey_dirt_32x32.png"));
            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/map2tiles/grass_left_only_32x32.png"));
            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/map2tiles/grey_dirt_purple_32x32.png"));
            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/map2tiles/brick_vines_32x32.png"));
            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/map2tiles/grass_bottom_only_32x32.png"));
            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/map2tiles/water_frame1_dark_32x32.png"));
            tile[14] = new Tile();
            tile[14].image = ImageIO.read(getClass().getResourceAsStream("/map2tiles/water_frame2_dark_32x32.png"));

            tile[15] = new Tile();
            tile[15].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/water32x32.png"));
            tile[16] = new Tile();
            tile[16].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/waterfall32x32.png"));
            tile[17] = new Tile();
            tile[17].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/topleftwaterfall32x32.png"));
            tile[18] = new Tile();
            tile[18].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/toprightwaterfall32x32.png"));
            tile[19] = new Tile();
            tile[19].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/splash_32x32.png"));
            tile[20] = new Tile();
            tile[20].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/sand_32x32.png"));
            tile[21] = new Tile();
            tile[21].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/leftwaterfall32x32.png"));
            tile[22] = new Tile();
            tile[22].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/rightwaterfall32x32.png"));
            tile[23] = new Tile();
            tile[23].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/leftstairs_32x32.png"));
            tile[24] = new Tile();
            tile[24].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/rightstairs_32x32.png"));
            tile[25] = new Tile();
            tile[25].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/path_32x32.png"));
            tile[26] = new Tile();
            tile[26].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/mud_32x32.png"));
            tile[27] = new Tile();
            tile[27].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/leftsidemud_32x32.png"));
            tile[28] = new Tile();
            tile[28].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/curvemud_32x32.png"));
            tile[29] = new Tile();
            tile[29].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/frontmud_32x32.png"));
            tile[30] = new Tile();
            tile[30].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/grey_dirt_32x32.png"));
            tile[31] = new Tile();
            tile[31].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/graypath_32x32.png"));
            tile[32] = new Tile();
            tile[32].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/grass_32x32.png"));
            tile[33] = new Tile();
            tile[33].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/fnbridge_32x32.png"));
            tile[34] = new Tile();
            tile[34].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/fence_32x32.png"));
            tile[35] = new Tile();
            tile[35].image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/crops_32x32.png"));


            System.out.println("Image loaded finished");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;

            while (row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) break;

                // This handles multiple spaces and leading/trailing spaces
                String[] numbers = line.trim().split("\\s+");

                for (int col = 0; col < gp.maxWorldCol; col++) {
                    if (col < numbers.length) {
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[col][row] = num;
                    }
                }
                row++;
            }
            br.close();
            System.out.println("Loaded map: " + filePath);
            currentMapName = filePath;
        } catch (Exception e) {
            System.err.println("ERROR loading map: " + filePath);
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g2) {
        spriteCounter++;
        if (spriteCounter > 20) { // Every 20 frames (~3 times per second), switch
            if (spriteNum == 1) spriteNum = 2;
            else if (spriteNum == 2) spriteNum = 1;
            spriteCounter = 0;
        }
        int worldCol = 0;
        int worldRow = 0;

        while (worldRow < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

// 2. THE ANIMATION SWAP
            if (tileNum == 13 && spriteNum == 2) {
                tileNum = 14; // Visually replace frame 1 with frame 2
            }
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}