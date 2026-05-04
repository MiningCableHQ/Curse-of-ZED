package Main;

import java.awt.*;
import java.awt.geom.*;

public class ScreenMessage {

    private boolean active = false;
    private String line1, line2;
    private float alpha = 0f;
    private int timer = 0;
    private int holdFrames;
    private static final int FADE_IN  = 25;
    private static final int FADE_OUT = 25;
    private boolean shimmering = false;
    private float shimmer = 0f;

    public void show(String line1, String line2, int holdFrames, boolean shimmering) {
        this.line1 = line1;
        this.line2 = line2;
        this.holdFrames = holdFrames;
        this.shimmering = shimmering;
        this.active = true;
        this.timer = 0;
        this.alpha = 0f;
        this.shimmer = 1.4f;
    }

    public void show(String line1, int holdFrames) {
        show(line1, null, holdFrames, false);
    }

    public boolean isActive() { return active; }

    public void update() {
        if (!active) return;
        timer++;
        int total = FADE_IN + holdFrames + FADE_OUT;

        if (timer <= FADE_IN) {
            alpha = (float) timer / FADE_IN;
        } else if (timer <= FADE_IN + holdFrames) {
            alpha = 1f;
            shimmer -= 0.008f;
            if (shimmer < -0.4f) shimmer = 1.4f;
        } else {
            alpha = 1f - (float)(timer - FADE_IN - holdFrames) / FADE_OUT;
        }

        if (timer >= total) active = false;
    }

    public void draw(Graphics2D g2) {
        if (!active || alpha <= 0) return;

        int W = 1024;
        Font f1 = new Font("Serif", Font.BOLD | Font.ITALIC, 22);
        Font f2 = new Font("Serif", Font.ITALIC, 16);

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // Measure and center
        g2.setFont(f1);
        FontMetrics fm1 = g2.getFontMetrics();
        int totalH = fm1.getHeight() + (line2 != null ? g2.getFontMetrics(f2).getHeight() + 6 : 0);
        int boxH   = totalH + 28;
        int boxW   = Math.max(fm1.stringWidth(line1), 400) + 60;
        int bx = (W - boxW) / 2;
        int by = 200;

        // Background box
        g2.setPaint(new LinearGradientPaint(bx, by, bx, by + boxH,
                new float[]{0f, 0.5f, 1f},
                new Color[]{new Color(20, 10, 2, 200),
                        new Color(10, 5, 0, 220),
                        new Color(20, 10, 2, 200)}));
        g2.fillRoundRect(bx, by, boxW, boxH, 14, 14);

        // Gold border
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(new Color(252, 218, 72, 180));
        g2.drawRoundRect(bx, by, boxW, boxH, 14, 14);

        // Line 1 — gold gradient
        int tx = (W - fm1.stringWidth(line1)) / 2;
        int ty = by + 20 + fm1.getAscent();
        g2.setColor(new Color(0, 0, 0, 100));
        g2.drawString(line1, tx + 1, ty + 1);

        if (shimmering) {
            java.awt.font.GlyphVector gv = f1.createGlyphVector(
                    g2.getFontRenderContext(), line1);
            Shape clip = g2.getClip();
            g2.clip(gv.getOutline(tx, ty));
            float bw = 80f;
            float bx2 = tx + shimmer * (fm1.stringWidth(line1) + bw) - bw;
            g2.setPaint(new LinearGradientPaint(bx2, 0, bx2 + bw, 0,
                    new float[]{0f, 0.5f, 1f},
                    new Color[]{new Color(255,255,255,0),
                            new Color(255,255,255,200),
                            new Color(255,255,255,0)}));
            g2.fillRect((int)bx2, ty - fm1.getAscent(), (int)bw, fm1.getHeight() + 6);
            g2.setClip(clip);
        }

        g2.setPaint(new LinearGradientPaint(tx, ty - fm1.getAscent(), tx, ty + 4,
                new float[]{0f, 0.5f, 1f},
                new Color[]{new Color(255, 248, 200),
                        new Color(252, 218, 72),
                        new Color(200, 140, 10)}));
        g2.drawString(line1, tx, ty);

        // Line 2
        if (line2 != null) {
            g2.setFont(f2);
            FontMetrics fm2 = g2.getFontMetrics();
            int tx2 = (W - fm2.stringWidth(line2)) / 2;
            int ty2 = ty + fm1.getDescent() + 8 + fm2.getAscent();
            g2.setColor(new Color(200, 180, 120, 200));
            g2.drawString(line2, tx2, ty2);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}