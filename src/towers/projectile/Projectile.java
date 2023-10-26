package towers.projectile;

import enemies.Enemy;
import game.Game;
import java.util.ArrayList;
import location.Location;
import location.Locationable;
import towers.DamageTower;


/**
 * A projectile that can be fired by a tower.
 */
public abstract class Projectile {
    protected final Game game;
    public final DamageTower tower;
    public final Locationable source;
    public Enemy target;
    protected final double damage;
    protected final boolean shouldMove;
    protected int ticksElapsed;

    /**
     * Constructs a projectile.
     * 
     * @param game       The game this projectile is in.
     * @param tower      The tower that fired this projectile.
     * @param source     The source of the projectile (does not nessesarily have to be the tower).
     * @param target     The target of the projectile.
     * @param damage     The damage of the projectile.
     * @param shouldMove Whether or not the location of the points it draws from should move.
     */
    public Projectile(
        Game game,
        DamageTower tower,
        Locationable source,
        Enemy target,
        double damage,
        boolean shouldMove
    ) {
        this.game = game;
        this.tower = tower;
        this.source = source;
        this.target = target;
        this.damage = damage;
        this.shouldMove = shouldMove;
        this.ticksElapsed = 0;

        // Define the final locations if it is not going to move.
        if (!this.shouldMove) {
            this.getSourceLocation();
            this.getTargetLocation();
        }
    }

    final double maxNewTargetDistance = 5.0;

    private void tryToFindNewTarget() {
        Location targetLocation = this.target.getLocation();
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
        if (this.target.isDead()) {
            this.tryToFindNewTarget();
            if (this.target == null) {
                return true;
            }
        }
        boolean shouldRemove = this.duringTick();
        this.ticksElapsed++;
        return shouldRemove;
    }

    private Location sourceLocation;
    private Location targetLocation;

    public Location getSourceLocation() {
        if (this.shouldMove) {
            return this.source.getLocation();
        }
        if (this.sourceLocation == null) {
            this.sourceLocation = this.source.getLocation();
        }
        return this.sourceLocation;
    }

    public Location getTargetLocation() {
        if (this.shouldMove) {
            return this.target.getLocation();
        }
        if (this.targetLocation == null) {
            this.targetLocation = this.target.getLocation();
        }
        return this.targetLocation;
    }
}
