package location;


/**
 * A Locationable object that can change its location.
 */
public abstract class LocationableWithSetter extends Locationable {
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
