package de.orangestar.game.gameobjects.player.action.ai;

import de.orangestar.game.gameobjects.player.action.Action;

/**
 * An AIAction that makes the associated AI Unit to walk to the destination.
 * This Action can fail if the destination cannot be reached. If the action
 * fails, the Action of {@linkplain setFailureAction} is set as next action.
 * 
 * @author 3funck
 */
public class WalkAIAction extends AIAction {

    public WalkAIAction(int destTileX, int destTileY) {
        _destTileX = destTileX;
        _destTileY = destTileY;
    }
    
    @Override
    public void onStart() {
        boolean success = getAIUnit().walkTo(_destTileX, _destTileY);
        
        if (!success) {
            setNextAction(_failureAction);
            finish();
        }
    }

    @Override
    public void onUpdate() {
        int playerX = getAIUnit().getTileLocation().x;
        int playerY = getAIUnit().getTileLocation().y;
        
        if (playerX == _destTileX && playerY == _destTileY) {
            setNextAction(_successAction);
            finish();
        }
    }

    @Override
    public void onEnd() {
        // TODO Auto-generated method stub
    }
    
    public WalkAIAction setFailureAction(Action action) {
        _failureAction = action;
        return this;
    }
    
    public WalkAIAction setSuccessAction(Action action) {
        _successAction = action;
        return this;
    }
    
    public int getX() {
        return _destTileX;
    }
    
    public int getY() {
        return _destTileX;
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Action _failureAction;
    private Action _successAction;
    
    private final int _destTileX;
    private final int _destTileY;
    
}
