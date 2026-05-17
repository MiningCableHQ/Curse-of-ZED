package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeListener;

public class PetCompanion {

    public enum PetState { FOLLOW, HIDE, DANCE, IDLE }

    private PetState state      = PetState.FOLLOW;
    private int      stateTimer = 0;

    private final LinkedList<int[]> trail = new LinkedList<>();
    private static final int TRAIL_LENGTH = 40;

    private float bobOffset  = 0f;
    private int   bobTimer   = 0;
    private float danceAngle = 0f;

    private int petScreenX, petScreenY;

    // ── Sprite ───────────────────────────────────────────────────
    private BufferedImage sprite;

    // ── Customization ────────────────────────────────────────────
    public Color  bodyColor    = new Color(120, 220, 120);   // default green slime
    public Color  eyeColor     = new Color(30, 30, 80);
    public String accessory    = "none";   // "none" | "hat" | "bow" | "crown" | "glasses"
    public String petName      = "Slimey";
    public boolean showName    = true;

    // Accessory colors
    private static final Color HAT_COLOR    = new Color(50, 20, 5);
    private static final Color BOW_COLOR    = new Color(220, 60, 120);
    private static final Color CROWN_COLOR  = new Color(252, 218, 72);
    private static final Color GLASS_COLOR  = new Color(80, 160, 220);

    public PetCompanion() {
        try {
            sprite = ImageIO.read(
                    getClass().getResourceAsStream("/pet/companion.png"));
        } catch (Exception e) {
            sprite = null;
        }
    }

    // ── Update ───────────────────────────────────────────────────
    public void update(int playerWorldX, int playerWorldY,
                       int playerScreenX, int playerScreenY) {
        trail.addLast(new int[]{playerWorldX, playerWorldY});
        if (trail.size() > TRAIL_LENGTH) trail.removeFirst();

        int[] delayed = trail.getFirst();
        petScreenX = delayed[0] - playerWorldX + playerScreenX - 20;
        petScreenY = delayed[1] - playerWorldY + playerScreenY + 8;

        bobTimer++;
        bobOffset = (float) Math.sin(bobTimer * 0.15f) * 3f;

        if (stateTimer > 0) {
            stateTimer--;
            if (stateTimer == 0) state = PetState.FOLLOW;
        }
        if (state == PetState.DANCE) danceAngle += 0.25f;
    }

    // ── Emote reactions ──────────────────────────────────────────
    public void reactToEmote(String emote) {
        switch (emote) {
            case "😱": state = PetState.HIDE;  stateTimer = 180; break;
            case "🔥": state = PetState.DANCE; stateTimer = 240; break;
            case "💖": state = PetState.IDLE;  stateTimer = 120; break;
            default:   state = PetState.FOLLOW; break;
        }
    }

    // ── Draw ─────────────────────────────────────────────────────
    public void draw(Graphics2D g2) {
        if (state == PetState.HIDE) {
            g2.setFont(new Font("Serif", Font.PLAIN, 12));
            g2.setColor(new Color(255, 255, 255, 160));
            g2.drawString("...", petScreenX, petScreenY);
            return;
        }

        Graphics2D g = (Graphics2D) g2.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int drawX = petScreenX;
        int drawY = petScreenY + (int) bobOffset;

        if (state == PetState.DANCE)
            g.rotate(Math.sin(danceAngle) * 0.4, drawX + 16, drawY + 16);

        if (sprite != null) {
            // Tint the sprite with body color
            g.drawImage(tintImage(sprite, bodyColor), drawX, drawY, 32, 32, null);
        } else {
            drawSlime(g, drawX, drawY);
        }

        drawAccessory(g, drawX, drawY);

        // Pet name tag
        if (showName && petName != null && !petName.isEmpty()) {
            g.setFont(new Font("Serif", Font.BOLD, 9));
            FontMetrics fm = g.getFontMetrics();
            int nw = fm.stringWidth(petName);
            g.setColor(new Color(0, 0, 0, 120));
            g.fillRoundRect(drawX + 16 - nw / 2 - 3, drawY - 14, nw + 6, 12, 4, 4);
            g.setColor(new Color(252, 218, 72));
            g.drawString(petName, drawX + 16 - nw / 2, drawY - 4);
        }

        // Heart when ❤️
        if (state == PetState.IDLE) {
            g.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
            g.setColor(Color.WHITE);
            g.drawString("❤️", drawX + 8, drawY - 16);
        }

        g.dispose();
    }

    private void drawSlime(Graphics2D g, int x, int y) {
        // Body
        g.setColor(new Color(bodyColor.getRed(), bodyColor.getGreen(),
                bodyColor.getBlue(), 220));
        g.fillOval(x + 4, y + 8, 24, 18);

        // Outline
        Color outline = bodyColor.darker();
        g.setColor(outline);
        g.setStroke(new BasicStroke(1.5f));
        g.drawOval(x + 4, y + 8, 24, 18);

        // Shine
        g.setColor(new Color(255, 255, 255, 80));
        g.fillOval(x + 8, y + 10, 8, 5);

        // Eyes
        g.setColor(Color.WHITE);
        g.fillOval(x + 8,  y + 10, 6, 6);
        g.fillOval(x + 18, y + 10, 6, 6);
        g.setColor(eyeColor);
        g.fillOval(x + 10, y + 12, 3, 3);
        g.fillOval(x + 20, y + 12, 3, 3);
    }

    private void drawAccessory(Graphics2D g, int x, int y) {
        switch (accessory) {
            case "hat":
                // Small top hat
                g.setColor(HAT_COLOR);
                g.fillRect(x + 9, y + 2, 14, 8);
                g.fillRect(x + 6, y + 9, 20, 3);
                g.setColor(new Color(120, 60, 10));
                g.setStroke(new BasicStroke(1f));
                g.drawRect(x + 9, y + 2, 14, 8);
                g.drawRect(x + 6, y + 9, 20, 3);
                break;
            case "bow":
                // Pink bow
                g.setColor(BOW_COLOR);
                int[] bx = {x+10, x+16, x+10, x+16};
                int[] by = {y+3,  y+7,  y+7,  y+3};
                g.fillPolygon(bx, by, 4);
                int[] bx2 = {x+16, x+22, x+16, x+22};
                int[] by2 = {y+3,  y+7,  y+7,  y+3};
                g.fillPolygon(bx2, by2, 4);
                g.setColor(new Color(255, 120, 180));
                g.fillOval(x + 14, y + 4, 5, 5);
                break;
            case "crown":
                // Gold crown
                g.setColor(CROWN_COLOR);
                int[] cx = {x+8, x+11, x+16, x+21, x+24, x+24, x+8};
                int[] cy = {y+8, y+4,  y+7,  y+4,  y+8,  y+11, y+11};
                g.fillPolygon(cx, cy, 7);
                g.setColor(new Color(200, 150, 0));
                g.setStroke(new BasicStroke(1f));
                g.drawPolygon(cx, cy, 7);
                // Gems
                g.setColor(new Color(220, 60, 60));
                g.fillOval(x + 14, y + 5, 4, 4);
                break;
            case "glasses":
                // Round glasses
                g.setColor(GLASS_COLOR);
                g.setStroke(new BasicStroke(1.5f));
                g.drawOval(x + 7, y + 10, 7, 7);
                g.drawOval(x + 17, y + 10, 7, 7);
                g.drawLine(x + 14, y + 13, x + 17, y + 13);
                break;
            // "none" — draw nothing
        }
    }

    // Tint a BufferedImage with a color (used if sprite is loaded)
    private BufferedImage tintImage(BufferedImage src, Color tint) {
        BufferedImage out = new BufferedImage(
                src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D tg = out.createGraphics();
        tg.drawImage(src, 0, 0, null);
        tg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
        tg.setColor(tint);
        tg.fillRect(0, 0, src.getWidth(), src.getHeight());
        tg.dispose();
        return out;
    }

    // ── Customization dialog ──────────────────────────────────────
    public void openCustomizeDialog(java.awt.Component parent) {
        JDialog dlg = new JDialog(
                SwingUtilities.getWindowAncestor(parent) instanceof JFrame
                        ? (JFrame) SwingUtilities.getWindowAncestor(parent) : null,
                "🐾 Customize Pet", true);
        dlg.setUndecorated(false);
        dlg.setSize(360, 420);
        dlg.setLocationRelativeTo(parent);
        dlg.setResizable(false);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(238, 212, 152));

        // Title
        JLabel title = new JLabel("Customize Your Pet!", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 18));
        title.setForeground(new Color(50, 22, 2));
        title.setBounds(0, 10, 360, 28);
        panel.add(title);

        // Pet name
        JLabel nameLbl = new JLabel("Pet Name:");
        nameLbl.setFont(new Font("Serif", Font.BOLD, 13));
        nameLbl.setForeground(new Color(50, 22, 2));
        nameLbl.setBounds(20, 50, 80, 24);
        panel.add(nameLbl);

        JTextField nameField = new JTextField(petName);
        nameField.setFont(new Font("Serif", Font.PLAIN, 13));
        nameField.setBounds(105, 50, 160, 26);
        panel.add(nameField);

        // Body color
        JLabel colorLbl = new JLabel("Body Color:");
        colorLbl.setFont(new Font("Serif", Font.BOLD, 13));
        colorLbl.setForeground(new Color(50, 22, 2));
        colorLbl.setBounds(20, 92, 90, 24);
        panel.add(colorLbl);

        // Color swatches
        Color[] colorChoices = {
                new Color(120, 220, 120),  // green
                new Color(100, 180, 255),  // blue
                new Color(255, 120, 120),  // red/pink
                new Color(220, 180, 80),   // gold
                new Color(200, 120, 220),  // purple
                new Color(255, 180, 80),   // orange
                new Color(220, 220, 220),  // white
                new Color(60,  60,  60),   // dark
        };
        final Color[] selectedColor = {bodyColor};
        JPanel swatchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        swatchPanel.setBackground(new Color(238, 212, 152));
        swatchPanel.setBounds(105, 90, 230, 34);
        for (Color c : colorChoices) {
            JButton sw = new JButton();
            sw.setBackground(c);
            sw.setPreferredSize(new Dimension(24, 24));
            sw.setBorder(BorderFactory.createLineBorder(
                    c.equals(selectedColor[0]) ? Color.BLACK : new Color(100,60,10), 2));
            sw.addActionListener(e -> {
                selectedColor[0] = c;
                // Update border highlights
                for (java.awt.Component comp : swatchPanel.getComponents()) {
                    if (comp instanceof JButton) {
                        ((JButton)comp).setBorder(BorderFactory.createLineBorder(
                                ((JButton)comp).getBackground().equals(c)
                                        ? Color.BLACK : new Color(100,60,10), 2));
                    }
                }
            });
            swatchPanel.add(sw);
        }
        panel.add(swatchPanel);

        // Eye color
        JLabel eyeLbl = new JLabel("Eye Color:");
        eyeLbl.setFont(new Font("Serif", Font.BOLD, 13));
        eyeLbl.setForeground(new Color(50, 22, 2));
        eyeLbl.setBounds(20, 134, 80, 24);
        panel.add(eyeLbl);

        Color[] eyeChoices = {
                new Color(30, 30, 80),     // dark blue (default)
                new Color(180, 30, 30),    // red
                new Color(20, 100, 20),    // green
                new Color(180, 100, 20),   // amber
                Color.BLACK,
                Color.WHITE,
        };
        final Color[] selectedEye = {eyeColor};
        JPanel eyePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        eyePanel.setBackground(new Color(238, 212, 152));
        eyePanel.setBounds(105, 132, 200, 34);
        for (Color c : eyeChoices) {
            JButton sw = new JButton();
            sw.setBackground(c);
            sw.setPreferredSize(new Dimension(24, 24));
            sw.setBorder(BorderFactory.createLineBorder(
                    c.equals(selectedEye[0]) ? Color.BLACK : new Color(100,60,10), 2));
            sw.addActionListener(e -> {
                selectedEye[0] = c;
                for (java.awt.Component comp : eyePanel.getComponents()) {
                    if (comp instanceof JButton) {
                        ((JButton)comp).setBorder(BorderFactory.createLineBorder(
                                ((JButton)comp).getBackground().equals(c)
                                        ? Color.BLACK : new Color(100,60,10), 2));
                    }
                }
            });
            eyePanel.add(sw);
        }
        panel.add(eyePanel);

        // Accessory
        JLabel accLbl = new JLabel("Accessory:");
        accLbl.setFont(new Font("Serif", Font.BOLD, 13));
        accLbl.setForeground(new Color(50, 22, 2));
        accLbl.setBounds(20, 178, 80, 24);
        panel.add(accLbl);

        String[] accessories = {"none", "hat", "bow", "crown", "glasses"};
        String[] accLabels   = {"None", "🎩 Hat", "🎀 Bow", "👑 Crown", "🕶 Glasses"};
        JComboBox<String> accBox = new JComboBox<>(accLabels);
        accBox.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        // Set current selection
        for (int i = 0; i < accessories.length; i++) {
            if (accessories[i].equals(accessory)) { accBox.setSelectedIndex(i); break; }
        }
        accBox.setBounds(105, 176, 160, 28);
        panel.add(accBox);

        // Show name toggle
        JCheckBox showNameBox = new JCheckBox("Show name tag", showName);
        showNameBox.setFont(new Font("Serif", Font.PLAIN, 13));
        showNameBox.setBackground(new Color(238, 212, 152));
        showNameBox.setForeground(new Color(50, 22, 2));
        showNameBox.setBounds(20, 218, 200, 24);
        panel.add(showNameBox);

        // Preview label
        JLabel previewLbl = new JLabel("Preview:", SwingConstants.CENTER);
        previewLbl.setFont(new Font("Serif", Font.ITALIC, 11));
        previewLbl.setForeground(new Color(80, 40, 8));
        previewLbl.setBounds(0, 248, 360, 18);
        panel.add(previewLbl);

        // Live preview canvas
        JPanel preview = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 20, 10));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                // Draw preview slime with current settings
                int cx = getWidth() / 2 - 16;
                int cy = getHeight() / 2 - 16;
                drawSlimePreview(g2, cx, cy, selectedColor[0], selectedEye[0],
                        accessories[accBox.getSelectedIndex()]);
            }
        };
        preview.setOpaque(false);
        preview.setBounds(130, 265, 100, 70);
        panel.add(preview);

        // Repaint preview on changes
        ChangeListener repaintPreview = e -> preview.repaint();
        accBox.addActionListener(e -> preview.repaint());
        for (java.awt.Component c : swatchPanel.getComponents())
            if (c instanceof JButton) ((JButton)c).addActionListener(e -> preview.repaint());
        for (java.awt.Component c : eyePanel.getComponents())
            if (c instanceof JButton) ((JButton)c).addActionListener(e -> preview.repaint());

        // Confirm / Cancel
        JButton confirmBtn = makeDialogBtn("✔ Apply");
        confirmBtn.setBounds(60, 348, 100, 36);
        confirmBtn.addActionListener(e -> {
            petName    = nameField.getText().trim().isEmpty() ? "Slimey" : nameField.getText().trim();
            bodyColor  = selectedColor[0];
            eyeColor   = selectedEye[0];
            accessory  = accessories[accBox.getSelectedIndex()];
            showName   = showNameBox.isSelected();
            dlg.dispose();
        });
        panel.add(confirmBtn);

        JButton cancelBtn = makeDialogBtn("✕ Cancel");
        cancelBtn.setBounds(200, 348, 100, 36);
        cancelBtn.addActionListener(e -> dlg.dispose());
        panel.add(cancelBtn);

        dlg.setContentPane(panel);
        dlg.setVisible(true);
    }

    private void drawSlimePreview(Graphics2D g, int x, int y,
                                  Color body, Color eye, String acc) {
        g.setColor(new Color(body.getRed(), body.getGreen(), body.getBlue(), 220));
        g.fillOval(x + 4, y + 8, 24, 18);
        g.setColor(body.darker());
        g.setStroke(new BasicStroke(1.5f));
        g.drawOval(x + 4, y + 8, 24, 18);
        g.setColor(Color.WHITE);
        g.fillOval(x + 8,  y + 10, 6, 6);
        g.fillOval(x + 18, y + 10, 6, 6);
        g.setColor(eye);
        g.fillOval(x + 10, y + 12, 3, 3);
        g.fillOval(x + 20, y + 12, 3, 3);
        // Draw accessory preview
        Color savedBody = bodyColor; Color savedEye = eyeColor;
        String savedAcc = accessory;
        bodyColor = body; eyeColor = eye; accessory = acc;
        drawAccessory(g, x, y);
        bodyColor = savedBody; eyeColor = savedEye; accessory = savedAcc;
    }

    private JButton makeDialogBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Serif", Font.BOLD, 13));
        btn.setBackground(new Color(192, 152, 78));
        btn.setForeground(new Color(42, 12, 0));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(80, 38, 2), 2));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}