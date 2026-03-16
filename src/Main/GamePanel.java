package Main;

import javax.swing.*;
import java.awt.*;
import Entities.Characters.*;
import Tile.TileManager;
import Objects.*;

public class GamePanel extends JPanel implements Runnable {

    // System
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;

    // Screen Settings
    public final int originalTileSize = 32;
    public final int scale = 2;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    // Entities and Objects
    public Player player = new Swordsman(this, keyH);
    public SuperObject obj[] = new SuperObject[100000];
    public int currentMap = 1;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        player.update();
        checkMapTransition();
        // ANIMATE OBJECTS
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null && obj[i] instanceof OBJ_Torch) {
                ((OBJ_Torch) obj[i]).updateAnimation();
            }
        }
    }

    public void checkMapTransition() {

        // --- MAP 0 (First Map) ---
        if (currentMap == 0) {
            // Exit Right -> To Map 1
            if (player.worldX > worldWidth - tileSize) {
                currentMap = 1;
                tileM.loadMap("/maps/world02.txt");
                aSetter.setObject();
                player.worldX = tileSize * 2;
            }
        }

        // --- MAP 1 (Your "Map 2" / Middle Map) ---
        else if (currentMap == 1) {
            // Exit Left -> Back to Map 0
            if (player.worldX < tileSize / 2) {
                currentMap = 0;
                tileM.loadMap("/maps/world01.txt");
                aSetter.setObject();
                player.worldX = worldWidth - (tileSize * 2);
            }

            // EXIT AT LOWER RIGHT BOTTOM -> To Map 2 (Final Map)
            // We check BOTH X (Right) and Y (Bottom)
            else if (player.worldX > worldWidth - (tileSize * 2) &&
                    player.worldY > worldHeight - (tileSize * 2)) {

                currentMap = 2;
                tileM.loadMap("/maps/world03.txt");
                aSetter.setObject();

                // Spawn at the top-left of the final map
                player.worldX = tileSize * 2;
                player.worldY = tileSize * 2;
            }
        }

        // --- MAP 2 (Final Map) ---
        else if (currentMap == 2) {
            // Exit Top-Left -> Back to Map 1's corner
            if (player.worldX < tileSize && player.worldY < tileSize) {
                currentMap = 1;
                tileM.loadMap("/maps/world02.txt");
                aSetter.setObject();

                // Spawn back at the bottom-right corner of Map 1
                player.worldX = worldWidth - (tileSize * 3);
                player.worldY = worldHeight - (tileSize * 3);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // 1. TILE LAYER
        tileM.draw(g2);

        // 2. FLOOR OBJECT LAYER (Bridges)
        // Draw these FIRST so they are always at the very bottom
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                if (obj[i] instanceof OBJ_BridgeHorizontal || obj[i] instanceof OBJ_Stone || obj[i] instanceof OBJ_BridgeVertical) {
                    obj[i].draw(g2, this);
                }
            }
        }

        // 3. Y-SORTED LAYER (Player, Trees, Rocks)
        boolean playerDrawn = false;
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {

                // SKIP bridges here because we already drew them!
                if (obj[i] instanceof OBJ_BridgeHorizontal ||  obj[i] instanceof OBJ_Stone || obj[i] instanceof OBJ_BridgeVertical) {
                    continue;
                }

                // Standard Y-Sorting logic
                int objectBottomY = obj[i].worldY;
                if (obj[i].image != null) {
                    objectBottomY += obj[i].image.getHeight() * scale;
                }

                if (!playerDrawn && player.worldY < objectBottomY) {
                    player.draw(g2);
                    playerDrawn = true;
                }
                obj[i].draw(g2, this);
            }
        }

        // 4. FALLBACK: Draw player if they are in front of EVERYTHING
        if (!playerDrawn) {
            player.draw(g2);
        }

        g2.dispose();
    }
}