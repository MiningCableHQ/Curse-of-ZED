package Main;

import Audio.SFX.ClickSFX;
import Audio.SFX.SFXPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/** Read-only leaderboard panel shown from TitlePanel's Leaderboard button. No music change. */
public class LeaderboardViewPanel extends JPanel {

    private static final int WIDTH  = 1024;
    private static final int HEIGHT = 768;

    private static final Color BG_COLOR       = new Color(10, 5, 20);
    private static final Color PARCH_TOP      = new Color(148, 108, 44);
    private static final Color PARCH_TAN      = new Color(192, 152, 78);
    private static final Color PARCH_WARM     = new Color(220, 186, 118);
    private static final Color PARCH_CENTRE   = new Color(238, 212, 152);
    private static final Color PARCH_BORDER   = new Color(80, 38, 2, 230);
    private static final Color TEXT_DARK      = new Color(60, 30, 5);
    private static final Color TEXT_GOLD      = new Color(252, 218, 72);
    private static final Color BTN_GOLD_NORMAL = new Color(238, 190, 28);
    private static final Color BTN_GOLD_HOVER  = new Color(250, 222, 62);
    private static final Color BTN_GOLD_PRESS  = new Color(178, 108, 0);
    private static final Color BTN_BORDER_CLR  = new Color(82, 38, 0, 215);
    private static final Color BTN_TEXT_DARK   = new Color(42, 12, 0);
    private static final Color ROW_GOLD   = new Color(255, 232, 100, 220);
    private static final Color ROW_SILVER = new Color(205, 210, 220, 220);
    private static final Color ROW_BRONZE = new Color(218, 165, 100, 220);
    private static final Color ROW_EVEN   = new Color(238, 210, 148);
    private static final Color ROW_ODD    = new Color(248, 224, 172);

    private Font fontTitle;
    private static final Font FONT_BTN       = new Font("Serif", Font.BOLD, 16);
    private static final Font FONT_TABLE     = new Font("Monospaced", Font.PLAIN, 13);
    private static final Font FONT_TABLE_HDR = new Font("Serif", Font.BOLD, 13);

    private static final int TABLE_PANEL_X = 30;
    private static final int TABLE_PANEL_Y = 100;
    private static final int TABLE_PANEL_W = 964;
    private static final int TABLE_PANEL_H = 560;

    private static final String LEADERBOARD_FILE = "leaderboard.dat";

    private final JFrame   parentFrame;
    private final Runnable onClose;

    private DefaultTableModel leaderboardTableModel;
    private JTable leaderboardTable;
    private final SFXPlayer sfxPlayer = new SFXPlayer();
    private float shimmer = 1.4f;
    private float floatY = 0f, floatDir = 1f;
    private javax.swing.Timer titleAnimTimer;
    private BufferedImage loadingBg;

    public LeaderboardViewPanel(JFrame parentFrame, Runnable onClose) {
        this.parentFrame = parentFrame;
        this.onClose     = onClose;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        setOpaque(true);
        setFocusable(true);
        loadBackground();
        loadTitleFont();
        initUI();
        startTitleAnimation();
    }

    private void loadBackground() {
        try { loadingBg = ImageIO.read(getClass().getResourceAsStream("/backgrounds/victory_bg.png")); }
        catch (Exception e) { loadingBg = null; }
    }

    private void loadTitleFont() {
        Font base = null;
        for (String n : new String[]{"RINGM___.TTF","RingbearerMedium.ttf","Ringbearer Medium.ttf",
                "ringbearer medium.ttf","Ringbearer.ttf","ringbearer.ttf"}) {
            java.io.File f = new java.io.File(n);
            if (!f.exists()) continue;
            try { base = Font.createFont(Font.TRUETYPE_FONT, f);
                  GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(base); break; }
            catch (Exception ex) { /* try next */ }
        }
        if (base == null) base = new Font("Palatino Linotype", Font.PLAIN, 12);
        fontTitle = base.deriveFont(Font.PLAIN, 48f);
    }

    private void startTitleAnimation() {
        titleAnimTimer = new javax.swing.Timer(16, e -> {
            floatY += 0.038f * floatDir;
            if (floatY >  5f) floatDir = -1f;
            if (floatY < -5f) floatDir =  1f;
            shimmer -= 0.005f;
            if (shimmer < -0.4f) shimmer = 1.4f;
            repaint();
        });
        titleAnimTimer.start();
    }

    private void initUI() {
        JPanel tablePanel = createParchmentPanel(TABLE_PANEL_X, TABLE_PANEL_Y, TABLE_PANEL_W, TABLE_PANEL_H);
        add(tablePanel);
        setupTablePanel(tablePanel);

        int btnW = 200, btnH = 48, btnY = 690;
        GoldButton closeBtn = new GoldButton("Close");
        closeBtn.setBounds((WIDTH - btnW) / 2, btnY, btnW, btnH);
        closeBtn.addActionListener(e -> {
            sfxPlayer.playSFX(new ClickSFX());
            cleanup();
            if (onClose != null) onClose.run();
        });
        add(closeBtn);
    }

    private void setupTablePanel(JPanel panel) {
        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setForeground(TEXT_DARK);
        title.setBounds(20, 10, TABLE_PANEL_W - 40, 30);
        panel.add(title);

        JSeparator sep = new JSeparator();
        sep.setBounds(20, 44, TABLE_PANEL_W - 40, 2);
        sep.setForeground(TEXT_GOLD);
        panel.add(sep);

        String[] cols = {"#", "Name", "Role", "Weapon", "Gold", "Time"};
        leaderboardTableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        leaderboardTable = new JTable(leaderboardTableModel) {
            @Override public boolean getScrollableTracksViewportWidth() { return true; }
        };
        leaderboardTable.setFont(FONT_TABLE);
        leaderboardTable.setForeground(TEXT_DARK);
        leaderboardTable.setRowHeight(28);
        leaderboardTable.setShowGrid(false);
        leaderboardTable.setIntercellSpacing(new Dimension(0, 1));
        leaderboardTable.setSelectionBackground(new Color(200, 170, 90));
        leaderboardTable.setSelectionForeground(TEXT_DARK);
        leaderboardTable.setFocusable(false);
        leaderboardTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] colW = {55, 210, 145, 255, 110, 130};
        for (int i = 0; i < colW.length; i++) {
            TableColumn tc = leaderboardTable.getColumnModel().getColumn(i);
            tc.setPreferredWidth(colW[i]);
            tc.setMinWidth(colW[i]);
            tc.setMaxWidth(colW[i]);
        }

        leaderboardTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object value, boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, value, sel, focus, row, col);
                if (!sel) {
                    switch (row) {
                        case 0: setBackground(ROW_GOLD);   break;
                        case 1: setBackground(ROW_SILVER); break;
                        case 2: setBackground(ROW_BRONZE); break;
                        default: setBackground(row % 2 == 0 ? ROW_EVEN : ROW_ODD);
                    }
                    setForeground(TEXT_DARK);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 4));
                setHorizontalAlignment(col == 4 ? SwingConstants.RIGHT : SwingConstants.LEFT);
                return this;
            }
        });

        JTableHeader header = leaderboardTable.getTableHeader();
        header.setFont(FONT_TABLE_HDR);
        header.setForeground(TEXT_GOLD);
        header.setBackground(new Color(70, 35, 5));
        header.setPreferredSize(new Dimension(0, 28));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object value, boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, value, sel, focus, row, col);
                setBackground(new Color(70, 35, 5));
                setForeground(TEXT_GOLD);
                setFont(FONT_TABLE_HDR);
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(140, 100, 30)),
                        BorderFactory.createEmptyBorder(0, 8, 0, 4)));
                setHorizontalAlignment(col == 4 ? SwingConstants.RIGHT : SwingConstants.LEFT);
                return this;
            }
        });

        JScrollPane sp = new JScrollPane(leaderboardTable);
        sp.setBounds(15, 52, TABLE_PANEL_W - 30, TABLE_PANEL_H - 62);
        sp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 140, 60), 2),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        sp.getViewport().setBackground(ROW_EVEN);
        sp.getVerticalScrollBar().setUnitIncrement(28);
        panel.add(sp);

        loadLeaderboard();
    }

    private static class LeaderboardEntry {
        String name, role, weapon;
        long playTimeMs;
        int gold;
        LeaderboardEntry(String n, String r, long t, int g, String w) {
            name = n; role = r; playTimeMs = t; gold = g; weapon = w;
        }
    }

    private String formatPlayTime(long ms) {
        long ts = ms / 1000, h = ts / 3600, m = (ts % 3600) / 60, s = ts % 60;
        if (h > 0) return String.format("%dh %02dm %02ds", h, m, s);
        if (m > 0) return String.format("%dm %02ds", m, s);
        return String.format("%ds", s);
    }

    private static List<LeaderboardEntry> readLeaderboardFile() {
        List<LeaderboardEntry> entries = new ArrayList<>();
        java.io.File file = new java.io.File(LEADERBOARD_FILE);
        if (!file.exists()) return entries;
        try (BufferedReader r = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = r.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    try {
                        entries.add(new LeaderboardEntry(
                                parts[0], parts[1],
                                Long.parseLong(parts[2]),
                                Integer.parseInt(parts[3]),
                                parts[4]));
                    } catch (NumberFormatException ignored) { }
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read leaderboard: " + e.getMessage());
        }
        entries.sort((a, b) -> Long.compare(a.playTimeMs, b.playTimeMs));
        return entries;
    }

    private void loadLeaderboard() {
        List<LeaderboardEntry> entries = readLeaderboardFile();
        leaderboardTableModel.setRowCount(0);
        if (entries.isEmpty()) {
            leaderboardTableModel.addRow(new Object[]{"--", "No entries yet", "", "", "", ""});
            return;
        }
        int rank = 1;
        for (LeaderboardEntry e : entries) {
            String rankStr = rank == 1 ? "1st" : rank == 2 ? "2nd" : rank == 3 ? "3rd" : rank + "th";
            String dn = e.name.length()   > 12 ? e.name.substring(0, 12)   : e.name;
            String dw = e.weapon.length() > 14 ? e.weapon.substring(0, 14) : e.weapon;
            leaderboardTableModel.addRow(new Object[]{
                rankStr, dn, e.role, dw, "$" + e.gold, formatPlayTime(e.playTimeMs)
            });
            rank++;
        }
    }

    private void cleanup() {
        if (titleAnimTimer != null) { titleAnimTimer.stop(); titleAnimTimer = null; }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        if (loadingBg != null) g2.drawImage(loadingBg, 0, 0, WIDTH, HEIGHT, this);
        else { g2.setColor(BG_COLOR); g2.fillRect(0, 0, WIDTH, HEIGHT); }
        g2.setColor(new Color(0, 0, 0, 80));
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        paintTitle(g2);
        g2.dispose();
    }

    private void paintTitle(Graphics2D g2) {
        String text = "Leaderboard";
        g2.setFont(fontTitle);
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv  = fontTitle.createGlyphVector(frc, text);
        Rectangle2D vis = gv.getVisualBounds();
        int tw = (int) vis.getWidth();
        int tx = (WIDTH - tw) / 2 - (int) vis.getX();
        int ty = (int)(60 + floatY);

        g2.setColor(new Color(0, 0, 0, 185));
        g2.drawString(text, tx + 4, ty + 6);
        g2.setColor(new Color(0, 0, 0, 75));
        g2.drawString(text, tx + 8, ty + 11);

        g2.setPaint(new LinearGradientPaint(tx, ty-(int)vis.getHeight(), tx, ty+8,
            new float[]{0f, 0.35f, 0.65f, 1f},
            new Color[]{new Color(255,252,210), new Color(252,218,72),
                        new Color(218,138,18),  new Color(245,198,48)}));
        g2.drawString(text, tx, ty);

        float bandW = 110f;
        float bandX = tx + shimmer * (tw + bandW) - bandW;
        Shape savedClip = g2.getClip();
        g2.clip(gv.getOutline(tx, ty));
        g2.setPaint(new LinearGradientPaint(bandX, 0, bandX + bandW, 0,
            new float[]{0f, 0.35f, 0.5f, 0.65f, 1f},
            new Color[]{new Color(255,248,200,0), new Color(255,248,200,85),
                        new Color(255,255,255,215), new Color(255,248,200,85),
                        new Color(255,248,200,0)}));
        g2.fill(new Rectangle2D.Float(bandX, ty-(int)vis.getHeight()-6, bandW, (int)vis.getHeight()+18));
        g2.setClip(savedClip);
    }

    private JPanel createParchmentPanel(int x, int y, int w, int h) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int sx = 0, sy = 0, sw = getWidth(), sh = getHeight();
                for (int i = 8; i >= 1; i--) {
                    float a = 0.06f * (9 - i);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
                    g2.setColor(new Color(20, 8, 0));
                    g2.fill(new RoundRectangle2D.Float(sx-i, sy+i*2, sw+i*2, sh, 18, 18));
                }
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                g2.setPaint(new LinearGradientPaint(sx, sy, sx, sy + sh,
                    new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                    new Color[]{PARCH_TOP, PARCH_TAN, PARCH_WARM, PARCH_CENTRE,
                                PARCH_WARM, PARCH_TAN, PARCH_TOP}));
                Shape body = new RoundRectangle2D.Float(sx, sy, sw, sh, 18, 18);
                g2.fill(body);
                g2.setStroke(new BasicStroke(2.5f));
                g2.setColor(PARCH_BORDER);
                g2.draw(body);
                g2.dispose();
            }
        };
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(x, y, w, h);
        return panel;
    }

    private static class GoldButton extends JButton {
        private boolean hovered = false, pressed = false;
        GoldButton(String text) {
            super(text);
            setContentAreaFilled(false); setBorderPainted(false);
            setFocusPainted(false); setFont(FONT_BTN); setForeground(BTN_TEXT_DARK);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) { if (isEnabled()) { hovered = true;  repaint(); } }
                public void mouseExited (java.awt.event.MouseEvent e) { hovered = false; repaint(); }
                public void mousePressed(java.awt.event.MouseEvent e) { if (isEnabled()) { pressed = true;  repaint(); } }
                public void mouseReleased(java.awt.event.MouseEvent e){ pressed = false; repaint(); }
            });
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            int c = Math.min(w, h) / 6; if (c < 6) c = 6;
            Polygon oct = new Polygon(
                new int[]{c, w-c, w,   w,   w-c, c,   0,   0},
                new int[]{0, 0,   c,   h-c, h,   h,   h-c, c}, 8);
            if (!isEnabled()) {
                g2.setColor(new Color(160, 140, 100)); g2.fill(oct);
                g2.setColor(new Color(100, 80, 50));
                g2.setStroke(new BasicStroke(1.8f)); g2.draw(oct);
            } else {
                Color fill = pressed ? BTN_GOLD_PRESS : hovered ? BTN_GOLD_HOVER : BTN_GOLD_NORMAL;
                g2.setColor(fill); g2.fill(oct);
                if (hovered && !pressed) {
                    g2.setColor(new Color(255, 255, 255, 40));
                    g2.fill(new Polygon(
                        new int[]{c, w-c, w,   w,     w-c, c,   0,     0},
                        new int[]{0, 0,   c,   h/2-c, h/2, h/2, h/2-c, c}, 8));
                }
                g2.setColor(BTN_BORDER_CLR);
                g2.setStroke(new BasicStroke(2.0f)); g2.draw(oct);
            }
            FontMetrics fm = g2.getFontMetrics(getFont());
            int tx = (w - fm.stringWidth(getText())) / 2;
            int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
            g2.setFont(getFont());
            g2.setColor(new Color(0, 0, 0, 100)); g2.drawString(getText(), tx+1, ty+1);
            g2.setColor(getForeground());          g2.drawString(getText(), tx,   ty);
            g2.dispose();
        }
    }
}
