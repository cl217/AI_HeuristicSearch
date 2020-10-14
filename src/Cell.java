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
	* TODO:
	* Returns distance for hValue
	* Not sure which distance calculation to use..
	*/
	private double calculateDistance(int[] from, int[] to) {
		double distance = 0.0;
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
	
	
	
	
	public int y() {
		return coordinate[1];
	}
	public int x() {
		return coordinate[0];
	}
}
