package Main;

import Entities.Characters.Player;
import Entities.Characters.Swordsman;
import Entities.Characters.Ranger;
import Entities.Characters.Mage;
import Items.Weapons.Weapon;

import Audio.SFX.SFXPlayer;
import Audio.SFX.ClickSFX;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LeaderboardPanel extends JPanel {

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    private static final Color BG_COLOR      = new Color(10, 5, 20);
    private static final Color PARCH_TOP     = new Color(148, 108, 44);
    private static final Color PARCH_TAN     = new Color(192, 152, 78);
    private static final Color PARCH_WARM    = new Color(220, 186, 118);
    private static final Color PARCH_CENTRE  = new Color(238, 212, 152);
    private static final Color PARCH_BORDER  = new Color(80, 38, 2, 230);
    private static final Color TEXT_DARK     = new Color(60, 30, 5);
    private static final Color TEXT_GOLD     = new Color(252, 218, 72);
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
    private static final Font FONT_STAT      = new Font("Monospaced", Font.BOLD, 14);
    private static final Font FONT_BTN       = new Font("Serif", Font.BOLD, 16);
    private static final Font FONT_NAME      = new Font("Serif", Font.BOLD, 24);
    private static final Font FONT_LABEL     = new Font("Serif", Font.BOLD, 12);
    private static final Font FONT_TABLE     = new Font("Monospaced", Font.PLAIN, 13);
    private static final Font FONT_TABLE_HDR = new Font("Serif", Font.BOLD, 13);

    private static final int LEFT_PANEL_X = 30;
    private static final int LEFT_PANEL_Y = 100;
    private static final int LEFT_PANEL_W = 460;
    private static final int LEFT_PANEL_H = 560;
    private static final int RIGHT_PANEL_X = 510;
    private static final int RIGHT_PANEL_Y = 100;
    private static final int RIGHT_PANEL_W = 484;
    private static final int RIGHT_PANEL_H = 560;

    private final JFrame parentFrame;
    private final Player player;
    private final long playTimeMs;
    private final Runnable onPlayAgain;
    private final Runnable onQuitToTitle;

    private BufferedImage[] playerIdleFrames = new BufferedImage[5];
    private int currentPlayerFrame = 0;
    private Timer animationTimer;
    private JTextField nameField;
    private JLabel playerSpriteLabel;
    private JLabel roleLabel;
    private JLabel goldLabel;
    private JLabel weaponLabel;
    private JLabel playtimeLabel;
    private JLabel rankLabel;
    private JLabel statusLabel;
    private DefaultTableModel leaderboardTableModel;
    private JTable leaderboardTable;
    private GoldButton saveButton;
    private GoldButton playAgainBtn;
    private GoldButton quitToTitleBtn;
    private final SFXPlayer sfxPlayer = new SFXPlayer();
    private float shimmer = 1.4f;
    private float floatY = 0f;
    private float floatDir = 1f;
    private javax.swing.Timer titleAnimTimer;
    private BufferedImage loadingBg;
    private static final String LEADERBOARD_FILE = "leaderboard.dat";

    public LeaderboardPanel(JFrame parentFrame, Player player, long playTimeMs,
                            Runnable onPlayAgain, Runnable onQuitToTitle) {
        this.parentFrame   = parentFrame;
        this.player        = player;
        this.playTimeMs    = playTimeMs;
        this.onPlayAgain   = onPlayAgain;
        this.onQuitToTitle = onQuitToTitle;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        setOpaque(true);
        setFocusable(true);
        loadBackground();
        loadTitleFont();
        loadPlayerAnimations();
        initUI();
        startAnimationTimer();
        startTitleAnimation();
    }

    private void loadBackground() {
        try { loadingBg = ImageIO.read(getClass().getResourceAsStream("/backgrounds/victory_bg.png")); }
        catch (Exception e) { loadingBg = null; }
    }

    private void loadTitleFont() {
        Font base = null;
        for (String n : new String[]{"RINGM___.TTF","RingbearerMedium.ttf","Ringbearer Medium.ttf","ringbearer medium.ttf","Ringbearer.ttf","ringbearer.ttf"}) {
            File f = new File(n);
            if (!f.exists()) continue;
            try { base = Font.createFont(Font.TRUETYPE_FONT, f); GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(base); break; }
            catch (Exception ex) { }
        }
        if (base == null) base = new Font("Palatino Linotype", Font.PLAIN, 12);
        fontTitle = base.deriveFont(Font.PLAIN, 48f);
    }

    private void loadPlayerAnimations() {
        String className = getCharacterResourceName();
        boolean loaded = false;
        for (int i = 0; i < 5; i++) {
            try {
                String path = "/" + className + "/" + className + "_idle/idle_right" + (i+1) + ".png";
                playerIdleFrames[i] = ImageIO.read(getClass().getResourceAsStream(path));
                if (playerIdleFrames[i] != null) loaded = true;
            } catch (Exception e) { playerIdleFrames[i] = null; }
        }
        if (!loaded) createPlaceholderFrames();
    }

    private String getCharacterResourceName() {
        if (player instanceof Swordsman) return "swordsman";
        if (player instanceof Ranger)   return "archer";
        if (player instanceof Mage)     return "mage";
        return "swordsman";
    }

    public String getCharacterRoleName() {
        if (player instanceof Swordsman) return "Swordsman";
        if (player instanceof Ranger)   return "Archer";
        if (player instanceof Mage)     return "Mage";
        return "Unknown";
    }

    private void createPlaceholderFrames() {
        Color bc; String init;
        if (player instanceof Swordsman) { bc = new Color(200,80,80); init = "S"; }
        else if (player instanceof Ranger) { bc = new Color(80,180,100); init = "R"; }
        else { bc = new Color(80,120,220); init = "M"; }
        for (int i = 0; i < 5; i++) {
            float b = 0.7f + i*0.06f;
            Color fc = new Color(Math.min(255,(int)(bc.getRed()*b)),Math.min(255,(int)(bc.getGreen()*b)),Math.min(255,(int)(bc.getBlue()*b)));
            BufferedImage img = new BufferedImage(96,96,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setColor(fc); g2.fillRect(0,0,96,96);
            g2.setFont(new Font("Serif",Font.BOLD,32));
            FontMetrics fm = g2.getFontMetrics();
            int lx=(96-fm.stringWidth(init))/2, ly=(96+fm.getAscent()-fm.getDescent())/2+2;
            g2.setColor(new Color(0,0,0,80)); g2.drawString(init,lx+2,ly+2);
            g2.setColor(Color.WHITE); g2.drawString(init,lx,ly);
            g2.dispose();
            playerIdleFrames[i]=img;
        }
    }

    private void startAnimationTimer() {
        animationTimer = new Timer();
        animationTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                currentPlayerFrame = (currentPlayerFrame + 1) % 5;
                SwingUtilities.invokeLater(() -> {
                    if (playerSpriteLabel != null && playerIdleFrames[currentPlayerFrame] != null) {
                        Image s = playerIdleFrames[currentPlayerFrame].getScaledInstance(96,96,Image.SCALE_SMOOTH);
                        playerSpriteLabel.setIcon(new ImageIcon(s));
                    }
                });
            }
        }, 0, 150);
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
        JPanel leftPanel = createParchmentPanel(LEFT_PANEL_X, LEFT_PANEL_Y, LEFT_PANEL_W, LEFT_PANEL_H);
        add(leftPanel);
        setupLeftPanel(leftPanel);

        JPanel rightPanel = createParchmentPanel(RIGHT_PANEL_X, RIGHT_PANEL_Y, RIGHT_PANEL_W, RIGHT_PANEL_H);
        add(rightPanel);
        setupRightPanel(rightPanel);

        // Status message — sits just below both panels
        statusLabel = new JLabel("Enter your name above and click  Save Score  to record your victory!", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Serif", Font.BOLD, 13));
        statusLabel.setForeground(TEXT_GOLD);
        statusLabel.setBounds(30, 662, 964, 22);
        add(statusLabel);

        // Play Again + Quit to Title — centred in the full width below both panels
        int btnW = 200, btnH = 48, gap = 28;
        int btnY = 690;
        int totalBtnW = btnW * 2 + gap;
        int startX = (WIDTH - totalBtnW) / 2;

        playAgainBtn = new GoldButton("Play Again");
        playAgainBtn.setBounds(startX, btnY, btnW, btnH);
        playAgainBtn.addActionListener(e -> {
            sfxPlayer.playSFX(new ClickSFX());
            cleanup();
            if (onPlayAgain != null) onPlayAgain.run();
        });
        add(playAgainBtn);

        quitToTitleBtn = new GoldButton("Quit to Title");
        quitToTitleBtn.setBounds(startX + btnW + gap, btnY, btnW, btnH);
        quitToTitleBtn.addActionListener(e -> {
            sfxPlayer.playSFX(new ClickSFX());
            cleanup();
            if (onQuitToTitle != null) onQuitToTitle.run();
        });
        add(quitToTitleBtn);
    }

    // ─────────────────────────────────────────────────────────────────
    //  LEFT PANEL  — player card
    // ─────────────────────────────────────────────────────────────────
    private void setupLeftPanel(JPanel panel) {
        // Section title
        JLabel sectionTitle = new JLabel("Your Results", SwingConstants.CENTER);
        sectionTitle.setFont(new Font("Serif", Font.BOLD, 20));
        sectionTitle.setForeground(TEXT_DARK);
        sectionTitle.setBounds(20, 10, 420, 28);
        panel.add(sectionTitle);

        // Divider under title
        JSeparator titleSep = new JSeparator();
        titleSep.setBounds(20, 42, 420, 2);
        titleSep.setForeground(TEXT_GOLD);
        panel.add(titleSep);

        // Name label + field
        JLabel nameLabel = new JLabel("Enter Your Name:");
        nameLabel.setFont(new Font("Serif", Font.BOLD, 16));
        nameLabel.setForeground(TEXT_DARK);
        nameLabel.setBounds(20, 52, 200, 22);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(FONT_NAME);
        nameField.setForeground(TEXT_DARK);
        nameField.setBackground(new Color(248, 228, 180));
        nameField.setCaretColor(TEXT_DARK);
        nameField.setBounds(20, 78, 290, 40);
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180,140,60), 2),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));

        ((AbstractDocument) nameField.getDocument()).setDocumentFilter(new DocumentFilter() {
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                    throws BadLocationException {
                if ((fb.getDocument().getLength() + text.length()) <= 10)
                    super.insertString(fb, offset, text, attr);
            }
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if ((fb.getDocument().getLength() - length + text.length()) <= 10)
                    super.replace(fb, offset, length, text, attrs);
            }
        });
        nameField.setText("Hero");
        panel.add(nameField);

        JLabel limitLabel = new JLabel("(Max 10 chars)");
        limitLabel.setFont(FONT_LABEL);
        limitLabel.setForeground(new Color(120, 100, 70));
        limitLabel.setBounds(320, 90, 110, 18);
        panel.add(limitLabel);

        // Player sprite
        playerSpriteLabel = new JLabel();
        if (playerIdleFrames[0] != null) {
            Image s = playerIdleFrames[0].getScaledInstance(96, 96, Image.SCALE_SMOOTH);
            playerSpriteLabel.setIcon(new ImageIcon(s));
        }
        playerSpriteLabel.setBounds(20, 136, 96, 96);
        panel.add(playerSpriteLabel);

        // Stats box to the right of sprite
        JPanel infoPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 100));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.setColor(TEXT_GOLD);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(2, 2, getWidth()-4, getHeight()-4, 8, 8));
                g2.dispose();
            }
        };
        infoPanel.setLayout(null);
        infoPanel.setOpaque(false);
        infoPanel.setBounds(130, 136, 310, 150);
        panel.add(infoPanel);

        roleLabel    = addInfoRow(infoPanel, "Role:",      getCharacterRoleName(),         8);
        goldLabel    = addInfoRow(infoPanel, "Gold:",      "$ " + player.getMoney(),       36);
        Weapon ew = player.getWeapon();
        weaponLabel  = addInfoRow(infoPanel, "Weapon:",    (ew != null ? ew.getName() : "None"), 64);
        playtimeLabel= addInfoRow(infoPanel, "Play Time:", formatPlayTime(playTimeMs),     92);
        rankLabel    = addInfoRow(infoPanel, "Best Rank:", "--",                           120);

        // Divider
        JSeparator sep = new JSeparator();
        sep.setBounds(20, 306, 420, 2);
        sep.setForeground(TEXT_GOLD);
        panel.add(sep);

        // Legend / instructions
        JLabel legend = new JLabel(
            "<html><center>Enter your name above, then click<br>" +
            "<b>Save Score</b> to record your victory!<br><br>" +
            "<i>Leaderboard is sorted by lowest play time.</i></center></html>");
        legend.setFont(new Font("Serif", Font.PLAIN, 13));
        legend.setForeground(TEXT_DARK);
        legend.setHorizontalAlignment(SwingConstants.CENTER);
        legend.setBounds(20, 316, 420, 100);
        panel.add(legend);

        // Tip label for buttons below panels
        JLabel tipLabel = new JLabel(
            "<html><center><i>Use  Play Again  to start fresh or<br>Quit to Title  to return to the main menu.</i></center></html>");
        tipLabel.setFont(new Font("Serif", Font.ITALIC, 12));
        tipLabel.setForeground(new Color(80, 50, 10));
        tipLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tipLabel.setBounds(20, 425, 420, 60);
        panel.add(tipLabel);

        // Save Score button — child of leftPanel so it is always rendered above the parchment
        saveButton = new GoldButton("Save Score");
        saveButton.setBounds((LEFT_PANEL_W - 200) / 2, LEFT_PANEL_H - 62, 200, 48);
        saveButton.addActionListener(e -> { sfxPlayer.playSFX(new ClickSFX()); saveScore(); });
        panel.add(saveButton);
    }

    private JLabel addInfoRow(JPanel panel, String label, String value, int y) {
        JLabel l = new JLabel(label);
        l.setFont(FONT_LABEL);
        l.setForeground(TEXT_GOLD);
        l.setBounds(10, y, 80, 20);
        panel.add(l);

        JLabel v = new JLabel(value);
        v.setFont(FONT_STAT);
        v.setForeground(Color.WHITE);
        v.setBounds(90, y, 210, 20);
        panel.add(v);
        return v;
    }

    // ─────────────────────────────────────────────────────────────────
    //  RIGHT PANEL  — leaderboard table
    // ─────────────────────────────────────────────────────────────────
    private void setupRightPanel(JPanel panel) {
        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setForeground(TEXT_DARK);
        title.setBounds(20, 10, 444, 30);
        panel.add(title);

        JSeparator sep = new JSeparator();
        sep.setBounds(20, 44, 444, 2);
        sep.setForeground(TEXT_GOLD);
        panel.add(sep);

        // Table model — columns: Rank, Name, Role, Weapon, Gold, Time
        String[] cols = {"#", "Name", "Role", "Weapon", "Gold", "Time"};
        leaderboardTableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        leaderboardTable = new JTable(leaderboardTableModel) {
            @Override public boolean getScrollableTracksViewportWidth() { return true; }
        };
        leaderboardTable.setFont(FONT_TABLE);
        leaderboardTable.setForeground(TEXT_DARK);
        leaderboardTable.setRowHeight(26);
        leaderboardTable.setShowGrid(false);
        leaderboardTable.setIntercellSpacing(new Dimension(0, 1));
        leaderboardTable.setSelectionBackground(new Color(200, 170, 90));
        leaderboardTable.setSelectionForeground(TEXT_DARK);
        leaderboardTable.setFocusable(false);
        leaderboardTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Column widths (total ≈ 448px — fills the 454px scroll pane minus scrollbar)
        int[] colW = {38, 92, 78, 100, 62, 78};
        for (int i = 0; i < colW.length; i++) {
            TableColumn tc = leaderboardTable.getColumnModel().getColumn(i);
            tc.setPreferredWidth(colW[i]);
            tc.setMinWidth(colW[i]);
            tc.setMaxWidth(colW[i]);
        }

        // Row renderer — alternating colors, gold/silver/bronze for top 3
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
                setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 2));
                // Right-align the Gold column
                setHorizontalAlignment(col == 4 ? SwingConstants.RIGHT : SwingConstants.LEFT);
                return this;
            }
        });

        // Table header styling
        JTableHeader header = leaderboardTable.getTableHeader();
        header.setFont(FONT_TABLE_HDR);
        header.setForeground(TEXT_GOLD);
        header.setBackground(new Color(70, 35, 5));
        header.setPreferredSize(new Dimension(0, 26));
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
                        BorderFactory.createEmptyBorder(0, 6, 0, 2)));
                setHorizontalAlignment(col == 4 ? SwingConstants.RIGHT : SwingConstants.LEFT);
                return this;
            }
        });

        JScrollPane sp = new JScrollPane(leaderboardTable);
        sp.setBounds(15, 52, 454, 462);
        sp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 140, 60), 2),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        sp.getViewport().setBackground(ROW_EVEN);
        sp.getVerticalScrollBar().setUnitIncrement(26);
        panel.add(sp);

        loadLeaderboard();
    }

    // ─────────────────────────────────────────────────────────────────
    //  DATA  — read / write / display
    // ─────────────────────────────────────────────────────────────────
    private String formatPlayTime(long ms) {
        long ts = ms / 1000, h = ts / 3600, m = (ts % 3600) / 60, s = ts % 60;
        if (h > 0) return String.format("%dh %02dm %02ds", h, m, s);
        if (m > 0) return String.format("%dm %02ds", m, s);
        return String.format("%ds", s);
    }

    private static class LeaderboardEntry {
        String name, role, weapon;
        long playTimeMs;
        int gold;
        LeaderboardEntry(String n, String r, long t, int g, String w) {
            name = n; role = r; playTimeMs = t; gold = g; weapon = w;
        }
    }

    private void loadLeaderboard() {
        updateLeaderboardDisplay(readLeaderboardFile());
    }

    private List<LeaderboardEntry> readLeaderboardFile() {
        List<LeaderboardEntry> entries = new ArrayList<>();
        File file = new File(LEADERBOARD_FILE);
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
        // Sort by lowest play time (ascending)
        entries.sort((a, b) -> Long.compare(a.playTimeMs, b.playTimeMs));
        return entries;
    }

    private void saveLeaderboardFile(List<LeaderboardEntry> entries) {
        entries.sort((a, b) -> Long.compare(a.playTimeMs, b.playTimeMs));
        try (BufferedWriter w = new BufferedWriter(new FileWriter(LEADERBOARD_FILE))) {
            for (LeaderboardEntry e : entries)
                w.write(e.name + "|" + e.role + "|" + e.playTimeMs + "|" + e.gold + "|" + e.weapon + "\n");
        } catch (IOException e) {
            System.err.println("Could not write leaderboard: " + e.getMessage());
        }
    }

    private void updateLeaderboardDisplay(List<LeaderboardEntry> entries) {
        leaderboardTableModel.setRowCount(0);
        if (entries.isEmpty()) {
            leaderboardTableModel.addRow(new Object[]{"--", "No entries yet", "", "", "", ""});
            return;
        }
        int rank = 1;
        for (LeaderboardEntry e : entries) {
            String rankStr = rank == 1 ? "1st" : rank == 2 ? "2nd" : rank == 3 ? "3rd" : rank + "th";
            String dn = e.name.length()   > 10 ? e.name.substring(0, 10)   : e.name;
            String dw = e.weapon.length() > 11 ? e.weapon.substring(0, 11) : e.weapon;
            leaderboardTableModel.addRow(new Object[]{
                rankStr,
                dn,
                e.role,
                dw,
                "$" + e.gold,
                formatPlayTime(e.playTimeMs)
            });
            rank++;
        }
    }

    private void saveScore() {
        String pn = nameField.getText().trim();
        if (pn.isEmpty()) pn = "Hero";
        if (pn.length() > 10) pn = pn.substring(0, 10);

        Weapon ew = player.getWeapon();
        String wn = (ew != null) ? ew.getName() : "None";

        LeaderboardEntry ne = new LeaderboardEntry(pn, getCharacterRoleName(), playTimeMs, player.getMoney(), wn);
        List<LeaderboardEntry> entries = readLeaderboardFile();
        entries.add(ne);
        saveLeaderboardFile(entries);
        updateLeaderboardDisplay(entries);

        // Find the saved entry's rank
        int rank = 1;
        for (LeaderboardEntry e : entries) {
            if (e == ne) break;
            rank++;
        }
        String rs = rank == 1 ? "1st" : rank == 2 ? "2nd" : rank == 3 ? "3rd" : rank + "th";
        rankLabel.setText(rs);

        statusLabel.setText("Score saved!  You placed  " + rs + "  on the leaderboard!");
        statusLabel.setForeground(new Color(100, 220, 100));

        saveButton.setEnabled(false);
        saveButton.setText("Saved!");

        nameField.setEnabled(false);
    }

    private void cleanup() {
        if (animationTimer != null) { animationTimer.cancel(); animationTimer = null; }
        if (titleAnimTimer != null) { titleAnimTimer.stop(); titleAnimTimer = null; }
    }

    // ─────────────────────────────────────────────────────────────────
    //  PAINTING
    // ─────────────────────────────────────────────────────────────────
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
        String text = "Victory Log";
        g2.setFont(fontTitle);
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = fontTitle.createGlyphVector(frc, text);
        Rectangle2D vis = gv.getVisualBounds();
        int tw = (int) vis.getWidth();
        int tx = (WIDTH - tw) / 2 - (int) vis.getX();
        int ty = (int) (60 + floatY);

        g2.setColor(new Color(0, 0, 0, 185));
        g2.drawString(text, tx + 4, ty + 6);
        g2.setColor(new Color(0, 0, 0, 75));
        g2.drawString(text, tx + 8, ty + 11);

        g2.setPaint(new LinearGradientPaint(tx, ty-(int)vis.getHeight(), tx, ty+8,
            new float[]{0f, 0.35f, 0.65f, 1f},
            new Color[]{new Color(255,252,210), new Color(252,218,72), new Color(218,138,18), new Color(245,198,48)}));
        g2.drawString(text, tx, ty);

        float bandW = 110f;
        float bandX = tx + shimmer * (tw + bandW) - bandW;
        Shape savedClip = g2.getClip();
        g2.clip(gv.getOutline(tx, ty));
        g2.setPaint(new LinearGradientPaint(bandX, 0, bandX+bandW, 0,
            new float[]{0f, 0.35f, 0.5f, 0.65f, 1f},
            new Color[]{new Color(255,248,200,0), new Color(255,248,200,85), new Color(255,255,255,215),
                        new Color(255,248,200,85), new Color(255,248,200,0)}));
        g2.fill(new Rectangle2D.Float(bandX, ty-(int)vis.getHeight()-6, bandW, (int)vis.getHeight()+18));
        g2.setClip(savedClip);
    }

    private JPanel createParchmentPanel(int x, int y, int w, int h) {
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int sx=0, sy=0, sw=getWidth(), sh=getHeight();
                for (int i = 8; i >= 1; i--) {
                    float a = 0.06f * (9 - i);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
                    g2.setColor(new Color(20, 8, 0));
                    g2.fill(new RoundRectangle2D.Float(sx-i, sy+i*2, sw+i*2, sh, 18, 18));
                }
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                g2.setPaint(new LinearGradientPaint(sx, sy, sx, sy+sh,
                    new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                    new Color[]{PARCH_TOP, PARCH_TAN, PARCH_WARM, PARCH_CENTRE, PARCH_WARM, PARCH_TAN, PARCH_TOP}));
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

    // ─────────────────────────────────────────────────────────────────
    //  GOLD BUTTON
    // ─────────────────────────────────────────────────────────────────
    private static class GoldButton extends JButton {
        private boolean hovered = false, pressed = false;
        GoldButton(String text) {
            super(text);
            setContentAreaFilled(false); setBorderPainted(false);
            setFocusPainted(false); setFont(FONT_BTN); setForeground(BTN_TEXT_DARK);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) { if (isEnabled()) { hovered = true; repaint(); } }
                public void mouseExited(java.awt.event.MouseEvent e)  { hovered = false; repaint(); }
                public void mousePressed(java.awt.event.MouseEvent e) { if (isEnabled()) { pressed = true; repaint(); } }
                public void mouseReleased(java.awt.event.MouseEvent e){ pressed = false; repaint(); }
            });
        }
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
                        new int[]{c, w-c, w,   w,   w-c, c,   0,   0},
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
