package Main;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class MapLabel {

    private static final int W = 1024;

    // Animation
    private float fadeAlpha = 0f;
    private float shimmer = 1.4f;
    private boolean fadingIn = true;
    private boolean fadingOut = false;
    private int displayTimer = 0;
    private static final int DISPLAY_DURATION = 180; // frames to stay fully visible
    private boolean finished = false;

    // Permanent label (always visible after fade)
    private boolean permanent = true; // set true to always show

    private Font fontLabel;
    private Font fontSub;
    private String mapName;
    private String mapSub; // optional subtitle like "Chapter 1"

    public MapLabel(String mapName, String mapSub) {
        this.mapName = mapName;
        this.mapSub  = mapSub;
        loadFonts();
    }

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
        fontLabel = base.deriveFont(Font.PLAIN, 20f);
        fontSub   = new Font("Serif", Font.ITALIC, 11);
    }

    public void update() {
        if (permanent) {
            fadeAlpha = 1f; // always fully visible
            shimmer -= 0.004f;
            if (shimmer < -0.4f) shimmer = 1.4f;
            return;
        }

        shimmer -= 0.004f;
        if (shimmer < -0.4f) shimmer = 1.4f;

        if (fadingIn) {
            fadeAlpha += 0.02f;
            if (fadeAlpha >= 1f) {
                fadeAlpha = 1f;
                fadingIn  = false;
            }
        } else if (!fadingOut) {
            displayTimer++;
            if (displayTimer >= DISPLAY_DURATION) fadingOut = true;
        } else {
            fadeAlpha -= 0.015f;
            if (fadeAlpha <= 0f) {
                fadeAlpha = 0f;
                finished  = true;
            }
        }
    }

    public boolean isFinished() { return finished && !permanent; }

    public void reset(String newMapName, String newMapSub) {
        this.mapName    = newMapName;
        this.mapSub     = newMapSub;
        fadeAlpha       = 0f;
        shimmer         = 1.4f;
        fadingIn        = true;
        fadingOut       = false;
        displayTimer    = 0;
        finished        = false;
    }

    public void draw(Graphics2D g2) {
        if (fadeAlpha <= 0f) return;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // ── Measure text ──
        g2.setFont(fontLabel);
        FontMetrics fmL = g2.getFontMetrics();
        int labelW = fmL.stringWidth(mapName);

        g2.setFont(fontSub);
        FontMetrics fmS = g2.getFontMetrics();
        int subW = mapSub != null ? fmS.stringWidth(mapSub) : 0;

        int totalW = Math.max(labelW, subW) + 60;
        int totalH = mapSub != null ? 54 : 36;
        int bx = (W - totalW) / 2;
        int by = 12;

        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, fadeAlpha));

        // ── Shadow ──
        for (int i = 8; i >= 1; i--) {
            float a = fadeAlpha * 0.04f * (9 - i);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
            g2.setColor(Color.BLACK);
            g2.fill(new RoundRectangle2D.Float(
                    bx - i, by + i, totalW + i * 2, totalH, 14, 14));
        }
        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, fadeAlpha));

        // ── Dark parchment body ──
        g2.setPaint(new LinearGradientPaint(bx, by, bx, by + totalH,
                new float[]{0f, 0.5f, 1f},
                new Color[]{
                        new Color(35, 20, 8, 220),
                        new Color(20, 12, 4, 235),
                        new Color(35, 20, 8, 220)
                }));
        Shape box = new RoundRectangle2D.Float(bx, by, totalW, totalH, 14, 14);
        g2.fill(box);

        // ── Gold top accent line ──
        g2.setPaint(new LinearGradientPaint(bx, by, bx + totalW, by,
                new float[]{0f, 0.5f, 1f},
                new Color[]{
                        new Color(252, 218, 72, 0),
                        new Color(252, 218, 72, 180),
                        new Color(252, 218, 72, 0)
                }));
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(bx + 14, by + 2, bx + totalW - 14, by + 2);

        // ── Gold bottom accent line ──
        g2.setPaint(new LinearGradientPaint(bx, by, bx + totalW, by,
                new float[]{0f, 0.5f, 1f},
                new Color[]{
                        new Color(252, 218, 72, 0),
                        new Color(252, 218, 72, 100),
                        new Color(252, 218, 72, 0)
                }));
        g2.setStroke(new BasicStroke(1f));
        g2.drawLine(bx + 14, by + totalH - 2, bx + totalW - 14, by + totalH - 2);

        // ── Outer border ──
        g2.setStroke(new BasicStroke(1.8f));
        g2.setColor(new Color(252, 218, 72, 130));
        g2.draw(box);

        // ── Inner border ──
        g2.setStroke(new BasicStroke(0.8f));
        g2.setColor(new Color(252, 218, 72, 50));
        g2.draw(new RoundRectangle2D.Float(
                bx + 4, by + 4, totalW - 8, totalH - 8, 10, 10));

        // ── Corner ornaments ──
        drawCornerDot(g2, bx + 10, by + 10);
        drawCornerDot(g2, bx + totalW - 10, by + 10);
        drawCornerDot(g2, bx + 10, by + totalH - 10);
        drawCornerDot(g2, bx + totalW - 10, by + totalH - 10);

        // ── Map name text ──
        g2.setFont(fontLabel);
        fmL = g2.getFontMetrics();
        int labelX = (W - fmL.stringWidth(mapName)) / 2;
        int labelY = mapSub != null ? by + 24 : by + (totalH + fmL.getAscent() - fmL.getDescent()) / 2;

        // Shadow
        g2.setColor(new Color(0, 0, 0, 160));
        g2.drawString(mapName, labelX + 2, labelY + 2);

        // Gold gradient
        g2.setPaint(new LinearGradientPaint(
                labelX, labelY - fmL.getAscent(),
                labelX, labelY + 4,
                new float[]{0f, 0.4f, 1f},
                new Color[]{
                        new Color(255, 248, 200),
                        new Color(252, 218, 72),
                        new Color(200, 140, 10)
                }));
        g2.drawString(mapName, labelX, labelY);

        // ── Shimmer ──
        float bandW = 70f;
        float bandX = labelX + shimmer * (fmL.stringWidth(mapName) + bandW) - bandW;
        Shape savedClip = g2.getClip();
        g2.setFont(fontLabel);
        java.awt.font.GlyphVector gv = fontLabel.createGlyphVector(
                g2.getFontRenderContext(), mapName);
        g2.clip(gv.getOutline(labelX, labelY));
        g2.setPaint(new LinearGradientPaint(bandX, 0, bandX + bandW, 0,
                new float[]{0f, 0.5f, 1f},
                new Color[]{
                        new Color(255, 255, 255, 0),
                        new Color(255, 255, 255, 180),
                        new Color(255, 255, 255, 0)
                }));
        g2.fill(new Rectangle2D.Float(
                bandX, labelY - fmL.getAscent() - 4, bandW, fmL.getHeight() + 8));
        g2.setClip(savedClip);

        // ── Subtitle ──
        if (mapSub != null) {
            g2.setFont(fontSub);
            fmS = g2.getFontMetrics();
            int subX = (W - fmS.stringWidth(mapSub)) / 2;
            int subY  = labelY + fmS.getHeight() - 2;
            g2.setColor(new Color(0, 0, 0, 100));
            g2.drawString(mapSub, subX + 1, subY + 1);
            g2.setColor(new Color(200, 180, 120, 200));
            g2.drawString(mapSub, subX, subY);
        }

        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 1f));
    }

    private void drawCornerDot(Graphics2D g2, int cx, int cy) {
        g2.setColor(new Color(252, 218, 72, 140));
        g2.fillOval(cx - 2, cy - 2, 5, 5);
        g2.setColor(new Color(252, 218, 72, 60));
        g2.drawOval(cx - 4, cy - 4, 8, 8);
    }
}