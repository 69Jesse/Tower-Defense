package location;


/**
 * A location on the field.
 */
public class Location {
    public int x;
    public int y;

    /**
     * Constructs a new location.
     * 
     * @param x The x coordinate in pixels.
     * @param y The y coordinate in pixels.
     */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the distance between this location and another location.
     * 
     * @param other The other location.
     * @return      The distance between this location and another location.
     */
    public double distanceTo(Location other) {
        return Math.sqrt(
            Math.pow(this.x - other.x, 2)
            + Math.pow(this.y - other.y, 2)
        );
    }

    public double distanceTo(Locationable other) {
        return this.distanceTo(other.getLocation());
    }
}
