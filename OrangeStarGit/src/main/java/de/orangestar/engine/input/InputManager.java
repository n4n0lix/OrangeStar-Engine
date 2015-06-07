package de.orangestar.engine.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import de.orangestar.engine.AbstractManager;
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
	    _renderManager = RenderManager.Get();
	    _keyboardLayout = KeyboardLayout.QWERTZ;
	    _window = _renderManager.getMainWindow().handle();
	  //  _cursorPos = _mouse.getMousePosition();
	    _mouse = new Mouse();
	    
	    GLFW.glfwSetCursorPosCallback(_window , _cursorPos = new GLFWCursorPosCallback(){

			@Override
			public void invoke(long window, double x, double y) {
				System.out.println(x + ":x " + y + ":y");
				
			} 
	    	
	    });
	    GLFW.glfwSetMouseButtonCallback(_window, _mouseButton =  new GLFWMouseButtonCallback(){

			@Override
			public void invoke(long window, int button, int action, int mods) {
				switch(button) {
					case 0: if(action == 1) { _mouse.setButton0(true);	}
							else if(action == 0) { _mouse.setButton0(false); }
						break;
					case 1: if(action == 1) { _mouse.setButton1(true);	}
							else if(action == 0) { _mouse.setButton1(false); }
						break;
					case 2:
				}		
			}
	    	
	    });
	    
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
	private Mouse 			   _mouse;
	private GLFWCursorPosCallback      _cursorPos;
	private GLFWMouseButtonCallback _mouseButton;
    private long _window;

    private RenderManager      _renderManager;
	
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
