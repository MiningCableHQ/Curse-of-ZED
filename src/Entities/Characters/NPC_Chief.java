package Entities.Characters;

import Main.GamePanel;
import Main.GameStateManager;
import javax.imageio.ImageIO;
import Dialogue.*;

public class NPC_Chief extends NPC {
    private Runnable onDialogueComplete;
    public void setOnDialogueComplete(Runnable r) { this.onDialogueComplete = r; }
    public Runnable getOnDialogueComplete() { return onDialogueComplete; }

    public NPC_Chief(GamePanel gp) {
        super(gp);
        npcName = "Chief";
        try {
            idleFrames[0] = ImageIO.read(getClass().getResourceAsStream("/npc/chief/chief_idle1.png"));
            idleFrames[1] = ImageIO.read(getClass().getResourceAsStream("/npc/chief/chief_idle2.png"));
            image = idleFrames[0];
        } catch (Exception e) {
            System.err.println("Chief sprite not found.");
        }
        portrait = loadPortrait("/npc/chief/chief_portrait.png");
    }

    @Override
    public DialogueTree getDialogue(String playerClassName) {
        GameStateManager.Map1Phase phase = GameStateManager.get().map1Phase;

        // Second visit (COLLECT_ESSENCE phase) — special dialogue
        if (phase == GameStateManager.Map1Phase.COLLECT_ESSENCE) {
            return getEssenceDialogue(playerClassName);
        }

        // First visit dialogue
        String khaiTitle = "Khai the " + playerClassName;
        DialogueTree tree = new DialogueTree();

        tree.addPage(new DialogueTree.Page("Chief",
                "You have your father's eyes, boy. And your mother's restless spirit.", false));
        tree.addPage(new DialogueTree.Page("Chief",
                "I won't pretend the barrier will hold much longer. The sorcerer's power grows by the day.", false));
        tree.addPage(new DialogueTree.Page(khaiTitle,
                "Then give me permission to go. I won't sit here while the forest dies around us.", true));
        tree.addPage(new DialogueTree.Page("Chief",
                "Spoken like your parents. I suppose that shouldn't surprise me.", false));
        tree.addPage(new DialogueTree.Page("Chief",
                "Very well. Go to the rangers' keep. Collect your equipment.", false));
        tree.addPage(new DialogueTree.Page("Chief",
                "And Khai — come back alive. That is an order.", false));

        return tree;
    }

    private DialogueTree getEssenceDialogue(String playerClassName) {
        String khaiTitle = "Khai the " + playerClassName;
        DialogueTree tree = new DialogueTree();

        tree.addPage(new DialogueTree.Page("Chief",
                "Your mother taught me how to lead. Your father taught me how to fight.", false));
        tree.addPage(new DialogueTree.Page("Chief",
                "Now I give you what they gave me — the strength to keep going when everything seems lost.", false));
        tree.addPage(new DialogueTree.Page("Chief",
                "Go, Khai. Finish this. For all of us.", false));
        tree.addPage(new DialogueTree.Page(khaiTitle,
                "I will return. With victory. Or not at all.", true));

        return tree;
    }

    public java.awt.image.BufferedImage getPortrait() { return portrait; }
}