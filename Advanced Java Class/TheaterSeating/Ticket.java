
public class Ticket{
    private String name;
    private double price;
    public Ticket(String name, double price){
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
