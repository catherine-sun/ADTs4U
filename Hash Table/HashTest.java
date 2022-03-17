/**
 * @(#)HashTest.java
 *
 *
 * @author 
 * @version 1.00 2020/3/5
 */

public class HashTest {

	public static void main(String[] args) {
        String name = "Jana";
        System.out.println(name.hashCode());	//23012558
        
        HashTable<Integer> nums = new HashTable<Integer>();
        for(int i = 0; i < 20; i++) {
        	int x = (int)(Math.random()*10000);
        	nums.add(x);
        }
        System.out.println(nums);
    }
}
