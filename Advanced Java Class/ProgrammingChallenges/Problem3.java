public class Problem3 {
    public static void main(){
        //Initialize variables
        int amount = 249;
        final int amountI = amount;
        int huns = 0;
        int fifs = 0;
        int twens = 0;
        int tens = 0;
        int fivs = 0;
        int ones = 0;
        int counter = 0;
        //Count 100's
        while(amount>100){
            amount -= 100;
            huns++;
        }
        System.out.println("You will need: \n" + huns + " $100 dollar bills");
        //Count 50's
        while(amount>50){
            amount -= 50;
            fifs++;
        }
        System.out.println("You will need: \n" + fifs + " $50 dollar bills");
        //Count 20's
        while(amount>20){
            amount -= 20;
            twens++;
        }
        System.out.println("You will need: \n" + twens + " $20 dollar bills");
        //Count 10's
        while(amount>10){
            amount -= 10;
            tens++;
        }
        System.out.println("You will need: \n" + tens + " $10 dollar bills");
        //Count 5's
        while(amount>5){
            amount -= 5;
            fivs++;
        }
        System.out.println("You will need: \n" + fivs + " $5 dollar bills");
        //Count 1's
        while(amount>0){
            amount -= 1;
            ones++;
        }
        System.out.println("And you will need: \n" + ones + " $1 dollar bills");
    }
}