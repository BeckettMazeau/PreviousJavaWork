package Aliasing;

import DogClassProject.Dog;

public class AliasingNotes {
    public static void main(String [] args) throws Exception{
        /*Aliasing refers to the idea that the same object can
        * have multiple names. Aliasing is essentially used for
        * the sake of memory usage.
        *
        * How memory works:
        *       We have two different sections of memory,
        *           Method memory (which we will call "the stack")
        *           and Object memory (Which we will call "the Heap")
        *
        * */

        int x =5;
        double y = 4.0;
        String str = "fdjalkfdjafjdl";
        /*The space for this memory will not be allocated until we actually
        * run the method. Once we do, these variables will have space
        * allocated into method memory/the stack. It will allocate only the nessecary
        * space for the designated variable type. However, strings are not so easy,
        * we don't know how long a string is, because it is made up of character primitives.
        * It can get longer/larger in terms of 'space.' The way we solve this is we
        * have object memory/the heap. The string is placed in heap memory, and
        * the str variable in the method stack actually just contains the ADDRESS
        * to the point in the heap where the string is contained.
        * If you change str, like below*/
        str = "tuesday";
        /*we will create an ENTIRELY NEW OBJECT IN THE HEAP, AND CREATE A POINTER TO IT.
        * THE TUESDAY WILL STILL EXISTS, AT LEAST UNTIL JAVA DOES AUTOMATIC GARBAGE COLLECTION
        * WHERE JAVA FINDS OBJECTS WITH NO REFERENCES AND REMOVES THEM AND FREES UP
        * THE ALLOCATED SPACE*/

        /*if i create a new string, str2, and set it equal to str, it will look at whatever
        * str is currently pointed at */
        String str2 = str;
        /*str2 now equals "tuesday", if we change either str or str2, the other
        * will not be effected*/
        str = "hi";

        /*if i set an object to the same value as another, java will attempt to
        * find the object and just reference it instead of creating a new one.*/
        str2 = "hi";

        //First version of aliasing: using the = sign, which changes the "pointer"

        Dog pepper = new Dog("pepper");
        pepper.birthday();
        pepper.birthday();
        /*The pepper object will lie in the heap, since
        * it is an object, however, since it contains a string,
        * the string INSIDE the object will actually
        * just be a POINTER TO A STRING OBJECT CONTAINING THE NAME
        * */
        //now, let's try something else
        Dog d2 = pepper;
        //This DOESN'T CREATE A NEW DOG OBJECT, this just
        //sets d2 as a reference to the pepper object.
        d2.birthday();
        //now pepper is 3!!!

        /*2nd other version of aliasing is when we
        * pass it as a parameter to another method*/
        party(d2);

    }
    public static void party(Dog d){
        System.out.println("Happy birthday "+d.getName());
        d.birthday();
        //here whatever we do will affect the object passed into the method.
        //this is not the same case with a primitive
        //this is only the case with objects, there is
        //no such thing as a pointer to a primitive.
        //if I do
        d = new Dog("fdjaklfjdl");
        //then now we will be referncing a different, new dog object.
    }
}
