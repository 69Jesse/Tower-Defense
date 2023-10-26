package towers.projectile;

import enemies.Enemy;
import game.Game;
import java.util.ArrayList;
import location.BaseLocationable;
import location.Location;
import location.Locationable;
import towers.DamageTower;


/**
 * A projectile that can be fired by a tower.
 */
public abstract class Projectile {
    protected final Game game;
    protected final DamageTower tower;
    protected final BaseLocationable source;
    protected BaseLocationable target;
    protected final double damage;
    protected final boolean shouldMove;
    protected final boolean shouldFindNewTarget;
    protected int ticksElapsed;

    /**
     * Constructs a projectile.
     * 
     * @param game          The game this projectile is in.
     * @param tower         The tower that fired this projectile.
     * @param source        The source of the projectile (not always equal the tower).
     * @param target        The target of the projectile.
     * @param damage        The damage of the projectile.
     * @param shouldMove    Whether or not the location of the points it draws from should move.
     * @param findNewTarget Whether or not it should find a new target when relevant.
     */
    public Projectile(
        Game game,
        DamageTower tower,
        BaseLocationable source,
        BaseLocationable target,
        double damage,
        boolean shouldMove,
        boolean findNewTarget
    ) {
        this.game = game;
        this.tower = tower;
        this.source = source;
        this.target = target;
        this.damage = damage;
        this.shouldMove = shouldMove;
        this.shouldFindNewTarget = findNewTarget;
        this.ticksElapsed = 0;

        // Define the final locations if it is not going to move.
        if (!this.shouldMove) {
            this.getSourceLocation();
            this.getTargetLocation();
        }
    }

    final double maxNewTargetDistance = 5.0;

    private void tryToFindNewEnemyTarget() {
        Location targetLocation = this.getTargetLocation();
        ArrayList<Enemy> possibleTargets = new ArrayList<>();
        for (Enemy enemy : this.game.field.enemies) {
            if (!enemy.isDead()) {
                if (!this.tower.canDamage(enemy)) {
                    continue;
                }
                double distance = enemy.getLocation().distanceTo(targetLocation);
                if (distance < this.maxNewTargetDistance) {
                    possibleTargets.add(enemy);
                }
            }
        }
        if (possibleTargets.size() == 0) {
            this.target = null;
            return;
        }
        this.game.field.sortEnemies(possibleTargets);
        this.target = possibleTargets.get(possibleTargets.size() - 1);
    }

    /**
     * Method that gets called during each tick.
     * 
     * @return Whether or not this projectile should be removed.
     */
    protected abstract boolean duringTick();

    /**
     * Tick this projectile.
     * 
     * @return Whether or not this projectile should be removed.
     */
    public boolean tick() {
        if (this.target instanceof Enemy) {
            if (((Enemy) this.target).isDead()) {
                if (this.shouldFindNewTarget) {
                    this.tryToFindNewEnemyTarget();
                    if (this.target == null) {
                        return true;
                    }
                }
            }
        }
        boolean shouldRemove = this.duringTick();
        this.ticksElapsed++;
        return shouldRemove;
    }

    private Location sourceLocation;
    private Location targetLocation;

    /**
     * Returns the location of the source of this projectile.
     * This takes into account whether or not the source can move.
     * 
     * @return The location of the source of this projectile.
     */
    public Location getSourceLocation() {
        // `sourceLocation` could have been set by `setSourceLocation` even if `shouldMove` is true.
        if (this.sourceLocation != null) {
            return this.sourceLocation;
        }
        if (this.shouldMove) {
            return this.source.getLocation();
        }
        if (this.sourceLocation == null) {
            this.sourceLocation = this.source.getLocation();
        }
        return this.sourceLocation;
    }

    /**
     * Returns the location of the target of this projectile.
     * This takes into account whether or not the target can move.
     * 
     * @return The location of the target of this projectile.
     */
    public Location getTargetLocation() {
        // `targetLocation` could have been set by `setTargetLocation` even if `shouldMove` is true.
        if (this.targetLocation != null) {
            return this.targetLocation;
        }
        if (this.shouldMove) {
            return this.target.getLocation();
        }
        if (this.targetLocation == null) {
            this.targetLocation = this.target.getLocation();
        }
        return this.targetLocation;
    }

    /**
     * Forcibly sets the source location of this projectile.
     * This is purely visual and can be used to make this
     * look better in specific cases.
     */
    public void setSourceLocation(Location location) {
        this.sourceLocation = location;
    }

    /**
     * Forcibly sets the target location of this projectile.
     * This is purely visual and can be used to make this
     * look better in specific cases.
     */
    public void setTargetLocation(Location location) {
        this.targetLocation = location;
    }

    /**
     * A dummy that can be used as a target for projectiles
     * so it can target a location instead of an enemy.
     */
    public class DummyTarget extends Locationable {
        public DummyTarget(Location location) {
            this.location = location;
        }
    }
}
