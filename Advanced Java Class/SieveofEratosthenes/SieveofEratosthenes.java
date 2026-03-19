package SieveofEratosthenes;

import java.util.Arrays;
import java.util.Scanner;

public class SieveofEratosthenes {
    public static void main(String [] args)throws Exception{
        Scanner input = new Scanner(System.in);
        System.out.println("Input Number:");
        int temp = input.nextInt();
        Boolean[] primes = new Boolean[temp+1];
        Arrays.fill(primes,true);
        primes[0] = false;
        primes[1] = false;
        int max = temp*temp;
        for (int value = 2; value < primes.length; value++) {
            if (primes[value]){
                for (int divisor = 2; divisor < value; divisor++) {

                    if (value%divisor==0){
                        primes[value]=false;
                        break;
                    }



                }
            }
            if (primes[value]){
                for (int i = 2; i*value<temp; i++) {
                    primes[value*i] = false;

                }
            }
        }
        System.out.println("");
        System.out.println("Primes:");
        System.out.println("");
        for (int i = 0; i < primes.length; i++) {
            if (primes[i]){
                System.out.println(i);
            }

        }

    }
}
