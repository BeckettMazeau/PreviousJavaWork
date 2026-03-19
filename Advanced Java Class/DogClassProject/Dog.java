package DogClassProject;

public class Dog {
    private int age;
    private String name;

    public Dog(String givenName){
        age = 0;
        name = givenName;
    }
    public int getAge(){
        return age;
    }
    public void birthday(){
        age +=1;
    }
    public void bark(){
        System.out.println("Bark noise");
    }
    public String getName(){return name;}
}