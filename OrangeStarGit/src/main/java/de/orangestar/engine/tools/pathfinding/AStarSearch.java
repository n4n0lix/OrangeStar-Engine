package de.orangestar.engine.tools.pathfinding;

import java.util.ArrayList;
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
		List<Pair<Integer, Integer>> path = new ArrayList<>();
		int distance = estimate(startX, startY);
		path.add(new Pair<>(startX, startY));
		
		while(posX != _destX && posY != _destY) {
			if(_area[posX-1][posY] == true && estimate(posX-1, posY) < distance) {
				path.add(new Pair<>(posX-1, posY));
				distance = estimate(posX-1, posY);
			} else if(_area[posX-1][posY-1] == true && estimate(posX-1, posY-1) < distance) {
				path.add(new Pair<>(posX-1, posY-1));
				distance = estimate(posX-1, posY-1);
			} else if(_area[posX][posY-1] == true && estimate(posX, posY-1) < distance) {
				path.add(new Pair<>(posX, posY-1));
				distance = estimate(posX, posY-1);
			} else if(_area[posX+1][posY-1] == true && estimate(posX+1, posY-1) < distance) {
				path.add(new Pair<>(posX+1, posY-1));
				distance = estimate(posX+1, posY-1);
			} else if(_area[posX+1][posY] == true && estimate(posX+1, posY) < distance) {
				path.add(new Pair<>(posX+1, posY));
				distance = estimate(posX+1, posY);
			} else if(_area[posX+1][posY+1] == true && estimate(posX+1, posY+1) < distance) {
				path.add(new Pair<>(posX+1, posY+1));
				distance = estimate(posX+1, posY+1);
			} else if(_area[posX][posY+1] == true && estimate(posX, posY+1) < distance) {
				path.add(new Pair<>(posX, posY+1));
				distance = estimate(posX, posY+1);
			} else if(_area[posX-1][posY+1] == true && estimate(posX-1, posY+1) < distance) {
				path.add(new Pair<>(posX-1, posY+1));
				distance = estimate(posX-1, posY+1);
			} else {
				System.out.println("You shall not path....finding!");
			}
			
		}
		return path;
	}
	
	public void setDestination(int x, int y) {
		_destX = x;
		_destY = y;
	}
	
	public int estimate(int posX, int posY) {
		 return Math.abs(posX - _destX) + Math.abs(posY - _destY);
	}
	
	private boolean[][] _area;
	private int _destX;
	private int _destY;

}
