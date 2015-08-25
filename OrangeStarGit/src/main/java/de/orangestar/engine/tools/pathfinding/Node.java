package de.orangestar.engine.tools.pathfinding;

import java.util.List;

import de.orangestar.engine.utils.Pair;

/**
 * Represents the properties of a node in a graph
 * 
 * @author Oliver &amp; Basti
 */
public interface Node {
	
	/**
	 * Represents the neighbourhood of the node
	 * @return neighbours of the node
	 */
	public List<Node> neighbourhood();
	
	/**
	 * Gets the nodeID
	 * @return the ID as a Pair
	 */
	public Pair<Integer, Integer> getNodeID();
	
	/**
	 * Gets the distance to a destination
	 * @return the distance
	 */
	public double getDistanceToDestination();
	
	/**
	 * sets the distance to a destination
	 * @param d the distance value
	 */
	public void setDistanceToDestination(double d);
	
	/**
	 * Represents how long it takes to move to this node
	 * @return the costs to move to this location
	 */
	public int getMovingCost();
}
