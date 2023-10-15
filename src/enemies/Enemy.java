package enemies;

import location.Location;
import location.LocationableWithSetter;


/**
 * An enemy on the field.
 */
public abstract class Enemy extends LocationableWithSetter {
    public final int worth;
    public final int weight;

    public final int maxHealth;
    public final int speed;
    public final boolean flying;

    protected Location location;

    /**
     * Constructs a new enemy.
     * 
     * @param worth     The worth of this enemy. This is used to calculate
     *                  the wave size and the gold reward on death.
     * @param weight    The weight of this enemy. This is used to determine
     *                  how often this enemy should appear in a wave.
     * @param maxHealth The maximum health of this enemy.
     * @param speed     The speed of this enemy.
     * @param flying    Whether or not this enemy is flying.
     * @param location  The location of this enemy on the field.
     */
    public Enemy(
        int worth,
        int weight,
        int maxHealth,
        int speed,
        boolean flying,
        Location location
    ) {
        this.worth = worth;
        this.weight = weight;
        this.maxHealth = maxHealth;
        this.speed = speed;
        this.flying = flying;
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    /**
     * Returns the image path of this tower.
     * This can be dependent on the level of this tower.
     * 
     * @return The image path of this tower.
     */
    public abstract String getImagePath();
}
