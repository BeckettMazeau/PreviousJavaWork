package MultiThreading;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception{
        ArrayList<Thread> threadArrayList = new ArrayList<Thread>();
        ArrayList<Integer> idList = new ArrayList<Integer>();
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            Test athing = new Test(idList,i);
            Thread thread = new Thread(athing);
            threadArrayList.add(thread);
            thread.start();
        }
        for (Thread thread : threadArrayList){
            thread.join();
        }
        for (int num : idList){
            System.out.println(num);
        }
    }
}
