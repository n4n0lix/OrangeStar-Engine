package de.orangestar.engine.tools.pathfinding;

import java.util.List;

import de.orangestar.engine.utils.Pair;

public interface Pathfinding {
	
	public List<Pair<Integer,Integer>> findPath(boolean[][] area, int startX, int startY, int destX, int destY);

}
