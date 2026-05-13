package Main;


import Entities.Characters.Player;
import StoryLine.StoryPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.font.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Curse of Zed — Title Screen
 * Java Swing  |  Ringbearer Medium  |  Vintage RPG
 *
 * Place "RingbearerMedium.ttf" and "background.jpg"
 * in the project root folder (next to src/).
 * Font: https://www.dafont.com/ringbearer.font
 */
public class CurseOfZed extends JFrame {

    private Runnable onStartCallback;
    private Player selectedPlayer;
    private StoryPanel storyPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Initialize the window
            CurseOfZed window = new CurseOfZed();

            // 2. Set the callback to show character selection
            window.setOnStartCallback(() -> {
                window.showStoryIntro();
                // window.showCharacterSelection();
            });

            window.setVisible(true);
        });
    }

    public void setOnStartCallback(Runnable callback) {
        this.onStartCallback = callback;
    }

    public CurseOfZed() {
        setTitle("Curse of Zed");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setResizable(false);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1024, 768));

        TitlePanel p = new TitlePanel();
        p.setOnStartCallback(() -> {
            if (this.onStartCallback != null) {
                this.onStartCallback.run();
            }
        });
        add(p);
        pack();
        setLocationRelativeTo(null);
    }

    public void showStoryIntro() {
        // We save the storyPanel to the class variable we just created
        this.storyPanel = new StoryPanel(() -> {
            showCharacterSelection();
        });

        getContentPane().removeAll();
        add(storyPanel);
        revalidate();
        repaint();
    }

    /**
     * Shows the character selection panel by replacing the TitlePanel
     * in the same JFrame window.
     */
    public void showCharacterSelection() {
        CharacterSelectionPanel selectionPanel = new CharacterSelectionPanel(this);

        // Tell the selection panel to reset the story when BACK is pressed
        selectionPanel.setOnBackPressed(() -> {
            if (storyPanel != null) {
                storyPanel.resetToBeginning(); // This triggers the animation

                getContentPane().removeAll();
                add(storyPanel);
                revalidate();
                repaint();
            }
        });

        selectionPanel.setOnCharacterSelected(player -> {
            this.selectedPlayer = player;
            startGameWithPlayer(selectedPlayer);
        });

        getContentPane().removeAll();
        add(selectionPanel);
        revalidate();
        repaint();
    }

    /**
     * Starts the game with the selected player character.
     * GamePanel construction and asset loading run off the EDT so the UI
     * stays responsive; a loading screen is shown in the meantime.
     */
    public void startGameWithPlayer(Player player) {
        GameStateManager.reset();
        KeyHandler keyH = new KeyHandler();

        try {
            java.lang.reflect.Field keyHField = player.getClass()
                    .getSuperclass().getDeclaredField("keyH");
            keyHField.setAccessible(true);
            keyHField.set(player, keyH);
        } catch (Exception e) {
            System.err.println("Warning: Could not set KeyHandler: " + e.getMessage());
        }

        // Show loading screen immediately so the UI doesn't appear frozen.
        getContentPane().removeAll();
        add(createLoadingPanel());
        revalidate();
        repaint();

        SwingWorker<GamePanel, Void> loader = new SwingWorker<GamePanel, Void>() {
            @Override
            protected GamePanel doInBackground() {
                GamePanel gp = new GamePanel(player, keyH);
                gp.setupGame();
                return gp;
            }

            @Override
            protected void done() {
                try {
                    GamePanel gamePanel = get();
                    getContentPane().removeAll();
                    add(gamePanel);
                    revalidate();
                    repaint();
                    pack();
                    setLocationRelativeTo(null);
                    gamePanel.requestFocusInWindow();
                    gamePanel.startGameThread();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        loader.execute();
    }

    private javax.swing.JPanel createLoadingPanel() {
        return new javax.swing.JPanel() {
            private java.awt.image.BufferedImage loadingBg;

            {
                setBackground(new Color(10, 5, 20));
                // Load once when the panel is created
                try {
                    loadingBg = javax.imageio.ImageIO.read(
                            getClass().getResourceAsStream("/backgrounds/loading_bg.png"));
                } catch (Exception ex) {
                    loadingBg = null;
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (loadingBg != null) {
                    double ia = (double) loadingBg.getWidth() / loadingBg.getHeight();
                    double pa = (double) getWidth() / getHeight();
                    int bw, bh;
                    if (ia > pa) { bh = getHeight(); bw = (int)(getHeight() * ia); }
                    else         { bw = getWidth();  bh = (int)(getWidth()  / ia); }
                    g2.drawImage(loadingBg,
                            (getWidth()  - bw) / 2,
                            (getHeight() - bh) / 2,
                            bw, bh, null);
                } else {
                    g2.setColor(new Color(10, 5, 20));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }

                // Semi-transparent overlay so text stays readable
                g2.setColor(new Color(0, 0, 0, 100));
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(new Color(252, 218, 72));
                g2.setFont(new Font("Serif", Font.BOLD, 32));
                FontMetrics fm = g2.getFontMetrics();
                String text = "Loading...";
                g2.drawString(text,
                        (getWidth()  - fm.stringWidth(text)) / 2,
                        getHeight() / 2 + fm.getAscent() / 2);
                g2.dispose();
            }
        };
    }
}


