package TD2;

public interface Point_ extends Comparable<Point_>{

	  public double getCartesian(int i);  
	  public void setCartesian(int i, double x);
	  public void setOrigin();
	  
	  public float[] toFloat();
	  public double[] toDouble();
	    
	  public void translateOf(Vector_ p);
	  public Vector_ minus(Point_ p);
	  //public void barycenter(TD2.Point_ [] points);
	  //public void linearCombination(TD2.Point_[] points, double[] coefficients);
	  
	  public int dimension();
	  public String toString();
	  
}


