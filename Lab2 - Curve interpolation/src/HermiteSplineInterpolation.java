

import Jama.Matrix;

import java.util.LinkedList;
import java.util.Random;

public class HermiteSplineInterpolation extends Interpolation {

    LinkedList<Vector_2> slopeList;

    public HermiteSplineInterpolation(Draw frame) {
        super(frame);
        this.slopeList = new LinkedList<Vector_2>();
        slopeList.add(new Vector_2(100, 100));
        slopeList.add(new Vector_2(-100, 0));
        slopeList.add(new Vector_2(0, 100));
        slopeList.add(new Vector_2(-100, 100));
        slopeList.add(new Vector_2(300, 100));
    }

    public Vector_2[] linkedListArray() {
        Vector_2[] slopeListArray = new Vector_2[slopeList.size()];
        int i = 0;
        for (Vector_2 v : slopeList) {
            slopeListArray[i] = v;
            i++;
        }
        return slopeListArray;
    }

    @Override
    public void interpolate() {
        int pointsSize = this.points.length;
        int slopesSize = this.slopeList.size();

        for (int i = 0; i < pointsSize; i++)
            drawPoint(this.points[i]);

        for (int i = 0; i < (pointsSize - slopesSize); i++) {
            Random randomGenerator = new Random();
            slopeList.add(new Vector_2(100 * randomGenerator.nextInt(10) - 500, 100 * randomGenerator.nextInt(10) - 500));
        }


        plotHermite(points, this.linkedListArray());

    }


    public void plotHermite(Point_2[] p, Vector_2[] sL) {
        int myLength = p.length;
        double[][] myDoubles = {
                {1, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 1, 0, 0},
                {0, 1, 2, 3}
        };
        Matrix myMatrix = new Matrix(myDoubles);
        double dx;
        for (int pi = 0; pi < myLength - 1; pi++) {
            double[][] myYxs = {{p[pi].x, p[pi + 1].x, getSlope(pi).x, getSlope(pi + 1).x}};
            double[][] myYys = {{p[pi].y, p[pi + 1].y, getSlope(pi).y, getSlope(pi + 1).y}};
            Matrix myYx = new Matrix(myYxs).transpose();
            Matrix myYy = new Matrix(myYys).transpose();

            Matrix myCoeffx = myMatrix.solve(myYx);
            Matrix myCoeffy = myMatrix.solve(myYy);

            plotPolynomialCurve(myCoeffx.getColumnPackedCopy(), myCoeffy.getColumnPackedCopy(), 20);


            drawSegment(p[pi].sum(new Vector_2(getSlope(pi).divisionByScalar(4).toDouble()).opposite()),
                    p[pi].sum(new Vector_2(getSlope(pi).divisionByScalar(4).toDouble())));
            
        }
    }


    public Vector_2 getSlope(int pointIndex) {
        return linkedListArray()[pointIndex];
    }

    void plotPolynomialCurve(double[] coeffX, double[] coeffY, int n) {
        Point_2[] plotPoints = new Point_2[n + 1];
        double space = (double) 1. / n;

        for (int i = 0; i <= n; i++) {
            double t = (double) i * space;
            double xValue = 0;
            for (int l = 0; l < coeffX.length; l++)
                xValue += coeffX[l] * Math.pow(t, l);

            double yValue = 0;
            for (int l = 0; l < coeffY.length; l++)
                yValue += coeffY[l] * Math.pow(t, l);

            plotPoints[i] = new Point_2(xValue, yValue);
        }

        for (int i = 0; i < n; i++)
            drawSegment(plotPoints[i], plotPoints[i + 1]);
    }


}
