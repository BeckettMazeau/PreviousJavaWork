 

public class StringMethods3 {
    public static void main (String [] args ) throws Exception{
        //When a string is initiated it goes to the "string pool"
        String string1 = "string";
        //If the same exact string is created again the string
        //is actually just referenced to the original string
        String string2 = "string";
        //is basically just stringn2=string1, because
        //java is assigning a reference to the original string
        System.out.println(string1==string2);
        //If we use 'new' then it will create a new reference,
        String string3 = new String("string");
        //Because these are the same string but different
        //OBJECTS, the comparison opperator will not recognize
        //them as the same
        System.out.println(string1==string3);
        //See, this prints out false
        //This is why we use the equivilancy operator
        System.out.println(string1.equals(string3));
        //Even if both strings are in the java heap, and are
        //equivilant, they are not the same string, and so are
        //not the same when asked by the == operator.
        String string4 = new String("string");
        System.out.println("jsdom: " + (string3==string4));
        string4 = "STRING";
        System.out.println(string1.toLowerCase()==string4.toLowerCase());
        System.out.println(string4.indexOf("y"));
        string2 = string1;
        string4 = string2;
        System.out.println(string4==string1);

    }
}
