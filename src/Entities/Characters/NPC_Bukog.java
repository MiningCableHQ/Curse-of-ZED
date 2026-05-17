package Entities.Characters;

import Dialogue.*;
import Main.GamePanel;
import javax.imageio.ImageIO;

public class NPC_Bukog extends NPC {

    public NPC_Bukog(GamePanel gp) {
        super(gp);
        npcName = "Bukog";
        try {
            idleFrames[0] = ImageIO.read(
                    getClass().getResourceAsStream("/npc/bukog/bukog_idle1.png"));
            idleFrames[1] = ImageIO.read(
                    getClass().getResourceAsStream("/npc/bukog/bukog_idle2.png"));
            image = idleFrames[0];
        } catch (Exception e) {
            System.err.println("Bukog sprite not found.");
        }
        portrait = loadPortrait("/npc/bukog/bukog_portrait.png");
    }

    @Override
    public DialogueTree getDialogue(String playerClassName) {
        String khaiTitle = "Khai the " + playerClassName;
        DialogueTree tree = new DialogueTree();

        tree.addPage(new DialogueTree.Page("Bukog",
                "Oi, traveler! You look like you've seen better days. "
                        + "Lucky for you, old Bukog's got just what you need!", false));

        tree.addPage(new DialogueTree.Page(khaiTitle, "", true)
                .addChoice("What are you selling?", 2)
                .addChoice("Just passing through.", -1));

        tree.addPage(new DialogueTree.Page("Bukog",
                "Heheh! Everything a warrior needs to survive out here. "
                        + "Take a look, take a look!", false));

        tree.addPage(new DialogueTree.Page(khaiTitle, "", true)
                .addShopChoice("Let me see your wares.")
                .addChoice("Maybe later.", -1));

        return tree;
    }

    public java.awt.image.BufferedImage getPortrait() { return portrait; }
}