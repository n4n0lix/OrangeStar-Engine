package de.orangestar.engine.input;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.orangestar.engine.debug.DebugManager;
import de.orangestar.engine.debug.EngineException;
import de.orangestar.engine.input.Key.KeyState;

/**
 * A keyboard that contains multiple {@link Key}s.
 * 
 * @author Basti
 */
public class Keyboard implements Iterable<Key> {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private List<Key> _allKeys = new LinkedList<>();
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public final Key Esc        = new Key(this, GLFW_KEY_ESCAPE);
    public final Key Enter      = new Key(this, GLFW_KEY_ENTER);
    public final Key Spacebar   = new Key(this, GLFW_KEY_SPACE);
    public final Key ShiftLeft  = new Key(this, GLFW_KEY_LEFT_SHIFT);
    public final Key ShiftRight = new Key(this, GLFW_KEY_RIGHT_SHIFT);
    public final Key CtrlLeft   = new Key(this, GLFW_KEY_LEFT_CONTROL);
    public final Key CtrlRight  = new Key(this, GLFW_KEY_RIGHT_CONTROL);
    public final Key AltLeft    = new Key(this, GLFW_KEY_LEFT_ALT);
    public final Key AltRight   = new Key(this, GLFW_KEY_RIGHT_ALT);
    public final Key CapsLock   = new Key(this, GLFW_KEY_CAPS_LOCK);
    public final Key Tab        = new Key(this, GLFW_KEY_TAB);
    
    public final Key _0 = new Key(this, GLFW_KEY_0);
    public final Key _1 = new Key(this, GLFW_KEY_1);
    public final Key _2 = new Key(this, GLFW_KEY_2);
    public final Key _3 = new Key(this, GLFW_KEY_3);
    public final Key _4 = new Key(this, GLFW_KEY_4);
    public final Key _5 = new Key(this, GLFW_KEY_5);
    public final Key _6 = new Key(this, GLFW_KEY_6);
    public final Key _7 = new Key(this, GLFW_KEY_7);
    public final Key _8 = new Key(this, GLFW_KEY_8);
    public final Key _9 = new Key(this, GLFW_KEY_9);
    
    public final Key A = new Key(this, GLFW_KEY_A);
    public final Key B = new Key(this, GLFW_KEY_B);
    public final Key C = new Key(this, GLFW_KEY_C);
    public final Key D = new Key(this, GLFW_KEY_D);
    public final Key E = new Key(this, GLFW_KEY_E);
    public final Key F = new Key(this, GLFW_KEY_F);
    public final Key G = new Key(this, GLFW_KEY_G);
    public final Key H = new Key(this, GLFW_KEY_H);
    public final Key I = new Key(this, GLFW_KEY_I);
    public final Key J = new Key(this, GLFW_KEY_J);
    public final Key K = new Key(this, GLFW_KEY_K);
    public final Key L = new Key(this, GLFW_KEY_L);
    public final Key M = new Key(this, GLFW_KEY_M);
    public final Key N = new Key(this, GLFW_KEY_N);
    public final Key O = new Key(this, GLFW_KEY_O);
    public final Key P = new Key(this, GLFW_KEY_P);
    public final Key Q = new Key(this, GLFW_KEY_Q);
    public final Key R = new Key(this, GLFW_KEY_R);
    public final Key S = new Key(this, GLFW_KEY_S);
    public final Key T = new Key(this, GLFW_KEY_T);
    public final Key U = new Key(this, GLFW_KEY_U);
    public final Key V = new Key(this, GLFW_KEY_V);
    public final Key W = new Key(this, GLFW_KEY_W);
    public final Key X = new Key(this, GLFW_KEY_X);
    public final Key Y = new Key(this, GLFW_KEY_Y);
    public final Key Z = new Key(this, GLFW_KEY_Z);
    
    public final Key F1  = new Key(this, GLFW_KEY_F1);
    public final Key F2  = new Key(this, GLFW_KEY_F2);
    public final Key F3  = new Key(this, GLFW_KEY_F3);
    public final Key F4  = new Key(this, GLFW_KEY_F4);
    public final Key F5  = new Key(this, GLFW_KEY_F5);
    public final Key F6  = new Key(this, GLFW_KEY_F6);
    public final Key F7  = new Key(this, GLFW_KEY_F7);
    public final Key F8  = new Key(this, GLFW_KEY_F8);
    public final Key F9  = new Key(this, GLFW_KEY_F9);
    public final Key F10 = new Key(this, GLFW_KEY_F10);
    public final Key F11 = new Key(this, GLFW_KEY_F11);
    public final Key F12 = new Key(this, GLFW_KEY_F12);
    
    @Override
    public Iterator<Key> iterator() {
        return _allKeys.iterator();
    }
    
    public String toString() {
        StringBuilder builder = new StringBuilder("Keyboard { Pressed: ");
        
        for(Key key : this) {
            if (KeyState.isDown(key.getState())) {
                builder.append(key._glfwKey).append(" ");
            }
        }
        
        return builder.append("}").toString();
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Package                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    void addKey(Key key) {
        _allKeys.add(key);
    }
    
    /**
     * Returns a Key instance that represents the given glfwkey number.
     * @param glfwkey The glfw key number
     * @return A Key instance or null if no key of this keyboard instance represents glfwkey
     */
    Key getKeyByGLFW(int glfwkey) {
        for(Key key : this) {
            if (key._glfwKey == glfwkey) {
                return key;
            }
        }
        
        DebugManager.Get().info(Keyboard.class, "Unkown key pressed: " + glfwkey);
        
        return null;
    }
        
}
