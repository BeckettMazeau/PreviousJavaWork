public abstract class Animal{
    private String name;
    private int age;
    private String breed;
    private String color;
    public Animal(String name, String color, int age, String breed){
        this.age = age;
        this.breed = breed;
        this.name = name;
        this.color = color;
    }
    public String getName(){
        return name;
    }

    public abstract void speak();
}