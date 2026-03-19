package PhotoshopProject;

public class image {
    Cooolor[][] image;
    public image (int row, int col){
        image = new Cooolor[row][col];

    }

    public void setImage(Cooolor[][] image) {
        this.image = image;
    }

    public Cooolor[][] getImage() {
        return image;
    }
    public void fillImage(){
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[row].length; col++) {
                image[row][col].randomizeRGB();
            }

        }
    }
    public void setPixel(int row, int col, Cooolor value){
        image[row][col] = value;
    }
    public void setPixelRed(int row, int col, int value){
        image[row][col].setRed(value);
    }
    public void setPixelGreen(int row, int col, int value){
        image[row][col].setGreen(value);
    }
    public void setPixelBlue(int row, int col, int value){
        image[row][col].setBlue(value);
    }
}
