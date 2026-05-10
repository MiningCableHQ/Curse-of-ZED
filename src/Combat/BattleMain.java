


package Combat;

import Entities.Characters.*;
import Entities.Characters.Player;
import Entities.Enemies.*;
import Main.GamePanel;
import Main.KeyHandler;

import javax.swing.*;

/**
 * BattleMain — standalone entry point for testing BattlePanel.
 *
 * Run this class directly to open the battle screen with a
 * default Swordsman vs. Masklet encounter.
 */
public class BattleMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // ── Create dummy entities for testing ──────────────
            GamePanel  gp   = new GamePanel();
            KeyHandler keyH = new KeyHandler();

            Player testPlayer = new Ranger(gp, keyH);

            // ── Build the battle panel ──────────────────────────
            BattlePanel battlePanel = new BattlePanel(testPlayer, new Frankenstein(), 1);

            // React when the player escapes (optional callback)
            battlePanel.setOnBattleEnd(() ->
                    System.out.println("[BattleMain] Battle ended — would return to exploration.")
            );

            // ── Wrap in a JFrame ────────────────────────────────
            JFrame frame = new JFrame("Curse of ZED — Battle Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(battlePanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}