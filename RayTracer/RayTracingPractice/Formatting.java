import java.text.DecimalFormat;

/**
 * Write a description of class Formatting here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Formatting
{
    public static void main(){
        DecimalFormat formatter = new DecimalFormat("#0.00");
        double d = 2.01;
        System.out.println(formatter.format(d));

    }
}
