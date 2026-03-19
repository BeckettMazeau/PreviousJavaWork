
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.*;
import javax.swing.*;
/** 
* This PhotoshopDriver is where you will write the commands to actually load,
* edit, and save your images. It contains a couple of utility methods that will be 
* used to handle the file reading and writing, which you shouldn't change, but the
* main method is yours to use however you need.
*/
public class PhotoshopDriver {
    public static void main() throws Exception{
        /**
         * An example of how you might write the code for loading an image, applying a filter, 
         * and saving it to a new filename.
         * Always save your resulting image to a new filename, so you don't overwrite the original!
         * Note I am saving to a file with the ".jpg" filetype.
         * This will work for most photos, but you may get some color error from the compression process.
         * If you want a higher-quality export, give your filename the ".png" filetype instead.
         */
        //Test image to show how images are organized. Everything is in row-major order.
        //Essentially, internally, the colorimage class stores all images 'vertically flipped,' however, because we're doing two flips,
        //one when the photoshopdriver loads in an image, and one when it saves it, images come out as intended. Because we're loading images as vertically flipped,
        //row 0 corresponds to y=0, and col 0 corresponds to x=0.
        /*Color[][] test = new Color[600][600];
        for (int row = 0; row < test.length; row++) {
            for (int col = 0; col < test[0].length; col++) {
                if (row< test.length/2){
                    if (col<test[0].length/2){
                        test[row][col] = new Color(0,0,0);
                    }else {
                        test[row][col] = new Color(255,255,255);
                    }
                }else {
                    if (col<test[0].length/2){
                        test[row][col] = new Color(255,0,0);
                    }else {
                        test[row][col] = new Color(0,255,0);
                    }
                }
            }
        }
        ColorImage testImage = new ColorImage(test);
        saveImage("testImage.jpg",testImage);
        testImage.toRotate90Clock();*/
        //Apply various methods to images so user has images to play with on startup (technically these images will
        //always be there since i've left them in the project files, however, if somebody deleted them, this is why they're here.)
        //Uncomment to use.
        /*//Load in images

        ColorImage beautifulCow = loadImage("./Images/beautifulCow.jpg");
        ColorImage cowInHay = loadImage("./Images/cowInHay.jpg");
        ColorImage greenScreenDancer = loadImage("./Images/greenScreenDancer.jpg");
        ColorImage disco = loadImage("./Images/disco.jpg");

        saveImage("cowInHaySepia.jpg", cowInHay.toSepia());
        saveImage("cowInHayMono.jpg", cowInHay.toMonochrome());
        saveImage("cowInHayContrast.jpg", cowInHay.toContrast(-1));
        saveImage("beautifulCowRightMirror.jpg", beautifulCow.toRightLeftMirror());
        saveImage("beautifulCowLeftMirror.jpg", beautifulCow.toLeftRightMirror());
        saveImage("beautifulCowRotate90Clock.jpg", beautifulCow.toRotate90Clock());
        saveImage("beautifulCowRotate90Counter.jpg", beautifulCow.toRotate90Counter());
        saveImage("beautifulCowCrop.jpg", beautifulCow.toCrop(90,90,90,90));
        saveImage("beautifulCowTopDown.jpg", beautifulCow.toTopDownMirror());
        saveImage("beautifulCowDownTop.jpg", beautifulCow.toDownTopMirror());
        saveImage("dancingOnDisco.jpg", greenScreenDancer.chromaKey(disco,140));
        saveImage("dancingOnDiscoEdgedBW.jpg", (greenScreenDancer.chromaKey(disco,180)).edgeDetectionBW(25));
        saveImage("dancingOnDiscoEdgedC.jpg", (greenScreenDancer.chromaKey(disco,180)).edgeDetectionC(255,0,0,25));
        saveImage("output1.jpg", beautifulCow);*/
        //Start photoshop GUI
        GUI myGUI = new GUI();
    }


    /**
     * Takes an image filename (e.g. "photo.jpg") and converts it to a ColorImage.
     */
    public static ColorImage loadImage(String filename){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (Exception e) {
            System.out.println("Problem loading image: " + filename);
            System.out.println(e);
            System.exit(1);
        }
        ColorImage c = new ColorImage(img.getWidth(), img.getHeight());
        for (int x=0; x<img.getWidth(); x++){
            for (int y=0; y<img.getHeight(); y++){
                c.setColor(x,y,fromARGB(img.getRGB(x,y)));
            }
        }
        return c;
    }
    //modified to take raw buffered image and convert it to ColorImage
    public static ColorImage loadImage(BufferedImage img){
        ColorImage c = new ColorImage(img.getWidth(), img.getHeight());
        for (int x=0; x<img.getWidth(); x++){
            for (int y=0; y<img.getHeight(); y++){
                c.setColor(x,y,fromARGB(img.getRGB(x,y)));
            }
        }
        return c;
    }
    
    /** 
     * Converts the R,G,B values of a Color to ARGB format, which is required by the BufferedImage
     * file writing class.
     */
    public static int toARGB(Color c) {
        int ir = Math.min(Math.max(c.getRed(),0),255);
        int ig = Math.min(Math.max(c.getGreen(),0),255);
        int ib = Math.min(Math.max(c.getBlue(),0),255);
        return (ir << 16) | (ig << 8) | (ib << 0);
    }
    
    /** 
     * Takes a packed int in ARGB format, which we get from the BufferedImage file writing class,
     * and turns it into a Color object by separating out its R,G,B values.
     */
    public static Color fromARGB(int packed){
        int r = ((packed >> 16) & 255);
        int g = ((packed >> 8) & 255);
        int b = (packed & 255);
        return new Color(r,g,b);
    
    }
    
    /**
     * Reads in each pixel from a ColorImage, and then writes the image out to a file.
     * Supports PNG and JPG file types.
     */
    public static void saveImage(String filename, ColorImage image){
        try {
            
            BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            for(int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    bi.setRGB(x,y,toARGB(image.getColor(x,y)));
                }
            }
            if(filename.substring(filename.length()-4).equalsIgnoreCase(".png")){
                ImageIO.write(bi, "PNG", new File(filename));
            }
            else if (filename.substring(filename.length()-4).equalsIgnoreCase(".jpg")){
                ImageIO.write(bi, "JPG", new File(filename));
            }
            else {
                System.out.println("Unsupported file type - please save your image as either a .jpg or .png file");
            }
            
        } catch(Exception e) {
            System.out.println("Problem saving image: " + filename);
            System.out.println(e);
            System.exit(1);
        }
    }

    /**
     * Simpler version of the saveImage method for testing. Doesn't require integration with ColorImage, just writes
     * a gradient of colors out to an image to make sure the BufferedImage, ImageIO, and File libraries are working
     * as expected.
     */
    //calculates color distance using color distance formula
    public static double calculateColorDistance(Color color1, Color color2){
            return Math.sqrt(Math.pow((color1.getRed()-color2.getRed()),2)+Math.pow((color1.getGreen()-color2.getGreen()),2)+Math.pow((color1.getBlue()-color2.getBlue()),2));
    }
    public static void saveTestImage(){
        try {
            
               BufferedImage biTest = new BufferedImage(250,200,BufferedImage.TYPE_INT_RGB);
               for(int x = 0; x < 250; x++){
                   for(int y = 0; y < 200; y++){
                       biTest.setRGB(x,y,(x << 16) | (y << 8) | (0 << 0));
                    }
                }
                ImageIO.write(biTest, "PNG", new File("testGradient.png"));
                
        } catch(Exception e) {
            System.out.println("Problem saving test gradient image");
            System.out.println(e);
            System.exit(1);
        }
    }
}