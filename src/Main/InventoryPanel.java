package Main;

import Entities.Characters.*;
import Entities.Entity;
import Entities.Enemies.Enemy;
import Items.Item;
import Items.Weapons.Weapon;
import Items.Consumables.Heal.*;
import Items.Consumables.Buff.*;
import Items.Consumables.Debuff.Dulling.*;
import Items.Consumables.Debuff.Softening.*;
import Items.Consumables.Debuff.Clumsiness.*;
import Items.Consumables.Debuff.Blinding.*;
import Moves.Move;

import Audio.SFX.ClickSFX;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;

public class InventoryPanel extends JPanel {

    // ── Screen Dimensions ─────────────────────────────────────────
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    // ── Palette ───────────────────────────────────────────────────
    private static final Color BG_COLOR = new Color(160, 120, 80);
    private static final Color PARCH_TOP = new Color(148, 108, 44);
    private static final Color PARCH_TAN = new Color(192, 152, 78);
    private static final Color PARCH_WARM = new Color(220, 186, 118);
    private static final Color PARCH_CENTRE = new Color(238, 212, 152);
    private static final Color PARCH_BORDER = new Color(80, 38, 2, 230);
    private static final Color TEXT_DARK = new Color(60, 30, 5);
    private static final Color TEXT_GOLD = new Color(252, 218, 72);
    private static final Color GOLD_DARK = new Color(178, 108, 0);
    private static final Color HOVER_GOLD = new Color(252, 218, 72);
    private static final Color TEXT_BROWN = new Color(80, 38, 2, 230);

    // ── Stat Colors ────────────────────────────────────────────────
    private static final Color STAT_HP = new Color(220, 80, 80);
    private static final Color STAT_ATK = new Color(255, 180, 80);
    private static final Color STAT_DEF = new Color(80, 150, 255);
    private static final Color STAT_SPD = new Color(80, 200, 80);
    private static final Color STAT_ACC = new Color(255, 220, 80);
    private static final Color STAT_EXP = new Color(100, 200, 255);
    private static final Color STAT_VALUE = Color.WHITE;

    // ── Gold Octagon Button Colors ─────────────────────────────────
    private static final Color BTN_GOLD_NORMAL = new Color(238, 190, 28);
    private static final Color BTN_GOLD_HOVER = new Color(250, 222, 62);
    private static final Color BTN_GOLD_PRESS = new Color(178, 108, 0);
    private static final Color BTN_BORDER_CLR = new Color(82, 38, 0, 215);
    private static final Color BTN_TEXT_DARK = new Color(42, 12, 0);

    // ── Fonts ─────────────────────────────────────────────────────
    private static final Font FONT_TITLE = new Font("Serif", Font.BOLD, 18);
    private static final Font FONT_STAT = new Font("Monospaced", Font.BOLD, 13);
    private static final Font FONT_BTN = new Font("Serif", Font.BOLD, 16);
    private static final Font FONT_NAME = new Font("Serif", Font.BOLD, 20);
    private static final Font FONT_ITEM = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font FONT_DIALOG = new Font("Serif", Font.BOLD, 14);

    // Title fonts
    private Font fontTitle;
    private float shimmer = 1.4f;
    private float floatY = 0f;
    private float floatDir = 1f;
    private javax.swing.Timer titleAnimTimer;

    // ── Layout Constants ───────────────────────────────────────────
    private static final int CHAR_PANEL_X = 20;
    private static final int CHAR_PANEL_Y = 100;
    private static final int CHAR_PANEL_W = 300;
    private static final int CHAR_PANEL_H = 460;

    private static final int INV_PANEL_X = 340;
    private static final int INV_PANEL_Y = 100;
    private static final int INV_PANEL_W = 664;
    private static final int INV_PANEL_H = 460;

    private static final int MSG_PANEL_X = 20;
    private static final int MSG_PANEL_Y = 580;
    private static final int MSG_PANEL_W = WIDTH - 40;
    private static final int MSG_PANEL_H = 100;

    private static final int BACK_BTN_X = WIDTH - 160;
    private static final int BACK_BTN_Y = HEIGHT - 70;
    private static final int BACK_BTN_W = 140;
    private static final int BACK_BTN_H = 50;

    // ── Entity References ─────────────────────────────────────────
    private final JFrame parentFrame;
    private final Player player;
    private final boolean fromCombat;
    private final BiConsumer<Item, Enemy> onItemSelected;
    private final Runnable onBackPressed;
    private Combat.Battle battle;
    private GamePanel gamePanel;

    // ── UI Components ─────────────────────────────────────────────
    private JPanel characterPanel;
    private JPanel inventoryPanel;
    private JPanel messagePanel;
    private GoldButton backButton;

    // ── Player Animation ──────────────────────────────────────────
    private BufferedImage[] playerIdleFrames = new BufferedImage[5];
    private int currentPlayerFrame = 0;
    private Timer animationTimer;
    private JLabel playerImageLabel;
    private JPanel equippedWeaponPanel;
    private JLabel weaponIconLabel;
    private JLabel weaponNameLabel;
    private JLabel characterLevelLabel;

    // ── Inventory Grid ────────────────────────────────────────────
    private JPanel inventoryGrid;
    private JScrollPane inventoryScrollPane;
    private ArrayList<ItemSlot> itemSlots = new ArrayList<>();
    private JLabel messageLabel;

    // ── Background Image ──────────────────────────────────────────
    private BufferedImage backgroundImage;

    // ─────────────────────────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────────────────────────
    public InventoryPanel(JFrame parentFrame, Player player, boolean fromCombat,
                          BiConsumer<Item, Enemy> onItemSelected, Runnable onBackPressed) {
        this.parentFrame = parentFrame;
        this.player = player;
        this.fromCombat = fromCombat;
        this.onItemSelected = onItemSelected;
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

        refreshInventory();
        updateEquippedWeaponDisplay();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.out.println("ESC pressed - closing inventory");
                    if (animationTimer != null) {
                        animationTimer.cancel();
                        animationTimer = null;
                    }
                    if (titleAnimTimer != null) {
                        titleAnimTimer.stop();
                    }
                    if (onBackPressed != null) {
                        onBackPressed.run();
                    }
                }
                if (!fromCombat && e.getKeyCode() == KeyEvent.VK_I) {
                    System.out.println("I pressed - closing inventory (exploration mode)");
                    if (animationTimer != null) {
                        animationTimer.cancel();
                        animationTimer = null;
                    }
                    if (titleAnimTimer != null) {
                        titleAnimTimer.stop();
                    }
                    if (onBackPressed != null) {
                        onBackPressed.run();
                    }
                }
            }
        });
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

    public void setBattle(Combat.Battle battle) {
        this.battle = battle;
    }

    // ── Title Methods ─────────────────────────────────────────────
    private void loadTitleFont() {
        Font base = null;
        for (String n : new String[]{
                "RINGM___.TTF","RingbearerMedium.ttf","Ringbearer Medium.ttf",
                "ringbearer medium.ttf","Ringbearer.ttf","ringbearer.ttf"}) {
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
        String text = "Inventory";
        g2.setFont(fontTitle);
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = fontTitle.createGlyphVector(frc, text);
        Rectangle2D vis = gv.getVisualBounds();
        int tw = (int) vis.getWidth();
        int tx = (WIDTH - tw) / 2 - (int) vis.getX();
        int ty = (int)(60 + floatY);

        g2.setColor(new Color(0, 0, 0, 185));
        g2.drawString(text, tx + 4, ty + 6);
        g2.setColor(new Color(0, 0, 0, 75));
        g2.drawString(text, tx + 8, ty + 11);

        g2.setPaint(new LinearGradientPaint(
                tx, ty - (int)vis.getHeight(), tx, ty + 8,
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
        g2.fill(new Rectangle2D.Float(bandX, ty - (int)vis.getHeight() - 6, bandW, (int)vis.getHeight() + 18));
        g2.setClip(savedClip);
    }

    // ── Weapon Management Methods ─────────────────────────────────
    private void updateEquippedWeaponDisplay() {
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
            weaponNameLabel.setToolTipText("ATK: +" + (int)equipped.getAttack());
        } else {
            weaponIconLabel.setIcon(null);
            weaponNameLabel.setText("No weapon equipped");
            weaponNameLabel.setToolTipText("Equip a weapon to increase ATK");
        }
        repaint();
    }

    private void equipWeapon(Weapon weapon) {
        if (!player.canEquipWeapon(weapon)) {
            String className = player instanceof Swordsman ? "Swordsman" :
                    (player instanceof Ranger ? "Ranger" : "Mage");
            showParchmentDialog("Cannot Equip", "This weapon cannot be equipped by " + className + "!", "OK", null, null);
            return;
        }

        String confirmMessage = "Equip " + weapon.getName() + "?\nATK: +" + (int)weapon.getAttack();
        showParchmentDialog("Equip Weapon", confirmMessage, "Yes", "No", () -> {
            Weapon currentWeapon = player.getWeapon();
            if (currentWeapon != null) {
                player.getInventory().addItem(currentWeapon, 1);
            }
            player.getInventory().removeItem(weapon, 1);
            player.setWeapon(weapon);
            updateEquippedWeaponDisplay();
            refreshInventory();
            updateStatsPanel();
            messageLabel.setText("Equipped " + weapon.getName() + "! ATK increased by " + (int)weapon.getAttack());
        });
    }

    private void unequipWeapon() {
        Weapon currentWeapon = player.getWeapon();
        if (currentWeapon == null) return;

        showParchmentDialog("Unequip Weapon", "Unequip " + currentWeapon.getName() + "?", "Yes", "No", () -> {
            player.getInventory().addItem(currentWeapon, 1);
            player.setWeapon(null);
            updateEquippedWeaponDisplay();
            refreshInventory();
            updateStatsPanel();
            messageLabel.setText("Unequipped " + currentWeapon.getName());
        });
    }

    // ── Helper Methods ─────────────────────────────────────────────
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
                                new Color(148, 108, 44), new Color(192, 152, 78),
                                new Color(220, 186, 118), new Color(238, 212, 152),
                                new Color(220, 186, 118), new Color(192, 152, 78),
                                new Color(148, 108, 44)
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

        JLabel messageLabel = new JLabel("<html><center>" + message + "</center></html>");
        messageLabel.setFont(FONT_DIALOG);
        messageLabel.setForeground(TEXT_DARK);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(20, 30, 360, 80);
        contentPane.add(messageLabel);

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
            btn2.addActionListener(e -> { playClickSFX(); dialog.dispose(); });
            contentPane.add(btn2);
        }

        dialog.setVisible(true);
    }

    private boolean isDebuffItem(Item item) {
        String name = item.getName();
        return name.contains("Dulling") || name.contains("Softening") ||
                name.contains("Clumsiness") || name.contains("Blinding");
    }

    private boolean isHealingItem(Item item) {
        return item instanceof LesserHealing || item instanceof Healing || item instanceof GreaterHealing;
    }

    private boolean isBuffItem(Item item) {
        return item instanceof LesserPower || item instanceof Power || item instanceof GreaterPower ||
                item instanceof LesserHardening || item instanceof Hardening || item instanceof GreaterHardening;
    }

    private boolean isWeaponItem(Item item) {
        return item instanceof Weapon;
    }

    private void useItemInExploration(Item item) {
        if (isWeaponItem(item)) {
            equipWeapon((Weapon) item);
        }
        else if (isHealingItem(item)) {
            if (player.getHp() >= player.getMaxHp()) {
                showParchmentDialog("Cannot Use", "Your health is already full! You don't need to use a " + item.getName() + ".", "OK", null, null);
                return;
            }
            String confirmMessage = "Use " + item.getName() + " on yourself?\n" + item.getDescription();
            showParchmentDialog("Use Item", confirmMessage, "Yes", "No", () -> {
                item.useItem(player);
                player.getInventory().removeItem(item, 1);
                messageLabel.setText(item.getUseMessage());
                refreshInventory();
                updateStatsPanel();
                System.out.println("Used " + item.getName() + " in exploration. Healed " +
                        String.format("%.1f", player.getHp()) + "/" + player.getMaxHp() + " HP");
            });
        }
        else if (isBuffItem(item)) {
            showParchmentDialog("Cannot Use", "Cannot use " + item.getName() + " outside of battle!", "OK", null, null);
        }
        else if (isDebuffItem(item)) {
            showParchmentDialog("Cannot Use", "Cannot use " + item.getName() + " outside of battle!", "OK", null, null);
        }
        else {
            showParchmentDialog("Cannot Use", "Cannot use " + item.getName() + " outside of battle!", "OK", null, null);
        }
    }

    // ── Setup Methods ─────────────────────────────────────────────
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
                playerIdleFrames[i] = ImageIO.read(getClass().getResourceAsStream("/" + className + "/" + className + "_idle/idle_right" + (i + 1) + ".png"));
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
                    repaint();
                });
            }
        }, 0, 120);
    }

    private void initUI() {
        characterPanel = createParchmentPanel(CHAR_PANEL_X, CHAR_PANEL_Y, CHAR_PANEL_W, CHAR_PANEL_H);
        add(characterPanel);
        setupCharacterPanel();

        inventoryPanel = createParchmentPanel(INV_PANEL_X, INV_PANEL_Y, INV_PANEL_W, INV_PANEL_H);
        add(inventoryPanel);
        setupInventoryPanel();

        messagePanel = createParchmentPanel(MSG_PANEL_X, MSG_PANEL_Y, MSG_PANEL_W, MSG_PANEL_H);
        add(messagePanel);
        setupMessagePanel();

        backButton = new GoldButton("← Back");
        backButton.setBounds(BACK_BTN_X, BACK_BTN_Y, BACK_BTN_W, BACK_BTN_H);
        backButton.addActionListener(e -> {
            playClickSFX();
            if (animationTimer != null) {
                animationTimer.cancel();
                animationTimer = null;
            }
            if (titleAnimTimer != null) {
                titleAnimTimer.stop();
            }
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
                                new Color(148, 108, 44), new Color(192, 152, 78),
                                new Color(220, 186, 118), new Color(238, 212, 152),
                                new Color(220, 186, 118), new Color(192, 152, 78),
                                new Color(148, 108, 44)
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

    private void setupCharacterPanel() {
        // Left side: Player image
        playerImageLabel = new JLabel();
        if (playerIdleFrames[0] != null) {
            Image scaled = playerIdleFrames[0].getScaledInstance(135, 135, Image.SCALE_SMOOTH);
            playerImageLabel.setIcon(new ImageIcon(scaled));
        }
        playerImageLabel.setBounds(15, 20, 135, 135);
        playerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        characterPanel.add(playerImageLabel);

        // Right side: Equipped weapon display
        equippedWeaponPanel = new JPanel() {
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
        equippedWeaponPanel.setBounds(160, 20, 120, 150);
        equippedWeaponPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!fromCombat && player.getWeapon() != null) {
                    playClickSFX();
                    unequipWeapon();
                }
            }
        });
        characterPanel.add(equippedWeaponPanel);

        // Weapon icon
        weaponIconLabel = new JLabel();
        weaponIconLabel.setBounds(36, 20, 48, 48);
        weaponIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        equippedWeaponPanel.add(weaponIconLabel);

        // Weapon name label
        weaponNameLabel = new JLabel("No weapon equipped");
        weaponNameLabel.setFont(new Font("Serif", Font.BOLD, 11));
        weaponNameLabel.setForeground(TEXT_GOLD);
        weaponNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        weaponNameLabel.setBounds(10, 80, 100, 40);
        equippedWeaponPanel.add(weaponNameLabel);

        // Unequip hint
        JLabel unequipHint = new JLabel("(click to unequip)");
        unequipHint.setFont(new Font("Serif", Font.ITALIC, 9));
        unequipHint.setForeground(new Color(200, 200, 150));
        unequipHint.setHorizontalAlignment(SwingConstants.CENTER);
        unequipHint.setBounds(10, 120, 100, 20);
        equippedWeaponPanel.add(unequipHint);

        // ADD THIS: Level label
        int level = player.getLevel();
        if (level < 1) level = 1;
        characterLevelLabel = new JLabel("Lv. " + level, SwingConstants.CENTER);
        characterLevelLabel.setFont(FONT_STAT);
        characterLevelLabel.setForeground(TEXT_BROWN);
        characterLevelLabel.setBounds(-40, 145, 260, 24);
        characterLevelLabel.setOpaque(false);
        characterPanel.add(characterLevelLabel);

        // Stats Box Panel (semi-transparent dark with gold border)
        JPanel statsBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 150));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.setColor(TEXT_GOLD);
                g2.setStroke(new BasicStroke(2.0f));
                g2.draw(new RoundRectangle2D.Float(2, 2, getWidth() - 4, getHeight() - 4, 10, 10));
                g2.dispose();
            }
        };
        statsBox.setLayout(null);
        statsBox.setOpaque(false);
        statsBox.setBounds(20, 180, 260, 160);
        characterPanel.add(statsBox);

        int lineHeight = 23;
        int startY = 12;

        addStatLine(statsBox, "HP:", (int) player.getHp() + " / " + (int) player.getMaxHp(), STAT_HP, 10, startY);
        addStatLine(statsBox, "ATK:", (int) player.getAttack() + " / " + (int) player.getMaxAttack(), STAT_ATK, 10, startY + lineHeight);
        addStatLine(statsBox, "DEF:", (int) player.getDefense() + " / " + (int) player.getMaxDefense(), STAT_DEF, 10, startY + lineHeight * 2);
        addStatLine(statsBox, "SPD:", String.valueOf((int) player.getSpeed()), STAT_SPD, 10, startY + lineHeight * 3);
        addStatLine(statsBox, "ACC:", String.format("%.0f", player.getAccuracy() * 100) + "%", STAT_ACC, 10, startY + lineHeight * 4);
        addStatLine(statsBox, "EXP:", player.getExperience() + " / " + player.getExpNeeded(), STAT_EXP, 10, startY + lineHeight * 5);

        ArrayList<Move> moves = player.getMoves();
        int btnW = 135, btnH = 40;
        int gapX = 140, gapY = 52;
        int startYButtons = 350;
        for (int i = 0; i < moves.size() && i < 4; i++) {
            final Move move = moves.get(i);
            GoldButton moveBtn = new GoldButton(move.getName());
            int row = i / 2;
            int col = i % 2;
            int x = 12 + col * gapX;
            int y = startYButtons + row * gapY;
            moveBtn.setBounds(x, y, btnW, btnH);
            moveBtn.addActionListener(e -> {
                playClickSFX();
                if (!fromCombat) {
                    messageLabel.setText(move.getName() + ": " + move.getDescription());
                }
            });
            moveBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    messageLabel.setText(move.getName() + ": " + move.getDescription());
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    messageLabel.setText("Hover over an item or move for info");
                }
            });
            characterPanel.add(moveBtn);
        }
    }

    private void addStatLine(JPanel panel, String label, String value, Color labelColor, int x, int y) {
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(FONT_STAT);
        labelComp.setForeground(labelColor);
        labelComp.setBounds(x, y, 55, 20);
        panel.add(labelComp);

        JLabel valueComp = new JLabel(value);
        valueComp.setFont(FONT_STAT);
        valueComp.setForeground(STAT_VALUE);
        valueComp.setBounds(x + 60, y, 185, 20);
        panel.add(valueComp);
    }

    private void updateStatsPanel() {
        for (Component comp : characterPanel.getComponents()) {
            if (comp instanceof JPanel && ((JPanel) comp).getLayout() == null &&
                    comp.getBounds().x == 20 && comp.getBounds().y == 180) {
                JPanel statsBox = (JPanel) comp;
                Component[] components = statsBox.getComponents();
                for (int i = 0; i < components.length; i += 2) {
                    if (i + 1 < components.length && components[i] instanceof JLabel && components[i + 1] instanceof JLabel) {
                        String labelText = ((JLabel) components[i]).getText();
                        JLabel valueLabel = (JLabel) components[i + 1];
                        switch (labelText) {
                            case "HP:":
                                valueLabel.setText((int) player.getHp() + " / " + (int) player.getMaxHp());
                                break;
                            case "ATK:":
                                valueLabel.setText((int) player.getAttack() + " / " + (int) player.getMaxAttack());
                                break;
                            case "DEF:":
                                valueLabel.setText((int) player.getDefense() + " / " + (int) player.getMaxDefense());
                                break;
                            case "SPD:":
                                valueLabel.setText(String.valueOf((int) player.getSpeed()));
                                break;
                            case "ACC:":
                                valueLabel.setText(String.format("%.0f", player.getAccuracy() * 100) + "%");
                                break;
                            case "EXP:":
                                valueLabel.setText(player.getExperience() + " / " + player.getExpNeeded());
                                break;
                        }
                    }
                }
                break;
            }
        }

        if (characterLevelLabel != null) {
            characterLevelLabel.setText("Lv. " + player.getLevel());
        }
    }

    private void setupInventoryPanel() {
        inventoryGrid = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Container parent = SwingUtilities.getAncestorOfClass(JViewport.class, this);
                if (parent != null) d.width = parent.getWidth();
                return d;
            }
        };
        inventoryGrid.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        inventoryGrid.setOpaque(false);

        inventoryScrollPane = new JScrollPane(inventoryGrid);
        inventoryScrollPane.setOpaque(false);
        inventoryScrollPane.getViewport().setOpaque(false);
        inventoryScrollPane.setBorder(BorderFactory.createEmptyBorder());
        inventoryScrollPane.setBounds(20, 20, INV_PANEL_W - 50, INV_PANEL_H - 50);
        inventoryScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        inventoryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JScrollBar verticalBar = inventoryScrollPane.getVerticalScrollBar();
        verticalBar.setUI(new ParchmentScrollBarUI());
        verticalBar.setUnitIncrement(70);
        verticalBar.setBlockIncrement(210);

        inventoryPanel.add(inventoryScrollPane);
        refreshInventory();
    }

    public void refreshInventory() {
        if (inventoryGrid == null) return;

        inventoryGrid.removeAll();
        itemSlots.clear();

        ArrayList<Item> items = player.getInventory().getItems();

        if (items.isEmpty()) {
            JLabel emptyLabel = new JLabel("No items in inventory");
            emptyLabel.setFont(FONT_NAME);
            emptyLabel.setForeground(TEXT_GOLD);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setPreferredSize(new Dimension(INV_PANEL_W - 70, 50));
            inventoryGrid.add(emptyLabel);
        } else {
            for (Item item : items) {
                int quantity = player.getInventory().getQuantity(item);
                if (quantity > 0) {
                    ItemSlot slot = new ItemSlot();
                    slot.setItem(item, quantity);
                    slot.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            slot.setHovered(true);
                            String displayText = item.getName() + " x" + quantity;
                            if (item instanceof Weapon) {
                                displayText += " - ATK: +" + (int)((Weapon)item).getAttack();
                            } else {
                                displayText += " - " + item.getDescription();
                            }
                            messageLabel.setText(displayText);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            slot.setHovered(false);
                            messageLabel.setText("Hover over an item or move for info");
                        }

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            playClickSFX();
                            if (fromCombat) {
                                if (item instanceof Weapon) {
                                    showParchmentDialog("Cannot Equip", "Cannot equip or unequip weapons during combat!", "OK", null, null);
                                    return;
                                }

                                if (isHealingItem(item)) {
                                    if (player.getHp() >= player.getMaxHp()) {
                                        showParchmentDialog("Cannot Use", "Your health is already full! You don't need to use a " + item.getName() + ".", "OK", null, null);
                                        return;
                                    }
                                }

                                boolean isDebuff = isDebuffItem(item);
                                String confirmMessage = isDebuff ?
                                        "Use " + item.getName() + " on an enemy?\n" + item.getDescription() :
                                        "Use " + item.getName() + " on yourself?\n" + item.getDescription();

                                showParchmentDialog("Use Item", confirmMessage, "Yes", "No", () -> {
                                    if (onItemSelected != null) {
                                        onItemSelected.accept(item, null);
                                    }
                                });
                            } else {
                                useItemInExploration(item);
                            }
                        }
                    });
                    inventoryGrid.add(slot);
                    itemSlots.add(slot);
                }
            }
        }

        inventoryGrid.revalidate();
        inventoryGrid.repaint();
    }

    private void setupMessagePanel() {
        messageLabel = new JLabel("Hover over an item or move for info");
        messageLabel.setFont(FONT_NAME);
        messageLabel.setForeground(TEXT_DARK);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(20, 25, MSG_PANEL_W - 40, 50);
        messagePanel.add(messageLabel);
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

    // ─────────────────────────────────────────────────────────────
    //  Inner Classes
    // ─────────────────────────────────────────────────────────────

    private static class ItemSlot extends JPanel {
        private Item item;
        private int quantity;
        private BufferedImage icon;
        private boolean hovered = false;

        public ItemSlot() {
            setPreferredSize(new Dimension(70, 70));
            setBackground(new Color(60, 40, 20, 180));
            setBorder(BorderFactory.createLineBorder(new Color(80, 50, 20), 2));
        }

        public void setItem(Item item, int qty) {
            this.item = item;
            this.quantity = qty;
            this.icon = item.getImage();
            repaint();
        }

        public Item getItem() { return item; }
        public int getQuantity() { return quantity; }
        public void setHovered(boolean hovered) { this.hovered = hovered; repaint(); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

            if (icon != null) {
                g2.drawImage(icon, 11, 11, 48, 48, null);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Monospaced", Font.BOLD, 12));
                g2.drawString("x" + quantity, 45, 60);
                if (hovered) {
                    g2.setColor(HOVER_GOLD);
                    g2.setStroke(new BasicStroke(3.0f));
                    g2.drawRect(2, 2, getWidth() - 5, getHeight() - 5);
                }
            } else {
                g2.setColor(new Color(100, 70, 40));
                g2.fillRect(19, 19, 32, 32);
                g2.setColor(Color.GRAY);
                g2.setFont(new Font("Serif", Font.BOLD, 20));
                g2.drawString("?", 33, 42);
            }
            g2.dispose();
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
                @Override public void mouseEntered(MouseEvent e) { if (isEnabled()) { hovered = true; repaint(); } }
                @Override public void mouseExited(MouseEvent e) { hovered = false; repaint(); }
                @Override public void mousePressed(MouseEvent e) { if (isEnabled()) { pressed = true; repaint(); } }
                @Override public void mouseReleased(MouseEvent e) { pressed = false; repaint(); }
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

    private class ParchmentScrollBarUI extends BasicScrollBarUI {

        private boolean thumbHovered = false;
        private boolean thumbPressed = false;

        @Override
        protected void installListeners() {
            super.installListeners();
            if (scrollbar != null) {
                scrollbar.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (isThumb(e.getPoint())) {
                            thumbHovered = true;
                            scrollbar.repaint();
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        thumbHovered = false;
                        scrollbar.repaint();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (isThumb(e.getPoint())) {
                            thumbPressed = true;
                            scrollbar.repaint();
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        thumbPressed = false;
                        scrollbar.repaint();
                    }
                });

                scrollbar.addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        boolean wasHovered = thumbHovered;
                        thumbHovered = isThumb(e.getPoint());
                        if (wasHovered != thumbHovered) {
                            scrollbar.repaint();
                        }
                    }
                });
            }
        }

        private boolean isThumb(Point p) {
            Rectangle thumbRect = getThumbBounds();
            return thumbRect != null && thumbRect.contains(p);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createEmptyButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createEmptyButton();
        }

        private JButton createEmptyButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            return button;
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setPaint(new LinearGradientPaint(
                    trackBounds.x, trackBounds.y,
                    trackBounds.x, trackBounds.y + trackBounds.height,
                    new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                    new Color[]{
                            PARCH_TOP, PARCH_TAN, PARCH_WARM, PARCH_CENTRE,
                            PARCH_WARM, PARCH_TAN, PARCH_TOP
                    }
            ));
            g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);

            g2.setColor(new Color(60, 30, 5, 30));
            g2.setStroke(new BasicStroke(0.5f));
            for (int y = trackBounds.y; y < trackBounds.y + trackBounds.height; y += 3) {
                g2.drawLine(trackBounds.x + 2, y, trackBounds.x + trackBounds.width - 4, y);
            }

            g2.setColor(new Color(80, 40, 10, 20));
            Random random = new Random(trackBounds.hashCode());
            for (int i = 0; i < 5; i++) {
                int spotY = trackBounds.y + random.nextInt(trackBounds.height - 8);
                int spotSize = 3 + random.nextInt(5);
                g2.fillOval(trackBounds.x + 2, spotY, spotSize, spotSize);
            }

            g2.setColor(PARCH_BORDER);
            g2.setStroke(new BasicStroke(2.0f));
            g2.drawRect(trackBounds.x + 1, trackBounds.y, trackBounds.width - 2, trackBounds.height - 1);

            g2.dispose();
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                return;
            }

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = thumbBounds.width;
            int h = Math.max(thumbBounds.height, 40);
            int y = thumbBounds.y;

            if (thumbBounds.height < 40) {
                y = thumbBounds.y - (40 - thumbBounds.height) / 2;
                y = Math.max(0, Math.min(y, c.getHeight() - 40));
            }

            int cSize = Math.min(w, h) / 6;
            if (cSize < 4) cSize = 4;

            Polygon oct = makeOctagon(thumbBounds.x, y, w, h, cSize);

            Color fillColor;
            if (!scrollbar.isEnabled()) {
                fillColor = new Color(160, 140, 100);
            } else if (thumbPressed) {
                fillColor = BTN_GOLD_PRESS;
            } else if (thumbHovered) {
                fillColor = BTN_GOLD_HOVER;
            } else {
                fillColor = BTN_GOLD_NORMAL;
            }

            g2.setColor(fillColor);
            g2.fill(oct);

            if (thumbHovered && !thumbPressed) {
                g2.setColor(new Color(255, 255, 255, 40));
                Polygon topOct = makeOctagon(thumbBounds.x, y, w, h / 2, cSize);
                g2.fill(topOct);
            }

            g2.setColor(BTN_BORDER_CLR);
            g2.setStroke(new BasicStroke(2.0f));
            g2.draw(oct);

            g2.setColor(new Color(252, 218, 72, 100));
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawLine(thumbBounds.x + 4, y + h / 2, thumbBounds.x + w - 4, y + h / 2);

            g2.dispose();
        }

        private Polygon makeOctagon(int x, int y, int w, int h, int c) {
            return new Polygon(
                    new int[]{x + c, x + w - c, x + w, x + w, x + w - c, x + c, x, x},
                    new int[]{y, y, y + c, y + h - c, y + h, y + h, y + h - c, y + c},
                    8
            );
        }

        @Override
        protected void setThumbBounds(int x, int y, int width, int height) {
            super.setThumbBounds(x, y, width, Math.max(height, 40));
        }

        @Override
        protected Dimension getMinimumThumbSize() {
            return new Dimension(16, 40);
        }
    }
}