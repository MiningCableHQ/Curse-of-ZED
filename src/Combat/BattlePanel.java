package Combat;

import Entities.Characters.*;
import Entities.Enemies.Enemy;
import Entities.Enemies.*;
import Main.GamePanel;
import Moves.Move;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import Items.Item;
import Main.InventoryPanel;
import java.util.function.BiConsumer;

import Combat.StatusEffects.StatusEffect;
import Audio.SFX.ClickSFX;


public class BattlePanel extends JPanel {

    // ── Screen Size (matches GamePanel / TitlePanel) ─────────────
    private static final int ORIGINAL_TILE = 32;
    private static final int SCALE         = 2;
    private static final int TILE          = ORIGINAL_TILE * SCALE;   // 64
    private static final int COLS          = 16;
    private static final int ROWS          = 12;
    public  static final int WIDTH         = TILE * COLS;              // 1024
    public  static final int HEIGHT        = TILE * ROWS;              // 768

    // ── Palette ───────────────────────────────────────────────────
    private static final Color STAT_BG      = Color.WHITE;
    private static final Color STAT_BORDER  = Color.BLACK;
    private static final Color HP_GREEN     = new Color(80,  200, 80);
    private static final Color HP_YELLOW    = new Color(230, 200, 40);
    private static final Color HP_RED       = new Color(220, 60,  60);
    private static final Color TEXT_LIGHT   = new Color(240, 230, 255);
    private static final Color TEXT_GOLD    = new Color(255, 220, 80);

    // ── Parchment Scroll Colors (matching TitlePanel) ─────────────
    private static final Color SCROLL_BORDER  = new Color(80, 38, 2, 230);

    // ── Gold Octagon Button Colors ─────────────────────────────────
    private static final Color BTN_GOLD_NORMAL = new Color(238, 190, 28);
    private static final Color BTN_GOLD_HOVER  = new Color(250, 222, 62);
    private static final Color BTN_GOLD_PRESS  = new Color(178, 108, 0);
    private static final Color BTN_BORDER_CLR  = new Color(82, 38, 0, 215);
    private static final Color BTN_TEXT_DARK   = new Color(42, 12, 0);
    private static final Color BTN_TEXT_LIGHT  = new Color(255, 245, 200);

    // ── Fonts ─────────────────────────────────────────────────────
    private static final Font FONT_TITLE = new Font("Serif",      Font.BOLD,  18);
    private static final Font FONT_STAT  = new Font("Monospaced", Font.BOLD,  13);
    private static final Font FONT_BTN   = new Font("Serif",      Font.BOLD,  16);
    private static final Font FONT_NAME  = new Font("Serif",      Font.BOLD,  20);

    // ── UI State ──────────────────────────────────────────────────
    private enum UIState { MAIN, FIGHT, TARGET_SELECT }
    private UIState uiState = UIState.MAIN;

    // ── Entity refs ───────────────────────────────────────────────
    private final Player playerEntity;
    private final List<Enemy> enemies;

    // ── Battle State ──────────────────────────────────────────────
    private String battleMessage = "";
    private boolean isExecutingMove = false;

    // ── Layout rects ──────────────────────────────────────────────
    private Rectangle playerImgRect;
    private Rectangle playerStatRect;
    private final List<Rectangle> enemyImgRects = new ArrayList<>();
    private final List<Rectangle> enemyStatRects = new ArrayList<>();
    private Rectangle uiBoxRect;

    // ── Player Animation ──────────────────────────────────────────
    private BufferedImage[] playerIdleFrames = new BufferedImage[5];
    private int currentPlayerFrame = 0;
    private Timer animationTimer;
    private Timer enemyAnimationTimer;
    private boolean showStatusEffects = true;

    // ── GIF placeholder labels (enemies only) ─────────────────────
    private final List<JLabel> enemyGifLabels = new ArrayList<>();

    // ── Buttons ───────────────────────────────────────────────────
    private int uiBoxVCenter;
    private BattleButton btnFight;
    private BattleButton btnBag;
    private BattleButton btnRun;
    private BattleButton[] moveBtns;
    private BattleButton btnBack;
    private BattleButton btnBackTarget;
    private final List<BattleButton> targetButtons = new ArrayList<>();

    // ── Battle ────────────────────────────────────────────────────
    private Battle battle;

    // ── Close-to-exploration callback ─────────────────────────────
    private Runnable onBattleEnd;

    // ── Background Image ──────────────────────────────────────────
    private Image backgroundImage;

    // ── Item selection callback ───────────────────────────────────
    private BiConsumer<Item, Enemy> itemSelectionCallback;

    // ── Game Panel ─────────────────────────────────────
    private GamePanel gp;

    // ─────────────────────────────────────────────────────────────
    //  Constructors
    // ─────────────────────────────────────────────────────────────
    public BattlePanel(Player player, Enemy enemy, GamePanel gp) {
        this(player, List.of(enemy), gp);
    }

    public BattlePanel(Player player, Enemy enemy1, Enemy enemy2, GamePanel gp) {
        this(player, List.of(enemy1, enemy2), gp);
    }

    public BattlePanel(Player player, Enemy enemy1, Enemy enemy2, Enemy enemy3, GamePanel gp) {
        this(player, List.of(enemy1, enemy2, enemy3), gp);
    }

    private BattlePanel(Player player, List<Enemy> enemies, GamePanel gp) {
        this.playerEntity = player;
        this.enemies = new ArrayList<>(enemies);
        this.gp = gp;

        if (this.enemies.isEmpty()) {
            throw new IllegalArgumentException("At least one enemy is required");
        }

        // Customize tooltip appearance
        ToolTipManager.sharedInstance().setInitialDelay(300);  // Show after 300ms
        ToolTipManager.sharedInstance().setDismissDelay(8000); // Show for 8 seconds
        ToolTipManager.sharedInstance().setReshowDelay(500);   // Reshow after 500ms

        // Style the tooltips
        UIManager.put("ToolTip.background", new Color(60, 30, 5, 240));
        UIManager.put("ToolTip.foreground", new Color(255, 245, 200));
        UIManager.put("ToolTip.border", BorderFactory.createLineBorder(new Color(255, 220, 80), 2));
        UIManager.put("ToolTip.font", new Font("Serif", Font.PLAIN, 12));

        // Stop whatever music is playing and start battle music immediately
        boolean isBossBattle = this.enemies.stream().anyMatch(e -> e instanceof Entities.Enemies.Boss);
        if (gp != null && gp.musicPlayer != null) {
            System.out.println("[BattlePanel] stopMapMusic() called");
            gp.musicPlayer.stopMapMusic();
            if (isBossBattle) {
                System.out.println("[BattlePanel] getBossMusic().play() called");
                gp.musicPlayer.getBossMusic().play(true);
            } else {
                System.out.println("[BattlePanel] getCombatMusic().play() called");
                gp.musicPlayer.getCombatMusic().play(true);
            }
        }

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        setOpaque(true);

        computeLayout();
        loadPlayerAnimations();
        loadEnemySprites();
        buildEnemyLabels();
        buildButtons();
        loadBackgroundImage(gp.currentMap);
        startAnimationTimer();
        startEnemyAnimationTimer();

        // Initialize battle system
        this.battle = new Battle(this, playerEntity, this.enemies);
        this.battle.setOnBattleEnd(() -> {
            stopAnimationTimer();
            stopEnemyAnimationTimer();
            if (gp != null && gp.musicPlayer != null) {
                if (isBossBattle) {
                    System.out.println("[BattlePanel] getBossMusic().stop() called");
                    gp.musicPlayer.getBossMusic().stop();
                } else {
                    System.out.println("[BattlePanel] getCombatMusic().stop() called");
                    gp.musicPlayer.getCombatMusic().stop();
                }
            }
            if (onBattleEnd != null) {
                onBattleEnd.run();
            }
        });

        // Start the battle
        battle.startBattle();
    }

    /** Load the background image */
    private void loadBackgroundImage(int mapNum) {
        String path;
        switch (mapNum) {
            case 0:  path = "/map1assets/combat_bg_map1.gif";   break;
            case 1:  path = "/map2assets/combat_bg_map2v2.gif"; break;
            case 2:  path = "/map3assets/combat_bg_map3.gif";   break;
            default: path = "/map2assets/combat_bg_map2v2.gif"; break;
        }
        try {
            java.net.URL imgUrl = getClass().getResource(path);
            if (imgUrl != null) {
                backgroundImage = new ImageIcon(imgUrl).getImage();
            } else {
                backgroundImage = new ImageIcon(path).getImage();
            }
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
            backgroundImage = null;
        }
    }

    /** Set custom background image */
    public void setBackgroundImage(Image image) {
        this.backgroundImage = image;
        repaint();
    }

    /** Register a callback invoked when the battle ends */
    public void setOnBattleEnd(Runnable callback) {
        this.onBattleEnd = callback;
    }

    // ─────────────────────────────────────────────────────────────
    //  Player Animation
    // ─────────────────────────────────────────────────────────────
    private void loadPlayerAnimations() {
        String className = getCharacterClassName();
        boolean anyFrameLoaded = false;

        // Try to load 5 idle frames
        for (int i = 0; i < 5; i++) {
            try {
                playerIdleFrames[i] = ImageIO.read(getClass().getResourceAsStream("/" + className + "/"+className+"_idle/idle_right" + (i + 1) + ".png"));
                if (playerIdleFrames[i] != null) anyFrameLoaded = true;
            } catch (Exception e) {
                playerIdleFrames[i] = null;
            }
        }

        // Create fallback placeholders if images not found
        if (!anyFrameLoaded) {
            createPlaceholderFrames();
        }
    }

    private void loadEnemySprites() {
        for (Enemy enemy : enemies) {
            enemy.loadSprite();
        }
    }

    private void startEnemyAnimationTimer() {
        if (enemyAnimationTimer != null) enemyAnimationTimer.stop();
        enemyAnimationTimer = new Timer(100, e -> {
            for (Enemy enemy : enemies) {
                if (enemy.getHp() > 0) enemy.updateAnimation();
            }
            repaint();
        });
        enemyAnimationTimer.start();
    }

    private void stopEnemyAnimationTimer() {
        if (enemyAnimationTimer != null) {
            enemyAnimationTimer.stop();
            enemyAnimationTimer = null;
        }
    }

    private void paintEnemySprite(Graphics2D g2, int index, Enemy enemy) {
        Rectangle rect = enemyImgRects.get(index);
        if (enemy.hasSprite()) {
            BufferedImage currentFrame = enemy.getCurrentFrame();
            if (currentFrame != null) {
                g2.drawImage(currentFrame, rect.x, rect.y, rect.width, rect.height, null);
            } else {
                drawEnemyTextFallback(g2, rect, enemy.getName());
            }
        } else {
            drawEnemyTextFallback(g2, rect, enemy.getName());
        }
    }

    private void drawEnemyTextFallback(Graphics2D g2, Rectangle rect, String name) {
        g2.setColor(Color.GRAY);
        g2.fillRect(rect.x, rect.y, rect.width, rect.height);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.BOLD, 20));
        String display = name.length() > 2 ? name.substring(0, 2) : name;
        g2.drawString(display, rect.x + rect.width/2 - 15, rect.y + rect.height/2 + 10);
    }

    private String getCharacterClassName() {
        if (playerEntity instanceof Swordsman) return "swordsman";
        if (playerEntity instanceof Ranger) return "archer";
        if (playerEntity instanceof Mage) return "mage";
        return "swordsman";
    }

    private void createPlaceholderFrames() {
        Color baseColor;
        String initial;

        if (playerEntity instanceof Swordsman) {
            baseColor = new Color(200, 80, 80);
            initial = "S";
        } else if (playerEntity instanceof Ranger) {
            baseColor = new Color(80, 180, 100);
            initial = "R";
        } else {
            baseColor = new Color(80, 120, 220);
            initial = "M";
        }

        // Create slightly different colors for each frame to simulate animation
        for (int i = 0; i < 5; i++) {
            float brightness = 0.7f + (i * 0.06f);
            Color frameColor = new Color(
                    Math.min(255, (int)(baseColor.getRed() * brightness)),
                    Math.min(255, (int)(baseColor.getGreen() * brightness)),
                    Math.min(255, (int)(baseColor.getBlue() * brightness))
            );
            playerIdleFrames[i] = createPlaceholderImage(frameColor, initial, i);
        }
    }

    private BufferedImage createPlaceholderImage(Color color, String initial, int frame) {
        int w = 150, h = 135;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        // Background
        g2.setColor(color);
        g2.fillRect(0, 0, w, h);

        // Add subtle movement effect
        int offsetX = (frame % 2 == 0) ? 0 : (frame - 2) * 2;
        int offsetY = (frame % 3 == 0) ? 2 : 0;

        // Large initial letter
        Font bigFont = new Font("Serif", Font.BOLD, 60);
        g2.setFont(bigFont);
        FontMetrics fm = g2.getFontMetrics();
        int lx = (w - fm.stringWidth(initial)) / 2 + offsetX;
        int ly = (h + fm.getAscent() - fm.getDescent()) / 2 + offsetY;

        g2.setColor(new Color(0, 0, 0, 80));
        g2.drawString(initial, lx + 2, ly + 2);

        g2.setColor(new Color(255, 245, 200));
        g2.drawString(initial, lx, ly);

        // Silhouette border
        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 120));
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(w/2 - 30, h/2 - 30, 60, 60);

        g2.dispose();
        return img;
    }

    private void startAnimationTimer() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        animationTimer = new Timer(120, e -> {
            currentPlayerFrame = (currentPlayerFrame + 1) % 5;
            repaint();
        });
        animationTimer.start();
    }

    public void stopAnimationTimer() {
        if (animationTimer != null) {
            animationTimer.stop();
            animationTimer = null;
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  Layout
    // ─────────────────────────────────────────────────────────────
    private void computeLayout() {
        int uiH    = 200;
        int uiY    = HEIGHT - uiH - 10;
        int battleH = uiY - 10;

        int imgW = 150, imgH = 135;
        int statW = 180, statH = 70;

        int enemyCount = enemies.size();

        // Calculate total width needed for all entities
        int enemySpacing = 50;
        int totalEnemyWidth = enemyCount * (imgW + enemySpacing) - enemySpacing;
        int totalWidth = imgW + 100 + totalEnemyWidth;

        // Calculate starting X position to center everything
        int startX = (WIDTH - totalWidth) / 2;

        // Y position - place entities above UI box
        int entityY = uiY - imgH - statH - 40;

        // Player on left side
        int pX = startX;
        int pY = entityY;
        playerImgRect = new Rectangle(pX, pY, imgW, imgH);
        playerStatRect = new Rectangle(pX - 8, pY + imgH + 8, statW + 15, statH + 8);

        // Position enemies horizontally to the right of player
        int enemyStartX = startX + imgW + 100;

        for (int i = 0; i < enemyCount; i++) {
            int eX = enemyStartX + (i * (imgW + enemySpacing));
            int eY = entityY;

            enemyImgRects.add(new Rectangle(eX, eY, imgW, imgH));
            enemyStatRects.add(new Rectangle(eX - 8, eY + imgH + 8, statW, statH));
        }

        // UI box
        uiBoxRect = new Rectangle(10, uiY, WIDTH - 20, uiH);
        uiBoxVCenter = uiBoxRect.y + (uiBoxRect.height / 2);
    }

    // ─────────────────────────────────────────────────────────────
    //  Enemy Labels
    // ─────────────────────────────────────────────────────────────
    private void buildEnemyLabels() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            JLabel enemyGifLabel = new JLabel("[ " + enemy.getName().toUpperCase() + " ]", SwingConstants.CENTER);
            enemyGifLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
            enemyGifLabel.setForeground(new Color(255, 150, 150));
            enemyGifLabel.setBorder(BorderFactory.createDashedBorder(
                    new Color(200, 80, 80), 3f, 6f, 4f, false));
            enemyGifLabel.setBounds(enemyImgRects.get(i));
            add(enemyGifLabel);
            enemyGifLabels.add(enemyGifLabel);
            enemyGifLabel.setVisible(false);
        }
    }

    public void setEnemyGif(int enemyIndex, ImageIcon icon) {
        if (enemyIndex < enemyGifLabels.size()) {
            enemyGifLabels.get(enemyIndex).setIcon(icon);
            enemyGifLabels.get(enemyIndex).setText(null);
            enemyGifLabels.get(enemyIndex).setBorder(null);
        }
    }

    private void playClickSFX() {
        if (gp != null) gp.getSFXPlayer().playSFX(new ClickSFX());
    }

    // ─────────────────────────────────────────────────────────────
    //  Button construction
    // ─────────────────────────────────────────────────────────────
    private void buildButtons() {
        // Main menu buttons
        int btnW = 140, btnH = 46, gap = 18;
        int totalMainBtnWidth = (btnW * 3) + (gap * 2);
        int startX = uiBoxRect.x + (uiBoxRect.width - totalMainBtnWidth) / 2;
        int rowY = uiBoxVCenter - (btnH / 2);

        btnFight = new BattleButton("Fight");
        btnFight.setBounds(startX, rowY, btnW, btnH);
        btnFight.addActionListener(e -> { playClickSFX(); showFightMenu(); });
        add(btnFight);

        btnBag = new BattleButton("Bag");
        btnBag.setBounds(startX + (btnW + gap), rowY, btnW, btnH);
        btnBag.addActionListener(e -> {
            playClickSFX();
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(BattlePanel.this);

            itemSelectionCallback = (item, target) -> {
                System.out.println("Item selected: " + item.getName()
                        + ", target: " + (target != null ? target.getName() : "null"));

                parentFrame.getContentPane().removeAll();
                parentFrame.add(BattlePanel.this);
                parentFrame.revalidate();
                parentFrame.repaint();

                battle.setPendingItem(item, target);
            };

            InventoryPanel invPanel = new InventoryPanel(
                    parentFrame,
                    playerEntity,
                    true,
                    itemSelectionCallback,
                    () -> {
                        parentFrame.getContentPane().removeAll();
                        parentFrame.add(BattlePanel.this);
                        parentFrame.revalidate();
                        parentFrame.repaint();
                    }
            );
            invPanel.setBattle(battle);

            parentFrame.getContentPane().removeAll();
            parentFrame.add(invPanel);
            parentFrame.revalidate();
            parentFrame.repaint();
        });
        add(btnBag);

        btnRun = new BattleButton("Run");
        btnRun.setBounds(startX + (btnW + gap) * 2, rowY, btnW, btnH);
        btnRun.addActionListener(e -> { playClickSFX(); battle.handleRunAway(); });
        add(btnRun);

        // Fight sub-menu moves
        ArrayList<Move> moves = playerEntity.getMoves();
        String[] moveNames = {"Move 1", "Move 2", "Move 3", "Move 4"};
        for (int i = 0; i < moves.size() && i < 4; i++) {
            String n = null;
            try { n = moves.get(i).getName(); } catch (Exception ignored) {}
            if (n != null && !n.isBlank()) moveNames[i] = n;
        }

        // Initialize the array
        moveBtns = new BattleButton[4];

        int mW = 195, mH = 42, mGapX = 16, mGapY = 10;
        int totalMoveGridWidth = (mW * 2) + mGapX;
        int mX = uiBoxRect.x + (uiBoxRect.width - totalMoveGridWidth) / 2;
        int mY = uiBoxRect.y + 35;

        for (int i = 0; i < 4; i++) {
            int col = i % 2, row = i / 2;

            // Create the button with move name
            moveBtns[i] = new BattleButton(moveNames[i]);

            // Set tooltip with move description if available
            if (i < moves.size() && moves.get(i) != null) {
                Move move = moves.get(i);
                String description = move.getDescription();
                if (description != null && !description.isEmpty()) {
                    // Format the description nicely with HTML
                    String formattedDesc = "<html><div style='width:250px; padding:5px;'>" +
                            "<b>" + move.getName() + "</b><br>" +
                            "<span style='font-size:11px;'>" + description + "</span>" +
                            "</div></html>";
                    moveBtns[i].setTooltipText(formattedDesc);
                } else {
                    moveBtns[i].setTooltipText("<html><i>No description available</i></html>");
                }
            } else {
                moveBtns[i].setTooltipText("<html><i>Move not available</i></html>");
            }

            moveBtns[i].setBounds(mX + col * (mW + mGapX), mY + row * (mH + mGapY), mW, mH);
            moveBtns[i].setVisible(false);

            final int idx = i;
            moveBtns[i].addActionListener(e -> { playClickSFX(); onMoveSelected(idx); });
            add(moveBtns[i]);
        }

        int backW = 140, backH = 46;
        int backX = uiBoxRect.x + 45;
        int backY = uiBoxRect.y + uiBoxRect.height - backH - 25;

        btnBack = new BattleButton("← Back");
        btnBack.setBounds(backX, backY, backW, backH);
        btnBack.addActionListener(e -> { playClickSFX(); showMainMenu(); });
        btnBack.setVisible(false);
        add(btnBack);

        // Target selection buttons
        buildTargetButtons();
    }

    private void buildTargetButtons() {
        int btnW = 195, btnH = 42;
        int gap = 20;
        int enemyCount = enemies.size();

        int totalButtonsWidth = (enemyCount * btnW) + ((enemyCount - 1) * gap);
        int startX = uiBoxRect.x + (uiBoxRect.width - totalButtonsWidth) / 2;
        btnW = 195;
        btnH = 42;
        int btnY = uiBoxVCenter - (btnH / 2);

        for (int i = 0; i < enemyCount; i++) {
            final int enemyIndex = i;
            int btnX = startX + (i * (btnW + gap));

            BattleButton targetBtn = new BattleButton(enemies.get(i).getName());
            targetBtn.setBounds(btnX, btnY, btnW, btnH);
            targetBtn.addActionListener(e -> { playClickSFX(); onTargetSelected(enemyIndex); });
            targetBtn.setVisible(false);
            add(targetBtn);
            targetButtons.add(targetBtn);
        }

        int backBtnW = 140, backBtnH = 46;
        int backBtnX = 20;
        int backBtnY = uiBoxRect.y + uiBoxRect.height - backBtnH - 20;

        btnBackTarget = new BattleButton("← Back");
        btnBackTarget.setBounds(backBtnX, backBtnY, backBtnW, backBtnH);
        btnBackTarget.addActionListener(e -> {
            playClickSFX();
            System.out.println("Back button pressed during target selection - resetting UI");

            if (battle.hasPendingItem()) {
                battle.cancelItemTargetSelection();
            } else {
                battle.cancelTargetSelection();
            }

            uiState = UIState.FIGHT;
            hideTargetButtons();
            for (BattleButton mb : moveBtns) mb.setVisible(true);
            btnBack.setVisible(true);
            btnFight.setVisible(false);
            btnBag.setVisible(false);
            btnRun.setVisible(false);
            battleMessage = "";
            setButtonsEnabled(true);
            repaint();
        });
        btnBackTarget.setVisible(false);
        add(btnBackTarget);
    }

    // ── UI state switching ─────────────────────────────────────────────
    public void showMainMenu() {
        System.out.println("showMainMenu() called");

        uiState = UIState.MAIN;

        // Show main menu buttons
        btnFight.setVisible(true);
        btnBag.setVisible(true);
        btnRun.setVisible(true);

        // Hide move buttons
        for (BattleButton mb : moveBtns) mb.setVisible(false);

        // Hide fight menu back button
        btnBack.setVisible(false);

        // Hide all target selection UI
        hideTargetButtons();

        repaint();
    }

    public void hideMainMenu(){
        uiState = UIState.MAIN;
        btnFight.setVisible(false);
        btnBag.setVisible(false);
        btnRun.setVisible(false);
        repaint();
    }

    public void showFightMenu() {
        System.out.println("showFightMenu() called - resetting to fight menu");

        uiState = UIState.FIGHT;

        // Hide main menu buttons
        btnFight.setVisible(false);
        btnBag.setVisible(false);
        btnRun.setVisible(false);

        // Show move buttons and Back button
        for (BattleButton mb : moveBtns) mb.setVisible(true);
        btnBack.setVisible(true);

        // Hide target selection UI completely
        hideTargetButtons();

        // Clear any pending battle message
        battleMessage = "";

        // Ensure all buttons are enabled
        setButtonsEnabled(true);

        repaint();
    }

    public void showTargetSelection(Move move) {
        System.out.println("showTargetSelection() called for move: " + move.getName());

        uiState = UIState.TARGET_SELECT;

        // Hide move buttons and fight menu back button
        for (BattleButton mb : moveBtns) mb.setVisible(false);
        btnBack.setVisible(false);

        // Update target button states based on enemy HP
        updateTargetButtonStates();

        // Show target selection buttons for each alive enemy
        for (BattleButton targetBtn : targetButtons) {
            targetBtn.setVisible(true);
        }

        // Ensure Back button is visible during target selection
        if (btnBackTarget != null) {
            btnBackTarget.setVisible(true);
            btnBackTarget.setEnabled(true);

            setComponentZOrder(btnBackTarget, 0);
            btnBackTarget.revalidate();
            btnBackTarget.repaint();
        }

        battleMessage = "Choose a target for " + move.getName() + "!";

        this.revalidate();
        this.repaint();
    }

    public void showItemTargetSelection(Item item) {
        System.out.println("showItemTargetSelection() called for item: " + item.getName());
        uiState = UIState.TARGET_SELECT;
        for (BattleButton mb : moveBtns) mb.setVisible(false);
        btnBack.setVisible(false);
        updateTargetButtonStates();
        for (BattleButton targetBtn : targetButtons) {
            targetBtn.setVisible(true);
        }
        if (btnBackTarget != null) {
            btnBackTarget.setVisible(true);
            btnBackTarget.setEnabled(true);
            setComponentZOrder(btnBackTarget, 0);
            btnBackTarget.revalidate();
            btnBackTarget.repaint();
        }
        battleMessage = "Choose a target for " + item.getName() + "!";
        this.revalidate();
        this.repaint();
    }

    public void hideTargetButtons() {
        for (BattleButton targetBtn : targetButtons) {
            targetBtn.setVisible(false);
        }
        if (btnBackTarget != null) {
            btnBackTarget.setVisible(false);
        }
    }

    public void hideBattleButtons() {
        for (BattleButton mb : moveBtns) mb.setVisible(false);
        btnBack.setVisible(false);
    }

    public void updateTargetButtonStates() {
        for (int i = 0; i < enemies.size() && i < targetButtons.size(); i++) {
            Enemy enemy = enemies.get(i);
            BattleButton targetBtn = targetButtons.get(i);

            if (enemy.getHp() < 1) {
                targetBtn.setEnabled(false);
                targetBtn.setText(enemy.getName() + " (DEFEATED)");
            } else {
                targetBtn.setEnabled(true);
                targetBtn.setText(enemy.getName());
            }
        }
        repaint();
    }

    // ── Action handlers ─────────────────────────────────────────────
    private void onMoveSelected(int index) {
        ArrayList<Move> moves = playerEntity.getMoves();
        if (index < moves.size()) {
            Move selectedMove = moves.get(index);
            battle.handlePlayerMove(selectedMove);
        }
    }

    private void onTargetSelected(int enemyIndex) {
        System.out.println("Target selected: " + enemyIndex);
        if (battle.hasPendingItem()) {
            battle.handleItemTargetSelected(enemyIndex);
        } else {
            battle.handleTargetSelected(enemyIndex);
        }
    }

    // ── Public methods for Battle class ─────────────────────────────
    public void setButtonsEnabled(boolean enabled) {
        btnFight.setEnabled(enabled);
        btnBag.setEnabled(enabled);
        btnRun.setEnabled(enabled);
        for (BattleButton btn : moveBtns) {
            btn.setEnabled(enabled);
        }
        btnBack.setEnabled(enabled);
        for (BattleButton targetBtn : targetButtons) {
            targetBtn.setEnabled(enabled);
        }
        if (btnBackTarget != null) {
            btnBackTarget.setEnabled(enabled);
        }

        updateTargetButtonStates();
    }

    public void setBattleMessage(String message) {
        this.battleMessage = message;
        repaint();
    }

    public void setExecutingMove(boolean executing) {
        this.isExecutingMove = executing;
    }

    public boolean isExecutingMove() {
        return isExecutingMove;
    }

    public Player getPlayerEntity() {
        return playerEntity;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    // ─────────────────────────────────────────────────────────────
    //  Painting
    // ─────────────────────────────────────────────────────────────
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Draw background
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, this);
        } else {
            g2.setColor(new Color(15, 10, 35));
            g2.fillRect(0, 0, WIDTH, HEIGHT);
            GradientPaint gradient = new GradientPaint(0, 0, new Color(15, 10, 35),
                    0, HEIGHT, new Color(35, 15, 55));
            g2.setPaint(gradient);
            g2.fillRect(0, 0, WIDTH, HEIGHT);
        }

        // Enemy stat boxes
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            paintEnemySprite(g2, i, enemy);
            if (enemy.getHp() > 0) {
                paintStatBox(g2, enemyStatRects.get(i),
                        enemy.getName() != null ? enemy.getName() : "Enemy " + (i + 1),
                        enemy.getLevel(),
                        (int) enemy.getHp(), (int) enemy.getMaxHp(),
                        null, false);
            } else {
                paintDefeatedStatBox(g2, enemyStatRects.get(i), enemy.getName());
            }
        }

        // Player stat box
        paintStatBox(g2, playerStatRect,
                playerEntity.getName() != null ? playerEntity.getName() : "Player",
                playerEntity.getLevel(),
                (int) playerEntity.getHp(), (int) playerEntity.getMaxHp(),
                "EXP: " + playerEntity.getExperience() + " / " + playerEntity.getExpNeeded(),
                true);

        // Draw player animated sprite (without frame/border)
        paintPlayerSprite(g2);

        paintUIBox(g2);
        g2.dispose();
    }

    private void paintPlayerSprite(Graphics2D g2) {
        if (playerImgRect == null) return;

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // Draw current animation frame directly without any frame/border
        BufferedImage currentFrame = playerIdleFrames[currentPlayerFrame];
        if (currentFrame != null) {
            g2.drawImage(currentFrame, playerImgRect.x, playerImgRect.y,
                    playerImgRect.width, playerImgRect.height, null);
        } else {
            // Fallback - draw placeholder without frame
            g2.setColor(Color.GRAY);
            g2.fillRect(playerImgRect.x, playerImgRect.y, playerImgRect.width, playerImgRect.height);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Serif", Font.BOLD, 40));
            g2.drawString("?", playerImgRect.x + playerImgRect.width/2 - 15,
                    playerImgRect.y + playerImgRect.height/2 + 15);
        }
    }

    private void paintDefeatedStatBox(Graphics2D g2, Rectangle r, String name) {
        g2.setColor(new Color(200, 200, 200, 180));
        g2.fill(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 12, 12));
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(2f));
        g2.draw(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 12, 12));

        g2.setFont(FONT_NAME);
        g2.setColor(Color.GREEN);
        int tx = r.x + 12;
        int ty = r.y + r.height / 2 + 5;
        g2.drawString("DEFEATED", tx, ty);
    }

    private void paintStatBox(Graphics2D g2, Rectangle r, String name, int level,
                              int hp, int maxHp, String expLine, boolean showExp) {
        g2.setColor(STAT_BG);
        g2.fill(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 10, 10));
        g2.setColor(STAT_BORDER);
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 10, 10));

        int tx = r.x + 8;
        int ty = r.y + 14;

        Font nameFont = new Font("Serif", Font.BOLD, 14);
        g2.setFont(nameFont);
        g2.setColor(Color.BLACK);
        g2.drawString(name, tx, ty);

        if (level >= 0) {
            String lvlTag = "Lv. " + level;
            g2.setFont(nameFont);
            FontMetrics fm = g2.getFontMetrics();
            int lvlWidth = fm.stringWidth(lvlTag);
            int lvlX = r.x + r.width - 8 - lvlWidth;
            g2.drawString(lvlTag, lvlX, ty);
        }

        ty += 16;
        Font statFont = new Font("Monospaced", Font.BOLD, 11);
        g2.setFont(statFont);
        g2.drawString("HP", tx, ty);

        int barX = tx + 28, barY = ty - 10;
        int barW = r.width - 42, barH = 10;

        g2.setColor(new Color(190, 190, 190));
        g2.fillRoundRect(barX, barY, barW, barH, 5, 5);

        double ratio = (maxHp > 0) ? Math.max(0, Math.min(1.0, (double) hp / maxHp)) : 0;
        Color hpColor = (ratio > 0.5) ? HP_GREEN : (ratio > 0.25) ? HP_YELLOW : HP_RED;
        g2.setColor(hpColor);
        g2.fillRoundRect(barX, barY, (int)(barW * ratio), barH, 5, 5);

        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(barX, barY, barW, barH, 5, 5);

        ty += 13;
        g2.setFont(statFont);
        g2.setColor(Color.BLACK);
        g2.drawString(hp + " / " + maxHp, barX, ty);

        // ========== PLAYER SECTION (showExp = true) ==========
        if (showExp) {
            int buffsOffset = 0;

            // --- BUFFS FIRST (Class buffs like Iron Stance, Harmony, Empower) ---
            if (playerEntity instanceof Swordsman) {
                Swordsman swordsman = (Swordsman) playerEntity;
                int stacks = swordsman.getIronStanceStacks();
                if (stacks > 0) {
                    buffsOffset += 12;
                    g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
                    g2.setColor(new Color(100, 150, 255));
                    g2.drawString("Iron Stance: " + stacks + "/3", tx, ty + buffsOffset);
                }
            }
            if (playerEntity instanceof Ranger) {
                Ranger ranger = (Ranger) playerEntity;
                int harmonyStacks = ranger.getHarmonyStacks();
                if (harmonyStacks > 0) {
                    buffsOffset += 12;
                    g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
                    g2.setColor(new Color(100, 200, 100));
                    g2.drawString("Harmony: " + harmonyStacks + "/3", tx, ty + buffsOffset);
                }
            }
            if (playerEntity instanceof Mage) {
                Mage mage = (Mage) playerEntity;
                int stacks = mage.getEmpowerStacks();
                if (stacks > 0) {
                    buffsOffset += 12;
                    g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
                    g2.setColor(new Color(255, 150, 100));
                    g2.drawString("Empower: " + stacks + "/3", tx, ty + buffsOffset);
                }
            }

            // --- STATUS EFFECTS BELOW BUFFS (Start after buffs) ---
            List<StatusEffect> playerEffects = playerEntity.getStatusEffects();
            if (!playerEffects.isEmpty()) {
                int statusOffset = buffsOffset;
                for (StatusEffect effect : playerEffects) {
                    statusOffset += 12;
                    g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
                    g2.setColor(new Color(255, 100, 100));
                    String effectText = effect.toString();
                    if (effectText.length() > 25) {
                        effectText = effectText.substring(0, 22) + "...";
                    }
                    g2.drawString(effectText, tx, ty + statusOffset);
                }
            }
        }

        // ========== ENEMY SECTION (showExp = false) ==========
        if (!showExp && enemies.size() > 0) {
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i).getName().equals(name)) {
                    Enemy enemy = enemies.get(i);
                    int buffsOffset = 0;

                    // --- BUFFS FIRST (Enemy-specific buffs like ZED's DEF increase) ---
                    if (enemy instanceof ZED) {
                        ZED zed = (ZED) enemy;
                        int stacks = zed.getDefBuffStacks();
                        if (stacks > 0) {
                            buffsOffset += 12;
                            g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
                            g2.setColor(new Color(255, 200, 100));
                            g2.drawString("DEF: +" + (stacks * 10), tx, ty + buffsOffset);
                        }
                    }

                    // --- STATUS EFFECTS BELOW BUFFS (Start after buffs) ---
                    List<StatusEffect> enemyEffects = enemy.getStatusEffects();
                    if (!enemyEffects.isEmpty()) {
                        int statusOffset = buffsOffset;
                        for (StatusEffect effect : enemyEffects) {
                            statusOffset += 12;
                            g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
                            g2.setColor(new Color(255, 100, 100));
                            String effectText = effect.toString();
                            if (effectText.length() > 25) {
                                effectText = effectText.substring(0, 22) + "...";
                            }
                            g2.drawString(effectText, tx, ty + statusOffset);
                        }
                    }
                    break;
                }
            }
        }
    }

    private void paintUIBox(Graphics2D g2) {
        Rectangle r = uiBoxRect;
        int sx = r.x;
        int sy = r.y;
        int sw = r.width;
        int sh = r.height;

        // ── 1. Outer worn shadow ──
        for (int i = 8; i >= 1; i--) {
            float a = 0.06f * (9 - i);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
            g2.setColor(new Color(20, 8, 0));
            g2.fill(new RoundRectangle2D.Float(sx - i, sy + i * 2, sw + i * 2, sh, 18, 18));
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // ── 2. Main parchment body ──
        g2.setPaint(new LinearGradientPaint(sx, sy, sx, sy + sh,
                new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                new Color[]{
                        new Color(148, 108, 44),
                        new Color(192, 152, 78),
                        new Color(220, 186, 118),
                        new Color(238, 212, 152),
                        new Color(220, 186, 118),
                        new Color(192, 152, 78),
                        new Color(148, 108, 44)
                }));
        Shape body = new RoundRectangle2D.Float(sx, sy, sw, sh, 18, 18);
        g2.fill(body);

        // ── 3. Horizontal paper fibre / grain ──
        g2.setStroke(new BasicStroke(0.5f));
        for (int ly = sy + 16; ly < sy + sh - 14; ly += 6) {
            int a = 14 + (int)(6 * Math.sin((ly - sy) * 0.4));
            g2.setColor(new Color(100, 60, 12, a));
            g2.drawLine(sx + 16, ly, sx + sw - 16, ly);
        }

        // ── 4. Subtle vertical vein lines ──
        g2.setStroke(new BasicStroke(0.4f));
        java.util.Random vr = new java.util.Random(77);
        for (int i = 0; i < 6; i++) {
            int vx = sx + 60 + vr.nextInt(sw - 120);
            int vlen = 20 + vr.nextInt(60);
            int vy = sy + 20 + vr.nextInt(sh - 40 - vlen);
            g2.setColor(new Color(88, 50, 10, 10 + vr.nextInt(12)));
            g2.drawLine(vx, vy, vx + vr.nextInt(6) - 3, vy + vlen);
        }

        // ── 5. Age spots ──
        java.util.Random sr = new java.util.Random(42);
        for (int i = 0; i < 20; i++) {
            int ax = sx + 40 + sr.nextInt(sw - 80);
            int ay = sy + 20 + sr.nextInt(sh - 40);
            int ar = 1 + sr.nextInt(5);
            g2.setColor(new Color(82, 44, 8, 10 + sr.nextInt(28)));
            g2.fillOval(ax, ay, ar, ar);
        }

        // ── 6. Warm inner glow ──
        g2.setPaint(new RadialGradientPaint(
                new Point2D.Float(sx + sw / 2f, sy + sh / 2f),
                sw * 0.46f,
                new float[]{0f, 1f},
                new Color[]{new Color(255, 230, 140, 38), new Color(255, 200, 80, 0)}));
        g2.fill(body);

        // ── 7. Rolled-edge curl: top ──
        g2.setPaint(new LinearGradientPaint(sx, sy, sx, sy + 28,
                new float[]{0f, 0.4f, 1f},
                new Color[]{
                        new Color(110, 72, 18, 175),
                        new Color(148, 108, 44, 90),
                        new Color(148, 108, 44, 0)
                }));
        g2.fill(new RoundRectangle2D.Float(sx, sy, sw, 28, 18, 18));

        // ── 8. Rolled-edge curl: bottom ──
        g2.setPaint(new LinearGradientPaint(sx, sy + sh - 28, sx, sy + sh,
                new float[]{0f, 0.6f, 1f},
                new Color[]{
                        new Color(148, 108, 44, 0),
                        new Color(148, 108, 44, 90),
                        new Color(110, 72, 18, 175)
                }));
        g2.fill(new RoundRectangle2D.Float(sx, sy + sh - 28, sw, 28, 18, 18));

        // ── 9. Inner shadow rings ──
        g2.setStroke(new BasicStroke(0.9f));
        for (int i = 1; i <= 7; i++) {
            g2.setColor(new Color(55, 22, 2, 48 - i * 6));
            g2.draw(new RoundRectangle2D.Float(sx + i, sy + i, sw - i * 2, sh - i * 2, 18 - i, 18 - i));
        }

        // ── 10. Decorative border ──
        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(new Color(115, 68, 14, 105));
        g2.draw(new RoundRectangle2D.Float(sx + 11, sy + 11, sw - 22, sh - 22, 10, 10));
        g2.setColor(new Color(115, 68, 14, 55));
        g2.draw(new RoundRectangle2D.Float(sx + 14, sy + 14, sw - 28, sh - 28, 8, 8));

        // ── 11. Outer hard border ──
        g2.setStroke(new BasicStroke(2.5f));
        g2.setColor(SCROLL_BORDER);
        g2.draw(body);

        // ── 12. Wax seal ornaments ──
        paintWaxSeal(g2, sx + 18, sy + 18);
        paintWaxSeal(g2, sx + sw - 36, sy + 18);
        paintWaxSeal(g2, sx + 18, sy + sh - 36);
        paintWaxSeal(g2, sx + sw - 36, sy + sh - 36);

        // ── 13. Message text ──
        g2.setFont(FONT_TITLE);
        if (!battleMessage.isEmpty()) {
            g2.setColor(new Color(60, 30, 5, 220));
            String displayMessage = battleMessage;
            FontMetrics fm = g2.getFontMetrics();
            int maxWidth = sw - 80;
            if (fm.stringWidth(displayMessage) > maxWidth) {
                while (fm.stringWidth(displayMessage + "...") > maxWidth && displayMessage.length() > 0) {
                    displayMessage = displayMessage.substring(0, displayMessage.length() - 1);
                }
                displayMessage = displayMessage + "...";
            }
            g2.drawString(displayMessage, sx + 60, sy + 28);
        } else {
            g2.setColor(new Color(60, 30, 5, 220));
            String prompt = (uiState == UIState.MAIN && !isExecutingMove)
                    ? "What will " + (playerEntity.getName() != null ? playerEntity.getName() : "you") + " do?"
                    : (uiState == UIState.FIGHT ? "Choose a move:" :
                    (uiState == UIState.TARGET_SELECT ? "Select target:" : ""));
            g2.drawString(prompt, sx + 60, sy + 28);
        }
    }

    private void paintWaxSeal(Graphics2D g2, int rx, int ry) {
        int cx = rx + 9, cy = ry + 9;
        g2.setStroke(new BasicStroke(1.3f));
        g2.setColor(new Color(80, 40, 8, 115));
        g2.drawOval(rx, ry, 18, 18);
        g2.setColor(new Color(80, 40, 8, 65));
        g2.drawOval(rx + 3, ry + 3, 12, 12);
        g2.setStroke(new BasicStroke(1.1f));
        g2.setColor(new Color(80, 40, 8, 105));
        g2.drawLine(cx, ry + 2, cx, ry + 16);
        g2.drawLine(rx + 2, cy, rx + 16, cy);
        g2.setColor(new Color(80, 40, 8, 60));
        g2.drawLine(rx + 4, ry + 4, rx + 14, ry + 14);
        g2.drawLine(rx + 14, ry + 4, rx + 4, ry + 14);
        g2.setColor(new Color(90, 50, 10, 110));
        g2.fillOval(cx - 2, cy - 2, 5, 5);
    }

    /**
     * Shows or hides all UI buttons (menus, move buttons, target buttons)
     * Used during message display to prevent button clicks while messages are showing
     */
    public void setUIVisible(boolean visible) {
        if (visible) {
            // When showing UI, only show the appropriate buttons based on current state
            if (uiState == UIState.MAIN) {
                showMainMenu();
            } else if (uiState == UIState.FIGHT) {
                showFightMenu();
            } else if (uiState == UIState.TARGET_SELECT) {
                // For target selection, show target buttons and back button
                for (BattleButton targetBtn : targetButtons) {
                    targetBtn.setVisible(true);
                }
                if (btnBackTarget != null) {
                    btnBackTarget.setVisible(true);
                }
            }
        } else {
            // Hide all buttons
            btnFight.setVisible(false);
            btnBag.setVisible(false);
            btnRun.setVisible(false);

            for (BattleButton mb : moveBtns) {
                mb.setVisible(false);
            }
            btnBack.setVisible(false);

            for (BattleButton targetBtn : targetButtons) {
                targetBtn.setVisible(false);
            }
            if (btnBackTarget != null) {
                btnBackTarget.setVisible(false);
            }
        }

        if (visible) {
            revalidate();
            repaint();
        }
    }

    public void refreshInventoryDisplay() {
        System.out.println("Inventory updated - item quantity changed");
    }

    // ─────────────────────────────────────────────────────────────
    //  Inner class: BattleButton with Gold Octagon styling + Tooltip
    // ─────────────────────────────────────────────────────────────
    private static class BattleButton extends JButton {
        private boolean hovered = false;
        private boolean pressed = false;
        private String tooltipText = "";

        BattleButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFont(FONT_BTN);
            setForeground(BTN_TEXT_DARK);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Enable tooltips
            setToolTipText("");

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (isEnabled()) {
                        hovered = true;
                        repaint();
                        // Show custom tooltip with description
                        if (!tooltipText.isEmpty()) {
                            setToolTipText(tooltipText);
                        }
                    }
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    hovered = false;
                    repaint();
                }
                @Override
                public void mousePressed(MouseEvent e) {
                    if (isEnabled()) {
                        pressed = true;
                        repaint();
                    }
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    pressed = false;
                    repaint();
                }
            });
        }

        public void setTooltipText(String text) {
            this.tooltipText = text;
            super.setToolTipText(text);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int c = Math.min(w, h) / 6;
            if (c < 6) c = 6;

            Polygon oct = makeOctagon(0, 0, w, h, c);

            if (!isEnabled()) {
                g2.setColor(new Color(160, 140, 100));
                g2.fill(oct);
                g2.setColor(new Color(100, 80, 50));
                g2.setStroke(new BasicStroke(1.8f));
                g2.draw(oct);

                FontMetrics fm = g2.getFontMetrics(getFont());
                int tx = (w - fm.stringWidth(getText())) / 2;
                int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
                g2.setFont(getFont());
                g2.setColor(Color.GRAY);
                g2.drawString(getText(), tx, ty);
            } else {
                Color fill = pressed ? BTN_GOLD_PRESS : hovered ? BTN_GOLD_HOVER : BTN_GOLD_NORMAL;
                g2.setColor(fill);
                g2.fill(oct);

                if (hovered && !pressed) {
                    g2.setColor(new Color(255, 255, 255, 40));
                    Polygon topOct = makeOctagon(0, 0, w, h / 2, c);
                    g2.fill(topOct);
                }

                g2.setColor(BTN_BORDER_CLR);
                g2.setStroke(new BasicStroke(2.0f));
                g2.draw(oct);

                FontMetrics fm = g2.getFontMetrics(getFont());
                int tx = (w - fm.stringWidth(getText())) / 2;
                int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
                g2.setFont(getFont());
                g2.setColor(new Color(0, 0, 0, 100));
                g2.drawString(getText(), tx + 1, ty + 1);
                g2.setColor(getForeground());
                g2.drawString(getText(), tx, ty);
            }

            g2.dispose();
        }

        private Polygon makeOctagon(int x, int y, int w, int h, int c) {
            return new Polygon(
                    new int[]{x + c, x + w - c, x + w, x + w, x + w - c, x + c, x, x},
                    new int[]{y, y, y + c, y + h - c, y + h, y + h, y + h - c, y + c},
                    8
            );
        }
    }
}