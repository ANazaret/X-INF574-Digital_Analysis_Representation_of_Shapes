import java.util.LinkedList;

public class Bezier extends Curve {

    public Transformation_2 transformation;
    public int transformationMode = 0;

    public Bezier(DrawCurve frame, LinkedList<Point_2> p) {
        super(frame, p);
        transformation = null;
    }

    public Bezier(DrawCurve frame, LinkedList<Point_2> p, Transformation_2 transformation) {
        super(frame, p);
        this.transformation = transformation;
    }

    public Bezier(DrawCurve frame, Point_2[] points, Transformation_2 transformation) {
        super(frame, points);
        this.transformation = transformation;
    }

    /**
     * Draw the control polygon
     */
    public void drawControlPolygon() {
        for (int i = 1; i < this.points.length; i++) {
            drawSegment(this.points[i], this.points[i - 1]);
            if (transformation != null)
                drawSegment(transformation.transform(points[i]),
                    transformation.transform(points[i-1]));
        }
        this.frame.stroke(0, 0, 0);
    }

    public void drawBezier(double dt) {
        this.frame.stroke(255, 0, 255);
        for (float t = 0.0f; t <= 1-dt; t += dt) {
            drawSegment(evaluate(t),evaluate(t + dt));
        }

        if (transformation != null){
            for (float t = 0.0f; t <= 1-dt; t += dt) {
                drawSegment(transformation.transform(evaluate(t)),transformation.transform(evaluate(t + dt)));
            }

            Point_2[] originalPoints = points.clone();
            for (int i=0; i<points.length; i++){
                points[i] = transformation.transform(points[i]);
            }

            this.frame.stroke(0, 125, 125);
            for (float t = 0.0f; t <= 1-dt; t += dt) {
                drawSegment(evaluate(t),evaluate(t + dt));
            }

            points = originalPoints;
        }



    }

    /**
     * Evaluate the curve for parameter t
     * Return point (x(t), y(t))
     */
    public Point_2 evaluate(double t) {
        //return recursiveDeCasteljau(this.points.length - 1, 0, t);
        //return iterativeDeCasteljau(t);
        return bernsteinBezier(t);
    }

    /**
     * Perform the subdivision (once) of the Bezier curve (with parameter t)
     * Return two Bezier curves (with n control points each)
     */
    public Bezier[] subdivide(double t) {
        Point_2[] controlPolygon = this.points;
        int n = this.points.length - 1; // degree and number of edges of the control polygon

        Point_2[] b0 = new Point_2[n + 1]; // first control polygon
        Point_2[] b1 = new Point_2[n + 1]; // second control polygon
        Bezier[] result = new Bezier[2]; // the pair of Bezier curves to return as result


        // Use the de Casteljau algorithm (iterative)
        Vector_2[] vector2s = new Vector_2[points.length];
        for (int i=0; i<points.length; i++){
            vector2s[i] = new Vector_2(points[i].toDouble());
        }
        b0[0] = points[0];
        b1[n] = points[n];

        for (int r=1; r<=n; r++){
            for(int i=0; i<=n-r; i++){
                vector2s[i] = vector2s[i].multiplyByScalar(1-t).sum(vector2s[i+1].multiplyByScalar(t));
            }
            b0[r] = new Point_2(vector2s[0].toDouble());
            b1[n-r] = new Point_2(vector2s[n-r].toDouble());
        }

        result[0] = new Bezier(frame,b0, transformation);
        result[1] = new Bezier(frame,b1, transformation);
        return result;
    }

    /**
     * Plot the curve (in the frame), for t=0..1, with step dt
     */
    public void plotCurve(double dt) {
        this.frame.stroke(0, 0, 255);
        this.drawControlPolygon();
        this.drawControlPoints();
        drawBezier(dt);

        Bezier[] subdivision = subdivide(0.5);
        this.frame.stroke(255, 0, 0);
        subdivision[0].drawControlPolygon();
        this.frame.stroke(0, 255, 255);
        subdivision[1].drawControlPolygon();

    }

    /**
     * Perform the rendering of the curve using subdivision approach
     * Perform the subdivision n times
     */
    public void subdivisionRendering(int n) {
        float red = 50f*(n%6);
        float green = 65f*((-n+500)%5);
        float blue = 255/(n+1.f);
        this.frame.stroke(red, green, blue);
        this.drawControlPolygon(); // draw original control polygon
        this.drawControlPoints(); // draw original control points
        LinkedList<Bezier> subCurves = new LinkedList<Bezier>();

        // to be completed TD INF574:
        if (this.points.length < 3) return;

        if (n > 0){
            Bezier[] subdivide = subdivide(0.5);
            subdivide[0].subdivisionRendering(n-1);
            subdivide[1].subdivisionRendering(n-1);
        }
    }

    public Point_2 recursiveDeCasteljau(int r, int i, double t) {
        if (r == 0) {
            return points[i];
        }

        Vector_2 pi = new Vector_2(recursiveDeCasteljau(r - 1, i, t).toDouble()).multiplyByScalar(1 - t);
        Vector_2 pi1 = new Vector_2(recursiveDeCasteljau(r - 1, i + 1, t).toDouble()).multiplyByScalar(t);
        return new Point_2(pi.sum(pi1).toDouble());
    }

    /**
     * Perform the (iterative) De Casteljau algorithm to evaluate b(t)
     */
    public Point_2 iterativeDeCasteljau(double t) {
        Vector_2[] vector2s = new Vector_2[points.length];
        for (int i=0; i<points.length; i++){
            vector2s[i] = new Vector_2(points[i].toDouble());
        }
        for (int r=points.length-1; r>0; r--){
            for(int i=0; i<r; i++){
                vector2s[i] = vector2s[i].multiplyByScalar(1-t).sum(vector2s[i+1].multiplyByScalar(t));
            }
        }

        return new Point_2(vector2s[0].toDouble());
    }

    Point_2 bernsteinBezier(double t){
        Vector_2 x = new Vector_2(points[0].toDouble());
        double s = 1-t;
        double tpow = 1;
        double binomial = 1;
        for (int i=1; i<points.length; i++){
            tpow*=t;
            binomial = binomial*(points.length-i)/i;
            x = x.multiplyByScalar(s).sum(
              new Vector_2(points[i].toDouble()).multiplyByScalar(tpow*binomial)
            );
        }
        return new Point_2(x.toDouble());
    }
}
