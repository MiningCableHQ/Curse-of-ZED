package Combat;

import Entities.Characters.Player;
import Entities.Enemies.Enemy;
import Moves.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private static final Color UI_BG        = new Color(14, 27,  45, 230);
    private static final Color UI_BORDER    = new Color(80, 150, 200);
    private static final Color STAT_BG      = Color.WHITE;
    private static final Color STAT_BORDER  = Color.BLACK;
    private static final Color HP_GREEN     = new Color(80,  200, 80);
    private static final Color HP_YELLOW    = new Color(230, 200, 40);
    private static final Color HP_RED       = new Color(220, 60,  60);
    private static final Color BTN_NORMAL   = new Color(40, 111, 120);
    private static final Color BTN_HOVER    = new Color(65, 167, 180);
    private static final Color BTN_PRESS    = new Color(25, 65,  80);
    private static final Color BTN_BORDER   = new Color(110, 190, 255);
    private static final Color TEXT_LIGHT   = new Color(240, 230, 255);
    private static final Color TEXT_GOLD    = new Color(255, 220, 80);

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
    private Enemy currentTargetEnemy;
    private Move pendingMove;

    // ── Battle State ──────────────────────────────────────────────
    private String battleMessage = "";
    private boolean isExecutingMove = false;
    private int currentEnemyIndex = 0;

    // ── Layout rects ──────────────────────────────────────────────
    private Rectangle playerImgRect;
    private Rectangle playerStatRect;
    private final List<Rectangle> enemyImgRects = new ArrayList<>();
    private final List<Rectangle> enemyStatRects = new ArrayList<>();
    private Rectangle uiBoxRect;

    // ── GIF placeholder labels ────────────────────────────────────
    private JLabel playerGifLabel;
    private final List<JLabel> enemyGifLabels = new ArrayList<>();

    // ── Buttons ───────────────────────────────────────────────────
    private BattleButton btnFight;
    private BattleButton btnBag;
    private BattleButton btnRun;
    private BattleButton[] moveBtns;
    private BattleButton btnBack;
    private final List<BattleButton> targetButtons = new ArrayList<>();

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
        setLayout(null);   // absolute positioning
        setOpaque(true);

        computeLayout();
        buildGifLabels();
        buildButtons();
        loadBackgroundImage();

        showMainMenu();
    }

    /** Load the background image */
    private void loadBackgroundImage() {
        try {
            // Try to load from resources folder
            java.net.URL imgUrl = getClass().getResource("/logos/curse_of_zed_logo.jpg");
            if (imgUrl != null) {
                backgroundImage = new ImageIcon(imgUrl).getImage();
            } else {
                // Fallback: try to load from file path
                backgroundImage = new ImageIcon("/logos/curse_of_zed_logo.jpg").getImage();
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

    /** Register a callback invoked when the battle ends (e.g. player runs). */
    public void setOnBattleEnd(Runnable callback) {
        this.onBattleEnd = callback;
    }

    // ─────────────────────────────────────────────────────────────
    //  Layout
    // ─────────────────────────────────────────────────────────────
    private void computeLayout() {
        int uiH    = 200;
        int uiY    = HEIGHT - uiH - 10;
        int battleH = uiY - 10;

        // Smaller sizes to prevent overlapping
        int imgW = 150, imgH = 135;
        int statW = 180, statH = 70;
        int margin = 40;

        // Calculate center positions
        int centerX = WIDTH / 2;
        int centerY = battleH / 2;

        int enemyCount = enemies.size();

        // Player on left side (centered vertically)
        int playerOffset = 200;
        int pX = centerX - playerOffset - imgW;
        int pY = centerY - (imgH + statH + 15) / 2;
        playerImgRect = new Rectangle(pX, pY, imgW, imgH);
        playerStatRect = new Rectangle(pX - 8, pY + imgH + 8, statW + 15, statH + 8);

        // Enemy start X position (right side)
        int enemyStartX = centerX + playerOffset - 20;

        if (enemyCount == 1) {
            // Single enemy - center it vertically
            int eY = centerY - (imgH + statH + 15) / 2;
            enemyImgRects.add(new Rectangle(enemyStartX, eY, imgW, imgH));
            enemyStatRects.add(new Rectangle(enemyStartX - 8, eY + imgH + 8, statW, statH));
        }
        else if (enemyCount == 2) {
            // Two enemies - positioned at top and bottom with more spacing
            int topY = 30;
            int bottomY = battleH - imgH - statH - 50;

            // Add extra spacing to prevent overlap
            int verticalGap = (bottomY - topY - imgH) / 2;
            topY = Math.max(20, topY);
            bottomY = Math.min(battleH - imgH - statH - 30, bottomY);

            // Top enemy
            enemyImgRects.add(new Rectangle(enemyStartX, topY, imgW, imgH));
            enemyStatRects.add(new Rectangle(enemyStartX - 8, topY + imgH + 8, statW, statH));

            // Bottom enemy
            enemyImgRects.add(new Rectangle(enemyStartX, bottomY, imgW, imgH));
            enemyStatRects.add(new Rectangle(enemyStartX - 8, bottomY + imgH + 8, statW, statH));
        }
        else {
            // Three enemies - positioned with clear separation
            int topY = 15;
            int bottomY = battleH - imgH - statH - 35;
            int middleY = centerY - (imgH + statH + 15) / 2;

            // Ensure no overlap by checking spacing
            int minSpacing = 20;
            if (middleY - topY - imgH < minSpacing) {
                middleY = topY + imgH + minSpacing;
            }
            if (bottomY - middleY - imgH < minSpacing) {
                bottomY = middleY + imgH + minSpacing;
            }

            // Top enemy - slightly to the right
            int topX = enemyStartX + 80;
            enemyImgRects.add(new Rectangle(topX, topY, imgW - 10, imgH - 10));
            enemyStatRects.add(new Rectangle(topX - 8, topY + imgH - 2, statW - 20, statH - 10));

            // Bottom enemy - slightly to the right
            int bottomX = enemyStartX + 80;
            enemyImgRects.add(new Rectangle(bottomX, bottomY, imgW - 10, imgH - 10));
            enemyStatRects.add(new Rectangle(bottomX - 8, bottomY + imgH - 2, statW - 20, statH - 10));

            // Middle enemy - in front (more to the left)
            int middleX = enemyStartX - 150;
            enemyImgRects.add(new Rectangle(middleX, middleY, imgW, imgH));
            enemyStatRects.add(new Rectangle(middleX - 8, middleY + imgH + 8, statW, statH));
        }

        // UI box
        uiBoxRect = new Rectangle(10, uiY, WIDTH - 20, uiH);
    }

    // ─────────────────────────────────────────────────────────────
    //  GIF labels
    // ─────────────────────────────────────────────────────────────
    private void buildGifLabels() {
        // Enemy labels with proper placeholder text
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

        // Player label
        playerGifLabel = new JLabel("[ " + playerEntity.getName().toUpperCase() + " ]", SwingConstants.CENTER);
        playerGifLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        playerGifLabel.setForeground(new Color(150, 220, 255));
        playerGifLabel.setBorder(BorderFactory.createDashedBorder(
                new Color(80, 160, 220), 3f, 6f, 4f, false));
        playerGifLabel.setBounds(playerImgRect);
        add(playerGifLabel);
    }

    /** Swap in the real player GIF once sprite assets are available. */
    public void setPlayerGif(ImageIcon icon) {
        playerGifLabel.setIcon(icon);
        playerGifLabel.setText(null);
        playerGifLabel.setBorder(null); // Remove placeholder border when actual GIF is set
    }

    /** Swap in the real enemy GIF once sprite assets are available. */
    public void setEnemyGif(int enemyIndex, ImageIcon icon) {
        if (enemyIndex < enemyGifLabels.size()) {
            enemyGifLabels.get(enemyIndex).setIcon(icon);
            enemyGifLabels.get(enemyIndex).setText(null);
            enemyGifLabels.get(enemyIndex).setBorder(null); // Remove placeholder border when actual GIF is set
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  Button construction
    // ─────────────────────────────────────────────────────────────
    private void buildButtons() {
        // ── Main menu buttons (Fight / Bag / Run) ─────────────
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
        btnRun.addActionListener(e -> runAway());
        add(btnRun);

        // ── Fight sub-menu (Move 1-4 + Back) ──────────────────
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

        int backY = mY + 2 * (mH + mGapY) + 6;
        btnBack = new BattleButton("← Back");
        btnBack.setBounds(mX, backY, mW, mH);
        btnBack.addActionListener(e -> showMainMenu());
        add(btnBack);

        // ── Target selection buttons ───────────────────────────
        buildTargetButtons();
    }

    private void buildTargetButtons() {
        int btnW = 180, btnH = 40, gap = 15;
        int startY = uiBoxRect.y + 60;
        int centerX = WIDTH / 2;
        int totalWidth = (enemies.size() * btnW) + ((enemies.size() - 1) * gap);
        int startX = centerX - (totalWidth / 2);

        for (int i = 0; i < enemies.size(); i++) {
            final int enemyIndex = i;
            BattleButton targetBtn = new BattleButton(enemies.get(i).getName());
            targetBtn.setBounds(startX + (i * (btnW + gap)), startY, btnW, btnH);
            targetBtn.addActionListener(e -> onTargetSelected(enemyIndex));
            targetBtn.setVisible(false);
            add(targetBtn);
            targetButtons.add(targetBtn);
        }
    }

    // ── UI state switching ─────────────────────────────────────────────
    private void showMainMenu() {
        uiState = UIState.MAIN;
        btnFight.setVisible(true);
        btnBag  .setVisible(true);
        btnRun  .setVisible(true);
        for (BattleButton mb : moveBtns) mb.setVisible(false);
        btnBack.setVisible(false);
        hideTargetButtons();
        repaint();
    }

    private void showFightMenu() {
        uiState = UIState.FIGHT;
        btnFight.setVisible(false);
        btnBag  .setVisible(false);
        btnRun  .setVisible(false);
        for (BattleButton mb : moveBtns) mb.setVisible(true);
        btnBack.setVisible(true);
        hideTargetButtons();
        repaint();
    }

    private void showTargetSelection(Move move) {
        uiState = UIState.TARGET_SELECT;
        pendingMove = move;

        // Hide move buttons and show target buttons
        for (BattleButton mb : moveBtns) mb.setVisible(false);
        btnBack.setVisible(false);

        // Show target selection buttons
        for (BattleButton targetBtn : targetButtons) {
            targetBtn.setVisible(true);
        }

        battleMessage = "Choose a target for " + pendingMove.getName() + "!";
        repaint();
    }

    private void hideTargetButtons() {
        for (BattleButton targetBtn : targetButtons) {
            targetBtn.setVisible(false);
        }
    }

    // ── Action handlers ─────────────────────────────────────────────
    private void onMoveSelected(int index) {
        if (isExecutingMove) return;

        ArrayList<Move> moves = playerEntity.getMoves();
        if (index < moves.size()) {
            Move selectedMove = moves.get(index);

            // Check if move requires target selection
            if (enemies.size() > 1 && selectedMove.getTargetType() == Move.TargetType.ENEMY) {
                showTargetSelection(selectedMove);
            } else {
                // Single enemy or move targets all enemies
                currentTargetEnemy = enemies.get(0);
                executePlayerMove(selectedMove);
            }
        }
    }

    private void onTargetSelected(int enemyIndex) {
        if (pendingMove != null && enemyIndex < enemies.size()) {
            currentTargetEnemy = enemies.get(enemyIndex);
            executePlayerMove(pendingMove);
            pendingMove = null;
        }
    }

    private void executePlayerMove(Move move) {
        setButtonsEnabled(false);
        isExecutingMove = true;

        // Determine target
        Enemy target;
        if (move.getTargetType() == Move.TargetType.ALL_ENEMIES) {
            target = null; // Will handle all enemies
        } else {
            target = currentTargetEnemy != null ? currentTargetEnemy : enemies.get(0);
        }

        // Execute move and calculate damage
        StringBuilder damageMessage = new StringBuilder();

        if (move.getTargetType() == Move.TargetType.ALL_ENEMIES) {
            // Handle multi-target move
            for (Enemy enemy : enemies) {
                if (enemy.getHp() > 0) {
                    double beforeHp = enemy.getHp();
                    Move.currentTarget = enemy;
                    move.execute(playerEntity);
                    double afterHp = enemy.getHp();
                    double damageDealt = beforeHp - afterHp;

                    if (damageDealt > 0) {
                        damageMessage.append(enemy.getName()).append(": ").append(String.format("%.1f", damageDealt)).append(" dmg; ");
                    }
                }
            }
            Move.currentTarget = null;
            battleMessage = playerEntity.getName() + " used " + move.getName() + "! " + damageMessage.toString();
        } else {
            // Single target move
            double beforeHp = target.getHp();
            Move.currentTarget = target;
            move.execute(playerEntity);
            Move.currentTarget = null;
            double afterHp = target.getHp();
            double damageDealt = beforeHp - afterHp;

            if (damageDealt > 0) {
                battleMessage = playerEntity.getName() + " used " + move.getName() + " on " +
                        target.getName() + " and dealt " + String.format("%.1f", damageDealt) + " damage!";
            } else {
                battleMessage = playerEntity.getName() + " used " + move.getName() + " on " +
                        target.getName() + " but it had no effect!";
            }
        }

        repaint();

        // Check if all enemies are defeated
        boolean allDefeated = true;
        for (Enemy enemy : enemies) {
            if (enemy.getHp() > 0) {
                allDefeated = false;
                break;
            }
        }

        if (allDefeated) {
            battleMessage = "All enemies have been defeated! You win!";
            repaint();

            Timer victoryTimer = new Timer(2000, e -> {
                if (onBattleEnd != null) {
                    onBattleEnd.run();
                }
                Window w = SwingUtilities.getWindowAncestor(this);
                if (w != null) w.dispose();
            });
            victoryTimer.setRepeats(false);
            victoryTimer.start();
            return;
        }

        // Enemy turn after a short delay
        Timer enemyTurnTimer = new Timer(1500, e -> {
            executeEnemyTurn();
        });
        enemyTurnTimer.setRepeats(false);
        enemyTurnTimer.start();
    }

    private void executeEnemyTurn() {
        // Execute turn for each alive enemy
        for (Enemy enemy : enemies) {
            if (enemy.getHp() <= 0) continue;

            ArrayList<Move> enemyMoves = enemy.getMoves();

            if (enemyMoves != null && !enemyMoves.isEmpty()) {
                Random random = new Random();
                int moveIndex = random.nextInt(enemyMoves.size());
                Move enemyMove = enemyMoves.get(moveIndex);

                double beforeHp = playerEntity.getHp();
                Move.currentTarget = playerEntity;
                enemyMove.execute(enemy);
                Move.currentTarget = null;
                double afterHp = playerEntity.getHp();
                double damageDealt = beforeHp - afterHp;

                if (damageDealt > 0) {
                    battleMessage = enemy.getName() + " used " + enemyMove.getName() + " and dealt " +
                            String.format("%.1f", damageDealt) + " damage!";
                } else {
                    battleMessage = enemy.getName() + " used " + enemyMove.getName() + " but it had no effect!";
                }
                repaint();

                // Check if player is defeated
                if (playerEntity.getHp() <= 0) {
                    battleMessage = playerEntity.getName() + " has been defeated! Game Over!";
                    repaint();

                    Timer gameOverTimer = new Timer(2000, e -> {
                        if (onBattleEnd != null) {
                            onBattleEnd.run();
                        }
                        Window w = SwingUtilities.getWindowAncestor(this);
                        if (w != null) w.dispose();
                    });
                    gameOverTimer.setRepeats(false);
                    gameOverTimer.start();
                    return;
                }

                // Small delay between enemy moves
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        // After enemy turn, show main menu again
        Timer resetTimer = new Timer(1500, e -> {
            battleMessage = "";
            showMainMenu();
            setButtonsEnabled(true);
            isExecutingMove = false;
            currentTargetEnemy = null;
            repaint();
        });
        resetTimer.setRepeats(false);
        resetTimer.start();
    }

    private void setButtonsEnabled(boolean enabled) {
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
    }

    private void runAway() {
        if (isExecutingMove) return;

        System.out.println("[Battle] Player escaped!");
        battleMessage = playerEntity.getName() + " escaped from battle!";
        repaint();

        Timer escapeTimer = new Timer(1000, e -> {
            if (onBattleEnd != null) {
                onBattleEnd.run();
            }
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w != null) w.dispose();
        });
        escapeTimer.setRepeats(false);
        escapeTimer.start();
    }

    // ─────────────────────────────────────────────────────────────
    //  Painting
    // ─────────────────────────────────────────────────────────────
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Draw background image if available
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, this);
        } else {
            // Fallback to solid color if no image is loaded
            g2.setColor(new Color(15, 10, 35));
            g2.fillRect(0, 0, WIDTH, HEIGHT);

            // Draw a simple gradient as fallback
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
                // Show defeated indicator
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

        paintUIBox(g2);
        g2.dispose();
    }

    private void paintDefeatedStatBox(Graphics2D g2, Rectangle r, String name) {
        g2.setColor(new Color(200, 200, 200, 180));
        g2.fill(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 12, 12));
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(2f));
        g2.draw(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 12, 12));

        g2.setFont(FONT_NAME);
        g2.setColor(Color.DARK_GRAY);
        int tx = r.x + 12;
        int ty = r.y + r.height / 2 + 5;
        g2.drawString(name + " (DEFEATED)", tx, ty);
    }

    // ── Stat box ──────────────────────────────────────────────────
    private void paintStatBox(Graphics2D g2, Rectangle r, String name, int level,
                              int hp, int maxHp, String expLine, boolean showExp) {
        // White rounded box
        g2.setColor(STAT_BG);
        g2.fill(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 10, 10));
        g2.setColor(STAT_BORDER);
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 10, 10));

        int tx = r.x + 8;
        int ty = r.y + 14;

        // Name (left side) - smaller font
        Font nameFont = new Font("Serif", Font.BOLD, 14);
        g2.setFont(nameFont);
        g2.setColor(Color.BLACK);
        g2.drawString(name, tx, ty);

        // Level (right side)
        if (level >= 0) {
            String lvlTag = "Lv. " + level;
            g2.setFont(nameFont);

            // Calculate text width for right alignment
            FontMetrics fm = g2.getFontMetrics();
            int lvlWidth = fm.stringWidth(lvlTag);
            int lvlX = r.x + r.width - 8 - lvlWidth;

            g2.drawString(lvlTag, lvlX, ty);
        }

        // HP label + bar
        ty += 16;
        Font statFont = new Font("Monospaced", Font.BOLD, 11);
        g2.setFont(statFont);
        g2.drawString("HP", tx, ty);

        int barX = tx + 28, barY = ty - 10;
        int barW = r.width - 42, barH = 10;

        // Bar background
        g2.setColor(new Color(190, 190, 190));
        g2.fillRoundRect(barX, barY, barW, barH, 5, 5);

        // HP fill
        double ratio = (maxHp > 0) ? Math.max(0, Math.min(1.0, (double) hp / maxHp)) : 0;
        Color hpColor = (ratio > 0.5) ? HP_GREEN : (ratio > 0.25) ? HP_YELLOW : HP_RED;
        g2.setColor(hpColor);
        g2.fillRoundRect(barX, barY, (int)(barW * ratio), barH, 5, 5);

        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(barX, barY, barW, barH, 5, 5);

        // HP numbers
        ty += 13;
        g2.setFont(statFont);
        g2.setColor(Color.BLACK);
        g2.drawString(hp + " / " + maxHp, barX, ty);

        // EXP (player only)
        if (showExp && expLine != null) {
            ty += 12;
            g2.setFont(new Font("Monospaced", Font.PLAIN, 9));
            g2.setColor(new Color(50, 50, 160));
            g2.drawString(expLine, tx, ty);
        }
    }

    // ── UI box ────────────────────────────────────────────────────
    private void paintUIBox(Graphics2D g2) {
        Rectangle r = uiBoxRect;

        g2.setColor(UI_BG);
        g2.fill(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 14, 14));

        g2.setColor(UI_BORDER);
        g2.setStroke(new BasicStroke(2.5f));
        g2.draw(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 14, 14));

        // Divider line
        g2.setColor(new Color(80, 150, 200, 90));
        g2.setStroke(new BasicStroke(1f));
        g2.drawLine(r.x + 14, r.y + 34, r.x + r.width - 14, r.y + 34);

        // Context prompt or battle message
        g2.setFont(FONT_TITLE);
        if (!battleMessage.isEmpty()) {
            g2.setColor(TEXT_GOLD);
            // Wrap message if too long
            String displayMessage = battleMessage;
            FontMetrics fm = g2.getFontMetrics();
            if (fm.stringWidth(displayMessage) > r.width - 40) {
                // Truncate if too long
                while (fm.stringWidth(displayMessage + "...") > r.width - 40 && displayMessage.length() > 0) {
                    displayMessage = displayMessage.substring(0, displayMessage.length() - 1);
                }
                displayMessage = displayMessage + "...";
            }
            g2.drawString(displayMessage, r.x + 20, r.y + 24);
        } else {
            g2.setColor(TEXT_GOLD);
            String prompt = (uiState == UIState.MAIN && !isExecutingMove)
                    ? "What will " + (playerEntity.getName() != null ? playerEntity.getName() : "you") + " do?"
                    : (uiState == UIState.FIGHT ? "Choose a move:" :
                    (uiState == UIState.TARGET_SELECT ? "Select target:" : ""));
            g2.drawString(prompt, r.x + 20, r.y + 24);
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  Inner class: styled button
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
            setForeground(TEXT_LIGHT);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered (MouseEvent e) { hovered = true;  repaint(); }
                @Override public void mouseExited  (MouseEvent e) { hovered = false; repaint(); }
                @Override public void mousePressed (MouseEvent e) { pressed = true;  repaint(); }
                @Override public void mouseReleased(MouseEvent e) { pressed = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color fill = pressed ? BTN_PRESS : hovered ? BTN_HOVER : BTN_NORMAL;
            g2.setColor(fill);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));

            // Top sheen when hovered
            if (hovered && !pressed) {
                g2.setColor(new Color(255, 255, 255, 25));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight() / 2f, 10, 10));
            }

            g2.setColor(BTN_BORDER);
            g2.setStroke(new BasicStroke(1.8f));
            g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 10, 10));

            // Label with shadow
            FontMetrics fm = g2.getFontMetrics(getFont());
            int tx = (getWidth()  - fm.stringWidth(getText())) / 2;
            int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

            g2.setFont(getFont());
            g2.setColor(new Color(0, 0, 0, 100));
            g2.drawString(getText(), tx + 1, ty + 1);
            g2.setColor(getForeground());
            g2.drawString(getText(), tx, ty);

            g2.dispose();
        }
    }
}