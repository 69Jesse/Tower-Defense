package game.options;

import field.Field;
import game.Game;
import location.Location;
import towers.Tower;
import towers.implementations.BombTower;


/**
 * The buy bomb tower option class.
 */
public class BuyBombTowerOption extends BuyTowerOption {
    /**
     * The constructor.
     *
     * @param game  The game.
     * @param field The field.
     */
    public BuyBombTowerOption(
        Game game,
        Field field
    ) {
        super(game, field);
    }

    @Override
    public Tower createTower(Location location) {
        return new BombTower(this.game, location);
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
        return "./assets/options/bomb_tower.png";
    }

    @Override
    public String getLabel(Location location) {
        return String.format(
            "Buy Bomb Tower (-%d gold)", this.getCost()
        );
    }
}
