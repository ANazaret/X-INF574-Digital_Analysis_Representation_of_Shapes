import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import Jama.Matrix;
import Jcg.geometry.Point_3;
import Jcg.polyhedron.Face;
import Jcg.polyhedron.Halfedge;
import Jcg.polyhedron.Polyhedron_3;
import Jcg.polyhedron.Vertex;

/**
 *
 */
public class QuadricErrorMetrics extends MeshSimplification{

	public QuadricErrorMetrics(Polyhedron_3<Point_3> polyhedron3d) {
		super(polyhedron3d);
	}

	/**
	 * Basic example of simplification based on edge contractions
	 * Simply select at random edges to be contracted
	 */
	public void simplify() {
		System.out.println("To be completed");		
	}
	
	
	/**
	 * Check whether a given halfedge can be contracted
	 */
	boolean isLegal(Halfedge<Point_3> h){
		throw new Error("To be completed");
	}

	@Override
	protected Point_3 getNewPoint(Halfedge<Point_3> h) {
		return null;
	}

}


