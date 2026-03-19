import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class ColorImage {
    private Color[][] imageArray;
    private int width;
    private int height;
    //constructor
    public ColorImage(int width, int height) {
        this.width = width;
        this.height = height;
        imageArray = new Color[width][height];
        fill(imageArray);
    }
    //setters and getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor(int x, int y) {
        return imageArray[x][y];
    }

    public void setColor(int x, int y, Color color) {
        imageArray[x][y] = color;
    }

    //Fills a color array with color objects (black)
    private void fill(Color[][] anImageArray) {
        for (int x = 0; x < anImageArray.length; x++) {
            for (int y = 0; y < anImageArray[x].length; y++) {
                anImageArray[x][y] = new Color(0, 0, 0);
            }
        }
    }
    //Used to aid in mainpreview, just copying colors over from color image to a same sized buffered image
    public BufferedImage toBufferedImage() {
        try {

            BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < getWidth(); x++) {
                for (int y = 0; y < getHeight(); y++) {
                    //This line reverses the y axis. Use the following line instead if your image is upside down.
                    bi.setRGB(x, getHeight() - 1 - y, getColor(x, y).toARGB());
                    //bi.setRGB(x,y,getColor(x,y).toARGB());
                }
            }
            return bi;

        } catch (Exception e) {
            System.out.println("Problem rendering image");
            System.out.println(e);
            System.exit(1);
        }
        return null;
    }
}
