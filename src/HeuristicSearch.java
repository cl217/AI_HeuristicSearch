import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
/*
 * Introduction to AI
 * Assignment 1: Heuristic Search
 * 
 * Made by:
 * Cindy Lin
 * Amielyn Musa
 * Sanidhi B
 */


public abstract class HeuristicSearch {

	PriorityQueue<Cell> queue; 
	
	/* Page 2 of PDF
 	- The cost of transitioning between two regular unblocked cells is 1 if the agent moves horizontally or vertically and sqrt(2) if the agent moves diagonally. 
 	- moving horizontally or vertically between two hard to traverse cells has a cost of 2;
	- moving diagonally between two hard to traverse cells has a cost of sqrt(8);
	- moving horizontally or vertically between a regular unblocked cell and a hard to traverse cell(in either direction) has a cost of 1.5;
	- moving diagonally between a regular unblocked cell and a hard to traverse cell (in either direction) has a cost of (sqrt(2)+sqrt(8))/2;
	
 	- Highway: if we are starting from a cell that contains a highway and we are moving
		horizontally or vertically into a cell that also contains a highway, the cost of this motion is four times
		less than it would be otherwise (i.e., 0.25 if both cells are regular, 0.5 if both cells are hard to traverse
		and 0.375 if we are moving between a regular unblocked cell and a hard to traverse cell). 
 */
	public double getTransitionCost(Cell c1, Cell c2) {
		char terrain1 = c1.c;
		char terrain2 = c2.c;
		boolean isDiagonal = false;
		int[] from = c1.coordinate;
		int[] to = c2.coordinate;
		if(from[0] != to[0] && from[1] != to[1]) {
			isDiagonal = true;
		}
		
		double output = -1;
		
		if( terrain1 == '1' ) {
			switch (terrain2) {
				case '1': output = (isDiagonal)? Math.sqrt(2) : 1; break;
				case '2': output = (isDiagonal)? ((Math.sqrt(2)+Math.sqrt(8))/2) : 1.5; break;
				case 'a': output = (isDiagonal)? Math.sqrt(2)*0.25 : 1*0.25; break;
				case 'b': output = (isDiagonal)? ((Math.sqrt(2)+Math.sqrt(8))/2)*0.25 : 1.5*0.25; break;
			}
		}
		if( terrain1 == '2' ) {
			switch (terrain2) {
				case '1': output = (isDiagonal)? ((Math.sqrt(2)+Math.sqrt(8))/2) : 1.5; break;
				case '2': output = (isDiagonal)? Math.sqrt(8) : 2; break;
				case 'a': output = (isDiagonal)? ((Math.sqrt(2)+Math.sqrt(8))/2)*0.25 : 1.5*0.25; break;
				case 'b': output = (isDiagonal)? Math.sqrt(8)*0.25 : 2*0.25; break;
			}
		}
		if( terrain1 == 'a' ) {
			switch (terrain2) {
				case '1': output = (isDiagonal)? Math.sqrt(2)*0.25 : 1*0.25; break;
				case '2': output = (isDiagonal)? ((Math.sqrt(2)+Math.sqrt(8))/2)*0.25 : 1.5*0.25; break;
				case 'a': output = (isDiagonal)? Math.sqrt(2)*0.25 : 1*0.25; break;
				case 'b': output = (isDiagonal)? ((Math.sqrt(2)+Math.sqrt(8))/2)*0.25 : 1.5*0.25; break;
			}
		}
		if( terrain1 == 'b' ) {
			switch (terrain2) {
				case '1': output = (isDiagonal)? ((Math.sqrt(2)+Math.sqrt(8))/2)*0.25 : 1.5*0.25; break;
				case '2': output = (isDiagonal)? Math.sqrt(8)*0.25 : 2*0.25; break;
				case 'a': output = (isDiagonal)? ((Math.sqrt(2)+Math.sqrt(8))/2)*0.25 : 1.5*0.25; break;
				case 'b': output = (isDiagonal)? Math.sqrt(8)*0.25 : 2*0.25; break;
			}
		}
		
		return output;
	}

	
	/**
	 * TODO: Algorithm to find shortest path here
	 * @return ArrayList of Cells = path from start to end
	 */
	public ArrayList<Cell> search(){
		ArrayList<Cell> path = new ArrayList<Cell>();
		//...
		return path;
	}



}
