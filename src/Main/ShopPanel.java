package Main;

import Entities.NPCs.Shopkeeper;
import Entities.Characters.Player;
import Entities.Characters.Swordsman;
import Entities.Characters.Ranger;
import Entities.Characters.Mage;
import Entities.Entity;
import Items.Item;
import Items.Weapons.Weapon;

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
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShopPanel extends JPanel {

    // Screen Dimensions
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    // Palette (matching InventoryPanel exactly)
    private static final Color BG_COLOR = new Color(160, 120, 80);
    private static final Color PARCH_TOP = new Color(148, 108, 44);
    private static final Color PARCH_TAN = new Color(192, 152, 78);
    private static final Color PARCH_WARM = new Color(220, 186, 118);
    private static final Color PARCH_CENTRE = new Color(238, 212, 152);
    private static final Color PARCH_BORDER = new Color(80, 38, 2, 230);
    private static final Color TEXT_DARK = new Color(60, 30, 5);
    private static final Color TEXT_GOLD = new Color(252, 218, 72);
    private static final Color TEXT_GREEN = new Color(79, 163, 32);
    private static final Color TEXT_LIGHT_GREEN = new Color(142, 220, 91);
    private static final Color HOVER_GOLD = new Color(252, 218, 72);
    private static final Color STAT_HP = new Color(220, 80, 80);

    // Gold Button Colors
    private static final Color BTN_GOLD_NORMAL = new Color(238, 190, 28);
    private static final Color BTN_GOLD_HOVER = new Color(250, 222, 62);
    private static final Color BTN_GOLD_PRESS = new Color(178, 108, 0);
    private static final Color BTN_BORDER_CLR = new Color(82, 38, 0, 215);
    private static final Color BTN_TEXT_DARK = new Color(42, 12, 0);

    // Fonts
    private static final Font FONT_TITLE = new Font("Serif", Font.BOLD, 18);
    private static final Font FONT_STAT = new Font("Monospaced", Font.BOLD, 13);
    private static final Font FONT_BTN = new Font("Serif", Font.BOLD, 16);
    private static final Font FONT_NAME = new Font("Serif", Font.BOLD, 20);
    private static final Font FONT_DIALOG = new Font("Serif", Font.BOLD, 14);

    // Layout Constants
    private static final int PLAYER_BOX_X = 20;
    private static final int PLAYER_BOX_Y = 100;
    private static final int PLAYER_BOX_W = 470;
    private static final int PLAYER_BOX_H = 460;

    private static final int SHOP_BOX_X = 510;
    private static final int SHOP_BOX_Y = 100;
    private static final int SHOP_BOX_W = 494;
    private static final int SHOP_BOX_H = 460;

    private static final int MSG_BOX_X = 20;
    private static final int MSG_BOX_Y = 580;
    private static final int MSG_BOX_W = 984;
    private static final int MSG_BOX_H = 100;

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
    private final Shopkeeper shopkeeper;
    private final Runnable onBackPressed;
    private GamePanel gamePanel;

    // Currency tracking
    private int playerGold = 5000;

    // UI Components
    private JPanel playerBox;
    private JPanel shopBox;
    private JPanel messageBox;
    private GoldButton backButton;
    private JLabel messageLabel;

    // Player Inventory Grid
    private JLabel currencyLabel;
    private JPanel playerGrid;
    private JScrollPane playerScrollPane;
    private ArrayList<ItemSlot> playerItemSlots = new ArrayList<>();

    // Shop Inventory Grid
    private JPanel shopGrid;
    private JScrollPane shopScrollPane;
    private ArrayList<ItemSlot> shopItemSlots = new ArrayList<>();

    // Player Animation
    private BufferedImage[] playerIdleFrames = new BufferedImage[5];
    private int currentPlayerFrame = 0;
    private Timer playerAnimTimer;
    private JLabel playerSpriteLabel;

    // Shopkeeper Animation
    private BufferedImage[] shopkeeperIdleFrames = new BufferedImage[5];
    private int currentShopkeeperFrame = 0;
    private Timer shopkeeperAnimTimer;
    private JLabel shopkeeperSpriteLabel;

    // ── Background GIF ────────────────────────────────────────────
    private ImageIcon backgroundGif;
    private boolean isBukogShop = false;   // <-- NEW: flag for Bukog shop background

    // Constructor
    public ShopPanel(JFrame parentFrame, Player player, Shopkeeper shopkeeper, Runnable onBackPressed) {
        this.parentFrame = parentFrame;
        this.player = player;
        this.shopkeeper = shopkeeper;
        this.onBackPressed = onBackPressed;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        setOpaque(true);
        setFocusable(true);

        shopkeeper.generateShop(player);

        // Detect Bukog shop (case‑insensitive)
        isBukogShop = "Bukog".equalsIgnoreCase(shopkeeper.getName());

        try {
            playerGold = player.getMoney();
        } catch (Exception e) {
            playerGold = 5000;
        }

        loadTitleFont();
        loadBackground();
        loadPlayerAnimations();
        loadShopkeeperAnimations();
        initUI();
        startAnimations();
        startTitleAnimation();

        refreshPlayerShopGrid();
        refreshShopGrid();
        updateCurrencyDisplay();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cleanup();
                    if (onBackPressed != null) {
                        onBackPressed.run();
                    }
                }
            }
        });
    }

    private void cleanup() {
        if (playerAnimTimer != null) {
            playerAnimTimer.cancel();
            playerAnimTimer = null;
        }
        if (shopkeeperAnimTimer != null) {
            shopkeeperAnimTimer.cancel();
            shopkeeperAnimTimer = null;
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

    // Title Methods
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
            // repaint is driven by the GIF ImageObserver — no need to call it here
        });
        titleAnimTimer.start();
    }

    private void paintTitle(Graphics2D g2) {
        String text = "Shop";
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

    // MODIFIED: Load the correct background based on isBukogShop
    private void loadBackground() {
        try {
            String bgFileName = isBukogShop ? "/backgrounds/bukog_bg.gif" : "/backgrounds/shop_bg.gif";
            java.net.URL gifUrl = getClass().getResource(bgFileName);
            if (gifUrl != null) {
                backgroundGif = new ImageIcon(gifUrl);
                backgroundGif.setImageObserver(this);
            }
        } catch (Exception e) {
            backgroundGif = null;
        }
    }

    private void loadPlayerAnimations() {
        String className = getPlayerClassName();
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
            createPlayerPlaceholderFrames();
        }
    }

    private String getPlayerClassName() {
        if (player instanceof Swordsman) return "swordsman";
        if (player instanceof Ranger) return "archer";
        if (player instanceof Mage) return "mage";
        return "swordsman";
    }

    private void createPlayerPlaceholderFrames() {
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
            playerIdleFrames[i] = createPlaceholderImage(frameColor, initial, 67);
        }
    }

    private void loadShopkeeperAnimations() {
        boolean anyFrameLoaded = false;

        String npcName = shopkeeper.getName().toLowerCase();
        for (int i = 0; i < 5; i++) {
            try {
                int frameNum = (i % 2) + 1;
                String path = "/npc/" + npcName + "/" + npcName + "_idle" + frameNum + ".png";
                shopkeeperIdleFrames[i] = ImageIO.read(getClass().getResourceAsStream(path));
                if (shopkeeperIdleFrames[i] != null) anyFrameLoaded = true;
            } catch (Exception e) {
                shopkeeperIdleFrames[i] = null;
            }
        }

        if (!anyFrameLoaded) {
            String className = "archer";
            for (int i = 0; i < 5; i++) {
                try {
                    String path = "/" + className + "/" + className + "_idle/idle_left" + (i + 1) + ".png";
                    shopkeeperIdleFrames[i] = ImageIO.read(getClass().getResourceAsStream(path));
                    if (shopkeeperIdleFrames[i] != null) anyFrameLoaded = true;
                } catch (Exception e) {
                    shopkeeperIdleFrames[i] = null;
                }
            }
        }

        if (!anyFrameLoaded) {
            createShopkeeperPlaceholderFrames();
        }
    }

    private void createShopkeeperPlaceholderFrames() {
        Color baseColor = new Color(139, 119, 101);
        String initial = "S";

        for (int i = 0; i < 5; i++) {
            float brightness = 0.7f + (i * 0.06f);
            Color frameColor = new Color(
                    Math.min(255, (int) (baseColor.getRed() * brightness)),
                    Math.min(255, (int) (baseColor.getGreen() * brightness)),
                    Math.min(255, (int) (baseColor.getBlue() * brightness))
            );
            shopkeeperIdleFrames[i] = createPlaceholderImage(frameColor, initial, 67);
        }
    }

    private BufferedImage createPlaceholderImage(Color color, String initial, int size) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        g2.setColor(color);
        g2.fillRect(0, 0, size, size);

        Font bigFont = new Font("Serif", Font.BOLD, size / 3);
        g2.setFont(bigFont);
        FontMetrics fm = g2.getFontMetrics();
        int lx = (size - fm.stringWidth(initial)) / 2;
        int ly = (size + fm.getAscent() - fm.getDescent()) / 2;

        g2.setColor(new Color(0, 0, 0, 80));
        g2.drawString(initial, lx + 2, ly + 2);
        g2.setColor(Color.WHITE);
        g2.drawString(initial, lx, ly);

        g2.dispose();
        return img;
    }

    private void startAnimations() {
        playerAnimTimer = new Timer();
        playerAnimTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentPlayerFrame = (currentPlayerFrame + 1) % 5;
                SwingUtilities.invokeLater(() -> {
                    if (playerSpriteLabel != null && playerIdleFrames[currentPlayerFrame] != null) {
                        Image scaled = playerIdleFrames[currentPlayerFrame].getScaledInstance(67, 67, Image.SCALE_SMOOTH);
                        playerSpriteLabel.setIcon(new ImageIcon(scaled));
                    }
                });
            }
        }, 0, 120);

        shopkeeperAnimTimer = new Timer();
        shopkeeperAnimTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentShopkeeperFrame = (currentShopkeeperFrame + 1) % 5;
                SwingUtilities.invokeLater(() -> {
                    if (shopkeeperSpriteLabel != null && shopkeeperIdleFrames[currentShopkeeperFrame] != null) {
                        Image scaled = shopkeeperIdleFrames[currentShopkeeperFrame].getScaledInstance(67, 67, Image.SCALE_SMOOTH);
                        shopkeeperSpriteLabel.setIcon(new ImageIcon(scaled));
                    }
                });
            }
        }, 0, 120);
    }

    private void initUI() {
        playerBox = createParchmentPanel(PLAYER_BOX_X, PLAYER_BOX_Y, PLAYER_BOX_W, PLAYER_BOX_H);
        add(playerBox);
        setupPlayerBox();

        shopBox = createParchmentPanel(SHOP_BOX_X, SHOP_BOX_Y, SHOP_BOX_W, SHOP_BOX_H);
        add(shopBox);
        setupShopBox();

        messageBox = createParchmentPanel(MSG_BOX_X, MSG_BOX_Y, MSG_BOX_W, MSG_BOX_H);
        add(messageBox);
        setupMessageBox();

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

    private void setupPlayerBox() {
        playerSpriteLabel = new JLabel();
        if (playerIdleFrames[0] != null) {
            Image scaled = playerIdleFrames[0].getScaledInstance(67, 67, Image.SCALE_SMOOTH);
            playerSpriteLabel.setIcon(new ImageIcon(scaled));
        }
        playerSpriteLabel.setBounds(20, 20, 67, 67);
        playerSpriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerSpriteLabel.setVerticalAlignment(SwingConstants.CENTER);
        playerBox.add(playerSpriteLabel);

        String playerName = player != null ? player.getClass().getSimpleName() : "Player";
        JLabel inventoryLabel = new JLabel(playerName + "'s Inventory");
        inventoryLabel.setFont(FONT_NAME);
        inventoryLabel.setForeground(TEXT_GOLD);
        inventoryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        inventoryLabel.setBounds(105, 40, 320, 30);
        playerBox.add(inventoryLabel);

        currencyLabel = new JLabel("$0", SwingConstants.RIGHT);
        currencyLabel.setFont(FONT_NAME);
        currencyLabel.setForeground(TEXT_LIGHT_GREEN);
        currencyLabel.setBounds(330, 40, 120, 30);
        currencyLabel.setOpaque(false);
        playerBox.add(currencyLabel);

        playerGrid = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                if (getLayout() instanceof FlowLayout) {
                    FlowLayout layout = (FlowLayout) getLayout();
                    int hgap = layout.getHgap();
                    int vgap = layout.getVgap();
                    int targetWidth = 430 - 20;
                    int currentRowWidth = 0;
                    int currentRowHeight = 0;
                    int totalHeight = 0;
                    int componentCount = getComponentCount();
                    for (int i = 0; i < componentCount; i++) {
                        Component comp = getComponent(i);
                        if (!comp.isVisible()) continue;
                        Dimension pref = comp.getPreferredSize();
                        if (currentRowWidth + pref.width + (currentRowWidth > 0 ? hgap : 0) > targetWidth && currentRowWidth > 0) {
                            totalHeight += currentRowHeight + vgap;
                            currentRowWidth = 0;
                            currentRowHeight = 0;
                        }
                        currentRowWidth += pref.width + (currentRowWidth > 0 ? hgap : 0);
                        currentRowHeight = Math.max(currentRowHeight, pref.height);
                    }
                    totalHeight += currentRowHeight;
                    totalHeight += 10;
                    return new Dimension(targetWidth, Math.max(totalHeight, 50));
                }
                return super.getPreferredSize();
            }
        };
        playerGrid.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        playerGrid.setOpaque(false);

        playerScrollPane = new JScrollPane(playerGrid);
        playerScrollPane.setOpaque(false);
        playerScrollPane.getViewport().setOpaque(false);
        playerScrollPane.setBorder(BorderFactory.createEmptyBorder());
        playerScrollPane.setBounds(20, 100, 430, 340);
        playerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        playerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JScrollBar verticalBar = playerScrollPane.getVerticalScrollBar();
        verticalBar.setBackground(new Color(60, 40, 20));
        verticalBar.setForeground(new Color(200, 160, 100));
        verticalBar.setUnitIncrement(85);
        verticalBar.setBlockIncrement(255);

        playerBox.add(playerScrollPane);
    }

    private void setupShopBox() {
        shopkeeperSpriteLabel = new JLabel();
        if (shopkeeperIdleFrames[0] != null) {
            Image scaled = shopkeeperIdleFrames[0].getScaledInstance(67, 67, Image.SCALE_SMOOTH);
            shopkeeperSpriteLabel.setIcon(new ImageIcon(scaled));
        }
        shopkeeperSpriteLabel.setBounds(SHOP_BOX_W - 86, 20, 67, 67);
        shopkeeperSpriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        shopkeeperSpriteLabel.setVerticalAlignment(SwingConstants.CENTER);
        shopBox.add(shopkeeperSpriteLabel);

        String shopName = shopkeeper != null ? shopkeeper.getName() : "Shopkeeper";
        JLabel shopLabel = new JLabel(shopName + "'s Shop");
        shopLabel.setFont(FONT_NAME);
        shopLabel.setForeground(TEXT_GOLD);
        shopLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        shopLabel.setBounds(45, 40, SHOP_BOX_W - 150, 30);
        shopBox.add(shopLabel);

        shopGrid = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                if (getLayout() instanceof FlowLayout) {
                    FlowLayout layout = (FlowLayout) getLayout();
                    int hgap = layout.getHgap();
                    int vgap = layout.getVgap();
                    int targetWidth = 454 - 20;
                    int currentRowWidth = 0;
                    int currentRowHeight = 0;
                    int totalHeight = 0;
                    int componentCount = getComponentCount();
                    for (int i = 0; i < componentCount; i++) {
                        Component comp = getComponent(i);
                        if (!comp.isVisible()) continue;
                        Dimension pref = comp.getPreferredSize();
                        if (currentRowWidth + pref.width + (currentRowWidth > 0 ? hgap : 0) > targetWidth && currentRowWidth > 0) {
                            totalHeight += currentRowHeight + vgap;
                            currentRowWidth = 0;
                            currentRowHeight = 0;
                        }
                        currentRowWidth += pref.width + (currentRowWidth > 0 ? hgap : 0);
                        currentRowHeight = Math.max(currentRowHeight, pref.height);
                    }
                    totalHeight += currentRowHeight;
                    totalHeight += 10;
                    return new Dimension(targetWidth, Math.max(totalHeight, 50));
                }
                return super.getPreferredSize();
            }
        };
        shopGrid.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        shopGrid.setOpaque(false);

        shopScrollPane = new JScrollPane(shopGrid);
        shopScrollPane.setOpaque(false);
        shopScrollPane.getViewport().setOpaque(false);
        shopScrollPane.setBorder(BorderFactory.createEmptyBorder());
        shopScrollPane.setBounds(20, 100, 454, 300);
        shopScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        shopScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JScrollBar verticalBar = shopScrollPane.getVerticalScrollBar();
        verticalBar.setBackground(new Color(60, 40, 20));
        verticalBar.setForeground(new Color(200, 160, 100));
        verticalBar.setUnitIncrement(85);
        verticalBar.setBlockIncrement(255);

        shopBox.add(shopScrollPane);
    }

    private void setupMessageBox() {
        messageLabel = new JLabel("Welcome to the shop! Click on items to buy or sell.");
        messageLabel.setFont(FONT_NAME);
        messageLabel.setForeground(TEXT_DARK);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(20, 25, MSG_BOX_W - 40, 50);
        messageBox.add(messageLabel);
    }

    private void updateCurrencyDisplay() {
        if (currencyLabel != null) {
            try {
                java.lang.reflect.Method getMoney = player.getClass().getMethod("getMoney");
                playerGold = (int) getMoney.invoke(player);
            } catch (Exception e) {
                // Keep using local playerGold
            }
            currencyLabel.setText("$" + playerGold);
        }
    }

    private void showSellConfirm(Item item, int defaultQty) {
        String message = "Sell " + item.getName() + "?";
        showParchmentDialog("Sell Item", message, "Yes", "No", () -> {
            if (defaultQty > 1) {
                showSellQuantityDialog(item, defaultQty);
            } else {
                executeSell(item, 1);
            }
        });
    }

    private void showSellQuantityDialog(Item item, int maxQty) {
        JDialog dialog = new JDialog(parentFrame, "Sell Quantity", true);
        dialog.setUndecorated(true);
        dialog.setSize(400, 220);
        dialog.setLocationRelativeTo(parentFrame);

        final Point[] dragPoint = {null};
        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(60, 40, 20));
        titleBar.setBounds(0, 0, 400, 25);
        titleBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { dragPoint[0] = e.getPoint(); }
        });
        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragPoint[0] != null) {
                    Point p = dialog.getLocation();
                    dialog.setLocation(p.x + e.getX() - dragPoint[0].x, p.y + e.getY() - dragPoint[0].y);
                }
            }
        });

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new LinearGradientPaint(0, 0, 0, getHeight(),
                        new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                        new Color[]{PARCH_TOP, PARCH_TAN, PARCH_WARM, PARCH_CENTRE, PARCH_WARM, PARCH_TAN, PARCH_TOP}));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(PARCH_BORDER);
                g2.setStroke(new BasicStroke(3.0f));
                g2.drawRect(5, 5, getWidth() - 10, getHeight() - 10);
                g2.dispose();
            }
        };
        contentPane.setLayout(null);
        contentPane.setOpaque(false);
        contentPane.add(titleBar);
        dialog.setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Sell " + item.getName() + "?");
        titleLabel.setFont(FONT_DIALOG);
        titleLabel.setForeground(TEXT_DARK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(20, 30, 360, 25);
        contentPane.add(titleLabel);

        JTextField qtyField = new JTextField("1");
        qtyField.setFont(FONT_STAT);
        qtyField.setHorizontalAlignment(SwingConstants.CENTER);
        qtyField.setBounds(98, 85, 60, 40);
        qtyField.setBackground(new Color(255, 255, 220));
        qtyField.setForeground(TEXT_DARK);
        qtyField.setBorder(BorderFactory.createLineBorder(PARCH_BORDER, 1));
        contentPane.add(qtyField);
        SwingUtilities.invokeLater(() -> qtyField.selectAll());

        GoldButton upBtn = new GoldButton("▲");
        upBtn.setFont(new Font("Arial", Font.BOLD, 14));
        upBtn.setMargin(new Insets(0, 0, 0, 0));
        upBtn.setBounds(166, 85, 40, 40);
        upBtn.setContentAreaFilled(false);
        upBtn.addActionListener(e -> {
            playClickSFX();
            try {
                int val = Integer.parseInt(qtyField.getText());
                if (val < maxQty) { val++; qtyField.setText(String.valueOf(val)); }
            } catch (NumberFormatException ex) { qtyField.setText("1"); }
        });
        contentPane.add(upBtn);

        GoldButton downBtn = new GoldButton("▼");
        downBtn.setFont(new Font("Arial", Font.BOLD, 14));
        downBtn.setMargin(new Insets(0, 0, 0, 0));
        downBtn.setBounds(214, 85, 40, 40);
        downBtn.setContentAreaFilled(false);
        downBtn.addActionListener(e -> {
            playClickSFX();
            try {
                int val = Integer.parseInt(qtyField.getText());
                if (val > 1) { val--; qtyField.setText(String.valueOf(val)); }
            } catch (NumberFormatException ex) { qtyField.setText("1"); }
        });
        contentPane.add(downBtn);

        GoldButton maxBtn = new GoldButton("MAX");
        maxBtn.setFont(new Font("Arial", Font.BOLD, 11));
        maxBtn.setMargin(new Insets(0, 0, 0, 0));
        maxBtn.setBounds(262, 85, 40, 40);
        maxBtn.setContentAreaFilled(false);
        maxBtn.addActionListener(e -> { playClickSFX(); qtyField.setText(String.valueOf(maxQty)); });
        contentPane.add(maxBtn);

        JLabel maxLabel = new JLabel("(Max: " + maxQty + ")");
        maxLabel.setFont(new Font("SansSerif", Font.ITALIC, 10));
        maxLabel.setForeground(new Color(100, 70, 40));
        maxLabel.setBounds(110, 130, 100, 15);
        contentPane.add(maxLabel);

        JLabel earningsLabel = new JLabel();
        earningsLabel.setFont(FONT_STAT);
        earningsLabel.setForeground(TEXT_GREEN);
        earningsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        earningsLabel.setBounds(45, 125, 360, 20);
        contentPane.add(earningsLabel);

        Runnable updateEarnings = () -> {
            try {
                int qty = Integer.parseInt(qtyField.getText());
                if (qty < 1) qty = 1;
                if (qty > maxQty) qty = maxQty;
                earningsLabel.setText("Earnings: $" + (item.getSellingPrice() * qty));
            } catch (NumberFormatException ex) { earningsLabel.setText("Earnings: $0"); }
        };

        qtyField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { updateEarnings.run(); }
            @Override public void removeUpdate(DocumentEvent e) { updateEarnings.run(); }
            @Override public void changedUpdate(DocumentEvent e) { updateEarnings.run(); }
        });

        qtyField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    int val = Integer.parseInt(qtyField.getText());
                    if (val < 1) val = 1;
                    if (val > maxQty) val = maxQty;
                    qtyField.setText(String.valueOf(val));
                } catch (NumberFormatException ex) { qtyField.setText("1"); }
                updateEarnings.run();
            }
        });

        updateEarnings.run();

        GoldButton sellBtn = new GoldButton("Sell");
        sellBtn.setBounds(80, 155, 100, 35);
        sellBtn.addActionListener(e -> {
            playClickSFX();
            try {
                int qty = Integer.parseInt(qtyField.getText());
                if (qty < 1) qty = 1;
                if (qty > maxQty) qty = maxQty;
                dialog.dispose();
                executeSell(item, qty);
            } catch (NumberFormatException ex) { dialog.dispose(); executeSell(item, 1); }
        });
        contentPane.add(sellBtn);

        GoldButton cancelBtn = new GoldButton("Cancel");
        cancelBtn.setBounds(220, 155, 100, 35);
        cancelBtn.addActionListener(e -> { playClickSFX(); dialog.dispose(); });
        contentPane.add(cancelBtn);

        dialog.setVisible(true);
    }

    private void handleWeaponBuy(Item item) {
        for (Item inv : player.getInventory().getItems()) {
            if (inv.getName().equals(item.getName())) {
                showParchmentDialog("Already Owned",
                        "You already have " + item.getName() + ".",
                        "OK", null, null);
                return;
            }
        }
        if (playerGold < item.getPrice()) {
            showParchmentDialog("Insufficient Gold",
                    "You need $" + item.getPrice() + " but only have $" + playerGold + ".",
                    "OK", null, null);
            return;
        }
        showParchmentDialog("Buy Weapon",
                "Buy " + item.getName() + " for $" + item.getPrice() + "?",
                "Yes", "No", () -> executeBuy(item, 1));
    }

    /**
     * Shows quantity selection dialog for buying items.
     */
    private void showBuyQuantityDialog(Item item) {
        JDialog dialog = new JDialog(parentFrame, "Buy Quantity", true);
        dialog.setUndecorated(true);
        dialog.setSize(400, 220);
        dialog.setLocationRelativeTo(parentFrame);

        int price = item.getPrice();
        int maxQty = (price > 0) ? Math.min(99, playerGold / price) : 99;
        if (maxQty < 1) maxQty = 1;

        final Point[] dragPoint = {null};
        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(60, 40, 20));
        titleBar.setBounds(0, 0, 400, 25);
        titleBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { dragPoint[0] = e.getPoint(); }
        });
        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragPoint[0] != null) {
                    Point p = dialog.getLocation();
                    dialog.setLocation(p.x + e.getX() - dragPoint[0].x, p.y + e.getY() - dragPoint[0].y);
                }
            }
        });

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new LinearGradientPaint(0, 0, 0, getHeight(),
                        new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                        new Color[]{PARCH_TOP, PARCH_TAN, PARCH_WARM, PARCH_CENTRE, PARCH_WARM, PARCH_TAN, PARCH_TOP}));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(PARCH_BORDER);
                g2.setStroke(new BasicStroke(3.0f));
                g2.drawRect(5, 5, getWidth() - 10, getHeight() - 10);
                g2.dispose();
            }
        };
        contentPane.setLayout(null);
        contentPane.setOpaque(false);
        contentPane.add(titleBar);
        dialog.setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Buy " + item.getName() + "?");
        titleLabel.setFont(FONT_DIALOG);
        titleLabel.setForeground(TEXT_DARK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(20, 30, 360, 25);
        contentPane.add(titleLabel);

        JTextField qtyField = new JTextField("1");
        qtyField.setFont(FONT_STAT);
        qtyField.setHorizontalAlignment(SwingConstants.CENTER);
        qtyField.setBounds(98, 85, 60, 40);
        qtyField.setBackground(new Color(255, 255, 220));
        qtyField.setForeground(TEXT_DARK);
        qtyField.setBorder(BorderFactory.createLineBorder(PARCH_BORDER, 1));
        contentPane.add(qtyField);

        GoldButton upBtn = new GoldButton("▲");
        upBtn.setFont(new Font("Arial", Font.BOLD, 14));
        upBtn.setMargin(new Insets(0, 0, 0, 0));
        upBtn.setBounds(166, 85, 40, 40);
        upBtn.setContentAreaFilled(false);
        int finalMaxQty = maxQty;
        upBtn.addActionListener(e -> {
            playClickSFX();
            try {
                int val = Integer.parseInt(qtyField.getText());
                if (val < finalMaxQty) { val++; qtyField.setText(String.valueOf(val)); }
            } catch (NumberFormatException ex) { qtyField.setText("1"); }
        });
        contentPane.add(upBtn);

        GoldButton downBtn = new GoldButton("▼");
        downBtn.setFont(new Font("Arial", Font.BOLD, 14));
        downBtn.setMargin(new Insets(0, 0, 0, 0));
        downBtn.setBounds(214, 85, 40, 40);
        downBtn.setContentAreaFilled(false);
        downBtn.addActionListener(e -> {
            playClickSFX();
            try {
                int val = Integer.parseInt(qtyField.getText());
                if (val > 1) { val--; qtyField.setText(String.valueOf(val)); }
            } catch (NumberFormatException ex) { qtyField.setText("1"); }
        });
        contentPane.add(downBtn);

        GoldButton maxBtn = new GoldButton("MAX");
        maxBtn.setFont(new Font("Arial", Font.BOLD, 11));
        maxBtn.setMargin(new Insets(0, 0, 0, 0));
        maxBtn.setBounds(262, 85, 40, 40);
        maxBtn.setContentAreaFilled(false);
        maxBtn.addActionListener(e -> { playClickSFX(); qtyField.setText(String.valueOf(finalMaxQty)); });
        contentPane.add(maxBtn);

        JLabel maxLabel = new JLabel("(Max: " + maxQty + ")");
        maxLabel.setFont(new Font("SansSerif", Font.ITALIC, 10));
        maxLabel.setForeground(new Color(100, 70, 40));
        maxLabel.setBounds(110, 130, 100, 15);
        contentPane.add(maxLabel);

        JLabel totalLabel = new JLabel();
        totalLabel.setFont(FONT_STAT);
        totalLabel.setForeground(TEXT_GREEN);
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setBounds(45, 125, 360, 20);
        contentPane.add(totalLabel);

        Runnable updateTotal = () -> {
            try {
                int qty = Integer.parseInt(qtyField.getText());
                if (qty < 1) qty = 1;
                if (qty > finalMaxQty) qty = finalMaxQty;
                int total = price * qty;
                totalLabel.setText("Total: $" + total);
                totalLabel.setForeground(total > playerGold ? STAT_HP : TEXT_GREEN);
            } catch (NumberFormatException ex) {
                totalLabel.setText("Total: $0");
                totalLabel.setForeground(STAT_HP);
            }
        };

        qtyField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { updateTotal.run(); }
            @Override public void removeUpdate(DocumentEvent e) { updateTotal.run(); }
            @Override public void changedUpdate(DocumentEvent e) { updateTotal.run(); }
        });

        qtyField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    int val = Integer.parseInt(qtyField.getText());
                    if (val < 1) val = 1;
                    if (val > finalMaxQty) val = finalMaxQty;
                    qtyField.setText(String.valueOf(val));
                } catch (NumberFormatException ex) { qtyField.setText("1"); }
                updateTotal.run();
            }
        });

        updateTotal.run();

        GoldButton buyBtn = new GoldButton("Buy");
        buyBtn.setBounds(80, 155, 100, 35);
        buyBtn.addActionListener(e -> {
            playClickSFX();
            try {
                int qty = Integer.parseInt(qtyField.getText());
                if (qty < 1) qty = 1;
                if (qty > finalMaxQty) qty = finalMaxQty;
                int totalCost = price * qty;
                if (totalCost <= playerGold) { dialog.dispose(); executeBuy(item, qty); }
            } catch (NumberFormatException ex) { dialog.dispose(); executeBuy(item, 1); }
        });
        contentPane.add(buyBtn);

        GoldButton cancelBtn = new GoldButton("Cancel");
        cancelBtn.setBounds(220, 155, 100, 35);
        cancelBtn.addActionListener(e -> { playClickSFX(); dialog.dispose(); });
        contentPane.add(cancelBtn);

        dialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void executeBuy(Item item, int qty) {
        int price = item.getPrice();
        int totalCost = price * qty;

        if (playerGold >= totalCost) {
            playerGold -= totalCost;
            player.getInventory().addItem(item, qty);
            try {
                java.lang.reflect.Method setMoney = player.getClass().getMethod("setMoney", int.class);
                setMoney.invoke(player, playerGold);
            } catch (Exception ex) { /* ignore */ }
            refreshPlayerShopGrid();
            updateCurrencyDisplay();
            messageLabel.setText("Bought " + qty + "x " + item.getName() + " for $" + totalCost + "!");
        } else {
            showParchmentDialog("Not Enough Gold",
                    "You need $" + totalCost + " to buy this!\nYou have: $" + playerGold,
                    "OK", null, null);
        }
    }

    public void refreshShopGrid() {
        if (shopGrid == null) return;

        shopGrid.removeAll();
        shopItemSlots.clear();

        ArrayList<Item> items = shopkeeper.getShop().getItems();

        if (items.isEmpty()) {
            JLabel emptyLabel = new JLabel("Shop is empty");
            emptyLabel.setFont(FONT_NAME);
            emptyLabel.setForeground(TEXT_GOLD);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setPreferredSize(new Dimension(400, 50));
            shopGrid.add(emptyLabel);
        } else {
            for (Item item : items) {
                int quantity = shopkeeper.getShop().getQuantity(item);
                if (quantity > 0) {
                    ItemSlot slot = new ItemSlot();
                    slot.setItem(item, quantity);
                    slot.setShopItem(true);

                    slot.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            slot.setHovered(true);
                            String displayText = item.getName() + " - $" + item.getPrice() + " | " + item.getDescription();
                            if (quantity > 1) displayText += " (x" + quantity + ")";
                            messageLabel.setText(displayText);
                        }
                        @Override
                        public void mouseExited(MouseEvent e) {
                            slot.setHovered(false);
                            messageLabel.setText("Welcome to the shop! Click on items to buy or sell.");
                        }
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            playClickSFX();
                            int availableQty = shopkeeper.getShop().getQuantity(item);
                            if (availableQty <= 0) {
                                refreshShopGrid();
                                return;
                            }

                            if (item instanceof Weapon) {
                                handleWeaponBuy(item);
                            } else {
                                showBuyQuantityDialog(item);
                            }
                        }
                    });

                    shopGrid.add(slot);
                    shopItemSlots.add(slot);
                }
            }
        }

        shopGrid.invalidate();
        shopScrollPane.getViewport().invalidate();
        SwingUtilities.invokeLater(() -> {
            shopGrid.revalidate();
            shopGrid.repaint();
            shopScrollPane.getViewport().revalidate();
            shopScrollPane.getViewport().repaint();
            shopScrollPane.revalidate();
            shopScrollPane.repaint();
            shopScrollPane.getViewport().setViewPosition(new Point(0, 0));
        });
    }

    private void executeSell(Item item, int qty) {
        int totalEarnings = item.getSellingPrice() * qty;
        player.addMoney(totalEarnings);
        player.getInventory().removeItem(item, qty);
        refreshPlayerShopGrid();
        updateCurrencyDisplay();
        messageLabel.setText("Sold " + qty + "x " + item.getName() + " for $" + totalEarnings + "!");
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
                        new Color[]{PARCH_TOP, PARCH_TAN, PARCH_WARM, PARCH_CENTRE, PARCH_WARM, PARCH_TAN, PARCH_TOP}));
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
        btn1.addActionListener(e -> { playClickSFX(); dialog.dispose(); if (onConfirm != null) onConfirm.run(); });
        contentPane.add(btn1);

        if (option2 != null) {
            GoldButton btn2 = new GoldButton(option2);
            btn2.setBounds(220, 130, 100, 40);
            btn2.addActionListener(e -> { playClickSFX(); dialog.dispose(); });
            contentPane.add(btn2);
        }

        dialog.setVisible(true);
    }

    public void refreshPlayerShopGrid() {
        if (playerGrid == null) return;

        playerGrid.removeAll();
        playerItemSlots.clear();

        ArrayList<Item> items = player.getInventory().getItems();

        if (items.isEmpty()) {
            JLabel emptyLabel = new JLabel("No items to display");
            emptyLabel.setFont(FONT_NAME);
            emptyLabel.setForeground(TEXT_GOLD);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setPreferredSize(new Dimension(390, 50));
            playerGrid.add(emptyLabel);
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
                            String displayText = item.getName() + " - $" + item.getSellingPrice();
                            if (quantity > 1) displayText += " (x" + quantity + ")";
                            messageLabel.setText(displayText + " (Click to sell)");
                        }
                        @Override
                        public void mouseExited(MouseEvent e) {
                            slot.setHovered(false);
                            messageLabel.setText("Welcome to the shop! Click on items to buy or sell.");
                        }
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            playClickSFX();
                            int availableQty = player.getInventory().getQuantity(item);
                            if (availableQty <= 0) { refreshPlayerShopGrid(); return; }
                            if (availableQty == 1) showSellConfirm(item, 1);
                            else showSellConfirm(item, availableQty);
                        }
                    });

                    playerGrid.add(slot);
                    playerItemSlots.add(slot);
                }
            }
        }

        playerGrid.invalidate();
        playerScrollPane.getViewport().invalidate();
        SwingUtilities.invokeLater(() -> {
            playerGrid.revalidate();
            playerGrid.repaint();
            playerScrollPane.getViewport().revalidate();
            playerScrollPane.getViewport().repaint();
            playerScrollPane.revalidate();
            playerScrollPane.repaint();
            playerScrollPane.getViewport().setViewPosition(new Point(0, 0));
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        if (backgroundGif != null) {
            // Draws current GIF frame; ImageObserver set in loadBackground()
            // fires repaint() automatically on every new frame
            g2.drawImage(backgroundGif.getImage(), 0, 0, WIDTH, HEIGHT, this);
        } else {
            g2.setColor(BG_COLOR);
            g2.fillRect(0, 0, WIDTH, HEIGHT);
        }

        paintTitle(g2);
        g2.dispose();
    }

    // ===== ITEM SLOT INNER CLASS =====
    private class ItemSlot extends JPanel {
        private Item item;
        private int quantity;
        private BufferedImage icon;
        private boolean hovered = false;
        private boolean isShopItem = false;

        public ItemSlot() {
            setPreferredSize(new Dimension(70, 70));
            setBackground(new Color(60, 40, 20, 180));
            setBorder(BorderFactory.createLineBorder(new Color(80, 50, 20), 2));
            setOpaque(false);
        }

        public void setItem(Item item, int qty) {
            this.item = item;
            this.quantity = qty;
            this.icon = item.getImage();
            this.isShopItem = false;
            repaint();
        }

        public void setShopItem(boolean isShopItem) { this.isShopItem = isShopItem; }
        public Item getItem() { return item; }
        public int getQuantity() { return quantity; }
        public void setHovered(boolean hovered) { this.hovered = hovered; repaint(); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

            g2.setColor(new Color(60, 40, 20, 180));
            g2.fillRect(0, 0, getWidth(), getHeight());

            if (icon != null) {
                g2.drawImage(icon, 11, 11, 48, 48, null);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Monospaced", Font.BOLD, 12));
                if (isShopItem) g2.drawString("$" + item.getPrice(), 35, 60);
                else g2.drawString("x" + quantity, 45, 60);

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

            g2.setColor(new Color(80, 50, 20));
            g2.setStroke(new BasicStroke(2.0f));
            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
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
}