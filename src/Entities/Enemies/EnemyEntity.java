package Entities.Enemies;
import Entities.Entity;
import Entities.Characters.NPC;
import Dialogue.DialogueTree;
import Main.GamePanel;
import Objects.SuperObject;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class EnemyEntity extends NPC {

    public enum EnemyState {
        ROAMING, ALERTED, CHASING, BATTLE_TRIGGERED
    }
    private boolean battleTriggered = false;
    public EnemyState state = EnemyState.ROAMING;

    // Walking frames
    protected BufferedImage[] walkLeft  = new BufferedImage[5];
    protected BufferedImage[] walkRight = new BufferedImage[5];

    // Roaming
    protected int    roamTimer    = 0;
    protected int    roamDuration = 120;
    protected String roamDir      = "right";
    protected int    roamSpeed    = 1;

    // Chase / detection
    protected int chaseSpeed     = 2;
    protected int detectionRange = 140;
    protected int battleRange    = 48;

    // Walk animation
    protected int walkFrame   = 0;
    protected int walkCounter = 0;
    protected static final int WALK_SPEED = 10;

    // Exclamation effect only (no emote)
    private ExclamationEffect exclamation;
    private boolean alertPlayed = false;

    // State flags
    public boolean defeated = false;

    // Battle callback
    private Runnable onBattleTrigger;

    // ─────────────────────────────────────────────────────────────
    public EnemyEntity(GamePanel gp) {
        super(gp);
        collision         = false;
        solidArea         = new Rectangle(4, 4, 40, 40);
        solidAreaDefaultX = 4;
        solidAreaDefaultY = 4;
        interactRange     = 0;
        available         = true;
        entitySpeed       = 2;
        exclamation       = new ExclamationEffect();
    }

    public abstract void loadFrames();
    public abstract Enemy createBattleEnemy();

    public void setOnBattleTrigger(Runnable r) { this.onBattleTrigger = r; }

    @Override
    public void updateAnimation() { updateEnemy(); }

    // ── Main update ───────────────────────────────────────────────
    public void updateEnemy() {
        if (defeated || !available) return;
        if (gp.player == null) return;

        exclamation.update();

        switch (state) {
            case ROAMING:         updateRoaming();  break;
            case ALERTED:         updateAlerted();  break;
            case CHASING:         updateChasing();  break;
            case BATTLE_TRIGGERED: break;
        }

        walkCounter++;
        if (walkCounter >= WALK_SPEED) {
            walkCounter = 0;
            int frameCount = (walkLeft[4] != null) ? 5 : 4;
            walkFrame = (walkFrame + 1) % frameCount;
            updateMapImage();
        }
    }

    // ── Roaming ───────────────────────────────────────────────────
    private void updateRoaming() {
        if (getDistanceToPlayer() < detectionRange) {
            state       = EnemyState.ALERTED;
            alertPlayed = false;
            return;
        }

        roamTimer++;
        if (roamTimer >= roamDuration) {
            roamTimer    = 0;
            roamDuration = 80 + (int)(Math.random() * 100);
            String[] dirs = {"left", "right", "up", "down", "none"};
            roamDir = dirs[(int)(Math.random() * dirs.length)];
        }

        if (!roamDir.equals("none")) moveInDirection(roamDir, roamSpeed);
    }

    // ── Alerted — exclamation mark only, NO emote ────────────────
    private void updateAlerted() {
        if (!alertPlayed) {
            exclamation.trigger();
            alertPlayed = true;
            // ← emote spawn removed entirely
        }
        if (!exclamation.isActive()) {
            state = EnemyState.CHASING;
        }
    }

    // ── Chasing ───────────────────────────────────────────────────
    private void updateChasing() {
        double dist = getDistanceToPlayer();

        if (dist < battleRange) {
            if (!battleTriggered && onBattleTrigger != null) onBattleTrigger.run();
            return;
        }

        int px = gp.player.worldX + gp.tileSize / 2;
        int py = gp.player.worldY + gp.tileSize / 2;
        int ex = worldX + gp.tileSize / 2;
        int ey = worldY + gp.tileSize / 2;
        int dx = px - ex;
        int dy = py - ey;

        String primaryDir = (Math.abs(dx) > Math.abs(dy))
                ? (dx > 0 ? "right" : "left")
                : (dy > 0 ? "down"  : "up");

        if (!tryMove(primaryDir, chaseSpeed)) {
            String altDir1, altDir2;
            if (primaryDir.equals("left") || primaryDir.equals("right")) {
                altDir1 = dy >= 0 ? "down" : "up";
                altDir2 = dy >= 0 ? "up"   : "down";
            } else {
                altDir1 = dx >= 0 ? "right" : "left";
                altDir2 = dx >= 0 ? "left"  : "right";
            }
            if (!tryMove(altDir1, chaseSpeed)) {
                tryMove(altDir2, chaseSpeed);
            }
        }
    }

    public void markBattleStarted() {
        state           = EnemyState.BATTLE_TRIGGERED;
        battleTriggered = true;
    }

    // ── Move with tile and object collision ───────────────────────
    private boolean tryMove(String dir, int speed) {
        this.direction   = dir;
        this.entitySpeed = speed;
        this.collisionOn = false;
        gp.cChecker.checkTile(this);
        if (!this.collisionOn) checkObjectCollision(dir, speed);

        if (this.collisionOn) {
            return false;
        }

        switch (dir) {
            case "up":    worldY -= speed; break;
            case "down":  worldY += speed; break;
            case "left":  worldX -= speed; break;
            case "right": worldX += speed; break;
        }

        worldX = Math.max(gp.tileSize,
                Math.min(gp.worldWidth  - gp.tileSize * 2, worldX));
        worldY = Math.max(gp.tileSize,
                Math.min(gp.worldHeight - gp.tileSize * 2, worldY));
        roamDir = dir;
        return true;
    }

    private void checkObjectCollision(String dir, int speed) {
        int nextX = worldX + solidAreaDefaultX;
        int nextY = worldY + solidAreaDefaultY;
        switch (dir) {
            case "up":    nextY -= speed; break;
            case "down":  nextY += speed; break;
            case "left":  nextX -= speed; break;
            case "right": nextX += speed; break;
        }
        Rectangle nextArea = new Rectangle(nextX, nextY, solidArea.width, solidArea.height);
        for (int i = 0; i < gp.obj.length; i++) {
            SuperObject obj = gp.obj[i];
            if (obj == null || obj == this || !obj.collision) continue;
            if (obj instanceof EnemyEntity) continue;
            Rectangle objArea = new Rectangle(
                    obj.worldX + obj.solidAreaDefaultX,
                    obj.worldY + obj.solidAreaDefaultY,
                    obj.solidArea.width, obj.solidArea.height);
            if (nextArea.intersects(objArea)) {
                this.collisionOn = true;
                return;
            }
        }
    }

    private void moveInDirection(String dir, int speed) {
        if (!tryMove(dir, speed)) {
            roamTimer = roamDuration;
        }
    }

    // ── Helpers ───────────────────────────────────────────────────
    public double getDistanceToPlayer() {
        if (gp.player == null) return Double.MAX_VALUE;
        int px = gp.player.worldX + gp.tileSize / 2;
        int py = gp.player.worldY + gp.tileSize / 2;
        int ex = worldX + gp.tileSize / 2;
        int ey = worldY + gp.tileSize / 2;
        return Math.sqrt(Math.pow(px - ex, 2) + Math.pow(py - ey, 2));
    }

    private void updateMapImage() {
        BufferedImage[] frames = direction.equals("left") ? walkLeft : walkRight;
        if (walkFrame < frames.length && frames[walkFrame] != null) {
            image = frames[walkFrame];
        }
    }

    protected BufferedImage loadFrame(String path) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (Exception e) {
            System.err.println("Frame not found: " + path);
            return null;
        }
    }

    // ── Draw ──────────────────────────────────────────────────────
    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        if (defeated || !available) return;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        boolean onScreen =
                worldX + gp.tileSize * 3 > gp.player.worldX - gp.player.screenX
                        && worldX - gp.tileSize * 3 < gp.player.worldX + gp.player.screenX
                        && worldY + gp.tileSize * 3 > gp.player.worldY - gp.player.screenY
                        && worldY - gp.tileSize * 3 < gp.player.worldY + gp.player.screenY;

        if (!onScreen) return;

        if (image != null) {
            g2.drawImage(image, screenX, screenY,
                    gp.tileSize, gp.tileSize, null);
        } else {
            g2.setColor(new Color(200, 60, 60, 180));
            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Serif", Font.BOLD, 10));
            g2.drawString(npcName != null ? npcName.substring(0, 1) : "E",
                    screenX + 8, screenY + 20);
        }

        // Exclamation mark only on alert — no emote
        if (state == EnemyState.ALERTED && exclamation.isActive()) {
            exclamation.draw(g2, screenX + gp.tileSize / 2, screenY - 10);
        }
    }

    @Override
    public DialogueTree getDialogue(String playerClassName) { return null; }
}