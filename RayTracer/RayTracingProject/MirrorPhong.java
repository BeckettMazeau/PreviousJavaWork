
/**
 * Extension of phong material which also reflects light
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MirrorPhong extends Phong {
    //instance variable
    double reflectiveness;
    //constructor
    public MirrorPhong(Color diff, Color spec, double exp, double refPwr) {
        //passing all phong variables up to the parent class for simplicity
        super(diff, spec, exp);
        reflectiveness = refPwr;
    }
    //getters
    public String getType() {
        return "MirrorPhong";
    }

    public double getReflectiveness() {
        return reflectiveness;
    }
}
