/*
 * Introduction to AI
 * Assignment 1: Heuristic Search
 * 
 * Made by:
 * Cindy Lin
 * Amielyn Musa
 * Sanidhi B
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Main {

	
	static final int nRows = 120; //120
	static final int nCols = 160; //160
	static final int nHardTerrainRegion = 8; //8
	static final int hardTerrainRegionSize = 31; //31
	static final int nHighways = 4; //4
	static final int highwayBlocks = 20; //20
	static final int minHighwayLength = 100; //100
	static final int blockedCells = 3840; //3840
	static final int startWithin = 20; //20
	static final int endWithin = 20; //20
	static final int minGoalDistance = 100; //100
	
	/** Generated or loaded from file */
	static ArrayList<int[]> hardTerrainCenter = new ArrayList<int[]>();
	public static Cell [][] map = new Cell[nRows][nCols];
	public static int[] start = new int[2]; // (x,y)
	public static int[] goal = new int[2];  // (x,y)

	static int heuristic = -1;
	
	/** MAIN **/
	public static void main (String[] args) {
		
		GUI gui = new GUI();
		gui.setVisible(true);
		
		//heuristic = 5;
		/*
		String[] arr = {"A", "B", "C", "D", "E"};
		//HeuristicSearch search = null;
		SequentialASearch search = null;
		double avgTime = 0;
		double avgRuntime = 0;
		double avgLength = 0;
		double avgNodes = 0;
		double avgMemory = 0;
		
		for(String str : arr ) {
			for(int i = 1; i <= 10; i++) {
				intializeFromFile("C:\\Users\\Cindy\\Desktop\\Maps\\Map"+str +"\\map"+str+i+".txt");
				
				//search = new UniformCostSearch();
				//search = new A_Search();
				//search = new WeightedASearch(1.25);
				//search = new WeightedASearch(1.25, 2);
				search = new SequentialASearch(2, 1.25);
				search.search();
				
				avgTime += search.time;
				avgRuntime += search.runtime;
				avgLength += Main.getCell(Main.goal[0], Main.goal[1]).seqG[search.solution];
				avgNodes += search.nodesExpanded;
				avgMemory += search.memory;
				
				System.out.print("("+str+ i + ") ");
				System.out.println(	"Time: " + search.time + ", Runtime: " + search.runtime + ", Length: " + Math.round(Main.getCell(Main.goal[0], Main.goal[1]).seqG[search.solution]*100.0)/100.0 + ", Nodes Expanded: "  + search.nodesExpanded + ", Memory: " + search.memory);
			}
		}
		avgTime = avgTime/50;
		avgRuntime = avgRuntime/50;
		avgLength = avgLength/50;
		avgNodes = avgNodes/50;
		avgMemory = avgMemory/50;
		
		System.out.print("\n" + "(Average) ");
		System.out.println(	"Time: " + avgTime + ", Runtime: " + avgRuntime + ", Length: " + Math.round(avgLength*100.0)/100.0 + ", Nodes Expanded: "  + avgNodes + ", Memory: " + avgMemory);
		*/
	}
	
	
	/* Done: Description - Page 3 of PDF */
	public static void intializeFromFile(String filePath) {
	    try {
	        File file = new File(filePath);
	        Scanner sc = new Scanner(file);
	        
	        //read start and goal coordinates
	        String[] arr = sc.nextLine().split(",");
	        start[0] = Integer.parseInt(arr[0]);
	        start[1] = Integer.parseInt(arr[1]);
	        arr = sc.nextLine().split(",");
	        goal[0] = Integer.parseInt(arr[0]);
	        goal[1] = Integer.parseInt(arr[1]);
	        
	        //read hard terrain centers
	        for(int i = 0; i < nHardTerrainRegion; i++) {
		        arr = sc.nextLine().split(",");
		        hardTerrainCenter.add(new int[] {Integer.parseInt(arr[0]), Integer.parseInt(arr[1])});
	        }
	    
	        int y = 0;
	        while (sc.hasNextLine()) {
	          String line = sc.nextLine();
	          for(int x = 0; x < line.length(); x++) {
	        	  map[y][x] = new Cell(line.charAt(x), new int[] {x, y}) ;
	        	  
	          }
	          y++;
	        }
	        
	        sc.close();
	      } catch (FileNotFoundException e) {
	        e.printStackTrace();
	      }
	}
	
	/*DONE: Decription - Page 3 of PDF*/
	public static void outputToFile(String filePath) {
        try {
			FileWriter writer = new FileWriter(filePath);
			writer.write(start[0]+","+start[1]+"\n");
			writer.write(goal[0]+","+goal[1]+"\n");
			for( int[]arr : hardTerrainCenter ) {
				writer.write(arr[0]+","+arr[1]+"\n");
			}
			for(int i = 0; i < nRows; i++) {
				for(int k = 0; k < nCols; k++) {
					writer.write(map[i][k].c);
				}
				writer.write("\n");
			}
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/** helper for makeHighway() */
	
	public static boolean isValidHighway(int x, int y, HashMap<Integer, ArrayList<Integer>> highwayPath ) {
		if( x < 0 || x >= nCols) {
			return false;
		}
		if( y < 0 || y >= nRows) {
			return false;
		}
		if( map[y][x].c == 'a' || map[y][x].c =='b' ) {
			return false;
		}
		if( highwayPath.containsKey(y) && highwayPath.get(y).contains(x)) {
			return false;
		}
		return true;
	}
	
	
	/** helper for intialize()
	 * @param start : int[] for starting position
	 * @param direction : 1-up, 2-down, 3-left, 4-right	
	 * @return null if invalid path, else int[] for ending position
	 */
	public static int[] makeHighway(int[] start, int direction, HashMap<Integer, ArrayList<Integer>> highwayPath) {
		
		if( direction == 1 || direction == 2 ) { //going vertical
			int x = start[0];
			int yStart = (direction == 1)? start[1]-highwayBlocks+1 : start[1];
			if( yStart < 0) { yStart = 0; }
			int yEnd = (direction == 1)? start[1]+1 : start[1]+highwayBlocks;
			if(yEnd >= nRows) { yEnd = nRows; }
			if(yEnd < 0 ) { yEnd = 0; }
			for(int y = yStart; y < yEnd; y++) { //check path
				if(!isValidHighway(x, y, highwayPath)) {
					return null;
				}
			}
			for(int y = yStart; y < yEnd; y++ ) { //add path to map
				if(!highwayPath.containsKey(y)) {
					ArrayList<Integer> list = new ArrayList<Integer>();
					list.add(x);
					highwayPath.put(y, list );
				}else {
					highwayPath.get(y).add(x);
				}
			}
			if(direction == 1) { 
				return new int[] {x, yStart};
			}else {
				return new int[] {x, yEnd-1};
			}
		}
		
		if( direction == 3 || direction == 4 ) { //going horizontal
			int y = start[1];
			int xStart = (direction == 3)? start[0]-highwayBlocks+1 : start[0];
			if(xStart < 0) { xStart = 0; }
			int xEnd = (direction == 3)? start[0]+1 : start[0]+highwayBlocks;
			if(xEnd >= nCols) { xEnd = nCols; }
			for(int x = xStart; x < xEnd; x++) { //check path
				if(!isValidHighway(x, y, highwayPath)) {
					return null;
				}
			}
			for(int x = xStart; x < xEnd; x++) {
				if(!highwayPath.containsKey(y)) {
					ArrayList<Integer> list = new ArrayList<Integer>();
					list.add(x);
					highwayPath.put(y, list);
				}else {
					highwayPath.get(y).add(x);
				}
			}
			if(direction == 3) {
				return new int[] {xStart, y};
			}else {
				return new int[] {xEnd-1, y};
			}
 		}
		
		return null;
	}
	
	/** DONE: Generates map with terrain and start and goal position */
	
	public static void initialize(){
		
		hardTerrainCenter = new ArrayList<int[]>();
		map = new Cell[nRows][nCols];
		start = new int[2]; // (x,y)
		goal = new int[2];  // (x,y)
		

		//initialize with all unblocked cells and their h value
		for(int y = 0; y < nRows; y++) {
			for(int x = 0; x < nCols; x++) {
				map[y][x] = new Cell('1', new int[] {x, y});
			}
		}
		//System.out.println("Unblocked cells generated");
		
		//select region around  random (x, y) for hard terrain 
		for(int i = 0; i < nHardTerrainRegion; i++) {
			//TODO: make sure (randX,randY) is not repeated?
			int randY = ThreadLocalRandom.current().nextInt(0, nRows);
			int randX = ThreadLocalRandom.current().nextInt(0, nCols);
			hardTerrainCenter.add(new int[] {randX, randY});
			for(int m = randY-hardTerrainRegionSize; m < randY+hardTerrainRegionSize; m++ ) {
				for(int n = randX-hardTerrainRegionSize; n < randX+hardTerrainRegionSize; n++) {
					if( m >= 0 && m < nRows && n >= 0 && n < nCols) {
						Random rand = new Random();
						boolean isHard = rand.nextBoolean();
						if(isHard) {
							map[m][n].c = '2';
						}
					}
				}
			}
		}
		//System.out.println("Hard terrain generated");
		
		//select 4 paths for highways
		int validHighways = 0;
		while( validHighways < nHighways ) {
			HashMap<Integer, ArrayList<Integer>> highwayPath = new HashMap<Integer, ArrayList<Integer>>();
			int[] pos = null; //{x, y}
			int direction = -1;
			while(pos == null) {
				direction = ThreadLocalRandom.current().nextInt(1, 5); //1-up, 2-down, 3-left, 4-right
				if( direction == 1 || direction == 2) { //start at x side
					int yPos = (direction == 1)? nRows-1 : 0;
					pos = makeHighway(new int[] {ThreadLocalRandom.current().nextInt(0, nCols), yPos}, direction, highwayPath);
				}else if( direction == 3 || direction == 4) { //start at y side
					int xPos = (direction == 3)? nCols-1 : 0;
					pos = makeHighway(new int[] {xPos, ThreadLocalRandom.current().nextInt(0, nRows)}, direction, highwayPath);
				}
			}
			
			boolean isDeadEnd = false;
			while(pos[0] != 0 && pos[1] != 0 && pos[0] != nCols-1 && pos[1] != nRows-1) {

				boolean[] dirValid = {true, true, true, true};
				
				//60% no (21-100), 20% yes(1-20)
				int changeDir = ThreadLocalRandom.current().nextInt(1, 101);
				int[] startPos = new int[2];
				if( changeDir <= 20) {
					if(direction == 1 || direction == 2) {
						direction = ThreadLocalRandom.current().nextInt(3, 5); //x direction
					}else {
						direction = ThreadLocalRandom.current().nextInt(1, 3); //y direction
					}
				}
				
				if(direction == 1 || direction == 2) {
					startPos[0] = pos[0];
					startPos[1] = (direction == 1)? pos[1]-1 : pos[1]+1;
				}else {
					startPos[0] = (direction == 3)? pos[0]-1 : pos[0]+1;
					startPos[1] = pos[1];
				}
				
				int[] newPos = makeHighway(startPos, direction, highwayPath);
				if( newPos != null ) {
					pos = newPos;
				}else {
					dirValid[direction-1] = false;
					int deadCount = 0;
					for(int i = 0; i < 4; i++ ) {
						if(dirValid[i] == true) {
							deadCount++;
						}
					}
					if(deadCount >= 3) {
						isDeadEnd = true;
						break;
					}
				}
			}
			
			if(isDeadEnd) {
				continue;
			}
			
			int highwayLength = 0;
			for( int i : highwayPath.keySet() ) {
				highwayLength += highwayPath.get(i).size();
			}
			if( highwayLength >= minHighwayLength) {
				validHighways++;
				//System.out.println("Highway generated (" + validHighways + ")");
				//key = y; value = x
				for( int y : highwayPath.keySet() ) {
					ArrayList<Integer> list = highwayPath.get(y);
					for(int x : list) {
						if(map[y][x].c == '1') {
							map[y][x].c = 'a';
						}else if( map[y][x].c == '2') {
							map[y][x].c = 'b';
						}
					}
				}
			}
		}
		
		int nBlocked = 0;
		while(nBlocked < blockedCells) {
			int randX = ThreadLocalRandom.current().nextInt(0, nCols); 
			int randY = ThreadLocalRandom.current().nextInt(0, nRows);
			if(map[randY][randX].c != 'a' && map[randY][randX].c != 'b' && map[randY][randX].c != '0') {
				map[randY][randX].c = '0';
				nBlocked++;
			}
		}
		
		//System.out.println("Blocked cells generated");
		
		newStartGoal();
		
		

	        	
		System.out.println("Map generated");
		

		
	}
	
	public static void newStartGoal() {
		
		//select start: 1-right, 2-left, 3-top, 4-bottom
		do {
			int startSide = ThreadLocalRandom.current().nextInt(1, 5);
			switch(startSide) {
				case 1: //right
					start[0] = ThreadLocalRandom.current().nextInt(0, startWithin);
					start[1] = ThreadLocalRandom.current().nextInt(0, nRows);
					break;
				case 2: //left
					start[0] = ThreadLocalRandom.current().nextInt(nCols-startWithin, nCols);
					start[1] = ThreadLocalRandom.current().nextInt(0, nRows);
					break;
				case 3: //top
					start[0] = ThreadLocalRandom.current().nextInt(0, nCols);
					start[1] = ThreadLocalRandom.current().nextInt(0, startWithin);
					break;
				case 4: //bottom
					start[0] = ThreadLocalRandom.current().nextInt(0, nCols);
					start[1] = ThreadLocalRandom.current().nextInt(nRows-startWithin, nRows);
					break;
			}
		}while(getCell(start[0], start[1]).c == '0'); //make sure start is not blocked
		System.out.println("Start generated: " + "(" + start[0] + "," + start[1] + ")");
		
		
		//select goal: 1-right, 2-left, 3-top, 4-bottom
		do {
			int goalSide = ThreadLocalRandom.current().nextInt(1, 5);
			switch(goalSide) {
				case 1: //right
					goal[0] = ThreadLocalRandom.current().nextInt(0, startWithin);
					goal[1] = ThreadLocalRandom.current().nextInt(0, nRows);
					break;
				case 2: //left
					goal[0] = ThreadLocalRandom.current().nextInt(nCols-startWithin, nCols);
					goal[1] = ThreadLocalRandom.current().nextInt(0, nRows);
					break;
				case 3: //top
					goal[0] = ThreadLocalRandom.current().nextInt(0, nCols);
					goal[1] = ThreadLocalRandom.current().nextInt(0, startWithin);
					break;
				case 4: //bottom
					goal[0] = ThreadLocalRandom.current().nextInt(0, nCols);
					goal[1] = ThreadLocalRandom.current().nextInt(nRows-startWithin, nRows);
					break;
			}
		}while(getCell(goal[0], goal[1]).c == '0' || getCell(goal[0], goal[1]).calculateDistanceTo(start) < minGoalDistance); //make sure start is not blocked
		System.out.println("Goal generated: " + "(" + goal[0] + "," + goal[1] + ")");
	}
	
	 public static Cell getCell( int x, int y) {
			return map[y][x];
	 }	
}
