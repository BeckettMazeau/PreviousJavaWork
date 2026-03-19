
/**
 * Purpose of createdpointlight class is to allow easier access to important light attributes, as well as allow for easier, and safer, interactions
 * with the pointlight class.
 *
 * @author Beckett Mazeau
 * @version 5/15/24
 */
public class CreatedPointLight {
    private String name;
    private PointLight light;
    //constructor
    public CreatedPointLight(String name, PointLight light) {
        this.name = name;
        this.light = light;
    }
    //getters for use in populating update section of creator panel as well as other GUI functions such as checking type etc.
    public PointLight getLight() {
        return light;
    }

    public String getType() {
        return "PointLight";
    }

    public String getName() {
        return name;
    }

    public void setLight(PointLight light) {
        this.light = light;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getIntensity() {
        return light.getLightColor();
    }

    public Point getPosition() {
        return light.getPosition();
    }
}
