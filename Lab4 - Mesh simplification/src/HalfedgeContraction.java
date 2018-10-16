import Jcg.geometry.*;
import Jcg.polyhedron.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
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
        int maxi = polyhedron3D.sizeOfHalfedges() * 10;
        do {
            maxi --;
            halfedgeId = (int) (Math.random() * polyhedron3D.sizeOfHalfedges());
        } while (!isLegal(polyhedron3D.halfedges.get(halfedgeId)) && maxi > 0);

        //halfedgeId = 0;
        Halfedge<Point_3> halfedge = polyhedron3D.halfedges.get(halfedgeId);
        edgeCollapse(halfedge);
    }

    @Override
    protected Point_3 getNewPoint(Halfedge<Point_3> h) {
        return h.opposite.vertex.getPoint();
    }


}
