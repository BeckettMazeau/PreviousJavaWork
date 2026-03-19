//Setup
package ProgrammingChallenges2;

import java.util.Scanner;
//Main Code
public class Problem5 {
    public static void main( String[] args){
        /*
        use .lenth command to check length of max
        subtract length from number of spaces (based on max)
         */
        /* Setup Scanner */
        Scanner num_in = new Scanner(System.in);

        System.out.println("Enter Integer:");
        //Grab num
        int number = num_in.nextInt();
        //Protection Circuit
        while (number>= 45825) {

                System.out.println("Enter Valid Integer:");
                //Grab num
                number = num_in.nextInt();

        }
        //Set max number to square of number
        final int maxnum = (int) (Math.pow(number,2));
        System.out.println("Maximum Value of Table: " + maxnum);
        //Set temporary variable to string of maxnum
        String temp = Integer.toString(maxnum);
        //set maxspaces to length of temp/ # of digits of maxnum -1
        final int maxspaces = (temp.length());
        System.out.println("Maximum Number of Digits: " + (maxspaces));
        System.out.println("Maximum Number of Spaces: " + maxspaces);
        //Main for loop
        for (int i = 1; i <= number; i++) {
            //print i
            for (int j = 1; j <= number; j++) {
                //Number of spaces to print
                int spaces = (maxspaces -  (Integer.toString((i*j))).length());
                //Print spaces
                for (int k = 0; k <= spaces ; k++) {
                    System.out.print(" ");
                }
                //Print number
                System.out.print(i*j);

            }
            //Next line
            System.out.println("");
        }

    }
}
