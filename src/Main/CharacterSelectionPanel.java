package Main;

import Entities.Characters.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class CharacterSelectionPanel extends JPanel {

    // ── Dimensions (match GamePanel) ──────────────────────────────
    private static final int W = 1024;
    private static final int H = 768;

    // ── Palette — mirrors TitlePanel exactly ──────────────────────
    private static final Color PARCH_TOP      = new Color(148, 108, 44);
    private static final Color PARCH_TAN      = new Color(192, 152, 78);
    private static final Color PARCH_WARM     = new Color(220, 186, 118);
    private static final Color PARCH_CENTRE   = new Color(238, 212, 152);
    private static final Color PARCH_BORDER   = new Color(80,  38,   2, 230);
    private static final Color GOLD_TEXT      = new Color(252, 218, 72);
    private static final Color GOLD_DARK      = new Color(178, 108,  0);
    private static final Color TEXT_SHADOW    = new Color(0, 0, 0, 185);
    private static final Color BTN_NORMAL     = new Color(40, 111, 120);
    private static final Color BTN_HOVER      = new Color(65, 167, 180);
    private static final Color BTN_PRESS      = new Color(25,  65,  80);
    private static final Color BTN_BORDER_CLR = new Color(110, 190, 255);
    private static final Color TEXT_LIGHT     = new Color(240, 230, 255);

    // ── Layout ────────────────────────────────────────────────────
    private static final int CARD_W = 240;
    private static final int CARD_H = 420;
    private static final int CARD_GAP = 40;
    private static final int CARD_Y = 130;
    private static final int IMG_W  = 150;
    private static final int IMG_H  = 150;

    // ── State ─────────────────────────────────────────────────────
    private int selectedIndex = -1; // -1 = none
    private float[] cardGlow = {0f, 0f, 0f};
    private float[] glowDir  = {1f, 1f, 1f};

    // ── UI Components ─────────────────────────────────────────────
    private SelectButton[] selectBtns = new SelectButton[3];
    private ConfirmButton  confirmBtn;
    private BackButton     backBtn;

    // ── Character data ────────────────────────────────────────────
    private static final String[] NAMES  = {"swordsman", "archer", "mage"};
    private static final Color[]  COLORS = {
            new Color(200,  80,  80),   // Swordsman — red
            new Color( 80, 180, 100),   // Ranger — green
            new Color( 80, 120, 220)    // Mage — blue
    };
    private static final String[] STAT_LINES = {
            "HP: 1000  ATK: 205\nDEF: 40   SPD: 28",
            "HP: 880   ATK: 240\nDEF: 20   SPD: 50",
            "HP: 800   ATK: 335\nDEF: 10   SPD: 35"
    };
    private static final String[] DESCS = {
            "Specializes on survivability,\ntanking everything with absurd \nhp and defenses.",
            "Specializes on frequent attack\nintervals with unmatched speed\nand accuracy.",
            "Specializes on burst damage,\nattack high enough to reach \nthe heavens."
    };

    // ── Animation data for each character ─────────────────────────
    private static class CharacterAnimation {
        BufferedImage[] idleFrames = new BufferedImage[5];
        BufferedImage[] walkingFrames = new BufferedImage[5];
        int currentFrame = 0;
        boolean isWalking = false;
        boolean framesLoaded = false;

        CharacterAnimation(int index) {
            loadFrames(index);
        }

        private void loadFrames(int idx) {
            String className = NAMES[idx].toLowerCase();
            boolean anyFrameLoaded = false;

            // Load idle frames (idle_left_1-5.png)
            for (int i = 0; i < 5; i++) {
                try {
                    idleFrames[i] = ImageIO.read(getClass().getResourceAsStream("/" + className + "/"+className+"_idle/idle_right" + (i + 1) + ".png"));
                    if (idleFrames[i] != null) anyFrameLoaded = true;
                } catch (Exception e) {
                    idleFrames[i] = null;
                }
            }

            // Load walking frames (left1-4.png, duplicate frame 4 for frame 5)
            String[] walkingPaths = {"right1.png", "right2.png", "right3.png", "right4.png"};
            for (int i = 0; i < 4; i++) {
                try {
                    walkingFrames[i] = ImageIO.read(getClass().getResourceAsStream("/" + className + "/"+className+"_walking/walking_"+walkingPaths[i]));
                    if (walkingFrames[i] != null) anyFrameLoaded = true;
                } catch (Exception e) {
                    walkingFrames[i] = null;
                }
            }
            // Duplicate frame 4 for frame 5 (index 4)
            if (walkingFrames[3] != null) {
                walkingFrames[4] = walkingFrames[3];
            } else {
                walkingFrames[4] = null;
            }

            framesLoaded = anyFrameLoaded;

            // Create fallback placeholders if images not found
            if (!framesLoaded) {
                createPlaceholderFrames(idx);
            }
        }

        private void createPlaceholderFrames(int idx) {
            Color baseColor = COLORS[idx];
            // Idle frames: softer/darker color
            Color idleColor = new Color(baseColor.getRed() / 2, baseColor.getGreen() / 2, baseColor.getBlue() / 2);
            // Walking frames: brighter color
            Color walkingColor = new Color(
                    Math.min(255, baseColor.getRed() + 50),
                    Math.min(255, baseColor.getGreen() + 50),
                    Math.min(255, baseColor.getBlue() + 50)
            );

            String initial = String.valueOf(NAMES[idx].charAt(0));

            for (int i = 0; i < 5; i++) {
                // Idle frames (static position)
                idleFrames[i] = createPlaceholderImage(idleColor, initial, false, i);
                // Walking frames (with slight offset to simulate movement)
                walkingFrames[i] = createPlaceholderImage(walkingColor, initial, true, i);
            }
        }

        private BufferedImage createPlaceholderImage(Color color, String initial, boolean isWalking, int frame) {
            BufferedImage img = new BufferedImage(IMG_W, IMG_H, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();

            // Background
            g2.setColor(color);
            g2.fillRect(0, 0, IMG_W, IMG_H);

            // Add movement effect for walking frames
            if (isWalking) {
                int offset = (frame % 2 == 0) ? 5 : -5;
                g2.setColor(new Color(255, 255, 255, 80));
                g2.fillOval(IMG_W/2 - 20 + offset, IMG_H - 30, 40, 10);
            }

            // Large initial letter
            Font bigFont = new Font("Serif", Font.BOLD, 80);
            g2.setFont(bigFont);
            FontMetrics fm = g2.getFontMetrics();
            int lx = (IMG_W - fm.stringWidth(initial)) / 2;
            int ly = (IMG_H + fm.getAscent() - fm.getDescent()) / 2;

            g2.setColor(new Color(0, 0, 0, 80));
            g2.drawString(initial, lx + 3, ly + 3);

            g2.setPaint(new LinearGradientPaint(lx, ly - fm.getAscent(), lx, ly + 6,
                    new float[]{0f, 0.5f, 1f},
                    new Color[]{new Color(255, 245, 180), GOLD_TEXT, GOLD_DARK}));
            g2.drawString(initial, lx, ly);

            // Silhouette border
            g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 120));
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(IMG_W/2 - 30, IMG_H/2 - 40, 60, 80);

            g2.dispose();
            return img;
        }

        public BufferedImage getCurrentFrame() {
            if (isWalking) {
                return walkingFrames[currentFrame];
            } else {
                return idleFrames[currentFrame];
            }
        }

        public void nextFrame() {
            currentFrame = (currentFrame + 1) % 5;
        }

        public void setWalking(boolean walking) {
            this.isWalking = walking;
        }
    }

    private CharacterAnimation[] animations = new CharacterAnimation[3];
    private Timer animationTimer;

    // ── Fonts ─────────────────────────────────────────────────────
    private Font fontTitle;
    private Font fontName;
    private Font fontStat;
    private Font fontDesc;

    // ── Callback ──────────────────────────────────────────────────
    private Consumer<Player> onCharacterSelected;
    private Runnable onBackPressed;

    // ── Parent frame reference ────────────────────────────────────
    private JFrame parentFrame;

    // ── Animation ─────────────────────────────────────────────────
    private float shimmer   = 1.4f;
    private float floatY    = 0f, floatDir = 1f;
    private Timer titleAnimTimer;

    // ── Background ────────────────────────────────────────────────
    private Image  bgImage;

    // ─────────────────────────────────────────────────────────────
    //  Constructors
    // ─────────────────────────────────────────────────────────────
    public CharacterSelectionPanel() {
        this(null);
    }

    public CharacterSelectionPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setPreferredSize(new Dimension(W, H));
        setLayout(null);
        setOpaque(true);

        loadFonts();
        loadImages();
        initAnimations();
        buildButtons();
        startAnimation();
        startCharacterAnimation();
    }

    // ─────────────────────────────────────────────────────────────
    //  Public API
    // ─────────────────────────────────────────────────────────────
    public void setOnCharacterSelected(Consumer<Player> callback) {
        this.onCharacterSelected = callback;
    }

    public void setOnBackPressed(Runnable callback) {
        this.onBackPressed = callback;
    }

    // ─────────────────────────────────────────────────────────────
    //  Setup
    // ─────────────────────────────────────────────────────────────
    private void loadFonts() {
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
        fontName  = base.deriveFont(Font.PLAIN, 22f);
        fontStat  = new Font("Monospaced", Font.BOLD, 12);
        fontDesc  = new Font("Serif",      Font.ITALIC, 13);
    }

    private void loadImages() {
        // Try loading from the file system first (same folder as the .jar / project root)
        java.io.File f = new java.io.File("backgroundd.gif");
        if (f.exists()) {
            // ImageIcon uses Java's Toolkit which supports animated GIFs natively
            ImageIcon icon = new ImageIcon(f.getAbsolutePath());
            // Register this panel as the observer so repaints fire on each GIF frame
            icon.setImageObserver(this);
            bgImage = icon.getImage();
            return;
        }

        // Fallback: try loading from classpath resources
        java.net.URL url = getClass().getResource("/backgroundd.gif");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            icon.setImageObserver(this);
            bgImage = icon.getImage();
            return;
        }

        bgImage = null; // will use gradient fallback in paintBackground()
    }

    private void initAnimations() {
        for (int i = 0; i < 3; i++) {
            animations[i] = new CharacterAnimation(i);
        }
    }

    private void startCharacterAnimation() {
        animationTimer = new Timer(120, e -> {
            for (int i = 0; i < 3; i++) {
                animations[i].nextFrame();
            }
            repaint();
        });
        animationTimer.start();
    }

    private void buildButtons() {
        int totalW = 3 * CARD_W + 2 * CARD_GAP;
        int startX = (W - totalW) / 2;

        for (int i = 0; i < 3; i++) {
            int cx = startX + i * (CARD_W + CARD_GAP);
            int btnX = cx + (CARD_W - 140) / 2;
            int btnY = CARD_Y + CARD_H - 60;

            final int idx = i;
            selectBtns[i] = new SelectButton("Select");
            selectBtns[i].setBounds(btnX, btnY, 140, 42);
            selectBtns[i].addActionListener(e -> selectCharacter(idx));
            add(selectBtns[i]);
        }

        // Confirm button — initially invisible, positioned at bottom right
        int confirmX = W - 240;
        int confirmY = H - 80;
        confirmBtn = new ConfirmButton("Confirm Selection");
        confirmBtn.setBounds(confirmX, confirmY, 220, 50);
        confirmBtn.setVisible(false);
        confirmBtn.addActionListener(e -> confirmSelection());
        add(confirmBtn);

        // Back button — always visible, positioned at bottom left
        backBtn = new BackButton("← Back");
        backBtn.setBounds(20, H - 80, 140, 50);
        backBtn.addActionListener(e -> goBack());
        add(backBtn);
    }

    private void startAnimation() {
        titleAnimTimer = new Timer(16, e -> {
            // Title float
            floatY += 0.038f * floatDir;
            if (floatY >  5f) floatDir = -1f;
            if (floatY < -5f) floatDir =  1f;

            // Shimmer
            shimmer -= 0.005f;
            if (shimmer < -0.4f) shimmer = 1.4f;

            // Card glow pulse
            for (int i = 0; i < 3; i++) {
                if (i == selectedIndex) {
                    cardGlow[i] += 0.025f * glowDir[i];
                    if (cardGlow[i] > 1f) glowDir[i] = -1f;
                    if (cardGlow[i] < 0f) glowDir[i] =  1f;
                } else {
                    cardGlow[i] = Math.max(0f, cardGlow[i] - 0.05f);
                }
            }
            repaint();
        });
        titleAnimTimer.start();
    }

    // ─────────────────────────────────────────────────────────────
    //  Event handlers
    // ─────────────────────────────────────────────────────────────
    private void selectCharacter(int idx) {
        selectedIndex = idx;
        glowDir[idx]  = 1f;
        confirmBtn.setVisible(true);

        // Set walking animation for selected character, idle for others
        for (int i = 0; i < 3; i++) {
            animations[i].setWalking(i == idx);
        }

        repaint();
    }

    private void confirmSelection() {
        if (selectedIndex < 0 || onCharacterSelected == null) return;

        if (animationTimer != null) {
            animationTimer.stop();
        }
        if (titleAnimTimer != null) {
            titleAnimTimer.stop();
        }

        // Create the selected player
        GamePanel  gp   = new GamePanel();
        KeyHandler keyH = new KeyHandler();

        Player player = switch (selectedIndex) {
            case 0 -> new Swordsman(gp, keyH);
            case 1 -> new Ranger(gp, keyH);
            default -> new Mage(gp, keyH);
        };

        onCharacterSelected.accept(player);
    }

    private void goBack() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        if (titleAnimTimer != null) {
            titleAnimTimer.stop();
        }

        if (onBackPressed != null) {
            onBackPressed.run();
        } else if (parentFrame != null) {
            // Fallback: use parent frame to switch back to TitlePanel
            TitlePanel titlePanel = new TitlePanel();
            // Reconnect the start button callback to show character selection again
            titlePanel.setOnStartCallback(() -> {
                // This will be called when Start is clicked again
                CharacterSelectionPanel newSelectionPanel = new CharacterSelectionPanel(parentFrame);
                newSelectionPanel.setOnCharacterSelected(onCharacterSelected);
                newSelectionPanel.setOnBackPressed(() -> {
                    parentFrame.getContentPane().removeAll();
                    parentFrame.add(titlePanel);
                    parentFrame.revalidate();
                    parentFrame.repaint();
                });
                parentFrame.getContentPane().removeAll();
                parentFrame.add(newSelectionPanel);
                parentFrame.revalidate();
                parentFrame.repaint();
            });

            parentFrame.getContentPane().removeAll();
            parentFrame.add(titlePanel);
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  Painting
    // ─────────────────────────────────────────────────────────────
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Apply nearest-neighbor interpolation for pixel-perfect scaling
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        paintBackground(g2);
        paintVignette(g2);
        paintTitle(g2);
        paintCards(g2);

        g2.dispose();
    }

    // ── Background ────────────────────────────────────────────────
    private void paintBackground(Graphics2D g2) {
        if (bgImage != null) {
            double ia = (double) bgImage.getWidth(this) / bgImage.getHeight(this);
            double pa = (double) W / H;
            int bw, bh;
            if (ia > pa) { bh = H; bw = (int)(H * ia); }
            else         { bw = W; bh = (int)(W / ia); }
            g2.drawImage(bgImage, (W - bw) / 2, (H - bh) / 2, bw, bh, null);
        } else {
            GradientPaint grad = new GradientPaint(0, 0, new Color(10, 5, 20), 0, H, new Color(25, 10, 40));
            g2.setPaint(grad);
            g2.fillRect(0, 0, W, H);
        }
    }

    private void paintVignette(Graphics2D g2) {
        g2.setPaint(new RadialGradientPaint(
                new Point2D.Float(W / 2f, H / 2f), W * 0.70f,
                new float[]{0.22f, 1f},
                new Color[]{new Color(0, 0, 0, 0), new Color(0, 0, 0, 160)}));
        g2.fillRect(0, 0, W, H);

        g2.setPaint(new LinearGradientPaint(0, H * 0.55f, 0, H,
                new float[]{0f, 1f},
                new Color[]{new Color(0, 0, 0, 0), new Color(0, 0, 0, 200)}));
        g2.fillRect(0, (int)(H * 0.55f), W, H);
    }

    // ── Title ─────────────────────────────────────────────────────
    private void paintTitle(Graphics2D g2) {
        String text = "Choose Your Hero";
        g2.setFont(fontTitle);
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv  = fontTitle.createGlyphVector(frc, text);
        Rectangle2D vis = gv.getVisualBounds();
        int tw = (int) vis.getWidth();
        int tx = (W - tw) / 2 - (int) vis.getX();
        int ty = (int)(75 + floatY);

        // Shadow
        g2.setColor(new Color(0, 0, 0, 185));
        g2.drawString(text, tx + 4, ty + 6);
        g2.setColor(new Color(0, 0, 0, 75));
        g2.drawString(text, tx + 8, ty + 11);

        // Gold gradient fill
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

        // Shimmer
        float bandW = 90f;
        float bandX = tx + shimmer * (tw + bandW) - bandW;
        Shape savedClip = g2.getClip();
        g2.clip(gv.getOutline(tx, ty));
        g2.setPaint(new LinearGradientPaint(bandX, 0, bandX + bandW, 0,
                new float[]{0f, 0.5f, 1f},
                new Color[]{
                        new Color(255, 248, 200,   0),
                        new Color(255, 255, 255, 200),
                        new Color(255, 248, 200,   0)
                }));
        g2.fill(new Rectangle2D.Float(bandX, ty - (int)vis.getHeight() - 4, bandW, (int)vis.getHeight() + 14));
        g2.setClip(savedClip);
    }

    // ── Cards ─────────────────────────────────────────────────────
    private void paintCards(Graphics2D g2) {
        int totalW = 3 * CARD_W + 2 * CARD_GAP;
        int startX = (W - totalW) / 2;

        for (int i = 0; i < 3; i++) {
            int cx = startX + i * (CARD_W + CARD_GAP);
            paintCard(g2, cx, CARD_Y, i);
        }
    }

    private void paintCard(Graphics2D g2, int cx, int cy, int idx) {
        boolean selected = (idx == selectedIndex);
        float glow = cardGlow[idx];

        // ── Outer glow when selected ──
        if (glow > 0.01f) {
            for (int i = 14; i >= 2; i -= 2) {
                float a = glow * 0.055f * (15 - i);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.min(a, 0.6f)));
                g2.setColor(COLORS[idx]);
                g2.fill(new RoundRectangle2D.Float(cx - i, cy - i, CARD_W + i * 2, CARD_H + i * 2, 22, 22));
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        // ── Drop shadow ──
        for (int i = 8; i >= 1; i--) {
            float a = 0.06f * (9 - i);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
            g2.setColor(new Color(20, 8, 0));
            g2.fill(new RoundRectangle2D.Float(cx - i, cy + i * 2, CARD_W + i * 2, CARD_H, 18, 18));
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // ── Parchment body ──
        g2.setPaint(new LinearGradientPaint(cx, cy, cx, cy + CARD_H,
                new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                new Color[]{
                        new Color(148, 108,  44),
                        new Color(192, 152,  78),
                        new Color(220, 186, 118),
                        new Color(238, 212, 152),
                        new Color(220, 186, 118),
                        new Color(192, 152,  78),
                        new Color(148, 108,  44)
                }));
        Shape body = new RoundRectangle2D.Float(cx, cy, CARD_W, CARD_H, 18, 18);
        g2.fill(body);

        // ── Grain lines ──
        g2.setStroke(new BasicStroke(0.5f));
        for (int ly = cy + 12; ly < cy + CARD_H - 10; ly += 7) {
            int a = 12 + (int)(5 * Math.sin((ly - cy) * 0.3));
            g2.setColor(new Color(100, 60, 12, a));
            g2.drawLine(cx + 12, ly, cx + CARD_W - 12, ly);
        }

        // ── Age spots ──
        java.util.Random sr = new java.util.Random(42 + idx * 17);
        for (int i = 0; i < 18; i++) {
            int ax = cx + 20 + sr.nextInt(CARD_W - 40);
            int ay = cy + 20 + sr.nextInt(CARD_H - 40);
            int ar = 1 + sr.nextInt(4);
            g2.setColor(new Color(82, 44, 8, 8 + sr.nextInt(24)));
            g2.fillOval(ax, ay, ar, ar);
        }

        // ── Inner glow ──
        g2.setPaint(new RadialGradientPaint(
                new Point2D.Float(cx + CARD_W / 2f, cy + CARD_H / 2f),
                CARD_W * 0.50f,
                new float[]{0f, 1f},
                new Color[]{new Color(255, 230, 140, 30), new Color(255, 200, 80, 0)}));
        g2.fill(body);

        // ── Rolled edges ──
        g2.setPaint(new LinearGradientPaint(cx, cy, cx, cy + 24,
                new float[]{0f, 0.4f, 1f},
                new Color[]{new Color(110, 72, 18, 165), new Color(148, 108, 44, 80), new Color(148, 108, 44, 0)}));
        g2.fill(new RoundRectangle2D.Float(cx, cy, CARD_W, 24, 18, 18));

        g2.setPaint(new LinearGradientPaint(cx, cy + CARD_H - 24, cx, cy + CARD_H,
                new float[]{0f, 0.6f, 1f},
                new Color[]{new Color(148, 108, 44, 0), new Color(148, 108, 44, 80), new Color(110, 72, 18, 165)}));
        g2.fill(new RoundRectangle2D.Float(cx, cy + CARD_H - 24, CARD_W, 24, 18, 18));

        // ── Inner shadow rings ──
        g2.setStroke(new BasicStroke(0.8f));
        for (int i = 1; i <= 5; i++) {
            g2.setColor(new Color(55, 22, 2, 44 - i * 7));
            g2.draw(new RoundRectangle2D.Float(cx + i, cy + i, CARD_W - i * 2, CARD_H - i * 2, 16 - i, 16 - i));
        }

        // ── Decorative border ──
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(new Color(115, 68, 14, 100));
        g2.draw(new RoundRectangle2D.Float(cx + 9, cy + 9, CARD_W - 18, CARD_H - 18, 10, 10));
        g2.setColor(new Color(115, 68, 14, 50));
        g2.draw(new RoundRectangle2D.Float(cx + 12, cy + 12, CARD_W - 24, CARD_H - 24, 8, 8));

        // ── Outer border ──
        g2.setStroke(new BasicStroke(selected ? 2.8f : 2.2f));
        g2.setColor(selected ? new Color(COLORS[idx].getRed(), COLORS[idx].getGreen(), COLORS[idx].getBlue(), 220) : PARCH_BORDER);
        g2.draw(body);

        // ── Corner wax seals ──
        paintWaxSeal(g2, cx + 14, cy + 14);
        paintWaxSeal(g2, cx + CARD_W - 32, cy + 14);
        paintWaxSeal(g2, cx + 14, cy + CARD_H - 32);
        paintWaxSeal(g2, cx + CARD_W - 32, cy + CARD_H - 32);

        // ── Color accent bar (top) ──
        int barH = 5;
        g2.setColor(new Color(COLORS[idx].getRed(), COLORS[idx].getGreen(), COLORS[idx].getBlue(), 140));
        g2.setStroke(new BasicStroke(barH));
        g2.drawLine(cx + 18, cy + 22, cx + CARD_W - 18, cy + 22);

        // ── Animated Character Image ──
        int imgX = cx + (CARD_W - IMG_W) / 2;
        int imgY = cy + 32;
        paintCharacterImage(g2, imgX, imgY, idx);

        // ── Name ──
        g2.setFont(fontName);
        FontMetrics fmName = g2.getFontMetrics();
        String name = NAMES[idx];
        int nameW = fmName.stringWidth(name);
        int nameX = cx + (CARD_W - nameW) / 2;
        int nameY = imgY + IMG_H + 22;

        // Shadow
        g2.setColor(new Color(30, 10, 0, 150));
        g2.drawString(name, nameX + 2, nameY + 2);

        // Gold gradient text
        g2.setPaint(new LinearGradientPaint(nameX, nameY - fmName.getAscent(), nameX, nameY + 4,
                new float[]{0f, 0.5f, 1f},
                new Color[]{new Color(255, 245, 180), GOLD_TEXT, GOLD_DARK}));
        g2.drawString(name, nameX, nameY);

        // ── Divider ──
        g2.setColor(new Color(115, 68, 14, 80));
        g2.setStroke(new BasicStroke(1f));
        g2.drawLine(cx + 20, nameY + 6, cx + CARD_W - 20, nameY + 6);

        // ── Description ──
        g2.setFont(fontDesc);
        g2.setColor(new Color(60, 30, 5, 200));
        String[] descLines = DESCS[idx].split("\n");
        int descY = nameY + 20;
        FontMetrics fmDesc = g2.getFontMetrics();
        for (String line : descLines) {
            int lw = fmDesc.stringWidth(line);
            g2.drawString(line, cx + (CARD_W - lw) / 2, descY);
            descY += fmDesc.getHeight();
        }

        // ── Stats ──
        descY += 6;
        g2.setFont(fontStat);
        g2.setColor(new Color(50, 25, 5, 220));
        FontMetrics fmStat = g2.getFontMetrics();
        String[] statLines = STAT_LINES[idx].split("\n");
        for (String line : statLines) {
            int lw = fmStat.stringWidth(line);
            g2.drawString(line, cx + (CARD_W - lw) / 2, descY);
            descY += fmStat.getHeight() + 2;
        }

        // ── Selected indicator ──
        if (selected) {
            String checkTxt = "✦ Selected ✦";
            g2.setFont(new Font("Serif", Font.BOLD, 12));
            FontMetrics fmCheck = g2.getFontMetrics();
            int checkW = fmCheck.stringWidth(checkTxt);
            float alpha = 0.6f + cardGlow[idx] * 0.4f;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.setColor(COLORS[idx]);
            g2.drawString(checkTxt, cx + (CARD_W - checkW) / 2, cy + CARD_H - 68);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    private void paintCharacterImage(Graphics2D g2, int ix, int iy, int idx) {
        // Apply nearest-neighbor interpolation for pixel-perfect scaling
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // Image frame background
        g2.setColor(new Color(40, 20, 5, 60));
        g2.fillRoundRect(ix - 4, iy - 4, IMG_W + 8, IMG_H + 8, 10, 10);

        g2.setColor(new Color(115, 68, 14, 100));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(ix - 4, iy - 4, IMG_W + 8, IMG_H + 8, 10, 10);

        // Draw current animation frame
        BufferedImage currentFrame = animations[idx].getCurrentFrame();
        if (currentFrame != null) {
            // Scale the frame to fit the display area while maintaining aspect ratio
            g2.drawImage(currentFrame, ix, iy, IMG_W, IMG_H, null);
        } else {
            // Fallback - should not happen as placeholders are created
            g2.setColor(COLORS[idx]);
            g2.fillRect(ix, iy, IMG_W, IMG_H);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Serif", Font.BOLD, 40));
            g2.drawString(NAMES[idx].substring(0, 1), ix + IMG_W/2 - 15, iy + IMG_H/2 + 15);
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
    //  Inner class: Select button (mirrors BattleButton styling)
    // ─────────────────────────────────────────────────────────────
    private static class SelectButton extends JButton {
        private boolean hovered = false;
        private boolean pressed = false;

        SelectButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFont(new Font("Serif", Font.BOLD, 15));
            setForeground(TEXT_LIGHT);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { if (isEnabled()) { hovered = true; repaint(); } }
                @Override public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
                @Override public void mousePressed(MouseEvent e) { if (isEnabled()) { pressed = true; repaint(); } }
                @Override public void mouseReleased(MouseEvent e){ pressed = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            int c = 7;
            Polygon oct = makeOct(0, 0, w, h, c);

            Color fill = pressed ? BTN_PRESS : hovered ? BTN_HOVER : BTN_NORMAL;
            g2.setColor(fill);
            g2.fill(oct);

            if (hovered && !pressed) {
                g2.setColor(new Color(255, 255, 255, 22));
                g2.fill(makeOct(0, 0, w, h / 2, c));
            }

            g2.setColor(BTN_BORDER_CLR);
            g2.setStroke(new BasicStroke(1.7f));
            g2.draw(oct);

            // Amber glow on hover
            if (hovered) {
                for (int i = 6; i >= 2; i -= 2) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.04f));
                    g2.setColor(new Color(205, 148, 12));
                    g2.fill(makeOct(-i, -i, w + i * 2, h + i * 2, c));
                }
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }

            FontMetrics fm = g2.getFontMetrics(getFont());
            int tx = (w - fm.stringWidth(getText())) / 2;
            int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
            g2.setFont(getFont());
            g2.setColor(new Color(0, 0, 0, 90));
            g2.drawString(getText(), tx + 1, ty + 1);
            g2.setColor(getForeground());
            g2.drawString(getText(), tx, ty);

            g2.dispose();
        }

        private Polygon makeOct(int x, int y, int w, int h, int c) {
            return new Polygon(
                    new int[]{x+c, x+w-c, x+w, x+w,   x+w-c, x+c, x,   x},
                    new int[]{y,   y,     y+c, y+h-c,  y+h,   y+h, y+h-c, y+c},
                    8);
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  Inner class: Confirm button (larger, gold gradient)
    // ─────────────────────────────────────────────────────────────
    private static class ConfirmButton extends JButton {
        private boolean hovered = false;
        private boolean pressed = false;
        private float pulse = 0f, pulseDir = 1f;
        private Timer pulseTimer;

        ConfirmButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFont(new Font("Serif", Font.BOLD, 17));
            setForeground(new Color(42, 12, 0));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            pulseTimer = new Timer(16, e -> {
                pulse += 0.025f * pulseDir;
                if (pulse > 1f) pulseDir = -1f;
                if (pulse < 0f) pulseDir =  1f;
                repaint();
            });
            pulseTimer.start();

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                @Override public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
                @Override public void mousePressed(MouseEvent e) { pressed = true; repaint(); }
                @Override public void mouseReleased(MouseEvent e){ pressed = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            int c = 9;
            Polygon oct = makeOct(0, 0, w, h, c);

            // Gold halo
            float ga = 0.28f + pulse * 0.35f;
            if (hovered) ga = 0.70f;
            for (int i = 16; i >= 2; i -= 2) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ga * 0.050f));
                g2.setColor(new Color(205, 148, 12));
                g2.fill(makeOct(-i, -i, w + i * 2, h + i * 2, c));
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            // Face
            Color tc = hovered ? new Color(255, 255, 208) : new Color(252, 240, 122);
            Color mc = hovered ? new Color(250, 222,  62) : new Color(238, 190,  28);
            Color bc = hovered ? new Color(212, 148,  12) : new Color(178, 108,   0);
            if (pressed) { tc = new Color(220, 200, 80); mc = new Color(190, 140, 10); bc = new Color(140, 80, 0); }

            g2.setPaint(new LinearGradientPaint(0, 0, 0, h,
                    new float[]{0f, .22f, .55f, 1f},
                    new Color[]{tc, mc, bc, new Color(208, 155, 12)}));
            g2.fill(oct);

            // Sheen
            g2.setColor(new Color(255, 255, 215, 140));
            g2.setStroke(new BasicStroke(1.4f));
            g2.drawLine(c, 1, w - c, 1);

            // Border
            g2.setStroke(new BasicStroke(2.2f));
            g2.setColor(new Color(82, 38, 0, 210));
            g2.draw(oct);

            // Label
            FontMetrics fm = g2.getFontMetrics(getFont());
            int tx = (w - fm.stringWidth(getText())) / 2;
            int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
            g2.setFont(getFont());
            g2.setColor(new Color(255, 215, 70, 80));
            g2.drawString(getText(), tx + 1, ty + 1);
            g2.setColor(getForeground());
            g2.drawString(getText(), tx, ty);

            g2.dispose();
        }

        private Polygon makeOct(int x, int y, int w, int h, int c) {
            return new Polygon(
                    new int[]{x+c, x+w-c, x+w, x+w,   x+w-c, x+c, x,   x},
                    new int[]{y,   y,     y+c, y+h-c,  y+h,   y+h, y+h-c, y+c},
                    8);
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  Inner class: Back button (styled like Confirm button but smaller)
    // ─────────────────────────────────────────────────────────────
    private static class BackButton extends JButton {
        private boolean hovered = false;
        private boolean pressed = false;
        private float pulse = 0f, pulseDir = 1f;
        private Timer pulseTimer;

        BackButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFont(new Font("Serif", Font.BOLD, 16));
            setForeground(new Color(42, 12, 0));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            pulseTimer = new Timer(16, e -> {
                pulse += 0.025f * pulseDir;
                if (pulse > 1f) pulseDir = -1f;
                if (pulse < 0f) pulseDir =  1f;
                repaint();
            });
            pulseTimer.start();

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                @Override public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
                @Override public void mousePressed(MouseEvent e) { pressed = true; repaint(); }
                @Override public void mouseReleased(MouseEvent e){ pressed = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            int c = 8;
            Polygon oct = makeOct(0, 0, w, h, c);

            // Gold halo
            float ga = 0.25f + pulse * 0.30f;
            if (hovered) ga = 0.60f;
            for (int i = 12; i >= 2; i -= 2) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ga * 0.045f));
                g2.setColor(new Color(205, 148, 12));
                g2.fill(makeOct(-i, -i, w + i * 2, h + i * 2, c));
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            // Face
            Color tc = hovered ? new Color(255, 255, 208) : new Color(252, 240, 122);
            Color mc = hovered ? new Color(250, 222,  62) : new Color(238, 190,  28);
            Color bc = hovered ? new Color(212, 148,  12) : new Color(178, 108,   0);
            if (pressed) { tc = new Color(220, 200, 80); mc = new Color(190, 140, 10); bc = new Color(140, 80, 0); }

            g2.setPaint(new LinearGradientPaint(0, 0, 0, h,
                    new float[]{0f, .22f, .55f, 1f},
                    new Color[]{tc, mc, bc, new Color(208, 155, 12)}));
            g2.fill(oct);

            // Top sheen
            g2.setColor(new Color(255, 255, 215, 130));
            g2.setStroke(new BasicStroke(1.3f));
            g2.drawLine(c, 1, w - c, 1);

            // Border
            g2.setStroke(new BasicStroke(2.0f));
            g2.setColor(new Color(82, 38, 0, 210));
            g2.draw(oct);

            // Label
            FontMetrics fm = g2.getFontMetrics(getFont());
            int tx = (w - fm.stringWidth(getText())) / 2;
            int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
            g2.setFont(getFont());
            g2.setColor(new Color(255, 215, 70, 80));
            g2.drawString(getText(), tx + 1, ty + 1);
            g2.setColor(getForeground());
            g2.drawString(getText(), tx, ty);

            g2.dispose();
        }

        private Polygon makeOct(int x, int y, int w, int h, int c) {
            return new Polygon(
                    new int[]{x+c, x+w-c, x+w, x+w,   x+w-c, x+c, x,   x},
                    new int[]{y,   y,     y+c, y+h-c,  y+h,   y+h, y+h-c, y+c},
                    8);
        }
    }
}