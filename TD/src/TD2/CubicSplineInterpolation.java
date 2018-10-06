package TD2;

import Jama.Matrix;
import processing.core.PConstants;

/**
 * Cubic spline interpolation
 *
 * @author Luca Castelli Aleardi (INF555, 2014)
 */
@SuppressWarnings("Duplicates")
public class CubicSplineInterpolation extends Interpolation {
    private static double TANGENT_FIRST = 0.;
    private static double TANGENT_LAST = 0.;
    private static float TANGENT_LENGTH = 40f;


    public CubicSplineInterpolation(Draw frame) {
        super(frame);
    }

    public static double[] computeCoefficients(Point_2[] P) {
        int n = P.length;
        if (n <= 1) {
            return new double[0];
        }

        int size = 4 * (n - 1);

        Matrix W = new Matrix(size, size, 0.);
        Matrix Y = new Matrix(size, 1, 0.);

        // Condition on first tangent
        for (int j = 1; j < 4; j++) {
            W.set(0, j, j * Math.pow(P[0].x, j - 1));
        }
        // Condition on last tangent
        for (int j = 1; j < 4; j++) {
            W.set(size - 1, j + 4 * (n - 2), j * Math.pow(P[n - 1].x, j - 1));
        }

        // Conditions f_i(x_i) = y_i, f_i(x_{i+1}) = y_{i+1},
        for (int i = 0; i < n - 1; i++) {
            Point_2 pi = P[i];
            Point_2 pi1 = P[i + 1];

            for (int j = 0; j < 4; j++) {
                W.set(4 * i + 1, 4 * i + j, Math.pow(pi.x, j));
                W.set(4 * i + 2, 4 * i + j, Math.pow(pi1.x, j));
            }
        }

        // Derivatives continuity : f'_i(x_i+1) = f'_i+1(x_{i+1}), f''
        for (int i = 1; i < n - 1; i++) {
            Point_2 pi = P[i];
            W.set(4 * (i - 1) + 3, 4 * (i - 1) + 1, 1);
            W.set(4 * (i - 1) + 3, 4 * (i - 1) + 2, 2 * pi.x);
            W.set(4 * (i - 1) + 3, 4 * (i - 1) + 3, 3 * pi.x * pi.x);

            W.set(4 * (i - 1) + 3, 4 * i + 1, -1);
            W.set(4 * (i - 1) + 3, 4 * i + 2, -2 * pi.x);
            W.set(4 * (i - 1) + 3, 4 * i + 3, -3 * pi.x * pi.x);

            W.set(4 * i, 4 * (i-1) + 2, 2);
            W.set(4 * i, 4 * (i-1) + 3, 6 * pi.x);

            W.set(4 * i, 4 * i  + 2, -2);
            W.set(4 * i, 4 * i + 3, -6 * pi.x);
        }

        for (int i = 0; i < n - 1; i++) {
            Y.set(4 * i + 1, 0, P[i].y);
            Y.set(4 * i + 2, 0, P[i + 1].y);
        }
        Y.set(0, 0, TANGENT_FIRST);
        Y.set(size - 1, 0, TANGENT_LAST);

        Matrix X = W.solve(Y);
        return X.getColumnPackedCopy();
    }

    /**
     * Evaluate polynomial a[0]+a[1]x+a[2]x^2+...+a[n]x^n, at point x
     */
    public static double evaluate(double[] a, double x) {
        if (a == null || a.length == 0) throw new Error("polynomial not defined");
        double result = a[0];
        double p = 1.;
        for (int i = 1; i < a.length; i++) {
            p = p * x;
            //System.out.println(""+a[i]+"*"+p+"="+(a[i]*p));
            result = result + (a[i] * p);
        }
        return result;
    }

    public void interpolate() {

        double[] coefficients = computeCoefficients(this.points);
        frame.colorMode(PConstants.HSB);
        for (int i = 0; i < points.length - 1; i++) {
            double[] subcoeffs = {coefficients[4*i],
                    coefficients[4*i + 1],
                    coefficients[4*i + 2],
                    coefficients[4*i + 3]};


            // Compute stuff for tangent
            double[] derivative = {coefficients[4*i+1],
                    2*coefficients[4*i + 2],
                    3*coefficients[4*i + 3]};
            double slope = evaluate(derivative, points[i].x);

            //Evaluate dx to have a fixed length tangent
            double dx = TANGENT_LENGTH / Math.sqrt(1+slope*slope);

            frame.stroke((i*255.f)/points.length, 180, 200);
            plotPolynomial(subcoeffs, this.points[i].getX(), this.points[i + 1].getX(), 50);
            drawSegment(points[i].sum(new Vector_2(-dx, -dx*slope)),
                    points[i].sum(new Vector_2(dx, dx*slope)));
        }

        for (Point_2 q : this.points) {
            drawPoint(q); // draw input points
        }

    }

    public void plotPolynomial(double[] a, double min, double max, int n) {
        double dx = (max - min) / n;

        double x = min;
        for (int i = 0; i < n; i++) {
            Point_2 p = new Point_2(x, evaluate(a, x));
            Point_2 q = new Point_2(x + dx, evaluate(a, x + dx));
            this.drawSegment(p, q);
            x = x + dx;
        }

    }

}
