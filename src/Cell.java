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
	
	
	public Cell( char c, int[] coordinate) {
		this.c = c;
		this.coordinate = coordinate;		
	}
	
	/*
	* DONE
	* Returns distance for hValue
	* Not sure which distance calculation to use..
	*/
	public double calculateDistanceTo(int[] to) {
		double distance = -1;
		int fx= coordinate[0];
		int fy= coordinate[1];
		int tx= to[0];
		int ty= to[1];
		int heuristic = Main.heuristic;
		//System.out.println("Select a Heuristic. 1:Manhattan 2:Euclidian 3:Chebyshev 4:Average of Euclidian & Manhattan 5:Manhattan/2");
		//int heuristic = Integer.parseInt(args[0]);
		
		if(heuristic==1) { //Manhattan
			distance = Math.abs(fx- tx) + Math.abs(fy-ty);
		}
		else if(heuristic==2) { //Euclidian
			distance= Math.sqrt((Math.pow(Math.abs(fx- tx),2) + Math.pow(Math.abs(fy - ty),2)));
		}
		else if(heuristic==3) {
			int cx= Math.abs(fx- tx);
			int cy= Math.abs(fy-ty);
			if (cx>cy) {
			distance= cx;
			}
			else {
				distance= cy;
			}
		}
		else if (heuristic==4) { //Avg(Euclid+Manhattan)
			distance = ((Math.abs(fx- tx) + Math.abs(fy-ty))+(Math.sqrt((Math.pow(Math.abs(fx- tx),2) + Math.pow(Math.abs(fy - ty),2))))/2);
		}
		else if (heuristic==5) { //Manhattan/2
			distance= (((Math.abs(fx- tx) + Math.abs(fy-ty))/2));
		}
		else {
			distance = Math.abs(fx- tx) + Math.abs(fy-ty); //default to manhattan
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
