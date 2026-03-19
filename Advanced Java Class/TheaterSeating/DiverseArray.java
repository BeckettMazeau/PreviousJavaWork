
/**
 * Write a description of class DiverseArray here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DiverseArray
{
   


public static int arraySum(int[] arr){
int sum = 0;
	for(int i = 0; i < arr.length; i++){
sum += arr[i];
}
return sum;
}
public static int[] rowSums(int[][] arr2D) {
int[] rowSums = new int[arr2D.length];
for(int i = 0; i < arr2D.length; i++){
	rowSums[i] = arraySum(arr2D[i]);
}
		return rowSums;
}
public static boolean isDiverse(int[][] arr2D){
int[] rowSums = rowSums(arr2D);
for(int i = 0; i < rowSums.length-1; i++){
for(int j =i+1; j<rowSums.length;j++){
	if(rowSums[j]==rowSums[i]){
		return false;
	}
}
}
return true;
}





}
