package Main;

import Entities.Characters.Player;
import Entities.Characters.Swordsman;
import Entities.Characters.Ranger;
import Entities.Characters.Mage;
import Items.Weapons.Weapon;
import Moves.Move;

import Audio.SFX.ClickSFX;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class CharacterPanel extends JPanel {

    // Screen Dimensions
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    // Palette
    private static final Color BG_COLOR = new Color(160, 120, 80);
    private static final Color PARCH_TOP = new Color(148, 108, 44);
    private static final Color PARCH_TAN = new Color(192, 152, 78);
    private static final Color PARCH_WARM = new Color(220, 186, 118);
    private static final Color PARCH_CENTRE = new Color(238, 212, 152);
    private static final Color PARCH_BORDER = new Color(80, 38, 2, 230);
    private static final Color TEXT_DARK = new Color(60, 30, 5);
    private static final Color TEXT_GOLD = new Color(252, 218, 72);
    private static final Color HOVER_GOLD = new Color(252, 218, 72);
    private static final Color TEXT_BROWN = new Color(80, 38, 2, 230);

    // Stat Colors
    private static final Color STAT_HP = new Color(220, 80, 80);
    private static final Color STAT_ATK = new Color(255, 180, 80);
    private static final Color STAT_DEF = new Color(80, 150, 255);
    private static final Color STAT_SPD = new Color(80, 200, 80);
    private static final Color STAT_ACC = new Color(255, 220, 80);
    private static final Color STAT_EXP = new Color(100, 200, 255);
    private static final Color STAT_VALUE = Color.WHITE;

    // Gold Button Colors
    private static final Color BTN_GOLD_NORMAL = new Color(238, 190, 28);
    private static final Color BTN_GOLD_HOVER = new Color(250, 222, 62);
    private static final Color BTN_GOLD_PRESS = new Color(178, 108, 0);
    private static final Color BTN_BORDER_CLR = new Color(82, 38, 0, 215);
    private static final Color BTN_TEXT_DARK = new Color(42, 12, 0);

    // Fonts
    private static final Font FONT_STAT = new Font("Monospaced", Font.BOLD, 12);
    private static final Font FONT_STAT_VALUE = new Font("Monospaced", Font.PLAIN, 10);
    private static final Font FONT_BTN = new Font("Serif", Font.BOLD, 15);
    private static final Font FONT_NAME = new Font("Serif", Font.BOLD, 20);
    private static final Font FONT_DIALOG = new Font("Serif", Font.BOLD, 14);
    private static final Font FONT_SECTION = new Font("Serif", Font.BOLD, 16);

    // Layout Constants
    private static final int LEFT_PANEL_X = 20;
    private static final int LEFT_PANEL_Y = 100;
    private static final int LEFT_PANEL_W = 300;
    private static final int LEFT_PANEL_H = 460;

    private static final int RIGHT_PANEL_X = 340;
    private static final int RIGHT_PANEL_Y = 100;
    private static final int RIGHT_PANEL_W = 664;
    private static final int RIGHT_PANEL_H = 460;

    private static final int MSG_PANEL_X = 20;
    private static final int MSG_PANEL_Y = 580;
    private static final int MSG_PANEL_W = 984;
    private static final int MSG_PANEL_H = 80;

    private static final int BACK_BTN_X = WIDTH - 160;
    private static final int BACK_BTN_Y = HEIGHT - 70;
    private static final int BACK_BTN_W = 140;
    private static final int BACK_BTN_H = 50;

    // Title animation
    private Font fontTitle;
    private float shimmer = 1.4f;
    private float floatY = 0f;
    private float floatDir = 1f;
    private javax.swing.Timer titleAnimTimer;

    // Entity References
    private final JFrame parentFrame;
    private final Player player;
    private final boolean fromCombat;
    private final Runnable onBackPressed;
    private GamePanel gamePanel;

    // UI Components
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel messagePanel;
    private GoldButton backButton;
    private JLabel messageLabel;
    private JLabel characterLevelLabel;

    // Stat Components
    private JPanel statsContainer;
    private StatBarPanel hpBarPanel;
    private StatBarPanel expBarPanel;
    private JLabel hpValueLabel;
    private JLabel expValueLabel;
    private JLabel atkLabel;
    private JLabel defLabel;
    private JLabel spdLabel;
    private JLabel accLabel;

    // Player Animation
    private BufferedImage[] playerIdleFrames = new BufferedImage[5];
    private int currentPlayerFrame = 0;
    private Timer animationTimer;
    private JLabel playerImageLabel;
    private JLabel weaponIconLabel;
    private JLabel weaponNameLabel;
    private JLabel goldLabel;

    // Move Management
    private ArrayList<MoveButton> equippedMoveButtons = new ArrayList<>();
    private ArrayList<MoveButton> availableMoveButtons = new ArrayList<>();
    private ArrayList<MoveButton> lockedMoveButtons = new ArrayList<>();
    private boolean isReplacingMove = false;
    private Move pendingSwapMove = null;

    private Map<MoveButton, Move> availableMoveButtonMap = new HashMap<>();

    // Background Image
    private BufferedImage backgroundImage;

    // Constructor
    public CharacterPanel(JFrame parentFrame, Player player, boolean fromCombat, Runnable onBackPressed) {
        this.parentFrame = parentFrame;
        this.player = player;
        this.fromCombat = fromCombat;
        this.onBackPressed = onBackPressed;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        setOpaque(true);
        setFocusable(true);

        loadTitleFont();
        loadBackground();
        loadPlayerAnimations();
        initUI();
        startAnimationTimer();
        startTitleAnimation();

        refreshUI();

        syncMoveButtonStates();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!fromCombat) {
                    if (e.getKeyCode() == KeyEvent.VK_C || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        cleanup();
                        if (onBackPressed != null) {
                            onBackPressed.run();
                        }
                    }
                }
            }
        });
    }

    private void cleanup() {
        if (animationTimer != null) {
            animationTimer.cancel();
            animationTimer = null;
        }
        if (titleAnimTimer != null) {
            titleAnimTimer.stop();
        }
    }

    public void setGamePanel(GamePanel gp) { this.gamePanel = gp; }

    private void playClickSFX() {
        if (gamePanel != null) {
            gamePanel.getSFXPlayer().playSFX(new ClickSFX());
        }
    }

    public void requestPanelFocus() {
        requestFocusInWindow();
    }

    private void loadTitleFont() {
        Font base = null;
        for (String n : new String[]{
                "RINGM___.TTF", "RingbearerMedium.ttf", "Ringbearer Medium.ttf",
                "ringbearer medium.ttf", "Ringbearer.ttf", "ringbearer.ttf"}) {
            java.io.File f = new java.io.File(n);
            if (!f.exists()) continue;
            try {
                base = Font.createFont(Font.TRUETYPE_FONT, f);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(base);
                break;
            } catch (Exception ex) { /* ignore */ }
        }
        if (base == null) base = new Font("Palatino Linotype", Font.PLAIN, 12);
        fontTitle = base.deriveFont(Font.PLAIN, 52f);
    }

    private void startTitleAnimation() {
        titleAnimTimer = new javax.swing.Timer(16, e -> {
            floatY += 0.038f * floatDir;
            if (floatY > 5f) floatDir = -1f;
            if (floatY < -5f) floatDir = 1f;
            shimmer -= 0.005f;
            if (shimmer < -0.4f) shimmer = 1.4f;
            repaint();
        });
        titleAnimTimer.start();
    }

    private void paintTitle(Graphics2D g2) {
        String text = "Character";
        g2.setFont(fontTitle);
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = fontTitle.createGlyphVector(frc, text);
        Rectangle2D vis = gv.getVisualBounds();
        int tw = (int) vis.getWidth();
        int tx = (WIDTH - tw) / 2 - (int) vis.getX();
        int ty = (int) (60 + floatY);

        g2.setColor(new Color(0, 0, 0, 185));
        g2.drawString(text, tx + 4, ty + 6);
        g2.setColor(new Color(0, 0, 0, 75));
        g2.drawString(text, tx + 8, ty + 11);

        g2.setPaint(new LinearGradientPaint(
                tx, ty - (int) vis.getHeight(), tx, ty + 8,
                new float[]{0f, 0.35f, 0.65f, 1f},
                new Color[]{
                        new Color(255, 252, 210),
                        new Color(252, 218, 72),
                        new Color(218, 138, 18),
                        new Color(245, 198, 48)
                }));
        g2.drawString(text, tx, ty);

        float bandW = 110f;
        float bandX = tx + shimmer * (tw + bandW) - bandW;
        Shape savedClip = g2.getClip();
        g2.clip(gv.getOutline(tx, ty));
        g2.setPaint(new LinearGradientPaint(bandX, 0, bandX + bandW, 0,
                new float[]{0f, 0.35f, 0.5f, 0.65f, 1f},
                new Color[]{
                        new Color(255, 248, 200, 0),
                        new Color(255, 248, 200, 85),
                        new Color(255, 255, 255, 215),
                        new Color(255, 248, 200, 85),
                        new Color(255, 248, 200, 0)
                }));
        g2.fill(new Rectangle2D.Float(bandX, ty - (int) vis.getHeight() - 6, bandW, (int) vis.getHeight() + 18));
        g2.setClip(savedClip);
    }

    private void loadBackground() {
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/ui/inventory_bg.png"));
        } catch (Exception e) {
            backgroundImage = null;
        }
    }

    private void loadPlayerAnimations() {
        String className = getCharacterClassName();
        boolean anyFrameLoaded = false;

        for (int i = 0; i < 5; i++) {
            try {
                playerIdleFrames[i] = ImageIO.read(getClass().getResourceAsStream(
                        "/" + className + "/" + className + "_idle/idle_right" + (i + 1) + ".png"));
                if (playerIdleFrames[i] != null) anyFrameLoaded = true;
            } catch (Exception e) {
                playerIdleFrames[i] = null;
            }
        }

        if (!anyFrameLoaded) {
            createPlaceholderFrames();
        }
    }

    private String getCharacterClassName() {
        if (player instanceof Swordsman) return "swordsman";
        if (player instanceof Ranger) return "archer";
        if (player instanceof Mage) return "mage";
        return "swordsman";
    }

    private void createPlaceholderFrames() {
        Color baseColor;
        String initial;

        if (player instanceof Swordsman) {
            baseColor = new Color(200, 80, 80);
            initial = "S";
        } else if (player instanceof Ranger) {
            baseColor = new Color(80, 180, 100);
            initial = "R";
        } else {
            baseColor = new Color(80, 120, 220);
            initial = "M";
        }

        for (int i = 0; i < 5; i++) {
            float brightness = 0.7f + (i * 0.06f);
            Color frameColor = new Color(
                    Math.min(255, (int) (baseColor.getRed() * brightness)),
                    Math.min(255, (int) (baseColor.getGreen() * brightness)),
                    Math.min(255, (int) (baseColor.getBlue() * brightness))
            );
            playerIdleFrames[i] = createPlaceholderImage(frameColor, initial);
        }
    }

    private BufferedImage createPlaceholderImage(Color color, String initial) {
        int w = 135, h = 135;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        g2.setColor(color);
        g2.fillRect(0, 0, w, h);

        Font bigFont = new Font("Serif", Font.BOLD, 32);
        g2.setFont(bigFont);
        FontMetrics fm = g2.getFontMetrics();
        int lx = (w - fm.stringWidth(initial)) / 2;
        int ly = (h + fm.getAscent() - fm.getDescent()) / 2 + 2;

        g2.setColor(new Color(0, 0, 0, 80));
        g2.drawString(initial, lx + 2, ly + 2);
        g2.setColor(Color.WHITE);
        g2.drawString(initial, lx, ly);

        g2.dispose();
        return img;
    }

    private void startAnimationTimer() {
        if (animationTimer != null) {
            animationTimer.cancel();
        }
        animationTimer = new Timer();
        animationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentPlayerFrame = (currentPlayerFrame + 1) % 5;
                SwingUtilities.invokeLater(() -> {
                    if (playerImageLabel != null && playerIdleFrames[currentPlayerFrame] != null) {
                        Image scaled = playerIdleFrames[currentPlayerFrame].getScaledInstance(135, 135, Image.SCALE_SMOOTH);
                        playerImageLabel.setIcon(new ImageIcon(scaled));
                    }
                });
            }
        }, 0, 120);
    }

    private void initUI() {
        leftPanel = createParchmentPanel(LEFT_PANEL_X, LEFT_PANEL_Y, LEFT_PANEL_W, LEFT_PANEL_H);
        add(leftPanel);
        setupLeftPanel();

        rightPanel = createParchmentPanel(RIGHT_PANEL_X, RIGHT_PANEL_Y, RIGHT_PANEL_W, RIGHT_PANEL_H);
        add(rightPanel);
        setupRightPanel();

        messagePanel = createParchmentPanel(MSG_PANEL_X, MSG_PANEL_Y, MSG_PANEL_W, MSG_PANEL_H);
        add(messagePanel);
        setupMessagePanel();

        backButton = new GoldButton("← Back");
        backButton.setBounds(BACK_BTN_X, BACK_BTN_Y, BACK_BTN_W, BACK_BTN_H);
        backButton.addActionListener(e -> {
            playClickSFX();
            cleanup();
            if (onBackPressed != null) {
                onBackPressed.run();
            }
        });
        add(backButton);
    }

    private JPanel createParchmentPanel(int x, int y, int w, int h) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int sx = 0, sy = 0, sw = getWidth(), sh = getHeight();

                for (int i = 8; i >= 1; i--) {
                    float a = 0.06f * (9 - i);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
                    g2.setColor(new Color(20, 8, 0));
                    g2.fill(new RoundRectangle2D.Float(sx - i, sy + i * 2, sw + i * 2, sh, 18, 18));
                }
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

                g2.setPaint(new LinearGradientPaint(sx, sy, sx, sy + sh,
                        new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                        new Color[]{
                                PARCH_TOP, PARCH_TAN, PARCH_WARM, PARCH_CENTRE,
                                PARCH_WARM, PARCH_TAN, PARCH_TOP
                        }));
                Shape body = new RoundRectangle2D.Float(sx, sy, sw, sh, 18, 18);
                g2.fill(body);

                g2.setStroke(new BasicStroke(2.5f));
                g2.setColor(PARCH_BORDER);
                g2.draw(body);

                g2.dispose();
            }
        };
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(x, y, w, h);
        return panel;
    }

    private void setupLeftPanel() {
        // Gold display above character sprite
        goldLabel = new JLabel("$ " + player.getMoney(), SwingConstants.CENTER);
        goldLabel.setFont(new Font("Serif", Font.BOLD, 14));
        goldLabel.setForeground(TEXT_GOLD);
        goldLabel.setBounds(10, 15, 260, 22);
        leftPanel.add(goldLabel);

        // Player image (left side)
        playerImageLabel = new JLabel();
        if (playerIdleFrames[0] != null) {
            Image scaled = playerIdleFrames[0].getScaledInstance(135, 135, Image.SCALE_SMOOTH);
            playerImageLabel.setIcon(new ImageIcon(scaled));
        }
        playerImageLabel.setBounds(15, 65, 135, 135);
        playerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        leftPanel.add(playerImageLabel);

        // Equipped weapon panel (right side of top row)
        JPanel equippedWeaponPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 100));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.setColor(TEXT_GOLD);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(2, 2, getWidth() - 4, getHeight() - 4, 8, 8));
                g2.dispose();
            }
        };
        equippedWeaponPanel.setLayout(null);
        equippedWeaponPanel.setOpaque(false);
        equippedWeaponPanel.setBounds(165, 65, 120, 135);
        leftPanel.add(equippedWeaponPanel);

        weaponIconLabel = new JLabel();
        weaponIconLabel.setBounds(36, 15, 48, 48);
        weaponIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        equippedWeaponPanel.add(weaponIconLabel);

        weaponNameLabel = new JLabel("No weapon");
        weaponNameLabel.setFont(new Font("Serif", Font.BOLD, 10));
        weaponNameLabel.setForeground(TEXT_GOLD);
        weaponNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        weaponNameLabel.setBounds(10, 70, 100, 40);
        equippedWeaponPanel.add(weaponNameLabel);

        int level = player.getLevel();
        if (level < 1) level = 1;
        characterLevelLabel = new JLabel("Lv. " + level, SwingConstants.CENTER);
        characterLevelLabel.setFont(FONT_STAT);
        characterLevelLabel.setForeground(TEXT_BROWN);
        characterLevelLabel.setBounds(-40, 195, 260, 24);
        characterLevelLabel.setOpaque(false);
        leftPanel.add(characterLevelLabel);

        // Stats Container
        setupStatsContainer();
    }

    private void setupStatsContainer() {
        statsContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Semi-transparent dark background
                g2.setColor(new Color(0, 0, 0, 150));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));

                // Gold border
                g2.setColor(TEXT_GOLD);
                g2.setStroke(new BasicStroke(2.0f));
                g2.draw(new RoundRectangle2D.Float(2, 2, getWidth() - 4, getHeight() - 4, 10, 10));

                g2.dispose();
            }
        };
        statsContainer.setLayout(null);
        statsContainer.setOpaque(false);
        statsContainer.setBounds(15, 235, 270, 170);
        leftPanel.add(statsContainer);

        // HEALTH Label
        JLabel hpLabel = new JLabel("HEALTH");
        hpLabel.setFont(FONT_STAT);
        hpLabel.setForeground(STAT_HP);
        hpLabel.setBounds(10, 10, 60, 18);
        statsContainer.add(hpLabel);

        // HEALTH Bar Panel
        hpBarPanel = new StatBarPanel(STAT_HP);
        hpBarPanel.setBounds(75, 14, 170, 14);
        statsContainer.add(hpBarPanel);

        // HEALTH Value Label
        hpValueLabel = new JLabel("0 / 0", SwingConstants.RIGHT);
        hpValueLabel.setFont(FONT_STAT_VALUE);
        hpValueLabel.setForeground(STAT_VALUE);
        hpValueLabel.setBounds(150, 31, 95, 14);
        statsContainer.add(hpValueLabel);

        // EXPERIENCE Label
        JLabel expLabel = new JLabel("EXP");
        expLabel.setFont(FONT_STAT);
        expLabel.setForeground(STAT_EXP);
        expLabel.setBounds(10, 44, 60, 18);
        statsContainer.add(expLabel);

        // EXPERIENCE Bar Panel
        expBarPanel = new StatBarPanel(STAT_EXP);
        expBarPanel.setBounds(75, 48, 170, 14);
        statsContainer.add(expBarPanel);

        // EXPERIENCE Value Label
        expValueLabel = new JLabel("0 / 0", SwingConstants.RIGHT);
        expValueLabel.setFont(FONT_STAT_VALUE);
        expValueLabel.setForeground(STAT_VALUE);
        expValueLabel.setBounds(150, 65, 95, 14);
        statsContainer.add(expValueLabel);

        // ATTACK Label (text only)
        atkLabel = new JLabel("ATTACK: 0 / 0");
        atkLabel.setFont(FONT_STAT);
        atkLabel.setForeground(STAT_ATK);
        atkLabel.setBounds(10, 80, 250, 18);
        statsContainer.add(atkLabel);

        // DEFENSE Label (text only)
        defLabel = new JLabel("DEFENSE: 0 / 0");
        defLabel.setFont(FONT_STAT);
        defLabel.setForeground(STAT_DEF);
        defLabel.setBounds(10, 100, 250, 18);
        statsContainer.add(defLabel);

        // SPEED Label (text only)
        spdLabel = new JLabel("SPEED: 0");
        spdLabel.setFont(FONT_STAT);
        spdLabel.setForeground(STAT_SPD);
        spdLabel.setBounds(10, 120, 250, 18);
        statsContainer.add(spdLabel);

        // ACCURACY Label (text only)
        accLabel = new JLabel("ACCURACY: 0.0%");
        accLabel.setFont(FONT_STAT);
        accLabel.setForeground(STAT_ACC);
        accLabel.setBounds(10, 140, 250, 18);
        statsContainer.add(accLabel);
    }

    private class StatBarPanel extends JPanel {
        private Color barColor;
        private float fillPercent = 0f;

        StatBarPanel(Color barColor) {
            this.barColor = barColor;
            setOpaque(false);
        }

        void setFillPercent(float percent) {
            this.fillPercent = Math.max(0f, Math.min(1f, percent));
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Background
            g2.setColor(new Color(30, 30, 30, 200));
            g2.fill(new RoundRectangle2D.Float(0, 0, w, h, 5, 5));

            // Fill
            int fillWidth = (int) (w * fillPercent);
            if (fillWidth > 0) {
                g2.setColor(barColor);
                g2.fill(new RoundRectangle2D.Float(0, 0, fillWidth, h, 5, 5));
            }

            // Border
            g2.setColor(new Color(60, 60, 60));
            g2.setStroke(new BasicStroke(1.0f));
            g2.draw(new RoundRectangle2D.Float(0, 0, w - 1, h - 1, 5, 5));

            g2.dispose();
        }
    }

    private void refreshStats() {
        if (goldLabel != null) goldLabel.setText("$ " + player.getMoney());

        // HEALTH
        int currentHp = (int) player.getHp();
        int maxHp = (int) player.getMaxHp();
        maxHp = Math.max(1, maxHp);
        float hpPercent = (float) currentHp / maxHp;
        hpBarPanel.setFillPercent(hpPercent);
        hpValueLabel.setText(currentHp + " / " + maxHp);

        // EXPERIENCE
        int currentExp = player.getExperience();
        int neededExp = player.getExpNeeded();
        neededExp = Math.max(1, neededExp);
        float expPercent = (float) currentExp / neededExp;
        expBarPanel.setFillPercent(expPercent);
        expValueLabel.setText(currentExp + " / " + neededExp);

        // ATTACK
        int currentAtk = (int) player.getAttack();
        int maxAtk = (int) player.getMaxAttack();
        atkLabel.setText("ATTACK: " + currentAtk + " / " + maxAtk);

        // DEFENSE
        int currentDef = (int) player.getDefense();
        int maxDef = (int) player.getMaxDefense();
        defLabel.setText("DEFENSE: " + currentDef + " / " + maxDef);

        // SPEED
        int currentSpd = (int) player.getSpeed();
        spdLabel.setText("SPEED: " + currentSpd);

        // ACCURACY
        double accuracy = player.getAccuracy();
        accLabel.setText(String.format("ACCURACY: %.1f%%", accuracy * 100));
    }

    private void setupRightPanel() {
        // Equipped Moves Section
        JLabel equippedLabel = new JLabel("EQUIPPED MOVES");
        equippedLabel.setFont(FONT_SECTION);
        equippedLabel.setForeground(TEXT_BROWN);
        equippedLabel.setBounds(20, 15, 200, 25);
        rightPanel.add(equippedLabel);

        // Create 4 equipped move buttons in 2x2 grid
        for (int i = 0; i < 4; i++) {
            int row = i / 2;
            int col = i % 2;
            MoveButton btn = new MoveButton("");
            btn.setBounds(20 + (col * 155), 50 + (row * 52), 140, 42);
            final int index = i;
            btn.addActionListener(e -> { playClickSFX(); handleEquippedMoveClick(index); });
            equippedMoveButtons.add(btn);
            rightPanel.add(btn);
        }

        // Divider line
        JSeparator divider1 = new JSeparator();
        divider1.setBounds(20, 160, 620, 2);
        divider1.setForeground(TEXT_GOLD);
        rightPanel.add(divider1);

        // Available Moves Section
        JLabel availableLabel = new JLabel("AVAILABLE MOVES");
        availableLabel.setFont(FONT_SECTION);
        availableLabel.setForeground(TEXT_BROWN);
        availableLabel.setBounds(20, 170, 200, 25);
        rightPanel.add(availableLabel);

        // Create available move buttons
        List<Move> moveset = player.getMoveset();
        List<Move> equippedMoves = player.getMoves();
        int col = 0, row = 0;
        for (Move move : moveset) {
            if (move.hasUnlocked() && !equippedMoves.contains(move)) {
                MoveButton btn = new MoveButton(move.getName());
                btn.setMove(move);
                btn.setBounds(20 + (col * 155), 205 + (row * 52), 140, 42);
                btn.addActionListener(e -> { playClickSFX(); handleAvailableMoveClick(move); });
                availableMoveButtons.add(btn);

                availableMoveButtonMap.put(btn, move);

                rightPanel.add(btn);

                col++;
                if (col >= 4) {
                    col = 0;
                    row++;
                }
            }
        }

        // Divider line
        int availableSectionHeight = 205 + (row + 1) * 52;
        JSeparator divider2 = new JSeparator();
        divider2.setBounds(20, availableSectionHeight + 10, 620, 2);
        divider2.setForeground(TEXT_GOLD);
        rightPanel.add(divider2);

        // Locked Moves Section
        JLabel lockedLabel = new JLabel("LOCKED MOVES");
        lockedLabel.setFont(FONT_SECTION);
        lockedLabel.setForeground(TEXT_BROWN);
        lockedLabel.setBounds(20, availableSectionHeight + 20, 200, 25);
        rightPanel.add(lockedLabel);

        // Create locked move buttons
        col = 0; row = 0;
        for (Move move : moveset) {
            if (!move.hasUnlocked()) {
                MoveButton btn = new MoveButton(move.getName());
                btn.setMove(move);
                btn.setEnabled(false);
                btn.setBounds(20 + (col * 155), availableSectionHeight + 55 + (row * 52), 140, 42);
                lockedMoveButtons.add(btn);

                availableMoveButtonMap.put(btn, move);

                rightPanel.add(btn);

                col++;
                if (col >= 4) {
                    col = 0;
                    row++;
                }
            }
        }
    }

    /**
     * Synchronizes the state of available move buttons with currently equipped moves.
     * Disables buttons for moves that are already equipped and updates tooltips.
     */
    private void syncMoveButtonStates() {
        // Build a set of equipped move names for quick lookup
        Set<String> equippedMoveNames = player.getMoves().stream()
                .map(Move::getName)
                .collect(Collectors.toSet());

        // Iterate through all available move buttons
        for (Map.Entry<MoveButton, Move> entry : availableMoveButtonMap.entrySet()) {
            MoveButton btn = entry.getKey();
            Move move = entry.getValue();

            boolean isEquipped = equippedMoveNames.contains(move.getName());
            boolean isLocked = !move.hasUnlocked();

            if (isLocked) {
                // Locked moves - disabled, dark styling
                btn.setEnabled(false);
                btn.setToolTipText("LOCKED - Requires level progression");
                btn.setForeground(new Color(120, 100, 70));
            } else if (isEquipped) {
                // Already equipped moves - disabled, distinct styling
                btn.setEnabled(false);
                btn.setToolTipText("Already equipped");
                btn.setForeground(new Color(120, 100, 70));
            } else {
                // Available moves - enabled, normal styling
                btn.setEnabled(true);
                btn.setToolTipText("Click to equip");
                btn.setForeground(BTN_TEXT_DARK);
            }
        }
    }

    private void handleEquippedMoveClick(int index) {
        List<Move> playerMoves = player.getMoves();

        if (isReplacingMove && pendingSwapMove != null) {
            if (index < playerMoves.size()) {
                if (playerMoves.contains(pendingSwapMove)) {
                    showParchmentDialog("Already Equipped",
                            pendingSwapMove.getName() + " is already equipped!", "OK", null, null);
                    cancelReplacement();
                    return;
                }

                Move oldMove = playerMoves.get(index);
                playerMoves.set(index, pendingSwapMove);
                messageLabel.setText("Replaced " + oldMove.getName() + " with " + pendingSwapMove.getName());
                refreshUI();
            }
            cancelReplacement();
        } else if (index < playerMoves.size()) {
            Move move = playerMoves.get(index);
            messageLabel.setText(move.getName() + ": " + move.getDescription());
        }
    }

    private void handleAvailableMoveClick(Move move) {
        if (isReplacingMove) {
            return;
        }

        List<Move> playerMoves = player.getMoves();

        if (playerMoves.contains(move)) {
            showParchmentDialog("Already Equipped",
                    move.getName() + " is already equipped!", "OK", null, null);
            refreshUI();
            return;
        }

        if (playerMoves.size() < 4) {
            playerMoves.add(move);
            messageLabel.setText("Equipped " + move.getName());
            refreshUI();
        } else {
            String confirmMessage = "Equip " + move.getName() + "?\n" +
                    move.getDescription();
            showParchmentDialog("Equip Move", confirmMessage, "Yes", "No", () -> {
                if (player.getMoves().contains(move)) {
                    showParchmentDialog("Already Equipped",
                            move.getName() + " is already equipped!", "OK", null, null);
                    refreshUI();
                    return;
                }

                isReplacingMove = true;
                pendingSwapMove = move;
                messageLabel.setText("Choose a move to replace with " + move.getName());
                highlightEquippedButtons(true);
            });
        }
    }

    private void cancelReplacement() {
        isReplacingMove = false;
        pendingSwapMove = null;
        highlightEquippedButtons(false);
        messageLabel.setText("Hover over a move for info");
    }

    private void highlightEquippedButtons(boolean highlight) {
        for (MoveButton btn : equippedMoveButtons) {
            btn.setHighlighted(highlight);
        }
    }

    private void setupMessagePanel() {
        messageLabel = new JLabel("Hover over a move for info");
        messageLabel.setFont(FONT_NAME);
        messageLabel.setForeground(TEXT_DARK);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(20, 20, MSG_PANEL_W - 40, 40);
        messagePanel.add(messageLabel);
    }

    private void refreshUI() {
        // Update weapon display
        Weapon equipped = player.getWeapon();
        if (equipped != null) {
            BufferedImage weaponImg = equipped.getImage();
            if (weaponImg != null) {
                Image scaled = weaponImg.getScaledInstance(48, 48, Image.SCALE_FAST);
                weaponIconLabel.setIcon(new ImageIcon(scaled));
            } else {
                weaponIconLabel.setIcon(null);
            }
            weaponNameLabel.setText(equipped.getName());
        } else {
            weaponIconLabel.setIcon(null);
            weaponNameLabel.setText("No weapon");
        }

        // Update equipped move buttons
        List<Move> playerMoves = player.getMoves();
        for (int i = 0; i < 4; i++) {
            if (i < playerMoves.size()) {
                Move move = playerMoves.get(i);
                equippedMoveButtons.get(i).setMove(move);
                equippedMoveButtons.get(i).setText(move.getName());
                equippedMoveButtons.get(i).setEnabled(true);
            } else {
                equippedMoveButtons.get(i).setMove(null);
                equippedMoveButtons.get(i).setText("Empty");
                equippedMoveButtons.get(i).setEnabled(!fromCombat);
            }
        }

        syncMoveButtonStates();

        // Refresh stats
        refreshStats();
    }

    private void showParchmentDialog(String title, String message, String option1, String option2, Runnable onConfirm) {
        JDialog dialog = new JDialog(parentFrame, title, true);
        dialog.setUndecorated(true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(parentFrame);

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setPaint(new LinearGradientPaint(0, 0, 0, getHeight(),
                        new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                        new Color[]{
                                PARCH_TOP, PARCH_TAN, PARCH_WARM, PARCH_CENTRE,
                                PARCH_WARM, PARCH_TAN, PARCH_TOP
                        }));
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(TEXT_GOLD);
                g2.setStroke(new BasicStroke(3.0f));
                g2.drawRect(5, 5, getWidth() - 10, getHeight() - 10);

                g2.dispose();
            }
        };
        contentPane.setLayout(null);
        contentPane.setOpaque(false);
        dialog.setContentPane(contentPane);

        JLabel msgLabel = new JLabel("<html><center>" + message + "</center></html>");
        msgLabel.setFont(FONT_DIALOG);
        msgLabel.setForeground(TEXT_DARK);
        msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        msgLabel.setBounds(20, 30, 360, 80);
        contentPane.add(msgLabel);

        GoldButton btn1 = new GoldButton(option1);
        btn1.setBounds(option2 == null ? 150 : 80, 130, option2 == null ? 100 : 100, 40);
        btn1.addActionListener(e -> {
            playClickSFX();
            dialog.dispose();
            if (onConfirm != null) onConfirm.run();
        });
        contentPane.add(btn1);

        if (option2 != null) {
            GoldButton btn2 = new GoldButton(option2);
            btn2.setBounds(220, 130, 100, 40);
            btn2.addActionListener(e -> {
                playClickSFX();
                dialog.dispose();
                cancelReplacement();
            });
            contentPane.add(btn2);
        }

        dialog.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);
        } else {
            g2.setColor(BG_COLOR);
            g2.fillRect(0, 0, WIDTH, HEIGHT);
        }

        paintTitle(g2);

        g2.dispose();
    }

    // Inner Classes
    private class MoveButton extends JButton {
        private Move move;
        private boolean hovered = false;
        private boolean pressed = false;
        private boolean highlighted = false;

        MoveButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFont(FONT_BTN);
            setForeground(BTN_TEXT_DARK);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (isEnabled()) {
                        hovered = true;
                        if (move != null) {
                            String displayText;

                            displayText = move.getName() + " | " + move.getDescription();

                            if (!move.hasUnlocked()) {
                                displayText = move.getName() + " | LOCKED | Requires level progression";
                            } else {
                                // Check if this move is currently equipped
                                boolean isEquipped = player.getMoves().stream()
                                        .anyMatch(m -> m.getName().equals(move.getName()));
                            }
                            messageLabel.setText(displayText);
                        }
                        repaint();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovered = false;
                    if (!isReplacingMove) {
                        messageLabel.setText("Hover over a move for info");
                    }
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

        public void setMove(Move move) {
            this.move = move;
        }

        public Move getMove() {
            return move;
        }

        public void setHighlighted(boolean highlighted) {
            this.highlighted = highlighted;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            int c = Math.min(w, h) / 6;
            if (c < 6) c = 6;
            Polygon oct = makeOctagon(0, 0, w, h, c);

            if (!isEnabled()) {
                g2.setColor(new Color(100, 80, 60));
                g2.fill(oct);
                g2.setColor(new Color(60, 50, 40));
                g2.setStroke(new BasicStroke(1.8f));
                g2.draw(oct);
            } else {
                Color fill = pressed ? BTN_GOLD_PRESS :
                        (highlighted ? new Color(255, 200, 50) :
                                (hovered ? BTN_GOLD_HOVER : BTN_GOLD_NORMAL));
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
            }

            FontMetrics fm = g2.getFontMetrics(getFont());
            String displayText = getText();

            int tx = (w - fm.stringWidth(displayText)) / 2;
            int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
            g2.setFont(getFont());

            if (!isEnabled()) {
                g2.setColor(new Color(150, 150, 150));
            } else {
                g2.setColor(new Color(0, 0, 0, 100));
                g2.drawString(displayText, tx + 1, ty + 1);
                g2.setColor(getForeground());
            }
            g2.drawString(displayText, tx, ty);
            g2.dispose();
        }

        private Polygon makeOctagon(int x, int y, int w, int h, int c) {
            return new Polygon(
                    new int[]{x + c, x + w - c, x + w, x + w, x + w - c, x + c, x, x},
                    new int[]{y, y, y + c, y + h - c, y + h, y + h, y + h - c, y + c}, 8);
        }
    }

    private static class GoldButton extends JButton {
        private boolean hovered = false;
        private boolean pressed = false;

        GoldButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFont(FONT_BTN);
            setForeground(BTN_TEXT_DARK);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (isEnabled()) {
                        hovered = true;
                        repaint();
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

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            int c = Math.min(w, h) / 6;
            if (c < 6) c = 6;
            Polygon oct = makeOctagon(0, 0, w, h, c);

            if (!isEnabled()) {
                g2.setColor(new Color(160, 140, 100));
                g2.fill(oct);
                g2.setColor(new Color(100, 80, 50));
                g2.setStroke(new BasicStroke(1.8f));
                g2.draw(oct);
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
            }

            FontMetrics fm = g2.getFontMetrics(getFont());
            int tx = (w - fm.stringWidth(getText())) / 2;
            int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
            g2.setFont(getFont());
            g2.setColor(new Color(0, 0, 0, 100));
            g2.drawString(getText(), tx + 1, ty + 1);
            g2.setColor(getForeground());
            g2.drawString(getText(), tx, ty);
            g2.dispose();
        }

        private Polygon makeOctagon(int x, int y, int w, int h, int c) {
            return new Polygon(
                    new int[]{x + c, x + w - c, x + w, x + w, x + w - c, x + c, x, x},
                    new int[]{y, y, y + c, y + h - c, y + h, y + h, y + h - c, y + c}, 8);
        }
    }
}