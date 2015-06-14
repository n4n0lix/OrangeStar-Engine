package de.orangestar.engine.tools.pathfinding;

import java.util.ArrayList;
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
		List<Pair<Integer, Integer>> path = new LinkedList<>();
		int estimatedDistance = estimate(startX, startY);
		int distance = estimate(startX, startY);
		path.add(new Pair<>(startX, startY));
		
		while(posX != _destX && posY != _destY) {
			List<Pair<Integer, Integer>> field = new ArrayList<>();
			for(int i = 0; i < 2; i++) {
				if(_area[posX-1+i][posY-1] == true && estimatedDistance + distance > estimate(posX-1+i, posY-1) + distance) {
					estimatedDistance = estimate(posX-1+i, posY-1);
					Pair<Integer, Integer> p = new Pair<>(posX-1+i, posY-1);
				}
				if(_area[posX-1+i][posY] == true && estimatedDistance + distance > estimate(posX-1+i, posY) + distance) {
					estimatedDistance = estimate(posX-1+i, posY);
					Pair<Integer, Integer> p = new Pair<>(posX-1+i, posY);
				}
				if(_area[posX-1+i][posY+1] == true && estimatedDistance + distance > estimate(posX-1+i, posY+1) + distance) {
					estimatedDistance = estimate(posX-1+i, posY+1);
					Pair<Integer, Integer> p = new Pair<>(posX-1+i, posY+1);
				}
				if(i == 2) {
					Pair<Integer, Integer> deadEnd = path.remove(path.size()-1);
					_area[deadEnd.x][deadEnd.y] = false;
				}
			}
			if(_area[posX-1][posY] == true && estimate(posX-1, posY) < estimatedDistance) {
				path.add(new Pair<>(posX-1, posY));
				estimatedDistance = estimate(posX-1, posY);
			} else if(_area[posX-1][posY-1] == true && estimate(posX-1, posY-1) < estimatedDistance) {
				path.add(new Pair<>(posX-1, posY-1));
				estimatedDistance = estimate(posX-1, posY-1);
			} else if(_area[posX][posY-1] == true && estimate(posX, posY-1) < estimatedDistance) {
				path.add(new Pair<>(posX, posY-1));
				estimatedDistance = estimate(posX, posY-1);
			} else if(_area[posX+1][posY-1] == true && estimate(posX+1, posY-1) < estimatedDistance) {
				path.add(new Pair<>(posX+1, posY-1));
				estimatedDistance = estimate(posX+1, posY-1);
			} else if(_area[posX+1][posY] == true && estimate(posX+1, posY) < estimatedDistance) {
				path.add(new Pair<>(posX+1, posY));
				estimatedDistance = estimate(posX+1, posY);
			} else if(_area[posX+1][posY+1] == true && estimate(posX+1, posY+1) < estimatedDistance) {
				path.add(new Pair<>(posX+1, posY+1));
				estimatedDistance = estimate(posX+1, posY+1);
			} else if(_area[posX][posY+1] == true && estimate(posX, posY+1) < estimatedDistance) {
				path.add(new Pair<>(posX, posY+1));
				estimatedDistance = estimate(posX, posY+1);
			} else if(_area[posX-1][posY+1] == true && estimate(posX-1, posY+1) < estimatedDistance) {
				path.add(new Pair<>(posX-1, posY+1));
				estimatedDistance = estimate(posX-1, posY+1);
			} else {
				Pair<Integer, Integer> deadEnd = path.remove(path.size()-1);
				_area[deadEnd.x][deadEnd.y] = false;
				// System.out.println("You shall not path....finding!");
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
