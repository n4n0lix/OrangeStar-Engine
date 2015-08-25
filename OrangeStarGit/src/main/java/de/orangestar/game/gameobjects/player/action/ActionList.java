package de.orangestar.game.gameobjects.player.action;

/**
 * A list of consecutive actions.
 * 
 * @author Oliver &amp; Basti
 */
public class ActionList extends Action {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public ActionList() {
        _actionProcessor = new ActionProcessor();
    }

    /**
     * Adds an action to the end of the list.
     * @param action The action
     * @return <i>this</i>
     */
    public ActionList add(Action action) {
        _actionProcessor.addAction(action);
        return this;
    }
    
    @Override
    public void onStart() {
        
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

    private ActionProcessor _actionProcessor;
    
}
