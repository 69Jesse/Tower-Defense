package game;

import enemies.Enemy;
import field.Field;
import game.options.BuyArcherTowerOption;
import game.options.BuyBombTowerOption;
import game.options.BuyLaserTowerOption;
import game.options.BuySlingshotTowerOption;
import game.options.BuyWizardTowerOption;
import game.options.SellOption;
import game.options.SwitchTargetOption;
import game.options.UpgradeOption;
import gui.Frame;
import java.util.ArrayList;
import java.util.Random;
import location.Location;
import towers.RangeDamageTower;
import towers.Tower;
import towers.projectile.Projectile;


/**
 * The game class.
 */
public final class Game {
    public final int ticksPerSecond = 60;

    public Field field;
    public Frame frame;

    public WaveHandler waveHandler;
    private int lives;
    private int gold;
    public Location selectedLocation;  // null when no location is selected.
    private Speed speed;

    enum GameState {
        WAITING_TO_START,
        STARTED,
        WON,
        LOST;
    }

    private GameState state;
    private Long seed;

    // Two randoms to make sure the randomness of the field is
    // always directly related to the seed, if given that is.
    public Random fieldRandom;
    public Random towerRandom;

    private int exp;
    private int enemyKills;
    private int goldSpent;

    /**
     * Runs the game.
     * 
     * @param seed The seed to use for the random object, null for no seed.
     */
    public void run(Long seed) {
        System.out.println("Game starting!");
        this.seed = seed;
        this.field = new Field(this);
        this.frame = new Frame(this);
        this.cacheOptions();
        this.frame.start();
        this.init();
        this.field.init();
    }

    public void run() {
        this.run(null);
    }

    /**
     * Initializes the game.
     */
    private void init() {
        this.fieldRandom = this.seed == null ? new Random() : new Random(this.seed);
        this.towerRandom = new Random();
        this.waveHandler = new WaveHandler(this);
        this.lives = this.getStartingLives();
        this.gold = this.getStartingGold();
        this.speed = Speed.ONE;
        this.exp = 0;
        this.enemyKills = 0;
        this.goldSpent = 0;
        this.state = GameState.WAITING_TO_START;
    }

    /**
     * Returns the starting lives.
     * 
     * @return The starting lives.
     */
    public int getStartingLives() {
        return 30;
    }

    /**
     * Returns the starting gold.
     * 
     * @return The starting gold.
     */
    public int getStartingGold() {
        return 1000;
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
    private static BuyWizardTowerOption BUY_WIZARD_TOWER_OPTION;
    private static BuySlingshotTowerOption BUY_SLINGSHOT_TOWER_OPTION;

    private static SellOption SELL_OPTION;
    private static UpgradeOption UPGRADE_OPTION;
    private static SwitchTargetOption SWITCH_TARGET_OPTION;

    /**
     * Caches the options because there is no need to create them every time.
     */
    private void cacheOptions() {
        BUY_ARCHER_TOWER_OPTION = new BuyArcherTowerOption(this, this.field);
        BUY_BOMB_TOWER_OPTION = new BuyBombTowerOption(this, this.field);
        BUY_LASER_TOWER_OPTION = new BuyLaserTowerOption(this, this.field);
        BUY_WIZARD_TOWER_OPTION = new BuyWizardTowerOption(this, this.field);
        BUY_SLINGSHOT_TOWER_OPTION = new BuySlingshotTowerOption(this, this.field);
        SELL_OPTION = new SellOption(this, this.field);
        UPGRADE_OPTION = new UpgradeOption(this, this.field);
        SWITCH_TARGET_OPTION = new SwitchTargetOption(this, this.field);
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
        options.add(BUY_WIZARD_TOWER_OPTION);
        options.add(BUY_SLINGSHOT_TOWER_OPTION);
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
        if (tower instanceof RangeDamageTower) {
            options.add(SWITCH_TARGET_OPTION);
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
        if (!this.isRunning()) {
            return;
        }
        for (int i = 0; i < this.speed.value; i++) {
            this.tickIteration();
        }
        this.field.sortEnemies();  // To make sure the enemies are drawn in the right order.
    }

    /**
     * Handle a game tick iteration.
     */
    private void tickIteration() {
        this.waveHandler.tick();
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
        if (this.waveHandler.isCompletelyDone()) {
            this.onWin();
        }
    }

    /**
     * Starts the game.
     */
    public void start() {
        if (this.hasStarted()) {
            return;
        }
        this.state = GameState.STARTED;
    }

    /**
     * Resets the game.
     */
    public void reset() {
        this.selectedLocation = null;
        this.init();
        this.field.init();
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

    public boolean hasStarted() {
        return this.state != GameState.WAITING_TO_START;
    }

    public boolean hasWon() {
        return this.state == GameState.WON;
    }

    public boolean hasLost() {
        return this.state == GameState.LOST;
    }

    public boolean isRunning() {
        return this.state == GameState.STARTED;
    }

    public boolean hasEnded() {
        return this.hasWon() || this.hasLost();
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
            this.onLose();
        }
    }

    /**
     * The speed of the game.
     */
    public enum Speed {
        ONE(1),
        TWO(2),
        THREE(3),
        FIVE(5),
        TEN(10);

        public final int value;

        Speed(int value) {
            this.value = value;
        }
    }

    /**
     * Returns the speed.
     * 
     * @return The speed.
     */
    public Speed getSpeed() {
        return this.speed;
    }

    /**
     * Switches the speed to the next speed in cycle.
     */
    public void switchSpeed() {
        this.speed = Speed.values()[(this.speed.ordinal() + 1) % Speed.values().length];
    }

    /**
     * Called when the player wins the game.
     */
    public void onWin() {
        if (this.hasEnded()) {
            return;
        }
        System.out.println("You won the game!");
        this.state = GameState.WON;
    }

    /**
     * Called when the player loses the game.
     */
    private void onLose() {
        if (this.hasEnded()) {
            return;
        }
        System.out.println("You lost the game :(");
        this.state = GameState.LOST;
    }
}
