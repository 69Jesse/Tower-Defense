abstract class Tower implements Locationable {
    protected final Game game;
    protected final Location location;
    public final int cost;
    public final int maxLevel;
    public final int cooldown;

    public int level = 1;            // The level of this tower.
    public int speedMultiplier = 1;  // The speed multiplier of this tower.
    public int remainingCooldown;    // Remaining cooldown until the next action in game ticks.

    /**
     * Constructs a new tower.
     * 
     * @param game     The game this tower is in.
     * @param location The location of this tower on the field.
     * @param cost     The cost to build this tower.
     * @param maxLevel The maximum level this tower can be upgraded to.
     * @param cooldown The cooldown of this tower after each action in game ticks.
     */
    public Tower(
        Game game,
        Location location,
        int cost,
        int maxLevel,
        int cooldown
    ) {
        this.game = game;
        this.location = location;
        this.cost = cost;
        this.maxLevel = maxLevel;
        this.cooldown = cooldown;

        // Always wait half a seconds before the first action.
        this.remainingCooldown = this.game.ticksPerSecond / 2;
    }

    /**
     * Returns the location of this tower on the field.
     * 
     * @return The location of this tower on the field.
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Returns whether or not this tower can be upgraded.
     * 
     * @return Whether or not this tower can be upgraded.
     */
    public boolean canUpgrade() {
        return this.level < this.maxLevel;
    }

    /**
     * Upgrades this tower.
     */
    public void upgrade() throws IllegalStateException {
        if (!this.canUpgrade()) {
            throw new IllegalStateException("Tower cannot be upgraded.");
        }
        this.level++;
    }

    /**
     * Returns whether or not this tower can perform an action.
     * 
     * @return Whether or not this tower can perform an action.
     */
    public boolean canAct() {
        return this.remainingCooldown <= 0;
    }

    /**
     * Performs an action.
     */
    public void act() throws IllegalStateException {
        if (!this.canAct()) {
            throw new IllegalStateException("Tower cannot act.");
        }
        this.remainingCooldown = this.cooldown;
    }

    /**
     * Returns whether or not this tower can be sold.
     * 
     * @return Whether or not this tower can be sold.
     */
    public boolean canSell() {
        return true;
    }

    /**
     * Sells this tower.
     */
    public void sell() throws IllegalStateException {
        if (!this.canSell()) {
            throw new IllegalStateException("Tower cannot be sold.");
        }
        this.game.field.sellTower(this);
    }

    /**
     * Returns the cost to upgrade this tower.
     * 
     * @return The cost to upgrade this tower.
     */
    public int getUpgradeCost() {
        // TODO
        return 1;
    }

    /**
     * Returns the total amount of gold spent on this tower.
     * 
     * @return The total amount of gold spent on this tower.
     */
    public int getTotalSpent() {
        // TODO
        return 1;
    }

    /**
     * Returns the gold earned by selling this tower.
     * 
     * @return The gold earned by selling this tower.
     */
    public int getSellCost() {
        return this.getTotalSpent() / 2;
    }
}


abstract class DamageTower extends Tower {
    public final int damage;

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
}


abstract class RangeDamageTower extends DamageTower {
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
    public boolean canDamage(Enemy enemy) {
        return this.location.distanceTo(enemy) <= this.range;
    }
}


class ArcherTower extends RangeDamageTower {
    /**
     * Constructs an archer tower.
     */
    public ArcherTower(Game game, Location location) {
        super(
            game,
            location,
            100,
            3,
            40,
            10,
            5
        );
    }
}
