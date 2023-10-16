package towers.implementations;

import enemies.Enemy;
import game.Game;
import location.Location;
import towers.Projectile;
import towers.RangeDamageTower;


/**
 * An archer tower.
 */
public final class ArcherTower extends RangeDamageTower {
    private static final int COST = 100;
    private static final int MAX_LEVEL = 3;
    private static final int COOLDOWN = 40;
    private static final int DAMAGE = 10;
    private static final double RANGE = 10.0;    

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
            RANGE
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
                return 0.4;
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
                return 1.5;
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

    @Override
    protected Projectile createProjectile(Enemy enemy) {
        return new Projectile(
            this,
            enemy,
            this.getDamage(),
            0.3,
            "./assets/projectiles/archer_tower.png",
            5.0
        );
    }
}
