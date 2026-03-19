package DogClassProject;

public class DogDriver {
    public static void main(String [] args) throws Exception{
        Dog rex = new Dog("Rex");
        Dog eebee = new Dog("Prince Eebee III");
        System.out.println(rex.getAge());
        System.out.println(eebee.getAge());
        rex.birthday();
        rex.birthday();
        rex.birthday();
        System.out.println(rex.getAge());
        eebee.bark();
        rex.bark();

    }
}