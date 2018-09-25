package TD2;

/**
 * Cubic spline interpolation
 * 
 * @author Luca Castelli Aleardi (INF555, 2014)
 *
 */
public class CubicSplineInterpolation extends Interpolation {
	
	//throw new Error("To be completed: TD1");	

	public CubicSplineInterpolation(Draw frame) {
		super(frame);
	}
	
	public void computeCoefficients(Point_2[] P) {
		//throw new Error("To be completed: TD1");
	}
	
	/**
	 * Evaluate polynomial a[0]+a[1]x+a[2]x^2+...+a[n]x^n, at point x
	 */
	public static double evaluate(double[] a, double x) {
		if(a==null || a.length==0) throw new Error("polynomial not defined");
		double result=a[0];
		double p=1.;
		for(int i=1;i<a.length;i++) {
			p=p*x;
			//System.out.println(""+a[i]+"*"+p+"="+(a[i]*p));
			result=result+(a[i]*p);
		}
		return result;
	}

	public void interpolate() {
		//throw new Error("To be completed: TD1");

		for(Point_2 q:this.points) {
	    	drawPoint(q); // draw input points
	    }
	}
	
	public void plotPolynomial(double[] a, double min, double max, int n) {
		double dx=(max-min)/n;
		
		double x=min;
		for(int i=0;i<n;i++) {
			Point_2 p=new Point_2(x, evaluate(a, x));
			Point_2 q=new Point_2(x+dx, evaluate(a, x+dx));
			this.drawSegment(p, q);
			x=x+dx;
		}
		
	}

}
