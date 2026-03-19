
/**
 * Simple class for interaction and storage of shapes.
 *
 * @author beckett mazeau
 * @version 5/17/24
 */

public class CreatedShape {
    //constructors + initialize instance variables
    private String name;
    private Surface shape;
    private String type;

    public CreatedShape(String name, Surface shape, String type) {
        this.name = name;
        this.shape = shape;
        this.type = type;
    }

    //getters for use in populating update section of creator panel as well as other GUI functions such as checking type etc.
    public String getType() {
        return type;
    }

    public Surface getShape() {
        return shape;
    }

    public String getName() {
        return name;
    }

    public void setShape(Surface shape) {
        this.shape = shape;
    }

    public void setName(String name) {
        this.name = name;
    }
}
