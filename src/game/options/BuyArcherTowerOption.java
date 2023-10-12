package game.options;

import field.Field;
import game.Game;
import location.Location;
import towers.Tower;
import towers.types.ArcherTower;


/**
 * The buy archer tower option class.
 */
public class BuyArcherTowerOption extends BuyTowerOption {
    /**
     * The constructor.
     *
     * @param game  The game.
     * @param field The field.
     */
    public BuyArcherTowerOption(
        Game game,
        Field field
    ) {
        super(game, field);
    }

    @Override
    public Tower createTower(Location location) {
        return new ArcherTower(this.game, location);
    }

    @Override
    public int getCost() {
        return this.createTower(null).cost;
    }

    @Override
    public boolean shouldBeEnabled(Location location) {
        Tower tower = this.field.towers.getOrDefault(location, null);
        if (tower != null) {
            return false;
        }
        return this.game.getGold() >= this.getCost(); 
    }

    @Override
    public String getImagePath() {
        return "./assets/options/archer_tower.png";
    }
}
