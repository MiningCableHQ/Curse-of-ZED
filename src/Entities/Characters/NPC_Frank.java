package Entities.Characters;

import Dialogue.*;
import Main.GamePanel;
import javax.imageio.ImageIO;

public class NPC_Frank extends NPC {

    public NPC_Frank(GamePanel gp) {
        super(gp);
        npcName = "Frank";
        try {
            idleFrames[0] = ImageIO.read(getClass().getResourceAsStream("/npc/frank/frank_idle1.png"));
            idleFrames[1] = ImageIO.read(getClass().getResourceAsStream("/npc/frank/frank_idle2.png"));
            image = idleFrames[0];
        } catch (Exception e) {
            System.err.println("Frank sprite not found.");
        }
        portrait = loadPortrait("/npc/frank/frank_portrait.png");
    }

    @Override
    public DialogueTree getDialogue(String playerClassName) {
        String khaiTitle = "Khai the " + playerClassName;
        DialogueTree tree = new DialogueTree();

        // Page 0
        tree.addPage(new DialogueTree.Page("Frank",
                "Hohoho, what a good day young lad! What would you like? Hohoho~", false));

        // Page 1 — player choices
        tree.addPage(new DialogueTree.Page(khaiTitle, "", true)
                .addChoice("Nothing.", -1)
                .addShopChoice("Shop Items")); // -2 triggers shop UI

        return tree;
    }

    public java.awt.image.BufferedImage getPortrait() { return portrait; }
}