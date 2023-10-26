package towers.implementations;

import enemies.Enemy;
import game.Game;
import java.awt.Color;
import location.Location;
import towers.RangeDamageTower;
import towers.projectile.LineProjectile;
import towers.projectile.Projectile;


/**
 * A wizard tower.
 */
public final class WizardTower extends RangeDamageTower {
    private static final int COST = 100;
    private static final int MAX_LEVEL = 3;
    private static final int COOLDOWN = 40;
    private static final double DAMAGE = 15.0;
    private static final double RANGE = 10.0;    
    private static final boolean CAN_DAMAGE_FLYING = true;

    /**
     * Constructs a wizard tower.
     * 
     * @param game     The game this tower is in.
     * @param location The location of this tower on the field.
     */
    public WizardTower(Game game, Location location) {
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

    private static final int TICKS_UNTIL_HIT = 0;
    private static final int TICKS_UNTIL_DELETE = 60;
    private static final double LINE_WIDTH = 0.5;
    private static final Color LINE_COLOR = new Color(0x76428A);

    @Override
    protected Projectile[] createProjectiles(Enemy enemy) {
        return new Projectile[] {
            new LineProjectile(
                this.game,
                this,
                this,
                enemy,
                this.getDamage(),
                TICKS_UNTIL_HIT,
                TICKS_UNTIL_DELETE,
                LINE_WIDTH,
                LINE_COLOR
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
            String.format("Archer Tower (Lvl %d)", this.level),
            "Multiple Target. Has a chain damage reaction",
            String.format("Damage: %.1f", this.getDamage()),
            String.format("Range: %.1f", this.getRange()),
            String.format("Cooldown: %d", this.getCooldown()),
            String.format("Total spent: %d", this.getTotalSpent()),
            String.format("Targeting mode: %s", this.getTargetingMode().getName())
        };
    }
}
