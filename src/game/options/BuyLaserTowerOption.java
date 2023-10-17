package game.options;

import field.Field;
import game.Game;
import location.Location;
import towers.Tower;
import towers.implementations.LaserTower;


/**
 * The buy laser tower option class.
 */
public class BuyLaserTowerOption extends BuyTowerOption {
    /**
     * The constructor.
     *
     * @param game  The game.
     * @param field The field.
     */
    public BuyLaserTowerOption(
        Game game,
        Field field
    ) {
        super(game, field);
    }

    @Override
    public Tower createTower(Location location) {
        return new LaserTower(this.game, location);
    }

    @Override
    public String getImagePath() {
        return "./assets/options/laser_tower.png";
    }

    @Override
    public String getLabel(Location location) {
        return String.format(
            "Buy Laser Tower (-%d gold)", this.getCost()
        );
    }
}
