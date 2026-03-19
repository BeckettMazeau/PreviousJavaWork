import java.util.ArrayList;

public class Test2 implements Runnable{
    ArrayList<ArrayList<Integer>> list;
    int bound1;
    int bound2;
    public Test2(ArrayList<ArrayList<Integer>> list, int bound1, int bound2){
        this.list = list;
        this.bound1 = bound1;
        this.bound2 = bound2;

    }
    @Override
    public void run() {
        for (int x = bound1; x < bound2; x++) {
            for (int y = 0; y < 143; y++) {
                list.get(x).add(Integer.valueOf(y));
            }
        }
    }
}