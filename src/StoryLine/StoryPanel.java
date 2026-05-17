package StoryLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import Audio.SFX.SFXPlayer;
import Audio.SFX.ClickSFX;
import Audio.Music.CutsceneMusic;

public class StoryPanel extends JPanel {
    // ── Palette ───────────────────────────────────────────────────
    private static final Color PARCH_BORDER = new Color(80, 38, 2, 230);
    private static final Color GOLD_DARK    = new Color(178, 108, 0);

    private String[] pages = {
            "In the ancient forest of Neverwinter, magic once flowed like water. Three heroes—a Fighter, an Archer, and a Half-blood Sorcerer—stood as the city’s shield. Together, they slew the Demon King and brought a peace that many thought would last forever.",
            "But peace breeds fear of its ending. The Sorcerer, Zed, foresaw a threat far greater than any demon. His desire to protect turned into an obsession with dark magic. For his 'altruism,' he was met with betrayal. His friends and his people cast him out, banishing him into the cold North.",
            "Decades passed. The old heroes faded into legend and eventually, the grave. Now, Zed has returned to claim his revenge. He consumes the essence of the forest to fuel his power. The trees wither, the plants wilt, and the very life of Neverwinter is decaying.",
            "As the forest dies, a new light emerges. Khai, the firstborn of the fallen heroes, stands where his parents once stood. The journey to the North is arduous, and the fate of Neverwinter rests upon his shoulders. But which path will he take?"
    };

    private int currentPage = 0;
    private String displayedText = "";
    private int charIndex = 0;
    private Timer typewriterTimer, fadeTimer;
    private float alpha = 1.0f;
    private GoldButton nextBtn, backBtn, skipBtn;
    private Runnable onFinish;
    private final SFXPlayer sfxPlayer = new SFXPlayer();
    private final CutsceneMusic cutsceneMusic = new CutsceneMusic();
    private Image[] images;
    private float panX = 0;
    private float panSpeed = 0.5f; // Adjust this for faster/slower panning
    private Timer panTimer;
    private float currentWidth = 1200f; // Start wide

    public StoryPanel(Runnable onFinish) {
        this.onFinish = onFinish;
        setPreferredSize(new Dimension(1024, 768));
        setLayout(null);

        images = new Image[4];
        for (int i = 0; i < 4; i++) {
            try {
                // This looks inside the 'page' package for the images
                String path = "/page/page" + (i + 1) + ".jpg";
                images[i] = ImageIO.read(getClass().getResource(path));

                if (images[i] == null) {
                    System.err.println("Could not find resource: " + path);
                }
            } catch (Exception e) {
                System.err.println("Error loading image " + (i + 1) + ": " + e.getMessage());
            }
        }
        startPanning();
        setupButtons();
        startTypewriter();
        fadeIn();
        cutsceneMusic.preload();
        cutsceneMusic.play(true);
    }

    private void setupButtons() {
        backBtn = new GoldButton("← BACK");
        backBtn.setBounds(62, 670, 140, 45);
        backBtn.setVisible(false);
        backBtn.addActionListener(e -> { sfxPlayer.playSFX(new ClickSFX()); changePage(-1); });

        nextBtn = new GoldButton("NEXT →");
        nextBtn.setBounds(822, 670, 140, 45);
        nextBtn.setVisible(false);
        nextBtn.addActionListener(e -> {
            sfxPlayer.playSFX(new ClickSFX());
            if (currentPage < pages.length - 1) changePage(1);
            else fadeToFinish();
        });

        skipBtn = new GoldButton("SKIP STORY");
        skipBtn.setBounds(822, 30, 140, 40);
        skipBtn.addActionListener(e -> { sfxPlayer.playSFX(new ClickSFX()); fadeToFinish(); });

        add(backBtn); add(nextBtn); add(skipBtn);
    }

    // ── Logic Methods ─────────────────────────────────────────────
    private void startTypewriter() {
        displayedText = ""; charIndex = 0;
        nextBtn.setVisible(false); backBtn.setVisible(false);
        if (typewriterTimer != null) typewriterTimer.stop();
        typewriterTimer = new Timer(38, e -> {
            if (charIndex < pages[currentPage].length()) {
                displayedText += pages[currentPage].charAt(charIndex++);
                repaint();
            } else { typewriterTimer.stop(); showNavigation(); }
        });
        typewriterTimer.start();
    }

    private void showNavigation() {
        if (currentPage > 0) backBtn.setVisible(true);
        nextBtn.setText(currentPage == pages.length - 1 ? "CHOOSE HERO" : "NEXT →");
        nextBtn.setVisible(true);
    }

    private void changePage(int delta) {
        alpha = 0;
        fadeTimer = new Timer(10, e -> {
            alpha += 0.1f;
            if (alpha >= 1.0f) {
                ((Timer)e.getSource()).stop();
                currentPage += delta;

                // --- ADD THIS LINE HERE ---
                startPanning(); // Start the camera movement for the NEW page

                startTypewriter();
                fadeIn();
            }
            repaint();
        });
        fadeTimer.start();
    }

    private void fadeIn() {
        alpha = 1.0f;
        fadeTimer = new Timer(20, e -> {
            alpha -= 0.05f;
            if (alpha <= 0) { alpha = 0; ((Timer)e.getSource()).stop(); }
            repaint();
        });
        fadeTimer.start();
    }

    private void fadeToFinish() {
        fadeTimer = new Timer(20, e -> {
            alpha += 0.05f;
            if (alpha >= 1.0f) {
                ((Timer)e.getSource()).stop();
                cutsceneMusic.stop();
                onFinish.run();
            }
            repaint();
        });
        fadeTimer.start();
    }
    public void jumpToLastPage() {
        if (typewriterTimer != null) typewriterTimer.stop();
        this.currentPage = pages.length - 1; // Go to the last story bit
        this.displayedText = pages[currentPage]; // Show all text instantly
        this.charIndex = pages[currentPage].length();
        this.alpha = 0f; // Make sure it's not black/faded
        showNavigation(); // Show the "Choose Hero" button
        repaint();
    }
    public void resetToBeginning() {
        if (typewriterTimer != null) typewriterTimer.stop();
        this.currentPage = 0;
        this.displayedText = "";
        this.charIndex = 0;
        this.alpha = 0f;

        nextBtn.setVisible(false);
        backBtn.setVisible(false);

        cutsceneMusic.stop();
        cutsceneMusic.play(true);

        startPanning();    // Trigger pan for the first page reset
        startTypewriter();
        repaint();
    }

    private void startPanning() {
        if (panTimer != null) panTimer.stop();

        panX = 0;
        // We make the image 1150px wide.
        // Since the screen is 1024px, we have 126px of "extra" image to pan.
        float targetWidth = 1150f;
        currentWidth = targetWidth;
        panSpeed = 0.7f;

        panTimer = new Timer(16, e -> {
            // 1. Move the image left
            panX -= panSpeed;

            // 2. Calculate the "Stop Point"
            // If the right edge of our image (panX + currentWidth)
            // reaches the right edge of the screen (1024), we STOP.
            if (panX + currentWidth <= 1024) {
                panX = 1024 - currentWidth; // Lock it exactly to the edge
                ((Timer)e.getSource()).stop();
            }

            repaint();
        });
        panTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Draw the Background Image
        if (images != null && images[currentPage] != null) {
            g2d.drawImage(images[currentPage], (int)panX, 0, (int)currentWidth, 768, null);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 1024, 768);
        }

        // 2. Draw the Parchment Box
        int px = 62, py = 480, pw = 900, ph = 170;

// Update these colors! I added '180' at the end for transparency
        g2d.setPaint(new LinearGradientPaint(px, py, px, py + ph,
                new float[]{0f, 0.5f, 1f},
                new Color[]{
                        new Color(220, 186, 118, 150), // Top (Transparent)
                        new Color(238, 212, 152, 150), // Middle (Transparent)
                        new Color(220, 186, 118, 150)  // Bottom (Transparent)
                }));

        g2d.fillRoundRect(px, py, pw, ph, 20, 20);

// Let's make the border slightly more solid so it still pops
        g2d.setStroke(new BasicStroke(4f));
        g2d.setColor(new Color(80, 38, 2, 200)); // Border at 200 alpha
        g2d.drawRoundRect(px, py, pw, ph, 20, 20);

        // 3. Draw the Story Text
        g2d.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 24));
        g2d.setColor(new Color(40, 20, 0));
        drawMultiLineString(g2d, displayedText, px + 40, py + 50, pw - 80);

        // 4. Draw Overlay Fade (The black screen effect)
        g2d.setColor(new Color(0, 0, 0, Math.max(0, Math.min(255, (int) (alpha * 255)))));
        g2d.fillRect(0, 0, 1024, 768);

    }
    private void drawMultiLineString(Graphics2D g, String text, int x, int y, int lineWidth) {
        FontMetrics metrics = g.getFontMetrics();
        int lineHeight = metrics.getHeight();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            if (metrics.stringWidth(line.toString() + word) < lineWidth) {
                line.append(word).append(" ");
            } else {
                g.drawString(line.toString(), x, y);
                y += lineHeight;
                line = new StringBuilder(word).append(" ");
            }
        }
        g.drawString(line.toString(), x, y);
    }

    // ── INNER CLASS: THE GOLD BUTTON ─────────────────────────────
    private static class GoldButton extends JButton {
        private boolean hovered = false, pressed = false;
        private float pulse = 0f, pulseDir = 1f;
        private Timer pulseTimer;

        GoldButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFont(new Font("Serif", Font.BOLD, 16));
            setForeground(new Color(42, 12, 0));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            pulseTimer = new Timer(16, e -> {
                pulse += 0.025f * pulseDir;
                if (pulse > 1f) pulseDir = -1f;
                if (pulse < 0f) pulseDir =  1f;
                repaint();
            });
            pulseTimer.start();

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                @Override public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
                @Override public void mousePressed(MouseEvent e) { pressed = true; repaint(); }
                @Override public void mouseReleased(MouseEvent e){ pressed = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            int c = 8;
            Polygon oct = makeOct(0, 0, w, h, c);

            float ga = 0.25f + pulse * 0.30f;
            if (hovered) ga = 0.60f;
            for (int i = 10; i >= 2; i -= 2) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ga * 0.045f));
                g2.setColor(new Color(205, 148, 12));
                g2.fill(makeOct(-i, -i, w + i * 2, h + i * 2, c));
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            Color tc = hovered ? new Color(255, 255, 208) : new Color(252, 240, 122);
            Color mc = hovered ? new Color(250, 222,  62) : new Color(238, 190,  28);
            Color bc = hovered ? new Color(212, 148,  12) : new Color(178, 108,   0);
            if (pressed) { tc = new Color(220, 200, 80); mc = new Color(190, 140, 10); bc = new Color(140, 80, 0); }

            g2.setPaint(new LinearGradientPaint(0, 0, 0, h,
                    new float[]{0f, .22f, .55f, 1f},
                    new Color[]{tc, mc, bc, new Color(208, 155, 12)}));
            g2.fill(oct);

            g2.setStroke(new BasicStroke(2.0f));
            g2.setColor(new Color(82, 38, 0, 210));
            g2.draw(oct);

            FontMetrics fm = g2.getFontMetrics(getFont());
            int tx = (w - fm.stringWidth(getText())) / 2;
            int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
            g2.setColor(getForeground());
            g2.drawString(getText(), tx, ty);

            g2.dispose();
        }

        private Polygon makeOct(int x, int y, int w, int h, int c) {
            return new Polygon(
                    new int[]{x+c, x+w-c, x+w, x+w, x+w-c, x+c, x, x},
                    new int[]{y, y, y+c, y+h-c, y+h, y+h, y+h-c, y+c}, 8);
        }
    }
}
