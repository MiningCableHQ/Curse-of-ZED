package Main;

import Entities.Characters.Mage;
import Entities.Characters.Player;
import Entities.Characters.Ranger;
import Entities.Characters.Swordsman;
import Entities.Entity;
import Main.KeyHandler;

import javax.swing.*;
import java.awt.*;

/**
 * Test class for ShopPanel visual layout.
 * Creates a JFrame with a ShopPanel displaying player and shopkeeper.
 */
public class ShopMain {

    public static void main(String[] args) {
        // Set Swing look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        // Create parent frame
        JFrame frame = new JFrame("Shop Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        GamePanel gp = new GamePanel();

        // Create a dummy player (Swordsman)
        Player player = new Mage(gp, new KeyHandler());
        player.setDefaultValues();

        // Create a dummy shopkeeper (simple Entity extension)
        Entity shopkeeper = new Ranger(gp, new KeyHandler());

        // Create the shop panel
        ShopPanel shopPanel = new ShopPanel(frame, player, shopkeeper, () -> {
            System.out.println("Back pressed - closing shop");
            frame.dispose();
        });

        // Add panel to frame
        frame.add(shopPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Request focus for keyboard input
        shopPanel.requestPanelFocus();
    }
}