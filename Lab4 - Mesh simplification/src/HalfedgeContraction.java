import Jcg.geometry.*;
import Jcg.polyhedron.*;

import java.util.Random;

/**
 * @author Luca Castelli Aleardi (INF555, 2012)
 *
 */
public class HalfedgeContraction extends MeshSimplification {
	
	public HalfedgeContraction(Polyhedron_3<Point_3> polyhedron3D) {
		super(polyhedron3D);
	}
	
	/**
	 * Basic example of simplification based on edge contractions
	 * Simply select at random edges to be contracted
	 */
	public void simplify() {
		int halfedgeId;
		do {
            halfedgeId = (int) (Math.random() * polyhedron3D.sizeOfHalfedges());
        } while (!isLegal(polyhedron3D.halfedges.get(halfedgeId)));

		//halfedgeId = 0;
		Halfedge<Point_3> halfedge = polyhedron3D.halfedges.get(halfedgeId);
        edgeCollapse(halfedge);
    }
	
	
	/**
	 * Check whether a given halfedge can be contracted
     *
     * I guess it can be contracted iff it has an opposite halfedge
     * What about the face ??
	 */
	boolean isLegal(Halfedge<Point_3> h){
	    return h.opposite != null;
	}
	
}
