public class MazeSolverDriver {
    public static void main(){
        char[][] mazeArray = {
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'},
            {'w','w','w','w','w','w','w','w','w','w',' ',' ',' ','w','w','w',' ',' ','E'},
            {'w','w','w',' ','w','w','w',' ',' ',' ',' ','w','w',' ','w','w',' ','w','w'},
            {'w','w',' ',' ','w','w',' ',' ','w','w','w','w',' ',' ','w','w',' ',' ','w'},
            {'w',' ',' ','w','w','w',' ','w','w',' ',' ',' ',' ','w','w',' ',' ','w','w'},
            {'w',' ','w',' ',' ',' ',' ',' ',' ',' ','w','w',' ',' ',' ',' ','w','w','w'},
            {'w',' ',' ',' ','w','w','w','w','w',' ',' ',' ',' ','w','w','w',' ','w','w'},
            {'w','w','w',' ','w','w','w','w','w','w','w','w',' ','w','w',' ',' ','w','w'},
            {'w','w','w',' ',' ',' ',' ','w',' ',' ',' ','w','w','w',' ',' ','w','w','w'},
            {'w','w','w','w','w','w',' ','w','w','w',' ','w','w','w',' ','w','w','w','w'},
            {'w',' ',' ',' ',' ',' ',' ','w','w','w',' ','w','w','w',' ',' ',' ',' ','w'},
            {'w','w','w','w','w','w',' ','w','w','w',' ','w','w','w','w','w',' ','w','w'},
            {'w','w','w','w','w','w',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','w','w'},
            {'w','w','w','w','w','w','w','w',' ','w','w','w','w','w','w','w','w','w','w'}
        };
        Maze maze1 = new Maze(mazeArray);
        System.out.println("Initial maze:");
        System.out.println(maze1);
        System.out.println();
        
        //starting position is at row 13, column 8
        if(maze1.solve(13,8)){
            System.out.println("Solved maze:");
            System.out.println(maze1);
        }
        else {
            System.out.println("Maze was not solved!");
            System.out.println(maze1);
        }
    }
}