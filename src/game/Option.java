package game;

import field.Field;
import location.Location;


/**
 * The abstract option class.
 * Options will appear around a placeable spot/tower when it is selected.
 */
public abstract class Option {
    protected final Game game;
    protected final Field field;

    public static final double RADIUS = 1.0;
    private final double distanceFromTowerCenter = 4.0;

    /**
     * The constructor.
     *
     * @param game  The game.
     * @param field The field.
     */
    public Option(
        Game game,
        Field field
    ) {
        this.game = game;
        this.field = field;
    }

    /**
     * The callback function of this option.
     * This function is called when the option is clicked.
     * 
     * @param location The location of the placeable spot/tower which this option is for.
     */
    public abstract void callback(Location location);

    /**
     * Returns whether this option should be enabled.
     * 
     * @param location The location of the placeable spot/tower which this option is for.
     * @return         Whether this option should be enabled.
     */
    public abstract boolean shouldBeEnabled(Location location);

    /**
     * Returns the image path of this tower.
     * 
     * @return The image path of this tower.
     */
    public abstract String getImagePath();

    /**
     * Returns the label of this option.
     * 
     * @return The label of this option.
     */
    public abstract String getLabel(Location location);

    /**
     * Returns the location of this option in field pixels.
     * It chooses a location around the tower in a circle.
     * 
     * @param towerLocation The location of the tower.
     * @param number        The number of this option.
     * @param total         The total number of options.
     * @return              The location of this option in field pixels.
     */
    public Location getLocation(
        Location towerLocation,
        int number,
        int total
    ) {
        double x = Math.cos(
            2 * Math.PI * number / total + Math.PI / 2
        ) * this.distanceFromTowerCenter;
        double y = -Math.sin(
            2 * Math.PI * number / total + Math.PI / 2
        ) * this.distanceFromTowerCenter;
        return new Location(
            towerLocation.x + x,
            towerLocation.y + y
        );
    }

    /**
     * Returns whether the mouse is hovering over this option.
     * 
     * @param mouseLocation The location of the mouse.
     * @param towerLocation The location of the placeable spot/tower.
     * @param number        The number of this option.
     * @param total         The total number of options.
     * @return              Whether the user has clicked on this option.
     */
    public boolean isHovering(
        Location mouseLocation,
        Location towerLocation,
        int number,
        int total
    ) {
        Location correctedLocation = this.getLocation(towerLocation, number, total);
        return mouseLocation.distanceTo(correctedLocation) <= RADIUS;
    }
}
