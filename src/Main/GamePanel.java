package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import Entities.Characters.*;
import Tile.TileManager;
import Objects.*;
import Entities.Characters.NPC;
import Dialogue.*;
import Main.*;

public class GamePanel extends JPanel implements Runnable {

    // System
    public TileManager tileM;
    public KeyHandler keyH;
    public CollisionChecker cChecker;
    public AssetSetter aSetter;
    Thread gameThread;
    public DialogueSystem dialogueSystem;
    public InteractionPrompt interactionPrompt;
    private NPC nearbyNPC = null;
    private boolean ePressedLastFrame = false;
    public MapLabel mapLabel;

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
        this.dialogueSystem    = new DialogueSystem();
        this.interactionPrompt = new InteractionPrompt();
        this.mapLabel = new MapLabel("Map 1", "The Neverwinter Village");

// Mouse listener for dialogue clicks
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (dialogueSystem.isActive()) {
                    dialogueSystem.handleClick(e.getX(), e.getY());
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (dialogueSystem.isActive()) {
                    dialogueSystem.handleHover(e.getX(), e.getY());
                }
            }
        });

// Hook up shop callback
        dialogueSystem.setOnOpenShop(() -> {
            // TODO: Your teammate's shop UI call goes here, e.g.:
            // ShopUI.open(player);
            System.out.println("OPEN SHOP UI HERE");
        });

        // ADD IT RIGHT HERE — load player portrait for dialogue
        try {
            String cls = player.getClass().getSimpleName().toLowerCase();
            String portraitName = cls.equals("ranger") ? "archer" : cls;
            java.awt.image.BufferedImage pp = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream("/player/" + portraitName  + "_portrait.png"));
            dialogueSystem.setPlayerPortrait(pp);
        } catch (Exception e) {
            System.err.println("Player portrait not found.");
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
         //if (player != null) { player.update();}
        checkMapTransition();
        // ANIMATE OBJECTS
        // ANIMATE OBJECTS
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null && obj[i] instanceof OBJ_Torch) {
                ((OBJ_Torch) obj[i]).updateAnimation();
            }
            // ADD THIS:
            if (obj[i] != null && obj[i] instanceof NPC) {
                ((NPC) obj[i]).updateAnimation();
            }
        }
        mapLabel.update();
        // Dialogue update
        dialogueSystem.update();
        interactionPrompt.update();


// Find nearby NPC
        nearbyNPC = null;
        if (!dialogueSystem.isActive()) {
            for (SuperObject o : obj) {
                if (o instanceof NPC) {
                    NPC npc = (NPC) o;
                    if (npc.available && npc.isPlayerNearby()) {
                        nearbyNPC = npc;
                        break;
                    }
                }
            }
        }

// E key to open dialogue
        if (keyH.ePressed && !ePressedLastFrame && nearbyNPC != null
                && !dialogueSystem.isActive()) {
            String cls = player.getClass().getSimpleName(); // "Swordsman", "Ranger", "Mage"
            dialogueSystem.open(nearbyNPC, cls);
        }
        ePressedLastFrame = keyH.ePressed;

// Block movement during dialogue
        // Alternative: only skip player movement
        if (!dialogueSystem.isActive()) {
            player.update();
        }
    }

    public void checkMapTransition() {
        if (player == null) return;

        // --- MAP 1  ---
        if (currentMap == 0) {
            // EXIT RIGHT -> TO MAP 2
            if (player.worldX > worldWidth - (tileSize * 1.5)) {
                currentMap = 1;
                tileM.loadMap("/maps/world02.txt");


                aSetter.setObject();
                mapLabel.reset("Map 2", "The Sorcerer's Lair");
                player.worldX = tileSize * 3;
                player.worldY = tileSize * 10;
            }
        }

        // --- MAP 2 ---
        else if (currentMap == 1) {
            // ... (previous left-exit code)

            // EXIT RIGHT -> TO MAP 3
            if (player.worldX > worldWidth - (tileSize * 1.5)) {
                currentMap = 2;
                tileM.loadMap("/maps/world03.txt");
                aSetter.setObject();
                mapLabel.reset("Map 3", "The Sorcerer's Lair - Final Battle");

                // SPAWN ON MAP 3: Top-Left Corner
                player.worldX = tileSize * 2; // 2 tiles from the left
                player.worldY = tileSize * 2; // 2 tiles from the top

            }
        }

        // --- MAP 3  ---
        else if (currentMap == 2) {
            // EXIT LEFT -> TO MAP 2
            if (player.worldX < tileSize) {
                currentMap = 1;
                tileM.loadMap("/maps/world02.txt");
                aSetter.setObject();

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
        // Draw interaction prompt above nearby NPC
        if (nearbyNPC != null && !dialogueSystem.isActive()) {
            int nx = nearbyNPC.worldX - player.worldX + player.screenX + tileSize / 2;
            int ny = nearbyNPC.worldY - player.worldY + player.screenY - 20;
            interactionPrompt.draw(g2, nx, ny);
        }
        mapLabel.draw(g2);
// Draw dialogue overlay (always on top)
        dialogueSystem.draw(g2);

        g2.dispose();
    }
}