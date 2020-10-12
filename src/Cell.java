/*
 * Introduction to AI
 * Assignment 1: Heuristic Search
 * 
 * Made by:
 * Cindy Lin
 * Amielyn Musa
 * Sanidhi B
 */

public class Cell {
	public char c;
	
	//Calculated using Manhattan Distance? Diagonal Distance? other?
	public double hValue; //how far away from end
	public double gValue; //cost of path away from start
	public double fValue; //g+h
	
	public Cell( char c ) {
		this.c = c;		
		//TODO: set h value
	}
	
	

}
