package de.orangestar.engine.input;

/**
 * Represents a key on a keyboard and its status.
 * 
 * @author Oliver &amp; Basti
 */
public class Key {

    /**
     * The press-state of a key.
     * 
     * @author Basti
     */
    public static enum KeyState {
        PRESSED, HOLD_DOWN, RELEASED, UP, UNKOWN;
        
        public boolean isDown() {
            return this == HOLD_DOWN || this == PRESSED;
        }
        
        public boolean isUp() {
            return this == UP || this == RELEASED;
        }
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Public-constructor
     * @param glfwcode The corresponding glfw key
     */
    public Key(int glfwcode) {
        _glfwCode = glfwcode;
        _state = KeyState.UNKOWN;
    }
    
    /**
     * Updates the key's status.
     * @param isDown If the current status is being pressed
     */
    public void setStatus(boolean isDown) {
        // DOWN -> RELEASED_UP
        if ((_state == KeyState.HOLD_DOWN || _state == KeyState.PRESSED || _state == KeyState.UNKOWN) && !isDown) {
            _state = KeyState.RELEASED;
            return;
        }
        
        // UP -> PRESSED_DOWN
        if ((_state == KeyState.UP || _state == KeyState.RELEASED || _state == KeyState.UNKOWN) && isDown) {
            _state = KeyState.PRESSED;
            return;
        }
        
        // PRESSED -> HOLD_DOWN
        if (_state == KeyState.PRESSED && isDown) {
            _state = KeyState.HOLD_DOWN;
            return;
        }
        
        // RELEASED -> HOLD_UP
        if (_state == KeyState.RELEASED && !isDown) {
            _state = KeyState.UP;
            return;
        }
    }
    
    /**
     * Returns the current key state.
     * @return A key state
     */
    public KeyState getState() {
        return _state;
    }
    
    /**
     * If this key is pressed.
     * @return If this key is pressed
     */
    public boolean isPressed() {
        return _state == KeyState.PRESSED;
    }
    
    /**
     * If this key is hold down.
     * @return If this key is hold down
     */
    public boolean isHoldDown() {
        return _state == KeyState.HOLD_DOWN;
    }
    
    /**
     * If this key is released.
     * @return If this key is released
     */
    public boolean isReleased() {
        return _state == KeyState.RELEASED;
    }
    
    /**
     * If this key is up.
     * @return If this key is up
     */
    public boolean isUp() {
        return _state == KeyState.UP;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Package                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    int _glfwCode;

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private KeyState _state;

}
