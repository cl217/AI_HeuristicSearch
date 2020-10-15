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
		distance = Math.abs(fx- tx) + Math.abs(fy - ty);
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
