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
    }

    @Override
    public Location getLocation() {
        final double distanceTraveled = this.traveledDistance();
        for (Map.Entry<Integer, Double> entry : this.game.field.distancesFromStart.entrySet()) {
            double distance = entry.getValue();
            if (distance <= distanceTraveled) {
                continue;
            }

            // TODO
        }
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
     * @param traveled The distance this enemy has traveled.
     * @return         The percentage of the field this enemy has traveled in [0, 1].
     */
    public double percentageDone(double traveled) {
        return traveled / this.game.field.totalDistance;
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
    public void doDamage(int damage) {
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
    public void onHit(int damage) {
        this.doDamage(damage);
    }

    /**
     * Handles the logic when this enemy is killed.
     */
    public void onKill() {
        this.game.addGold(this.worth);
    }

    /**
     * Tick this enemy.
     */
    public void tick() {
        this.ticksElapsed++;
    }
}
