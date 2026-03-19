import java.util.ArrayList;

public class Pascals
{
    public int pascals(int row, int col){
        if(col ==0){
            return 1;
        } if (col == 1){
            return row;
        }
        int[][] triangle = new int[row+1][row+1];
        
        for (int rows = 0; rows < triangle.length; rows++) {
            triangle[rows][0] = 1;
            triangle[rows][1] = rows;
        }
        for (int curRow = 2; curRow < triangle.length; curRow++) {
            for (int curCol = 2; curCol < triangle[0].length; curCol++) {
                int temp = 0;
                temp += triangle[curRow-1][curCol-1];
                
                
                temp +=triangle[curRow-1][curCol];
                
                triangle[curRow][curCol] = temp;
            }
        }
        
        
        return triangle[row][col];
    }
}
