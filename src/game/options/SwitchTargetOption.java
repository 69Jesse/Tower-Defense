package game.options;

import field.Field;
import game.Game;
import game.Option;
import location.Location;
import towers.RangeDamageTower;
import towers.Tower;


/**
 * The switch target option class.
 */
public final class SwitchTargetOption extends Option {
    /**
     * The constructor.
     *
     * @param game  The game.
     * @param field The field.
     */
    public SwitchTargetOption(
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
        RangeDamageTower rdTower = (RangeDamageTower) tower;
        rdTower.switchTargetingMode();
    }

    @Override
    public boolean shouldBeEnabled(Location location) {
        Tower tower = this.field.towers.getOrDefault(location, null);
        return tower instanceof RangeDamageTower;
    }

    @Override
    public String getImagePath() {
        return "./assets/options/switch_target.png";
    }

    @Override
    public String getLabel(Location location) {
        Tower tower = this.field.towers.getOrDefault(location, null);
        if (tower == null || !(tower instanceof RangeDamageTower)) {
            return "You should not be able to see this..";
        }
        RangeDamageTower rdTower = (RangeDamageTower) tower;
        return String.format(
            "Switch Target to %s.", rdTower.nextTargetingMode().getName()
        );
    }
}
