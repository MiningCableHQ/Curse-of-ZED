package Main;

import java.awt.*;
import Entities.Characters.NPC;
import Entities.Enemies.EnemyEntity;
import Objects.SuperObject;

public class Minimap {

    private static final int MAP_W      = 110;
    private static final int MAP_H      = 85;
    private static final int PADDING    = 12;
    private static final int WIDGET_H   = 32;
    private static final int WIDGET_GAP = 6;
    // Gap between ObjectivesHUD bottom and minimap top
    private static final int HUD_GAP    = 10;

    private int pulseTimer = 0;

    public void update() { pulseTimer++; }

    public void draw(Graphics2D g2, GamePanel gp) {
        draw(g2, gp, null);
    }

    public void draw(Graphics2D g2, GamePanel gp, WeatherSystem weather) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // ── Position: left side, just below the ObjectivesHUD ────
        // ObjectivesHUD starts at y=10; ask it how tall it drew itself
        int objHudBottom = 10 + gp.objectivesHUD.getDrawnHeight();
        int x = PADDING;
        int y = objHudBottom + HUD_GAP;

        int worldW = gp.worldWidth;
        int worldH = gp.worldHeight;

        // ── Map panel background ──────────────────────────────────
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.82f));
        g2.setColor(new Color(8, 6, 4));
        g2.fillRoundRect(x - 3, y - 3, MAP_W + 6, MAP_H + 6, 10, 10);
        g2.setStroke(new BasicStroke(1.4f));
        g2.setColor(new Color(252, 218, 72, 150));
        g2.drawRoundRect(x - 3, y - 3, MAP_W + 6, MAP_H + 6, 10, 10);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // "MAP" label
        // MAP label — inside the box, top-left
        g2.setFont(new Font("Serif", Font.BOLD, 9));
        g2.setColor(new Color(252, 218, 72, 200));
        g2.drawString("MAP", x + 4, y + 10);

        // ── Dots ─────────────────────────────────────────────────
        for (SuperObject o : gp.obj) {
            if (o == null) continue;
            int dotX = x + (int)((float) o.worldX / worldW * MAP_W);
            int dotY = y + 12 + (int)((float) o.worldY / worldH * (MAP_H - 12));

            if (o instanceof EnemyEntity && ((EnemyEntity) o).available) {
                g2.setColor(new Color(220, 60, 60));
                g2.fillOval(dotX - 2, dotY - 2, 5, 5);
            }  else if (o instanceof NPC && ((NPC) o).available && ((NPC) o).showOnMinimap) {
                g2.setColor(new Color(252, 218, 72, 180));
                g2.fillOval(dotX - 2, dotY - 2, 4, 4);
            }
        }




        // ── Player dot — pulsing ──────────────────────────────────
        int px = x + (int)((float) gp.player.worldX / worldW * MAP_W);
        int py = y + 12 + (int)((float) gp.player.worldY / worldH * (MAP_H - 12));

        float pulse = (float)(Math.sin(pulseTimer * 0.12f) * 0.5f + 0.5f);
        g2.setColor(new Color(100, 200, 255, (int)(80 * pulse)));
        g2.fillOval(px - 5, py - 5, 11, 11);
        g2.setColor(Color.WHITE);
        g2.fillOval(px - 3, py - 3, 7, 7);
        g2.setColor(new Color(100, 200, 255));
        g2.setStroke(new BasicStroke(1f));
        g2.drawOval(px - 3, py - 3, 7, 7);

        // ── Weather widget below map ──────────────────────────────
        drawWeatherWidget(g2, x, y + MAP_H + WIDGET_GAP,
                MAP_W + 6, WIDGET_H, weather);
    }

    private void drawWeatherWidget(Graphics2D g2, int x, int y,
                                   int w, int h, WeatherSystem weather) {
        String weatherLabel;
        String weatherIcon;
        Color  accentColor;

        if (weather == null
                || weather.getCurrent() == WeatherSystem.WeatherType.CLEAR) {
            weatherLabel = "Clear";
            weatherIcon  = "☀";
            accentColor  = new Color(255, 220, 80);
        } else if (weather.getCurrent() == WeatherSystem.WeatherType.RAIN) {
            weatherLabel = "Rainy";
            weatherIcon  = "🌧";
            accentColor  = new Color(100, 160, 255);
        } else {
            weatherLabel = "Storm";
            weatherIcon  = "⛈";
            accentColor  = new Color(180, 120, 255);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.82f));
        g2.setColor(new Color(8, 6, 4));
        g2.fillRoundRect(x - 3, y, w, h, 8, 8);
        g2.setStroke(new BasicStroke(1.2f));
        g2.setColor(new Color(accentColor.getRed(),
                accentColor.getGreen(), accentColor.getBlue(), 140));
        g2.drawRoundRect(x - 3, y, w, h, 8, 8);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        g2.setFont(new Font("Serif", Font.ITALIC, 9));
        g2.setColor(new Color(200, 190, 160));
        g2.drawString("Weather:", x + 2, y + 11);

        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        g2.setColor(accentColor);
        g2.drawString(weatherIcon, x + 2, y + 26);

        g2.setFont(new Font("Serif", Font.BOLD, 12));
        g2.setColor(accentColor);
        g2.drawString(weatherLabel, x + 22, y + 26);
    }
}