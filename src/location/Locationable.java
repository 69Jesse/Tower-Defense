package location;


/**
 * An interface for objects that have a location on the field.
 */
public abstract class Locationable extends BaseLocationable {
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
