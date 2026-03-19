package ProgrammingChallenges3;

import java.util.Scanner;

public class Problem8 {
    public static void main(String[] args){
        //Initialize Variables
        int checksum = 0;
        //Grab Card Number
        Scanner cardgrab = new Scanner(System.in);
        System.out.println("Enter 16 digit card number:");
        long cardnum = (long)(cardgrab.nextLong());

        for (int i = 0; cardnum > 0 ; i++) {
            //Check if we are on 'other' number
            if (i%2!=0) {
                //Check if cardnum mod 10 * 2 >=10
                if ((cardnum%10)*2>=10){
                    //Add digits of cardnum mod 10 * 2 together
                    int temp = (int)((cardnum%10)*2);
                    int temp2 = temp%10;
                    temp /=10;
                    temp2 += temp;
                    checksum += temp2;
                    //update cardnum
                    cardnum /=10;
                }
                else {
                    //add cardnum mod 10 * 2 to checksum
                    checksum += (cardnum%10)*2;
                    //update cardnum
                    cardnum /=10;
                }
            }
            else {
                //add last digit of cardnum to checksum
                checksum += (cardnum % 10);
                //update cardnum
                cardnum /= 10;
            }
            //Debugging checks
            System.out.println("Check: " + checksum);
            System.out.println("Num: " + cardnum);
            System.out.println("I: " + i);
            System.out.println("");
        }
        System.out.println("------------------------ \n");
        if ((checksum)%10==0){
            System.out.println("Card Number is Valid");
        }
        else {
            System.out.println("Card Number is Invalid");
        }

    }
}
