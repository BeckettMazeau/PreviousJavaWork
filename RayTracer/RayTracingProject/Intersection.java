public class Intersection {
    //instance variables
    private Point position;
    private Vector normal;
    private double distance;
    private Material surfaceMaterial;
    //constructor
    public Intersection(Point position, Vector normal, double distance, Material material) {
        this.position = position;
        this.normal = normal;
        this.distance = distance;
        this.surfaceMaterial = material;
    }
    //getters
    public double getDistance() {
        return distance;
    }

    public Material getSurfaceMaterial() {
        return surfaceMaterial; 
    }

    public Point getPosition() {
        return position;
    }

    public Vector getNormal() {
        return normal;
    }
}
