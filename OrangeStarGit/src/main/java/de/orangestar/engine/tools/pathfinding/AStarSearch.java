package de.orangestar.engine.tools.pathfinding;

import java.util.LinkedList;
import java.util.List;

import de.orangestar.engine.utils.Pair;

public class AStarSearch implements Pathfinding{
	
	public AStarSearch() {
		
	}
	
	public List<Pair<Integer, Integer>> findPath(boolean[][] area, int startX, int startY, int destX, int destY) {
		_area = area;
		setDestination(destX, destY);
		int posX = startX;
		int posY = startY;
		int previousX = posX;
		int previousY = posY;
		List<Pair<Integer, Integer>> path = new LinkedList<>();
		int distance = 0;
		path.add(new Pair<>(startX, startY));
		
		while(posX != _destX && posY != _destY) {
			boolean next = false;
			Pair<Integer, Integer> p = Pair.New(-1, -1); // create new Pair, setting correct value later
			_area[posX][posY] = false; // mark current node
			double minDist =  Integer.MAX_VALUE; // look for minimum distance
			for(int i = 0; i < 3; i++) {
				if(_area[posX-1+i][posY-1] == true && estimate(posX-1+i, posY-1) + distance +1 <= minDist) { // save minimum distance if distance is lower, also create new Pair that we need later.
					minDist = estimate(posX-1+i, posY-1);
					p = new Pair<>(posX-1+i, posY-1);
				}
				if(_area[posX-1+i][posY] == true && estimate(posX-1+i, posY) + distance +1 <= minDist) {
					minDist = estimate(posX-1+i, posY);
					p = new Pair<>(posX-1+i, posY);
				}
				if(_area[posX-1+i][posY+1] == true && estimate(posX-1+i, posY+1) + distance +1 <= minDist) {
					minDist = estimate(posX-1+i, posY+1) + distance;
					p = new Pair<>(posX-1+i, posY+1);
				}
				if(minDist == Integer.MAX_VALUE && i == 2) { //if no minimum is found, then go to previous node back
					Pair<Integer, Integer> deadEnd = path.remove(path.size()-1);
					_area[deadEnd.x][deadEnd.y] = false;
					next = true;
					distance--;
					posX = previousX;
					posY = previousY;
				}
			}
			if(!next) {
				if(p.x != -1 && p.y != -1) { // if node is found set posX and posY to this node and add distance (just +1 at the moment, because every node has the same cost)
					path.add(p);
					distance++;
					previousX = posX;
					previousY = posY;
					posX = p.x;
					posY = p.y;
				}
			}
			
			
		}
		return path;
	}
	
	public void setDestination(int x, int y) {
		_destX = x;
		_destY = y;
	}
	
	public double estimate(int posX, int posY) {
		 return Math.sqrt(( Math.pow(2-4, 2) + Math.pow(4 - 6, 2)));
	}
	
	private boolean[][] _area;
	private int _destX;
	private int _destY;

}
