/**
 * @author Luca Castelli Aleardi (INF555, 2014)
 * <p>
 * Compute a Bezier Patch surface in 3D (with tensor product approach)
 */
public class BezierPatchTensorProduct extends Surface {

    public BezierPatchTensorProduct(DrawSurface frame) {
        super(frame);
    }

    /**
     * Initialize control polygon
     */
    public void initialize(int N, int M) {
        points = new Point_3[N][N];
        for (int i = 0; i < N; i++) {
            double x = ((double) i) / N;
            for (int j = 0; j < N; j++) {
                double y = ((double) j) / N;
                double z = (Math.pow(1.8 * (0.2f - x), 3)) * Math.pow(1.8 * (0.5f - y), 3);
                //double z = (double) j / N;
                //double y = x*z;

                points[i][j] = new Point_3(x, y, z);
                //System.out.print(" "+points[i][j]);
            }
            System.out.println();
        }
    }

    public Point_3 evaluate(double u, double v) {
        // According to the given implementation of BezierPatchTensorProduct, N==M
        return tensorProduct(points.length-1, points.length-1, u, v);
    }

    /**
     * Perform the (iterative) De Casteljau algorithm (to compute a 3D curve)
     */
    public Point_3 iterativeDeCasteljau(Point_3[] controlPolygon, double t) {
        throw new Error("Bonus: TD3");
    }

    /**
     * Compute the tensor product of two Bezier curves B(u), B(v)
     */
    public Point_3 tensorProduct(int m, int n, double u, double v) {

        // Pre-compute Bernstein
        double[] Bu = new double[m + 1];
        double[] Bv = new double[m + 1];

        double bu = Math.pow(1 - u, m);
        for (int i = 0; i <= m; i++) {
            Bu[i] = bu;
            bu *= u * (m - i) / ((1 - u) * (i + 1));
        }
        double bv = Math.pow(1 - v, n);
        for (int i = 0; i <= n; i++) {
            Bv[i] = bv;
            bv *= v * (n - i) / ((1 - v) * (i + 1));
        }

        Vector_3 x = new Vector_3();
        for (int i=0; i<=m; i++){
            for(int j=0; j<=n; j++){
                x = x.sum(new Vector_3(points[i][j].toDouble()).multiplyByScalar(Bu[i]*Bv[j]));
            }
        }
        return new Point_3(x.toDouble());
    }

}
