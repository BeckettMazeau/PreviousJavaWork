package StringProgrammingChallenges1;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args){
        Scanner sentGrab = new Scanner(System.in);
        System.out.println("Enter Sentence: ");
        final String sent = (sentGrab.nextLine());
        int number = 0;
        String sent1 = sent.toLowerCase();
        char[] chararray = sent1.toCharArray();
        for (int i = 0; i < chararray.length; i++) {
            if (chararray[i]=='a' || chararray[i]=='e' || chararray[i]=='i'||chararray[i]=='o'||chararray[i]=='u'){
                number++;
            } else if(chararray[i]=='y'&& (chararray[i-1]!='a' || chararray[i-1]!='e' || chararray[i-1]!='i'||chararray[i-1]!='o'||chararray[i-1]!='u'||chararray[i-1]!=' ')) {
                number++;
            }
        }
        System.out.println("The sentence \""+sent+"\""+ " has " + number + " vowels.");
    }
}
