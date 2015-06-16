package de.orangestar.engine.tools.pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.orangestar.engine.utils.Pair;

public class SingleNode implements Node{
	
	public SingleNode(Pair<Integer, Integer> node) {
		_nodeID = node;
		_movingCost = 0;
	}
	
	public SingleNode(Pair<Integer, Integer> node, int cost) { // to use when we have different moving costs
		_nodeID = node;
		_movingCost = cost;
	}
	
	public List<Node> neighbourhood() {
		List <Node> neighbours = new ArrayList<>(8);
		neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y-1)));
		neighbours.add(new SingleNode(Pair.New(_nodeID.x, _nodeID.y-1)));
		neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y-1)));
		neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y)));
		neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y)));
		neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y+1)));
		neighbours.add(new SingleNode(Pair.New(_nodeID.x, _nodeID.y+1)));
		neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y+1)));
		return Collections.unmodifiableList(neighbours);
	}
	
	
	private Pair<Integer, Integer> 	_nodeID;
	private int 					_movingCost;
	
}
