package field;

import enemies.Enemy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import location.Location;
import towers.Tower;


/**
 * Field class.
 */
public class Field {
    public final int width = 80;  // The width of the field in (field) pixels.
    public final int height = 45;  // The height of the field in (field) pixels.

    public ArrayList<Location> waypoints;
    public ArrayList<Location> path;
    public ArrayList<Location> placeable;

    public HashMap<Location, Tower> towers;
    public ArrayList<Enemy> enemies;

    /**
     * Constructs a new field.
     */
    public Field() {
        this.init();
    }

    private void init() {
        this.towers = new HashMap<>();
        this.enemies = new ArrayList<>();
        this.createWaypoints();
        this.createPath();
        this.createPlaceable();
    }

    public void reset() {
        this.init();
    }

    private Random random = new Random();

    private Location randomLocation() {
        final double margin = Math.max(this.width, this.height) * 0.1;
        double x = this.random.nextDouble() * (this.width - 2 * margin) + margin;
        double y = this.random.nextDouble() * (this.height - 2 * margin) + margin;
        return new Location(x, y);
    }

    private void createWaypoints() {
        double margin = 0.1;
        Location start = new Location(
            0,
            this.random.nextDouble() * this.height * (1 - 2 * margin) + margin * this.height
        );
        Location end = new Location(
            this.width,
            this.random.nextDouble() * this.height * (1 - 2 * margin) + margin * this.height
        );

        this.waypoints = new ArrayList<>();
        this.waypoints.add(start);
        final int n = 3;
        for (int i = 0; i < n; i++) {
            this.waypoints.add(this.randomLocation());
        }
        this.waypoints.add(end);
    }

    private void createPath() {
        final int steps = 1001;
        CubicSpline2D spline = new CubicSpline2D(this.waypoints);
        this.path = spline.calculateLocations(steps);
    }

    private void createPlaceable() {
        this.placeable = new ArrayList<>();
        final int n = 5;
        for (int i = 0; i < n; i++) {
            this.placeable.add(this.randomLocation());
        }
    }

    /**
     * Adds a tower to the field.
     * 
     * @param tower                     The tower to add.
     * @throws IllegalArgumentException If the tower is not placeable.
     */
    public void addTower(Tower tower) throws IllegalArgumentException {
        Location location = tower.getLocation();
        if (!this.placeable.contains(location)) {
            throw new IllegalArgumentException("Tower is not placeable at this location.");
        }
        if (this.towers.containsKey(location)) {
            throw new IllegalArgumentException("Tower already exists at this location.");
        }
        this.towers.put(location, tower);
    }

    /**
     * Removes a tower from the field.
     * 
     * @param tower                     The tower to remove.
     * @throws IllegalArgumentException If the tower does not exist.
     */
    public void removeTower(Tower tower) throws IllegalArgumentException {
        boolean removed = this.towers.remove(tower.getLocation(), tower);
        if (!removed) {
            throw new IllegalArgumentException("Tower does not exist at this location.");
        }
    }
}
