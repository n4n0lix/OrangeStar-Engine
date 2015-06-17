package de.orangestar.game.gameobjects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.debug.DebugManager;
import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.physics.component.UnitPhysicsComponent;
import de.orangestar.engine.tools.pathfinding.AStarSearch;
import de.orangestar.engine.tools.pathfinding.Pathfinding;
import de.orangestar.engine.utils.Pair;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.game.gameobjects.map.MapChunk;

public class PlayerLogicComponent extends UnitLogicComponent {

    public static final float MOVE_SPEED = 16f;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public PlayerLogicComponent(GameObject obj, UnitPhysicsComponent physics) {
		super(obj);
		_physics = physics;
        
        Vector3f position = getGameObject().getLocalTransform().position;

        int x = 0;//(int) position.x * MapChunk.TILE_SIZE;
        int y = 0;//(int) position.y * MapChunk.TILE_SIZE;
        
        boolean[][] world = new boolean[MapChunk.CHUNK_SIZE][MapChunk.CHUNK_SIZE];
        for(int i = 0; i < world.length; i++) {
            for(int p = 0; p < world[0].length; p++) {
                world[i][p] = true;
            }
        }
        
        _pathFinder = new AStarSearch();
        _currentPath = _pathFinder.findPath(world, x, y, 8, 5);
        
        for(int i = 0; i < _currentPath.size(); i++) {
            System.out.println(_currentPath.get(i));
        }
	}

	@Override
    public void onUpdate() {
        super.onUpdate();
        
        handleAIMovement();
    }
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private void handleAIMovement() {
        Vector3f position = getGameObject().getLocalTransform().position;

        int x = (int) position.x / MapChunk.TILE_SIZE;
        int y = (int) position.x / MapChunk.TILE_SIZE;
        
        // Check if we arrived our current target location
        if (_currentTarget != null && x == _currentTarget.x && y == _currentTarget.y) {
            DebugManager.Get().debug(PlayerLogicComponent.class, "Player AI reached target location (" + x + "/" + y + ")");
            _currentTarget = null;
        }
        
        // Try to get a new target location if we haven't one
        if (_currentTarget == null && !_currentPath.isEmpty()) {
            _index++;
            _currentTarget = _currentPath.get(_index);
            DebugManager.Get().debug(PlayerLogicComponent.class, "Player AI new target location (" + _currentTarget.x + "/" + _currentTarget.y + ")");
        }
        
        // Do movement into the direction of the current target
        if (_currentTarget != null) {
            if (x < _currentTarget.x) {
                _physics._velocityX += MOVE_SPEED;
            }
            
            if (x > _currentTarget.x) {
                _physics._velocityX -= MOVE_SPEED;
            }
            
            if (y < _currentTarget.y) {
                _physics._velocityY += MOVE_SPEED;
            }
            
            if (y > _currentTarget.y) {
                _physics._velocityY -= MOVE_SPEED;
            }
        }
	}
	
	private int _index = 0;
	
    private Pathfinding                    _pathFinder;
	private Pair<Integer,Integer>          _currentTarget;
	private List<Pair<Integer,Integer>>   _currentPath;

	private UnitPhysicsComponent _physics;
}
