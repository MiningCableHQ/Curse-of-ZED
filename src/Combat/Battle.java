package Combat;

import Entities.Characters.Player;
import Entities.Characters.Ranger;
import Entities.Characters.Swordsman;
import Entities.Characters.Mage;
import Entities.Enemies.Enemy;
import Entities.Entity;
import Moves.Move;

import javax.swing.*;
import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    private boolean isExecutingRound = false;

    // Store chosen move for player's turn
    private Move chosenMove;
    private Enemy chosenTarget;

    // Random for enemy AI
    private final Random random = new Random();

    // Callback for battle end
    private Runnable onBattleEnd;

    // Delay constants
    private static final int MESSAGE_DELAY = 1500;
    private static final int TURN_DELAY = 1000;
    private static final int SPEED_DEDUCTION = 20;

    // Track which entity is currently acting in the turn queue
    private int currentTurnIndex = 0;
    private List<Entity> currentTurnOrder = new ArrayList<>();
    private Map<Entity, Double> tempSpeeds = new HashMap<>();

    // ─────────────────────────────────────────────────────────────
    // Constructor
    // ─────────────────────────────────────────────────────────────
    public Battle(BattlePanel battlePanel, Player player, List<Enemy> enemies) {
        this.battlePanel = battlePanel;
        this.player = player;
        this.enemies = new ArrayList<>(enemies);
    }

    // ─────────────────────────────────────────────────────────────
    // Battle Control Methods
    // ─────────────────────────────────────────────────────────────

    /** Start the battle */
    public void startBattle() {
        isBattleActive = true;
        startPlayerDecisionPhase();
    }

    /** Start the phase where player chooses their move */
    private void startPlayerDecisionPhase() {
        if (!isBattleActive) return;

        isWaitingForPlayerInput = true;
        battlePanel.showMainMenu();
        battlePanel.setBattleMessage("What will " + player.getName() + " do?");
        battlePanel.setButtonsEnabled(true);
    }

    /** Execute a full round with speed-based turn order within the round */
    private void executeRound() {
        if (!isBattleActive) return;

        isExecutingRound = true;
        currentTurnIndex = 0;

        // Reset temporary speeds for this round
        tempSpeeds.clear();

        // Get all alive entities
        List<Entity> allEntities = new ArrayList<>();
        if (player.getHp() > 0) {
            allEntities.add(player);
            tempSpeeds.put(player, player.getSpeed());
        }
        for (Enemy enemy : enemies) {
            if (enemy.getHp() > 0) {
                allEntities.add(enemy);
                tempSpeeds.put(enemy, enemy.getSpeed());
            }
        }

        System.out.println("DEBUG: Round starting - Temporary speeds:");
        for (Map.Entry<Entity, Double> entry : tempSpeeds.entrySet()) {
            System.out.println("DEBUG: " + entry.getKey().getName() + " temp speed: " + entry.getValue());
        }

        // Build initial turn order based on temporary speeds
        buildTurnOrder();

        // Start processing turns
        processNextTurn();
    }

    /** Build the turn order based on current temporary speeds */
    private void buildTurnOrder() {
        currentTurnOrder.clear();

        // Add all entities that still have speed > 0
        for (Map.Entry<Entity, Double> entry : tempSpeeds.entrySet()) {
            if (entry.getValue() > 0) {
                currentTurnOrder.add(entry.getKey());
            }
        }

        // Sort by temporary speed (highest first)
        currentTurnOrder.sort((a, b) -> {
            double speedA = tempSpeeds.getOrDefault(a, 0.0);
            double speedB = tempSpeeds.getOrDefault(b, 0.0);

            if (speedA != speedB) {
                return Double.compare(speedB, speedA);
            }
            // Tie-breaking: Player always goes first
            if (a instanceof Player && !(b instanceof Player)) return -1;
            if (!(a instanceof Player) && b instanceof Player) return 1;
            return 0;
        });

        System.out.println("DEBUG: Turn order built:");
        for (int i = 0; i < currentTurnOrder.size(); i++) {
            Entity e = currentTurnOrder.get(i);
            System.out.println("DEBUG: " + i + ": " + e.getName() + " (temp speed: " + tempSpeeds.get(e) + ")");
        }
    }

    /** Process the next turn in the queue */
    private void processNextTurn() {
        if (!isBattleActive) return;

        // Check if battle should end
        if (getAliveEnemies().isEmpty()) {
            endBattle(true);
            return;
        }
        if (player.getHp() < 1) {
            endBattle(false);
            return;
        }

        // Rebuild turn order if needed (in case speeds changed)
        buildTurnOrder();

        // Check if we've processed all turns in this round
        if (currentTurnIndex >= currentTurnOrder.size()) {
            // Round complete, start next player decision phase
            finishRound();
            return;
        }

        Entity currentEntity = currentTurnOrder.get(currentTurnIndex);

        // Skip if entity is defeated
        if (currentEntity.getHp() < 1) {
            currentTurnIndex++;
            processNextTurn();
            return;
        }

        System.out.println("DEBUG: " + currentEntity.getName() + "'s turn (Index: " + currentTurnIndex + ")");

        if (currentEntity instanceof Player) {
            // Player's turn - need to wait for move selection
            isWaitingForPlayerTurn = true;
            battlePanel.showMainMenu();
            battlePanel.setBattleMessage(player.getName() + "'s turn! Choose a move.");
            battlePanel.setButtonsEnabled(true);
            // The move will be handled via handlePlayerMove, which will call continueAfterPlayerTurn
        } else if (currentEntity instanceof Enemy) {
            // Enemy's turn - execute move immediately
            executeEnemyRoundMove((Enemy) currentEntity);
        }
    }

    // Flag to track if we're waiting for player turn during round
    private boolean isWaitingForPlayerTurn = false;

    /** Continue after player has selected their move */
    private void continueAfterPlayerTurn() {
        if (!isBattleActive) return;

        // Deduct speed from player
        Entity playerEntity = player;
        double currentSpeed = tempSpeeds.getOrDefault(playerEntity, playerEntity.getSpeed());
        double newSpeed = Math.max(0, currentSpeed - SPEED_DEDUCTION);
        tempSpeeds.put(playerEntity, newSpeed);
        System.out.println("DEBUG: " + playerEntity.getName() + " speed reduced: " + currentSpeed + " -> " + newSpeed);

        // Move to next turn
        currentTurnIndex++;

        // Process next turn after delay
        Timer nextTimer = new Timer(TURN_DELAY, e -> processNextTurn());
        nextTimer.setRepeats(false);
        nextTimer.start();
    }

    /** Execute enemy's move during the round */
    private void executeEnemyRoundMove(Enemy enemy) {
        Move enemyMove = enemy.selectMove();

        if (enemyMove == null) {
            // No move selected, just continue
            continueAfterEnemyTurn(enemy);
            return;
        }

        battlePanel.hideBattleButtons();
        battlePanel.hideTargetButtons();

        // Display message
        String message = enemy.getName() + " used " + enemyMove.getName();
        battlePanel.setBattleMessage(message);
        battlePanel.repaint();

        // Execute move after delay
        Timer executeTimer = new Timer(MESSAGE_DELAY, e -> {
            if (!isBattleActive) return;

            double beforeHp = player.getHp();
            Move.currentTarget = player;
            enemyMove.execute(enemy);
            Move.currentTarget = null;
            double afterHp = player.getHp();
            double damageDealt = beforeHp - afterHp;

            String resultMessage;
            if (damageDealt > 0) {
                resultMessage = enemy.getName() + " used " + enemyMove.getName() + " and dealt " +
                        String.format("%.0f", damageDealt) + " damage!";
            } else {
                resultMessage = enemy.getName() + " used " + enemyMove.getName();
            }

            battlePanel.setBattleMessage(resultMessage);
            battlePanel.repaint();

            // Check if player is defeated
            if (player.getHp() < 1) {
                if (player.getHp() < 0) player.setHp(0);
                endBattle(false);
                return;
            }

            continueAfterEnemyTurn(enemy);
        });
        executeTimer.setRepeats(false);
        executeTimer.start();
    }

    /** Continue after enemy has taken their turn */
    private void continueAfterEnemyTurn(Enemy enemy) {
        if (!isBattleActive) return;

        // Deduct speed from enemy
        double currentSpeed = tempSpeeds.getOrDefault(enemy, enemy.getSpeed());
        double newSpeed = Math.max(0, currentSpeed - SPEED_DEDUCTION);
        tempSpeeds.put(enemy, newSpeed);
        System.out.println("DEBUG: " + enemy.getName() + " speed reduced: " + currentSpeed + " -> " + newSpeed);

        // Move to next turn
        currentTurnIndex++;

        // Process next turn after delay
        Timer nextTimer = new Timer(TURN_DELAY, e -> processNextTurn());
        nextTimer.setRepeats(false);
        nextTimer.start();
    }

    /** Finish the round and start player decision phase */
    private void finishRound() {
        if (!isBattleActive) return;

        isExecutingRound = false;
        isWaitingForPlayerTurn = false;
        chosenMove = null;
        chosenTarget = null;
        pendingMove = null;
        currentTurnOrder.clear();
        tempSpeeds.clear();

        System.out.println("DEBUG: Round complete - Starting player decision phase");

        // Check if battle should continue
        if (getAliveEnemies().isEmpty()) {
            endBattle(true);
        } else if (player.getHp() < 1) {
            endBattle(false);
        } else {
            Timer roundDelay = new Timer(TURN_DELAY, e -> startPlayerDecisionPhase());
            roundDelay.setRepeats(false);
            roundDelay.start();
        }
    }

    // ─────────────────────────────────────────────────────────────
    // Player Actions
    // ─────────────────────────────────────────────────────────────

    /** Handle player selecting a move during decision phase */
    public void handlePlayerMove(Move move) {
        if (!isBattleActive) return;

        // If waiting for player turn during round execution
        if (isWaitingForPlayerTurn) {
            pendingMove = move;
            battlePanel.hideMainMenu();
            battlePanel.setButtonsEnabled(false);

            // Check if move requires target selection
            if (move.getTargetType() == Move.TargetType.SELF) {
                chosenMove = move;
                chosenTarget = null;
                isWaitingForPlayerTurn = false;
                executePlayerRoundMove();
            } else if (move.getTargetType() == Move.TargetType.ALL_ENEMIES) {
                chosenMove = move;
                chosenTarget = null;
                isWaitingForPlayerTurn = false;
                executePlayerRoundMove();
            } else if (getAliveEnemies().size() > 1 && move.getTargetType() == Move.TargetType.ENEMY) {
                battlePanel.showTargetSelection(move);
            } else {
                chosenMove = move;
                chosenTarget = getAliveEnemies().get(0);
                isWaitingForPlayerTurn = false;
                executePlayerRoundMove();
            }
            return;
        }

        // Original decision phase handling (start of round)
        if (!isWaitingForPlayerInput) return;

        isWaitingForPlayerInput = false;
        pendingMove = move;
        battlePanel.hideMainMenu();
        battlePanel.setButtonsEnabled(false);

        // Check if move requires target selection
        if (move.getTargetType() == Move.TargetType.SELF) {
            chosenMove = move;
            chosenTarget = null;
            executeRound();
        } else if (move.getTargetType() == Move.TargetType.ALL_ENEMIES) {
            chosenMove = move;
            chosenTarget = null;
            executeRound();
        } else if (getAliveEnemies().size() > 1 && move.getTargetType() == Move.TargetType.ENEMY) {
            battlePanel.showTargetSelection(move);
        } else {
            chosenMove = move;
            chosenTarget = getAliveEnemies().get(0);
            executeRound();
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

        if (isWaitingForPlayerTurn) {
            isWaitingForPlayerTurn = false;
            executePlayerRoundMove();
        } else {
            executeRound();
        }
    }

    /** Execute player's move during the round */
    private void executePlayerRoundMove() {
        if (chosenMove == null) {
            continueAfterPlayerTurn();
            return;
        }

        final Move move = chosenMove;
        final Enemy target = chosenTarget;

        battlePanel.hideBattleButtons();
        battlePanel.hideTargetButtons();

        // Display message that player is about to act
        String startMessage = player.getName() + " is about to use " + move.getName() + "!";
        battlePanel.setBattleMessage(startMessage);
        battlePanel.repaint();

        Timer executeTimer = new Timer(MESSAGE_DELAY, e -> {
            if (!isBattleActive) return;

            // Execute the move based on target type
            if (move.getTargetType() == Move.TargetType.SELF) {
                double beforeHp = player.getHp();
                Move.currentTarget = player;
                move.execute(player);
                Move.currentTarget = null;

                String message = player.getName() + " used " + move.getName();

                if (move.getName().equals("Iron Stance") && player instanceof Swordsman) {
                    Swordsman swordsman = (Swordsman) player;
                    message = player.getName() + " used " + move.getName() + "! Defense increased to " +
                            String.format("%.0f", swordsman.getDefense());
                } else if (move.getName().equals("Windstep") && player instanceof Ranger) {
                    Ranger ranger = (Ranger) player;
                    message = player.getName() + " used " + move.getName() + "! Speed increased to " +
                            String.format("%.0f", ranger.getSpeed());
                } else if (move.getName().equals("Empower") && player instanceof Mage) {
                    Mage mage = (Mage) player;
                    message = player.getName() + " used " + move.getName() + "! Attack increased to " +
                            String.format("%.0f", mage.getAttack());
                } else if (move.getName().equals("Revitalize") && player instanceof Mage) {
                    double afterHp = player.getHp();
                    double healAmount = afterHp - beforeHp;
                    if (healAmount > 0) {
                        message = player.getName() + " used " + move.getName() + " and healed " +
                                String.format("%.1f", healAmount) + " HP!";
                    } else {
                        message = player.getName() + " used " + move.getName() + " but was already at full health!";
                    }
                }

                battlePanel.setBattleMessage(message);
                battlePanel.repaint();

            } else if (move.getTargetType() == Move.TargetType.ALL_ENEMIES) {
                for (Enemy enemy : enemies) {
                    if (enemy.getHp() > 0) {
                        Move.currentTarget = enemy;
                        move.execute(player);
                    }
                }
                Move.currentTarget = null;

                String message = player.getName() + " used " + move.getName() + " on all enemies!";
                battlePanel.setBattleMessage(message);
                battlePanel.repaint();
                battlePanel.updateTargetButtonStates();

            } else {
                Enemy currentTarget = target;

                if (currentTarget == null || currentTarget.getHp() <= 0) {
                    List<Enemy> aliveEnemies = getAliveEnemies();
                    if (!aliveEnemies.isEmpty()) {
                        currentTarget = aliveEnemies.get(0);
                    } else {
                        continueAfterPlayerTurn();
                        return;
                    }
                }

                if (currentTarget != null && currentTarget.getHp() > 0) {
                    double beforeTargetHp = currentTarget.getHp();
                    double beforePlayerHp = player.getHp();

                    Move.currentTarget = currentTarget;
                    move.execute(player);
                    Move.currentTarget = null;

                    double afterTargetHp = currentTarget.getHp();
                    double afterPlayerHp = player.getHp();
                    double damageDealt = beforeTargetHp - afterTargetHp;
                    double playerHpLost = beforePlayerHp - afterPlayerHp;

                    String message;
                    if (damageDealt > 0) {
                        if (move.getName().equals("Sacrificial Blade") && player instanceof Swordsman && playerHpLost > 0) {
                            message = player.getName() + " used " + move.getName() + " on " +
                                    currentTarget.getName() + " and dealt " + String.format("%.1f", damageDealt) +
                                    " damage, sacrificing " + String.format("%.1f", playerHpLost) + " HP!";
                        } else {
                            message = player.getName() + " used " + move.getName() + " on " +
                                    currentTarget.getName() + " and dealt " + String.format("%.1f", damageDealt) + " damage!";
                        }
                    } else {
                        if (move.getName().equals("Sacrificial Blade") && player instanceof Swordsman && playerHpLost > 0) {
                            message = player.getName() + " used " + move.getName() + " on " +
                                    currentTarget.getName() + " but it had no effect, sacrificing " +
                                    String.format("%.1f", playerHpLost) + " HP in vain!";
                        } else {
                            message = player.getName() + " used " + move.getName() + " on " +
                                    currentTarget.getName() + " but it had no effect!";
                        }
                    }

                    battlePanel.setBattleMessage(message);
                    battlePanel.repaint();
                    battlePanel.updateTargetButtonStates();
                }
            }

            // Check if battle should end
            if (getAliveEnemies().isEmpty()) {
                endBattle(true);
                return;
            }

            if (player.getHp() < 1) {
                endBattle(false);
                return;
            }

            // Continue to next turn
            continueAfterPlayerTurn();
        });
        executeTimer.setRepeats(false);
        executeTimer.start();
    }

    /** Cancel target selection */
    public void cancelTargetSelection() {
        if (!isBattleActive) return;

        System.out.println("Target selection cancelled - resetting state");

        pendingMove = null;
        chosenMove = null;
        chosenTarget = null;

        if (isWaitingForPlayerTurn) {
            isWaitingForPlayerTurn = false;
            // Return to player decision within the round
            battlePanel.showFightMenu();
            battlePanel.setBattleMessage(player.getName() + "'s turn! Choose a move.");
            battlePanel.setButtonsEnabled(true);
        } else {
            isWaitingForPlayerInput = true;
            battlePanel.showFightMenu();
        }
    }

    /** Handle player running away */
    public void handleRunAway() {
        if (!isBattleActive) return;

        if (isWaitingForPlayerTurn) {
            battlePanel.setBattleMessage("You cannot run away during battle!");
            battlePanel.repaint();
            return;
        }

        if (!isWaitingForPlayerInput) return;

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
                    // Enemy gets a free turn - just let one enemy act
                    List<Enemy> aliveEnemies = getAliveEnemies();
                    if (!aliveEnemies.isEmpty()) {
                        executeEnemyRoundMove(aliveEnemies.get(0));
                    } else {
                        startPlayerDecisionPhase();
                    }
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

    /** End the battle */
    private void endBattle(boolean playerWon) {
        if (!isBattleActive) return;

        isBattleActive = false;
        isWaitingForPlayerInput = false;
        isWaitingForPlayerTurn = false;
        isExecutingRound = false;
        currentTurnOrder.clear();
        tempSpeeds.clear();

        String endMessage = playerWon ? "All enemies have been defeated! You win!" :
                player.getName() + " has been defeated! Game Over!";
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

        Timer endTimer = new Timer(2000, e -> {
            if (onBattleEnd != null) {
                onBattleEnd.run();
            }
            Window window = SwingUtilities.getWindowAncestor(battlePanel);
            if (window != null) {
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