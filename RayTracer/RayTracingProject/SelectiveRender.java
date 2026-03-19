import javax.swing.*;

/**
 * Write a description of class SelectiveRender here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SelectiveRender implements Runnable {
    //This is our runnable class. It will run on our threads. Because we are in a completely seperate class, we need to pass in anything we need to use for the render, and use aliasing to pass things back out
    //instance variables
    private boolean aliasing;
    private int aliasingRes;
    private ColorImage sceneImage;
    //inclusive
    private int minBound;
    //exclusive
    private int maxBound;
    private Scene scene;
    //constructor to initialize instance variables
    public SelectiveRender(ColorImage sceneImage, Scene scene, int minBound, int maxBound, boolean aliasing, int aliasingRes) {
        this.aliasing = aliasing;
        this.aliasingRes = aliasingRes;
        this.sceneImage = sceneImage;
        this.minBound = minBound;
        this.maxBound = maxBound;
        this.scene = scene;
    }

    @Override
    public void run() {
        try {

            //see Scene class for explanation of render method, only difference here is we only render the x's within our defined bounds
            if (aliasing) {
                for (int x = minBound; x < maxBound; x++) {

                    for (int y = 0; y < sceneImage.getHeight(); y++) {
                        Color aColor = new Color(0, 0, 0);
                        for (int i = 0; i < aliasingRes; i++) {
                            for (int j = 0; j < aliasingRes; j++) {
                                double u = ((x + ((i + 0.5) / aliasingRes)) / sceneImage.getWidth());
                                double v = ((y + ((j + 0.5) / aliasingRes)) / sceneImage.getHeight());
                                Ray currentRay = scene.getCamera().generateRay(u, v);
                                aColor = aColor.add(scene.computeVisibleColor(currentRay, 5));
                            }
                        }
                        aColor = aColor.scale(1.0 / (Math.pow(aliasingRes, 2)));
                        sceneImage.setColor(x, y, aColor);

                    }
                }
            } else {
                for (int x = minBound; x < maxBound; x++) {

                    for (int y = 0; y < sceneImage.getHeight(); y++) {
                        Color aColor = new Color(0, 0, 0);
                        double u = (x + 0.5) / sceneImage.getWidth();
                        double v = (y + 0.5) / sceneImage.getHeight();
                        Ray currentRay = scene.getCamera().generateRay(u, v);
                        sceneImage.setColor(x, y, scene.computeVisibleColor(currentRay, 5));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
