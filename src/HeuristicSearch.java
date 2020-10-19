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
	public int runtime = 0;
	public int memory = 0;
	public long time = 0;
	public int nodesExpanded = 0;
	
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
	 * @return ArrayList of Cells = path from start to end
	 */
	public ArrayList<Cell> search(){
		long startTime = System.currentTimeMillis();
		
		HashMap<Cell, Cell> parent = new HashMap<Cell, Cell>();
		ArrayList<Cell> visited = new ArrayList<Cell>();
		ArrayList<Cell> path = new ArrayList<Cell>();
		
		queue.add(Main.getCell(Main.start[0], Main.start[1]));
		memory++;
		
		while(queue.size() > 0) {

			Cell c = queue.poll();
			visited.add(c);
			memory++;
			
			if( isGoal(c) ) {
				path.add(c);
				Cell backtrack = parent.get(c);

				do{
					path.add(backtrack);
					backtrack = parent.get(backtrack);
				}while(!isStart(backtrack));
				path.add(backtrack);
				break;
			}
			
			ArrayList<Cell> succ = succ(c);
			nodesExpanded++;
			for(Cell c2 : succ) {
				if( !visited.contains(c2) && c2.c != '0' ) {
					if(queue.contains(c2)) {
						double oldTcost = c2.gValue; //save old tCost
						c2.gValue = c2.gValue - parent.get(c2).gValue; //Set to default value
						double newTcost = c.gValue + getTransitionCost(c, c2); //get new tCost
						if(newTcost < oldTcost) { //if new cost < oldcost
							c2.gValue = newTcost; //update cost
							c2.fValue = c2.gValue + c2.fValue;
							parent.put(c2, c); //update parent
						}else {
							c2.gValue = oldTcost;
						}
					}else {
						c2.gValue = c.gValue + getTransitionCost(c, c2);
						c2.fValue = c2.gValue+c2.hValue;
						queue.add(c2);
						memory++;
						parent.put(c2, c);
						memory++;
					}
				}
				runtime++;
			}
		}
		
		time = System.currentTimeMillis() - startTime;
		
		
		return path;
	}
	
	//Max possible vertices is 8 (PDF 3)
	public static ArrayList<Cell> succ(Cell c) {
		
		ArrayList<Cell> successors = new ArrayList<Cell>();
				
		for(int y = c.y()-1; y<=c.y()+1; y++) {
			for(int x = c.x()-1; x<=c.x()+1; x++ ) {
				if( (y==c.y() && x==c.x()) || y < 0 || x < 0 || y > Main.nRows-1 || x > Main.nCols-1 ) {
					//System.out.println("("+x+", "+y+") skipped");
					continue;
				}
				Cell c2 = Main.getCell(x, y);
				successors.add(c2);
			}
		}
		return successors;
	}
	
	
	private boolean isGoal(Cell c) {
		if(c.x() == Main.goal[0] && c.y() == Main.goal[1]) {
			return true;
		}
		return false;
	}
	private boolean isStart(Cell c) {
		if(c.x() == Main.start[0] && c.y() == Main.start[1]) {
			return true;
		}
		return false;
	}
	
	public static void resetValues() {
		for(int y = 0; y < Main.nRows; y++) {
			for(int x = 0; x < Main.nCols; x++) {
				Main.map[y][x].gValue = 0;
				Main.map[y][x].hValue = 0;
				Main.map[y][x].fValue = 0;
			}
		}
	}
	
	public static void setHValue() {
		for(int y = 0; y < Main.nRows; y++) {
			for(int x = 0; x < Main.nCols; x++) {
				Cell c = Main.getCell(x, y);
				c.hValue = c.calculateDistanceTo(Main.getCell(Main.goal[0], Main.goal[1]).coordinate);
			}
		}
	}
	

	
	
	public long getTime() {
		return time;
	}
	public int getRuntime() {
		return runtime;
	}
	public int getMemory() {
		return memory;
	}
	public int getNodesExpanded() {
		return nodesExpanded;
	}

}
	
