package de.orangestar.engine.tools.pathfinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.orangestar.engine.utils.Pair;
/**
 * A* Search algorithm to find the shortest path from one point to another
 * 
 * @author Oliver &amp; Basti
 */
public class AStarSearch implements Pathfinding {
	
	/**
	 * This method finds the shortest path to a target location with the A* search algorithm
	 * @param area 2D Array to determine which node can be accessed
	 * @param startX start x coordinate
	 * @param startY start y coordinate
	 * @param destX destination x coordinate
	 * @param destY destination y coordinate
	 * @return The shortest path to the destination
	 */
	public List<Node> findPath(boolean[][] area, int startX, int startY, int destX, int destY) {
		
	    if (destX < 0 || destX >= area.length || destY < 0 || destY >= area.length) {
	        return null;
	    }
	    
	    if (startX < 0 || startX >= area.length || startY < 0 || startY >= area.length) {
	        return null;
	    }
	    
		if(!area[startX][startY]) { // can not move if starting location is blocked
			return null;
		}
		
		if(!area[destX][destY]) { // currently not moving if the target location is blocked
			return null;
		}
		
		_graph = new Graph(area.length, area[0].length); // creating a graph, we need this for the dimensions
		_area = area;
		setDestination(destX, destY);
		setStart(startX, startY);
		int[][] distanceToTile = new int[_area.length][_area[0].length]; // saving a distance to this tile value for every tile
		List<Node> closedSet = new LinkedList<>(); // visited Nodes
		Map<Node, Node> path = new HashMap<>(); // Map where we can reconstruct a path
		List<Node> openSet = new ArrayList<>(); // unvisited nodes
		Node currentNode = new SingleNode(Pair.New(startX, startY), _graph); // setting starting Node and adding it in the openSet
		openSet.add(currentNode);
		openSet.get(0).setDistanceToDestination(estimate(startX, startY));
		
		while(!openSet.isEmpty()) { // goes through every found node and looks for the shortest path to the target
			double min = Integer.MAX_VALUE;
			for(Node n : openSet) {
				if(n.getDistanceToDestination() < min) { // look for node with the lowest distance value
					min = n.getDistanceToDestination();
					currentNode = n;
				}
			}
			if(currentNode.getNodeID().x == _destX && currentNode.getNodeID().y == _destY) { // if destination reached, we reconstruct the path to the start.
				return reconstructPath(path, currentNode);
			}
			closedSet.add(currentNode);
			openSet.remove(currentNode);
			int tempDist = 0;
			List<Node> curNeighbours = currentNode.neighbourhood();
			for(Node n : curNeighbours){	// black magic and stuff
				if(!closedSet.contains(n)) {
					tempDist = distanceToTile[currentNode.getNodeID().x][currentNode.getNodeID().y] +  1;
					if(_area[n.getNodeID().x][n.getNodeID().y] && !(openSet.contains(n)) || tempDist < distanceToTile[n.getNodeID().x][n.getNodeID().y] + 1) {
						path.put(n, currentNode);
						distanceToTile[n.getNodeID().x][n.getNodeID().y] = tempDist;
						n.setDistanceToDestination(distanceToTile[n.getNodeID().x][n.getNodeID().y] + estimate(n.getNodeID().x, n.getNodeID().y));
						if(!openSet.contains(n)) {
							openSet.add(n);
						}
					}
				}
			}
			
		}
		return null;
	}
	
	/**
	 * Reconstructs and connects the path.
	 * @param nodes a Map of nodes where every node has their previous node mapped to
	 * @param cur current node, at the beginning it is the destination node or the last node it could reach
	 * @return the reconstructed path to the destination
	 */
	private List<Node> reconstructPath(Map<Node, Node> nodes, Node cur) {
		List<Node> finalPath = new LinkedList<>();
		finalPath.add(0, cur);
		while(!(cur.getNodeID().equals(_startPair))) {
			finalPath.add(0,nodes.get(cur));
			cur = finalPath.get(0);
		}
		return finalPath;
	}
	
	/**
	 * Sets the destination node
	 * @param x x coordinate of the destination
	 * @param y y coordinate of the destination
	 */
	public void setDestination(int x, int y) {
		_destX = x;
		_destY = y;
	}
	
	/**
	 * Sets the start node
	 * @param x x coordinate of the start
	 * @param y y coordinate of the start
	 */
	public void setStart(int x, int y) {
		_startPair = Pair.New(x, y);
	}
	
	/**
	 * Gets the start node of the path
	 * @return the start Node as a Pair
	 */
	public Pair<Integer, Integer> getStart() {
		return _startPair;
	}
	
	/**
	 * Estimation method, used to calculate the airline between the start and destination
	 * @param posX
	 * @param posY
	 * @return
	 */
	private double estimate(int posX, int posY) {
		 return Math.sqrt(( Math.pow(_destX - posX, 2) + Math.pow(_destY - posY, 2)));
	}
	
	private Pair<Integer, Integer> _startPair;
	private Graph _graph;
	private boolean[][] _area;
	private int _destX;
	private int _destY;

}
