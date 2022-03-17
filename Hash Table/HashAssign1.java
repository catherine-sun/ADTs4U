/**
 * @(#)HashTest.java
 *
 *
 * @author 
 * @version 1.00 2020/3/5
 *
 * Displays possible words made from specified 8 letters
 */
import java.util.*;
import java.io.*;
public class HashAssign1 {
	public static HashTable<String> dict = new HashTable<String>();	//table of possible words
	public static ArrayList<String> words = new ArrayList<String>();	//permutations
	public static String lets;

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		load();
		lets = "";
        while(lets.length() == 0) {
        	String ln = kb.nextLine().toLowerCase();
    		for(char ch : ln.toCharArray()) {
    			if(lets.length() < 8 && ch > 96 && ch < 123) {	//check if input has exceeded 8 and are letters
    				lets += Character.toString(ch);
    			}
    		}
        }
		words = permutations();	//get permutations
        for(String s : words) {
        	if(dict.contains(s)) {
        		System.out.println(s);	//display if the word is in the dictionary
        	}
        }
    }

	public static ArrayList<String> permutations() {
		ArrayList<String> arr = new ArrayList<String>();
		permutations(arr, "", lets);
		return arr;
	}

	public static ArrayList<String> permutations(ArrayList<String> arr, String sofar, String left) {
		if(left.equals("")) {
			arr.add(sofar);
		} else {
			for(int i = 0; i < left.length(); i++) {
				String next = left.substring(0, i) + left.substring(i + 1);
				permutations(arr, sofar + left.charAt(i), next);
			}
		} return arr;
	}

    public static void load() {	//Read from file
		try {
	    	Scanner inFile = new Scanner(new BufferedReader(new FileReader("dictionary.txt")));
			while(inFile.hasNext()) {
	    		dict.add(inFile.nextLine());
	    	}
			inFile.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
    }
}
