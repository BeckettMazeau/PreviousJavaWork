package ProgrammingChallenges2;

import java.util.Scanner;

public class Problem6_2 {
    public static void main(String[] args){
            //god save the queen

            int prime = 1;
            Scanner num_in = new Scanner(System.in);
            System.out.println("Enter Integer:");
            //Grab num
            int number = num_in.nextInt();
            if (number!=0){
                System.out.println("1 is Prime");
            }
            for (int i = 2; i <= number; i++) {
                prime=1;
                for (int j = 2; j < i; j++) {
                    if (i % j == 0) {
                        prime = 0;
                    }

                }
                if (number==2){
                    prime=1;
                }
                if (prime==1){
                    System.out.println(i + " is Prime");
                }
                else {
                    System.out.println(i + " is not Prime");
                }
            }



        }
    }

