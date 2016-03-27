---
title: Linear Gradient Descent
author: Kenny Cason
tags: java, machine learning, regression, gradient descent
---

<a href="https://en.wikipedia.org/wiki/Gradient_descent" target="blank">Gradient descent</a> is the foundation of many machine learning algorithms, including the all popular <a href="https://en.wikipedia.org/wiki/Backpropagation" target="blank">Backpropagation</a> algorithm popularly used in neural networks. Gradients can used to determine how much a previous layer's neurons contributed to the current layer's activation.

To measured the gradient we must be able to take the partial derivative with respect to all parameters, in the case of linear gradient descent: `∂/∂m` and `∂/∂b`, where `m` is the slope, and `b` is the y-intercept. Below is a simple example using linear gradient descent to fit a line to a set of points. The code is commented alongside mathematical derivations of all partial derivatives.

The source can be found on <a href="https://github.com/kennycason/ml" target="blank">GitHub</a>. The exact source can be found in the regression module.

```{.java .numberLines startFrom="1"}
public class LinearGradientDescent {

    public LinearModel regress(final Parameters parameters) {
        Line currentLine = parameters.getStartLine();
        for (int i = 0; i < parameters.getIterations(); i++) {
            currentLine = stepGradient(currentLine, parameters);
        }
        return new LinearModel(currentLine, computeTotalError(parameters.getPoints(), currentLine));
    }

    // perform gradient descent
    // since y = mx + b has two parameters that we are optimizing (m and b),
    // take the partial derivative with respect to each variable. we will use this to
    // walk the gradient with respect to both variables.
    // specifically walk the gradient of the error function
    private Line stepGradient(final Line currentLine, final Parameters parameters) {
        final double learningRate = parameters.getLearningRate();
        final double[][] points = parameters.getPoints();
        final double n = points.length;
        // set a start slope/y-intercept, arbitrary line
        final double currentSlope = currentLine.getSlope();
        final double currentYIntercept = currentLine.getyIntercept();
        // the current gradient
        double slopeGradient = 0;
        double yInterceptGradient = 0;

        for (final double[] point : points) {
            slopeGradient += computeErrorDerivativeRespectToSlope(point, currentSlope, currentYIntercept, n);
            yInterceptGradient += computeErrorDerivativeRespectToYIntercept(point, currentSlope, currentYIntercept, n);
        }
        final double regressedSlope = currentSlope - (learningRate * slopeGradient);
        final double regressedYIntercept = currentYIntercept - (learningRate * yInterceptGradient);
        return new Line(regressedSlope, regressedYIntercept);
    }

    // y = mx + b
    // total error = 1/N ∑(y - (mx + b))^2
    // compute error of supplied line against the known y value.
    public double computeTotalError(final double[][] points, final Line line) {
        double totalError = 0;
        for (final double[] point : points) {
            totalError += computeError(point, line);
        }
        return totalError / points.length;
    }

    private double computeError(final double[] point, final Line line) {
        final double slope = line.getSlope();
        final double yIntercept = line.getyIntercept();
        final double x = point[0];
        final double y = point[1];
        return Math.pow(y - (slope * x + yIntercept), 2);
    }

    // 1/N ∑(y - (mx + b))^2
    // ∂/∂m   1/N ∑(y - (mx + b))^2
    //      = 1/N ∑(y - (mx - b))(y - (mx - b))
    //      = 1/N ∑(y - mx - b)(y - mx - b)
    //      = 1/N ∑(y^2 - ymx - yb - ymx + m^2x^2 + bmx - yb + bmx + b^2)
    //      = 1/N ∑(y^2 -2ymx - 2yb + m^2x^2 + 2bmx + b^2)

    // now start taking the derivative with respect to m
    //      = 1/N ∑(- 2yx + 2mx^2 + 2bx)
    //      = 1/N ∑(- 2x (y - mx - b))
    // ∂/∂m = 2/N ∑(- x (y - mx - b)
    private double computeErrorDerivativeRespectToSlope(final double[] point,
                                                        final double slope,
                                                        final double yIntercept,
                                                        final double n) {
        final double x = point[0];
        final double y = point[1];
        return (2 / n) * -x * (y - slope * x - yIntercept);
    }

    // now start taking the derivative with respect to y-intercept
    // ∂/∂b = 1/N ∑(- 2y + 2mx + 2b)
    //      = 2/N ∑(-y + mx + b)
    //      = 2/N ∑-(y - mx - b)
    private double computeErrorDerivativeRespectToYIntercept(final double[] point,
                                                             final double slope,
                                                             final double yIntercept,
                                                             final double n) {
        final double x = point[0];
        final double y = point[1];
        return (2 / n) * -(y - slope * x - yIntercept);
    }
}
```

Parameters.java - This class contains the basic parameters for linear gradient descent.
```{.java .numberLines startFrom="1"}
public class Parameters {
    private int iterations = 1000;
    private double learningRate = 0.001;
    private Line startLine = new Line(0, 0);
    private double[][] points;

    private Parameters() {}

    public static Parameters with(final double[][] points) {
        final Parameters parameters = new Parameters();
        parameters.points = points;
        return parameters;
    }

    public Parameters setIterations(final int iterations) {
        this.iterations = iterations;
        return this;
    }

    public Parameters setLearningRate(final double learningRate) {
        this.learningRate = learningRate;
        return this;
    }

    public Parameters setStartLine(final Line startLine) {
        this.startLine = startLine;
        return this;
    }

    public int getIterations() {
        return iterations;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public Line getStartLine() {
        return startLine;
    }

    public double[][] getPoints() {
        return points;
    }
}
```

LinearModel.java - A representation of the regressed line, as well as the fit error.
```{.java .numberLines startFrom="1"}
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
```{.java .numberLines startFrom="1"}
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

TestLinearGradientDescent.java
```{.java .numberLines startFrom="1"}
public class TestLinearGradientDescent {
    private static final LinearGradientDescent LINEAR_GRADIENT_DESCENT = new LinearGradientDescent();
    private static final double LEARNING_RATE = 0.001;
    private static final int ITERATIONS = 5000;
    private static final double LINE_DELTA = 0.002;
    private static final double GRADES_DELTA = 0.2;

    @Test
    public void line() {
        final double[][] points = new double[][] {
                {-1, -1},
                {0, 0},
                {1, 1}
        };

        final LinearModel linearModel = LINEAR_GRADIENT_DESCENT.regress(
                                                Parameters.with(points)
                                                          .setLearningRate(LEARNING_RATE)
                                                          .setIterations(ITERATIONS));

        assertEquals(1.0, linearModel.getLine().getSlope(), LINE_DELTA);
        assertEquals(0.0, linearModel.getLine().getyIntercept(), LINE_DELTA);
        assertEquals(0.0, linearModel.error, LINE_DELTA);
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

        final LinearModel linearModel = LINEAR_GRADIENT_DESCENT.regress(
                                               Parameters.with(grades)
                                                         .setStartLine(new Line(5, 25))
                                                         .setLearningRate(LEARNING_RATE / 1000)
                                                         .setIterations(ITERATIONS * 10000));
        // the expected values are known exactly
        assertEquals(0.64, linearModel.getLine().getSlope(), GRADES_DELTA);
        assertEquals(26.78, linearModel.getLine().getyIntercept(), GRADES_DELTA);
        assertEquals(65.48, linearModel.error, GRADES_DELTA);
    }

}
```
