package lab1;

import java.util.HashMap;

public class Display {
//	private int bxneg = -12;
//	private int bxpos = 12;
//	private int byneg = -12;
//	private int bypos = 12;
	private HashMap<Point, String> output = new HashMap<Point, String>();
	
	public HashMap<Point, String> getDisplay(){
		return this.output;
	}

	public Display() {
		int x = 0;
		//we need to be mindful that we need a box of obstacles surrounding the board so the agents don't leave the board
		for(int i = x; i<6; i++) {
			output.put(new Point(0, i), "O");
			System.out.print("0");
			x++;
		}
		for(int i = x; i<13; i++) {
			output.put(new Point(0, i), "O");
			System.out.print(" ");
			x++;
		}
		for(int i = x; i<26; i++) {
			output.put(new Point(0, i), "O");
			System.out.print("0");
			x++;
		}
		System.out.println(); 

	}
	
}
