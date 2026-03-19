public class TheaterDriver
{
    public static void main(){
        boolean error = false;
        Event concert = new Event(8,4,10.00); //8 rows, 4 columns, $10 base price
        
        System.out.println("Before any ticket sales: should be 8 rows, 4 columns, and no customers yet");
        concert.printChart();
        
        //testing a very large budget: should get a front-row seat
        boolean sold = concert.findSeat("John D Rockefeller", 5000.00);
        Ticket frontRowCheck = concert.checkTicket(0,0);
        if(frontRowCheck == null){
            System.out.println("ERROR: High-budget customer did not get a front row seat");
            error = true;
        }
        else if(!frontRowCheck.getName().equals("John D Rockefeller") || frontRowCheck.getPrice() != 30.00){
            System.out.println("ERROR: First Ticket object was created with the wrong name or price");
            error = true;
        }
        else if(!sold){
            System.out.println("ERROR: findSeat returned false even though a ticket actually was sold");
            error = true;
        }
        
        //testing a medium budget: should get a seat in row 1 (not the front row, 0)
        concert.findSeat("Yuri Gagarin", 25.00);
        Ticket secondRowCheck = concert.checkTicket(1,0);
        if(secondRowCheck == null){
            System.out.println("ERROR: Medium-budget customer did not get a seat in row=1");
            error = true;
        }
        else if(secondRowCheck.getPrice() != 20.00){
            System.out.println("ERROR: Medium-budget ticket was created with the wrong price");
            error = true;
        }
        
        //testing a low budget: should get a seat in row 4
        concert.findSeat("Jane Doe", 10.00);
        Ticket budgetSectionCheck = concert.checkTicket(4,0);
        if(budgetSectionCheck == null){
            System.out.println("ERROR: Low-budget customer did not get a seat in row=4");
            error = true;
        }
        else if(budgetSectionCheck.getPrice() != 10.00){
            System.out.println("ERROR: Low-budget ticket was created with the wrong price");
            error = true;
        }
        
        //testing a budget that can't afford any ticket
        sold = concert.findSeat("Brett Favre", 9.00);
        if(sold){
            System.out.println("ERROR: findSeat sold a ticket to a customer that could not afford the base price");
            error = true;
        }
        
        //testing "sold out" conditions: new 1x1 concert
        Event oneSeat = new Event(1,1,1.00);
        oneSeat.findSeat("Lonely Person",10.00);
        sold = oneSeat.findSeat("A Friend",10.00);
        if(sold){
            System.out.println("ERROR: findSeat sold a ticket even when the venue is sold out");
            error = true;
        }
        
        //testing finding a seat in a row that already has someone in it
        concert.findSeat("Janet Jackson", 5000.00);
        Ticket secondPersonInRow = concert.checkTicket(0,1);
        if(secondPersonInRow == null){
            System.out.println("ERROR: Customer who wants a ticket in a row that already has someone " + 
                "in it did not get the 2nd seat");
            error = true;
        }
        
        System.out.println("\nAfter selling 4 tickets: should be John and Janet in front row; " + 
                "Yuri in second row; Jane in 5th row");
        concert.printChart();
        
        //testing a high-value pair
        sold = concert.findPair("Lando Calrissian", 100.00);
        Ticket pairSeat1 = concert.checkTicket(0,2);
        Ticket pairSeat2 = concert.checkTicket(0,3);
        if(pairSeat1 == null || pairSeat2 == null){
            System.out.println("ERROR: findPair did not correctly find open front-row seats");
            error = true;
        }
        else if(pairSeat1.getPrice() != 30.00 || pairSeat2.getPrice() != 30.00){
            System.out.println("ERROR: findPair assigned the wrong price to front row tickets");
            error = true;
        }
        else if(!sold){
            System.out.println("ERROR: findPair returned false even though 2 tickets actually were sold");
            error = true;
        }
        
        //testing a high-value single except now the front row is sold out
        sold = concert.findSeat("Slim Shady", 100.00);
        Ticket shady = concert.checkTicket(1,1);
        if(shady == null){
            System.out.println("ERROR: findSeat did not correctly find an open second-row seat " +
                "when the front row was full");
            error = true;
        }
        else if(shady.getPrice() != 20.00){
            System.out.println("ERROR: findSeat assigned the wrong price to mid-price tickets " +
                "when the front row was full");
            error = true;
        }
        else if(!sold){
            System.out.println("ERROR: findSeat returned false even though a ticket actually was sold" +
                "when the front row was full");
            error = true;
        }
        
        //testing refund amount: 2nd row should be $20.00
        double refund = concert.refund("Slim Shady");
        if(refund != 20.00){
            System.out.println("ERROR: Refund did not return the correct ticket price paid by the customer");
            error = true;
        }
        if(concert.checkTicket(1,1) != null){
            System.out.println("ERROR: Refund did not remove customer's ticket from the seating chart");
            error = true;
        }
        
        //testing a high-value pair except now the front row is sold out
        sold = concert.findPair("Cloud Strife", 100.00);
        pairSeat1 = concert.checkTicket(1,1);
        pairSeat2 = concert.checkTicket(1,2);
        if(pairSeat1 == null || pairSeat2 == null){
            System.out.println("ERROR: findPair did not correctly find open second-row seats " + 
                "when the front row was full");
            error = true;
        }
        else if(pairSeat1.getPrice() != 20.00 || pairSeat2.getPrice() != 20.00){
            System.out.println("ERROR: findPair assigned the wrong price to mid-price tickets " +
                "when the front row was full");
            error = true;
        }
        else if(!sold){
            System.out.println("ERROR: findPair returned false even though 2 tickets actually were sold" +
                "when the front row was full");
            error = true;
        }
        
        //change base price and test to make sure refund still returns the original amount
        concert.setBasePrice(100.00);
        refund = concert.refund("Yuri Gagarin");
        if(refund != 20.00){
            System.out.println("ERROR: refund returned the wrong ticket price after the base price changed. " + 
                "Make sure you're storing and returning the amount they ACTUALLY paid");
            error = true;
        }
        
        //testing a low-budget pair
        sold = concert.findPair("King Charles III", 200.00);
        pairSeat1 = concert.checkTicket(4,1);
        pairSeat2 = concert.checkTicket(4,2);
        if(pairSeat1 == null || pairSeat2 == null){
            System.out.println("ERROR: findPair did not correctly find back-section seats for a low-budget pair" + 
                " after the base price changed");
            error = true;
        }
        else if(pairSeat1.getPrice() != 100.00 || pairSeat2.getPrice() != 100.00){
            System.out.println("ERROR: findPair assigned the wrong price to back-section tickets " +
                "after the base price changed");
            error = true;
        }
        else if(!sold){
            System.out.println("ERROR: findPair returned false even though 2 tickets actually were sold " + 
                "after the base price changed");
            error = true;
        }
        
        //testing a mid-budget pair except the only 2 free seats in row=1 are not next to each other
        //(they should get pushed to row=2)
        sold = concert.findPair("Daniel Ricciardo", 410.00);
        pairSeat1 = concert.checkTicket(2,0);
        pairSeat2 = concert.checkTicket(2,1);
        if(pairSeat1 == null || pairSeat2 == null){
            System.out.println("ERROR: findPair did not correctly find mid-section seats " + 
                "when row=1 was full (they should get pushed to row=2)");
            error = true;
        }
        else if(pairSeat1.getPrice() != 200.00 || pairSeat2.getPrice() != 200.00){
            System.out.println("ERROR: findPair assigned the wrong price to mid-section tickets " +
                "after the base price changed");
            error = true;
        }
        else if(!sold){
            System.out.println("ERROR: findPair returned false even though 2 tickets actually were sold " + 
                "when row=1 was full");
            error = true;
        }
        
        if(!error){
            System.out.println("ALL TESTS PASSED");
        }
    }
}