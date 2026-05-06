package Objects;

import Main.GamePanel;
import Main.GameStateManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class OBJ_NoticeBoard extends SuperObject {

    private Runnable onCodeUnlocked;
    private boolean  codeAlreadyUsed = false;

    private static final int    SECRET_CODE  = 37182;
    private static final String[] MEMBER_NAMES = {
            "Member 1", "Member 2", "Member 3", "Member 4", "Member 5"
    };

    private BufferedImage[] memberPhotos = new BufferedImage[5];

    public OBJ_NoticeBoard(GamePanel gp) {
        name      = "Notice Board";
        collision = true;
        solidArea = new Rectangle(8, 16, 48, 48);

        try {
            image = ImageIO.read(
                    getClass().getResourceAsStream("/objects/notice_board.png"));
        } catch (Exception e) {
            image = createFallbackBoardImage();
        }

        for (int i = 0; i < 5; i++) {
            try {
                memberPhotos[i] = ImageIO.read(
                        getClass().getResourceAsStream(
                                "/objects/notice_board/member" + (i + 1) + ".png"));
            } catch (Exception e) {
                memberPhotos[i] = null;
            }
        }
    }

    private BufferedImage createFallbackBoardImage() {
        BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(101, 67, 33));
        g.fillRect(28, 32, 8, 32);
        g.setColor(new Color(160, 110, 60));
        g.fillRoundRect(4, 4, 56, 44, 6, 6);
        g.setColor(new Color(80, 45, 10));
        g.setStroke(new BasicStroke(2f));
        g.drawRoundRect(4, 4, 56, 44, 6, 6);
        // Egg icon instead of exclamation
        g.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        g.setColor(new Color(252, 218, 72));
        g.drawString("🥚", 18, 34);
        g.dispose();
        return img;
    }

    public void setOnCodeUnlocked(Runnable r) { this.onCodeUnlocked = r; }

    public boolean isPlayerNearby(GamePanel gp) {
        int dx = Math.abs(gp.player.worldX - worldX);
        int dy = Math.abs(gp.player.worldY - worldY);
        return dx < gp.tileSize * 2 && dy < gp.tileSize * 2;
    }

    public void interact(GamePanel gp) {
        Window parent = SwingUtilities.getWindowAncestor(gp);
        JFrame frame = (parent instanceof JFrame) ? (JFrame) parent : null;

        NoticeBoardDialog dialog = new NoticeBoardDialog(frame, memberPhotos,
                MEMBER_NAMES, codeAlreadyUsed);
        dialog.setVisible(true);

        String result = dialog.getEnteredCode();
        if (result == null) return; // cancelled or no code entered (e.g. already used / zoom only)

        try {
            int entered = Integer.parseInt(result.trim());
            if (codeAlreadyUsed) {
                gp.screenMessage.show("The seal is already broken.", null, 80, false);
            } else if (entered == SECRET_CODE) {
                codeAlreadyUsed = true;
                gp.screenMessage.show(
                        "Code Accepted!",
                        "The Easter Egg Boss stirs in Map 2...",
                        200, false);
                if (onCodeUnlocked != null) onCodeUnlocked.run();
            } else {
                gp.screenMessage.show(
                        "Wrong code.",
                        "Study the photos more carefully!",
                        80, false);
            }
        } catch (NumberFormatException e) {
            gp.screenMessage.show("Numbers only, please.", null, 80, false);
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  Custom Parchment-Themed Dialog
    // ═══════════════════════════════════════════════════════════════
    private static class NoticeBoardDialog extends JDialog {

        private String enteredCode = null;
        private JTextField codeField;

        private static final Color PARCH_CENTRE = new Color(238, 212, 152);
        private static final Color PARCH_WARM   = new Color(220, 186, 118);
        private static final Color PARCH_TAN    = new Color(192, 152,  78);
        private static final Color PARCH_DARK   = new Color(148, 108,  44);
        private static final Color PARCH_BORDER = new Color( 80,  38,   2, 230);
        private static final Color GOLD         = new Color(252, 218,  72);
        private static final Color GOLD_DARK    = new Color(178, 108,   0);
        private static final Color TEXT_BROWN   = new Color( 50,  22,   2);
        private static final Color TEXT_MID     = new Color( 80,  40,   8);

        NoticeBoardDialog(JFrame parent,
                          BufferedImage[] photos,
                          String[] names,
                          boolean alreadyUsed) {
            super(parent, "Notice Board", true);
            setUndecorated(true);
            setBackground(new Color(0, 0, 0, 0));

            ParchmentPanel content = new ParchmentPanel(
                    photos, names, alreadyUsed, this);
            setContentPane(content);
            pack();
            setLocationRelativeTo(parent);
        }

        public String getEnteredCode() { return enteredCode; }
        public void setEnteredCode(String c) { enteredCode = c; }

        // ── Main parchment panel ────────────────────────────────────
        private class ParchmentPanel extends JPanel {

            private final BufferedImage[] photos;
            private final String[]        names;
            private final boolean         alreadyUsed;
            private final NoticeBoardDialog dlg;

            // Feedback message state
            private String feedbackMsg   = null;
            private Color  feedbackColor = TEXT_BROWN;
            private int    feedbackTimer = 0;

            private static final int PHOTO_W = 70;
            private static final int PHOTO_H = 70;
            private static final int COLS    = 5;
            private static final int PAD     = 24;

            // Photo click zones
            private Rectangle[] photoZones = new Rectangle[5];

            ParchmentPanel(BufferedImage[] photos, String[] names,
                           boolean alreadyUsed, NoticeBoardDialog dlg) {
                this.photos      = photos;
                this.names       = names;
                this.alreadyUsed = alreadyUsed;
                this.dlg         = dlg;

                setLayout(null);
                setOpaque(false);

                int dialogW = PAD * 2 + COLS * PHOTO_W + (COLS - 1) * 10;
                int dialogH = 540;
                setPreferredSize(new Dimension(dialogW, dialogH));

                buildUI(dialogW, dialogH);

                // Photo click listener for zoom
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        for (int i = 0; i < photoZones.length; i++) {
                            if (photoZones[i] != null && photoZones[i].contains(e.getPoint())) {
                                showPhotoZoom(i);
                                return;
                            }
                        }
                    }
                });
                addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        boolean onPhoto = false;
                        for (Rectangle z : photoZones) {
                            if (z != null && z.contains(e.getPoint())) { onPhoto = true; break; }
                        }
                        setCursor(onPhoto
                                ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                                : Cursor.getDefaultCursor());
                    }
                });
            }

            private void showPhotoZoom(int idx) {
                // Create zoom dialog
                JDialog zoomDlg = new JDialog(dlg, names[idx], true);
                zoomDlg.setUndecorated(true);
                zoomDlg.setBackground(new Color(0, 0, 0, 0));

                int zW = 320, zH = 360;
                JPanel zPanel = new JPanel(null) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

                        // Shadow
                        for (int i = 8; i >= 1; i--) {
                            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f * (9 - i)));
                            g2.setColor(Color.BLACK);
                            g2.fill(new RoundRectangle2D.Float(-i, i * 2, zW + i * 2, zH, 16, 16));
                        }
                        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

                        // Parchment background
                        g2.setPaint(new LinearGradientPaint(0, 0, 0, zH,
                                new float[]{0f, 0.5f, 1f},
                                new Color[]{PARCH_TAN, PARCH_CENTRE, PARCH_TAN}));
                        g2.fillRoundRect(0, 0, zW, zH, 16, 16);
                        g2.setStroke(new BasicStroke(2.5f));
                        g2.setColor(PARCH_BORDER);
                        g2.drawRoundRect(0, 0, zW - 1, zH - 1, 16, 16);

                        // Photo
                        int pw = 250, ph = 250;
                        int px = (zW - pw) / 2;
                        int py = 40;
                        if (photos[idx] != null) {
                            // Photo frame
                            g2.setColor(new Color(80, 40, 10, 180));
                            g2.fillRoundRect(px - 4, py - 4, pw + 8, ph + 8, 6, 6);
                            g2.drawImage(photos[idx], px, py, pw, ph, null);
                            g2.setStroke(new BasicStroke(2f));
                            g2.setColor(PARCH_BORDER);
                            g2.drawRect(px, py, pw, ph);
                        } else {
                            g2.setColor(new Color(170, 130, 70));
                            g2.fillRoundRect(px, py, pw, ph, 6, 6);
                            g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
                            FontMetrics fm = g2.getFontMetrics();
                            g2.setColor(new Color(80, 40, 10, 180));
                            g2.drawString("📷", px + (pw - fm.stringWidth("📷")) / 2, py + ph / 2 + 20);
                        }

                        // Name label
                        g2.setFont(new Font("Serif", Font.BOLD, 15));
                        FontMetrics fm = g2.getFontMetrics();
                        g2.setColor(TEXT_BROWN);
                        g2.drawString(names[idx], (zW - fm.stringWidth(names[idx])) / 2, py + ph + 28);

                        // Hint
                        g2.setFont(new Font("Serif", Font.ITALIC, 11));
                        g2.setColor(TEXT_MID);
                        String hint = "Click anywhere to close";
                        FontMetrics hm = g2.getFontMetrics();
                        g2.drawString(hint, (zW - hm.stringWidth(hint)) / 2, zH - 12);
                    }
                };
                zPanel.setPreferredSize(new Dimension(zW, zH));
                zPanel.setOpaque(false);
                zPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) { zoomDlg.dispose(); }
                });
                zoomDlg.setContentPane(zPanel);
                zoomDlg.pack();
                zoomDlg.setLocationRelativeTo(dlg);
                zoomDlg.setVisible(true);
            }

            private void buildUI(int dw, int dh) {
                if (!alreadyUsed) {
                    // Code input field
                    codeField = new JTextField() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            Graphics2D g2 = (Graphics2D) g;
                            g2.setColor(new Color(255, 245, 220));
                            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                            g2.setColor(PARCH_BORDER);
                            g2.setStroke(new BasicStroke(2f));
                            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                            super.paintComponent(g);
                        }
                    };
                    codeField.setOpaque(false);
                    codeField.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
                    codeField.setFont(new Font("Serif", Font.BOLD, 18));
                    codeField.setForeground(TEXT_BROWN);
                    codeField.setHorizontalAlignment(JTextField.CENTER);
                    int fieldW = 180, fieldH = 38;
                    codeField.setBounds((dw - fieldW) / 2, dh - 120, fieldW, fieldH);
                    add(codeField);

                    // Confirm button
                    ParchButton confirmBtn = new ParchButton("Confirm");
                    confirmBtn.setBounds((dw - 240) / 2, dh - 68, 110, 40);
                    confirmBtn.addActionListener(e -> {
                        String txt = codeField.getText().trim();
                        if (txt.isEmpty()) return;
                        try {
                            int entered = Integer.parseInt(txt);
                            if (entered == SECRET_CODE) {
                                dlg.setEnteredCode(txt);
                                dlg.dispose();
                            } else {
                                // Show inline feedback
                                setFeedback("✗  Incorrect code. Study the photos again!", new Color(160, 30, 10));
                                codeField.setText("");
                                codeField.requestFocus();
                            }
                        } catch (NumberFormatException ex) {
                            setFeedback("✗  Numbers only, please.", new Color(160, 30, 10));
                            codeField.setText("");
                        }
                    });
                    add(confirmBtn);

                    // Cancel button
                    ParchButton cancelBtn = new ParchButton("Cancel");
                    cancelBtn.setBounds((dw - 240) / 2 + 130, dh - 68, 110, 40);
                    cancelBtn.addActionListener(e -> dlg.dispose());
                    add(cancelBtn);

                    // Enter key submits
                    codeField.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                confirmBtn.doClick();
                            }
                        }
                    });

                    // Feedback repaint timer
                    new Timer(50, e -> {
                        if (feedbackTimer > 0) {
                            feedbackTimer--;
                            repaint();
                        }
                    }).start();

                } else {
                    // Already used — just a close button
                    ParchButton closeBtn = new ParchButton("Close");
                    closeBtn.setBounds((dw - 120) / 2, dh - 68, 120, 40);
                    closeBtn.addActionListener(e -> dlg.dispose());
                    add(closeBtn);
                }

                // Close X
                JButton closeX = new JButton("✕");
                closeX.setBounds(dw - 36, 8, 28, 28);
                closeX.setContentAreaFilled(false);
                closeX.setBorderPainted(false);
                closeX.setFocusPainted(false);
                closeX.setFont(new Font("Serif", Font.BOLD, 14));
                closeX.setForeground(new Color(120, 60, 10));
                closeX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                closeX.addActionListener(e -> dlg.dispose());
                add(closeX);
            }

            private void setFeedback(String msg, Color color) {
                feedbackMsg   = msg;
                feedbackColor = color;
                feedbackTimer = 120; // ~6 seconds at 50ms
                repaint();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int dw = getWidth(), dh = getHeight();

                // Drop shadow
                for (int i = 10; i >= 1; i--) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f * (11 - i)));
                    g2.setColor(new Color(20, 8, 0));
                    g2.fill(new RoundRectangle2D.Float(-i, i * 2, dw + i * 2, dh, 20, 20));
                }
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

                // Parchment body
                g2.setPaint(new LinearGradientPaint(0, 0, 0, dh,
                        new float[]{0f, 0.08f, 0.30f, 0.50f, 0.70f, 0.92f, 1f},
                        new Color[]{PARCH_DARK, PARCH_TAN, PARCH_WARM,
                                PARCH_CENTRE, PARCH_WARM, PARCH_TAN, PARCH_DARK}));
                g2.fillRoundRect(0, 0, dw, dh, 20, 20);

                // Grain
                g2.setStroke(new BasicStroke(0.5f));
                for (int ly = 14; ly < dh - 12; ly += 7) {
                    int a = 12 + (int)(5 * Math.sin(ly * 0.3));
                    g2.setColor(new Color(100, 60, 12, a));
                    g2.drawLine(14, ly, dw - 14, ly);
                }

                // Age spots
                java.util.Random sr = new java.util.Random(99);
                for (int i = 0; i < 30; i++) {
                    int ax = 20 + sr.nextInt(dw - 40);
                    int ay = 20 + sr.nextInt(dh - 40);
                    int ar = 1 + sr.nextInt(4);
                    g2.setColor(new Color(82, 44, 8, 8 + sr.nextInt(22)));
                    g2.fillOval(ax, ay, ar, ar);
                }

                // Rolled edges
                g2.setPaint(new LinearGradientPaint(0, 0, 0, 28,
                        new float[]{0f, 0.4f, 1f},
                        new Color[]{new Color(110, 72, 18, 170),
                                new Color(148, 108, 44, 80),
                                new Color(148, 108, 44, 0)}));
                g2.fill(new RoundRectangle2D.Float(0, 0, dw, 28, 20, 20));
                g2.setPaint(new LinearGradientPaint(0, dh - 28, 0, dh,
                        new float[]{0f, 0.6f, 1f},
                        new Color[]{new Color(148, 108, 44, 0),
                                new Color(148, 108, 44, 80),
                                new Color(110, 72, 18, 170)}));
                g2.fill(new RoundRectangle2D.Float(0, dh - 28, dw, 28, 20, 20));

                // Inner shadow rings
                g2.setStroke(new BasicStroke(0.8f));
                for (int i = 1; i <= 6; i++) {
                    g2.setColor(new Color(55, 22, 2, 42 - i * 6));
                    g2.draw(new RoundRectangle2D.Float(i, i, dw - i * 2, dh - i * 2, 18 - i, 18 - i));
                }

                // Decorative inner border
                g2.setStroke(new BasicStroke(1f));
                g2.setColor(new Color(115, 68, 14, 100));
                g2.drawRoundRect(10, 10, dw - 20, dh - 20, 12, 12);
                g2.setColor(new Color(115, 68, 14, 50));
                g2.drawRoundRect(13, 13, dw - 26, dh - 26, 10, 10);

                // Outer border
                g2.setStroke(new BasicStroke(2.5f));
                g2.setColor(PARCH_BORDER);
                g2.drawRoundRect(0, 0, dw - 1, dh - 1, 20, 20);

                // Title
                Font titleFont;
                try {
                    java.io.File f = new java.io.File("RingbearerMedium.ttf");
                    if (!f.exists()) f = new java.io.File("RINGM___.TTF");
                    titleFont = f.exists()
                            ? Font.createFont(Font.TRUETYPE_FONT, f).deriveFont(Font.PLAIN, 26f)
                            : new Font("Serif", Font.BOLD, 22);
                } catch (Exception ex) {
                    titleFont = new Font("Serif", Font.BOLD, 22);
                }
                g2.setFont(titleFont);
                FontMetrics ftm = g2.getFontMetrics();
                String title = "🥚  Notice Board  🥚";
                int tx = (dw - ftm.stringWidth(title)) / 2;
                g2.setColor(new Color(30, 10, 0, 120));
                g2.drawString(title, tx + 2, 44);
                g2.setPaint(new LinearGradientPaint(tx, 20, tx, 46,
                        new float[]{0f, 0.5f, 1f},
                        new Color[]{new Color(255, 245, 180), GOLD, GOLD_DARK}));
                g2.drawString(title, tx, 42);

                // Divider
                g2.setStroke(new BasicStroke(1.5f));
                g2.setColor(new Color(180, 120, 20, 160));
                g2.drawLine(PAD, 52, dw - PAD, 52);

                // Wanted text
                g2.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
                g2.setColor(new Color(120, 50, 0));
                String wanted = "Wanted: The Easter Egg Boss!";
                FontMetrics wm = g2.getFontMetrics();
                g2.drawString(wanted, (dw - wm.stringWidth(wanted)) / 2, 72);

                // Subtitle
                g2.setFont(new Font("Serif", Font.ITALIC, 12));
                g2.setColor(TEXT_MID);
                String sub = "Each brave adventurer holds one digit of the secret code.";
                FontMetrics sm = g2.getFontMetrics();
                g2.drawString(sub, (dw - sm.stringWidth(sub)) / 2, 90);

                // Click hint
                g2.setFont(new Font("Serif", Font.ITALIC, 10));
                g2.setColor(new Color(100, 60, 15, 160));
                String clickHint = "( Click a photo to enlarge )";
                FontMetrics chm = g2.getFontMetrics();
                g2.drawString(clickHint, (dw - chm.stringWidth(clickHint)) / 2, 104);

                // Member photos
                int totalRowW = COLS * PHOTO_W + (COLS - 1) * 10;
                int startX    = (dw - totalRowW) / 2;
                int startY    = 116;

                for (int i = 0; i < photos.length; i++) {
                    int px = startX + i * (PHOTO_W + 10);
                    int py = startY;

                    // Store click zone
                    photoZones[i] = new Rectangle(px, py, PHOTO_W, PHOTO_H + 30);

                    // Photo frame card
                    g2.setPaint(new LinearGradientPaint(px, py, px, py + PHOTO_H + 30,
                            new float[]{0f, 0.5f, 1f},
                            new Color[]{PARCH_TAN, PARCH_CENTRE, PARCH_TAN}));
                    g2.fillRoundRect(px - 3, py - 3, PHOTO_W + 6, PHOTO_H + 36, 8, 8);
                    g2.setStroke(new BasicStroke(1.4f));
                    g2.setColor(PARCH_BORDER);
                    g2.drawRoundRect(px - 3, py - 3, PHOTO_W + 6, PHOTO_H + 36, 8, 8);

                    // Hover highlight
                    Point mp = getMousePosition();
                    if (mp != null && photoZones[i].contains(mp)) {
                        g2.setColor(new Color(252, 218, 72, 60));
                        g2.fillRoundRect(px - 3, py - 3, PHOTO_W + 6, PHOTO_H + 36, 8, 8);
                        g2.setStroke(new BasicStroke(2f));
                        g2.setColor(new Color(252, 218, 72, 180));
                        g2.drawRoundRect(px - 3, py - 3, PHOTO_W + 6, PHOTO_H + 36, 8, 8);
                    }

                    // Photo or placeholder
                    if (photos[i] != null) {
                        g2.drawImage(photos[i], px, py, PHOTO_W, PHOTO_H, null);
                    } else {
                        g2.setColor(new Color(170, 130, 70));
                        g2.fillRect(px, py, PHOTO_W, PHOTO_H);
                        g2.setFont(new Font("Serif", Font.BOLD, 28));
                        FontMetrics nm = g2.getFontMetrics();
                        String num = String.valueOf(i + 1);
                        g2.setColor(new Color(80, 40, 10, 180));
                        g2.drawString(num, px + (PHOTO_W - nm.stringWidth(num)) / 2 + 1, py + PHOTO_H / 2 + 11);
                        g2.setPaint(new LinearGradientPaint(px, py, px, py + PHOTO_H,
                                new float[]{0f, 0.5f, 1f},
                                new Color[]{new Color(255, 245, 180), GOLD, GOLD_DARK}));
                        g2.drawString(num, px + (PHOTO_W - nm.stringWidth(num)) / 2, py + PHOTO_H / 2 + 10);
                        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
                        g2.setColor(new Color(120, 80, 30, 160));
                        g2.drawString("📷", px + 4, py + PHOTO_H - 5);
                    }

                    // Photo border
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.setColor(new Color(80, 40, 10, 140));
                    g2.drawRect(px, py, PHOTO_W, PHOTO_H);

                    // Member name
                    g2.setFont(new Font("Serif", Font.BOLD, 11));
                    g2.setColor(TEXT_BROWN);
                    FontMetrics mnm = g2.getFontMetrics();
                    String mname = names[i];
                    g2.drawString(mname, px + (PHOTO_W - mnm.stringWidth(mname)) / 2, py + PHOTO_H + 16);

                    // Index
                    g2.setFont(new Font("Serif", Font.ITALIC, 10));
                    g2.setColor(new Color(140, 80, 20));
                    String idx = "[" + (i + 1) + "]";
                    FontMetrics idxm = g2.getFontMetrics();
                    g2.drawString(idx, px + (PHOTO_W - idxm.stringWidth(idx)) / 2, py + PHOTO_H + 27);
                }

                // Divider above input
                g2.setStroke(new BasicStroke(1.2f));
                g2.setColor(new Color(180, 120, 20, 120));
                g2.drawLine(PAD, startY + PHOTO_H + 50, dw - PAD, startY + PHOTO_H + 50);

                // Input label
                g2.setFont(new Font("Serif", Font.BOLD, 13));
                g2.setColor(TEXT_BROWN);
                String lbl;
                Color lblColor;
                if (alreadyUsed) {
                    lbl = "🥚  The seal is already broken. The Easter Egg Boss awaits!";
                    lblColor = new Color(80, 140, 40); // green for success
                } else {
                    lbl = "Enter the " + names.length + "-digit code:";
                    lblColor = TEXT_BROWN;
                }
                g2.setColor(lblColor);
                FontMetrics lm = g2.getFontMetrics();
                g2.drawString(lbl, (dw - lm.stringWidth(lbl)) / 2, startY + PHOTO_H + 70);

                // Inline feedback message (wrong code etc.)
                if (feedbackMsg != null && feedbackTimer > 0) {
                    float alpha = Math.min(1f, feedbackTimer / 20f);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                    g2.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 12));
                    g2.setColor(feedbackColor);
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(feedbackMsg, (dw - fm.stringWidth(feedbackMsg)) / 2,
                            startY + PHOTO_H + 88);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }

                // Wax seals
                paintWaxSeal(g2, 14, 14);
                paintWaxSeal(g2, dw - 32, 14);
                paintWaxSeal(g2, 14, dh - 32);
                paintWaxSeal(g2, dw - 32, dh - 32);
            }

            private void paintWaxSeal(Graphics2D g2, int rx, int ry) {
                int cx = rx + 9, cy = ry + 9;
                g2.setStroke(new BasicStroke(1.3f));
                g2.setColor(new Color(80, 40, 8, 115));
                g2.drawOval(rx, ry, 18, 18);
                g2.setColor(new Color(80, 40, 8, 65));
                g2.drawOval(rx + 3, ry + 3, 12, 12);
                g2.setStroke(new BasicStroke(1.1f));
                g2.setColor(new Color(80, 40, 8, 105));
                g2.drawLine(cx, ry + 2, cx, ry + 16);
                g2.drawLine(rx + 2, cy, rx + 16, cy);
                g2.setColor(new Color(80, 40, 8, 60));
                g2.drawLine(rx + 4, ry + 4, rx + 14, ry + 14);
                g2.drawLine(rx + 14, ry + 4, rx + 4, ry + 14);
                g2.setColor(new Color(90, 50, 10, 110));
                g2.fillOval(cx - 2, cy - 2, 5, 5);
            }
        }

        // ── Gold octagon button ───────────────────────────────────────
        private static class ParchButton extends JButton {
            private boolean hovered = false, pressed = false;

            ParchButton(String text) {
                super(text);
                setContentAreaFilled(false);
                setBorderPainted(false);
                setFocusPainted(false);
                setFont(new Font("Serif", Font.BOLD, 14));
                setForeground(new Color(42, 12, 0));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                    public void mousePressed(MouseEvent e) { pressed = true;  repaint(); }
                    public void mouseReleased(MouseEvent e){ pressed = false; repaint(); }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight(), c = 7;
                int[] xs = {c, w-c, w, w, w-c, c, 0, 0};
                int[] ys = {0, 0, c, h-c, h, h, h-c, c};
                Polygon oct = new Polygon(xs, ys, 8);

                Color tc = pressed ? new Color(220,200,80)
                        : hovered ? new Color(255,255,208)
                        : new Color(252,240,122);
                Color bc = pressed ? new Color(140, 80,  0)
                        : hovered ? new Color(212,148, 12)
                        : new Color(178,108,  0);

                g2.setPaint(new LinearGradientPaint(0, 0, 0, h,
                        new float[]{0f, 1f}, new Color[]{tc, bc}));
                g2.fill(oct);
                g2.setStroke(new BasicStroke(1.8f));
                g2.setColor(new Color(82, 38, 0, 210));
                g2.draw(oct);

                FontMetrics fm = g2.getFontMetrics(getFont());
                g2.setFont(getFont());
                g2.setColor(getForeground());
                g2.drawString(getText(),
                        (w - fm.stringWidth(getText())) / 2,
                        (h + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        }
    }
}