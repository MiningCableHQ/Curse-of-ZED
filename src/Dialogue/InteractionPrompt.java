package Dialogue;


import java.awt.*;
import java.awt.geom.*;

public class InteractionPrompt {

    private float pulse = 0f, pulseDir = 1f;

    public void update() {
        pulse += 0.05f * pulseDir;
        if (pulse > 1f) pulseDir = -1f;
        if (pulse < 0f) pulseDir =  1f;
    }

    /**
     * Draw the "E" prompt at screen position (sx, sy) — typically above the NPC.
     */
    public void draw(Graphics2D g2, int sx, int sy) {
        int r = 18; // circle radius
        int cx = sx, cy = sy;

        float alpha = 0.6f + pulse * 0.4f;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // Outer glow
        for (int i = 8; i >= 2; i -= 2) {
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alpha * 0.06f));
            g2.setColor(new Color(200, 230, 255));
            g2.fillOval(cx - r - i, cy - r - i, (r + i) * 2, (r + i) * 2);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // White border (transparent fill inside)
        g2.setStroke(new BasicStroke(2.5f));
        g2.setColor(new Color(255, 255, 255, 220));
        g2.drawOval(cx - r, cy - r, r * 2, r * 2);

        // Very subtle fill so the E stands out
        g2.setColor(new Color(0, 0, 0, 60));
        g2.fillOval(cx - r, cy - r, r * 2, r * 2);

        // "E" letter
        g2.setFont(new Font("Serif", Font.BOLD, 22));
        FontMetrics fm = g2.getFontMetrics();
        int ex = cx - fm.stringWidth("E") / 2;
        int ey = cy + fm.getAscent() / 2 - 2;
        g2.setColor(new Color(0, 0, 0, 120));
        g2.drawString("E", ex + 1, ey + 1);
        g2.setColor(new Color(230, 240, 255));
        g2.drawString("E", ex, ey);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}