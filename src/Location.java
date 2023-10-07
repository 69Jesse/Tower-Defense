/**
 * An interface for objects that have a location on the field.
 */
abstract class Locationable {
    protected Location location;

    /**
     * Returns the location of this object on the field.
     * 
     * @return The location of this object on the field.
     */
    public Location getLocation() {
        return this.location;
    }
}


abstract class LocationableWithSetter extends Locationable {
    /**
     * Sets the location of this object on the field.
     * 
     * TODO 
     * er is een reden dat dit x en y veranderd en niet variabele veranderd
     * want denk dat het handig gaat zijn bij animeren van projectiles die een
     * doelwit hebben en dan hoef je alleen location object te geven en na
     * elke tick stukje dichterbij dat object te gaan maar weet nog niet echt
     * zeker ofzo dus ja
     * 
     * @param location The new location of this object on the field.
     */
    public void setLocation(Location location) {
        this.location.x = location.x;
        this.location.y = location.y;
    }
}


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
