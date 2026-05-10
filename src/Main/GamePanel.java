package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;

import Entities.Characters.*;
import Tile.TileManager;
import Objects.*;
import Entities.Characters.NPC;
import Dialogue.*;
import StoryLine.*;

import static Main.GameStateManager.Map1Phase.COLLECT_ESSENCE;

public class GamePanel extends JPanel implements Runnable {

    // ── Systems ───────────────────────────────────────────────────
    public TileManager       tileM;
    public KeyHandler        keyH;
    public CollisionChecker  cChecker;
    public AssetSetter       aSetter;
    Thread gameThread;
    public DialogueSystem    dialogueSystem;
    public InteractionPrompt interactionPrompt;
    public MapLabel          mapLabel;
    public GameStateManager  gsm;
    public WeaponPopup       weaponPopup;
    public ScreenMessage     screenMessage;
    public ObjectivesHUD     objectivesHUD;
    public BattleTransition  battleTransition;
    public EmoteSystem       emoteSystem;
    public WeatherSystem     weatherSystem;
    public GossipSystem      gossipSystem;
    public PetCompanion      pet;
    public Minimap           minimap;

    private NPC              nearbyNPC   = null;
    private OBJ_NoticeBoard  nearbyBoard = null;
    private boolean ePressedLastFrame    = false;

    // Map 2 intro
    private boolean map2PlayerFrozen = false;
    private boolean map2ExploreShown = false;
    private boolean map2ExploreReady = false;

    private EssenceParticle essenceParticle = new EssenceParticle();

    // Inventory management
    private InventoryPanel currentInventoryPanel;
    private boolean inventoryOpen = false;
    private JFrame parentFrame;

    // Character management
    private CharacterPanel currentCharacterPanel;
    private boolean characterOpen = false;

    // ── Screen settings ───────────────────────────────────────────
    public final int originalTileSize = 32;
    public final int scale            = 2;
    public final int tileSize         = originalTileSize * scale;
    public final int maxScreenCol     = 16;
    public final int maxScreenRow     = 12;
    public final int screenWidth      = tileSize * maxScreenCol;
    public final int screenHeight     = tileSize * maxScreenRow;

    // ── World settings ────────────────────────────────────────────
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth  = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    public int FPS = 60;

    // ── Entities ──────────────────────────────────────────────────
    public Player      player;
    public SuperObject obj[] = new SuperObject[100000];
    public int         currentMap = 0;

    // ─────────────────────────────────────────────────────────────
    public GamePanel() { this(null, new KeyHandler()); }

    public GamePanel(Player selectedPlayer, KeyHandler keyH) {
        this.keyH = keyH;

        this.tileM        = new TileManager(this);
        this.cChecker     = new CollisionChecker(this);
        this.aSetter      = new AssetSetter(this);
        this.weatherSystem = new WeatherSystem();
        this.gossipSystem  = new GossipSystem();
        this.pet           = new PetCompanion();
        this.minimap       = new Minimap();

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyH);
        this.setFocusable(true);

        // ── Player ────────────────────────────────────────────────
        if (selectedPlayer != null) {
            this.player = selectedPlayer;
            try {
                java.lang.reflect.Field gpField =
                        this.player.getClass().getSuperclass().getDeclaredField("gp");
                gpField.setAccessible(true);
                gpField.set(this.player, this);
            } catch (Exception e) {
                System.err.println("Warning: Could not set gp in player: " + e.getMessage());
            }
        } else {
            this.player = new Swordsman(this, this.keyH);
        }

        // ── Core systems ──────────────────────────────────────────
        this.dialogueSystem    = new DialogueSystem();
        this.interactionPrompt = new InteractionPrompt();

        gossipSystem.setDialogueSystem(this.dialogueSystem);

        // ── Single mouse listener ─────────────────────────────────
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (emoteSystem.handleClick(e.getX(), e.getY())) return;
                if (dialogueSystem.isActive()) {
                    dialogueSystem.handleClick(e.getX(), e.getY());
                }
            }
        });

        // ── Phase change callback ─────────────────────────────────
        dialogueSystem.setOnPhaseChangeCallback(() -> {
            GameStateManager.Map1Phase phase = GameStateManager.get().map1Phase;
            if (phase == GameStateManager.Map1Phase.TALK_TO_RANGER) {
                objectivesHUD.setSimpleObjective("Talk to the Ranger");
            }
        });

        // ── Weapon popup callback ─────────────────────────────────
        dialogueSystem.setWeaponPopupCallback(() -> {
            String cls = player.getClass().getSimpleName();
            String weaponPath, weaponName;
            switch (cls) {
                case "Ranger":
                    weaponPath = "/items/archer_weapon/mistwood.png";
                    weaponName = "Mistwood";
                    break;
                case "Mage":
                    weaponPath = "/items/mage_weapon/ankh_staff.png";
                    weaponName = "Ankh Staff";
                    break;
                default:
                    weaponPath = "/items/warrior_weapon/razoredge_sword.png";
                    weaponName = "Razoredge Sword";
                    break;
            }

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] instanceof NPC) {
                    NPC npc = (NPC) obj[i];
                    if (npc.npcName.equals("Chief") || npc.npcName.equals("Ranger")) {
                        npc.available = false;
                    }
                }
            }

            weaponPopup.show(weaponPath, weaponName, "Given by the Ranger", () -> {
                GameStateManager.get().map1Phase = GameStateManager.Map1Phase.FIGHT_ENEMIES;
                objectivesHUD.setObjective("Fight the enemies", 0, 2);
                spawnMap1Enemies();
            });
        });

        // ── Essence callback ──────────────────────────────────────
        dialogueSystem.setOnEssenceGranted(npcName -> {
            int npcIndex = getEssenceIndex(npcName);
            if (npcIndex >= 0) {
                GameStateManager.get().collectEssence(npcIndex);
                int count = GameStateManager.get().essenceCount;
                objectivesHUD.updateProgress(count);
                essenceParticle.trigger(screenWidth / 2, screenHeight / 2);

                if (GameStateManager.get().allEssenceCollected()) {
                    screenMessage.show("Full Essence Complete!", "Ready for Round 2!", 220, false);
                    javax.swing.Timer t = new javax.swing.Timer(3000, e -> {
                        GameStateManager.get().isMap2Revisit = true;
                        objectivesHUD.setSimpleObjective("Return to Map 2");
                        screenMessage.show("Map 2 is now accessible!",
                                "Head there to continue.", 160, false);
                    });
                    t.setRepeats(false);
                    t.start();
                }
            }
        });

        // ── Shop callback ─────────────────────────────────────────
        dialogueSystem.setOnOpenShop(() -> {
            if (parentFrame == null)
                parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

            // Use the nearby NPC's name to determine shopkeeper
            String shopName = "Frank"; // default
            if (nearbyNPC != null) {
                shopName = nearbyNPC.npcName; // "Frank" or "Bukog"
            }

            final Entities.NPCs.Shopkeeper sk = new Entities.NPCs.Shopkeeper(shopName);
            final GamePanel gpRef = this;

            ShopPanel shopPanel = new ShopPanel(parentFrame, player, sk, () -> {
                SwingUtilities.invokeLater(() -> {
                    parentFrame.getContentPane().removeAll();
                    parentFrame.add(gpRef);
                    parentFrame.revalidate();
                    parentFrame.repaint();
                    gpRef.setVisible(true);
                    gpRef.requestFocusInWindow();
                });
            });

            this.setVisible(false);
            parentFrame.getContentPane().removeAll();
            parentFrame.add(shopPanel);
            parentFrame.revalidate();
            parentFrame.repaint();
            shopPanel.requestPanelFocus();
        });

        // ── Other systems ─────────────────────────────────────────
        this.gsm            = GameStateManager.get();
        this.weaponPopup    = new WeaponPopup();
        this.screenMessage  = new ScreenMessage();
        this.objectivesHUD  = new ObjectivesHUD();
        this.battleTransition = new BattleTransition();
        this.emoteSystem    = new EmoteSystem();

        emoteSystem.setOnEmotePicked(emote -> {
            pet.reactToEmote(emote);
            emoteSystem.spawnEmote(emote,
                    player.worldX + tileSize / 2,
                    player.worldY - tileSize / 2);
        });

        emoteSystem.setOnCustomizePet(() -> pet.openCustomizeDialog(this));

        this.mapLabel = new MapLabel("Map 1", "The Neverwinter Village");
        objectivesHUD.setSimpleObjective("Talk to the Chief");
        objectivesHUD.setEggCodeObjective("Solve the secret code");

        // ── Player portrait ───────────────────────────────────────
        try {
            String cls         = player.getClass().getSimpleName().toLowerCase();
            String portraitName = cls.equals("ranger") ? "archer" : cls;
            java.awt.image.BufferedImage pp = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/player/" + portraitName + "_portrait.png"));
            dialogueSystem.setPlayerPortrait(pp);
        } catch (Exception e) {
            System.err.println("Player portrait not found.");
        }

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                emoteSystem.updateMouse(e.getX(), e.getY());
                if (dialogueSystem.isActive()) {
                    dialogueSystem.handleHover(e.getX(), e.getY());
                }
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                emoteSystem.updateMouse(e.getX(), e.getY());
            }
        });
    }

    // ─────────────────────────────────────────────────────────────
    private int getEssenceIndex(String npcName) {
        switch (npcName) {
            case "Healer":         return 0;
            case "Ranger":         return 1;
            case "Woman Villager": return 2;
            case "Farmer":         return 3;
            case "Chief":          return 4;
            default:               return -1;
        }
    }

    public void setupGame()      { aSetter.setObject(); }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / FPS;
        double delta   = 0;
        long lastTime  = System.nanoTime();

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    // ═════════════════════════════════════════════════════════════
    //  UPDATE
    // ═════════════════════════════════════════════════════════════
    public void update() {
        checkMapTransition();

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] == null) continue;
            if (obj[i] instanceof OBJ_Torch) ((OBJ_Torch) obj[i]).updateAnimation();
            if (obj[i] instanceof NPC)        ((NPC)       obj[i]).updateAnimation();
        }

        weaponPopup.update();
        screenMessage.update();
        objectivesHUD.update();
        battleTransition.update();
        mapLabel.update();
        dialogueSystem.update();
        interactionPrompt.update();
        essenceParticle.update();
        emoteSystem.update();
        weatherSystem.update(screenWidth, screenHeight);
        gossipSystem.update(obj);
        minimap.update();

        pet.update(player.worldX, player.worldY,
                player.screenX, player.screenY);

        // ── Find nearby NPC ───────────────────────────────────────
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

        // ── Find nearby Notice Board ──────────────────────────────
        nearbyBoard = null;
        for (SuperObject o : obj) {
            if (o instanceof OBJ_NoticeBoard) {
                OBJ_NoticeBoard board = (OBJ_NoticeBoard) o;
                if (board.isPlayerNearby(this)) {
                    nearbyBoard = board;
                    break;
                }
            }
        }

        // ── Player movement ───────────────────────────────────────
        if (!dialogueSystem.isActive() && !map2PlayerFrozen) {
            player.update();
        }

        // ── Inventory key press ───────────────────────────────────
        if (keyH.iPressed) {
            if (!inventoryOpen && !characterOpen) {
                openInventory();
            } else if (inventoryOpen) {
                closeInventory();
            }
            keyH.iPressed = false;
        }

        // ── Character panel key press ─────────────────────────────
        if (keyH.cPressed) {
            if (!characterOpen && !inventoryOpen) {
                openCharacter();
            } else if (characterOpen) {
                closeCharacter();
            }
            keyH.cPressed = false;
        }

        // ── E key interactions ────────────────────────────────────
        if (keyH.ePressed && !ePressedLastFrame && !dialogueSystem.isActive()) {

            if (nearbyBoard != null) {
                // ── Notice board interaction gate ─────────────────
                // Blocked during essence collection (Map 1 revisit) — puzzle already solved
                if (GameStateManager.get().map1Phase == COLLECT_ESSENCE) {
                    screenMessage.show("Already Solved!",
                            "Focus on collecting the Essence.", 80, false);

                    // Blocked before boss is defeated (still in first run of Map 1)
                } else if (!GameStateManager.get().map1BossDefeated) {
                    screenMessage.show("Defeat Boss First!",
                            "Then return for the code mystery...", 120, false);

                    // Normal interaction — Map 1 boss defeated, not yet in essence phase
                } else {
                    nearbyBoard.interact(this);
                }

                ePressedLastFrame = keyH.ePressed;
                return;
            }

            // ── NPC interactions ──────────────────────────────────
            if (nearbyNPC != null) {
                GameStateManager.Map1Phase phase = GameStateManager.get().map1Phase;
                boolean canInteract             = false;
                boolean shouldShowLockedMessage = false;

                if (nearbyNPC.npcName.equals("Frank")
                        || nearbyNPC.npcName.equals("Bukog")) {
                    canInteract = true;

                } else if (nearbyNPC.npcName.equals("Chief")) {
                    if (phase == GameStateManager.Map1Phase.TALK_TO_CHIEF) {
                        canInteract = true;
                    } else if (phase == COLLECT_ESSENCE && !nearbyNPC.interacted) {
                        canInteract = true;
                    } else {
                        shouldShowLockedMessage = true;
                    }

                } else if (nearbyNPC.npcName.equals("Ranger")) {
                    if (phase == GameStateManager.Map1Phase.TALK_TO_RANGER) {
                        canInteract = true;
                    } else if (phase == COLLECT_ESSENCE && !nearbyNPC.interacted) {
                        canInteract = true;
                    } else {
                        shouldShowLockedMessage = true;
                    }

                } else if (nearbyNPC.npcName.equals("Healer")
                        || nearbyNPC.npcName.equals("Farmer")
                        || nearbyNPC.npcName.equals("Woman Villager")) {
                    if (phase == COLLECT_ESSENCE && !nearbyNPC.interacted) {
                        canInteract = true;
                    } else {
                        shouldShowLockedMessage = true;
                    }

                } else if (nearbyNPC.npcName.equals("Frankenstein")) {
                    if (currentMap == 1
                            && !GameStateManager.get().isMap2Revisit
                            && nearbyNPC.available
                            && GameStateManager.get().map2BossSpawned) {
                        canInteract = true;
                    } else {
                        shouldShowLockedMessage = true;
                    }

                } else {
                    shouldShowLockedMessage = true;
                }

                if (canInteract) {
                    dialogueSystem.open(nearbyNPC, getPlayerDisplayClass());
                } else if (shouldShowLockedMessage) {
                    screenMessage.show("Cannot Interact.",
                            "Proceed with Next Objectives.", 80, false);
                }
            }
        }
        ePressedLastFrame = keyH.ePressed;
    }

    // ─────────────────────────────────────────────────────────────
    public void triggerEasterEggFromCode() {
        if (!GameStateManager.get().easterEggUnlockedByCode) {
            GameStateManager.get().easterEggUnlockedByCode = true;
            screenMessage.show(
                    "Code Unlocked!",
                    "Map 2 Easter Egg Spawn Guaranteed",
                    220, false);
            objectivesHUD.markEggCodeUnlocked();
        }
    }

    // ═════════════════════════════════════════════════════════════
    //  INVENTORY & CHARACTER PANEL
    // ═════════════════════════════════════════════════════════════
    private void openInventory() {
        if (parentFrame == null) parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        inventoryOpen = true;
        currentInventoryPanel = new InventoryPanel(parentFrame, player, false,
                (item, target) -> System.out.println("Cannot use items outside of combat!"),
                () -> closeInventory()
        );
        this.setVisible(false);
        parentFrame.getContentPane().removeAll();
        parentFrame.add(currentInventoryPanel);
        parentFrame.revalidate(); parentFrame.repaint();
        currentInventoryPanel.requestFocusInWindow();
    }

    public void closeInventory() {
        inventoryOpen = false;
        currentInventoryPanel = null;
        parentFrame.getContentPane().removeAll();
        parentFrame.add(this);
        parentFrame.revalidate(); parentFrame.repaint();
        this.setVisible(true);
        this.requestFocusInWindow();
    }

    private void openCharacter() {
        if (parentFrame == null) parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        characterOpen = true;
        currentCharacterPanel = new CharacterPanel(parentFrame, player, false,
                () -> closeCharacter()
        );
        this.setVisible(false);
        parentFrame.getContentPane().removeAll();
        parentFrame.add(currentCharacterPanel);
        parentFrame.revalidate(); parentFrame.repaint();
        currentCharacterPanel.requestFocusInWindow();
    }

    public void closeCharacter() {
        characterOpen = false;
        currentCharacterPanel = null;
        parentFrame.getContentPane().removeAll();
        parentFrame.add(this);
        parentFrame.revalidate(); parentFrame.repaint();
        this.setVisible(true);
        this.requestFocusInWindow();
    }

    // ═════════════════════════════════════════════════════════════
    //  MAP TRANSITIONS
    // ═════════════════════════════════════════════════════════════
    public void checkMapTransition() {

        // MAP 1 → MAP 2
        if (currentMap == 0) {
            if (player.worldX > worldWidth - (tileSize * 1.5)) {
                if (!GameStateManager.get().map1BossDefeated
                        && GameStateManager.get().map1Phase
                        != GameStateManager.Map1Phase.COLLECT_ESSENCE) {
                    player.worldX = worldWidth - (tileSize * 2);
                    screenMessage.show("Defeat the Boss first!", null, 60, false);
                    return;
                }
                if (GameStateManager.get().map1Phase == COLLECT_ESSENCE
                        && !GameStateManager.get().allEssenceCollected()) {
                    player.worldX = worldWidth - (tileSize * 2);
                    screenMessage.show("Collect all 5 Essence first!",
                            "Seek out the villagers.", 80, false);
                    return;
                }
                if (!isEasterEggUnlocked()) {
                    player.worldX = worldWidth - (tileSize * 2);
                    screenMessage.show("Secret Code Required! 🥚",
                            "Solve Notice Board First", 120, false);
                    return;
                }

                currentMap = 1;
                tileM.loadMap("/maps/world02.txt");
                aSetter.setObject();
                mapLabel.reset("Map 2", "The Sorcerer's Lair");

                player.worldX = tileSize * 3;
                player.worldY = tileSize * 10;

                objectivesHUD.clearEggCodeObjective();
                map2ExploreShown = false;
                map2ExploreReady = false;
                if (!GameStateManager.get().map2IntroShown
                        && !GameStateManager.get().isMap2Revisit) {
                    triggerMap2Intro();
                } else if (GameStateManager.get().isMap2Revisit) {
                    objectivesHUD.setObjective("Fight the enemies", 0, 2);
                    map2PlayerFrozen = false;
                }
            }
        }

        // MAP 2 → MAP 3 or MAP 2 → MAP 1
        else if (currentMap == 1) {
            if (player.worldX > worldWidth - (tileSize * 1.5)) {
                if (!GameStateManager.get().isMap2Revisit
                        && !GameStateManager.get().map2BossDefeated) {
                    player.worldX = worldWidth - (tileSize * 2);
                    screenMessage.show("Defeat Zed first!", null, 60, false);
                    return;
                }
                if (GameStateManager.get().isMap2Revisit
                        && !GameStateManager.get().map2RevisitCleared) {
                    player.worldX = worldWidth - (tileSize * 2);
                    screenMessage.show("Defeat the enemies first!", null, 60, false);
                    return;
                }

                currentMap = 2;
                tileM.loadMap("/maps/world03.txt");
                aSetter.setObject();
                mapLabel.reset("Map 3", "The Sorcerer's Lair - Final Battle");
                objectivesHUD.setObjective("Fight the enemy", 0, 1);
                player.worldX = tileSize * 2;
                player.worldY = tileSize * 2;
            }

            if (player.worldX < tileSize && !GameStateManager.get().isMap2Revisit) {
                currentMap = 0;
                tileM.loadMap("/maps/world01.txt");
                aSetter.setObject();
                mapLabel.reset("Map 1", "The Neverwinter Village");
                player.worldX = worldWidth - (tileSize * 3);
                player.worldY = tileSize * 10;
            }
        }

        // MAP 3 → MAP 2
        else if (currentMap == 2) {
            if (player.worldX < tileSize) {
                currentMap = 1;
                tileM.loadMap("/maps/world02.txt");
                aSetter.setObject();
                player.worldX = worldWidth - (tileSize * 3);
                player.worldY = tileSize * 10;
            }
        }
    }

    // ═════════════════════════════════════════════════════════════
    //  MAP 2 INTRO
    // ═════════════════════════════════════════════════════════════
    private void triggerMap2Intro() {
        GameStateManager.get().map2IntroShown = true;
        map2PlayerFrozen = true;

        screenMessage.show("A week into the journey...", null, 120, false);
        javax.swing.Timer step2 = new javax.swing.Timer(2500, e -> {
            screenMessage.show("The ruins of a village...", null, 120, false);
            javax.swing.Timer step3 = new javax.swing.Timer(2500, ev ->
                    showMap2PlayerMonologue());
            step3.setRepeats(false);
            step3.start();
        });
        step2.setRepeats(false);
        step2.start();
    }

    private void showMap2PlayerMonologue() {
        String cls = getPlayerDisplayClass();
        String displayName;
        switch (cls) {
            case "Archer":    displayName = "Khai the Archer";    break;
            case "Mage":      displayName = "Khai the Mage";      break;
            case "Swordsman": displayName = "Khai the Swordsman"; break;
            default:          displayName = "Khai";               break;
        }

        String line = "The silence here is wrong. This magic... "
                + "it's the same as the withering. He passed through here. "
                + "North. He's in the north. I can feel it.";

        final String finalName = displayName;
        Entities.Characters.NPC monoNPC = new Entities.Characters.NPC(this) {
            @Override
            public Dialogue.DialogueTree getDialogue(String c) {
                Dialogue.DialogueTree t = new Dialogue.DialogueTree();
                t.addPage(new Dialogue.DialogueTree.Page(finalName, line, true)
                        .addChoice("Explore the Ruins", -1));
                return t;
            }
        };
        monoNPC.npcName   = finalName;
        monoNPC.available = true;

        try {
            String portraitName = cls.equalsIgnoreCase("ranger")
                    ? "archer" : cls.toLowerCase();
            java.awt.image.BufferedImage pp =
                    javax.imageio.ImageIO.read(getClass().getResourceAsStream(
                            "/player/" + portraitName + "_portrait.png"));
            dialogueSystem.setPlayerPortrait(pp);
        } catch (Exception e) { /* ignore */ }

        dialogueSystem.open(monoNPC, cls);
        dialogueSystem.setOnMap2MonologueDone(() -> {
            map2PlayerFrozen = false;
            objectivesHUD.setObjective("Fight the enemies", 0, 2);
            screenMessage.show("Explore the ruins...", null, 90, false);
        });
    }

    // ═════════════════════════════════════════════════════════════
    //  ENEMY SPAWNS & BATTLES — MAP 1
    // ═════════════════════════════════════════════════════════════
    public void spawnMap1Enemies() {
        if (!GameStateManager.get().map1EnemiesDefeated_masklet) {
            Entities.Enemies.MapEnemy_Masklet masklet =
                    new Entities.Enemies.MapEnemy_Masklet(this);
            masklet.worldX = 8 * tileSize;
            masklet.worldY = 5 * tileSize;
            setupEnemyOnMap(masklet, true);
            obj[20] = masklet;
        }
        if (!GameStateManager.get().map1EnemiesDefeated_zenzilla) {
            Entities.Enemies.MapEnemy_Zenzilla zenzilla =
                    new Entities.Enemies.MapEnemy_Zenzilla(this);
            zenzilla.worldX = 15 * tileSize;
            zenzilla.worldY = 10 * tileSize;
            setupEnemyOnMap(zenzilla, false);
            obj[21] = zenzilla;
        }
    }

    private void setupEnemyOnMap(Entities.Enemies.EnemyEntity enemy, boolean isMasklet) {
        enemy.setOnBattleTrigger(() -> {
            if (battleTransition.isRunning()) return;
            battleTransition.start(() -> launchEnemyBattle(enemy, isMasklet));
        });
    }

    private void launchEnemyBattle(Entities.Enemies.EnemyEntity mapEnemy,
                                   boolean isMasklet) {
        final double atkBeforeBattle = player.getAttack();
        final double defBeforeBattle = player.getDefense();

        // Randomize number of enemies between 1 and 3
        int enemyCount = 1 + new Random().nextInt(3);

        javax.swing.JFrame frame =
                (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (frame == null) return;

        java.util.List<Entities.Enemies.Enemy> enemiesInBattle = new java.util.ArrayList<>();
        for (int i = 0; i < enemyCount; i++) {
            enemiesInBattle.add(mapEnemy.createBattleEnemy());
        }

        Combat.BattlePanel bp;

        if (enemyCount == 1) {
            bp = new Combat.BattlePanel(player, enemiesInBattle.get(0));
        } else if (enemyCount == 2) {
            bp = new Combat.BattlePanel(player,
                    enemiesInBattle.get(0),
                    enemiesInBattle.get(1));
        } else {
            bp = new Combat.BattlePanel(player,
                    enemiesInBattle.get(0),
                    enemiesInBattle.get(1),
                    enemiesInBattle.get(2));
        }

        final GamePanel gpRef = this;
        final java.util.List<Entities.Enemies.Enemy> finalEnemies = enemiesInBattle;

        bp.setOnBattleEnd(() -> {
            boolean playerWon = player.getHp() > 0;

            boolean allEnemiesDefeated = true;
            for (Entities.Enemies.Enemy e : finalEnemies) {
                if (e.getHp() > 0) {
                    allEnemiesDefeated = false;
                    System.out.println("Enemy still alive: " + e.getName() + " HP: " + e.getHp());
                    break;
                }
            }

            boolean won = playerWon && allEnemiesDefeated;

            //Reset all buffs after battle
            restorePlayerStats(atkBeforeBattle, defBeforeBattle);

            javax.swing.SwingUtilities.invokeLater(() -> {
                frame.getContentPane().removeAll();
                frame.add(gpRef);
                frame.revalidate();
                frame.repaint();
                gpRef.requestFocusInWindow();
                battleTransition = new BattleTransition();
                map2PlayerFrozen = false;
            });

            if (won) {
                mapEnemy.defeated = true;
                mapEnemy.available = false;

                if (isMasklet) {
                    GameStateManager.get().map1EnemiesDefeated_masklet = true;
                } else {
                    GameStateManager.get().map1EnemiesDefeated_zenzilla = true;
                }

                GameStateManager.get().map1EnemiesDefeated++;
                int count = GameStateManager.get().map1EnemiesDefeated;

                javax.swing.Timer hudTimer = new javax.swing.Timer(200, e -> {
                    objectivesHUD.updateProgress(count);
                    if (count >= 2 && !GameStateManager.get().map1BossSpawned) {
                        GameStateManager.get().map1BossSpawned = true;
                        GameStateManager.get().map1Phase = GameStateManager.Map1Phase.FIGHT_BOSS;
                        objectivesHUD.setObjective("Defeat the Boss", 0, 1);
                        screenMessage.show("The Boss Has Spawned!",
                                "Find and defeat Thorncrusher!", 180, false);
                        spawnMap1Boss();
                    }
                });
                hudTimer.setRepeats(false);
                hudTimer.start();
            } else {
                javax.swing.Timer t = new javax.swing.Timer(2500,
                        ev -> returnToTitleScreen(frame));
                t.setRepeats(false);
                t.start();
                javax.swing.SwingUtilities.invokeLater(() ->
                        screenMessage.show("Game Over", null, 120, false));
            }
        });

        frame.getContentPane().removeAll();
        frame.add(bp);
        frame.revalidate();
        frame.repaint();
    }

    private void restorePlayerStats(double atkBeforeBattle, double defBeforeBattle) {
        if (player instanceof Swordsman) {
            ((Swordsman) player).resetBattleBuffs();
        } else if (player instanceof Ranger) {
            ((Ranger) player).resetBattleBuffs();
        } else if (player instanceof Mage) {
            ((Mage) player).resetBattleBuffs();
        }

        player.setAttack(atkBeforeBattle);
        player.setDefense(defBeforeBattle);
    }

    private void spawnMap1Boss() {
        Entities.Enemies.MapBoss_Thorncrusher boss =
                new Entities.Enemies.MapBoss_Thorncrusher(this);
        boss.worldX = 20 * tileSize;
        boss.worldY = 40 * tileSize;
        boss.setOnBattleTrigger(() -> {
            if (battleTransition.isRunning()) return;
            battleTransition.start(() -> launchBossBattle(boss));
        });
        obj[30] = boss;
    }

    private void launchBossBattle(Entities.Enemies.MapBoss_Thorncrusher mapBoss) {
        Entities.Enemies.Enemy bossEnemy = mapBoss.createBattleEnemy();
        javax.swing.JFrame frame =
                (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (frame == null) return;

        Combat.BattlePanel bp = new Combat.BattlePanel(player, bossEnemy);
        final GamePanel gpRef = this;

        bp.setOnBattleEnd(() -> {
            boolean won = bossEnemy.getHp() <= 0;
            javax.swing.SwingUtilities.invokeLater(() -> {
                frame.getContentPane().removeAll();
                frame.add(gpRef);
                frame.revalidate(); frame.repaint();
                gpRef.requestFocusInWindow();
                battleTransition = new BattleTransition();
                map2PlayerFrozen = false;
            });

            if (won) {
                mapBoss.defeated  = true;
                mapBoss.available = false;
                GameStateManager.get().map1BossDefeated = true;
                GameStateManager.get().map1Phase = GameStateManager.Map1Phase.COMPLETE;

                javax.swing.Timer hudTimer = new javax.swing.Timer(200, e -> {
                    objectivesHUD.updateProgress(1);

                    boolean allObjectivesComplete = GameStateManager.get().allEssenceCollected()
                            && isEasterEggUnlocked();

                    if (allObjectivesComplete) {
                        screenMessage.show("ALL OBJECTIVES COMPLETE!",
                                "The Bridge to the Next Map is NOW OPEN!", 220, true);
                    } else {
                        screenMessage.show("Boss Defeated!",
                                "Complete remaining objectives to unlock Map 2", 180, false);
                    }

                    lockNPCsExceptShop();
                });
                hudTimer.setRepeats(false);
                hudTimer.start();
            } else {
                javax.swing.Timer t = new javax.swing.Timer(2500,
                        ev -> returnToTitleScreen(frame));
                t.setRepeats(false); t.start();
                javax.swing.SwingUtilities.invokeLater(() ->
                        screenMessage.show("Game Over", null, 120, false));
            }
        });

        frame.getContentPane().removeAll();
        frame.add(bp);
        frame.revalidate(); frame.repaint();
    }

    // ═════════════════════════════════════════════════════════════
    //  ENEMY SPAWNS & BATTLES — MAP 2
    // ═════════════════════════════════════════════════════════════
    public void spawnMap2Enemies() {
        if (!GameStateManager.get().map2EnemiesDefeated_sanjveil) {
            Entities.Enemies.MapEnemy_Sanjveil sanjveil =
                    new Entities.Enemies.MapEnemy_Sanjveil(this);
            sanjveil.worldX = 25 * tileSize;
            sanjveil.worldY = 18 * tileSize;
            setupMap2Enemy(sanjveil, true);
            obj[20] = sanjveil;
        }
        if (!GameStateManager.get().map2EnemiesDefeated_razormaw) {
            Entities.Enemies.MapEnemy_Razormaw razormaw =
                    new Entities.Enemies.MapEnemy_Razormaw(this);
            razormaw.worldX = 32 * tileSize;
            razormaw.worldY = 24 * tileSize;
            setupMap2Enemy(razormaw, false);
            obj[21] = razormaw;
        }
    }

    private void setupMap2Enemy(Entities.Enemies.EnemyEntity enemy, boolean isSanjveil) {
        enemy.setOnBattleTrigger(() -> {
            if (battleTransition.isRunning()) return;
            battleTransition.start(() -> launchMap2EnemyBattle(enemy, isSanjveil));
        });
    }

    private void launchMap2EnemyBattle(Entities.Enemies.EnemyEntity mapEnemy,
                                       boolean isSanjveil) {
        Entities.Enemies.Enemy battleEnemy = mapEnemy.createBattleEnemy();
        javax.swing.JFrame frame =
                (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (frame == null) return;

        Combat.BattlePanel bp = new Combat.BattlePanel(player, battleEnemy);
        final GamePanel gpRef = this;

        bp.setOnBattleEnd(() -> {
            boolean won = battleEnemy.getHp() <= 0;
            javax.swing.SwingUtilities.invokeLater(() -> {
                frame.getContentPane().removeAll();
                frame.add(gpRef);
                frame.revalidate(); frame.repaint();
                gpRef.requestFocusInWindow();
                battleTransition = new BattleTransition();
                map2PlayerFrozen = false;
            });

            if (won) {
                mapEnemy.defeated = true;
                mapEnemy.available = false;
                if (isSanjveil) GameStateManager.get().map2EnemiesDefeated_sanjveil = true;
                else            GameStateManager.get().map2EnemiesDefeated_razormaw  = true;

                GameStateManager.get().map2EnemiesDefeated++;
                int count = GameStateManager.get().map2EnemiesDefeated;

                javax.swing.Timer t = new javax.swing.Timer(200, e -> {
                    objectivesHUD.updateProgress(count);
                    if (count >= 2
                            && !GameStateManager.get().map2BossSpawned
                            && !GameStateManager.get().isMap2Revisit) {

                        GameStateManager.get().map2EnemiesDefeated_run1 = true;
                        GameStateManager.get().map2BossSpawned = true;

                        if (isEasterEggUnlocked()) {
                            screenMessage.show("An Easter Egg Boss has been Dropped!",
                                    "Find it at your own risk!", 180, false);

                            objectivesHUD.setChallenge("Find the Easter Egg Boss!");

                            javax.swing.Timer bossTimer = new javax.swing.Timer(3500, ev -> {
                                screenMessage.show("Zed the Sorcerer has Spawned!",
                                        "Defeat the Boss (Easter Egg still active!)", 180, false);
                                objectivesHUD.setObjective("Defeat the Boss", 0, 1);
                                spawnMap2Boss();
                                enableFrankenstein();
                            });
                            bossTimer.setRepeats(false);
                            bossTimer.start();
                        } else {
                            screenMessage.show("Zed the Sorcerer has Spawned!",
                                    "Defeat the Boss", 180, false);
                            objectivesHUD.setObjective("Defeat the Boss", 0, 1);
                            spawnMap2Boss();
                        }
                    }
                });
                t.setRepeats(false);
                t.start();
            } else {
                javax.swing.Timer t = new javax.swing.Timer(2500,
                        ev -> returnToTitleScreen(frame));
                t.setRepeats(false); t.start();
                javax.swing.SwingUtilities.invokeLater(() ->
                        screenMessage.show("Game Over", null, 120, false));
            }
        });

        frame.getContentPane().removeAll();
        frame.add(bp);
        frame.revalidate(); frame.repaint();
    }

    // ── Map 2 revisit enemy spawns ────────────────────────────────
    public void spawnMap2RevisitEnemies() {
        Entities.Enemies.MapEnemy_Sanjveil sanjveil =
                new Entities.Enemies.MapEnemy_Sanjveil(this);
        sanjveil.worldX = 25 * tileSize;
        sanjveil.worldY = 18 * tileSize;
        sanjveil.setOnBattleTrigger(() -> {
            if (battleTransition.isRunning()) return;
            battleTransition.start(() -> launchMap2RevisitEnemyBattle(sanjveil, true));
        });
        obj[20] = sanjveil;

        Entities.Enemies.MapEnemy_Razormaw razormaw =
                new Entities.Enemies.MapEnemy_Razormaw(this);
        razormaw.worldX = 32 * tileSize;
        razormaw.worldY = 24 * tileSize;
        razormaw.setOnBattleTrigger(() -> {
            if (battleTransition.isRunning()) return;
            battleTransition.start(() -> launchMap2RevisitEnemyBattle(razormaw, false));
        });
        obj[21] = razormaw;
    }

    private void launchMap2RevisitEnemyBattle(Entities.Enemies.EnemyEntity mapEnemy,
                                              boolean isSanjveil) {
        Entities.Enemies.Enemy battleEnemy = mapEnemy.createBattleEnemy();
        javax.swing.JFrame frame =
                (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (frame == null) return;

        Combat.BattlePanel bp = new Combat.BattlePanel(player, battleEnemy);
        final GamePanel gpRef = this;

        bp.setOnBattleEnd(() -> {
            boolean won = battleEnemy.getHp() <= 0;
            javax.swing.SwingUtilities.invokeLater(() -> {
                frame.getContentPane().removeAll();
                frame.add(gpRef);
                frame.revalidate(); frame.repaint();
                gpRef.requestFocusInWindow();
                battleTransition = new BattleTransition();
                map2PlayerFrozen = false;
            });

            if (won) {
                mapEnemy.defeated = true;
                mapEnemy.available = false;
                GameStateManager.get().map2RevisitEnemiesDefeated++;
                int count = GameStateManager.get().map2RevisitEnemiesDefeated;

                javax.swing.Timer t = new javax.swing.Timer(200, e -> {
                    objectivesHUD.updateProgress(count);
                    if (count >= 2) {
                        GameStateManager.get().map2RevisitCleared = true;
                        screenMessage.show("Objectives Cleared!",
                                "Final Fight Unlocked — Proceed to Map 3", 180, false);
                        objectivesHUD.setSimpleObjective("Proceed to Map 3");
                    }
                });
                t.setRepeats(false); t.start();
            } else {
                javax.swing.Timer t = new javax.swing.Timer(2500,
                        ev -> returnToTitleScreen(frame));
                t.setRepeats(false); t.start();
                javax.swing.SwingUtilities.invokeLater(() ->
                        screenMessage.show("Game Over", null, 120, false));
            }
        });

        frame.getContentPane().removeAll();
        frame.add(bp);
        frame.revalidate(); frame.repaint();
    }

    // ═════════════════════════════════════════════════════════════
    //  MAP 2 BOSS
    // ═════════════════════════════════════════════════════════════
    public void spawnMap2Boss() {
        Entities.Enemies.MapBoss_Zed zed = new Entities.Enemies.MapBoss_Zed(this);
        zed.worldX = 5 * tileSize;
        zed.worldY = 37 * tileSize;
        zed.setOnBattleTrigger(() -> {
            if (battleTransition.isRunning()) return;
            battleTransition.start(() -> launchMap2BossBattle(zed));
        });
        obj[30] = zed;
    }

    public void enableFrankenstein() {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] instanceof NPC_Frankenstein) {
                NPC_Frankenstein frank = (NPC_Frankenstein) obj[i];

                frank.available     = false;
                frank.setVisible(false);
                frank.showOnMinimap = false;

                System.out.println("✅ Frankenstein scheduled to spawn (first run)");

                frank.setOnFightChosen(() -> {
                    if (battleTransition.isRunning()) return;
                    battleTransition.start(() -> launchFrankensteinBattle(frank));
                });

                javax.swing.Timer revealTimer = new javax.swing.Timer(3500, e -> {
                    frank.available     = true;
                    frank.setVisible(true);
                    frank.showOnMinimap = false;
                    System.out.println("✅ Frankenstein NOW VISIBLE (first run) at "
                            + frank.worldX / tileSize + "," + frank.worldY / tileSize);
                });
                revealTimer.setRepeats(false);
                revealTimer.start();

                break;
            }
        }
    }

    private void launchMap2BossBattle(Entities.Enemies.MapBoss_Zed mapBoss) {
        Entities.Enemies.Enemy bossEnemy = mapBoss.createBattleEnemy();
        javax.swing.JFrame frame =
                (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (frame == null) return;

        StoryLine.ThroneRoomCutscene cutscene =
                new StoryLine.ThroneRoomCutscene(() -> {
                    Combat.BattlePanel bp = new Combat.BattlePanel(player, bossEnemy);
                    final GamePanel gpRef = this;

                    bp.setOnBattleEnd(() -> {
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            frame.getContentPane().removeAll();
                            frame.revalidate(); frame.repaint();

                            StoryLine.PostDefeatCutscene postCutscene =
                                    new StoryLine.PostDefeatCutscene(() -> {
                                        returnToMap1SecondVisit(frame, gpRef);
                                        map2PlayerFrozen = false;
                                    });
                            frame.getContentPane().removeAll();
                            frame.add(postCutscene);
                            frame.revalidate(); frame.repaint();
                        });
                    });

                    frame.getContentPane().removeAll();
                    frame.add(bp);
                    frame.revalidate(); frame.repaint();
                });

        frame.getContentPane().removeAll();
        frame.add(cutscene);
        frame.revalidate(); frame.repaint();
    }

    private void launchFrankensteinBattle(NPC_Frankenstein frankNPC) {
        Entities.Enemies.Enemy battleEnemy = new Entities.Enemies.Frankenstein();
        javax.swing.JFrame frame =
                (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (frame == null) return;

        Combat.BattlePanel bp = new Combat.BattlePanel(player, battleEnemy);
        final GamePanel gpRef = this;

        GameStateManager.get().easterEggFound = true;
        objectivesHUD.completeChallenge();

        bp.setOnBattleEnd(() -> {
            boolean won = battleEnemy.getHp() <= 0;
            bp.stopAnimationTimer();

            javax.swing.SwingUtilities.invokeLater(() -> {
                frame.getContentPane().removeAll();
                frame.add(gpRef);
                frame.revalidate();
                frame.repaint();
                gpRef.requestFocusInWindow();
                gpRef.battleTransition = new BattleTransition();
                gpRef.map2PlayerFrozen = false;
            });

            if (won) {
                frankNPC.defeated  = true;
                frankNPC.available = false;
                frankNPC.setVisible(false);

                javax.swing.Timer msgTimer = new javax.swing.Timer(300, e ->
                        screenMessage.show(
                                "Easter Egg Boss Defeated!",
                                "Continue your journey...", 180, false));
                msgTimer.setRepeats(false);
                msgTimer.start();

            } else {
                javax.swing.Timer t = new javax.swing.Timer(2500,
                        ev -> returnToTitleScreen(frame));
                t.setRepeats(false);
                t.start();
                javax.swing.SwingUtilities.invokeLater(() ->
                        screenMessage.show("Game Over", null, 120, false));
            }
        });

        frame.getContentPane().removeAll();
        frame.add(bp);
        frame.revalidate();
        frame.repaint();
    }

    // ═════════════════════════════════════════════════════════════
    //  HELPERS
    // ═════════════════════════════════════════════════════════════
    private String getPlayerDisplayClass() {
        String cls = player.getClass().getSimpleName();
        return cls.equals("Ranger") ? "Archer" : cls;
    }

    private void returnToTitleScreen(javax.swing.JFrame frame) {
        Main.GameStateManager.reset();
        javax.swing.SwingUtilities.invokeLater(() -> {
            if (frame instanceof Main.CurseOfZed) {
                Main.CurseOfZed cozFrame = (Main.CurseOfZed) frame;
                Main.TitlePanel titlePanel = new Main.TitlePanel();
                titlePanel.setOnStartCallback(() -> cozFrame.showStoryIntro());
                cozFrame.getContentPane().removeAll();
                cozFrame.add(titlePanel);
                cozFrame.revalidate(); cozFrame.repaint();
            } else {
                Main.CurseOfZed coz = new Main.CurseOfZed();
                coz.setVisible(true);
                frame.dispose();
            }
        });
    }

    private void returnToMap1SecondVisit(javax.swing.JFrame frame, GamePanel gpRef) {
        currentMap = 0;
        GameStateManager.get().isMap2Revisit = false;
        GameStateManager.get().map1Phase     = COLLECT_ESSENCE;

        tileM.loadMap("/maps/world01.txt");
        aSetter.setObject();
        mapLabel.reset("Map 1", "The Neverwinter Village");

        player.worldX = tileSize * 27;
        player.worldY = tileSize * 27;

        objectivesHUD.setObjective("Collect 5 Essence", 0, 5);
        objectivesHUD.clearChallengeObjective();
        objectivesHUD.clearEggCodeObjective();

        screenMessage.show("You have returned...",
                "Seek the essence of your village.", 180, false);

        frame.getContentPane().removeAll();
        frame.add(gpRef);
        frame.revalidate(); frame.repaint();
        gpRef.requestFocusInWindow();
        battleTransition  = new BattleTransition();
        map2PlayerFrozen  = false;
    }

    private void lockNPCsExceptShop() {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] instanceof Entities.Characters.NPC) {
                Entities.Characters.NPC npc = (Entities.Characters.NPC) obj[i];
                if (!npc.npcName.equals("Frank") && !npc.npcName.equals("Bukog")) {
                    npc.interacted = true;
                }
            }
        }
    }

    // ═════════════════════════════════════════════════════════════
    //  PAINT
    // ═════════════════════════════════════════════════════════════
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (player == null) return;

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null
                    && (obj[i] instanceof OBJ_BridgeHorizontal
                    || obj[i] instanceof OBJ_Stone
                    || obj[i] instanceof OBJ_BridgeVertical)) {
                obj[i].draw(g2, this);
            }
        }

        boolean playerDrawn = false;
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] == null) continue;
            if (obj[i] instanceof OBJ_BridgeHorizontal
                    || obj[i] instanceof OBJ_Stone
                    || obj[i] instanceof OBJ_BridgeVertical) continue;

            int objectBottomY = obj[i].worldY;
            if (obj[i].image != null)
                objectBottomY += obj[i].image.getHeight() * scale;

            if (!playerDrawn && player.worldY < objectBottomY) {
                player.draw(g2);
                if (emoteSystem.isPetVisible()) pet.draw(g2);
                playerDrawn = true;
            }
            obj[i].draw(g2, this);
        }

        if (!playerDrawn) {
            player.draw(g2);
            if (emoteSystem.isPetVisible()) pet.draw(g2);
        }

        gossipSystem.draw(g2, this);
        weatherSystem.draw(g2, screenWidth, screenHeight);

        if (nearbyNPC != null && !dialogueSystem.isActive()) {
            int nx = nearbyNPC.worldX - player.worldX + player.screenX + tileSize / 2;
            int ny = nearbyNPC.worldY - player.worldY + player.screenY - 20;
            interactionPrompt.draw(g2, nx, ny);
        }
        if (nearbyBoard != null && !dialogueSystem.isActive()) {
            int nx = nearbyBoard.worldX - player.worldX + player.screenX + tileSize / 2;
            int ny = nearbyBoard.worldY - player.worldY + player.screenY - 20;
            interactionPrompt.draw(g2, nx, ny);
        }

        mapLabel.draw(g2);
        dialogueSystem.draw(g2);
        objectivesHUD.draw(g2);
        mapLabel.draw(g2);
        weaponPopup.draw(g2);
        screenMessage.draw(g2);
        battleTransition.draw(g2);
        essenceParticle.draw(g2);

        emoteSystem.drawFloatingEmotes(g2, this);
        emoteSystem.drawUI(g2, screenWidth);

        minimap.draw(g2, this, weatherSystem);

        g2.dispose();
    }

    private boolean isEasterEggUnlocked() {
        return GameStateManager.get().easterEggUnlockedByCode;
    }
}