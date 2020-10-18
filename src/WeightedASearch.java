import java.util.ArrayList;
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

public class WeightedASearch extends HeuristicSearch{
	
	public WeightedASearch(double weight){
		//pops lowest weighted A first
		resetValues();
		setHValue();
		Main.weight = weight;
		queue = new PriorityQueue<Cell>(5, Comparator.comparing(Cell::weightedA));
	}
	

}
