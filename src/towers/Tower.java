package towers;

import game.Game;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;
import location.Location;
import location.Locationable;


/**
 * A tower in the game.
 */
public abstract class Tower extends Locationable {
    protected final Game game;
    public final int cost;
    public final int maxLevel;
    protected final int cooldown;

    public int level = 1;          // The level of this tower.
    public int remainingCooldown;  // Remaining cooldown until the next action in game ticks.

    public static final Image UNPLACED_IMAGE = new ImageIcon(
        "./assets/towers/unplaced.png"
    ).getImage();

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
     * Returns whether or not this tower can be upgraded.
     * 
     * @return Whether or not this tower can be upgraded.
     */
    public boolean canUpgrade() {
        return this.level < this.maxLevel;
    }

    /**
     * Upgrades this tower.
     * 
     * @throws IllegalStateException If this tower cannot be upgraded.
     */
    public void upgrade() throws IllegalStateException {
        if (!this.canUpgrade()) {
            throw new IllegalStateException("Tower cannot be upgraded.");
        }
        this.level++;
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
     * Returns the value of this tower when sold.
     * 
     * @return The value of this tower when sold.
     */
    public int getSellValue() {
        return this.getTotalSpent() / 2;
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
    public abstract void act();

    /**
     * Performs an action.
     */
    public final void tick() {
        this.remainingCooldown--;
        int cooldown = this.getCooldown();
        this.remainingCooldown = Math.min(this.remainingCooldown, cooldown);
        if (this.canAct()) {
            this.act();
            this.remainingCooldown = cooldown;
        }
    }

    /**
     * Returns the image path of this tower.
     * This can be dependent on the level of this tower.
     * 
     * @return The image path of this tower.
     */
    public abstract String getImagePath();

    // The images of this tower based on its level.
    public HashMap<Integer, Image> images = new HashMap<>();

    /**
     * Returns the image of this tower.
     * 
     * @return The image of this tower.
     */
    public Image getImage() {
        Image image = this.images.getOrDefault(this.level, null);
        if (image == null) {
            image = new ImageIcon(this.getImagePath()).getImage();
            this.images.put(this.level, image);
        }
        return image;
    }

    /**
     * Returns the multiplier of the cooldown of this tower.
     * This can be dependent on the level of this tower.
     * 
     * @return The multiplier of the cooldown of this tower.
     */
    protected abstract double cooldownMultiplier();

    /**
     * Returns the cooldown of this tower.
     * 
     * @return The cooldown of this tower.
     */
    public int getCooldown() {
        return (int) (this.cooldown * this.cooldownMultiplier());
    }
}
