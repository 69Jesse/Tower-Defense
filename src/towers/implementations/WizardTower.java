package towers.implementations;

import enemies.Enemy;
import game.Game;
import java.awt.Color;
import java.util.ArrayList;
import location.Location;
import towers.RangeDamageTower;
import towers.projectile.LineProjectile;
import towers.projectile.Projectile;

/**
 * A wizard tower.
 */
public final class WizardTower extends RangeDamageTower {
    private static final int COST = 200;
    private static final int MAX_LEVEL = 3;
    private static final int COOLDOWN = 60;
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
                return "./assets/towers/wizard_tower_1.png";
            case 2:
                return "./assets/towers/wizard_tower_2.png";
            case 3:
                return "./assets/towers/wizard_tower_3.png";
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

    private static final boolean SHOULD_MOVE = false;
    private static final int TICKS_UNTIL_HIT = 0;
    private static final int TICKS_UNTIL_DELETE = 30;
    private static final double LINE_WIDTH = 0.25;
    private static final Color LINE_COLOR = new Color(0x76428A);
    private final double maxBounceRange = 5.0;

    // Max purely visual offset of the projectiles source and target per dimension.
    // So the actual max offset would be sqrt(pow(maxVisualOffset, 2) * 2))
    private final double maxVisualOffset = 1.0;

    private Enemy getClosestEnemy(ArrayList<Enemy> enemies, Location to) {
        Enemy closestEnemy = null;
        double closestDistance = Double.MAX_VALUE;

        for (Enemy enemy : enemies) {
            double distance = enemy.getLocation().distanceTo(to);
            if (distance >= this.maxBounceRange) {
                continue;
            }
            if (distance < closestDistance) {
                closestEnemy = enemy;
                closestDistance = distance;
            }
        }
        return closestEnemy;
    }

    /**
     * Returns the number of bounces the lightning can make.
     * 
     * @return The number of bounces the lightning can make.
     */
    private int getBounceCount() {
        switch (this.level) {
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 5;
            default:
                throw new RuntimeException("Invalid level: " + this.level);
        }
    }

    /**
     * Returns a random offset for the visual effect of the projectile.
     */
    private double getVisualOffset(int index) {
        return this.game.towerRandom.nextDouble()
            * this.maxVisualOffset * 2 - this.maxVisualOffset
            * ((index) / (double) this.getBounceCount());
    }

    @Override
    protected Projectile[] createProjectiles(Enemy enemy) {
        int bounceCount = this.getBounceCount();
        Projectile[] projectiles = new Projectile[bounceCount];
        ArrayList<Enemy> potentialEnemies = new ArrayList<>(this.game.field.enemies);
        Enemy lastEnemy = enemy;

        for (int i = 0; i < bounceCount; i++) {
            if (i != 0) {
                enemy = this.getClosestEnemy(potentialEnemies, enemy.getLocation());
                if (enemy == null) {
                    break;
                }
            }
            projectiles[i] = new LineProjectile(
                this.game,
                this,
                i == 0 ? this : lastEnemy,
                enemy,
                this.getDamage(),
                SHOULD_MOVE,
                TICKS_UNTIL_HIT,
                TICKS_UNTIL_DELETE,
                LINE_WIDTH,
                LINE_COLOR
            );
            lastEnemy = enemy;
            potentialEnemies.remove(enemy);
        }

        // Offset the projectiles slightly to make it look more like electricity.
        for (int i = 0; i < bounceCount; i++) {
            if (projectiles[i] == null) {
                continue;
            }
            double xOffset = this.getVisualOffset(i);
            double yOffset = this.getVisualOffset(i);
            Location targetLocation = projectiles[i].getTargetLocation();
            projectiles[i].setTargetLocation(
                new Location(
                    targetLocation.x + xOffset,
                    targetLocation.y + yOffset
                )
            );
            if (i == bounceCount - 1 || projectiles[i + 1] == null) {
                break;
            }
            Location sourceLocation = projectiles[i + 1].getSourceLocation();
            projectiles[i + 1].setSourceLocation(
                new Location(
                    sourceLocation.x + xOffset,
                    sourceLocation.y + yOffset
                )
            );
        }

        return projectiles;
    }

    @Override
    public void onTargetHit(Enemy target, double damage) {
        target.onHit(damage);
    }

    @Override
    public String[] getInfo() {
        return new String[] {
            String.format("Wizard Tower (Lvl %d)", this.level),
            "Multiple Target. Has a chain damage reaction",
            String.format("Damage: %.1f", this.getDamage()),
            String.format("Range: %.1f", this.getRange()),
            String.format("Cooldown: %d", this.getCooldown()),
            String.format("Total spent: %d", this.getTotalSpent()),
            String.format("Targeting mode: %s", this.getTargetingMode().getName())
        };
    }
}
