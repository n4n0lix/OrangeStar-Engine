package de.orangestar.engine.input;

import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;
import de.orangestar.engine.debug.Logger;
import de.orangestar.engine.debug.Logger.Level;

/**
 * Handles the players input.
 * It also provides an adequate interface to the engine, that it can use the input data.
 * 
 * @author Oliver &amp; Basti
 *
 */
public class InputEngine implements IInputEngine {
	
    // TODO: AsyncKeyListener for text input
    
    static { Logger.setLogging(InputEngine.class, Level.DEBUG); }
    
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                               Public                               */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	@Override
	public void onStart(long windowHandle) {
	    _syncMouse = new Mouse();
	    _asyncMouse = new Mouse();
	    
	    GLFW.glfwSetCursorPosCallback(windowHandle, _cursorPosCallback = new GLFWCursorPosCallback(){

			@Override
			public void invoke(long window, double x, double y) {
				_syncMouse.setXPos(x);
				_syncMouse.setYPos(y);
			} 
	    	
	    });
	    GLFW.glfwSetMouseButtonCallback(windowHandle, _mouseButtonCallback =  new GLFWMouseButtonCallback(){

			@Override
			public void invoke(long window, int button, int action, int mods) {
				_asyncMouse.getKeyByGLFW(button).setStatus(action == 1 || action == 2);
			}
	    	
	    });
	    GLFW.glfwSetScrollCallback(windowHandle, _scrollCallback = new GLFWScrollCallback() {

			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				_asyncScrollOffset += yoffset;
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
    public void onUpdate() {
    	// synchronize the Mouse input, so that the exposed Mouse instance doesn't change between ticks.
        syncMouse();
        
        // Synchronize keyboard input, so that the exposed Keyboard instance doesn't change between ticks.
        syncKeyboard();
        
        // 2# Execute InputComponents
        List<World> worlds = World.getAllWorlds();
        for(int worldI = 0; worldI < worlds.size(); worldI++) {
            List<GameObject> gameobjects = worlds.get(worldI).getGameObjects();
            
            for(int gameObjectI = 0; gameObjectI < gameobjects.size(); gameObjectI++) {
                InputComponent input = gameobjects.get(gameObjectI).getInputComponent();
                
                if (input != null) {
                    input.onUpdate();
                }
            }
        }
        
        _syncMouse.setScrollOffset(_asyncScrollOffset);
        _asyncScrollOffset = 0.0;
    }
    	
	@Override
	public void onShutdown() {
		_keyCallback.release();
		_mouseButtonCallback.release();
		_cursorPosCallback.release();
		_scrollCallback.release();
	}
	
	/**
	 * Returns the local mouse.
	 * @return The local mouse
	 */
	@Override
	public Mouse getMouse() {
	    return _syncMouse;
	}
	
    /**
     * Returns the local keyboard.
     * @return The local keyboard
     */
	@Override
	public Keyboard getKeyboard() {
	    return _syncKeyboard;
	}
	
    @Override
    public void addWorld(World world) {
        _worlds.add(world);
    }

    @Override
    public void removeWorld(World world) {
        _worlds.remove(world);
    }
    
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                              Private                               */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private List<World> _worlds;
    
	// TODO: A lot of copy+pasta code, maybe extract to method or handle differently
	private void syncMouse() {
        List<Key> asyncMouseKeys = _asyncMouse.getAllKeys();
        for(int i = 0; i < asyncMouseKeys.size(); i++) {
            Key asyncKey = asyncMouseKeys.get(i);
            Key syncKey = _syncMouse.getKeyByGLFW(asyncKey._glfwCode);
            syncKey.setStatus(asyncKey.getState().isDown());
        }
	}
	
	private void syncKeyboard() {
        List<Key> asyncKeyboardKeys = _asyncKeyboard.getKeys();
        for(int i = 0; i < asyncKeyboardKeys.size(); i++) {
            Key asyncKey = asyncKeyboardKeys.get(i);
            Key syncKey = _syncKeyboard.getKeyByGLFW(asyncKey._glfwCode);
            syncKey.setStatus(asyncKey.getState().isDown());
        }
	}
	
	// Mouse
	private Mouse 			           _syncMouse;
    private Mouse                      _asyncMouse;
    private double                     _asyncScrollOffset;
	
	private GLFWCursorPosCallback      _cursorPosCallback;
	private GLFWMouseButtonCallback    _mouseButtonCallback;
	private GLFWScrollCallback         _scrollCallback;

    // Keyboard
    private GLFWKeyCallback     _keyCallback;
    
    private Keyboard            _asyncKeyboard;
    private Keyboard            _syncKeyboard;
    
}
