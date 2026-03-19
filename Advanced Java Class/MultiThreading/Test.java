package MultiThreading;

import java.util.ArrayList;

public class Test implements Runnable{
    ArrayList<Integer> list;
    int id;
    public Test(ArrayList<Integer> Integer, int id){
        this.list = list;
        this.id = id;
    }
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            list.add(id);
        }
    }
}
