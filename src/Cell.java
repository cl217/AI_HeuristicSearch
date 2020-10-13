import java.util.Arrays;

public class Cell {
	public char c;
	
	
	public char cName;
	
	//Calculated using Manhattan Distance
	public double hValue; //how far away from end
	public double gValue; //how far away from start
	public double fValue; //g+h
	public boolean isGoal = false;
	public boolean isStart = false;
	int[] coordinate = new int[2];
	
	public Cell( double hValue ) {
		this.hValue = hValue;
	}
	
	public Cell( char c, int[] current) {
		this.c = c;
		this.coordinate = current;
		if(Main.goal[0] == current[0] && Main.goal[1]== current[1]) {
			isGoal = true;
		}
		if(Main.start[0] == current[0] && Main.start[1] == current[1]) {
			System.out.println("start set: " + Arrays.toString(coordinate));
			gValue = 0;
			isStart = true;
		}
		
		hValue = manhattanDistance(current, Main.goal);
	}
	
	private double manhattanDistance(int[] from, int[] to) {
		int x1 = from[0], x2 = to[0], y1 = from[1], y2 = to[1];
		double distance = Math.abs(x2-x1) + Math.abs(y2-y1);
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
