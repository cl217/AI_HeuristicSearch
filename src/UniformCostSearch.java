import java.util.Comparator;
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

public class UniformCostSearch extends HeuristicSearch{
	
	
	public UniformCostSearch() {
		//pops lowest gValue first
		resetValues();
		queue = new PriorityQueue<Cell>(5, Comparator.comparing(Cell::gValue));		
	}
}
