package Moving;

public class Moving
{
    public static void main(String [] args) throws Exception{
        //moving things around will be on the quiz

        //some basic examples of moving elements around within an array

        int[] grades = {30, 40, 50, 30, 10, 55};

        //most basic move something
        //grades[0] = grades[5];
        //this will replace whatever is in slot 0, with what ever is in slot 5
        //but slot 5 won't change. now we have a duplicate. and we have lost the index 0

        //better version: use a temp variable
        int temp = grades[0];
        grades[0] = grades[5];
        grades[5] = temp;
        //this will swap the values at position 0 and 5

        //more complicated example: REVERSING AN ARRAY
        //there are several ways that i could accomplish this
        //example 1: copying

        //declaring a new array takes up a lot of space

        int[] gradesCopy = new int[grades.length];
        //loc -> "location"
        int copyLoc = grades.length - 1;
        for (int i = 0; i < grades.length; i++){
            //filling in the copy array starting from the right, with the values in og array starting from the left
            gradesCopy[copyLoc] = grades[i];
            copyLoc--;
        }

        //setting grades to be the copied version, which is backwards
        //now grades points at gradesCopy in the object memory
        grades = gradesCopy;

        //example 2: in-place reversing
        //this one is more efficient
        for (int i = 0; i < grades.length/2; i++){
            int temp2 = grades[i];
            grades[i] = grades[grades.length-i-1];
            grades[grades.length-i-1] = temp2;
        }


    }
}

