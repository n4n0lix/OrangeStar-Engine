package de.orangestar.engine.input;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWKeyCallback;

import de.orangestar.engine.AbstractManager;
import de.orangestar.engine.debug.DebugManager;
import de.orangestar.engine.render.RenderManager;

/**
 * Handles the players input.
 * It also provides an adequate interface to the engine, that it can use the input data.
 * 
 * @author Basti
 *
 */
public class InputManager extends AbstractManager {
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                               PUBLIC                               */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	@Override
	public void start() {
	    _keyboardLayout = KeyboardLayout.QWERTZ;
	    
//        glfwSetKeyCallback(_renderManager.getMainWindow().handle(), keyCallback = new GLFWKeyCallback() {
//            @Override
//            public void invoke(long window, int key, int scancode, int action, int mods) {
//                if ( key == _keyboardLayout.convert(GLFW_KEY_Z) && action == GLFW_RELEASE )
//                    glfwSetWindowShouldClose(window, GL_TRUE);
//            }
//        });
	}
	
    @Override
    public void update() {
        // TODO: Implement input handling here
    }
    	
	@Override
	public void shutdown() {
		//keyCallback.release();
	}
	
	public void 	      setKeyboardLayout(KeyboardLayout layout) {
	    _keyboardLayout = layout;
	}
	
	public KeyboardLayout getKeyboardLayout() {
	    return _keyboardLayout;
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                              PRIVATE                               */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private GLFWKeyCallback    _keyCallback;
	private KeyboardLayout     _keyboardLayout;

    private RenderManager   _renderManager   = RenderManager.Get();
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                              SINGLETON                             */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

	/**
	 * Returns the active instance of {@link InputManager}
	 * @return A input manager
	 */
	public static InputManager Get() {
		if (INSTANCE == null) {
			INSTANCE = new InputManager();
		}
		
		return INSTANCE;
	}

	private static InputManager INSTANCE = null;
	
}
