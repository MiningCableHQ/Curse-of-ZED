package Entities.Characters;

import Dialogue.*;
import Main.GamePanel;
import Main.GameStateManager;
import javax.imageio.ImageIO;

public class NPC_Healer extends NPC {

    public NPC_Healer(GamePanel gp) {
        super(gp);
        npcName = "Healer";
        try {
            idleFrames[0] = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/npc/healer/healer_idle1.png"));
            idleFrames[1] = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/npc/healer/healer_idle2.png"));
            image = idleFrames[0];
        } catch (Exception e) {
            System.err.println("Healer sprite not found.");
        }
        portrait = loadPortrait("/npc/healer/healer_portrait.png");
    }

    @Override
    public DialogueTree getDialogue(String playerClassName) {
        String khaiTitle = "Khai the " + playerClassName;
        DialogueTree tree = new DialogueTree();

        tree.addPage(new DialogueTree.Page("Healer",
                "Your mother healed my land when the blight came. "
                        + "Take what I have.", false));

        // Player choice after receiving essence
        tree.addPage(new DialogueTree.Page(khaiTitle, "", true)
                .addChoice("Thank you so much!", -1)
                .addChoice("This will help me a lot!", -1));

        return tree;
    }

    public java.awt.image.BufferedImage getPortrait() { return portrait; }
}