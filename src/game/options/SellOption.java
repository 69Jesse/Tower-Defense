package game.options;

import field.Field;
import game.Game;
import game.Option;
import location.Location;
import towers.Tower;


/**
 * The sell option class.
 */
public final class SellOption extends Option {
    /**
     * The constructor.
     *
     * @param game  The game.
     * @param field The field.
     */
    public SellOption(
        Game game,
        Field field
    ) {
        super(game, field);
    }

    @Override
    public void callback(Location location) {
        Tower tower = this.field.towers.getOrDefault(location, null);
        if (tower == null) {
            return;
        }
        if (!this.shouldBeEnabled(location)) {
            return;
        }
        int value = tower.getSellValue();
        try {
            this.game.addGold(value);
            this.field.removeTower(tower);
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    @Override
    public boolean shouldBeEnabled(Location location) {
        Tower tower = this.field.towers.getOrDefault(location, null);
        if (tower == null) {
            return false;
        }
        return tower.canSell();
    }

    @Override
    public String getImagePath() {
        return "./assets/options/sell.png";
    }

    @Override
    public String getLabel(Location location) {
        Tower tower = this.field.towers.getOrDefault(location, null);
        if (tower == null) {
            return "You should not be able to see this..";
        }
        return String.format(
            "Sell (+%d gold)", tower.getSellValue()
        );
    }
}
