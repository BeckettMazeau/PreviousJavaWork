import javax.swing.*;
import java.awt.*;


//IGNORE, FOR TESTING

public class TestDriver {
    public static void main() {
        Point cameraLocation = new Point(0, 0, 0);
        Vector cameraForward = new Vector(0, 0, -1);
        Vector cameraUp = new Vector(0, 1, 0);
        double FoV = 20;
        double aspectRat = 16.0 / 9.0;
        Camera aCamera = new Camera(cameraLocation, cameraForward, cameraUp, FoV, aspectRat);
        System.out.println(aCamera.generateRay(0.5, 0.5).stringValue());
        System.out.println(aCamera.generateRay(0, 0).stringValue());
        System.out.println(aCamera.generateRay(1, 1).stringValue());

    }

    public static void other() {
        JFrame mainframe = new JFrame();
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(800, 600);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        mainframe.add(panel);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setMinimumSize(new Dimension(200, 100));
        panel.add(progressBar);
        progressBar.setValue(0);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        mainframe.setVisible(true);
        progressBar.setVisible(true);
        for (int i = 0; i < 101; i++) {
            progressBar.setValue(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
