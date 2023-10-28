package towers.projectile;

import location.Location;
import location.Locationable;


/**
 * A dummy that can be used as a target for projectiles
 * so it can target a location instead of an enemy.
 */
public class DummyTarget extends Locationable {
    public DummyTarget(Location location) {
        this.location = location;
    }
}
