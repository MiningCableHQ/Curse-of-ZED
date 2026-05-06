package Main;

import java.awt.*;
import java.awt.geom.*;

public class BattleTransition {

    public enum State { IDLE, RUNNING, DONE }

    private State state = State.IDLE;
    private int timer = 0;
    private static final int TOTAL = 60;
    private float alpha = 0f;

    // Ripple effect
    private float ripple = 0f;

    private Runnable onComplete;

    public void start(Runnable onComplete) {
        this.onComplete = onComplete;
        this.state = State.RUNNING;
        this.timer = 0;
        this.alpha = 0f;
        this.ripple = 0f;
    }

    public boolean isRunning() { return state == State.RUNNING; }

    public void update() {
        if (state != State.RUNNING) return;
        timer++;
        ripple += 0.12f;

        if (timer <= 30) {
            alpha = (float) timer / 30f;
        } else {
            alpha = 1f;
        }

        if (timer >= TOTAL) {
            state = State.DONE;
            if (onComplete != null) onComplete.run();
        }
    }

    public void draw(Graphics2D g2) {
        if (state == State.IDLE) return;

        int W = 1024, H = 768;

        // Radial ripple from center
        for (int i = 5; i >= 1; i--) {
            float r = ripple * 80f * i;
            float a = Math.max(0, (1f - r / (W * 0.8f)) * alpha * 0.3f);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
            g2.setColor(new Color(252, 218, 72));
            g2.drawOval((int)(W/2 - r), (int)(H/2 - r), (int)(r*2), (int)(r*2));
        }

        // Dark fill
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, W, H);

        // "!" flash text
        if (timer < 35) {
            float textAlpha = Math.max(0f, 1f - (float)timer / 35f);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, textAlpha));
            g2.setFont(new Font("Serif", Font.BOLD, 60));
            g2.setColor(new Color(252, 218, 72));
            FontMetrics fm = g2.getFontMetrics();
            String txt = "!";
            g2.drawString(txt, (W - fm.stringWidth(txt)) / 2, H / 2 + 20);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}