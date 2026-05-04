package Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Dialogue.DialogueSystem;
import Entities.Characters.NPC;
import Entities.Enemies.EnemyEntity;
import Objects.SuperObject;

public class GossipSystem {

    private static final String[] VILLAGER_GOSSIP = {
            "Did you hear about the Sorcerer?",
            "Stay safe out there...",
            "Something's wrong with the forest.",
            "I haven't slept in days.",
            "The elder knows more than he says.",
            "My crops are withering...",
            "I saw lights in the north last night.",
            "Don't go near the ruins.",
            "They say the bridge is cursed.",
            "Who is this stranger anyway?",
            "I trust no one these days.",
            "Even the animals are acting strange.",
            "The harvests have been bad for weeks.",
            "I saw the hero earlier. Looks brave.",
            "Something evil is approaching...",
    };

    private static final String[] FRANKENSTEIN_LINES = {
            "…you weren't supposed to find me.",
            "The code... you solved it?",
            "Hehehe... curious one.",
            "This world has secrets beyond secrets.",
            "You see more than others do..."
    };



    private static final String[] SHOP_LINES = {
            "Best deals in the village!",
            "Shop shop! Fresh stock!",
            "Buy something, will ya?",
            "Quality goods, cheap prices!",
            "Everything must go!",
            "You look like you need gear...",
            "Step right up, adventurer!",
            "Come browse my wares!",
    };

    public static class GossipBubble {
        public String text;
        public int    worldX, worldY;
        public int    timer = 0;
        public float  alpha = 1f;
        public static final int DURATION = 200;

        GossipBubble(String text, int wx, int wy) {
            this.text = text; this.worldX = wx; this.worldY = wy;
        }

        public void update() {
            timer++;
            if (timer > DURATION * 0.7f)
                alpha = 1f - (float)(timer - DURATION * 0.7f) / (DURATION * 0.3f);
        }

        public boolean isDone() { return timer >= DURATION; }
    }

    private final List<GossipBubble> bubbles = new ArrayList<>();
    private int gossipTimer = 0;
    private final Random rng = new Random();

    // Reference so we can suppress gossip while dialogue is active
    private DialogueSystem dialogueSystem;
    public void setDialogueSystem(DialogueSystem ds) { this.dialogueSystem = ds; }

    public void update(SuperObject[] obj) {
        bubbles.removeIf(GossipBubble::isDone);
        bubbles.forEach(GossipBubble::update);

        // Don't gossip while player is in dialogue
        if (dialogueSystem != null && dialogueSystem.isActive()) return;

        gossipTimer++;
        if (gossipTimer >= 300) {
            gossipTimer = 0;
            trySpawnGossip(obj);
        }
    }

    private void trySpawnGossip(SuperObject[] obj) {
        List<SuperObject> candidates = new ArrayList<>();

        for (SuperObject o : obj) {
            if (o == null) continue;

            // ── Only NPCs gossip — exclude enemies, Frankenstein, and handle shopkeepers ──────
            if (o instanceof NPC && !(o instanceof EnemyEntity)) {
                NPC npc = (NPC) o;
                String name = npc.npcName == null ? "" : npc.npcName;

                // Exclude Frankenstein (Easter egg boss)
                if (name.equals("Frankenstein")) continue;

                if (npc.available) candidates.add(o);
            }
        }

        if (candidates.isEmpty()) return;

        SuperObject chosen = candidates.get(rng.nextInt(candidates.size()));
        String line = pickLineFor(chosen);
        if (line == null) return;

        bubbles.add(new GossipBubble(line,
                chosen.worldX + 32,
                chosen.worldY - 20));
    }

    private String pickLineFor(SuperObject o) {
        if (o instanceof NPC) {
            NPC npc = (NPC) o;
            String name = npc.npcName == null ? "" : npc.npcName;

            // 🛒 Shopkeepers - special shop lines
            if (name.equals("Frank") || name.equals("Bukog")) {
                return SHOP_LINES[rng.nextInt(SHOP_LINES.length)];
            }

            // 👥 Normal villagers only
            return VILLAGER_GOSSIP[rng.nextInt(VILLAGER_GOSSIP.length)];
        }
        return null;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        g2.setFont(new Font("Serif", Font.ITALIC, 11));

        for (GossipBubble b : bubbles) {
            int sx = b.worldX - gp.player.worldX + gp.player.screenX;
            int sy = b.worldY - gp.player.worldY + gp.player.screenY;

            float a = Math.max(0f, Math.min(1f, b.alpha));
            FontMetrics fm = g2.getFontMetrics();
            int tw  = fm.stringWidth(b.text);
            int pad = 6;

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a * 0.85f));
            g2.setColor(new Color(255, 252, 220));
            g2.fillRoundRect(sx - tw / 2 - pad, sy - 16, tw + pad * 2, 20, 8, 8);
            g2.setColor(new Color(100, 80, 30));
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(sx - tw / 2 - pad, sy - 16, tw + pad * 2, 20, 8, 8);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
            g2.setColor(new Color(50, 30, 10));
            g2.drawString(b.text, sx - tw / 2, sy - 2);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}