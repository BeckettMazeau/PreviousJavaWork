public class NestedLoops {
    public static void main(){
        //Will do a total of 35 times
        for(int line = 1; line <= 5; line++){
            for (int num =1; num <=7;num++){
                System.out.println(num);
            }
        }
        
        for(int line = 1; line <= 5; line++){
            for (int num =1; num <=7;num++){
                System.out.print("$");
            }
            System.out.println("");
        }
        
        for(int line = 1; line <= 5; line++){
            for (int num =1; num <=line;num++){
                System.out.print("$");
            }
            System.out.println("");
        }
        
    }
}