public class Lambert extends Material {
    //instance variable
    Color diffuse;
    //constructor
    public Lambert(Color diffuse) {
        this.diffuse = diffuse;
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
        //shading the returned color the color of the light
        return newColor.shade(li.getLightColor());

    }
    //getters and setters
    //gettype for easier checking of object type
    public String getType() {
        return "Lambert";
    }

    public Color getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Color diffuse) {
        this.diffuse = diffuse;
    }
}
