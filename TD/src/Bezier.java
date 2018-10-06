import java.util.LinkedList;

public class Bezier extends Curve {

	Transformation_2 transformation;

	public Bezier(DrawCurve frame, LinkedList<Point_2> p) {
		super(frame, p);
		transformation=null;
	}

	public Bezier(DrawCurve frame, LinkedList<Point_2> p, Transformation_2 transformation) {
		super(frame, p);
		this.transformation=transformation;
	}
	
	public Bezier(DrawCurve frame, Point_2[] points, Transformation_2 transformation) {
		super(frame, points);
		this.transformation=transformation;
	}

	/**
	 * Draw the control polygon
	 */
	public void drawControlPolygon() {
		this.frame.stroke(0, 0, 255);
	    for(int i=1;i<this.points.length;i++) {
	    	drawSegment(this.points[i], this.points[i-1]);
	    	//drawSegment(transformation.transform(points[i]), transformation.transform(points[i-1]));
	    }
		this.frame.stroke(0, 0, 0);
	}

	/**
	 * Evaluate the curve for parameter t
	 * Return point (x(t), y(t))
	 */
	public Point_2 evaluate(double t) {
		return recursiveDeCasteljau(this.points.length-1, 0, t);
		//return iterativeDeCasteljau(t);
	}

	/**
	 * Perform the subdivision (once) of the Bezier curve (with parameter t)
	 * Return two Bezier curves (with n control points each)
	 */
	public Bezier[] subdivide(double t) {
		Point_2[] controlPolygon=this.points;
		int n=this.points.length-1; // degree and number of edges of the control polygon

		Point_2[] b0=new Point_2[n+1]; // first control polygon
		Point_2[] b1=new Point_2[n+1]; // second control polygon
		Bezier[] result=new Bezier[2]; // the pair of Bezier curves to return as result

		throw new Error("To be completed:  INF574");
	}

	/**
	 * Plot the curve (in the frame), for t=0..1, with step dt
	 */
	public void plotCurve(double dt) {
		this.drawControlPolygon();
		this.drawControlPoints();
		
		// to be completed TD INF574
	}

	/**
	 * Perform the rendering of the curve using subdivision approach
	 * Perform the subdivision n times
	 */
	public void subdivisionRendering(int n) {
		this.drawControlPolygon(); // draw original control polygon
		this.drawControlPoints(); // draw original control points
		LinkedList<Bezier> subCurves=new LinkedList<Bezier>();
		
		// to be completed TD INF574:
		if(this.points.length<3) return;

	}

	public Point_2 recursiveDeCasteljau(int r, int i, double t) {
		throw new Error("TD INF574: to be completed");
	}

	/**
	 * Perform the (iterative) De Casteljau algorithm to evaluate b(t)
	 */
	public Point_2 iterativeDeCasteljau(double t) {
		throw new Error("TD INF574: to be completed");
	}

}
