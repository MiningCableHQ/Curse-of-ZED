package Entities.Characters;

import Dialogue.*;
import Main.GamePanel;
import javax.imageio.ImageIO;

public class NPC_WomanVillager extends NPC {

    public NPC_WomanVillager(GamePanel gp) {
        super(gp);
        npcName = "Woman Villager";
        try {
            idleFrames[0] = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/npc/woman/woman_idle1.png"));
            idleFrames[1] = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/npc/woman/woman_idle2.png"));
            image = idleFrames[0];
        } catch (Exception e) {
            System.err.println("Woman Villager sprite not found.");
        }
        portrait = loadPortrait("/npc/woman/woman_portrait.png");
    }

    @Override
    public DialogueTree getDialogue(String playerClassName) {
        DialogueTree tree = new DialogueTree();

        tree.addPage(new DialogueTree.Page("Woman Villager",
                "We fight together or not at all. "
                        + "That's the Neverwinter way.", false));

        return tree;
        // Exit only — no choices, just Next then closes
    }

    public java.awt.image.BufferedImage getPortrait() { return portrait; }
}