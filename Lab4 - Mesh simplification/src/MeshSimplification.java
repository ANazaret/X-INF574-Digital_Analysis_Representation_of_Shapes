import Jcg.geometry.Point_3;
import Jcg.geometry.Vector_3;
import Jcg.polyhedron.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract class defining methods for performing mesh simplification (via incremental decimation)
 *
 */
public abstract class MeshSimplification {

	public Polyhedron_3<Point_3> polyhedron3D;
	
	public MeshSimplification(Polyhedron_3<Point_3> polyhedron3D) {
		this.polyhedron3D=polyhedron3D;
	}

	/**
	 * The main method performing the simplification process
	 * To be implemented
	 */
	public abstract void simplify();

	/**
	 * Perform the collapse of edge e=(u,v)
	 * Edge e is shared by triangles (u,v,w) and (v,u,z)
	 */
	public void edgeCollapse(Halfedge<Point_3> e) {
		System.out.print("Performing edge collapse...");
		if(this.polyhedron3D==null || e==null)
			return;

        Point_3 newPoint = getNewPoint(e);

        // retrieve the cells incident to edge e
		Face<Point_3> f1=e.getFace();
		Face<Point_3> f2=e.getOpposite().getFace();
		Vertex<Point_3> u=e.getOpposite().getVertex();
		Vertex<Point_3> v=e.getVertex();
		Vertex<Point_3> w=e.getNext().getVertex();
		Vertex<Point_3> z=e.getOpposite().getNext().getVertex();
		Halfedge<Point_3> eUW=e.prev.opposite;
		Halfedge<Point_3> eWV=e.next.opposite;
		Halfedge<Point_3> eZU=e.opposite.next.opposite;
		Halfedge<Point_3> eVZ=e.opposite.prev.opposite;
		
		// update references between neighboring cells
		eUW.opposite=eWV;
		eWV.opposite=eUW;
		eZU.opposite=eVZ;
		eVZ.opposite=eZU;
		u.setEdge(eZU);
		w.setEdge(eUW);
		z.setEdge(eVZ);
		
		Halfedge<Point_3> pEdge=eWV;
		while(pEdge!=eVZ.getOpposite()) {
			pEdge.setVertex(u);
			pEdge=pEdge.getNext().getOpposite();
		}
		

		u.setPoint(newPoint);
		
		// remove old cells: 2 faces, 1 vertex, 6 halfedges
		this.polyhedron3D.vertices.remove(v);
		this.polyhedron3D.facets.remove(f1);
		this.polyhedron3D.facets.remove(f2);
		this.polyhedron3D.halfedges.remove(e);
		this.polyhedron3D.halfedges.remove(e.next);
		this.polyhedron3D.halfedges.remove(e.prev);
		this.polyhedron3D.halfedges.remove(e.opposite);
		this.polyhedron3D.halfedges.remove(e.opposite.next);
		this.polyhedron3D.halfedges.remove(e.opposite.prev);
		System.out.println("done");
	}


	/**
	 * Check whether a given halfedge (u -> v) can be contracted
	 *
	 * See : https://stackoverflow.com/questions/27049163/mesh-simplification-edge-collapse-conditions
	 */
	boolean isLegal(Halfedge<Point_3> h) {
		// We need to check that triangle connected to v having are not flipped after the contraction
		Point_3 u = getNewPoint(h);
		Point_3 v = h.vertex.getPoint();


		Halfedge<Point_3> triangleToV = h.next.opposite;
		while (triangleToV != h) {
			Vector_3 crossProduct = ((Vector_3) v.minus(triangleToV.prev.vertex.getPoint())).crossProduct(
					triangleToV.next.vertex
							.getPoint()
							.minus(triangleToV.prev.vertex.getPoint())
			);
			Vector_3 crossProduct2 = ((Vector_3) u.minus(triangleToV.prev.vertex.getPoint())).crossProduct(
					triangleToV.next.vertex
							.getPoint()
							.minus(triangleToV.prev.vertex.getPoint())
			);

			if (crossProduct.innerProduct(crossProduct2).floatValue() < 0f){
				System.out.println("Illegal edge : Flip");
				return false;
			}

			triangleToV = triangleToV.next.opposite;

		}

		// We also need to check that we do not turn two triangles into only one
		Set<Integer> neighborsU = new HashSet<>();
		Set<Integer> neighborsV = new HashSet<>();

		triangleToV = h.next.opposite;
		while (triangleToV != h) {
			neighborsV.add(triangleToV.opposite.vertex.index);
			triangleToV = triangleToV.next.opposite;
		}

		Halfedge<Point_3> triangleToU = h.opposite.next.opposite;
		while (triangleToU != h.opposite) {
			neighborsU.add(triangleToU.opposite.vertex.index);
			triangleToU = triangleToU.next.opposite;
		}

		int commons = 0;
		for(int x : neighborsU){
			if (neighborsV.contains(x)){
				commons++;
			}
		}

		if (commons > 2){
			System.out.println("Illegal edge : Two many common neighbors");
			return false;
		}


		return true;
	}

	protected abstract Point_3 getNewPoint(Halfedge<Point_3> h);

}
