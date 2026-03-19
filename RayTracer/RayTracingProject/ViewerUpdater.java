import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.Image;

/**
 * Runnable method to take care of updating and managing render of the image preview on a seprate thread from main
 * to allow the main thread to continue working whilst the image is updated.
 *
 * @author beckett mazeau
 * @version 5/17/24
 */

public class ViewerUpdater implements Runnable {
    // instance variables - variables nessecary to execute task
    private JPanel viewerPanel;
    private CreatedScene currentScene;
    private JLabel mainpreview;

    /**
     * Constructor for objects of class ViewerUpdater
     */
    /*passed in objects:
    * viewer panel - used to tell when to stop rendering (isEnabled)
    * currentScene - scene to render, as well as provides settings across classes
    * mainpreview - display to update*/
    public ViewerUpdater(JPanel viewerPanel, CreatedScene currentScene, JLabel mainPreview) {
        this.viewerPanel = viewerPanel;
        this.mainpreview = mainPreview;
        this.currentScene = currentScene;
    }

    //method to be run on another thread
    @Override
    public void run() {
        boolean check = true;
        //to handle interruptedthread exception
        try {
            while (check) {
                //these images need to be made quickly, they are only a preview, so a fixed resolution of 720 wiht
                //3 samples was chosen. The image isn't pretty, but it updates fast and allows you to arrange the scene.
                int xRes = 720;
                double aspect = (double) (currentScene.getSettings().get("aspect"));
                //calculating yres based on xres and aspect ratio
                int yRes = (int) (xRes / aspect);
                int numSamples = 3;
                //using multithreaded render from sceneclass
                ColorImage image = ((Scene) currentScene.getScene()).renderMultiThread(xRes, yRes, true, numSamples);
                //converting colorimage to buffered image
                BufferedImage bufferedImage = image.toBufferedImage();
                //storing image as icon and setting preview
                Icon icon = new ImageIcon((Image) bufferedImage);
                mainpreview.setIcon(icon);
                mainpreview.revalidate();
                try {
                    //waiting
                    Thread.sleep(200);
                } catch (Exception fdas) {
                }
                //updating check
                check = viewerPanel.isEnabled();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
