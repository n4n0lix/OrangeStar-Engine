package de.orangestar.engine.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import de.orangestar.engine.AbstractManager;
import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;
import de.orangestar.engine.render.RenderManager;

/**
 * Handles the players input.
 * It also provides an adequate interface to the engine, that it can use the input data.
 * 
 * @author Basti
 *
 */
public class InputManager extends AbstractManager {
	
    // TODO: AsyncKeyListener for text input
    
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                               PUBLIC                               */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	@Override
	public void start() {
	    long windowHandle = RenderManager.Get().getMainWindow().handle();
	    _mouse = new Mouse();
	    
	    GLFW.glfwSetCursorPosCallback(windowHandle, _cursorPosCallback = new GLFWCursorPosCallback(){

			@Override
			public void invoke(long window, double x, double y) {
				_mouse.setxPos(x);
				_mouse.setyPos(y);
				//System.out.println(x + ":x " + y + ":y");
				
			} 
	    	
	    });
	    GLFW.glfwSetMouseButtonCallback(windowHandle, _mouseButtonCallback =  new GLFWMouseButtonCallback(){

			@Override
			public void invoke(long window, int button, int action, int mods) {
				switch(button) {
					case 0: _mouse.setButton0(action == 1 || action == 2);
						break;
					case 1: _mouse.setButton1(action == 1 || action == 2);
						break;
					case 2: _mouse.setButton2(action == 1 || action == 2);
				}		
			}
	    	
	    });
	    GLFW.glfwSetScrollCallback(windowHandle, _mouseScrollCallback = new GLFWScrollCallback() {

			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				_mouse.addScrollOffset(yoffset);
			}
	    	
	    });
	    
        // Keyboard
        _asyncKeyboard = new Keyboard();
        _syncKeyboard  = new Keyboard();
        
        GLFW.glfwSetKeyCallback(windowHandle, _keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int glfwkey, int scancode, int action, int mods) {
                Key key = _asyncKeyboard.getKeyByGLFW(glfwkey);
                if (key != null) {
                    key.setStatus(action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT);
                }
            }
        });
	}
	
    @Override
    public void update() {
        // Synchronize keyboard input, so that the exposed Keyboard instance doesn't change between ticks.
        for(Key asyncKey : _asyncKeyboard) {
            Key syncKey = _syncKeyboard.getKeyByGLFW(asyncKey._glfwKey);
            syncKey.setStatus(asyncKey.getState().isDown());
        }
        
        for(GameObject obj : World.Get()) {
            if (obj.getInputModule() != null) {
                obj.getInputModule().onUpdate();
            }
        }
        
        _mouse.setScrollOffset(0.0);
    }
    	
	@Override
	public void shutdown() {
		_keyCallback.release();
		_mouseButtonCallback.release();
		_cursorPosCallback.release();
	}
	
	public Mouse getMouse() {
	    return _mouse;
	}
	
	public Keyboard getKeyboard() {
	    return _syncKeyboard;
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~a~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                              PRIVATE                               */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private Mouse 			   _mouse;
	private GLFWCursorPosCallback      _cursorPosCallback;
	private GLFWMouseButtonCallback    _mouseButtonCallback;
	private GLFWScrollCallback		   _mouseScrollCallback;

    // Keyboard
    private GLFWKeyCallback     _keyCallback;
    
    private Keyboard            _asyncKeyboard;
    private Keyboard            _syncKeyboard;
    
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
