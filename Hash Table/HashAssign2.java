/**
 * @(#)HashAssign2.java
 *
 *
 * @author Catherine Sun
 * @version 1.00 2020/3/5
 *
 * Displays the emotions of bloggers near a location as a coloured pixel
 */
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
public class HashAssign2 extends JFrame implements ActionListener {
	Timer myTimer;
	MapPanel windsor;
	
	public HashAssign2() {
		super("Windsor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		myTimer = new Timer(10, this);	//trigger every 10 ms
		
		windsor = new MapPanel(this);
		add(windsor);
		
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	public void start() {
		myTimer.start();
	}
	
	public void actionPerformed(ActionEvent evt) {
		windsor.repaint();
	}

	public static void main(String[] args) {
		HashAssign2 frame = new HashAssign2();
    }
}

class MapPanel extends JPanel implements MouseListener{
	private HashAssign2 mainFrame;
	private boolean click;
	private Image map;	//map of Windsor
	private int w, h;
	private HashTable<Blogger> blogs;	//table of bloggers
	private ArrayList<Point> display = new ArrayList<Point>();	//points to display emotions
	
	public MapPanel(HashAssign2 m) {
		mainFrame = m;
		click = false;
		addMouseListener(this);
		
		map = new ImageIcon("windsor.png").getImage();
		w = map.getWidth(this);
		h = map.getHeight(this);
		setPreferredSize(new Dimension(w, h));
		
		blogs = new HashTable<Blogger>();
		load();
	}
	
	public void addNotify() {
		super.addNotify();
		requestFocus();
        mainFrame.start();
	}

    public void load() {	//read from file and add to hash table
		try {
	    	Scanner inFile = new Scanner(new BufferedReader(new FileReader("creeper.txt")));
			while(inFile.hasNext()) {
				String[] ln = inFile.nextLine().split(" ");
				int hc = Integer.parseInt(ln[0])*1000 + Integer.parseInt(ln[1]);	//get hash code
				if(blogs.get(hc) == null) {	//no other blogger shares the location yet
					blogs.add(new Blogger(ln));	
				} else {	//add emotion values to the existing blogger
					blogs.get(hc).add(Integer.parseInt(ln[2]), Integer.parseInt(ln[3]), Integer.parseInt(ln[4]));
				}
	    	}
			inFile.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
    }
    
    public void paint(Graphics g) {
		g.drawImage(map, 0, 0, w, h, null);
		if(click) {
			display.add(getPos());	//add location to be displayed
			click = false;	//no draging
		}
		for(Point pos : display) {
			for(int x = pos.x - 10; x < pos.x + 10; x++) {
				for(int y = pos.y - 10; y < pos.y + 10; y++) {	//all points (x, y) in a 20x20 pixel square
					if(Math.pow(x - pos.x, 2) + Math.pow(y - pos.y, 2) <= 100) {	//checks if point is on or within a circle with a 10 pixel radius
						Blogger b = blogs.get(x*1000 + y);
						if(b != null) {
							g.setColor(b.getCol());
							g.fillOval(x, y, 2, 2);	//display emotions
						}
					}
				}
			}
		}
    }
    
    public Point getPos() {	//returns position of mouse pointer
    	Point mouse = MouseInfo.getPointerInfo().getLocation();
    	Point offset = getLocationOnScreen();
    	return new Point(mouse.x - offset.x, mouse.y - offset.y);
    }
	
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}    
    public void mouseClicked(MouseEvent e) {}  
    public void mousePressed(MouseEvent e) { click = true; }
}

class Blogger {
	private int x, y, love, happiness, excitement, numberOfBlogs;	//information in an entry
	
	public Blogger(String[] entry) {
		x = Integer.parseInt(entry[0]);
		y = Integer.parseInt(entry[1]);
		love = Integer.parseInt(entry[2]);
		happiness = Integer.parseInt(entry[3]);
		excitement = Integer.parseInt(entry[4]);
		numberOfBlogs = 1;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	@Override
	public int hashCode() {	//custom hash code
		return x*1000 + y;
	}
	
	public void add(int lov, int hap, int excite) {	//weighted average of the emotions of bloggers at a location
		love = (love*numberOfBlogs + lov)/(numberOfBlogs + 1);
		happiness = (happiness*numberOfBlogs + hap)/(numberOfBlogs + 1);
		excitement = (excitement*numberOfBlogs + excite)/(numberOfBlogs + 1);
		numberOfBlogs++;	//total number of blogs sharing this location
	}
	
	public Color getCol() {	//converts emotions to a colour
		//1.275 is 255/200
		int r = (int)Math.round((love + 100)*1.275);
		int g = (int)Math.round((happiness + 100)*1.275);
		int b = (int)Math.round((excitement + 100)*1.275);
		return new Color(r, g, b);
	}
}