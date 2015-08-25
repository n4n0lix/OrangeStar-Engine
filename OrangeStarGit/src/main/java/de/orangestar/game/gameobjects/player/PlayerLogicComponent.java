package de.orangestar.game.gameobjects.player;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;
import de.orangestar.engine.debug.Logger;
import de.orangestar.engine.logic.LogicComponent;
import de.orangestar.engine.tools.pathfinding.AStarSearch;
import de.orangestar.engine.tools.pathfinding.Node;
import de.orangestar.engine.tools.pathfinding.Pathfinding;
import de.orangestar.engine.utils.ArrayUtils;
import de.orangestar.engine.utils.MathUtils;
import de.orangestar.engine.utils.Pair;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.game.gameobjects.map.Map;
import de.orangestar.game.gameobjects.map.MapChunk;
import de.orangestar.game.gameobjects.map.MapLogicComponent;
import de.orangestar.game.gameobjects.player.action.ai.AIActionProcessor;

/**
 * The {@link LogicComponent} of a player gameobject.
 * 
 * @author Oliver &amp; Basti
 */
public class PlayerLogicComponent extends LogicComponent {

    public static final float MOVE_SPEED              = 28f;
    public static final float TILE_SIZE               = MapChunk.TILE_SIZE;
    public static final int   MAP_CHUNK_WALK_RADIUS   = 8;
    public static final float PATHFINDING_TOLERANCE   = -(MapChunk.TILE_SIZE / 3);
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    @Override
    public void onInitialize() {
        super.onInitialize();
        
        _pathFinder  = new AStarSearch();
        _aiProcessor = new AIActionProcessor(this);
    }
    
	@Override
    public void onUpdate() {
        super.onUpdate();
        
        handleAI();
        handleAIMovement();
    }

    public boolean walkTo(int targetTileX, int targetTileY) {
        _currentPath = findPath(targetTileX, targetTileY);
        
        if (_currentPath == null) {
            return false;
        }
        
        _currentWaypoint = null;        
        return true;
    }
    
	/**
	 * If the player is currently walking.
	 * @return If the player is currently walking
	 */
	public boolean isWalking() {
	    return _isWalking;
	}
	
	/**
	 * Tests if the player is able to walk to the destination.
	 * @param targetTileX The destination tile x
	 * @param targetTileY The destination tile y
	 * @return If a valid path was found
	 */
	public boolean canWalkTo(int targetTileX, int targetTileY) {
	    return findPath(targetTileX, targetTileY) != null;
	}
	
	/**
	 * Returns the world position of the player.
	 * @return The world position of the player
	 */
	public Pair<Float, Float> getWorldPosition() {
	    return Pair.New(getGameObject().getLocalTransform().position.x,
	                    getGameObject().getLocalTransform().position.y);
	}
	
	/**
     * Returns the x/y of the tile on which the player currently stands.
     * @return The x/y of the tile on which the player currently stands
     */
	public Pair<Integer, Integer> getTileLocation() {
	    Pair<Float, Float> position = getWorldPosition();
	      
        int x = MathUtils.fastFloor(position.x / TILE_SIZE);
        int y = MathUtils.fastFloor(position.y / TILE_SIZE);

	    return Pair.New(x, y);
	}
	
	/**
     * Returns the x/y of the map chunk in which the player currently is.
     * @return The x/y of the map chunk in which the player currently is
     */
	public Pair<Integer, Integer> getChunkLocation() {
        Pair<Integer, Integer> tileLocation = getTileLocation();
        return Pair.New(tileLocation.x / MapChunk.CHUNK_SIZE, tileLocation.y / MapChunk.CHUNK_SIZE);
	}
	
	/**
	 * Returns the AIActionProcessor.
	 * @return The AIActionProcessor
	 */
	public AIActionProcessor getAIActionProcessor() {
	    return _aiProcessor;
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private void handleAIMovement() {
        Vector3f position = getGameObject().getLocalTransform().position;

        boolean needsMoreLeftMoving     = false;
        boolean needsMoreRightMoving    = false;
        boolean needsMoreUpMoving       = false;
        boolean needsMoreDownMoving     = false;
        
        if (_currentWaypoint != null) {
            float wpLeftX;
            float wpRightX;
            float wpUpY;
            float wpDownY;
            
            // We're moving towards the last waypoint/tile of the path so we want to
            // move more to the middle of the final tile
            if (_currentPath.isEmpty()) {
                wpLeftX  = _currentWaypoint.x * TILE_SIZE  + TILE_SIZE + PATHFINDING_TOLERANCE;
                wpRightX = _currentWaypoint.x * TILE_SIZE              - PATHFINDING_TOLERANCE;
                wpUpY    = _currentWaypoint.y * TILE_SIZE  + TILE_SIZE + PATHFINDING_TOLERANCE;
                wpDownY  = _currentWaypoint.y * TILE_SIZE              - PATHFINDING_TOLERANCE;  
            } else {
                wpLeftX  = _currentWaypoint.x * TILE_SIZE + TILE_SIZE;
                wpRightX = _currentWaypoint.x * TILE_SIZE ;
                wpUpY    = _currentWaypoint.y * TILE_SIZE + TILE_SIZE;   
                wpDownY  = _currentWaypoint.y * TILE_SIZE;
            }
            
            needsMoreLeftMoving  = position.x > wpLeftX; 
            needsMoreRightMoving = position.x < wpRightX;
            needsMoreUpMoving    = position.y > wpUpY;   
            needsMoreDownMoving  = position.y < wpDownY; 
        }
        
        // Player reached waypoint
        if (_currentWaypoint != null && !needsMoreLeftMoving && !needsMoreRightMoving && !needsMoreUpMoving && !needsMoreDownMoving) {
            Logger.debug(PlayerLogicComponent.class, "Player-AI: Waypoint reached " + _currentWaypoint + " (" + _currentPath.size() + " Waypoints left)");
            _currentWaypoint = null;
            
            // Final-waypoint reached
            if (_currentPath.isEmpty()) {
                
                _isWalking = false;
            }
        }
        
        // Get new waypoint
        if(_currentWaypoint == null && _currentPath != null && !_currentPath.isEmpty()) {
            _currentWaypoint = _currentPath.poll().getNodeID();
            _isWalking = true;
            Logger.debug(PlayerLogicComponent.class, "Player-AI: New Waypoint " + _currentWaypoint + "");
        }

        // Do movement into the direction of the current target
        if (_currentWaypoint != null) {
            PlayerPhysicsComponent physics = getGameObject().getComponent( PlayerPhysicsComponent.class );
            
            if (physics != null) {
                if (needsMoreRightMoving) {
                    physics.addVelocityX( MOVE_SPEED );
                }
                
                if (needsMoreLeftMoving) {
                    physics.addVelocityX( -MOVE_SPEED );
                }
                
                if (needsMoreDownMoving) {
                    physics.addVelocityY( MOVE_SPEED );
                }
                
                if (needsMoreUpMoving) {
                    physics.addVelocityY( -MOVE_SPEED );
                }
            }
        }
	}

	private Queue<Node> findPath(int targetTileX, int targetTileY) {
        int playerChunkX = getChunkLocation().x;
        int playerChunkY = getChunkLocation().y;
        
        int dataChunkX   = playerChunkX - MAP_CHUNK_WALK_RADIUS;
        int dataChunkY   = playerChunkY - MAP_CHUNK_WALK_RADIUS;
        dataTileX = dataChunkX * MapChunk.CHUNK_SIZE;
        dataTileY = dataChunkY * MapChunk.CHUNK_SIZE;
        
        // 1# Prepare Pathfinding data
        MapLogicComponent map = getMapLogicComponent();
        
        if (map == null) {
            return null;
        }
        
        boolean[][] pathfindingData = getPathfindingData(dataChunkX, dataChunkY, map);
        
        // 2# Find the path
        int playerTileX  = getTileLocation().x;
        int playerTileY  = getTileLocation().y;
        
        int startTileX = playerTileX - dataTileX;
        int startTileY = playerTileY - dataTileY;
        int destTileX  = targetTileX - dataTileX;
        int destTileY  = targetTileY - dataTileY;

        List<Node> path = _pathFinder.findPath(pathfindingData, startTileX, startTileY, destTileX, destTileY);
        
        if (path == null) {
            return null;
        }
        
        Queue<Node> result = new LinkedList<>(path);
        result.poll(); // Remove first node, because that is the tile we start on

        for(Node node : result) {
            node.getNodeID().x += dataTileX;
            node.getNodeID().y += dataTileY;
        }
        
        return result;
	}

    private MapLogicComponent getMapLogicComponent() {
        List<GameObject> objects = GameObject.getByTagsAny("map");
        
        // < No objects found with tag 'Map'
        if (objects.isEmpty()) {
            return null;
        }

        Map map        = (Map) objects.get(0);
        
        // < Map got no logic component
        if (map.getLogicComponent() == null) {
            return null;
        }
        
        return map.getLogicComponent();
    }

	private boolean[][] getPathfindingData(int topLeftChunkX, int topLeftChunkY, MapLogicComponent logic) {
        int numWalkableChunksPerAxis = 1 + (MAP_CHUNK_WALK_RADIUS * 2);
        
        boolean[][] pathfindingData = new boolean[MapChunk.CHUNK_SIZE * numWalkableChunksPerAxis][MapChunk.CHUNK_SIZE * numWalkableChunksPerAxis];
        
        for(int x = 0; x < numWalkableChunksPerAxis; x++) {
            for(int y = 0; y < numWalkableChunksPerAxis; y++) {
                int arrayXOffset = x * MapChunk.CHUNK_SIZE;
                int arrayYOffset = y * MapChunk.CHUNK_SIZE;
                
                ArrayUtils.copyIntoArray(pathfindingData, logic.getChunk(topLeftChunkX + x, topLeftChunkY + y).getPathfindingData(), arrayXOffset, arrayYOffset);
            }
        }
        
        return pathfindingData;
	}

	private void handleAI() {
	    _aiProcessor.process();
	}

	private boolean                        _isWalking;
    private Pathfinding                    _pathFinder;
	private Pair<Integer,Integer>          _currentWaypoint;
	private Queue<Node>                    _currentPath;
	
	private PlayableGroup                  _group;
	private AIActionProcessor              _aiProcessor;
    private int dataTileY;
    private int dataTileX;

}
