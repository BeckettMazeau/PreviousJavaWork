import java.awt.*;

public class ColorImage {
    //Initialize 2d color array for colorimage class
    private Color[][] colorImage;
    //ColorImage Constructor, fills array with colors of random values
    public ColorImage (int width, int height){
        //initialize array
        this.colorImage = new Color[height][width];
        //fill array with random colors
        fillImage(colorImage);
    }
    //ColorImage constructor, fills in colorImage color array with passed in color array.
    public ColorImage(Color[][] imageArray){
        colorImage = imageArray;
    }
    //Reversed for interaction with external methods. Top of image is y=0, left is x=0. All works as intended. See README for explanation
    public Color getColor(int x, int y) {
        return colorImage[y][x];
    }
    public void setImage(Color[][] ColorImage) {
        this.colorImage = ColorImage;
    }

    public Color[][] getImage() {
        return colorImage;
    }
    public void fillImage(Color[][] image){
        //traverses image, places a color object in each space, and randomizes its color
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[row].length; col++) {
                image[row][col] = new Color(0,0,0);
                image[row][col].randomizeRGB();
            }

        }
    }
    public void fillImage(){
        //traverses colorImage, places a color object in each space, and randomizes its color
        for (int row = 0; row < colorImage.length; row++) {
            for (int col = 0; col < colorImage[row].length; col++) {
                colorImage[row][col] = new Color(0,0,0);
                colorImage[row][col].randomizeRGB();
            }

        }
    }
    public void setColor(int x, int y, Color color){
        //sets color at x position along the images width, and y position along its height, to the color passed in
        colorImage[y][x] = color;
    }
    public void setColorRGB(int x, int y, int R, int G, int B){
        //sets color at x position along the images width, and y position along its height, to the color passed in as integers R, G, and B
        colorImage[y][x].setRGB(R,G,B);
    }
    public void setColorRed(int y, int x, int value){
        //sets R value for the color at x position along the images width, and y position along its height, to the integer R
        colorImage[y][x].setRed(value);
    }
    public void setColorGreen(int row, int col, int value){
        //sets G value for the color at x position along the images width, and y position along its height, to the integer G
        colorImage[row][col].setGreen(value);
    }
    public void setColorBlue(int row, int col, int value){
        //sets B value for the color at x position along the images width, and y position along its height, to the integer B
        colorImage[row][col].setBlue(value);
    }
    public void checkNull(Color[][] toCheck){
    //Traverses through color array and fills any null spaces with a new green color object
    for (int y = 0; y < toCheck.length; y++) {
        for (int x = 0; x < toCheck[0].length; x++) {
            if (toCheck[y][x] == null){
                toCheck[y][x] = new Color(0, 255, 42);
            }
        }
    }

    }
    public int getWidth(){
        //returns the width of the image
        return colorImage[0].length;
    }
    public int getHeight(){
        //returns the height of the image
        return colorImage.length;
    }


    //Filters
        //Static Instance Variable for ColorAverage Method
            public static final int UP_LEFT = 0;
            public static final int UP_RIGHT = 1;
            public static final int DOWN_LEFT = 2;
            public static final int DOWN_RIGHT = 3;
    //Color Filters
    public ColorImage toSepia(){
        //initalize new image
        ColorImage newImage = new ColorImage(getWidth(),getHeight());
        //fill new image with color objects
        newImage.fillImage();
        //traverse colorimage and new image
        for (int row = 0; row < colorImage.length; row++) {
            for (int col = 0; col < colorImage[row].length; col++) {

                Color currentColor=colorImage[row][col];
                //perform calculations to find sepia colors for current location in colorimage
                int outputRed = (int)((currentColor.getRed() * .393) + (currentColor.getGreen() *.769) + (currentColor.getBlue() * .189));
                int outputGreen = (int)((currentColor.getRed() * .349) + (currentColor.getGreen() *.686) + (currentColor.getBlue() * .168));
                int outputBlue = (int)((currentColor.getRed() * .272) + (currentColor.getGreen() *.534) + (currentColor.getBlue() * .131));
                //set newimage at currentlocation to sepia colors in colorimage
                newImage.setColorRGB(col,row,outputRed,outputGreen,outputBlue);

            }
        }
        return newImage;
    }
    public ColorImage toMonochrome(){
        //initalize new image
        ColorImage newImage = new ColorImage(getWidth(),getHeight());
        //fill new image with color objects
        newImage.fillImage();
        //traverse colorimage and new image
        for (int row = 0; row < colorImage.length; row++) {
            for (int col = 0; col < colorImage[row].length; col++) {
                //access color and color values
                Color currentColor = colorImage[row][col];
                int[] RGB = currentColor.getRGB();
                //average values
                int value = (RGB[0]+RGB[1]+RGB[2])/3;
                //set new image at location
                newImage.setColorRGB(col,row,value,value,value);
            }
        }
        return newImage;
    }
    public ColorImage toContrast(double f){
        //initalize new image
        ColorImage newImage = new ColorImage(getWidth(),getHeight());
        //fill new image with color objects
        newImage.fillImage();
        //traverse colorimage and new image
        for (int row = 0; row < colorImage.length; row++) {
            for (int col = 0; col < colorImage[row].length; col++) {
                //retrieve color
                Color currentColor=colorImage[row][col];
                //modify output colors through contrast formula
                int outputRed = (int)((currentColor.getRed()-128)*f+128);
                int outputGreen = (int)((currentColor.getGreen()-128)*f+128);
                int outputBlue = (int)((currentColor.getBlue()-128)*f+128);
                //set new image at location
                newImage.setColorRGB(col,row,outputRed,outputGreen,outputBlue);
            }
        }
        return newImage;
    }
    public ColorImage chromaKey(ColorImage background, double tolerance){
        //create new image of same dimensions
        Color[][] newImage = new Color[getHeight()][getWidth()];
        //grab color array of background image (prescaled)
        Color[][] backgroundImage = background.getImage();
        //define green
        Color green = new Color(0,255,0);
        //traverse main image
        for (int row=0; row < getHeight(); row++) {

            for (int width=0; width < getWidth(); width++) {
                //check the colordistance from green of the color at this location, if less than tolerance, place background image color at current position onto newimage at position, otherwise,
                //place color at current position on image into current position on color image
                if (Color.findColorDistance(colorImage[row][width],green)<tolerance){
                    newImage[row][width] = backgroundImage[row][width];
                }else {
                    newImage[row][width]=colorImage[row][width];
                }
            }
        }
        return new ColorImage(newImage);
    }
    public ColorImage chromaKey(ColorImage background, double tolerance, int R, int G, int B){
        //create new image of same dimensions
        Color[][] newImage = new Color[getHeight()][getWidth()];
        //grab color array of background image (prescaled)
        Color[][] backgroundImage = background.getImage();
        //define color
        Color color = new Color(R,G,B);
        //traverse main image
        for (int row=0; row < getHeight(); row++) {

            for (int width=0; width < getWidth(); width++) {
                //check the colordistance from passed in color of the color at this location, if less than tolerance, place background image color at current position onto newimage at position, otherwise,
                //place color at current position on image into current position on color image
                if (Color.findColorDistance(colorImage[row][width],color)<tolerance){
                    newImage[row][width] = backgroundImage[row][width];
                }else {
                    newImage[row][width]=colorImage[row][width];
                }
            }
        }
        //create and return colorimage of newImage color array
        return new ColorImage(newImage);
    }
    public ColorImage chromaKey(ColorImage background, double tolerance, Color color){
        //create new color array of same dimensions
        Color[][] newImage = new Color[getHeight()][getWidth()];
        //grab color array of background image (prescaled)
        Color[][] backgroundImage = background.getImage();
        //traverse main image
        for (int row=0; row < getHeight(); row++) {

            for (int width=0; width < getWidth(); width++) {
                //check the colordistance from passed in color of the color at this location, if less than tolerance, place background image color at current position onto newimage at position, otherwise,
                //place color at current position on image into current position on color image
                if (Color.findColorDistance(colorImage[row][width],color)<tolerance){
                    newImage[row][width] = backgroundImage[row][width];
                }else {
                    newImage[row][width]=colorImage[row][width];
                }
            }
        }
        //create and return colorimage of newImage color array
        return new ColorImage(newImage);
}
    public ColorImage edgeDetectionC(int R, int G, int B, double tolerance){
        //define color
        Color color = new Color(R,G,B);
        //create color array of same dimensions
        Color[][] newImage = new Color[getHeight()][getWidth()];
        //traverses colorimage we are modifying
        for (int row=0; row < getHeight(); row++) {
            //checks if current row index is at either the top or the bottom of the image, used for logic
            boolean heightMax = row == getHeight()-1;
            boolean heightMin = row==0;
            for (int width=0; width < getWidth(); width++) {
                //checks if width index is at either the left edge or the right edge of the image, used for logic
                boolean widthMax = width==getWidth()-1;
                boolean widthMin = width== 0;
                //initializes variables used for averaging of distance. Higher average distance means more likely to be an edge
                int count = 0;
                double distanceSum = 0;
                //checks all pixels around our current index and adds together their R G and B values. Counts how many pixels added
                //does not try to add pixels that do not exist (off of array).
                if (!heightMax){
                    if (!widthMin){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row+1][width-1]);
                    }
                    if (!widthMax){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row+1][width+1]);
                    }
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row+1][width]);
                }
                if(!heightMin){
                    if (!widthMin){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row-1][width-1]);
                    }
                    if (!widthMax){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row-1][width+1]);
                    }
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row-1][width]);
                }
                if (!widthMin){
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row][width-1]);
                }
                if (!widthMax){
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row][width+1]);
                }
                //find average distance
                double distanceAverage = distanceSum/count;
                //check if average distance is less than the tolerance, if so, fills in current position in newimage color array
                //with a new color of the same RGB value as current position in original image, otherwise, fills in with designated
                //color
                if (distanceAverage<tolerance){
                    newImage[row][width] = new Color(colorImage[row][width].getRGB());
                }else {
                    newImage[row][width] = color;
                }
            }
        }
        return new ColorImage(newImage);
    }
    public ColorImage edgeDetectionC(Color color, double tolerance){
        //same as other edgeDetectionC, just allows for direct passing in of a color object
        Color[][] newImage = new Color[getHeight()][getWidth()];
        for (int row=0; row < getHeight(); row++) {
            boolean heightMax = row == getHeight()-1;
            boolean heightMin = row==0;
            for (int width=0; width < getWidth(); width++) {
                boolean widthMax = width==getWidth()-1;
                boolean widthMin = width== 0;
                int count = 0;
                double distanceSum = 0;
                if (!heightMax){
                    if (!widthMin){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row+1][width-1]);
                    }
                    if (!widthMax){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row+1][width+1]);
                    }
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row+1][width]);
                }
                if(!heightMin){
                    if (!widthMin){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row-1][width-1]);
                    }
                    if (!widthMax){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row-1][width+1]);
                    }
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row-1][width]);
                }
                if (!widthMin){
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row][width-1]);
                }
                if (!widthMax){
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row][width+1]);
                }
                double distanceAverage = distanceSum/count;
                if (distanceAverage<tolerance){
                    newImage[row][width] = colorImage[row][width];
                }else {
                    newImage[row][width] = color;
                }
            }
        }
        return new ColorImage(newImage);
    }
    public ColorImage edgeDetectionB(int R, int G, int B,double tolerance){
        //same as edgeDetectionC. Only difference is this method takes in a color to highlight
        //edges with, everything else is made black.
        Color[][] newImage = new Color[getHeight()][getWidth()];
        Color black = new Color(0,0,0);
        Color color = new Color(R,G,B);

        for (int row=0; row < getHeight(); row++) {
            boolean heightMax = row == getHeight()-1;
            boolean heightMin = row==0;
            for (int width=0; width < getWidth(); width++) {
                boolean widthMax = width==getWidth()-1;
                boolean widthMin = width== 0;
                int count = 0;
                double distanceSum = 0;
                if (!heightMax){
                    if (!widthMin){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row+1][width-1]);
                    }
                    if (!widthMax){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row+1][width+1]);
                    }
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row+1][width]);
                }
                if(!heightMin){
                    if (!widthMin){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row-1][width-1]);
                    }
                    if (!widthMax){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row-1][width+1]);
                    }
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row-1][width]);
                }
                if (!widthMin){
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row][width-1]);
                }
                if (!widthMax){
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row][width+1]);
                }
                double distanceAverage = distanceSum/count;
                if (distanceAverage<tolerance){
                    newImage[row][width] = black;
                }else {
                    newImage[row][width] = color;
                }
            }
        }
        return new ColorImage(newImage);
    }
    public ColorImage edgeDetectionBW(double tolerance){
        //same as edgeDetectionB, only difference is this just highlights in white, no color passed in.
        Color[][] newImage = new Color[getHeight()][getWidth()];
        Color black = new Color(0,0,0);
        Color white = new Color(255,255,255);

        for (int row=0; row < getHeight(); row++) {
            boolean heightMax = row == getHeight()-1;
            boolean heightMin = row==0;
            for (int width=0; width < getWidth(); width++) {
                boolean widthMax = width==getWidth()-1;
                boolean widthMin = width== 0;
                int count = 0;
                double distanceSum = 0;
                if (!heightMax){
                    if (!widthMin){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row+1][width-1]);
                    }
                    if (!widthMax){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row+1][width+1]);
                    }
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row+1][width]);
                }
                if(!heightMin){
                    if (!widthMin){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row-1][width-1]);
                    }
                    if (!widthMax){
                        count++;
                        distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row-1][width+1]);
                    }
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row-1][width]);
                }
                if (!widthMin){
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row][width-1]);
                }
                if (!widthMax){
                    count++;
                    distanceSum+= Color.findColorDistance(colorImage[row][width],colorImage[row][width+1]);
                }
                double distanceAverage = distanceSum/count;
                if (distanceAverage<tolerance){
                    newImage[row][width] = black;
                }else {
                    newImage[row][width] = white;
                }
            }
        }
        return new ColorImage(newImage);
    }
    public ColorImage blur(){
        //creates new 2d color array of same dimensions
        Color[][] newImage = new Color[getHeight()][getWidth()];
        //fills 2d array with color objects
        fillImage(newImage);
        //traverses colorimage we are modifying
        for (int row=0; row < getHeight(); row++) {
            //checks if current row index is at either the top or the bottom of the image, used for logic
            boolean heightMax = row == getHeight()-1;
            boolean heightMin = row==0;
            for (int width=0; width < getWidth(); width++) {
                //checks if width index is at either the left edge or the right edge of the image, used for logic
                boolean widthMax = width==getWidth()-1;
                boolean widthMin = width== 0;
                //initializes variables used for averaging
                int count = 0;
                int redSum = 0;
                int greenSum = 0;
                int blueSum = 0;
                //checks all pixels around our current index and adds together their R G and B values. Counts how many pixels added
                //does not try to add pixels that do not exist (off of array).
                if (!heightMax){
                    if (!widthMin){
                        count++;
                        redSum+= colorImage[row+1][width-1].getRed();
                        greenSum+=colorImage[row+1][width-1].getGreen();
                        blueSum+=colorImage[row+1][width-1].getBlue();
                    }
                    if (!widthMax){
                        count++;
                        redSum+= colorImage[row+1][width+1].getRed();
                        greenSum+=colorImage[row+1][width+1].getGreen();
                        blueSum+=colorImage[row+1][width+1].getBlue();
                    }
                    count++;
                    redSum+= colorImage[row+1][width].getRed();
                    greenSum+=colorImage[row+1][width].getGreen();
                    blueSum+=colorImage[row+1][width].getBlue();
                }
                if(!heightMin){
                    if (!widthMin){
                        count++;
                        redSum+= colorImage[row-1][width-1].getRed();
                        greenSum+=colorImage[row-1][width-1].getGreen();
                        blueSum+=colorImage[row-1][width-1].getBlue();
                    }
                    if (!widthMax){
                        count++;
                        redSum+= colorImage[row-1][width+1].getRed();
                        greenSum+=colorImage[row-1][width+1].getGreen();
                        blueSum+=colorImage[row-1][width+1].getBlue();
                    }
                    count++;
                    redSum+= colorImage[row-1][width].getRed();
                    greenSum+=colorImage[row-1][width].getGreen();
                    blueSum+=colorImage[row-1][width].getBlue();
                }
                if (!widthMin){
                    count++;
                    redSum+= colorImage[row][width-1].getRed();
                    greenSum+=colorImage[row][width-1].getGreen();
                    blueSum+=colorImage[row][width-1].getBlue();
                }
                if (!widthMax){
                    count++;
                    redSum+= colorImage[row][width+1].getRed();
                    greenSum+=colorImage[row][width+1].getGreen();
                    blueSum+=colorImage[row][width+1].getBlue();
                }
                //add current pixel
                count++;
                redSum+= colorImage[row][width].getRed();
                greenSum+=colorImage[row][width].getGreen();
                blueSum+=colorImage[row][width].getBlue();
                //average the RGB values of the surrounding pixels (including current pixel)
                int redAvg = redSum/count;
                int greenAvg = greenSum/count;
                int blueAvg = blueSum/count;
                //fills current pixel in the new array with a new color of the averaged value
                newImage[row][width] = new Color(redAvg,greenAvg,blueAvg);
            }
        }
        //constructs and returns new colorimage using newImage color array.
        return new ColorImage(newImage);
    }

    public ColorImage shiftPerspective(double f, int directionOfShift){
        //creates new 2d color array of same dimensions
        Color[][] newImage = new Color[getHeight()][getWidth()];
        //fills 2d array with color objects
        fillImage(newImage);
        //applies shift filter
        colorAverageShift(f,newImage,directionOfShift);
        //returns colorImage of modified newImage array
        return new ColorImage(newImage);
    }

    private void colorAverageShift(double f, Color[][] newImage, int directionOfShift) {
        //This is arguably extremely primative code. It essentially smears the color values in the opposite direction of its movement.
        //This was originally intended to be part of a Mosaic Blur function, but once I discovered how this worked on a small test image I loved it and wanted
        //to expand it. Because it was intended as a blur function, it does end up distorting and blurring the image, however, the effect it makes can be incredible,
        //and high 'shift factor's can lead to awesome effects akin to chromatic abberation.

        //check direction of shift is desired
        if(directionOfShift==UP_LEFT) {
            //start at top left and move right
            for (int row = 0; row < getHeight(); row++) {
                //checks if this row is the top or minimum row for use in later if statements
                boolean heightMax = row == getHeight() - 1;
                boolean heightMin = row == 0;
                for (int width = 0; width < getWidth(); width++) {
                    //checks if this column is the leftmost or rightmost column for use in later if statements
                    boolean widthMax = width == getWidth() - 1;
                    boolean widthMin = width == 0;
                    //initializes variables used for averaging
                    int count = 0;
                    int redSum = 0;
                    int greenSum = 0;
                    int blueSum = 0;
                    //checks all pixels around our current index and adds together their R G and B values. Counts how many pixels added
                    //does not try to add pixels that do not exist (off of array).
                    if (!heightMax) {
                        if (!widthMin) {
                            count++;
                            redSum += colorImage[row + 1][width - 1].getRed();
                            greenSum += colorImage[row + 1][width - 1].getGreen();
                            blueSum += colorImage[row + 1][width - 1].getBlue();
                        }
                        if (!widthMax) {
                            count++;
                            redSum += colorImage[row + 1][width + 1].getRed();
                            greenSum += colorImage[row + 1][width + 1].getGreen();
                            blueSum += colorImage[row + 1][width + 1].getBlue();
                        }
                        count++;
                        redSum += colorImage[row + 1][width].getRed();
                        greenSum += colorImage[row + 1][width].getGreen();
                        blueSum += colorImage[row + 1][width].getBlue();
                    }
                    if (!heightMin) {
                        if (!widthMin) {
                            count++;
                            redSum += colorImage[row - 1][width - 1].getRed();
                            greenSum += colorImage[row - 1][width - 1].getGreen();
                            blueSum += colorImage[row - 1][width - 1].getBlue();
                        }
                        if (!widthMax) {
                            count++;
                            redSum += colorImage[row - 1][width + 1].getRed();
                            greenSum += colorImage[row - 1][width + 1].getGreen();
                            blueSum += colorImage[row - 1][width + 1].getBlue();
                        }
                        count++;
                        redSum += colorImage[row - 1][width].getRed();
                        greenSum += colorImage[row - 1][width].getGreen();
                        blueSum += colorImage[row - 1][width].getBlue();
                    }
                    if (!widthMin) {
                        count++;
                        redSum += colorImage[row][width - 1].getRed();
                        greenSum += colorImage[row][width - 1].getGreen();
                        blueSum += colorImage[row][width - 1].getBlue();
                    }
                    if (!widthMax) {
                        count++;
                        redSum += colorImage[row][width + 1].getRed();
                        greenSum += colorImage[row][width + 1].getGreen();
                        blueSum += colorImage[row][width + 1].getBlue();
                    }
                    //average the RGB values of the surrounding pixels
                    int redAvg = (int) redSum / count;
                    int greenAvg = (int) greenSum / count;
                    int blueAvg = (int) blueSum / count;
                    //create weighted average of current pixel and the surrounding pixels.
                    int weightedRed = (int) ((colorImage[row][width].getRed() + (redAvg * f)) / (1.0 + f));
                    int weightedGreen = (int) ((colorImage[row][width].getGreen() + (greenAvg * f)) / (1.0 + f));
                    int weightedBlue = (int) ((colorImage[row][width].getBlue() + (blueAvg * f)) / (1.0 + f));
                    //Fills all pixels around and including current pixel with the weighted color
                    //When this function executes, there is some overlap when a new color is set.
                    //This causes a 'shift' away from whatever direction the array is traversed in.
                    //The colors from the last column and the last row are not overwritten, and so they
                    //'stick' and seemingly extend that part of the image
                    if (!heightMax) {
                        if (!widthMin) {
                            newImage[row + 1][width - 1] = new Color(weightedRed, weightedGreen, weightedBlue);
                        }
                        if (!widthMax) {
                            newImage[row + 1][width + 1] = new Color(weightedRed, weightedGreen, weightedBlue);
                        }
                        newImage[row + 1][width] = new Color(weightedRed, weightedGreen, weightedBlue);
                    }
                    if (!heightMin) {
                        if (!widthMin) {
                            newImage[row - 1][width - 1] = new Color(weightedRed, weightedGreen, weightedBlue);
                        }
                        if (!widthMax) {
                            newImage[row - 1][width + 1] = new Color(weightedRed, weightedGreen, weightedBlue);
                        }
                        newImage[row - 1][width] = new Color(weightedRed, weightedGreen, weightedBlue);
                    }
                    if (!widthMin) {
                        newImage[row][width - 1] = new Color(weightedRed, weightedGreen, weightedBlue);
                    }
                    if (!widthMax) {
                        newImage[row][width + 1] = new Color(weightedRed, weightedGreen, weightedBlue);
                    }
                    newImage[row][width] = new Color(weightedRed, weightedGreen, weightedBlue);
                }
            }
        }
        if (directionOfShift==UP_RIGHT){
            //see UP_LEFT comments, direction of traversal just changed
            for (int row = 0; row < getHeight(); row++) {
                boolean heightMax = row == getHeight() - 1;
                boolean heightMin = row == 0;
                for (int width = getWidth()-1; width >= 0; width--) {
                    boolean widthMax = width == getWidth() - 1;
                    boolean widthMin = width == 0;
                    int count = 0;
                    int redSum = 0;
                    int greenSum = 0;
                    int blueSum = 0;

                    if (!heightMax) {
                        if (!widthMin) {
                            count++;
                            redSum += colorImage[row + 1][width - 1].getRed();
                            greenSum += colorImage[row + 1][width - 1].getGreen();
                            blueSum += colorImage[row + 1][width - 1].getBlue();
                        }
                        if (!widthMax) {
                            count++;
                            redSum += colorImage[row + 1][width + 1].getRed();
                            greenSum += colorImage[row + 1][width + 1].getGreen();
                            blueSum += colorImage[row + 1][width + 1].getBlue();
                        }
                        count++;
                        redSum += colorImage[row + 1][width].getRed();
                        greenSum += colorImage[row + 1][width].getGreen();
                        blueSum += colorImage[row + 1][width].getBlue();
                    }
                    if (!heightMin) {
                        if (!widthMin) {
                            count++;
                            redSum += colorImage[row - 1][width - 1].getRed();
                            greenSum += colorImage[row - 1][width - 1].getGreen();
                            blueSum += colorImage[row - 1][width - 1].getBlue();
                        }
                        if (!widthMax) {
                            count++;
                            redSum += colorImage[row - 1][width + 1].getRed();
                            greenSum += colorImage[row - 1][width + 1].getGreen();
                            blueSum += colorImage[row - 1][width + 1].getBlue();
                        }
                        count++;
                        redSum += colorImage[row - 1][width].getRed();
                        greenSum += colorImage[row - 1][width].getGreen();
                        blueSum += colorImage[row - 1][width].getBlue();
                    }
                    if (!widthMin) {
                        count++;
                        redSum += colorImage[row][width - 1].getRed();
                        greenSum += colorImage[row][width - 1].getGreen();
                        blueSum += colorImage[row][width - 1].getBlue();
                    }
                    if (!widthMax) {
                        count++;
                        redSum += colorImage[row][width + 1].getRed();
                        greenSum += colorImage[row][width + 1].getGreen();
                        blueSum += colorImage[row][width + 1].getBlue();
                    }

                    int redAvg = (int) redSum / count;
                    int greenAvg = (int) greenSum / count;
                    int blueAvg = (int) blueSum / count;
                    int weightedRed = (int) ((colorImage[row][width].getRed() + (redAvg * f)) / (1.0 + f));
                    int weightedGreen = (int) ((colorImage[row][width].getGreen() + (greenAvg * f)) / (1.0 + f));
                    int weightedBlue = (int) ((colorImage[row][width].getBlue() + (blueAvg * f)) / (1.0 + f));
                    if (!heightMax) {
                        if (!widthMin) {
                            newImage[row + 1][width - 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                        }
                        if (!widthMax) {
                            newImage[row + 1][width + 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                        }
                        newImage[row + 1][width].setRGB(weightedRed, weightedGreen, weightedBlue);
                    }
                    if (!heightMin) {
                        if (!widthMin) {
                            newImage[row - 1][width - 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                        }
                        if (!widthMax) {
                            newImage[row - 1][width + 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                        }
                        newImage[row - 1][width].setRGB(weightedRed, weightedGreen, weightedBlue);
                    }
                    if (!widthMin) {
                        newImage[row][width - 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                    }
                    if (!widthMax) {
                        newImage[row][width + 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                    }
                    newImage[row][width].setRGB(weightedRed, weightedGreen, weightedBlue);
                }
            }
        }
        if (directionOfShift==DOWN_LEFT){
            //see UP_LEFT comments, direction of traversal just changed
            for (int row = getHeight()-1; row >=0; row--) {
                boolean heightMax = row == getHeight() - 1;
                boolean heightMin = row == 0;
                for (int width = 0; width <getWidth(); width++) {
                    boolean widthMax = width == getWidth() - 1;
                    boolean widthMin = width == 0;
                    int count = 0;
                    int redSum = 0;
                    int greenSum = 0;
                    int blueSum = 0;

                    if (!heightMax) {
                        if (!widthMin) {
                            count++;
                            redSum += colorImage[row + 1][width - 1].getRed();
                            greenSum += colorImage[row + 1][width - 1].getGreen();
                            blueSum += colorImage[row + 1][width - 1].getBlue();
                        }
                        if (!widthMax) {
                            count++;
                            redSum += colorImage[row + 1][width + 1].getRed();
                            greenSum += colorImage[row + 1][width + 1].getGreen();
                            blueSum += colorImage[row + 1][width + 1].getBlue();
                        }
                        count++;
                        redSum += colorImage[row + 1][width].getRed();
                        greenSum += colorImage[row + 1][width].getGreen();
                        blueSum += colorImage[row + 1][width].getBlue();
                    }
                    if (!heightMin) {
                        if (!widthMin) {
                            count++;
                            redSum += colorImage[row - 1][width - 1].getRed();
                            greenSum += colorImage[row - 1][width - 1].getGreen();
                            blueSum += colorImage[row - 1][width - 1].getBlue();
                        }
                        if (!widthMax) {
                            count++;
                            redSum += colorImage[row - 1][width + 1].getRed();
                            greenSum += colorImage[row - 1][width + 1].getGreen();
                            blueSum += colorImage[row - 1][width + 1].getBlue();
                        }
                        count++;
                        redSum += colorImage[row - 1][width].getRed();
                        greenSum += colorImage[row - 1][width].getGreen();
                        blueSum += colorImage[row - 1][width].getBlue();
                    }
                    if (!widthMin) {
                        count++;
                        redSum += colorImage[row][width - 1].getRed();
                        greenSum += colorImage[row][width - 1].getGreen();
                        blueSum += colorImage[row][width - 1].getBlue();
                    }
                    if (!widthMax) {
                        count++;
                        redSum += colorImage[row][width + 1].getRed();
                        greenSum += colorImage[row][width + 1].getGreen();
                        blueSum += colorImage[row][width + 1].getBlue();
                    }

                    int redAvg = (int) redSum / count;
                    int greenAvg = (int) greenSum / count;
                    int blueAvg = (int) blueSum / count;
                    int weightedRed = (int) ((colorImage[row][width].getRed() + (redAvg * f)) / (1.0 + f));
                    int weightedGreen = (int) ((colorImage[row][width].getGreen() + (greenAvg * f)) / (1.0 + f));
                    int weightedBlue = (int) ((colorImage[row][width].getBlue() + (blueAvg * f)) / (1.0 + f));
                    if (!heightMax) {
                        if (!widthMin) {
                            newImage[row + 1][width - 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                        }
                        if (!widthMax) {
                            newImage[row + 1][width + 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                        }
                        newImage[row + 1][width].setRGB(weightedRed, weightedGreen, weightedBlue);
                    }
                    if (!heightMin) {
                        if (!widthMin) {
                            newImage[row - 1][width - 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                        }
                        if (!widthMax) {
                            newImage[row - 1][width + 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                        }
                        newImage[row - 1][width].setRGB(weightedRed, weightedGreen, weightedBlue);
                    }
                    if (!widthMin) {
                        newImage[row][width - 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                    }
                    if (!widthMax) {
                        newImage[row][width + 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                    }
                    newImage[row][width].setRGB(weightedRed, weightedGreen, weightedBlue);
                }
            }
        }
        if (directionOfShift==DOWN_RIGHT){
            //see UP_LEFT comments, direction of traversal just changed
            for (int row = getHeight()-1; row >=0; row--) {
                boolean heightMax = row == getHeight() - 1;
                boolean heightMin = row == 0;
                for (int width = getWidth()-1; width >=0; width--) {
                    boolean widthMax = width == getWidth() - 1;
                    boolean widthMin = width == 0;
                    int count = 0;
                    int redSum = 0;
                    int greenSum = 0;
                    int blueSum = 0;

                    if (!heightMax) {
                        if (!widthMin) {
                            count++;
                            redSum += colorImage[row + 1][width - 1].getRed();
                            greenSum += colorImage[row + 1][width - 1].getGreen();
                            blueSum += colorImage[row + 1][width - 1].getBlue();
                        }
                        if (!widthMax) {
                            count++;
                            redSum += colorImage[row + 1][width + 1].getRed();
                            greenSum += colorImage[row + 1][width + 1].getGreen();
                            blueSum += colorImage[row + 1][width + 1].getBlue();
                        }
                        count++;
                        redSum += colorImage[row + 1][width].getRed();
                        greenSum += colorImage[row + 1][width].getGreen();
                        blueSum += colorImage[row + 1][width].getBlue();
                    }
                    if (!heightMin) {
                        if (!widthMin) {
                            count++;
                            redSum += colorImage[row - 1][width - 1].getRed();
                            greenSum += colorImage[row - 1][width - 1].getGreen();
                            blueSum += colorImage[row - 1][width - 1].getBlue();
                        }
                        if (!widthMax) {
                            count++;
                            redSum += colorImage[row - 1][width + 1].getRed();
                            greenSum += colorImage[row - 1][width + 1].getGreen();
                            blueSum += colorImage[row - 1][width + 1].getBlue();
                        }
                        count++;
                        redSum += colorImage[row - 1][width].getRed();
                        greenSum += colorImage[row - 1][width].getGreen();
                        blueSum += colorImage[row - 1][width].getBlue();
                    }
                    if (!widthMin) {
                        count++;
                        redSum += colorImage[row][width - 1].getRed();
                        greenSum += colorImage[row][width - 1].getGreen();
                        blueSum += colorImage[row][width - 1].getBlue();
                    }
                    if (!widthMax) {
                        count++;
                        redSum += colorImage[row][width + 1].getRed();
                        greenSum += colorImage[row][width + 1].getGreen();
                        blueSum += colorImage[row][width + 1].getBlue();
                    }

                    int redAvg = (int) redSum / count;
                    int greenAvg = (int) greenSum / count;
                    int blueAvg = (int) blueSum / count;
                    int weightedRed = (int) ((colorImage[row][width].getRed() + (redAvg * f)) / (1.0 + f));
                    int weightedGreen = (int) ((colorImage[row][width].getGreen() + (greenAvg * f)) / (1.0 + f));
                    int weightedBlue = (int) ((colorImage[row][width].getBlue() + (blueAvg * f)) / (1.0 + f));
                    if (!heightMax) {
                        if (!widthMin) {
                            newImage[row + 1][width - 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                        }
                        if (!widthMax) {
                            newImage[row + 1][width + 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                        }
                        newImage[row + 1][width].setRGB(weightedRed, weightedGreen, weightedBlue);
                    }
                    if (!heightMin) {
                        if (!widthMin) {
                            newImage[row - 1][width - 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                        }
                        if (!widthMax) {
                            newImage[row - 1][width + 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                        }
                        newImage[row - 1][width].setRGB(weightedRed, weightedGreen, weightedBlue);
                    }
                    if (!widthMin) {
                        newImage[row][width - 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                    }
                    if (!widthMax) {
                        newImage[row][width + 1].setRGB(weightedRed, weightedGreen, weightedBlue);
                    }
                    newImage[row][width].setRGB(weightedRed, weightedGreen, weightedBlue);
                }
            }
        }

    }

    //Image Modification Filters
    public ColorImage toRotate90Counter() {
        //Start at the far right column of our new image
        int newWidth = getHeight() - 1;
        //create newimage with flipped dimensions
        Color[][] newImage = new Color[getWidth()][getHeight()];
        //Remember that row 0 is the bottom of the image (from a visual perspective, as the entire thing is flipped).
        for (/*Start normal height at top of the image*/int normalHeight = getHeight() - 1; /*run towards bottom of the image*/normalHeight >= 0; normalHeight--, /*move to the left along the width of the newimage*/newWidth--) {
            //newimage height index starting from top of the newimage
            int newHeight = getWidth() - 1;
            for (int normalWidth = 0; normalWidth < getWidth();/*move right along the image*/ normalWidth++, newHeight--/*move down from the top of the newimage*/) {
                /*copy color at normal position in image to rotated position in newimage*/
                newImage[newHeight][newWidth] = colorImage[normalHeight][normalWidth];
            }
        }
        ColorImage forReturn = new ColorImage(newImage);
        return forReturn;
    }
    public ColorImage toRotate90Clock(){
        //Start at the far left column of our newimage
        int newWidth = 0;
        //create newimage with flipped dimensions
        Color[][] newImage = new Color[getWidth()][getHeight()];
        //Remember that row 0 is the bottom of the image (from a visual perspective, as the entire thing is flipped).
        for (/*Start normal height at top of the image*/int normalHeight = getHeight() - 1; /*run towards bottom of the image*/normalHeight >= 0; normalHeight--,/*move to the right along the width of the newimage*/ newWidth++) {
            //newimage height index starting from bottom of the newimage
            int newHeight = 0;
            for (int normalWidth = 0; normalWidth < getWidth(); normalWidth++, newHeight++) {
                /*copy color at normal position in image to rotated position in newimage*/
                newImage[newHeight][newWidth] = colorImage[normalHeight][normalWidth];
            }
        }
        ColorImage forReturn = new ColorImage(newImage);
        return forReturn;
    }
    public ColorImage toCrop(int topCut, int bottomCut, int rightCut, int leftCut){
        //Method creates newImage with the topcut and bottomcut subtracted from its height, and the rightcut and leftcut subtracted from its width
        //Runs through only the portion in-between cuts and places them into new image.

        //start is left, end is right
        Color[][] newImage = new Color[getHeight()-topCut-bottomCut][getWidth()-leftCut-rightCut];
        //inclusive
        //where we start in the cut image
        int newRow = 0;
        for (/*where we start in the actual image*/int row = bottomCut; /*where we end in the cut image*/row < getHeight()-topCut; row++, newRow++) {
            //where we start in the cut image
            int newWidth = 0;
            for (/*where we start in the actual image*/int width = leftCut;/*where we end in the cut image*/ width < getWidth()-rightCut; width++,newWidth++) {
                newImage[newRow][newWidth]=colorImage[row][width];
            }
        }
        checkNull(newImage);
        return new ColorImage(newImage);
    }
    public ColorImage rightFlip(){
        //create new color array of same dimensions
        Color[][] newImage = new Color[getHeight()][getWidth()];
        //traverse current image
        for (int height = 0; height < getHeight(); height++) {
            for (int width = 0; width < (getWidth()/2)+1; width++) {
                //swap colors on right side of the image with colors on the left side, flipping image.
                Color temp = colorImage[height][getWidth()-1-width];
                newImage[height][getWidth()-1-width] = colorImage[height][width];
                newImage[height][width] = temp;
            }
        }
        ColorImage forReturn = new ColorImage(newImage);
        return forReturn;
    }
    public ColorImage toLeftRightMirror(){
        //create new color array of same dimensions
        Color[][] newImage = new Color[getHeight()][getWidth()];
        //traverse current image
        for (int height = 0; height < getHeight(); height++) {
            for (int width = 0; width < (getWidth()/2)+1; width++) {
                //Places colors on the left side of the image onto the reflected
                //position on the right side of the new image
                newImage[height][getWidth()-1-width] = colorImage[height][width];
                //place colors on the left side off the image into their correct position in the
                //new image
                newImage[height][width] = colorImage[height][width];
            }
        }
        //Create colorimage for return from the modified color array
        ColorImage forReturn = new ColorImage(newImage);
        return forReturn;
    }
    public ColorImage toRightLeftMirror(){
        //see toLeftRightMirror comments, direction of traversal just changed
        Color[][] newImage = new Color[getHeight()][getWidth()];
        for (int height = 0; height < getHeight(); height++) {

            for (int width = getWidth()-1; width >(getWidth()/2)-1; width--) {
                newImage[height][getWidth()-1-width] = colorImage[height][width];
                newImage[height][width] = colorImage[height][width];
            }
        }

        ColorImage forReturn = new ColorImage(newImage);
        return forReturn;
    }
    public ColorImage toTopDownMirror(){
        //see toLeftRightMirror comments, direction of traversal just changed
        Color[][] newImage = new Color[getHeight()][getWidth()];
        for (int height = getHeight()-1; height > (getHeight()/2)-1; height--) {

            for (int width = 0; width <getWidth(); width++) {
                newImage[getHeight()-1-height][width] = colorImage[height][width];
                newImage[height][width] = colorImage[height][width];
            }
        }
        checkNull(newImage);
        ColorImage forReturn = new ColorImage(newImage);
        return forReturn;
    }
    public ColorImage toDownTopMirror(){
        //see toLeftRightMirror comments, direction of traversal just changed
        Color[][] newImage = new Color[getHeight()][getWidth()];
        for (int height = 0; height <(getHeight()/2) ; height++) {

            for (int width = 0; width <getWidth(); width++) {
                newImage[getHeight()-1-height][width] = colorImage[height][width];
                newImage[height][width] = colorImage[height][width];
            }
        }
        checkNull(newImage);
        ColorImage forReturn = new ColorImage(newImage);
        return forReturn;
    }
}
