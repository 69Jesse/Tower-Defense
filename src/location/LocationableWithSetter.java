package location;


/**
 * A Locationable object that can change its location.
 */
public abstract class LocationableWithSetter extends Locationable {
    /**
     * Sets the location of this object on the field.
     * 
     * @param location The new location of this object on the field.
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}
