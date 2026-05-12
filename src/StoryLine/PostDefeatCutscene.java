package StoryLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import Main.GamePanel;
import Audio.SFX.ClickSFX;

public class PostDefeatCutscene extends JPanel {
    private static final int W = 1024, H = 768;
    private static final Color TEXT_COLOR = new Color(40, 20, 0);
    private static final Color PARCH_BORDER = new Color(80, 38, 2, 200);

    private static final Object[][][] PAGE_DIALOGUE = {
            // Page 5
            {{"narrator", "The battle erupted like a storm... strikes precise, but Zed moved like smoke, no longer bound by mortal limitations."}, {"Khai", "... I can't move... what have you done to me?"}},
            // Page 6
            {{"Zed", "Go home, boy. Tell them what you learned here."}, {"Zed", "Tell them I am not their enemy. I am their only hope."}, {"Zed", "And when they refuse to listen — as they always do — return to me."}},
            // Page 7
            {{"narrator", "The curse didn't take Khai's life; it took his hope. He was sent back to the village not as a victor, but as a warning."}},
            // Page 8
            {{"narrator", "Khai collapsed at the barrier, a hollow shell. The legacy of his parents remained, but the fire in his soul was nearly gone."}},
            // Page 9
            {{"narrator", "Found by scouts, Khai was rushed to the Healer's Hall. No medicine could mend a spirit imprisoned by shadow."}},
            // Page 10
            {{"Khai", "I failed."}, {"Chief", "Tell me everything."}, {"Khai", "He believes he is saving us, Chief. He truly thinks he is right."}, {"Chief", "Belief does not make something true. The worst men in history all believed they were right."}, {"Chief", "Your parents were great because they could fail and keep going. Be broken and let others help them rebuild."}},
            // Page 11
            {{"narrator", "The people of Neverwinter carry an essence. If they offer it willingly, the curse would shatter."}, {"Chief", "We are asking you to let them choose. This is the difference, Khai. This is why you are not him."}},
            // Page 12
            {{"narrator", "The shadows recede, replaced by the collective spirit of Neverwinter."}, {"Khai", "I feel it... as a light. Their strength is now my own."}, {"Khai", "If they stand with me, then I will be the shield they deserve. The sorcerer ends today."}}
    };

    private Image[] bgImages;
    private int currentPage = 0, lineIndex = 0, charIndex = 0;
    private java.util.List<DialogueLine> currentLines = new java.util.ArrayList<>();
    private String displayedPartial = "";
    private Timer typewriterTimer, fadeTimer, panTimer;
    private float alpha = 1.0f, panX = 0;
    private GoldButton nextBtn, backBtn, skipBtn;
    private Runnable onFinish;
    private GamePanel gamePanel;
    public void setGamePanel(GamePanel gp) { this.gamePanel = gp; }
    private void playClickSFX() {
        if (gamePanel != null) gamePanel.getSFXPlayer().playSFX(new ClickSFX());
    }

    public PostDefeatCutscene(Runnable onFinish) {
        this.onFinish = onFinish;
        setPreferredSize(new Dimension(W, H));
        setLayout(null);

        bgImages = new Image[PAGE_DIALOGUE.length];
        String[] paths = {
                "/cutscene/throne/p5.jpg", "/cutscene/throne/p6.jpg", "/cutscene/throne/p7.jpg", "/cutscene/throne/p8.jpg",
                "/cutscene/throne/p9.jpg", "/cutscene/throne/p10.jpg", "/cutscene/throne/p11.jpg", "/cutscene/throne/p12.jpg"
        };

        for (int i = 0; i < bgImages.length; i++) {
            try { bgImages[i] = ImageIO.read(getClass().getResource(paths[i])); }
            catch (Exception e) { System.err.println("Could not load: " + paths[i]); }
        }

        setupButtons();
        loadPage(0);
        fadeIn();
    }

    private static class DialogueLine { String s, t; DialogueLine(String s, String t) { this.s = s; this.t = t; } }

    private void loadPage(int p) {
        currentPage = p; currentLines.clear(); lineIndex = 0; charIndex = 0; displayedPartial = "";
        for (Object[] line : PAGE_DIALOGUE[p]) currentLines.add(new DialogueLine((String)line[0], (String)line[1]));
        nextBtn.setVisible(false); backBtn.setVisible(false);
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

        String fullText = currentLines.get(lineIndex).t;
        typewriterTimer = new Timer(7, e -> { // SPEED UP: 12ms for faster text flow
            if (charIndex < fullText.length()) {
                displayedPartial += fullText.charAt(charIndex++);
                repaint();
            } else {
                typewriterTimer.stop();
                lineIndex++;

                // Wait slightly so player can read, then move to next line
                Timer pause = new Timer(900, ev -> { // SPEED UP: Shorter pause between lines
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

    private void showNavigation() {
        if (currentPage > 0) backBtn.setVisible(true);
        nextBtn.setText(currentPage == PAGE_DIALOGUE.length - 1 ? "FINAL BATTLE →" : "NEXT →");
        nextBtn.setVisible(true);
    }

    private void changePage(int d) {
        alpha = 0f;
        fadeTimer = new Timer(15, e -> {
            alpha += 0.08f;
            if (alpha >= 1f) {
                ((Timer)e.getSource()).stop();
                loadPage(currentPage + d);
                fadeIn();
            }
            repaint();
        });
        fadeTimer.start();
    }

    private void fadeIn() {
        alpha = 1f;
        fadeTimer = new Timer(20, e -> {
            alpha -= 0.05f;
            if (alpha <= 0f) { alpha = 0f; ((Timer)e.getSource()).stop(); }
            repaint();
        });
        fadeTimer.start();
    }

    private void fadeToFinish() {
        if (typewriterTimer != null) typewriterTimer.stop();
        nextBtn.setVisible(false); backBtn.setVisible(false); skipBtn.setVisible(false);
        alpha = 0f;
        fadeTimer = new Timer(20, e -> {
            alpha += 0.05f;
            if (alpha >= 1f) {
                alpha = 1f;
                ((Timer)e.getSource()).stop();
                SwingUtilities.invokeLater(onFinish);
            }
            repaint();
        });
        fadeTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (bgImages[currentPage] != null) g2.drawImage(bgImages[currentPage], (int)panX, 0, 1180, 768, null);
        else {
            g2.setPaint(new GradientPaint(0, 0, new Color(10, 5, 25), 0, H, new Color(20, 10, 45)));
            g2.fillRect(0, 0, W, H);
        }

        g2.setColor(new Color(0, 0, 0, 110)); // Darkness vignette
        g2.fillRect(0, 0, W, H);

        paintParchmentBox(g2);

        g2.setColor(new Color(0, 0, 0, (int)(alpha * 255)));
        g2.fillRect(0, 0, W, H);
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
            drawWrappedLine(g2, nF, tF, active.s, displayedPartial,
                    tx, ty, pw - 80, false, active.s);

        } else if (lineIndex > 0) {
            // ── DONE TYPING: show last line — use THAT line's speaker for color ──
            DialogueLine last = currentLines.get(lineIndex - 1);
            drawWrappedLine(g2, nF, tF, last.s, last.t,
                    tx, ty, pw - 80, true, last.s);
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
        backBtn = new GoldButton("← BACK");
        backBtn.setBounds(62, 700, 140, 45);
        backBtn.setVisible(false);
        backBtn.addActionListener(e -> { playClickSFX(); changePage(-1); });

        nextBtn = new GoldButton("NEXT →");
        nextBtn.setBounds(822, 700, 140, 45);
        nextBtn.setVisible(false);
        nextBtn.addActionListener(e -> {
            playClickSFX();
            if (currentPage < PAGE_DIALOGUE.length - 1) changePage(1);
            else fadeToFinish();
        });

        skipBtn = new GoldButton("SKIP");
        skipBtn.setBounds(840, 30, 120, 40);
        skipBtn.addActionListener(e -> { playClickSFX(); fadeToFinish(); });

        add(backBtn); add(nextBtn); add(skipBtn);
    }

    private static class GoldButton extends JButton {
        private boolean h = false;
        GoldButton(String t) {
            super(t);
            setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
            setFont(new Font("Serif", Font.BOLD, 16)); setForeground(new Color(42, 12, 0));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { h = true; repaint(); }
                public void mouseExited(MouseEvent e) { h = false; repaint(); }
            });
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h_ = getHeight(), c = 8;
            Polygon oct = new Polygon(new int[]{c, w-c, w, w, w-c, c, 0, 0}, new int[]{0, 0, c, h_-c, h_, h_, h_-c, c}, 8);
            Color tc = h ? new Color(255, 255, 208) : new Color(252, 240, 122);
            Color bc = h ? new Color(212, 148, 12) : new Color(178, 108, 0);
            g2.setPaint(new LinearGradientPaint(0, 0, 0, h_, new float[]{0f, 1f}, new Color[]{tc, bc}));
            g2.fill(oct);
            g2.setColor(new Color(82, 38, 0, 210));
            g2.draw(oct);
            FontMetrics fm = g2.getFontMetrics();
            g2.setColor(getForeground());
            g2.drawString(getText(), (w - fm.stringWidth(getText())) / 2, (h_ + fm.getAscent() - fm.getDescent()) / 2);
            g2.dispose();
        }
    }
}