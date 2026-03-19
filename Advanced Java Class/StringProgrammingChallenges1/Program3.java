package StringProgrammingChallenges1;

import java.util.Scanner;

public class Program3 {
    public static void main(String[] args) throws Exception {
        Scanner wordgrab = new Scanner(System.in);
        System.out.println("Enter a Word: ");
        String word = wordgrab.nextLine();
        word = word.trim();
        word = word.toLowerCase();
        char[] charlist = word.toCharArray();
        StringBuilder newword = new StringBuilder();
        for (int i = charlist.length-1; i >= 0; i--) {
            //https://www.geeksforgeeks.org/string-vs-stringbuilder-vs-stringbuffer-in-java/
            newword = newword.append(charlist[i]);
        }
        System.out.println("Word: "+ word);
        System.out.println("Reverse: " + newword.toString());
        if (word.equals(newword.toString())){
            System.out.println(word + " is a palindrome.");

        } else {
            System.out.println(word + " is not a palindrome.");
        }

    }
}
