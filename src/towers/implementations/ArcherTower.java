package towers.implementations;

import enemies.Enemy;
import game.Game;
import location.Location;
import towers.RangeDamageTower;
import towers.projectile.ImageProjectile;
import towers.projectile.Projectile;


/**
 * An archer tower.
 */
public final class ArcherTower extends RangeDamageTower {
    private static final int COST = 100;
    private static final int MAX_LEVEL = 3;
    private static final int COOLDOWN = 40;
    private static final double DAMAGE = 15.0;
    private static final double RANGE = 10.0;    
    private static final boolean CAN_DAMAGE_FLYING = true;

    /**
     * Constructs an archer tower.
     * 
     * @param game     The game this tower is in.
     * @param location The location of this tower on the field.
     */
    public ArcherTower(Game game, Location location) {
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
                return "./assets/towers/archer_tower_1.png";
            case 2:
                return "./assets/towers/archer_tower_2.png";
            case 3:
                return "./assets/towers/archer_tower_3.png";
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
                return 1.3;
            case 3:
                return 1.7;
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

    private static final double PROJECTILE_SPEED = 0.4;
    private static final String PROJECTILE_IMAGE_PATH = "./assets/projectiles/archer_tower.png";
    private static final double PROJECTILE_SIZE = 1.0;
    private static final double PROJECTILE_MAX_CURVE = 5.0;

    @Override
    protected Projectile createProjectile(Enemy enemy) {
        return new ImageProjectile(
            this.game,
            this,
            this,
            enemy,
            this.getDamage(),
            PROJECTILE_SPEED,
            PROJECTILE_SIZE,
            PROJECTILE_MAX_CURVE,
            PROJECTILE_IMAGE_PATH
        );
    }

    @Override
    public void onTargetHit(Enemy target, double damage) {
        target.onHit(this.getDamage());
    }

    @Override
    public String[] getInfo() {
        return new String[] {
            String.format("Archer Tower (Lvl %d)", this.level),
            "Single Target. Has a long range.",
            String.format("Damage: %.1f", this.getDamage()),
            String.format("Range: %.1f", this.getRange()),
            String.format("Cooldown: %d", this.getCooldown()),
            String.format("Total spent: %d", this.getTotalSpent()),
            String.format("Targeting mode: %s", this.getTargetingMode().getName())
        };
    }
}
