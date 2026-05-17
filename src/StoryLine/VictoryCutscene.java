package StoryLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import Main.GamePanel;
import Audio.SFX.ClickSFX;
import Audio.SFX.VictorySFX;

public class VictoryCutscene extends JPanel {
    private static final int W = 1024, H = 768;
    private static final Color TEXT_COLOR = new Color(40, 20, 0);
    private static final Color PARCH_BORDER = new Color(80, 38, 2, 200);

    private static final Object[][][] PAGE_DIALOGUE = {
            // Page 1: The Final Exchange
            {
                    {"Zed", "What have you done? I feel them. All of them. How did you—"},
                    {"Khai", "They chose. They chose to give me their strength. Their hope. Their love."},
                    {"Zed", "I asked them once. I begged them to listen. And they chose you instead."},
                    {"Khai", "They chose hope. The hope you abandoned."},
                    {"Zed", "I saw this. In my visions. A child of Neverwinter, carrying everyone's light."},
                    {"Zed", "I thought if I grew strong enough, I could prevent it. I could protect them all myself."},
                    {"Khai", "What comes next? What did you see?"},
                    {"Zed", "You will face it together. That is all I know. That is all I ever needed to know...."},
                    {"Zed", "(Whispering) I only... wanted to keep the dark away... Tell them I'm sorry, Khai."},
                    {"Zed", "Tell them I was only trying to save the light. Tell them..."},
                    {"Khai", "I'll tell them. I promise."}
            },
            // Page 2: The Forest Heals
            {
                    {"narrator", "As Zed's breath faded, the forest breathed its first clean air in decades. Faint green shoots pushed through cracks in stone."},
                    {"narrator", "Life surged back into the soil, chasing away the gray rot of the withering."}
            },
            // Page 3: The Memory of the Warning
            {
                    {"narrator", "The essence returned to the people, but it left Khai with a final, heavy gift: the memory of Zed's warning."},
                    {"Khai", "It wasn't just my strength that won today. It was theirs."}
            },
            // Page 4: The Walk Home
            {
                    {"Khai", "The forest is healing... but we must be ready. Together, we can face whatever comes next."},
                    {"narrator", "Khai closed his eyes and let the breeze wash over him, and began the long walk home."}
            }
    };

    private Image[] bgImages;
    private Image victoryBg;
    private int currentPage = 0, lineIndex = 0, charIndex = 0;
    private java.util.List<DialogueLine> currentLines = new java.util.ArrayList<>();
    private String displayedPartial = "";
    private Timer typewriterTimer, fadeTimer, panTimer, victoryTimer;
    private float alpha = 1.0f, panX = 0, victoryScale = 0f;
    private boolean showVictory = false;
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

    public VictoryCutscene(Runnable onFinish) {
        this.onFinish = onFinish;
        setPreferredSize(new Dimension(W, H));
        setLayout(null);

        bgImages = new Image[PAGE_DIALOGUE.length];
        String[] paths = {"/cutscene/ending/victory/pp1.jpg", "/cutscene/ending/victory/pp2.jpg", "/cutscene/ending/victory/pp3.jpg", "/cutscene/ending/victory/pp4.jpg"};
        for (int i = 0; i < bgImages.length; i++) { try { bgImages[i] = ImageIO.read(getClass().getResource(paths[i])); } catch (Exception e) {} }

        try { victoryBg = ImageIO.read(getClass().getResource("/backgrounds/victory_bg.png")); }
        catch (Exception e) { victoryBg = null; }

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
            panX -= 1.2f;
            if (panX + 1150 <= 1024) panX = 1024 - 1150;
            repaint();
        });
        panTimer.start();
    }

    private void startTypewriter() {
        if (typewriterTimer != null) typewriterTimer.stop();

        displayedPartial = "";
        charIndex = 0;
        repaint();

        if (lineIndex >= currentLines.size()) {
            showNavigation();
            return;
        }

        String fullText = currentLines.get(lineIndex).t;
        typewriterTimer = new Timer(7, e -> {
            if (charIndex < fullText.length()) {
                displayedPartial += fullText.charAt(charIndex++);
                repaint();
            } else {
                typewriterTimer.stop();
                lineIndex++;

                Timer pause = new Timer(900, ev -> {
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
        nextBtn.setText(currentPage == PAGE_DIALOGUE.length - 1 ? "VICTORY →" : "NEXT →");
        nextBtn.setVisible(true);
    }

    private void changePage(int d) {
        alpha = 0f;
        fadeTimer = new Timer(10, e -> {
            alpha += 0.1f;
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

    private void showVictoryScreen() {
        if (typewriterTimer != null) typewriterTimer.stop();
        nextBtn.setVisible(false); backBtn.setVisible(false); skipBtn.setVisible(false);
        if (gamePanel != null) gamePanel.getSFXPlayer().playSFX(new VictorySFX());
        showVictory = true; alpha = 0f;
        fadeTimer = new Timer(20, e -> {
            alpha += 0.04f;
            if (alpha >= 1f) { ((Timer)e.getSource()).stop(); startVictoryAnim(); }
            repaint();
        });
        fadeTimer.start();
    }

    private void startVictoryAnim() {
        victoryTimer = new Timer(16, e -> {
            victoryScale += 0.035f;
            if (victoryScale >= 1f) { victoryScale = 1f; ((Timer)e.getSource()).stop(); playAgainBtn.setVisible(true); }
            repaint();
        });
        victoryTimer.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (showVictory) { paintVictoryState(g2); return; }

        if (bgImages[currentPage] != null) g2.drawImage(bgImages[currentPage], (int)panX, 0, 1150, 768, null);

        g2.setColor(new Color(0, 0, 0, 110));
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
            DialogueLine active = currentLines.get(lineIndex);
            drawWrappedLine(g2, nF, tF, active.s, displayedPartial,
                    tx, ty, pw - 80, false, active.s);

        } else if (lineIndex > 0) {
            DialogueLine last = currentLines.get(lineIndex - 1);
            drawWrappedLine(g2, nF, tF, last.s, last.t,
                    tx, ty, pw - 80, true, last.s);
        }
    }

    private int drawWrappedLine(Graphics2D g2, Font nF, Font tF,
                                String speaker, String text,
                                int x, int y, int width, boolean done,
                                String colorSpeaker) {

        boolean isN = colorSpeaker.equalsIgnoreCase("narrator");
        int curX = x;

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
                g2.setColor(speakerColor);
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

    private void paintVictoryState(Graphics2D g2) {
        if (victoryBg != null) {
            g2.drawImage(victoryBg, 0, 0, W, H, null);
        } else {
            g2.setColor(new Color(5, 12, 5));
            g2.fillRect(0, 0, W, H);
        }

        if (victoryScale > 0.1f) {
            float r = W * 0.6f * victoryScale;
            g2.setPaint(new RadialGradientPaint(W/2f, H/2f - 60, r,
                    new float[]{0f, 1f},
                    new Color[]{new Color(80, 200, 80, (int)(80*victoryScale)), new Color(0,0,0,0)}));
            g2.fillRect(0, 0, W, H);
        }
        if (victoryScale > 0) {
            int s = (int)(90 * victoryScale);
            g2.setFont(new Font("Serif", Font.BOLD, s));
            FontMetrics fm = g2.getFontMetrics();
            String t = "VICTORY";
            int tx = (W - fm.stringWidth(t)) / 2, ty = H/2 - 20;
            g2.setColor(new Color(0, 80, 0, 160));
            g2.drawString(t, tx + 4, ty + 6);
            g2.setPaint(new GradientPaint(tx, ty - s, new Color(255, 255, 210), tx, ty, new Color(220, 180, 20)));
            g2.drawString(t, tx, ty);
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
            if (currentPage < PAGE_DIALOGUE.length - 1) changePage(1);
            else showVictoryScreen();
        });

        skipBtn = new GoldButton("SKIP");
        skipBtn.setBounds(840, 30, 120, 40);
        skipBtn.addActionListener(e -> { playClickSFX(); showVictoryScreen(); });

        // --- ENHANCED "NEXT" BUTTON WITH VICTORY-STYLE COLORS ---
        playAgainBtn = new JButton("Next") {
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

                // Optional subtle hover background
                if (hovered) {
                    g2.setColor(new Color(255, 255, 255, 20));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                }

                FontMetrics fm = g2.getFontMetrics(getFont());
                String text = getText();
                int tx = (getWidth() - fm.stringWidth(text)) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

                // Draw dark green shadow (same as "VICTORY" text)
                g2.setColor(new Color(0, 80, 0, 160));
                g2.drawString(text, tx + 4, ty + 6);

                // Draw bright gold gradient (matching victory text)
                g2.setPaint(new GradientPaint(tx, ty - 20, new Color(255, 255, 210),
                        tx, ty + 5, new Color(220, 180, 20)));
                g2.setFont(getFont());
                g2.drawString(text, tx, ty);

                // Optional: subtle underline on hover
                if (hovered) {
                    g2.setColor(new Color(255, 200, 50, 200));
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawLine(tx, ty + 4, tx + fm.stringWidth(text), ty + 4);
                }
                g2.dispose();
            }
        };

        // Bigger, bolder font for better visibility
        playAgainBtn.setFont(new Font("Serif", Font.BOLD, 30));
        playAgainBtn.setContentAreaFilled(false);
        playAgainBtn.setBorderPainted(false);
        playAgainBtn.setFocusPainted(false);
        playAgainBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        playAgainBtn.setBounds(W / 2 - 120, H / 2 + 90, 240, 60);
        playAgainBtn.setVisible(false);
        playAgainBtn.addActionListener(e -> {
            playClickSFX();
            Main.GameStateManager.reset();
            if (gamePanel != null) gamePanel.musicPlayer.stopCutsceneMusic();
            if (onFinish != null) onFinish.run();
        });
        // -----------------------------------------------

        add(backBtn); add(nextBtn); add(skipBtn); add(playAgainBtn);
    }

    private static class GoldButton extends JButton {
        private boolean h = false;
        GoldButton(String t) {
            super(t);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFont(new Font("Serif", Font.BOLD, 16));
            setForeground(new Color(42, 12, 0));
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
            Polygon oct = new Polygon(new int[]{c, w-c, w, w, w-c, c, 0, 0},
                    new int[]{0, 0, c, h_-c, h_, h_, h_-c, c}, 8);
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