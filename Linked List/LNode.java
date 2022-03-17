/**
 * @(#)LNode.java
 *
 *
 * @author
 * @version 1.00 2020/1/31
 */

class LNode {
	private int val;
	private LNode next, prev;	//self-referential data structure

	public LNode(LNode p, int v, LNode n) {
		prev = p;
		val = v;
		next = n;
	}

	public int getVal() { return val; }
	public LNode getNext() { return next; }
	public LNode getPrev() { return prev; }
	
	public void setVal(int v) { val = v; }
	public void setNext(LNode n) { next = n;}
	public void setPrev(LNode p) { prev = p;}

    public String address (){
		return ""+hashCode();
    }
    
    public String toString (){
    	String ans = ""+val;
    	if(next==null){
    		ans += ": null";
    	}
    	else{
    		ans += ": @"+next.address();
    	}
		return "<" + ans + ">";
    }

}
