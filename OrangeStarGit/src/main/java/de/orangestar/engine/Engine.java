package de.orangestar.engine;

import static org.lwjgl.glfw.GLFW.glfwInit;

import java.io.File;

import org.lwjgl.opengl.GL11;

import de.orangestar.engine.debug.DebugManager;
import de.orangestar.engine.input.InputManager;
import de.orangestar.engine.logic.GameManager;
import de.orangestar.engine.physics.PhysicsManager;
import de.orangestar.engine.render.GLWindow;
import de.orangestar.engine.render.RenderManager;

/**
 * The core component of the engine.
 * Needs at least OpenGL 3.3 support.
 * @author Basti
 */
public class Engine {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
	public void run(String... args) {
	    // Point the VM to the native libraries
	    System.setProperty("org.lwjgl.librarypath", new File("libs").getAbsolutePath());
	    
	    // Not in RenderManager, because we want to be able to debug the renderer initialization
	    if ( glfwInit() != GL11.GL_TRUE ) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
	    
		_debugManager.start();
		_renderManager.start();
		_physicManager.start();
	    _inputManager.start();
		_gameManager.start();

		mainloop();
		
		_gameManager.shutdown();
	    _inputManager.shutdown();
	    _physicManager.shutdown();
		_renderManager.shutdown();
		_debugManager.shutdown();

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
	private void mainloop() {
		boolean programEnd = false;
        	    
	    while ( !programEnd ) {
	        GLWindow.doGuiEvents();
	        //_inputManager.update();   Moved into GameManager.update()
	        _gameManager.update();
	        //_physicManager.update();  Moved into GameManager.update()
            _renderManager.update();

	        programEnd = _renderManager.requestsExit() || _gameManager.requestsExit() || _inputManager.requestsExit();
	    }
	}

    private final DebugManager    _debugManager    = DebugManager.Get();
    private final RenderManager   _renderManager   = RenderManager.Get();
    private final GameManager     _gameManager     = GameManager.Get();
    private final InputManager    _inputManager    = InputManager.Get();
    private final PhysicsManager   _physicManager   = PhysicsManager.Get();
    
}
