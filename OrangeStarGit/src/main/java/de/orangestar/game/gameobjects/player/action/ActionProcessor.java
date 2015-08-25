package de.orangestar.game.gameobjects.player.action;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * An action processor which is fed with one action at a time and is then processing it.
 * The ActionProcessor is a state machine, where an {@link Action} represents a state.
 * @author Oliver &amp; Basti
 */
public class ActionProcessor {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Public                                */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/**
	 * Public-Constructor
	 */
    public ActionProcessor() {
        _actions = new LinkedList<>();
    }
    
    /**
     * Adds an action to the action processor.
     * @param action The action
     */
    public void addAction(Action action) {
        _actions.add(action);
    }
    
    /**
     * Adds multiple actions to the action processor.
     * @param actions A collection of actions
     */
    public void addActions(Collection<Action> actions) {
        _actions.addAll(actions);
    }
    
    /**
     * Processes one step in the state machine.
     */
    public void process() {
        // START
        
        // 1# Finished -> GetNext().start()         
        if (_currentAction != null && _currentAction.isFinished()) {
            _currentAction = _currentAction.getNextAction();
            
            if (_currentAction != null) {
                onPreStartAction(_currentAction);
                _currentAction.onStart();
                onPostStartAction(_currentAction);
            }
        }
        
        // 2# Null -> GetNext().start()
        if (_currentAction == null) {
            _currentAction = _actions.poll();
            
            if (_currentAction != null) {
                onPreStartAction(_currentAction);
                _currentAction.onStart();
                onPostStartAction(_currentAction);
            }
        }
        
        // UPDATE
        // 3# Action.onUpdate()
        if (_currentAction != null) {
            onPreUpdateAction(_currentAction);
            _currentAction.onUpdate();
            onPostUpdateAction(_currentAction);
        }
        
        // END
        // 4# Finished -> onEnd()
        if (_currentAction != null && _currentAction.isFinished()) {
            onPreEndAction(_currentAction);
            _currentAction.onEnd();
            onPostEndAction(_currentAction);
        }
    }
    
    /** 
     * Override for pre start logic. 
     * @param currentAction The current execute action
     */
    public void onPreStartAction(Action currentAction)      { }
    
    /** 
     * Override for post start logic.
     * @param currentAction The current execute action
     */
    public void onPostStartAction(Action currentAction)     { }
    
    /** 
     * Override for pre update logic. 
     * @param currentAction The current execute action
     */
    public void onPreUpdateAction(Action currentAction)     { }
    
    /** 
     * Override for post update logic. 
     * @param currentAction The current execute action
     */
    public void onPostUpdateAction(Action currentAction)    { }
    
    /** 
     * Override for pre end logic. 
     * @param currentAction The current execute action
     */
    public void onPreEndAction(Action currentAction)        { }
    
    /** 
     * Override for post start logic.
     * @param currentAction The current execute action
     */
    public void onPostEndAction(Action currentAction)       { }
    
    /**
     * If this action processor is idle.
     * @return If this action processor is idle
     */
    public boolean isIdle() {
        return _currentAction == null && _actions.isEmpty();
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private Action         _currentAction;
    private Queue<Action>  _actions;
}
