package de.orangestar.engine.input;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Provides several layout classes that convert an input from one layout to another.
 * 
 * @author Basti
 */
public abstract class KeyboardLayout {

    public static final KeyboardLayout QWERTZ = new KeyboardLayout() {
        @Override
        public int convert(int glfwScanCode) {
            switch(glfwScanCode) {
                case GLFW_KEY_Z: return GLFW_KEY_Y;
                case GLFW_KEY_Y: return GLFW_KEY_Z;
                default: return glfwScanCode;
            }
        }
    };
    
    public static final KeyboardLayout QWERTY = new KeyboardLayout() {
        @Override
        public int convert(int glfwScanCode) {
            return glfwScanCode;
        }
    };
    
    public abstract int convert(int glfwScanCode);
    
}
