package Entities.Characters;

import Main.GamePanel;
import javax.imageio.ImageIO;
import Dialogue.*;
public class NPC_Chief extends NPC {

    public NPC_Chief(GamePanel gp) {
        super(gp);
        npcName = "Chief";
        // Load your NPC sprite image here
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/npc/chief/chief_idle.png"));
        } catch (Exception e) {
            System.err.println("Chief sprite not found.");
        }
        // Load portrait (250x250 for dialogue box)
        portrait = loadPortrait("/npc/chief/chief_portrait.png");
    }

    @Override
    public DialogueTree getDialogue(String playerClassName) {
        // playerClassName will be "Mage", "Swordsman", or "Archer"
        String khaiTitle = "Khai the " + playerClassName;

        DialogueTree tree = new DialogueTree();

        // Page 0
        tree.addPage(new DialogueTree.Page("Chief",
                "You have your father's eyes, boy. And your mother's restless spirit.", false));

        // Page 1
        tree.addPage(new DialogueTree.Page("Chief",
                "I won't pretend the barrier will hold much longer. The sorcerer's power grows by the day.", false));

        // Page 2 — player line
        tree.addPage(new DialogueTree.Page(khaiTitle,
                "Then give me permission to go. I won't sit here while the forest dies around us.", true));

        // Page 3
        tree.addPage(new DialogueTree.Page("Chief",
                "Spoken like your parents. I suppose that shouldn't surprise me.", false));

        // Page 4
        tree.addPage(new DialogueTree.Page("Chief",
                "Very well. Go to the rangers' keep. Collect your equipment.", false));

        // Page 5 — last line, closes on Next
        tree.addPage(new DialogueTree.Page("Chief",
                "And Khai — come back alive. That is an order.", false));

        return tree;
    }

    public java.awt.image.BufferedImage getPortrait() { return portrait; }
}