package Combat;

import Entities.Characters.Player;
import Entities.Enemies.Enemy;
import Moves.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
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
    private static final Color BG_TOP       = new Color(15,  10,  35);
    private static final Color BG_BOT       = new Color(35,  15,  55);
    private static final Color UI_BG        = new Color(20,  14,  45, 230);
    private static final Color UI_BORDER    = new Color(120, 80, 200);
    private static final Color STAT_BG      = Color.WHITE;
    private static final Color STAT_BORDER  = Color.BLACK;
    private static final Color HP_GREEN     = new Color(80,  200, 80);
    private static final Color HP_YELLOW    = new Color(230, 200, 40);
    private static final Color HP_RED       = new Color(220, 60,  60);
    private static final Color BTN_NORMAL   = new Color(60,  40, 120);
    private static final Color BTN_HOVER    = new Color(100, 65, 180);
    private static final Color BTN_PRESS    = new Color(40,  25,  80);
    private static final Color BTN_BORDER   = new Color(160, 110, 255);
    private static final Color TEXT_LIGHT   = new Color(240, 230, 255);
    private static final Color TEXT_GOLD    = new Color(255, 220, 80);

    // ── Fonts ─────────────────────────────────────────────────────
    private static final Font FONT_TITLE = new Font("Serif",      Font.BOLD,  18);
    private static final Font FONT_STAT  = new Font("Monospaced", Font.BOLD,  13);
    private static final Font FONT_BTN   = new Font("Serif",      Font.BOLD,  16);
    private static final Font FONT_NAME  = new Font("Serif",      Font.BOLD,  20);

    // ── UI State ──────────────────────────────────────────────────
    private enum UIState { MAIN, FIGHT }
    private UIState uiState = UIState.MAIN;

    // ── Entity refs ───────────────────────────────────────────────
    private final Player playerEntity;
    private final Enemy  enemyEntity;

    // ── Battle State ──────────────────────────────────────────────
    private String battleMessage = "";
    private boolean isExecutingMove = false;

    // ── Layout rects ──────────────────────────────────────────────
    private Rectangle enemyImgRect;
    private Rectangle playerImgRect;
    private Rectangle enemyStatRect;
    private Rectangle playerStatRect;
    private Rectangle uiBoxRect;

    // ── GIF placeholder labels ────────────────────────────────────
    private JLabel playerGifLabel;
    private JLabel enemyGifLabel;

    // ── Buttons ───────────────────────────────────────────────────
    private BattleButton btnFight;
    private BattleButton btnBag;
    private BattleButton btnRun;
    private BattleButton[] moveBtns;
    private BattleButton btnBack;

    // ── Close-to-exploration callback ─────────────────────────────
    private Runnable onBattleEnd;

    // ─────────────────────────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────────────────────────
    public BattlePanel(Player player, Enemy enemy) {
        this.playerEntity = player;
        this.enemyEntity  = enemy;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);   // absolute positioning
        setOpaque(true);

        computeLayout();
        buildGifLabels();
        buildButtons();

        showMainMenu();
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

        int imgW = 200, imgH = 180;
        int statW = 230, statH = 90;
        int margin = 40;

        // Calculate center positions
        int centerX = WIDTH / 2;
        int centerY = battleH / 2;

        // Spacing between the two characters
        int spacing = 100;

        // Player — LEFT side (centered vertically)
        int pX = centerX - spacing - imgW;
        int pY = centerY - (imgH + statH + 20) / 2;
        playerImgRect  = new Rectangle(pX, pY, imgW, imgH);
        playerStatRect = new Rectangle(pX - 10, pY + imgH + 10, statW + 20, statH + 10);

        // Enemy — RIGHT side (centered vertically)
        int eX = centerX + spacing;
        int eY = centerY - (imgH + statH + 20) / 2;
        enemyImgRect  = new Rectangle(eX, eY, imgW, imgH);
        enemyStatRect = new Rectangle(eX - 10, eY + imgH + 10, statW, statH);

        // UI box
        uiBoxRect = new Rectangle(10, uiY, WIDTH - 20, uiH);
    }

    // ─────────────────────────────────────────────────────────────
    //  GIF labels
    // ─────────────────────────────────────────────────────────────
    private void buildGifLabels() {
        enemyGifLabel = new JLabel("[ ENEMY ]", SwingConstants.CENTER);
        enemyGifLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        enemyGifLabel.setForeground(new Color(255, 150, 150));
        enemyGifLabel.setBorder(BorderFactory.createDashedBorder(
                new Color(200, 80, 80), 3f, 6f, 4f, false));
        enemyGifLabel.setBounds(enemyImgRect);
        add(enemyGifLabel);

        playerGifLabel = new JLabel("[ PLAYER ]", SwingConstants.CENTER);
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
    }

    /** Swap in the real enemy GIF once sprite assets are available. */
    public void setEnemyGif(ImageIcon icon) {
        enemyGifLabel.setIcon(icon);
        enemyGifLabel.setText(null);
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
    }

    // ─────────────────────────────────────────────────────────────
    //  UI state switching
    // ─────────────────────────────────────────────────────────────
    private void showMainMenu() {
        uiState = UIState.MAIN;
        btnFight.setVisible(true);
        btnBag  .setVisible(true);
        btnRun  .setVisible(true);
        for (BattleButton mb : moveBtns) mb.setVisible(false);
        btnBack.setVisible(false);
        repaint();
    }

    private void showFightMenu() {
        uiState = UIState.FIGHT;
        btnFight.setVisible(false);
        btnBag  .setVisible(false);
        btnRun  .setVisible(false);
        for (BattleButton mb : moveBtns) mb.setVisible(true);
        btnBack.setVisible(true);
        repaint();
    }

    // ─────────────────────────────────────────────────────────────
    //  Action handlers
    // ─────────────────────────────────────────────────────────────
    private void onMoveSelected(int index) {
        if (isExecutingMove) return; // Prevent multiple move executions

        ArrayList<Move> moves = playerEntity.getMoves();
        if (index < moves.size()) {
            Move selectedMove = moves.get(index);
            System.out.println("[Battle] Player used: " + selectedMove.getName());

            //Disable buttons during animation, tho wala pay animation
            setButtonsEnabled(false);
            isExecutingMove = true;

            //Execute the move
            executePlayerMove(selectedMove);
        }
    }

    private void executePlayerMove(Move move) {
        double beforeHp = enemyEntity.getHp();
        Move.currentTarget = enemyEntity;
        move.execute(playerEntity);
        Move.currentTarget = null;
        double afterHp = enemyEntity.getHp();
        double damageDealt = beforeHp - afterHp;

        // Show battle message
        if (damageDealt > 0) {
            battleMessage = playerEntity.getName() + " used " + move.getName() + " and dealt " +
                    String.format("%.1f", damageDealt) + " damage!";
        } else {
            battleMessage = playerEntity.getName() + " used " + move.getName() + " but it had no effect!";
        }
        repaint();

        // Check if enemy is defeated
        if (enemyEntity.getHp() <= 0) {
            battleMessage = enemyEntity.getName() + " has been defeated! You win!";
            repaint();

            // Handle victory
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
        // Get enemy's moves
        ArrayList<Move> enemyMoves = enemyEntity.getMoves();

        if (enemyMoves != null && !enemyMoves.isEmpty()) {
            // Choose a random move for the enemy
            Random random = new Random();
            int moveIndex = random.nextInt(enemyMoves.size());
            Move enemyMove = enemyMoves.get(moveIndex);

            // Store current HP to calculate damage
            double beforeHp = playerEntity.getHp();

            // Execute enemy move
            enemyMove.execute(enemyEntity);

            // Calculate damage dealt
            double afterHp = playerEntity.getHp();
            double damageDealt = beforeHp - afterHp;

            // Show battle message
            if (damageDealt > 0) {
                battleMessage = enemyEntity.getName() + " used " + enemyMove.getName() + " and dealt " +
                        String.format("%.1f", damageDealt) + " damage!";
            } else {
                battleMessage = enemyEntity.getName() + " used " + enemyMove.getName() + " but it had no effect!";
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
        }

        // After enemy turn, show main menu again
        Timer resetTimer = new Timer(1500, e -> {
            battleMessage = "";
            showMainMenu();
            setButtonsEnabled(true);
            isExecutingMove = false;
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

        paintBackground(g2);

        // Enemy stat box
        paintStatBox(g2, enemyStatRect,
                enemyEntity.getName() != null ? enemyEntity.getName() : "Enemy",
                -1,
                (int) enemyEntity.getHp(), (int) enemyEntity.getMaxHp(),
                null, false);

        // Player stat box (with EXP display - placeholder)
        paintStatBox(g2, playerStatRect,
                playerEntity.getName() != null ? playerEntity.getName() : "Player",
                1, // Placeholder level
                (int) playerEntity.getHp(), (int) playerEntity.getMaxHp(),
                "EXP: 0 / 100", // Placeholder EXP
                true);

        paintUIBox(g2);
        g2.dispose();
    }

    // ── Background ────────────────────────────────────────────────
    private void paintBackground(Graphics2D g2) {
        // Sky gradient
        g2.setPaint(new GradientPaint(0, 0, BG_TOP, 0, HEIGHT, BG_BOT));
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        // Ground stripe
        int groundY = uiBoxRect.y - 28;
        g2.setPaint(new GradientPaint(0, groundY, new Color(45, 28, 75),
                0, groundY + 55, new Color(22, 12, 40)));
        g2.fillRect(0, groundY, WIDTH, 55);

        // Faint star field
        g2.setColor(new Color(255, 255, 255, 55));
        long seed = 99991L;
        for (int i = 0; i < 70; i++) {
            seed = seed * 6364136223846793005L + 1442695040888963407L;
            int sx = (int) Math.abs(seed % WIDTH);
            seed = seed * 6364136223846793005L + 1442695040888963407L;
            int sy = (int) Math.abs(seed % Math.max(1, groundY - 10));
            int sr = (i % 4 == 0) ? 2 : 1;
            g2.fillOval(sx, sy, sr, sr);
        }
    }

    // ── Stat box ──────────────────────────────────────────────────
    private void paintStatBox(Graphics2D g2, Rectangle r, String name, int level,
                              int hp, int maxHp, String expLine, boolean showExp) {
        // White rounded box
        g2.setColor(STAT_BG);
        g2.fill(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 12, 12));
        g2.setColor(STAT_BORDER);
        g2.setStroke(new BasicStroke(2f));
        g2.draw(new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, 12, 12));

        int tx = r.x + 12;
        int ty = r.y + 20;

        // Name (left side)
        g2.setFont(FONT_NAME);
        g2.setColor(Color.BLACK);
        g2.drawString(name, tx, ty);

        // Level (right side)
        if (level >= 0) {
            String lvlTag = "Lv. " + level;
            g2.setFont(FONT_NAME);

            // Calculate text width for right alignment
            FontMetrics fm = g2.getFontMetrics();
            int lvlWidth = fm.stringWidth(lvlTag);
            int lvlX = r.x + r.width - 12 - lvlWidth;

            g2.drawString(lvlTag, lvlX, ty);
        }

        // HP label + bar
        ty += 22;
        g2.setFont(FONT_STAT);
        g2.drawString("HP", tx, ty);

        int barX = tx + 30, barY = ty - 12;
        int barW = r.width - 52, barH = 13;

        // Bar background
        g2.setColor(new Color(190, 190, 190));
        g2.fillRoundRect(barX, barY, barW, barH, 6, 6);

        // HP fill
        double ratio = (maxHp > 0) ? Math.max(0, Math.min(1.0, (double) hp / maxHp)) : 0;
        Color hpColor = (ratio > 0.5) ? HP_GREEN : (ratio > 0.25) ? HP_YELLOW : HP_RED;
        g2.setColor(hpColor);
        g2.fillRoundRect(barX, barY, (int)(barW * ratio), barH, 6, 6);

        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(barX, barY, barW, barH, 6, 6);

        // HP numbers
        ty += 17;
        g2.setFont(FONT_STAT);
        g2.setColor(Color.BLACK);
        g2.drawString(hp + " / " + maxHp, barX, ty);

        // EXP (player only)
        if (showExp && expLine != null) {
            ty += 15;
            g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
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
        g2.setColor(new Color(120, 80, 200, 90));
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
                    : (uiState == UIState.FIGHT ? "Choose a move:" : "");
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