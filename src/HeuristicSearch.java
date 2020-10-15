import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
		ArrayList<Cell> visited = new ArrayList<Cell>();
		
		Cell start = new Cell('S', Main.start);
		
		path.add(start);
		
		Cell goal = new Cell('G', Main.goal);
		
		return path;
	}
	
	//Max possible vertices is 8 (PDF 3)
	public static ArrayList<Cell> succ(Cell s) {
		
				ArrayList<Cell> successors = new ArrayList<Cell>();
		
				int x1 = s.coordinate[0];
				int y1 = s.coordinate[1];
				
				
				int maxEdges = 8;
				
				//BROKEN <-- have to make sure Cells are not out of bounds first
				
				Cell n1 = new Cell(Main.map[x1 - 1][y1 + 1].c, new int[] {x1 - 1, y1+1});						
				Cell n2 = new Cell(Main.map[x1 - 1][y1].c, new int[] {x1 - 1, y1});
				Cell n3 = new Cell(Main.map[x1 - 1][y1 - 1].c, new int[] {x1 - 1, y1 - 1});
				Cell n4 = new Cell(Main.map[x1][y1 + 1].c, new int[] {x1, y1 + 1});
				Cell n5 = new Cell(Main.map[x1][y1 - 1].c, new int[] {x1, y1 - 1});
				Cell n6 = new Cell(Main.map[x1 + 1][y1 + 1].c, new int[] {x1 + 1, y1 + 1});
				Cell n7 = new Cell(Main.map[x1 + 1][y1].c, new int[] {x1 + 1, y1});
				Cell n8 = new Cell(Main.map[x1 + 1][y1 - 1].c, new int[] {x1 + 1, y1 - 1});
				
				System.out.print("Neighbor 1: (" + n1.x() + "," + n1.y() + ")" );
				System.out.print("Neighbor 2: (" + n2.x() + "," + n2.y() + ")" );				
				System.out.print("Neighbor 3: (" + n3.x() + "," + n3.y() + ")" );
				System.out.print("Neighbor 4: (" + n4.x() + "," + n4.y() + ")" );
				System.out.print("Neighbor 5: (" + n5.x() + "," + n5.y() + ")" );
				System.out.print("Neighbor 6: (" + n6.x() + "," + n6.y() + ")" );
				System.out.print("Neighbor 7: (" + n7.x() + "," + n7.y() + ")" );
				System.out.print("Neighbor 8: (" + n8.x() + "," + n8.y() + ")" );
				
				successors.add(n1);
				successors.add(n2);
				successors.add(n3);
				successors.add(n4);
				successors.add(n5);
				successors.add(n6);
				successors.add(n7);
				successors.add(n8);
				
				for(int i = 0; i < maxEdges; i++) {
					
					if(successors.get(i).c == 'b' || successors.get(i).x() == -1  || successors.get(i).y() == -1
							|| successors.get(i).x() == 50 || successors.get(i).y() == 30) {
						
						successors.remove(i);
					}
					
					
				}
				
				
				return successors;
	}

//|| n[i][1] == -1 || n[i][0] == 50 || n[i][1] == 30
}
	
