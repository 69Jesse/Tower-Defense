package field;

import enemy.Enemy;
import java.util.ArrayList;
import java.util.Random;
import location.Location;
import tower.base.Tower;


/**
 * Field class.
 */
public class Field {
    public final int width = 160;  // The width of the field in pixels.
    public final int height = 90;  // The height of the field in pixels.

    public ArrayList<Tower> towers;
    public ArrayList<Enemy> enemies;

    public ArrayList<Location> waypoints;
    public ArrayList<Location> path;
    public ArrayList<Location> placeable;


    /**
     * Constructs a new field.
     */
    public Field() {
        this.init();
    }

    private void init() {
        this.towers = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.createWaypoints();
        this.createPath();
        this.createPlaceable();
    }

    private Random random = new Random();

    private Location randomLocation() {
        final double margin = Math.max(this.width, this.height) * 0.1;
        double x = this.random.nextDouble() * (this.width - 2 * margin) + margin;
        double y = this.random.nextDouble() * (this.height - 2 * margin) + margin;
        return new Location(x, y);
    }

    private void createWaypoints() {
        Location start = new Location(0, this.height / 2);
        Location end = new Location(this.width, this.height / 2);

        this.waypoints = new ArrayList<>();
        this.waypoints.add(start);
        final int n = 4;
        for (int i = 0; i < n; i++) {
            this.waypoints.add(this.randomLocation());
        }
        this.waypoints.add(end);
    }

    private void createPath() {
        final int steps = 101;
        CubicSpline2D spline = new CubicSpline2D(this.waypoints);
        this.path = spline.calculateLocations(steps);
        System.out.println(this.path);
    }

    private void createPlaceable() {
        this.placeable = new ArrayList<>();
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
        for (Tower t : this.towers) {
            if (t.getLocation().equals(location)) {
                throw new IllegalArgumentException("Tower already exists at this location.");
            }
        }
        this.towers.add(tower);
    }

    /**
     * Removes a tower from the field.
     * 
     * @param tower                     The tower to remove.
     * @throws IllegalArgumentException If the tower does not exist.
     */
    public void removeTower(Tower tower) throws IllegalArgumentException {
        boolean removed = this.towers.remove(tower);
        if (!removed) {
            throw new IllegalArgumentException("Tower does not exist.");
        }
    }
}
