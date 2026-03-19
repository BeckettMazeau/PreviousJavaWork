public class Horse extends Animal{
    private int numberOfWins;
    public Horse(String name, String color, int age, String breed, int numwins){
        super(name,color,age, breed); //super says go do the parent's class first; it MUST COME FIRST
        numberOfWins = numwins;
    }
    public void speak(){
        System.out.println("neigh");
    }

}