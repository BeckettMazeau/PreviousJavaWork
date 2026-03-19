package GradebookV2;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GradebookV2 {
    private int[] scores;
    private Scanner input = new Scanner(System.in);
    private int maxScore = 0;
    private int minScore = Integer.MAX_VALUE;
    private double avgScore = 0;


    public GradebookV2() {
        int length = input.nextInt();
        scores = new int[length];
        for (int i = 0; i < length; i++) {
            scores[i] = input.nextInt();
        }
        int temp = 0;
        for (int l = 0; l < scores.length; l++) {
            temp += scores[l];
            if (scores[l]<minScore){
                minScore = scores[l];
            }
            if (scores[l]>maxScore){
                maxScore = scores[l];
            }
        }
        avgScore = (double) temp / scores.length;
    }
    public GradebookV2(int length){
        scores = new int[length];
        for (int i = 0; i < scores.length; i++) {
            scores[i] = (int)(Math.random()*101);
        }
        int temp = 0;
        for (int l = 0; l < scores.length; l++) {
            temp += scores[l];
            if (scores[l]<minScore){
                minScore = scores[l];
            }
            if (scores[l]>maxScore){
                maxScore = scores[l];
            }
        }
        avgScore = (double) temp / scores.length;
    }
    public GradebookV2(int[] predefinedGrades) {
        scores = new int[predefinedGrades.length];
        for (int j = 0; j < predefinedGrades.length; j++) {
            scores[j]=predefinedGrades[j];
        }
        int temp = 0;
        for (int l = 0; l < predefinedGrades.length; l++) {
            temp += predefinedGrades[l];
            if (predefinedGrades[l]<minScore){
                minScore = predefinedGrades[l];
            }
            if (predefinedGrades[l]>maxScore){
                maxScore = predefinedGrades[l];
            }
        }
        avgScore = (double) temp / predefinedGrades.length;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public int getMinScore() {
        return minScore;
    }
    public int linearSearch (int target){
        for (int i = 0; i < scores.length; i++) {
            if (scores[i]==target){
                System.out.println("Index: " + (i-1));
                System.out.println("Iterations: " + i );
                return i;
            }
        }
        System.out.println("Number not found");
        return -1;
    }
    public void printGrades(){
        for (int i = 0; i < scores.length; i++) {
            System.out.println(scores[i]);
        }
    }
    public boolean isSorted(){

        for (int i = 1; i < scores.length; i++) {
            if(scores[i]<scores[i-1]){
                return false;
            }
        }
        return true;
    }
    public void selectionSort(){
        for (int i = 0; i <scores.length; i++) {
            int current,minIndex =0;
            int min = Integer.MAX_VALUE;
            for (int j = i; j < scores.length; j++) {
                if (scores[j]<min){
                    min = scores[j];
                    minIndex = j;
                }
            }
            current = scores[i];
            scores[i] = min;
            scores[minIndex]=current;
        }
    }
    public void insertionSort(){
        for (int i = 1; i < scores.length; i++) {
            for (int j = i; j >0; j--) {
                if (scores[j-1]>scores[j]){
                    int temp = scores[j-1];
                    scores[j-1] = scores[j];
                    scores[j] = temp;
                }
            }
        }
    }
    public int binarySearch (int target){
        int maxIndex = scores.length-1;
        int middleIndex = maxIndex/2;
        int minIndex = 0;
        //Aware this will become a double if odd, but relying on int class
        //auto truncating decimal to make it an int again.
        while (true){
            if (maxIndex == minIndex && scores[maxIndex]!=target){
                return -1;
            }
            else if(scores[middleIndex]==target){
                return middleIndex;
            } else if (scores[middleIndex]>target){
                maxIndex = middleIndex-1;
                System.out.println(maxIndex);
            } else {
                minIndex = middleIndex+1;
                System.out.println(minIndex);
            }
            middleIndex = (maxIndex+minIndex)/2;
        }


    }

}
