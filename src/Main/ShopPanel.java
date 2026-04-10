package Main;

import Entities.Characters.Player;
import Entities.Characters.Swordsman;
import Entities.Characters.Ranger;
import Entities.Characters.Mage;
import Entities.Entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

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
    private static final Color HOVER_GOLD = new Color(252, 218, 72);

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
    private final Entity shopkeeper;
    private final Runnable onBackPressed;

    // UI Components
    private JPanel playerBox;
    private JPanel shopBox;
    private JPanel messageBox;
    private GoldButton backButton;
    private JLabel messageLabel;

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

    // Background Image
    private BufferedImage backgroundImage;

    // Constructor
    public ShopPanel(JFrame parentFrame, Player player, Entity shopkeeper, Runnable onBackPressed) {
        this.parentFrame = parentFrame;
        this.player = player;
        this.shopkeeper = shopkeeper;
        this.onBackPressed = onBackPressed;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        setOpaque(true);
        setFocusable(true);

        loadTitleFont();
        loadBackground();
        loadPlayerAnimations();
        loadShopkeeperAnimations();
        initUI();
        startAnimations();
        startTitleAnimation();

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
            repaint();
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

        // Double drop shadow
        g2.setColor(new Color(0, 0, 0, 185));
        g2.drawString(text, tx + 4, ty + 6);
        g2.setColor(new Color(0, 0, 0, 75));
        g2.drawString(text, tx + 8, ty + 11);

        // Gold gradient
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

        // Shimmer sweep
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
            playerIdleFrames[i] = createPlaceholderImage(frameColor, initial, 96);
        }
    }

    private void loadShopkeeperAnimations() {
        if (shopkeeper == null) {
            createShopkeeperPlaceholderFrames();
            return;
        }

        String className = getShopkeeperClassName();
        boolean anyFrameLoaded = false;

        for (int i = 0; i < 5; i++) {
            try {
                String path = "/" + className + "/" + className + "_idle/idle_left" + (i + 1) + ".png";
                shopkeeperIdleFrames[i] = ImageIO.read(getClass().getResourceAsStream(path));
                if (shopkeeperIdleFrames[i] != null) anyFrameLoaded = true;
            } catch (Exception e) {
                shopkeeperIdleFrames[i] = null;
            }
        }

        if (!anyFrameLoaded) {
            createShopkeeperPlaceholderFrames();
        }
    }

    private String getShopkeeperClassName() {
        if (shopkeeper instanceof Swordsman) return "swordsman";
        if (shopkeeper instanceof Ranger) return "archer";
        if (shopkeeper instanceof Mage) return "mage";
        return shopkeeper.getClass().getSimpleName().toLowerCase();
    }

    private void createShopkeeperPlaceholderFrames() {
        Color baseColor = new Color(139, 119, 101); // Warm brown
        String initial = "S";

        for (int i = 0; i < 5; i++) {
            float brightness = 0.7f + (i * 0.06f);
            Color frameColor = new Color(
                    Math.min(255, (int) (baseColor.getRed() * brightness)),
                    Math.min(255, (int) (baseColor.getGreen() * brightness)),
                    Math.min(255, (int) (baseColor.getBlue() * brightness))
            );
            shopkeeperIdleFrames[i] = createPlaceholderImage(frameColor, initial, 96);
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
        // Player animation timer
        playerAnimTimer = new Timer();
        playerAnimTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentPlayerFrame = (currentPlayerFrame + 1) % 5;
                SwingUtilities.invokeLater(() -> {
                    if (playerSpriteLabel != null && playerIdleFrames[currentPlayerFrame] != null) {
                        Image scaled = playerIdleFrames[currentPlayerFrame].getScaledInstance(96, 96, Image.SCALE_SMOOTH);
                        playerSpriteLabel.setIcon(new ImageIcon(scaled));
                    }
                });
            }
        }, 0, 120);

        // Shopkeeper animation timer
        shopkeeperAnimTimer = new Timer();
        shopkeeperAnimTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentShopkeeperFrame = (currentShopkeeperFrame + 1) % 5;
                SwingUtilities.invokeLater(() -> {
                    if (shopkeeperSpriteLabel != null && shopkeeperIdleFrames[currentShopkeeperFrame] != null) {
                        Image scaled = shopkeeperIdleFrames[currentShopkeeperFrame].getScaledInstance(96, 96, Image.SCALE_SMOOTH);
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

                // Outer shadow layers (rolled edges)
                for (int i = 8; i >= 1; i--) {
                    float a = 0.06f * (9 - i);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
                    g2.setColor(new Color(20, 8, 0));
                    g2.fill(new RoundRectangle2D.Float(sx - i, sy + i * 2, sw + i * 2, sh, 18, 18));
                }
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

                // Multi-stop parchment gradient
                g2.setPaint(new LinearGradientPaint(sx, sy, sx, sy + sh,
                        new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                        new Color[]{
                                PARCH_TOP, PARCH_TAN, PARCH_WARM, PARCH_CENTRE,
                                PARCH_WARM, PARCH_TAN, PARCH_TOP
                        }));
                Shape body = new RoundRectangle2D.Float(sx, sy, sw, sh, 18, 18);
                g2.fill(body);

                // Gold border
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
        // Player sprite - LEFT side
        playerSpriteLabel = new JLabel();
        if (playerIdleFrames[0] != null) {
            Image scaled = playerIdleFrames[0].getScaledInstance(96, 96, Image.SCALE_SMOOTH);
            playerSpriteLabel.setIcon(new ImageIcon(scaled));
        }
        playerSpriteLabel.setBounds(20, 20, 96, 96);
        playerSpriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerSpriteLabel.setVerticalAlignment(SwingConstants.CENTER);
        playerBox.add(playerSpriteLabel);

        // Player inventory label - LEFT ALIGNED, right of sprite
        String playerName = player != null ? player.getClass().getSimpleName() : "Player";
        JLabel inventoryLabel = new JLabel(playerName + "'s Inventory");
        inventoryLabel.setFont(FONT_NAME);
        inventoryLabel.setForeground(TEXT_GOLD);
        inventoryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        inventoryLabel.setBounds(130, 55, 320, 30);
        playerBox.add(inventoryLabel);

        // Space below reserved for future item grid
    }

    private void setupShopBox() {
        // Shopkeeper sprite - RIGHT side
        shopkeeperSpriteLabel = new JLabel();
        if (shopkeeperIdleFrames[0] != null) {
            Image scaled = shopkeeperIdleFrames[0].getScaledInstance(96, 96, Image.SCALE_SMOOTH);
            shopkeeperSpriteLabel.setIcon(new ImageIcon(scaled));
        }
        shopkeeperSpriteLabel.setBounds(SHOP_BOX_W - 116, 20, 96, 96);
        shopkeeperSpriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        shopkeeperSpriteLabel.setVerticalAlignment(SwingConstants.CENTER);
        shopBox.add(shopkeeperSpriteLabel);

        // Shopkeeper name label - RIGHT ALIGNED, left of sprite
        String shopName = shopkeeper != null ? shopkeeper.getName() : "Shopkeeper";
        JLabel shopLabel = new JLabel(shopName + "'s Shop");
        shopLabel.setFont(FONT_NAME);
        shopLabel.setForeground(TEXT_GOLD);
        shopLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        shopLabel.setBounds(20, 55, SHOP_BOX_W - 150, 30);
        shopBox.add(shopLabel);

        // Space below reserved for future shop items
    }

    private void setupMessageBox() {
        messageLabel = new JLabel("Welcome to the shop! Click on items to buy or sell.");
        messageLabel.setFont(FONT_NAME);
        messageLabel.setForeground(TEXT_DARK);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(20, 25, MSG_BOX_W - 40, 50);
        messageBox.add(messageLabel);
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

    // Inner class for GoldButton
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