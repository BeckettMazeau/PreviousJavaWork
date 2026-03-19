package TwoDArrarys;

public class Potato {
    private int weight;
    private String strain;
    public Potato(String strain){
        this.strain = strain;
        weight = 1;
    }

    public int getWeight() {
        return weight;
    }

    public String getStrain() {
        return strain;
    }
    public void water(){
        if (Math.random()>0.5){
            weight++;
        }
    }
}
