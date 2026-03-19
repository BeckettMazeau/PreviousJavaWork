public class Point {
    //instance variables storing location
    private double x;
    private double y;
    private double z;
    //constructor
    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    //getters and setters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
    //adding vector to a point (moving point by vector magnitude in vector direction)
    public Point add(Vector v) {
        double newX = x + v.getDX();
        double newY = y + v.getDY();
        double newZ = z + v.getDZ();
        return new Point(newX, newY, newZ);
    }
    //Getting vector from subtracting two points (getting vector magnitude and direction by looking at difference in location between two points i.e. how far do i have to move and in what direction from where I am now to reach that other point?)
    public Vector subtract(Point p) {
        double newDX = x - p.getX();
        double newDY = y - p.getY();
        double newDZ = z - p.getZ();
        return new Vector(newDX, newDY, newDZ);
    }

    public String stringValue() {
        return "(" + x + "," + y + "," + z + ")";
    }
}