package towers;

import enemies.Enemy;
import game.Game;
import java.util.ArrayList;
import java.util.function.Function;
import location.Location;
import towers.projectile.Projectile;


/**
 * A tower that can damage enemies that are in a specific range.
 */
public abstract class RangeDamageTower extends DamageTower {
    protected final double range;
    protected TargetingMode targetingMode;

    /**
     * Constructs a tower that can damage enemies that are in a specific range.
     * 
     * @param game            The game this tower is in.
     * @param location        The location of this tower on the field.
     * @param cost            The cost to build this tower.
     * @param maxLevel        The maximum level this tower can be upgraded to.
     * @param cooldown        The cooldown of this tower after each action in game ticks.
     * @param damage          The amount of damage this tower does to enemies.
     * @param range           The range of this tower.
     * @param canAttackFlying Whether or not this tower can attack flying enemies.
     */
    public RangeDamageTower(
        Game game,
        Location location,
        int cost,
        int maxLevel,
        int cooldown,
        double damage,
        double range,
        boolean canAttackFlying
    ) {
        super(
            game,
            location,
            cost,
            maxLevel,
            cooldown,
            damage,
            canAttackFlying
        );
        this.range = range;
        this.targetingMode = TargetingMode.FIRST;
    }

    @Override
    public boolean canDamage(Enemy enemy) {
        return (this.location.distanceTo(enemy) - enemy.size / 2) <= this.getRange()
            && super.canDamage(enemy);
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

    /**
     * Returns the target enemy of this tower.
     * 
     * @param enemies The enemies to choose from.
     * @return        The target enemy of this tower.
     */
    protected Enemy findTarget(ArrayList<Enemy> enemies) {
        Function<Enemy, Double> getValue;
        switch (this.targetingMode) {
            case FIRST:
                getValue = enemy -> -enemy.percentageDone();
                break;
            case LAST:
                getValue = enemy -> enemy.percentageDone();
                break;
            case STRONGEST:
                // Strongest is defined as the enemy with the highest max health.
                // After this it still has to be sorted by percentage done.
                // Assuming max health is bigger than 1.0, this will work.
                getValue = enemy -> -enemy.maxHealth - enemy.percentageDone();
                break;
            case WEAKEST:
                // Weakest is defined as the enemy with the lowest max health.
                getValue = enemy -> enemy.maxHealth - enemy.percentageDone();
                break;
            default:
                throw new RuntimeException("Invalid targeting mode: " + this.targetingMode);
        }

        enemies.sort((enemy1, enemy2) -> {
            double value1 = getValue.apply(enemy1);
            double value2 = getValue.apply(enemy2);
            if (value1 < value2) {
                return -1;
            } else if (value1 > value2) {
                return 1;
            } else {
                return 0;
            }
        });
        return enemies.get(0);
    }

    @Override
    protected Enemy findEnemy() {
        ArrayList<Enemy> enemies = this.damagableEnemies();
        if (enemies.size() == 0) {
            return null;
        }
        return this.findTarget(enemies);
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
     * Creates the projectiles that can damage an enemy.
     * Most of the time this will be a single projectile, but it can also be multiple.
     * 
     * @param enemy The enemy to damage.
     */
    protected abstract Projectile[] createProjectiles(Enemy enemy);

    /**
     * Fires a projectile at an enemy.
     * 
     * @param enemy The enemy to fire at.
     */
    protected void fireAtEnemy(Enemy enemy) {
        for (Projectile projectile : this.createProjectiles(enemy)) {
            this.game.field.projectiles.add(projectile);
        }
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

    /**
     * The targeting mode of this tower.
     */
    public enum TargetingMode {
        FIRST("First"),
        LAST("Last"),
        STRONGEST("Strongest"),
        WEAKEST("Weakest");

        private final String name;

        private TargetingMode(String name) {
            this.name = name;
        }
    
        /**
         * Returns the name of this targeting mode.
         * 
         * @return The name of this targeting mode.
         */
        public String getName() {
            return this.name;
        }
    }

    /**
     * Returns the targeting mode of this tower.
     * 
     * @return The targeting mode of this tower.
     */
    public TargetingMode getTargetingMode() {
        return this.targetingMode;
    }

    /**
     * Returns the next targeting mode of this tower when switched.
     * 
     * @return The next targeting mode of this tower when switched.
     */
    public TargetingMode nextTargetingMode() {
        return TargetingMode.values()[
            (this.targetingMode.ordinal() + 1) % TargetingMode.values().length
        ];
    }

    /**
     * Switch the targeting mode of this tower to the next targeting mode in the cycle.
     */
    public void switchTargetingMode() {
        this.targetingMode = this.nextTargetingMode();
    }
}
