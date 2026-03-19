public class Dog extends Animal{
    private int numberOfWins;
    public Dog(String name, String color, int age, String breed){
        super(name,color,age, breed); //super says go do the parent's class first; it MUST COME FIRST
    }
    //override of parent speak method (@override operator???? sick)
    public void speak(){
        System.out.println("bark");
    }
}