package Main;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class WeaponPopup {

    private boolean active = false;
    private float alpha    = 0f;
    private float scale    = 0.3f; // starts small, zooms in
    private float glow     = 0f;
    private float glowDir  = 1f;
    private int   timer    = 0;

    private static final int FADE_IN   = 25;
    private static final int HOLD      = 140;
    private static final int ZOOM_OUT  = 35;
    private static final int TOTAL     = FADE_IN + HOLD + ZOOM_OUT;

    private BufferedImage itemImage;
    private String itemName;
    private String subtitle;
    private Runnable onComplete;

    public void show(String imagePath, String itemName,
                     String subtitle, Runnable onComplete) {
        try {
            itemImage = ImageIO.read(
                    getClass().getResourceAsStream(imagePath));
        } catch (Exception e) {
            itemImage = null;
        }
        this.itemName   = itemName;
        this.subtitle   = subtitle;
        this.onComplete = onComplete;
        this.active     = true;
        this.timer      = 0;
        this.alpha      = 0f;
        this.scale      = 0.3f;
    }

    public boolean isActive() { return active; }

    public void update() {
        if (!active) return;
        timer++;

        glow += 0.05f * glowDir;
        if (glow > 1f) glowDir = -1f;
        if (glow < 0f) glowDir =  1f;

        if (timer <= FADE_IN) {
            float t = (float) timer / FADE_IN;
            alpha = t;
            scale = 0.3f + t * 0.7f;
        } else if (timer <= FADE_IN + HOLD) {
            alpha = 1f;
            scale = 1f;
        } else {
            float t = (float)(timer - FADE_IN - HOLD) / ZOOM_OUT;
            alpha = 1f - t;
            scale = 1f + t * 0.4f;
        }

        if (timer >= TOTAL) {
            active = false;
            if (onComplete != null) onComplete.run();
        }
    }

    // ✅ SAFE alpha helper (THIS fixes your crash)
    private float safeAlpha(float a) {
        if (Float.isNaN(a)) return 0f;
        return Math.max(0f, Math.min(1f, a));
    }

    public void draw(Graphics2D g2) {
        if (!active) return;

        int W = 1024, H = 768;
        int boxW = 300, boxH = 320;
        int baseBx = (W - boxW) / 2;
        int baseBy = (H - boxH) / 2;

        float a = safeAlpha(alpha * 0.55f);

        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, a));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, W, H);

        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, safeAlpha(alpha)));

        java.awt.geom.AffineTransform old = g2.getTransform();
        g2.translate(W / 2.0, H / 2.0);
        g2.scale(scale, scale);
        g2.translate(-W / 2.0, -H / 2.0);

        int bx = baseBx, by = baseBy;

        // Glow rings
        for (int i = 24; i >= 2; i -= 2) {
            float ga = glow * 0.025f * (26 - i);
            float alphaVal = safeAlpha(alpha * ga);

            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alphaVal));

            g2.setColor(new Color(252, 218, 72));
            g2.fillRoundRect(bx - i, by - i,
                    boxW + i * 2, boxH + i * 2, 22, 22);
        }

        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, safeAlpha(alpha)));

        g2.setPaint(new LinearGradientPaint(bx, by, bx, by + boxH,
                new float[]{0f, 0.5f, 1f},
                new Color[]{
                        new Color(30, 18, 6, 245),
                        new Color(18, 10, 2, 252),
                        new Color(30, 18, 6, 245)
                }));
        g2.fillRoundRect(bx, by, boxW, boxH, 18, 18);

        g2.setStroke(new BasicStroke(2.5f));
        g2.setColor(new Color(252, 218, 72, 200));
        g2.drawRoundRect(bx, by, boxW, boxH, 18, 18);

        g2.setStroke(new BasicStroke(1f));
        g2.setColor(new Color(252, 218, 72, 80));
        g2.drawRoundRect(bx + 5, by + 5,
                boxW - 10, boxH - 10, 13, 13);

        drawCornerDot(g2, bx + 14, by + 14);
        drawCornerDot(g2, bx + boxW - 14, by + 14);
        drawCornerDot(g2, bx + 14, by + boxH - 14);
        drawCornerDot(g2, bx + boxW - 14, by + boxH - 14);

        g2.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 14));
        FontMetrics fmH = g2.getFontMetrics();
        String header = "✦ Weapon Received! ✦";

        g2.setColor(new Color(0, 0, 0, 100));
        g2.drawString(header,
                bx + (boxW - fmH.stringWidth(header)) / 2 + 1, by + 22);

        g2.setColor(new Color(252, 218, 72, 220));
        g2.drawString(header,
                bx + (boxW - fmH.stringWidth(header)) / 2, by + 21);

        int imgSize = 160;
        int ix = bx + (boxW - imgSize) / 2;
        int iy = by + 35;

        for (int i = 16; i >= 2; i -= 2) {
            float ga = glow * 0.035f * (18 - i);
            float alphaVal = safeAlpha(alpha * ga);

            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alphaVal));

            g2.setColor(new Color(252, 218, 72));
            g2.fillOval(ix - i + imgSize / 2 - 20,
                    iy - i + imgSize / 2 - 20,
                    40 + i * 2, 40 + i * 2);
        }

        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, safeAlpha(alpha)));

        if (itemImage != null) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(itemImage, ix, iy, imgSize, imgSize, null);
        } else {
            g2.setColor(new Color(252, 218, 72, 80));
            g2.fillRoundRect(ix, iy, imgSize, imgSize, 10, 10);
            g2.setFont(new Font("Serif", Font.BOLD, 40));
            g2.setColor(new Color(252, 218, 72));
            g2.drawString("⚔",
                    ix + imgSize / 2 - 20, iy + imgSize / 2 + 15);
        }

        Font nameFont = new Font("Serif", Font.BOLD, 18);
        g2.setFont(nameFont);
        FontMetrics fm = g2.getFontMetrics();
        int nameY = iy + imgSize + 28;

        g2.setColor(new Color(0, 0, 0, 120));
        g2.drawString(itemName,
                bx + (boxW - fm.stringWidth(itemName)) / 2 + 1,
                nameY + 1);

        g2.setPaint(new LinearGradientPaint(
                bx, nameY - 16, bx, nameY + 4,
                new float[]{0f, 0.5f, 1f},
                new Color[]{
                        new Color(255, 248, 200),
                        new Color(252, 218, 72),
                        new Color(200, 140, 10)
                }));

        g2.drawString(itemName,
                bx + (boxW - fm.stringWidth(itemName)) / 2, nameY);

        if (subtitle != null) {
            g2.setFont(new Font("Serif", Font.ITALIC, 12));
            fm = g2.getFontMetrics();
            g2.setColor(new Color(200, 180, 120, 200));
            g2.drawString(subtitle,
                    bx + (boxW - fm.stringWidth(subtitle)) / 2,
                    nameY + 20);
        }

        g2.setTransform(old);
        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 1f));
    }

    private void drawCornerDot(Graphics2D g2, int cx, int cy) {
        g2.setColor(new Color(252, 218, 72, 160));
        g2.fillOval(cx - 3, cy - 3, 6, 6);
        g2.setColor(new Color(252, 218, 72, 60));
        g2.drawOval(cx - 5, cy - 5, 10, 10);
    }
}