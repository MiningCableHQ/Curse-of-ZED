package Main;

import java.awt.*;
import java.util.Random;

public class WeatherSystem {

    public enum WeatherType { CLEAR, RAIN, STORM }

    private WeatherType current = WeatherType.CLEAR;
    private int timer = 0;
    private int duration = 0;
    private int cooldown = 600; // frames before next weather
    private final Random rng = new Random();

    // Rain drops
    private final int[][] drops = new int[120][3]; // x, y, speed
    private boolean dropsInit = false;

    // Thunder flash
    private float thunderAlpha = 0f;
    private int thunderTimer = 0;
    private boolean newThunderFlash = false;

    public void init(int screenW, int screenH) {
        for (int i = 0; i < drops.length; i++) {
            drops[i][0] = rng.nextInt(screenW);
            drops[i][1] = rng.nextInt(screenH);
            drops[i][2] = 8 + rng.nextInt(6);
        }
        dropsInit = true;
    }

    public void update(int screenW, int screenH) {
        if (!dropsInit) init(screenW, screenH);

        // Weather lifecycle
        if (current == WeatherType.CLEAR) {
            cooldown--;
            if (cooldown <= 0) {
                // Pick rain or storm
                current = rng.nextBoolean()
                        ? WeatherType.RAIN : WeatherType.STORM;
                duration = 400 + rng.nextInt(300);
                cooldown = 700 + rng.nextInt(600);
            }
        } else {
            timer++;
            if (timer >= duration) {
                current = WeatherType.CLEAR;
                timer = 0;
            }
        }

        // Move rain drops
        if (current != WeatherType.CLEAR) {
            for (int[] drop : drops) {
                drop[1] += drop[2];
                drop[0] += drop[2] / 3; // diagonal
                if (drop[1] > screenH) {
                    drop[1] = -10;
                    drop[0] = rng.nextInt(screenW);
                }
                if (drop[0] > screenW) drop[0] = 0;
            }
        }

        // Thunder flash
        newThunderFlash = false;
        if (current == WeatherType.STORM) {
            thunderTimer++;
            if (thunderTimer % (90 + rng.nextInt(120)) == 0) {
                thunderAlpha = 0.6f;
                newThunderFlash = true;
            }
        }
        if (thunderAlpha > 0) thunderAlpha -= 0.04f;
    }

    public void draw(Graphics2D g2, int screenW, int screenH) {
        if (current == WeatherType.CLEAR) return;

        // Dark overlay for storm mood
        if (current == WeatherType.STORM) {
            g2.setColor(new Color(0, 0, 30, 60));
            g2.fillRect(0, 0, screenW, screenH);
        }

        // Rain drops
        g2.setStroke(new BasicStroke(1.2f));
        for (int[] drop : drops) {
            float alpha = current == WeatherType.STORM ? 0.7f : 0.45f;
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alpha));
            g2.setColor(new Color(180, 210, 255));
            g2.drawLine(drop[0], drop[1],
                    drop[0] + 4, drop[1] + 10);
        }
        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 1f));

        // Thunder flash
        if (thunderAlpha > 0) {
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER,
                    Math.min(1f, thunderAlpha)));
            g2.setColor(new Color(240, 240, 255));
            g2.fillRect(0, 0, screenW, screenH);
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 1f));
        }
    }

    public WeatherType getCurrent() { return current; }

    public boolean consumeThunderFlash() {
        if (newThunderFlash) { newThunderFlash = false; return true; }
        return false;
    }
}