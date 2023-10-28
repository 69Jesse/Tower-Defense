package towers.implementations;

import enemies.Enemy;
import game.Game;
import location.Location;
import towers.RangeDamageTower;
import towers.projectile.DummyTarget;
import towers.projectile.ImageProjectile;
import towers.projectile.Projectile;


/**
 * A slingshot tower.
 */
public final class SlingshotTower extends RangeDamageTower {
    private static final int COST = 300;
    private static final int MAX_LEVEL = 3;
    private static final int COOLDOWN = 0;
    private static final double DAMAGE = 0.5;
    private static final double RANGE = 10.0;
    private static final boolean CAN_DAMAGE_FLYING = true;

    /**
     * Constructs a slingshot tower.
     * 
     * @param game     The game this tower is in.
     * @param location The location of this tower on the field.
     */
    public SlingshotTower(Game game, Location location) {
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
                return "./assets/towers/laser_tower_1.png";
            case 2:
                return "./assets/towers/laser_tower_2.png";
            case 3:
                return "./assets/towers/laser_tower_3.png";
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
                return 1.5;
            case 3:
                return 2.0;
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
                return 1.5;
            case 3:
                return 1.8;
            default:
                throw new RuntimeException("Invalid level: " + this.level);
        }
    }

    private static final boolean SHOULD_MOVE = false;
    private static final double PROJECTILE_SPEED = 0.5;
    private static final String PROJECTILE_IMAGE_PATH = "./assets/projectiles/slingshot_tower.png";
    private static final double PROJECTILE_SIZE = 1.0;
    private static final double PROJECTILE_MAX_CURVE = 0.0;

    /**
     * Returns the location of the border of the field that
     * is on the line through the tower and the enemy.
     * 
     * @param enemy The enemy to target.
     * @return      The location of the border of the field that
     */
    private Location getTargetLocation(Enemy enemy) {
        Location a = this.getLocation();
        Location b = enemy.getLocation();
        int width = this.game.field.width;
        int height = this.game.field.height;
    
        double dx = b.x - a.x;
        double dy = b.y - a.y;
    
        if (Math.abs(dx) > Math.abs(dy)) {
            // The line is steeper in the x-direction
            if (dx > 0) {
                // Intersection with the right border
                return new Location(
                    width,
                    a.y + (width - a.x) * dy / dx
                );
            } else {
                // Intersection with the left border
                return new Location(
                    0,
                    a.y - a.x * dy / dx
                );
            }
        } else {
            // The line is steeper in the y-direction
            if (dy > 0) {
                // Intersection with the bottom border
                return new Location(
                    a.x + (height - a.y) * dx / dy,
                    height
                );
            } else {
                // Intersection with the top border
                return new Location(
                    a.x - a.y * dx / dy,
                    0
                );
            }
        }
    }

    @Override
    protected Projectile[] createProjectiles(Enemy enemy) {
        return new Projectile[] {
            new ImageProjectile(
                this.game,
                this,
                this,
                new DummyTarget(this.getTargetLocation(enemy)),
                this.getDamage(),
                SHOULD_MOVE,
                PROJECTILE_SPEED,
                PROJECTILE_SIZE,
                PROJECTILE_MAX_CURVE,
                PROJECTILE_IMAGE_PATH
            )
        };
    }

    @Override
    public void onTargetHit(Enemy target, double damage) {
        target.onHit(damage);
    }

    @Override
    public String[] getInfo() {
        return new String[] {
            String.format("Laser Tower (Lvl %d)", this.level),
            "Single Target. Fires at a high frequency.",
            String.format("Damage: %.1f", this.getDamage()),
            String.format("Range: %.1f", this.getRange()),
            String.format("Cooldown: %d", this.getCooldown()),
            String.format("Total spent: %d", this.getTotalSpent()),
            String.format("Targeting mode: %s", this.getTargetingMode().getName())
        };
    }
}
