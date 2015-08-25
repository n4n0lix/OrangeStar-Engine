package de.orangestar.engine;

import static org.lwjgl.glfw.GLFW.glfwInit;

import java.io.File;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import de.orangestar.engine.input.IInputEngine;
import de.orangestar.engine.input.InputEngine;
import de.orangestar.engine.logic.ILogicEngine;
import de.orangestar.engine.logic.LogicEngine;
import de.orangestar.engine.physics.IPhysicsEngine;
import de.orangestar.engine.physics.PhysicsEngine;
import de.orangestar.engine.render.GLWindow;
import de.orangestar.engine.render.IRenderEngine;
import de.orangestar.engine.render.RenderEngine;

/**
 * The core component of the engine.
 * Needs at least OpenGL 3.3 support.
 * @author Oliver &amp; Basti
 */
public class Engine {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public void run(Class<? extends GameState> startGamestate, String... args) {
        // Point the VM to the native libraries
        System.out.println(new File("libs").getAbsolutePath());
	    System.setProperty("org.lwjgl.librarypath", new File("libs").getAbsolutePath());

	    // Not in RenderManager, because we want to be able to debug the renderer initialization
	    if ( glfwInit() != GL11.GL_TRUE ) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

		_renderEngine.onStart();
		_physicsEngine.onStart();
		_inputEngine.onStart(_renderEngine.getGLFWWindowHandle());
		_logicEngine.onStart();

		mainloop(startGamestate);

        _logicEngine.onShutdown();
        _inputEngine.onShutdown();
        _physicsEngine.onShutdown();
        _renderEngine.onShutdown();

		GLFW.glfwTerminate();
	}

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

	/**
	 * Contains the heart logic of the engine.
	 * The used mainloop-approach allows fixed-time logical step updates to preserve logical determinism, but also
	 * allows a variable frame rate, so that slower pc's will still be able to calculate the same gameworld as
	 * faster pc's, but at a lower rendering rate. The logic for this is implemented in {@link GameManager.update()}
	 * and {@link RenderManager.update()}.
	 */
	private void mainloop(Class<? extends GameState> startGameStateClass) {
		boolean programEnd = false;
		float tickCurrent  = System.nanoTime() / 1000000;
		float tickPrevious = System.nanoTime() / 1000000;
		float tickDuration = 0;
		float lag          = 0;
		
		try {
            _gameState = startGameStateClass.newInstance();
            _gameState.start(_logicEngine, _physicsEngine, _inputEngine, _renderEngine);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	    while ( !programEnd ) {
	        GLWindow.doGuiEvents();
	        
	        // Calculate the tick rate
	        tickCurrent  = System.nanoTime() / 1000000;
	        tickDuration = tickCurrent - tickPrevious;
	        tickPrevious = tickCurrent;
	        lag          += tickDuration;

	        
	        // 1# Logic
	        while ( lag >= ILogicEngine.TICK_TIME )
	        {
	            _inputEngine.onUpdate();
	            
	            updateGameState();
	            
	            _logicEngine.onUpdate();
	            _physicsEngine.onUpdate();
	            
	            lag -= ILogicEngine.TICK_TIME;
	        }

	        // 2# Rendering
	        _renderEngine.setExtrapolation( 0f + (float) lag / (float) ILogicEngine.TICK_TIME );
	        
	        _gameState.onFrameStart();
	        _renderEngine.onUpdate(); 
	        _gameState.onFrameEnd();
	        
	        programEnd = _renderEngine.requestsExit();
	    }
	}
	
	private void updateGameState() {
	    if (_gameState != null) {
            _gameState.update();
            
            if (_gameState.hasFinished()) {
                _gameState.end();
                
                _gameState = _gameState.getNextGameState();
                if (_gameState != null) {
                    _gameState.start(_logicEngine, _physicsEngine, _inputEngine, _renderEngine);
                }
            }
        }
	}

	private GameState _gameState;
    
    private final IRenderEngine   _renderEngine    = new RenderEngine();
    private final ILogicEngine    _logicEngine     = new LogicEngine();
    private final IInputEngine    _inputEngine     = new InputEngine();
    private final IPhysicsEngine  _physicsEngine   = new PhysicsEngine();
        
}
