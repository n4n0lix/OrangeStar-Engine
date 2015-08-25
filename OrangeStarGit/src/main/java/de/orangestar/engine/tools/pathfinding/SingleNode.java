package de.orangestar.engine.tools.pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.orangestar.engine.utils.Pair;

/**
 * An Implementation of the Node interface
 * 
 * @author Oliver &amp; Basti
 */
public class SingleNode implements Node{
	
	/**
	 * Creates a SingleNode with default moving costs
	 * @param node a Pair of where the node is located
	 * @param g the graph in which the node is currently at
	 */
	public SingleNode(Pair<Integer, Integer> node, Graph g) {
		_nodeID = node;
		_graph = g;
		setMovingCost(0);

	}
	
	/**
	 * Creates a SingleNode with specified moving costs
	 * @param node a Pair of where the node is located
	 * @param g the graph in which the node is currently at
	 * @param cost the higher the costs are the longer it takes to move to this location
	 */
	public SingleNode(Pair<Integer, Integer> node, Graph g, int cost) { // to use when we have different moving costs
		_nodeID = node;
		_graph = g;
		setMovingCost(cost);
	}
	
	/**
	 * Represents the neighbourhood of the node
	 * @return neighbours of the node
	 */
	public List<Node> neighbourhood() {
		List <Node> neighbours = new ArrayList<>(8);
		if(_nodeID.x == 0 && _nodeID.y == 0) {
			neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y)  , _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x  , _nodeID.y+1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y+1), _graph));
		} else if(_nodeID.x == 0 && _nodeID.y == _graph.getYDim() -1) {
			neighbours.add(new SingleNode(Pair.New(_nodeID.x  , _nodeID.y-1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y-1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y)  , _graph));
		} else if(_nodeID.x == 0) {
			neighbours.add(new SingleNode(Pair.New(_nodeID.x  , _nodeID.y-1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y-1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y)  , _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x  , _nodeID.y+1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y+1), _graph));
		} else if(_nodeID.x == _graph.getXDim() -1 && _nodeID.y == 0) {
			neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y)  , _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y+1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x  , _nodeID.y+1), _graph));
		} else if(_nodeID.x == _graph.getXDim() -1 && _nodeID.y == _graph.getYDim() -1) {
			neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y-1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x  , _nodeID.y-1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y)  , _graph));
		} else if(_nodeID.y == 0) {
			neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y)  , _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y)  , _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y+1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x  , _nodeID.y+1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y+1), _graph));
		} else if(_nodeID.x == _graph.getXDim() -1) {
			neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y-1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x  , _nodeID.y-1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y)  , _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y+1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x  , _nodeID.y+1), _graph));
		} else if(_nodeID.y == _graph.getYDim() -1) {
			neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y-1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x  , _nodeID.y-1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y-1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y)  , _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y)  , _graph));
		} else {
			neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y-1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x  , _nodeID.y-1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y-1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y)  , _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y)  , _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x-1, _nodeID.y+1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x  , _nodeID.y+1), _graph));
			neighbours.add(new SingleNode(Pair.New(_nodeID.x+1, _nodeID.y+1), _graph));
		}
		
		return Collections.unmodifiableList(neighbours);
	}
	
	@Override
	public Pair<Integer, Integer> getNodeID() {
		return _nodeID;
	}
	
	@Override
	public double getDistanceToDestination() {
		return _destDistance;
	}
	
	@Override
	public void setDistanceToDestination(double d) {
		_destDistance = d;
		
	}
	
	@Override
    public int getMovingCost() {
		return _movingCost;
	}

	/**
	 * Sets the moving costs, in case the value changes after node creation
	 * @param _movingCost the new moving costs
	 */
	public void setMovingCost(int _movingCost) {
		this._movingCost = _movingCost;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_nodeID.x == null) ? 0 : _nodeID.x.hashCode());
        result = prime * result + ((_nodeID.y == null) ? 0 : _nodeID.y.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SingleNode other = (SingleNode) obj;
        if (_nodeID.x == null) {
            if (other.getNodeID().x != null)
                return false;
        } else if (!_nodeID.x.equals(other.getNodeID().x))
            return false;
        if (_nodeID.y == null) {
            if (other.getNodeID().y != null)
                return false;
        } else if (!_nodeID.y.equals(other.getNodeID().y))
            return false;
        return true;
    }
    

    @Override
    public String toString() {
        return _nodeID.toString();
    }





    private double					_destDistance;
	private Graph 					_graph;
	private Pair<Integer, Integer> 	_nodeID;
	private int 					_movingCost;
	
}
