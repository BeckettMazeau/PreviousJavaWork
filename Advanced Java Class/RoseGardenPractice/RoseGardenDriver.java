package RoseGardenPractice;

public class RoseGardenDriver
{
    public static void main(String [] args) throws Exception{
        boolean error = false;

        RoseGarden farm = new RoseGarden(3,4);
        farm.addPlant(0,0,new RoseBush(2));
        farm.addPlant(0,1,new RoseBush(3));
        farm.addPlant(0,2,new RoseBush(3));
        farm.addPlant(1,1,new RoseBush(3));
        farm.addPlant(1,2,new RoseBush(2));
        farm.addPlant(1,3,new RoseBush(1));
        farm.addPlant(2,0,new RoseBush(1));
        farm.addPlant(2,1,new RoseBush(1));
        farm.addPlant(2,3,new RoseBush(3));

        if(farm.getPlant(0,3) != null){
            System.out.println("ERROR: getPlant thinks the null space at [0][3] has an object in it.");
            error = true;
        }

        if(farm.getPlant(0,2).getNumRoses() != 3){
            System.out.println("ERROR: getPlant reports [0][2] does not have 3 flowers.\n" +
                    "  Make sure your addPlant method is putting the new bushes in the right places");
            error = true;
        }

        double avg = farm.avgNumRoses();
        if(Math.abs(avg - 2.1111) > 0.01){
            System.out.println("ERROR: avgNumRoses returned " + avg + ", should be 2.1111.\n" +
                    "  Make sure you are counting the total number of roses and total number of bushes correctly (16 and 9).");
            error = true;
        }

        System.out.println("Garden pre-swap: ");
        for(int row=0; row<3; row++){
            for(int col=0; col<4; col++){
                RoseBush b = farm.getPlant(row,col);
                if(b == null){
                    System.out.print("_ ");
                }
                else{
                    System.out.print(b.getNumRoses() + " ");
                }
            }
            System.out.println("");
        }
        System.out.println("");


        farm.swapRoseBushes();


        System.out.println("Garden post-swap: ");
        for(int row=0; row<3; row++){
            for(int col=0; col<4; col++){
                RoseBush b = farm.getPlant(row,col);
                if(b == null){
                    System.out.print("_ ");
                }
                else{
                    System.out.print(b.getNumRoses() + " ");
                }
            }
            System.out.println("");
        }


        if(farm.getPlant(0,0).getNumRoses() != 3 || farm.getPlant(0,1).getNumRoses() != 2 || farm.getPlant(0,2).getNumRoses() != 3 || farm.getPlant(0,3) != null ||
                farm.getPlant(1,0) != null || farm.getPlant(1,1).getNumRoses() != 1 || farm.getPlant(1,2).getNumRoses() != 2 || farm.getPlant(1,3).getNumRoses() != 3 ||
                farm.getPlant(2,0).getNumRoses() != 3 || farm.getPlant(2,1).getNumRoses() != 1 || farm.getPlant(2,2) != null || farm.getPlant(2,3).getNumRoses() != 1 ){
            System.out.println("ERROR: empty plots are in the right places, but the swapped bushes aren't in their correct final plots.\n" +
                    "  Check the swap method logic");
            error = true;
        }

        if(!error){
            System.out.println("All tests passed");
        }
    }
}
