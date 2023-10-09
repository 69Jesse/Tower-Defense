package field;

import java.util.ArrayList;


/**
 * A 1D cubic spline.
 */
public class CubicSpline1D {
    private ArrayList<Double> xValues;
    private ArrayList<Double> yValues;
    private int n;  // Number of points.

    private ArrayList<Double> hValues;
    private ArrayList<Double> aValues;
    private ArrayList<Double> bValues;
    private ArrayList<Double> cValues;
    private ArrayList<Double> dValues;

    /**
     * Constructs a new 1D cubic spline.
     * 
     * @param xValues The x values of the points.
     * @param yValues The y values of the points.
     */
    public CubicSpline1D(
        ArrayList<Double> xValues,
        ArrayList<Double> yValues
    ) {
        this.xValues = xValues;
        this.yValues = yValues;
        if (this.xValues.size() != this.yValues.size()) {
            throw new IllegalArgumentException(
                "The number of x values must be equal to the number of y values."
            );
        }
        this.n = this.xValues.size();
        this.init();
    }

    private void init() {
        for (double v : getDiff(this.xValues)) {
            if (v <= 0) {
                throw new IllegalArgumentException(
                    "The x values must be strictly increasing."
                );
            }
        }
        this.aValues = this.yValues;
        this.hValues = getDiff(this.xValues);
        this.calculateCValues();
        this.calculateBDValues();
    }

    /**
     * Calculate the difference between each value in a given array.
     * 
     * @param values The array of values.
     * @return       An array of the differences between each value in the given array.
     */
    public static ArrayList<Double> getDiff(ArrayList<Double> values) {
        ArrayList<Double> diff = new ArrayList<>();
        for (int i = 0; i < values.size() - 1; i++) {
            diff.add(values.get(i + 1) - values.get(i));
        }
        return diff;
    }

    /**
     * Solve a linear matrix equation, or system of linear scalar equations.
     * 
     * For example:
     *     Let a be                 b be      it would return
     *     [[1.0, 0.0, 0.0, 0.0],   [0.0,     [0.0,
     *      [1.0, 4.0, 1.0, 0.0],    27.0,     8.4,
     *      [0.0, 1.0, 4.0, 1.0],    -18.0,    -6.6,
     *      [0.0, 0.0, 0.0, 1.0]]      0.0]     0.0]
     * 
     * Because:
     * 1.0 * 0.0 + 0.0 * 8.4 + 0.0 * -6.6 + 0.0 * 0.0 = 0.0
     * 1.0 * 0.0 + 4.0 * 8.4 + 1.0 * -6.6 + 0.0 * 0.0 = 27.0
     * 0.0 * 0.0 + 1.0 * 8.4 + 4.0 * -6.6 + 1.0 * 0.0 = -18.0
     * 0.0 * 0.0 + 0.0 * 8.4 + 0.0 * -6.6 + 1.0 * 0.0 = 0.0
     * 
     * @param a The matrix of coefficients.
     * @param b Ordinate or "dependent variable" values.
     * @return The solution to the system ax = b. Returned shape is identical to b.
     */
    public static ArrayList<Double> linearSolve(
        double[][] a,
        double[] b
    ) {
        int numRows = a.length;
        int numCols = a[0].length;

        // Create a 2D array to hold the coefficients matrix (a) as doubles
        double[][] coefficients = new double[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            double[] row = a[i];
            for (int j = 0; j < numCols; j++) {
                coefficients[i][j] = (double) row[j];
            }
        }

        // Create a vector for the solution (x) as doubles and initialize it with the b values
        double[] solution = new double[numCols];
        for (int i = 0; i < numCols; i++) {
            solution[i] = (double) b[i];
        }

        // Gaussian Elimination
        for (int i = 0; i < numRows; i++) {
            int maxRowIndex = i;
            for (int j = i + 1; j < numRows; j++) {
                if (Math.abs(coefficients[j][i]) > Math.abs(coefficients[maxRowIndex][i])) {
                    maxRowIndex = j;
                }
            }

            // Swap rows if necessary
            if (maxRowIndex != i) {
                double[] tempRow = coefficients[i];
                coefficients[i] = coefficients[maxRowIndex];
                coefficients[maxRowIndex] = tempRow;

                double tempB = solution[i];
                solution[i] = solution[maxRowIndex];
                solution[maxRowIndex] = tempB;
            }

            // Eliminate entries below pivot
            for (int j = i + 1; j < numRows; j++) {
                double factor = coefficients[j][i] / coefficients[i][i];
                solution[j] -= factor * solution[i];
                for (int k = i; k < numCols; k++) {
                    coefficients[j][k] -= factor * coefficients[i][k];
                }
            }
        }

        // Back substitution
        for (int i = numCols - 1; i >= 0; i--) {
            for (int j = i + 1; j < numCols; j++) {
                solution[i] -= coefficients[i][j] * solution[j];
            }
            solution[i] /= coefficients[i][i];
        }

        // Convert the solution to an ArrayList of Doubles
        ArrayList<Double> solutionList = new ArrayList<>();
        for (int i = 0; i < numCols; i++) {
            solutionList.add(solution[i]);
        }

        return solutionList;
    }

    private double[][] getCoefficients() {
        double[][] coefficients = new double[this.n][this.n];
        coefficients[0][0] = 1;
        for (int i = 0; i < this.n - 1; i++) {
            coefficients[i + 1][i] = this.hValues.get(i);
            coefficients[i][i + 1] = this.hValues.get(i);
            if (i != this.n - 2) {
                coefficients[i + 1][i + 1] = 2 * (this.hValues.get(i) + this.hValues.get(i + 1));
            }
        }

        coefficients[0][1] = 0;
        coefficients[this.n - 1][this.n - 2] = 0;
        coefficients[this.n - 1][this.n - 1] = 1;

        return coefficients;
    }

    private double[] getOrdinates() {
        double[] ordinates = new double[this.n];
        for (int i = 0; i < this.n - 2; i++) {
            ordinates[i + 1] = (
                3 * (this.aValues.get(i + 2) - this.aValues.get(i + 1)) / this.hValues.get(i + 1)
                - 3 * (this.aValues.get(i + 1) - this.aValues.get(i)) / this.hValues.get(i)
                );  // It only allows this indentation for some reason.
        }
        return ordinates;
    }

    private void calculateCValues() {
        this.cValues = linearSolve(
            this.getCoefficients(),
            this.getOrdinates()
        );
    }

    private void calculateBDValues() {
        this.bValues = new ArrayList<>();
        this.dValues = new ArrayList<>();
        for (int i = 0; i < this.n - 1; i++) {
            this.bValues.add(
                (this.aValues.get(i + 1) - this.aValues.get(i)) / this.hValues.get(i)
                - this.hValues.get(i) * (this.cValues.get(i + 1) + 2 * this.cValues.get(i)) / 3
            );
            this.dValues.add(
                (this.cValues.get(i + 1) - this.cValues.get(i)) / (3 * this.hValues.get(i))
            );
        }
    }

    /**
     * Get the interval that a given value falls in.
     * 
     * @param x The value to find the interval of.
     * @return  The index of the interval that the given value falls in.
     */
    public int getInterval(double x) {
        try {
            int i = 0;
            while (x > this.xValues.get(i + 1)) {
                i++;
            }
            return i;
        } catch (IndexOutOfBoundsException e) {
            return this.n - 2;
        }
    }

    /**
     * Get the value of the spline at a given x value.
     * 
     * @param x The x value to get the value of the spline at.
     * @return  The value of the spline at the given x value.
     */
    public double getValue(double x) {
        int i = this.getInterval(x);
        // System.out.println("A: " + this.aValues);
        // System.out.println("B: " + this.bValues);
        // System.out.println("C: " + this.cValues);
        // System.out.println("D: " + this.dValues);
        double dx = x - this.xValues.get(i);
        double value = this.aValues.get(i)
            + this.bValues.get(i) * dx
            + this.cValues.get(i) * dx * dx
            + this.dValues.get(i) * dx * dx * dx;
        return value;
    }
}
