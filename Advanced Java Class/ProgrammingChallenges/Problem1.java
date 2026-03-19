public class Problem1{
    public static void main(){
        /*
         * P1: Find the x-th power of n (i.e. nx) for two arbitrary positive numbers int n and int x. (Note that you may not use Math.pow() for this, even if you know how - I want you to do it using a loop.)
Testing: if x is 3 and n is 4, your answer should be 64, because 43 = 64
More test data: 28 = 256, 94 = 6561, 10243 = 1073741824

         */
        int x = 4;
        int n = 9;
        final int nI= n;
        for(int i = x; i >1;i--){
            n *= nI;
        }
        System.out.println(n);
    }
}