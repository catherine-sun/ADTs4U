/* BTree.java
 * Catherine Sun
 * Constructs a functional Binary Tree
 *
 * 2n-1 elements
 * tree, subtree
 * siblings, parent, child
 * anscestor, descendant
 * leaf
 * depth, height
 */

//24/25 depth checks for null too late, and should check tmp not root -1

public class BTree{
	private BNode root;
	private final int IN = 0, PRE = 1, POST = 2;

	public BTree(){
		root = null;
	}
//1.
	public int depth(int v) {	//return depth of integer
		return depth(root, v);
	}

    public int depth(BNode tmp, int v) {	//O(lgn)
    	if(v == tmp.getVal()) {	//integer is found
    		return 1;
    	}
    	if(root == null) {
    		return -1;
    	}
    	if(v < tmp.getVal()) {	//integer is left/right of current node
    		tmp = tmp.getLeft();
    	} else {
    		tmp = tmp.getRight();
    	}
    	return depth(tmp, v) + 1;
    }
//2.
    public void display(int order) {	//print nodes in indicated order
    	if(order == IN || root == null) {	//print null here to avoid splicing
    		display();
    		return;
    	}
    	String s = "";
    	if(order == PRE) {
    		s = pre(root);
    	} else if(order == POST) {
    		s = post(root);
    	}
    	System.out.println("{" + s.substring(0, s.length() - 2) + "}");	//remove last comma
    }

    public void display() {
    	System.out.println(toString());	//no parameter = in-order
    }

    public String pre(BNode branch) {
    	if(branch != null) {
			return branch.getVal() + ", " +	//first
				   pre(branch.getLeft()) +
				   pre(branch.getRight());
    	}
    	return "";
    }

    public String post(BNode branch) {
    	if(branch != null) {
			return post(branch.getLeft()) +
				   post(branch.getRight()) +
				   branch.getVal() + ", ";	//last
    	}
    	return "";
    }
//3.
	public int countLeaves() {	//return amount of leaves
		return countLeaves(root);
	}

	public int countLeaves(BNode branch) {
		if(branch == null) {	//end
			return 0;
		}
		if(branch.getLeft() == null && branch.getRight() == null) {	//found a leaf
			return 1;
		}
		return countLeaves(branch.getLeft()) + countLeaves(branch.getRight());	//add for total

	}
//4.
	public int height() {	//O(n) return height of tree
		return height(root);
	}

	public int height(BNode branch) {
		if(branch == null) {	//end
			return 0;
		}
		return Math.max(height(branch.getLeft()), height(branch.getRight()))+1;	//greatest num is height
	}
//5.
	public boolean isAncestor(int a, int b) {	//returns if BNode of val b is a descendant of a
		return find(find(root, a), b) != null;	//BNode of val b = find(BNode of val a, int b)
	}

	public BNode find(BNode branch, int v) {
		if(branch == null || branch.getVal() == v) {	//end of branch or found BNode with indicated val
			return branch;
		}
		if(v > branch.getVal()) {	//integer is left/right of current node
			return find(branch.getRight(), v);
		}
		return find(branch.getLeft(), v);
	}
//6.
	public void delete(int v) {	//O(lgn) remove pointer to BNode with indicated integer and keep following nodes
		BNode tmp;	//store the node to be removed
		if(v == root.getVal()) {
			tmp = root;
			root = null;	//erase
		} else {
			BNode p = findParent(root, v);
			if(p == null) {	//no parent = node with value v does not exist
				return;
			}
			if(p.getLeft() != null && v == p.getLeft().getVal()) {
				tmp = p.getLeft();
				p.setLeft(null);	//erase
			} else {
				tmp = p.getRight();
				p.setRight(null);	//erase
			}
		}
		add(tmp.getLeft());	//add back values following the removed node
		add(tmp.getRight());
	}

	public void add(BNode branch) {
		if(branch == null) {	//end
			return;
		}
		add(branch.getVal());	//add value
		add(branch.getLeft());	//recurse left&right
		add(branch.getRight());
	}

	public BNode findParent(BNode branch, int v) {
		if(branch == null) {	//end
			return branch;
		}
		if(branch.getLeft() != null && v == branch.getLeft().getVal()) {	//left child is the node
			return branch;
		}
		if(branch.getRight() != null && v == branch.getRight().getVal()) {	//right child is the node
			return branch;
		}
		if(v > branch.getVal()) {	//node is to the left/right of the current node
			return findParent(branch.getRight(), v);
		}
		return findParent(branch.getLeft(), v);	//recurse left
	}
//7.
/* fully-balanced: all nulls same depth
 * balanced: nulls on last two depths
 * unbalanced: nulls on more than two depths
 */
	public boolean isBalanced() {	//true when height of null pointers differ by less than two
		return height(root) - heightMin(root) < 2;
	}

	public int heightMin(BNode branch) {
		if(branch == null) {
			return 0;
		}
		return Math.min(height(branch.getLeft()), height(branch.getRight()))+1;	//lowest num is minimum height
	}
//8.
	public void add(BTree tree) {
		if(tree.getRoot() == null) {	//added tree is empty
			return;
		}
		if(root == null) {	//added tree is not empty and current tree is empty
			root = tree.getRoot();
		}
		add(root, tree.getRoot());
	}
//
	public BNode getRoot(){return root;}

	public void add(int n){
		BNode tmp = new BNode(n);
		if(root==null){
			root = tmp;
		}
		else{
			add(root, tmp);
		}
	}

	public void add(BNode branch, BNode tmp){	//O(lgn)
		if(tmp.getVal() > branch.getVal()){
			if(branch.getRight()== null){
				branch.setRight(tmp);
			}
			else{
				add(branch.getRight(),tmp);
			}
		}
		else if(tmp.getVal() < branch.getVal()){
			if(branch.getLeft()==null){
				branch.setLeft(tmp);
			}
			else{
				add(branch.getLeft(), tmp);
			}
		}
	}

	@Override
	public String toString(){	//no parameters
		String ans = stringify(root);
		if(ans!=""){
			ans = ans.substring(0,ans.length()-2);
		}
		return "{"+ans+"}";
	}

	public String stringify(BNode branch){
		if(branch != null){
			return stringify(branch.getLeft()) +
				   branch.getVal() + ", " +
				   stringify(branch.getRight());
		}
		return "";
	}
}