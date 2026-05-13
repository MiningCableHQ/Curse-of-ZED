package StoryLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import Main.GamePanel;
import Audio.SFX.ClickSFX;

public class ThroneRoomCutscene extends JPanel {
    private static final int W = 1024, H = 768;
    private static final int STORY_PAGES = 4;

    private static final Color TEXT_COLOR = new Color(40, 20, 0);
    private static final Color PARCH_BORDER = new Color(80, 38, 2, 200);

    private static final Object[][][] PAGE_DIALOGUE = {
            // Page 1
            {
                    {"narrator", "Inside was an incredibly large hall... Zed sat with magical energy hovering around him like a celestial shroud."},
                    {"Zed", "Well. If it isn't Khai. I sensed a familiar presence. I didn't expect it to be Mathilde and Thalien's son."},
                    {"Khai", "Do not speak their names. You forfeited that right when you became a monster."},
                    {"Zed", "Monster. Yes, I've been called that many times... do you know what truly makes a monster?"}
            },
            // Page 2
            {
                    {"Zed", "The child not embraced by the village will burn it down just to feel its warmth."},
                    {"Zed", "If they will not have my protection, they shall have my shadow. I did not want to be this."}
            },
            // Page 3
            {
                    {"Khai", "I know what I've seen. The withering. The dead forest. The decay you left behind."},
                    {"Zed", "I saved that town. Together with your parents, I slew the demon king. And in return, I was shown visions. A darkness greater than anything Neverwinter has faced."},
                    {"Zed", "I sought power not for myself — but for all of you. To protect the very people who now curse my name."},
                    {"Khai", "You consumed innocent lives."},
                    {"Zed", "I borrowed from those who would have died anyway when the true darkness came. A fair trade, I thought."},
                    {"Zed", "But your parents didn't ask why. They simply decided I was the villain."},
                    {"Khai", "You could have stopped. After the banishment, you could have found another way."},
                    {"Zed", "Your parents are dead. The town weakens. And I am the only one who prepared."},
                    {"Khai", "I am sorry for what was done to you. But I cannot let you destroy everything my parents loved."},
                    {"Khai", "There must be another way."},
                    {"Zed", "There is no other way."},
                    {"Khai", "Then I'll find it."}
            },
            // Page 4
            {
                    {"Zed", "You have your mother’s heart, Khai... but it is too soft for the darkness I have seen. Let me show you why your parents failed!"}
            }
    };

    private Image[] bgImages;
    private int currentPage = 0;
    private java.util.List<DialogueLine> currentLines = new java.util.ArrayList<>();
    private int lineIndex = 0, charIndex = 0;
    private String displayedPartial = "";
    private Timer typewriterTimer, fadeTimer, panTimer, jitterTimer;
    private float alpha = 1.0f, panX = 0, currentWidth = 1200f;
    private int jitterX = 0, jitterY = 0;
    private boolean jitterActive = false;
    private GoldButton nextBtn, backBtn, skipBtn;
    private Runnable onFinish;
    private GamePanel gamePanel;
    public void setGamePanel(GamePanel gp) {
        this.gamePanel = gp;
        if (gp != null) gp.musicPlayer.playCutsceneMusic();
    }
    private void playClickSFX() {
        if (gamePanel != null) gamePanel.getSFXPlayer().playSFX(new ClickSFX());
    }

    public ThroneRoomCutscene(Runnable onFinish) {
        this.onFinish = onFinish;
        setPreferredSize(new Dimension(W, H));
        setLayout(null);

        bgImages = new Image[STORY_PAGES];
        String[] paths = {"/cutscene/throne/p1.jpg", "/cutscene/throne/p2.jpg", "/cutscene/throne/p3.jpg", "/cutscene/throne/p4.jpg"};
        for (int i = 0; i < STORY_PAGES; i++) {
            try { bgImages[i] = ImageIO.read(getClass().getResource(paths[i])); } catch (Exception e) {}
        }

        setupButtons();
        loadPage(0);
        fadeIn();
        // Pulse timer removed to get rid of violet tint
    }

    private static class DialogueLine {
        String speaker, text;
        DialogueLine(String s, String t) { speaker = s; text = t; }
    }

    private void loadPage(int page) {
        currentPage = page;
        currentLines.clear();
        lineIndex = 0;
        charIndex = 0;
        displayedPartial = "";

        for (Object[] line : PAGE_DIALOGUE[page]) {
            currentLines.add(new DialogueLine((String)line[0], (String)line[1]));
        }

        nextBtn.setVisible(false);
        backBtn.setVisible(false);

        // --- TRIGGER JITTER HERE ---
        // If we are on the last page (Page 4, which is index 3), start the shake
        if (currentPage == STORY_PAGES - 1) {
            startJitter();
        } else {
            stopJitter();
        }

        startPanning();
        startTypewriter();
    }

    private void startPanning() {
        if (panTimer != null) panTimer.stop();
        panX = 0;
        panTimer = new Timer(16, e -> {
            panX -= 1.2f; // INCREASED: Faster cinematic sweep
            if (panX + 1150 <= 1024) panX = 1024 - 1150;
            repaint();
        });
        panTimer.start();
    }

    private void startTypewriter() {
        if (typewriterTimer != null) typewriterTimer.stop();

        // CRITICAL FIX: Wipe the text IMMEDIATELY before starting the next line
        displayedPartial = "";
        charIndex = 0;
        repaint();

        if (lineIndex >= currentLines.size()) {
            showNavigation();
            return;
        }

        String fullText = currentLines.get(lineIndex).text;
        typewriterTimer = new Timer(20, e -> {
            if (charIndex < fullText.length()) {
                displayedPartial += fullText.charAt(charIndex++);
                repaint();
            } else {
                typewriterTimer.stop();
                lineIndex++;

                Timer pause = new Timer(1600, ev -> {
                    if (lineIndex < currentLines.size()) {
                        startTypewriter();
                    } else {
                        showNavigation();
                    }
                });
                pause.setRepeats(false);
                pause.start();
            }
        });
        typewriterTimer.start();
    }

    private void startJitter() { jitterActive = true; jitterTimer = new Timer(30, e -> { jitterX = (int)(Math.random() * 8 - 4); jitterY = (int)(Math.random() * 8 - 4); repaint(); }); jitterTimer.start(); }
    private void stopJitter() { jitterActive = false; if (jitterTimer != null) jitterTimer.stop(); }
    private void showNavigation() { if (currentPage > 0) backBtn.setVisible(true); nextBtn.setText(currentPage == STORY_PAGES - 1 ? "BATTLE →" : "NEXT →"); nextBtn.setVisible(true); }
    private void changePage(int delta) { alpha = 0f; fadeTimer = new Timer(15, e -> { alpha += 0.08f; if (alpha >= 1f) { ((Timer)e.getSource()).stop(); loadPage(currentPage + delta); fadeIn(); } repaint(); }); fadeTimer.start(); }
    private void fadeIn() { alpha = 1f; fadeTimer = new Timer(20, e -> { alpha -= 0.05f; if (alpha <= 0f) { alpha = 0f; ((Timer)e.getSource()).stop(); } repaint(); }); fadeTimer.start(); }
    private void fadeToBattle() { stopJitter(); nextBtn.setVisible(false); backBtn.setVisible(false); skipBtn.setVisible(false); alpha = 0f; Timer ft = new Timer(16, e -> { alpha += 0.06f; repaint(); if (alpha >= 1f) { ((Timer)e.getSource()).stop(); if (gamePanel != null) gamePanel.musicPlayer.stopCutsceneMusic(); SwingUtilities.invokeLater(onFinish); } }); ft.start(); }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g); Graphics2D g2 = (Graphics2D) g; g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Inside paintComponent
        int ox = jitterActive ? jitterX : 0, oy = jitterActive ? jitterY : 0;

        if (bgImages[currentPage] != null) {
            // We add ox and oy to the X and Y coordinates to create the shake
            g2.drawImage(bgImages[currentPage], (int)panX + ox, oy, (int)currentWidth, 768, null);
        }

        // 2. DARKNESS VIGNETTE
        g2.setColor(new Color(0, 0, 0, 110)); g2.fillRect(0, 0, W, H);

        // 3. UI
        paintParchmentBox(g2);

        // 4. SCREEN FADE
        g2.setColor(new Color(0, 0, 0, (int)(alpha * 255))); g2.fillRect(0, 0, W, H);
    }

    private void paintParchmentBox(Graphics2D g2) {
        int px = 62, py = 500, pw = 900, ph = 160;

        g2.setPaint(new LinearGradientPaint(px, py, px, py + ph,
                new float[]{0f, 0.5f, 1f},
                new Color[]{
                        new Color(220, 195, 160, 175),
                        new Color(240, 225, 200, 175),
                        new Color(220, 195, 160, 175)
                }));
        g2.fillRoundRect(px, py, pw, ph, 20, 20);
        g2.setStroke(new BasicStroke(4f));
        g2.setColor(PARCH_BORDER);
        g2.drawRoundRect(px, py, pw, ph, 20, 20);

        int tx = px + 40, ty = py + 60;
        Font nF = new Font("Serif", Font.BOLD, 24);
        Font tF = new Font("Serif", Font.BOLD | Font.ITALIC, 22);

        if (lineIndex < currentLines.size()) {
            // ── CURRENTLY TYPING: use the ACTIVE line's speaker for color ──
            DialogueLine active = currentLines.get(lineIndex);
            drawWrappedLine(g2, nF, tF, active.speaker, displayedPartial,
                    tx, ty, pw - 80, false, active.speaker);

        } else if (lineIndex > 0) {
            // ── DONE TYPING: show last line — use THAT line's speaker for color ──
            DialogueLine last = currentLines.get(lineIndex - 1);
            drawWrappedLine(g2, nF, tF, last.speaker, last.text,
                    tx, ty, pw - 80, true, last.speaker);
        }
    }

    private int drawWrappedLine(Graphics2D g2, Font nF, Font tF,
                                String speaker, String text,
                                int x, int y, int width, boolean done,
                                String colorSpeaker) {  // ← NEW param

        boolean isN = colorSpeaker.equalsIgnoreCase("narrator");
        int curX = x;

        // Color locked to the line being drawn, never bleeds
        Color speakerColor;
        if (isN) {
            speakerColor = new Color(40, 20, 0);
        } else if (colorSpeaker.equalsIgnoreCase("Zed")) {
            speakerColor = new Color(130, 0, 0);
        } else if (colorSpeaker.equalsIgnoreCase("Khai")) {
            speakerColor = new Color(0, 30, 100);
        } else if (colorSpeaker.equalsIgnoreCase("Chief")) {
            speakerColor = new Color(120, 80, 0);
        } else {
            speakerColor = new Color(40, 20, 0);
        }

        g2.setColor(speakerColor);

        if (!isN) {
            g2.setFont(nF);
            g2.drawString(colorSpeaker + ": ", curX, y);
            curX += g2.getFontMetrics().stringWidth(colorSpeaker + ": ");
        }

        g2.setFont(tF);
        FontMetrics fm = g2.getFontMetrics();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (fm.stringWidth(line + word) < (width - (curX - x))) {
                line.append(word).append(" ");
            } else {
                g2.setColor(speakerColor); // keep color locked on each line wrap
                g2.drawString(line.toString(), curX, y);
                y += fm.getHeight();
                curX = x;
                line = new StringBuilder(word).append(" ");
            }
        }
        g2.setColor(speakerColor);
        g2.drawString(line.toString(), curX, y);
        return y + (done ? fm.getHeight() + 2 : fm.getHeight());
    }

    private void setupButtons() {
        backBtn = new GoldButton("← BACK"); backBtn.setBounds(62, 700, 140, 45); backBtn.setVisible(false); backBtn.addActionListener(e -> { playClickSFX(); changePage(-1); });
        nextBtn = new GoldButton("NEXT →"); nextBtn.setBounds(822, 700, 140, 45); nextBtn.setVisible(false); nextBtn.addActionListener(e -> { playClickSFX(); if (currentPage < STORY_PAGES-1) changePage(1); else fadeToBattle(); });
        skipBtn = new GoldButton("SKIP"); skipBtn.setBounds(840, 30, 120, 40); skipBtn.addActionListener(e -> { playClickSFX(); fadeToBattle(); });
        add(backBtn); add(nextBtn); add(skipBtn);
    }

    private static class GoldButton extends JButton {
        private boolean h = false;
        GoldButton(String t) { super(t); setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false); setFont(new Font("Serif", Font.BOLD, 16)); setForeground(new Color(42, 12, 0)); setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); addMouseListener(new MouseAdapter() { public void mouseEntered(MouseEvent e) { h = true; repaint(); } public void mouseExited(MouseEvent e) { h = false; repaint(); } }); }
        protected void paintComponent(Graphics g) { Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); int w = getWidth(), h_ = getHeight(), c = 8; Polygon oct = new Polygon(new int[]{c, w-c, w, w, w-c, c, 0, 0}, new int[]{0, 0, c, h_-c, h_, h_, h_-c, c}, 8); Color tc = h ? new Color(255, 255, 208) : new Color(252, 240, 122); Color bc = h ? new Color(212, 148, 12) : new Color(178, 108, 0); g2.setPaint(new LinearGradientPaint(0, 0, 0, h_, new float[]{0f, 1f}, new Color[]{tc, bc})); g2.fill(oct); g2.setColor(new Color(82, 38, 0, 210)); g2.draw(oct); FontMetrics fm = g2.getFontMetrics(); g2.setColor(getForeground()); g2.drawString(getText(), (w - fm.stringWidth(getText())) / 2, (h_ + fm.getAscent() - fm.getDescent()) / 2); g2.dispose(); }
    }
}