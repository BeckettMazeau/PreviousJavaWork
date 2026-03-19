package Hashing;

public class Hashmaps {
    public static void main(String[] args) throws Exception{
        /*Hash function - tells us what index this element is
        * SUPPOSED to be at
        *
        * let's say I have an array of dates
        * 12/31/1989
        *
        * hash function:
        * (m+d+y)%10
        *
        * for example, 1/1/2000
        * if I run this through the hash function,
        * 1+1+2000=2002
        * 2002%10=2
        * so it goes in slot/index 2
        * when I want to look for this date, I do the exact
        * same thing! No searching required, just a simple,
        * quick equation, with O(1)!
        *
        * if we are checking if something is there,
        * we do our one time calculation, and we immediately know
        * if it's there or not.
        *
        * However, hash has a problem of "collisions". What
        * if something already exists at x index and we
        * try to put something in?
        * we can put arrays in each slot but then we are back to
        * searching.
        *
        * One answer is we can increase what we mod by, however,
        * there is always a possibility of collisions. The answer
        * is to increase the number of slots until in practice it's not a problem
        * anymore, but the only 100% way to fix this is to have arrays inside each slot
        * But the best way is to change the function. If a collision is
        * detected, increase the number of slots, and move everything to new slots.
        *
        * often the most simple hashmaps solve this problem by
        * having the hash function: binarydata% 2^(some arbitrary number of x)
        *
        * The tradeoff with hashmaps is you may get a collision, and you also have
        * a massive amount of wasted space, but you save a massive amount of time.
        *  */
    }
}
