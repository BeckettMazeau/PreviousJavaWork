public class Color {
    /*Colors represented as 0-1 to simplify math.*/
    //initialize instance variables
    private double r;
    private double g;
    private double b;
    //constructor with dual input type capabilities
    public Color(double r, double g, double b) {
        if (r>255||g>255||b>255){
            this.r = r/255.0;
            this.g = g/255.0;
            this.b = b/255.0;
        }else {
            this.r = r;
            this.b = b;
            this.g = g;
        }
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setB(double b) {
        this.b = b;
    }

    //getters
    public double getR() {
        return r;
    }
    public double getG() {
        return g;
    }
    public double getB() {
        return b;
    }
    //adding values RGB values of two colors
    public Color add(Color c) {
        Color aColor = new Color(0,0,0);
        aColor.setR(r + c.getR());
        aColor.setG(g + c.getG());
        aColor.setB(b + c.getB());
        return aColor;
    }
    //scaling values of a color by a scalar
    public Color scale(double scalar){
        return new Color(r * scalar, g * scalar, b * scalar);
    }
    //essentially mixing colors to create new combination
    public Color shade(Color c) {
        return new Color(r*c.getR(), g*c.getG(), b*c.getB());
    }
    //essentially adding one color to another, taking it closer to white
    public Color tint(Color c) {
        double rTint = r+(1-r)*c.getR();
        double gTint = g+(1-g)*c.getG();
        double bTint = b+(1-b)*c.getB();
        return new Color(rTint,gTint,bTint);
    }
    //interaction method for java.awt.Color
    public java.awt.Color toAWTColor(){
        return new java.awt.Color(toARGB());
    }
    //Conversion to argb with protections against too low values and too large values.
    public int toARGB(){
        int ir = (int)(Math.min(Math.max(r,0),1) * 255 + 0.1);
        int ig = (int)(Math.min(Math.max(g,0),1) * 255 + 0.1);
        int ib = (int)(Math.min(Math.max(b,0),1) * 255 + 0.1);
        return (ir << 16) | (ig << 8) | (ib << 0);
    }
}
