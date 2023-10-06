import java.util.ArrayList;


/**
 * Field class.
 */
public class Field {
    public ArrayList<Tower> towers;
    public ArrayList<Enemy> enemies;

    public ArrayList<Location> placeableSpots;

    /**
     * Adds a tower to the field.
     * 
     * @param tower                     The tower to add.
     * @throws IllegalArgumentException If the tower is not placeable.
     */
    public void addTower(Tower tower) throws IllegalArgumentException {
        if (this.placeableSpots.contains(tower.location)) {
            throw new IllegalArgumentException("Tower already exists at this location.");
        }
        for (Tower t : this.towers) {
            if (t.location.equals(tower.location)) {
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
