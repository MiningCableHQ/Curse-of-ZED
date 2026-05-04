package Entities.Characters;

import Dialogue.*;
import Main.GamePanel;
import Main.GameStateManager;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class NPC_Frankenstein extends NPC {

    public boolean defeated = false;
    private Runnable onFightChosen;
    private boolean isVisible = false;  // HIDDEN BY DEFAULT

    public void setOnFightChosen(Runnable r) {
        this.onFightChosen = r;
    }

    public Runnable getOnFightChosen() {
        return onFightChosen;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
        System.out.println("👻 Frankenstein now " + (visible ? "VISIBLE" : "HIDDEN"));
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        if (!isVisible) return;  // INVISIBLE UNTIL ENABLED
        super.draw(g2, gp);
    }

    public NPC_Frankenstein(GamePanel gp) {
        super(gp);
        npcName = "Frankenstein";
        showOnMinimap = false;
        available = false;
        try {
            idleFrames[0] = ImageIO.read(getClass().getResourceAsStream("/npc/woman/woman_idle1.png"));
            idleFrames[1] = ImageIO.read(getClass().getResourceAsStream("/npc/woman/woman_idle2.png"));
            image = idleFrames[0];
        } catch (Exception e) {
            System.err.println("Frankenstein sprite not found.");
        }
        portrait = loadPortrait("/npc/frankenstein/frank_portrait.png");
    }

    @Override
    public DialogueTree getDialogue(String playerClassName) {
        String khaiTitle = "Khai the " + playerClassName;
        DialogueTree tree = new DialogueTree();

        // Page 0
        tree.addPage(new DialogueTree.Page("Frankenstein",
                "WAHAHAH! Happy Easter's Month! Enjoyed the hide and seek?", false));

        // Page 1 - Choices
        tree.addPage(new DialogueTree.Page(khaiTitle, "", true)
                .addChoice("Hide and seek? Bye!", -1)
                .addChoice("Yes! Warm-up time!", 2));

        // Page 2
        tree.addPage(new DialogueTree.Page("Frankenstein",
                "HEHEHEH, sure about that smaaaall fryyyy??", false));

        // Page 3 - Fight choice
        tree.addPage(new DialogueTree.Page(khaiTitle, "", true)
                .addChoice("Nevermind", -1)
                .addChoice("Always up for challenge!", 4));

        // Page 4 - Final (false = NPC speaking, -1 handled by choices above)
        tree.addPage(new DialogueTree.Page("Frankenstein",
                "Then come, little one! Let's see what you're made of!", false));

        return tree;
    }
    public java.awt.image.BufferedImage getPortrait() { return portrait; }
}