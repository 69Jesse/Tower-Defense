package towers.projectile;

import enemies.Enemy;
import game.Game;
import location.BaseLocationable;
import location.Location;
import towers.DamageTower;


/**
 * A projectile that can be fired by a tower that has an image.
 */
public final class ImageProjectile extends Projectile {
    private Location location;
    private final double speed;
    public final double size;
    public final double maxCurve;
    private final String imagePath;

    /**
     * Constructs an image projectile.
     * 
     * @param game       The game this projectile is in.
     * @param tower      The tower that fired this projectile.
     * @param source     The source of the projectile (not always equal the tower).
     * @param target     The target of the projectile.
     * @param damage     The damage of the projectile.
     * @param shouldMove Whether or not the location of the points it draws from should move.
     * @param speed      The speed of the projectile in field pixels per game tick.
     * @param size       The size of the image of the projectile in field pixels.
     * @param maxCurve   The maximum curve of the projectile in field pixels (0 for no curve).
     * @param imagePath  The path to the image of the projectile.
     */
    public ImageProjectile(
        Game game,
        DamageTower tower,
        BaseLocationable source,
        BaseLocationable target,
        double damage,
        boolean shouldMove,
        double speed,
        double size,
        double maxCurve,
        String imagePath
    ) {
        super(
            game,
            tower,
            source,
            target,
            damage,
            shouldMove,
            true
        );
        this.speed = speed;
        this.size = size;
        this.maxCurve = maxCurve;
        this.imagePath = imagePath;
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
        final Location sourceLocation = this.getSourceLocation();
        final Location targetLocation = this.getTargetLocation();

        final double x = sourceLocation.x + (targetLocation.x - sourceLocation.x) * percentage;
        final double y = sourceLocation.y + (targetLocation.y - sourceLocation.y) * percentage;
        this.location = new Location(x, y);
    }

    public Location getLocation() {
        return this.location;
    }

    /**
     * Returns the image path of this projectile.
     * 
     * @return The image path of this projectile.
     */
    public String getImagePath() {
        return this.imagePath;
    }

    @Override
    protected boolean duringTick() {
        this.calculateLocation();
        if (this.hasHitTarget()) {
            if (this.target instanceof Enemy) {
                this.tower.onTargetHit((Enemy) this.target, this.damage);
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
