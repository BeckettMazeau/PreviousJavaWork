import java.util.Scanner;
public class Notes10_3_2023{
    public static void main(String[] args){
                Scanner george;
        george = new Scanner(System.in);
            /* System.in refers to keyboard here. We are declaring a NEW SCANNER OBJECT. Before this second line george isn't anything at all.
            You can also do it condensed like this: */
        Scanner james = new Scanner(System.in);

        System.out.println("What is your name?");
        String name = george.nextLine();
        
        String age = george.nextLine();
        int actualAge = Integer.parseInt(age);
        
        System.out.println("You are " + actualAge);



    }
}