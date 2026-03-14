package Main;

import javax.swing.*;
import java.awt.*;
import Entities.Characters.*;
import Tile.TileManager;
import Objects.SuperObject;
import Objects.CollisionChecker;
import Objects.AssetSetter;

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
    public SuperObject obj[] = new SuperObject[10];
    public int currentMap = 0;

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
    }

    public void checkMapTransition() {
        if (currentMap == 0) {
            if (player.worldX > worldWidth - tileSize) {
                currentMap = 1;
                tileM.loadMap("/maps/world02.txt");
                aSetter.setObject();
                player.worldX = tileSize * 2;
            }
        } else if (currentMap == 1) {
            if (player.worldX < tileSize / 2) {
                currentMap = 0;
                tileM.loadMap("/maps/world01.txt");
                aSetter.setObject();
                player.worldX = worldWidth - (tileSize * 2);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // 1. TILE LAYER
        tileM.draw(g2);

        // 2. OBJECT & PLAYER LAYER (Y-Sorting)
        boolean playerDrawn = false;
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {

                // Get the bottom Y coordinate of the object
                int objectBottomY = obj[i].worldY;
                if(obj[i].image != null) {
                    objectBottomY += obj[i].image.getHeight() * scale;
                }

                // Compare Player's Y to the Object's BOTTOM Y
                if (!playerDrawn && player.worldY < objectBottomY) {
                    player.draw(g2);
                    playerDrawn = true;
                }
                obj[i].draw(g2, this);
            }
        }

        // 3. FALLBACK: Draw player if not already drawn
        if (!playerDrawn) {
            player.draw(g2);
        }

        g2.dispose();
    }
}