package ProgrammingChallenges2;

import java.util.Scanner;

public class Problem6 {
    public static void main(String[] args) {

        //This broke my brain

        Scanner num_in = new Scanner(System.in);
        System.out.println("Enter Integer to Check if Prime:");
        //Grab num
        int number = num_in.nextInt();
        boolean prime = true;
        for (int maybeDivisor = 2; maybeDivisor <= Math.sqrt(number); maybeDivisor++) {
            if(number%maybeDivisor==0){
                prime = false;
                break;
            }
        }
        if(prime){
            System.out.println(number+" is prime");
        }
        else {
            System.out.println(number+" is not prime");
        }
//
//            for (int i = 2; i < number; i++) {
//                if (number%i==0){
//                    prime = 0;
//                    i = number;
//                }
//            }
//            if (number==2){
//                prime=1;
//            }
//            if (prime==1){
//                System.out.println(number + " is Prime");
//            }
//            else {
//                System.out.println(number + " is not Prime");
//            }


    }
}