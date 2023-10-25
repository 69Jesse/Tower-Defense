package enemies;

import game.Game;
import java.util.Map;
import location.BaseLocationable;
import location.Location;


/**
 * An enemy on the field.
 */
public abstract class Enemy extends BaseLocationable {
    protected final Game game;
    public final int worth;
    public final int weight;

    public final int maxHealth;
    public final double speed;
    public final double size;
    public final boolean flying;

    protected int health;
    protected int ticksElapsed;
    protected boolean done;
    public final Location drawOffsetLocation;

    /**
     * Constructs a new enemy.
     * 
     * @param game      The game this enemy is in.
     * @param worth     The worth of this enemy. This is used to calculate
     *                  the wave size and the gold reward on death.
     * @param weight    The weight of this enemy. This is used to determine
     *                  how often this enemy should appear in a wave.
     * @param maxHealth The maximum health of this enemy.
     * @param speed     The speed of this enemy in field pixels per game tick.
     * @param size      The size of this enemy in field pixels.
     * @param flying    Whether or not this enemy is flying.
     */
    public Enemy(
        Game game,
        int worth,
        int weight,
        int maxHealth,
        double speed,
        double size,
        boolean flying
    ) {
        this.game = game;
        this.worth = worth;
        this.weight = weight;
        this.maxHealth = maxHealth;
        this.speed = speed;
        this.size = size;
        this.flying = flying;
        this.health = maxHealth;
        this.ticksElapsed = 0;
        this.done = false;
        this.drawOffsetLocation = this.createDrawOffsetLocation();
    }

    private Location createDrawOffsetLocation() {
        final double maxOffset = 0.5;
        double x = this.game.random.nextDouble() * maxOffset * 2 - maxOffset;
        double y = this.game.random.nextDouble() * maxOffset * 2 - maxOffset;
        return new Location(x, y);
    }

    @Override
    public Location getLocation() throws RuntimeException {
        final double distanceTraveled = this.traveledDistance();
        for (Map.Entry<Integer, Double> entry : this.game.field.distancesFromStart.entrySet()) {
            double upperDistance = entry.getValue();
            if (upperDistance <= distanceTraveled) {
                continue;
            }

            Location upperLocation = this.game.field.path.get(entry.getKey());
            int index = entry.getKey() - 1;
            double lowerDistance = this.game.field.distancesFromStart.get(index);
            Location lowerLocation = this.game.field.path.get(index);

            double remainder = distanceTraveled - lowerDistance;
            double percentage = remainder / (upperDistance - lowerDistance);
            
            double x = lowerLocation.x + (upperLocation.x - lowerLocation.x) * percentage;
            double y = lowerLocation.y + (upperLocation.y - lowerLocation.y) * percentage;
            return new Location(x, y);
        }

        // This should never be reached, but still can due to rounding errors.
        return this.game.field.path.get(this.game.field.path.size() - 1).copy();
    }

    /**
     * Returns the distance this enemy has traveled.
     * 
     * @return The distance this enemy has traveled.
     */
    public double traveledDistance() {
        return this.ticksElapsed * this.speed;
    }

    /**
     * Returns the percentage of the field this enemy has traveled.
     * If this is >= 1.0, this enemy has reached the end of the field.
     * 
     * @return         The percentage of the field this enemy has traveled in [0, 1].
     */
    public double percentageDone() {
        return this.traveledDistance() / this.game.field.totalDistance;
    }

    /**
     * Returns whether or not this enemy has reached the end of the field.
     * 
     * @return Whether or not this enemy has reached the end of the field.
     */
    public boolean isAtEnd() {
        return this.percentageDone() >= 1.0;
    }

    /**
     * Returns the current health of this enemy.
     * 
     * @return The current health of this enemy.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Returns whether or not this enemy is dead.
     * 
     * @return Whether or not this enemy is dead.
     */
    public boolean isDead() {
        return this.health <= 0;
    }

    /**
     * Damages this enemy.
     * 
     * @param damage The amount of damage to do.
     */
    public void doDamage(double damage) {
        this.health -= damage;
    }

    /**
     * Returns the image path of this tower.
     * This can be dependent on the level of this tower.
     * 
     * @return The image path of this tower.
     */
    public abstract String getImagePath();

    /**
     * Handles the logic when this enemy is hit.
     * 
     * @param damage The amount of damage to do.
     */
    public void onHit(double damage) {
        this.doDamage(damage);
        if (this.isDead()) {
            this.onDeath();
        }
    }

    /**
     * Handles the logic when this enemy dies.
     */
    public void onDeath() {
        if (this.done) {
            return;
        }
        this.game.addGold(this.worth);
        this.game.addExp(this.worth);
        this.game.addEnemyKills(1);
        this.done = true;
    }

    /**
     * Handles the logic when this enemy reaches the end of the field.
     */
    public void onEndReached() {
        if (this.done) {
            return;
        }
        this.game.removeLife(
            Math.max(1, (int) Math.ceil(this.maxHealth / 10.0))
        );
        this.done = true;
    }

    /**
     * Tick this enemy.
     */
    public void tick() {
        this.ticksElapsed++;
        if (this.isAtEnd()) {
            this.onEndReached();
        }
    }
}
