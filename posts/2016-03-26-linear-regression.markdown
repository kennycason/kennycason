---
title: Linear Regression - Least-Squares Estimation
author: Kenny Cason
tags: machine learning, java
---

<a href="https://en.wikipedia.org/wiki/Linear_regression" target="blank">Linear Regression</a> is the process of fitting a line to a plot of points. The simplest and most commonly used estimator is the Least-squares estimation.

The source can be found on <a href="https://github.com/kennycason/ml" target="blank">GitHub</a>. The exact source can be found in the regression module.

```java
public class LinearRegression {

    /*
     * generate a linear model of the form y = b0 + x * b1
     *
     * The line minimizes the sum of squared differences between observed values (y)
     * and predicted values
     *
     * b1 = Σ[(xi - E(x))(yi - E(y))] / Σ[(xi - E(x))^2]
     * b1 = r * (σy / σx)
     * b0 = E(y) - b1 * E(x)
     *
     * Coefficient of determination (accuracy)
     * R2 = ( 1/n * Σ[(xi - E(x)) * (yi - E(y)] ) / (σx * σy))^2
     */
    public LinearModel regress(final double[][] points) {
        final double meanX = mean(points, 0);
        final double meanY = mean(points, 1);
        final double sumDeltaSquaredX = sumDeltaSquared(points, meanX, 0);
        final double sumDeltaMeansCorrelation = sumDeltaMeansMultiplied(points, meanX, meanY);

        // calculate slope and y-intercept
        final double b1 = sumDeltaMeansCorrelation / sumDeltaSquaredX;
        final double b0 = meanY - b1 * meanX;

        // calculate error
        final double standardDeviationX = standardDeviation(points, meanX, 0);
        final double standardDeviationY = standardDeviation(points, meanY, 1);
        final double coefficientOfDetermination = Math.pow(((1.0 / points.length) * sumDeltaMeansCorrelation) / (standardDeviationX * standardDeviationY), 2);

        return new LinearModel(new Line(b1, b0), coefficientOfDetermination);
    }

    private static double standardDeviation(final double[][] points, final double mean, final int index) {
        final double variance = sumDeltaSquared(points, mean, index) / points.length;
        return Math.sqrt(variance);
    }

    private static double sumDeltaSquared(final double points[][], final double mean, final int index) {
        double sumDeltaSquares = 0.0;
        for(double[] point : points) {
            sumDeltaSquares += (point[index] - mean) * (point[index] - mean);
        }
        return sumDeltaSquares;
    }

    private static double mean(final double[][] points, final int index) {
        double sum = 0.0;
        for(double[] point : points) {
            sum += point[index];
        }
        return sum / points.length;
    }

    private static double sumDeltaMeansMultiplied(final double[][] points, final double meanX, final double meanY) {
        double sum = 0.0;
        for(double[] point : points) {
            sum += (point[0] - meanX) * (point[1] - meanY);
        }
        return sum;
    }

}
```

LinearModel.java - A representation of the regressed line, as well as the fit error.
```java
public class LinearModel {
    public final Line line;
    public final double error;

    public LinearModel(final Line line, final double error) {
        this.line = line;
        this.error = error;
    }

    public Line getLine() {
        return line;
    }

    public double distance(final double x, final double y) {
        return Math.abs(y - f(x));
    }

    public boolean isAbove(final double x, final double y) {
        return y > f(x);
    }

    public double predict(final double x) {
        return f(x);
    }

    private double f(final double x) {
        return line.f(x);
    }

    public double getError() {
        return error;
    }
}
```

Line.java - A simply line
```java
public class Line {
    private final double slope;
    private final double yIntercept;

    public Line(final double slope, final double yIntercept) {
        this.slope = slope;
        this.yIntercept = yIntercept;
    }

    public double f(final double x) {
        return slope * x + yIntercept;
    }

    public double getSlope() {
        return slope;
    }

    public double getyIntercept() {
        return yIntercept;
    }
}
```

Unit Test / Demo
```java
public class TestLinearRegression {

    private static final LinearRegression LINEAR_REGRESSION = new LinearRegression();

    private static final double DELTA = 0.005;

    @Test
    public void line() {
        final double[][] grades = new double[][] {
                {-1, -1},
                {0, 0},
                {1, 1}
        };

        final LinearModel linearModel = LINEAR_REGRESSION.regress(grades);
        assertEquals(1.0, linearModel.getLine().getSlope(), DELTA);
        assertEquals(0.0, linearModel.getLine().getyIntercept(), DELTA);
        assertEquals(1.0, linearModel.coefficientOfDetermination, DELTA);
    }

    @Test
    public void grades() {
        final double[][] grades = new double[][] {
                {95, 85},
                {85, 95},
                {80, 70},
                {70, 65},
                {60, 70}
        };

        final LinearModel linearModel = LINEAR_REGRESSION.regress(grades);
        assertEquals(0.64, linearModel.getLine().getSlope(), DELTA);
        assertEquals(26.78, linearModel.getLine().getyIntercept(), DELTA);
        assertEquals(0.48, linearModel.coefficientOfDetermination, DELTA);
    }

}
```
