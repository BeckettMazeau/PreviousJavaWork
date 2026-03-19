import java.util.ArrayList;
public class Delimiters{
private String openDel;
private String closeDel;

public Delimiters(String open, String close){
openDel = open;
closeDel = close;

	}

public ArrayList<String> getDelimitersList (String[] tokens){
	ArrayList<String> delimitersList = new ArrayList();
	for(int i = 0; i<tokens.length;i++){
	if(tokens[i].equals(openDel) || tokens[i].equals(closeDel)){
	delimitersList.add(tokens[i]);
	}
	}
	return delimitersList;
}

public boolean isBalanced(ArrayList<String> delimiters){
int opens = 0;
int closes = 0;
	for(int i = 0; i<delimiters.size(); i++){
if(closes > opens){
return false;
}
		if((delimiters.get(i)).equals(openDel)){
opens++;
}
if((delimiters.get(i)).equals(closeDel)){
closes++;
}

	}
if(closes==opens){
	return true;
}
return false;
}

}
