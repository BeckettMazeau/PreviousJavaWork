
/**
 * Implementation of specular phong material
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Phong extends Material {
    //instance variables
    //base color
    Color diffuse;
    //highlight color
    Color specular;
    //exponenet determining intensity of highlights
    double exponent;
    //constructor
    public Phong(Color diffuse, Color specular, double exponent) {
        this.diffuse = diffuse;
        this.exponent = exponent;
        this.specular = specular;
    }
    //computelighting for use in render clas
    public Color computeLighting(Intersection i, Ray viewingRay, Light li) {

        Vector lightDirection = li.computeLightDirection(i.getPosition());
        Vector normal = i.getNormal();
        //finding cosine of 'impact' angle with surface to determine how much of the lights color to add to the returned surface color
        double dot = normal.dot(lightDirection);
        //if cosine of angle is <0 there is no light returned at all
        if (dot < 0) {
            return new Color(0, 0, 0);
        }
        //scaling the color of the surface by the amount of light returning
        
        Color newColor = diffuse.scale(dot);
        //aquiring mirror vector (direction of reflection) off of surface given impact angle
        Vector mirrorVec = ((normal.scale(2 * (normal.dot(lightDirection)))).subtract(lightDirection)).normalize();
        //getting the cosine of the angle between the inversion of our viewing ray and the created mirror vector
        double cosine = (viewingRay.getDirection().scale(-1)).dot(mirrorVec);
        //if this cosine is less than 0, perform simple shading of surface by light color, no shine
        if (cosine < 0) {
            return newColor.shade(li.getLightColor());
        }
        //otherwise, get the specular coefficient by taking the calculated cosine to the power of our specular exponenet
        double specCo = Math.pow(cosine, exponent);
        //The base fo the highlight color will be the color of the light, which is then scaled by the above specular coefficient and shaded by our passed in specular color
        Color highColor = li.getLightColor();
        highColor = highColor.scale(specCo).shade(specular);
        //the returned color is the color created at line 34 shaded by the light color and then tinted by the highlight color
        return (newColor.shade(li.getLightColor())).tint(highColor);

    }
    //getters and setters for easy interaction with gui and field population
    public String getType() {
        return "Phong";
    }

    public Color getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Color diffuse) {
        this.diffuse = diffuse;
    }

    public Color getSpecular() {
        return specular;
    }

    public void setSpecular(Color specular) {
        this.specular = specular;
    }

    public double getExponent() {
        return exponent;
    }

    public void setExponent(double exponent) {
        this.exponent = exponent;
    }
}
