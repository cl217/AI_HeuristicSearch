import java.util.Comparator;
import java.util.PriorityQueue;

public class UniformCostSearch extends HeuristicSearch{
	
	
	public UniformCostSearch() {
		//pops lowest gValue first
		queue = new PriorityQueue<Cell>(5, Comparator.comparing(Cell::gValue));		
	}
}
