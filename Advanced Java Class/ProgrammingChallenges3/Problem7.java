package ProgrammingChallenges3;

import java.util.Scanner;

public class Problem7 {
    public static void main(String[] args){
        //Initialize variables
        long num1 = 0L;
        long num2 = 1L;
        long num3 = 0L;
        Scanner terms = new Scanner(System.in);

        System.out.println("Enter how many terms you want:");
        //Grab num
        int number = terms.nextInt();
        //Print first 2 nums
        System.out.println(num1);
        System.out.println(num2);
        //Loop for number-2 times
        for (int i = 0; i <= number-2; i++) {
            //find next num in sequence, update num1 and 2
            num3 = num1 +num2;
            num1=num2;
            num2=num3;
            System.out.println(num3);
        }

    }
}
