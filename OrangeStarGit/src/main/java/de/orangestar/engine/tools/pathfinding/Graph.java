package de.orangestar.engine.tools.pathfinding;

import de.orangestar.engine.utils.Pair;

/**
 * Used to get and accesses the boundaries of the world where we can move
 * 
 * @author Oliver &amp; Basti
 */
public class Graph {
	
	/**
	 * constructs a Graph with given boundaries
	 * @param x x value of world
	 * @param y y value of the world
	 */
	public Graph(int x, int y) {
		_xDimension = x;
		_yDimension = y;
	}
	
	/**
	 * To get both values at once
	 * @return dimensions as a Pair
	 */
	public Pair<Integer, Integer> getDimensionsAsPair() {
		return new Pair<Integer, Integer>(_xDimension, _yDimension);
	}
	
	/**
	 * 
	 * @return x value of the graph
	 */
	public int getXDim() {
		return _xDimension;
	}
	/**
	 * 
	 * @return y value of the graph
	 */
	public int getYDim() {
		return _yDimension;
	}
	
	private int _xDimension;
	private int _yDimension;

}
