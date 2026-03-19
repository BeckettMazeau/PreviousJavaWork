
public class Event
{
    private Ticket[][] eventArray;
    private double basePrice;
    public Event(int numRows, int numCols, double basePrice){
        eventArray = new Ticket[numRows][numCols];
        this.basePrice = basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
    public Ticket checkTicket(int row, int col){
        return eventArray[row][col];
    }
    public void printChart(){
        for (int row = 0; row < eventArray.length; row++) {
            for (int col = 0; col < eventArray[0].length; col++) {
                if (eventArray[row][col] == null){
                    System.out.print("________, ");
                    continue;
                }
                System.out.print(eventArray[row][col].getName()+", ");
            }
            System.out.println("");
        }
    }
    public boolean findSeat(String custName, double Budget){
        if (Budget < basePrice){
            return false;
        }

        for (int row = 0; row < eventArray.length; row++) {
            double rowPrice = basePrice;
            if (row == 0){
                rowPrice*=3;
            } else if (row <=3) {
                rowPrice*=2;
            }
            if (rowPrice > Budget){
                continue;
            }
            for (int col = 0; col < eventArray[0].length; col++) {
                if (eventArray[row][col] != null){
                    continue;
                }
                eventArray[row][col] = new Ticket(custName,rowPrice);
                return true;
            }
        }
        return false;
    }
    public double refund(String custName){
        double refundTotal = 0;
        for (int row = 0; row < eventArray.length; row++) {
            for (int col = 0; col < eventArray[0].length; col++) {
                if (eventArray[row][col] == null){
                    continue;
                }
                if (eventArray[row][col].getName() != custName){
                    continue;
                }
                refundTotal += eventArray[row][col].getPrice();
                eventArray[row][col] = null;
            }
        }
        return refundTotal;
    }
    public boolean findPair(String custName, double Budget){
        if (Budget < basePrice*2){
            return false;
        }

        for (int row = 0; row < eventArray.length; row++) {
            double rowPrice = basePrice;
            if (row == 0){
                rowPrice*=3;
            } else if (row <=3) {
                rowPrice*=2;
            }
            if (rowPrice*2 > Budget){
                continue;
            }
            for (int col = 0; col < eventArray[0].length; col++) {
                if (col == eventArray[0].length-1){
                    break;
                }
                if (col+1 < eventArray[0].length && eventArray[row][col+1] != null ){
                    col++;
                    continue;
                }
                if (eventArray[row][col] != null ){
                    continue;
                }


                eventArray[row][col] = new Ticket(custName,rowPrice);
                eventArray[row][col+1] = new Ticket(custName,rowPrice);
                return true;
            }
        }
        return false;
    }
}
