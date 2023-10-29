package game.options;

import field.Field;
import game.Game;
import location.Location;
import towers.Tower;
import towers.implementations.WizardTower;


/**
 * The buy wizard tower option class.
 */
public class BuyWizardTowerOption extends BuyTowerOption {
    /**
     * The constructor.
     *
     * @param game  The game.
     * @param field The field.
     */
    public BuyWizardTowerOption(
        Game game,
        Field field
    ) {
        super(game, field);
    }

    @Override
    public Tower createTower(Location location) {
        return new WizardTower(this.game, location);
    }

    @Override
    public String getImagePath() {
        return "./assets/options/wizard_tower.png";
    }

    @Override
    public String getLabel(Location location) {
        return String.format(
            "Buy Wizard Tower (-%d gold)", this.getCost()
        );
    }
}
