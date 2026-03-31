package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.font.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Curse of Zed — Title Screen
 * Java Swing  |  Ringbearer Medium  |  Vintage RPG
 *
 * Place "RingbearerMedium.ttf" and "background.jpg"
 * in the project root folder (next to src/).
 * Font: https://www.dafont.com/ringbearer.font
 */
public class CurseOfZed extends JFrame {


    public static void main(String[] args) {

                SwingUtilities.invokeLater(() -> {
                    // 1. Initialize the window
                    CurseOfZed window = new CurseOfZed();

                    // 2. Initialize the GamePanel (your map/player logic)
                    GamePanel gamePanel = new GamePanel();

                    // 3. TELL THE BUTTON WHAT TO DO
                    window.setOnStartCallback(() -> {
                        window.getContentPane().removeAll(); // Clear the Title Screen
                        window.add(gamePanel);               // Add your Game World
                        window.revalidate();                 // Refresh the UI
                        window.pack();                       // Resize to fit the 16x12 tiles

                        gamePanel.setupGame();
                        gamePanel.requestFocusInWindow();    // Allow keyboard control
                        gamePanel.startGameThread();         // Start the 60 FPS loop
                    });

                    window.setVisible(true);
                });
            }


    public void setOnStartCallback(Runnable callback) {
        this.onStartCallback = callback;
    }
    private Runnable onStartCallback;

    public CurseOfZed() {
        setTitle("Curse of Zed");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setResizable(false);
        TitlePanel p = new TitlePanel();
        p.setOnStartCallback(() -> {
            if (this.onStartCallback != null) {
                this.onStartCallback.run();
            }
        });
        add(p);
        pack();
        setLocationRelativeTo(null);
    }
}

// ═════════════════════════════════════════════════════════════
//  Main Panel
// ═════════════════════════════════════════════════════════════
class TitlePanel extends JPanel {
    public static GamePanel gm = new GamePanel();
    private Runnable onStartCallback;

    public void setOnStartCallback(Runnable callback) {
        this.onStartCallback = callback;
    }
    static final int W = gm.screenWidth, H = gm.screenHeight;

    // ── Assets ───────────────────────────────────────────────
    private BufferedImage bgImage;
    private Font rbTitle, rbBtn, rbTitlePx, rbBtnPx;

    // ── Animation ────────────────────────────────────────────
    private float shimmer    =  1.4f;          // R→L sweep (counts DOWN)
    private float floatY     =  0f, floatDir   =  1f;
    private float scrollBob  =  0f, bobDir     =  1f;
    private float btnPulse   =  0f, pulseDir   =  1f;
    private float blinkPhase =  0f;

    // ── Particles (left = green/cyan, right = purple/pink) ───
    private final List<Particle> particles = new ArrayList<>();
    private final Random rng = new Random();

    // ── Button state ─────────────────────────────────────────
    private boolean hover = false, press = false;
    private final Rectangle btnRect = new Rectangle();

    // ── Constructor ──────────────────────────────────────────
    TitlePanel() {
        setPreferredSize(new Dimension(W, H));
        setOpaque(true);
        loadAssets();

        addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                if (btnRect.contains(e.getPoint())) {
                    press = true;
                    if (onStartCallback != null) {
                        onStartCallback.run();
                    }
                }
            }
            @Override public void mouseReleased(MouseEvent e) {
                press = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override public void mouseMoved(MouseEvent e) {
                hover = btnRect.contains(e.getPoint());
                setCursor(hover
                    ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                    : Cursor.getDefaultCursor());
            }
        });

        new Timer(16, e -> { tick(); repaint(); }).start();
    }

    // ── Load font + image ────────────────────────────────────
    private void loadAssets() {
        try { bgImage = ImageIO.read(new File("background.jpg")); }
        catch (IOException e) { System.err.println("background.jpg not found"); }

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
        rbTitle = base.deriveFont(Font.PLAIN, 72f);
        rbBtn   = base.deriveFont(Font.PLAIN, 17f);
        // Larger versions for pixel buffer rendering (drawn at 2x then downscaled)
        rbTitlePx = base.deriveFont(Font.PLAIN, 80f);
        rbBtnPx   = base.deriveFont(Font.PLAIN, 30f);
    }

    // ── Tick ─────────────────────────────────────────────────
    private void tick() {
        // Title float
        floatY += 0.042f * floatDir;
        if (floatY >  6f) floatDir = -1f;
        if (floatY < -6f) floatDir =  1f;

        // Scroll bob (slightly offset)
        scrollBob += 0.026f * bobDir;
        if (scrollBob >  4f) bobDir = -1f;
        if (scrollBob < -4f) bobDir =  1f;

        // Shimmer RIGHT → LEFT
        shimmer -= 0.0055f;
        if (shimmer < -0.4f) shimmer = 1.4f;

        // Button pulse
        btnPulse += 0.022f * pulseDir;
        if (btnPulse > 1f) pulseDir = -1f;
        if (btnPulse < 0f) pulseDir =  1f;

        // Cursor blink
        blinkPhase += 0.038f;
        if (blinkPhase > (float)(Math.PI * 2)) blinkPhase -= (float)(Math.PI * 2);

        // Particles
        if (rng.nextFloat() < 0.30f) spawnParticle();
        particles.removeIf(p -> !p.alive());
        particles.forEach(Particle::update);
    }

    // ── Paint ────────────────────────────────────────────────
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Background, vignette, particles — full res, as-is
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        paintBg(g2);
        paintVignette(g2);
        paintParticles(g2);

        // Render scroll + title + button into half-res buffer
        // Text uses 2x fonts so letterforms stay legible after upscale
        int SCALE = 2;
        int lw = W / SCALE, lh = H / SCALE;
        BufferedImage buf = new BufferedImage(lw, lh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D lg = buf.createGraphics();
        hintPixel(lg);
        lg.scale(1.0 / SCALE, 1.0 / SCALE);

        int scrollCY = (int)(H * 0.24f + scrollBob);
        paintScroll(lg, scrollCY);
        // Swap to pixel-sized fonts for the buffer pass
        Font savedTitle = rbTitle, savedBtn = rbBtn;
        rbTitle = rbTitlePx;
        rbBtn   = rbBtnPx;
        paintTitle(lg, scrollCY + (int) floatY);
        paintButton(lg, scrollCY);
        paintButtonLabel(lg, scrollCY);
        rbTitle = savedTitle;
        rbBtn   = savedBtn;
        lg.dispose();

        // Upscale with nearest-neighbor — creates chunky pixel blocks
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(buf, 0, 0, W, H, null);

        g2.dispose();
    }

    private void hint(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,     RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
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
        if (bgImage == null) { g2.setColor(new Color(10,5,20)); g2.fillRect(0,0,W,H); return; }
        double ia = (double) bgImage.getWidth() / bgImage.getHeight();
        double pa = (double) W / H;
        int bw, bh;
        if (ia > pa) { bh = H; bw = (int)(H * ia); }
        else         { bw = W; bh = (int)(W / ia); }
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(bgImage, (W-bw)/2, (H-bh)/2, bw, bh, null);
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
    //  IMPROVED PARCHMENT SCROLL
    // ══════════════════════════════════════════════════════════
    private void paintScroll(Graphics2D g2, int cy) {
        int sw = 650, sh = 115;
        int sx = (W - sw) / 2;
        int sy = cy - sh / 2;

        // ── 1. Outer worn shadow (gives the scroll depth & lift) ──
        for (int i = 8; i >= 1; i--) {
            float a = 0.06f * (9 - i);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
            g2.setColor(new Color(20, 8, 0));
            g2.fill(new RoundRectangle2D.Float(sx-i, sy+i*2, sw+i*2, sh, 18, 18));
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // ── 2. Main parchment body ──
        // Multi-stop gradient: darker worn edges, warm creamy centre
        g2.setPaint(new LinearGradientPaint(sx, sy, sx, sy + sh,
            new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
            new Color[]{
                new Color(148, 108,  44),   // very dark top edge
                new Color(192, 152,  78),   // worn tan
                new Color(220, 186, 118),   // warm parchment
                new Color(238, 212, 152),   // bright creamy centre
                new Color(220, 186, 118),   // back to warm
                new Color(192, 152,  78),   // worn tan
                new Color(148, 108,  44)    // very dark bottom edge
            }));
        Shape body = new RoundRectangle2D.Float(sx, sy, sw, sh, 18, 18);
        g2.fill(body);

        // ── 3. Horizontal paper fibre / grain ──
        g2.setStroke(new BasicStroke(0.5f));
        for (int ly = sy + 16; ly < sy + sh - 14; ly += 6) {
            // Vary alpha slightly for organic look
            int a = 14 + (int)(6 * Math.sin((ly - sy) * 0.4));
            g2.setColor(new Color(100, 60, 12, a));
            g2.drawLine(sx + 16, ly, sx + sw - 16, ly);
        }

        // ── 4. Subtle vertical vein lines (real parchment has these) ──
        g2.setStroke(new BasicStroke(0.4f));
        Random vr = new Random(77);
        for (int i = 0; i < 8; i++) {
            int vx = sx + 60 + vr.nextInt(sw - 120);
            int vlen = 20 + vr.nextInt(60);
            int vy = sy + 20 + vr.nextInt(sh - 40 - vlen);
            g2.setColor(new Color(88, 50, 10, 10 + vr.nextInt(12)));
            g2.drawLine(vx, vy, vx + vr.nextInt(6) - 3, vy + vlen);
        }

        // ── 5. Age spots (foxing) ──
        Random sr = new Random(42);
        for (int i = 0; i < 28; i++) {
            int ax = sx + 40 + sr.nextInt(sw - 80);
            int ay = sy + 20 + sr.nextInt(sh - 40);
            int ar = 1 + sr.nextInt(5);
            g2.setColor(new Color(82, 44, 8, 10 + sr.nextInt(28)));
            g2.fillOval(ax, ay, ar, ar);
        }

        // ── 6. Warm inner glow (parchment glows from within) ──
        g2.setPaint(new RadialGradientPaint(
            new Point2D.Float(W / 2f, cy),
            sw * 0.46f,
            new float[]{0f, 1f},
            new Color[]{new Color(255, 230, 140, 38), new Color(255, 200, 80, 0)}));
        g2.fill(body);

        // ── 7. Rolled-edge curl: top ──
        // The top edge curves away — painted as a darker strip with gradient
        g2.setPaint(new LinearGradientPaint(sx, sy, sx, sy + 28,
            new float[]{0f, 0.4f, 1f},
            new Color[]{
                new Color(110, 72, 18, 175),
                new Color(148, 108, 44, 90),
                new Color(148, 108, 44, 0)
            }));
        g2.fill(new RoundRectangle2D.Float(sx, sy, sw, 28, 18, 18));

        // ── 8. Rolled-edge curl: bottom ──
        g2.setPaint(new LinearGradientPaint(sx, sy+sh-28, sx, sy+sh,
            new float[]{0f, 0.6f, 1f},
            new Color[]{
                new Color(148, 108, 44, 0),
                new Color(148, 108, 44, 90),
                new Color(110, 72, 18, 175)
            }));
        g2.fill(new RoundRectangle2D.Float(sx, sy+sh-28, sw, 28, 18, 18));

        // ── 9. Inner shadow rings (depth from rolled edges) ──
        g2.setStroke(new BasicStroke(0.9f));
        for (int i = 1; i <= 7; i++) {
            g2.setColor(new Color(55, 22, 2, 48 - i*6));
            g2.draw(new RoundRectangle2D.Float(sx+i, sy+i, sw-i*2, sh-i*2, 18-i, 18-i));
        }

        // ── 10. Decorative border: double ruled line ──
        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(new Color(115, 68, 14, 105));
        g2.draw(new RoundRectangle2D.Float(sx+11, sy+11, sw-22, sh-22, 10, 10));
        g2.setColor(new Color(115, 68, 14, 55));
        g2.draw(new RoundRectangle2D.Float(sx+14, sy+14, sw-28, sh-28, 8, 8));

        // ── 11. Outer hard border ──
        g2.setStroke(new BasicStroke(2.5f));
        g2.setColor(new Color(80, 38, 2, 230));
        g2.draw(body);

        // ── 12. Gold rods ──
        paintRod(g2, sx - 28, sy - 12, sh + 24);
        paintRod(g2, sx + sw + 12, sy - 12, sh + 24);

        // ── 13. Wax seal ornaments at corners (rune + circle) ──
        paintWaxSeal(g2, sx + 18, sy + 18);
        paintWaxSeal(g2, sx + sw - 36, sy + 18);
        paintWaxSeal(g2, sx + 18, sy + sh - 36);
        paintWaxSeal(g2, sx + sw - 36, sy + sh - 36);
    }

    // ── Gold rod ──────────────────────────────────────────────
    private void paintRod(Graphics2D g2, int rx, int ry, int rh) {
        // Rod body — radial left→right gold sheen
        g2.setPaint(new LinearGradientPaint(rx, ry, rx + 18, ry,
            new float[]{0f, 0.25f, 0.5f, 0.75f, 1f},
            new Color[]{
                new Color(100, 66, 8),
                new Color(238, 195, 58),
                new Color(255, 222, 88),
                new Color(200, 148, 28),
                new Color(95, 58, 6)
            }));
        RoundRectangle2D rod = new RoundRectangle2D.Float(rx, ry, 18, rh, 9, 9);
        g2.fill(rod);

        // Subtle knurl lines
        g2.setStroke(new BasicStroke(0.5f));
        g2.setColor(new Color(80, 45, 4, 40));
        for (int ly = ry + 28; ly < ry + rh - 28; ly += 12) g2.drawLine(rx+3, ly, rx+15, ly);

        // Highlight line down the rod centre
        g2.setStroke(new BasicStroke(1.2f));
        g2.setColor(new Color(255, 240, 140, 55));
        g2.drawLine(rx+9, ry+18, rx+9, ry+rh-18);

        // Border
        g2.setStroke(new BasicStroke(1.3f));
        g2.setColor(new Color(72, 36, 0, 210));
        g2.draw(rod);

        // Top & bottom finials
        for (int fy : new int[]{ry - 4, ry + rh - 18}) {
            g2.setPaint(new RadialGradientPaint(rx + 7, fy + 9, 13,
                new float[]{0f, 0.5f, 1f},
                new Color[]{new Color(255, 245, 160), new Color(238, 188, 50), new Color(120, 72, 4)}));
            g2.fillOval(rx - 4, fy, 26, 22);
            g2.setStroke(new BasicStroke(1.3f));
            g2.setColor(new Color(72, 36, 0, 215));
            g2.drawOval(rx - 4, fy, 26, 22);
            // Small gem dot in finial
            g2.setColor(new Color(255, 235, 100, 180));
            g2.fillOval(rx + 5, fy + 7, 6, 6);
        }
    }

    // ── Wax seal ornament ─────────────────────────────────────
    private void paintWaxSeal(Graphics2D g2, int rx, int ry) {
        int cx = rx + 9, cy = ry + 9;
        // Outer ring
        g2.setStroke(new BasicStroke(1.4f));
        g2.setColor(new Color(80, 40, 8, 125));
        g2.drawOval(rx, ry, 18, 18);
        // Inner ring
        g2.setColor(new Color(80, 40, 8, 75));
        g2.drawOval(rx+3, ry+3, 12, 12);
        // Cross lines
        g2.setStroke(new BasicStroke(1.2f));
        g2.setColor(new Color(80, 40, 8, 115));
        g2.drawLine(cx, ry+2, cx, ry+16);
        g2.drawLine(rx+2, cy, rx+16, cy);
        // Diagonal
        g2.setColor(new Color(80, 40, 8, 70));
        g2.drawLine(rx+4, ry+4, rx+14, ry+14);
        g2.drawLine(rx+14, ry+4, rx+4, ry+14);
        // Centre dot
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

        // Hard drop shadows
        g2.setFont(rbTitle);
        g2.setColor(new Color(0, 0, 0, 185));
        g2.drawString(text, tx + 5, ty + 7);
        g2.setColor(new Color(0, 0, 0, 75));
        g2.drawString(text, tx + 10, ty + 13);

        // Molten gold gradient
        g2.setFont(rbTitle);
        g2.setPaint(new LinearGradientPaint(
            tx, ty - (int)vis.getHeight(),
            tx, ty + 10,
            new float[]{0f, 0.12f, 0.32f, 0.52f, 0.72f, 0.88f, 1f},
            new Color[]{
                new Color(255, 252, 210),
                new Color(252, 218,  72),
                new Color(218, 138,  18),
                new Color(108,  48,   0),
                new Color(198, 112,   4),
                new Color(245, 198,  48),
                new Color(228, 168,  28)
            }));
        g2.drawString(text, tx, ty);

        // Shimmer RIGHT → LEFT
        float bandW = 110f;
        float bandX = tx + shimmer * (tw + bandW) - bandW;
        Shape savedClip = g2.getClip();
        g2.clip(gv.getOutline(tx, ty));
        g2.setPaint(new LinearGradientPaint(bandX, 0, bandX + bandW, 0,
            new float[]{0f, 0.35f, 0.5f, 0.65f, 1f},
            new Color[]{
                new Color(255, 248, 200,   0),
                new Color(255, 248, 200,  85),
                new Color(255, 255, 255, 215),
                new Color(255, 248, 200,  85),
                new Color(255, 248, 200,   0)
            }));
        g2.fill(new Rectangle2D.Float(bandX, ty-(int)vis.getHeight()-6, bandW, (int)vis.getHeight()+18));
        g2.setClip(savedClip);
    }

    // ── Button shape (pixelated) ──────────────────────────────
    private void paintButton(Graphics2D g2, int scrollCY) {
        int bw = 240, bh = 45;
        int bx = (W - bw) / 2, by = (H - bh) / 2;
        if (press)      { bx += 3; by += 3; }
        else if (hover) { bx -= 2; by -= 2; }
        btnRect.setBounds(bx, by, bw, bh);

        int c = 8;
        Polygon oct = oct(bx, by, bw, bh, c);

        // Amber glow halo
        float ga = 0.25f + btnPulse * 0.38f;
        if (hover) ga = 0.65f;
        AlphaComposite ac = (AlphaComposite) g2.getComposite();
        for (int i = 16; i >= 2; i -= 2) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ga * 0.055f));
            g2.setColor(new Color(205, 148, 12));
            g2.fill(oct(bx-i, by-i, bw+i*2, bh+i*2, c));
        }
        g2.setComposite(ac);

        // Face gradient
        Color tc = hover ? new Color(255,255,208) : new Color(252,240,122);
        Color mc = hover ? new Color(250,222, 62) : new Color(238,190, 28);
        Color bc = hover ? new Color(212,148, 12) : new Color(178,108,  0);
        g2.setPaint(new LinearGradientPaint(bx, by, bx, by+bh,
            new float[]{0f, .22f, .55f, 1f},
            new Color[]{tc, mc, bc, new Color(208, 155, 12)}));
        g2.fill(oct);

        // Top sheen
        g2.setColor(new Color(255, 255, 215, 155));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawLine(bx+c, by+1, bx+bw-c, by+1);

        // Border
        g2.setStroke(new BasicStroke(2.3f));
        g2.setColor(new Color(82, 38, 0, 215));
        g2.draw(oct);
    }

    // ── Button label drawn at full res for crisp readable text ─
    private void paintButtonLabel(Graphics2D g2, int scrollCY) {
        int bw = 240, bh = 45;
        int bx = (W - bw) / 2, by = (H - bh) / 2;
        if (press)      { bx += 3; by += 3; }
        else if (hover) { bx -= 2; by -= 2; }

        // Blinking ▶
        if (Math.sin(blinkPhase) > 0) {
            g2.setFont(new Font("Monospaced", Font.BOLD, 13));
            g2.setColor(new Color(48, 14, 0, 220));
            g2.drawString("\u25BA", bx+15, by+bh/2+5);
        }

        // Label
        g2.setFont(rbBtn);
        FontMetrics fm = g2.getFontMetrics(rbBtn);
        String label = "Start Game";
        int lw = fm.stringWidth(label);
        int lx = bx + (bw - lw) / 2;
        int ly = by + (bh + fm.getAscent() - fm.getDescent()) / 2;
        g2.setColor(new Color(255, 215, 70, 88));
        g2.drawString(label, lx+1, ly+1);
        g2.setColor(new Color(42, 12, 0));
        g2.drawString(label, lx, ly);
    }

    // ── Side particles ───────────────────────────────────────
    private void spawnParticle() {
        boolean left = rng.nextFloat() < 0.52f;
        float x = left
            ? rng.nextFloat() * W * 0.30f              // left third
            : W * 0.70f + rng.nextFloat() * W * 0.30f; // right third
        float y = H * 0.55f + rng.nextFloat() * H * 0.40f;
        float dx = (rng.nextFloat() - 0.5f) * 1.0f;
        float dy = -(0.4f + rng.nextFloat() * 1.6f);
        // Left: green/teal;  Right: purple/violet
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
            // Vary size slightly for depth
            int sz = (p.life > p.maxLife * 0.6f) ? 4 : 3;
            g2.fillRect((int)p.x, (int)p.y, sz, sz);
        }
        g2.setComposite(ac);
    }

    // ── Octagon helper ───────────────────────────────────────
    private Polygon oct(int x, int y, int w, int h, int c) {
        return new Polygon(
            new int[]{x+c, x+w-c, x+w, x+w,   x+w-c, x+c, x,   x},
            new int[]{y,   y,     y+c, y+h-c,  y+h,   y+h, y+h-c, y+c},
            8);
    }
}

// ═════════════════════════════════════════════════════════════
//  Particle
// ═════════════════════════════════════════════════════════════
class Particle {
    float x, y, dx, dy, life, maxLife;
    Color color;
    Particle(float x, float y, float dx, float dy, Color c, float life) {
        this.x=x; this.y=y; this.dx=dx; this.dy=dy;
        this.color=c; this.life=life; this.maxLife=life;
    }
    void update() { x+=dx; y+=dy; life--; }
    boolean alive() { return life>0; }
}
