package Main;

import java.awt.*;
import java.awt.geom.*;

public class ObjectivesHUD {

    private String  mainObjective = "";
    private String  subChallenge  = null;
    private boolean challengeDone = false;
    private int     progress      = 0;
    private int     total         = 0;
    private boolean visible       = true;

    private String  eggCodeObjective  = null;
    private boolean eggCodeUnlocked   = false;

    private float checkAlpha = 0f;

    private int lastDrawnHeight = 50;
    public  int getDrawnHeight() { return lastDrawnHeight; }

    public void setObjective(String text, int progress, int total) {
        this.mainObjective = text;
        this.progress      = progress;
        this.total         = total;
    }

    public void setSimpleObjective(String text) {
        this.mainObjective = text;
        this.progress      = -1;
        this.total         = 0;
        this.subChallenge  = null;
    }

    public void updateProgress(int progress) { this.progress = progress; }

    public void setChallenge(String text) {
        this.subChallenge  = text;
        this.challengeDone = false;
        this.checkAlpha    = 0f;
    }

    public void completeChallenge() { this.challengeDone = true; }

    /** Clears the sub-challenge line entirely (e.g. when returning to Map 1). */
    public void clearChallengeObjective() {
        this.subChallenge  = null;
        this.challengeDone = false;
        this.checkAlpha    = 0f;
    }

    // ── Easter egg code objective ─────────────────────────────────
    public void setEggCodeObjective(String text) {
        this.eggCodeObjective = text;
        this.eggCodeUnlocked  = false;
    }

    public void markEggCodeUnlocked() {
        this.eggCodeUnlocked = true;
    }

    public void clearEggCodeObjective() {
        this.eggCodeObjective = null;
    }

    public void setVisible(boolean v) { this.visible = v; }

    public void update() {
        if (challengeDone && checkAlpha < 1f) checkAlpha += 0.05f;
    }

    public void draw(Graphics2D g2) {
        if (!visible || mainObjective.isEmpty()) return;

        int bx = 14, by = 10;
        int bw = 220;

        int contentH = 44;
        if (progress >= 0) contentH += 10;
        if (subChallenge != null) contentH += 18;
        if (eggCodeObjective != null) contentH += 24;
        int bh = contentH + 10;

        // Shadow
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(bx + 3, by + 3, bw, bh, 10, 10);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Background
        g2.setPaint(new LinearGradientPaint(bx, by, bx, by + bh,
                new float[]{0f, 1f},
                new Color[]{new Color(20, 12, 4, 210), new Color(12, 6, 2, 220)}));
        g2.fillRoundRect(bx, by, bw, bh, 10, 10);

        // Gold border
        g2.setStroke(new BasicStroke(1.5f));
        g2.setColor(new Color(252, 218, 72, 120));
        g2.drawRoundRect(bx, by, bw, bh, 10, 10);

        int curY = by + 16;

        // "Objectives:" label
        g2.setFont(new Font("Serif", Font.BOLD, 11));
        g2.setColor(new Color(252, 218, 72, 200));
        g2.drawString("Objectives:", bx + 10, curY);
        curY += 16;

        // Main objective text
        if (progress >= 0) {
            String objText = mainObjective + " (" + progress + "/" + total + ")";
            g2.setFont(new Font("Serif", Font.PLAIN, 12));
            g2.setColor(new Color(230, 215, 180));
            g2.drawString(objText, bx + 10, curY);
            curY += 4;

            // Progress bar
            int barX = bx + 10, barW = bw - 20, barH = 6;
            g2.setColor(new Color(60, 40, 10));
            g2.fillRoundRect(barX, curY, barW, barH, 3, 3);
            if (total > 0) {
                float ratio = Math.min(1f, (float) progress / total);
                g2.setColor(ratio >= 1f ? new Color(80, 220, 80) : new Color(252, 218, 72));
                g2.fillRoundRect(barX, curY, (int)(barW * ratio), barH, 3, 3);
            }
            g2.setStroke(new BasicStroke(0.8f));
            g2.setColor(new Color(252, 218, 72, 80));
            g2.drawRoundRect(barX, curY, barW, barH, 3, 3);
            curY += barH + 4;
        } else {
            g2.setFont(new Font("Serif", Font.PLAIN, 12));
            g2.setColor(new Color(230, 215, 180));
            g2.drawString(mainObjective, bx + 10, curY);
            curY += 16;
        }

        // Sub-challenge line
        if (subChallenge != null) {
            curY += 6;
            g2.setFont(new Font("Serif", Font.ITALIC, 11));
            float alpha = 0.6f + checkAlpha * 0.4f;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.setColor(challengeDone
                    ? new Color(80, 220, 80)
                    : new Color(180, 210, 255, 200));
            String challengeText = (challengeDone ? "✔ " : "◌ ") + "Challenge: " + subChallenge;
            g2.drawString(challengeText, bx + 10, curY);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            curY += 16;
        }

        // Easter egg code objective line
        if (eggCodeObjective != null) {
            curY += 6;
            g2.setFont(new Font("Serif", Font.ITALIC, 11));
            if (eggCodeUnlocked) {
                g2.setColor(new Color(252, 218, 72));
                g2.drawString("🥚 " + eggCodeObjective + " ✔", bx + 10, curY);
            } else {
                g2.setColor(new Color(255, 200, 80, 200));
                g2.drawString("🥚 " + eggCodeObjective, bx + 10, curY);
            }
            curY += 16;
        }

        lastDrawnHeight = curY - by + 6;
    }
}