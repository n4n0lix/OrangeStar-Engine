package de.orangestar.engine.render;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

/**
 * A basic rendering window.
 * It wraps a GLFW-Window and its handling.
 * @author Basti
 *
 */
public class GLWindow {

	public static void doGuiEvents() {
		glfwPollEvents();
	}
	
	private long _handle;
	
	private int _width;
	private int _height;
	
	public GLWindow() {
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
 
        int WIDTH = 800;
        int HEIGHT = 600;
 
        // Create the window
        _handle = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( _handle == NULL ) {
        	throw new RuntimeException("Failed to create the GLFW window");
        }
	}
	
	/**
	 * Sets the position of the window.
	 * @param x The x coordiante
	 * @param y The y coordiante
	 */
	public void setPostion(int x, int y) {
        glfwSetWindowPos( _handle, x, y );
	}
		
	/**
	 * Returns the current width.
	 */
	public int 	getWidth() {
	    IntBuffer w = BufferUtils.createIntBuffer(1);
	    IntBuffer h = BufferUtils.createIntBuffer(1);
	    glfwGetWindowSize(_handle, w, h);
	    return w.get();
	}
	
    /**
     * Returns the current height.
     */
	public int 	getHeight() {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(_handle, w, h);
        return h.get();
	}
	
    /**
     * Returns the current rendering width.
     */
    public int  getRenderWidth() {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        glfwGetFramebufferSize(_handle, w, h);
        return w.get();
    }
    
    /**
     * Returns the current rendering height.
     */
    public int  getRenderHeight() {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        glfwGetFramebufferSize(_handle, w, h);
        return h.get();
    }
	
	public void show() {
        glfwShowWindow(_handle);
	}
	
	public long handle() {
		return _handle;
	}
	
	public void destroy() {
		glfwDestroyWindow(_handle);
	}

	public boolean closeRequested() {
	    return glfwWindowShouldClose(_handle) == GL_TRUE;
	}
}
