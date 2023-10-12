package towers.types;

import game.Game;
import location.Location;
import towers.RangeDamageTower;


/**
 * An archer tower.
 */
public final class ArcherTower extends RangeDamageTower {
    /**
     * Constructs an archer tower.
     * 
     * @param game     The game this tower is in.
     * @param location The location of this tower on the field.
     */
    public ArcherTower(Game game, Location location) {
        super(
            game,
            location,
            100,
            3,
            40,
            10,
            10
        );
    }

    @Override
    public String getImagePath() {
        switch (this.level) {
            case 1:
                return "./assets/towers/archer_tower_1.png";
            case 2:
                return "./assets/towers/archer_tower_2.png";
            case 3:
                return "./assets/towers/archer_tower_3.png";
            default:
                throw new RuntimeException("Invalid level: " + this.level);
        }
    }

    /**
     * Performs an action.
     */
    public void act() {
        System.out.println("Archer tower shoots arrow.");
    }
}
