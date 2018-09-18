import Jama.Matrix;

/**
 * Define a 3D transformation (using homogeneous coordinates)
 *
 * @author Luca Castelli Aleardi (INF574, 2018)
 */
public class Transformation_3 {

	Matrix m;
	static final double precision=100.;
	
	/**
	 * Define a transformation in 3D using homogeneous coordinates, given as a 4x4 matrix
	 */
	public Transformation_3(Matrix m) {
		this.m=m;
	}

	/**
	 * Return the identity transformation
	 */
	public static Transformation_3 identity() {
		double[][] array = {{1.,0.,0.,0.},
							{0.,1.,0.,0.},
							{0.,0.,1.,0.},
							{0.,0.,0.,1.}}; 
		Matrix matrix=new Matrix(array);
		return new Transformation_3(matrix);
	}

	/**
	 * Return a translation by a vector v
	 */
	public static Transformation_3 translation(Vector_3 v) {
		throw new Error("to be completed: TD1");
	}

	/**
	 * Return a scaling by a factor s
	 */
	public static Transformation_3 scaling(double s) {
		throw new Error("to be completed: TD1");
	}

	/**
	 * Return a rotation of an angle theta, around X axis
	 */
	public static Transformation_3 rotationAxisX(double theta) {
		throw new Error("to be completed: TD1");
	}

	/**
	 * Return a rotation of an angle theta, around Y axis
	 */
	public static Transformation_3 rotationAxisY(double theta) {
		throw new Error("to be completed: TD1");
	}

	/**
	 * Return a rotation of an angle theta, around Z axis
	 */
	public static Transformation_3 rotationAxisZ(double theta) {
		throw new Error("to be completed: TD1");
	}

	/**
	 * Apply the transformation to point p (having homogeneous coordinates)
	 */
	public Point_d transform(Point_d p) {
		double x=approx(p.getCartesian(0));
		double y=approx(p.getCartesian(1));
		double z=approx(p.getCartesian(2));
		double w=approx(p.getCartesian(3));
		double[][] array = {{x}, {y}, {z}, {w}}; 
		Matrix v=new Matrix(array); // the vector
		
		Matrix result=this.m.times(v);
		double[] coord={result.get(0, 0), result.get(1, 0), result.get(2, 0), result.get(3, 0)};
		return new Point_d(coord);
	}

	/**
	 * Compose the current transformation with a transfomation 't': return a new transformation
	 */
	public Transformation_3 compose(Transformation_3 t) {
		Matrix M=t.m; 
		
		Matrix composition=this.m.times(M);
		return new Transformation_3(composition);
	}
	
	public static double approx(double d) {
		int rounded=(int)(d*precision);
		return rounded/precision;
	}
	
	public static void main(String[] args) {
	}

}
