import java.util.ArrayList;

public class Vet{
    public static void main(){
        ArrayList<Animal> patients = new ArrayList<Animal>();

        Horse h1 = new Horse("Seabiscuit","black",5,"black",100);
        Dog d1 = new Dog("brog","grey",1,"terrier");
        patients.add(d1);
        patients.add(h1);
        for (int i = 0; i < patients.size(); i++) {
            Animal current = patients.get(i);
            System.out.println(patients.get(i).getName());
            //this sucks
            /*if (current instanceof Dog){
                ((Dog)current).bark();
            }
            if (current instanceof Horse){
                ((Horse)current).neigh();
            }*/
            //this is better
            current.speak();

        }
    }
    
}