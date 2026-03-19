package CustomerClass;

public class CustomerClass {
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private double discount;
    private double rewardsBalance;
    private double spentMonth;
    private double spentAll;
    public CustomerClass(String nameEntered, String addressEntered,String emailEntered,String phoneEntered) {
        name = nameEntered;
        address=addressEntered;
        phoneNumber=phoneEntered;
        email= emailEntered;
        discount = 0;
        rewardsBalance = 0;
        spentAll = 0;
        spentMonth = 0;
    }
    public void rechargeRewardsBalance(double rewardsBalance){
        spentMonth = 0;
        rewardsBalance = this.rewardsBalance;
    }
    public double makePurchase(double price){
        price *= discount;
        if(price >rewardsBalance){
            price -= rewardsBalance;
            rewardsBalance = 0;

        } else if (price < rewardsBalance){
            rewardsBalance -= price;
            price = 0;
        } else {
            price = 0;
            rewardsBalance = 0;
        }

        spentMonth += price;
        spentAll += price;
        return price;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setDiscount(double discount){
        this.discount= 1-(discount/100);
    }
    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
