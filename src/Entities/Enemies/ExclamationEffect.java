package Entities.Enemies;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ExclamationEffect {

    private boolean active = false;
    private int timer = 0;
    private static final int DURATION = 60; // 1 second at 60fps

    // Optional: load your exclamation image
    private BufferedImage exclamImage;
    private float bobY = 0f;
    private float alpha = 1f;

    public ExclamationEffect() {
        // Try to load exclamation image
        // When your groupmate finishes it, put it at /enemies/exclamation.png
        try {
            exclamImage = ImageIO.read(
                    getClass().getResourceAsStream("/enemies/exclamation.png"));
        } catch (Exception e) {
            exclamImage = null; // Will use drawn fallback
        }
    }

    public void trigger() {
        active = true;
        timer = 0;
        bobY = 0f;
        alpha = 1f;
    }

    public boolean isActive() { return active; }

    public void update() {
        if (!active) return;
        timer++;
        bobY = (float)(Math.sin(timer * 0.2f) * 3f);

        // Fade out in last 15 frames
        if (timer > DURATION - 15) {
            alpha = (float)(DURATION - timer) / 15f;
        }

        if (timer >= DURATION) active = false;
    }

    /**
     * Draw above the enemy.
     * cx = screen center X of enemy
     * topY = screen top Y of enemy
     */
    public void draw(Graphics2D g2, int cx, int topY) {
        if (!active) return;

        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, Math.max(0f, alpha)));

        int iy = (int)(topY - 28 + bobY);

        if (exclamImage != null) {
            // Draw the actual exclamation image (32x32 or whatever size)
            g2.drawImage(exclamImage, cx - 16, iy, 32, 32, null);
        } else {
            // ── Drawn fallback "!" ────────────────────────────────
            // Yellow background bubble
            g2.setColor(new Color(255, 220, 30, 220));
            g2.fillOval(cx - 12, iy, 24, 28);
            g2.setColor(new Color(180, 120, 0));
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(cx - 12, iy, 24, 28);

            // "!" text
            g2.setFont(new Font("Serif", Font.BOLD, 20));
            FontMetrics fm = g2.getFontMetrics();
            g2.setColor(new Color(80, 40, 0));
            g2.drawString("!", cx - fm.stringWidth("!") / 2, iy + 22);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}