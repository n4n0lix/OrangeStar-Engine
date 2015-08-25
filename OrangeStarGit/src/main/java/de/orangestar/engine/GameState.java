package de.orangestar.engine;

import de.orangestar.engine.input.IInputEngine;
import de.orangestar.engine.logic.ILogicEngine;
import de.orangestar.engine.physics.IPhysicsEngine;
import de.orangestar.engine.render.IRenderEngine;

/**
 * The basic implementation of game states.
 * 
 * @author Oliver &amp; Basti
 */
public abstract class GameState {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Public                                */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public GameState() { }
    
    /**
     * If this gamestate has finished.
     * @return If this gamestate has finished
     */
    public boolean hasFinished() {
        return _hasFinished;
    }

    /**
     * @return the _logicManager
     */
    public ILogicEngine getLogicEngine() {
        return _logicManager;
    }

    /**
     * @return the _physicsManager
     */
    public IPhysicsEngine getPhysicsEngine() {
        return _physicsManager;
    }
    
    /**
     * @return the _inputManager
     */
    public IInputEngine getInputEngine() {
        return _inputManager;
    }
    
    public IRenderEngine getRenderEngine() {
        return _renderEngine;
    }
    
    
    public void start(ILogicEngine logic, IPhysicsEngine physics, IInputEngine input, IRenderEngine render) {
        _logicManager = logic;
        _physicsManager = physics;
        _inputManager = input;
        _renderEngine = render;
        
        onStateStart();
    }
    
    public void update() {
        onStateUpdate();
    }
    
    public void end() {
        onStateEnd();
    }

    public void onFrameStart() { }
    
    public void onFrameEnd() { }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                             Protected                              */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Implement here logic that is execute upon entering this game state.
     */
    protected abstract void onStateStart();

    /**
     * Implement here logic that is execute on every tick.
     */
    protected abstract void onStateUpdate();
    
    /**
     * Implement here logic that is execute upon leaving this game state.
     */
    protected abstract void onStateEnd();
    
    /**
     * Sets the succeeding game state and end this.
     * @param state The succeeding game state
     */
    protected void next(GameState state) {
        _nextState = state;
        _hasFinished = true;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Package                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Starts this game state.
     */
    void start() {
        _hasFinished = false;
    }
    
    /**
     * Returns the succeeding game state 
     * @return The succeeding game state
     */
    GameState getNextGameState() {
        return _nextState;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private boolean   _hasFinished;
    
    private GameState _nextState;
    
    private ILogicEngine   _logicManager;
    private IPhysicsEngine _physicsManager;
    private IInputEngine   _inputManager;
    private IRenderEngine   _renderEngine;

}
