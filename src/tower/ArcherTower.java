package tower;

import game.Game;
import location.Location;
import tower.base.RangeDamageTower;


class ArcherTower extends RangeDamageTower {
    /**
     * Constructs an archer tower.
     */
    public ArcherTower(Game game, Location location) {
        super(
            game,
            location,
            100,
            3,
            40,
            10,
            5
        );
    }

    /**
     * Performs an action.
     */
    public void act() {
        System.out.println("Archer tower shoots arrow.");
    }
}
