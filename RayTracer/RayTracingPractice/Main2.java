import java.util.ArrayList;

public class Main2 {
    public static void main(String[] args) throws Exception{
        
        ArrayList<Thread> threadArrayList = new ArrayList<Thread>();
        ArrayList<ArrayList<Integer>> idList = new ArrayList<ArrayList<Integer>>();
        for (int x = 0; x < 133; x++) {
            idList.add(new ArrayList<Integer>());
            for (int y = 0; y < 143; y++) {
                idList.get(x).add(y);
            }
        }
        for(ArrayList<Integer> id: idList){
            for(int x = 0; x < id.size(); x++){
                System.out.print(id.get(x));
            }
            System.out.println();
        }
        System.out.println("\n\n");
        ArrayList<ArrayList<Integer>> newList = new ArrayList<ArrayList<Integer>>();
        for (int x = 0; x < 133; x++) {
            newList.add(new ArrayList<Integer>());
        }
        int threads = Runtime.getRuntime().availableProcessors();
        if (133%threads==0){
            int perThread = 133/threads;
            int current = perThread;
            int last= 0;
            for (int i = 0; i < threads; i++) {
                Test2 athing;
                if (i == threads-1){
                    athing = new Test2(newList,last,current-1);
                }else {
                    athing = new Test2(newList,last,current);
                }

                Thread thread = new Thread(athing);
                threadArrayList.add(thread);
                thread.start();
                last = current;
                current += perThread;
            }
        } else {
            int perThread = 133/threads;
            int current = perThread;
            int last= 0;
            for (int i = 0; i < threads; i++) {
                Test2 athing;
                if (i == threads-1){
                     athing = new Test2(newList,last,current+(133%threads)-1);
                }else {
                    athing = new Test2(newList,last,current);
                }
                Thread thread = new Thread(athing);
                threadArrayList.add(thread);
                thread.start();
                last = current;
                current += perThread;
            }
        }
        for (Thread thread : threadArrayList){
            thread.join();
        }
        System.out.println("x:"+newList.size());
        System.out.println("y:"+newList.get(1).size());
        for(ArrayList<Integer> id: newList){
            for(int x = 0; x < id.size(); x++){
                System.out.print(id.get(x));
            }
            System.out.println();
        }
    }
}