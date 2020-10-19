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
public class SequentialASearch {
	
	ArrayList<PriorityQueue<Cell>> open;
	ArrayList<ArrayList<Cell>> closed;
	ArrayList<HashMap<Cell, Cell>> bp;

	double w1; 
	double w2;
	
	public long time;
	public long runtime;
	public int solution;
	public long nodesExpanded;
	public long memory;
	
	
	public SequentialASearch(double w1, double w2){
		open = new ArrayList<PriorityQueue<Cell>>();
		closed = new ArrayList<ArrayList<Cell>>();
		bp = new ArrayList<HashMap<Cell, Cell>>();
		this.w1 = w1;
		this.w2 = w2;
		
		time = System.currentTimeMillis();
		runtime = 0;
		solution = -1;
		nodesExpanded = 0;
		memory = 0;
		
		setSeqHValue();
	}

	public ArrayList<Cell> search(){
		
		int n = 5;
		
		for( int i = 0; i < n; i++) {
			final int k = i;
			bp.add(new HashMap<Cell, Cell>());
			PriorityQueue<Cell> queue = new PriorityQueue<Cell>(5, Comparator.comparing(Cell -> Cell.seqKey(k)));
			open.add(queue); //14
			closed.add(new ArrayList<Cell>()); //15
			Cell start = Main.getCell(Main.start[0], Main.start[1]);
			start.seqG[i] = 0; //16
			Cell goal = Main.getCell(Main.goal[0], Main.goal[1]);
			goal.seqG[i] = Double.MAX_VALUE; //16
			bp.get(i).put(start, null); //17
			bp.get(i).put(goal, null); //17
			
			start.seqKey[i] = key(start, i); //18
			open.get(i).add(start); //18
			memory += 3;
		}
		
		
		while(open.get(0).peek().seqKey(0) < Integer.MAX_VALUE) {
			for(int i = 1; i < n; i++) {
				if( open.get(i).peek().seqKey(i) <= w2*open.get(0).peek().seqKey(0) ) {
					if(Main.getCell(Main.goal[0], Main.goal[1]).seqG[i] <= open.get(i).peek().seqKey(i))  {
						if( Main.getCell(Main.goal[0], Main.goal[1]).seqG[i] < Double.MAX_VALUE) {
							return getPath(i);
						}
					}else {
						Cell c = open.get(i).poll();
						expandState(c, i);
						closed.get(i).add(c);
						memory++;
					}
				}else {
					if( Main.getCell(Main.goal[0], Main.goal[1]).seqG[0] <= open.get(0).peek().seqKey(0) ) {
						if(Main.getCell(Main.goal[0], Main.goal[1]).seqG[0] < Double.MAX_VALUE) {
							return getPath(0);
						}
					}else {
						Cell c = open.get(0).poll();
						expandState(c, 0);
						closed.get(0).add(c);
						memory++;
					}
				}
			}
		}
		
		return null;
	}
	
	
	private ArrayList<Cell> getPath(int i){
		ArrayList<Cell> path = new ArrayList<Cell>();
		Cell c = Main.getCell(Main.goal[0], Main.goal[1]);
		path.add(c);
		
		do {
			c = bp.get(i).get(c);
			path.add(c);
		}while(!(c.coordinate[0] == Main.start[0] && c.coordinate[1] == Main.start[1]));
		
		solution = i;
		time = System.currentTimeMillis() - time;
		
		return path;
	}
	
	
	public double key(Cell s, int i ) {
		return s.seqG[i] + w1* s.seqH[i]; //2
	}
	
	public void expandState(Cell c, int i) {
		nodesExpanded++;
		open.get(i).remove(c); //4
		memory--;
		ArrayList<Cell> succ = succ(c);
		for(Cell s : succ) { //5
			runtime++;
			if(!closed.get(i).contains(s) && !open.get(i).contains(s)) { //6
				s.seqG[i] = Double.MAX_VALUE; //7
				bp.get(i).put(s, null); //7
			}
			if(s.seqG[i] > (c.seqG[i] + cost(c, s))) { //8
				s.seqG[i] = c.seqG[i] + cost(c,s); //9
				bp.get(i).put(s, c); //9
				memory++;
				if(!closed.get(i).contains(s)) { //10
					if( open.get(i).contains(s) ) { //11
						open.get(i).remove(s); //11
						memory--;
					}
					s.seqKey[i] = key(s, i); //11
					open.get(i).add(s); //11
					memory++;
				}
			}
		}
	}
	
	
	public static ArrayList<Cell> succ(Cell c) {
		
		ArrayList<Cell> successors = new ArrayList<Cell>();
				
		for(int y = c.y()-1; y<=c.y()+1; y++) {
			for(int x = c.x()-1; x<=c.x()+1; x++ ) {
				if( (y==c.y() && x==c.x()) || y < 0 || x < 0 || y > Main.nRows-1 || x > Main.nCols-1 || c.c == '0') {
					continue;
				}
				Cell c2 = Main.getCell(x, y);
				successors.add(c2);
			}
		}
		return successors;
	}
	
	public double cost(Cell c1, Cell c2) {
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
	
	public static void setSeqHValue() {
		for(int y = 0; y < Main.nRows; y++) {
			for(int x = 0; x < Main.nCols; x++) {
				for(int i = 1; i <=5; i++) {
					Cell c = Main.getCell(x, y);
					Main.heuristic = i;
					c.seqH[i-1] = c.calculateDistanceTo(Main.getCell(Main.goal[0], Main.goal[1]).coordinate);
				}
			}
		}
	}
}
