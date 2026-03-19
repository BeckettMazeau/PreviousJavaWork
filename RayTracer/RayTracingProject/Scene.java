import javax.swing.*;
import java.util.ArrayList;
//Core class for the rendering and creation of images.
public class Scene {
    //instance variables, arraylists used to store objects in scene, camera used to create images
    private ArrayList<Surface> surfaces;
    private Camera camera;
    private ArrayList<Light> lights;
    //constructor only takes in camera and initializes arraylists
    public Scene(Camera newCam) {
        surfaces = new ArrayList<Surface>();
        lights = new ArrayList<Light>();
        camera = newCam;
    }


    //Finds color of closest intersection of a surface with passed in ray, based on
    //color of closest surface, reflectivity of closest surface, and illumination sources
    //and their effect on the color of that given surface. Returns black if no intersection
    //found
    public Color computeVisibleColor(Ray r, int bouncesLeft) throws Exception {
        //Creating variable to store our closest intersection
        Intersection closestIntersection = null;
        //running through all surfaces
        for (Surface s : surfaces) {
            //grabbing intersection for ray r and current object
            Intersection intersection = s.intersect(r);
            //update closest intersection if current intersection (if there is one) is closer
            if (intersection != null) {
                if (closestIntersection == null) {
                    closestIntersection = intersection;
                }
                if (intersection.getDistance() < closestIntersection.getDistance()) {
                    closestIntersection = intersection;
                }
            }
        }
        if (closestIntersection == null) {
            return new Color(0.09,0.09,0.09);
            //return new Color((135.0 / 255), (206.0 / 255), (235.0 / 255));
        }
        //Retrieves position and material of intersection for use in light loop,
        // placed before to minimize repeated operations.
        Point pos = closestIntersection.getPosition();
        Material mat = closestIntersection.getSurfaceMaterial();
        //default black color to be altered in light loop
        Color aColor = new Color(0, 0, 0);
        for (Light aLight : lights) {
            //checks if current intersection is illuminated by current light (isShadowed)
            //if shadowed, light does not affect color
            if (isShadowed(pos, aLight)) {
                continue;
            }
            //use computelighting method of intersections material to find the effect
            //by current light on surface's color. color modified to include said effect.
            if ((closestIntersection.getSurfaceMaterial().getType().equals("PerfectMirror"))) {
                continue;
            }
            Color clr = mat.computeLighting(closestIntersection, r, aLight);
            aColor = aColor.tint(clr);
        }
        //base case for reflection code recursion
        if (bouncesLeft <= 0 || mat.getReflectiveness() == 0) {
            return aColor;
        }
        //grabbing the normal vector for closest intersection, and opposite of current viewing vector
        //for use in finding the direction we need to look to find the 'mirror' color for
        //current point (current point referring to the point of impact/intersection with the surface)
        Vector normVec = closestIntersection.getNormal();
        //reversing vector to get, well, inverse
        Vector viewPrimeVec = r.getDirection().scale(-1);
        //calculating mirror vector using quantum hippopotamus's (nah we're taking the cosine of the angle between the normal vector of the surface intersection and the inverse viewing vector, multiplying that number by two, scaling our normal vector by that, and then subtracting our inverse viewing vector from that new vector
        Vector mirrorVec = (normVec.scale((2 * normVec.dot(viewPrimeVec)))).subtract(viewPrimeVec);
        //creating a mirror ray using the intersection point and the newly calculated vector
        Ray mirrorRay = new Ray(pos, mirrorVec);
        //checking if the material at intersection is a perfect mirror
        if ((closestIntersection.getSurfaceMaterial().getType().equals("PerfectMirror"))) {
            Color reflColor = (computeVisibleColor(mirrorRay, bouncesLeft - 1));
            return reflColor;

        } else {
            //if we're not a perfect mirror, we tint our color by the mirror color (scaled by the reflectiveness). If we are a perfect mirror, then dur we're whatever the refection returns.
            Color reflColor = (computeVisibleColor(mirrorRay, bouncesLeft - 1)).scale(mat.getReflectiveness());
            return aColor.tint(reflColor);
        }
    }

    //check if point is shadowed relative to light li
    public boolean isShadowed(Point p, Light li) throws Exception {
        //get direction from light to point
        Vector temp1 = li.computeLightDirection(p);
        //make it a ray
        Ray shadowRay = new Ray(p, temp1);
        Intersection possibleIntersection = null;
        //check if this ray intersects with anything else, if it does, and that object is closer to the light than we are, we're shadowed, if not, well we're not shadowed then.
        for (Surface surface : surfaces) {
            possibleIntersection = surface.intersect(shadowRay);
            if (possibleIntersection != null) {
                break;
            }
        }
        if (possibleIntersection == null) {
            return false;
        }
        double pDistanceToLight = li.computeLightDistance(p);
        double iDistanceToLight = possibleIntersection.getDistance();
        if (iDistanceToLight < pDistanceToLight) {
            return true;
        }
        return false;
    }

    /*====RENDERING====*/

    public ColorImage renderSingleThread(int xRes, int yRes, boolean aliasing, int numSamples) throws Exception {
        //base color is black
        Color black = new Color(0, 0, 0);
        //get aliasing resolution
        int aliasingRes = (int) Math.sqrt(numSamples);

        //create new blank image
        ColorImage sceneImage = new ColorImage(xRes, yRes);
        //are we aliasing?
        if (aliasing) {
            //traverse image
            for (int x = 0; x < sceneImage.getWidth(); x++) {
                for (int y = 0; y < sceneImage.getHeight(); y++) {
                    //set color as black
                    Color aColor = black;

                    //traverse smaller inner cube of aliasingres x aliasingres within pixel (how we're aliasing
                    for (int i = 0; i < aliasingRes; i++) {
                        for (int j = 0; j < aliasingRes; j++) {
                            //subdivide u and v using aliasing res to get portion of pixel to fire ray at
                            double u = ((x + ((i + 0.5) / aliasingRes)) / sceneImage.getWidth());
                            double v = ((y + ((j + 0.5) / aliasingRes)) / sceneImage.getHeight());
                            //get the ray
                            Ray currentRay = camera.generateRay(u, v);
                            //fire the laser
                            aColor = aColor.add(computeVisibleColor(currentRay, 5));
                        }
                    }
                    //get the average color for that pixel and set it
                    aColor = aColor.scale(1.0 / (Math.pow(aliasingRes, 2)));
                    sceneImage.setColor(x, y, aColor);


                }
            }
        } else {
            //if not aliasing, basically same thing except no subdividing pixels and no averaging color for that pixel
            for (int x = 0; x < sceneImage.getWidth(); x++) {

                for (int y = 0; y < sceneImage.getHeight(); y++) {
                    Color aColor = black;
                    double u = (x + 0.5) / xRes;
                    double v = (y + 0.5) / yRes;
                    Ray currentRay = camera.generateRay(u, v);
                    sceneImage.setColor(x, y, computeVisibleColor(currentRay, 5));
                }
            }
        }
        return sceneImage;
    }
    //SEE ABOVE FOR HOW RENDERING WAS DONE, THIS IS JUST DIVIDING UP TASKS
    public ColorImage renderMultiThread(int xRes, int yRes, boolean aliasing, int numSamples) throws Exception {

        Color black = new Color(0, 0, 0);
        int aliasingRes = (int) Math.sqrt(numSamples);
        //ADD CODE TO CHANGE ALIASING METHODS AND SUCH, MAKE EFFICIENT AND LEGIBLE
        ColorImage sceneImage = new ColorImage(xRes, yRes);
        //check available threads
        int threads = Runtime.getRuntime().availableProcessors();
        //create array to store all of the threads we will use
        Thread[] threadArray = new Thread[threads];
        //if we can evenly divide our resolution by the number of threads, each thread takes xRes/threads worth of width in terms of rendering the image
        if (xRes % threads == 0) {
            int perThread = xRes / threads;
            int current = perThread;
            int last = 0;
            for (int i = 0; i < threads; i++) {
                SelectiveRender currentThreadsRender;
                // if we're the last thread, minus one from where we run till to avoid out of bounds
                if (i == threads - 1) {
                    //create runnable to hand thread
                    currentThreadsRender = new SelectiveRender(sceneImage, this, last, current - 1, aliasing, aliasingRes);

                } else {

                    currentThreadsRender = new SelectiveRender(sceneImage, this, last, current, aliasing, aliasingRes);

                }
                //hand thread runable
                Thread currentThread = new Thread(currentThreadsRender);
                //set priority high for speed
                currentThread.setPriority(Thread.MAX_PRIORITY);
                //place thread into thread array
                threadArray[i] = currentThread;
                //start threads run method
                currentThread.start();
                //update current pos
                last = current;
                current += perThread;
            }
        } else {
            //same thing as above except the last thread gets the leftovers
            int perThread = xRes / threads;
            int current = perThread;
            int last = 0;
            for (int i = 0; i < threads; i++) {
                SelectiveRender currentThreadsRender;
                if (i == threads - 1) {

                    currentThreadsRender = new SelectiveRender(sceneImage, this, last, current + (xRes % threads) - 1, aliasing, aliasingRes);


                } else {

                    currentThreadsRender = new SelectiveRender(sceneImage, this, last, current, aliasing, aliasingRes);

                }
                Thread currentThread = new Thread(currentThreadsRender);
                currentThread.setPriority(Thread.MAX_PRIORITY);
                threadArray[i] = currentThread;
                currentThread.start();
                last = current;
                current += perThread;
            }
        }
        try {
            //join the threads with the main thread (wait till all the lil guys are done doing their thing before moving on))
            for (Thread thread : threadArray) {
                thread.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sceneImage;
    }
    //setters and getter
    public void setCamera(Camera newCam) {
        camera = newCam;
    }
    public void addSurface(Surface newSurface) {
        surfaces.add(newSurface);
    }
    //adds a light to the light arraylist
    public void addLight(Light li) {
        lights.add(li);
    }
    public Camera getCamera() {
        return camera;
    }
    public void removeSurface(Surface surface) {
        surfaces.remove(surface);
    }
    public void removeSurfaceAll(ArrayList<Surface> surfaces) {
        this.surfaces.removeAll(surfaces);
    }
    public void removeLight(Light light) {
        lights.remove(light);
    }
    public void removeLightAll(ArrayList<PointLight> lights) {
        this.lights.removeAll(lights);
    }
}
