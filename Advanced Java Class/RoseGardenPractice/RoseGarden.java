package RoseGardenPractice;

public class RoseGarden {
    RoseBush[][] garden;
    public RoseGarden(int numRows, int numCols){
        garden = new RoseBush[numRows][numCols];
    }
    public void addPlant(int row, int col, RoseBush newPlant){
        garden[row][col] = newPlant;

    }
    public RoseBush getPlant(int row, int col){
        return garden[row][col];
    }
    public double avgNumRoses(){
        int count = 0;
        int roses = 0;
        for (int row = 0; row < garden.length; row++) {
            for (int col = 0; col < garden[row].length; col++) {
                if (garden[row][col] !=null){
                    count++;
                    roses+= garden[row][col].getNumRoses();
                }
            }
        }
        if (count ==0){
            System.out.println("No Rose Bushes.");
            return 0;
        }
        return (double)roses/count;

    }
    public void swapRoseBushes(){

        for (int row = 0; row < garden.length; row++) {
            int[] highIndex = new int[2];
            int[] lowIndex = new int[2];
            int highRoses = -1;
            int lowRoses = Integer.MAX_VALUE;

            for (int col = 0; col < garden[row].length; col++) {
                if (garden[row][col] !=null){
                    if (garden[row][col].getNumRoses() > highRoses){
                        highRoses = garden[row][col].getNumRoses();
                        highIndex[0] = row;
                        highIndex[1] = col;

                    }
                    if (garden[row][col].getNumRoses() < lowRoses){
                        lowRoses = garden[row][col].getNumRoses();
                        lowIndex[0] = row;
                        lowIndex[1] = col;
                    }
                }
            }
            RoseBush temp = garden[highIndex[0]][highIndex[1]];
            garden[highIndex[0]][highIndex[1]] = garden[lowIndex[0]][lowIndex[1]];
            garden[lowIndex[0]][lowIndex[1]] = temp;

        }
    }
}
