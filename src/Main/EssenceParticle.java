package Main;

import java.awt.*;
import java.awt.geom.*;

public class EssenceParticle {

    private boolean active = false;
    private float alpha = 0f;
    private int timer = 0;
    private float scale = 0.8f;
    private float glow = 0f, glowDir = 1f;
    private static final int FADE_IN  = 25;
    private static final int HOLD     = 90;
    private static final int FADE_OUT = 35;
    private static final int TOTAL    = FADE_IN + HOLD + FADE_OUT;

    private float[] px = new float[10]; // Increased to 10 for more richness
    private float[] py = new float[10];
    private float[] pa = new float[10];
    private float[] pr = new float[10];
    private float[] pOffset = new float[10]; // For twinkling effect

    private int cx, cy;

    public void trigger(int screenCx, int screenCy) {
        this.cx = screenCx;
        this.cy = screenCy;
        active = true;
        timer = 0;
        alpha = 0f;
        scale = 0.8f;

        for (int i = 0; i < 10; i++) {
            pa[i] = (float)(i * Math.PI * 2 / 10);
            pr[i] = 70 + (float)(Math.random() * 40);
            pOffset[i] = (float)(Math.random() * Math.PI); // Randomized twinkle start
        }
    }

    public boolean isActive() { return active; }

    public void update() {
        if (!active) return;
        timer++;

        // Faster glow pulse for "energy" feel
        glow += 0.12f * glowDir;
        if (glow > 1.2f) glowDir = -1f;
        if (glow < 0.8f) glowDir =  1f;

        if (timer <= FADE_IN) {
            alpha = (float) timer / FADE_IN;
            scale = 0.8f + (alpha * 0.7f);
        } else if (timer <= FADE_IN + HOLD) {
            alpha = 1f;
            scale = 1.5f;
        } else {
            float t = (float)(timer - FADE_IN - HOLD) / FADE_OUT;
            alpha = 1f - t;
            scale = 1.5f + t * 0.6f;
        }

        for (int i = 0; i < 10; i++) {
            pa[i] += 0.04f + (i * 0.005f); // Varied speeds for more natural motion
            px[i] = cx + (float)Math.cos(pa[i]) * pr[i] * scale;
            py[i] = cy + (float)Math.sin(pa[i]) * pr[i] * scale;
        }

        if (timer >= TOTAL) active = false;
    }

    public void draw(Graphics2D g2) {
        if (!active || alpha <= 0) return;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Save original composite
        Composite oldComp = g2.getComposite();

        // 1. Outer Massive Bloom (The "Aura")
        float auraSize = 120f * scale * glow;
        RadialGradientPaint auraGrad = new RadialGradientPaint(cx, cy, auraSize,
                new float[]{0f, 0.4f, 1f},
                new Color[]{
                        new Color(255, 200, 50, (int)(70 * alpha)),
                        new Color(255, 100, 0, (int)(30 * alpha)),
                        new Color(255, 100, 0, 0)
                });
        g2.setPaint(auraGrad);
        g2.fill(new Ellipse2D.Float(cx - auraSize, cy - auraSize, auraSize * 2, auraSize * 2));

        // 2. The Core Lens Flare Effect
        int coreR = (int)(25 * scale);
        RadialGradientPaint coreGrad = new RadialGradientPaint(cx, cy, coreR,
                new float[]{0f, 0.2f, 0.5f, 1f},
                new Color[]{
                        Color.WHITE,                             // Hot center
                        new Color(255, 255, 200, (int)(255 * alpha)), // Inner yellow
                        new Color(255, 215, 0, (int)(180 * alpha)),   // Gold edge
                        new Color(255, 215, 0, 0)                // Fade out
                });
        g2.setPaint(coreGrad);
        g2.fillOval(cx - coreR, cy - coreR, coreR * 2, coreR * 2);

        // 3. Sparkling Orbiting Particles
        for (int i = 0; i < 10; i++) {
            // Twinkle logic: size varies based on time/offset
            float twinkle = (float)Math.sin(timer * 0.2f + pOffset[i]) * 0.3f + 0.7f;
            float pSize = 10f * scale * twinkle;

            RadialGradientPaint pGrad = new RadialGradientPaint(px[i], py[i], pSize,
                    new float[]{0f, 1f},
                    new Color[]{
                            new Color(255, 255, 255, (int)(255 * alpha)),
                            new Color(255, 215, 0, 0)
                    });
            g2.setPaint(pGrad);
            g2.fill(new Ellipse2D.Float(px[i] - pSize, py[i] - pSize, pSize * 2, pSize * 2));
        }

        // 4. Shiny "ESSENCE RECEIVED" Text
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setFont(new Font("Serif", Font.BOLD, 28));
        FontMetrics fm = g2.getFontMetrics();
        String txt = "✧ ESSENCE RECEIVED ✧";
        int tx = cx - fm.stringWidth(txt) / 2;
        int ty = cy - (int)(110 * scale);

        // Draw glow behind text
        g2.setColor(new Color(255, 215, 0, (int)(100 * alpha * glow)));
        g2.drawString(txt, tx - 1, ty - 1);
        g2.drawString(txt, tx + 1, ty + 1);

        // Shiny Gradient
        g2.setPaint(new LinearGradientPaint(tx, ty - 25, tx, ty + 5,
                new float[]{0f, 0.2f, 0.5f, 0.8f, 1f},
                new Color[]{
                        new Color(200, 150, 50),  // Bronze
                        new Color(255, 255, 255), // Shine Highlight
                        new Color(255, 220, 100), // Gold
                        new Color(255, 255, 255), // Shine Highlight
                        new Color(150, 100, 0)    // Dark Gold
                }));
        g2.drawString(txt, tx, ty);

        g2.setComposite(oldComp);
    }
}