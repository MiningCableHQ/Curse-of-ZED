package Main;

import javax.swing.*;
import java.awt.*;

public class Game {
    static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame();

            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("Curse of ZED");

            TitlePanel titlePanel = new TitlePanel();

            titlePanel.setOnStartCallback(() -> {
                // Create GamePanel only when needed
                GamePanel gamePanel = new GamePanel();

                window.remove(titlePanel);
                window.add(gamePanel);
                window.revalidate();
                window.repaint();
                window.pack();
                window.setLocationRelativeTo(null);

                // Start game thread AFTER UI is updated
                SwingUtilities.invokeLater(() -> {
                    gamePanel.requestFocusInWindow();
                    gamePanel.startGameThread();
                });
            });

            window.add(titlePanel);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}