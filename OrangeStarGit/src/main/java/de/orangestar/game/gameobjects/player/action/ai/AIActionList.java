package de.orangestar.game.gameobjects.player.action.ai;

import java.util.Collection;
import java.util.LinkedList;

import de.orangestar.game.gameobjects.player.action.Action;
import de.orangestar.game.gameobjects.player.action.ActionProcessor;

/**
 * A list of consecutive ai actions.
 * 
 * @see de.orangestar.game.gameobjects.player.action.ActionList
 * @author 3funck
 */
public class AIActionList extends AIAction {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Adds an action to the end of the list.
     * @param action The action
     */
    public void add(Action action) {
        _tmpActions.add(action);
    }
    
    @Override
    public void onStart() {
        _actionProcessor = new AIActionProcessor(getAIUnit());
        _actionProcessor.addActions(_tmpActions);
    }

    @Override
    public void onUpdate() {
        _actionProcessor.process();
        
        if (_actionProcessor.isIdle()) {
            finish();
        }
    }
    
    @Override
    public void onEnd() {

    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private AIActionProcessor  _actionProcessor;
    private Collection<Action> _tmpActions = new LinkedList<>();
    
}
