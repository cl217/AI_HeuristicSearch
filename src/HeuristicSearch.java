import java.util.PriorityQueue;

public abstract class HeuristicSearch {

	PriorityQueue<Integer> fringe = new PriorityQueue<Integer>(); //pops lowest
	
	
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
	
	
	
	
}
