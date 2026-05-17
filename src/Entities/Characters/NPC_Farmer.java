package Entities.Characters;

import Dialogue.*;
import Main.GamePanel;
import javax.imageio.ImageIO;

public class NPC_Farmer extends NPC {

    public NPC_Farmer(GamePanel gp) {
        super(gp);
        npcName = "Farmer";
        try {
            idleFrames[0] = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/npc/farmer/farmer_idle1.png"));
            idleFrames[1] = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/npc/farmer/farmer_idle2.png"));
            image = idleFrames[0];
        } catch (Exception e) {
            System.err.println("Farmer sprite not found.");
        }
        portrait = loadPortrait("/npc/farmer/farmer_portrait.png");
    }

    @Override
    public DialogueTree getDialogue(String playerClassName) {
        String khaiTitle = "Khai the " + playerClassName;
        DialogueTree tree = new DialogueTree();

        tree.addPage(new DialogueTree.Page("Farmer",
                "I'm no warrior. But this much, I can give.", false));

        tree.addPage(new DialogueTree.Page(khaiTitle, "", true)
                .addChoice("I appreciate your generosity!", -1)
                .addChoice("This is more than I could ever ask for!", -1));

        return tree;
    }

    public java.awt.image.BufferedImage getPortrait() { return portrait; }
}