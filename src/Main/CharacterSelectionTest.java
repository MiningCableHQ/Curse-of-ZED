package Main;

import javax.swing.*;

public class CharacterSelectionTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CharacterSelectionPanel panel = new CharacterSelectionPanel();

            panel.setOnCharacterSelected(player -> {
                System.out.println("Selected: " + player.getName());
                System.out.printf("  HP: %.0f  ATK: %.0f  DEF: %.0f  SPD: %.0f%n",
                        player.getMaxHp(), player.getMaxAttack(),
                        player.getMaxDefense(), player.getSpeed());
            });

            JFrame frame = new JFrame("Character Selection — Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}