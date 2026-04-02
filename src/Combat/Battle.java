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
import java.util.List;
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

    // Random for enemy AI
    private final Random random = new Random();

    // Callback for battle end
    private Runnable onBattleEnd;

    // Delay constants
    private static final int MESSAGE_DELAY = 1500;
    private static final int TURN_DELAY = 1000;

    // Store the chosen move for the round
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

    /** Execute a full round after player has chosen their move */
    private void executeRound() {
        if (!isBattleActive) return;

        isExecutingRound = true;

        // Get all alive entities
        List<Entity> allEntities = new ArrayList<>();
        if (player.getHp() > 0) {
            allEntities.add(player);
        }
        for (Enemy enemy : enemies) {
            if (enemy.getHp() > 0) {
                allEntities.add(enemy);
            }
        }

        // Sort by speed (fastest to slowest)
        allEntities.sort((e1, e2) -> Double.compare(e2.getSpeed(), e1.getSpeed()));

        System.out.println("DEBUG: Round Turn Order (Speed-based):");
        for (Entity e : allEntities) {
            System.out.println("DEBUG: " + e.getName() + " (Speed: " + e.getSpeed() + ")");
        }

        // Process each entity's turn
        processRoundTurn(allEntities, 0);
    }

    /** Process each turn in the round */
    private void processRoundTurn(List<Entity> turnOrder, int currentIndex) {
        if (!isBattleActive) return;

        if (currentIndex >= turnOrder.size()) {
            // Round complete, start next player decision phase
            isExecutingRound = false;
            chosenMove = null;
            chosenTarget = null;

            // Check if battle should continue
            if (getAliveEnemies().isEmpty()) {
                endBattle(true);
            } else if (player.getHp() <= 0) {
                endBattle(false);
            } else {
                Timer roundDelay = new Timer(TURN_DELAY, e -> startPlayerDecisionPhase());
                roundDelay.setRepeats(false);
                roundDelay.start();
            }
            return;
        }

        Entity currentEntity = turnOrder.get(currentIndex);

        // Skip if entity is defeated
        if (currentEntity.getHp() <= 0) {
            processRoundTurn(turnOrder, currentIndex + 1);
            return;
        }

        System.out.println("DEBUG: " + currentEntity.getName() + "'s turn");

        if (currentEntity instanceof Player) {
            // Player executes their chosen move
            executePlayerRoundMove((Player) currentEntity, turnOrder, currentIndex);
        } else if (currentEntity instanceof Enemy) {
            // Enemy executes their move
            executeEnemyRoundMove((Enemy) currentEntity, turnOrder, currentIndex);
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
            executeRound();
        } else if (move.getTargetType() == Move.TargetType.ALL_ENEMIES) {
            // AoE move - no specific target needed
            chosenMove = move;
            chosenTarget = null;
            executeRound();
        } else if (getAliveEnemies().size() > 1 && move.getTargetType() == Move.TargetType.ENEMY) {
            // Need target selection
            battlePanel.showTargetSelection(move);
        } else {
            // Single enemy
            chosenMove = move;
            chosenTarget = getAliveEnemies().get(0);
            executeRound();
        }
    }

    /** Handle player selecting a target for a move */
    public void handleTargetSelected(int enemyIndex) {
        if (!isBattleActive || !isWaitingForPlayerInput) return;

        isWaitingForPlayerInput = false;

        if (pendingMove != null && enemyIndex < enemies.size()) {
            chosenMove = pendingMove;
            chosenTarget = enemies.get(enemyIndex);
            pendingMove = null;
            executeRound();
        }
    }

    /** Cancels target selection */
    public void cancelTargetSelection() {
        if (!isBattleActive) return;

        isWaitingForPlayerInput = false;
        pendingMove = null;
        chosenMove = null;
        chosenTarget = null;

        // Return to decision phase
        Timer delay = new Timer(100, e -> startPlayerDecisionPhase());
        delay.setRepeats(false);
        delay.start();
    }

    /** Execute player's move during the round */
    private void executePlayerRoundMove(Player player, List<Entity> turnOrder, int currentIndex) {
        if (chosenMove == null) {
            // Should not happen, but just in case
            processRoundTurn(turnOrder, currentIndex + 1);
            return;
        }

        final Move move = chosenMove;
        final Enemy target = chosenTarget;  // Make a final copy for the lambda

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
                // Self-targeting move
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
                }

                battlePanel.setBattleMessage(message);
                battlePanel.repaint();

            } else if (move.getTargetType() == Move.TargetType.ALL_ENEMIES) {
                // Multi-target move
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
                // Single target move - use the final target variable
                Enemy currentTarget = target;

                if (currentTarget == null || currentTarget.getHp() <= 0) {
                    currentTarget = getAliveEnemies().get(0);
                }

                if (currentTarget != null && currentTarget.getHp() > 0) {
                    double beforeHp = currentTarget.getHp();
                    Move.currentTarget = currentTarget;
                    move.execute(player);
                    Move.currentTarget = null;
                    double afterHp = currentTarget.getHp();
                    double damageDealt = beforeHp - afterHp;

                    String message;
                    if (damageDealt > 0) {
                        message = player.getName() + " used " + move.getName() + " on " +
                                currentTarget.getName() + " and dealt " + String.format("%.1f", damageDealt) + " damage!";
                    } else {
                        message = player.getName() + " used " + move.getName() + " on " +
                                currentTarget.getName() + " but it had no effect!";
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

            // Move to next entity in turn order
            Timer nextTimer = new Timer(TURN_DELAY, ev -> {
                processRoundTurn(turnOrder, currentIndex + 1);
            });
            nextTimer.setRepeats(false);
            nextTimer.start();
        });
        executeTimer.setRepeats(false);
        executeTimer.start();
    }

    // ─────────────────────────────────────────────────────────────
    // Enemy Actions
    // ─────────────────────────────────────────────────────────────

    /** Execute enemy's move during the round */
    private void executeEnemyRoundMove(Enemy enemy, List<Entity> turnOrder, int currentIndex) {
        Move enemyMove = enemy.selectMove();

        if (enemyMove == null) {
            processRoundTurn(turnOrder, currentIndex + 1);
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
            if (player.getHp() <= 0) {
                if (player.getHp() < 0) player.setHp(0);
                endBattle(false);
                return;
            }

            // Move to next turn
            Timer nextTimer = new Timer(TURN_DELAY, ev -> {
                processRoundTurn(turnOrder, currentIndex + 1);
            });
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
                    // Enemy gets a free turn
                    List<Entity> enemiesOnly = new ArrayList<>();
                    for (Enemy enemy : enemies) {
                        if (enemy.getHp() > 0) {
                            enemiesOnly.add(enemy);
                        }
                    }

                    enemiesOnly.sort((e1, e2) -> Double.compare(e2.getSpeed(), e1.getSpeed()));
                    processRoundTurn(enemiesOnly, 0);
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
        isExecutingRound = false;

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