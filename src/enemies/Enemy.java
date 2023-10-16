package enemies;

import game.Game;
import location.Location;
import location.LocationableWithSetter;


/**
 * An enemy on the field.
 */
public abstract class Enemy extends LocationableWithSetter {
    protected final Game game;
    public final int worth;
    public final int weight;

    public final int maxHealth;
    public final int speed;
    public final double size;
    public final boolean flying;

    protected int health;
    protected double pathCompleted;

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
        int speed,
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
        this.pathCompleted = 0.0;
        this.health = maxHealth;
    }

    @Override
    public Location getLocation() {
        return this.location;
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
     * Returns the percentage of the path this enemy has completed.
     * 
     * @return The percentage of the path this enemy has completed.
     */
    public double getPathCompleted() {
        return this.pathCompleted;
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
}
