package WhatIsStatic;

import java.util.Random;
import java.util.random.RandomGenerator;

public class WhatIsStatic {
    public static void main(String [] args) throws Exception{
        //Static means:
        /*You can use THAT method without declaring an object of THAT type
        *
        * Think of Dog.type, you can't just say the dog class, you need to specify a dog object
        * With static you just call the method
        * */
        double squareRootOf5 = Math.sqrt(5); // this is an example of a static object, I don't have to make a "Math" object,
        // I can just call it from the MATH method, it is a STATIC method.
        //Important math class methods,

        //Math.abs   absolute value
        double x = Math.abs(-5);
        //I don't need to know anything else but the number they want me to operate on, so it's static.

        //Math.pow   power/exponents
        //first is the base, second is the exponent
        double p = Math.pow(4,3);

        //Math.sqrt   square root
        //Here's an example, this is the pythagorean theorum, finding the hypotenuse from a and b
        double a = 3;
        double b = 4;
        double length = Math.sqrt(Math.pow(a,2)+Math.pow(b,2));

        //comprehension check
        double y1 = Math.pow(x,0.5);
        double y2 = Math.sqrt(x);
        //mathmatically these should be exactly the same, but internally they might be calculated differently, so
        //our answer might be slightly different, meaing if we were to do ==, they might not be = for all values
        //THIS IS A BIG ONE, BE CAREFUL OF THIS ON TEST, HE DEF GON MESS WITH YOU ON THIS
        //Doubles don't have infinite precision, possible rounding error/difference



        //mystery formula
        int i = 1;
        int j = 4;
        int k =-7;

        double result1 =
                (-j +Math.sqrt(Math.pow(j,2)-4*i*k)) / (2*i);
        double result2 =
                (-j - Math.sqrt(Math.pow(j,2)-4*i*k)) / (2*i);
        //QUADRATIC FORMULA


        //Other static math methods
        /*Math.cos
        * Math.sin
        * Math.tan
        * Math.acos     sec
        * Math.asin     csc
        * Math.atan     cot
        * Math.floor    round down
        * Math.ceil     round up
        * Math.exp(n)   natural log formula e^n
        * */

        /*We can also declare instance variables as static, such as PI here, this is simply the value of PI
        * Math.PI
        * It's actually a final, that's why it's all caps, since it's a final, it's also able to be public, and it is
        *
        * Similar to pi, there's a value for e, fuck e*/




        //THE MOST IMPORTANT MATH METHOD: Math.random()
        double r = Math.random();
        System.out.println(r);
        //This number will always be any number between 0 and 1, it is NOT UNPREDICTABLE
        //RANDOM IS WHAT IS CALLED PSEUDO-RANDOM, IT ACTUALLY HAS AN ALGORITHM THAT YOU COULD TECHNICALLY PREDICT

        /*start with a "seed"
        * some number maybe "number of ms since the computer was turned on"
        * perform math: (a * seed + b) % c
        * a b and c are all predetermined
        *
        * repeat every time you want a new number, but plug the old number in instead of the seed
        *
        * a b and c were chosen to evenly distribute the number set
        *
        * this is extremely predictable, if you know the last number, then you can easily calculate the next number
        * it is a simple chain
        *
        * a computer is simply not good at picking random numbers, because it is inherently deterministic
        *
        * let's say i want a number from 0-10*/
        double result0to10 = Math.random()*10;
        //Since Math.random gives you a number from 0 to 1 (although it never actually goes to 1, only 0.99999999999...), what you times it by, no matter what

        //if we wanted this as an int, we would do:
        int from0to10 = (int)(Math.random()*10);
        //MAKE SURE THAT YOU MULTIPLY BY 10 BEFORE CASTING, BECAUSE MATH.RANDOM, WILL ALWAYS CAST TO 0

        //1-10
        int from1to10 = (int)(Math.random()*10)+1;

        //FINAL FORMULA:
        /*Starting with Math.random()
        * multiply it by the range (number of possible results)
        * add the low end/minimum value
        * cast to int if you want that, or keep as double, do whatever*/

        //if we want to expand the range of our random, we can just multiply, REMEMBER THAT RANDOM ONLY GOES TO BASICALLY 1, NOT 1.
        //Steps for finding a random int,
        int resultRand = (int)(Math.random()*10)+1;
        //gives 1-10

//        We can also write out own static methods, such as simpleAddition below
        System.out.println(WhatIsStatic.simpleAddition(1,2));

    }
    public static int simpleAddition (int a, int b){
        return a+b;
    }

}
