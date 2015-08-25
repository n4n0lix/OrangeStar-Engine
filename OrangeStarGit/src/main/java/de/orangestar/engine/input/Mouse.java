package de.orangestar.engine.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.glfw.GLFW;

public class Mouse {

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

	public final Key LEFT;
	public final Key RIGHT;

	public Mouse() {
	    _allKeys = new Key[10];
	    _listAllKeys = new ArrayList<>();
	    _readOnlyAllKeys = Collections.unmodifiableList(_listAllKeys);
	    
	    LEFT  = addKey( new Key( GLFW.GLFW_MOUSE_BUTTON_LEFT ));
	    RIGHT = addKey(new Key( GLFW.GLFW_MOUSE_BUTTON_RIGHT ));
	}
	
	public List<Key> getAllKeys() {
	    return _readOnlyAllKeys;
	}
	
	public double getScrollOffset() {
		return _scrollOffset;
	}
	
	public double getX() {
	    return _xPos;
	}

	public void setXPos(double x) {
		_xPos = x;
	}

	public double getY() {
	    return _yPos;
	}

	public void setYPos(double y) {
		_yPos = y;
	}
	
	public void setScrollOffset(double scrolling) {
		_scrollOffset = scrolling;
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Package                              */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
    /**
     * Returns a Key instance that represents the given glfwkey number.
     * @param glfwkey The glfw key number
     * @return A Key instance or null if no key of this keyboard instance represents glfwkey
     */
    Key getKeyByGLFW(int glfwkey) {
        if (glfwkey >= _allKeys.length || glfwkey < 0) {
            return null;
        }
        
        return _allKeys[glfwkey];
    }
    
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private Key addKey(Key key) {
	    _listAllKeys.add(key);
	    _allKeys[key._glfwCode] = key;
	    return key;
	}
	
    private double  _xPos;
    private double  _yPos;
    private double  _scrollOffset;
	
	private Key[] _allKeys;
	private List<Key> _listAllKeys;
	private List<Key> _readOnlyAllKeys;

}
