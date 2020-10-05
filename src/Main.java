import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

	
	static final int nRows = 50; //160
	static final int nCols = 50; //120
	static final int nHighways = 4; //4
	static final int highwayBlocks = 5; //20
	static final int minHighwayLength = 50; //100
	static final int blockedCells = 5; //3840
	static final int startWithin = 5; //
	static final int minGoalDistance = 20; //100
	
	/*
	 	Use ’0’ to indicate a blocked cell
		Use ’1’ to indicate a regular unblocked cell
		Use ’2’ to indicate a hard to traverse cell
		Use ’a’ to indicate a regular unblocked cell with a highway
	 	Use ’b’ to indicate a hard to traverse cell with a highway 
	*/
	/** Generated or loaded from file */
	static char [][] map = new char[nRows][nCols];
	static int[] start = new int[2];
	static int[] goal = new int[2];
	
	
	static HashMap<Integer, ArrayList<Integer>> highwayPath = new HashMap<Integer, ArrayList<Integer>>(); //key = y, list = x
	
	
	/* TODO: Page 3 of PDF */
	public static void intitalizeFromFile(String filePath) {
		
	}
	
	/* TODO: Page 3 of PDF*/
	public static void outputToFile() {
		
	}
	
	
	/* TODO: Page 2 of PDF
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
	public static double getTransitionCost(int[] from, int[] to) {
		return 0;
	}
	
	/* TODO */
	public static void shortestPath(int[] start, int[] goal) {
		
	}
	
	
	/** MAIN **/
	public static void main (String[] args) {
		
		Scanner in = new Scanner(System.in);
		String input = "";
		while( !input.equals("1") && !input.equals("2") ) {
			System.out.println("Choose:\n (1) Generate \n (2) Load from file");
			input = in.nextLine();
		}
		
		if(input.equals("1")) {
			initialize();
		}else {
			System.out.println("Enter file path:");
			input = in.nextLine();
			intitalizeFromFile(input);
		}
		in.close();
		
		testPrintMapToFile("C:\\Users\\Cindy\\Desktop\\map.txt");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** helper for makeHighway() */
	public static boolean isValidHighway(int x, int y) {
		if( x < 0 || x >= nCols) {
			return false;
		}
		if( y < 0 || y >= nRows) {
			return false;
		}
		if( map[y][x] == 'a' || map[y][x] =='b' ) {
			return false;
		}
		if( highwayPath.containsKey(y) && highwayPath.get(y).contains(x)) {
			return false;
		}
		return true;
	}
	
	/** DONE
	 * @param start : int[] for starting position
	 * @param direction : 1-up, 2-down, 3-left, 4-right	
	 * @return null if invalid path, else int[] for ending position
	 */
	public static int[] makeHighway(int[] start, int direction) {
		
		if( direction == 1 || direction == 2 ) { //going vertical
			int x = start[0];
			int yStart = (direction == 1)? start[1]-highwayBlocks+1 : start[1];
			if( yStart < 0) { yStart = 0; }
			int yEnd = (direction == 1)? start[1]+1 : start[1]+highwayBlocks;
			if(yEnd >= nRows) { yEnd = nRows; }
			if(yEnd < 0 ) { yEnd = 0; }
			for(int y = yStart; y < yEnd; y++) { //check path
				if(!isValidHighway(x, y)) {
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
				if(!isValidHighway(x, y)) {
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
	
	/** DONE 
	 * Generates map with terrain and start and goal position
	 * **/
	public static void initialize(){
		//initialize with all unblocked cells
		for(char[] row : map) {
			Arrays.fill(row, '1');
		}
		System.out.println("Unblocked cells generated");
		
		//select region around 8 random (x, y) for hard terrain 
		for(int i = 0; i < 8; i++) {
			//TODO: make sure (randX,randY) is not repeated
			int randY = ThreadLocalRandom.current().nextInt(0, nRows);
			int randX = ThreadLocalRandom.current().nextInt(0, nCols);
			int regionSize = 3; //31
			for(int m = randY-regionSize; m < randY+regionSize; m++ ) {
				for(int n = randX-regionSize; n < randX+regionSize; n++) {
					if( m >= 0 && m < nRows && n >= 0 && n < nCols) {
						Random rand = new Random();
						boolean isHard = rand.nextBoolean();
						if(isHard) {
							map[m][n] = '2';
						}
					}
				}
			}
		}
		System.out.println("Hard terrain generated");
		
		//select 4 paths for highways
		int validHighways = 0;
		while( validHighways < nHighways ) {
			highwayPath = new HashMap<Integer, ArrayList<Integer>>();
			int[] pos = null; //{x, y}
			int direction = -1;
			while(pos == null) {
				direction = ThreadLocalRandom.current().nextInt(1, 5); //1-up, 2-down, 3-left, 4-right
				if( direction == 1 || direction == 2) { //start at x side
					int yPos = (direction == 1)? nRows-1 : 0;
					pos = makeHighway(new int[] {ThreadLocalRandom.current().nextInt(0, nCols), yPos}, direction);
				}else if( direction == 3 || direction == 4) { //start at y side
					int xPos = (direction == 3)? nCols-1 : 0;
					pos = makeHighway(new int[] {xPos, ThreadLocalRandom.current().nextInt(0, nRows)}, direction);
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
				
				int[] newPos = makeHighway(startPos, direction);
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
				System.out.println("Highway generated (" + validHighways + ")");
				//key = y; value = x
				for( int y : highwayPath.keySet() ) {
					ArrayList<Integer> list = highwayPath.get(y);
					for(int x : list) {
						if(map[y][x] == '1') {
							map[y][x] = 'a';
						}else if( map[y][x] == '2') {
							map[y][x] = 'b';
						}
					}
				}
			}
		}
		
		int nBlocked = 0;
		while(nBlocked < blockedCells) {
			int randX = ThreadLocalRandom.current().nextInt(0, nCols); 
			int randY = ThreadLocalRandom.current().nextInt(0, nRows);
			if(map[randY][randX] != 'a' && map[randY][randX] != 'b' && map[randY][randX] != '0') {
				map[randY][randX] = '0';
				nBlocked++;
			}
		}
		System.out.println("Blocked cells generated");
		
		//select start: 1-right, 2-left, 3-top, 4-bottom
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
		System.out.println("Start generated: " + "(" + start[0] + "," + start[1] + ")");
		 
		//TODO
		System.out.println("Goal generated: " + "Not Implemented Yet");
		
	}
	
	
	
	/** Just using these for debugging */
	public static void testPrintMap() {
		System.out.println();
		String space = "     ";
		System.out.print(space);
		for(int i = 0; i < nCols; i++) {
			if( i < 10) {
				System.out.print(" "+i+"   ");
			}else if ( i < 100) {
				System.out.print(" "+i+"  ");
			}else{
				System.out.print(" "+i+" ");
			}
		}
		System.out.println();
		System.out.print(space);
		for(int i = 0; i < nCols; i++ ) {
			System.out.print(" ____");
		}
		System.out.println();
		
		for(int i = 0; i < nRows; i++) {
			if( i < 10) {
				System.out.print("   "+i+" ");
			}else if ( i < 100) {
				System.out.print("  "+i+" ");
			}else{
				System.out.print(" "+i+" ");
			}
			
			for(int k = 0; k < nCols; k++) {
				if(map[i][k] == '1' || map[i][k] == '2') {
					System.out.print("|    ");
				}else {
					System.out.print("|" + map[i][k] + "   ");	
				}

			}
			System.out.println("|");
			System.out.print(space);
			for(int k = 0; k < nCols; k++) {
				System.out.print("|____");
			}
			System.out.println("|");
		}
	}
	public static void testPrintMapToFile(String path) {
	    try {
	        FileWriter writer = new FileWriter(path);
			writer.write("\n");
			String space = "     ";
			writer.write(space);
			for(int i = 0; i < nCols; i++) {
				if( i < 10) {
					writer.write(" "+i+"   ");
				}else if ( i < 100) {
					writer.write(" "+i+"  ");
				}else{
					writer.write(" "+i+" ");
				}
			}
			writer.write("\n");
			writer.write(space);
			for(int i = 0; i < nCols; i++ ) {
				writer.write(" ____");
			}
			writer.write("\n");
			
			for(int i = 0; i < nRows; i++) {
				if( i < 10) {
					writer.write("   "+i+" ");
				}else if ( i < 100) {
					writer.write("  "+i+" ");
				}else{
					writer.write(" "+i+" ");
				}
				
				for(int k = 0; k < nCols; k++) {
					if(map[i][k] == '1' || map[i][k] == '2') {
						writer.write("|    ");
					}else {
						writer.write("|" + map[i][k] + "   ");	
					}

				}
				writer.write("|");
				writer.write("\n");
				writer.write(space);
				for(int k = 0; k < nCols; k++) {
					writer.write("|____");
				}
				writer.write("|");
				writer.write("\n");
			}
	        
	        
	        
	        
	        writer.close();
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	}

}
