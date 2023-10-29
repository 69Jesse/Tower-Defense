package game.options;

import field.Field;
import game.Game;
import location.Location;
import towers.Tower;
import towers.implementations.SlingshotTower;


/**
 * The buy slingshot tower option class.
 */
public class BuySlingshotTowerOption extends BuyTowerOption {
    /**
     * The constructor.
     *
     * @param game  The game.
     * @param field The field.
     */
    public BuySlingshotTowerOption(
        Game game,
        Field field
    ) {
        super(game, field);
    }

    @Override
    public Tower createTower(Location location) {
        return new SlingshotTower(this.game, location);
    }

    @Override
    public String getImagePath() {
        return "./assets/options/slingshot_tower.png";
    }

    @Override
    public String getLabel(Location location) {
        return String.format(
            "Buy Slingshot Tower (-%d gold)", this.getCost()
        );
    }
}
