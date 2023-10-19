package game.options;

import field.Field;
import game.Game;
import game.Option;
import location.Location;
import towers.Tower;


/**
 * The upgrade option class.
 */
public final class UpgradeOption extends Option {
    /**
     * The constructor.
     *
     * @param game  The game.
     * @param field The field.
     */
    public UpgradeOption(
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
        try {
            int cost = tower.getUpgradeCost();
            this.game.removeGold(cost);
            tower.upgrade();
            this.game.addGoldSpent(cost);
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
        return tower.canUpgrade() && tower.getUpgradeCost() <= this.game.getGold();
    }

    @Override
    public String getImagePath() {
        return "./assets/options/upgrade.png";
    }

    @Override
    public String getLabel(Location location) {
        Tower tower = this.field.towers.getOrDefault(location, null);
        if (tower == null) {
            return "You should not be able to see this..";
        }
        return String.format(
            "Upgrade to level %d (-%d gold)", tower.level + 1, tower.getUpgradeCost()
        );
    }
}
