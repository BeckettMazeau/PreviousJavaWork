
public class Maze{
   private char[][] maze;
   public Maze(char[][] passedMaze){
       maze = passedMaze;
   }
   public String toString(){
       String string = "";
       for (int row = 0; row < maze.length; row++) {
           for (int col = 0; col < maze[0].length; col++) {
               string += maze[row][col];
           }
           string += "\n";
       }
       return string;
   }
   public boolean solve(int row, int col){
       if (!isValidPlace(row,col)){
           return false;
       }
       if(maze[row][col]=='E'||maze[row][col]=='!'){
           return true;
       } else {

           maze[row][col] = '.';

           if (solve(row+1,col)){
               maze[row][col] = '!';
               return true;
           }
           if (solve(row-1,col)){
               maze[row][col] = '!';
               return true;
           }
           if (solve(row,col+1)){
               maze[row][col] = '!';
               return true;
           }
           if (solve(row,col-1)){
               maze[row][col] = '!';
               return true;
           }
       }
       return false;
   }
   public boolean isValidPlace(int row,int col){
       if (row <0 || row >= maze.length || col<0 || col>= maze[0].length || maze[row][col]=='w' ||maze[row][col]=='.'){
           return false;
       }
       if(maze[row][col]==' '){
           return true;
       }
       return true;
   }
}

