package towers.implementations;

import enemies.Enemy;
import game.Game;
import location.Location;
import towers.Projectile;
import towers.RangeDamageTower;


/**
 * A laser tower.
 */
public final class LaserTower extends RangeDamageTower {
    private static final int COST = 300;
    private static final int MAX_LEVEL = 3;
    private static final int COOLDOWN = 0;
    private static final int DAMAGE = 2;
    private static final double RANGE = 10.0;    

    /**
     * Constructs a laser tower.
     * 
     * @param game     The game this tower is in.
     * @param location The location of this tower on the field.
     */
    public LaserTower(Game game, Location location) {
        super(
            game,
            location,
            COST,
            MAX_LEVEL,
            COOLDOWN,
            DAMAGE,
            RANGE
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
        return 0.0;
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
                return 2.0;
            default:
                throw new RuntimeException("Invalid level: " + this.level);
        }
    }

    private static final double PROJECTILE_SPEED = 0.5;
    private static final String PROJECTILE_IMAGE_PATH = "./assets/projectiles/laser_tower.png";
    private static final double PROJECTILE_SIZE = 1.0;
    private static final double PROJECTILE_MAX_CURVE = 0.0;

    @Override
    protected Projectile createProjectile(Enemy enemy) {
        return new Projectile(
            this.game,
            this,
            enemy,
            this.getDamage(),
            PROJECTILE_SPEED,
            PROJECTILE_IMAGE_PATH,
            PROJECTILE_SIZE,
            PROJECTILE_MAX_CURVE
        );
    }

    @Override
    public void onTargetHit(Enemy target, int damage) {
        target.onHit(this.damage);
    }
}
