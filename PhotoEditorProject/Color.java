public class Color {
    //array for the RGB values in each color object
    int[] RGB = new int[3];
    //constructor to fill in/initialize RGB values in each color object
    public Color(int R, int G, int B){
        RGB[0] = R;
        RGB[1] = G;
        RGB[2] = B;
    }
    //constructor to fill in/initialize RGB values in each color object, alternative constructor allowing for direct input of int array
    public Color(int[] rgb){
        RGB = rgb;
    }
    //finds color distance using standard distance formula (derived from pythagorean theorem)
    public static double findColorDistance(Color color1, Color color2){
        int R1 = color1.getRed();
        int R2 = color2.getRed();
        int B1 = color1.getBlue();
        int B2 = color2.getBlue();
        int G1 = color1.getGreen();
        int G2 = color2.getGreen();
        return Math.sqrt(Math.pow((R1-R2),2)+Math.pow((B1-B2),2)+Math.pow((G1-G2),2));
    }
    //method allowing RGB int array to be set from an int array
    public void setRGB(int[] RGB) {
        this.RGB = RGB;
    }
    //method allowing RGB int array to be set from individual RGB int values
    public void setRGB(int R, int G, int B){
        RGB[0] = R;
        RGB[1] = G;
        RGB[2] = B;
    }
    //sets RGB values to random numbers between 0 and 255
    public void randomizeRGB(){
        //generates number for R G and B between 0 and 255, then cast to int
        int R = (int)(Math.random()*256);
        int G = (int)(Math.random()*256);
        int B = (int)(Math.random()*256);
        this.setRed(R);
        this.setGreen(G);
        this.setBlue(B);
    }
    //takes the average of R G and B values and then returns a new color which is the average of the two input colors
    public static Color averageColor(Color color1, Color color2){
        int R1 = color1.getRed();
        int R2 = color2.getRed();
        int B1 = color1.getBlue();
        int B2 = color2.getBlue();
        int G1 = color1.getGreen();
        int G2 = color2.getGreen();
        int R = (R1+R2)/2;
        int G = (G1+G2)/2;
        int B = (B1+B2)/2;
        return new Color(R,G,B);
    }
    //Setter methods for individual color values
    public void setRed(int R){RGB[0]=R;}
    public void setGreen(int G){RGB[1]=G;}
    public void setBlue(int B){RGB[2]=B;}
    //Getter method for RGB array
    public int[] getRGB() {
        return RGB;
    }
    //getter methods for individual R G and B values
    public int getRed(){return RGB[0];}
    public int getGreen(){return RGB[1];}
    public int getBlue(){return RGB[2];}
}
