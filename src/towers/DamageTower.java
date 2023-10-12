package towers;

import game.Game;
import location.Location;


/**
 * A tower that can damage enemies.
 */
public abstract class DamageTower extends Tower {
    protected final int damage;

    /**
     * Constructs a tower that can damage enemies.
     * 
     * @param game     The game this tower is in.
     * @param location The location of this tower on the field.
     * @param cost     The cost to build this tower.
     * @param maxLevel The maximum level this tower can be upgraded to.
     * @param cooldown The cooldown of this tower after each action in game ticks.
     * @param damage   The amount of damage this tower does to enemies.
     */
    public DamageTower(
        Game game,
        Location location,
        int cost,
        int maxLevel,
        int cooldown,
        int damage
    ) {
        super(
            game,
            location,
            cost,
            maxLevel,
            cooldown
        );
        this.damage = damage;
    }

    /**
     * Returns the multiplier of the damage of this tower.
     * This can be dependent on the level of this tower.
     * 
     * @return The multiplier of the damage of this tower.
     */
    protected abstract double damageMultiplier();

    /**
     * Returns the damage of this tower.
     * 
     * @return The damage of this tower.
     */
    public double getDamage() {
        return this.damage * this.damageMultiplier();
    }
}
