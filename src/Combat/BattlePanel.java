package Combat;

import Entities.Characters.*;
import Entities.Enemies.Enemy;
import Entities.Enemies.*;
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

    // ── GIF placeholder labels (enemies only) ─────────────────────
    private final List<JLabel> enemyGifLabels = new ArrayList<>();

    // ── Buttons ───────────────────────────────────────────────────
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

    // ─────────────────────────────────────────────────────────────
    //  Constructors
    // ─────────────────────────────────────────────────────────────
    public BattlePanel(Player player, Enemy enemy) {
        this(player, List.of(enemy));
    }

    public BattlePanel(Player player, Enemy enemy1, Enemy enemy2) {
        this(player, List.of(enemy1, enemy2));
    }

    public BattlePanel(Player player, Enemy enemy1, Enemy enemy2, Enemy enemy3) {
        this(player, List.of(enemy1, enemy2, enemy3));
    }

    private BattlePanel(Player player, List<Enemy> enemies) {
        this.playerEntity = player;
        this.enemies = new ArrayList<>(enemies);

        if (this.enemies.isEmpty()) {
            throw new IllegalArgumentException("At least one enemy is required");
        }

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        setOpaque(true);

        computeLayout();
        loadPlayerAnimations();
        buildEnemyLabels();
        buildButtons();
        loadBackgroundImage();
        startAnimationTimer();

        // Initialize battle system
        this.battle = new Battle(this, playerEntity, this.enemies);
        this.battle.setOnBattleEnd(() -> {
            stopAnimationTimer();
            if (onBattleEnd != null) {
                onBattleEnd.run();
            }
        });

        // Start the battle
        battle.startBattle();
    }

    /** Load the background image */
    private void loadBackgroundImage() {
        try {
            java.net.URL imgUrl = getClass().getResource("/map2assets/combat_bg_map2v2.gif");
            if (imgUrl != null) {
                backgroundImage = new ImageIcon(imgUrl).getImage();
            } else {
                backgroundImage = new ImageIcon("/map2assets/combat_bg_map2v2.gif").getImage();
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

    private void stopAnimationTimer() {
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
        }
    }

    public void setEnemyGif(int enemyIndex, ImageIcon icon) {
        if (enemyIndex < enemyGifLabels.size()) {
            enemyGifLabels.get(enemyIndex).setIcon(icon);
            enemyGifLabels.get(enemyIndex).setText(null);
            enemyGifLabels.get(enemyIndex).setBorder(null);
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  Button construction
    // ─────────────────────────────────────────────────────────────
    private void buildButtons() {
        // Main menu buttons
        int btnW = 140, btnH = 46, gap = 18;
        int rowY = uiBoxRect.y + uiBoxRect.height - btnH - 20;
        int startX = uiBoxRect.x + 20;

        btnFight = new BattleButton("Fight");
        btnFight.setBounds(startX, rowY, btnW, btnH);
        btnFight.addActionListener(e -> showFightMenu());
        add(btnFight);

        btnBag = new BattleButton("Bag");
        btnBag.setBounds(startX + (btnW + gap), rowY, btnW, btnH);
        btnBag.addActionListener(e -> { /* TODO: open bag */ });
        add(btnBag);

        btnRun = new BattleButton("Run");
        btnRun.setBounds(startX + (btnW + gap) * 2, rowY, btnW, btnH);
        btnRun.addActionListener(e -> battle.handleRunAway());
        add(btnRun);

        // Fight sub-menu moves
        ArrayList<Move> moves = playerEntity.getMoves();
        String[] moveNames = {"Move 1", "Move 2", "Move 3", "Move 4"};
        for (int i = 0; i < moves.size() && i < 4; i++) {
            String n = null;
            try { n = moves.get(i).getName(); } catch (Exception ignored) {}
            if (n != null && !n.isBlank()) moveNames[i] = n;
        }

        moveBtns = new BattleButton[4];
        int mW = 195, mH = 42, mGapX = 16, mGapY = 10;
        int mX = uiBoxRect.x + 20;
        int mY = uiBoxRect.y + 38;

        for (int i = 0; i < 4; i++) {
            int col = i % 2, row = i / 2;
            moveBtns[i] = new BattleButton(moveNames[i]);
            moveBtns[i].setBounds(mX + col * (mW + mGapX), mY + row * (mH + mGapY), mW, mH);
            final int idx = i;
            moveBtns[i].addActionListener(e -> onMoveSelected(idx));
            add(moveBtns[i]);
        }

        int backW = 140, backH = 46;
        int backY = mY + 2 * (mH + mGapY) + 6;
        btnBack = new BattleButton("← Back");
        btnBack.setBounds(mX, backY, backW, backH);
        btnBack.addActionListener(e -> showMainMenu());
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
        int btnY = uiBoxRect.y + 70;

        for (int i = 0; i < enemyCount; i++) {
            final int enemyIndex = i;
            int btnX = startX + (i * (btnW + gap));

            BattleButton targetBtn = new BattleButton(enemies.get(i).getName());
            targetBtn.setBounds(btnX, btnY, btnW, btnH);
            targetBtn.addActionListener(e -> onTargetSelected(enemyIndex));
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
            System.out.println("Back button pressed during target selection - resetting UI");

            // Clear pending move and target selection state in Battle
            battle.cancelTargetSelection();

            // Reset UI state to FIGHT menu
            uiState = UIState.FIGHT;

            // Hide target buttons
            hideTargetButtons();

            // Show move buttons and Back button
            for (BattleButton mb : moveBtns) mb.setVisible(true);
            btnBack.setVisible(true);

            // Hide main menu buttons
            btnFight.setVisible(false);
            btnBag.setVisible(false);
            btnRun.setVisible(false);

            // Clear any pending battle message
            battleMessage = "";

            // Re-enable all buttons
            setButtonsEnabled(true);

            // Force repaint
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

            if (enemy.getHp() <= 0) {
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
        battle.handleTargetSelected(enemyIndex);
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

        if (showExp && expLine != null) {
            ty += 12;
            g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
            g2.setColor(new Color(50, 50, 160));
            g2.drawString(expLine, tx, ty);
        }

        if (showExp) {
            int tyOffset = 0;
            if (playerEntity instanceof Swordsman) {
                Swordsman swordsman = (Swordsman) playerEntity;
                int stacks = swordsman.getIronStanceStacks();
                if (stacks > 0) {
                    tyOffset += 12;
                    g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
                    g2.setColor(new Color(100, 150, 255));
                    g2.drawString("Iron Stance: " + stacks + "/3", tx, ty + tyOffset);
                }
            }
            if (playerEntity instanceof Ranger) {
                Ranger ranger = (Ranger) playerEntity;
                int stacks = ranger.getWindstepStacks();
                if (stacks > 0) {
                    tyOffset += 12;
                    g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
                    g2.setColor(new Color(100, 200, 100));
                    g2.drawString("Windstep: " + stacks + "/3", tx, ty + tyOffset);
                }
            }
            if (playerEntity instanceof Mage) {
                Mage mage = (Mage) playerEntity;
                int stacks = mage.getEmpowerStacks();
                if (stacks > 0) {
                    tyOffset += 12;
                    g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
                    g2.setColor(new Color(255, 150, 100));
                    g2.drawString("Empower: " + stacks + "/3", tx, ty + tyOffset);
                }
            }
        }

        if (!showExp && enemies.size() > 0) {
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i).getName().equals(name)) {
                    Enemy enemy = enemies.get(i);
                    int tyOffset = 0;

                    if (enemy instanceof ZED) {
                        ZED zed = (ZED) enemy;
                        int stacks = zed.getDefBuffStacks();
                        if (stacks > 0) {
                            tyOffset += 12;
                            g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
                            g2.setColor(new Color(255, 200, 100));
                            g2.drawString("DEF: +" + (stacks * 5), tx, ty + tyOffset);
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

    // ─────────────────────────────────────────────────────────────
    //  Inner class: BattleButton with Gold Octagon styling
    // ─────────────────────────────────────────────────────────────
    private static class BattleButton extends JButton {
        private boolean hovered = false;
        private boolean pressed = false;

        BattleButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFont(FONT_BTN);
            setForeground(BTN_TEXT_DARK);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered (MouseEvent e) {
                    if (isEnabled()) {
                        hovered = true;
                        repaint();
                    }
                }
                @Override public void mouseExited  (MouseEvent e) {
                    hovered = false;
                    repaint();
                }
                @Override public void mousePressed (MouseEvent e) {
                    if (isEnabled()) {
                        pressed = true;
                        repaint();
                    }
                }
                @Override public void mouseReleased(MouseEvent e) {
                    pressed = false;
                    repaint();
                }
            });
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