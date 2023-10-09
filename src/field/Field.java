package field;

import enemy.Enemy;
import java.util.ArrayList;
import location.Location;
import tower.base.Tower;


/**
 * Field class.
 */
public class Field {
    public final int width = 160;  // The width of the field in pixels.
    public final int height = 90;  // The height of the field in pixels.

    public ArrayList<Tower> towers;
    public ArrayList<Enemy> enemies;

    public ArrayList<Location> path;
    public ArrayList<Location> placeable;

    /**
     * Adds a tower to the field.
     * 
     * @param tower                     The tower to add.
     * @throws IllegalArgumentException If the tower is not placeable.
     */
    public void addTower(Tower tower) throws IllegalArgumentException {
        Location location = tower.getLocation();
        if (!this.placeable.contains(location)) {
            throw new IllegalArgumentException("Tower is not placeable at this location.");
        }
        for (Tower t : this.towers) {
            if (t.getLocation().equals(location)) {
                throw new IllegalArgumentException("Tower already exists at this location.");
            }
        }
        this.towers.add(tower);
    }

    /**
     * Removes a tower from the field.
     * 
     * @param tower                     The tower to remove.
     * @throws IllegalArgumentException If the tower does not exist.
     */
    public void removeTower(Tower tower) throws IllegalArgumentException {
        boolean removed = this.towers.remove(tower);
        if (!removed) {
            throw new IllegalArgumentException("Tower does not exist.");
        }
    }
}
