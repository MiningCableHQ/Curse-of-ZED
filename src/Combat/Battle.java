package Combat;

import Combat.StatusEffects.Frozen;
import Combat.StatusEffects.Slow;
import Combat.StatusEffects.StatusEffect;
import Combat.StatusEffects.Stun;
import Entities.Characters.Player;
import Entities.Characters.Ranger;
import Entities.Characters.Swordsman;
import Entities.Characters.Mage;
import Entities.Enemies.Enemy;
import Entities.Enemies.*;
import Entities.Entity;
import Items.Item;
import Moves.Move;

import javax.swing.*;
import java.awt.Window;
import java.util.*;
import javax.swing.Timer;

import static Combat.StatusEffects.Stun.ACCURACY_REDUCTION;

public class Battle {

    // References to UI components
    private final BattlePanel battlePanel;
    private final Player player;
    private final List<Enemy> enemies;

    // Battle state
    private boolean isBattleActive = true;
    private boolean isWaitingForPlayerInput = false;
    private Move pendingMove;
    private Enemy selectedTarget;
    private boolean isExecutingTurn = false;

    // Item usage tracking
    private Item pendingItem;
    private boolean hasPendingItem;

    // Cycle System fields
    private List<Entity> currentTurnOrder;
    private int currentTurnIndex;
    private boolean isCycleActive = false;
    private Map<Entity, Double> originalSpeeds = new HashMap<>();
    private static final int SPEED_DEDUCTION = 20;

    // Random for enemy AI
    private final Random random = new Random();

    // Callback for battle end
    private Runnable onBattleEnd;

    // Delay constants
    private static final int MESSAGE_DELAY = 1500;
    private static final int TURN_DELAY = 1000;

    // Store the chosen move for the player's turn
    private Move chosenMove;
    private Enemy chosenTarget;

    // Track status effects on entities for the current cycle
    private List<Entity> frozenEntities = new ArrayList<>();
    private List<Entity> stunnedEntities = new ArrayList<>();
    private List<Entity> slowedEntities = new ArrayList<>();

    // Store original accuracy for stunned entities to restore later
    private java.util.Map<Entity, Double> originalAccuracyMap = new java.util.HashMap<>();

    // ─────────────────────────────────────────────────────────────
    // Constructor
    // ─────────────────────────────────────────────────────────────
    public Battle(BattlePanel battlePanel, Player player, List<Enemy> enemies) {
        this.battlePanel = battlePanel;
        this.player = player;
        this.enemies = new ArrayList<>(enemies);
    }

    // ─────────────────────────────────────────────────────────────
    // Item Usage Methods
    // ─────────────────────────────────────────────────────────────
    public boolean hasPendingItem() {
        return hasPendingItem;
    }

    public void setPendingItem(Item item, Enemy target) {
        pendingItem = item;

        if (item.getTargetType() == Item.TargetType.SELF) {
            // Self-targeting item (potions, buffs, heals) — apply directly to player
            hasPendingItem = false;
            executeItemTurn(item, null);

        } else if (item.getTargetType() == Item.TargetType.ALL_ENEMIES) {
            // AoE item — no target selection needed, hits all enemies
            hasPendingItem = false;
            executeItemTurn(item, null);

        } else {
            // Enemy-targeted item (debuffs etc.) — show target selection screen
            hasPendingItem = true;
            battlePanel.showItemTargetSelection(item);
        }
    }

    public void handleItemTargetSelected(int enemyIndex) {
        if (!hasPendingItem || pendingItem == null) return;

        if (enemyIndex < enemies.size()) {
            Enemy target = enemies.get(enemyIndex);
            hasPendingItem = false;
            executeItemTurn(pendingItem, target);
            pendingItem = null;
        }
    }

    public void cancelItemTargetSelection() {
        hasPendingItem = false;
        pendingItem = null;
        isWaitingForPlayerInput = true;
        battlePanel.showFightMenu();
    }

    public void executeItemTurn() {
        // This is called when item was already selected (for self-targeting items)
        if (pendingItem != null) {
            executeItemTurn(pendingItem, null);
            pendingItem = null;
        }
    }

    private void executeItemTurn(Item item, Enemy target) {
        if (!isBattleActive) return;

        isExecutingTurn = true;
        isWaitingForPlayerInput = false;
        battlePanel.hideMainMenu();
        battlePanel.hideBattleButtons();
        battlePanel.hideTargetButtons();
        battlePanel.setButtonsEnabled(false);

        // Display message that player is about to act
        String startMessage = player.getName() + " is about to use " + item.getName() + "!";
        battlePanel.setBattleMessage(startMessage);
        battlePanel.repaint();

        Timer executeTimer = new Timer(MESSAGE_DELAY, e -> {
            if (!isBattleActive) return;

            String resultMessage;

            // Check if player still has the item (in case quantity changed)
            int currentQuantity = player.getInventory().getQuantity(item);
            if (currentQuantity <= 0) {
                resultMessage = player.getName() + " has no " + item.getName() + " left!";
                battlePanel.setBattleMessage(resultMessage);
                battlePanel.repaint();

                Timer nextTimer = new Timer(TURN_DELAY, ev -> moveToNextTurn());
                nextTimer.setRepeats(false);
                nextTimer.start();
                return;
            }

            // Check item target type
            if (item.getTargetType() == Item.TargetType.SELF) {
                // Self-targeting item
                item.useItem(player);
                // Remove ONE item from inventory
                player.getInventory().removeItem(item, 1);

                // USE THE ITEM'S MESSAGE
                resultMessage = item.getUseMessage();
                if (resultMessage == null || resultMessage.isEmpty()) {
                    resultMessage = player.getName() + " used " + item.getName() + "!";
                }

            } else if (item.getTargetType() == Item.TargetType.ENEMY && target != null) {
                // Single enemy target
                double beforeHp = target.getHp();
                item.useItem(target);
                double afterHp = target.getHp();
                double effectAmount = beforeHp - afterHp;

                // Remove ONE item from inventory after using
                player.getInventory().removeItem(item, 1);

                // USE THE ITEM'S MESSAGE OR CREATE ONE
                resultMessage = item.getUseMessage();
                if (resultMessage == null || resultMessage.isEmpty()) {
                    if (effectAmount > 0) {
                        resultMessage = player.getName() + " used " + item.getName() + " on " +
                                target.getName() + " and dealt " + String.format("%d", (int)effectAmount) + " damage!";
                    } else {
                        resultMessage = player.getName() + " used " + item.getName() + " on " + target.getName();
                    }
                }

            } else if (item.getTargetType() == Item.TargetType.ALL_ENEMIES) {
                // AoE item
                for (Enemy enemy : enemies) {
                    if (enemy.getHp() > 0) {
                        item.useItem(enemy);
                    }
                }
                // Remove ONE item from inventory
                player.getInventory().removeItem(item, 1);

                // USE THE ITEM'S MESSAGE
                resultMessage = item.getUseMessage();
                if (resultMessage == null || resultMessage.isEmpty()) {
                    resultMessage = player.getName() + " used " + item.getName() + " on all enemies!";
                }

            } else {
                // Default case
                resultMessage = item.getUseMessage();
                if (resultMessage == null || resultMessage.isEmpty()) {
                    resultMessage = player.getName() + " used " + item.getName() + "!";
                }
            }

            battlePanel.setBattleMessage(resultMessage);
            battlePanel.repaint();
            battlePanel.updateTargetButtonStates();

            battlePanel.refreshInventoryDisplay();

            // Check if battle should end
            if (getAliveEnemies().isEmpty()) {
                endBattle(true);
                return;
            }

            // Move to next turn
            Timer nextTimer = new Timer(TURN_DELAY, ev -> moveToNextTurn());
            nextTimer.setRepeats(false);
            nextTimer.start();
        });
        executeTimer.setRepeats(false);
        executeTimer.start();
    }

    // ─────────────────────────────────────────────────────────────
    // Battle Control Methods
    // ─────────────────────────────────────────────────────────────

    /** Start the battle */
    public void startBattle() {
        isBattleActive = true;
        startNewCycle();
    }

    /** Start a new cycle - resets all speeds and begins turn order */
    private void startNewCycle() {
        if (!isBattleActive) return;

        // CLEAR ALL FROZEN AND STUNNED STATUS EFFECTS AT START OF NEW CYCLE
        clearAllFrozenEffects();
        clearAllStunEffects();

        // Store original speeds (preserving any buffs)
        storeOriginalSpeeds();

        // Reset all entities to their original speeds
        resetAllSpeeds();

        // Calculate turn order for this cycle
        calculateTurnOrder();

        if (currentTurnOrder.isEmpty()) {
            if (getAliveEnemies().isEmpty()) {
                endBattle(true);
            } else if (player.getHp() < 1) {
                endBattle(false);
            } else {
                System.out.println("WARNING: No entities can act despite having HP!");
                endBattle(false);
            }
            return;
        }

        isCycleActive = true;
        currentTurnIndex = 0;

        System.out.println("=== NEW CYCLE START ===");
        printSpeedStatus();

        // Start processing the cycle
        processNextTurn();
    }

    /** Store original speeds before deduction cycle */
    private void storeOriginalSpeeds() {
        if (originalSpeeds.isEmpty()) { // Only store once
            if (player.getHp() > 0) {
                originalSpeeds.put(player, player.getSpeed());
            }
            for (Enemy enemy : enemies) {
                if (enemy.getHp() > 0) {
                    originalSpeeds.put(enemy, enemy.getSpeed());
                }
            }
            System.out.println("Initial original speeds stored:");
            for (Map.Entry<Entity, Double> entry : originalSpeeds.entrySet()) {
                System.out.println("  " + entry.getKey().getName() + ": " + entry.getValue());
            }
        }
    }

    /** Reset all entities to their original speeds */
    private void resetAllSpeeds() {
        for (Map.Entry<Entity, Double> entry : originalSpeeds.entrySet()) {
            Entity entity = entry.getKey();
            double originalSpeed = entry.getValue();
            entity.setSpeed(originalSpeed);
            System.out.println("Reset " + entity.getName() + " speed to: " + originalSpeed);
        }
    }

    /** Calculate turn order based on current speeds (highest to lowest) */
    private void calculateTurnOrder() {
        List<Entity> allEntities = new ArrayList<>();

        if (player.getHp() > 0 && player.getSpeed() > 0) {
            allEntities.add(player);
        }
        for (Enemy enemy : enemies) {
            if (enemy.getHp() > 0 && enemy.getSpeed() > 0) {
                allEntities.add(enemy);
            }
        }

        // Sort by speed (highest to lowest)
        allEntities.sort((e1, e2) -> {
            int speedCompare = Double.compare(e2.getSpeed(), e1.getSpeed());
            if (speedCompare != 0) return speedCompare;

            if (e1 instanceof Player) return -1;
            if (e2 instanceof Player) return 1;

            // Random tie-breaking for enemies
            return random.nextBoolean() ? -1 : 1;
        });

        currentTurnOrder = new ArrayList<>(allEntities);

        System.out.println("Turn order calculated:");
        for (int i = 0; i < currentTurnOrder.size(); i++) {
            System.out.println("  " + (i+1) + ". " + currentTurnOrder.get(i).getName() +
                    " (Speed: " + currentTurnOrder.get(i).getSpeed() + ")");
        }
    }

    /** Process status effects for all entities at the start of their turn/cycle */
    private void processEntityStatusEffects(Entity entity) {
        if (entity.getHp() <= 0) return;

        // Process all status effects for this entity
        entity.processStatusEffects();

        // Check if entity died from status effects
        if (entity.getHp() <= 0) {
            battlePanel.setBattleMessage(entity.getName() + " has been defeated by status effects!");
            battlePanel.repaint();
        }
    }

    /** Process the next turn in the cycle */
    private void processNextTurn() {
        if (!isBattleActive) return;

        // Check if we've completed the current round
        if (currentTurnIndex >= currentTurnOrder.size()) {
            // End of round - deduct speed from all entities
            deductSpeedFromAll();

            // Remove defeated entities from turn order for next round
            currentTurnOrder.removeIf(entity -> entity.getHp() < 1);

            // Check if cycle should end
            if (shouldEndCycle()) {
                endCycle();
                return;
            } else {
                // Start new round within the same cycle
                currentTurnIndex = 0;
                calculateTurnOrder();
                processNextTurn();
                return;
            }
        }

        Entity currentEntity = currentTurnOrder.get(currentTurnIndex);

        // Skip if entity is defeated
        if (currentEntity.getHp() < 1) {
            currentTurnIndex++;
            processNextTurn();
            return;
        }

        System.out.println("\n--- Turn: " + currentEntity.getName() + " ---");

        // Process turn-based status effects BEFORE the entity acts
        processTurnBasedStatusEffects(currentEntity);

        // Check if entity died from status effects
        if (currentEntity.getHp() < 1) {
            battlePanel.setBattleMessage(currentEntity.getName() + " succumbed to their wounds!");
            battlePanel.repaint();

            if (currentEntity instanceof Player) {
                endBattle(false);
                return;
            } else if (getAliveEnemies().isEmpty()) {
                endBattle(true);
                return;
            }

            currentTurnIndex++;
            processNextTurn();
            return;
        }

        // CHECK IF ENTITY IS FROZEN - skip entire turn
        if (isFrozen(currentEntity)) {
            battlePanel.setBattleMessage(currentEntity.getName() + " is Frozen and cannot act!");
            battlePanel.repaint();

            Timer frozenTimer = new Timer(800, ev -> moveToNextTurn());
            frozenTimer.setRepeats(false);
            frozenTimer.start();
            return;
        }

        // CHECK IF ENTITY IS SLOWED - cannot move (but can still act? depends on your definition)
        // If "cannot move" means cannot act at all, treat like frozen for 1 turn:
        if (isSlowed(currentEntity)) {
            battlePanel.setBattleMessage(currentEntity.getName() + " is Slowed and cannot move this turn!");
            battlePanel.repaint();

            // Remove slow after this skipped turn
            removeSlow(currentEntity);

            Timer slowTimer = new Timer(800, ev -> moveToNextTurn());
            slowTimer.setRepeats(false);
            slowTimer.start();
            return;
        }

        if (currentEntity instanceof Player) {
            startPlayerTurn();
        } else if (currentEntity instanceof Enemy) {
            executeEnemyTurn((Enemy) currentEntity);
        }
    }

    /** Deduct speed from all alive entities */
    private void deductSpeedFromAll() {
        System.out.println("\n--- End of Round - Deducting " + SPEED_DEDUCTION + " Speed from all ---");

        if (player.getHp() > 0) {
            double newSpeed = Math.max(0, player.getSpeed() - SPEED_DEDUCTION);
            player.setSpeed(newSpeed);
            System.out.println("  " + player.getName() + " speed: " + player.getSpeed());
        }

        for (Enemy enemy : enemies) {
            if (enemy.getHp() > 0) {
                double newSpeed = Math.max(0, enemy.getSpeed() - SPEED_DEDUCTION);
                enemy.setSpeed(newSpeed);
                System.out.println("  " + enemy.getName() + " speed: " + enemy.getSpeed());
            }
        }
    }

    /** Check if cycle should end (all entities have speed <= 0) */
    private boolean shouldEndCycle() {
        // Check if ANY alive entity still has speed > 0
        if (player.getHp() > 0 && player.getSpeed() > 0) {
            return false;
        }
        for (Enemy enemy : enemies) {
            if (enemy.getHp() > 0 && enemy.getSpeed() > 0) {
                return false;
            }
        }
        return true;
    }

    /** End current cycle and start a new one */
    private void endCycle() {
        System.out.println("=== CYCLE END ===\n");
        isCycleActive = false;

        // Check if battle should continue
        if (getAliveEnemies().isEmpty()) {
            endBattle(true);
        } else if (player.getHp() < 1) {
            endBattle(false);
        } else {
            // Start a new cycle
            startNewCycle();
        }
    }

    /** Process only cycle-based status effects */
    private void processCycleBasedEffects(Entity entity) {
        List<StatusEffect> effects = entity.getStatusEffects();
        List<StatusEffect> toRemove = new ArrayList<>();

        for (StatusEffect effect : effects) {
            if (effect.isCycleBased()) {
                // For Frozen, we just log the effect (actual skipping happens in turn processing)
                if (effect.getName().equals("Frozen")) {
                    System.out.println(entity.getName() + " is " + effect.getName() +
                            " (" + effect.getDuration() + " cycles remaining)");
                } else {
                    effect.executeEffect(entity);
                }
                effect.reduceDuration();

                if (effect.isExpired()) {
                    toRemove.add(effect);
                    System.out.println(entity.getName() + "'s " + effect.getName() + " has worn off!");
                }
            }
        }

        for (StatusEffect effect : toRemove) {
            entity.removeStatusEffect(effect.getName());
        }

        if (entity.getHp() <= 0) {
            battlePanel.setBattleMessage(entity.getName() + " has been defeated by status effects!");
            battlePanel.repaint();
        }
    }

    /** Start player's turn - show menu and wait for input */
    private void startPlayerTurn() {
        if (!isBattleActive) return;

        isWaitingForPlayerInput = true;
        isExecutingTurn = false;
        hasPendingItem = false; // Reset item pending state
        pendingItem = null;
        pendingMove = null; // Reset move pending state

        // Show the main menu for player to choose action
        battlePanel.showMainMenu();
        battlePanel.setBattleMessage(player.getName() + "'s turn! What will you do?");
        battlePanel.setButtonsEnabled(true);
    }

    /** Start the phase where player chooses their move */
    private void startPlayerDecisionPhase() {
        startPlayerTurn();
    }

    /** Execute enemy's turn */
    private void executeEnemyTurn(Enemy enemy) {
        if (!isBattleActive) return;

        isExecutingTurn = true;
        isWaitingForPlayerInput = false;
        battlePanel.hideMainMenu();
        battlePanel.hideBattleButtons();
        battlePanel.setButtonsEnabled(false);

        Move enemyMove = enemy.selectMove();

        if (enemyMove == null) {
            moveToNextTurn();
            return;
        }

        String message = enemy.getName() + " used " + enemyMove.getName();
        battlePanel.setBattleMessage(message);
        battlePanel.repaint();

        Timer executeTimer = new Timer(MESSAGE_DELAY, e -> {
            if (!isBattleActive) return;

            Move.currentTarget = player;
            // mao ni bai
            Move.currentBattle = this;
            // -----
            enemyMove.execute(enemy);
            Move.currentTarget = null;

            // Use the move's own message
            battlePanel.setBattleMessage(enemyMove.getMessage());
            battlePanel.repaint();

            if (player.getHp() < 1) {
                player.setHp(0);
                endBattle(false);
                return;
            }

            Timer nextTimer = new Timer(TURN_DELAY, ev -> moveToNextTurn());
            nextTimer.setRepeats(false);
            nextTimer.start();
        });
        executeTimer.setRepeats(false);
        executeTimer.start();
    }

    /** Move to the next turn in the cycle */
    /** Move to the next turn in the cycle */
    private void moveToNextTurn() {
        if (!isBattleActive) return;

        currentTurnIndex++;
        isExecutingTurn = false;
        processNextTurn();
    }

    /** Print current speed status for debugging */
    private void printSpeedStatus() {
        System.out.println("Current Speeds:");
        if (player.getHp() > 0) {
            System.out.println("  " + player.getName() + ": " + player.getSpeed());
        }
        for (Enemy enemy : enemies) {
            if (enemy.getHp() > 0) {
                System.out.println("  " + enemy.getName() + ": " + enemy.getSpeed());
            }
        }
    }

    // ─────────────────────────────────────────────────────────────
    // Player Actions
    // ─────────────────────────────────────────────────────────────

    /** Handle player selecting a move during decision phase */
    public void handlePlayerMove(Move move) {
        if (!isBattleActive || !isWaitingForPlayerInput) return;

        isWaitingForPlayerInput = false;
        pendingMove = move;
        battlePanel.hideMainMenu();
        battlePanel.setButtonsEnabled(false);

        // Check if move requires target selection
        if (move.getTargetType() == Move.TargetType.SELF) {
            // Self-targeting move - no target needed
            chosenMove = move;
            chosenTarget = null;
            executePlayerMove();
        } else if (move.getTargetType() == Move.TargetType.ALL_ENEMIES) {
            // AoE move - no specific target needed
            chosenMove = move;
            chosenTarget = null;
            executePlayerMove();
        } else if (getAliveEnemies().size() > 1 && move.getTargetType() == Move.TargetType.ENEMY) {
            // Need target selection
            battlePanel.showTargetSelection(move);
        } else {
            // Single enemy
            chosenMove = move;
            chosenTarget = getAliveEnemies().get(0);
            executePlayerMove();
        }
    }

    /** Handle player selecting a target for a move */
    public void handleTargetSelected(int enemyIndex) {
        System.out.println("handleTargetSelected called for index: " + enemyIndex);

        if (!isBattleActive) return;

        // Check if we have a pending move
        if (pendingMove == null) {
            System.out.println("ERROR: No pending move!");
            battlePanel.showFightMenu();
            return;
        }

        // Check if enemy index is valid
        if (enemyIndex >= enemies.size()) {
            System.out.println("ERROR: Invalid enemy index: " + enemyIndex);
            battlePanel.showFightMenu();
            return;
        }

        Enemy targetEnemy = enemies.get(enemyIndex);

        // Check if target enemy is alive
        if (targetEnemy.getHp() < 1) {
            System.out.println("ERROR: Target enemy is already defeated!");
            battlePanel.setBattleMessage("That enemy is already defeated! Choose another target.");
            battlePanel.showTargetSelection(pendingMove);
            return;
        }

        // Valid target selected
        chosenMove = pendingMove;
        chosenTarget = targetEnemy;
        pendingMove = null;

        System.out.println("Target selected: " + chosenTarget.getName() + " (HP: " + chosenTarget.getHp() + ")");

        executePlayerMove();
    }

    /** Cancels target selection - resets state and returns to fight menu */
    public void cancelTargetSelection() {
        if (!isBattleActive) return;

        System.out.println("Target selection cancelled - resetting state");

        // Clear pending selections
        pendingMove = null;
        chosenMove = null;
        chosenTarget = null;

        // Reset waiting flag - player can now make new choices
        isWaitingForPlayerInput = true;

        // Show fight menu directly
        battlePanel.showFightMenu();
    }

    /** Execute player's move */
    private void executePlayerMove() {
        if (chosenMove == null) {
            moveToNextTurn();
            return;
        }

        // Validate target for single-target moves
        if (chosenMove.getTargetType() == Move.TargetType.ENEMY) {
            if (chosenTarget == null || chosenTarget.getHp() <= 0) {
                List<Enemy> aliveEnemies = getAliveEnemies();
                if (!aliveEnemies.isEmpty()) {
                    chosenTarget = aliveEnemies.get(0);
                } else {
                    moveToNextTurn();
                    return;
                }
            }
        }

        final Move move = chosenMove;
        final Enemy target = chosenTarget;

        battlePanel.hideBattleButtons();
        battlePanel.hideTargetButtons();

        String startMessage = player.getName() + " is about to use " + move.getName() + "!";
        battlePanel.setBattleMessage(startMessage);
        battlePanel.repaint();

        Timer executeTimer = new Timer(MESSAGE_DELAY, e -> {
            if (!isBattleActive) return;

            // Execute the move based on target type
            if (move.getTargetType() == Move.TargetType.SELF) {
                Move.currentTarget = player;
                move.execute(player);
                Move.currentTarget = null;

                // Activate after-move passive
                if (player.getWeapon() != null) {
                    player.getWeapon().onAfterMove(player, move, null);
                }

                battlePanel.setBattleMessage(move.getMessage());
                battlePanel.repaint();

            } else if (move.getTargetType() == Move.TargetType.ALL_ENEMIES) {
                StringBuilder combinedMessage = new StringBuilder();
                double totalDamageDealt = 0;

                for (Enemy enemy : enemies) {
                    if (enemy.getHp() > 0) {
                        Move.currentTarget = enemy;
                        move.execute(player);
                        totalDamageDealt += move.getLastDamageDealt();

                        // Activate after-damage passive for each enemy
                        if (player.getWeapon() != null) {
                            player.getWeapon().onAfterDamage(player, move, enemy, move.getLastDamageDealt());
                        }

                        if (move.getMessage() != null && !move.getMessage().isEmpty()) {
                            if (combinedMessage.length() > 0) combinedMessage.append(" ");
                            combinedMessage.append(move.getMessage());
                        }
                    }
                }
                Move.currentTarget = null;

                // Activate after-move passive once after all enemies
                if (player.getWeapon() != null) {
                    player.getWeapon().onAfterMove(player, move, null);
                }

                if (combinedMessage.length() > 0) {
                    battlePanel.setBattleMessage(combinedMessage.toString());
                } else {
                    battlePanel.setBattleMessage(move.getMessage());
                }
                battlePanel.repaint();
                battlePanel.updateTargetButtonStates();

            } else {
                Enemy currentTarget = target;
                if (currentTarget == null || currentTarget.getHp() <= 0) {
                    List<Enemy> aliveEnemies = getAliveEnemies();
                    if (!aliveEnemies.isEmpty()) {
                        currentTarget = aliveEnemies.get(0);
                    } else {
                        moveToNextTurn();
                        return;
                    }
                }

                if (currentTarget != null && currentTarget.getHp() > 0) {
                    Move.currentTarget = currentTarget;
                    Move.currentBattle = this;
                    move.execute(player);

                    // Activate after-damage passive
                    if (player.getWeapon() != null) {
                        player.getWeapon().onAfterDamage(player, move, currentTarget, move.getLastDamageDealt());
                        player.getWeapon().onAfterMove(player, move, currentTarget);
                    }

                    Move.currentTarget = null;
                    battlePanel.setBattleMessage(move.getMessage());
                    battlePanel.repaint();
                    battlePanel.updateTargetButtonStates();
                }
            }

            if (getAliveEnemies().isEmpty()) {
                endBattle(true);
                return;
            }

            if (player.getHp() < 1) {
                endBattle(false);
                return;
            }

            Timer nextTimer = new Timer(TURN_DELAY, ev -> moveToNextTurn());
            nextTimer.setRepeats(false);
            nextTimer.start();
        });
        executeTimer.setRepeats(false);
        executeTimer.start();
    }

    /** Handle player running away */
    public void handleRunAway() {
        if (!isBattleActive || !isWaitingForPlayerInput) return;

        isWaitingForPlayerInput = false;
        battlePanel.setButtonsEnabled(false);
        battlePanel.hideMainMenu();

        boolean escapeSuccess = random.nextDouble() < 0.8;

        if (escapeSuccess) {
            battlePanel.setBattleMessage(player.getName() + " escaped from battle!");
            battlePanel.repaint();
            Timer escapeTimer = new Timer(1000, e -> endBattle(true));
            escapeTimer.setRepeats(false);
            escapeTimer.start();
        } else {
            battlePanel.setBattleMessage(player.getName() + " failed to escape!");
            battlePanel.repaint();

            Timer failTimer = new Timer(1500, e -> {
                if (isBattleActive) {
                    // Enemy gets a free turn - just move to next turn
                    moveToNextTurn();
                }
            });
            failTimer.setRepeats(false);
            failTimer.start();
        }
    }

    // ─────────────────────────────────────────────────────────────
    // STATUS EFFECT METHODS
    // ─────────────────────────────────────────────────────────────
    /** Check if entity is frozen and cannot act */
    private boolean isFrozen(Entity entity) {
        return frozenEntities.contains(entity);
    }

    /** Apply frozen status to an entity */
    public void applyFrozen(Entity entity) {
        if (!frozenEntities.contains(entity)) {
            frozenEntities.add(entity);
            entity.addStatusEffect(new Frozen());
            System.out.println(entity.getName() + " has been FROZEN for the rest of this cycle!");
            battlePanel.setBattleMessage(entity.getName() + " is Frozen and cannot act this cycle!");
            battlePanel.repaint();
        }
    }

    /** Clear all frozen effects at the start of a new cycle */
    private void clearAllFrozenEffects() {
        if (!frozenEntities.isEmpty()) {
            for (Entity entity : frozenEntities) {
                entity.removeStatusEffect("Frozen");
                System.out.println(entity.getName() + " is no longer Frozen!");
                battlePanel.setBattleMessage(entity.getName() + " has thawed out!");
                battlePanel.repaint();
            }
            frozenEntities.clear();
        }
    }

    /** Process turn-based status effects for a single entity */
    private void processTurnBasedStatusEffects(Entity entity) {
        List<StatusEffect> effects = entity.getStatusEffects();
        List<StatusEffect> toRemove = new ArrayList<>();

        for (StatusEffect effect : effects) {
            if (!effect.isCycleBased()) { // Turn-based effects
                effect.executeEffect(entity);
                effect.reduceDuration();

                if (effect.isExpired()) {
                    toRemove.add(effect);
                    System.out.println(entity.getName() + "'s " + effect.getName() + " has worn off!");
                    battlePanel.setBattleMessage(entity.getName() + "'s " + effect.getName() + " has worn off!");
                    battlePanel.repaint();
                }
            }
        }

        for (StatusEffect effect : toRemove) {
            entity.removeStatusEffect(effect.getName());
        }
    }

    /** Apply stun to an entity (reduces accuracy by 10% for current cycle) */
    public void applyStun(Entity entity) {
        if (!stunnedEntities.contains(entity)) {
            stunnedEntities.add(entity);

            // Store original accuracy
            originalAccuracyMap.put(entity, entity.getAccuracy());

            // Apply accuracy reduction (10% of original, not flat 10%)
            double newAccuracy = entity.getAccuracy() * (1 - ACCURACY_REDUCTION);
            entity.setAccuracy(newAccuracy);

            entity.addStatusEffect(new Stun());
            System.out.println(entity.getName() + " has been STUNNED! Accuracy reduced by 10% for this cycle!");
            battlePanel.setBattleMessage(entity.getName() + " is Stunned! Accuracy reduced!");
            battlePanel.repaint();
        }
    }

    /** Check if an entity is stunned */
    private boolean isStunned(Entity entity) {
        return stunnedEntities.contains(entity);
    }

    /** Clear all stun effects at the start of a new cycle (restore accuracy) */
    private void clearAllStunEffects() {
        if (!stunnedEntities.isEmpty()) {
            for (Entity entity : stunnedEntities) {
                // Restore original accuracy
                Double originalAccuracy = originalAccuracyMap.get(entity);
                if (originalAccuracy != null) {
                    entity.setAccuracy(originalAccuracy);
                }
                entity.removeStatusEffect("Stun");
                System.out.println(entity.getName() + "'s accuracy has been restored!");
                battlePanel.setBattleMessage(entity.getName() + " is no longer Stunned!");
                battlePanel.repaint();
            }
            stunnedEntities.clear();
            originalAccuracyMap.clear();
        }
    }

    /** Apply slow to an entity (prevents movement for 1 turn) */
    public void applySlow(Entity entity) {
        if (!slowedEntities.contains(entity)) {
            slowedEntities.add(entity);
            entity.addStatusEffect(new Slow(1));
            System.out.println(entity.getName() + " has been SLOWED! Cannot move this turn!");
            battlePanel.setBattleMessage(entity.getName() + " is Slowed and cannot move this turn!");
            battlePanel.repaint();
        }
    }

    /** Check if an entity is slowed */
    private boolean isSlowed(Entity entity) {
        return slowedEntities.contains(entity);
    }

    /** Remove slow effect from an entity after their turn */
    private void removeSlow(Entity entity) {
        if (slowedEntities.contains(entity)) {
            slowedEntities.remove(entity);
            entity.removeStatusEffect("Slow");
            System.out.println(entity.getName() + " is no longer Slowed!");
        }
    }

    // ─────────────────────────────────────────────────────────────
    // Helper Methods
    // ─────────────────────────────────────────────────────────────

    /** Get list of alive enemies */
    private List<Enemy> getAliveEnemies() {
        List<Enemy> alive = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (enemy.getHp() > 0) {
                alive.add(enemy);
            }
        }
        return alive;
    }

    private void endBattle(boolean playerWon) {
        if (!isBattleActive) return;

        isBattleActive = false;
        isWaitingForPlayerInput = false;
        isExecutingTurn = false;

        String endMessage = playerWon
                ? "All enemies defeated! Victory!"
                : player.getName() + " has been defeated! Game Over!";
        battlePanel.setBattleMessage(endMessage);
        battlePanel.repaint();

        // Reset battle buffs
        if (player instanceof Swordsman) {
            ((Swordsman) player).resetBattleBuffs();
        } else if (player instanceof Ranger) {
            ((Ranger) player).resetBattleBuffs();
        } else if (player instanceof Mage) {
            ((Mage) player).resetBattleBuffs();
        }

        int delay = playerWon ? 1500 : 2500;

        Timer endTimer = new Timer(delay, e -> {
            if (onBattleEnd != null) {
                onBattleEnd.run(); // This handles BOTH win and lose — GamePanel decides
            }
            // Only dispose if no callback handled it
            Window window = SwingUtilities.getWindowAncestor(battlePanel);
            if (window != null && onBattleEnd == null) {
                window.dispose();
            }
        });
        endTimer.setRepeats(false);
        endTimer.start();
    }

    // ─────────────────────────────────────────────────────────────
    // Getters and Setters
    // ─────────────────────────────────────────────────────────────

    public boolean isBattleActive() {
        return isBattleActive;
    }

    public void setOnBattleEnd(Runnable callback) {
        this.onBattleEnd = callback;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}