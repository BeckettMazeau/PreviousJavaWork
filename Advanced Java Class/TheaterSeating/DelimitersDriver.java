import java.util.ArrayList;

public class DelimitersDriver
{
    public static void main(){
        boolean hadError = false;
        
        String[] tokens1 = { "(", "x + y", ")", " * 5" };
        String[] tokens2 = { "<q>", "yy", "</q>", "zz", "</q>" };
        
        Delimiters del1 = new Delimiters("(", ")");
        Delimiters del2 = new Delimiters("<q>", "</q>");
        
        ArrayList<String> delList1 = del1.getDelimitersList(tokens1);
        ArrayList<String> delList2 = del2.getDelimitersList(tokens2);
        
        if(delList1.size()!=2 || !delList1.get(0).equals("(") || !delList1.get(1).equals(")")
            || delList2.size()!=3 || !delList2.get(0).equals("<q>")
            || !delList2.get(1).equals("</q>") || !delList2.get(2).equals("</q>")){
                System.out.println("ERROR with getDelimitersList");
                hadError = true;
        }
        
        
        Delimiters del3 = new Delimiters("<sup>", "</sup>");
        
        ArrayList<String> balList1 = new ArrayList<String>();
        balList1.add("<sup>");
        balList1.add("<sup>");
        balList1.add("</sup>");
        balList1.add("<sup>");
        balList1.add("</sup>");
        balList1.add("</sup>");
        
        if(!del3.isBalanced(balList1)){
            System.out.println("ERROR with isBalanced list #1");
            hadError = true;
        }
        
        ArrayList<String> balList2 = new ArrayList<String>();
        balList2.add("<sup>");
        balList2.add("</sup>");
        balList2.add("</sup>");
        balList2.add("<sup>");
        
        if(del3.isBalanced(balList2)){
            System.out.println("ERROR with isBalanced list #2");
            hadError = true;
        }
        
        ArrayList<String> balList3 = new ArrayList<String>();
        balList3.add("</sup>");
        
        if(del3.isBalanced(balList3)){
            System.out.println("ERROR with isBalanced list #3");
            hadError = true;
        }
        
        ArrayList<String> balList4 = new ArrayList<String>();
        balList4.add("<sup>");
        balList4.add("<sup>");
        balList4.add("</sup>");
        
        if(del3.isBalanced(balList4)){
            System.out.println("ERROR with isBalanced list #4");
            hadError = true;
        }
        
        if(!hadError){
            System.out.println("All tests passed");
        }
    }
}