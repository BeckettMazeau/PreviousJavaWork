package GradebookV2;
import GradebookV2.GradebookV2;

import java.util.Scanner;

public class GradebookDriverV2 {
    public static void main(String[] args) throws Exception{
        //Test: creating a new gradebook via manual user entry
        GradebookV2 book1 = new GradebookV2();
        System.out.println("book1 high score: " + book1.getMaxScore());
        System.out.println("book1 low score: " + book1.getMinScore());
        System.out.println("book1 average score: " + book1.getAvgScore());

        //Test: creating a new gradebook via pre-initialized array
        int[] tempGrades = {50,55,65,72,74,75,76,77,78,80,81,81,81,82,83,83,85,86,88,89,
                89,89,89,90,91,92,92,93,93,93,93,94,95,97,98,100,100};
        GradebookV2 book2 = new GradebookV2(tempGrades);

        //zeroing out the original array: this enforces that you COPY the values
        //rather than just aliasing the array. If you're getting all zeroes, this is why -
        //you should update your constructor to COPY the values instead.
        for(int i=0; i<tempGrades.length; i++){
            tempGrades[i] = 0;
        }

        System.out.println("book2 high score: " + book2.getMaxScore() + ", (should be 100)");
        System.out.println("book2 low score: " + book2.getMinScore() + ", (should be 50)");
        System.out.println("book2 average score: " + book2.getAvgScore() + ", (should be 84 or 85 depending on rounding)");

        //Test: linear searching for known values
        System.out.println("Linear searching book2 for the grade 98...");
        int index = book2.linearSearch(98);
        System.out.println("linearSearch method returned " + index + ", (should be 34)");

        System.out.println("Linear searching book2 for the grade 79...");
        index = book2.linearSearch(79);
        System.out.println("linearSearch method returned " + index + ", (should be -1)");


        //Binary search - Leave this section commented out until we get to this in class (Part 2).
        //At that point you can remove the /* and */ to uncomment



        //Test: binary searching for known values
        System.out.println("Binary searching book2 for the grade 98...");
        index = book2.binarySearch(98);
        System.out.println("binarySearch method returned " + index + ", (should be 34)");

        System.out.println("Binary searching book2 for the grade 79...");
        index = book2.binarySearch(79);
        System.out.println("binarySearch method returned " + index + ", (should be -1)");




        //Test searching for a user-entered grade
        Scanner gradeFinder = new Scanner(System.in);
        System.out.println("Search for what grade?");
        int find = Integer.parseInt(gradeFinder.nextLine());
        System.out.println("Linear searching book2 for user-entered grade (" + find + ")...");
        index = book2.linearSearch(find);
        System.out.println("linearSearch method returned " + index);
        index = book2.binarySearch(find);
        System.out.println("binarySearch method returned " + index);
        GradebookV2 book3 = new GradebookV2(120);
        book3.selectionSort();
        book3.printGrades();
        System.out.println(book3.isSorted());



        //Test binary searching for a user-entered grade
        //Same as above, uncomment when you are ready to test this portion (Part 2)

        /*
        System.out.println("Binary searching book2 for user-entered grade (" + find + ")...");
        index = book2.binarySearch(find);
        System.out.println("binarySearch method returned " + index);
        */

    }
}
