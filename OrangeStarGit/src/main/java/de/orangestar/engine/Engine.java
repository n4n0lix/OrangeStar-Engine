package de.orangestar.engine;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import de.orangestar.engine.debug.DebugManager;
import de.orangestar.engine.input.InputManager;
import de.orangestar.engine.logic.GameManager;
import de.orangestar.engine.render.GLWindow;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.resources.ResourceManager;

/**
 * The core component of the engine.
 * Needs at least OpenGL 3.3 support.
 * @author Basti
 */
public class Engine {

	public void run(String... args) {
	    // Not in RenderManager, because we want to be able to debug the renderer initialization
	    if ( glfwInit() != GL11.GL_TRUE ) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
	    
		_debugManager.start();
		_renderManager.start();
	    _inputManager.start();
	    _resourceManager.start();
		_gameManager.start();

		mainloop();
		
		_gameManager.shutdown();
		_resourceManager.shutdown();
	    _inputManager.shutdown();
		_renderManager.shutdown();
		_debugManager.shutdown();

	}
	
	/**
	 * Contains the heart logic of the engine.
	 * The used mainloop-approach allows fixed-time logical step updates to preserve logical determinism, but also 
	 * allows a variable frame rate, so that slower pc's will still be able to calculate the same gameworld as
	 * faster pc's, but at a lower rendering rate.
	 */
	private void mainloop() {
		boolean programEnd = false;

	    // Used to determine if the logical updating is lagging
        long tickcurrent  = System.currentTimeMillis();
        long tickprevious = System.currentTimeMillis();
        long tickduration = 0;
        long lag = 0;
        	    
	    while ( !programEnd ) {
            tickcurrent  = System.currentTimeMillis();
            tickduration = tickcurrent - tickprevious;
            tickprevious = tickcurrent;
            lag          += tickduration;
	        
	        GLWindow.doGuiEvents();
	        _inputManager.update();
	        
	        // Catch up
            while ( lag >= GameManager.TICK_TIME )
            {
                _gameManager.update();
                lag -= GameManager.TICK_TIME;
            }
            
            _renderManager.setExtrapolation( 1f + (float) lag / (float) GameManager.TICK_TIME );
            _renderManager.update();

	        programEnd = _renderManager.requestsExit() || _gameManager.requestsExit() || _inputManager.requestsExit();
	    }
	}

    private final DebugManager    _debugManager    = DebugManager.Get();
    private final RenderManager   _renderManager   = RenderManager.Get();
    private final GameManager     _gameManager     = GameManager.Get();
    private final InputManager    _inputManager    = InputManager.Get();
    private final ResourceManager _resourceManager = ResourceManager.Get();

    
}
