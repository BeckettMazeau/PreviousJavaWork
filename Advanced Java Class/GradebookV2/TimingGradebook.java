package GradebookV2;

public class TimingGradebook {
    public static void main(String [] args) throws Exception {
        GradebookV2 bookSelect = new GradebookV2(10000);
        GradebookV2 bookInsert = new GradebookV2(10000);
        GradebookV2 bookSelect1 = new GradebookV2(10000);
        GradebookV2 bookInsert1 = new GradebookV2(10000);
        GradebookV2 bookSelect2 = new GradebookV2(10000);
        GradebookV2 bookInsert2 = new GradebookV2(10000);
        GradebookV2 bookSelect3 = new GradebookV2(10000);
        GradebookV2 bookInsert3 = new GradebookV2(10000);
        GradebookV2 bookSelect4 = new GradebookV2(10000);
        GradebookV2 bookInsert4 = new GradebookV2(10000);
        double avgSelect = 0;
        double avgInsert = 0;
        double temp = 0;
        temp = System.currentTimeMillis();
        bookSelect.selectionSort();
        avgSelect += System.currentTimeMillis()-temp;
        temp = System.currentTimeMillis();
        bookSelect1.selectionSort();
        avgSelect += System.currentTimeMillis()-temp;
        temp = System.currentTimeMillis();
        bookSelect2.selectionSort();
        avgSelect += System.currentTimeMillis()-temp;
        temp = System.currentTimeMillis();
        bookSelect3.selectionSort();
        avgSelect += System.currentTimeMillis()-temp;
        temp = System.currentTimeMillis();
        bookSelect4.selectionSort();
        avgSelect += System.currentTimeMillis()-temp;
        temp = System.currentTimeMillis();
        bookInsert.insertionSort();
        avgInsert += System.currentTimeMillis()-temp;
        temp = System.currentTimeMillis();
        bookInsert1.insertionSort();
        avgInsert += System.currentTimeMillis()-temp;
        temp = System.currentTimeMillis();
        bookInsert2.insertionSort();
        avgInsert += System.currentTimeMillis()-temp;
        temp = System.currentTimeMillis();
        bookInsert3.insertionSort();
        avgInsert += System.currentTimeMillis()-temp;
        temp = System.currentTimeMillis();
        bookInsert4.insertionSort();
        avgInsert += System.currentTimeMillis()-temp;
        System.out.println("Average Insertion Speed: " + (avgInsert/5) + " Milliseconds");
        System.out.println("Average Selection Speed: " + (avgSelect/5) + " Milliseconds");
    }
}
