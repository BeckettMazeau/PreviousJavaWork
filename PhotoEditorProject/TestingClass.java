import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import java.util.Arrays;

public class TestingClass{
    public static void main(String[] args) throws Exception{
    System.out.println("step1");
    BufferedImage img = ImageIO.read(new File("./Images/beautifulCow.jpg"));
    int newWidth = 0;
    int newHeight =0;
    double ratio1 = 200/img.getHeight();
    newHeight = (int)(img.getHeight()*ratio1);
    double ratio = 200/img.getWidth();
    newWidth = (int)(img.getWidth()*ratio);
        System.out.println("step12");
        System.out.println(img.getHeight());
    Image scaled =  img.getScaledInstance(newWidth, newHeight, 1);
        System.out.println("step123");
    }
}



