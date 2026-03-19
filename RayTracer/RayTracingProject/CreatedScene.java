import java.util.ArrayList;
import java.util.HashMap;

/**
 * Purpose of created scene is to allow easier access to important scene attributes, as well as allow for easier, and safer, interactions
 * with the scene class.
 *
 * @author Beckett Mazeau
 * @version 5/15/24
 */
public class CreatedScene {
    //Initializing instance variables
    private ArrayList<CreatedShape> createdShapes = new ArrayList<CreatedShape>();
    private ArrayList<CreatedPointLight> createdLights = new ArrayList<CreatedPointLight>();
    private Scene scene;
    private String sceneName;
    private HashMap settings;

    //initial constructors. takes in or creates a hashmap of the scenes settings for later retrieval and use (such as when rendering
    //or populating settings and objects for a "selected" scene
    public CreatedScene(Scene scene, String sceneName, HashMap settings) {
        this.scene = scene;
        this.sceneName = sceneName;
        this.settings = settings;
    }

    public CreatedScene(Scene scene, String sceneName, double FoV, double aspect, int xRes, boolean aliasing, int numSamples, boolean multiThread) {
        this.scene = scene;
        this.sceneName = sceneName;
        settings = new HashMap<>();
        settings.put("FoV", FoV);
        settings.put("xRes", xRes);
        settings.put("aspect", aspect);
        settings.put("aliasing", aliasing);
        settings.put("numSamples", numSamples);
        settings.put("multiThread", multiThread);
    }

    //Basic getters and setters
    public HashMap getSettings() {
        return settings;
    }

    public Scene getScene() {
        return scene;
    }

    public ArrayList<CreatedShape> getCreatedShapes() {
        return createdShapes;
    }

    public ArrayList<CreatedPointLight> getCreatedLights() {
        return createdLights;
    }
    public String getSceneName() {
        return sceneName;
    }
    //Ability to run through and get objects by string name, for compatibility with selectors
    public CreatedShape getCreatedShape(String name) {
        for (CreatedShape createdShape : createdShapes) {
            if (createdShape.getName().equals(name)) {
                return createdShape;
            }
        }
        return null;
    }



    public CreatedPointLight getCreatedLight(String name) {
        for (CreatedPointLight createdPointLight : createdLights) {
            if (createdPointLight.getName().equals(name)) {
                return createdPointLight;
            }
        }
        return null;
    }

    public void setCreatedShapes(ArrayList<CreatedShape> createdShapes) {
        this.createdShapes = createdShapes;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public void setSettings(HashMap settings) {
        this.settings = settings;
        scene.getCamera().setFoV((int) settings.get("FoV"), (double) settings.get("aspect"));
    }

    public void setFoV(double foV) {
        settings.put("FoV", foV);
    }

    public void setAspect(double aspect) {
        settings.put("aspect", aspect);
    }

    public void setAliasingMethod(boolean aliasing) {
        settings.put("aliasingMethod", aliasing);
    }

    public void setNumSamples(int numSamples) {
        settings.put("numSamples", numSamples);
    }

    public void setMultiThread(boolean multiThread) {
        settings.put("multiThread", multiThread);
    }

    public void setCreatedLights(ArrayList<CreatedPointLight> createdLights) {
        this.createdLights = createdLights;
    }


    // Update, remove, and add objects
    public void addCreatedShape(CreatedShape createdShape) {
        createdShapes.add(createdShape);
        scene.addSurface(createdShape.getShape());
    }

    public void addCreatedLight(CreatedPointLight createdLight) {
        createdLights.add(createdLight);
        scene.addLight(createdLight.getLight());
    }

    //Name based for easier interaction with JComboBoxes
    public void updateCreatedShape(String shapeName, CreatedShape newShape) {
        //not removing during iteration to handle concurrent modification exception
        /*int index = Integer.MAX_VALUE;
        for (CreatedShape shape :
                createdShapes) {
            if (shape.getName().equals(shapeName)) {
                index = createdShapes.indexOf(shape);

            }
        }
        if (index == Integer.MAX_VALUE) {
            return;}
        createdShapes.set(index, newShape);*/
        removeCreatedShape(shapeName);
        addCreatedShape(newShape);
    }

    public void removeCreatedShape(String shapeName) {
        ArrayList<CreatedShape> toRemove = new ArrayList<CreatedShape>();
        ArrayList<Surface> shapes = new ArrayList<Surface>();
        for (CreatedShape shape :
                createdShapes) {
            if (shape.getName().equals(shapeName)) {
                toRemove.add(shape);
                shapes.add(shape.getShape());
            }
        }
        if (toRemove.size() == 0) {return;}
        createdShapes.removeAll(toRemove);
        scene.removeSurfaceAll(shapes);

    }

    public void removeCreatedLight(String lightName) {
        ArrayList<CreatedPointLight> toRemove = new ArrayList<CreatedPointLight>();
        ArrayList<PointLight> lightsToRemove = new ArrayList<PointLight>();
        for (CreatedPointLight light :
                createdLights) {
            if (light.getName().equals(lightName)) {
                toRemove.add(light);
                lightsToRemove.add(light.getLight());
            }
        }
        if (toRemove.size() == 0) {return;}
        createdLights.removeAll(toRemove);
        scene.removeLightAll(lightsToRemove);
    }

    public void updateCreatedLight(String lightName, CreatedPointLight updatedLight) {
        //not altering during modification to avoid concurrent modification exception
        /*int index = Integer.MAX_VALUE;
        for (CreatedPointLight light :
                createdLights) {
            if (light.getName().equals(lightName)) {
                index = createdLights.indexOf(light);

            }
        }
        if (index == Integer.MAX_VALUE) {return;}
        createdLights.set(index, updatedLight);
        scene.removeLight();*/
        removeCreatedLight(lightName);
        addCreatedLight(updatedLight);
    }


    //Simple methods allowing easier access to camera movements for GUI
    public void moveForward() {
        scene.getCamera().moveForward();
    }

    public void moveBackward() {
        scene.getCamera().moveBackward();
    }

    public void moveRight() {
        scene.getCamera().moveRight();
    }

    public void moveLeft() {
        scene.getCamera().moveLeft();
    }

    public void moveUp() {
        scene.getCamera().moveUp();
    }

    public void moveDown() {
        scene.getCamera().moveDown();
    }

    public void lookUp() {
        scene.getCamera().lookUp();
    }

    public void lookDown() {
        scene.getCamera().lookDown();
    }

    public void lookLeft() {
        scene.getCamera().lookLeft();
    }

    public void lookRight() {
        scene.getCamera().lookRight();
    }


}
