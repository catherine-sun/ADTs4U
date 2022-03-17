/**
 * @(#)HashTable.java
 *
 *
 * @author Catherine Sun
 * @version 1.00 2020/3/5
 *
 * Constructs a functional Hash Table that stores T
 */
import java.util.*;

class HashTable<T> {
	private int numElements;	//amount of T
	private ArrayList<LinkedList<T>> table;	//the table
	private double maxLoad;

    public HashTable() {
    	numElements = 0;
    	maxLoad = 0.75;
    	emptyList(10);
    }
    
    public void add(T val) {	//add an object to the table
    	int spot = Math.abs(val.hashCode())%table.size();
    	if(table.get(spot) == null) {
    		table.set(spot, new LinkedList<T>());
    	}
    	table.get(spot).add(val);	//add T to a linked list in its spot on the array list
    	numElements++;	//amount of T increases by 1
    	
    	if((double)numElements/table.size() >= maxLoad) {	//add empty list when load >= maxLoad
    		resize();
    	}
    }
    
    public void remove(T val) {	//remove an object from the table
    	int spot = Math.abs(val.hashCode()%table.size());
    	if(table.get(spot) != null) {
    		if(table.get(spot).contains(val)) {	//check if the object is in the linked list
	    		table.get(spot).remove(val);
	    		numElements --;	//amount of T decreases by 1
    		}
    	}
    }
    
    public boolean contains(T val) {	//check if T is in the table
    	int spot = Math.abs(val.hashCode()%table.size());
    	if(table.get(spot) != null) {	//true if there is a linked list at the spot
    		return table.get(spot).contains(val);	//checks if the linked list has T
    	}
    	return false;
    }
    
    public T get(int hc) {	//returns the value at the specified hash code
    	int spot = Math.abs(hc%table.size());
    	if(table.get(spot) != null) {
    		for(T val : table.get(spot)) {	//loop through values in the linked list
    			if(val.hashCode() == hc) {	//check for identical hash codes
    				return val;
    			}
    		}
    	}
    	return null;
    }
    
    public double getLoad() {	//returns the current load which indicates how full the table is
    	return (double)numElements/table.size();
    }
    
    public void setMaxLoad(double percent) {	//checks if new max load is usable and changes the maxLoad variable accordingly
    	maxLoad = percent >= 0.1 && percent <= 0.8 ? percent : maxLoad;
    }
    
    public void setLoad(double percent) {	//checks if new load is usable and resizes the table accordingly
    	if(percent >= 0.1 && percent <= maxLoad) {
    		double d = numElements/percent;	//new size of table
    		resize(d);
    	}
    }
    
    public void resize() {
    	resize(table.size()*10);
    }
    
    public void resize(double newSize) {
    	ArrayList<LinkedList<T>> tmp = table;
    	emptyList((int)newSize);
    	numElements = 0;
    	for(LinkedList<T> lst : tmp) {
    		if(lst != null) {
    			for(T val : lst) {
    				add(val);
    			}
    		}
    	}
    }
    
    public ArrayList<T> toArray() {	//returns an array list of T
    	ArrayList<T> arr = new ArrayList<T>();
    	for(LinkedList<T> lst : table) {	//loop through linked lists in table
    		if(lst != null) {	
    			for(T val : lst) {	//loop through T in linked lists
    				arr.add(val);
    			}
    		}
    	}
    	return arr;
    }
    
    public void emptyList(int n) {
    	table = new ArrayList<LinkedList<T>>();
    	for(int i = 0; i < n; i++) {
    		table.add(null);
    	}
    }
    
    @Override
    public String toString() {
    	String ans = "";
    	for(LinkedList<T> lst : table) {
    		if(lst!= null) {
    			for(T val : lst) {
    				ans += val + ", ";
    			}
    		}
    	}
    	if(ans != "") {
    		ans = ans.substring(0, ans.length() - 2);
    	}
    	return "{" + ans + "}";
    }
    
}