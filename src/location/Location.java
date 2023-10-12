package location;


/**
 * A location on the field.
 */
public class Location {
    public double x;
    public double y;

    /**
     * Constructs a new location.
     * 
     * @param x The x coordinate in pixels.
     * @param y The y coordinate in pixels.
     */
    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Location(int x, int y) {
        this((double) x, (double) y);
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

    /**
     * Returns the manhattan distance between this location and another location.
     * 
     * @param other The other location.
     * @return      The manhattan distance between this location and another location.
     */
    public double manhattanDistanceTo(Location other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    public double manhattanDistanceTo(Locationable other) {
        return this.manhattanDistanceTo(other.getLocation());
    }

    public String toString() {
        return String.format("Location(%f, %f)", this.x, this.y);
    }
}
