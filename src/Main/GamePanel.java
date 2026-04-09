package Main;

import javax.swing.*;
import java.awt.*;
import Entities.Characters.*;
import Tile.TileManager;
import Objects.*;

public class GamePanel extends JPanel implements Runnable {

    // System
    public TileManager tileM;
    public KeyHandler keyH;
    public CollisionChecker cChecker;
    public AssetSetter aSetter;
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
    public Player player;
    public SuperObject obj[] = new SuperObject[100000];
    public int currentMap = 0;

    /**
     * No-arg constructor for compatibility.
     * Creates a default Swordsman with a new KeyHandler.
     * WARNING: This creates a temporary GamePanel reference that will be replaced.
     * Use the parameterized constructor for proper initialization.
     */
    public GamePanel() {
        this(null, new KeyHandler());
    }

    /**
     * Constructor that accepts a pre-selected player and a KeyHandler.
     *
     * @param selectedPlayer The player character to use in the game
     * @param keyH The KeyHandler for input processing
     */
    public GamePanel(Player selectedPlayer, KeyHandler keyH) {
        this.keyH = keyH;

        // Initialize systems that depend on 'this'
        this.tileM = new TileManager(this);
        this.cChecker = new CollisionChecker(this);
        this.aSetter = new AssetSetter(this);

        // Set up the panel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyH);
        this.setFocusable(true);

        // Set the player
        if (selectedPlayer != null) {
            this.player = selectedPlayer;
            // Update the player's GamePanel reference using reflection
            try {
                java.lang.reflect.Field gpField = this.player.getClass().getSuperclass().getDeclaredField("gp");
                gpField.setAccessible(true);
                gpField.set(this.player, this);
            } catch (Exception e) {
                System.err.println("Warning: Could not set GamePanel reference in player: " + e.getMessage());
            }
        } else {
            // Create a default player with proper GamePanel reference
            this.player = new Swordsman(this, this.keyH);
        }
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
        if (player != null) {
            player.update();
        }
        checkMapTransition();
        // ANIMATE OBJECTS
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null && obj[i] instanceof OBJ_Torch) {
                ((OBJ_Torch) obj[i]).updateAnimation();
            }
        }
    }

    public void checkMapTransition() {
        if (player == null) return;

        // --- MAP 0 (Starting Map) ---
        if (currentMap == 0) {
            // EXIT RIGHT: If player hits the right edge of Map 0
            if (player.worldX > worldWidth - (tileSize * 1.5)) {
                currentMap = 1;
                tileM.loadMap("/maps/world02.txt");
                aSetter.setObject();

                // SPAWN ON MAP 1: Move to Column 3 to stay away from the left-exit trigger
                player.worldX = tileSize * 3;
                player.worldY = tileSize * 10;
            }
        }

        // --- MAP 1 (Middle Map) ---
        else if (currentMap == 1) {
            // EXIT LEFT: Back to Map 0
            // We check if player is at the far left (less than 1 tile in)
            if (player.worldX < tileSize) {
                currentMap = 0;
                tileM.loadMap("/maps/world01.txt");
                aSetter.setObject();

                // SPAWN ON MAP 0: Move them back to the right side
                // (worldWidth minus 3 tiles keeps you away from the right-exit trigger)
                player.worldX = worldWidth - (tileSize * 3);
                player.worldY = tileSize * 10;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (player == null) return;

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
        if (!playerDrawn && player != null) {
            player.draw(g2);
        }

        g2.dispose();
    }
}