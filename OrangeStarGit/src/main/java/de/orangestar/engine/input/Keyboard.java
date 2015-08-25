package de.orangestar.engine.input;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A keyboard that contains multiple {@link Key}s.
 * 
 * @author Oliver &amp; Basti
 */
public class Keyboard {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public Keyboard() {
        _listAllKeys      = new ArrayList<>();
        _allKeys          = new Key[512];
        _readOnlyAllKeys  = Collections.unmodifiableList(_listAllKeys);
        
        _0 = addKey( new Key( GLFW_KEY_0 ));
        _1 = addKey( new Key( GLFW_KEY_1 ));
        _2 = addKey( new Key( GLFW_KEY_2 ));
        _3 = addKey( new Key( GLFW_KEY_3 ));
        _4 = addKey( new Key( GLFW_KEY_4 ));
        _5 = addKey( new Key( GLFW_KEY_5 ));
        _6 = addKey( new Key( GLFW_KEY_6 ));
        _7 = addKey( new Key( GLFW_KEY_7 ));
        _8 = addKey( new Key( GLFW_KEY_8 ));
        _9 = addKey( new Key( GLFW_KEY_9 ));
        
        Esc        = addKey( new Key( GLFW_KEY_ESCAPE ));       
        Enter      = addKey( new Key( GLFW_KEY_ENTER ));        
        Spacebar   = addKey( new Key( GLFW_KEY_SPACE ));        
        ShiftLeft  = addKey( new Key( GLFW_KEY_LEFT_SHIFT ));   
        ShiftRight = addKey( new Key( GLFW_KEY_RIGHT_SHIFT ));  
        CtrlLeft   = addKey( new Key( GLFW_KEY_LEFT_CONTROL )); 
        CtrlRight  = addKey( new Key( GLFW_KEY_RIGHT_CONTROL ));
        AltLeft    = addKey( new Key( GLFW_KEY_LEFT_ALT ));     
        AltRight   = addKey( new Key( GLFW_KEY_RIGHT_ALT ));    
        CapsLock   = addKey( new Key( GLFW_KEY_CAPS_LOCK ));    
        Tab        = addKey( new Key( GLFW_KEY_TAB ));   
        
        A = addKey( new Key( GLFW_KEY_A ));
        B = addKey( new Key( GLFW_KEY_B ));
        C = addKey( new Key( GLFW_KEY_C ));
        D = addKey( new Key( GLFW_KEY_D ));
        E = addKey( new Key( GLFW_KEY_E ));
        F = addKey( new Key( GLFW_KEY_F ));
        G = addKey( new Key( GLFW_KEY_G ));
        H = addKey( new Key( GLFW_KEY_H ));
        I = addKey( new Key( GLFW_KEY_I ));
        J = addKey( new Key( GLFW_KEY_J ));
        K = addKey( new Key( GLFW_KEY_K ));
        L = addKey( new Key( GLFW_KEY_L ));
        M = addKey( new Key( GLFW_KEY_M ));
        N = addKey( new Key( GLFW_KEY_N ));
        O = addKey( new Key( GLFW_KEY_O ));
        P = addKey( new Key( GLFW_KEY_P ));
        Q = addKey( new Key( GLFW_KEY_Q ));
        R = addKey( new Key( GLFW_KEY_R ));
        S = addKey( new Key( GLFW_KEY_S ));
        T = addKey( new Key( GLFW_KEY_T ));
        U = addKey( new Key( GLFW_KEY_U ));
        V = addKey( new Key( GLFW_KEY_V ));
        W = addKey( new Key( GLFW_KEY_W ));
        X = addKey( new Key( GLFW_KEY_X ));
        Y = addKey( new Key( GLFW_KEY_Y ));
        Z = addKey( new Key( GLFW_KEY_Z ));
        
        F1  = addKey( new Key( GLFW_KEY_F1 )); 
        F2  = addKey( new Key( GLFW_KEY_F2 )); 
        F3  = addKey( new Key( GLFW_KEY_F3 )); 
        F4  = addKey( new Key( GLFW_KEY_F4 )); 
        F5  = addKey( new Key( GLFW_KEY_F5 )); 
        F6  = addKey( new Key( GLFW_KEY_F6 )); 
        F7  = addKey( new Key( GLFW_KEY_F7 )); 
        F8  = addKey( new Key( GLFW_KEY_F8 )); 
        F9  = addKey( new Key( GLFW_KEY_F9 )); 
        F10 = addKey( new Key( GLFW_KEY_F10 ));
        F11 = addKey( new Key( GLFW_KEY_F11 ));
        F12 = addKey( new Key( GLFW_KEY_F12 ));
    }
    
    public final Key Esc;
    public final Key Enter;
    public final Key Spacebar;
    public final Key ShiftLeft;
    public final Key ShiftRight;
    public final Key CtrlLeft;
    public final Key CtrlRight;
    public final Key AltLeft;
    public final Key AltRight;
    public final Key CapsLock;
    public final Key Tab;
    
    public final Key _0;
    public final Key _1;
    public final Key _2;
    public final Key _3;
    public final Key _4;
    public final Key _5;
    public final Key _6;
    public final Key _7;
    public final Key _8;
    public final Key _9;

    public final Key A;
    public final Key B;
    public final Key C;
    public final Key D;
    public final Key E;
    public final Key F;
    public final Key G;
    public final Key H;
    public final Key I;
    public final Key J;
    public final Key K;
    public final Key L;
    public final Key M;
    public final Key N;
    public final Key O;
    public final Key P;
    public final Key Q;
    public final Key R;
    public final Key S;
    public final Key T;
    public final Key U;
    public final Key V;
    public final Key W;
    public final Key X;
    public final Key Y;
    public final Key Z;
    
    public final Key F1;
    public final Key F2;
    public final Key F3;
    public final Key F4;
    public final Key F5;
    public final Key F6;
    public final Key F7;
    public final Key F8;
    public final Key F9;
    public final Key F10;
    public final Key F11;
    public final Key F12;
    
    /**
     * Returns a read only list of all keys of this keyboard.
     * @return A read only list of all keys of this keyboard
     */
    public List<Key> getKeys() {
        return _readOnlyAllKeys;
    }
    
    public String toString() {
        StringBuilder builder = new StringBuilder("Keyboard { Pressed: ");
        
        for(Key key : getKeys()) {
            if (key.getState().isDown()) {
                builder.append(key._glfwCode).append(" ");
            }
        }
        
        return builder.append("}").toString();
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Package                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Adds a key.
     * @param key A key
     * @return The key
     */
    Key addKey(Key key) {
        _listAllKeys.add(key);
        _allKeys[key._glfwCode] = key;
        return key;
    }
    
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
    
    private List<Key> _listAllKeys;           
    private Key[] _allKeys;
    private List<Key> _readOnlyAllKeys;
    
    
}
