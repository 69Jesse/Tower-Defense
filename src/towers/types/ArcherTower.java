package towers.types;

import game.Game;
import location.Location;
import towers.RangeDamageTower;


/**
 * An archer tower.
 */
public class ArcherTower extends RangeDamageTower {
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

    public String getImagePath() {
        return "./assets/towers/archer_tower.png";
    }

    /**
     * Performs an action.
     */
    public void act() {
        System.out.println("Archer tower shoots arrow.");
    }
}
