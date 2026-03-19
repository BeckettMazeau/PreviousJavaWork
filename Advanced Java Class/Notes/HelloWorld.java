public class HelloWorld
{
    public static void main(){
        //main is the entry point to the program, not a keyword
        System.out.println("Hello World!"); //all commands end off with a semicolon
        //Creating string variable
        String message;
        
        message = "hi";
        
        //==, !=, >, <, >=, <=
        //&&, !, ||
        // Declaring int variable
        int number;
        //Initializing an int variable
        number = 5;
        
        System.out.println(number - 2);
        //Change int variable
        number = number - 7;
        
        System.out.println(number);
        //Demonstrating overflow with ints
        int bigNumber = 1_900_000_000;
        System.out.println(bigNumber);
        bigNumber = bigNumber + 40000000;
        System.out.println(bigNumber);
        bigNumber = bigNumber + 40000000;
        System.out.println(bigNumber);
        bigNumber = bigNumber + 40000000;
        System.out.println(bigNumber);
        bigNumber = bigNumber + 40000000;
        System.out.println(bigNumber);
        bigNumber = bigNumber + 40000000;
        System.out.println(bigNumber);
        bigNumber = bigNumber + 40000000;
        System.out.println(bigNumber);
        bigNumber = bigNumber + 40000000;
        System.out.println(bigNumber);
        
        //Can fix int overflow with long instead of int, however this can
        //still technically overflow, but the number is so absurdly high
        //its unlikely you will encounter it.
        
        //longs can be pos or neg, but to flag a LITERAL (see data types notes)
        //you must use L at the end
        long biggerNumber = 1_900_000_000L;
        System.out.println(biggerNumber);
        biggerNumber = biggerNumber + 40000000;
        System.out.println(biggerNumber);
        biggerNumber = biggerNumber + 40000000;
        System.out.println(biggerNumber);
        biggerNumber = biggerNumber + 40000000;
        System.out.println(biggerNumber);
        biggerNumber = biggerNumber + 40000000;
        System.out.println(biggerNumber);
        biggerNumber = biggerNumber + 40000000;
        System.out.println(biggerNumber);
        biggerNumber = biggerNumber + 40000000;
        System.out.println(biggerNumber);
        biggerNumber = biggerNumber + 40000000;
        System.out.println(biggerNumber);
        biggerNumber = biggerNumber + 40000000;
        System.out.println(biggerNumber);
        biggerNumber = biggerNumber + 40000000;
        System.out.println(biggerNumber);
        biggerNumber = biggerNumber + 40000000;
        System.out.println(biggerNumber);
        biggerNumber = biggerNumber + 2000000000000L;
        System.out.println(biggerNumber);
        
        //declaring doubles
        double fraction = 0.2;
        
        System.out.println(fraction);
        
        fraction = fraction + 0.2;
        System.out.println(fraction);
        fraction = fraction + 0.2;
        System.out.println(fraction);
        fraction = fraction + 0.2;
        System.out.println(fraction);
        //imperfect see Data Types
        
        char letter = 'a';
        
        //escape characters are put in front of coding characters that
        //you want to be a part of the string, see below.
        //Applicable to new line as well, new line character is \n
        String quote = "George Washington said, \"I cannot tell a lie\"";
        String quote2 = "George Washington said,\n \"I cannot tell a lie\"";
        System.out.println(quote);
        System.out.println(quote2);
        
        //Different way to Print
        System.out.print("line1");
        System.out.print("line2");
        System.out.print("line3");
        //Prints onto the same line, .println prints then goes to the next line
        String user = "Jack";
        System.out.print("Hello ");
        System.out.print(user);
        System.out.println(" welcome to my site!");
        //Concatenation allows you to combine multiple variables / strings
        System.out.println ("Hello " + user + " welcome to my site!");
        
        //With literals, Concatenation becomes calculations
        System.out.println(5+6);
        //If one is a string and one is a number it is concatenation
        System.out.println("you are customer #"+5);
        //This also applies to if you are adding numbers but one is a string
        System.out.println("11"+5);
        //Like in normal math, java follows PEMDAS, and will respect parentheses
        //to force a certain order of operations
        System.out.println("11"+5+3);
        //1153
        System.out.println("11"+(5+3));
        //118
        
        //Because the + has more than 1
        //"function", the + symbol is "overloaded"
        
        //ints can also be declared in a row, but it
        //is bad practice because it makes it harder to find
        
        int x =1, y=2, z=3;
        
        //you can declare "final" variables, these variables
        //can never be changed, you will recieve a 
        //final variable cannot be changed error
        final double PI = 3.1415926;
        final int SITE_WIDTH = 200;
        //You must declare a type for finals
        //Final variabels are written in ALL CAPS
        
        //Java follows the principle of "auto-unboxing"
        //but NOT "auto-boxing"
        //Java will not automatically put something
        //from a broader, less restrictive category
        //and put it into a more restrictive one
        double fractional = 5;
        //this is okay, 5 is less restrictive so we can fit
        //int integer = 5.5; will result in a lossy
        //conversion, and will give us an error
        //we fix this with "casting"
        int integer = (int)5.5;
        //here we are telling java i know we can lose data,
        //its okay
        long bigNum = 37498;
        int smallNum = (int)bigNum;
        System.out.println(smallNum);
        //here the program gets worried because there
        //is a possibility of loss because a long
        //can hold something a int cant hold,
        //but in this specific case there is no issue, so we
        //"cast"
        long biggerNum = 324091904904132L;
        int smallerNum = (int)biggerNum;
        System.out.println(smallerNum);
        //Casting will truncate, so it will chop off the part
        //of the data past what it can handle (starting from
        //the front, so we recieve the back half).
        
        //Casting comes before any order of operations
        double mysteryNum = (int)5.5 + 1.5;
        System.out.println(mysteryNum);
        //Here we recieve 6.5, because the 5.5 is casted
        //down to a 5, THEN added
        double mysteryNum2 = 5.5 + 1.5;
        System.out.println(mysteryNum2);
        //Notice doubles, even if they are a whole num, have
        //a .0
        
        
        //math ops
        // + - * / (^ is not exponents) % (% is mod op, gives
        //remainder)
        
        //int division is different than double division
        //EVEN IF IT IS STORED IN A DOUBLE (must both be ints)
        //INT DIVISION "TRUNCATES" THE FRACTIONAL PORTION
        double d = 10 / 3;
        System.out.println(d);
        
        double dd = 10.0 / 3.0;
        System.out.println(dd);
        
        //If they are MIXED TYPES, java will always
        //default to the less restrictive types
        
        double ddd = 10.0 / 3;
        System.out.println(ddd);
        
        //the "mod"/"modulo" operator % is getting the remainder
        int remainder = 12 % 5;
        System.out.println(remainder);
        //0 means cleanly divisble
        
        //FULL ODER OF OPS:
        //Parentheses - Casting - M/D - A/S
        
        
        Math.pow(5,2);
        //This is 5 squared
        
        d = d + 3;
        //shortcut is
        d+= 3;
        //same thing, take whatever d is and add 3.
        //can be used with other ops.
        //further shortcuts are
        d++; //+ 1
        d--; //- 1
        
        //IF STATEMENTS
        
        //relational operators
        // == != > < >= <=
        if(d==16 //this is a boolean
        ){
            System.out.println("d is greater than 16");
        }
        
        //logical operators
        // ! && ||
        
        boolean dogOwner = true;
        boolean catOwner = false;
        boolean wantsEmails = false;
        
        if(dogOwner || catOwner && wantsEmails){
            //send them a pet coupon
        }
        //issue here is java looks at && first
        //so our result here is not what we want
        if((dogOwner || catOwner) && wantsEmails){
            //send them a pet coupon
        }
        //just make sure any time you need a specific
        //order of computation you are super specific
        
        
        //types of errors
        
        //1. Sytax Error: code breaks a language rule
        //if(x == 7||8);
        //here it breaks because you have to have a boolean
        //on both sides of an ||
        
        //2. Runtime error:
        // Code is "grammatically fine", or syntaxically
        //fine, but causes an unrecoverable problem when run
        int totalHits = 50;
        int numGames = 35;
        if(totalHits / numGames >= 1) {
            System.out.println("more than 1 hit/game");
        }
        //This is fine, but imagine either numGames or both
        //variables are 0.
        /*
         * int totalHits = 0;
        int numGames = 0;
        if(totalHits / numGames >= 1) {
            System.out.println("more than 1 hit/game");
        }
        This will cause a runtime error, because there is
        no way to recover from a divide by 0 error
        
        We can protect ourselves from this by using short
        circuiting if statements:
         */
        totalHits = 50;
        numGames = 0;
        if(numGames > 0 && totalHits / numGames >= 1) {
            System.out.println("more than 1 hit/game");
        }
        //Here we dont even calculate the 2nd half
        //because if it knows the answer in the 1st half
        //it wont do the second half.
        
        //3. Logic error
        /*
         * No synatx problems - no crash (runtime problems)
         * - but it does the wrong thing.
         * These are very hard to find, because these will
         * run but will not give the answer you want.
         */
    }
}
