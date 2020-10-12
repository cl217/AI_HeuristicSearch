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
	
	//Calculated using Manhattan Distance
	public double hValue; //how far away from end
	public double gValue; //how far away from start
	public double fValue; //g+h
	
	public Cell( char c ) {
		this.c = c;		
		//TODO: also set the h/g/f values
	}
	
	

}
