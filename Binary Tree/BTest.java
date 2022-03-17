public class BTest{
	public static void main(String []args){
		BTree oak = new BTree();
		oak.add(45);
		oak.add(5);
		oak.add(23);
		oak.add(164);
		oak.add(73);
		oak.add(15);
		oak.add(48);
		System.out.println(oak);
		
		BTree bush = new BTree();
		bush.add(50);
		bush.add(31);
		bush.add(105);
		bush.add(71);
		bush.add(4);
		System.out.println(bush);
	}
}