package towers;

import location.Location;
import location.Locationable;
import location.LocationableWithSetter;


/**
 * A projectile that can be fired by a tower.
 */
public class Projectile extends LocationableWithSetter {
    private Location location;
    public final Locationable source;
    public final Locationable target;
    private final int damage;
    private final double speed;
    public final String imagePath;
    public final boolean withCurve;
    private int ticksElapsed;

    /**
     * Constructs a projectile.
     * 
     * @param source    The source of the projectile.
     * @param target    The target of the projectile.
     * @param damage    The damage of the projectile.
     * @param speed     The speed of the projectile in field pixels per game tick.
     * @param imagePath The path to the image of the projectile.
     * @param withCurve Whether or not the projectile should curve upwards (purely visual).
     */
    public Projectile(
        Locationable source,
        Locationable target,
        int damage,
        double speed,
        String imagePath,
        boolean withCurve
    ) {
        this.location = source.getLocation();
        this.source = source;
        this.target = target;
        this.damage = damage;
        this.speed = speed;
        this.imagePath = imagePath;
        this.withCurve = withCurve;
        this.ticksElapsed = 0;
    }

    /**
     * Returns the amount of ticks this projectile has to travel to reach its target.
     * 
     * @return The amount of ticks this projectile has to travel to reach its target.
     */
    private double ticksToTarget() {
        return this.location.distanceTo(this.target) / this.speed;
    }

    /**
     * Calculates the percentage of the path this projectile has travelled.
     * 
     * @return The percentage of the path this projectile has travelled.
     */
    private double getPercentage() {
        return this.ticksElapsed / this.ticksToTarget();
    }

    private void calculateLocation() {
        final double percentage = this.getPercentage();
        final Location sourceLocation = this.source.getLocation();
        final Location targetLocation = this.target.getLocation();

        final double x = sourceLocation.x + (targetLocation.x - sourceLocation.x) * percentage;
        final double y = sourceLocation.y + (targetLocation.y - sourceLocation.y) * percentage;
        this.setLocation(new Location(x, y));
    }

    /**
     * Tick this projectile.
     */
    public void tick() {
        this.calculateLocation();
        this.ticksElapsed++;
    }
}
