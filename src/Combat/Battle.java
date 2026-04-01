package Combat;

import Entities.Characters.Player;
import Entities.Characters.Ranger;
import Entities.Characters.Swordsman;
import Entities.Characters.Mage;
import Entities.Enemies.Enemy;
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
    private boolean isPlayerTurn = true;
    private boolean isExecutingAction = false;
    private Move pendingMove;
    private Enemy selectedTarget;
    private boolean playerUsedItem = false;

    // Random for enemy AI
    private final Random random = new Random();

    // Callback for battle end
    private Runnable onBattleEnd;

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
        battlePanel.hideMainMenu();
        battlePanel.hideBattleButtons();
        determineFirstTurn();
    }

    /** Determine who goes first based on speed */
    private void determineFirstTurn() {
        if (playerUsedItem) {
            // Player used an item, guaranteed to go first
            isPlayerTurn = true;
            playerUsedItem = false;
            startPlayerTurn();
        } else {
            // Compare speed stats
            double playerSpeed = player.getSpeed();
            double highestEnemySpeed = getHighestEnemySpeed();

            if (playerSpeed >= highestEnemySpeed) {
                isPlayerTurn = true;
                startPlayerTurn();
            } else {
                isPlayerTurn = false;
                startEnemyTurn();
            }
        }
    }

    /** Get the highest speed among all enemies */
    private double getHighestEnemySpeed() {
        double highestSpeed = 0;
        for (Enemy enemy : enemies) {
            if (enemy.getHp() > 0) {
                highestSpeed = Math.max(highestSpeed, enemy.getSpeed());
            }
        }
        return highestSpeed;
    }

    /** Start player's turn */
    private void startPlayerTurn() {
        if (!isBattleActive) return;

        isExecutingAction = false;
        battlePanel.showMainMenu();
        battlePanel.setBattleMessage("What will " + player.getName() + " do?");
        battlePanel.setButtonsEnabled(true);
    }

    /** Start enemy's turn */
    private void startEnemyTurn() {
        if (!isBattleActive) return;

        isExecutingAction = true;
        battlePanel.setButtonsEnabled(false);
        battlePanel.setBattleMessage("Enemy turn...");

        // Small delay before enemy acts
        Timer enemyTurnDelay = new Timer(1000, e -> {
            if (isBattleActive) {
                executeEnemyTurn();
            }
        });
        enemyTurnDelay.setRepeats(false);
        enemyTurnDelay.start();
    }

    // ─────────────────────────────────────────────────────────────
    // Player Actions
    // ─────────────────────────────────────────────────────────────

    /** Handle player selecting a move */
    public void handlePlayerMove(Move move) {
        if (isExecutingAction || !isPlayerTurn || !isBattleActive) return;

        isExecutingAction = true;
        pendingMove = move;

        // Check if move requires target selection
        if (move.getTargetType() == Move.TargetType.SELF) {
            // Self-targeting move
            executePlayerMove(move, null);
        } else if (move.getTargetType() == Move.TargetType.ALL_ENEMIES) {
            // AoE move - targets all enemies
            executePlayerMove(move, null);
        } else if (enemies.size() > 1 && move.getTargetType() == Move.TargetType.ENEMY) {
            // Single target move with multiple enemies - need target selection
            battlePanel.showTargetSelection(move);
        } else {
            // Single target move with only one enemy
            executePlayerMove(move, enemies.get(0));
        }
    }

    /** Handle player selecting a target for a move */
    public void handleTargetSelected(int enemyIndex) {
        if (pendingMove != null && enemyIndex < enemies.size()) {
            selectedTarget = enemies.get(enemyIndex);
            executePlayerMove(pendingMove, selectedTarget);
            pendingMove = null;
        }
    }

    /** Cancels target selection of a target*/
    public void cancelTargetSelection() {
        isExecutingAction = false;
        pendingMove = null;
        selectedTarget = null;
    }

    /** Execute the player's selected move */
    private void executePlayerMove(Move move, Enemy target) {

        battlePanel.setButtonsEnabled(false);
        battlePanel.hideBattleButtons();
        battlePanel.hideTargetButtons();

        // Handle different target types
        if (move.getTargetType() == Move.TargetType.SELF) {
            // Self-targeting moves (buffs, heals)
            Move.currentTarget = player;
            move.execute(player);
            Move.currentTarget = null;

            String message = player.getName() + " uses " + move.getName() + "!";


            // Handle class-specific buff messages
            if (move.getName().equals("Iron Stance") && player instanceof Swordsman) {
                Swordsman swordsman = (Swordsman) player;
                message = player.getName() + " uses " + move.getName() + "! Defense increased to " +
                        String.format("%.0f", swordsman.getDefense());
            } else if (move.getName().equals("Windstep") && player instanceof Ranger) {
                Ranger ranger = (Ranger) player;
                message = player.getName() + " uses " + move.getName() + "! Speed increased to " +
                        String.format("%.0f", ranger.getSpeed());
            } else if (move.getName().equals("Empower") && player instanceof Mage) {
                Mage mage = (Mage) player;
                message = player.getName() + " uses " + move.getName() + "! Attack increased to " +
                        String.format("%.0f", mage.getAttack());
            }

            battlePanel.setBattleMessage(message);
            battlePanel.repaint();

            // After player's move, check battle status and switch turns
            checkBattleStatusAfterPlayerMove();

        } else if (move.getTargetType() == Move.TargetType.ALL_ENEMIES) {
            // Multi-target move
            StringBuilder damageMessage = new StringBuilder();

            for (Enemy enemy : enemies) {
                if (enemy.getHp() > 0) {
                    double beforeHp = enemy.getHp();
                    Move.currentTarget = enemy;
                    move.execute(player);
                    double afterHp = enemy.getHp();
                    double damageDealt = beforeHp - afterHp;

                    if (damageDealt > 0) {
                        damageMessage.append(enemy.getName()).append(": ").append(String.format("%.1f", damageDealt)).append(" dmg; ");
                    }
                }
            }
            Move.currentTarget = null;

            String message = player.getName() + " used " + move.getName() + "! " + damageMessage.toString();

            battlePanel.setBattleMessage(message);
            battlePanel.repaint();

            // Check battle status after move
            checkBattleStatusAfterPlayerMove();

        } else {
            // Single target move

            if (target == null || target.getHp() <= 0) {
                // Target is already defeated, find a valid target
                for (Enemy enemy : enemies) {
                    if (enemy.getHp() > 0) {
                        target = enemy;
                        break;
                    }
                }
            }

            if (target != null && target.getHp() > 0) {
                double beforeHp = target.getHp();

                Move.currentTarget = target;
                move.execute(player);
                Move.currentTarget = null;
                double afterHp = target.getHp();
                double damageDealt = beforeHp - afterHp;


                String message;
                if (damageDealt > 0) {
                    message = player.getName() + " used " + move.getName() + " on " +
                            target.getName() + " and dealt " + String.format("%.1f", damageDealt) + " damage!";
                } else {
                    message = player.getName() + " used " + move.getName() + " on " +
                            target.getName() + " but it had no effect!";
                }


                battlePanel.setBattleMessage(message);
                battlePanel.repaint();

                // Check battle status after move
                checkBattleStatusAfterPlayerMove();
            }
        }
        battlePanel.updateTargetButtonStates();
    }

    /** Handle player running away */
    public void handleRunAway() {
        if (isExecutingAction || !isPlayerTurn || !isBattleActive) return;

        isExecutingAction = true;
        battlePanel.setButtonsEnabled(false);

        // Random chance to escape (80% success rate)
        boolean escapeSuccess = random.nextDouble() < 0.8;

        if (escapeSuccess) {
            battlePanel.setBattleMessage(player.getName() + " escaped from battle!");
            battlePanel.repaint();

            Timer escapeTimer = new Timer(1000, e -> {
                endBattle(true); // True indicates player escaped
            });
            escapeTimer.setRepeats(false);
            escapeTimer.start();
        } else {
            battlePanel.setBattleMessage(player.getName() + " failed to escape!");
            battlePanel.repaint();

            Timer failTimer = new Timer(1500, e -> {
                // Enemy gets a free turn
                isPlayerTurn = false;
                startEnemyTurn();
            });
            failTimer.setRepeats(false);
            failTimer.start();
        }
    }

    /** Handle player using an item (potion, etc.) */
    public void handleUseItem(Runnable itemAction) {
        if (isExecutingAction || !isPlayerTurn || !isBattleActive) return;

        playerUsedItem = true;
        isExecutingAction = true;
        battlePanel.setButtonsEnabled(false);

        // Execute item action
        itemAction.run();

        // After using item, check battle status
        checkBattleStatusAfterPlayerMove();
    }

    // ─────────────────────────────────────────────────────────────
    // Enemy Actions
    // ─────────────────────────────────────────────────────────────

    /** Execute enemy turn for all alive enemies */
    private void executeEnemyTurn() {
        if (!isBattleActive) return;

        // Execute turn for each alive enemy
        for (Enemy enemy : enemies) {
            if (enemy.getHp() <= 0) continue;

            ArrayList<Move> enemyMoves = enemy.getMoves();

            if (enemyMoves != null && !enemyMoves.isEmpty()) {
                // Choose a random move for the enemy
                int moveIndex = random.nextInt(enemyMoves.size());
                Move enemyMove = enemyMoves.get(moveIndex);

                double beforeHp = player.getHp();
                Move.currentTarget = player;
                enemyMove.execute(enemy);
                Move.currentTarget = null;
                double afterHp = player.getHp();
                double damageDealt = beforeHp - afterHp;

                String message;
                if (damageDealt > 0) {
                    message = enemy.getName() + " used " + enemyMove.getName() + " and dealt " +
                            String.format("%.1f", damageDealt) + " damage!";
                } else {
                    message = enemy.getName() + " used " + enemyMove.getName() + " but it had no effect!";
                }

                battlePanel.setBattleMessage(message);
                battlePanel.repaint();

                // Check if player is defeated
                if (player.getHp() <= 0) {
                    endBattle(false); // False indicates player lost
                    return;
                }

                // Small delay between enemy moves
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        // Check if all enemies are defeated
        boolean allDefeated = true;
        for (Enemy enemy : enemies) {
            if (enemy.getHp() > 0) {
                allDefeated = false;
                break;
            }
        }

        if (allDefeated) {
            endBattle(true); // True indicates player won
        } else {
            // After enemy turn, switch back to player
            switchTurn();
        }
    }

    // ─────────────────────────────────────────────────────────────
    // Battle Flow Control
    // ─────────────────────────────────────────────────────────────

    /** Check battle status after player's move */
    private void checkBattleStatusAfterPlayerMove() {

        // Check if all enemies are defeated
        boolean allDefeated = true;
        for (Enemy enemy : enemies) {
            if (enemy.getHp() > 0) {
                allDefeated = false;
                break;
            }
        }

        if (allDefeated) {
            endBattle(true); // Player won
        } else {
            // After player's move, add a delay before switching to enemy turn
            Timer turnDelay = new Timer(1500, e -> {
                switchTurn();
            });
            turnDelay.setRepeats(false);
            turnDelay.start();
        }
    }

    /** Switch turns between player and enemies */
    private void switchTurn() {
        if (!isBattleActive) return;

        // Reset flags
        isExecutingAction = false;
        playerUsedItem = false;

        // Determine next turn based on current turn
        if (isPlayerTurn) {
            isPlayerTurn = false;
            startEnemyTurn();
        } else {
            isPlayerTurn = true;
            startPlayerTurn();
        }
    }

    /** End the battle */
    private void endBattle(boolean playerWon) {
        if (!isBattleActive) return;

        isBattleActive = false;

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

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void setOnBattleEnd(Runnable callback) {
        this.onBattleEnd = callback;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}