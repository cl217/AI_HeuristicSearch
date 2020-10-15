/*
 * Introduction to AI
 * Assignment 1: Heuristic Search
 * 
 * Made by:
 * Cindy Lin
 * Amielyn Musa
 * Sanidhi B
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;


public class Main {

	
	static final int nRows = 30; //120
	static final int nCols = 50; //160
	static final int nHardTerrainRegion = 10; //8
	static final int hardTerrainRegionSize = 2; //31
	static final int nHighways = 2; //4
	static final int highwayBlocks = 5; //20
	static final int minHighwayLength = 30; //100
	static final int blockedCells = 10; //3840
	static final int startWithin = 1; //20
	static final int endWithin = 1; //20
	static final int minGoalDistance = 100; //100
	
	/*
	 	Use �0� to indicate a blocked cell
		Use �1� to indicate a regular unblocked cell
		Use �2� to indicate a hard to traverse cell
		Use �a� to indicate a regular unblocked cell with a highway
	 	Use �b� to indicate a hard to traverse cell with a highway 
	*/
	/** Generated or loaded from file */
	static ArrayList<int[]> hardTerrainCenter = new ArrayList<int[]>();
	static Cell [][] map = new Cell[nRows][nCols];
	static int[] start = new int[2]; // (x,y)
	static int[] goal = new int[2];  // (x,y)

	static double weight = -1;
	
	
	/* TODO */
	public static void shortestPath(int[] start, int[] goal) {
		
	}
	
	/** MAIN **/
	public static void main (String[] args) {


		initialize();
		
		//ArrayList<Cell> successors = HeuristicSearch.succ(map[start[0]][start[1]]);
		
		//TODO: HeuristicSearch search() function has to be implemented
		HeuristicSearch uniformCostSearch = new UniformCostSearch();
		ArrayList<Cell> uniformCostSearch_ShortestPath = uniformCostSearch.search();

		HeuristicSearch aSearch = new A_Search();
		ArrayList<Cell> aSearch_ShortestPath = aSearch.search();

		HeuristicSearch WeightedASearch = new WeightedASearch();
		ArrayList<Cell> WeightedASearch_ShortestPath = WeightedASearch.search();
		
		
		showGUI(uniformCostSearch_ShortestPath); //shows GUI with path highlighted



		//outputToFile("C:\\Users\\Cindy\\Desktop\\mapoutput.txt");
		

		
		
		/*
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
			intializeFromFile(input);
			//intializeFromFile("C:\\Users\\Cindy\\Desktop\\map.txt");
		}
		
		
		input = "";
		while( !input.equals("1") && !input.equals("2") ) {
			System.out.println("Choose:\n (1) Show GUI \n (2) Output to file");
			input = in.nextLine();
		}
		
		if(input.equals("1")) {
			showGUI();
		}else {
			System.out.println("Enter file path:");
			input = in.nextLine();
			outputToFile(input);
			//outputToFile("C:\\Users\\Cindy\\Desktop\\mapoutput.txt");
		}
		
		in.close();
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
	
	public static void showGUI(ArrayList<Cell> path) {
		int bSize = 30;
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(nRows, nCols));
        
        HashMap<JButton, Cell> buttonMap = new HashMap<JButton, Cell>();
        for( int i = 0; i < nRows; i++ ) {
        	for(int k = 0; k < nCols; k++ ) {
        		String text = Character.toString(map[i][k].c);
        		//String text = Double.toString(map[i][k].gValue);
        		//String text = Character.toString(map[i][k].cName);
        		
                JButton button = new JButton(text);
                buttonMap.put(button, map[i][k]);
                button.setFont(new Font("Arial", Font.PLAIN, 10));
                button.setMargin(new Insets(0, 0, 0, 0));
                button.addActionListener(new ActionListener() {
                	@Override
                	public void actionPerformed(ActionEvent e) {
                		Cell c = buttonMap.get(button);
                		System.out.println("g(" + c.gValue  + "), h(" + c.hValue + "), f(" + c.fValue + ")");
                	}
                });
                
                if(i==start[1] && k == start[0] ) {
                	button.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.RED, 3), 
                            BorderFactory.createEmptyBorder(
                                button.getBorder().getBorderInsets(button).top, 
                                button.getBorder().getBorderInsets(button).left, 
                                button.getBorder().getBorderInsets(button).bottom, 
                                button.getBorder().getBorderInsets(button).right)));
                }
                
                if(i==goal[1] && k == goal[0]) {
                	button.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.BLUE, 3), 
                            BorderFactory.createEmptyBorder(
                                button.getBorder().getBorderInsets(button).top, 
                                button.getBorder().getBorderInsets(button).left, 
                                button.getBorder().getBorderInsets(button).bottom, 
                                button.getBorder().getBorderInsets(button).right)));
                }
                
                
                switch(map[i][k].c) {
                	case '0': button.setBackground(Color.BLACK); break; 
                	case '1': button.setBackground(Color.GREEN); break;
                	case '2': button.setBackground(new Color(0x994C00)); break;
                	case 'a': button.setBackground(Color.LIGHT_GRAY); break;
                	case 'b': button.setBackground(Color.GRAY); break;
                }
                
                if(path.contains(map[i][k])) {
                	button.setBackground(Color.YELLOW);
                }
                
                
                button.setPreferredSize(new Dimension(bSize,bSize));
                panel.add(button);
        	}
        }
        
        
        
        JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        container.add(panel);
        JScrollPane scrollPane = new JScrollPane(container);
        f.getContentPane().add(scrollPane);

        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);

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

		/*
			TODO - generate goal[] that is at least minGoalDistance away from start
			The goal is currently hardcoded
		*/
		

		goal[0] = nCols - 1; 
		goal[1] = nRows - 1;
		System.out.println("Goal generated: " + "(" + goal[0] + "," + goal[1] + ")" + " Hardcorded");
		
		

	        	
		System.out.println("Map generated");
		

		
	}
	
	
	 static Cell getCell( int x, int y) {
			return map[y][x];
		}
		
	
	}
