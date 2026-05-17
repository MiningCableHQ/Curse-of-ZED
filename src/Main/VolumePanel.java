package Main;

import Audio.Audio;
import Audio.SFX.ClickSFX;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class VolumePanel extends JPanel {

    private static final int WIDTH = 350;
    private static final int HEIGHT = 220;

    private static final Color PARCH_TOP = new Color(148, 108, 44);
    private static final Color PARCH_TAN = new Color(192, 152, 78);
    private static final Color PARCH_WARM = new Color(220, 186, 118);
    private static final Color PARCH_CENTRE = new Color(238, 212, 152);
    private static final Color PARCH_BORDER = new Color(80, 38, 2, 230);
    private static final Color TEXT_GOLD = new Color(252, 218, 72);
    private static final Color BTN_GOLD_NORMAL = new Color(238, 190, 28);
    private static final Color BTN_GOLD_HOVER = new Color(250, 222, 62);
    private static final Color BTN_GOLD_PRESS = new Color(178, 108, 0);

    private static final Font FONT_TITLE = new Font("Serif", Font.BOLD, 20);
    private static final Font FONT_BTN = new Font("Serif", Font.BOLD, 14);

    private Audio audioObject;
    private float currentVolume;
    private JSlider volumeSlider;
    private JLabel volumePercentLabel;

    private final GamePanel gp;
    private Runnable onClose;
    private AtomicBoolean isClosing = new AtomicBoolean(false);

    public VolumePanel(GamePanel gp, Audio audioObject, Runnable onClose) {
        this.gp = gp;
        this.audioObject = audioObject;
        this.onClose = onClose;
        this.currentVolume = 0.45f;  // Changed from 0.5f to 0.6f for better default

        setLayout(null);
        setOpaque(true);
        setFocusable(true);
        setSize(WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        initUI();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    e.consume();
                    closePanel();
                }
            }
        });
    }

    private void initUI() {
        JLabel titleLabel = new JLabel("Volume Control");
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(TEXT_GOLD);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(75, 15, 200, 30);
        add(titleLabel);

        volumeSlider = new JSlider(0, 100, (int)(currentVolume * 100));
        volumeSlider.setBounds(35, 65, 280, 40);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setBackground(new Color(60, 40, 20));
        volumeSlider.setForeground(TEXT_GOLD);

        volumeSlider.setUI(new BasicSliderUI(volumeSlider) {
            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(80, 50, 25));
                g2.fillRect(trackRect.x, trackRect.y + trackRect.height/3,
                        trackRect.width, trackRect.height/3);
            }

            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(BTN_GOLD_NORMAL);
                g2.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
                g2.setColor(PARCH_BORDER);
                g2.drawOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
            }
        });

        volumeSlider.addChangeListener(e -> {
            int value = volumeSlider.getValue();
            currentVolume = value / 100.0f;
            volumePercentLabel.setText(value + "%");

            if (audioObject != null) {
                audioObject.setVolume(currentVolume);
            }
        });
        add(volumeSlider);

        volumePercentLabel = new JLabel((int)(currentVolume * 100) + "%");
        volumePercentLabel.setFont(FONT_BTN);
        volumePercentLabel.setForeground(TEXT_GOLD);
        volumePercentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        volumePercentLabel.setBounds(135, 115, 80, 25);
        add(volumePercentLabel);

        GoldButton closeButton = new GoldButton("Close");
        closeButton.setBounds(125, 155, 100, 35);
        closeButton.addActionListener(e -> { gp.getSFXPlayer().playSFX(new ClickSFX()); closePanel(); });
        add(closeButton);
    }

    private void closePanel() {
        if (isClosing.compareAndSet(false, true)) {
            SwingUtilities.invokeLater(() -> {
                if (onClose != null) {
                    onClose.run();
                }
                isClosing.set(false);
            });
        }
    }

    public float getVolume() {
        return currentVolume;
    }

    public void setVolume(float volume) {
        this.currentVolume = Math.max(0f, Math.min(1f, volume));
        if (volumeSlider != null) {
            volumeSlider.setValue((int)(currentVolume * 100));
        }
        if (audioObject != null) {
            audioObject.setVolume(currentVolume);
        }
    }

    public void requestPanelFocus() {
        requestFocusInWindow();
    }

    private class GoldButton extends JButton {
        private boolean hovered = false;
        private boolean pressed = false;

        GoldButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFont(FONT_BTN);
            setForeground(new Color(42, 12, 0));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (isEnabled()) {
                        hovered = true;
                        repaint();
                    }
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    hovered = false;
                    repaint();
                }
                @Override
                public void mousePressed(MouseEvent e) {
                    if (isEnabled()) {
                        pressed = true;
                        repaint();
                    }
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    pressed = false;
                    repaint();
                }
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
            } else {
                Color fill = pressed ? BTN_GOLD_PRESS : hovered ? BTN_GOLD_HOVER : BTN_GOLD_NORMAL;
                g2.setColor(fill);
                g2.fill(oct);
                if (hovered && !pressed) {
                    g2.setColor(new Color(255, 255, 255, 40));
                    Polygon topOct = makeOctagon(0, 0, w, h / 2, c);
                    g2.fill(topOct);
                }
            }
            g2.setColor(PARCH_BORDER);
            g2.setStroke(new BasicStroke(2.0f));
            g2.draw(oct);

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(new LinearGradientPaint(0, 0, 0, getHeight(),
                new float[]{0f, 0.08f, 0.22f, 0.50f, 0.78f, 0.92f, 1f},
                new Color[]{
                        PARCH_TOP, PARCH_TAN, PARCH_WARM, PARCH_CENTRE,
                        PARCH_WARM, PARCH_TAN, PARCH_TOP
                }));
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(PARCH_BORDER);
        g2.setStroke(new BasicStroke(3.0f));
        g2.drawRect(5, 5, getWidth() - 10, getHeight() - 10);

        g2.dispose();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}