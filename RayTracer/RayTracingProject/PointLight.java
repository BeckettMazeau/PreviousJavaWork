public class PointLight extends Light {
    //instance variables
    private Color intensity;
    private Point position;
    //construcotr
    public PointLight(Color c, Point location) {
        intensity = c;
        position = location;
    }
    //get light direction by subtracting location of point of surface of an object from current position, see explanation from point below
    //Getting vector from subtracting two points (getting vector magnitude and direction by looking at difference in location between two points i.e. how far do i have to move and in what direction from where I am now to reach that other point?)
    public Vector computeLightDirection(Point surfacePoint) {
        Vector newVec = position.subtract(surfacePoint);
        newVec = newVec.normalize();
        return newVec;
    }
    //getting light distance by.. well.. just look up two lines
    public double computeLightDistance(Point surfacePoint) {
        Vector newVec = position.subtract(surfacePoint);
        return newVec.length();
    }
    //getters
    public Color computeLightColor(Point surfacePoint) {
        return intensity;
    }

    public Color getLightColor() {
        return intensity;
    }
    public Point getPosition() {
        return position;
    }



}
