package Main;

import Entities.Characters.*;
import Entities.Entity;
import Entities.Enemies.Enemy;
import Items.Item;
import Items.Weapons.Weapon;
import Moves.Move;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;

public class InventoryPanel extends JPanel {

    // ── Screen Dimensions ─────────────────────────────────────────
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    // ── Palette ───────────────────────────────────────────────────
    private static final Color BG_COLOR = new Color(160, 120, 80);
    private static final Color PARCH_TOP = new Color(148, 108, 44);
    private static final Color PARCH_TAN = new Color(192, 152, 78);
    private static final Color PARCH_WARM = new Color(220, 186, 118);
    private static final Color PARCH_CENTRE = new Color(238, 212, 152);
    private static final Color PARCH_BORDER = new Color(80, 38, 2, 230);
    private static final Color TEXT_DARK = new Color(60, 30, 5);
    private static final Color TEXT_GOLD = new Color(252, 218, 72);
    private static final Color HOVER_GOLD = new Color(252, 218, 72);
    private static final Color GOLD_DARK = new Color(178, 108, 0);

    // ── Stat Colors ────────────────────────────────────────────────
    private static final Color STAT_HP = new Color(220, 80, 80);
    private static final Color STAT_ATK = new Color(255, 180, 80);
    private static final Color STAT_DEF = new Color(80, 150, 255);
    private static final Color STAT_SPD = new Color(80, 200, 80);
    private static final Color STAT_ACC = new Color(255, 220, 80);
    private static final Color STAT_EXP = new Color(100, 200, 255);
    private static final Color STAT_VALUE = Color.WHITE;

    // ── Gold Octagon Button Colors ─────────────────────────────────
    private static final Color BTN_GOLD_NORMAL = new Color(238, 190, 28);
    private static final Color BTN_GOLD_HOVER = new Color(250, 222, 62);
    private static final Color BTN_GOLD_PRESS = new Color(178, 108, 0);
    private static final Color BTN_BORDER_CLR = new Color(82, 38, 0, 215);
    private static final Color BTN_TEXT_DARK = new Color(42, 12, 0);

    // ── Fonts ─────────────────────────────────────────────────────
    private static final Font FONT_TITLE = new Font("Serif", Font.BOLD, 18);
    private static final Font FONT_STAT = new Font("Monospaced", Font.BOLD, 13);
    private static final Font FONT_BTN = new Font("Serif", Font.BOLD, 16);
    private static final Font FONT_NAME = new Font("Serif", Font.BOLD, 20);
    private static final Font FONT_ITEM = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font FONT_DIALOG = new Font("Serif", Font.BOLD, 14);

    // ── Layout Constants ───────────────────────────────────────────
    private static final int CHAR_PANEL_X = 20;
    private static final int CHAR_PANEL_Y = 80;
    private static final int CHAR_PANEL_W = 300;
    private static final int CHAR_PANEL_H = 480;

    private static final int INV_PANEL_X = 340;
    private static final int INV_PANEL_Y = 80;
    private static final int INV_PANEL_W = 664;
    private static final int INV_PANEL_H = 480;

    private static final int MSG_PANEL_X = 20;
    private static final int MSG_PANEL_Y = 580;
    private static final int MSG_PANEL_W = WIDTH - 40;
    private static final int MSG_PANEL_H = 100;

    private static final int BACK_BTN_X = WIDTH - 160;
    private static final int BACK_BTN_Y = HEIGHT - 70;
    private static final int BACK_BTN_W = 140;
    private static final int BACK_BTN_H = 50;

    // ── Entity References ─────────────────────────────────────────
    private final JFrame parentFrame;
    private final Player player;
    private final boolean fromCombat;
    private final BiConsumer<Item, Enemy> onItemSelected;
    private final Runnable onBackPressed;
    private Combat.Battle battle;

    // ── UI Components ─────────────────────────────────────────────
    private JPanel characterPanel;
    private JPanel inventoryPanel;
    private JPanel messagePanel;
    private GoldButton backButton;

    // ── Player Animation ──────────────────────────────────────────
    private BufferedImage[] playerIdleFrames = new BufferedImage[5];
    private int currentPlayerFrame = 0;
    private Timer animationTimer;
    private JLabel playerImageLabel;

    // ── Inventory Grid ────────────────────────────────────────────
    private JPanel inventoryGrid;
    private JScrollPane inventoryScrollPane;
    private ArrayList<ItemSlot> itemSlots = new ArrayList<>();
    private JLabel messageLabel;

    // ── Background Image ──────────────────────────────────────────
    private BufferedImage backgroundImage;

    // ─────────────────────────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────────────────────────
    public InventoryPanel(JFrame parentFrame, Player player, boolean fromCombat,
                          BiConsumer<Item, Enemy> onItemSelected, Runnable onBackPressed) {
        this.parentFrame = parentFrame;
        this.player = player;
        this.fromCombat = fromCombat;
        this.onItemSelected = onItemSelected;
        this.onBackPressed = onBackPressed;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        setOpaque(true);

        loadBackground();
        loadPlayerAnimations();
        initUI();
        startAnimationTimer();

        refreshInventory();
    }

    public void setBattle(Combat.Battle battle) {
        this.battle = battle;
    }

    // ── Helper Methods ─────────────────────────────────────────────
    private void showParchmentDialog(String title, String message, String option1, String option2, Runnable onConfirm) {
        JDialog dialog = new JDialog(parentFrame, title, true);
        dialog.setUndecorated(true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(parentFrame);

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setPaint(new LinearGradientPaint(0, 0, 0, getHeight(),
                        new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                        new Color[]{
                                new Color(148, 108, 44), new Color(192, 152, 78),
                                new Color(220, 186, 118), new Color(238, 212, 152),
                                new Color(220, 186, 118), new Color(192, 152, 78),
                                new Color(148, 108, 44)
                        }));
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(TEXT_GOLD);
                g2.setStroke(new BasicStroke(3.0f));
                g2.drawRect(5, 5, getWidth() - 10, getHeight() - 10);

                g2.dispose();
            }
        };
        contentPane.setLayout(null);
        contentPane.setOpaque(false);
        dialog.setContentPane(contentPane);

        JLabel messageLabel = new JLabel("<html><center>" + message + "</center></html>");
        messageLabel.setFont(FONT_DIALOG);
        messageLabel.setForeground(TEXT_DARK);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(20, 30, 360, 80);
        contentPane.add(messageLabel);

        GoldButton btn1 = new GoldButton(option1);
        btn1.setBounds(option2 == null ? 150 : 80, 130, option2 == null ? 100 : 100, 40);
        btn1.addActionListener(e -> {
            dialog.dispose();
            if (onConfirm != null) onConfirm.run();
        });
        contentPane.add(btn1);

        if (option2 != null) {
            GoldButton btn2 = new GoldButton(option2);
            btn2.setBounds(220, 130, 100, 40);
            btn2.addActionListener(e -> dialog.dispose());
            contentPane.add(btn2);
        }

        dialog.setVisible(true);
    }

    private boolean isDebuffItem(Item item) {
        String name = item.getName();
        return name.contains("Dulling") || name.contains("Softening") ||
                name.contains("Clumsiness") || name.contains("Blinding");
    }

    // ── Setup Methods ─────────────────────────────────────────────
    private void loadBackground() {
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/ui/inventory_bg.png"));
        } catch (Exception e) {
            backgroundImage = null;
        }
    }

    private void loadPlayerAnimations() {
        String className = getCharacterClassName();
        boolean anyFrameLoaded = false;

        for (int i = 0; i < 5; i++) {
            try {
                playerIdleFrames[i] = ImageIO.read(getClass().getResourceAsStream("/" + className + "/" + className + "_idle/idle_right" + (i + 1) + ".png"));
                if (playerIdleFrames[i] != null) anyFrameLoaded = true;
            } catch (Exception e) {
                playerIdleFrames[i] = null;
            }
        }

        if (!anyFrameLoaded) {
            createPlaceholderFrames();
        }
    }

    private String getCharacterClassName() {
        if (player instanceof Swordsman) return "swordsman";
        if (player instanceof Ranger) return "archer";
        if (player instanceof Mage) return "mage";
        return "swordsman";
    }

    private void createPlaceholderFrames() {
        Color baseColor;
        String initial;

        if (player instanceof Swordsman) {
            baseColor = new Color(200, 80, 80);
            initial = "S";
        } else if (player instanceof Ranger) {
            baseColor = new Color(80, 180, 100);
            initial = "R";
        } else {
            baseColor = new Color(80, 120, 220);
            initial = "M";
        }

        for (int i = 0; i < 5; i++) {
            float brightness = 0.7f + (i * 0.06f);
            Color frameColor = new Color(
                    Math.min(255, (int) (baseColor.getRed() * brightness)),
                    Math.min(255, (int) (baseColor.getGreen() * brightness)),
                    Math.min(255, (int) (baseColor.getBlue() * brightness))
            );
            playerIdleFrames[i] = createPlaceholderImage(frameColor, initial);
        }
    }

    private BufferedImage createPlaceholderImage(Color color, String initial) {
        int w = 150, h = 150;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        g2.setColor(color);
        g2.fillRect(0, 0, w, h);

        Font bigFont = new Font("Serif", Font.BOLD, 60);
        g2.setFont(bigFont);
        FontMetrics fm = g2.getFontMetrics();
        int lx = (w - fm.stringWidth(initial)) / 2;
        int ly = (h + fm.getAscent() - fm.getDescent()) / 2;

        g2.setColor(new Color(0, 0, 0, 80));
        g2.drawString(initial, lx + 2, ly + 2);
        g2.setColor(Color.WHITE);
        g2.drawString(initial, lx, ly);

        g2.dispose();
        return img;
    }

    private void startAnimationTimer() {
        if (animationTimer != null) {
            animationTimer.cancel();
        }
        animationTimer = new Timer();
        animationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentPlayerFrame = (currentPlayerFrame + 1) % 5;
                SwingUtilities.invokeLater(() -> {
                    if (playerImageLabel != null && playerIdleFrames[currentPlayerFrame] != null) {
                        Image scaled = playerIdleFrames[currentPlayerFrame].getScaledInstance(150, 150, Image.SCALE_FAST);
                        playerImageLabel.setIcon(new ImageIcon(scaled));
                    }
                    repaint();
                });
            }
        }, 0, 120);
    }

    private void initUI() {
        characterPanel = createParchmentPanel(CHAR_PANEL_X, CHAR_PANEL_Y, CHAR_PANEL_W, CHAR_PANEL_H);
        add(characterPanel);
        setupCharacterPanel();

        inventoryPanel = createParchmentPanel(INV_PANEL_X, INV_PANEL_Y, INV_PANEL_W, INV_PANEL_H);
        add(inventoryPanel);
        setupInventoryPanel();

        messagePanel = createParchmentPanel(MSG_PANEL_X, MSG_PANEL_Y, MSG_PANEL_W, MSG_PANEL_H);
        add(messagePanel);
        setupMessagePanel();

        backButton = new GoldButton("← Back");
        backButton.setBounds(BACK_BTN_X, BACK_BTN_Y, BACK_BTN_W, BACK_BTN_H);
        backButton.addActionListener(e -> {
            if (animationTimer != null) {
                animationTimer.cancel();
                animationTimer = null;
            }
            if (onBackPressed != null) {
                onBackPressed.run();
            }
        });
        add(backButton);
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
                    g2.fill(new RoundRectangle2D.Float(sx - i, sy + i * 2, sw + i * 2, sh, 18, 18));
                }
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

                g2.setPaint(new LinearGradientPaint(sx, sy, sx, sy + sh,
                        new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                        new Color[]{
                                new Color(148, 108, 44), new Color(192, 152, 78),
                                new Color(220, 186, 118), new Color(238, 212, 152),
                                new Color(220, 186, 118), new Color(192, 152, 78),
                                new Color(148, 108, 44)
                        }));
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

    private void setupCharacterPanel() {
        playerImageLabel = new JLabel();
        if (playerIdleFrames[0] != null) {
            Image scaled = playerIdleFrames[0].getScaledInstance(150, 150, Image.SCALE_FAST);
            playerImageLabel.setIcon(new ImageIcon(scaled));
        }
        playerImageLabel.setBounds(75, 20, 150, 150);
        playerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        characterPanel.add(playerImageLabel);

        JPanel statsBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 150));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.setColor(TEXT_GOLD);
                g2.setStroke(new BasicStroke(2.0f));
                g2.draw(new RoundRectangle2D.Float(2, 2, getWidth() - 4, getHeight() - 4, 10, 10));
                g2.dispose();
            }
        };
        statsBox.setLayout(null);
        statsBox.setOpaque(false);
        statsBox.setBounds(20, 180, 260, 160);
        characterPanel.add(statsBox);

        int lineHeight = 23;
        int startY = 12;

        addStatLine(statsBox, "HP:", (int) player.getHp() + " / " + (int) player.getMaxHp(), STAT_HP, 10, startY);
        addStatLine(statsBox, "ATK:", (int) player.getAttack() + " / " + (int) player.getMaxAttack(), STAT_ATK, 10, startY + lineHeight);
        addStatLine(statsBox, "DEF:", (int) player.getDefense() + " / " + (int) player.getMaxDefense(), STAT_DEF, 10, startY + lineHeight * 2);
        addStatLine(statsBox, "SPD:", String.valueOf((int) player.getSpeed()), STAT_SPD, 10, startY + lineHeight * 3);
        addStatLine(statsBox, "ACC:", String.format("%.0f", player.getAccuracy() * 100) + "%", STAT_ACC, 10, startY + lineHeight * 4);
        addStatLine(statsBox, "EXP:", player.getExperience() + " / " + player.getExpNeeded(), STAT_EXP, 10, startY + lineHeight * 5);

        ArrayList<Move> moves = player.getMoves();
        int btnW = 135, btnH = 40;
        int gapX = 140, gapY = 52;
        int startYButtons = 350;
        for (int i = 0; i < moves.size() && i < 4; i++) {
            final Move move = moves.get(i);
            GoldButton moveBtn = new GoldButton(move.getName());
            int row = i / 2;
            int col = i % 2;
            int x = 12 + col * gapX;
            int y = startYButtons + row * gapY;
            moveBtn.setBounds(x, y, btnW, btnH);
            moveBtn.addActionListener(e -> {
                if (!fromCombat) {
                    messageLabel.setText(move.getName() + ": " + move.getDescription());
                }
            });
            moveBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    messageLabel.setText(move.getName() + ": " + move.getDescription());
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    messageLabel.setText("Hover over an item or move for info");
                }
            });
            characterPanel.add(moveBtn);
        }
    }

    private void addStatLine(JPanel panel, String label, String value, Color labelColor, int x, int y) {
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(FONT_STAT);
        labelComp.setForeground(labelColor);
        labelComp.setBounds(x, y, 55, 20);
        panel.add(labelComp);

        JLabel valueComp = new JLabel(value);
        valueComp.setFont(FONT_STAT);
        valueComp.setForeground(STAT_VALUE);
        valueComp.setBounds(x + 60, y, 185, 20);
        panel.add(valueComp);
    }

    private void setupInventoryPanel() {
        inventoryGrid = new JPanel();
        inventoryGrid.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        inventoryGrid.setOpaque(false);

        inventoryScrollPane = new JScrollPane(inventoryGrid);
        inventoryScrollPane.setOpaque(false);
        inventoryScrollPane.getViewport().setOpaque(false);
        inventoryScrollPane.setBorder(BorderFactory.createEmptyBorder());
        inventoryScrollPane.setBounds(20, 20, INV_PANEL_W - 40, INV_PANEL_H - 40);
        inventoryScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        inventoryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JScrollBar verticalBar = inventoryScrollPane.getVerticalScrollBar();
        verticalBar.setBackground(new Color(60, 40, 20));
        verticalBar.setForeground(new Color(200, 160, 100));

        inventoryPanel.add(inventoryScrollPane);
        refreshInventory();
    }

    public void refreshInventory() {
        if (inventoryGrid == null) return;

        inventoryGrid.removeAll();
        itemSlots.clear();

        ArrayList<Item> items = player.getInventory().getItems();

        if (items.isEmpty()) {
            JLabel emptyLabel = new JLabel("No items in inventory");
            emptyLabel.setFont(FONT_NAME);
            emptyLabel.setForeground(TEXT_GOLD);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setPreferredSize(new Dimension(600, 50));
            inventoryGrid.add(emptyLabel);
        } else {
            for (Item item : items) {
                int quantity = player.getInventory().getQuantity(item);
                if (quantity > 0) {
                    ItemSlot slot = new ItemSlot();
                    slot.setItem(item, quantity);
                    slot.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            slot.setHovered(true);
                            messageLabel.setText(item.getName() + " x" + quantity + " - " + item.getDescription());
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            slot.setHovered(false);
                            messageLabel.setText("Hover over an item or move for info");
                        }

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (fromCombat) {
                                if (item instanceof Weapon) {
                                    showParchmentDialog("Cannot Equip", "Cannot equip or unequip weapons during combat!", "OK", null, null);
                                    return;
                                }

                                boolean isDebuff = isDebuffItem(item);
                                String confirmMessage = isDebuff ?
                                        "Use " + item.getName() + " on an enemy?\n" + item.getDescription() :
                                        "Use " + item.getName() + " on yourself?\n" + item.getDescription();

                                showParchmentDialog("Use Item", confirmMessage, "Yes", "No", () -> {
                                    if (isDebuff) {
                                        // For debuff items, we need to show enemy target selection
                                        // First close inventory, then BattlePanel will show target selection
                                        if (onItemSelected != null) {
                                            onItemSelected.accept(item, null);
                                        }
                                    } else {
                                        // For healing/buff items, target is the player (null indicates self)
                                        if (onItemSelected != null) {
                                            onItemSelected.accept(item, null);
                                        }
                                    }
                                });
                            }
                        }
                    });
                    inventoryGrid.add(slot);
                    itemSlots.add(slot);
                }
            }
        }

        inventoryGrid.revalidate();
        inventoryGrid.repaint();
    }

    private void setupMessagePanel() {
        messageLabel = new JLabel("Hover over an item or move for info");
        messageLabel.setFont(FONT_NAME);
        messageLabel.setForeground(TEXT_DARK);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(20, 25, MSG_PANEL_W - 40, 50);
        messagePanel.add(messageLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);
        } else {
            g2.setColor(BG_COLOR);
            g2.fillRect(0, 0, WIDTH, HEIGHT);
        }

        g2.dispose();
    }

    // ─────────────────────────────────────────────────────────────
    //  Inner Classes
    // ─────────────────────────────────────────────────────────────

    private static class ItemSlot extends JPanel {
        private Item item;
        private int quantity;
        private BufferedImage icon;
        private boolean hovered = false;

        public ItemSlot() {
            setPreferredSize(new Dimension(70, 70));
            setBackground(new Color(60, 40, 20, 180));
            setBorder(BorderFactory.createLineBorder(new Color(80, 50, 20), 2));
        }

        public void setItem(Item item, int qty) {
            this.item = item;
            this.quantity = qty;
            this.icon = item.getImage();
            repaint();
        }

        public Item getItem() { return item; }
        public int getQuantity() { return quantity; }
        public void setHovered(boolean hovered) { this.hovered = hovered; repaint(); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

            if (icon != null) {
                g2.drawImage(icon, 11, 11, 48, 48, null);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Monospaced", Font.BOLD, 12));
                g2.drawString("x" + quantity, 45, 60);
                if (hovered) {
                    g2.setColor(HOVER_GOLD);
                    g2.setStroke(new BasicStroke(3.0f));
                    g2.drawRect(2, 2, getWidth() - 5, getHeight() - 5);
                }
            } else {
                g2.setColor(new Color(100, 70, 40));
                g2.fillRect(19, 19, 32, 32);
                g2.setColor(Color.GRAY);
                g2.setFont(new Font("Serif", Font.BOLD, 20));
                g2.drawString("?", 33, 42);
            }
            g2.dispose();
        }
    }

    private static class GoldButton extends JButton {
        private boolean hovered = false;
        private boolean pressed = false;

        GoldButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFont(FONT_BTN);
            setForeground(BTN_TEXT_DARK);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { if (isEnabled()) { hovered = true; repaint(); } }
                @Override public void mouseExited(MouseEvent e) { hovered = false; repaint(); }
                @Override public void mousePressed(MouseEvent e) { if (isEnabled()) { pressed = true; repaint(); } }
                @Override public void mouseReleased(MouseEvent e) { pressed = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            int c = Math.min(w, h) / 6;
            if (c < 6) c = 6;
            Polygon oct = makeOctagon(0, 0, w, h, c);

            if (!isEnabled()) {
                g2.setColor(new Color(160, 140, 100));
                g2.fill(oct);
                g2.setColor(new Color(100, 80, 50));
                g2.setStroke(new BasicStroke(1.8f));
                g2.draw(oct);
            } else {
                Color fill = pressed ? BTN_GOLD_PRESS : hovered ? BTN_GOLD_HOVER : BTN_GOLD_NORMAL;
                g2.setColor(fill);
                g2.fill(oct);
                if (hovered && !pressed) {
                    g2.setColor(new Color(255, 255, 255, 40));
                    Polygon topOct = makeOctagon(0, 0, w, h / 2, c);
                    g2.fill(topOct);
                }
                g2.setColor(BTN_BORDER_CLR);
                g2.setStroke(new BasicStroke(2.0f));
                g2.draw(oct);
            }

            FontMetrics fm = g2.getFontMetrics(getFont());
            int tx = (w - fm.stringWidth(getText())) / 2;
            int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
            g2.setFont(getFont());
            g2.setColor(new Color(0, 0, 0, 100));
            g2.drawString(getText(), tx + 1, ty + 1);
            g2.setColor(getForeground());
            g2.drawString(getText(), tx, ty);
            g2.dispose();
        }

        private Polygon makeOctagon(int x, int y, int w, int h, int c) {
            return new Polygon(
                    new int[]{x + c, x + w - c, x + w, x + w, x + w - c, x + c, x, x},
                    new int[]{y, y, y + c, y + h - c, y + h, y + h, y + h - c, y + c}, 8);
        }
    }
}