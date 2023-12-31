package towers.implementations;

import enemies.Enemy;
import game.Game;
import java.util.ArrayList;
import location.Location;
import towers.RangeDamageTower;
import towers.projectile.ImageProjectile;
import towers.projectile.Projectile;


/**
 * A bomb tower.
 */
public final class BombTower extends RangeDamageTower {
    private static final int COST = 300;
    private static final int MAX_LEVEL = 3;
    private static final int COOLDOWN = 120;
    private static final double DAMAGE = 35.0;
    private static final double RANGE = 7.5;
    private static final boolean CAN_DAMAGE_FLYING = false;

    /**
     * Constructs a bomb tower.
     * 
     * @param game     The game this tower is in.
     * @param location The location of this tower on the field.
     */
    public BombTower(Game game, Location location) {
        super(
            game,
            location,
            COST,
            MAX_LEVEL,
            COOLDOWN,
            DAMAGE,
            RANGE,
            CAN_DAMAGE_FLYING
        );
    }

    @Override
    public String getImagePath() {
        switch (this.level) {
            case 1:
                return "./assets/towers/bomb_tower_1.png";
            case 2:
                return "./assets/towers/bomb_tower_2.png";
            case 3:
                return "./assets/towers/bomb_tower_3.png";
            default:
                throw new RuntimeException("Invalid level: " + this.level);
        }
    }

    @Override
    protected double cooldownMultiplier() {
        switch (this.level) {
            case 1:
                return 1.0;
            case 2:
                return 0.8;
            case 3:
                return 0.6;
            default:
                throw new RuntimeException("Invalid level: " + this.level);
        }
    }

    @Override
    protected double damageMultiplier() {
        switch (this.level) {
            case 1:
                return 1.0;
            case 2:
                return 1.4;
            case 3:
                return 1.8;
            default:
                throw new RuntimeException("Invalid level: " + this.level);
        }
    }

    @Override
    protected double rangeMultiplier() {
        switch (this.level) {
            case 1:
                return 1.0;
            case 2:
                return 1.3;
            case 3:
                return 1.5;
            default:
                throw new RuntimeException("Invalid level: " + this.level);
        }
    }

    private static final boolean SHOULD_MOVE = true;
    private static final double PROJECTILE_SPEED = 0.2;
    private static final String PROJECTILE_IMAGE_PATH = "./assets/projectiles/bomb_tower.png";
    private static final double PROJECTILE_SIZE = 3.0;
    private static final double PROJECTILE_MAX_CURVE = 6.0;

    @Override
    protected Projectile[] createProjectiles(Enemy enemy) {
        return new Projectile[] {
            new ImageProjectile(
                this.game,
                this,
                this,
                enemy,
                this.getDamage(),
                SHOULD_MOVE,
                PROJECTILE_SPEED,
                PROJECTILE_SIZE,
                PROJECTILE_MAX_CURVE,
                PROJECTILE_IMAGE_PATH
            )
        };
    }

    private static final double SPLASH_DAMAGE_RANGE = 3.0;

    /**
     * Returns whether or not an enemy is in splash range of a location.
     * 
     * @param enemy    The enemy to check.
     * @param location The (target) location to check.
     * @return         Whether or not the enemy is in splash range of the location.
     */
    private boolean enemyInSplashRange(Enemy enemy, Location location) {
        return enemy.getLocation().distanceTo(location) <= SPLASH_DAMAGE_RANGE;
    }

    @Override
    public void onTargetHit(Enemy target, double damage) {
        Location targetLocation = target.getLocation();
        ArrayList<Enemy> hitting = new ArrayList<>();
        for (Enemy enemy : this.game.field.enemies) {
            if (this.enemyInSplashRange(enemy, targetLocation) && this.canDamageWithFlight(enemy)) {
                hitting.add(enemy);
            }
        }
        for (Enemy enemy : hitting) {
            enemy.onHit(damage);
        }
    }

    @Override
    public String[] getInfo() {
        return new String[] {
            String.format("Bomb Tower (Lvl %d)", this.level),
            "Splash Damage. Cannot damage flying enemies.",
            String.format("Damage: %.1f", this.getDamage()),
            String.format("Range: %.1f", this.getRange()),
            String.format("Cooldown: %d", this.getCooldown()),
            String.format("Total spent: %d", this.getTotalSpent()),
            String.format("Targeting mode: %s", this.getTargetingMode().getName())
        };
    }
}
