
/**
 * A class for representing Points in 2D (with floating points coordinates)
 *
 * @author Luca Castelli Aleardi (INF555, 2012)
 */
public class Point_2 {
  public double x,y;

  public Point_2() {}
  
  public Point_2(double x, double y) { 
  	this.x=x; 
  	this.y=y;
  }

  public Point_2(double[] coordinates) { 
	  	this.x=coordinates[0]; 
	  	this.y=coordinates[1];
  }

  /**
   * Construct a new point (copying point p)
   */
  public Point_2(Point_2 p) { 
  	this.x=p.getCartesian(0); 
  	this.y=p.getCartesian(1); 
  }
  
  public void barycenter(Point_2[] points) {
  	double x_=0., y_=0.;
  	for(int i=0;i<points.length;i++) {
  		x_=x_+points[i].getCartesian(0);
  		y_=y_+points[i].getCartesian(1);
  	}
  	this.x = x_/points.length;
  	this.y = y_/points.length;
  }
  
  /**
   * Return a linear combination of a set of points.
   * Coefficients must sum to 1
   */
  public static Point_2 linearCombination(Point_2 [] points, double[] coefficients) {
	  	double x_=0., y_=0.;
	  	for(int i=0;i<points.length;i++) {
	  		x_=x_+(points[i].getX()*coefficients[i]);
	  		y_=y_+(points[i].getY()*coefficients[i]);
	  	}
	  	return new Point_2(x_,y_);
	  }

  /**
   * Return the x coordinate of the point
   */
  public double getX() {return x; }

  /**
   * Return the y coordinate of the point
   */
  public double getY() {return y; }
  
  public float[] toFloat() {
	  float[] result=new float[2];
	  result[0]=(float)x;
	  result[1]=(float)y;
	  return result;
  }
  
  public double[] toDouble() {
	  double[] result=new double[2];
	  result[0]=x;
	  result[1]=y;
	  return result;
  }
  
  public Point_2 toCartesian() {
	  throw new Error("method not defined: to be completed");
  }

  /**
   * Return the point in homogeneous coordinates (point in 3D)
   */
  public Point_2 toHomogeneous() {
	  double[] hCoordinates=new double[3];
	  hCoordinates[0]=x;
	  hCoordinates[1]=y;
	  hCoordinates[2]=1.;
	  return new Point_3(hCoordinates);
  }

  /**
   * Set the x coordinate of the point
   */
  public void setX(double x) {this.x=x; }

  /**
   * Set the x coordinate of the point
   */
  public void setY(double y) {this.y=y; }
    
  public void translateOf(Vector_2 v) {
    this.x=x+v.getCartesian(0);
    this.y=y+v.getCartesian(1);
  }

  /**
   * Check whether two points have same coordinates
   */
  public boolean equals(Object o) {
	  if (o instanceof Point_2) {
		  Point_2 p = (Point_2) o;
		  return this.x==p.getCartesian(0) && this.y==p.getCartesian(1); 
	  }
	throw new RuntimeException ("Comparing TD1.TD2.Point_2 with object of type " + o.getClass());
  }

  /**
   * Return the Euclidean distance of two points
   */
  public double distanceFrom(Point_2 p) {
    double dX=p.getX()-x;
    double dY=p.getY()-y;
    return Math.sqrt(dX*dX+dY*dY);
  }
  
  public double squareDistance(Point_2 p) {
    double dX=p.getX()-x;
    double dY=p.getY()-y;
    return dX*dX+dY*dY;
  }

  public String toString() {return "("+x+","+y+")"; }
  public int dimension() { return 2;}
  
  public double getCartesian(int i) {
  	if(i==0) return x;
  	return y;
  } 
  public void setCartesian(int i, double x) {
  	if(i==0) this.x=x;
  	else this.y=x;
  }

  public void setOrigin() {
	  	this.x=0.;
	  	this.y=0.;
	  }
    
  public Vector_2 minus(Point_2 b){
  	return new Vector_2(b.getCartesian(0)-x, 
  						b.getCartesian(1)-y);
  }
  
  public Point_2 sum(Vector_2 v) {
	  	return new Point_2(this.x+v.getCartesian(0),
	  					   this.y+v.getCartesian(1)); 
  }

  /**
   * Compare coordinates according to lexicographic order
   */
  public int compareTo(Point_2 o) {
	  Point_2 p = (Point_2) o;
	  if(this.x<p.getX())
		  return -1;
	  if(this.x>p.getX())
		  return 1;
	  if(this.y<p.getY())
		  return -1;
	  if(this.y>p.getY())
		  return 1;
	  return 0;
  }

}




