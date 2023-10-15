package enemies.implementations;

import enemies.Enemy;
import location.Location;


/**
 * A regular enemy.
 */
public class RegularEnemy extends Enemy {
    /**
     * Constructs a regular enemy.
     * 
     * @param location The location of this enemy on the field.
     */
    public RegularEnemy(Location location) {
        super(
            10,
            1,
            100,
            1,
            false,
            location
        );
    }

    @Override
    public String getImagePath() {
        return "./assets/enemies/regular_enemy.png";
    }
}
