package towers;

import enemies.Enemy;
import game.Game;
import java.util.ArrayList;
import location.Location;


/**
 * A tower that can damage enemies that are in a specific range.
 */
public abstract class RangeDamageTower extends DamageTower {
    protected final double range;
    protected final boolean canAttackFlying;

    /**
     * Constructs a tower that can damage enemies that are in a specific range.
     * 
     * @param game     The game this tower is in.
     * @param location The location of this tower on the field.
     * @param cost     The cost to build this tower.
     * @param maxLevel The maximum level this tower can be upgraded to.
     * @param cooldown The cooldown of this tower after each action in game ticks.
     * @param damage   The amount of damage this tower does to enemies.
     * @param range    The range of this tower.
     */
    public RangeDamageTower(
        Game game,
        Location location,
        int cost,
        int maxLevel,
        int cooldown,
        int damage,
        double range,
        boolean canAttackFlying
    ) {
        super(
            game,
            location,
            cost,
            maxLevel,
            cooldown,
            damage
        );
        this.range = range;
        this.canAttackFlying = canAttackFlying;
    }

    /**
     * Returns whether or not this tower can damage an enemy because of flight.
     * 
     * @param enemy The enemy to check.
     * @return      Whether or not this tower can damage the enemy because of flight.
     */
    protected boolean canDamageWithFlight(Enemy enemy) {
        return this.canAttackFlying || !enemy.flying;
    }

    /**
     * Returns whether or not this tower can damage an enemy.
     * 
     * @param enemy The enemy to check.
     * @return      Whether or not this tower can damage the enemy.
     */
    protected boolean canDamage(Enemy enemy) {
        return (this.location.distanceTo(enemy) - enemy.size / 2) <= this.getRange()
            && this.canDamageWithFlight(enemy);
    }

    /**
     * Returns a list of enemies that can be damaged by this tower.
     * 
     * @return A list of enemies that can be damaged by this tower.
     */
    protected ArrayList<Enemy> damagableEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (Enemy enemy : this.game.field.enemies) {
            if (this.canDamage(enemy)) {
                enemies.add(enemy);
            }
        }
        return enemies;
    }

    @Override
    protected Enemy findEnemy() {
        ArrayList<Enemy> enemies = this.damagableEnemies();
        if (enemies.size() == 0) {
            return null;
        }
        this.game.field.sortEnemies(enemies);
        return enemies.get(enemies.size() - 1);
    }

    /**
     * Returns the multiplier of the range of this tower.
     * This can be dependent on the level of this tower.
     * 
     * @return The multiplier of the range of this tower.
     */
    protected abstract double rangeMultiplier();

    /**
     * Returns the range of this tower.
     * 
     * @return The range of this tower.
     */
    public double getRange() {
        return this.range * this.rangeMultiplier();
    }

    /**
     * Creates a projectile that can damage an enemy.
     * 
     * @param enemy The enemy to damage.
     */
    protected abstract Projectile createProjectile(Enemy enemy);

    /**
     * Fires a projectile at an enemy.
     * 
     * @param enemy The enemy to fire at.
     */
    protected void fireAtEnemy(Enemy enemy) {
        Projectile projectile = this.createProjectile(enemy);
        this.game.field.projectiles.add(projectile);
    }

    /**
     * Performs an action.
     */
    public void act() {
        Enemy enemy = this.findEnemy();
        if (enemy == null) {
            return;
        }
        this.fireAtEnemy(enemy);
    }
}
