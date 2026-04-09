package Entities.Characters;

import Main.GamePanel;
import javax.imageio.ImageIO;
import Dialogue.*;

public class NPC_Ranger extends NPC {

    public NPC_Ranger(GamePanel gp) {
        super(gp);
        npcName = "Ranger";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/npc/ranger/ranger_idle.png"));
        } catch (Exception e) {
            System.err.println("Ranger sprite not found.");
        }
        portrait = loadPortrait("/npc/ranger/ranger_portrait.png");
    }

    @Override
    public DialogueTree getDialogue(String playerClassName) {
        String khaiTitle = "Khai the " + playerClassName;
        DialogueTree tree = new DialogueTree();

        // Page 0
        tree.addPage(new DialogueTree.Page("Ranger",
                "The chief told us you'd be coming.", false));

        // Page 1 — player choice
        tree.addPage(new DialogueTree.Page(khaiTitle, "", true)
                .addChoice("No he didn't. Good bye.", -1)            // -1 = close dialogue
                .addChoice("Really? He told me to grab something from you.", 2)); // go to page 2

        // Page 2
        tree.addPage(new DialogueTree.Page("Ranger",
                "Everything's been prepared.", false));

        // Page 3
        tree.addPage(new DialogueTree.Page("Ranger",
                "Your mother used to say a good armament is nothing without the will behind it. " +
                        "Don't forget that out there. The sorcerer will try to break more than your body.", false));

        // Page 4 — player choice, both options close dialogue
        tree.addPage(new DialogueTree.Page(khaiTitle, "", true)
                .addChoice("Thank you so much!", -1)
                .addChoice("Nah, I'd win.", -1));

        return tree;
    }

    public java.awt.image.BufferedImage getPortrait() { return portrait; }
}