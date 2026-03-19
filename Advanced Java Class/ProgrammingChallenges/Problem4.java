public class Problem4 {
    public static void main(String[] args){
        //Initialize Variables
        int lines = 30;
        int stars = 1;
        int spaces = lines - stars;
        //Main loop, runs for each line to print characters
        for (int i = 0; i<lines;i++){
            //Prints spaces for each line
            for (int s = 0; s<spaces;s++){
                System.out.print(" ");
            }
            //Prints stars for each line
            for (int j = 0; j<stars;j++){
                System.out.print("*");
            }
            //Change star value, space value, and then print
            stars += 2;
            spaces--;
            System.out.println("");
        }
    }
}