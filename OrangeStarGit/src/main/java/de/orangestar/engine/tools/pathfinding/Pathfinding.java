package de.orangestar.engine.tools.pathfinding;

import java.util.List;

/**
 * Finds the shortest path a destination
 * 
 * @author Oliver &amp; Basti
 */
public interface Pathfinding {
	
	/**
	 * This method finds the shortest path to a target location
	 * @param area 2D Array to determine which node can be accessed
	 * @param startX start x coordinate
	 * @param startY start y coordinate
	 * @param destX destination x coordinate
	 * @param destY destination y coordinate
	 * @return The shortest path to the destination or null if the destination is unreachable
	 */
	public List<Node> findPath(boolean[][] area, int startX, int startY, int destX, int destY);
	
}
