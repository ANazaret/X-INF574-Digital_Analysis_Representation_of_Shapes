import processing.core.PApplet;

/**
 * A simple animation of 3D cubes
 *
 * @author Luca Castelli Aleardi (INF574, 2018)
 */
public class CubicLimit extends PApplet {

    Draw3DCube renderer;
    int iterations = 0;
    double alpha = 0., theta = 0., phi = 0.;
    double zoomFactor = 0.02;

    Transformation_3 transformation = Transformation_3.identity();

    public void setup() {
        size(800, 600, P3D);
        this.renderer = new Draw3DCube(this);

        ArcBall arcball = new ArcBall(this); // for interaction with mouse events and 3D rendering
    }

    public void draw() {
        this.iterations++;
        background(0);
        this.lights();
        // rotate and translate the camera
        translate(width / 2.f, height / 2.f, -1 * height / 2.f);
        this.rotateX((float) (PI / 3.0));
        //this.rotateY((float)(PI/3.0));
        this.rotateZ((float) (PI / 3.0));
        this.strokeWeight(1);
        stroke(150, 150, 150);


        // do not change the code below
        /** the skeleton of a 3D cube to transform and animate */
        //TD1.Cube cube = this.animatingCube(30);
        Cube cube=this.rotatingCube(80); // remove comment to test this function
        cube.transformVertices(this.transformation);
        this.renderer.draw(cube);

        //if(this.rubik!=null)
        //	  this.renderer.draw(this.rubik);
    }

    /**
     * A simple function animating a 3D cube (performing a scaling)
     */
    public Cube animatingCube(double size) {
        Cube cube = new Cube(size); // create a small (unit) cube
        if (this.iterations % 50 == 0)
            this.zoomFactor = this.zoomFactor * -1; // increase/decrease the scaling ratio every 50 iterations

        Transformation_3 scaling = Transformation_3.scaling(1 + zoomFactor);
        this.transformation = this.transformation.compose(scaling);
        cube.transformVertices(this.transformation);
        return cube;
    }

    public Cube rotatingCube(double size) {
        Cube cube = new Cube(size); // create a small (unit) cube
        Point_3 bar = new Point_3(); // barycenter of the cube
        Point_3 origin = new Point_3(0., 0., 0.); // origin

        Point_3[] points = new Point_3[cube.n];
        for (int i = 0; i < cube.n; i++) {
            points[i] = cube.getVertex(i).toPoint3D(); // store the vertices of the cube as TD1.TD2.Point_3 (cartesian coordinates)
        }

        bar.barycenter(points);
        Transformation_3 rot = Transformation_3.rotationAxisX(0.01);
        Vector_3 myVector = new Vector_3(origin, bar);
        this.transformation = this.transformation
                .compose(Transformation_3.translation(myVector))
                .compose(rot)
                .compose(Transformation_3.translation(myVector.opposite()));
        cube.transformVertices(this.transformation);
        return cube;
    }

    public void keyPressed() {
    }

    /**
     * For running the PApplet as Java application
     */
    public static void main(String args[]) {
        PApplet.main(new String[]{"CubicLimit"});
    }

}
