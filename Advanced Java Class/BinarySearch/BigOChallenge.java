package BinarySearch;

public class BigOChallenge {
    public static void main(String [] args){
        //we refer to transfers as O()
        /*For a hard drive/file size of n, an internet transfer
        * would be O(n)
        * pronounced "order n"
        * here, n is the number of transfers.
        *
        * with a pigeon transfer the transfer would be O(1)
        *
        *
        *
        * for an array size of n
        *
        * how long would it take to linear search through the array
        * looking for a specific value?
        *   O(n)
        * how long would it take to print out every possible
        * pairing of numbers from that array?
        * O(n^2)
        * e.g. if the array was [1,2,3], I want to print out:
        * 1,1
        * 1,2
        * 1,3
        * 2,3
        *
        * for binary search n ~= 2^(checks)-1
        * checks = log base 2 (n)
        * here we're ignoring plus or minus one because at such
        * large numbers we don't care that much about precision, we just
        * need to roughly establish relationship
        *
        * linear search
        *   O(n)
        * binary search
        *   O(log base 2 n)
        * n = 1,000,000?
        * linear = 1,000,000 checks
        * binary search = 20 checks
        *
        * RULES FOR BIG O NOTATION:
        * Different inputs --> Different variables (e.g. lets say im testing for equal
        * elements in two different arrays, to see what values overlap. Maybe we say first
        * array is size n, second array is size x, and then algorithm is size O(n*x))
        *
        * 2. different steps get added together
        * e.g lets say i run through those two arrays and print them out
        * O(n)+O(x)=O(n+x)
        *
        * 3. drop all constants
        e.g. run through an array and find min and max
        O(n) + O(n) = O(2n) = O(n)
        *
        4. drop all non-dominant terms
        O(n + n^2) = O(n^2)
        O(3n^3 + 5n^2 + 5000n + 40sqrt(n)) = O(n^3)
        O(n^4)
        * public int bogoSearch(int target){
        * int i = (int)(Math.random()*grades.length);
        * while(grades[i] !=target){
        * i = (int)(Math.random()*grades.length)
        * }
        * return i;
        * }
        * Big O looks at worst case situation, so in this case, you might never
        * land on the correct number. It's unlikely, but it could happen.
        * So it's O(???)
        * */
    }
}
