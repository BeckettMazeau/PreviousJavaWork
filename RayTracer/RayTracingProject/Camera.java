public class Camera {
    //initializing instance variables
    private Point location;
    private Vector forward;
    private Vector up;
    private Vector right;
    private double xFoV;
    private double yFoV;

    //Camera contstructor
    public Camera(Point location, Vector forward, Vector up, double fieldOfView, double aspectRatio) {
        this.location = location;
        //Normalizing vectors so that their length is = 1. This is for convenient use later, as because it's equal to 1, it is essentially just a direction.
        //This lets us scale the vector by a distance to get a vector of the same direction and of a desired magnitude/distance
        this.forward = forward.normalize();
        this.up = up.normalize();
        //Taking cross product of up and forward vectors to derive rightward vector
        right = forward.cross(up);
        /*conversions from fov in degrees into radians (better compatible with the math we're doing)
         * Using derived xfov in radians and the aspect ratio to find the fov in the y direction*/
        xFoV = Math.toRadians(fieldOfView);
        yFoV = Math.atan((Math.tan(xFoV)) / aspectRatio);
    }

    /*This method essentially creates the point which we'll use to cast out our viewing rays. Our image is a rectangular shot
    of our scene, however, we need to know the location of the rectangle, and the pixels within it. The points derived by this method can
    have another point, our camera point, subtracted from it, to derive where the camera needs to look to see that pixel. This is a somewhat rough
    explanation, as it is significantly easier to visualize and vocalize when looking at an image/diagram to explain.
     */
    public Point imagePlanePoint(double u, double v) {
        //formula spread out for readability
        //p = cameraPosition + forwardVec + rightVec * (2 * (u-0.5) * tan(xFoV) ) + upVec * (2 * (v-0.5) * tan(yFoV) )
        //rightVec * (2 * (u-0.5) * tan(xFoV) )
        Vector component1 = right.scale(2 * (u - 0.5) * Math.tan(xFoV));
        //upVec * (2 * (v-0.5) * tan(yFoV)
        Vector component2 = up.scale(2 * (v - 0.5) * Math.tan(yFoV));
        //forwardVec + component1 + component2
        Vector component3 = forward.add(component1);
        component3 = component3.add(component2);
        // cameraPosition + component3
        Point p = location.add(component3);
        return p;
    }

    //Essentially the later part of what was described above. Creating an image plane point at the desired location within our 'shot' and
    //deriving the vector from our current position pointing at it, then combining that vector and current location (camera) to create a ray.
    public Ray generateRay(double u, double v) {
        Point p = imagePlanePoint(u, v);
        Vector rayDirection = p.subtract(location);
        return new Ray(location, rayDirection);
    }

    //Move methods | Literally just shifting current location in different axis relative to stored vectors (to moveforward, add to location a vector of same direction as forward vector, etc.)
    public void moveForward() {
        location = location.add(forward.scale(0.3));
    }

    public void moveBackward() {
        location = location.add(forward.scale(-0.3));
    }

    public void moveRight() {
        location = location.add(right.scale(0.3));
    }

    public void moveLeft() {
        location = location.add(right.scale(-0.3));
    }

    public void moveUp() {
        location = location.add(up.scale(0.3));
    }

    public void moveDown() {
        location = location.add(up.scale(-0.3));
    }

    //Look methods | the look up and down ones are quite simple, just adding a vector of only some y value to the up vector and the forward vector
    //and then normalizing (because this is a directional vector)
    public void lookUp() {
        Vector moreUp = new Vector(0, 0.05, 0);
        up = up.add(moreUp).normalize();
        forward = forward.add(moreUp).normalize();
        right = forward.cross(up);
    }

    public void lookDown() {
        Vector moreDown = new Vector(0, -0.05, 0);
        up = up.add(moreDown).normalize();
        forward = forward.add(moreDown).normalize();
        right = forward.cross(up);
    }

    //these methods are a bit odder. They combine both tilt and turning.
    //firstly, the corrent dy values for both forward and up are stored, since dealing with these whilst turning would
    //massively complicate the calculations. Next, up dy is set to one, and forward dy to 0. These vectors are then normalized.
    //a vector with dx 0.05 and dz 0.05 is then added to the up and forward vectors, which are then again normalized. This is where the
    //tilt comes in. When the vector's previousd dy is added and is scaled down to remain normal, it changes to dy to something less than
    //it was previously, causing a tilt in the direction of "motion". This tilt can easily be corrected for by one or two taps of the
    //opposing arrow key (due to the addition being performed "weighing" less and less as we approach dz=1:
    //
    public void lookRight() {
        Vector moreRight;
        if ((forward.getDZ() >= -1 && forward.getDZ() < 1) && (forward.getDX() >= -1 && forward.getDX() < 1)) {
            moreRight = new Vector(0.05, 0, 0.05);
        } else {
            moreRight = new Vector(-0.05, 0, -0.05);
        }
        double tempUY = up.getDY();
        double tempFY = forward.getDY();
        up.setDy(1);
        forward.setDy(0);
        up.normalize();
        forward.normalize();
        up = up.add(moreRight).normalize();
        forward = forward.add(moreRight).normalize();
        right = forward.cross(up).normalize();
        up.setDy(tempUY);
        forward.setDy(tempFY);
        up.normalize();
        forward.normalize();
    }

    public void lookLeft() {

        Vector moreLeft;
        if ((forward.getDZ() >= -1 && forward.getDZ() < 1) && (forward.getDX() >= -1 && forward.getDX() < 1)) {
            moreLeft = new Vector(-0.05, 0, -0.05);
        } else {
            moreLeft = new Vector(0.05, 0, 0.05);
        }
        double tempUY = up.getDY();
        double tempFY = forward.getDY();
        up.setDy(1);
        forward.setDy(0);
        up.normalize();
        forward.normalize();
        up = up.add(moreLeft).normalize();
        forward = forward.add(moreLeft).normalize();
        right = forward.cross(up).normalize();
        up.setDy(tempUY);
        forward.setDy(tempFY);
        up.normalize();
        forward.normalize();
    }

    //Calculates and sets Fov based on a passed in fov value and aspect ratio
    public void setFoV(int FoV, double aspectRatio) {
        xFoV = Math.toRadians(FoV);
        yFoV = Math.atan((Math.tan(xFoV)) / aspectRatio);
    }

}
