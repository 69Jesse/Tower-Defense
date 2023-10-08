package tower.base;

import enemy.Enemy;
import game.Game;
import java.util.ArrayList;
import location.Location;


/**
 * A tower that can damage enemies that are in a specific range.
 */
public abstract class RangeDamageTower extends DamageTower {
    public final int range;

    /**
     * Constructs a tower that can damage enemies that are in a specific range.
     * 
     * @param game     The game this tower is in.
     * @param location The location of this tower on the field.
     * @param cost     The cost to build this tower.
     * @param maxLevel The maximum level this tower can be upgraded to.
     * @param cooldown The cooldown of this tower after each action in game ticks.
     * @param damage   The amount of damage this tower does to enemies.
     * @param range    The range of this tower.
     */
    public RangeDamageTower(
        Game game,
        Location location,
        int cost,
        int maxLevel,
        int cooldown,
        int damage,
        int range
    ) {
        super(
            game,
            location,
            cost,
            maxLevel,
            cooldown,
            damage
        );
        this.range = range;
    }

    /**
     * Returns whether or not this tower can damage an enemy.
     * 
     * @param enemy The enemy to check.
     * @return      Whether or not this tower can damage the enemy.
     */
    protected boolean canDamage(Enemy enemy) {
        return this.location.distanceTo(enemy) <= this.range;
    }

    /**
     * Returns a list of enemies that can be damaged by this tower.
     * 
     * @return A list of enemies that can be damaged by this tower.
     */
    protected ArrayList<Enemy> damagableEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (Enemy enemy : this.game.field.enemies) {
            if (this.canDamage(enemy)) {
                enemies.add(enemy);
            }
        }
        return enemies;
    }
}
