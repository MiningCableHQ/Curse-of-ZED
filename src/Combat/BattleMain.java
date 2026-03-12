package Combat;

import Main.GamePanel;
import javax.swing.*;

public class BattleMain {
    static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BattlePanel bp = new BattlePanel();
            }
        });
    }
}
