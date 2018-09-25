package TD2;

/**
 * Simple scheme implementing linear interpolation
 * 
 * @author Luca Castelli Aleardi (INF555, 2012)
 *
 */
public class LinearInterpolation extends Interpolation {
	
	public LinearInterpolation(Draw frame) {
		super(frame);
	}
	
	public void interpolate() {
	    for (int i=1; i<points.length; i++){
	        drawSegment(points[i-1], points[i]);
        }
        for (Point_2 p : points) {
            drawPoint(p);
        }

	}
	
}
