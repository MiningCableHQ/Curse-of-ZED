package Dialogue;

import Dialogue.*;
import Entities.Characters.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import Main.*;


public class DialogueSystem {

    private BufferedImage playerPortrait;
    public void setPlayerPortrait(BufferedImage img) { this.playerPortrait = img;}
    private Runnable weaponPopupCallback;
    public void setWeaponPopupCallback(Runnable r) { this.weaponPopupCallback = r; }
    private Runnable onPhaseChangeCallback;
    private java.util.function.Consumer<String> onEssenceGranted;
    public void setOnEssenceGranted(
            java.util.function.Consumer<String> cb) {
        this.onEssenceGranted = cb;
    }
    public void setOnPhaseChangeCallback(Runnable r) {
        this.onPhaseChangeCallback = r;
    }
        // ── Layout ────────────────────────────────────────────────────
    private static final int BOX_X      = 30;
    private static final int BOX_Y      = 490;
    private static final int BOX_W      = 1024 - 60;
    private static final int BOX_H      = 250;
    private static final int PORTRAIT_W = 200;
    private static final int PORTRAIT_H = 200;
    private static final int PORTRAIT_X = BOX_X + 18;
    private static final int PORTRAIT_Y = BOX_Y + (BOX_H - PORTRAIT_H) / 2;
    private static final int TEXT_X     = PORTRAIT_X + PORTRAIT_W + 22;
    private static final int TEXT_W     = BOX_W - PORTRAIT_W - 60;

    // ── Colors per speaker ────────────────────────────────────────
    // Each NPC gets a unique box accent color
    private static final Color COLOR_CHIEF  = new Color(180,  90,  30); // warm amber
    private static final Color COLOR_RANGER = new Color( 40, 130,  60); // forest green
    private static final Color COLOR_FRANK  = new Color(200, 140,   0); // gold/merchant
    private static final Color COLOR_PLAYER = new Color( 60, 100, 200); // blue for Khai
    private static final Color COLOR_DEFAULT= new Color( 80,  60, 100);

    // ── Fonts ─────────────────────────────────────────────────────
    private Font fontName;
    private Font fontText;
    private Font fontBtn;

    // ── State ─────────────────────────────────────────────────────
    private boolean active          = false;
    private DialogueTree tree       = null;
    private int currentPage         = 0;
    private NPC currentNPC          = null;
    private String playerClassName  = "Swordsman";

    // Typewriter
    private String fullText         = "";
    private int    charIndex        = 0;
    private int    typewriterTick   = 0;
    private static final int TYPEWRITER_SPEED = 2; // frames per char
    private boolean textDone        = false;

    // Buttons
    private Rectangle btnNext  = new Rectangle();
    private Rectangle btnBack  = new Rectangle();
    private Rectangle btnExit  = new Rectangle();
    private Rectangle[] choiceBtns = new Rectangle[4];

    // Hover
    private int hoveredChoice = -1;
    private boolean hoverNext = false, hoverBack = false, hoverExit = false;

    // Callback for shop
    private Runnable onOpenShop;

    public DialogueSystem() {
        loadFonts();
        for (int i = 0; i < choiceBtns.length; i++) {
            choiceBtns[i] = new Rectangle();
        }
    }
    private Runnable onMap2MonologueDone;

    public void setOnMap2MonologueDone(Runnable r) {
        this.onMap2MonologueDone = r;
    }
    // ── Fonts ─────────────────────────────────────────────────────
    private void loadFonts() {
        Font base = null;
        for (String n : new String[]{
                "RINGM___.TTF","RingbearerMedium.ttf","Ringbearer Medium.ttf",
                "ringbearer medium.ttf","Ringbearer.ttf","ringbearer.ttf"}) {
            java.io.File f = new java.io.File(n);
            if (!f.exists()) continue;
            try {
                base = Font.createFont(Font.TRUETYPE_FONT, f);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(base);
                break;
            } catch (Exception ex) { /* ignore */ }
        }
        if (base == null) base = new Font("Palatino Linotype", Font.PLAIN, 12);

        fontName = base.deriveFont(Font.PLAIN, 16f);
        fontText = new Font("Serif", Font.PLAIN, 17);
        fontBtn  = new Font("Serif", Font.BOLD,  13);
    }

    // ── Public API ────────────────────────────────────────────────
    public void setOnOpenShop(Runnable r) { this.onOpenShop = r; }

    public void open(NPC npc, String playerClassName) {
        if (!npc.available) return;
        this.currentNPC       = npc;
        this.playerClassName  = playerClassName;
        this.tree             = npc.getDialogue(playerClassName);
        this.currentPage      = 0;
        this.active           = true;
        startPage(0);
    }

    public boolean isActive() { return active; }

    public void close() {
        active = false;
        currentNPC = null;
        tree = null;
    }

    /** Called every game tick while dialogue is open */
    public void update() {
        if (!active) return;
        if (!textDone) {
            typewriterTick++;
            if (typewriterTick >= TYPEWRITER_SPEED) {
                typewriterTick = 0;
                if (charIndex < fullText.length()) {
                    charIndex++;
                } else {
                    textDone = true;
                }
            }
        }
    }

    /** Handle mouse click; call from GamePanel's mouseClicked */
    public void handleClick(int mx, int my) {
        if (!active) return;

        DialogueTree.Page page = tree.getPage(currentPage);
        if (page == null) { close(); return; }

        // If text still typing, clicking anywhere finishes it instantly
        if (!textDone) {
            charIndex = fullText.length();
            textDone  = true;
            return;
        }

        // Choice buttons
        if (page.choices != null && !page.choices.isEmpty()) {
            for (int i = 0; i < page.choices.size() && i < choiceBtns.length; i++) {
                if (choiceBtns[i].contains(mx, my)) {
                    DialogueTree.Choice choice = page.choices.get(i);
                    if (choice.opensShop) {
                        close();
                        if (onOpenShop != null) onOpenShop.run();
                    } else if (choice.jumpToPage == -1) {
                        // ── Closing via choice — check if we should fire end callbacks ──
                        onDialogueEndViaChoice();
                        close();
                    } else {
                        startPage(choice.jumpToPage);
                    }
                    return;
                }
            }
            return;
        }

        // Next button
        if (btnNext.contains(mx, my)) {
            int next = currentPage + 1;
            if (next >= tree.size()) {
                onDialogueEnd();
            } else {
                startPage(next);
            }
            return;
        }

        // Back button
        if (btnBack.contains(mx, my) && currentPage > 0) {
            startPage(currentPage - 1);
            return;
        }

        // Exit button
        if (btnExit.contains(mx, my)) {
            close();
        }
    }

    /** Call from GamePanel's mouseMoved to update hover state */
    public void handleHover(int mx, int my) {
        if (!active) return;
        hoverNext = btnNext.contains(mx, my);
        hoverBack = btnBack.contains(mx, my);
        hoverExit = btnExit.contains(mx, my);
        hoveredChoice = -1;
        DialogueTree.Page page = tree.getPage(currentPage);
        if (page != null && page.choices != null) {
            for (int i = 0; i < page.choices.size() && i < choiceBtns.length; i++) {
                if (choiceBtns[i].contains(mx, my)) hoveredChoice = i;
            }
        }
    }

    // ── Called when last page is done ─────────────────────────────
    private void onDialogueEnd() {
        if (currentNPC != null) {
            //currentNPC.interacted = true;

            // Chief dialogue done (ONLY during first visit)
            if (currentNPC.npcName.equals("Chief")
                    && GameStateManager.get().map1Phase
                    != GameStateManager.Map1Phase.COLLECT_ESSENCE) {

                GameStateManager.get().map1Phase =
                        GameStateManager.Map1Phase.TALK_TO_RANGER;

                if (onPhaseChangeCallback != null) onPhaseChangeCallback.run();
            }

            if (currentNPC.npcName.equals("Ranger")
                    && GameStateManager.get().map1Phase
                    == GameStateManager.Map1Phase.TALK_TO_RANGER) {

                GameStateManager.get().map1Phase =
                        GameStateManager.Map1Phase.RECEIVE_WEAPON;

                if (weaponPopupCallback != null) weaponPopupCallback.run();
            }

            // Frankenstein fight — page 4 is the fight confirmation page
            if (currentNPC.npcName.equals("Frankenstein")
                    && currentPage == 4) {
                if (currentNPC instanceof NPC_Frankenstein) {
                    Runnable fightCb =
                            ((NPC_Frankenstein) currentNPC).getOnFightChosen();
                    if (fightCb != null) fightCb.run();
                }
            }
        }
        // Essence NPCs — grant essence when dialogue ends
        if (currentNPC != null) {
            String n = currentNPC.npcName;

            if ((n.equals("Healer") || n.equals("Farmer")
                    || n.equals("Woman Villager") || n.equals("Ranger")
                    || n.equals("Chief"))
                    && GameStateManager.get().map1Phase
                    == GameStateManager.Map1Phase.COLLECT_ESSENCE) {

                if (!currentNPC.interacted) {
                    currentNPC.interacted = true;

                    if (onEssenceGranted != null) {
                        onEssenceGranted.accept(n);
                    }
                }
            }
        }
        // Map 2 monologue done
        if (onMap2MonologueDone != null) {
            Runnable cb = onMap2MonologueDone;
            onMap2MonologueDone = null; // clear so it doesn't fire again
            cb.run();
        }
        close();
    }
    /** Called when dialogue closes via a choice button (jumpToPage == -1) */
    private void onDialogueEndViaChoice() {
        if (currentNPC == null) return;

        if (currentNPC.npcName.equals("Ranger")
                && currentPage == 3
                && GameStateManager.get().map1Phase
                == GameStateManager.Map1Phase.TALK_TO_RANGER) {
            GameStateManager.get().map1Phase =
                    GameStateManager.Map1Phase.RECEIVE_WEAPON;
            if (weaponPopupCallback != null) weaponPopupCallback.run();
        }

        if (currentNPC != null) {
            String n = currentNPC.npcName;
            if ((n.equals("Healer") || n.equals("Farmer")
                    || n.equals("Woman Villager"))
                    && GameStateManager.get().map1Phase
                    == GameStateManager.Map1Phase.COLLECT_ESSENCE
                    && !currentNPC.interacted) {
                currentNPC.interacted = true;
                if (onEssenceGranted != null) {
                    onEssenceGranted.accept(n);
                }
            }
        }

        // ADD THIS — fires the explore button callback
        if (onMap2MonologueDone != null) {
            Runnable cb = onMap2MonologueDone;
            onMap2MonologueDone = null;
            cb.run();
        }
    }

    // ── Start a specific page ─────────────────────────────────────
    private void startPage(int index) {
        currentPage  = index;
        charIndex    = 0;
        typewriterTick = 0;
        textDone     = false;

        DialogueTree.Page page = tree.getPage(index);
        // For choice pages with no text body, skip directly to choices
        if (page != null && page.text != null && !page.text.isEmpty()) {
            fullText = page.text;
        } else {
            fullText  = "";
            textDone  = true;
        }
    }

    // ── Draw ──────────────────────────────────────────────────────
    public void draw(Graphics2D g2) {
        if (!active || tree == null) return;

        DialogueTree.Page page = tree.getPage(currentPage);
        if (page == null) return;

        Color accent = getAccentColor(page.speaker);

        // ── 1. Box shadow ──
        for (int i = 10; i >= 1; i--) {
            float a = 0.05f * (11 - i);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
            g2.setColor(Color.BLACK);
            g2.fill(new RoundRectangle2D.Float(BOX_X - i, BOX_Y + i * 2,
                    BOX_W + i * 2, BOX_H, 18, 18));
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // ── 2. Main parchment body ──
        g2.setPaint(new LinearGradientPaint(BOX_X, BOX_Y, BOX_X, BOX_Y + BOX_H,
                new float[]{0f, 0.08f, 0.5f, 0.92f, 1f},
                new Color[]{
                        new Color(50, 30, 10, 230),
                        new Color(28, 18, 8, 240),
                        new Color(22, 14, 6, 250),
                        new Color(28, 18, 8, 240),
                        new Color(50, 30, 10, 230)
                }));
        Shape body = new RoundRectangle2D.Float(BOX_X, BOX_Y, BOX_W, BOX_H, 18, 18);
        g2.fill(body);

        // ── 3. Accent color top bar ──
        g2.setPaint(new LinearGradientPaint(BOX_X, BOX_Y, BOX_X + BOX_W, BOX_Y,
                new float[]{0f, 0.5f, 1f},
                new Color[]{
                        new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 0),
                        new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 180),
                        new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 0)
                }));
        g2.setStroke(new BasicStroke(3f));
        g2.drawLine(BOX_X + 18, BOX_Y + 2, BOX_X + BOX_W - 18, BOX_Y + 2);

        // ── 4. Outer border ──
        g2.setStroke(new BasicStroke(2.5f));
        g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 160));
        g2.draw(body);

        // Inner border
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 60));
        g2.draw(new RoundRectangle2D.Float(BOX_X + 5, BOX_Y + 5,
                BOX_W - 10, BOX_H - 10, 13, 13));

        // ── 5. Portrait ──
        drawPortrait(g2, accent, page.isPlayerLine);

        // ── 6. Speaker name ──
        int nameY = BOX_Y + 34;
        g2.setFont(fontName);
        FontMetrics fmN = g2.getFontMetrics();
        String speaker = page.speaker != null ? page.speaker : "";
        int nameW = fmN.stringWidth(speaker);
        // Name badge background
        g2.setPaint(new LinearGradientPaint(TEXT_X, nameY - 18, TEXT_X + nameW + 20, nameY,
                new float[]{0f, 1f},
                new Color[]{
                        new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 140),
                        new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 0)
                }));
        g2.fillRoundRect(TEXT_X - 6, nameY - fmN.getAscent() - 2,
                nameW + 18, fmN.getHeight() + 4, 6, 6);

        // Name text
        g2.setColor(new Color(0, 0, 0, 120));
        g2.drawString(speaker, TEXT_X + 2, nameY + 2);
        g2.setColor(page.isPlayerLine
                ? new Color(180, 210, 255)
                : new Color(252, 218, 72));
        g2.drawString(speaker, TEXT_X, nameY);

        // Divider under name
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 80));
        g2.drawLine(TEXT_X, nameY + 4, TEXT_X + TEXT_W - 20, nameY + 4);

        // ── 7. Dialogue text (typewriter) ──
        String displayed = fullText.substring(0, Math.min(charIndex, fullText.length()));
        g2.setFont(fontText);
        drawWrappedText(g2, displayed, TEXT_X, nameY + 22, TEXT_W - 20, 22);

        // ── 8. Buttons or Choices ──
        if (page.choices != null && !page.choices.isEmpty() && textDone) {
            drawChoiceButtons(g2, page, accent);
        } else if (textDone) {
            drawNavButtons(g2, accent);
        }
    }

    // ── Portrait drawing ──────────────────────────────────────────
    private void drawPortrait(Graphics2D g2, Color accent,  boolean isPlayerLine) {
        // Portrait frame
        for (int i = 6; i >= 1; i--) {
            float a = 0.08f * (7 - i);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
            g2.setColor(accent);
            g2.fillRoundRect(PORTRAIT_X - i, PORTRAIT_Y - i,
                    PORTRAIT_W + i * 2, PORTRAIT_H + i * 2, 12, 12);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Dark background behind portrait
        g2.setColor(new Color(10, 6, 2));
        g2.fillRoundRect(PORTRAIT_X, PORTRAIT_Y, PORTRAIT_W, PORTRAIT_H, 10, 10);

        // Portrait image
        BufferedImage portrait = getPortrait(isPlayerLine);
        if (portrait != null) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(portrait, PORTRAIT_X, PORTRAIT_Y, PORTRAIT_W, PORTRAIT_H, null);
        } else {
            // Fallback colored block with initial
            g2.setColor(new Color(accent.getRed() / 3, accent.getGreen() / 3, accent.getBlue() / 3));
            g2.fillRoundRect(PORTRAIT_X, PORTRAIT_Y, PORTRAIT_W, PORTRAIT_H, 10, 10);
            g2.setFont(new Font("Serif", Font.BOLD, 60));
            g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 180));
            String initial = currentNPC != null ? currentNPC.npcName.substring(0, 1) : "?";
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(initial,
                    PORTRAIT_X + (PORTRAIT_W - fm.stringWidth(initial)) / 2,
                    PORTRAIT_Y + (PORTRAIT_H + fm.getAscent()) / 2 - 10);
        }

        // Portrait border
        g2.setStroke(new BasicStroke(2.5f));
        g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 200));
        g2.drawRoundRect(PORTRAIT_X, PORTRAIT_Y, PORTRAIT_W, PORTRAIT_H, 10, 10);

        // Inner portrait border
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 80));
        g2.drawRoundRect(PORTRAIT_X + 4, PORTRAIT_Y + 4,
                PORTRAIT_W - 8, PORTRAIT_H - 8, 7, 7);
    }

    private BufferedImage getPortrait(boolean isPlayerLine) {
        if (isPlayerLine) return playerPortrait;
        if (currentNPC == null) return null;
        try {
            java.lang.reflect.Method m = currentNPC.getClass().getMethod("getPortrait");
            return (BufferedImage) m.invoke(currentNPC);
        } catch (Exception e) {
            return null;
        }
    }

    // ── Nav buttons (Next / Back / Exit) ─────────────────────────
    private void drawNavButtons(Graphics2D g2, Color accent) {
        int btnY  = BOX_Y + BOX_H - 52;
        int btnH  = 36;
        int btnW  = 110;

        // Next button — right side
        int nextX = BOX_X + BOX_W - btnW - 18;
        btnNext.setBounds(nextX, btnY, btnW, btnH);
        drawStyledButton(g2, btnNext, "Next ›", accent, hoverNext);

        // Back button — left of Next
        if (currentPage > 0) {
            int backX = nextX - btnW - 10;
            btnBack.setBounds(backX, btnY, btnW, btnH);
            drawStyledButton(g2, btnBack, "‹ Back", new Color(100, 100, 100), hoverBack);
        } else {
            btnBack.setBounds(0, 0, 0, 0);
        }

        // Exit / Close button — far left
        btnExit.setBounds(PORTRAIT_X + PORTRAIT_W + 22, btnY, 90, btnH);
        drawStyledButton(g2, btnExit, "✕ Exit", new Color(150, 50, 50), hoverExit);
    }

    // ── Choice buttons ────────────────────────────────────────────
    private void drawChoiceButtons(Graphics2D g2, DialogueTree.Page page, Color accent) {
        int count = Math.min(page.choices.size(), choiceBtns.length);

        int btnW = 340;
        int btnH = 38;
        int gap  = 10;

        int startX = BOX_X + 270;

        int totalH = count * btnH + (count - 1) * gap;
        int startY = BOX_Y + (BOX_H - totalH) / 2 + 10;

        for (int i = 0; i < count; i++) {
            int bx = startX;
            int by = startY + i * (btnH + gap);

            choiceBtns[i].setBounds(bx, by, btnW, btnH);

            boolean hovered = (hoveredChoice == i);

            // Background colors
            Color bg = hovered ? new Color(100, 180, 255, 220)
                    : new Color(20, 40, 70, 200);

            // Border colors
            Color border = hovered ? new Color(0, 150, 255)
                    : new Color(50, 100, 180, 180);

            // Background
            g2.setColor(bg);
            g2.fillRoundRect(bx, by, btnW, btnH, 10, 10);

            // Border
            g2.setColor(new Color(0, 102, 204));
            g2.setStroke(new BasicStroke(1.8f));
            g2.drawRoundRect(bx, by, btnW, btnH, 10, 10);

            // Text
            g2.setFont(new Font("Serif", Font.PLAIN, 14));
            g2.setColor(hovered ? new Color(40, 10, 0)
                    : new Color(230, 210, 170));

            FontMetrics fm = g2.getFontMetrics();
            String text = page.choices.get(i).label;

            int tx = bx + 14;
            int ty = by + btnH / 2 + fm.getAscent() / 2 - 2;

            g2.drawString(text, tx, ty);
        }
    }
    // ── Styled button renderer ────────────────────────────────────
    private void drawStyledButton(Graphics2D g2, Rectangle r,
                                  String label, Color accent, boolean hovered) {
        int x = r.x, y = r.y, w = r.width, h = r.height;
        int c = 6;

        // Polygon (octagon-ish corners)
        Polygon oct = new Polygon(
                new int[]{x+c, x+w-c, x+w, x+w,   x+w-c, x+c, x,   x},
                new int[]{y,   y,     y+c, y+h-c,  y+h,   y+h, y+h-c, y+c},
                8);

        // Glow
        if (hovered) {
            for (int i = 8; i >= 2; i -= 2) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
                g2.setColor(accent);
                g2.fill(new Polygon(
                        new int[]{x-i+c, x+w+i-c, x+w+i, x+w+i,   x+w+i-c, x-i+c, x-i,   x-i},
                        new int[]{y-i,   y-i,     y-i+c, y+h+i-c,  y+h+i,   y+h+i, y+h+i-c, y-i+c},
                        8));
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        // Face
        Color lo = hovered
                ? new Color(Math.min(accent.getRed() + 50, 255),
                Math.min(accent.getGreen() + 50, 255),
                Math.min(accent.getBlue() + 50, 255))
                : accent;
        Color hi = new Color(
                Math.max(accent.getRed() - 60, 0),
                Math.max(accent.getGreen() - 60, 0),
                Math.max(accent.getBlue() - 60, 0));
        g2.setPaint(new LinearGradientPaint(x, y, x, y + h,
                new float[]{0f, 0.5f, 1f},
                new Color[]{lo, hi, new Color(hi.getRed() / 2, hi.getGreen() / 2, hi.getBlue() / 2)}));
        g2.fill(oct);

        // Top sheen
        g2.setColor(new Color(255, 255, 255, hovered ? 60 : 30));
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawLine(x + c, y + 1, x + w - c, y + 1);

        // Border
        g2.setStroke(new BasicStroke(1.8f));
        g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(),
                hovered ? 255 : 180));
        g2.draw(oct);

        // Label
        g2.setFont(fontBtn);
        FontMetrics fm = g2.getFontMetrics();
        // Word-wrap if needed: try to fit, else truncate
        String txt = label;
        while (fm.stringWidth(txt) > w - 10 && txt.length() > 3) {
            txt = txt.substring(0, txt.length() - 4) + "...";
        }
        int tx = x + (w - fm.stringWidth(txt)) / 2;
        int ty = y + (h + fm.getAscent() - fm.getDescent()) / 2;
        g2.setColor(new Color(0, 0, 0, 100));
        g2.drawString(txt, tx + 1, ty + 1);
        g2.setColor(new Color(240, 230, 210));
        g2.drawString(txt, tx, ty);
    }

    // ── Word-wrap text rendering ──────────────────────────────────
    private void drawWrappedText(Graphics2D g2, String text,
                                 int x, int y, int maxW, int lineH) {
        if (text == null || text.isEmpty()) return;
        FontMetrics fm = g2.getFontMetrics();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        int curY = y;

        g2.setColor(new Color(230, 215, 180)); // warm parchment text

        for (String word : words) {
            // Handle explicit newlines in text
            if (word.contains("\n")) {
                String[] parts = word.split("\n", -1);
                for (int i = 0; i < parts.length; i++) {
                    if (i > 0) {
                        // Draw current line and move down
                        if (line.length() > 0) {
                            g2.setColor(new Color(0, 0, 0, 80));
                            g2.drawString(line.toString(), x + 1, curY + 1);
                            g2.setColor(new Color(230, 215, 180));
                            g2.drawString(line.toString(), x, curY);
                            line = new StringBuilder();
                        }
                        curY += lineH;
                    }
                    if (!parts[i].isEmpty()) {
                        if (line.length() > 0) line.append(" ");
                        line.append(parts[i]);
                    }
                }
                continue;
            }

            String test = line.length() > 0 ? line + " " + word : word;
            if (fm.stringWidth(test) > maxW) {
                if (line.length() > 0) {
                    g2.setColor(new Color(0, 0, 0, 80));
                    g2.drawString(line.toString(), x + 1, curY + 1);
                    g2.setColor(new Color(230, 215, 180));
                    g2.drawString(line.toString(), x, curY);
                    curY += lineH;
                    line = new StringBuilder(word);
                } else {
                    line = new StringBuilder(word);
                }
            } else {
                if (line.length() > 0) line.append(" ");
                line.append(word);
            }
        }
        // Remaining
        if (line.length() > 0) {
            g2.setColor(new Color(0, 0, 0, 80));
            g2.drawString(line.toString(), x + 1, curY + 1);
            g2.setColor(new Color(230, 215, 180));
            g2.drawString(line.toString(), x, curY);
        }
    }

    private Color getAccentColor(String speaker) {
        if (speaker == null) return COLOR_DEFAULT;
        String s = speaker.toLowerCase();
        if (s.startsWith("khai"))         return COLOR_PLAYER;
        if (s.contains("chief"))          return COLOR_CHIEF;
        if (s.contains("ranger"))         return COLOR_RANGER;
        if (s.contains("frank")
                && !s.contains("stein"))  return COLOR_FRANK;
        if (s.contains("bukog"))          return new Color(102, 51, 153); // purple
        if (s.contains("frankenstein"))   return new Color(60, 160, 80);  // green
        if (s.contains("zed"))            return new Color(100, 20, 180); // dark purple
        if (s.contains("healer"))         return new Color(80, 180, 200); // teal
        if (s.contains("farmer"))         return new Color(120, 160, 40); // olive
        if (s.contains("woman")
                || s.contains("villager"))return new Color(200, 80, 120); // pink
        return COLOR_DEFAULT;
    }
}