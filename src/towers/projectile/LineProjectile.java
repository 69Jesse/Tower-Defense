package towers.projectile;

import enemies.Enemy;
import game.Game;
import java.awt.Color;
import location.Locationable;
import towers.DamageTower;


/**
 * A projectile that can be fired by a tower that is a line.
 */
public final class LineProjectile extends Projectile {
    private final int ticksUntilHit;
    private final int ticksUntilDelete;
    public final double lineWidth;
    public final Color lineColor;

    /**
     * Constructs a line projectile.
     * 
     * @param game             The game this projectile is in.
     * @param tower            The tower that fired this projectile.
     * @param source           The source of the projectile
     *                         (does not nessesarily have to be the tower).
     * @param target           The target of the projectile.
     * @param damage           The damage of the projectile.
     * @param ticksUntilHit    The amount of ticks this projectile waits before hitting its target.
     * @param ticksUntilDelete The amount of ticks this projectile waits before deleting itself.
     * @param lineWidth        The width of the line of the projectile in field pixels.
     * @param lineColor        The color of the line of the projectile.
     */
    public LineProjectile(
        Game game,
        DamageTower tower,
        Locationable source,
        Enemy target,
        double damage,
        int ticksUntilHit,
        int ticksUntilDelete,
        double lineWidth,
        Color lineColor
    ) {
        super(
            game,
            tower,
            source,
            target,
            damage
        );
        this.ticksUntilHit = ticksUntilHit;
        this.ticksUntilDelete = ticksUntilDelete;
        this.lineWidth = lineWidth;
        this.lineColor = lineColor;
    }

    @Override
    protected boolean duringTick() {
        if (this.ticksElapsed == this.ticksUntilHit) {
            this.tower.onTargetHit(this.target, this.damage);
        }
        return this.ticksElapsed >= this.ticksUntilDelete;
    }
}
