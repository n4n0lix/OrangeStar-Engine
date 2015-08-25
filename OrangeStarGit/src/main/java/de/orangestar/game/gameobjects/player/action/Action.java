package de.orangestar.game.gameobjects.player.action;

/**
 * The base class for actions, which represents states in the {@link ActionProcessor}.
 * 
 * @author Oliver &amp; Basti
 */
public abstract class Action {

	/**
	 * Logic that is executed on action start. (On state entering)
	 */
    public abstract void        onStart();

	/**
	 * Logic that is executed on action updates.
	 */
    public abstract void        onUpdate();

	/**
	 * Logic that is executed on action end. (On state leaving)
	 */
    public abstract void        onEnd();

    /**
     * Executes the logic of this Action without the need of an {@link ActionProcessor}.
     */
    public void execute() {
        onStart();
        onUpdate();
        onEnd();
    }
    
    /**
     * If this action has finished.
     * @return If this action has finished
     */
    public final boolean isFinished() {
        return _isFinished;
    }
    
    /**
     * The action that shall be executed after this action has finished.
     * @return An action
     */
    public final Action getNextAction() {
        return _nextAction;
    }

    /**
     * Sets the next action when this has finished.
     * @param action The action that is next after this has finished
     * @return this
     */
    public final Action setNextAction(Action action) {
        _nextAction = action;
        return this;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                             Protected                              */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    protected final void finish() {
        _isFinished = true;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private boolean  _isFinished;
    private Action   _nextAction;
}
