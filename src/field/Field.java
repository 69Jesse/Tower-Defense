package field;

import enemies.Enemy;
import game.Game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import location.Location;
import towers.Projectile;
import towers.Tower;


/**
 * Field class.
 */
public class Field {
    private final Game game;

    public final int width = 80;  // The width of the field in (field) pixels.
    public final int height = 45;  // The height of the field in (field) pixels.

    public ArrayList<Location> waypoints;
    public ArrayList<Location> path;
    public ArrayList<Location> placeable;
    public double totalDistance;
    // Mapping from the index of a point in the path to the distance from the start.
    public LinkedHashMap<Integer, Double> distancesFromStart;

    public HashMap<Location, Tower> towers;
    public ArrayList<Enemy> enemies;
    public ArrayList<Projectile> projectiles;

    /**
     * Constructs a new field.
     */
    public Field(Game game) {
        this.game = game;
    }

    /**
     * Initializes the field.
     */
    public void init() {
        this.towers = new HashMap<>();
        this.enemies = new ArrayList<>();
        this.projectiles = new ArrayList<>();
        this.createPath();
        this.createPlaceable();
    }

    /**
     * Generates a random location within the field.
     * 
     * @return The random location.
     */
    private Location randomLocation() {
        final double margin = Math.max(this.width, this.height) * 0.1;
        double x = this.game.random.nextDouble() * (this.width - 2 * margin) + margin;
        double y = this.game.random.nextDouble() * (this.height - 2 * margin) + margin;
        return new Location(x, y);
    }

    // Excluding the start and end point.
    private final int waypointsAmount = 3;
    private final int placeableAmount = 12;
    private final int maxPathRetries = 10000;
    private final int maxWaypointRetries = 10000;
    private final int maxPlaceableRetries = 10000;

    /**
     * Creates the waypoints.
     */
    private void createWaypoints() {
        final double margin = 0.1;
        Location start = new Location(
            0,
            this.game.random.nextDouble() * this.height * (1 - 2 * margin) + margin * this.height
        );
        Location end = new Location(
            this.width,
            this.game.random.nextDouble() * this.height * (1 - 2 * margin) + margin * this.height
        );

        int retries = 0;
        this.waypoints = new ArrayList<>();
        this.waypoints.add(start);
        for (int i = 0; i < this.waypointsAmount; i++) {
            this.waypoints.add(this.randomLocation());
            if (!validAngle(i, end)) {
                this.waypoints.remove(i + 1);
                i--;
                retries++;
                if (retries > this.maxWaypointRetries) {
                    // Insanely unlikely to reach this point, but just in case.
                    throw new RuntimeException("Could not generate valid waypoints.");
                }
            }
        }
        this.waypoints.add(end);
    }

    /**
     * Creates the path.
     */
    private void createPath() {
        int retries = 0;
        do {
            if (retries > this.maxPathRetries) {
                // Should actually never happen.
                throw new RuntimeException("Could not generate a valid path.");
            }

            try {
                this.createWaypoints();
            } catch (RuntimeException e) {
                retries++;
                continue;
            }

            final int steps = 1001;
            CubicSpline2D spline = new CubicSpline2D(this.waypoints);
            this.path = spline.calculateLocations(steps);

            if (!validPath()) {
                this.path = null;
            }
            retries++;
        } while (this.path == null);

        this.totalDistance = 0.0;
        this.distancesFromStart = new LinkedHashMap<>();
        for (int i = 0; i < this.path.size() - 1; i++) {
            Location location1 = this.path.get(i);
            Location location2 = this.path.get(i + 1);
            double distance = location1.distanceTo(location2);
            this.distancesFromStart.put(i, this.totalDistance);
            this.totalDistance += distance;
        }
        this.distancesFromStart.put(this.path.size() - 1, this.totalDistance);
    }

    /**
     * Creates the placeable locations for the towers.
     */
    private void createPlaceable() {
        int retries = 0;
        this.placeable = new ArrayList<>();
        for (int i = 0; i < this.placeableAmount; i++) {
            Location possibleLocation = this.randomLocation();
            if (validTowerLocation(possibleLocation)) {
                this.placeable.add(possibleLocation);
            } else {
                // The location is not a valid location, so we will have to try again.
                i--;
                retries++;
                if (retries > this.maxPlaceableRetries) {
                    // Really unlikely to reach this point, but just in case.
                    throw new RuntimeException("Could not generate valid Placeables.");
                }
            }
        }
    }

    /**
     * Checks if the latest points of the path have a valid angle.
     * 
     * @param pointNumber The amount of points that have been generated.
     * @param end         The end point of the path.
     * @return            Whether the angle is valid.
     */
    private boolean validAngle(int pointNumber, Location end) {
        if (pointNumber == 0) {
            return true;
        }
        final double sharpAngle = 30.0; // The angels sharper than this angle, are too sharp.
        double angle = this.calculateAngle(pointNumber, end);
        if (angle < sharpAngle || angle > (360 - sharpAngle)) {
            return false;
        }
        if (pointNumber == 2) {
            angle = this.calculateAngle(pointNumber + 1, end);
            if (angle < sharpAngle || angle > (360 - sharpAngle)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates the angle of the path between 
     * the first and second point and the second and third point.
     * 
     * @param pointNumber The amount of points that have been generated.
     * @return            The angle.
     */
    private double calculateAngle(int pointNumber, Location end) {
        double angle;
        double x1 = this.waypoints.get(pointNumber - 1).x; // the x-coordinate of the first point
        double y1 = this.waypoints.get(pointNumber - 1).y; // the y-coordinate of the first point
        double x2 = this.waypoints.get(pointNumber).x; // the x-coordinate of the second point
        double y2 = this.waypoints.get(pointNumber).y; // the y-coordinate of the second point
        double x3; // the x-coordinate of the third point
        double y3; // the y-coordinate of the third point

        if (pointNumber == this.waypointsAmount) {
            // The last point is the endpoint, which hasn't yet been added to the waypoints.
            x3 = end.x;
            y3 = end.y;
        } else {
            x3 = this.waypoints.get(pointNumber + 1).x;
            y3 = this.waypoints.get(pointNumber + 1).y;
        }

        // First we calculate the angle of the path between point 1 and 2 with the x-axis.
        double angleBetween1And2 = Math.atan2(y1 - y2, x1 - x2);
        angleBetween1And2 = Math.toDegrees(angleBetween1And2);

        // Then we calculate the angle of the path between point 1 and 2 with the x-axis.
        double angleBetween2And3 = Math.atan2(y3 - y2, x3 - x2);
        angleBetween2And3 = Math.toDegrees(angleBetween2And3);

        // Then we substract them to get the angle we need.
        angle = Math.abs(angleBetween1And2 - angleBetween2And3);

        return angle;
    }

    /**
     * Checks if the path is valid.
     * 
     * @return Whether the path is valid.
     */
    private boolean validPath() {
        // Tiny offset to prevent rounding errors.
        final double zero = -0.0001;
        final double width = this.width - zero;
        final double height = this.height - zero;

        // Check if the whole path is within the bounds of the field.
        for (Location location : this.path) {
            if (
                location.x < zero || location.x > width
                    || location.y < zero || location.y > height
            ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a placeable location of a tower is valid.
     * @param location The location that will be checked for validity.
     * @return Whether the location is valid.
     */
    private boolean validTowerLocation(Location location) {
        final double closeDistanceTower = 5.0; // Distance where a location is too close to a tower.
        final double closeDistancePath = 3.7; // Distance where a location is too close to the path.
        final double farDistance = 8.0; // Distance where a location is too far from the path.

        // The tower location cannot be too close to another tower.
        for (Location placeableLocation : this.placeable) {
            double distance = placeableLocation.distanceTo(location);
            if (distance < closeDistanceTower) {
                // The location is too close to another tower, so it is invalid.
                return false;
            }
        }

        // The tower location has to be close enough to the path.
        // But not on the path.
        boolean tooFar = true; // Whether the location is too far from the path.
        for (Location pathPoint : this.path) {
            double distance = pathPoint.distanceTo(location);
            if (distance < closeDistancePath) {
                // The location is too close to the path, so it is invalid.
                return false;
            }
            if (distance < farDistance) {
                // The location is close enough to the path.
                tooFar = false;
            }
        }
        if (tooFar) {
            // The location is too far from the path, so it is invalid.
            return false;
        }

        return true;
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

    /**
     * Adds an enemy to the beginning of the field.
     * 
     * @param enemy The enemy to add.
     */
    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    /**
     * Sorts the enemies by percentage done.
     */
    public void sortEnemies() {
        this.sortEnemies(this.enemies);
    }

    /**
     * Sorts the enemies by percentage done from low to high.
     * 
     * @param enemies The enemies to sort.
     */
    public void sortEnemies(ArrayList<Enemy> enemies) {
        enemies.sort((enemy1, enemy2) -> {
            double percentage1 = enemy1.percentageDone();
            double percentage2 = enemy2.percentageDone();
            if (percentage1 < percentage2) {
                return -1;
            } else if (percentage1 > percentage2) {
                return 1;
            } else {
                return 0;
            }
        });
    }

    /**
     * Returns whether all enemies are dead.
     * 
     * @return Whether all enemies are dead.
     */
    public boolean noEnemiesAlive() {
        return this.enemies.size() == 0;
    }
}
