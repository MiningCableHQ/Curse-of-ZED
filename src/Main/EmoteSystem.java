package Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmoteSystem {

    // Only 3 emotes — each triggers a specific pet reaction
    public static final String[] EMOTES = { "💖", "🔥", "😱" };

    // ── Floating emote ────────────────────────────────────────────
    public static class FloatingEmote {
        public String emote;
        public int worldX, worldY;
        public float alpha   = 1f;
        public float offsetY = 0f;
        public int   timer   = 0;
        public static final int DURATION = 140;


        FloatingEmote(String emote, int worldX, int worldY) {
            this.emote  = emote;
            this.worldX = worldX;
            this.worldY = worldY;
        }

        public void update() {
            timer++;
            offsetY -= 0.4f;
            if (timer > DURATION * 0.6f)
                alpha = 1f - (float)(timer - DURATION * 0.6f) / (DURATION * 0.4f);
        }

        public boolean isDone() { return timer >= DURATION; }
    }

    // ── Layout ────────────────────────────────────────────────────
    private static final int BTN_SIZE   = 46;
    private static final int BTN_MARGIN = 10;
    private int screenW = 1024;

    // Top-right toggle
    private Rectangle   toggleBtn  = new Rectangle(0, BTN_MARGIN, BTN_SIZE, BTN_SIZE);
    // Emote slots drop down from toggle
    private Rectangle[] emoteSlots = new Rectangle[EMOTES.length];
    // "Pet" button — to the left of toggle
    private Rectangle   petMenuBtn = new Rectangle(0, BTN_MARGIN, 50, BTN_SIZE);

    private boolean pickerOpen  = false;
    private boolean petVisible  = true;
    private int mouseX = -1, mouseY = -1;

    public void updateMouse(int mx, int my) {
        this.mouseX = mx;
        this.mouseY = my;
    }
    public  boolean petMenuOpen = false;

    // Rectangles for the pet sub-menu items (drawn when petMenuOpen)
    private Rectangle hidePetSlot      = new Rectangle();
    private Rectangle customizePetSlot = new Rectangle();

    private final List<FloatingEmote> floatingEmotes = new ArrayList<>();
    private java.util.function.Consumer<String> onEmotePicked;
    private Runnable onCustomizePet;

    public void setOnEmotePicked(java.util.function.Consumer<String> cb) { this.onEmotePicked = cb; }
    public void setOnCustomizePet(Runnable r)                            { this.onCustomizePet = r; }

    public EmoteSystem() { rebuildLayout(1024); }

    private void rebuildLayout(int sw) {
        screenW = sw;
        int bx = sw - BTN_SIZE - BTN_MARGIN;

        toggleBtn = new Rectangle(bx, BTN_MARGIN, BTN_SIZE, BTN_SIZE);

        // "Pet" button sits to the left of the emote toggle
        int petBtnW = 50;
        int petBtnX = bx - petBtnW - 6;
        petMenuBtn = new Rectangle(petBtnX, BTN_MARGIN, petBtnW, BTN_SIZE);

        // Pet sub-menu items drop below "Pet" button
        hidePetSlot      = new Rectangle(petBtnX, BTN_MARGIN + BTN_SIZE + 4, petBtnW, BTN_SIZE - 6);
        customizePetSlot = new Rectangle(petBtnX, BTN_MARGIN + BTN_SIZE + 4 + (BTN_SIZE - 6) + 3,
                petBtnW, BTN_SIZE - 6);

        // Emote slots drop below toggle
        for (int i = 0; i < EMOTES.length; i++) {
            emoteSlots[i] = new Rectangle(
                    bx, BTN_MARGIN + (i + 1) * (BTN_SIZE + 4), BTN_SIZE, BTN_SIZE);
        }
    }

    // ── Mouse ─────────────────────────────────────────────────────
    public boolean handleClick(int mx, int my) {
        // Emote toggle
        if (toggleBtn.contains(mx, my)) {
            pickerOpen  = !pickerOpen;
            petMenuOpen = false;
            return true;
        }

        // Pet menu button
        if (petMenuBtn.contains(mx, my)) {
            petMenuOpen = !petMenuOpen;
            pickerOpen  = false;
            return true;
        }

        // Pet sub-menu
        if (petMenuOpen) {
            if (hidePetSlot.contains(mx, my)) {
                petVisible  = !petVisible;
                petMenuOpen = false;
                return true;
            }
            if (customizePetSlot.contains(mx, my)) {
                petMenuOpen = false;
                if (onCustomizePet != null) onCustomizePet.run();
                return true;
            }
            petMenuOpen = false;
        }

        // Emote slots
        if (pickerOpen) {
            for (int i = 0; i < emoteSlots.length; i++) {
                if (emoteSlots[i].contains(mx, my)) {
                    pickerOpen = false;
                    if (onEmotePicked != null) onEmotePicked.accept(EMOTES[i]);
                    return true;
                }
            }
            pickerOpen = false;
        }
        return false;
    }

    // ── Spawn — ONLY used for player's own emote (above head) ─────
    // Do NOT call this for NPC/enemy reactions anymore.
    public void spawnEmote(String emote, int worldX, int worldY) {
        // Remove any existing emote from the same world position to prevent spam
        floatingEmotes.removeIf(fe ->
                Math.abs(fe.worldX - worldX) < 32 && Math.abs(fe.worldY - worldY) < 32);
        floatingEmotes.add(new FloatingEmote(emote, worldX, worldY));
    }

    public void update() {
        floatingEmotes.removeIf(FloatingEmote::isDone);
        floatingEmotes.forEach(FloatingEmote::update);
    }

    public boolean isPetVisible()                        { return petVisible; }
    public List<FloatingEmote> getFloatingEmotes()       { return floatingEmotes; }

    // ── Draw UI ───────────────────────────────────────────────────
    public void drawUI(Graphics2D g2, int sw) {
        rebuildLayout(sw);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // ── Emote toggle ──────────────────────────────────────────
        drawBtn(g2, toggleBtn,
                pickerOpen ? new Color(252, 218, 72, 220) : new Color(30, 15, 5, 200),
                pickerOpen ? new Color(82, 38, 0)         : new Color(252, 218, 72),
                "😀");

        // ── "Pet" menu button ─────────────────────────────────────
        drawTextBtn(g2, petMenuBtn,
                petMenuOpen ? new Color(252, 218, 72, 220) : new Color(30, 15, 5, 200),
                petMenuOpen ? new Color(42, 12, 0)         : new Color(252, 218, 72),
                "🐾 Pet",
                petMenuBtn.contains(mouseX, mouseY));

        // ── Pet sub-menu ──────────────────────────────────────────
        if (petMenuOpen) {
            drawTextBtn(g2, hidePetSlot,
                    new Color(30, 15, 5, 210),
                    new Color(252, 218, 72),
                    petVisible ? "Hide" : "Show",
                    hidePetSlot.contains(mouseX, mouseY));
            drawTextBtn(g2, customizePetSlot,
                    new Color(30, 15, 5, 210),
                    new Color(180, 230, 255),
                    "Style",
                    customizePetSlot.contains(mouseX, mouseY));
        }

        // ── Emote picker panel ────────────────────────────────────
        if (!pickerOpen) return;

        int px = emoteSlots[0].x - 4;
        int py = emoteSlots[0].y - 4;
        int pw = BTN_SIZE + 8;
        int ph = EMOTES.length * (BTN_SIZE + 4) + 8;

        g2.setPaint(new LinearGradientPaint(px, py, px, py + ph,
                new float[]{0f, 1f},
                new Color[]{new Color(20, 10, 2, 230), new Color(10, 5, 0, 245)}));
        g2.fillRoundRect(px, py, pw, ph, 12, 12);
        g2.setStroke(new BasicStroke(1.6f));
        g2.setColor(new Color(252, 218, 72, 160));
        g2.drawRoundRect(px, py, pw, ph, 12, 12);

        for (int i = 0; i < EMOTES.length; i++) {
            Rectangle slot    = emoteSlots[i];
            boolean hovered = slot.contains(mouseX, mouseY);
            g2.setColor(hovered ? new Color(252, 218, 72, 180) : new Color(40, 20, 5, 180));
            g2.fillRoundRect(slot.x, slot.y, slot.width, slot.height, 10, 10);
            g2.setStroke(new BasicStroke(1.2f));
            g2.setColor(new Color(252, 218, 72, 100));
            g2.drawRoundRect(slot.x, slot.y, slot.width, slot.height, 10, 10);

            Shape oldClip = g2.getClip();
            g2.setClip(slot.x, slot.y, slot.width, slot.height);
            g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(EMOTES[i],
                    slot.x + (slot.width  - fm.stringWidth(EMOTES[i])) / 2,
                    slot.y + (slot.height + fm.getAscent() - fm.getDescent()) / 2 - 1);
            g2.setClip(oldClip);
        }

        g2.setFont(new Font("Serif", Font.ITALIC, 9));
        g2.setColor(new Color(252, 218, 72, 140));
        g2.drawString("pet reacts!", px + 4, py + ph + 12);
    }

    public void drawUI(Graphics2D g2) { drawUI(g2, screenW); }

    private void drawBtn(Graphics2D g2, Rectangle r,
                         Color bg, Color fg, String emoji) {
        boolean hovered = r.contains(mouseX, mouseY);
        Color drawBg = hovered
                ? new Color(Math.min(255, bg.getRed()   + 40),
                Math.min(255, bg.getGreen() + 30),
                Math.min(255, bg.getBlue()  + 20),
                Math.min(255, bg.getAlpha() + 20))
                : bg;

        g2.setColor(drawBg);
        g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);

        if (hovered) {
            g2.setStroke(new BasicStroke(2f));
            g2.setColor(new Color(252, 218, 72, 220));
        } else {
            g2.setStroke(new BasicStroke(1.4f));
            g2.setColor(new Color(252, 218, 72, 120));
        }
        g2.drawRoundRect(r.x, r.y, r.width, r.height, 10, 10);

        Shape old = g2.getClip();
        g2.setClip(r.x, r.y, r.width, r.height);
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        g2.setColor(fg);
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(emoji,
                r.x + (r.width  - fm.stringWidth(emoji)) / 2,
                r.y + (r.height + fm.getAscent() - fm.getDescent()) / 2);
        g2.setClip(old);
    }

    private void drawTextBtn(Graphics2D g2, Rectangle r,
                             Color bg, Color fg, String text,
                             boolean hovered) {
        // Brighten on hover
        Color drawBg = hovered
                ? new Color(Math.min(255, bg.getRed()   + 40),
                Math.min(255, bg.getGreen() + 30),
                Math.min(255, bg.getBlue()  + 20),
                Math.min(255, bg.getAlpha() + 20))
                : bg;

        g2.setColor(drawBg);
        g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);

        // Gold glow ring on hover
        if (hovered) {
            g2.setStroke(new BasicStroke(2f));
            g2.setColor(new Color(252, 218, 72, 200));
        } else {
            g2.setStroke(new BasicStroke(1.3f));
            g2.setColor(new Color(252, 218, 72, 110));
        }
        g2.drawRoundRect(r.x, r.y, r.width, r.height, 10, 10);

        // Text — slightly larger on hover
        g2.setFont(hovered
                ? new Font("Serif", Font.BOLD, 11)
                : new Font("Serif", Font.BOLD, 10));
        g2.setColor(hovered ? new Color(255, 240, 140) : fg);
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(text,
                r.x + (r.width  - fm.stringWidth(text)) / 2,
                r.y + (r.height + fm.getAscent() - fm.getDescent()) / 2);
    }


    // ── Draw floating emotes ──────────────────────────────────────
    public void drawFloatingEmotes(Graphics2D g2, GamePanel gp) {
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        for (FloatingEmote fe : floatingEmotes) {
            int sx = fe.worldX - gp.player.worldX + gp.player.screenX - 16;
            int sy = fe.worldY - gp.player.worldY + gp.player.screenY + (int) fe.offsetY;
            float a = Math.max(0f, Math.min(1f, fe.alpha));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
            g2.setColor(new Color(0, 0, 0, (int)(120 * a)));
            g2.drawString(fe.emote, sx + 2, sy + 2);
            g2.setColor(Color.WHITE);
            g2.drawString(fe.emote, sx, sy);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}