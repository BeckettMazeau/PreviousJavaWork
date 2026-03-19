//essential fundamental class
public class Vector {
    //initialize instance variables for storing properties of vector
    private double dx;
    private double dy;
    private double dz;

    //constructor for vector
    public Vector(double dx, double dy, double dz) {
        //store passed in values in instance variables
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    //Getters
    public double getDX() {
        return dx;
    }

    public double getDY() {
        return dy;
    }

    public double getDZ() {
        return dz;
    }

    //setters
    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setDz(double dz) {
        this.dz = dz;
    }

    //Scale value of vector by scalar by multiplying the derivative for all axis by
    //the scalar
    public Vector scale(double scalar) {
        double newDX = dx * scalar;
        double newDY = dy * scalar;
        double newDZ = dz * scalar;
        return new Vector(newDX, newDY, newDZ);
    }

    //get two vectors added by adding together the derivatives for all axis (isolated by axis)
    public Vector add(Vector v) {
        double newDX = dx + v.getDX();
        double newDY = dy + v.getDY();
        double newDZ = dz + v.getDZ();
        return new Vector(newDX, newDY, newDZ);
    }

    //get two vectors subtracted from eachother by subtracting the derivatives for all axis(isolated by axis)
    public Vector subtract(Vector v) {
        double newDX = dx - v.getDX();
        double newDY = dy - v.getDY();
        double newDZ = dz - v.getDZ();
        return new Vector(newDX, newDY, newDZ);
    }

    // get dot product by squaring derivatives for all axis and adding them together
    public double dot(Vector v) {
        return ((dx * v.getDX()) + (dy * v.getDY()) + (dz * v.getDZ()));
    }

    //get cross product
    public Vector cross(Vector v) {
        double newDX = (dy * v.getDZ()) - (dz * v.getDY());
        double newDY = (dz * v.getDX()) - (dx * v.getDZ());
        double newDZ = (dx * v.getDY()) - (dy * v.getDX());
        return new Vector(newDX, newDY, newDZ);
    }

    //get length of vector by taking square root of the dot product of the vector with itself
    public double length() {
        return Math.sqrt(dot(this));
    }

    //normalize vector by scaling vector by 1/its length
    public Vector normalize() {
        double length = this.length();
        return this.scale(1.0 / length);
    }

    //for testing
    public String stringValue() {
        return "[" + dx + "," + dy + "," + dz + "]";
    }
}
