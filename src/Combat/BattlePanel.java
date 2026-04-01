package Combat;

import Entities.Characters.*;
import Entities.Enemies.Enemy;
import Moves.Move;
import Moves.Swordsman.*;
import Moves.Ranger.*;
import Moves.Mage.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
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

    // ── Battle State ──────────────────────────────────────────────
    private String battleMessage = "";
    private boolean isExecutingMove = false;

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
        buildGifLabels();
        buildButtons();
        loadBackgroundImage();

        // Initialize battle system
        this.battle = new Battle(this, playerEntity, this.enemies);
        this.battle.setOnBattleEnd(() -> {
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
    //  GIF labels
    // ─────────────────────────────────────────────────────────────
    private void buildGifLabels() {
        // Enemy labels
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

    public void setPlayerGif(ImageIcon icon) {
        playerGifLabel.setIcon(icon);
        playerGifLabel.setText(null);
        playerGifLabel.setBorder(null);
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
        int backBtnX = uiBoxRect.x + 20;
        int backBtnY = uiBoxRect.y + uiBoxRect.height - backBtnH - 20;

        btnBackTarget = new BattleButton("← Back");
        btnBackTarget.setBounds(backBtnX, backBtnY, backBtnW, backBtnH);
        btnBackTarget.addActionListener(e -> {
            battle.cancelTargetSelection();
            showFightMenu();
            repaint();
        });
        btnBackTarget.setVisible(false);
        add(btnBackTarget);
    }

    // ── UI state switching ─────────────────────────────────────────────
    public void showMainMenu() {
        uiState = UIState.MAIN;
        btnFight.setVisible(true);
        btnBag.setVisible(true);
        btnRun.setVisible(true);
        for (BattleButton mb : moveBtns) mb.setVisible(false);
        btnBack.setVisible(false);
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
        uiState = UIState.FIGHT;
        btnFight.setVisible(false);
        btnBag.setVisible(false);
        btnRun.setVisible(false);
        for (BattleButton mb : moveBtns) mb.setVisible(true);
        btnBack.setVisible(true);
        battleMessage = "";
        hideTargetButtons();
        repaint();
    }

    public void showTargetSelection(Move move) {
        uiState = UIState.TARGET_SELECT;

        // Hide move buttons and back button
        for (BattleButton mb : moveBtns) mb.setVisible(false);
        btnBack.setVisible(false);

        // Update target button states based on enemy HP
        updateTargetButtonStates();

        // Show target selection buttons and back button
        for (BattleButton targetBtn : targetButtons) {
            targetBtn.setVisible(true);
        }
        btnBackTarget.setVisible(true);

        battleMessage = "Choose a target for " + move.getName() + "!";
        repaint();
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
                // Enemy is defeated - disable button and change appearance
                targetBtn.setEnabled(false);
                targetBtn.setForeground(Color.GRAY);
                targetBtn.setText(enemy.getName());
            } else {
                // Enemy is alive - enable button
                targetBtn.setEnabled(true);
                targetBtn.setForeground(TEXT_LIGHT);
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

        // Update target button states to reflect current enemy HP
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
    }

    private void paintUIBox(Graphics2D g2) {
        Rectangle r = uiBoxRect;

        g2.setColor(UI_BG);
        g2.fill(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 14, 14));

        g2.setColor(UI_BORDER);
        g2.setStroke(new BasicStroke(2.5f));
        g2.draw(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 14, 14));

        g2.setColor(new Color(80, 150, 200, 90));
        g2.setStroke(new BasicStroke(1f));
        g2.drawLine(r.x + 14, r.y + 34, r.x + r.width - 14, r.y + 34);

        g2.setFont(FONT_TITLE);
        if (!battleMessage.isEmpty()) {
            g2.setColor(TEXT_GOLD);
            String displayMessage = battleMessage;
            FontMetrics fm = g2.getFontMetrics();
            int maxWidth = r.width - 40;
            if (fm.stringWidth(displayMessage) > maxWidth) {
                while (fm.stringWidth(displayMessage + "...") > maxWidth && displayMessage.length() > 0) {
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

            // Handle disabled state differently
            if (!isEnabled()) {
                // Grayed out disabled button
                Color disabledFill = new Color(60, 60, 60);
                g2.setColor(disabledFill);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));

                g2.setColor(new Color(100, 100, 100));
                g2.setStroke(new BasicStroke(1.8f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 10, 10));

                // Draw text in gray
                FontMetrics fm = g2.getFontMetrics(getFont());
                int tx = (getWidth() - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

                g2.setFont(getFont());
                g2.setColor(Color.GRAY);
                g2.drawString(getText(), tx, ty);
            } else {
                // Normal enabled state
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
                int tx = (getWidth() - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

                g2.setFont(getFont());
                g2.setColor(new Color(0, 0, 0, 100));
                g2.drawString(getText(), tx + 1, ty + 1);
                g2.setColor(getForeground());
                g2.drawString(getText(), tx, ty);
            }

            g2.dispose();
        }
    }
}