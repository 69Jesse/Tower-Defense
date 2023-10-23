package game;

import enemies.Enemy;
import enemies.implementations.DroneEnemy;
import enemies.implementations.RegularEnemy;
import field.Field;
import game.options.BuyArcherTowerOption;
import game.options.BuyBombTowerOption;
import game.options.BuyLaserTowerOption;
import game.options.SellOption;
import game.options.UpgradeOption;
import gui.frame.Frame;
import java.util.ArrayList;
import location.Location;
import towers.Projectile;
import towers.Tower;


/**
 * The game class.
 */
public final class Game {
    public final int ticksPerSecond = 60;

    public Field field;
    public Frame frame;

    private int waveNumber;
    private int waveValue;
    private int lives;
    private int gold;
    public Location selectedLocation;  // null when no location is selected.
    public int speed;
    public boolean gameLost;
    public boolean gameStarted;
    public boolean gameWon;

    private int exp;
    private int enemyKills;
    private int goldSpent;

    private final int maxWave = 20;

    /**
     * Runs the game.
     */
    public void run() {
        System.out.println("Game starting!");
        this.init();
        this.field = new Field();
        this.frame = new Frame(this);
        this.cacheOptions();
        this.frame.start();
    }

    /**
     * Initializes the game.
     */
    private void init() {
        this.waveNumber = 0;
        this.waveValue = 10;
        this.lives = 15;
        this.gold = 10000;
        this.speed = 1;
        this.exp = 0;
        this.enemyKills = 0;
        this.goldSpent = 0;
        this.gameLost = false;
        this.gameStarted = false;
        this.gameWon = false;
    }

    /**
     * Returns the amount of lives left.
     * 
     * @return The lives left.
     */
    public int getLives() {
        return this.lives;
    }

    /**
     * Returns the gold.
     * 
     * @return The gold.
     */
    public int getGold() {
        return this.gold;
    }

    /**
     * Adds gold.
     * 
     * @param gold                      The gold to add.
     * @throws IllegalArgumentException If the gold is negative.
     */
    public void addGold(int gold) throws IllegalArgumentException {
        if (gold < 0) {
            throw new IllegalArgumentException("Cannot add negative gold.");
        }
        this.gold += gold;
    }

    /**
     * Removes gold.
     * 
     * @param gold                      The gold to remove.
     * @throws IllegalArgumentException If the gold is negative.
     */
    public void removeGold(int gold) throws IllegalArgumentException {
        if (gold < 0) {
            throw new IllegalArgumentException("Cannot remove negative gold.");
        }
        int newGold = this.gold - gold;
        if (newGold < 0) {
            throw new IllegalArgumentException("Gold cannot be negative.");
        }
        this.gold = newGold;
    }

    /**
     * Buy a tower.
     * 
     * @param tower                     The tower to buy.
     * @throws IllegalArgumentException Not enough gold or tower not placeable.
     */
    public void buyTower(Tower tower) throws IllegalArgumentException {
        if (this.getGold() < tower.cost) {
            throw new IllegalArgumentException("Not enough gold.");
        }
        this.field.addTower(tower);  // Throws if it cannot be placed.
        this.removeGold(tower.cost);
    }

    /**
     * Sell a tower.
     * 
     * @param tower                     The tower to sell.
     * @throws IllegalArgumentException If the tower does not exist.
     */
    public void sellTower(Tower tower) throws IllegalArgumentException {
        this.field.removeTower(tower);  // Throws if it cannot be sold.
        this.addGold(tower.getSellValue());
    }

    /**
     * Upgrade a tower.
     * 
     * @param tower                     The tower to upgrade.
     * @throws IllegalArgumentException If the tower does not exist or cannot be upgraded.
     */
    public void upgradeTower(Tower tower) throws IllegalArgumentException {
        if (this.getGold() < tower.getUpgradeCost()) {
            throw new IllegalArgumentException("Not enough gold.");
        }
        tower.upgrade();  // Throws if it cannot be upgraded.
        this.removeGold(tower.getUpgradeCost());
    }

    private static BuyArcherTowerOption BUY_ARCHER_TOWER_OPTION;
    private static BuyBombTowerOption BUY_BOMB_TOWER_OPTION;
    private static BuyLaserTowerOption BUY_LASER_TOWER_OPTION;

    private static SellOption SELL_OPTION;
    private static UpgradeOption UPGRADE_OPTION;

    /**
     * Caches the options because there is no need to create them every time.
     */
    private void cacheOptions() {
        BUY_ARCHER_TOWER_OPTION = new BuyArcherTowerOption(this, this.field);
        BUY_BOMB_TOWER_OPTION = new BuyBombTowerOption(this, this.field);
        BUY_LASER_TOWER_OPTION = new BuyLaserTowerOption(this, this.field);
        SELL_OPTION = new SellOption(this, this.field);
        UPGRADE_OPTION = new UpgradeOption(this, this.field);
    }

    /**
     * Adds the buy tower options.
     * 
     * @param options The options to add to.
     */
    private void addBuyTowerOptions(ArrayList<Option> options) {
        options.add(BUY_ARCHER_TOWER_OPTION);
        options.add(BUY_BOMB_TOWER_OPTION);
        options.add(BUY_LASER_TOWER_OPTION);
    }

    /**
     * Adds the existing tower options.
     * 
     * @param options The options to add to.
     * @param tower   The tower.
     */
    private void addExistingTowerOptions(ArrayList<Option> options, Tower tower) {
        options.add(SELL_OPTION);
        if (tower.canUpgrade()) {
            options.add(UPGRADE_OPTION);
        }
    }

    /**
     * Returns the selected options.
     * 
     * @return The selected options.
     */
    public ArrayList<Option> getSelectedOptions() {
        ArrayList<Option> options = new ArrayList<>();
        if (this.selectedLocation == null) {
            return options;
        }
        Tower tower = this.field.towers.getOrDefault(this.selectedLocation, null);
        if (tower == null) {
            this.addBuyTowerOptions(options);
            return options;
        }
        this.addExistingTowerOptions(options, tower);
        return options;
    }

    /**
     * Handle a game tick.
     */
    public void tick() {
        if (this.gameLost || this.gameWon || !this.gameStarted) {
            return;
        }
        for (int i = 0; i < this.speed; i++) {
            this.tickIteration();
        }
    }

    /**
     * Handles a wave.
     */
    private void waveIteration() {
        int differentEnemyAmount = 2;
        this.waveNumber++;
        if (this.waveNumber > this.maxWave) {
            // The player won the game.
            this.gameWon = true;
            return;
        }
        double newWaveValue = this.waveValue * 1.5;
        this.waveValue = (int) newWaveValue;
        int currentWaveValue = 0;
        while (currentWaveValue + 1 < this.waveValue) {
            double randomDouble = Math.random() * differentEnemyAmount;
            int randomInt = (int) randomDouble;
            Enemy enemy = null;
            switch (randomInt) {
                case 0:
                    enemy = new RegularEnemy(this);
                    break;
                case 1:
                    enemy = new DroneEnemy(this);
                    break;
                default:
                    // We shouldn't get here, but just in case:
                    enemy = new RegularEnemy(this);
                    break;
            }
            currentWaveValue += enemy.weight;
            if (currentWaveValue > this.waveValue) {
                // The enemy is worth too much, so we don't put them in.
                currentWaveValue -= enemy.weight;
            } else {
                this.field.enemies.add(enemy);
            }
        }
        // Now we have spawned a wave of enemies.
    }

    /**
     * Returns the wave number.
     * 
     * @return The wave number.
     */
    public int getWaveNumber() {
        return this.waveNumber;
    }

    /**
     * Returns the max wave.
     * 
     * @return The max wave.
     */
    public int getMaxWave() {
        return this.maxWave;
    }

    /**
     * Returns the wave value.
     * 
     * @return The wave value.
     */
    public int getWaveValue() {
        return this.waveValue;
    }

    /**
     * Handle a game tick iteration.
     */
    private void tickIteration() {
        for (Tower tower : this.field.towers.values()) {
            tower.tick();
        }
        for (Enemy enemy : this.field.enemies) {
            enemy.tick();
        }
        for (int i = this.field.projectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = this.field.projectiles.get(i);
            boolean shouldBeRemoved = projectile.tick();
            if (shouldBeRemoved) {
                this.field.projectiles.remove(i);
            }
        }
        for (int i = this.field.enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = this.field.enemies.get(i);
            boolean shouldBeRemoved = enemy.isDead() || enemy.isAtEnd();
            if (shouldBeRemoved) {
                this.field.enemies.remove(i);
            }
        }
        this.field.sortEnemies();
    }

    /**
     * Starts the game.
     */
    public void start() {
        this.gameStarted = true;
        this.waveIteration();
    }

    /**
     * Resets the game.
     */
    public void reset() {
        this.field.reset();
        this.selectedLocation = null;
        this.init();
    }

    /**
     * Returns the exp.
     * 
     * @return The exp.
     */
    public int getExp() {
        return this.exp;
    }

    /**
     * Adds exp.
     * 
     * @param amount                    The amount of exp to add.
     * @throws IllegalArgumentException If the exp is negative.
     */
    public void addExp(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add negative exp.");
        }
        this.exp += amount;
    }

    /**
     * Returns the enemy kills.
     * 
     * @return The enemy kills.
     */
    public int getEnemyKills() {
        return this.enemyKills;
    }

    /**
     * Adds enemy kills.
     * 
     * @param amount                    The amount of enemy kills to add.
     * @throws IllegalArgumentException If the enemy kills is negative.
     */
    public void addEnemyKills(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add negative enemy kills.");
        }
        this.enemyKills += amount;

        // Now we check if we can spawn the next wave.
        if (this.field.enemies.size() <= 2) {
            // We can spawn the next wave.
            waveIteration();
        }
    }

    /**
     * Returns the gold spent.
     * 
     * @return The gold spent.
     */
    public int getGoldSpent() {
        return this.goldSpent;
    }

    /**
     * Adds gold spent.
     * 
     * @param amount                    The amount of gold spent to add.
     * @throws IllegalArgumentException If the gold spent is negative.
     */
    public void addGoldSpent(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add negative gold spent.");
        }
        this.goldSpent += amount;
    }

    /**
     * Removed an amount of life from the total.
     * And checks if the player lost the game.
     * 
     * @param amount The amount of lives to be removed.
     */
    public void removeLife(int amount) {
        this.lives -= amount;
        if (this.lives <= 0) {
            this.lives = 0;
            gameLost();
        }
    }

    /**
     * Executed when the player loses the game.
     */
    private void gameLost() {
        if (this.gameLost) {
            return;
        }
        System.out.println("You lost the game :(");
        this.gameLost = true;
    }
}
