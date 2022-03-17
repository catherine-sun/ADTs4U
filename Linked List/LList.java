/**
 * @(#)LList.java
 *
 *
 * @author Catherine Sun
 * @version 1.00 2020/1/31
 *
 * Constructs functional linked lists and doubly linked lists
 */


class LList {
	private LNode head, tail;	//points at first and last node

    public LList() {
    	head = null;
    	tail = null;
    }
//Q1
    public void push(int n) {	//new integer becomes head
    	LNode tmp = new LNode(null, n, head);
    	head = tmp;
    }

    public int pop() {	//remove first node and return first node's value
    	LNode tmp = head;
    	head = head.getNext();
    	return tmp.getVal();
    }
//Q2
    public void add(int n) {	//new integer becomes head
    	LNode tmp = new LNode(null, n, head);
    	if(head == null) {
    		tail = tmp;	//also becomes tail if empty
    	} else {
    		head.setPrev(tmp);	//becomes old head's proceeding value
    	}
    	head = tmp;
    }

    public void enqueue(int n) {	//new integer becomes tail
    	LNode tmp = new LNode(tail, n, null);
    	if(tail == null) {
    		head = tmp;	//also becomes head if empty
    	} else {
    		tail.setNext(tmp);	//becomes old tail's following value
    	}
    	tail = tmp;
    }

    public int dequeue() {	//remove first node and return first node's value
    	LNode tmp = head;
    	head = tmp.getNext();	//2nd element is new head
    	head.setPrev(null);		//new head has no prev
    	return tmp.getVal();
    }
//Q3    
    public void delete(int n) {	//remove first instance of specified value n
    	LNode tmp = head;
    	while(tmp != null) {
    		if(tmp.getVal() == n) {
    			delete(tmp);
    			return;
    		}
    		tmp = tmp.getNext();
    	}
    }
    
    public LNode deleteAt(int n) {	//remove node and return node's value at position n
    	LNode tmp = head;
    	for(int i = 0; i < n; i++) {
    		tmp = tmp.getNext();
    	}
    	delete(tmp);
    	return tmp;
    }
    
    public void delete(LNode tmp) {	//remove node
    	if(tmp.getPrev() == null) {
    		if(tmp.getNext() == null) {	//only element
    			head = null;
    			tail = null;
    		} else {	//first element
    			head = tmp.getNext();	//2nd element is new head
    			head.setPrev(null);
    		}
    	} else {
    		if(tmp.getNext() == null) {	//last element
    			tail = tmp.getPrev();	//2nd last element is new tail
    			tail.setNext(null);
    		} else {	//between
    			tmp.getPrev().setNext(tmp.getNext());	//change proceeding element's next node
    			tmp.getNext().setPrev(tmp.getPrev());	//change following element's prev node
    		}
    	}
    	
    }
//Q4	-original submission had ascending from head to tail
	public void sortedInsert(int n) {	//add new node in descending order (head to tail)
		if(head == null) {	//empty
			add(n);
			return;
		}
		LNode tmp = head;
		while(tmp != null) {
			
			if(n >= tmp.getVal()) {	//compare values
				
				if(tmp == head) {	//first element
					add(n);
				} else {	//between prev and tmp
					LNode node = new LNode(tmp.getPrev(), n, tmp);
					tmp.getPrev().setNext(node);	//change proceeding element's next node
					tmp.setPrev(node);	//change following element's prev node
				}
				return;
			}
			
			if(tmp == tail) {	//last element
				enqueue(n);
				return;
			}
			
			tmp = tmp.getNext();
		}
	}
//Q5
    public void removeDuplicates() {	//keep first instance each value
    	LNode a = head;
    	while(a != null) {
    		LNode b = a.getNext();	//doesn't include the node value that is being checked
    		while(b != null) {
    			if(a.getVal() == b.getVal()) {
    				delete(b);
    			}
    			b = b.getNext();
    		}
    		a = a.getNext();
    	}
    }
//Q6    
    public void reverse() {	//reverse order
    	LNode tmp = head;
    	head = tail;	//flip head and tail
    	tail = tmp;
    	while(tmp != null) {
    		if(tmp == head) {	//new head has no prev node
    			tmp.setNext(tmp.getPrev());
    			tmp.setPrev(null);
    		} else if(tmp == tail) {	//new tail has no next node
    			tmp.setPrev(tmp.getNext());	
    			tmp.setNext(null);
    		} else {	//flip proceeding and following nodes
    			LNode n = tmp.getNext();
    			tmp.setNext(tmp.getPrev());
    			tmp.setPrev(n);
    		}		
    		tmp = tmp.getPrev();
    	}
    }
//Q7    
    public LList clone() {	//create and return copy of list
    	LList list = new LList();
    	LNode tmp = head;
    	while(tmp != null) {
    		list.enqueue(tmp.getVal());	//FI
    		tmp = tmp.getNext();
    	}
    	return list;
    }
    
    @Override
    public String toString() {
    	LNode tmp = head;
    	String ans = "";
    	while(tmp != null) {
    		ans += tmp.getVal() + " ,";
    		tmp = tmp.getNext();
    	}
    	if(head != null) {
    		ans = ans.substring(0, ans.length() - 2);
    	}    	
    	return "[" + ans + "]";
    }
}