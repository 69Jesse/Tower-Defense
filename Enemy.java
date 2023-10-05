/**
 * An enemy on the field.
 */
public class Enemy implements Locationable {
    protected Location location;

    /**
     * Constructs a new enemy.
     * 
     * @param location The location of this enemy on the field.
     */
    public Enemy(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return this.location;
    }
}
