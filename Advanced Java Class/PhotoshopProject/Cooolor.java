package PhotoshopProject;

import java.security.InvalidParameterException;

public class Cooolor {
    int[] RGB = new int[3];
    public Cooolor(int R, int G, int B){
        if (R > 255 || G > 255 || B > 255){
            throw new InvalidParameterException("Value too high, max value 256");
        }
        RGB[0] = R;
        RGB[1] = G;
        RGB[2] = B;
    }
    public static double findColorDistance(Cooolor color1, Cooolor color2){
        int R1 = color1.getRed();
        int R2 = color2.getRed();
        int B1 = color1.getBlue();
        int B2 = color2.getBlue();
        int G1 = color1.getGreen();
        int G2 = color2.getGreen();
        return Math.sqrt(Math.pow((R1-R2),2)+Math.pow((B1-B2),2)+Math.pow((G1-G2),2));
    }

    public void setRGB(int[] RGB) {
        this.RGB = RGB;
    }
    public void setRGB(int R, int G, int B){
        RGB[0] = R;
        RGB[1] = G;
        RGB[2] = B;
    }
    public void randomizeRGB(){
        int R = (int)(Math.random()*256);
        int G = (int)(Math.random()*256);
        int B = (int)(Math.random()*256);
        setRed(R);
        setGreen(G);
        setBlue(B);
    }
    public void setRed(int R){RGB[0]=R;}
    public void setGreen(int G){RGB[1]=G;}
    public void setBlue(int B){RGB[2]=B;}

    public int[] getRGB() {
        return RGB;
    }
    public int getRed(){return RGB[0];}
    public int getGreen(){return RGB[1];}
    public int getBlue(){return RGB[2];}
}
