package StringProgrammingChallenges1;


import java.util.Scanner;

public class Program1 {
    public static void main(String[] args){
        
        //Initialize Variables
        int checksum = 0;
        //Grab Card Number
        Scanner cardgrab = new Scanner(System.in);
        System.out.println("Enter 16 digit card number:");
        long cardnum = (long)(cardgrab.nextLong());
        final long cardnumf = cardnum;
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
        //Visa 4
        //Discover 6
        //Amex 34 or 37
        //Mastercard 51-55 or 22-27
        int cardsub1 = Integer.parseInt((Long.toString(cardnumf)).substring(0,1));
        int cardsub2 = Integer.parseInt((Long.toString(cardnumf)).substring(0,2));
        String cardtype = "Unknown Card";

        if (cardsub1 == 4){
            cardtype = "Visa";
        } else if (cardsub1 == 6) {
            cardtype = "Discover";
        } else if (cardsub2 == 34 || cardsub2 == 37) {
            cardtype = "American Express";
        } else if ((cardsub2>=51 && cardsub2<=55) || (cardsub2>=22 && cardsub2<=27) ) {
            cardtype ="Mastercard";
        }


        if ((checksum)%10==0){
            System.out.println("Card Number " +cardnumf+" is a Valid "+cardtype + " Card.");
        }
        else {
            System.out.println("Card Number is Invalid");
        }


    }
}


