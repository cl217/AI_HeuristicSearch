import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class WeightedASearch extends HeuristicSearch{
	
	public WeightedASearch(){
		//pops lowest weighted A first
		queue = new PriorityQueue<Cell>(5, Comparator.comparing(Cell::weightedA));
	}

}
