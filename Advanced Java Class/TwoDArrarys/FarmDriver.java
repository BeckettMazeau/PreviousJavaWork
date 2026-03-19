package TwoDArrarys;

public class FarmDriver {
    public static void main(String [] args) throws Exception{
        Farm oldMc = new Farm();

        while(oldMc.findBiggestPotato().getWeight()<10){
            for (int row = 0; row < oldMc.getNumRows(); row++) {
                oldMc.waterRow(row);
            }
        }
    }
}
