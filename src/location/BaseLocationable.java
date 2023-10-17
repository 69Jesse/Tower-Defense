package location;


/**
 * An interface for objects that have a location on the field (but no variable `location`).
 */
public abstract class BaseLocationable {
    /**
     * Returns the location of this object on the field.
     * 
     * @return The location of this object on the field.
     */
    public abstract Location getLocation();
}
