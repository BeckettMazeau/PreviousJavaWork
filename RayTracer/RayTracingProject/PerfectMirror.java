
/**
 * A perfect mirror material
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PerfectMirror extends Material {
    public Color computeLighting(Intersection i, Ray viewingRay, Light li) {
        return new Color(0, 0, 0);
    }


    //constructor (or lack thereof). Because this is a perfect mirror, it essentially needs to do nothing, any code related to it is done in the scene class
    public PerfectMirror() {}

    public String getType() {
        return "PerfectMirror";
    }

    public double getReflectiveness() {
        return Double.MAX_VALUE;
    }
}
