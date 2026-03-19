
public class Problem2 {

    public static void main() {
        int x = 20000;
        int counter = 0;
        while (x > 0) {
            x /= 10;
            counter++;
        }
        System.out.println(counter);
    }
}
