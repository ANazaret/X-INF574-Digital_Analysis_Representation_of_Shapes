import Jcg.geometry.Point_3;
import Jcg.polyhedron.Halfedge;
import Jcg.polyhedron.Polyhedron_3;
import Jcg.polyhedron.Vertex;

import java.util.*;

/**
 *
 */
public class EdgeContraction extends MeshSimplification {

    private final PriorityQueue<Halfedge<Point_3>> thePriorityQueue;
    private final Map<Halfedge<Point_3>, Double> theLengths;

    EdgeContraction(Polyhedron_3<Point_3> polyhedron3d) {
        super(polyhedron3d);
        theLengths = new HashMap<>();


        thePriorityQueue = new PriorityQueue<>(Comparator.comparingDouble(theLengths::get));
        polyhedron3D.halfedges.forEach(myHalfEdge -> {
            double myLength = myHalfEdge.vertex.getPoint().distanceFrom(
                    myHalfEdge.opposite.vertex.getPoint()
            ).doubleValue();
            theLengths.put(myHalfEdge, myLength);
            thePriorityQueue.add(myHalfEdge);
        });
    }

    /**
     * Basic example of simplification based on edge contractions
     * Simply select at random edges to be contracted
     */
    public void simplify() {

        Halfedge<Point_3> halfedge = null;

        Set<Halfedge<Point_3>> tmp = new HashSet<>();

        while (!thePriorityQueue.isEmpty()) {
            Halfedge<Point_3> he = thePriorityQueue.poll();
            if (isLegal(he)) {
                halfedge = he;
                thePriorityQueue.addAll(tmp);
                break;
            } else {
                tmp.add(he);
            }
        }
        if (halfedge == null) {
            return;
        }

        edgeCollapse(halfedge);

        // Remove the halfedges
        thePriorityQueue.remove(halfedge.next);
        thePriorityQueue.remove(halfedge.prev);
        thePriorityQueue.remove(halfedge.opposite.next);
        thePriorityQueue.remove(halfedge.opposite.prev);
        thePriorityQueue.remove(halfedge.opposite);

        // Update all incidents
        Vertex<Point_3> z = halfedge.opposite.vertex;
        Halfedge<Point_3> triangleToZ = z.getHalfedge();
        do {
            // Update
            theLengths.replace(triangleToZ, triangleToZ.vertex.getPoint().distanceFrom(
                    triangleToZ.opposite.vertex.getPoint()
            ).doubleValue());
            thePriorityQueue.remove(triangleToZ);
            thePriorityQueue.add(triangleToZ);

            triangleToZ = triangleToZ.next.opposite;
        } while ((triangleToZ != z.getHalfedge()));
    }


	/*

	// Barycenter with only u and v
	@Override
	protected Point_3 getNewPoint(Halfedge<Point_3> h) {
		Point_3[] myPoints = new Point_3[2];
		myPoints[0] = h.vertex.getPoint();
		myPoints[1] = h.opposite.vertex.getPoint();
		Point_3 myBarycenter = new Point_3();
		myBarycenter.barycenter(myPoints);
		return myBarycenter;
	}*/


    @Override
    protected Point_3 getNewPoint(Halfedge<Point_3> h) {
        Set<Vertex> points = new HashSet<>();
        points.add(h.vertex);
        points.add(h.opposite.vertex);

        Halfedge<Point_3> triangleToV = h.next.opposite;
        while (triangleToV != h) {
            points.add(triangleToV.opposite.vertex);
            triangleToV = triangleToV.next.opposite;
        }
        triangleToV = h.opposite.next.opposite;
        while (triangleToV != h.opposite) {
            points.add(triangleToV.opposite.vertex);
            triangleToV = triangleToV.next.opposite;
        }
        Point_3[] myPoints = points.stream().map(Vertex::getPoint).toArray(Point_3[]::new);
        Point_3 myBarycenter = new Point_3();
        myBarycenter.barycenter(myPoints);
        return myBarycenter;
    }


}
