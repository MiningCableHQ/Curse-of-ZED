package Combat;

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
        if (target != null) {
            // Target already selected (for self-targeting items)
            pendingItem = item;
            hasPendingItem = false;
            executeItemTurn(item, target);
        } else {
            // Need target selection
            pendingItem = item;
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

        // Display message
        String message = player.getName() + " used " + item.getName();
        battlePanel.setBattleMessage(message);
        battlePanel.repaint();

        Timer executeTimer = new Timer(MESSAGE_DELAY, e -> {
            if (!isBattleActive) return;

            String resultMessage;

            // Check item target type
            if (item.getTargetType() == Item.TargetType.SELF) {
                // Self-targeting item
                item.useItem(player);
                resultMessage = player.getName() + " used " + item.getName() + "!";
            } else if (item.getTargetType() == Item.TargetType.ENEMY && target != null) {
                // Single enemy target
                double beforeHp = target.getHp();
                item.useItem(target);
                double afterHp = target.getHp();
                double effectAmount = beforeHp - afterHp;

                if (effectAmount > 0) {
                    resultMessage = player.getName() + " used " + item.getName() + " on " +
                            target.getName() + " and dealt " + String.format("%d", (int)effectAmount) + " damage!";
                } else {
                    resultMessage = player.getName() + " used " + item.getName() + " on " + target.getName();
                }
            } else if (item.getTargetType() == Item.TargetType.ALL_ENEMIES) {
                // AoE item
                for (Enemy enemy : enemies) {
                    if (enemy.getHp() > 0) {
                        item.useItem(enemy);
                    }
                }
                resultMessage = player.getName() + " used " + item.getName() + " on all enemies!";
            } else {
                resultMessage = player.getName() + " used " + item.getName() + "!";
            }

            battlePanel.setBattleMessage(resultMessage);
            battlePanel.repaint();
            battlePanel.updateTargetButtonStates();

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

    /** Process the next turn in the cycle */
    private void processNextTurn() {
        if (!isBattleActive) return;

        // Check if we've completed the current round
//        if (currentTurnIndex >= currentTurnOrder.size()) {
//            // End of round - deduct speed from all entities
//            deductSpeedFromAll();
//
//            // Remove defeated entities from turn order for next round
//            currentTurnOrder.removeIf(entity -> entity.getHp() < 1);
//
//            // Check if cycle should end
//            if (shouldEndCycle()) {
//                endCycle();
//                return;
//            } else {
//                // Start new round within the same cycle
//                currentTurnIndex = 0;
//                calculateTurnOrder(); // Recalculate order with reduced speeds
//                processNextTurn();
//                return;
//            }
//        }

        Entity currentEntity = currentTurnOrder.get(currentTurnIndex);

        // Skip if entity is defeated
        if (currentEntity.getHp() < 1) {
            currentTurnIndex++;
            processNextTurn();
            return;
        }

        System.out.println("\n--- Turn: " + currentEntity.getName() + " ---");

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
    private void moveToNextTurn() {
        if (!isBattleActive) return;

        currentTurnIndex++;

        if (currentTurnIndex >= currentTurnOrder.size()) {
            deductSpeedFromAll();

            currentTurnOrder.removeIf(entity -> entity.getHp() < 1);

            if (shouldEndCycle()) {
                endCycle();
                return;
            } else {
                currentTurnIndex = 0;
                calculateTurnOrder();
            }
        }

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
                battlePanel.setBattleMessage(move.getMessage());
                battlePanel.repaint();

            } else if (move.getTargetType() == Move.TargetType.ALL_ENEMIES) {
                for (Enemy enemy : enemies) {
                    if (enemy.getHp() > 0) {
                        Move.currentTarget = enemy;
                        move.execute(player);
                    }
                }
                Move.currentTarget = null;
                battlePanel.setBattleMessage(move.getMessage());
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
                    move.execute(player);
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