package TD2;

import java.util.LinkedList;
import java.util.Random;

public class HermiteSplineInterpolation extends Interpolation{
	
	LinkedList<Vector_2> slopeList;

	public HermiteSplineInterpolation(Draw frame) {
		super(frame);
		this.slopeList = new LinkedList<Vector_2>();
		slopeList.add(new Vector_2(100,100));
		slopeList.add(new Vector_2(-100,0));
		slopeList.add(new Vector_2(0,100));
		slopeList.add(new Vector_2(-100,100));
		slopeList.add(new Vector_2(300,100));
	}
	
	public Vector_2[] linkedListArray() {
		Vector_2[] slopeListArray=new Vector_2[slopeList.size()];
		int i=0;
		for(Vector_2 v: slopeList) {
			slopeListArray[i]=v;
			i++;
		}		
		return slopeListArray;
	}

	@Override
	public void interpolate() {
		int pointsSize = this.points.length;
		int slopesSize = this.slopeList.size();
		
		for(int i=0;i<pointsSize;i++)
	    	drawPoint(this.points[i]);
		
		for(int i=0; i<(pointsSize-slopesSize); i++){
			Random randomGenerator = new Random();
			slopeList.add(new Vector_2(100*randomGenerator.nextInt(10)-500,100*randomGenerator.nextInt(10)-500));
		}
		
		
		plotHermite(points, this.linkedListArray());
		
	}
	
	
	public void plotHermite(Point_2[] p, Vector_2[] sL){
		//throw new Error("To be completed: TD1");
	}
	
	public Vector_2 getSlope(int pointIndex){
		return linkedListArray()[pointIndex];
	}
	
	void plotPolynomialCurve(double[] coeffX, double[] coeffY, int n) {
		Point_2[] plotPoints = new Point_2[n+1];
		double space = (double)1./n;
		
		for(int i=0;i<=n;i++){
			double t = (double)i*space;
			double xValue = 0;
			for(int l=0;l<coeffX.length;l++)
				xValue+=coeffX[l]*Math.pow(t,l);

			double yValue = 0;
			for(int l=0;l<coeffY.length;l++)
				yValue+=coeffY[l]*Math.pow(t,l);
			
			plotPoints[i]=new Point_2(xValue,yValue);
		}
			
		for(int i=0;i<n;i++)
			drawSegment(plotPoints[i], plotPoints[i+1]);
	}
	
	
	
}
