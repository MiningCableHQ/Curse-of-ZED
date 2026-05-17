package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import Audio.SFX.SFXPlayer;
import Audio.SFX.ClickSFX;
import Audio.Music.MainMenuMusic;

// ═════════════════════════════════════════════════════════════
//  Main Panel
// ═════════════════════════════════════════════════════════════
public class TitlePanel extends JPanel {
    private Runnable onStartCallback;

    public void setOnStartCallback(Runnable callback) {
        this.onStartCallback = callback;
    }

    public void setParentFrame(JFrame frame) {
        this.parentFrame = frame;
    }

    static final int W = 1024;
    static final int H = 768;

    // ── Assets ───────────────────────────────────────────────
    private ImageIcon bgImage;
    private Font rbTitle, rbBtn, rbTitlePx, rbBtnPx;

    // ── Animation ────────────────────────────────────────────
    private float shimmer    =  1.4f;
    private float floatY     =  0f, floatDir   =  1f;
    private float scrollBob  =  0f, bobDir     =  1f;
    private float btnPulse   =  0f, pulseDir   =  1f;
    private float blinkPhase =  0f;

    // ── Particles (left = green/cyan, right = purple/pink) ───
    private final java.util.List<Particle> particles = new ArrayList<>();
    private final Random rng = new Random();

    // ── Button state ─────────────────────────────────────────
    private boolean startHover = false, startPress = false;
    private boolean credsHover = false, credsPress = false;
    private boolean showingCredits = false;

    // Button geometry constants — match the original single-button exactly
    private static final int BTN_W  = 250;//240;
    private static final int BTN_H  = 55;//45;
    private static final int BTN_GAP = 14; // gap between the two buttons

    // These hold the UN-offset hit bounds (used for mouse hit-testing)
    private final Rectangle startRect = new Rectangle();
    private final Rectangle credsRect = new Rectangle();
    private final SFXPlayer sfxPlayer = new SFXPlayer();
    private final MainMenuMusic mainMenuMusic = new MainMenuMusic();

    private boolean lbHover = false, lbPress = false;
    private boolean quitHover = false, quitPress = false;

    private final Rectangle lbRect   = new Rectangle();
    private final Rectangle quitRect = new Rectangle();

    private JFrame parentFrame;

    // ── Constructor ──────────────────────────────────────────
    public TitlePanel() {
        setPreferredSize(new Dimension(W, H));
        setOpaque(true);
        loadAssets();
        sfxPlayer.preloadSFX(new ClickSFX());
        mainMenuMusic.preload();
        mainMenuMusic.play(true);

        addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                if (showingCredits) {
                    showingCredits = false;
                    sfxPlayer.playSFX(new ClickSFX());
                    return;
                }
                if (startRect.contains(e.getPoint())) {
                    startPress = true;
                    sfxPlayer.playSFX(new ClickSFX());
                    mainMenuMusic.stop();
                    new Timer(80, ev -> {
                        ((Timer) ev.getSource()).stop();
                        if (onStartCallback != null) onStartCallback.run();
                    }).start();
                    //if (onStartCallback != null) onStartCallback.run();
                } else if (credsRect.contains(e.getPoint())) {
                    credsPress = true;
                    sfxPlayer.playSFX(new ClickSFX());
                    showingCredits = true;
                } else if (lbRect.contains(e.getPoint())) {
                    lbPress = true;
                    sfxPlayer.playSFX(new ClickSFX());
                    openLeaderboard();
                } else if (quitRect.contains(e.getPoint())) {
                    quitPress = true;
                    sfxPlayer.playSFX(new ClickSFX());
                    System.exit(0);
                }
            }
            @Override public void mouseReleased(MouseEvent e) {
                startPress = false;
                credsPress = false;
                lbPress    = false;
                quitPress  = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override public void mouseMoved(MouseEvent e) {
                if (showingCredits) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    return;
                }
                startHover = startRect.contains(e.getPoint());
                credsHover = credsRect.contains(e.getPoint());
                lbHover    = lbRect.contains(e.getPoint());
                quitHover  = quitRect.contains(e.getPoint());
                setCursor((startHover || credsHover || lbHover || quitHover)
                        ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                        : Cursor.getDefaultCursor());
            }
        });

        new Timer(16, e -> { tick(); repaint(); }).start();
    }

    // ── Load font + image ────────────────────────────────────
    private void loadAssets() {
        bgImage = new ImageIcon("backgroundd.gif");

        Font base = null;
        for (String n : new String[]{
                "RINGM___.TTF","RingbearerMedium.ttf","Ringbearer Medium.ttf",
                "ringbearer medium.ttf","Ringbearer.ttf","ringbearer.ttf"}) {
            File f = new File(n);
            if (!f.exists()) continue;
            try {
                base = Font.createFont(Font.TRUETYPE_FONT, f);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(base);
                System.out.println("Font loaded: " + n);
                break;
            } catch (Exception ex) { System.err.println("Font error: " + ex.getMessage()); }
        }
        if (base == null) {
            System.err.println("RingbearerMedium.ttf not found — using fallback.\n"
                    + "Download: https://www.dafont.com/ringbearer.font");
            base = new Font("Palatino Linotype", Font.PLAIN, 12);
        }
        rbTitle   = base.deriveFont(Font.PLAIN, 72f);
        rbBtn     = base.deriveFont(Font.PLAIN, 17f);  // ← same as original
        rbTitlePx = base.deriveFont(Font.PLAIN, 80f);
        rbBtnPx   = base.deriveFont(Font.PLAIN, 30f);  // ← same as original (2x for pixel buffer)
    }

    // ── Tick ─────────────────────────────────────────────────
    private void tick() {
        floatY += 0.042f * floatDir;
        if (floatY >  6f) floatDir = -1f;
        if (floatY < -6f) floatDir =  1f;

        scrollBob += 0.026f * bobDir;
        if (scrollBob >  4f) bobDir = -1f;
        if (scrollBob < -4f) bobDir =  1f;

        shimmer -= 0.0055f;
        if (shimmer < -0.4f) shimmer = 1.4f;

        btnPulse += 0.022f * pulseDir;
        if (btnPulse > 1f) pulseDir = -1f;
        if (btnPulse < 0f) pulseDir =  1f;

        blinkPhase += 0.038f;
        if (blinkPhase > (float)(Math.PI * 2)) blinkPhase -= (float)(Math.PI * 2);

        if (rng.nextFloat() < (showingCredits ? 0.60f : 0.30f)) spawnParticle();
        particles.removeIf(p -> !p.alive());
        particles.forEach(Particle::update);
    }

    // ── Paint ────────────────────────────────────────────────
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        paintBg(g2);
        paintVignette(g2);
        paintParticles(g2);

        if (showingCredits) {
            paintCreditsScreen(g2);
        } else {
            // 4-button stack centred vertically
            int totalH    = BTN_H * 4 + BTN_GAP * 3;
            int startByBase = (H - totalH) / 2 + 50;
            int credsBaseY  = startByBase + BTN_H + BTN_GAP;
            int lbBaseY     = credsBaseY  + BTN_H + BTN_GAP;
            int quitBaseY   = lbBaseY     + BTN_H + BTN_GAP;

            startRect.setBounds((W - BTN_W) / 2, startByBase, BTN_W, BTN_H);
            credsRect.setBounds((W - BTN_W) / 2, credsBaseY,  BTN_W, BTN_H);
            lbRect.setBounds   ((W - BTN_W) / 2, lbBaseY,     BTN_W, BTN_H);
            quitRect.setBounds ((W - BTN_W) / 2, quitBaseY,   BTN_W, BTN_H);

            // ── Half-res pixel buffer (same pattern as original) ──
            int SCALE = 2;
            int lw = W / SCALE, lh = H / SCALE;
            BufferedImage buf = new BufferedImage(lw, lh, BufferedImage.TYPE_INT_ARGB);
            Graphics2D lg = buf.createGraphics();
            hintPixel(lg);
            lg.scale(1.0 / SCALE, 1.0 / SCALE);

            int scrollCY = (int)(H * 0.24f + scrollBob);
            paintScroll(lg, scrollCY);

            Font savedTitle = rbTitle, savedBtn = rbBtn;
            rbTitle = rbTitlePx;
            rbBtn   = rbBtnPx;
            paintTitle(lg, scrollCY + (int) floatY);
            rbTitle = savedTitle;
            rbBtn   = savedBtn;
            lg.dispose();

            // Upscale — nearest-neighbour for chunky pixel look
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.drawImage(buf, 0, 0, W, H, null);

            // Button shapes and labels drawn at full resolution for pixel-perfect x alignment
            paintButtonShape(g2, startByBase, startHover, startPress);
            paintButtonShape(g2, credsBaseY,  credsHover, credsPress);
            paintButtonShape(g2, lbBaseY,     lbHover,    lbPress);
            paintButtonShape(g2, quitBaseY,   quitHover,  quitPress);
            paintButtonLabel(g2, "Start Game",  startByBase, startHover, startPress, true);
            paintButtonLabel(g2, "Credits",     credsBaseY,  credsHover, credsPress, true);
            paintButtonLabel(g2, "Leaderboard", lbBaseY,     lbHover,    lbPress,    true);
            paintButtonLabel(g2, "Quit",        quitBaseY,   quitHover,  quitPress,  true);
        }

        g2.dispose();
    }

    private void hintPixel(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,     RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,   RenderingHints.VALUE_COLOR_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_DITHERING,         RenderingHints.VALUE_DITHER_DISABLE);
    }

    // ── Background ───────────────────────────────────────────
    private void paintBg(Graphics2D g2) {
        if (bgImage == null) {
            g2.setColor(new Color(10,5,20));
            g2.fillRect(0,0,W,H);
            return;
        }
        Image img = bgImage.getImage();
        double ia = (double) img.getWidth(null) / img.getHeight(null);
        double pa = (double) W / H;
        int bw, bh;
        if (ia > pa) { bh = H; bw = (int)(H * ia); }
        else         { bw = W; bh = (int)(W / ia); }
        g2.drawImage(img, (W-bw)/2, (H-bh)/2, bw, bh, this);
    }

    // ── Vignette ─────────────────────────────────────────────
    private void paintVignette(Graphics2D g2) {
        g2.setPaint(new RadialGradientPaint(
                new Point2D.Float(W/2f, H/2f), W*0.70f,
                new float[]{0.22f, 1f},
                new Color[]{new Color(0,0,0,0), new Color(0,0,0,150)}));
        g2.fillRect(0, 0, W, H);
        g2.setPaint(new LinearGradientPaint(0, H*0.48f, 0, H,
                new float[]{0f, 1f},
                new Color[]{new Color(0,0,0,0), new Color(0,0,0,210)}));
        g2.fillRect(0, (int)(H*0.48f), W, H);
    }

    // ══════════════════════════════════════════════════════════
    //  PARCHMENT SCROLL
    // ══════════════════════════════════════════════════════════
    private void paintScroll(Graphics2D g2, int cy) {
        int sw = 650, sh = 115;
        int sx = (W - sw) / 2;
        int sy = cy - sh / 2;

        for (int i = 8; i >= 1; i--) {
            float a = 0.06f * (9 - i);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
            g2.setColor(new Color(20, 8, 0));
            g2.fill(new RoundRectangle2D.Float(sx-i, sy+i*2, sw+i*2, sh, 18, 18));
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        g2.setPaint(new LinearGradientPaint(sx, sy, sx, sy + sh,
                new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                new Color[]{
                        new Color(148, 108,  44), new Color(192, 152,  78),
                        new Color(220, 186, 118), new Color(238, 212, 152),
                        new Color(220, 186, 118), new Color(192, 152,  78),
                        new Color(148, 108,  44)
                }));
        Shape body = new RoundRectangle2D.Float(sx, sy, sw, sh, 18, 18);
        g2.fill(body);

        g2.setStroke(new BasicStroke(0.5f));
        for (int ly = sy + 16; ly < sy + sh - 14; ly += 6) {
            int a = 14 + (int)(6 * Math.sin((ly - sy) * 0.4));
            g2.setColor(new Color(100, 60, 12, a));
            g2.drawLine(sx + 16, ly, sx + sw - 16, ly);
        }

        g2.setStroke(new BasicStroke(0.4f));
        Random vr = new Random(77);
        for (int i = 0; i < 8; i++) {
            int vx = sx + 60 + vr.nextInt(sw - 120);
            int vlen = 20 + vr.nextInt(60);
            int vy = sy + 20 + vr.nextInt(sh - 40 - vlen);
            g2.setColor(new Color(88, 50, 10, 10 + vr.nextInt(12)));
            g2.drawLine(vx, vy, vx + vr.nextInt(6) - 3, vy + vlen);
        }

        Random sr = new Random(42);
        for (int i = 0; i < 28; i++) {
            int ax = sx + 40 + sr.nextInt(sw - 80);
            int ay = sy + 20 + sr.nextInt(sh - 40);
            int ar = 1 + sr.nextInt(5);
            g2.setColor(new Color(82, 44, 8, 10 + sr.nextInt(28)));
            g2.fillOval(ax, ay, ar, ar);
        }

        g2.setPaint(new RadialGradientPaint(
                new Point2D.Float(W / 2f, cy), sw * 0.46f,
                new float[]{0f, 1f},
                new Color[]{new Color(255, 230, 140, 38), new Color(255, 200, 80, 0)}));
        g2.fill(body);

        g2.setPaint(new LinearGradientPaint(sx, sy, sx, sy + 28,
                new float[]{0f, 0.4f, 1f},
                new Color[]{new Color(110, 72, 18, 175), new Color(148, 108, 44, 90), new Color(148, 108, 44, 0)}));
        g2.fill(new RoundRectangle2D.Float(sx, sy, sw, 28, 18, 18));

        g2.setPaint(new LinearGradientPaint(sx, sy+sh-28, sx, sy+sh,
                new float[]{0f, 0.6f, 1f},
                new Color[]{new Color(148, 108, 44, 0), new Color(148, 108, 44, 90), new Color(110, 72, 18, 175)}));
        g2.fill(new RoundRectangle2D.Float(sx, sy+sh-28, sw, 28, 18, 18));

        g2.setStroke(new BasicStroke(0.9f));
        for (int i = 1; i <= 7; i++) {
            g2.setColor(new Color(55, 22, 2, 48 - i*6));
            g2.draw(new RoundRectangle2D.Float(sx+i, sy+i, sw-i*2, sh-i*2, 18-i, 18-i));
        }

        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(new Color(115, 68, 14, 105));
        g2.draw(new RoundRectangle2D.Float(sx+11, sy+11, sw-22, sh-22, 10, 10));
        g2.setColor(new Color(115, 68, 14, 55));
        g2.draw(new RoundRectangle2D.Float(sx+14, sy+14, sw-28, sh-28, 8, 8));

        g2.setStroke(new BasicStroke(2.5f));
        g2.setColor(new Color(80, 38, 2, 230));
        g2.draw(body);

        paintRod(g2, sx - 28, sy - 12, sh + 24);
        paintRod(g2, sx + sw + 12, sy - 12, sh + 24);

        paintWaxSeal(g2, sx + 18, sy + 18);
        paintWaxSeal(g2, sx + sw - 36, sy + 18);
        paintWaxSeal(g2, sx + 18, sy + sh - 36);
        paintWaxSeal(g2, sx + sw - 36, sy + sh - 36);
    }

    // ── Gold rod ──────────────────────────────────────────────
    private void paintRod(Graphics2D g2, int rx, int ry, int rh) {
        g2.setPaint(new LinearGradientPaint(rx, ry, rx + 18, ry,
                new float[]{0f, 0.25f, 0.5f, 0.75f, 1f},
                new Color[]{
                        new Color(100, 66, 8), new Color(238, 195, 58),
                        new Color(255, 222, 88), new Color(200, 148, 28), new Color(95, 58, 6)
                }));
        RoundRectangle2D rod = new RoundRectangle2D.Float(rx, ry, 18, rh, 9, 9);
        g2.fill(rod);

        g2.setStroke(new BasicStroke(0.5f));
        g2.setColor(new Color(80, 45, 4, 40));
        for (int ly = ry + 28; ly < ry + rh - 28; ly += 12) g2.drawLine(rx+3, ly, rx+15, ly);

        g2.setStroke(new BasicStroke(1.2f));
        g2.setColor(new Color(255, 240, 140, 55));
        g2.drawLine(rx+9, ry+18, rx+9, ry+rh-18);

        g2.setStroke(new BasicStroke(1.3f));
        g2.setColor(new Color(72, 36, 0, 210));
        g2.draw(rod);

        for (int fy : new int[]{ry - 4, ry + rh - 18}) {
            g2.setPaint(new RadialGradientPaint(rx + 7, fy + 9, 13,
                    new float[]{0f, 0.5f, 1f},
                    new Color[]{new Color(255, 245, 160), new Color(238, 188, 50), new Color(120, 72, 4)}));
            g2.fillOval(rx - 4, fy, 26, 22);
            g2.setStroke(new BasicStroke(1.3f));
            g2.setColor(new Color(72, 36, 0, 215));
            g2.drawOval(rx - 4, fy, 26, 22);
            g2.setColor(new Color(255, 235, 100, 180));
            g2.fillOval(rx + 5, fy + 7, 6, 6);
        }
    }

    // ── Wax seal ornament ─────────────────────────────────────
    private void paintWaxSeal(Graphics2D g2, int rx, int ry) {
        int cx = rx + 9, cy = ry + 9;
        g2.setStroke(new BasicStroke(1.4f));
        g2.setColor(new Color(80, 40, 8, 125));
        g2.drawOval(rx, ry, 18, 18);
        g2.setColor(new Color(80, 40, 8, 75));
        g2.drawOval(rx+3, ry+3, 12, 12);
        g2.setStroke(new BasicStroke(1.2f));
        g2.setColor(new Color(80, 40, 8, 115));
        g2.drawLine(cx, ry+2, cx, ry+16);
        g2.drawLine(rx+2, cy, rx+16, cy);
        g2.setColor(new Color(80, 40, 8, 70));
        g2.drawLine(rx+4, ry+4, rx+14, ry+14);
        g2.drawLine(rx+14, ry+4, rx+4, ry+14);
        g2.setColor(new Color(90, 50, 10, 120));
        g2.fillOval(cx-2, cy-2, 5, 5);
    }

    // ── Title ─────────────────────────────────────────────────
    private void paintTitle(Graphics2D g2, int cy) {
        String text = "Curse of Zed";
        g2.setFont(rbTitle);
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv  = rbTitle.createGlyphVector(frc, text);
        Rectangle2D vis = gv.getVisualBounds();
        int tw = (int) vis.getWidth();
        int tx = (W - tw) / 2 - (int) vis.getX();
        int ty = cy + (int)(vis.getHeight() / 2) - 2;

        g2.setFont(rbTitle);
        g2.setColor(new Color(0, 0, 0, 185));
        g2.drawString(text, tx + 5, ty + 7);
        g2.setColor(new Color(0, 0, 0, 75));
        g2.drawString(text, tx + 10, ty + 13);

        g2.setFont(rbTitle);
        g2.setPaint(new LinearGradientPaint(
                tx, ty - (int)vis.getHeight(),
                tx, ty + 10,
                new float[]{0f, 0.12f, 0.32f, 0.52f, 0.72f, 0.88f, 1f},
                new Color[]{
                        new Color(255, 252, 210), new Color(252, 218,  72),
                        new Color(218, 138,  18), new Color(108,  48,   0),
                        new Color(198, 112,   4), new Color(245, 198,  48),
                        new Color(228, 168,  28)
                }));
        g2.drawString(text, tx, ty);

        float bandW = 110f;
        float bandX = tx + shimmer * (tw + bandW) - bandW;
        Shape savedClip = g2.getClip();
        g2.clip(gv.getOutline(tx, ty));
        g2.setPaint(new LinearGradientPaint(bandX, 0, bandX + bandW, 0,
                new float[]{0f, 0.35f, 0.5f, 0.65f, 1f},
                new Color[]{
                        new Color(255, 248, 200,   0), new Color(255, 248, 200,  85),
                        new Color(255, 255, 255, 215), new Color(255, 248, 200,  85),
                        new Color(255, 248, 200,   0)
                }));
        g2.fill(new Rectangle2D.Float(bandX, ty-(int)vis.getHeight()-6, bandW, (int)vis.getHeight()+18));
        g2.setClip(savedClip);
    }

    // ── Button shape drawn into the pixel buffer ──────────────
    // byBase is the un-offset Y; hover/press offsets are applied inside,
    // exactly matching the original single-button logic.
    private void paintButtonShape(Graphics2D g2, int byBase, boolean hover, boolean press) {
        int bx = (W - BTN_W) / 2;
        int by = byBase;
        if (press)      { bx += 3; by += 3; }
        else if (hover) { bx -= 2; by -= 2; }

        int c = 8;
        Polygon oct = oct(bx, by, BTN_W, BTN_H, c);

        // Amber glow halo — identical to original
        float ga = 0.25f + btnPulse * 0.38f;
        if (hover) ga = 0.65f;
        AlphaComposite ac = (AlphaComposite) g2.getComposite();
        for (int i = 16; i >= 2; i -= 2) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ga * 0.055f));
            g2.setColor(new Color(205, 148, 12));
            g2.fill(oct(bx-i, by-i, BTN_W+i*2, BTN_H+i*2, c));
        }
        g2.setComposite(ac);

        // Face gradient — identical to original
        Color tc = hover ? new Color(255,255,208) : new Color(252,240,122);
        Color mc = hover ? new Color(250,222, 62) : new Color(238,190, 28);
        Color bc = hover ? new Color(212,148, 12) : new Color(178,108,  0);
        g2.setPaint(new LinearGradientPaint(bx, by, bx, by+BTN_H,
                new float[]{0f, .22f, .55f, 1f},
                new Color[]{tc, mc, bc, new Color(208, 155, 12)}));
        g2.fill(oct);

        // Top sheen
        g2.setColor(new Color(255, 255, 215, 155));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawLine(bx+c, by+1, bx+BTN_W-c, by+1);

        // Border
        g2.setStroke(new BasicStroke(2.3f));
        g2.setColor(new Color(82, 38, 0, 215));
        g2.draw(oct);
    }

    // ── Button label at full resolution — identical to original ─
    // byBase is the un-offset Y; hover/press offsets applied to match shape.
    private void paintButtonLabel(Graphics2D g2, String label, int byBase,
                                  boolean hover, boolean press, boolean showArrow) {
        int bx = (W - BTN_W) / 2;
        int by = byBase;
        if (press)      { bx += 3; by += 3; }
        else if (hover) { bx -= 2; by -= 2; }

        // Blinking ▶ — now on both buttons (showArrow always true in our calls)
        if (showArrow && Math.sin(blinkPhase) > 0) {
            g2.setFont(new Font("Monospaced", Font.BOLD, 13));
            g2.setColor(new Color(48, 14, 0, 220));
            g2.drawString("\u25BA", bx + 15, by + BTN_H / 2 + 5);//5
        }

        g2.setFont(rbBtn);
        FontMetrics fm = g2.getFontMetrics(rbBtn);
        int lw = fm.stringWidth(label);
        int lx = bx + (BTN_W - lw) / 2;
        int ly = by + (BTN_H + fm.getAscent() - fm.getDescent()) / 2;
        // Subtle shadow
        g2.setColor(new Color(255, 215, 70, 88));
        g2.drawString(label, lx + 1, ly + 1);
        // Main ink
        g2.setColor(new Color(42, 12, 0));
        g2.drawString(label, lx, ly);
    }

    // ── Credits overlay screen ────────────────────────────────
    private void paintCreditsScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, W, H);

        int cw = 820, ch = 617;
        int cy = H / 2;
        paintScrollEx(g2, cy, cw, ch);

        int sx = (W - cw) / 2;
        int sy = cy - ch / 2;

        // Heading
        Font headFont = rbTitle.deriveFont(Font.PLAIN, 42f);
        g2.setFont(headFont);
        FontMetrics hfm = g2.getFontMetrics(headFont);
        String head = "CURSE OF ZED";
        g2.setColor(new Color(0, 0, 0, 120));
        g2.drawString(head, sx + (cw - hfm.stringWidth(head)) / 2 + 2, sy + 82);
        g2.setPaint(new LinearGradientPaint(
                sx, sy + 40, sx, sy + 90,
                new float[]{0f, 0.5f, 1f},
                new Color[]{new Color(255, 252, 210), new Color(238, 190, 28), new Color(178, 108, 0)}));
        g2.drawString(head, sx + (cw - hfm.stringWidth(head)) / 2, sy + 80);

        g2.setColor(new Color(115, 68, 14, 140));
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawLine(sx + 60, sy + 95, sx + cw - 60, sy + 95);

        Font roleFont = rbBtn.deriveFont(Font.PLAIN, 13f);
        Font nameFont = rbBtn.deriveFont(Font.PLAIN, 20f);

        String[][] lines = {
                {"PROJECT MANAGER, GAME DEVELOPER",    "Zedjy Vargas"},
                {"GAME DEVELOPERS",      "Reniel Bogoy, Zeny Marzan, Frank Rosero, Sharlene Sanjorjo"},
                {"ASSETS AI RECOGNITION", "Sprite Labs and Gemini"},
                {"CLAUDE AI ASSETS ASSISTANCE", "NPC Portraits/Maps/Cutscenes"},
                {"AUDIO & SFX", "Good Kid, randy dominguez, RoyaltyFree, BreakingCopyright"},
                {"",                  ""},
                {"PRESENTED TO",         "Kenn Migan Vincent Gumonan, PhD"}
        };

        int entryY = sy + 140;
        for (String[] pair : lines) {
            if (pair[0].isEmpty() && pair[1].isEmpty()) { entryY += 20; continue; }

            g2.setFont(roleFont);
            g2.setColor(new Color(115, 68, 14, 200));
            g2.drawString(pair[0], sx + 100, entryY);

            g2.setFont(nameFont);
            g2.setColor(new Color(255, 200, 50, 45));
            g2.drawString(pair[1], sx + 102, entryY + 27);
            g2.setColor(new Color(42, 12, 0));
            g2.drawString(pair[1], sx + 100, entryY + 25);

            entryY += 72;
        }

        // Blinking close hint
        int alpha = (int)(150 + 100 * Math.sin(blinkPhase));
        g2.setFont(rbBtn.deriveFont(Font.PLAIN, 14f));
        g2.setColor(new Color(80, 40, 0, alpha));
        String hint = "- CLICK ANYWHERE TO CLOSE -";
        FontMetrics hm = g2.getFontMetrics();
        g2.drawString(hint, sx + (cw - hm.stringWidth(hint)) / 2, sy + ch - 38);
    }

    // ── Extended scroll (credits overlay) ─────────────────────
    private void paintScrollEx(Graphics2D g2, int cy, int sw, int sh) {
        int sx = (W - sw) / 2;
        int sy = cy - sh / 2;

        for (int i = 8; i >= 1; i--) {
            float a = 0.06f * (9 - i);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
            g2.setColor(new Color(20, 8, 0));
            g2.fill(new RoundRectangle2D.Float(sx-i, sy+i*2, sw+i*2, sh, 18, 18));
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        g2.setPaint(new LinearGradientPaint(sx, sy, sx, sy + sh,
                new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                new Color[]{
                        new Color(148, 108,  44), new Color(192, 152,  78),
                        new Color(220, 186, 118), new Color(238, 212, 152),
                        new Color(220, 186, 118), new Color(192, 152,  78),
                        new Color(148, 108,  44)
                }));
        Shape body = new RoundRectangle2D.Float(sx, sy, sw, sh, 18, 18);
        g2.fill(body);

        g2.setStroke(new BasicStroke(0.5f));
        for (int ly = sy + 16; ly < sy + sh - 14; ly += 6) {
            int a = 14 + (int)(6 * Math.sin((ly - sy) * 0.4));
            g2.setColor(new Color(100, 60, 12, a));
            g2.drawLine(sx + 16, ly, sx + sw - 16, ly);
        }

        g2.setStroke(new BasicStroke(0.4f));
        Random vr = new Random(77);
        for (int i = 0; i < 12; i++) {
            int vx = sx + 60 + vr.nextInt(sw - 120);
            int vlen = 20 + vr.nextInt(80);
            int vy = sy + 20 + vr.nextInt(Math.max(1, sh - 40 - vlen));
            g2.setColor(new Color(88, 50, 10, 10 + vr.nextInt(12)));
            g2.drawLine(vx, vy, vx + vr.nextInt(6) - 3, vy + vlen);
        }

        Random sr = new Random(42);
        for (int i = 0; i < 50; i++) {
            int ax = sx + 40 + sr.nextInt(sw - 80);
            int ay = sy + 20 + sr.nextInt(sh - 40);
            int ar = 1 + sr.nextInt(5);
            g2.setColor(new Color(82, 44, 8, 10 + sr.nextInt(28)));
            g2.fillOval(ax, ay, ar, ar);
        }

        g2.setPaint(new RadialGradientPaint(
                new Point2D.Float(W / 2f, cy), sw * 0.46f,
                new float[]{0f, 1f},
                new Color[]{new Color(255, 230, 140, 38), new Color(255, 200, 80, 0)}));
        g2.fill(body);

        g2.setPaint(new LinearGradientPaint(sx, sy, sx, sy + 28,
                new float[]{0f, 0.4f, 1f},
                new Color[]{new Color(110, 72, 18, 175), new Color(148, 108, 44, 90), new Color(148, 108, 44, 0)}));
        g2.fill(new RoundRectangle2D.Float(sx, sy, sw, 28, 18, 18));

        g2.setPaint(new LinearGradientPaint(sx, sy+sh-28, sx, sy+sh,
                new float[]{0f, 0.6f, 1f},
                new Color[]{new Color(148, 108, 44, 0), new Color(148, 108, 44, 90), new Color(110, 72, 18, 175)}));
        g2.fill(new RoundRectangle2D.Float(sx, sy+sh-28, sw, 28, 18, 18));

        g2.setStroke(new BasicStroke(0.9f));
        for (int i = 1; i <= 7; i++) {
            g2.setColor(new Color(55, 22, 2, 48 - i*6));
            g2.draw(new RoundRectangle2D.Float(sx+i, sy+i, sw-i*2, sh-i*2, 18-i, 18-i));
        }

        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(new Color(115, 68, 14, 105));
        g2.draw(new RoundRectangle2D.Float(sx+11, sy+11, sw-22, sh-22, 10, 10));
        g2.setColor(new Color(115, 68, 14, 55));
        g2.draw(new RoundRectangle2D.Float(sx+14, sy+14, sw-28, sh-28, 8, 8));

        g2.setStroke(new BasicStroke(2.5f));
        g2.setColor(new Color(80, 38, 2, 230));
        g2.draw(body);

        paintRod(g2, sx - 28, sy - 12, sh + 24);
        paintRod(g2, sx + sw + 12, sy - 12, sh + 24);

        paintWaxSeal(g2, sx + 18, sy + 18);
        paintWaxSeal(g2, sx + sw - 36, sy + 18);
        paintWaxSeal(g2, sx + 18, sy + sh - 36);
        paintWaxSeal(g2, sx + sw - 36, sy + sh - 36);
    }

    // ── Side particles ───────────────────────────────────────
    private void spawnParticle() {
        boolean left = rng.nextFloat() < 0.52f;
        float x = left
                ? rng.nextFloat() * W * 0.30f
                : W * 0.70f + rng.nextFloat() * W * 0.30f;
        float y = H * 0.55f + rng.nextFloat() * H * 0.40f;
        float dx = (rng.nextFloat() - 0.5f) * 1.0f;
        float dy = -(0.4f + rng.nextFloat() * 1.6f);
        Color[] lc = {new Color(60,255,160), new Color(55,200,255), new Color(120,255,60)};
        Color[] rc = {new Color(185,60,255),  new Color(255,60,195), new Color(110,30,255)};
        Color col = (left ? lc : rc)[rng.nextInt(3)];
        float life = 100 + rng.nextFloat() * 180;
        particles.add(new Particle(x, y, dx, dy, col, life));
    }

    private void paintParticles(Graphics2D g2) {
        AlphaComposite ac = (AlphaComposite) g2.getComposite();
        for (Particle p : particles) {
            float a = Math.min(1f, p.life / 35f)
                    * Math.min(1f, (p.maxLife - p.life) / 25f);
            a = Math.max(0f, a) * 0.80f;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
            g2.setColor(p.color);
            int sz = (p.life > p.maxLife * 0.6f) ? 4 : 3;
            g2.fillRect((int)p.x, (int)p.y, sz, sz);
        }
        g2.setComposite(ac);
    }

    // ── Leaderboard overlay ──────────────────────────────────
    private void openLeaderboard() {
        JFrame frame = (parentFrame != null) ? parentFrame
                     : (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame == null) return;

        TitlePanel self = this;
        LeaderboardViewPanel lbPanel = new LeaderboardViewPanel(frame, () -> SwingUtilities.invokeLater(() -> {
            frame.getContentPane().removeAll();
            frame.add(self);
            frame.revalidate();
            frame.repaint();
            self.requestFocusInWindow();
        }));

        frame.getContentPane().removeAll();
        frame.add(lbPanel);
        frame.revalidate();
        frame.repaint();
    }

    // ── Octagon helper ───────────────────────────────────────
    private Polygon oct(int x, int y, int w, int h, int c) {
        return new Polygon(
                new int[]{x+c, x+w-c, x+w, x+w,   x+w-c, x+c, x,   x},
                new int[]{y,   y,     y+c, y+h-c,  y+h,   y+h, y+h-c, y+c},
                8);
    }
}