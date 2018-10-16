import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import Jcg.geometry.Point_3;
import Jcg.polyhedron.Halfedge;
import Jcg.polyhedron.Polyhedron_3;
import Jcg.polyhedron.Vertex;

/**
 * @author Luca Castelli Aleardi (INF555, 2012)
 *
 */
public class EdgeContraction extends MeshSimplification {

	public EdgeContraction(Polyhedron_3<Point_3> polyhedron3d) {
		super(polyhedron3d);
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

        int size = 2;
        Point_3[] myPoints = new Point_3[size];
        myPoints[0] = halfedge.vertex.getPoint();
        myPoints[1] = halfedge.opposite.vertex.getPoint();

        Point_3 myBarycenter = new Point_3();
        myBarycenter.barycenter(myPoints);


        // Now we change the position of the vertex
        halfedge.vertex.setPoint(myBarycenter);

	}
	
	
	/**
	 * Check whether a given halfedge can be contracted
	 */
	boolean isLegal(Halfedge<Point_3> h){
		return true;
	}
		
}



