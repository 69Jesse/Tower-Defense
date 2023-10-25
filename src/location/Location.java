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
     * @param x The x coordinate in field pixels.
     * @param y The y coordinate in field pixels.
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

    public double distanceTo(BaseLocationable other) {
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

    public double manhattanDistanceTo(BaseLocationable other) {
        return this.manhattanDistanceTo(other.getLocation());
    }

    /**
     * Returns whether or not this location is in the same rectangle as another location.
     * 
     * @param other  The middle of the other location/rectangle.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     * @return       Whether or not this location is in the same rectangle as another location.
     */
    public boolean inSameRect(Location other, double width, double height) {
        double otherX = other.x - width / 2;
        double otherY = other.y - height / 2;
        return this.x >= otherX && this.x <= otherX + width
            && this.y >= otherY && this.y <= otherY + height;
    }

    public boolean inSameRect(BaseLocationable other, double width, double height) {
        return this.inSameRect(other.getLocation(), width, height);
    }

    public boolean inSameSquare(Location other, double size) {
        return this.inSameRect(other, size, size);
    }

    public boolean inSameSquare(BaseLocationable other, double size) {
        return this.inSameSquare(other.getLocation(), size);
    }

    public String toString() {
        return String.format("Location(%f, %f)", this.x, this.y);
    }

    /**
     * Returns a copy of this location as a new instance.
     * 
     * @return A copy of this location.
     */ 
    public Location copy() {
        return new Location(this.x, this.y);
    }
}
