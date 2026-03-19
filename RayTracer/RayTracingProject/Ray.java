public class Ray {
    //instance variables
    private Point position;
    private Vector direction;
    //constructor
    public Ray(Point position, Vector direction) {
        this.position = position;
        this.direction = direction.normalize();
    }
    //getter
    public Point getPosition() {
        return position;
    }

    public Vector getDirection() {
        return direction;
    }
    //Move current point by dist  magnitude in vector direction (the directional vector that is a part of the ray)
    public Point evaluate(double dist) {
        Vector newV = direction.scale(dist);
        return position.add(newV);
    }

    public String stringValue() {
        return "{" + position.stringValue() + "," + direction.stringValue() + "}";
    }
}