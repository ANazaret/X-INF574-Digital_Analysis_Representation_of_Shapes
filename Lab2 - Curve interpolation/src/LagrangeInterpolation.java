

import Jama.*;

/**
 * Lagrange interpolation
 *
 * @author Luca Castelli Aleardi (INF555, 2012)
 */
@SuppressWarnings("Duplicates")
public class LagrangeInterpolation extends Interpolation {

    double[] coeff = null;

    public LagrangeInterpolation(Draw frame) {
        super(frame);
    }

    public static double[] computeCoefficients(Point_2[] P) {
        int n = P.length;
        Matrix W = new Matrix(n, n);
        Matrix Y = new Matrix(n, 1);
        for (int i = 0; i < n; i++) {
            Y.set(i,0, P[i].y);
            for (int j = 0; j < n; j++) {
                W.set(i, j, Math.pow(P[i].x, j));
            }
        }

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
        if (this.points.length > 2) {
            this.coeff = computeCoefficients(this.points);
            plotPolynomial(this.coeff, this.points[0].getX(), this.points[this.points.length - 1].getX(), 300);
            //System.out.println("Interpolating polynomial: "+polynomialToString(this.coeff));
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
 
