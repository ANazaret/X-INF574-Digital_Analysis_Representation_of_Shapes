package TD1;

import processing.core.*;


/**
 * A simple 3d viewer for visualizing cubes in 3D space
 *
 * @author Luca Castelli Aleardi (INF574, 2018)
 */
public class Rotating3DCube extends PApplet {

    Draw3DCube renderer;

    /**
     * the skeleton of a 3D cube to transform and animate
     */
    Cube cube;

    Cube[] rubik = null; // a set of 27 cubes, defining a Rubik's cube


    public void setup() {
        size(800, 600, P3D);
        this.renderer = new Draw3DCube(this);
        this.cube = new Cube(20);
    }

    public void draw() {
        background(0);
        this.lights();

        // rotate and translate the camera
        translate(width / 2.f, height / 2.f, -1 * height / 2.f);
        this.rotateX((float) (PI / 3.0));
        //this.rotateY((float)(PI/3.0));
        this.rotateZ((float) (PI / 3.0));
        this.strokeWeight(1);
        stroke(150, 150, 150);

        this.renderer.draw(this.cube);

        if (this.rubik != null)
            this.renderer.draw(this.rubik);
    }

    public void keyPressed() {
        switch (key) {
            case ('i'):
            case ('I'):
                this.scale(2.0);
                break;
            case ('o'):
            case ('O'):
                this.scale(0.5);
                break;
            case ('x'):
            case ('X'):
                this.rotateX(PI / 6.0);
                break;
            case ('y'):
            case ('Y'):
                this.rotateY(PI / 6.0);
                break;
            case ('z'):
            case ('Z'):
                this.rotateZ(PI / 6.0);
                break;
            case ('r'):
            case ('R'):
                this.rotateAroundDiagonal(PI / 20.0);
                break;
            case ('u'):
            case ('U'):
                this.moveZ(2.0);
                break;
            case ('d'):
            case ('D'):
                this.moveZ(-2.0);
                break;
        }
    }

    /**
     * Zoom in
     */
    public void scale(double zoomFactor) {
        Transformation_3 scaling = Transformation_3.scaling(zoomFactor);
        this.cube.transformVertices(scaling);
        System.out.println("Scaling by a factor " + zoomFactor);
    }

    /**
     * Move up (vertically)
     */
    public void moveZ(double t) {

        Transformation_3 myZMove = Transformation_3.translation(new Vector_3(0., 0., t));
        this.cube.transformVertices(myZMove);
        System.out.println("Moving along z-axis by a " + t);
    }

    /**
     * rotate around X-axis
     */
    public void rotateX(double angle) {
        Transformation_3 myRotation = Transformation_3.rotationAxisX(angle);
        Point_d myBarycenter = new Point_d(3);
        myBarycenter.barycenter(this.cube.vertices);
        Transformation_3 myTranslation = Transformation_3.translation(new Vector_3(myBarycenter.coordinates));
        Transformation_3 myInverseTranslation = Transformation_3.translation(new Vector_3(myBarycenter.coordinates).opposite());
        this.cube.transformVertices(myTranslation.compose(myRotation).compose(myInverseTranslation));
        System.out.println("Rotate around x-axis of angle " + angle);
    }

    /**
     * rotate around Y-axis
     */
    public void rotateY(double angle) {
        Transformation_3 myRotation = Transformation_3.rotationAxisY(angle);
        Point_d myBarycenter = new Point_d(3);
        myBarycenter.barycenter(this.cube.vertices);
        Transformation_3 myTranslation = Transformation_3.translation(new Vector_3(myBarycenter.coordinates));
        Transformation_3 myInverseTranslation = Transformation_3.translation(new Vector_3(myBarycenter.coordinates).opposite());
        this.cube.transformVertices(myTranslation.compose(myRotation).compose(myInverseTranslation));
        System.out.println("Rotate around y-axis of angle " + angle);
    }

    /**
     * rotate around Z-axis
     */
    public void rotateZ(double angle) {
        Transformation_3 myRotation = Transformation_3.rotationAxisZ(angle);
        Point_d myBarycenter = new Point_d(3);
        myBarycenter.barycenter(this.cube.vertices);
        Transformation_3 myTranslation = Transformation_3.translation(new Vector_3(myBarycenter.coordinates));
        Transformation_3 myInverseTranslation = Transformation_3.translation(new Vector_3(myBarycenter.coordinates).opposite());
        this.cube.transformVertices(myTranslation.compose(myRotation).compose(myInverseTranslation));
        System.out.println("Rotate around z-axis of angle " + angle);
    }


    /**
     * rotate the cube around its diagonal
     */
    public void rotateAroundDiagonal(double angle) {
        Vector_3 vector_3 = new Vector_3(this.cube.vertices[7].toPoint3D(), this.cube.vertices[1].toPoint3D());
        vector_3 = vector_3.divisionByScalar(
                Math.sqrt(vector_3.squaredLength()));

        Point_d myBarycenter = new Point_d(3);
        myBarycenter.barycenter(this.cube.vertices);
        Transformation_3 myTranslation = Transformation_3.translation(new Vector_3(myBarycenter.coordinates));
        Transformation_3 myInverseTranslation = Transformation_3.translation(new Vector_3(myBarycenter.coordinates).opposite());

        this.cube.transformVertices(
                myTranslation.compose(Transformation_3.rotationRodrigues(angle, vector_3)).compose(myInverseTranslation));


    }

    /**
     * For running the PApplet as Java application
     */
    public static void main(String args[]) {
        PApplet.main(new String[]{"TD1.Rotating3DCube"});
    }

}
