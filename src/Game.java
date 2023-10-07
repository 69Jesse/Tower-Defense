/**
 * The game class.
 */
public final class Game {
    public final int ticksPerSecond = 20;

    public Field field;

    public int gold = 0;

    /**
     * Starts the game.
     */
    public void start() {
        System.out.println("Game starting!");
        this.field = new Field();
    }

    /**
     * Buy a tower.
     * 
     * @param tower                     The tower to buy.
     * @throws IllegalArgumentException Not enough gold or tower not placeable.
     */
    public void buyTower(Tower tower) throws IllegalArgumentException {
        if (this.gold < tower.cost) {
            throw new IllegalArgumentException("Not enough gold.");
        }
        this.field.addTower(tower);
    }

    /**
     * Sell a tower.
     * 
     * @param tower                     The tower to sell.
     * @throws IllegalArgumentException If the tower does not exist.
     */
    public void sellTower(Tower tower) throws IllegalArgumentException {
        this.field.removeTower(tower);
        this.gold += tower.getSellCost();
    }

    /**
     * Upgrade a tower.
     * 
     * @param tower                     The tower to upgrade.
     * @throws IllegalArgumentException If the tower does not exist or cannot be upgraded.
     */
    public void upgradeTower(Tower tower) throws IllegalArgumentException {
        if (!tower.canUpgrade()) {
            throw new IllegalArgumentException("Tower cannot be upgraded.");
        }
        this.gold -= tower.getUpgradeCost();
        tower.upgrade();
    }
}
