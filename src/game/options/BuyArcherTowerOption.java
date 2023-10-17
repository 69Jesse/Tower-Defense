package game.options;

import field.Field;
import game.Game;
import location.Location;
import towers.Tower;
import towers.implementations.ArcherTower;


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
    public String getImagePath() {
        return "./assets/options/archer_tower.png";
    }

    @Override
    public String getLabel(Location location) {
        return String.format(
            "Buy Archer Tower (-%d gold)", this.getCost()
        );
    }
}
