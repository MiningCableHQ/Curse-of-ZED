package Objects;

import Entities.Entity;
import Main.GamePanel;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp) { this.gp = gp; }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch(entity.direction) {
                    case "up":    entity.solidArea.y -= entity.entitySpeed; break;
                    case "down":  entity.solidArea.y += entity.entitySpeed; break;
                    case "left":  entity.solidArea.x -= entity.entitySpeed; break;
                    case "right": entity.solidArea.x += entity.entitySpeed; break;
                }

                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) { index = i; }
                }

                // Reset positions
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public void checkTile(Entity entity) {
        int entityLeftX   = entity.worldX + entity.solidArea.x;
        int entityRightX  = entity.worldX + entity.solidArea.x
                + entity.solidArea.width;
        int entityTopY    = entity.worldY + entity.solidArea.y;
        int entityBottomY = entity.worldY + entity.solidArea.y
                + entity.solidArea.height;

        int entityLeftCol   = entityLeftX  / gp.tileSize;
        int entityRightCol  = entityRightX / gp.tileSize;
        int entityTopRow    = entityTopY    / gp.tileSize;
        int entityBottomRow = entityBottomY / gp.tileSize;

        int tileNum1, tileNum2;

        try {
            switch (entity.direction) {
                case "up":
                    entityTopRow = (entityTopY - entity.entitySpeed)
                            / gp.tileSize;
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    if (gp.tileM.tile[tileNum1].collision
                            || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    break;
                case "down":
                    entityBottomRow = (entityBottomY + entity.entitySpeed)
                            / gp.tileSize;
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                    if (gp.tileM.tile[tileNum1].collision
                            || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    break;
                case "left":
                    entityLeftCol = (entityLeftX - entity.entitySpeed)
                            / gp.tileSize;
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                    if (gp.tileM.tile[tileNum1].collision
                            || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    break;
                case "right":
                    entityRightCol = (entityRightX + entity.entitySpeed)
                            / gp.tileSize;
                    tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                    if (gp.tileM.tile[tileNum1].collision
                            || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            entity.collisionOn = true; // treat out-of-bounds as wall
        }
    }
    public void checkTile(SuperObject entity) {
        int entityLeftX   = entity.worldX + entity.solidArea.x;
        int entityRightX  = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopY    = entity.worldY + entity.solidArea.y;
        int entityBottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol   = entityLeftX  / gp.tileSize;
        int entityRightCol  = entityRightX / gp.tileSize;
        int entityTopRow    = entityTopY   / gp.tileSize;
        int entityBottomRow = entityBottomY / gp.tileSize;

        int tileNum1, tileNum2;

        try {
            switch (entity.direction) {
                case "up":
                    entityTopRow = (entityTopY - entity.entitySpeed) / gp.tileSize;
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    if (gp.tileM.tile[tileNum1].collision
                            || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    break;
                case "down":
                    entityBottomRow = (entityBottomY + entity.entitySpeed) / gp.tileSize;
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                    if (gp.tileM.tile[tileNum1].collision
                            || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    break;
                case "left":
                    entityLeftCol = (entityLeftX - entity.entitySpeed) / gp.tileSize;
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                    if (gp.tileM.tile[tileNum1].collision
                            || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    break;
                case "right":
                    entityRightCol = (entityRightX + entity.entitySpeed) / gp.tileSize;
                    tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                    if (gp.tileM.tile[tileNum1].collision
                            || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            entity.collisionOn = true;
        }
    }
}