
public class DiverseArrayDriver {
    public static void main(){
        
        boolean hadError = false;
        
        int[] arr1 = { 1, 3, 2, 7, 3 };
        int ret1 = DiverseArray.arraySum(arr1);
        if(ret1 != 16){
            System.out.println("ERROR with part A (arraySum): wrong sum");
            hadError = true;
        }
        
        int[][] arr2 = { { 1, 3, 2, 7, 3 },
                         {10,10, 4, 6, 2 },
                         { 5, 3, 5, 9, 6 },
                         { 7, 6, 4, 2, 1 } };
        int[] ret2 = DiverseArray.rowSums(arr2);
        int[] expected2 = { 16, 32, 28, 20 };
        if(ret2.length != 4){
            System.out.println("ERROR with part B (rowSums): " +
                               "\n  returned array is not the expected length");
            hadError = true;
        }
        for(int i=0; i<ret2.length; i++){
            if(ret2[i] != expected2[i]){
                System.out.println("ERROR with part B (rowSums): " +
                                   "\n  returned array has the wrong value at position " + i +
                                   "\n  (got " + ret2[i] + ", expected " + expected2[i] + ")");
                hadError = true;
            }
        }
        
        int[][] arr3 = { { 1, 1, 5, 3, 4 },
                         {12, 7, 6, 1, 9 },
                         { 8,11,10, 2, 5 },
                         { 3, 2, 3, 0, 6 } };
        if(!DiverseArray.isDiverse(arr2)){
            System.out.println("ERROR with part C (isDiverse): " +
                               "\n  method returning false for the 1st array (which is diverse)");
            hadError = true;
        }
        if(DiverseArray.isDiverse(arr3)){
            System.out.println("ERROR with part C (isDiverse): " +
                               "\n  method returning true for the 2nd array (which is NOT diverse)");
            hadError = true;
        }
        
        if(!hadError){
            System.out.println("ALL TESTS PASSED!");
        }
    }
}
