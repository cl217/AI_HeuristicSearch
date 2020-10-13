import java.util.Comparator;
import java.util.PriorityQueue;

public class A_Search extends HeuristicSearch {
	
	public A_Search(){
		//pops lowest fValue first
		queue = new PriorityQueue<Cell>(5, Comparator.comparing(Cell::fValue));
	}

}
