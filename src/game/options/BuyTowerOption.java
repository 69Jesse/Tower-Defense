package game.options;

import field.Field;
import game.Game;
import game.Option;
import location.Location;
import towers.Tower;


/**
 * An abstract class for buying towers.
 */
public abstract class BuyTowerOption extends Option {
    /**
     * The constructor.
     *
     * @param game  The game.
     * @param field The field.
     */
    public BuyTowerOption(
        Game game,
        Field field
    ) {
        super(game, field);
    }

    /**
     * Creates a tower with a specific subclass.
     * 
     * @param location The location of the tower.
     * @return         The tower.
     */
    public abstract Tower createTower(Location location);

    @Override
    public void callback(Location location) {
        if (!this.shouldBeEnabled(location)) {
            return;
        }
        Tower tower = this.createTower(location);
        try {
            this.game.removeGold(this.getCost());
            this.field.addTower(tower);
        } catch (Exception e) {
            return;
        }
    }

    private Integer cost = null;

    /**
     * Returns the cost of this tower.
     * 
     * @return The cost of this tower.
     */
    public int getCost() {
        if (this.cost == null) {
            this.cost = this.createTower(null).cost;
        }
        return this.cost;
    }

    @Override
    public boolean shouldBeEnabled(Location location) {
        Tower tower = this.field.towers.getOrDefault(location, null);
        if (tower != null) {
            return false;
        }
        return this.game.getGold() >= this.getCost(); 
    }
}
