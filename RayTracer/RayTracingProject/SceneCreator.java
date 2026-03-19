
/**
 * Details the static methods to creating various scenes for use in the raytracer.
 * scene1() is included as an example. You can add more static methods (for example scene2(),
 * scene3(), etc.) to create different scenes without affecting scene1.
 *
 * @author Ben Farrar
 * @version 2019.05.22
 */




//             !!!!!!!!!!!!!!!!!!!!!!DEPRECIATED !!!!!!!!!!!!!!!!!!!!!!




public class SceneCreator {
    public static Scene scene1(double xResolution, double yResolution) {
        Camera cam = new Camera(new Point(0, 0, 0),       // camera location
                new Vector(0, 0, -1),     // forward vector/view direction
                new Vector(0, 1, 0),      // up vector
                20,                     // field of view
                xResolution / yResolution); // aspect ratio
        Scene s = new Scene(cam);

        //Each sphere takes a Point (its center), the radius, and a material.
        //For now, since we have not implemented the Material classes, we simply say they are null.
        Surface s1 = new Sphere(new Point(0, 0, -20), 3, null);
        s.addSurface(s1);
        Surface s2 = new Sphere(new Point(0, 4, -15), 1, null);
        s.addSurface(s2);
        Surface s3 = new Sphere(new Point(5, 0, -20), 1.5, null);
        s.addSurface(s3);
        //Each triangle takes 3 Points (its vertexes), and a material.
        Surface t1 = new Triangle(new Point(-3.5, -1, -15), new Point(-3.5, 1, -15), new Point(-5, 0, -16), null);
        s.addSurface(t1);
        Surface floor = new Triangle(new Point(0, -5, 0), new Point(3000, -5, -1000), new Point(-3000, -5, -1000), null);
        s.addSurface(floor);
        return s;
    }

    public static Scene scene2(double xResolution, double yResolution) {
        Camera cam = new Camera(new Point(0, 0, 0),
                new Vector(0, 0, -1),     // forward vector/view direction
                new Vector(0, 1, 0),      // up vector
                20,                     // field of view
                xResolution / yResolution
        );
        Scene s = new Scene(cam);
        Color blue = new Color(0, 0, 1);
        Color green = new Color(0, 1, 0);
        Color red = new Color(1, 0, 0);
        Lambert aBlueMaterial = new Lambert(blue);
        Lambert aGreenMaterial = new Lambert(green);
        Lambert aRedMaterial = new Lambert(red);
        PointLight aRedLight = new PointLight(red, new Point(5, 9, -12));
        PointLight aGreenLight = new PointLight(green, new Point(1, 1, -1));
        PointLight aBlueLight = new PointLight(blue, new Point(2, 5, -10));
        Surface s1 = new Sphere(new Point(3, 1, -17), 3, aBlueMaterial);
        s.addSurface(s1);
        Surface s2 = new Sphere(new Point(-1, 1, -5), 1, aGreenMaterial);
        s.addSurface(s2);
        Surface s3 = new Sphere(new Point(5, 1, -10), 1.5, aRedMaterial);
        s.addSurface(s3);
        //Each triangle takes 3 Points (its vertexes), and a material.
        Surface t1 = new Triangle(new Point(-3.5, 0.2, -1), new Point(-2, 0.2, -1), new Point(-0.75, 0.5, -1), aRedMaterial);
        s.addSurface(t1);
        Surface floor = new Triangle(new Point(0, -10, 0), new Point(3000, -10, -1000), new Point(-3000, -11, -1000), new Lambert(new Color(0.5, 0.5, 0.5)));
        s.addSurface(floor);
        s.addLight(aBlueLight);
        s.addLight(aGreenLight);
        s.addLight(aRedLight);
        return s;
    }

    public static Scene scene3(double xResolution, double yResolution) {
        Camera cam = new Camera(new Point(0, 0, 0),
                new Vector(0, 0, -1),     // forward vector/view direction
                new Vector(0, 1, 0),      // up vector
                50,                     // field of view
                xResolution / yResolution
        );
        Scene s = new Scene(cam);
        Color blue = new Color(0, 0, 1);
        Color green = new Color(0, 1, 0);
        Color red = new Color(1, 0, 0);
        Lambert aBlueMaterial = new Lambert(blue);
        Lambert aGreenMaterial = new Lambert(green);
        Lambert aRedMaterial = new Lambert(red);
        MirrorPhong aMirror = new MirrorPhong(new Color(0.07, 0.07, 0.07), new Color(1, 1, 1), 100, 0.9);
        PointLight aRedLight = new PointLight(red, new Point(5, 9, -12));
        PointLight aGreenLight = new PointLight(green, new Point(1, 1, -1));
        PointLight aBlueLight = new PointLight(blue, new Point(2, 5, -10));
        PointLight aWhiteLight = new PointLight(new Color(1, 1, 1), new Point(4, 19, 3));
        Surface s1 = new Sphere(new Point(3, 1, 5), 1, aBlueMaterial);
        s.addSurface(s1);
        Surface s2 = new Sphere(new Point(-1, 0.2, -5), 2, aMirror);
        s.addSurface(s2);
        Surface s3 = new Sphere(new Point(5.3, 1, -1), 1.5, aRedMaterial);
        s.addSurface(s3);
        //Each triangle takes 3 Points (its vertexes), and a material.
        Surface t1 = new Triangle(new Point(-5, 20, -4), new Point(-4, -3, -5), new Point(1, 2, -7), aMirror);
        //s.addSurface(t1);
        Surface floor1 = new Triangle(new Point(-4000, -10, 0), new Point(-4000, -10, -4000), new Point(4000, -10, -4000), new Lambert(new Color(0, (154.0 / 255), (23.0 / 255))));
        Surface floor2 = new Triangle(new Point(-4000, -10, 0), new Point(4000, -10, 0), new Point(4000, -10, -4000), new Lambert(new Color(0, (154.0 / 255), (23.0 / 255))));
        Surface floor3 = new Triangle(new Point(-4000, -10, 0), new Point(-4000, -10, 4000), new Point(4000, -10, 4000), new Lambert(new Color(0, (154.0 / 255), (23.0 / 255))));
        Surface floor4 = new Triangle(new Point(-4000, -10, 0), new Point(4000, -10, 0), new Point(4000, -10, 4000), new Lambert(new Color(0, (154.0 / 255), (23.0 / 255))));
        s.addSurface(floor1);
        s.addSurface(floor2);
        s.addSurface(floor3);
        s.addSurface(floor4);
        s.addLight(aBlueLight);
        s.addLight(aRedLight);
        s.addLight(aGreenLight);
        s.addLight(aWhiteLight);
        return s;
    }

    public static Scene scene4(double xResolution, double yResolution) {
        Camera cam = new Camera(new Point(0, 0, 0),
                new Vector(0, 0, -1),     // forward vector/view direction
                new Vector(0, 1, 0),      // up vector
                50,                     // field of view
                xResolution / yResolution
        );
        Scene s = new Scene(cam);
        Color blue = new Color(0, 0, 0.7);
        Color green = new Color(0, 0.7, 0);
        Color red = new Color(0.7, 0, 0);
        Lambert aBlueMaterial = new Lambert(blue);
        Lambert aGreenMaterial = new Lambert(green);
        Lambert aRedMaterial = new Lambert(red);
        MirrorPhong aMirror = new MirrorPhong(new Color(0.07, 0.07, 0.07), new Color(1, 1, 1), 100, 0.9);
        MirrorPhong aShiny = new MirrorPhong(new Color(0.5, 0.079, 0.5), new Color(1, 1, 1), 30, 0.4);
        Phong aPhongy = new Phong(new Color(0.3, 0.8, 0.1), new Color(0.7, 0.7, 0.7), 20);
        PointLight aRedLight = new PointLight(red, new Point(5, 9, -12));
        PointLight aGreenLight = new PointLight(green, new Point(1, 1, -1));
        PointLight aBlueLight = new PointLight(blue, new Point(2, 5, -10));
        PointLight aWhiteLight = new PointLight(new Color(1, 1, 1), new Point(4, 19, 3));
        Surface s1 = new Sphere(new Point(3, 1, 5), 1, aBlueMaterial);
        s.addSurface(s1);
        Surface s3 = new Sphere(new Point(5.3, 1, -1), 1.5, aRedMaterial);
        s.addSurface(s3);
        Surface s4 = new Sphere(new Point(0, 0, -20), 3, aShiny);
        s.addSurface(s4);
        Surface s5 = new Sphere(new Point(0, 4, -15), 1, aPhongy);
        s.addSurface(s5);
        Surface s6 = new Sphere(new Point(5, 0, -20), 1.5, aGreenMaterial);
        s.addSurface(s6);
        //Each triangle takes 3 Points (its vertexes), and a material.
        Surface t1 = new Triangle(new Point(-5, 20, -4), new Point(-4, -3, -5), new Point(1, 2, -7), aMirror);
        //s.addSurface(t1);
        Surface floor1 = new Triangle(new Point(-4000, -10, 0), new Point(-4000, -10, -4000), new Point(4000, -10, -4000), new Lambert(new Color(0.07, 0.07, 0.07)));
        Surface floor2 = new Triangle(new Point(-4000, -10, 0), new Point(4000, -10, 0), new Point(4000, -10, -4000), new Lambert(new Color(0.07, 0.07, 0.07)));
        Surface floor3 = new Triangle(new Point(-4000, -10, 0), new Point(-4000, -10, 4000), new Point(4000, -10, 4000), new Lambert(new Color(0.07, 0.07, 0.07)));
        Surface floor4 = new Triangle(new Point(-4000, -10, 0), new Point(4000, -10, 0), new Point(4000, -10, 4000), new Lambert(new Color(0.07, 0.07, 0.07)));
        s.addSurface(floor1);
        s.addSurface(floor2);
        s.addSurface(floor3);
        s.addSurface(floor4);
        s.addLight(aBlueLight);
        s.addLight(aRedLight);
        s.addLight(aGreenLight);
        s.addLight(aWhiteLight);
        return s;
    }
}