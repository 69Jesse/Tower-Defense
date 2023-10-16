package towers;

import enemies.Enemy;
import location.Location;
import location.Locationable;
import location.LocationableWithSetter;


/**
 * A projectile that can be fired by a tower.
 */
public class Projectile extends LocationableWithSetter {
    public final Locationable source;
    public final Enemy target;
    private final int damage;
    private final double speed;
    private final String imagePath;
    public final double size;
    public final double maxCurve;
    private int ticksElapsed;

    /**
     * Constructs a projectile.
     * 
     * @param source    The source of the projectile.
     * @param target    The target of the projectile.
     * @param damage    The damage of the projectile.
     * @param speed     The speed of the projectile in field pixels per game tick.
     * @param imagePath The path to the image of the projectile.
     * @param size The size of the image of the projectile in field pixels.
     * @param maxCurve  The maximum curve of the projectile in field pixels (0 for no curve).
     */
    public Projectile(
        Locationable source,
        Enemy target,
        int damage,
        double speed,
        String imagePath,
        double size,
        double maxCurve
    ) {
        this.location = source.getLocation();
        this.source = source;
        this.target = target;
        this.damage = damage;
        this.speed = speed;
        this.imagePath = imagePath;
        this.size = size;
        this.maxCurve = maxCurve;
        this.ticksElapsed = 0;
    }

    /**
     * Returns the amount of ticks this projectile has to travel to reach its target.
     * 
     * @return The amount of ticks this projectile has to travel to reach its target.
     */
    private double ticksToTarget() {
        return this.source.getLocation().distanceTo(this.target) / this.speed;
    }

    /**
     * Calculates the percentage of the path this projectile has travelled.
     * 
     * @return The percentage of the path this projectile has travelled.
     */
    public double getPercentage() {
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
     * Returns the image path of this projectile.
     * 
     * @return The image path of this projectile.
     */
    public String getImagePath() {
        return this.imagePath;
    }

    /**
     * Tick this projectile.
     * 
     * @return Whether or not this projectile should be removed.
     */
    public boolean tick() {
        if (this.target.isDead()) {
            return true;
        }
        this.calculateLocation();
        this.ticksElapsed++;

        if (this.hasHitTarget()) {
            this.target.onHit(this.damage);
            if (this.target.isDead()) {
                this.target.onKill();
            }
            return true;
        }
        return false;
    }

    /**
     * Returns if this projectile has hit its target.
     * 
     * @return If this projectile has hit its target.
     */
    public boolean hasHitTarget() {
        return this.ticksElapsed >= this.ticksToTarget();
    }
}
