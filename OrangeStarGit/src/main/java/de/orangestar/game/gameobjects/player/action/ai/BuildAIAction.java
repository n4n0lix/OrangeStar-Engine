package de.orangestar.game.gameobjects.player.action.ai;

import de.orangestar.engine.logic.LogicEngine;
import de.orangestar.engine.render.actor.tilemap.Surface;
import de.orangestar.game.gameobjects.player.action.Action;
import de.orangestar.game.gameobjects.player.action.SurfaceChangeAction;
import de.orangestar.game.gameobjects.player.action.WaitAction;

public class BuildAIAction extends AIActionList {

    public static final long DURATION = 1 * LogicEngine.TICK;
    
    public BuildAIAction(int destTileX, int destTileY, Surface surface) {
        
        _destTileX = destTileX;
        _destTileY = destTileY;
        
        // 1# Walk to destination
        WalkAIAction firstAction = new WalkAIAction(_destTileX, _destTileY);
        
        // 2# Wait at destination
        Action secondAction = new WaitAction(DURATION);
        
        // 3# Change the surface
        Action thirdAction = new SurfaceChangeAction(_destTileX, _destTileY, surface);

        // Wire the actions
        firstAction
            .setFailureAction(null)
            .setSuccessAction(secondAction
                                  .setNextAction(thirdAction));
        
        add(firstAction);
    }
    
    public int getX() {
        return _destTileX;
    }
    
    public int getY() {
        return _destTileY;
    }
    
    private int _destTileX;
    private int _destTileY;
}
