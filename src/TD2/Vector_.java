package TD2;

public interface Vector_ {
	
	  public double getCartesian(int i);  
	  public void setCartesian(int i, double x);
	      
	  public float[] toFloat();
	  public double[] toDouble();

	  //public boolean equals(TD2.Vector_ v);
	  
	  public Vector_ sum(Vector_ v);
	  public Vector_ difference(Vector_ v);
	  public Vector_ opposite();

	  public double innerProduct(Vector_ v);

	  public Vector_ divisionByScalar(double s);
	  public Vector_ multiplyByScalar(double s);
	  
	  public double squaredLength();
	   
	  public int dimension();
	  public String toString();
}
