package Entities.Characters;

import Main.GamePanel;
import Main.GameStateManager;
import javax.imageio.ImageIO;
import Dialogue.*;

public class NPC_Ranger extends NPC {

    public NPC_Ranger(GamePanel gp) {
        super(gp);
        npcName = "Ranger";
        try {
            idleFrames[0] = ImageIO.read(getClass().getResourceAsStream("/npc/ranger/ranger_idle1.png"));
            idleFrames[1] = ImageIO.read(getClass().getResourceAsStream("/npc/ranger/ranger_idle2.png"));
            image = idleFrames[0];
        } catch (Exception e) {
            System.err.println("Ranger sprite not found.");
        }
        portrait = loadPortrait("/npc/ranger/ranger_portrait.png");
    }

    @Override
    public DialogueTree getDialogue(String playerClassName) {
        GameStateManager.Map1Phase phase = GameStateManager.get().map1Phase;

        if (phase == GameStateManager.Map1Phase.COLLECT_ESSENCE) {
            return getEssenceDialogue(playerClassName);
        }

        // First visit
        String khaiTitle = "Khai the " + playerClassName;
        DialogueTree tree = new DialogueTree();

        tree.addPage(new DialogueTree.Page("Ranger",
                "The chief told us you'd be coming.", false));
        tree.addPage(new DialogueTree.Page(khaiTitle, "", true)
                .addChoice("No he didn't. Good bye.", -1)
                .addChoice("Really? He told me to grab something from you.", 2));
        tree.addPage(new DialogueTree.Page("Ranger",
                "Everything's been prepared. Your mother used to say a good armament "
                        + "is nothing without the will behind it. Don't forget that out there. "
                        + "The sorcerer will try to break more than your body.", false));
        tree.addPage(new DialogueTree.Page(khaiTitle, "", true)
                .addChoice("Thank you so much!", -1)
                .addChoice("Nah, I'd win.", -1));

        return tree;
    }

    private DialogueTree getEssenceDialogue(String playerClassName) {
        DialogueTree tree = new DialogueTree();
        tree.addPage(new DialogueTree.Page("Ranger",
                "I watched you grow up, Khai. I always knew this day would come.", false));
        return tree;
    }

    public java.awt.image.BufferedImage getPortrait() { return portrait; }
}