package TwoDArrarys;

public class Farm {
    private Potato[][] field;
        public Farm(){
            field = new Potato[3][8];
            field[0][0] = new Potato("sweet");
            field[1][5] = new Potato("russet");
            field[2][7] = new Potato("yukon");
        }
        public int getNumRows(){
            return field.length;
        }
        public void printField(){
            for (int row = 0; row < field.length; row++) { // field.length is number of rows
                for (int col = 0; col < field[row].length; col++) { //field.length is number of columns
                    try {
                        System.out.println(field[row][col].getStrain());
                    } catch (Exception e){}
                }
            }
        }
        public void waterRow(int row){
            for (int col = 0; col < field[row].length; col++) {
                if(field[row][col]!=null){
                    field[row][col].water();
                }
            }
        }
        public Potato findBiggestPotato(){
            int biggestWeight =-1;
            Potato biggestPotato = null;
            for (int row = 0; row < field.length; row++) {
                for (int col = 0; col < field[row].length; col++) {
                    if ((field[row][col].getWeight()>biggestWeight)&&field[row][col]!=null){
                        biggestPotato = field[row][col];
                        biggestWeight = field[row][col].getWeight();
                    }
                }
            }
            return biggestPotato;
        }
        public int harvest(){
            int total = 0;
            for (int row = 0; row < field.length; row++) {
                for (int col = 0; col < field[row].length; col++) {
                    if(field[row][col]!=null){
                        total += field[row][col].getWeight();
                        field[row][col] = null;
                    }
                }
            }
            return total;
        }
}
