import java.util.Arrays;
/*
 * Introduction to AI
 * Assignment 1: Heuristic Search
 * 
 * Made by:
 * Cindy Lin
 * Amielyn Musa
 * Sanidhi B
 */

public class Cell {

	public char c;
	
	//Calculated using Manhattan Distance? Diagonal Distance? other?
	public double hValue; //how far away from end
	public double gValue; //cost of path away from start
	public double fValue; //g+h
	public double dis;
	int[] coordinate = new int[2];
	
	public Cell( double hValue ) {
		this.hValue = hValue;
	}
	
	public Cell( char c, int[] coordinate) {
		this.c = c;
		this.coordinate = coordinate;		
		hValue = calculateDistance(coordinate, Main.goal);
	}
	
	/*
	* DONE
	* Returns distance for hValue
	* Not sure which distance calculation to use..
	*/
	private double calculateDistance(int[] from, int[] to) {
		double distance;
		int fx= from[0];
		int fy= from[1];
		int tx= to[0];
		int ty= to[1];
		System.out.println("Select a Heuristic. 0:Manhattan 1:Euclidian 2:Chebyshev 3:Average of Euclidian & Manhattan 4:Manhattan/2");
		int heuristic = Integer.parseInt(args[0]);
		
		if(heuristic==0) {
			distance = Math.abs(fx- tx) + Math.abs(fy-ty);
		}
		else if(heuristic==1) {
			distance= Math.sqrt((Math.pow(Math.abs(fx- tx),2) + Math.pow(Math.abs(fy - ty),2)));
		}
		else if(heuristic==2) {
			int cx= Math.abs(fx- tx);
			int cy= Math.abs(fy-ty);
			if (cx>cy) {
			distance= cx;
			}
			else {
				distance= cy;
			}
		}
		else if (heuristic==3) {
			distance = ((Math.abs(fx- tx) + Math.abs(fy-ty))+(Math.sqrt((Math.pow(Math.abs(fx- tx),2) + Math.pow(Math.abs(fy - ty),2))))/2);
		}
		else if (heuristic==4) {
			distance= (((Math.abs(fx- tx) + Math.abs(fy-ty))/2));
		}
		else {
			System.out.println("Error");
		}
		return distance;
	}
	
	
	public double hValue() {
		return hValue;
	}
	public double fValue() {
		return gValue+hValue;
	}
	public double gValue() {
		return gValue;
	}
	public double weightedA() {
		return gValue + hValue * Main.weight;
	}
	
	
	public int x() {
		return coordinate[0];
	}
	public int y() {
		return coordinate[1];
	}
}
