package StoryLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import Main.GamePanel;
import Audio.SFX.ClickSFX;
import Audio.SFX.DefeatSFX;

public class DefeatCutscene extends JPanel {
    private static final int W = 1024, H = 768;

    // Unified Aesthetic Colors
    private static final Color TEXT_COLOR = new Color(40, 20, 0);
    private static final Color PARCH_BORDER = new Color(80, 38, 2, 200);

    private static final Object[][][] PAGE_DIALOGUE = {
            // Page 1
            {
                    {"Zed", "You carried their hope, Khai... but hope is a flickering candle in the face of the eternal void."},
                    {"Zed", "You were never enough."}
            },
            // Page 2
            {
                    {"Khai", "I can feel them... slipping away. Chief... Healer... Everyone— I'm sorry. I couldn't carry the weight."}
            },
            // Page 3
            {
                    {"narrator", "As the last hero of Neverwinter fell, the forest let out a final, silent scream. The withering was no longer a curse — it was the end of all things."}
            },
            // Page 4
            {
                    {"Zed", "Now we shall face the true darkness together... in the silence of the void."}
            }
    };

    private static final int TOTAL_PAGES = PAGE_DIALOGUE.length;
    private Image[] bgImages;
    private int currentPage = 0;
    private java.util.List<DialogueLine> currentLines = new java.util.ArrayList<>();
    private int lineIndex = 0, charIndex = 0;
    private String displayedPartial = "";
    private Timer typewriterTimer, fadeTimer, panTimer, defeatTimer;
    private float alpha = 1.0f;
    private float panX = 0, currentWidth = 1150f;
    private boolean showDefeat = false;
    private float defeatScale = 0f;
    private GoldButton nextBtn, backBtn, skipBtn;
    private JButton playAgainBtn;
    private Runnable onFinish;
    private GamePanel gamePanel;
    public void setGamePanel(GamePanel gp) {
        this.gamePanel = gp;
        if (gp != null) gp.musicPlayer.playCutsceneMusic();
    }
    private void playClickSFX() {
        if (gamePanel != null) gamePanel.getSFXPlayer().playSFX(new ClickSFX());
    }

    public DefeatCutscene(Runnable onFinish) {
        this.onFinish = onFinish;
        setPreferredSize(new Dimension(W, H));
        setLayout(null);

        bgImages = new Image[TOTAL_PAGES];
        String[] imgPaths = {"/cutscene/ending/defeat/ppp1.jpg", "/cutscene/ending/defeat/ppp2.jpg", "/cutscene/ending/defeat/ppp3.jpg", "/cutscene/ending/defeat/ppp4.jpg"};
        for (int i = 0; i < TOTAL_PAGES; i++) {
            try { bgImages[i] = ImageIO.read(getClass().getResource(imgPaths[i])); }
            catch (Exception e) { bgImages[i] = null; }
        }

        setupButtons();
        loadPage(0);
        fadeIn();
    }

    private static class DialogueLine {
        String speaker, text;
        DialogueLine(String s, String t) { speaker=s; text=t; }
    }

    private void loadPage(int page) {
        currentPage = page;
        currentLines.clear();
        lineIndex = 0; charIndex = 0; displayedPartial = "";
        for (Object[] line : PAGE_DIALOGUE[page])
            currentLines.add(new DialogueLine((String)line[0], (String)line[1]));

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

        String fullText = currentLines.get(lineIndex).text;
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
        nextBtn.setText(currentPage == TOTAL_PAGES-1 ? "DEFEAT →" : "NEXT →");
        nextBtn.setVisible(true);
    }

    private void changePage(int delta) {
        alpha = 0f;
        fadeTimer = new Timer(10, e -> {
            alpha += 0.1f;
            if (alpha >= 1f) {
                ((Timer)e.getSource()).stop();
                loadPage(currentPage + delta);
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

    private void showDefeatScreen() {
        if (typewriterTimer != null) typewriterTimer.stop();
        nextBtn.setVisible(false); backBtn.setVisible(false); skipBtn.setVisible(false);
        if (gamePanel != null) gamePanel.getSFXPlayer().playSFX(new DefeatSFX());
        showDefeat = true; alpha = 0f;
        fadeTimer = new Timer(20, e -> {
            alpha += 0.04f;
            if (alpha >= 1f) { alpha=1f; ((Timer)e.getSource()).stop(); startDefeatAnim(); }
            repaint();
        });
        fadeTimer.start();
    }

    private void startDefeatAnim() {
        defeatTimer = new Timer(16, e -> {
            defeatScale += 0.03f;
            if (defeatScale >= 1f) { defeatScale=1f; ((Timer)e.getSource()).stop(); playAgainBtn.setVisible(true); }
            repaint();
        });
        defeatTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (showDefeat) { paintDefeatState(g2); return; }

        if (bgImages[currentPage] != null) g2.drawImage(bgImages[currentPage], (int)panX, 0, (int)currentWidth, 768, null);
        else { g2.setColor(Color.BLACK); g2.fillRect(0,0,W,H); }

        paintParchmentBox(g2);
        g2.setColor(new Color(0,0,0, (int)(alpha*255))); g2.fillRect(0,0,W,H);
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

    private void paintDefeatState(Graphics2D g2) {
        g2.setColor(new Color(3, 0, 5)); g2.fillRect(0, 0, W, H);
        if (defeatScale > 0.1f) {
            float glowR = W * 0.55f * defeatScale;
            g2.setPaint(new RadialGradientPaint(W/2f, H/2f - 40, glowR, new float[]{0f, 1f},
                    new Color[]{new Color(120, 10, 10, (int)(60*defeatScale)), new Color(0,0,0,0)}));
            g2.fillRect(0, 0, W, H);
        }
        if (defeatScale > 0) {
            int fontSize = (int)(90 * defeatScale); g2.setFont(new Font("Serif", Font.BOLD, fontSize));
            FontMetrics fm = g2.getFontMetrics(); String txt = "DEFEAT"; int tx = (W - fm.stringWidth(txt)) / 2, ty = H/2 - 20;
            g2.setColor(new Color(40, 0, 0, 200)); g2.drawString(txt, tx+5, ty+7);
            g2.setPaint(new GradientPaint(tx, ty-fontSize, new Color(200, 80, 80), tx, ty, new Color(80, 0, 0)));
            g2.drawString(txt, tx, ty);
        }
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
            if (currentPage < TOTAL_PAGES - 1) changePage(1);
            else showDefeatScreen();
        });

        skipBtn = new GoldButton("SKIP");
        skipBtn.setBounds(840, 30, 120, 40);
        skipBtn.addActionListener(e -> { playClickSFX(); showDefeatScreen(); });

        // --- NEW GHOST BUTTON UPGRADE START ---
        playAgainBtn = new JButton("Play Again") {
            private boolean hovered = false;
            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent e) { hovered = true; repaint(); }
                    public void mouseExited(java.awt.event.MouseEvent e) { hovered = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (hovered) {
                    g2.setColor(new Color(255, 100, 100, 20));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                }

                FontMetrics fm = g2.getFontMetrics(getFont());
                String text = getText();
                int tx = (getWidth() - fm.stringWidth(text)) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

                g2.setPaint(hovered
                        ? new GradientPaint(tx, ty - 20, new Color(255, 200, 200), tx, ty + 5, new Color(200, 50, 50))
                        : new GradientPaint(tx, ty - 20, new Color(180, 150, 150), tx, ty + 5, new Color(120, 80, 80)));

                g2.setFont(getFont());
                g2.drawString(text, tx, ty);

                if (hovered) {
                    g2.setColor(new Color(200, 50, 50, 200));
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawLine(tx, ty + 4, tx + fm.stringWidth(text), ty + 4);
                }
                g2.dispose();
            }
        };

        playAgainBtn.setFont(new Font("Serif", Font.BOLD, 26));
        playAgainBtn.setContentAreaFilled(false);
        playAgainBtn.setBorderPainted(false);
        playAgainBtn.setFocusPainted(false);
        playAgainBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        playAgainBtn.setBounds(W / 2 - 110, H / 2 + 100, 220, 55);
        playAgainBtn.setVisible(false);
        playAgainBtn.addActionListener(e -> {
            playClickSFX();
            Main.GameStateManager.reset();
            if (gamePanel != null) gamePanel.musicPlayer.stopCutsceneMusic();
            if (onFinish != null) onFinish.run();
        });
        // --- NEW GHOST BUTTON UPGRADE END ---

        add(backBtn); add(nextBtn); add(skipBtn); add(playAgainBtn);
    }

    private static class GoldButton extends JButton {
        private boolean h = false;
        GoldButton(String t) {
            super(t); setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
            setFont(new Font("Serif", Font.BOLD, 16)); setForeground(new Color(42, 12, 0));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { h = true; repaint(); }
                public void mouseExited(MouseEvent e) { h = false; repaint(); }
            });
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h_ = getHeight(), c = 8;
            Polygon oct = new Polygon(new int[]{c, w-c, w, w, w-c, c, 0, 0}, new int[]{0, 0, c, h_-c, h_, h_, h_-c, c}, 8);
            Color tc = h ? new Color(255, 255, 208) : new Color(252, 240, 122);
            Color bc = h ? new Color(212, 148, 12) : new Color(178, 108, 0);
            g2.setPaint(new LinearGradientPaint(0, 0, 0, h_, new float[]{0f, 1f}, new Color[]{tc, bc}));
            g2.fill(oct); g2.setColor(new Color(82, 38, 0, 210)); g2.draw(oct);
            FontMetrics fm = g2.getFontMetrics(); g2.setColor(getForeground());
            g2.drawString(getText(), (w - fm.stringWidth(getText())) / 2, (h_ + fm.getAscent() - fm.getDescent()) / 2);
            g2.dispose();
        }
    }
}