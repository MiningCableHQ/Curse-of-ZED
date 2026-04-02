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
        startPlayerTurn();
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
        if (isExecutingAction || !isBattleActive) return;

        isExecutingAction = true;
        pendingMove = move;

        // Hide main menu buttons immediately
        battlePanel.hideMainMenu();

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
        startPlayerTurn();
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

            String message = player.getName() + " used " + move.getName();

            // Handle class-specific buff messages
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
            } else {
                message = player.getName() + " used " + move.getName();
            }

            battlePanel.setBattleMessage(message);
            battlePanel.repaint();

        } else if (move.getTargetType() == Move.TargetType.ALL_ENEMIES) {
            // Multi-target move
            StringBuilder damageMessage = new StringBuilder();

            for (Enemy enemy : enemies) {
                if (enemy.getHp() > 0) {
                    double beforeHp = enemy.getHp();
                    Move.currentTarget = enemy;
                    move.execute(player);
                    double afterHp = enemy.getHp();
                }
            }
            Move.currentTarget = null;

            String message = player.getName() + " used " + move.getName() + " to all enemy(ies)!";


            battlePanel.setBattleMessage(message);
            battlePanel.repaint();
            battlePanel.updateTargetButtonStates();

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
                battlePanel.updateTargetButtonStates();
            }
        }
        // After player's move, check battle status and determine next turn
        checkBattleStatusAfterAction();
    }

    /** Handle player running away */
    public void handleRunAway() {
        if (isExecutingAction || !isBattleActive) return;

        isExecutingAction = true;
        battlePanel.setButtonsEnabled(false);
        battlePanel.hideMainMenu();

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
                startEnemyTurn();
            });
            failTimer.setRepeats(false);
            failTimer.start();
        }
    }

    /** Handle player using an item (potion, etc.) */
    public void handleUseItem(Runnable itemAction) {
        if (isExecutingAction || !isBattleActive) return;

        playerUsedItem = true;
        isExecutingAction = true;
        battlePanel.setButtonsEnabled(false);
        battlePanel.hideMainMenu();

        // Execute item action
        itemAction.run();

        // After using item, check battle status
        checkBattleStatusAfterAction();
    }

    // ─────────────────────────────────────────────────────────────
    // Enemy Actions
    // ─────────────────────────────────────────────────────────────

    /** Execute enemy turn for all alive enemies */
    private void executeEnemyTurn() {
        if (!isBattleActive) return;

        executeNextEnemyAction(0);
    }

    private void executeNextEnemyAction(int enemyIndex) {
        if (!isBattleActive || enemyIndex >= enemies.size()) {
            checkAllEnemiesActed();
            return;
        }

        Enemy enemy = enemies.get(enemyIndex);

        if (enemy.getHp() <= 0) {
            executeNextEnemyAction(enemyIndex + 1);
            return;
        }

        Move enemyMove = enemy.selectMove();

        if (enemyMove == null) {
            executeNextEnemyAction(enemyIndex + 1);
            return;
        }

        // Display the message that enemy is about to use a move
        String prepMessage = enemy.getName() + " used " + enemyMove.getName();
        battlePanel.setBattleMessage(prepMessage);
        battlePanel.repaint();

        // Timer to show message before executing
        Timer messageTimer = new Timer(2000, e -> {
            // Execute the move
            double beforeHp = player.getHp();
            Move.currentTarget = player;
            enemyMove.execute(enemy);
            Move.currentTarget = null;
            double afterHp = player.getHp();
            double damageDealt = beforeHp - afterHp;

            String message;
            if (damageDealt > 0) {
                message = enemy.getName() + " used " + enemyMove.getName() + " and dealt " +
                        String.format("%.0f", damageDealt) + " damage!";
            } else {
                message = enemy.getName() + " used " + enemyMove.getName();
            }

            battlePanel.setBattleMessage(message);
            battlePanel.repaint();

            // Check if player is defeated
            if (player.getHp() <= 0) {
                if(player.getHp() < 0) player.setHp(0);
                endBattle(false);
                return;
            }

            // Timer to delay before next enemy
            Timer nextEnemyTimer = new Timer(2000, ev -> {
                executeNextEnemyAction(enemyIndex + 1);
            });
            nextEnemyTimer.setRepeats(false);
            nextEnemyTimer.start();
        });
        messageTimer.setRepeats(false);
        messageTimer.start();
    }

    private void checkAllEnemiesActed() {
        // Check if all enemies are defeated
        boolean allDefeated = true;
        for (Enemy enemy : enemies) {
            if (enemy.getHp() > 0) {
                allDefeated = false;
                break;
            }
        }

        if (allDefeated) {
            endBattle(true);
        } else {
            switchToPlayerTurn();
        }
    }

    // ─────────────────────────────────────────────────────────────
    // Battle Flow Control
    // ─────────────────────────────────────────────────────────────

    /** Check battle status after any action and determine next turn */
    private void checkBattleStatusAfterAction() {
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
            return;
        }

        // Check if player is defeated
        if (player.getHp() <= 0) {
            endBattle(false); // Player lost
            return;
        }

        isExecutingAction = false;

        // Determine who goes next based on speed
        if (isPlayerTurn) {
            // Player just acted, now it's enemy's turn
            switchToEnemyTurn();
        } else {
            // Enemy just acted, now it's player's turn
            switchToPlayerTurn();
        }
    }

    /** Switch to player's turn */
    private void switchToPlayerTurn() {
        if (!isBattleActive) return;

        isExecutingAction = false;
        isPlayerTurn = true;
        startPlayerTurn();
    }

    /** Switch to enemy's turn */
    private void switchToEnemyTurn() {
        if (!isBattleActive) return;

        isPlayerTurn = false;
        startEnemyTurn();
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