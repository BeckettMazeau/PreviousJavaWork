
public class FizzBuzz
{
    public static void main(){
        int n = 100;
        //Main loop to run if statements on each value between
        //1 and n
        for(int i=1;i<=n;i++){
            //If statements to check relevant output
            if( i % 3 == 0 && i % 5 == 0 ){
                System.out.println("FizzBuzz");
            }
            else if ( i % 3 == 0 ){
                System.out.println("Fizz");
            }
            else if ( i % 5 == 0 ){
                System.out.println("Buzz");
            }
            else {
                System.out.print(i);
                //Initializing Temp Decimal variable
                double decimal1 = 0;
                double decimal2 = 0;
                //Determining Results of i div 3 and 5
                decimal1 = (i / 3.0);
                decimal2 = (i / 5.0);
                //Printing results of above calcs with spacers
                System.out.print (" || " + "Div by 3: " + decimal1+
                " || " +"Div by 5: "+decimal2 + "\n");
                
            }
            
        }
        
        
    }
}
