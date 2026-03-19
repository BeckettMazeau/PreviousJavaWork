package Gradebook2;
import java.util.Scanner;

public class GradebookDriver {
    public static void main(String[] args) throws Exception{
        Scanner grabInput = new Scanner(System.in);
        Gradebook primaryBook = new Gradebook();
        int temp = 0;
        String input = "";
        while(true){
            System.out.println("Enter next score, or type -1 to exit");
            temp = grabInput.nextInt();
            if (temp == -1){
                break;
            } else {
                primaryBook.addScore(temp);
            }
        }
        while (true) {
            System.out.println("Enter command:");
            input = grabInput.nextLine().trim().toLowerCase();
            if(input.contains("exit")){
                break;
            } else if (input.contains("getmax")) {
                System.out.println("Max Score: " + primaryBook.getMaxScore());

            } else if (input.contains("getmin")){
                System.out.println("Min Score: " + primaryBook.getMinScore());
            } else if (input.contains("getavg")) {
                System.out.println("Avg Score: " + primaryBook.getAvgScore());
            } else {
                System.out.println("List of Valid Commands:\n" +
                        "- Get max score: getmax\n" +
                        "- Get min score: getmin\n" +
                        "- Get avg score: getavg\n" +
                        "- Exit program: exit");
            }
        }
    }
}