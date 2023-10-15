package game;

import field.Field;
import game.options.BuyArcherTowerOption;
import game.options.SellOption;
import game.options.UpgradeOption;
import gui.frame.Frame;
import java.util.ArrayList;
import location.Location;
import towers.Tower;


/**
 * The game class.
 */
public final class Game {
    public final int ticksPerSecond = 20;

    public Field field;
    public Frame frame;

    private int gold = 300;

    public Location selectedLocation = null;

    /**
     * Starts the game.
     */
    public void start() {
        System.out.println("Game starting!");
        this.field = new Field();
        this.frame = new Frame(this);
        this.cacheOptions();
        this.frame.start();
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

    private static SellOption SELL_OPTION;
    private static UpgradeOption UPGRADE_OPTION;

    /**
     * Caches the options because there is no need to create them every time.
     */
    private void cacheOptions() {
        BUY_ARCHER_TOWER_OPTION = new BuyArcherTowerOption(this, this.field);
        SELL_OPTION = new SellOption(this, this.field);
        UPGRADE_OPTION = new UpgradeOption(this, this.field);
    }

    /**
     * Adds the buy tower options.
     * 
     * @param options The options to add to.
     */
    private void addBuyTowerOptions(ArrayList<Option> options) {
        for (int i = 0; i < 10; i++) {
            options.add(BUY_ARCHER_TOWER_OPTION);
        }
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
        for (Tower tower : this.field.towers.values()) {
            tower.tick();
        }
    }
}
