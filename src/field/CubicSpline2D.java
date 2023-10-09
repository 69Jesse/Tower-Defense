package field;

import java.util.ArrayList;
import java.util.Arrays;
import location.Location;


/**
 * A 2D cubic spline.
 */
public class CubicSpline2D {
    public static void main(String[] args) {
        ArrayList<Double> xValues = new ArrayList<>(Arrays.asList(
            0.0, 1.0, 2.0, 3.0
        ));
        ArrayList<Double> yValues = new ArrayList<>(Arrays.asList(
            5.0, 1.0, 6.0, 5.0
        ));
        CubicSpline2D spline = new CubicSpline2D(xValues, yValues);
        for (Location location : spline.getLocations(11)) {
            System.out.println(location);
        }
    }

    private ArrayList<Double> steps;
    private CubicSpline1D splineX;
    private CubicSpline1D splineY;

    /**
     * Constructs a new 2D cubic spline.
     * 
     * @param xValues The x values of the points.
     * @param yValues The y values of the points.
     */
    public CubicSpline2D(
        ArrayList<Double> xValues,
        ArrayList<Double> yValues
    ) {
        this.init(xValues, yValues);
    }

    /**
     * Constructs a new 2D cubic spline.
     * 
     * @param locations The locations of the points.
     */
    public CubicSpline2D(
        ArrayList<Location> locations
    ) {
        ArrayList<Double> xValues = new ArrayList<>();
        ArrayList<Double> yValues = new ArrayList<>();
        for (Location location : locations) {
            xValues.add(location.x);
            yValues.add(location.y);
        }
        this.init(xValues, yValues);
    }

    private void init(
        ArrayList<Double> xValues,
        ArrayList<Double> yValues
    ) {
        this.calculateSteps(xValues, yValues);
        this.splineX = new CubicSpline1D(this.steps, xValues);
        this.splineY = new CubicSpline1D(this.steps, yValues);
    }

    /**
     * Calculate the cumalative sum of a given array.
     * 
     * @param values The array of values.
     * @return       An array of the cumalative sum of the given array.
     */
    public static ArrayList<Double> cumalativeSum(ArrayList<Double> values) {
        ArrayList<Double> cumalativeSum = new ArrayList<>();
        cumalativeSum.add(values.get(0));
        for (int i = 1; i < values.size(); i++) {
            cumalativeSum.add(cumalativeSum.get(i - 1) + values.get(i));
        }
        return cumalativeSum;
    }

    private void calculateSteps(
        ArrayList<Double> xValues,
        ArrayList<Double> yValues
    ) {
        ArrayList<Double> xDiff = CubicSpline1D.getDiff(xValues);
        ArrayList<Double> yDiff = CubicSpline1D.getDiff(yValues);

        ArrayList<Double> temp = new ArrayList<>();
        temp.add(0.0);
        for (int i = 0; i < xDiff.size(); i++) {
            temp.add(Math.sqrt(
                Math.pow(xDiff.get(i), 2) + Math.pow(yDiff.get(i), 2)
            ));
        }
        this.steps = cumalativeSum(temp);
    }

    /**
     * Get the maximum distance along the spline.
     * 
     * @return The maximum distance along the spline.
     */
    private double maxS() {
        return this.steps.get(this.steps.size() - 1);
    }

    /**
     * Get the location of a point on the spline at a given distance along the spline.
     * The distance must be between 0 and 1 which correspond to the start and the end.
     * 
     * @param a The distance along the spline in the range [0, 1].
     * @return  The location of the point on the spline at the given distance.
     */
    public Location getLocation(double a) {
        if (a < 0 || a > 1) {
            throw new IllegalArgumentException(
                "The distance must be between 0 and 1."
            );
        }
        double s = a * this.maxS();
        double x = this.splineX.getValue(s);
        double y = this.splineY.getValue(s);
        return new Location(x, y);
    }

    /**
     * Get the locations of points on the spline at a given number of steps along the spline.
     * 
     * @param steps The number of steps along the spline.
     * @return      The locations of points on the spline at the given number of steps.
     */
    public ArrayList<Location> getLocations(int steps) {
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < steps; i++) {
            double a = (double) i / (steps - 1);  // Range [0, 1].
            locations.add(this.getLocation(a));
        }
        return locations;
    }
}
