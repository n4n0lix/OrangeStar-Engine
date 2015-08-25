package de.orangestar.game.gameobjects.player.action.ai;

import de.orangestar.engine.logic.LogicEngine;
import de.orangestar.game.gameobjects.item.prototypes.Wood;
import de.orangestar.game.gameobjects.map.MapChunk;
import de.orangestar.game.gameobjects.player.action.Action;
import de.orangestar.game.gameobjects.player.action.SpawnItemAction;
import de.orangestar.game.gameobjects.player.action.TreeRemoveAction;
import de.orangestar.game.gameobjects.player.action.WaitAction;
import de.orangestar.game.gameobjects.tree.TreeFlyweight;

public class ChopAIAction extends AIActionList {

    public static final long DURATION = 1 * LogicEngine.TICK;
    
    public ChopAIAction(TreeFlyweight tree) {

        float treeX = tree.getGlobalTransform().position.x;
        float treeY = tree.getGlobalTransform().position.y;
        
        int tileDestX = (int) Math.floor( treeX / MapChunk.TILE_SIZE);
        int tileDestY = (int) Math.floor( treeY / MapChunk.TILE_SIZE);
        
        // 1# Walk to destination
        WalkAIAction firstAction  = new WalkAIAction(tileDestX, tileDestY);
        
        // 2# Wait at destination
        Action secondAction = new WaitAction(DURATION);
        
        // 3# Remove tree
        Action thirdAction  = new TreeRemoveAction(tree.getUID());
        
        // 4# Spawn wood item
        Action fourthAction  = new SpawnItemAction(Wood.Instance, 1, treeX, treeY );
        
        // Wire the actions        
        firstAction.setFailureAction(null)
                   .setSuccessAction(secondAction);
        
        secondAction.setNextAction(thirdAction);
        
        thirdAction.setNextAction(fourthAction);
        
        add(firstAction);
    }
    
    public int getX() {
        return _tileX;
    }
    
    public int getY() {
        return _tileY;
    }
    
    private int _tileX;
    private int _tileY;
    
}
