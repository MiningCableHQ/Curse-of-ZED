package Combat;

import Main.GamePanel;

import javax.swing.*;
import java.awt.*;

public class BattlePanel{
    private JFrame frame;

    public BattlePanel() {
        initialize();
    }

    public void initialize(){
        frame = new JFrame();
        GamePanel gp = new GamePanel();
        this.frame.setTitle("Curse of ZED");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(gp.screenWidth, gp.screenHeight);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setBackground(Color.green);

        Button button = new Button("Fight");
        panel.add(button);

        frame.add(panel, BorderLayout.CENTER);
    }
}